package net.mcviral.dev.plugins.comjail.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import net.mcviral.dev.plugins.comjail.main.JailController;

public class CommandListener implements Listener{
	
	@SuppressWarnings("unused")
	private Plugin plugin;
	private JailController jailcontroller;
	
	public CommandListener(Plugin mplugin, JailController mjailcontroller){
		plugin = mplugin;
		jailcontroller = mjailcontroller;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){
		if (event.getMessage().startsWith("/jail")){
			//Allow
		}else if (jailcontroller.isJailed(event.getPlayer().getUniqueId()) && (!event.getPlayer().hasPermission("jail.override"))){
			event.setCancelled(true);
			event.getPlayer().sendMessage("[" + ChatColor.BLUE + "GUARD" + ChatColor.WHITE + "] " + ChatColor.RED + "You are jailed, you're not allowed to perform commands.");
		}
	}
	
}
