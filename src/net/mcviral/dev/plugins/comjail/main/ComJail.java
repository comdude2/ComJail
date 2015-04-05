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
