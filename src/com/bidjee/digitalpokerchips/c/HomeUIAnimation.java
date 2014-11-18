package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.m.Button;
import com.bidjee.digitalpokerchips.m.DPCSprite;

public class HomeUIAnimation {

	////////////////////Constants ////////////////////
	public static final int STATE_NONE = 0;
	public static final int STATE_HOLDOFF = 1;
	public static final int STATE_LOGO = 2;
	public static final int STATE_HOST_JOIN = 3;
	public static final int STATE_CLEAR = 4;
	
	////////////////////State Variables ////////////////////
	int animationState;
	int holdoffDuration;
	int holdoffTimer;
	boolean running;
	boolean done;
	int buttonsTimer;
	int buttonsHoldoff;
	boolean buttonsDestSet;
	int selectionsTimer;
	int selectionsHoldoff;
	boolean selectionsDestSet;

	//////////////////// World Scale & Layout ////////////////////	
	Vector2 posLogoStart=new Vector2();
	Vector2 posLogoStop=new Vector2();
	
	Vector2 posButtonsStart=new Vector2();
	Vector2 posSettingsStop=new Vector2();
	Vector2 posHelpStop=new Vector2();
	Vector2 posShopStop=new Vector2();
	
	Vector2 posHostButtonStart=new Vector2();
	Vector2 posHostButtonStop=new Vector2();
	Vector2 posJoinButtonStart=new Vector2();
	Vector2 posJoinButtonStop=new Vector2();	

	//////////////////// Sprites ////////////////////	
	public DPCSprite logoSprite=new DPCSprite();
	public DPCSprite settingsSprite=new DPCSprite();
	public DPCSprite shopSprite=new DPCSprite();
	public Button helpButton;
	public Button joinButton;
	public Button hostButton;
	
	public HomeUIAnimation() {
		logoSprite.opacity=1;
		logoSprite.setFrameAnimation(10,50,false,0);
		helpButton=new Button(true,1,"");
		hostButton=new Button(true,1,"Host a Game");
		hostButton.getLabel().outline=true;
		joinButton=new Button(true,1,"Join a Game");
		joinButton.getLabel().outline=true;
	}
	
	public void setDimensions(float screenWidth,float screenHeight) {
		logoSprite.setDimensions((int)(screenHeight*0.2f),(int)(screenHeight*0.2f));
		logoSprite.setMoveFunc(DPCSprite.MOVE_BOUNCE,screenHeight*0.002f,9,screenHeight*6,0.6f);
		settingsSprite.setDimensions((int)(screenHeight*0.07f),(int)(screenHeight*0.07f));
		settingsSprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,screenHeight*0.004f,5,0,0);
		shopSprite.setDimensions((int)(screenHeight*0.07f),(int)(screenHeight*0.07f));
		shopSprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,screenHeight*0.004f,5,0,0);
		helpButton.setDimensions((int)(screenHeight*0.07f),(int)(screenHeight*0.07f));
		helpButton.setMoveFunc(DPCSprite.MOVE_EASE_IN,screenHeight*0.004f,5,0,0);
		hostButton.setDimensions((int)(screenHeight*0.12f*1.8f),(int)(screenHeight*0.12f),0.8f,0.8f);
		hostButton.yLabelOffset=0.22f;
		hostButton.setMoveFunc(DPCSprite.MOVE_EASE_IN,screenHeight*0.004f,5,0,0);
		joinButton.setDimensions((int)(screenHeight*0.12f*1.8f),(int)(screenHeight*0.12f),0.8f,0.8f);
		joinButton.yLabelOffset=-0.22f;
		joinButton.setMoveFunc(DPCSprite.MOVE_EASE_IN,screenHeight*0.004f,5,0,0);
	}
	
	public void setPositions(float screenWidth,float screenHeight) {
		
		posLogoStart.x=screenWidth*0.13f;
		posLogoStart.y=screenHeight+logoSprite.radiusY*1.6f;
		posLogoStop.x=posLogoStart.x;
		posLogoStop.y=screenHeight*0.75f;
		logoSprite.setPosition(posLogoStart);
		
		posButtonsStart.x=posLogoStart.x;
		posButtonsStart.y=screenHeight+helpButton.radiusY*4f;
		posHelpStop.x=posButtonsStart.x;
		posShopStop.x=posButtonsStart.x;
		posSettingsStop.x=posButtonsStart.x;

		float buttonSpacing=screenHeight*0.18f;
		posHelpStop.y=buttonSpacing*0.6f;
		posShopStop.y=buttonSpacing*1.6f;
		posSettingsStop.y=buttonSpacing*2.6f;
		
		helpButton.setPosition(posButtonsStart);
		settingsSprite.setPosition(posButtonsStart);
		shopSprite.setPosition(posButtonsStart);
		
		posHostButtonStart.x=screenWidth*0.5f;
		posHostButtonStart.y=screenHeight+hostButton.radiusY*3f;
		posHostButtonStop.x=posHostButtonStart.x;
		posHostButtonStop.y=screenHeight*0.8f;
		posJoinButtonStart.x=screenWidth*0.5f;
		posJoinButtonStart.y=0-joinButton.radiusY*3f;
		posJoinButtonStop.x=posJoinButtonStart.x;
		posJoinButtonStop.y=screenHeight*0.3f;
		
		hostButton.setPosition(posHostButtonStart);
		joinButton.setPosition(posJoinButtonStart);
	}
	
	public void animate(float delta) {
		if (animationState==STATE_HOLDOFF) {
			holdoffTimer+=delta*1000;
			if (holdoffTimer>holdoffDuration) {
				Gdx.app.log("HomeUIAnimation", "STATE_LOGO");
				animationState=STATE_LOGO;
				logoSprite.setDest(posLogoStop);
				buttonsTimer=0;
				buttonsDestSet=false;
				selectionsTimer=0;
				selectionsDestSet=false;
			}
		} else if (animationState==STATE_LOGO) {
			if (!buttonsDestSet&&buttonsTimer>=buttonsHoldoff) {
				buttonsDestSet=true;
				helpButton.setDest(posHelpStop);
				settingsSprite.setDest(posSettingsStop);
				shopSprite.setDest(posShopStop);
			} else {
				buttonsTimer+=delta*1000;
			}
			if (!selectionsDestSet&&selectionsTimer>=selectionsHoldoff) {
				selectionsDestSet=true;
				hostButton.setDest(posHostButtonStop);
				joinButton.setDest(posJoinButtonStop);
			} else {
				selectionsTimer+=delta*1000;
			}
			if (logoSprite.atDest&&helpButton.atDest&&settingsSprite.atDest&&shopSprite.atDest&&
					hostButton.atDest&&joinButton.atDest) {
				Gdx.app.log("HomeUIAnimation", "STATE_NONE");
				animationState=STATE_NONE;
				done=true;
			}
		} else if (animationState==STATE_CLEAR) {
			
			if (logoSprite.atDest&&helpButton.atDest&&settingsSprite.atDest&&shopSprite.atDest&&
					hostButton.atDest&&joinButton.atDest) {
				Gdx.app.log("HomeUIAnimation", "STATE_NONE");
				animationState=STATE_NONE;
			}
		}
		logoSprite.animate(delta);
		helpButton.animate(delta);
		shopSprite.animate(delta);
		settingsSprite.animate(delta);
		hostButton.animate(delta);
		joinButton.animate(delta);
	}
	
	public void begin(int holdoffDuration) {
		Gdx.app.log("HomeUIAnimation", "begin");
		animationState=STATE_HOLDOFF;
		this.holdoffDuration=holdoffDuration;
		holdoffTimer=0;
		logoSprite.setDest(posLogoStop);
		logoSprite.setMoveFunc(DPCSprite.MOVE_BOUNCE);
		// make holdoff a function of where logo is up to in its animation
		float logoRatio=(logoSprite.y-posLogoStop.y)/(posLogoStart.y-posLogoStop.y);
		logoRatio=Math.abs(logoRatio);
		buttonsHoldoff=(int) (800*logoRatio);
		selectionsHoldoff=(int) (1200*logoRatio);
		running=true;
		done=false;
	}
	
	public void end() {
		Gdx.app.log("HomeUIAnimation", "end");
		animationState=STATE_CLEAR;
		running=false;
		logoSprite.setDest(posLogoStart);
		logoSprite.setMoveFunc(DPCSprite.MOVE_EASE_IN);
		settingsSprite.setDest(posButtonsStart);
		helpButton.setDest(posButtonsStart);
		shopSprite.setDest(posButtonsStart);
		hostButton.setDest(posHostButtonStart);
		joinButton.setDest(posJoinButtonStart);
	}

	public void logoTapped() {
		logoSprite.startFrameAnimation();
	}
	
}
