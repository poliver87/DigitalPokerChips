package com.bidjee.digitalpokerchips.c;

import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.TextLabel;

public class DivisibilityDialog extends DPCSprite {
	//
	public static final int DURATION_DISPLAY = 3000;
	public TextLabel messageLabel;
	int displayTimer;
	
	public DivisibilityDialog() {
		this.opacity=0;
		messageLabel=new TextLabel("Please enter divisible chip values",0,true,0,false);
		messageLabel.setFontFace("segoe_print.ttf");
		displayTimer=0;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		messageLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.8f));
		messageLabel.setTextSizeToMax();
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x,y);
		messageLabel.setPosition(x,y);
	}
	
	@Override
	public void scalePosition(float scaleX,float scaleY) {
		super.scalePosition(scaleX,scaleY);
		messageLabel.scalePosition(scaleX,scaleY);
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		messageLabel.opacity=this.opacity;
		if (opacity==1) {
			displayTimer+=delta*1000;
			if (displayTimer>DURATION_DISPLAY) {
				hide();
			}
		}
	}
	
	public void show() {
		this.fadeIn();
		messageLabel.fadeIn();
		displayTimer=0;
	}
	
	public void hide() {
		this.fadeOut();
		messageLabel.fadeOut();
	}
}
