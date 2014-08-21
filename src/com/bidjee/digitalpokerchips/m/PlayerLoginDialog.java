package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.Gdx;

public class PlayerLoginDialog extends Dialog {
	
	TextField focussedField;
	
	public Button closeButton;
	public TextLabel guestTitleLabel;
	public TextLabel nameLabel;
	public TextField nameField;
	public Button guestOKButton;
	public TextLabel orLabel;
	
	public Button facebookButton;
	
	public PlayerLoginDialog() {
		closeButton=new Button(true,1,"");
		guestTitleLabel=new TextLabel("Play As Guest",0,true,0,false);
		nameLabel=new TextLabel("Name:",0,true,0,false);
		nameField=new TextField("", 0, false, true);
		guestOKButton=new Button(true,1,"OK");
		
		orLabel=new TextLabel("- OR -",0,true,0,false);
		facebookButton=new Button(true,1,"Login with Facebook");
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		
		guestTitleLabel.setMaxDimensions((int)(radiusX*0.3f),(int)(radiusY*0.1f));
		guestTitleLabel.setTextSizeToMax();
		nameLabel.setMaxDimensions((int)(radiusX*0.2f),(int)(radiusY*0.1f));
		nameLabel.setTextSizeToMax();
		nameField.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.2f));
		nameField.label.setTextSize(nameLabel.getTextSize());
		guestOKButton.setDimensions((int)(radiusX*0.2f),(int)(radiusY*0.2f));
		orLabel.setTextSize(guestTitleLabel.getTextSize());
		facebookButton.setDimensions((int)(radiusX*0.4f),(int)(radiusY*0.2f));
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x,y);
		
		guestTitleLabel.setPosition(x,y+radiusY*0.8f);
		nameLabel.setPosition(x-radiusX*0.7f,y+radiusY*0.4f);
		nameField.setPosition(x,nameLabel.y);
		guestOKButton.setPosition(x+radiusX*0.7f,nameLabel.y);
		orLabel.setPosition(x,y);
		facebookButton.setPosition(x,y-radiusY*0.4f);
	}
	
	@Override
	public void scalePosition(float scaleX_,float scaleY_) {
		super.scalePosition(scaleX_,scaleY_);
	}
	
	@Override
	public void start() {
		guestTitleLabel.fadeIn();
		nameLabel.fadeIn();
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
		nameLabel.fadeOut();
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
		nameLabel.opacity=0;
		nameField.opacity=0;
		guestOKButton.opacity=0;
		orLabel.opacity=0;
		facebookButton.opacity=0;
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		guestTitleLabel.animate(delta);
		nameLabel.animate(delta);
		nameField.animate(delta);
		guestOKButton.animate(delta);
		orLabel.animate(delta);
		facebookButton.animate(delta);
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