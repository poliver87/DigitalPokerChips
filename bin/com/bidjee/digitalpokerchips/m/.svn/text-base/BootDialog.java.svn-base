package com.bidjee.digitalpokerchips.m;


public class BootDialog extends Dialog {
	
	public Button sitOutButton;
	public Button bootButton;
	
	public BootDialog() {
		sitOutButton=new Button(true,0,"Sit-Out");
		sitOutButton.opacity=0;
		bootButton=new Button(true,0,"Boot");
		bootButton.opacity=0;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		sitOutButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.8f));
		bootButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.8f));
		int textSize=Math.min(sitOutButton.getLabel().getTextSize(),bootButton.getLabel().getTextSize());
		sitOutButton.getLabel().setTextSize(textSize);
		bootButton.getLabel().setTextSize(textSize);
	}
	
	public void setPosition(float x,float y,float rotation) {
		super.setPosition(x,y);
		this.rotation=rotation;
		float xOffset=radiusX*0.5f;
		float yOffset=radiusY*0.1f;
		if (rotation==0) {
			sitOutButton.setPosition(x-xOffset,y+yOffset);
			bootButton.setPosition(x+xOffset,y+yOffset);
		} else if (rotation==-90) {
			sitOutButton.setPosition(x+yOffset,y+xOffset);
			bootButton.setPosition(x+yOffset,y-xOffset);
		} else if (rotation==180) {
			sitOutButton.setPosition(x+xOffset,y-yOffset);
			bootButton.setPosition(x-xOffset,y-yOffset);
		} else if (rotation==90) {
			sitOutButton.setPosition(x-yOffset,y-xOffset);
			bootButton.setPosition(x-yOffset,y+xOffset);
		}
	}
	
	@Override
	public void scalePosition(float scaleX,float scaleY) {
		super.scalePosition(scaleX,scaleY);
		sitOutButton.scalePosition(scaleX,scaleY);
		bootButton.scalePosition(scaleX,scaleY);
	}
	
	@Override
	public void start() {
		sitOutButton.fadeIn();
		sitOutButton.setTouchable(true);
		bootButton.fadeIn();
		bootButton.setTouchable(true);
	}
	
	@Override
	public void stop() {
		super.fadeOut();
		sitOutButton.fadeOut();
		sitOutButton.setTouchable(false);
		bootButton.fadeOut();
		bootButton.setTouchable(false);
	}
	
	public void disappear() {
		sitOutButton.opacity=0;
		bootButton.opacity=0;
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		sitOutButton.animate(delta);
		bootButton.animate(delta);
	}
	
	public void setRotation(float rotation) {
		this.rotation=rotation;
		
	}

}