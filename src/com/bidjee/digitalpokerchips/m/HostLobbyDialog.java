package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class HostLobbyDialog extends Dialog {
	
	public static final int STATE_NONE = 0;
	public static final int STATE_WAITING = 1;
	public static final int STATE_DEALER = 2;
	
	public int state;
	
	public DPCSprite ringSprite;
	public DPCSprite lineSprite;
	public TextLabel waitingLabel;
	public TextLabel dealerLabel;
	public TextLabel nameLabel;
	
	public Button backButton;
	public Button startButton;
	
	Vector2 posStart;
	Vector2 posOnscreen;
	Vector2 posNext;
	
	String name;
	
	public HostLobbyDialog() {
		
		ringSprite=new DPCSprite();
		lineSprite=new DPCSprite();
		
		waitingLabel=new TextLabel("Waiting for Players to Join",0,false,0,false);
		waitingLabel.setFontFace("coolvetica_rg.ttf");
		waitingLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		dealerLabel=new TextLabel("Tap a player to select as dealer",0,false,0,false);
		dealerLabel.setFontFace("coolvetica_rg.ttf");
		dealerLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		
		nameLabel=new TextLabel("",0,false,0,false);
		nameLabel.setFontFace("coolvetica_rg.ttf");
		nameLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		
		backButton=new Button(false,1,"BACK");
		backButton.getLabel().setFontFace("coolvetica_rg.ttf");
		backButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
		startButton=new Button(false,1,"START");
		startButton.getLabel().setFontFace("coolvetica_rg.ttf");
		startButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		ringSprite.setDimensions((int)(radiusX*0.9f),(int)(radiusY*0.68f));
		lineSprite.setDimensions((int)(radiusX*0.7f),(int)(radiusY*0.01f));
		waitingLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.14f));
		waitingLabel.setTextSizeToMax();
		dealerLabel.setTextSize(waitingLabel.getTextSize());
		nameLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.28f));
		nameLabel.setTextSizeToMax("CoolGame");
		backButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.3f),0.6f,0.7f);
		startButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.3f),0.6f,0.7f);
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		ringSprite.setPosition(x,y+radiusY*0.3f);
		lineSprite.setPosition(ringSprite.x,ringSprite.y);
		nameLabel.setPosition(x,y+radiusY*0.6f);
		waitingLabel.setPosition(x,y+radiusY*0.00f);
		dealerLabel.setPosition(waitingLabel.x,waitingLabel.y);
		backButton.setPosition(x-radiusX*0.5f+1,y-radiusY+backButton.radiusY);
		startButton.setPosition(x+radiusX*0.5f-1,y-radiusY+backButton.radiusY);
	}
	
	public void setPositions(float xStart,float yStart,float xOnscreen,float yOnscreen,float xNext,float yNext) {		
		posStart=new Vector2(xStart,yStart);
		posOnscreen=new Vector2(xOnscreen,yOnscreen);
		posNext=new Vector2(xNext,yNext);
	}
	
	public void initialisePosition() {
		setPosition(posStart);
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
		waitingLabel.animate(delta);
		dealerLabel.animate(delta);
		nameLabel.animate(delta);
		backButton.animate(delta);
		startButton.animate(delta);
	}
	
	public void show(String name) {
		this.name=name;
		nameLabel.setText(name);
		nameLabel.loadTexture();
		this.setDest(posOnscreen);
		nameLabel.opacity=1;
		backButton.setTouchable(true);
		startButton.setTouchable(true);
		showWaitingDialog();
	}
	
	private void hide() {
		backButton.setTouchable(false);
		startButton.setTouchable(false);
	}
	
	public void back() {
		this.setDest(posStart);
		hide();
	}
	
	public void next() {
		this.setDest(posNext);
		hide();
	}
	
	public void setStartEnabled(boolean en) {
		startButton.setTouchable(en);
		if (en) {
			startButton.getLabel().opacity = 1;
			waitingLabel.stopFlashing();
			waitingLabel.fadeIn();
		} else {
			startButton.getLabel().opacity = 0.5f;
			waitingLabel.startFlashing();
		}
	}
	
	public void showWaitingDialog() {
		state=STATE_WAITING;
		dealerLabel.fadeOut();
		waitingLabel.fadeIn();
		startButton.getLabel().setText("START");
		startButton.getLabel().loadTexture();
	}
	
	public void showDealerDialog() {
		state=STATE_DEALER;
		waitingLabel.fadeOut();
		dealerLabel.fadeIn();
		startButton.getLabel().setText("RANDOM");
		startButton.getLabel().loadTexture();
	}

}