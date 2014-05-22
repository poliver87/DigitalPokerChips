package com.bidjee.digitalpokerchips.m;


public class PlayerEntry implements Comparable<PlayerEntry> {
		
	public static int playerEntryTextSize;
	
	public TextLabel name;
	public TextLabel amount;
	
	public PlayerEntry(String name,int amount) {
		this.name=new TextLabel(name,0,false,0,false);
		this.name.setTextSize(playerEntryTextSize);
		this.name.bodyColor=TextLabel.greyColor;
		setName(name);
		this.amount=new TextLabel(Integer.toString(amount),0,false,0,false);
		this.amount.setTextSize(playerEntryTextSize);
		this.amount.bodyColor=TextLabel.greyColor;
		setAmount(amount);
	}
	
	public void setName(String name) {
		this.name.setText(name);
		this.name.loadTexture();
	}
	
	public void setAmount(int amount) {
		this.amount.setText(Integer.toString(amount));
		this.amount.loadTexture();
	}

    @Override
    public int compareTo(PlayerEntry otherEntry) {
        return (Integer.parseInt(otherEntry.amount.getText())-Integer.parseInt(this.amount.getText()));
    }
    
    public void dispose() {
    	name.dispose();
    	amount.dispose();
    }
}