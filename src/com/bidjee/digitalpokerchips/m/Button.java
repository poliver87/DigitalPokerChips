package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;


public class Button extends DPCSprite {
	
	int radiusXOriginal;
	int radiusYOriginal;
	boolean growWhenTouched;
	
	private TextLabel label;
	
	public float xLabelOffset;
	public float yLabelOffset;
	
	public Button(boolean growWhenTouched_,float opacity_,String labelText) {
		super();
		growWhenTouched=growWhenTouched_;
		opacity=opacity_;
		label=new TextLabel(labelText,0,false,0,false);
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		setDimensions(radiusX,radiusY,0.9f,0.9f);
	}
	
	public void setDimensions(int radiusX,int radiusY,float xPercentLabel,float yPercentLabel) {
		super.setDimensions(radiusX, radiusY);
		radiusXOriginal=radiusX;
		radiusYOriginal=radiusY;
		setLabelDims(xPercentLabel,yPercentLabel);
	}
	
	@Override
	public void setDimensions(Vector2 dims) {
		setDimensions((int)dims.x,(int)dims.y);
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x,y);
		label.setPosition(x+xLabelOffset*radiusX,y+yLabelOffset*radiusY);
	}
	
	@Override
	public void setPosition(Vector2 pos) {
		setPosition(pos.x,pos.y);
	}
	
	@Override
	public void scalePosition(float scaleX_,float scaleY_) {
		super.scalePosition(scaleX_, scaleY_);
		label.scalePosition(scaleX_, scaleY_);
	}
	
	@Override
	public void fadeIn() {
		super.fadeIn();
		label.fadeIn();
	}
	
	@Override
	public void fadeOut() {
		super.fadeOut();
		label.fadeOut();
	}
	
	@Override
	public void startFlashing() {
		super.startFlashing();
		label.startFlashing();
	}
	
	@Override
	public void stopFlashing() {
		super.stopFlashing();
		label.stopFlashing();
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		label.animate(delta);
	}
	
	@Override
	public void setIsTouched(boolean t_) {
		super.setIsTouched(t_);
		if (growWhenTouched) {
			if (t_) {
				radiusX=(int)(radiusXOriginal*1.1f);
				radiusY=(int)(radiusYOriginal*1.1f);
			} else {
				radiusX=radiusXOriginal;
				radiusY=radiusYOriginal;
			}
		}
	}
	
	public void setLabelDims(float xPercentLabel, float yPercentLabel) {
		label.setMaxDimensions((int)(radiusX*xPercentLabel),(int)(radiusY*yPercentLabel));
		label.setTextSizeToMax();
	}
	
	public void setLabelText(String text) {
		label.setText(text);
	}
	
	public TextLabel getLabel() {
		return label;
	}
	
	public void translateX(float deltaX) {
		this.x+=deltaX;
		label.x+=deltaX;
	}
	
	public void translateY(float deltaY) {
		this.y+=deltaY;
		label.y+=deltaY;
	}

}
