package com.bidjee.digitalpokerchips.m;


public class PlayerDealerChip {
	static final int animationMargin=1;
	
	int radiusX;
	int radiusY;
	
	float x;
	float y;

	float onScreenDestY;
	float offScreenDestY;
	boolean isAtDest;
	
	boolean doShow;
	boolean isOnScreen;
	
	public PlayerDealerChip() {
		isAtDest=false;
		isOnScreen=false;
	}
	
	public void setDimensions(int radiusX_,int radiusY_) {
		radiusX=radiusX_;
		radiusY=radiusY_;
	}
	
	public void setPosition(float x_,float onScreenY_,float offScreenY_) {
		x=x_;
		onScreenDestY=onScreenY_;
		offScreenDestY=offScreenY_;
		if (!doShow) {
			y=offScreenY_;
		} else {
			y=onScreenY_;
		}
	}
	
	public boolean animate(float delta) {
		if  (doShow) {
			if (Math.abs(y-onScreenDestY)<animationMargin) {
				isAtDest=true;
				y=onScreenDestY;
			} else {
				float timeFactor = delta*12;
				y=(float)(y-timeFactor*(y-onScreenDestY));
			}	
		} else {
			if (Math.abs(y-offScreenDestY)<animationMargin) {
				isAtDest=true;
				y=offScreenDestY;
				isOnScreen=false;
			} else {
				float timeFactor = delta*12;
				y=(float)(y+timeFactor*300);
			}
		}
		return isAtDest;
	}
	
	public void show() {
		doShow=true;
		isAtDest=false;
		isOnScreen=true;
	}
	
	public void remove() {
		doShow=false;
		isAtDest=false;
	}

}
