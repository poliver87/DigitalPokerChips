package com.bidjee.digitalpokerchips.m;

public class HostDealerChip {
	
	public float x;
	public float y;
	float originX;
	float originY;
	public float radiusX;
	public float radiusY;
	float destX;
	float destY;
	
	public float rotation;
	
	private boolean isTouched;
	public float touchDownX;
	public float touchDownY;
	public float touchDraggedX;
	public float touchDraggedY;
	
	float velocityY;
	float velocityX;
	
	float screenWidth;
	float screenHeight;
	
	public boolean isOnScreen;
	
	public float opacity;
	
	public HostDealerChip() {
		rotation=0;
		isTouched=false;
		isOnScreen=true;
		destX=0;
		originX=0;
		opacity=0;
	}
	
	public void setDimensions(float radiusX_,float radiusY_,float screenWidth_,float screenHeight_) {
		radiusX=radiusX_;
		radiusY=radiusY_;
		screenWidth=screenWidth_;
		screenHeight=screenHeight_;
	}
	
	public void setPosition(float x_,float y_,float scaleX_,float scaleY_) {
		if (originX==0) {
			x=x_;
			y=y_;
			velocityY=0;
			velocityX=0;
			destX=x_;
			destY=y_;
		} else {
			x*=scaleX_;
			y*=scaleY_;
			destX*=scaleX_;
			destY*=scaleY_;
		}
		originX=x_;
		originY=y_;
	}
	
	public void setDest(Seat seat_) {
		rotation=seat_.rotation;
		if (rotation==0||rotation==360) {
			destX=seat_.x;
			destY=seat_.y+Seat.radiusY*1.2f;
		} else if (rotation==90||rotation==-270) {
			destX=seat_.x-Seat.radiusY*1.2f;
			destY=seat_.y;
		} else if (rotation==180||rotation==-180) {
			destX=seat_.x;
			destY=seat_.y-Seat.radiusY*1.2f;
		} else if (rotation==270||rotation==-90) {
			destX=seat_.x+Seat.radiusY*1.2f;
			destY=seat_.y;
		}
	}
	
	public void setVelocityY(float velocityY_) {
		velocityY=velocityY_;
	}
	
	public void setVelocityX(float velocityX_) {
		velocityX=velocityX_;
	}
	
	public void resetDest() {
		destX=originX;
		destY=originY;
		rotation=0;
	}
	
	public boolean pointContained(float x_,float y_) {
		float deltaX_=x_-x;
		float deltaY_=y_-y;
		float xDist_=Math.abs(deltaX_)/radiusX;
		float yDist_=Math.abs(deltaY_)/radiusY;
		float totalDist_=(float)(xDist_*xDist_+yDist_*yDist_);
		return (totalDist_<1);
	}
	
	public boolean animate(float delta) {
		boolean sendDealerChip_=false;
		if (velocityY!=0) {
			y+=velocityY*delta;
			if (velocityY>0&&y>screenHeight) {
				sendDealerChip_=true;
				if (y>screenHeight+radiusY)
					isOnScreen=false;
			} else if (velocityY<0&&y<0) {
				sendDealerChip_=true;
				if (y<-1*radiusY)
					isOnScreen=false;
			}
		} else if (velocityX!=0) {
			x+=velocityX*delta;
			if (velocityX>0&&x>screenWidth) {
				sendDealerChip_=true;
				if (x>screenWidth+radiusY)
					isOnScreen=false;
			} else if (velocityX<0&&x<0) {
				sendDealerChip_=true;
				if (x<-1*radiusY)
					isOnScreen=false;
			}
		} else if (Math.abs(y-destY)<1&&
				Math.abs(x-destX)<1) {
			x=destX;
			y=destY;
		} else {
			float timeFactor = delta*9;
			y=(float)(y-timeFactor*(y-destY));
			x=(float)(x-timeFactor*(x-destX));
		}
		return sendDealerChip_;
	}

	public boolean isTouched() {
		return isTouched;
	}

	public void setTouched(boolean isTouched,float touchDownX_,float touchDownY_) {
		this.isTouched = isTouched;
		touchDownX=touchDownX_;
		touchDownY=touchDownY_;
	}
	
	public void touchDragged(float touchDraggedX_,float touchDraggedY_) {
		touchDraggedX=touchDraggedX_;
		touchDraggedY=touchDraggedY_;
	}
	
	public void reset() {
		isTouched=false;
		velocityY=0;
		velocityX=0;
		x=originX;
		y=originY;
		isOnScreen=false;
		opacity=0;
		resetDest();
	}
	
	public void fadeOut(float delta) {
		if (opacity>0) {
			opacity-=delta*5f;
			if (opacity<0)
				opacity=0;
		}
	}
	
	public void fadeIn(float delta) {
		if (opacity<1) {
			opacity+=delta*2f;
			if (opacity>1)
				opacity=1;
		}
	}

}
