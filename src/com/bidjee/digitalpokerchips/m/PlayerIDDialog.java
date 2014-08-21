package com.bidjee.digitalpokerchips.m;

import com.bidjee.digitalpokerchips.c.WorldLayer;


public class PlayerIDDialog extends Dialog {
	
	public Button closeButton;
	public TextLabel guestTitleLabel;
	public TextLabel nameLabel;
	public DPCSprite profilePic=new DPCSprite();
	
	private int padding;
	
	public PlayerIDDialog() {
		closeButton=new Button(true,1,"");
		guestTitleLabel=new TextLabel("Play As Guest",0,true,0,false);
		nameLabel=new TextLabel("",0,true,0,false);
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		padding=(int) (radiusY*0.2f);
		guestTitleLabel.setMaxDimensions((int)(radiusX*0.3f),(int)(radiusY*0.1f));
		guestTitleLabel.setTextSizeToMax();
		nameLabel.setMaxDimensions((int)(radiusX*0.6f),(int)(radiusY*0.4f));
		nameLabel.setTextSizeToMax(WorldLayer.NAME_MEASURE);
		profilePic.setDimensions((int)(radiusY*0.8f),(int)(radiusY*0.8f));
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x,y);
		
		guestTitleLabel.setPosition(x,y+radiusY*0.8f);
		nameLabel.setPosition(x+radiusX*0.3f,y+radiusY*0.4f);
		profilePic.setPosition(x-radiusX+profilePic.radiusX+padding,y);
	}
	
	@Override
	public void scalePosition(float scaleX_,float scaleY_) {
		super.scalePosition(scaleX_,scaleY_);
	}
	
	@Override
	public void start() {
		guestTitleLabel.fadeIn();
		nameLabel.fadeIn();
		profilePic.fadeIn();
	}
	
	@Override
	public void stop() {
		super.fadeOut();
		guestTitleLabel.fadeOut();
		nameLabel.fadeOut();
		profilePic.fadeOut();
	}
	
	
	public void disappear() {
		guestTitleLabel.opacity=0;
		nameLabel.opacity=0;
		profilePic.opacity=0;
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		guestTitleLabel.animate(delta);
		nameLabel.animate(delta);
		profilePic.animate(delta);
	}

	public void setPlayerName(String playerName) {
		nameLabel.setText(playerName);
		nameLabel.loadTexture();
	}

}