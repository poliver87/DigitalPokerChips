package com.bidjee.digitalpokerchips.m;

public class Seat {
	
	public static final int[] zOrder={5,6,4,7,3,0,2,1};
	
	public static final float PLAYER_CHAIR_RATIO_X = 0.6f;
	public static final float PLAYER_CHAIR_RATIO_Y = 0.9f;
	
	public static int radiusX;
	public static int radiusY;
	public static float opacity=0;
	public static float distToOffscreen;
	
	public int position;
	public float x;
	public float y;
	
	public float rotation;
	
	public float xOffscreen;
	public float yOffscreen;
	public float xScreenEdge;
	public float yScreenEdge;
	
	public Player player;
	
	public DPCSprite playerSlot=new DPCSprite();
	
	public Button undoButton;
	
	public Seat(int i_) {
		player=null;
		position=i_;
		playerSlot.opacity=0;
		playerSlot.flashVisibleTime=100;
		playerSlot.flashInvisibleTime=0;
		playerSlot.setFadeInSpeed(1);
		playerSlot.setFadeOutSpeed(3);
		undoButton=new Button(true,0,"Undo");
	}
	
	public void setDimensions(int radiusX_,int radiusY_) {
		radiusX=radiusX_;
		radiusY=radiusY_;
		if (player!=null) {
			player.setDimensions((int)(radiusX_*PLAYER_CHAIR_RATIO_X),
					(int)(radiusY_*PLAYER_CHAIR_RATIO_Y));
		}
		playerSlot.setDimensions((int)(radiusY*1.7f),(int)(radiusY*0.6f));
		undoButton.setDimensions((int)(radiusX*0.8f),(int)(radiusY*0.2f));
	}
	
	public void setPosition(float x_,float y_,float rotation_) {
		x=x_;
		y=y_;
		rotation=rotation_;
		if (player!=null) {
			player.setPosition(x_,y_,rotation_);
		}
		float xCoeff=(float) Math.sin(Math.toRadians(rotation_));
		float yCoeff=(float) Math.cos(Math.toRadians(rotation_));
		xOffscreen=(float) (x_+xCoeff*distToOffscreen);
		yOffscreen=(float) (y_-yCoeff*distToOffscreen);
		xScreenEdge=(float) (x_+xCoeff*radiusY);
		yScreenEdge=(float) (y_-yCoeff*radiusY);
		playerSlot.setPosition((float) (x_+xCoeff*radiusY),(float) (y_-yCoeff*radiusY));
		float undoOffset=radiusY*0.5f;
		undoButton.setPosition(x_+xCoeff*undoOffset,y_+yCoeff*undoOffset);
	}
	
	public void scalePosition(float scaleX_,float scaleY_) {
		x*=scaleX_;
		y*=scaleY_;
		if (player!=null) {
			player.scalePosition(scaleX_,scaleY_);
		}
		playerSlot.scalePosition(scaleX_,scaleY_);
	}
	
	public void animate(float delta) {
		playerSlot.animate(delta);
		undoButton.animate(delta);
	}
	
	public void seatPlayer(Player player_) {
		// TODO change the flow of this so player gets sized like everything else
		player_.setDimensions(radiusX,radiusY);
		player_.setPosition(x,y,rotation);
		player_.seat();
		setPlayer(player_);
	}
	
	public void setPlayer(Player player_) {
		player=player_;
		player.seat();
	}
	
	/** Note that anywhere south of the chair is included
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @return contained whether the point is contained in the seat */
	public boolean pointContained(float x,float y) {
		boolean contained_=false;
		if (rotation==0||rotation==360) {
			if (Math.abs(x-this.x)<Seat.radiusX&&
					y-this.y<Seat.radiusY*1.2f)
				contained_=true;
		} else if (rotation==90||rotation==-270) {
			if (Math.abs(y-this.y)<Seat.radiusX&&
					this.x-x<Seat.radiusY*1.2f)
				contained_=true;
		} else if (rotation==180||rotation==-180) {
			if (Math.abs(x-this.x)<Seat.radiusX&&
					this.y-y<Seat.radiusY*1.2f)
				contained_=true;
		} else if (rotation==270||rotation==-90) {
			if (Math.abs(y-this.y)<Seat.radiusX&&
					x-this.x<Seat.radiusY*1.2f)
				contained_=true;
		}
		return contained_;
	}
	
	public static void fadeOut(float delta) {
		if (opacity>0) {
			Seat.opacity-=delta*5f;
			if (Seat.opacity<0)
				Seat.opacity=0;
		}
	}
	
	public static void fadeIn(float delta) {
		if (opacity<1) {
			Seat.opacity+=delta*2f;
			if (Seat.opacity>1)
				Seat.opacity=1;
		}
	}

	public static int getStartingSeat(int deltaAzimuth_) {
		int startingSeat_;
		if (deltaAzimuth_>18&&deltaAzimuth_<=50) {
			startingSeat_=0;
		} else if (deltaAzimuth_>50&&deltaAzimuth_<=130) {
			startingSeat_=7;
		} else if (deltaAzimuth_>130&&deltaAzimuth_<=162) {
			startingSeat_=6;
		} else if (deltaAzimuth_>162&&deltaAzimuth_<=198) {
			startingSeat_=5;
		} else if (deltaAzimuth_>198&&deltaAzimuth_<=230) {
			startingSeat_=4;
		} else if (deltaAzimuth_>230&&deltaAzimuth_<=310) {
			startingSeat_=3;
		} else if (deltaAzimuth_>310&&deltaAzimuth_<=342) {
			startingSeat_=2;
		} else {
			startingSeat_=1;
		}
		return startingSeat_;
	}

	public void notifyAtLobby() {
		playerSlot.startFlashing();
	}

	public void removePlayer() {
		if (player!=null) {
			player=null;
		}
	}

	public boolean checkInOffscreenRegion(float px,float py) {
		boolean inOffscreenRegion=false;
		if (rotation==0) {
			if ((Math.abs(x-px)<radiusX)&&py<yScreenEdge) {
				inOffscreenRegion=true;
			}
		} else if (rotation==-90) {
			if ((Math.abs(y-py)<radiusX)&&px<xScreenEdge) {
				inOffscreenRegion=true;
			}
		} else if (rotation==180) {
			if ((Math.abs(x-px)<radiusX)&&py>yScreenEdge) {
				inOffscreenRegion=true;
			}
		} else if (rotation==90) {
			if ((Math.abs(y-py)<radiusX)&&px>xScreenEdge) {
				inOffscreenRegion=true;
			}
		}
		return inOffscreenRegion;
	}
}
