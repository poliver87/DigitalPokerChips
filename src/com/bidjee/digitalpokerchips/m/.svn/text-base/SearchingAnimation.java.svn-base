package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;

public class SearchingAnimation extends DPCSprite {
	
	static final int STATE_STOPPED = 0;
	static final int STATE_PING = 1;
	static final int STATE_FADE_OUT = 2;
	
	int animationState;
	
	Vector2 pingDimsSmall=new Vector2();
	Vector2 pingDimsBig=new Vector2();
	
	public DPCSprite ping=new DPCSprite();
	
	public SearchingAnimation() {
		ping.opacity=0;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		pingDimsSmall.set((int)(radiusX*1f),(int)(radiusY*0.5f));
		pingDimsBig.set(pingDimsSmall.x*7,pingDimsSmall.y*7);
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x,y);
		ping.setPosition(x,y);
	}
	
	@Override
	public void scalePosition(float scaleX,float scaleY) {
		super.scalePosition(scaleX,scaleY);
		ping.scalePosition(scaleX,scaleY);
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		if (animationState==STATE_PING) {
			if (ping.radiusX<pingDimsBig.x||ping.radiusY<pingDimsBig.y) {
				ping.radiusX+=delta*pingDimsSmall.x*13f;
				ping.radiusY=(int) (ping.radiusX*0.5f);
			}
		} else if (animationState==STATE_FADE_OUT) {
			this.opacity-=delta*5f;
			if (this.opacity<=0) {
				this.opacity=0;
				animationState=STATE_STOPPED;
			}
			ping.opacity=this.opacity;
		}
	}
	
	public void ping() {
		animationState=STATE_PING;
		ping.setDimensions(pingDimsSmall);
		this.opacity=1;
		ping.opacity=1;
	}
	
	public void stop() {
		animationState=STATE_FADE_OUT;
	}

}
