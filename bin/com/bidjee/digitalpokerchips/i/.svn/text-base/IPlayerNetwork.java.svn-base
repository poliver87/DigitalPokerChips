package com.bidjee.digitalpokerchips.i;

import com.bidjee.digitalpokerchips.c.ThisPlayer;
import com.bidjee.digitalpokerchips.m.DiscoveredTable;

public interface IPlayerNetwork {
	public void startRequestGames();
	public void stopRequestGames();
	public void requestConnect(DiscoveredTable table_, int azimuth_,int[] chipNumbers);
	public void setName(String name_);
	public void submitMove(int move,String chipString);
	public void leaveTable();
	public void setPlayer(ThisPlayer player);
	public void sendBell(String hostName);
	public void requestInvitation(byte[] hostBytes);
}
