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
