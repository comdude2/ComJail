package net.mcviral.dev.plugins.comjail.main;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.mcviral.dev.plugins.comjail.events.CommandListener;
import net.mcviral.dev.plugins.comjail.events.JoinEvent;
import net.mcviral.dev.plugins.comjail.events.KickEvent;
import net.mcviral.dev.plugins.comjail.events.QuitEvent;
import net.mcviral.dev.plugins.comjail.events.TeleportEvent;
import net.mcviral.dev.plugins.comjail.objects.JailedPlayer;

public class JailController {

	private Plugin plugin;
	private LinkedList <JailedPlayer> jailedplayers = new LinkedList <JailedPlayer> ();
	protected int taskid;
	private Location jail;
	
	public JailController(Plugin mplugin){
		plugin = mplugin;
		jail = new Location(plugin.getServer().getWorld("prison"), 1210, 73, 63);
		//jail = new Location(plugin.getServer().getWorld("world"), 176, 67, 251);
		plugin.getServer().getPluginManager().registerEvents(new JoinEvent(plugin, this), plugin);
		plugin.getServer().getPluginManager().registerEvents(new QuitEvent(plugin, this), plugin);
		plugin.getServer().getPluginManager().registerEvents(new KickEvent(plugin, this), plugin);
		plugin.getServer().getPluginManager().registerEvents(new TeleportEvent(plugin, this), plugin);
		plugin.getServer().getPluginManager().registerEvents(new CommandListener(plugin, this), plugin);
		
		taskid = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				if (jailedplayers.size() > 0){
					Date date = new Date();
					for (JailedPlayer jp : jailedplayers){
						if (jp.getExpires().before(date)){
							boolean worked = unjail(jp.getUUID());
							if (worked){
								Player player = null;
								for (Player extract : plugin.getServer().getOnlinePlayers()){
									if (extract.getUniqueId().equals(jp.getUUID())){
										player = extract;
									}
								}
								if (player != null){
									player.sendMessage(me() + ChatColor.GREEN + "Your jail time is up. Have a nice day ^.^");
								}else{
									plugin.getLogger().warning("Failed to unjail player with UUID: " + jp.getUUID().toString() + " ##### They seem to be offline? #####");
								}
							}else{
								plugin.getLogger().warning("Failed to unjail player with UUID: " + jp.getUUID().toString());
							}
						}
					}
				}
			}
		}, 0L, 40L);
	}
	
	public void shutdown(){
		plugin.getServer().getScheduler().cancelTask(taskid);
	}
	
	public boolean jail(UUID uuid, Date expires){
		if (isJailed(uuid) == false){
			Player player = null;
			for (Player extract : plugin.getServer().getOnlinePlayers()){
				if (extract.getUniqueId().equals(uuid)){
					player = extract;
					break;
				}
			}
			if (player != null){
				JailedPlayer jp = new JailedPlayer(uuid, expires, player.getLocation());
				player.teleport(jail);
				jailedplayers.add(jp);
				plugin.getLogger().info("UUID: " + uuid.toString() + " jailed.");
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public boolean unjail(UUID uuid){
		Player player = null;
		for (Player extract : plugin.getServer().getOnlinePlayers()){
			if (extract.getUniqueId().equals(uuid)){
				player = extract;
				break;
			}
		}
		if (player != null){
			for (JailedPlayer jp : jailedplayers){
				if (jp.getUUID().equals(uuid)){
					jailedplayers.remove(jp);
					player.teleport(jp.getPreviousLocation());
					plugin.getLogger().info("UUID: " + uuid.toString() + " unjailed.");
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isJailed(UUID uuid){
		for (JailedPlayer jp : jailedplayers){
			if (jp.getUUID().equals(uuid)){
				return true;
			}
		}
		return false;
	}
	
	public LinkedList <JailedPlayer> getJailed(){
		return jailedplayers;
	}
	
	public String me(){
		return ("[" + ChatColor.BLUE + "GUARD" + ChatColor.WHITE + "] ");
	}
	
}
