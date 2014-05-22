package com.bidjee.digitalpokerchips.m;

public class MovePrompt {
	
	public int stake;
	public boolean foldEnabled;
	public String message;
	public String messageStateChange;
	
	public MovePrompt() {	}
	
	public MovePrompt(int stake,boolean foldEnabled,String message,String messageStateChange) {
		this.stake=stake;
		this.foldEnabled=foldEnabled;
		this.message=message;
		this.messageStateChange=messageStateChange;
	}
	
}
