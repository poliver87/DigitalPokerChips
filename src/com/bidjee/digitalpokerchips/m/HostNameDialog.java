package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class HostNameDialog extends Dialog {
	
	TextField focussedField;
	
	public DPCSprite tableSprite;
	public TextLabel enterNameLabel;
	public TextField nameField;
	public TextLabel infoLabel;
	
	public Button backButton;
	public Button okButton;
	
	Vector2 posStart;
	Vector2 posOnscreen;
	Vector2 posNext;
	
	public HostNameDialog() {
		tableSprite = new DPCSprite();
		enterNameLabel=new TextLabel("Enter Your Game's Name",0,false,0,false);
		enterNameLabel.setFontFace("coolvetica_rg.ttf");
		enterNameLabel.bodyColor=ForegroundLayer.brightestGoldColor;

		nameField=new TextField(0, false, false);
		nameField.label.setFontFace("coolvetica_rg.ttf");
		nameField.label.bodyColor=ForegroundLayer.brightestGoldColor;
		nameField.label.outline=false;
		
		infoLabel=new TextLabel("Players will see this when joining via WiFi",0,false,0,false);
		infoLabel.setFontFace("coolvetica_rg.ttf");
		infoLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		
		backButton=new Button(false,1,"BACK");
		backButton.getLabel().setFontFace("coolvetica_rg.ttf");
		backButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
		okButton=new Button(false,1,"OKAY");
		okButton.getLabel().setFontFace("coolvetica_rg.ttf");
		okButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		tableSprite.setDimensions((int)(radiusY*0.25f),(int)(radiusY*0.25f));
		enterNameLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.12f));
		enterNameLabel.setTextSizeToMax();
		nameField.setDimensions((int)(radiusX*0.8f),(int)(radiusY*0.25f));
		nameField.setTextSizeToMax("JohnsonY");
		infoLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.08f));
		infoLabel.setTextSizeToMax();
		backButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.2f),0.6f,0.7f);
		okButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.2f),0.6f,0.7f);
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		tableSprite.setPosition(x,y+radiusY*0.99f);
		enterNameLabel.setPosition(x,y+radiusY*0.54f);
		nameField.setPosition(x,y+radiusY*0.1f);
		infoLabel.setPosition(x,y-radiusY*0.3f);
		backButton.setPosition(x-radiusX*0.5f+1,y-radiusY+backButton.radiusY);
		okButton.setPosition(x+radiusX*0.5f-1,y-radiusY+backButton.radiusY);
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
		tableSprite.animate(delta);
		enterNameLabel.animate(delta);
		nameField.animate(delta);
		infoLabel.animate(delta);
		backButton.animate(delta);
		okButton.animate(delta);
	}
	
	public void show() {
		enterNameLabel.opacity=1;
		nameField.opacity=1;
		nameField.setText("");
		infoLabel.opacity=1;
		
		this.setDest(posOnscreen);
		
		nameField.setTouchable(true);
		backButton.setTouchable(true);
		okButton.setTouchable(true);
		   
		setFieldFocus(nameField);
		Gdx.input.setOnscreenKeyboardVisible(false);
	}
	
	private void hide() {
		nameField.setTouchable(false);
		backButton.setTouchable(false);
		okButton.setTouchable(false);
		Gdx.input.setOnscreenKeyboardVisible(false);
		if (focussedField!=null) {
			focussedField.setFocus(false);
			focussedField=null;
		}
	}
	
	public void back() {
		this.setDest(posStart);
		hide();
	}
	
	public void next() {
		this.setDest(posNext);
		hide();
	}
	
	public void setFieldFocus(TextField focussedField) {
		this.focussedField=focussedField;
		if (focussedField!=null) {
			focussedField.setFocus(false);
		}
		focussedField.setFocus(true);
		Gdx.input.setOnscreenKeyboardVisible(true);
	}
	
	public void backspace() {
		nameField.backspace();
	}
	
	public void enterTyped() {
		Gdx.input.setOnscreenKeyboardVisible(false);
	}
	
	public void keyTyped(String chars) {
		nameField.append(chars);
	}

}