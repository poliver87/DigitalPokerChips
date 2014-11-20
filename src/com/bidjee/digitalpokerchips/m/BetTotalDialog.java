package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class BetTotalDialog extends DPCSprite {
	
	public TextLabel titleLabel;
	public TextLabel amountLabel;
	
	Vector2 posOffscreen;
	public Vector2 posOnscreen;
	
	public BetTotalDialog() {
		titleLabel = new TextLabel("Bet Total",0,false,0,false);
		titleLabel.setFontFace("coolvetica_rg.ttf");
		titleLabel.bodyColor=new Color(1f,0.94f,0.77f,1);
		amountLabel = new TextLabel("",0,false,1,false);
		amountLabel.setFontFace("coolvetica_rg.ttf");
		amountLabel.bodyColor=new Color(1f,0.88f,0.55f,1);
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX, radiusY);
		titleLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.4f));
		titleLabel.setTextSizeToMax();
		amountLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.4f));
		amountLabel.setTextSizeToMax("$100000000");
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		titleLabel.setPosition(x,y+radiusY*0.35f);
		amountLabel.setPosition(x,y-radiusY*0.35f);
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
	
	public void setAmount(int amount) {
		String amountStr = "$ "+Integer.toString(amount);
		amountLabel.setText(amountStr);
		amountLabel.loadTexture();
	}
	
	public void show() {
		setPosition(posOffscreen);
		this.setDest(posOnscreen);
	}
	
	public void hide() {
		this.setDest(posOffscreen);
	}
}
