package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class HostGameNameDialog extends DPCSprite {
	
	public TextLabel gameLabel;
	public TextLabel stateLabel;
	
	Vector2 posOffscreen;
	public Vector2 posOnscreen;
	
	public HostGameNameDialog() {
		gameLabel = new TextLabel("",0,false,0,false);
		gameLabel.setFontFace("coolvetica_rg.ttf");
		gameLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		stateLabel = new TextLabel("",0,false,0,false);
		stateLabel.setFontFace("coolvetica_rg.ttf");
		stateLabel.bodyColor=ForegroundLayer.brightGoldColor;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX, radiusY);
		gameLabel.setMaxDimensions((int)(radiusX*0.9f),(int)(radiusY*0.9f));
		gameLabel.setTextSizeToMax("Somemadgame");
		stateLabel.setMaxDimensions((int)(radiusX*0.9f),(int)(radiusY*0.9f));
		stateLabel.setTextSizeToMax("Somemaddergame");
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		gameLabel.setPosition(x,(int)(y+radiusY*0.4));
		stateLabel.setPosition(x,(int)(y-radiusY*0.3));
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
	
	public void show() {
		setPosition(posOffscreen);
		this.setDest(posOnscreen);
		gameLabel.opacity=1;
		stateLabel.opacity=1;
	}
	
	public void hide() {
		this.setDest(posOffscreen);
	}
	
	public void setGameName(String gameName) {
		gameLabel.setText(gameName);
		gameLabel.loadTexture();
	}
	
	public void setStateText(String stateText) {
		stateLabel.setText(stateText);
		stateLabel.loadTexture();
	}
}
