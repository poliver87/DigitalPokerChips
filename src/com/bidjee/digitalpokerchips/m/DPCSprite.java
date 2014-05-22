package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class DPCSprite {
	
	public Texture texture;
	public float x;
	public float y;
	public int radiusX;
	public int radiusY;
	protected boolean isTouched;
	private boolean touchable;
	private float touchAreaMultiplier;
	public float opacity;
	public float maxOpacity;
	public float rotation;
	public float xDest;
	public float yDest;
	public int radiusXDest;
	public int radiusYDest;
	public boolean atDest;
	
	private int fadeState;
	private boolean flashing;
	private long arrivalTime;
	
	private float fadeInSpeed;
	private float fadeOutSpeed;
	
	public int frame;
	private int frameElapsed;
	int frameDuration;
	public int numFrames;
	
	public int flashVisibleTime;
	public int flashInvisibleTime;
	
	private static final int FADE_NONE = 0;
	private static final int FADE_FADE_IN = 1;
	private static final int FADE_FADE_OUT = 2;
	private static final int FADE_WAIT_VISIBLE = 3;
	private static final int FADE_WAIT_INVISIBLE = 4;
	
	public DPCSprite() {
		x=0;
		y=0;
		radiusX=10;
		radiusY=10;
		isTouched=false;
		touchable=false;
		opacity=1f;
		rotation=0;
		fadeState=FADE_NONE;
		flashing=false;
		frame=0;
		touchAreaMultiplier=1;
		maxOpacity=1;
		fadeInSpeed=2f;
		fadeOutSpeed=5f;
		flashVisibleTime=100;
		flashInvisibleTime=300;
	}
	
	public DPCSprite(DPCSprite copyFrom) {
		this.x=copyFrom.x;
		this.y=copyFrom.y;
		this.radiusX=copyFrom.radiusX;
		this.radiusY=copyFrom.radiusY;
		this.isTouched=copyFrom.isTouched;
		this.touchable=copyFrom.touchable;
		this.touchAreaMultiplier=copyFrom.touchAreaMultiplier;
		this.opacity=copyFrom.opacity;
		this.maxOpacity=copyFrom.maxOpacity;
		this.rotation=copyFrom.rotation;
		this.xDest=copyFrom.xDest;
		this.yDest=copyFrom.yDest;
		this.atDest=copyFrom.atDest;
		this.fadeState=copyFrom.fadeState;
		this.flashing=copyFrom.flashing;
		this.arrivalTime=copyFrom.arrivalTime;
		this.fadeInSpeed=copyFrom.fadeInSpeed;
		this.fadeOutSpeed=copyFrom.fadeOutSpeed;
		this.frame=copyFrom.frame;
		this.frameElapsed=copyFrom.frameElapsed;
		this.frameDuration=copyFrom.frameDuration;
		this.numFrames=copyFrom.numFrames;
		this.flashVisibleTime=copyFrom.flashVisibleTime;
		this.flashInvisibleTime=copyFrom.flashInvisibleTime;
	}

	public void setDimensions(int radiusX,int radiusY) {
		this.radiusX=radiusX;
		this.radiusY=radiusY;
	}
	
	public void setDimensions(Vector2 dims_) {
		radiusX=(int) dims_.x;
		radiusY=(int) dims_.y;
	}
	
	public void setPosition(float x,float y) {
		this.x=x;
		this.y=y;
	}
	
	public void setPosition(Vector2 pos_) {
		x=pos_.x;
		y=pos_.y;
	}
	
	public void scalePosition(float scaleX,float scaleY) {
		x*=scaleX;
		y*=scaleY;
	}
	
	public void setDest(float xDest,float yDest) {
		this.xDest=xDest;
		this.yDest=yDest;
	}
	
	public void setAnimation(int numFrames_,int frameDuration_) {
		frameDuration=frameDuration_;
		numFrames=numFrames_;
		frame=0;
	}
	
	public void stopAnimation() {
		numFrames=0;
	}
	
	public boolean getIsTouched() { return isTouched;}
	
	public boolean getIsFlashing() {return flashing;}
	
	/** Sets the ratio of the touchable area of the sprite to the rendered area
	 * of the sprite. e.g. set multiplier to 1.1 for touchable area 10% larger than rendered area
	 * @param multiplier the ratio of touchable area to rendered area */
	public void setTouchAreaMultiplier(float multiplier) {
		touchAreaMultiplier=multiplier;
	}
	
	public void setIsTouched(boolean t_) {
		isTouched=t_;
		opacity=maxOpacity;
	}
	
	public boolean getTouchable() {
		return touchable;
	}
	
	public void setTouchable(boolean touchable) {
		this.touchable=touchable;
	}
	
	public boolean pointContained(float x_,float y_) {
		boolean contained=false;
		int radiusXRotated=(int) (Math.sin(Math.toRadians(rotation))*radiusY+Math.cos(Math.toRadians(rotation))*radiusX);
		radiusXRotated=(Math.abs(radiusXRotated));
		int radiusYRotated=(int) (Math.sin(Math.toRadians(rotation))*radiusX+Math.cos(Math.toRadians(rotation))*radiusY);
		radiusYRotated=(Math.abs(radiusYRotated));
		if (Math.abs(x_-x)<radiusXRotated*touchAreaMultiplier&&(Math.abs(y_-y)<radiusYRotated*touchAreaMultiplier)) {
			contained=true;
		}
		return contained;
	}
	
	public void setOpacity(float opacity) {
		this.opacity=opacity;
		fadeState=FADE_NONE;
	}
	
	public void fadeIn() {
		flashing=false;
		fadeState=FADE_FADE_IN;
	}
	
	public void fadeOut() {
		flashing=false;
		fadeState=FADE_FADE_OUT;
	}
	
	public void startFlashing() {
		if (!flashing) {
			flashing=true;
			if (fadeState==FADE_NONE) {
				fadeState=FADE_FADE_IN;
			}
		}
	}
	
	public void stopFlashing() {
		flashing=false;
		fadeState=FADE_NONE;
	}
	
	public void animate(float delta) {
		if (!isTouched) {
			switch (fadeState) {
			case FADE_FADE_IN:
				opacity+=delta*fadeInSpeed;
				if (opacity>=maxOpacity) {
					opacity=maxOpacity;
					fadeState=FADE_WAIT_VISIBLE;
					arrivalTime=System.currentTimeMillis();
				}
				break;
			case FADE_FADE_OUT:
				opacity-=delta*fadeOutSpeed;
				if (opacity<=0) {
					opacity=0;
					fadeState=FADE_WAIT_INVISIBLE;
					arrivalTime=System.currentTimeMillis();
				}
				break;
			case FADE_WAIT_VISIBLE:
				if (System.currentTimeMillis()-arrivalTime>flashVisibleTime) {
					if (flashing) {
						fadeState=FADE_FADE_OUT;
					} else {
						fadeState=FADE_NONE;
					}
				}
				break;
			case FADE_WAIT_INVISIBLE:
				if (System.currentTimeMillis()-arrivalTime>flashInvisibleTime) {
					if (flashing) {
						fadeState=FADE_FADE_IN;
					} else{
						fadeState=FADE_NONE;
					}
				}
				break;
			default:
				break;
			}
		} else {
			opacity=1;
			fadeState=FADE_WAIT_VISIBLE;
		}
		if (numFrames>0) {
			frameElapsed+=(int)(delta*1000f);
			if (frameElapsed>frameDuration) {
				frame++;
				frameElapsed=0;
			}
			if (frame==numFrames) {
				frame=0;
			}
		}
	}
	
	public void clear() {
		opacity=0;
		fadeState=FADE_NONE;
		flashing=false;
	}
	
	public void setFadeInSpeed(float speed) {
		fadeInSpeed=speed;
	}
	public float getFadeInSpeed() {
		return fadeInSpeed;
	}
	public void setFadeOutSpeed(float speed) {
		fadeOutSpeed=speed;
	}
	public float getFadeOutSpeed() {
		return fadeOutSpeed;
	}
	
	public void init() {}
	
}
