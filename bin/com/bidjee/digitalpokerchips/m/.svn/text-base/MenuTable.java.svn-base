package com.bidjee.digitalpokerchips.m;

import com.bidjee.digitalpokerchips.i.IPlayerNetwork;

public class MenuTable {
	
	float x;
	protected float y;
	float xOpen;
	float yOpen;
	float xClosed;
	float yClosed;
	float radiusX;
	float radiusY;
	float radiusXOpen;
	float radiusYOpen;
	float radiusXClosed;
	float radiusYClosed;
	float radiusXLobby;
	float radiusYLobby;
	
	private float yStartClose;
	private float radiusYStartClose;
	private float xStartClose;
	
	boolean isTouched;
	
	boolean doOpen;
	boolean isOpen;
	boolean isClosed;
	boolean doLobby;
	boolean undoLobby;
	boolean isLobby;
	
	protected float xFirstColumn;
	protected float yFirstRow;
	protected float xColumnOffset;
	protected float yRowOffset;
	protected int buttonRadius;
	
	protected int currentScreen;
	int savedScreen;
	
	int lineWidth;
	float linesX1;
	float linesX2;
	float linesX3;
	float linesX4;
	float linesY1;
	float linesY2;
	float linesY3;
	float linesY4;
	
	public MenuTable() {
		isTouched=false;
		doOpen=false;
		isOpen=false;
		isClosed=true;
		isLobby=false;
	}
	
	public void setDimensions(float radiusXOpen_,float radiusYOpen_,
			float radiusXClosed_,float radiusYClosed_,
			float radiusXLobby_,float radiusYLobby_) {
		radiusXOpen=radiusXOpen_;
		radiusYOpen=radiusYOpen_;
		radiusXClosed=radiusXClosed_;
		radiusYClosed=radiusYClosed_;
		radiusXLobby=radiusXLobby_;
		radiusYLobby=radiusYLobby_;
		buttonRadius=(int) (radiusYOpen*0.25f);
		if (isOpen) {
			radiusX=radiusXOpen;
			radiusY=radiusYOpen;
		} else if (isClosed) {
			radiusX=radiusXClosed;
			radiusY=radiusYClosed;
		} else if (isLobby) {
			radiusX=radiusXLobby;
			radiusY=radiusYLobby;
		}
	}
	
	public void setPosition(float xOpen_,float yOpen_,float xClosed_,float yClosed_,float scaleX_,float scaleY_) {
		xOpen=xOpen_;
		yOpen=yOpen_;
		xClosed=xClosed_;
		yClosed=yClosed_;
		if (isOpen) {
			x=xOpen;
			y=yOpen;
		} else if (isClosed) {
			x=xClosed;
			y=yClosed;
		} else if (isLobby) {
			x=xOpen;
			y=yOpen;
		} else {
			// scale everything
			x*=scaleX_;
			y*=scaleY_;
			float travelRatio=(y-yClosed)/(yOpen-yClosed);
			radiusY=travelRatio*(radiusYOpen-radiusYClosed)+radiusYClosed;
			radiusX=radiusY*(radiusXOpen/radiusYOpen);
		}
		xFirstColumn=xOpen-radiusXOpen*0.45f;
		yFirstRow=yOpen-radiusYOpen*0.35f;
		xColumnOffset=xOpen-xFirstColumn;
		yRowOffset=radiusYOpen*0.85f;
		lineWidth=Math.max(Math.round(radiusYOpen*0.014f),1);
		linesX1=xOpen-radiusXOpen*0.7f;
		linesX4=xOpen+radiusXOpen*0.7f;
		linesX2=linesX1+(linesX4-linesX1)*0.3333f-lineWidth*0.5f;
		linesX3=linesX1+(linesX4-linesX1)*0.6666f-lineWidth*0.5f;
		linesY1=yOpen-radiusYOpen*0.78f;
		linesY2=yOpen-radiusYOpen*0.07f;
		linesY3=yOpen+radiusYOpen*0.07f;
		linesY4=yOpen+radiusYOpen*0.78f;
	}
	
	public void setToLobby() {
		doOpen=false;
		isOpen=false;
		isClosed=false;
		doLobby=false;
		isLobby=true;
		x=xOpen;
		y=yOpen;
		radiusX=radiusXLobby;
		radiusY=radiusYLobby;
	}
	
	public void setRadii(float radiusX_,float radiusY_) {
		radiusX=radiusX_;
		radiusY=radiusY_;
	}
	
	public void setPosition(float x_,float y_) {
		x=x_;
		y=y_;
	}
	
	public boolean pointContained(float x_,float y_) {
		boolean contained=false;
		float xCircleLeft=x-radiusX*0.5f;
		float xCircleRight=x+radiusX*0.5f;
		// check if center rectangular area is touched
		if (Math.abs(x_-x)<radiusX*0.51f&&
				Math.abs(y_-y)<radiusY) {
			contained=true;
		}
		// check if left circle is touched
		if ((x_-xCircleLeft)*(x_-xCircleLeft)+(y_-y)*
				(y_-y)<radiusY*radiusY) {
			contained=true;
		}
		// check if right circle is touched
		if ((x_-xCircleRight)*(x_-xCircleRight)+(y_-y)*
				(y_-y)<radiusY*radiusY) {
			contained=true;
		}
		return contained;
	}
	
	public void open(IPlayerNetwork iPN_) {
		if (doLobby||isLobby) {
			undoLobby=true;
		} else {
			doOpen=true;
		}
		isClosed=false;
		isLobby=false;
		doLobby=false;
		// initialize everything
		setCurrentScreen(currentScreen,iPN_);
	}
	
	public void close() {
		yStartClose=y;
		radiusYStartClose=radiusY;
		xStartClose=x;
		doOpen=false;
		isOpen=false;
		isLobby=false;
		doLobby=false;
	}
	
	public int getCurrentScreen() {
		return currentScreen;
	}
	
	public void setCurrentScreen(int cs_,IPlayerNetwork iPN_) {
		currentScreen=cs_;
	}
	
	public void saveCurrentScreen() {
		savedScreen=currentScreen;
	}
	
	public boolean animateToDest(float delta) {
		boolean arrived_=false;
		if (doOpen) {
			if (Math.abs(y-yOpen)<=1&&
					Math.abs(x-xOpen)<=1) {
				isOpen=true;
				doOpen=false;
				arrived_=true;
				y=yOpen;
				x=xOpen;
				radiusX=radiusXOpen;
				radiusY=radiusYOpen;
			} else {
				y=(float)(y-Math.min(delta*8*(y-yOpen),-1));
				float travelRatio=(y-yClosed)/(yOpen-yClosed);
				radiusY=travelRatio*(radiusYOpen-radiusYClosed)+radiusYClosed;
				radiusX=radiusY*(radiusXOpen/radiusYOpen);
				x=travelRatio*(xOpen-xClosed)+xClosed;
			}
		} else if (doLobby) {
			if (Math.abs(radiusY-radiusYLobby)<=1) {
				doLobby=false;
				isLobby=true;
				arrived_=true;
				radiusX=radiusXLobby;
				radiusY=radiusYLobby;
			} else {
				radiusY=radiusY-delta*4*Math.max(radiusY-radiusYLobby,1);
				radiusX=radiusY*(radiusXLobby/radiusYLobby);
			}
		} else if (undoLobby) {
			if (Math.abs(radiusY-radiusYOpen)<=1) {
				undoLobby=false;
				isOpen=true;
				arrived_=true;
				radiusX=radiusXOpen;
				radiusY=radiusYOpen;
			} else {
				radiusY=radiusY+delta*4*Math.max(radiusYOpen-radiusY,1);
				radiusX=radiusY*(radiusXLobby/radiusYLobby);
			}
		} else {
			if (Math.abs(y-yClosed)<=1&&
					Math.abs(x-xClosed)<=1) {
				isClosed=true;
				y=yClosed;
				x=xClosed;
				radiusX=radiusXClosed;
				radiusY=radiusYClosed;
				arrived_=true;
			} else {
				y=(float)(y-Math.max(delta*8*(y-yClosed),1));
				float travelRatio=(y-yClosed)/(yStartClose-yClosed);
				radiusY=travelRatio*(radiusYStartClose-radiusYClosed)+radiusYClosed;
				radiusX=radiusY*(radiusXClosed/radiusYClosed);
				x=travelRatio*(xStartClose-xClosed)+xClosed;
			}
		}
		return arrived_;
	}
	
	public void animateText(float delta) {}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y_) {
		y=y_;
	}
	
	public void translateY(float deltaY_) {
		y+=deltaY_;
		yOpen+=deltaY_;
		yClosed+=deltaY_;
		yFirstRow+=deltaY_;
		linesY1+=deltaY_;
		linesY2+=deltaY_;
		linesY3+=deltaY_;
		linesY4+=deltaY_;
	}
	
}

