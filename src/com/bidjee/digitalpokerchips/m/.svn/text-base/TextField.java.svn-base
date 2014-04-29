package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.Gdx;
import com.bidjee.digitalpokerchips.c.DPCGame;

public class TextField extends DPCSprite {
	
	public TextLabel label;
	int maxLength;
	boolean onlyNumerals;
	String defaultText;
	
	public DPCSprite cursor;
	
	private boolean clearOnAction;

	public TextField(String defaultText_,int maxLength_,boolean onlyNumerals_,boolean outline_) {
		super();
		label=new TextLabel(defaultText_,0,false,1,false);
		maxLength=maxLength_;
		onlyNumerals=onlyNumerals_;
		cursor=new DPCSprite();
		cursor.opacity=0;
		defaultText=defaultText_;
		label.outline=outline_;
		if (!defaultText_.equals("")) {
			clearOnAction=true;
		}
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		label.setMaxDimensions((int)(radiusX*0.9f),(int)(radiusY*1.5f));
		cursor.setDimensions((int)(radiusY*0.6f*0.05f),(int)(radiusY*0.6f));
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x,y);
		label.setPosition(x,y);
	}
	
	public void scalePosition(float scaleX,float scaleY) {
		super.scalePosition(scaleX,scaleY);
		label.scalePosition(scaleX,scaleY);
		cursor.scalePosition(scaleX,scaleY);
	}
	
	public void translateY(float deltaY_) {
		y+=deltaY_;
		label.y+=deltaY_;
		cursor.y+=deltaY_;
	}
	
	@Override
	public void setIsTouched(boolean t_) {
		super.setIsTouched(t_);
		if (t_) {
			Gdx.input.setOnscreenKeyboardVisible(true);
		}
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		label.animate(delta);
		cursor.animate(delta);
	}
	
	public void setFocus(boolean focus) {
		if (focus) {
			cursor.setPosition(label.x+label.radiusX+cursor.radiusX,y+radiusY*0.0f);
			cursor.startFlashing();
		} else {
			cursor.stopFlashing();
			cursor.opacity=0;
		}
	}
	
	public void setTextDefault(String text_) {
		setText(text_);
		clearOnAction=true;
	}
	
	public void setText(String text_) {
		if (!onlyNumerals||(onlyNumerals&&isNumerals(text_))) {
			if (text_.length()<=maxLength||maxLength==0) {
				if (DPCGame.textFactory.isWithinBounds(text_,label)) {
					label.setText(text_);
					label.loadTexture();
				}
			}
		}
		cursor.x=label.x+label.radiusX+cursor.radiusX;
	}
	

	public void append(String text_) {
		if (clearOnAction) {
			clearOnAction=false;
			label.setText("");
		}
		String newText=label.getText()+text_;
		setText(newText);
	}
	
	public void backspace() {
		if (clearOnAction) {
			setText("");
		} else {
			int len=label.getText().length();
			if (len>0) {
				setText(label.getText().substring(0,len-1));
			}
		}
	}
	
	public static boolean isNumerals(String text_) {
		boolean result=true;
		for (int i=0;i<text_.length();i++) {
			if (!Character.isDigit(text_.charAt(i))) {
				result=false;
			}
		}
		return result;
	}

	public void setTextSizeToMax() {
		label.setTextSizeToMax();
	}

	public String getText() {
		return label.getText();
	}

	
}
