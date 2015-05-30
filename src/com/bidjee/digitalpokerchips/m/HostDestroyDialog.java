package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class HostDestroyDialog extends Dialog {
	
	public static final String QUIT_MESSAGE = "Are you sure you want to quit ";
	
	public DPCSprite anonSprite;
	public TextLabel text1Label;
	public TextLabel text2Label;
	
	public Button cancelButton;
	public Button quitButton;
	
	Vector2 posOffscreen;
	Vector2 posOnscreen;
	
	public HostDestroyDialog() {
		anonSprite = new DPCSprite();
		text1Label=new TextLabel(QUIT_MESSAGE+"COOOLGAME"+" ?",0,false,0,false);
		text1Label.setFontFace("coolvetica_rg.ttf");
		text1Label.bodyColor=ForegroundLayer.brightestGoldColor;
		
		text2Label=new TextLabel("You can load the game from the end of the last hand",0,false,0,false);
		text2Label.setFontFace("coolvetica_rg.ttf");
		text2Label.bodyColor=ForegroundLayer.brightestGoldColor;
		
		cancelButton=new Button(false,1,"CANCEL");
		cancelButton.getLabel().setFontFace("coolvetica_rg.ttf");
		cancelButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
		quitButton=new Button(false,1,"QUIT");
		quitButton.getLabel().setFontFace("coolvetica_rg.ttf");
		quitButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		anonSprite.setDimensions((int)(radiusY*0.25f),(int)(radiusY*0.25f));
		text1Label.setMaxDimensions((int)(radiusX*0.9f),(int)(radiusY*0.15f));
		text1Label.setTextSizeToMax();
		text2Label.setMaxDimensions((int)(radiusX*0.9f),(int)(radiusY*0.10f));
		text2Label.setTextSizeToMax();
		cancelButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.20f),0.6f,0.7f);
		quitButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.20f),0.6f,0.7f);
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		anonSprite.setPosition(x,y+radiusY*0.99f);
		text1Label.setPosition(x,y+radiusY*0.2f);
		text2Label.setPosition(x,y-radiusY*0.15f);
		cancelButton.setPosition(x-radiusX*0.5f+1,y-radiusY+cancelButton.radiusY);
		quitButton.setPosition(x+radiusX*0.5f-1,y-radiusY+quitButton.radiusY);
	}
	
	public void setPositions(float xOffscreen,float yOffscreen,float xOnscreen,float yOnscreen) {		
		posOffscreen=new Vector2(xOffscreen,yOffscreen);
		posOnscreen=new Vector2(xOnscreen,yOnscreen);
	}
	
	@Override
	public void scalePosition(float scaleX_,float scaleY_) {
		super.scalePosition(scaleX_,scaleY_);
	}
	
	@Override
	public void start() {
		
	}
	
	@Override
	public void stop() {
		
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		anonSprite.animate(delta);
		text1Label.animate(delta);
		text2Label.animate(delta);
		cancelButton.animate(delta);
		quitButton.animate(delta);
	}
	
	public void show() {
		text1Label.opacity=1;
		text2Label.opacity=1;
		setPosition(posOffscreen);
		this.setDest(posOnscreen);
		cancelButton.setTouchable(true);
		quitButton.setTouchable(true);
	}
	
	public void hide() {
		this.setDest(posOffscreen);
		text1Label.setTouchable(false);
		text2Label.setTouchable(false);
		cancelButton.setTouchable(false);
		quitButton.setTouchable(false);
	}
	
	public void setTableName(String tableName) {
		text1Label.setText(QUIT_MESSAGE+tableName+" ?");
		text1Label.loadTexture();
	}

}