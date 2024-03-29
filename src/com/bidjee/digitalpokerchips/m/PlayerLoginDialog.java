package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class PlayerLoginDialog extends Dialog {
	
	TextField focussedField;
	
	public DPCSprite anonSprite;
	public TextLabel enterNameLabel;
	public TextField nameField;
	public TextLabel orLabel;
	
	public Button facebookButton;
	
	public Button backButton;
	public Button okButton;
	
	Vector2 posOffscreen;
	Vector2 posOnscreen;
	
	public PlayerLoginDialog() {
		anonSprite = new DPCSprite();
		enterNameLabel=new TextLabel("Enter Your Name",0,false,0,false);
		enterNameLabel.setFontFace("coolvetica_rg.ttf");
		enterNameLabel.bodyColor=ForegroundLayer.brightestGoldColor;

		nameField=new TextField(0, false, false);
		nameField.label.setFontFace("coolvetica_rg.ttf");
		nameField.label.bodyColor=ForegroundLayer.brightestGoldColor;
		nameField.label.outline=false;
		
		orLabel=new TextLabel("OR",0,false,0,false);
		orLabel.setFontFace("coolvetica_rg.ttf");
		orLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		
		facebookButton=new Button(true,1,"Login with Facebook");
		facebookButton.getLabel().setFontFace("coolvetica_rg.ttf");
		
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
		anonSprite.setDimensions((int)(radiusY*0.25f),(int)(radiusY*0.25f));
		enterNameLabel.setMaxDimensions((int)(radiusX*0.6f),(int)(radiusY*0.09f));
		enterNameLabel.setTextSizeToMax();
		nameField.setDimensions((int)(radiusX*0.8f),(int)(radiusY*0.25f));
		nameField.setTextSizeToMax("JohnsonY");
		orLabel.setMaxDimensions((int)(radiusX*0.2f),(int)(radiusY*0.09f));
		orLabel.setTextSizeToMax();
		facebookButton.setDimensions((int)(radiusY*8f*0.15f),(int)(radiusY*0.15f),0.6f,0.8f);
		backButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.167f),0.6f,0.7f);
		okButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.167f),0.6f,0.7f);
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		anonSprite.setPosition(x,y+radiusY*0.99f);
		enterNameLabel.setPosition(x,y+radiusY*0.62f);
		nameField.setPosition(x,y+radiusY*0.22f);
		orLabel.setPosition(x,y-radiusY*0.16f);
		facebookButton.setPosition(x,y-radiusY*0.45f);
		backButton.setPosition(x-radiusX*0.5f+1,y-radiusY+backButton.radiusY);
		okButton.setPosition(x+radiusX*0.5f-1,y-radiusY+backButton.radiusY);
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
		enterNameLabel.fadeIn();
		nameField.fadeIn();
		orLabel.fadeIn();
		facebookButton.setTouchable(true);
		facebookButton.fadeIn();
		backButton.setTouchable(true);
		backButton.fadeIn();
		okButton.setTouchable(true);
		okButton.fadeIn();
	}
	
	@Override
	public void stop() {
		super.fadeOut();
		enterNameLabel.fadeOut();
		nameField.fadeOut();
		orLabel.fadeOut();
		if (focussedField!=null) {
			focussedField.setFocus(false);
			focussedField=null;
		}
		facebookButton.fadeOut();
		facebookButton.setTouchable(false);
		Gdx.input.setOnscreenKeyboardVisible(false);
	}
	
	
	public void disappear() {
		enterNameLabel.opacity=0;
		nameField.opacity=0;
		orLabel.opacity=0;
		facebookButton.opacity=0;
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		anonSprite.animate(delta);
		enterNameLabel.animate(delta);
		nameField.animate(delta);
		orLabel.animate(delta);
		facebookButton.animate(delta);
		backButton.animate(delta);
		okButton.animate(delta);
	}
	
	public void show() {
		enterNameLabel.opacity=1;
		nameField.opacity=1;
		//nameField.setTextDefault(RandomNames.getRandomName());
		nameField.setText("");
		orLabel.opacity=1;
		facebookButton.opacity=1;
		
		setPosition(posOffscreen);
		this.setDest(posOnscreen);
		
		nameField.setTouchable(true);
		facebookButton.setTouchable(true);
		backButton.setTouchable(true);
		okButton.setTouchable(true);
		   
		setFieldFocus(nameField);
		Gdx.input.setOnscreenKeyboardVisible(false);
	}
	
	public void hide() {
		this.setDest(posOffscreen);
		nameField.setTouchable(false);
		facebookButton.setTouchable(false);
		backButton.setTouchable(false);
		okButton.setTouchable(false);
		Gdx.input.setOnscreenKeyboardVisible(false);
		if (focussedField!=null) {
			focussedField.setFocus(false);
			focussedField=null;
		}
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