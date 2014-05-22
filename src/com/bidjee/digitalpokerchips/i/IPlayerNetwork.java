package com.bidjee.digitalpokerchips.i;

import com.bidjee.digitalpokerchips.c.ThisPlayer;
import com.bidjee.digitalpokerchips.m.DiscoveredTable;
import com.bidjee.digitalpokerchips.m.Move;

public interface IPlayerNetwork {
	public void startRequestGames();
	public void stopRequestGames();
	public void requestConnect(DiscoveredTable table_, int azimuth_,int[] chipNumbers);
	public void stopListen();
	public void setName(String name);
	public void submitMove(Move move);
	public void leaveTable();
	public void setPlayer(ThisPlayer player);
	public void sendBell(String hostName);
	public void requestInvitation(byte[] hostBytes);
	
}
