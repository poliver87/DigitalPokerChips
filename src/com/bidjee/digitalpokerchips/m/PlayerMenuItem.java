package com.bidjee.digitalpokerchips.m;


public class PlayerMenuItem {
	
	public String name;
	public int betTotal;
	public int chipTotal;
	public boolean folded;
	
	public PlayerMenuItem(String name,int betTotal,int chipTotal,boolean folded) {
		this.name=name;
		this.betTotal=betTotal;
		this.chipTotal=chipTotal;
		this.folded=folded;
	}

}
