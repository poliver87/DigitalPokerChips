package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.Player;
import com.bidjee.digitalpokerchips.m.Seat;
import com.bidjee.digitalpokerchips.m.TextLabel;

public class TutorialArrangement {

	//private ForegroundLayer mFL;
	
	static final int STATE_NONE = 0;
	static final int STATE_FADE_IN = 1;
	static final int STATE_ANIMATE_1 = 2;
	static final int STATE_WAITING_FOR_TOUCH = 3;
	static final int STATE_FADE_OUT = 4;
	
	public int state;
	
	public TextLabel instrLabel;
	
	public DPCSprite highlight=new DPCSprite();
	
	public Vector2 dimsHighlight=new Vector2();
	
	public TutorialArrangement() {
		state=STATE_NONE;
		instrLabel=new TextLabel("Drag your name to best spot",0,true,0,false);
		instrLabel.setFontFace("segoe_print.ttf");
		highlight.opacity=0;
	}
	
	public void setDimensions(float screenWidth,float screenHeight) {
		instrLabel.setMaxDimensions((int)(screenWidth*0.3f),(int)(screenHeight*0.1f));
		instrLabel.setTextSizeToMax();
	}
	
	public void setPositions(float screenWidth,float screenHeight) {
		instrLabel.setPosition(screenWidth*0.5f,screenHeight*0.5f);
	}
	
	public void scalePositions(float scaleX_,float scaleY_) {
		instrLabel.scalePosition(scaleX_,scaleY_);
	}
	
	public void start(Player demoPlayer,ForegroundLayer mFL) {
		state=STATE_FADE_IN;
		instrLabel.opacity=0;
		instrLabel.fadeIn();
		highlight.opacity=0;
		highlight.setDimensions(0,0);
		float xHighlightWorld=demoPlayer.x+demoPlayer.xCoeff*Seat.radiusY*0.8f;
		float yHighlightWorld=demoPlayer.y+demoPlayer.yCoeff*Seat.radiusY*0.8f;
		float xHighlightScreen=mFL.game.mWL.worldRenderer.xWorldToScreen(xHighlightWorld);
		float yHighlightScreen=mFL.game.mWL.worldRenderer.yWorldToScreen(yHighlightWorld);
		highlight.setPosition(xHighlightScreen, yHighlightScreen);
		if (demoPlayer.rotation==-90||demoPlayer.rotation==90) {
			dimsHighlight.set(Seat.radiusY*1.2f,Seat.radiusX *0.8f);
		} else {
			dimsHighlight.set(Seat.radiusX*0.8f,Seat.radiusY*1.2f);
		}
		
	}
	
	public void stop() {
		state=STATE_FADE_OUT;
		instrLabel.fadeOut();
	}
	
	public void animate(float delta) {
		if (state==STATE_FADE_IN) {
			highlight.opacity+=delta*2f;
			if (highlight.opacity>=0.4f) {
				highlight.opacity=0.4f;
				state=STATE_ANIMATE_1;
			}
		} else if (state==STATE_ANIMATE_1) {
			boolean done=true;
			if (Math.abs(highlight.radiusX-dimsHighlight.x)<2&&
					Math.abs(highlight.radiusY-dimsHighlight.y)<2) {
				;
			} else {
				float deltaRadiusX=5*delta*(dimsHighlight.x-highlight.radiusX);
				highlight.radiusX+=deltaRadiusX;
				float deltaRadiusY=5*delta*(dimsHighlight.y-highlight.radiusY);
				highlight.radiusY+=deltaRadiusY;
				done=false;
			}
			instrLabel.animate(delta);
			if (instrLabel.opacity!=1) {
				done=false;
			}
			if (done) {
				state=STATE_WAITING_FOR_TOUCH;
			}
		} else if (state==STATE_WAITING_FOR_TOUCH) {
			// wait here until user touches something whcih will move state to fade out
		} else if (state==STATE_FADE_OUT) {
			instrLabel.animate(delta);
			highlight.opacity-=delta*5f;
			if (highlight.opacity<0) {
				highlight.opacity=0;
			}
			if (instrLabel.opacity==0&&highlight.opacity==0) {
				state=STATE_NONE;
			}
		}
	}

	public boolean isReadyToStop() {
		return (state!=STATE_FADE_IN);
	}

	public boolean isRunning() {
		return (state!=STATE_NONE);
	}

}
