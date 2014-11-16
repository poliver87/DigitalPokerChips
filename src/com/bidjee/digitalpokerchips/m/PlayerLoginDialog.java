package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class PlayerLoginDialog extends Dialog {
	
	TextField focussedField;
	
	public Button closeButton;
	public TextLabel guestTitleLabel;
	public TextField nameField;
	public Button guestOKButton;
	public TextLabel orLabel;
	
	public Button facebookButton;
	
	Vector2 posOffscreen;
	Vector2 posOnscreen;
	
	public PlayerLoginDialog() {
		closeButton=new Button(true,1,"");
		guestTitleLabel=new TextLabel("Play As Guest",0.03f,true,0,false);
		guestTitleLabel.setFontFace("coolvetica_rg.ttf");
		nameField=new TextField(0, false, true);
		nameField.label.bodyColor=new Color(0.1f,0.23f,0.32f,1);
		nameField.label.outline=false;
		
		guestOKButton=new Button(true,1,"OKAY");
		guestOKButton.getLabel().setFontFace("coolvetica_rg.ttf");
		
		orLabel=new TextLabel("OR",0.03f,true,0,false);
		orLabel.setFontFace("coolvetica_rg.ttf");
		facebookButton=new Button(true,1,"Login with Facebook");
		facebookButton.getLabel().setFontFace("coolvetica_rg.ttf");
		
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		closeButton.setDimensions((int)(radiusY*0.15f),(int)(radiusY*0.15f));
		guestTitleLabel.setMaxDimensions((int)(radiusX*0.6f),(int)(radiusY*0.11f));
		guestTitleLabel.setTextSizeToMax();
		nameField.setDimensions((int)(radiusX*0.65f),(int)(radiusY*0.13f));
		nameField.setTextSizeToMax("Name");
		guestOKButton.setDimensions((int)(radiusX*0.22f),(int)(radiusY*0.13f),0.8f,0.8f);
		guestOKButton.getLabel().outline=true;
		orLabel.setMaxDimensions((int)(radiusX*0.2f),(int)(radiusY*0.08f));
		orLabel.setTextSizeToMax();
		facebookButton.setDimensions((int)(radiusX*0.86f),(int)(radiusY*0.14f),0.6f,0.8f);
		facebookButton.getLabel().outline=true;
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		closeButton.setPosition(x+radiusX*0.96f,y+radiusY*0.78f);
		guestTitleLabel.setPosition(x-radiusX*0.23f,y+radiusY*0.52f);
		nameField.setPosition(x-radiusX*0.23f,y+radiusY*0.2f);
		guestOKButton.setPosition(x+radiusX*0.66f,y+radiusY*0.2f);
		orLabel.setPosition(x,y-radiusY*0.12f);
		facebookButton.setPosition(x,y-radiusY*0.5f);
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
		guestTitleLabel.fadeIn();
		nameField.fadeIn();
		
		guestOKButton.fadeIn();
		guestOKButton.setTouchable(true);
		orLabel.fadeIn();
		facebookButton.setTouchable(true);
		facebookButton.fadeIn();
	}
	
	@Override
	public void stop() {
		super.fadeOut();
		guestTitleLabel.fadeOut();
		nameField.fadeOut();
		guestOKButton.fadeOut();
		guestOKButton.setTouchable(false);
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
		guestTitleLabel.opacity=0;
		nameField.opacity=0;
		guestOKButton.opacity=0;
		orLabel.opacity=0;
		facebookButton.opacity=0;
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		guestTitleLabel.animate(delta);
		nameField.animate(delta);
		guestOKButton.animate(delta);
		orLabel.animate(delta);
		facebookButton.animate(delta);
	}
	
	public void show() {
		closeButton.opacity=1;
		guestTitleLabel.opacity=1;
		nameField.opacity=1;
		nameField.setTextDefault("Name");
		guestOKButton.opacity=1;
		orLabel.opacity=1;
		facebookButton.opacity=1;
		
		setPosition(posOffscreen);
		this.setDest(posOnscreen);
		
		closeButton.setTouchable(true);
		nameField.setTouchable(true);
		guestOKButton.setTouchable(true);
		facebookButton.setTouchable(true);
	}
	
	public void hide() {
		this.setDest(posOffscreen);
		closeButton.setTouchable(false);
		nameField.setTouchable(false);
		guestOKButton.setTouchable(false);
		facebookButton.setTouchable(false);
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