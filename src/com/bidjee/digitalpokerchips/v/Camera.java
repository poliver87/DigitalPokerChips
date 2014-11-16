package com.bidjee.digitalpokerchips.v;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.m.CameraPosition;
import com.bidjee.digitalpokerchips.m.DPCSprite;

public class Camera {
	
	static final int ANIM_NONE = 0;
	static final int ANIM_ZOOMING = 1;
	static final int ANIM_PANNING = 2;
	static final int ANIM_ZOOMING_AND_PANNING = 3;
	// View Matrix
	private final Matrix4 viewMatrix = new Matrix4();
	// State Variables
	float x;
	float y;
	float yLast;
	float xLast;
	private int xDest;
	private int yDest;
	float zoom;
	float zoomLast;
	float zoomDest;
	private int animationState;
	// Screen Scale & Layout //
	int maxCameraDelta;
	float screenWidth;
	float screenHeight;
	float panMargin;
	float zoomMargin;
	// References //
	WorldRenderer mWR;
	
	public Camera(WorldRenderer mWR_) {
		x=0;
		y=0;
		mWR=mWR_;
		zoom=1;
		animationState=ANIM_NONE;
	}
	
	public void resize(float width_,float height_) {
		if (screenWidth>0) {
			float scaleX_=width_/screenWidth;
			x=(int) (x*scaleX_);
			xDest=(int) (xDest*scaleX_);
		}
		if (screenHeight>0) {
			float scaleY_=height_/screenHeight;
			y=(int) (y*scaleY_);
			yDest=(int) (yDest*scaleY_);
		}
		screenWidth=width_;
		screenHeight=height_;
		maxCameraDelta=(int) (height_*0.05f);
		panMargin=Math.max(1,height_*0.003f);
		zoomMargin=1/width_;
		updateMatrix();
	}
	
	private void updateMatrix() {
		viewMatrix.setToOrtho2D(x-screenWidth/(2*zoom),y-screenHeight/(2*zoom),screenWidth/zoom,screenHeight/zoom);
	}
	
	public void setTo(CameraPosition position_) {
		setY((int)(position_.getY()));
		setX((int)(position_.getX()));
		setZoom(position_.getZoomFactor());
		updateMatrix();
		mWR.mWL.cameraAtDestination();
	}
	
	public void sendTo(CameraPosition destination) {
		if (Math.abs(destination.getZoomFactor()-zoom)>destination.getZoomFactor()*zoomMargin&&
				(Math.abs(x-destination.getX())>panMargin||
				Math.abs(y-destination.getY())>panMargin)) {
			zoomAndPanTo(destination);
		} else if (Math.abs(destination.getZoomFactor()-zoom)>destination.getZoomFactor()*zoomMargin) {
			zoomTo(destination);
		} else if (Math.abs(x-destination.getX())>panMargin||
				Math.abs(y-destination.getY())>panMargin) {
			panTo(destination);
		} else {
			
		}
	}
	
	private void zoomTo(CameraPosition destination) {
		animationState=ANIM_ZOOMING;
		zoomLast=zoom;
		zoomDest=destination.getZoomFactor();
	}
	
	private void panTo(CameraPosition destination) {
		animationState=ANIM_PANNING;
		yLast=y;
		xLast=x;
		yDest=(int) destination.getY();
		xDest=(int) destination.getX();
	}
	
	private void zoomAndPanTo(CameraPosition destination) {
		animationState=ANIM_ZOOMING_AND_PANNING;
		zoomLast=zoom;
		yLast=y;
		xLast=x;
		yDest=(int) destination.getY();
		xDest=(int) destination.getX();
		zoomDest=destination.getZoomFactor();
	}
	
	public void animate(float delta) {
		if (animationState==ANIM_ZOOMING) {
			float deltaZoom_=delta*4f*(zoom-zoomDest);
			if (deltaZoom_<-0.03f) {
				deltaZoom_=-0.03f;
			} else if (deltaZoom_>0.03f) {
				deltaZoom_=0.03f;
			}
			setZoom(zoom-deltaZoom_);
			if (Math.abs(zoom-zoomDest)<zoomDest*zoomMargin) {
				animationState=ANIM_NONE;
				//setZoom(zoomDest);
				mWR.mWL.cameraAtDestination();
			}
			updateMatrix();
		} else if (animationState==ANIM_PANNING) {
			float deltaY_=delta*4f*(y-yDest);
			if (deltaY_<-1*maxCameraDelta) {
				deltaY_=-1*maxCameraDelta;
			} else if (deltaY_>maxCameraDelta) {
				deltaY_=maxCameraDelta;
			}
			setY(y-deltaY_);
			float travelRatio_=(y-yLast)/(yDest-yLast);
			setX(x+travelRatio_*(xDest-xLast));
			if (Math.abs(y-yDest)<panMargin&&
					Math.abs(x-xDest)<panMargin) {
				animationState=ANIM_NONE;
				//setX(xDest);
				//setY(yDest);
				mWR.mWL.cameraAtDestination();
			}
			updateMatrix();
		} else if (animationState==ANIM_ZOOMING_AND_PANNING) {
			float deltaZoom_=delta*4f*(zoom-zoomDest);
			if (deltaZoom_<-0.03f) {
				deltaZoom_=-0.03f;
			} else if (deltaZoom_>0.03f) {
				deltaZoom_=0.03f;
			}
			setZoom(zoom-deltaZoom_);
			float travelRatio_=(zoomDest/zoom)*Math.abs(zoom-zoomLast)/Math.abs(zoomDest-zoomLast);
			setY(travelRatio_*(yDest-yLast)+yLast);
			setX(travelRatio_*(xDest-xLast)+xLast);
			if (Math.abs(zoom-zoomDest)<zoomDest*zoomMargin&&
					Math.abs(y-yDest)<panMargin&&
					Math.abs(x-xDest)<panMargin) {
				animationState=ANIM_NONE;
				//setZoom(zoomDest);
				//setX(xDest);
				//setY(yDest);
				mWR.mWL.cameraAtDestination();
			}
			updateMatrix();
		}
	}
	
	public Matrix4 getViewMatrix() {
		return viewMatrix;
	}
	
	private void translate(float deltaX_,float deltaY_) {
		y+=deltaY_;
		x+=deltaX_;
		viewMatrix.translate(deltaX_/zoom,-deltaY_/zoom,0);
	}
	
	private void setZoom(float zoom_) {
		zoom=zoom_;
	}
	
	private void setY(float y_) {
		translate(0,y_-y);
	}
	
	private void setX(float x_) {
		translate(x_-x,0);
	}

	public boolean getAtDest() {
		return (animationState==ANIM_NONE);
	}

	public float getTopY(CameraPosition zoomDest_) {
		return zoomDest_.getY()+(0.5f*screenHeight)/zoomDest_.getZoomFactor();
	}

	public boolean isOnScreen(Vector2 pixel_) {
		boolean onScreen_=false;
		if (pixel_.x>=x-(0.5f*screenWidth)/zoom&&
				pixel_.x<=x+(0.5f*screenWidth)/zoom&&
				pixel_.y>=y-(0.5f*screenHeight)/zoom&&
				pixel_.y<=y+(0.5f*screenHeight)/zoom) {
			onScreen_=true;
		}
		return onScreen_;
	}
	
	public boolean isOnScreen(DPCSprite sprite) {
		float left;
		float top;
		float right;
		float bottom;
		if (sprite.rotation==90||sprite.rotation==-90) {
			left=sprite.x-sprite.radiusY;
			top=sprite.y+sprite.radiusX;
			right=sprite.x+sprite.radiusY;
			bottom=sprite.y-sprite.radiusX;
		} else {
			left=sprite.x-sprite.radiusX;
			top=sprite.y+sprite.radiusY;
			right=sprite.x+sprite.radiusX;
			bottom=sprite.y-sprite.radiusY;
		}
		return (isOnScreen(new Vector2(left,top))||isOnScreen(new Vector2(right,top))||
				isOnScreen(new Vector2(left,bottom))||isOnScreen(new Vector2(left,bottom)));
	}

	public boolean testZoomLessThan(float zoomTest) {
		if (zoom<zoomTest) {
			return true;
		} else {
			return false;
		}
	}
	
}
