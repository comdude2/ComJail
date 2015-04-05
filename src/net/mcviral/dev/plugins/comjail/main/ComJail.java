package net.mcviral.dev.plugins.comjail.main;

import org.bukkit.plugin.java.JavaPlugin;

import net.mcviral.dev.plugins.comjail.events.JailCommand;

public class ComJail extends JavaPlugin{

	private JailController jailcontroller;
	
	public void onEnable(){
		this.saveDefaultConfig();
		jailcontroller = new JailController(this);
		JailCommand command = new JailCommand(this, jailcontroller);
		getCommand("jail").setExecutor(command);
		getCommand("jailcheck").setExecutor(command);
		this.getLogger().info("ComJail V" + this.getDescription().getVersion() + " Enabled!");
	}
	
	public void onDisable(){
		jailcontroller.shutdown();
		this.getLogger().info("ComJail Disabled!");
	}
	
}
