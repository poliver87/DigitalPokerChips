package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class BetTotalDialog extends DPCSprite {
	
	private static final String MORE_TO_CALL_TEXT = "More to Call";
	private static final String RAISE_TEXT = "Raise";
	private static final String CALL_TEXT = "Fling to Call";
	
	public TextLabel titleLabel;
	public TextLabel amountLabel;
	public TextLabel callLabel;
	
	Vector2 posOffscreen;
	public Vector2 posOnscreen;
	
	public BetTotalDialog() {
		titleLabel = new TextLabel(CALL_TEXT,0,false,0,false);
		titleLabel.setFontFace("coolvetica_rg.ttf");
		titleLabel.bodyColor=ForegroundLayer.brightGoldColor;
		callLabel = new TextLabel(CALL_TEXT,0,false,0,false);
		callLabel.setFontFace("coolvetica_rg.ttf");
		callLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		amountLabel = new TextLabel(CALL_TEXT,0,false,1,false);
		amountLabel.setFontFace("coolvetica_rg.ttf");
		amountLabel.bodyColor=ForegroundLayer.brightestGoldColor;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX, radiusY);
		titleLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.4f));
		titleLabel.setTextSizeToMax();
		callLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.4f));
		callLabel.setTextSize(titleLabel.getTextSize());
		amountLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.4f));
		amountLabel.setTextSizeToMax("$100000000");
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		titleLabel.setPosition(x,y-radiusY*0.35f);
		callLabel.setPosition(x,y-radiusY*0.00f);
		amountLabel.setPosition(x,y+radiusY*0.35f);
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
	}
	
	public void setPositions(float xOffscreen,float yOffscreen,float xOnscreen,float yOnscreen) {		
		posOffscreen=new Vector2(xOffscreen,yOffscreen);
		posOnscreen=new Vector2(xOnscreen,yOnscreen);
		setPosition(xOffscreen, yOffscreen);
	}
	
	public void setAmountMoreToCall(int amount) {
		
		
		if (amount<0) {
			titleLabel.setText(RAISE_TEXT);
			amount *= -1;
			titleLabel.opacity = 1;
			amountLabel.opacity = 1;
			callLabel.opacity = 0;
		} else if (amount==0) {
			titleLabel.opacity = 0;
			amountLabel.opacity = 0;
			callLabel.opacity = 1;
		} else {
			titleLabel.setText(MORE_TO_CALL_TEXT);
			titleLabel.opacity = 1;
			amountLabel.opacity = 1;
			callLabel.opacity = 0;
		}
		String amountStr = "$ "+Integer.toString(amount);
		amountLabel.setText(amountStr);
		amountLabel.loadTexture();
		titleLabel.loadTexture();
	}
	
	public void show() {
		setPosition(posOffscreen);
		this.setDest(posOnscreen);
	}
	
	public void hide() {
		this.setDest(posOffscreen);
	}
}
