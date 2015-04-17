package net.mcviral.dev.plugins.comjail.events;

import net.mcviral.dev.plugins.comjail.main.JailController;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEvent implements Listener{
	
	private JailController jailcontroller;
	
	public RespawnEvent(JailController mjailcontroller){
		jailcontroller = mjailcontroller;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event){
		if (jailcontroller.isJailed(event.getPlayer().getUniqueId())){
			event.setRespawnLocation(jailcontroller.getJail());
		}
	}
	
}
