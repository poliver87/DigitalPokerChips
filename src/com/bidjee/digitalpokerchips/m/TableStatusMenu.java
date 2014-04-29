package com.bidjee.digitalpokerchips.m;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.bidjee.digitalpokerchips.c.DPCGame;
import com.bidjee.digitalpokerchips.c.ForegroundInput;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;
import com.bidjee.digitalpokerchips.c.WorldLayer;

public class TableStatusMenu {

	static final int STATE_NONE = 0;
	static final int STATE_SHOW = 1;
	static final int STATE_OPENING = 2;
	static final int STATE_OPEN = 3;
	static final int STATE_CLOSING = 4;
	static final int STATE_CLOSED = 5;
	static final int STATE_HIDE = 6;
	
	private int animationState;
	public String nudgeHostName;
	
	public Button handle;
	public Button leaveButton;
	public TextLabel tableName;
	public Button bellButton;
	
	public ArrayList<PlayerEntry> playerList;

	public float x;
	public float y;
	public int radiusX;
	public int radiusY;
	public float opacity;
	
	float xHidden;
	float xOpen;
	float xClosed;
	public float xPlayerEntryNameLeft;
	public float xPlayerEntryAmountLeft;
	public float xBellButtonCentre;
	public float yPlayerEntryBottom;
	public float yPlayerEntryPitch;
	
	ForegroundLayer mFL;
	
	public TableStatusMenu(ForegroundLayer mFL) {
		this.mFL=mFL;
		this.opacity=0;
		handle=new Button(false,0,"");
		tableName=new TextLabel("",0,false,1,false);
		tableName.bold=true;
		tableName.bodyColor=TextLabel.whiteColor;
		leaveButton=new Button(false,0,"Leave");
		bellButton=new Button(false,0,"");
		bellButton.setTouchAreaMultiplier(1.5f);
		playerList=new ArrayList<PlayerEntry>();
	}

	public void setDimensions(int radiusX,int radiusY) {
		this.radiusX=radiusX;
		this.radiusY=radiusY;
		tableName.setText(WorldLayer.NAME_MEASURE);
		tableName.setMaxDimensions((int)(radiusX*0.9f),(int)(radiusY*0.15f));
		tableName.setTextSizeToMax();
		tableName.setText("");
		handle.setDimensions((int)(radiusY*0.12f),(int)(radiusY*0.3f));
		// TODO set up vertical text sizing and rendering
		leaveButton.setDimensions((int)(radiusX*0.9f),(int)(radiusY*0.14f));
		TextLabel sizeLabel=new TextLabel(WorldLayer.NAME_MEASURE,0,false,0,false);
		sizeLabel.setMaxDimensions((int)(radiusX*0.6f),(int)(radiusY*0.1f));
		PlayerEntry.playerEntryTextSize=DPCGame.textFactory.getMaxTextSize(sizeLabel);
		yPlayerEntryPitch=sizeLabel.maxRadiusY*2;
		bellButton.setDimensions((int)(radiusY*0.11f),(int)(radiusY*0.11f));
	}
	
	public void setPosition(float x,float y) {
		this.x=x;
		this.y=y;
		xOpen=x;
		xHidden=x+radiusX*3;
		xClosed=x+radiusX*2;
		handle.setPosition(x-radiusX-handle.radiusX*0.99f,y+radiusY*0.6f);
		leaveButton.setPosition(x,y-radiusY*0.8f);
		xPlayerEntryNameLeft=x-radiusX*0.9f;
		xPlayerEntryAmountLeft=x+radiusX*0.1f;
		xBellButtonCentre=x+radiusX*0.8f;
		bellButton.setPosition(xBellButtonCentre,0);
		tableName.setPosition(x,y+radiusY*0.85f);
		yPlayerEntryBottom=tableName.y-tableName.radiusY*1.5f-yPlayerEntryPitch;
	}
	
	public void scalePosition(float scaleX,float scaleY) {
		
	}
	
	public void animate(float delta) {
		if (animationState==STATE_OPENING) {
			if (Math.abs(xOpen-x)<3) {
				translateX(xOpen-x);
				animationState=STATE_OPEN;
			} else {
				translateX(delta*8*(xOpen-x));
			}
		} else if (animationState==STATE_CLOSING) {
			if (Math.abs(xClosed-x)<4) {
				translateX(xClosed-x);
				animationState=STATE_CLOSED;
			} else {
				translateX(delta*8*(xClosed-x));
			}
		} else if (animationState==STATE_SHOW) {
			if (Math.abs(xClosed-x)<4) {
				translateX(xClosed-x);
				animationState=STATE_CLOSED;
				handle.setTouchable(true);
			} else {
				translateX(delta*8*(xClosed-x));
			}
		} else if (animationState==STATE_HIDE) {
			if (Math.abs(xHidden-x)<4) {
				disappear();
			} else {
				translateX(delta*8*(xHidden-x));
			}
		}
	}
	
	public void setTableName(String tableName) {
		// update the label and load the texture
		this.tableName.setText(tableName);
		this.tableName.loadTexture();
	}
	
	public void show() {
		opacity=1;
		animationState=STATE_SHOW;
		handle.setTouchable(true);
		translateX(xHidden-x);
	}
	
	public void remove() {
		handle.setTouchable(false);
		animationState=STATE_HIDE;
	}
	
	public void disappear() {
		animationState=STATE_NONE;
		handle.setTouchable(false);
		leaveButton.setTouchable(false);
		for (PlayerEntry playerEntry:playerList) {
			playerEntry.dispose();
		}
		disableNudge();
		playerList.clear();
		opacity=0;
	}
	
	public void open() {
		animationState=STATE_OPENING;
		leaveButton.setTouchable(true);
		mFL.input.pushTouchFocus(ForegroundInput.TOUCH_TABLE_STATUS);
	}
	
	public void close() {
		animationState=STATE_CLOSING;
		leaveButton.setTouchable(false);
		mFL.input.popTouchFocus(ForegroundInput.TOUCH_TABLE_STATUS);
	}
	
	public void translateX(float deltaX) {
		x+=deltaX;
		handle.translateX(deltaX);
		leaveButton.translateX(deltaX);
		tableName.x+=deltaX;
		xPlayerEntryNameLeft+=deltaX;
		xPlayerEntryAmountLeft+=deltaX;
		xBellButtonCentre+=deltaX;
		bellButton.setPosition(xBellButtonCentre,bellButton.y);
	}
	
	public boolean pointContained(float touchX, float touchY) {
		return (Math.abs(touchX-x)<radiusX&&Math.abs(touchY-y)<radiusY);
	}
	
	public void handleDropped() {
		// calculate whether it should open or close from here
		if (Math.abs(x-xOpen)<Math.abs(x-xClosed)) {
			open();
		} else {
			close();
		}
	}
	
	public void syncStatusMenu(ArrayList<PlayerEntry> playerList) {
		this.playerList.clear();
		this.playerList=playerList;
		Collections.sort(playerList);
	}
	
	public void enableNudge(String hostName) {
		for (int i=0;i<playerList.size();i++) {
			if (playerList.get(i).hostName.equals(hostName)) {
				bellButton.y=yPlayerEntryBottom-i*yPlayerEntryPitch+playerList.get(i).name.radiusY;
				bellButton.opacity=1;
				bellButton.setTouchable(true);
				nudgeHostName=hostName;
				break;
			}
		}
	}
	
	public void disableNudge() {
		bellButton.opacity=0;
		bellButton.setTouchable(false);
	}
	
}
