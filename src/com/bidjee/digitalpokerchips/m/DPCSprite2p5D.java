package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector3;


public class DPCSprite2p5D extends DPCSprite {
	
	public float z;
	public float zDest;
	
	public DPCSprite2p5D() {
		super();
		z=0;
		zDest=0;
	}
	
	public void setPosition(float x,float y, float z) {
		super.setPosition(x,y);
		this.z=z;
	}
	
	public void setPosition(Vector3 pos) {
		setPosition(pos.x, pos.y, pos.z);
	}
	
	public void setDest(float xDest,float yDest,float zDest,int holdoff) {
		super.setDest(xDest, yDest, holdoff);
		this.zDest=zDest;
	}
	
	public void setDest(float xDest,float yDest,float zDest) {
		super.setDest(xDest, yDest);
		this.zDest=zDest;
	}
	
	public void setDest(Vector3 dest) {
		setDest(dest.x,dest.y,dest.z);
	}
	
	
	public boolean pointContained(float x,float y) {
		boolean contained=false;
		contained=super.pointContained(x, y);
		return contained;
	}
	
	public void animate(float delta) {
		boolean atDestBeforeXY=atDest;
		super.animate(delta);
		boolean atDestAfterXY=atDest;
		if (!atDestBeforeXY) {
			atDest=false;
			if (moveHoldoffTimer>=moveHoldoffDuration) {
				float zNew=0;
				if (moveFunction==MOVE_EASE_IN) {
					if (Math.abs(z-zDest)<1) {
						atDest=atDestAfterXY;
						z=zDest;
					} else {
						float timeFactor = delta*moveSpeed;
						zNew=(float)(z-timeFactor*(z-zDest));
						this.setPosition(x,y,zNew);
					}
				} else if (moveFunction==MOVE_LINEAR) {
					
				} else if (moveFunction==MOVE_BOUNCE) {

				}
			}
		}
	}
	
}
