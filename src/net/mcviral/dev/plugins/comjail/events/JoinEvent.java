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
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import net.mcviral.dev.plugins.comjail.main.JailController;
import net.mcviral.dev.plugins.comjail.objects.JailedPlayer;
import net.mcviral.dev.plugins.comjail.util.FileManager;

public class JoinEvent implements Listener{

	private Plugin plugin;
	private JailController jailcontroller;
	
	public JoinEvent(Plugin mplugin, JailController mjailcontroller){
		plugin = mplugin;
		jailcontroller = mjailcontroller;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		FileManager fm = new FileManager(plugin, "", event.getPlayer().getUniqueId().toString());
		if (fm.exists()){
			fm.reloadYAML();
			Date d = new Date();
			d.setTime(fm.getYAML().getLong("expires"));
			JailedPlayer jp = new JailedPlayer(event.getPlayer().getUniqueId(), d, new Location(plugin.getServer().getWorld(fm.getYAML().getString("location.world")), fm.getYAML().getDouble("location.x"), fm.getYAML().getDouble("location.y"), fm.getYAML().getDouble("location.z")));
			fm.delete();
			jailcontroller.getJailed().add(jp);
			event.getPlayer().sendMessage("[" + ChatColor.BLUE + "GUARD" + ChatColor.WHITE + "] " + ChatColor.GREEN + "You are in jail.");
		}
	}
	
}
