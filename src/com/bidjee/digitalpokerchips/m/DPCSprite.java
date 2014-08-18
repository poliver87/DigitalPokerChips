package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class DPCSprite {
	
	public static final int MOVE_NONE = 0;
	public static final int MOVE_EASE_IN = 1;
	public static final int MOVE_LINEAR = 2;
	public static final int MOVE_BOUNCE = 3;
	
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
	Vector2 velocity=new Vector2();
	private float gravityAcceleration;
	private float elasticity;
	private float flightTime;
	private float y0;
	private float v0;
	public float distanceMargin;
	int moveHoldoffTimer;
	int moveHoldoffDuration;
	int moveFunction;
	
	private int fadeState;
	private boolean flashing;
	private long arrivalTime;
	
	private float fadeInSpeed;
	private float fadeOutSpeed;
	
	public float moveSpeed;
	
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
		atDest=true;
		isTouched=false;
		touchable=false;
		opacity=1f;
		rotation=0;
		fadeState=FADE_NONE;
		flashing=false;
		moveSpeed=9;
		frame=0;
		touchAreaMultiplier=1;
		maxOpacity=1;
		fadeInSpeed=2f;
		fadeOutSpeed=5f;
		flashVisibleTime=100;
		flashInvisibleTime=300;
		moveFunction=MOVE_EASE_IN;
		distanceMargin=2;
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
		this.moveFunction=copyFrom.moveFunction;
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
	
	public void setDest(float xDest,float yDest,int holdoff) {
		this.xDest=xDest;
		this.yDest=yDest;
		flightTime=0;
		y0=y;
		v0=0;
		moveHoldoffTimer=0;
		moveHoldoffDuration=holdoff;
		atDest=false;
	}
	
	public void setDest(float xDest,float yDest) {
		this.setDest(xDest,yDest,0);
	}
	
	public void setDest(Vector2 dest) {
		this.setDest(dest.x,dest.y,0);
	}
	
	public void setDest(Vector2 dest,int holdoff) {
		this.setDest(dest.x, dest.y, holdoff);
	}
	
	public void setMoveFunc(int moveFunc,float distanceMargin,float moveSpeed,float gravityAcceleration,float elasticity) {
		this.moveFunction=moveFunc;
		this.distanceMargin=distanceMargin;
		this.moveSpeed=moveSpeed;
		this.gravityAcceleration=-1*Math.abs(gravityAcceleration);
		this.elasticity=elasticity;
	}
	
	public void setMoveFunc(int moveFunc) {
		this.setMoveFunc(moveFunc,this.distanceMargin,this.moveSpeed,this.gravityAcceleration,this.elasticity);
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
					arrivalTime=0;
				}
				break;
			case FADE_FADE_OUT:
				opacity-=delta*fadeOutSpeed;
				if (opacity<=0) {
					opacity=0;
					fadeState=FADE_WAIT_INVISIBLE;
					arrivalTime=0;
				}
				break;
			case FADE_WAIT_VISIBLE:
				if (arrivalTime>flashVisibleTime) {
					if (flashing) {
						fadeState=FADE_FADE_OUT;
					} else {
						fadeState=FADE_NONE;
					}
				} else {
					arrivalTime+=delta*1000;
				}
				break;
			case FADE_WAIT_INVISIBLE:
				if (arrivalTime>flashInvisibleTime) {
					if (flashing) {
						fadeState=FADE_FADE_IN;
					} else{
						fadeState=FADE_NONE;
					}
				} else {
					arrivalTime+=delta*1000;
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
		if (!atDest) {
			if (moveHoldoffTimer>=moveHoldoffDuration) {
				float xNew=0;
				float yNew=0;
				if (moveFunction==MOVE_EASE_IN) {
					if (Math.abs(y-yDest)<distanceMargin&&Math.abs(x-xDest)<distanceMargin) {
						atDest=true;
					} else {
						float timeFactor = delta*moveSpeed;
						yNew=(float)(y-timeFactor*(y-yDest));
						xNew=(float)(x-timeFactor*(x-xDest));
						this.setPosition(xNew,yNew);
					}
				} else if (moveFunction==MOVE_LINEAR) {
					
				} else if (moveFunction==MOVE_BOUNCE) {
					float yf=y0+v0*(flightTime+delta)+0.5f*gravityAcceleration*(flightTime+delta)*(flightTime+delta);
					if (yf>yDest) {
						flightTime+=delta;
						yNew=y0+v0*flightTime+0.5f*gravityAcceleration*flightTime*flightTime;
					} else {
						float deltaY=y-yDest;
						double vStart=v0+gravityAcceleration*flightTime;
						double timeBeforeCollision=(-1*vStart-Math.sqrt(vStart*vStart-gravityAcceleration*deltaY))/gravityAcceleration;
						double velocityAtCollision=vStart+gravityAcceleration*timeBeforeCollision;
						yNew=yDest;
						y0=yNew;
						flightTime=(float) Math.max(delta-timeBeforeCollision, 0);
						v0=(float) (-1*velocityAtCollision*elasticity);
						yNew=y0+v0*flightTime+0.5f*gravityAcceleration*flightTime*flightTime;
						if (yNew<=yDest) {
							atDest=true;
							flightTime=0;
							v0=1000;
							yNew=yDest;
							y0=yNew;
						}
					}
					this.setPosition(x,yNew);
				}
			} else {
				moveHoldoffTimer+=delta*1000;
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
