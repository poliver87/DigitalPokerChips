package com.bidjee.digitalpokerchips.m;

import java.util.ArrayList;

public class GameMenuData {
	
	public String gameName;
	public int dealStage;
	public int potTotal;
	public ArrayList<PlayerMenuItem> players;
	
	public GameMenuData() {
		players=new ArrayList<PlayerMenuItem>();
		gameName="";
	}
	
	public void reset() {
		gameName="";
		dealStage=0;
		potTotal=0;
		players.clear();
	}
	
	public void set(String gameName,int dealStage,int potTotal,ArrayList<PlayerMenuItem> players) {
		this.gameName=gameName;
		this.dealStage=dealStage;
		this.potTotal=potTotal;
		this.players=players;
	}

}
