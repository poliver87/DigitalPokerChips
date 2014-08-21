package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.Gdx;
import com.bidjee.digitalpokerchips.i.IDPCSprite;

public class HelpDialog extends Dialog {
	
	
	public TextLabel titleLabel;
	
	IDPCSprite helpWebView;
	
	public HelpDialog(IDPCSprite helpWebView) {
		titleLabel=new TextLabel("", 0, true, 0, false);
		this.helpWebView=helpWebView;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		
		helpWebView.setDimensions((int)(radiusX*0.8f),(int)(radiusY*0.8f));
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x,y);
		
		helpWebView.setPosition(x,y);
	}
	
	@Override
	public void scalePosition(float scaleX_,float scaleY_) {
		super.scalePosition(scaleX_,scaleY_);
	}
	
	@Override
	public void start() {
		helpWebView.setOpacity(1);
		titleLabel.fadeIn();
	}
	
	@Override
	public void stop() {
		super.fadeOut();
		helpWebView.setOpacity(0);
		titleLabel.fadeOut();
	}
	
	
	public void disappear() {
		helpWebView.setOpacity(0);
		titleLabel.setOpacity(0);
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		titleLabel.animate(delta);
	}

}