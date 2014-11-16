package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.m.DPCSprite;

public class HomeForegroundAnimation {

	////////////////////Constants ////////////////////
	public static final int STATE_NONE = 0;
	public static final int STATE_HOLDOFF = 1;
	public static final int STATE_SHOW = 2;
	public static final int STATE_RUNNING = 3;
	public static final int STATE_CLEAR = 4;
	
	////////////////////State Variables ////////////////////
	int animationState;
	int holdoffDuration;
	int holdoffTimer;
	boolean running;
	boolean lightLeftSwung;
	boolean lightRightSwung;

	//////////////////// World Scale & Layout ////////////////////
	Vector2 posTreeStart=new Vector2();
	Vector2 posTreeStop=new Vector2();
	Vector2 posLightLeftStart=new Vector2();
	Vector2 posLightLeftStop=new Vector2();
	Vector2 posLightLeftSwung=new Vector2();
	Vector2 posLightRightStart=new Vector2();
	Vector2 posLightRightStop=new Vector2();
	Vector2 posLightRightSwung=new Vector2();
	float lightAnimationMarginShow;
	float lightAnimationMarginSwing;
	float lightAnimationSpeedShow;
	float lightAnimationSpeedSwing;

	//////////////////// Sprites ////////////////////
	public DPCSprite treeSprite=new DPCSprite();
	public DPCSprite lightLeftSprite=new DPCSprite();
	public DPCSprite lightRightSprite=new DPCSprite();
	
	public HomeForegroundAnimation() {

	}
	
	public void setDimensions(float screenWidth,float screenHeight) {
		treeSprite.setDimensions((int)(screenHeight*0.28f*0.54f),(int)(screenHeight*0.28f));
		treeSprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,screenHeight*0.004f,5,0,0);
		lightLeftSprite.setDimensions((int)(screenHeight*0.32f*1.2f),(int)(screenHeight*0.32f));
		lightLeftSprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,screenHeight*0.004f,5,0,0);
		lightRightSprite.setDimensions((int)(screenHeight*0.32f*1.2f),(int)(screenHeight*0.32f));
		lightRightSprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,screenHeight*0.004f,5,0,0);
		lightAnimationMarginShow=screenHeight*0.004f;
		lightAnimationMarginSwing=screenHeight*0.004f;
		lightAnimationSpeedShow=6;
		lightAnimationSpeedSwing=0.05f;
	}
	
	public void setPositions(float screenWidth,float screenHeight) {
		posTreeStart.x=screenWidth+treeSprite.radiusX*1.1f;
		posTreeStart.y=0+treeSprite.radiusY*0.99f;
		posTreeStop.x=screenWidth-treeSprite.radiusX*0.99f;
		posTreeStop.y=posTreeStart.y;
		
		posLightLeftStart.x=screenWidth*0.35f;
		posLightLeftStart.y=screenHeight+lightLeftSprite.radiusY*1.1f;
		posLightLeftStop.x=posLightLeftStart.x;
		posLightLeftStop.y=screenHeight-lightLeftSprite.radiusY*0.85f;
		posLightLeftSwung.x=posLightLeftStop.x-lightLeftSprite.radiusX*0.16f;
		posLightLeftSwung.y=posLightLeftStop.y;
		posLightRightStart.x=screenWidth*0.65f;
		posLightRightStart.y=screenHeight+lightRightSprite.radiusY*1.1f;
		posLightRightStop.x=posLightRightStart.x;
		posLightRightStop.y=screenHeight-lightRightSprite.radiusY*0.85f;
		posLightRightSwung.x=posLightRightStop.x-lightRightSprite.radiusX*0.12f;
		posLightRightSwung.y=posLightRightStop.y;
		
		treeSprite.setPosition(posTreeStart);
		lightLeftSprite.setPosition(posLightLeftStart);
		lightRightSprite.setPosition(posLightRightStart);
	}
	
	public void animate(float delta) {
		if (animationState==STATE_HOLDOFF) {
			holdoffTimer+=delta*1000;
			if (holdoffTimer>holdoffDuration) {
				setAnimationState(STATE_SHOW);
			}
		} else if (animationState==STATE_SHOW) {
			if (treeSprite.atDest&&lightLeftSprite.atDest&&lightRightSprite.atDest) {
				setAnimationState(STATE_RUNNING);
			}	
		} else if (animationState==STATE_RUNNING) {
			if (lightLeftSprite.atDest) {
				if (!lightLeftSwung) {
					lightLeftSprite.setDest(posLightLeftStop);
					lightLeftSwung=true;
				} else {
					lightLeftSprite.setDest(posLightLeftSwung);
					lightLeftSwung=false;
				}
			}
			if (lightRightSprite.atDest) {
				if (!lightRightSwung) {
					lightRightSprite.setDest(posLightRightStop);
					lightRightSwung=true;
				} else {
					lightRightSprite.setDest(posLightRightSwung);
					lightRightSwung=false;
				}
			}
		} else if (animationState==STATE_CLEAR) {
			if (treeSprite.atDest&&lightLeftSprite.atDest&&lightRightSprite.atDest) {
				setAnimationState(STATE_NONE);
			}
		}
		treeSprite.animate(delta);
		lightLeftSprite.animate(delta);
		lightRightSprite.animate(delta);
	}
	
	public void setAnimationState(int animationState) {
		this.animationState=animationState;
		if (animationState==STATE_HOLDOFF) {
			
		} else if (animationState==STATE_SHOW) {
			treeSprite.setDest(posTreeStop);
			lightLeftSprite.setDest(posLightLeftStop);
			lightLeftSprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,lightAnimationMarginShow,lightAnimationSpeedShow,0,0);
			lightRightSprite.setDest(posLightRightStop);
			lightRightSprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,lightAnimationMarginShow,lightAnimationSpeedShow,0,0);
		} else if (animationState==STATE_RUNNING) {
			lightLeftSprite.setDest(posLightLeftSwung);
			lightLeftSprite.setMoveFunc(DPCSprite.MOVE_SINE,lightAnimationMarginSwing,lightAnimationSpeedSwing*1.2f,0,0);
			lightRightSprite.setDest(posLightRightSwung);
			lightRightSprite.setMoveFunc(DPCSprite.MOVE_SINE,lightAnimationMarginSwing,lightAnimationSpeedSwing,0,0);
		} else if (animationState==STATE_CLEAR) {
			treeSprite.setDest(posTreeStart);
			lightLeftSprite.setDest(posLightLeftStart);
			lightLeftSprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,lightAnimationMarginShow,lightAnimationSpeedShow,0,0);
			lightRightSprite.setDest(posLightRightStart);
			lightRightSprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,lightAnimationMarginShow,lightAnimationSpeedShow,0,0);
		}
	}
	
	public void begin(int holdoffDuration) {
		Gdx.app.log("HomeForegroundAnimation", "begin");
		this.holdoffDuration=holdoffDuration;
		holdoffTimer=0;
		running=true;
		setAnimationState(STATE_HOLDOFF);
	}
	
	public void end() {
		Gdx.app.log("HomeForegroundAnimation", "end");
		running=false;
		setAnimationState(STATE_CLEAR);
	}
	
}
