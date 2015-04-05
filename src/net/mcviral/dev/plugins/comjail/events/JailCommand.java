/*
ComJail - A jail plugin for Minecraft servers
Copyright (C) 2015  comdude2 (Matt Armer)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Contact: admin@mcviral.net
*/

package net.mcviral.dev.plugins.comjail.events;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.mcviral.dev.plugins.comjail.main.JailController;
import net.mcviral.dev.plugins.comjail.objects.JailedPlayer;

public class JailCommand implements CommandExecutor{
	
	private Plugin plugin;
	private JailController jailcontroller;
	
	public JailCommand(Plugin mplugin, JailController mjailcontroller){
		plugin = mplugin;
		jailcontroller = mjailcontroller;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("jail")){
			if (sender.hasPermission("jail.use")){
				if (args.length > 0){
					if (args.length == 3){
						Player player = null;
						for (Player extract : plugin.getServer().getOnlinePlayers()){
							if (extract.getName().equalsIgnoreCase(args[0])){
								player = extract;
							}
						}
						if (player != null){
							Date date = new Date();
							Integer i = null;
							try{
								i = Integer.parseInt(args[1]);
							}catch(Exception e){
								
							}
							if (i != null){
								if (i > 0){
									if (i < 31){
										//Jail
										Long time = date.getTime();
										time += (i * ( 60 * 1000));
										date.setTime(time);
										boolean worked = jailcontroller.jail(player.getUniqueId(), date);
										if (worked == true){	
											sender.sendMessage(me() + ChatColor.DARK_GREEN + "Player jailed.");
											player.sendMessage(me() + ChatColor.RED + "You have been jailed for " + ChatColor.BLUE + i + " minutes by " + ChatColor.YELLOW + sender.getName() + " for " + ChatColor.DARK_GREEN + args[2]);
										}else{
											sender.sendMessage(me() + ChatColor.RED + "That player isn't online, you sure you're not seeing things? " + ChatColor.YELLOW + ">.>");
										}
									}else{
										sender.sendMessage(me() + ChatColor.RED + "You can't jail someone for longer than 30 minutes. " + ChatColor.YELLOW + "You nub.");
									}
								}else{
									sender.sendMessage(me() + ChatColor.RED + "You can't jail someone for less than a minute, you poop! :P");
								}
							}else{
								sender.sendMessage(me() + ChatColor.RED + "Invalid time parameter, do not use letters like 'm' for minutes, please only write the number of minutes.");
							}
						}else{
							sender.sendMessage(me() + ChatColor.RED + "That player isn't online, you sure you're not seeing things? " + ChatColor.YELLOW + ">.>");
						}
					}else if (args.length == 2){
						if (args[0].equalsIgnoreCase("unjail")){
							Player player = null;
							for (Player extract : plugin.getServer().getOnlinePlayers()){
								if (extract.getName().equalsIgnoreCase(args[1])){
									player = extract;
								}
							}
							if (player != null){
								if (jailcontroller.isJailed(player.getUniqueId())){
									boolean worked = jailcontroller.unjail(player.getUniqueId());
									if (worked == true){
										sender.sendMessage(me() + ChatColor.DARK_GREEN + "Player unjailed.");
										player.sendMessage(me() + ChatColor.YELLOW + "You have been unjailed by " + sender.getName());
									}else{
										sender.sendMessage(me() + ChatColor.DARK_RED + "Could not jail player!");
									}
								}else{
									sender.sendMessage(me() + ChatColor.RED + "That person is not in jail.");
								}
							}else{
								sender.sendMessage(me() + ChatColor.RED + "That player is not online.");
							}
						}else{
							help(sender);
						}
					}else if (args.length == 1){
						if (args[0].equalsIgnoreCase("list")){
							if (sender.hasPermission("jail.admin")){
								sender.sendMessage(me() + ChatColor.BLUE + "Jailed Players:");
								for (JailedPlayer jp : jailcontroller.getJailed()){
									Player player = null;
									for (Player extract : plugin.getServer().getOnlinePlayers()){
										if (extract.getUniqueId().equals(jp.getUUID())){
											player = extract;
											break;
										}
									}
									sender.sendMessage(me() + ChatColor.BLUE + "----------------------------------");
									sender.sendMessage(me() + ChatColor.YELLOW + "Player UUID: " + ChatColor.DARK_GREEN + jp.getUUID().toString());
									if (player != null){
										sender.sendMessage(me() + ChatColor.YELLOW + "Player name: " + ChatColor.DARK_GREEN + player.getName());
									}else{
										sender.sendMessage(me() + ChatColor.YELLOW + "Player name: " + ChatColor.DARK_RED + "NULL");
									}
									sender.sendMessage(me() + ChatColor.YELLOW + "Expires: " + ChatColor.DARK_GREEN + jp.getExpires().toString());
								}
							}else{
								sender.sendMessage(me() + ChatColor.DARK_RED + "You don't have permission to do this!");
							}
						}else{
							help(sender);
						}
					}else{
						help(sender);
					}
				}else{
					//sender.sendMessage(me() + ChatColor.RED + "");
					help(sender);
				}
			}else{
				sender.sendMessage(me() + ChatColor.DARK_RED + "You don't have permission to do this!");
			}
			return true;
		}else if (cmd.getName().equalsIgnoreCase("jailcheck")){
			if (sender instanceof Player){
				Player player = (Player) sender;
				if (jailcontroller.isJailed(player.getUniqueId())){
					JailedPlayer jp = null;
					for (JailedPlayer mjp : jailcontroller.getJailed()){
						if (mjp.getUUID().equals(player.getUniqueId())){
							jp = mjp;
						}
					}
					if (jp != null){
						Date date = new Date();
						sender.sendMessage(me() + ChatColor.YELLOW + "You will be unjailed in: " + ChatColor.BLUE + ((jp.getExpires().getTime() - date.getTime()) / 1000) + " seconds.");
					}else{
						sender.sendMessage(me() + ChatColor.DARK_RED + "ERROR: Could find you on pass1 but not on pass2.");
					}
				}else{
					sender.sendMessage(me() + ChatColor.RED + "You are not in jail.");
				}
			}else{
				sender.sendMessage(me() + ChatColor.RED + "You need to be a player to perform this command.");
			}
			return true;
		}else{
			return false;
		}
	}
	
	public void help(CommandSender sender){
		sender.sendMessage(me() + ChatColor.YELLOW + "ComJail V" + plugin.getDescription().getVersion());
		sender.sendMessage(me() + ChatColor.DARK_GREEN + "/jail <player> <time in minutes> <reason>");
		sender.sendMessage(me() + ChatColor.DARK_GREEN + "/jail unjail <player>");
	}
	
	public String me(){
		return ("[" + ChatColor.BLUE + "GUARD" + ChatColor.WHITE + "] ");
	}

}
