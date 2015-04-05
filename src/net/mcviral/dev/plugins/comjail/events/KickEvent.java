package net.mcviral.dev.plugins.comjail.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.Plugin;

import net.mcviral.dev.plugins.comjail.main.JailController;
import net.mcviral.dev.plugins.comjail.objects.JailedPlayer;
import net.mcviral.dev.plugins.comjail.util.FileManager;

public class KickEvent implements Listener{

	private Plugin plugin;
	private JailController jailcontroller;
	
	public KickEvent(Plugin mplugin, JailController mjailcontroller){
		plugin = mplugin;
		jailcontroller = mjailcontroller;
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event){
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
				fm.reloadYAML();
				fm.getYAML().set("expires", jp.getExpires().getTime());
				fm.getYAML().set("location.world", event.getPlayer().getLocation().getWorld());
				fm.getYAML().set("location.x", event.getPlayer().getLocation().getX());
				fm.getYAML().set("location.y", event.getPlayer().getLocation().getY());
				fm.getYAML().set("location.z", event.getPlayer().getLocation().getZ());
				fm.saveYAML();
				jailcontroller.getJailed().remove(jp);
			}
		}
	}
	
}
