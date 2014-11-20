package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class WorldGestureInput implements GestureListener {

	WorldLayer mWL;
	
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		velocityY*=-1;
		boolean consumed=false;
 
		
		if (mWL.table.animationState==Table.ANIM_SELECT_WINNER&&
				mWL.table.pots.size()>0&&mWL.table.pots.get(mWL.table.displayedPotIndex).potStack.get(0).isTouched) {
			consumed=true;
			mWL.table.pots.get(mWL.table.displayedPotIndex).potStack.get(0).isTouched=false;
			if (Math.abs(velocityX)>mWL.limChipFlingVel||Math.abs(velocityY)>mWL.limChipFlingVel) {
				mWL.table.pots.get(mWL.table.displayedPotIndex).potStack.setVelocityX(velocityX*0.8f);
				mWL.table.pots.get(mWL.table.displayedPotIndex).potStack.setVelocityY(velocityY*0.8f);
			}
		}
		
		if (mWL.thisPlayer.betStack.size()>0&&mWL.thisPlayer.betStack.get(0).isTouched) {
			consumed=true;
			mWL.thisPlayer.betStack.get(0).isTouched=false;
			if (Math.abs(velocityY)>mWL.limChipFlingVel) {
				mWL.thisPlayer.betStack.setVelocityY(velocityY*0.8f);
			}
		}
		
		if (mWL.thisPlayer.pickedUpChip!=null&&mWL.thisPlayer.pickedUpChip.isTouched) {
			if (Math.sqrt(velocityY*velocityY+velocityX*velocityX)>mWL.limChipFlingVel) {
				Vector2 flingVel=new Vector2(velocityX,velocityY);
				Vector2 toBetStack=new Vector2(mWL.thisPlayer.betStack.getX()-mWL.thisPlayer.pickedUpChip.x,
						mWL.thisPlayer.betStack.getTopY()-mWL.thisPlayer.pickedUpChip.getProjectedY());
				float flingAngle=flingVel.angle();
				float toBetStackAngle=toBetStack.angle();
				float deltaAngle=Math.abs(toBetStackAngle-flingAngle);
				deltaAngle=deltaAngle>180?360-deltaAngle:deltaAngle;
				if (deltaAngle<70) {
					consumed=true;
					mWL.thisPlayer.pickedUpChip.isTouched=false;
					mWL.thisPlayer.doPickedUpChipFlung();
				}
 			}
		}
		return consumed;
	}
	
	public void setScreen(WorldLayer mWL_) {
		mWL=mWL_;
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
		return false;
	}

}
