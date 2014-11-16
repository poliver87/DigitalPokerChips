package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.graphics.Color;
import com.bidjee.digitalpokerchips.c.DPCGame;

public class TextLabel extends DPCSprite {
	
	public static final Color whiteColor=new Color(1,1,1,1);
	public static final Color greyColor=new Color(0.7f,0.7f,0.7f,1);
	public static final Color blackColor=new Color(0,0,0,1);
	
	private String text;
	
	public int maxRadiusX;
	public int maxRadiusY;
	
	private int textSize;
	public float ascent;
	public float strokeWidth;
	public boolean outline;
	public boolean shadow;
	public boolean bold;
	public boolean renderVertical;
	
	public String fontFace;
	
	public Color bodyColor;
	public Color outlineColor;
	
	public TextLabel(String text_,float strokeWidth_,boolean outline_,float opacity_,boolean shadow_) {
		super();
		text=text_;
		strokeWidth=strokeWidth_;
		outline=outline_;
		shadow=shadow_;
		opacity=opacity_;
		textSize=5;
		fontFace=null;
		bodyColor=whiteColor;
		outlineColor=blackColor;
	}
	
	public void setMaxDimensions(int maxRadiusX_,int maxRadiusY_) {
		maxRadiusX=maxRadiusX_;
		maxRadiusY=maxRadiusY_;
	}
	
	public void setTextSize(int textSize) {this.textSize=textSize;}
	public void setTextSizeToMax() {textSize=DPCGame.textFactory.getMaxTextSize(this);}
	public int getTextSize() {return textSize;}
	public void setText(String text_) {text=text_;}
	public String getText() {return text;}
	
	public void setTextSizeToMax(String maxString_) {
		String tmp_=text;
		setText(maxString_);
		setTextSizeToMax();
		setText(tmp_);
	}

	public void setFontFace(String fontFace_) {
		fontFace=fontFace_;
	}
	
	public void loadTexture() {
		DPCGame.textFactory.createTextureForLabel(this,bodyColor,outlineColor,true);
	}
	
	public void loadTexture(Color bodyColor,Color outlineColor) {
		this.bodyColor=bodyColor;
		this.outlineColor=outlineColor;
		loadTexture();
	}

	public void dispose() {
		DPCGame.textFactory.dispose(this);
	}

}
