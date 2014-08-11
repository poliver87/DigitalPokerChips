package com.bidjee.digitalpokerchips.m;

import com.bidjee.digitalpokerchips.c.WorldLayer;


public class LeaveTableDialog extends Dialog {
	
	static final String TITLE_STRING = "Do you want to leave ";
	
	public TextLabel titleLabel;
	public Button okButton;
	public Button cancelButton;
	
	public LeaveTableDialog() {
		titleLabel=new TextLabel("",0,true,0,false);
		okButton=new Button(true,0,"");
		okButton.opacity=0;
		cancelButton=new Button(true,0,"");
		cancelButton.opacity=0;
	}
	
	@Override
	public void setDimensions(int radiusX_,int radiusY_) {
		super.setDimensions(radiusX_,radiusY_);
		String tmp=titleLabel.getText();
		titleLabel.setText(TITLE_STRING+WorldLayer.NAME_MEASURE);
		titleLabel.setMaxDimensions((int)(radiusX_*0.8f),(int)(radiusY_*0.15f));
		titleLabel.setTextSizeToMax();
		titleLabel.setText(tmp);
		okButton.setDimensions((int)(radiusY_*0.15f),(int)(radiusY_*0.15f));
		cancelButton.setDimensions((int)(radiusY_*0.15f),(int)(radiusY_*0.15f));
	}
	
	@Override
	public void setPosition(float x_,float y_) {
		super.setPosition(x_,y_);
		titleLabel.setPosition(x_,y_+radiusY*0.86f);
		okButton.setPosition(x_+radiusX*0.78f,y_-radiusY*0.78f);
		cancelButton.setPosition(x_-radiusX*0.78f,y_-radiusY*0.78f);
	}
	
	@Override
	public void scalePosition(float scaleX_,float scaleY_) {
		super.scalePosition(scaleX_,scaleY_);
		titleLabel.scalePosition(scaleX_,scaleY_);
		okButton.scalePosition(scaleX_,scaleY_);
		cancelButton.scalePosition(scaleX_,scaleY_);
	}
	
	@Override
	public void start() {
		titleLabel.fadeIn();
		okButton.fadeIn();
		okButton.setTouchable(true);
		cancelButton.fadeIn();
		cancelButton.setTouchable(true);
	}
	
	@Override
	public void stop() {
		super.fadeOut();
		titleLabel.fadeOut();
		okButton.fadeOut();
		okButton.setTouchable(false);
		cancelButton.fadeOut();
		cancelButton.setTouchable(false);
	}
	
	public void disappear() {
		titleLabel.opacity=0;
		okButton.opacity=0;
		cancelButton.opacity=0;
	}
	
	@Override
	public void animate(float delta_) {
		super.animate(delta_);
		titleLabel.animate(delta_);
		okButton.animate(delta_);
		cancelButton.animate(delta_);
	}
	
	public void setTableName(String tableName) {
		titleLabel.setText(TITLE_STRING+tableName+"?");
		titleLabel.loadTexture();
	}

}