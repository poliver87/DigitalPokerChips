package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.m.Button;
import com.bidjee.digitalpokerchips.m.DPCSprite;

public class HomeMenu {
	
	static final String STATE_NONE="";
	static final String STATE_CLOSED="STATE_CLOSED";
	static final String STATE_SHOW_LOGO="STATE_SHOW_LOGO";
	static final String STATE_SHOW_MENU="STATE_SHOW_MENU";
	static final String STATE_OPENED="STATE_OPENED";
	static final String STATE_CLOSING="STATE_CLOSING";
	
	public DPCSprite logoDPC=new DPCSprite();
	public Button createButton;
	public Button joinButton;
	public Button loadButton;
	public Button howButton;
	
	Vector2 posLogoOffscreen=new Vector2();
	Vector2 posLogoOnscreen=new Vector2();
	Vector2 posMenuCentreOffscreen=new Vector2();
	Vector2 posMenuCentreOnscreen=new Vector2();
	
	
	private float yButtonPitch;
	
	private String animationState;

	public HomeMenu() {
		animationState=STATE_NONE;
		createButton=new Button(true,0,"Create a Table");
		createButton.getLabel().outline=true;
		createButton.getLabel().setFontFace("stone_sans_semi_bold_italic.ttf");
		joinButton=new Button(true,0,"Join a Table");
		joinButton.getLabel().outline=true;
		joinButton.getLabel().setFontFace("stone_sans_semi_bold_italic.ttf");
		loadButton=new Button(true,0,"Load a Table");
		loadButton.getLabel().outline=true;
		loadButton.getLabel().setFontFace("stone_sans_semi_bold_italic.ttf");
		howButton=new Button(true,0,"How it Works");
		howButton.getLabel().outline=true;
		howButton.getLabel().setFontFace("stone_sans_semi_bold_italic.ttf");
	}
	
	public void setDimensions(int screenWidth,int screenHeight) {
		logoDPC.setDimensions((int)(screenHeight*0.36f),(int)(screenHeight*0.12f));
		int radiusXButton=(int)(screenHeight*0.36f);
		int radiusYButton=(int)(screenHeight*0.065f);
		float xPercentLabel=0.75f;
		float yPercentLabel=0.75f;
		createButton.setDimensions(radiusXButton,radiusYButton,xPercentLabel,yPercentLabel);
		joinButton.setDimensions(radiusXButton,radiusYButton,xPercentLabel,yPercentLabel);
		loadButton.setDimensions(radiusXButton,radiusYButton,xPercentLabel,yPercentLabel);
		howButton.setDimensions(radiusXButton,radiusYButton,xPercentLabel,yPercentLabel);
		int textSize=Math.min(createButton.getLabel().getTextSize(),joinButton.getLabel().getTextSize());
		textSize=Math.min(textSize,loadButton.getLabel().getTextSize());
		textSize=Math.min(textSize,howButton.getLabel().getTextSize());
		createButton.getLabel().setTextSize(textSize);
		joinButton.getLabel().setTextSize(textSize);
		loadButton.getLabel().setTextSize(textSize);
		howButton.getLabel().setTextSize(textSize);
		yButtonPitch=createButton.radiusY*2.2f;
	}
	
	public void setPositions(float screenLeft,float screenTop,float screenRight,float screenBottom) {
		posLogoOffscreen.set(screenLeft+logoDPC.radiusX*1.1f,screenTop+logoDPC.radiusY*3f);
		posLogoOnscreen.set(screenLeft+logoDPC.radiusX*1.1f,screenTop-logoDPC.radiusY*1.2f);
		posMenuCentreOffscreen.set(screenLeft-createButton.radiusX*1.1f,(screenTop+screenBottom)*0.45f);
		posMenuCentreOnscreen.set(screenLeft+createButton.radiusX*0.9f,(screenTop+screenBottom)*0.45f);
	}
	
	public void scalePositions(float scaleX,float scaleY) {
		
	}
	
	public void animate(float delta) {
		if (animationState.equals(STATE_SHOW_LOGO)) {
			if (Math.abs(logoDPC.x-posLogoOnscreen.x)>2||
					Math.abs(logoDPC.y-posLogoOnscreen.y)>2) {
				float deltaX=9*delta*(posLogoOnscreen.x-logoDPC.x);
				float deltaY=9*delta*(posLogoOnscreen.y-logoDPC.y);
				logoDPC.x+=deltaX;
				logoDPC.y+=deltaY;
			} else {
				animationState=STATE_SHOW_MENU;
			}
		} else if (animationState.equals(STATE_SHOW_MENU)) {
			boolean opened=true;
			if (Math.abs(createButton.x-createButton.xDest)>2||
					Math.abs(createButton.y-createButton.yDest)>2) {
				float deltaX=9*delta*(createButton.xDest-createButton.x);
				float deltaY=9*delta*(createButton.yDest-createButton.y);
				createButton.translateX(deltaX);
				createButton.translateY(deltaY);
				opened=false;
			}
			if (Math.abs(loadButton.x-loadButton.xDest)>2||
					Math.abs(loadButton.y-loadButton.yDest)>2) {
				float deltaX=9*delta*(loadButton.xDest-loadButton.x);
				float deltaY=9*delta*(loadButton.yDest-loadButton.y);
				loadButton.translateX(deltaX);
				loadButton.translateY(deltaY);
				opened=false;
			}
			if (Math.abs(joinButton.x-joinButton.xDest)>2||
					Math.abs(joinButton.y-joinButton.yDest)>2) {
				float deltaX=9*delta*(joinButton.xDest-joinButton.x);
				float deltaY=9*delta*(joinButton.yDest-joinButton.y);
				joinButton.translateX(deltaX);
				joinButton.translateY(deltaY);
				opened=false;
			}
			if (Math.abs(howButton.x-howButton.xDest)>2||
					Math.abs(howButton.y-howButton.yDest)>2) {
				float deltaX=9*delta*(howButton.xDest-howButton.x);
				float deltaY=9*delta*(howButton.yDest-howButton.y);
				howButton.translateX(deltaX);
				howButton.translateY(deltaY);
				opened=false;
			}
			if (opened) {
				animationState=STATE_OPENED;
			}
		} else if (animationState.equals(STATE_CLOSING)) {
			boolean closed=true;
			if (Math.abs(logoDPC.x-posLogoOffscreen.x)>2||
					Math.abs(logoDPC.y-posLogoOffscreen.y)>2) {
				float deltaX=9*delta*(posLogoOffscreen.x-logoDPC.x);
				float deltaY=9*delta*(posLogoOffscreen.y-logoDPC.y);
				logoDPC.x+=deltaX;
				logoDPC.y+=deltaY;
				closed=false;
			}
			if (Math.abs(createButton.x-createButton.xDest)>2||
					Math.abs(createButton.y-createButton.yDest)>2) {
				float deltaX=9*delta*(createButton.xDest-createButton.x);
				float deltaY=9*delta*(createButton.yDest-createButton.y);
				createButton.translateX(deltaX);
				createButton.translateY(deltaY);
				closed=false;
			}
			if (Math.abs(loadButton.x-loadButton.xDest)>2||
					Math.abs(loadButton.y-loadButton.yDest)>2) {
				float deltaX=9*delta*(loadButton.xDest-loadButton.x);
				float deltaY=9*delta*(loadButton.yDest-loadButton.y);
				loadButton.translateX(deltaX);
				loadButton.translateY(deltaY);
				closed=false;
			}
			if (Math.abs(joinButton.x-joinButton.xDest)>2||
					Math.abs(joinButton.y-joinButton.yDest)>2) {
				float deltaX=9*delta*(joinButton.xDest-joinButton.x);
				float deltaY=9*delta*(joinButton.yDest-joinButton.y);
				joinButton.translateX(deltaX);
				joinButton.translateY(deltaY);
				closed=false;
			}
			if (Math.abs(howButton.x-howButton.xDest)>2||
					Math.abs(howButton.y-howButton.yDest)>2) {
				float deltaX=9*delta*(howButton.xDest-howButton.x);
				float deltaY=9*delta*(howButton.yDest-howButton.y);
				howButton.translateX(deltaX);
				howButton.translateY(deltaY);
				closed=false;
			}
			if (closed) {
				notifyClosed();
				animationState=STATE_CLOSED;
			}
		}
	}
	
	public void open(boolean includeLoadButton) {
		logoDPC.setPosition(posLogoOffscreen);
		logoDPC.setDest(posLogoOnscreen.x, posLogoOnscreen.y);
		logoDPC.setOpacity(1);
		int numButtons=includeLoadButton?4:3;
		int buttonIndex=0;
		float yTopButton=posMenuCentreOnscreen.y+yButtonPitch*0.5f*(numButtons-1);
		createButton.setPosition(posMenuCentreOffscreen.x,yTopButton-yButtonPitch*buttonIndex);
		createButton.setDest(posMenuCentreOnscreen.x,yTopButton-yButtonPitch*buttonIndex);
		createButton.setOpacity(1);
		createButton.setTouchable(true);
		buttonIndex++;
		if (includeLoadButton) {
			loadButton.setPosition(posMenuCentreOffscreen.x,yTopButton-yButtonPitch*buttonIndex);
			loadButton.setDest(posMenuCentreOnscreen.x,yTopButton-yButtonPitch*buttonIndex);
			loadButton.setOpacity(1);
			loadButton.setTouchable(true);
			buttonIndex++;
		}
		joinButton.setPosition(posMenuCentreOffscreen.x,yTopButton-yButtonPitch*buttonIndex);
		joinButton.setDest(posMenuCentreOnscreen.x,yTopButton-yButtonPitch*buttonIndex);
		joinButton.setOpacity(1);
		joinButton.setTouchable(true);
		buttonIndex++;
		howButton.setPosition(posMenuCentreOffscreen.x,yTopButton-yButtonPitch*buttonIndex);
		howButton.setDest(posMenuCentreOnscreen.x,yTopButton-yButtonPitch*buttonIndex);
		howButton.setOpacity(1);
		howButton.setTouchable(true);
		buttonIndex++;
		animationState=STATE_SHOW_LOGO;
	}
	
	public void close() {
		logoDPC.setDest(posLogoOffscreen.x, posLogoOffscreen.y);
		createButton.setDest(posMenuCentreOffscreen.x,createButton.y);
		createButton.setTouchable(false);
		loadButton.setDest(posMenuCentreOffscreen.x,loadButton.y);
		loadButton.setTouchable(false);
		joinButton.setDest(posMenuCentreOffscreen.x,joinButton.y);
		joinButton.setTouchable(false);
		howButton.setDest(posMenuCentreOffscreen.x,howButton.y);
		howButton.setTouchable(false);
		animationState=STATE_CLOSING;
	}
	
	private void notifyClosed() {
		logoDPC.setOpacity(0);
		createButton.setOpacity(0);
		loadButton.setOpacity(0);
		joinButton.setOpacity(0);
		howButton.setOpacity(0);
	}

}
