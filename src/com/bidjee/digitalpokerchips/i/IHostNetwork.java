package com.bidjee.digitalpokerchips.i;

import java.util.ArrayList;

import com.bidjee.digitalpokerchips.c.Table;
import com.bidjee.digitalpokerchips.m.MovePrompt;
import com.bidjee.digitalpokerchips.m.Player;

public interface IHostNetwork {
	
	public String getTableName();
	public String getIpAddress();
	boolean getWifiEnabled();
	public void setTable(Table table);
	public void sendDealerChip(String playerName);
	public void recallDealerChip(String playerName);
	public void sendChips(String playerName,String chipString);
	public void setColor(String playerName,int color);
	public void sendTextMessage(String playerName,String message);
	public void removePlayer(String playerName);
	public void createTable(String tableName);
	public void destroyTable();
	public void syncPlayersChips(String playerName,int chipAmount);
	public void syncAllTableStatusMenu(ArrayList<Player> players);
	public void promptMove(String playerName,MovePrompt movePrompt,int chipAmount);
	public void cancelMove(String playerName);
	public void sendBell(String playerName);
	public void enableNudge(String dstPlayerName,String nudgablePlayerName);
	public void disableNudge();
	public void arrange();
	public void selectDealer();
	public void promptWaitNextHand(String playerName);
	public void parsePlayerMessage(String playerName,String msg);
}
