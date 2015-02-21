package com.bidjee.util;

import java.util.ArrayList;

public class ScrollPane {
	
	public float x,y;
	public int radiusX,radiusY;
	
	boolean touchable;
	
	public ArrayList<DPCView> views;
	
	public ScrollPane() {
		views=new ArrayList<DPCView>();
	}
	
	public void setDimensions(int radiusX,int radiusY) {
		this.radiusX=radiusX;
		this.radiusY=radiusY;
	}
	
	public void setPosition(float x,float y) {
		this.x=x;
		this.y=y;
		
		float yPointer = y + radiusY;
		for (int i=0;i<views.size();i++) {
			DPCView thisView = views.get(i);
			thisView.setYTop(yPointer);
			yPointer-=thisView.getRadiusY()*2;
		}
	}
	
	public void animate(float delta) {
		
	}
	
	public void setTouchable(boolean touchable) {
		this.touchable=touchable;
	}
	
	public void addView(DPCView newView) {
		views.add(newView);
	}

}
