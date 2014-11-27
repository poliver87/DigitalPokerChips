package com.bidjee.digitalpokerchips.c;

import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.TextLabel;

public class GameMenuPanel extends DPCSprite {
	
	public TextLabel dealLabel;
	public TextLabel potLabel;
	
	public GameMenuPanel() {
		dealLabel=new TextLabel("",0,false,1,false);
		dealLabel.setFontFace("coolvetica_rg.ttf");
		dealLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		potLabel=new TextLabel("",0,false,1,false);
		potLabel.setFontFace("coolvetica_rg.ttf");
		potLabel.bodyColor=ForegroundLayer.brightestGoldColor;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX, radiusY);
		dealLabel.setMaxDimensions((int)(radiusX*0.7f), (int)(radiusY*0.35f));
		dealLabel.setTextSizeToMax("Pre-Flop");
		potLabel.setMaxDimensions((int)(radiusX*0.7f), (int)(radiusY*0.35f));
		potLabel.setTextSizeToMax("$1000000");
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		dealLabel.setPosition(x, y+radiusY*0.4f);
		potLabel.setPosition(x, y-radiusY*0.38f);
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
	}
	
	public void set(String dealStr,int potAmount) {
		dealLabel.setText(dealStr);
		dealLabel.loadTexture();
		potLabel.setText("$"+Integer.toString(potAmount));
		potLabel.loadTexture();
	}
	
}
