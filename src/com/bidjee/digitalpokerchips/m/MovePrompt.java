package com.bidjee.digitalpokerchips.m;

public class MovePrompt {
	
	public static final int BLINDS_NONE = 0;
	public static final int BLINDS_SMALL = 1;
	public static final int BLINDS_BIG = 2;
	
	public int stake;
	public int blinds;
	 	
	public MovePrompt() {	}
	
	public MovePrompt(int stake,int blinds) {
		this.stake=stake;
		this.blinds=blinds;
	}
	
}
