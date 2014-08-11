package com.bidjee.digitalpokerchips.i;

import java.util.ArrayList;

import com.bidjee.digitalpokerchips.c.Table;
import com.bidjee.digitalpokerchips.m.Player;

public interface IHostNetwork {
	
	public String getTableName();
	public String getIpAddress();
	boolean getWifiEnabled();
	public void setTable(Table table);
	public void sendDealerChip(String dealerHostName);
	public void recallDealerChip(String dealerHostName);
	public void sendChips(String hostName,int position,String chipString);
	public void sendSetupInfo(String hostName,int position,int color,String chipString);
	public void sendTextMessage(String hostName_,String message);
	public void removePlayer(String hostName);
	public void startLobby(boolean loadedGame,ArrayList<String> playerNames);
	public void stopLobby();
	public void createTable(String tableName);
	public void destroyTable();
	public void syncAllTableStatusMenu(ArrayList<Player> players);
	public void promptMove(String hostName,int position,int stake,boolean foldEnabled,String message,String messageStateChange);
	public void cancelMove(String hostName);
	public void sendBell(String destHostName);
	public void enableNudge(String dstHostName,String nudgableHostName);
	public void disableNudge();
	public void showConnection(String hostName);
	public void hideConnection(String hostName);
	public void promptWaitNextHand(String hostName);	
}
