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
	public void sendChipsBuyin(String playerName,String chipString);
	public void sendChipsWin(String playerName,String chipString);
	public void setColor(String playerName,int color);
	public void removePlayer(String playerName);
	public void createTable(String tableName);
	public void destroyTable();
	public void syncPlayersChips(String playerName,int chipAmount);
	public void syncAllTableStatusMenu(ArrayList<Player> players);
	public void promptMove(String playerName,MovePrompt movePrompt,int chipTotal);
	public void cancelMove(String playerName);
	public void sendBell(String playerName);
	public void arrange();
	public void selectDealer();
	public void promptWaitNextHand(String playerName);
	public void promptDealer(String playerName,int dealStage);
	public void notifyPlayerWaitDealer(String playerName,String dealerName,int dealStage);
	public void notifyPlayerWaitBet(String playerName,String betterName);
	public void promptShowCards(String playerName);
	public void parsePlayerMessage(String playerName,String msg);
}
