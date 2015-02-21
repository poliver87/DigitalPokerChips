package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class ForegroundGestureInput implements GestureListener {

	ForegroundLayer mFL;
	
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		velocityY*=-1;
		boolean consumed=false;
		
		return consumed;
	}
	
	public void setScreen(ForegroundLayer mFL_) {
		mFL=mFL_;
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {return false;}
	@Override
	public boolean tap(float x, float y, int count, int button) {return false;}
	@Override
	public boolean longPress(float x, float y) {return false;}
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {return false;}
	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}
	@Override
	public boolean pinch(Vector2 IP1, Vector2 IP2,
			Vector2 P1, Vector2 P2) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

}
