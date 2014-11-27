package com.bidjee.digitalpokerchips.c;

import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.TextLabel;

public class PlayerMenuPanel extends DPCSprite implements Comparable<PlayerMenuPanel> {
	
	public int chipTotal;
	
	public TextLabel nameLabel;
	public TextLabel amountsLabel;
	
	public static int panelRadiusX;
	public static int panelRadiusY;
	public static int nameTextSize;
	public static int amountTextSize;
	
	public boolean markedForDeletion;
	
	public PlayerMenuPanel(String name) {
		nameLabel = new TextLabel(name,0,false,1,false);
		nameLabel.setFontFace("coolvetica_rg.ttf");
		nameLabel.bodyColor=ForegroundLayer.brightGoldColor;
		amountsLabel = new TextLabel("",0,false,1,false);	
		amountsLabel.setFontFace("coolvetica_rg.ttf");
		amountsLabel.bodyColor=ForegroundLayer.brightGoldColor;
	}
	
	public void setAmounts(int betAmount,boolean folded,int totalAmount) {
		this.chipTotal = totalAmount;
		String betStr=Integer.toString(betAmount);
		if (folded) {
			betStr="Fold";
			opacity = 0.3f;
		}
		String totalStr=Integer.toString(totalAmount);
		if (totalAmount==0) {
			totalStr="All-In";
		}
		String amountsStr=betStr+" / "+totalStr;
		amountsLabel.setText(amountsStr);
		nameLabel.loadTexture();
		amountsLabel.loadTexture();
	}
	
	public void setDimensionsStatic() {
		this.radiusX=panelRadiusX;
		this.radiusY=panelRadiusY;
		amountsLabel.setTextSize(amountTextSize);
		nameLabel.setTextSize(nameTextSize);
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		nameLabel.setPosition(x, y+radiusY*0.4f);
		amountsLabel.setPosition(x, y-radiusY*0.38f);
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		
	}
	
	public static void setStaticDimensions(int radiusX,int radiusY) {
		panelRadiusX=radiusX;
		panelRadiusY=radiusY;
		PlayerMenuPanel dummyPanel = new PlayerMenuPanel("Someshithead");
		dummyPanel.setAmounts(100000, false, 1000000);
		dummyPanel.nameLabel.setMaxDimensions((int)(radiusX*0.8f), (int)(radiusY*0.5f));
		dummyPanel.nameLabel.setTextSizeToMax();
		nameTextSize = dummyPanel.nameLabel.getTextSize();
		dummyPanel.amountsLabel.setMaxDimensions((int)(radiusX*0.9f), (int)(radiusY*0.5f));
		dummyPanel.amountsLabel.setTextSizeToMax();
		amountTextSize = dummyPanel.amountsLabel.getTextSize();
	}

	@Override
	public int compareTo(PlayerMenuPanel another) {
		return another.chipTotal-this.chipTotal;
	}
	
	
}
