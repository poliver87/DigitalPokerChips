package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class HostPlayerDialog extends DPCSprite {
	
	public static final int STATE_FOLD_OR_BOOT = 0;
	public static final int STATE_CONFIRM_FOLD = 1;
	public static final int STATE_CONFIRM_BOOT = 2;
	
	public TextLabel nameLabel;
	public TextLabel confirmLabel;
	public Button foldButton;
	public Button cancelButton;
	public Button bootButton;
	public Button okButton;
	
	Vector2 posOffscreen;
	public Vector2 posOnscreen;
	
	public int state;
	
	public HostPlayerDialog() {
		opacity = 0;
		nameLabel = new TextLabel("",0,false,0,false);
		nameLabel.setFontFace("coolvetica_rg.ttf");
		nameLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		confirmLabel = new TextLabel("Confirm?",0,false,0,false);
		confirmLabel.setFontFace("coolvetica_rg.ttf");
		confirmLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		foldButton=new Button(false,1,"Fold");
		foldButton.getLabel().setFontFace("coolvetica_rg.ttf");
		foldButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
		cancelButton=new Button(false,1,"Cancel");
		cancelButton.getLabel().setFontFace("coolvetica_rg.ttf");
		cancelButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
		bootButton=new Button(false,1,"Boot");
		bootButton.getLabel().setFontFace("coolvetica_rg.ttf");
		bootButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
		okButton=new Button(false,1,"Ok");
		okButton.getLabel().setFontFace("coolvetica_rg.ttf");
		okButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX, radiusY);
		nameLabel.setMaxDimensions((int)(radiusX*0.9f),(int)(radiusY*0.55f));
		nameLabel.setTextSizeToMax("Someone");
		confirmLabel.setTextSize(nameLabel.getTextSize());
		foldButton.setDimensions((int)(radiusX*0.50f),(int)(radiusY*0.41f),0.8f,0.95f);
		cancelButton.setDimensions((int)(radiusX*0.50f),(int)(radiusY*0.41f),0.8f,0.95f);
		bootButton.setDimensions((int)(radiusX*0.50f),(int)(radiusY*0.41f),0.8f,0.95f);
		okButton.setDimensions((int)(radiusX*0.50f),(int)(radiusY*0.41f),0.8f,0.95f);
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		int xUp = (int) (1*Math.sin(Math.toRadians(this.rotation)));
		int yUp = (int) Math.cos(Math.toRadians(this.rotation));
		nameLabel.setPosition((int)(x-xUp*radiusY*0.4),(int)(y+yUp*radiusY*0.4));
		confirmLabel.setPosition(nameLabel.x, nameLabel.y);
		foldButton.setPosition(x-yUp*(radiusX*0.5f-1)+xUp*(radiusY-foldButton.radiusY),
				y-xUp*(radiusX*0.5f-1)-yUp*(radiusY-foldButton.radiusY));
		cancelButton.setPosition(foldButton.x,foldButton.y);
		bootButton.setPosition(x+yUp*(radiusX*0.5f-1)+xUp*(radiusY-bootButton.radiusY),
				y+xUp*(radiusX*0.5f-1)-yUp*(radiusY-bootButton.radiusY));
		okButton.setPosition(bootButton.x, bootButton.y);
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		foldButton.animate(delta);
		cancelButton.animate(delta);
		bootButton.animate(delta);
		okButton.animate(delta);
	}
	
	public void setPositions(float xOffscreen,float yOffscreen,float xOnscreen,float yOnscreen) {		
		posOffscreen=new Vector2(xOffscreen,yOffscreen);
		posOnscreen=new Vector2(xOnscreen,yOnscreen);
		setPosition(xOffscreen, yOffscreen);
	}
	
	public void show() {
		opacity = 1;
		setPosition(posOffscreen);
		this.setDest(posOnscreen);
		setState(STATE_FOLD_OR_BOOT);
	}
	
	public void hide() {
		if (posOffscreen!=null) {
			this.setDest(posOffscreen);
		}
		foldButton.setTouchable(false);
		bootButton.setTouchable(false);
	}
	
	public void setPlayerName(String playerName) {
		nameLabel.setText(playerName);
		nameLabel.loadTexture();
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
		setPosition(x, y);
	}
	
	public void setState(int state) {
		this.state = state;
		if (state == STATE_FOLD_OR_BOOT) {
			nameLabel.opacity=1;
			bootButton.opacity=1;
			foldButton.opacity=1;
			confirmLabel.opacity=0;
			okButton.opacity=0;
			cancelButton.opacity=0;
			foldButton.setTouchable(true);
			bootButton.setTouchable(true);
			cancelButton.setTouchable(false);
			okButton.setTouchable(false);
		} else if (state == STATE_CONFIRM_BOOT || state == STATE_CONFIRM_FOLD) {
			nameLabel.opacity=0;
			bootButton.opacity=0;
			foldButton.opacity=0;
			confirmLabel.opacity=1;
			okButton.opacity=1;
			cancelButton.opacity=1;
			foldButton.setTouchable(false);
			bootButton.setTouchable(false);
			cancelButton.setTouchable(true);
			okButton.setTouchable(true);
		}
	}
	
	public void foldTapped() {
		setState(STATE_CONFIRM_FOLD);
	}
	
	public void bootTapped() {
		setState(STATE_CONFIRM_BOOT);
	}
	
	public void cancelTapped() {
		setState(STATE_FOLD_OR_BOOT);
	}
}
