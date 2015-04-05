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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import net.mcviral.dev.plugins.comjail.main.JailController;
import net.mcviral.dev.plugins.comjail.objects.JailedPlayer;
import net.mcviral.dev.plugins.comjail.util.FileManager;

public class QuitEvent implements Listener{

	private Plugin plugin;
	private JailController jailcontroller;
	
	public QuitEvent(Plugin mplugin, JailController mjailcontroller){
		plugin = mplugin;
		jailcontroller = mjailcontroller;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		if (jailcontroller.isJailed(event.getPlayer().getUniqueId())){
			FileManager fm = new FileManager(plugin, "", event.getPlayer().getUniqueId().toString());
			if (fm.exists()){
				fm.delete();
			}
			JailedPlayer jp = null;
			for (JailedPlayer mjp : jailcontroller.getJailed()){
				if (mjp.getUUID().equals(event.getPlayer().getUniqueId())){
					jp = mjp;
					break;
				}
			}
			if (jp != null){
				//bug
				fm.reloadYAML();
				fm.getYAML().set("expires", jp.getExpires().getTime());
				fm.getYAML().set("location.world", jp.getPreviousLocation().getWorld().getName());
				fm.getYAML().set("location.x", jp.getPreviousLocation().getX());
				fm.getYAML().set("location.y", jp.getPreviousLocation().getY());
				fm.getYAML().set("location.z", jp.getPreviousLocation().getZ());
				fm.saveYAML();
				jailcontroller.getJailed().remove(jp);
			}
		}
	}
	
}
