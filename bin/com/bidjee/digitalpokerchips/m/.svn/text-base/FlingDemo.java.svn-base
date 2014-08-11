package com.bidjee.digitalpokerchips.m;

public class FlingDemo {
	
	static final int WAIT_DURATION = 2500;
	
	static final int STATE_STOPPED = 0;
	static final int STATE_WAITING = 1;
	static final int STATE_FADE_IN = 2;
	static final int STATE_FLINGING = 3;
	static final int STATE_PAUSED = 4;
	
	long waitStartTime;
	int animationState;
	
	Hand hand;
	
	float screenWidth;
	float screenHeight;
	float rotation;
	float startX;
	float startY;
	
	public FlingDemo() {
		animationState=STATE_STOPPED;
		hand=new Hand();
		screenWidth=0;
		screenHeight=0;
		rotation=0;
		startX=0;
		startY=0;
	}
	
	public void setDimensions(int radiusX_,int radiusY_,float screenWidth_,float screenHeight_) {
		//hand.setDimensions(radiusX_,radiusY_);
		screenWidth=screenWidth_;
		screenHeight=screenHeight_;
	}
	
	public void scalePosition(float scaleX_,float scaleY_) {
		hand.x*=scaleX_;
		hand.y*=scaleY_;
		startX*=scaleX_;
		startY*=scaleY_;
		hand.velocityX*=scaleX_;
		hand.velocityY*=scaleY_;
		
	}
	
	public void start(float rotation_,float startX_,float startY_) {
		rotation=rotation_;
		startX=startX_;
		startY=startY_;
		hand.x=startX;
		hand.y=startY;
		animationState=STATE_WAITING;
		hand.opacity=0;
		hand.velocityX=0;
		hand.velocityY=0;
		waitStartTime=System.currentTimeMillis();
		if (rotation==0||rotation==360) {
			hand.velocityY=-0.5f*screenWidth;
			hand.x-=hand.radiusX;
			hand.y+=hand.radiusY;
		} else if (rotation==-90||rotation==270) {
			hand.velocityX=screenWidth*-0.5f;
			hand.y+=hand.radiusX;
			hand.x+=hand.radiusY;
		} else if (rotation==180||rotation==-180) {
			hand.velocityY=0.5f*screenWidth;
			hand.x+=hand.radiusX;
			hand.y-=hand.radiusY;
		} else if (rotation==90||rotation==-270) {
			hand.velocityX=screenWidth*0.5f;
			hand.y-=hand.radiusX;
			hand.x-=hand.radiusY;
		}
	}
	
	public void stop() {
		animationState=STATE_STOPPED;
		hand.opacity=0;
	}
	
	public void pause() {
		animationState=STATE_PAUSED;
		hand.opacity=0;
	}
	
	public void unpause() {
		if (animationState==STATE_PAUSED) {
			start(rotation,startX,startY);
		}
	}
	
	public void animate(float delta) {
		if (animationState==STATE_WAITING) {
			if (System.currentTimeMillis()-waitStartTime>WAIT_DURATION)
				animationState=STATE_FADE_IN;
		} else if (animationState==STATE_FADE_IN) {
			hand.opacity+=delta*2f;
			if (hand.opacity>=1) {
				hand.opacity=1;
				animationState=STATE_FLINGING;
			}
		} else if (animationState==STATE_FLINGING) {
			hand.x+=hand.velocityX*delta;
			hand.y+=hand.velocityY*delta;
			if (hand.velocityY<0) {
				if (hand.y<-hand.radiusY)
					start(rotation,startX,startY);
			} else if (hand.velocityX<0) {
				if (hand.x<-hand.radiusX)
					start(rotation,startX,startY);
			} else if (hand.velocityY>0) {
				if (hand.y>screenHeight+hand.radiusY)
					start(rotation,startX,startY);
			} else if (hand.velocityX>0) {
				if (hand.x>screenWidth+hand.radiusX)
					start(rotation,startX,startY);
			}
		}
	}
	

}
