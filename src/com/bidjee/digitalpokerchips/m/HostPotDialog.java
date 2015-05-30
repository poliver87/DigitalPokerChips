package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class HostPotDialog extends DPCSprite {
	
	public TextLabel potLabel;
	public TextLabel instructionLabel;
	public Button splitButton;
	public Button cancelButton;
	public Button okButton;
	
	Vector2 posOffscreen;
	public Vector2 posOnscreen;
	
	static final int STATE_SELECT_WINNER = 1;
	static final int STATE_SPLIT = 2;
	private int state;
	private boolean splitEnabled;
	
	public HostPotDialog() {
		opacity = 0;
		potLabel = new TextLabel("Main Pot",0,false,0,false);
		potLabel.setFontFace("coolvetica_rg.ttf");
		potLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		instructionLabel = new TextLabel("Fling to Winner",0,false,0,false);
		instructionLabel.setFontFace("coolvetica_rg.ttf");
		instructionLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		splitButton=new Button(false,1,"Split Pot");
		splitButton.getLabel().setFontFace("coolvetica_rg.ttf");
		splitButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
		cancelButton=new Button(false,1,"Cancel");
		cancelButton.getLabel().setFontFace("coolvetica_rg.ttf");
		cancelButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
		okButton=new Button(false,1,"Ok");
		okButton.getLabel().setFontFace("coolvetica_rg.ttf");
		okButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX, radiusY);
		potLabel.setMaxDimensions((int)(radiusX*0.9f),(int)(radiusY*0.21f));
		potLabel.setTextSizeToMax();
		instructionLabel.setMaxDimensions((int)(radiusX*0.9f),(int)(radiusY*0.5f));
		instructionLabel.setTextSizeToMax();
		splitButton.setDimensions((int)(radiusX*1f),(int)(radiusY*0.28f),0.8f,0.85f);
		cancelButton.setDimensions((int)(radiusX*0.50f),(int)(radiusY*0.28f));
		cancelButton.getLabel().setTextSize(splitButton.getLabel().getTextSize());
		okButton.setDimensions((int)(radiusX*0.50f),(int)(radiusY*0.28f));
		okButton.getLabel().setTextSize(splitButton.getLabel().getTextSize());
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		potLabel.setPosition(x,(int)(y+radiusY*0.7));
		instructionLabel.setPosition(x,y+radiusY*0.1f);
		splitButton.setPosition(x, y-radiusY+splitButton.radiusY);
		cancelButton.setPosition(x-radiusX*0.5f+1,splitButton.y);
		okButton.setPosition(x+radiusX*0.5f-1,splitButton.y);
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		instructionLabel.animate(delta);
	}
	
	public void setPositions(float xOffscreen,float yOffscreen,float xOnscreen,float yOnscreen) {		
		posOffscreen=new Vector2(xOffscreen,yOffscreen);
		posOnscreen=new Vector2(xOnscreen,yOnscreen);
		setPosition(xOffscreen, yOffscreen);
	}
	
	public void show() {
		opacity = 1;
		potLabel.opacity = 1;
		setPosition(posOffscreen);
		this.setDest(posOnscreen);
	}
	
	public void hide() {
		this.setDest(posOffscreen);
	}
	
	public void setTitle(String title) {
		this.potLabel.setText(title);
		this.potLabel.loadTexture();
	}
	
	public void setStateSingleWinner() {
		state = STATE_SELECT_WINNER;
		this.instructionLabel.setText("Fling to Winner");
		this.instructionLabel.loadTexture();
		this.instructionLabel.startFlashing();
		this.splitButton.opacity = 1;
		this.splitButton.setTouchable(true);
		this.cancelButton.opacity = 0;
		this.cancelButton.setTouchable(false);
		this.okButton.opacity = 0;
		this.okButton.setTouchable(false);
	}
	
	public void setStateSplit() {
		state = STATE_SPLIT;
		this.instructionLabel.setText("Tap All Winners");
		this.instructionLabel.loadTexture();
		this.instructionLabel.startFlashing();
		this.splitButton.opacity = 0;
		this.splitButton.setTouchable(false);
		this.cancelButton.opacity = 1;
		this.cancelButton.setTouchable(true);
		if (splitEnabled) {
			this.okButton.opacity = 1;
			this.okButton.setTouchable(true);
		} else {
			this.okButton.opacity = 0.4f;
			this.okButton.setTouchable(false);
		}
	}
	
	public void setSplitEnabled(boolean en) {
		if (state == STATE_SPLIT) {
			if (en) {
				this.okButton.opacity = 1;
				this.okButton.setTouchable(true);
			} else {
				this.okButton.opacity = 0.4f;
				this.okButton.setTouchable(false);
			}
		} else {
			
		}
		
		splitEnabled = en;
	}
	
}
