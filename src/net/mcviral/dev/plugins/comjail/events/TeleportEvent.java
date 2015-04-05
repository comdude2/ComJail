package net.mcviral.dev.plugins.comjail.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import net.mcviral.dev.plugins.comjail.main.JailController;

public class TeleportEvent implements Listener{

	@SuppressWarnings("unused")
	private Plugin plugin;
	private JailController jailcontroller;
	
	public TeleportEvent(Plugin mplugin, JailController mjailcontroller){
		plugin = mplugin;
		jailcontroller = mjailcontroller;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerTeleport(PlayerTeleportEvent event){
		if (jailcontroller.isJailed(event.getPlayer().getUniqueId())){
			if (!event.getPlayer().hasPermission("jail.override")){
				event.setCancelled(true);
				event.getPlayer().sendMessage("[" + ChatColor.BLUE + "GUARD" + ChatColor.WHITE + "] " + ChatColor.RED + "You are jailed, you may not teleport.");
			}
		}
	}
	
}
