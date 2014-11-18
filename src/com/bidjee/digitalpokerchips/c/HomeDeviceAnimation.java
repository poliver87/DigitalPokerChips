package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.DPCSprite2p5D;

public class HomeDeviceAnimation {

	////////////////////Constants ////////////////////
	public static final int STATE_NONE = 0;
	public static final int STATE_HOLDOFF = 1;
	public static final int STATE_HOST = 2;
	public static final int STATE_P1 = 3;
	public static final int STATE_P2 = 4;
	public static final int STATE_P3 = 5;
	public static final int STATE_CHIP1 = 6;
	public static final int STATE_CHIP2 = 7;
	public static final int STATE_CHIP3 = 8;
	public static final int STATE_CHIPS_FADE_OUT = 9;
	public static final int STATE_CHIPS_FADE_IN = 10;
	
	////////////////////State Variables ////////////////////
	int animationState;
	int holdoffDuration;
	int holdoffTimer;
	boolean loopedOnce;
	public boolean paused;

	//////////////////// World Scale & Layout ////////////////////
	Vector3 posHostStart=new Vector3();
	Vector3 posHostStop=new Vector3();
	Vector3 posP1Start=new Vector3();
	public Vector3 posP1Stop=new Vector3();
	Vector2 posP2Start=new Vector2();
	Vector2 posP2Stop=new Vector2();
	Vector2 posP3Start=new Vector2();
	Vector2 posP3Stop=new Vector2();
	Vector2 offsetChip1Start=new Vector2();
	Vector2 offsetChip1Stop=new Vector2();
	Vector2 offsetChip2Start=new Vector2();
	Vector2 offsetChip2Stop=new Vector2();
	Vector2 offsetChip3Start=new Vector2();
	Vector2 offsetChip3Stop=new Vector2();
	

	public DPCSprite2p5D hostSprite=new DPCSprite2p5D();
	public DPCSprite hostShineSprite=new DPCSprite();
	public DPCSprite2p5D p1Sprite=new DPCSprite2p5D();
	public DPCSprite p1ShineSprite=new DPCSprite();
	public DPCSprite p2Sprite=new DPCSprite();
	public DPCSprite p3Sprite=new DPCSprite();
	public DPCSprite2p5D chip1Sprite=new DPCSprite2p5D();
	public DPCSprite chip2Sprite=new DPCSprite();
	public DPCSprite chip3Sprite=new DPCSprite();
	
	public Rectangle hostClippingRect=new Rectangle();
	public Rectangle p1ClippingRect=new Rectangle();
	public Rectangle p2ClippingRect=new Rectangle();
	public Rectangle p3ClippingRect=new Rectangle();
	
	public HomeDeviceAnimation() {
		loopedOnce=false;
		hostSprite.opacity=0;
		hostShineSprite.setFadeInSpeed(1);
		hostShineSprite.setFadeOutSpeed(1);
		p1Sprite.opacity=0;
		p1ShineSprite.setFadeInSpeed(1);
		p1ShineSprite.setFadeOutSpeed(1);
		p2Sprite.opacity=0;
		p3Sprite.opacity=0;
		hostSprite.setTouchable(false);
		p1Sprite.setTouchable(false);
	}
	
	public void setDimensions(float worldWidth,float worldHeight) {
		hostSprite.setDimensions((int)(worldHeight*0.125f*2.1f),(int)(worldHeight*0.125f));
		hostShineSprite.setDimensions(hostSprite.radiusX,hostSprite.radiusY);
		p1Sprite.setDimensions((int)(worldHeight*0.060f*2.3f),(int)(worldHeight*0.060f));
		p1ShineSprite.setDimensions(p1Sprite.radiusX,p1Sprite.radiusY);
		p2Sprite.setDimensions((int)(worldHeight*0.10f*0.875f),(int)(worldHeight*0.10f));
		p3Sprite.setDimensions((int)(worldHeight*0.10f*0.875f),(int)(worldHeight*0.10f));
		hostSprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,worldHeight*0.006f,9,0,0);
		p1Sprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,worldHeight*0.006f,9,0,0);
		p2Sprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,worldHeight*0.006f,9,0,0);
		p3Sprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,worldHeight*0.006f,9,0,0);
		chip1Sprite.setDimensions((int)(worldHeight*0.028f),(int)(worldHeight*0.028f));
		chip2Sprite.setDimensions((int)(worldHeight*0.028f),(int)(worldHeight*0.028f));
		chip3Sprite.setDimensions((int)(worldHeight*0.028f),(int)(worldHeight*0.028f));
		chip1Sprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,worldHeight*0.006f,6,0,0);
		chip2Sprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,worldHeight*0.006f,6,0,0);
		chip3Sprite.setMoveFunc(DPCSprite.MOVE_EASE_IN,worldHeight*0.006f,6,0,0);
	}
	
	public void setPositions(int worldWidth,int worldHeight,float yHost,float yPlayer1) {
		posHostStart.x=worldWidth*0.5f;
		posHostStart.y=worldHeight*-2f;
		//posHostStart.z=900;
		posHostStop.x=posHostStart.x;
		posHostStop.y=yHost;
		posHostStop.z=0;
		posP1Start.x=posHostStart.x;
		posP1Start.y=worldHeight*-2.0f;
		posP1Start.z=0;
		posP1Stop.x=posHostStart.x;
		posP1Stop.y=yPlayer1;
		posP1Stop.z=0;
		posP2Start.x=0-p2Sprite.radiusX;
		posP2Start.y=posHostStop.y;
		posP2Stop.x=worldWidth*0.28f;
		posP2Stop.y=posHostStop.y;
		posP3Start.x=worldWidth+p3Sprite.radiusX;
		posP3Start.y=posHostStop.y;
		posP3Stop.x=worldWidth*0.72f;
		posP3Stop.y=posHostStop.y;
		
		offsetChip1Start.x=0;
		offsetChip1Start.y=worldHeight*0.001f;
		offsetChip1Stop.x=0;
		offsetChip1Stop.y=0.8f*(posHostStop.y-posP1Stop.y);
		offsetChip2Start.x=worldWidth*0.005f;
		offsetChip2Start.y=0;
		offsetChip2Stop.x=0.6f*(posHostStop.x-posP2Stop.x);
		offsetChip2Stop.y=0;
		offsetChip3Start.x=worldWidth*-0.005f;
		offsetChip3Start.y=0;
		offsetChip3Stop.x=0.6f*(posHostStop.x-posP3Stop.x);
		offsetChip3Stop.y=0;
		
		hostSprite.setPosition(posHostStart);
		hostShineSprite.setPosition(hostSprite.x,hostSprite.y);
		p1Sprite.setPosition(posP1Start);
		p1ShineSprite.setPosition(p1Sprite.x,p1Sprite.y);
		
		p2Sprite.setPosition(posP2Start);
		p3Sprite.setPosition(posP3Start);
		chip1Sprite.setPosition(p1Sprite.x+offsetChip1Start.x,p1Sprite.y+offsetChip1Start.y,p1Sprite.z);
		chip2Sprite.setPosition(p2Sprite.x+offsetChip2Start.x,p2Sprite.y+offsetChip2Start.y);
		chip3Sprite.setPosition(p3Sprite.x+offsetChip3Start.x,p3Sprite.y+offsetChip3Start.y);
		
		hostClippingRect.set(0,0,worldWidth,worldHeight);
		p1ClippingRect.set(0,0,p1Sprite.radiusX*2,p1Sprite.radiusY*1.8f);
		p2ClippingRect.set(0,0,p2Sprite.radiusX*1.38f,p2Sprite.radiusY*2);
		p3ClippingRect.set(0,0,p3Sprite.radiusX*1.4f,p3Sprite.radiusY*2);
	}
	
	public void animate(float delta) {
		if (paused) {
			
		} else if (animationState==STATE_HOLDOFF) {
			holdoffTimer+=delta*1000;
			if (holdoffTimer>holdoffDuration) {
				setAnimationState(STATE_HOST);
			}
		} else if (animationState==STATE_HOST) {
			hostSprite.animate(delta);
			hostShineSprite.setPosition(hostSprite.x,hostSprite.y);
			if (hostSprite.atDest) {
				setAnimationState(STATE_P1);
			}
		} else if (animationState==STATE_P1) {
			p1Sprite.animate(delta);
			p1ShineSprite.setPosition(p1Sprite.x, p1Sprite.y);
			chip1Sprite.setPosition(p1Sprite.x+offsetChip1Start.x,p1Sprite.y+offsetChip1Start.y,p1Sprite.z);
			if (p1Sprite.atDest) {
				setAnimationState(STATE_P2);
			}
		} else if (animationState==STATE_P2) {
			p2Sprite.animate(delta);
			chip2Sprite.setPosition(p2Sprite.x+offsetChip2Start.x,p2Sprite.y+offsetChip2Start.y);
			if (p2Sprite.atDest) {
				setAnimationState(STATE_P3);
			}
		} else if (animationState==STATE_P3) {
			p3Sprite.animate(delta);
			chip3Sprite.setPosition(p3Sprite.x+offsetChip3Start.x,p3Sprite.y+offsetChip3Start.y);
			if (p3Sprite.atDest) {
				setAnimationState(STATE_CHIP1);
			}
		} else if (animationState==STATE_CHIP1) {
			chip1Sprite.animate(delta);
			if (chip1Sprite.atDest) {
				setAnimationState(STATE_CHIP2);
			}
		} else if (animationState==STATE_CHIP2) {
			chip2Sprite.animate(delta);
			if (chip2Sprite.atDest) {
				setAnimationState(STATE_CHIP3);
			}
		} else if (animationState==STATE_CHIP3) {
			chip3Sprite.animate(delta);
			if (chip3Sprite.atDest) {
				setAnimationState(STATE_CHIPS_FADE_OUT);
				loopedOnce=true;
			}
		} else if (animationState==STATE_CHIPS_FADE_OUT) {
			chip1Sprite.animate(delta);
			chip2Sprite.animate(delta);
			chip3Sprite.animate(delta);
			if (chip1Sprite.opacity==0) {
				setAnimationState(STATE_CHIPS_FADE_IN);
			}
		} else if (animationState==STATE_CHIPS_FADE_IN) {
			chip1Sprite.animate(delta);
			chip2Sprite.animate(delta);
			chip3Sprite.animate(delta);
			if (chip1Sprite.opacity==1) {
				setAnimationState(STATE_CHIP1);
			}
		}
		p1ShineSprite.animate(delta);
		hostShineSprite.animate(delta);
	}
	
	public void begin(int holdoffDuration) {
		setAnimationState(STATE_HOLDOFF);
		this.holdoffDuration=holdoffDuration;
		holdoffTimer=0;
		hostSprite.setDest(posHostStop);
	}
	
	public void setAnimationState(int animationState) {
		this.animationState=animationState;
		if (animationState==STATE_HOLDOFF) {
			
		} else if (animationState==STATE_HOST) {

		} else if (animationState==STATE_P1) {
			p1Sprite.setDest(posP1Stop);
		} else if (animationState==STATE_P2) {
			p2Sprite.setDest(posP2Stop);
		} else if (animationState==STATE_P3) {
			p3Sprite.setDest(posP3Stop);
		} else if (animationState==STATE_CHIP1) {
			chip1Sprite.setDest(posP1Stop.x+offsetChip1Stop.x,posP1Stop.y+offsetChip1Stop.y);
			hostClippingRect.set(hostSprite.x-hostSprite.radiusX*0.82f,hostSprite.y-hostSprite.radiusY*0.74f,hostSprite.radiusX*1.62f,hostSprite.radiusY*1.44f);
			p1ClippingRect.setCenter(p1Sprite.x,p1Sprite.y);
			p2ClippingRect.setCenter(p2Sprite.x,p2Sprite.y);
			p3ClippingRect.setCenter(p3Sprite.x,p3Sprite.y);
		} else if (animationState==STATE_CHIP2) {
			chip2Sprite.setDest(posP2Stop.x+offsetChip2Stop.x,posP2Stop.y+offsetChip2Stop.y);
		} else if (animationState==STATE_CHIP3) {
			chip3Sprite.setDest(posP3Stop.x+offsetChip3Stop.x,posP3Stop.y+offsetChip3Stop.y);
		} else if (animationState==STATE_CHIPS_FADE_OUT) {
			chip1Sprite.fadeOut();
			chip2Sprite.fadeOut();
			chip3Sprite.fadeOut();
		} else if (animationState==STATE_CHIPS_FADE_IN) {
			chip1Sprite.setPosition(p1Sprite.x+offsetChip1Start.x,p1Sprite.y+offsetChip1Start.y);
			chip2Sprite.setPosition(p2Sprite.x+offsetChip2Start.x,p2Sprite.y+offsetChip2Start.y);
			chip3Sprite.setPosition(p3Sprite.x+offsetChip3Start.x,p3Sprite.y+offsetChip3Start.y);
			chip1Sprite.fadeIn();
			chip2Sprite.fadeIn();
			chip3Sprite.fadeIn();
		}
	}
	
	public void pause() {
		paused=true;
	}
	
	public void resume() {
		paused=false;
	}
	
	public void resetChips() {
		chip1Sprite.opacity=0;
		chip2Sprite.opacity=0;
		chip3Sprite.opacity=0;
		chip1Sprite.atDest=true;
		chip2Sprite.atDest=true;
		chip3Sprite.atDest=true;
		setAnimationState(STATE_CHIPS_FADE_IN);
	}
	
	public void setDevicesTouchable(boolean touchable) {
		hostSprite.setTouchable(touchable);
		p1Sprite.setTouchable(touchable);
	}
	
	public void fadeOutShine() {
		hostShineSprite.fadeOut();
		p1ShineSprite.fadeOut();
	}
	
	public void fadeInShine() {
		hostShineSprite.fadeIn();
		p1ShineSprite.fadeIn();
	}
	
}
