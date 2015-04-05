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

package net.mcviral.dev.plugins.comjail.objects;

import java.util.Date;
import java.util.UUID;

import org.bukkit.Location;

public class JailedPlayer {

	private UUID uuid;
	private Date expires;
	//private boolean muted;
	private Location previousloc;
	
	public JailedPlayer(UUID muuid, Date mexpires, Location mpreviousloc){
		uuid = muuid;
		expires = mexpires;
		previousloc = mpreviousloc;
	}
	
	public UUID getUUID(){
		return uuid;
	}
	
	public Date getExpires(){
		return expires;
	}
	
	public Location getPreviousLocation(){
		return previousloc;
	}
	
}
