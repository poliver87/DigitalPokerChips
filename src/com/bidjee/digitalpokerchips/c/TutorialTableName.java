package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.TextLabel;

public class TutorialTableName {
	
	static final int STATE_NONE = 0;
	static final int STATE_FADE_IN = 2;
	static final int STATE_HAND_DOWN = 3;
	static final int STATE_HAND_UP = 4;
	static final int STATE_FADE_OUT = 5;
	
	public int tutorialState;
	
	Vector2 posHandStart=new Vector2();
	Vector2 posHandEnd=new Vector2();
	
	public DPCSprite nameHighlightRect=new DPCSprite();
	public TextLabel label1;
	public TextLabel label2;
	// hand just for timing
	public DPCSprite hand=new DPCSprite();
	
	private ForegroundLayer mFL;
	
	public TutorialTableName(ForegroundLayer mFL_) {
		mFL=mFL_;
		tutorialState=STATE_NONE;
		nameHighlightRect.opacity=0;
		hand.opacity=0;
		label1=new TextLabel("Change Table Name",0,true,0,false);
		label1.setFontFace("segoe_print.ttf");
		label2=new TextLabel("tap table name any time to change",0,true,0,false);
		label2.setFontFace("segoe_print.ttf");
	}
	
	public void setDimensions(float screenWidth_,float screenHeight_) {
		nameHighlightRect.setDimensions((int)(screenWidth_*0.3f),(int)(screenHeight_*0.14f));
		label1.setMaxDimensions((int)(screenWidth_*0.2f),(int)(screenHeight_*0.2f));
		label1.setTextSizeToMax();
		label2.setMaxDimensions((int)(screenWidth_*0.2f),(int)(screenHeight_*0.2f));
		label2.setTextSizeToMax();
		hand.setDimensions((int)(screenHeight_*0.10f),(int)(screenHeight_*0.10f));
	}
	
	public void setPositions(float screenWidth_,float screenHeight_) {
		nameHighlightRect.setPosition(screenWidth_*0.5f,screenHeight_*0.5f);
		label1.setPosition(screenWidth_*0.5f,screenHeight_*0.9f);
		label2.setPosition(screenWidth_*0.5f,screenHeight_*0.8f);
		posHandStart.set(screenWidth_*0.6f,screenHeight_*0.38f);
		posHandEnd.set(screenWidth_*0.6f,screenHeight_*-0.45f);
	}
	
	public void scalePositions(float scaleX_,float scaleY_) {
		nameHighlightRect.scalePosition(scaleX_,scaleY_);
		label1.scalePosition(scaleX_,scaleY_);
		label2.scalePosition(scaleX_,scaleY_);
		posHandStart.x*=scaleX_;
		posHandStart.y*=scaleY_;
		posHandEnd.x*=scaleX_;
		posHandEnd.y*=scaleY_;
		hand.scalePosition(scaleX_,scaleY_);
	}
	
	public void start() {
		tutorialState=STATE_FADE_IN;
		mFL.game.freezeAnimation();
	}
	
	public void stop() {
		tutorialState=STATE_FADE_OUT;
	}
	
	public void animate(float delta_) {
		if (tutorialState==STATE_FADE_IN) {
			hand.setPosition(posHandStart);
			nameHighlightRect.opacity+=1.5f*delta_;
			if (nameHighlightRect.opacity>0.6f) {
				nameHighlightRect.opacity=0.6f;
			}
			label1.opacity+=1.5f*delta_;
			if (label1.opacity>1) {
				label1.opacity=1;
			}
			label2.opacity=label1.opacity;
			if (nameHighlightRect.opacity==0.6f&&
					label1.opacity==1) {
				tutorialState=STATE_HAND_DOWN;
			}
		} else if (tutorialState==STATE_HAND_DOWN) {
			if (Math.abs(hand.x-posHandEnd.x)<2&&
					Math.abs(hand.y-posHandEnd.y)<2) {
				tutorialState=STATE_HAND_UP;
			} else {
				float deltaX_=delta_*3*(hand.x-posHandEnd.x);
				hand.x-=deltaX_;
				float deltaY_=delta_*3*(hand.y-posHandEnd.y);
				hand.y-=deltaY_;
			}
		} else if (tutorialState==STATE_HAND_UP) {
			if (Math.abs(hand.x-posHandStart.x)<2&&
					Math.abs(hand.y-posHandStart.y)<2) {
				tutorialState=STATE_FADE_OUT;
			} else {
				float deltaX_=delta_*3*(hand.x-posHandStart.x);
				hand.x-=deltaX_;
				float deltaY_=delta_*3*(hand.y-posHandStart.y);
				hand.y-=deltaY_;
			}
		} else if (tutorialState==STATE_FADE_OUT) {
			nameHighlightRect.opacity-=1.5f*delta_;
			if (nameHighlightRect.opacity<0) {
				nameHighlightRect.opacity=0;
			}
			label1.opacity-=2f*delta_;
			if (label1.opacity<0) {
				label1.opacity=0;
			}
			label2.opacity=label1.opacity;
			if (nameHighlightRect.opacity==0&&
					label1.opacity==0) {
				tutorialState=STATE_NONE;
				mFL.game.unfreezeAnimation();
			}
		}
	}
}
