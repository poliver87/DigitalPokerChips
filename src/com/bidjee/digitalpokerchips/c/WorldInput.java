package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.m.ChipCase;
import com.bidjee.digitalpokerchips.m.Player;
import com.bidjee.digitalpokerchips.m.ScreenState;
import com.bidjee.util.Logger;

public class WorldInput implements InputProcessor {
	
	public static final String LOG_TAG = "DPCInput";
	
	static final int TYPING_NOTHING = 0;
	public static final int TYPING_TABLE_NAME = 2;
	
	WorldLayer mWL;
	
	private int typingFocus;
	Vector2 deltaTouch=new Vector2();
	Vector2 lastTouch=new Vector2();
	
	public WorldInput() {
		typingFocus=TYPING_NOTHING;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		if (typingFocus==TYPING_TABLE_NAME) {
			if (character=='\b') {
				//mWL.table.tableNameField.backspace();
			} else if (character=='\n') {
				mWL.table.tableNameDone();
			} else if ((int)(character)!=0) {
				//mWL.table.tableNameField.append(""+character);
			}
		}
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (pointer==0&&mWL.game.screenState==ScreenState.GAMEPLAY) {
			float touchX=mWL.worldRenderer.xTouchScreenToWorld(screenX);
			float touchY=mWL.worldRenderer.yTouchScreenToWorld(screenY);
			Logger.log(LOG_TAG,"touchDown("+touchX+","+touchY+")");
			lastTouch.set(touchX,touchY);
			if (mWL.cameraDestination==mWL.camPosHome) {
				if (mWL.homeDeviceAnimation.hostSprite.getTouchable()&&
						mWL.homeDeviceAnimation.hostSprite.pointContained(touchX, touchY)) {
					mWL.homeDeviceAnimation.hostSprite.setIsTouched(true);
					mWL.game.mFL.homeUIAnimation.hostButton.setIsTouched(true);
				}
				if (mWL.homeDeviceAnimation.p1Sprite.getTouchable()&&
						mWL.homeDeviceAnimation.p1Sprite.pointContained(touchX, touchY)) {
					mWL.homeDeviceAnimation.p1Sprite.setIsTouched(true);
					mWL.game.mFL.homeUIAnimation.joinButton.setIsTouched(true);
				}
			} else if (mWL.cameraDestination==mWL.camPosPlayer) {
				// check if chip stacks are touched
				for (int chip=0;chip<ChipCase.CHIP_TYPES;chip++) {
					if (mWL.thisPlayer.mainStacks[chip].size()>0&&mWL.thisPlayer.pickedUpChip==null&&
							mWL.thisPlayer.cancellingStack.size()==0&&mWL.thisPlayer.cancelStack.size()==0&&
							mWL.thisPlayer.mainStacks[chip].getOpacity()==1) {
						if (mWL.thisPlayer.mainStacks[chip].getLastRendered().pointContained(touchX, touchY)) {
							deltaTouch.x=touchX-mWL.thisPlayer.mainStacks[chip].getLast().x;
							deltaTouch.y=touchY-mWL.thisPlayer.mainStacks[chip].getLast().y;
							mWL.thisPlayer.setPickedUpChip(chip,touchX-deltaTouch.x,touchY-deltaTouch.y);
							mWL.thisPlayer.pickedUpChip.isTouched=true;
						}
					}
				}
				if (mWL.thisPlayer.pickedUpChip!=null&&mWL.thisPlayer.pickedUpChip.isTouched) {
				// check if the bet stack is touched
				} else if (mWL.thisPlayer.betStack.size()>0&&mWL.thisPlayer.bettingStack.size()==0&&
						mWL.thisPlayer.pickedUpChip==null&&mWL.thisPlayer.betStack.pointContained(touchX,touchY)&&
						mWL.thisPlayer.betStack.getOpacity()==1) {
					deltaTouch.y=touchY-mWL.thisPlayer.betStack.getY();
					mWL.thisPlayer.betStack.get(0).isTouched=true;
				}
				if (mWL.thisPlayer.checkButton.getTouchable()&&
						mWL.thisPlayer.checkButton.pointContained(touchX, touchY)) {
					mWL.thisPlayer.checkButton.setIsTouched(true);
				}
			} else if (mWL.cameraDestination==mWL.camPosTable) {
				if (mWL.table.gameState==Table.STATE_LOBBY||mWL.table.gameState==Table.STATE_GAME_REARRANGE) {
					if (mWL.table.pickedUpPlayer==null) {
						// TODO deal with players animating, might not be a problem
						
						// check if a player is touched
						for (int seat_=0;seat_<Table.NUM_SEATS;seat_++) {
							Player thisPlayer_=mWL.table.seats[seat_].player;
							if (thisPlayer_!=null) {
								// TODO change this to be based on the connectionBlob so it can't be moved unless connectionBlob clickable
								if (thisPlayer_.pointContained(touchX,touchY)) {
									mWL.table.setPickedUpPlayer(seat_);
									// use instance variable so rotation can be applied
									deltaTouch.x=touchX-thisPlayer_.x;
									deltaTouch.y=touchY-thisPlayer_.y;
									break;
								}
							}
						}
					}
				} else if (mWL.table.gameState==Table.STATE_SELECTING_DEALER) {
					for (int i=0;i<Table.NUM_SEATS;i++) {
						if (mWL.table.seats[i].player!=null&&mWL.table.seats[i].player.getTouchable()) {
							if (mWL.table.seats[i].player.pointContained(touchX,touchY)) {
								mWL.table.seats[i].player.setIsTouched(true);
								break;
							}
						}
					}
				} else if (mWL.table.gameState==Table.STATE_GAME) {
					if (mWL.table.animationState==Table.ANIM_SELECT_WINNER) {
						if (mWL.table.pots.get(mWL.table.pots.size()-1).potStack.pointContained(touchX,touchY)) {
							deltaTouch.y=touchY-mWL.table.pots.get(mWL.table.pots.size()-1).potStack.getY();
							deltaTouch.x=touchX-mWL.table.pots.get(mWL.table.pots.size()-1).potStack.getX();
							mWL.table.pots.get(mWL.table.pots.size()-1).potStack.get(0).isTouched=true;
							mWL.table.pots.get(mWL.table.pots.size()-1).potStack.setVelocityX(0);
							mWL.table.pots.get(mWL.table.pots.size()-1).potStack.setVelocityY(0);
						}
					}
					for (int i=0;i<Table.NUM_SEATS;i++) {
						if (mWL.table.seats[i].player!=null&&mWL.table.seats[i].player.getTouchable()) {
							if (mWL.table.seats[i].player.pointContained(touchX,touchY)) {
								mWL.table.seats[i].player.setIsTouched(true);
								break;
							}
						}
						if (mWL.table.seats[i].undoButton.getTouchable()&&
							mWL.table.seats[i].undoButton.pointContained(touchX,touchY)) {
							mWL.table.seats[i].undoButton.setIsTouched(true);
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (pointer==0&&mWL.game.screenState==ScreenState.GAMEPLAY) {
			if (mWL.homeDeviceAnimation.hostSprite.getIsTouched()) {
				mWL.homeDeviceAnimation.hostSprite.setIsTouched(false);
			}
			if (mWL.homeDeviceAnimation.p1Sprite.getIsTouched()) {
				mWL.homeDeviceAnimation.p1Sprite.setIsTouched(false);
			}
			if (mWL.thisPlayer.pickedUpChip!=null&&mWL.thisPlayer.pickedUpChip.isTouched) {
				mWL.thisPlayer.pickedUpChip.isTouched=false;
				mWL.thisPlayer.doPickedUpChipDropped();
			}
			if (mWL.thisPlayer.betStack.size()>0&&mWL.thisPlayer.betStack.get(0).isTouched) {
				mWL.thisPlayer.betStack.get(0).isTouched=false;
			}
			if (mWL.thisPlayer.checkButton.getIsTouched()) {
				mWL.thisPlayer.checkButton.setIsTouched(false);
				mWL.thisPlayer.doCheck();
			}
			if (mWL.table.gameState==Table.STATE_LOBBY||mWL.table.gameState==Table.STATE_GAME_REARRANGE) {
				if (mWL.table.pickedUpPlayer!=null) {
					if (mWL.table.pickedUpPlayer.getIsTouched()) {
						mWL.table.pickedUpPlayer.setIsTouched(false);
						mWL.table.pickedUpPlayerDropped();
					}
				}
			} else if (mWL.table.gameState==Table.STATE_SELECTING_DEALER) {
				for (int i=0;i<Table.NUM_SEATS;i++) {
					if (mWL.table.seats[i].player!=null&&mWL.table.seats[i].player.getIsTouched()) {
						mWL.table.seats[i].player.setIsTouched(false);
						mWL.table.dealerSelected(i);
					}
				}
			} else if (mWL.table.gameState==Table.STATE_GAME) {
				for (int i=0;i<Table.NUM_SEATS;i++) {
					if (mWL.table.seats[i].player!=null&&mWL.table.seats[i].player.getIsTouched()) {
						mWL.table.seats[i].player.setIsTouched(false);
						mWL.table.playerClicked(i);
					}
					if (mWL.table.seats[i].undoButton.getIsTouched()) {
						mWL.table.seats[i].undoButton.setIsTouched(false);
						mWL.table.undoClicked(i);
					}
				}
				if (mWL.table.animationState==Table.ANIM_SELECT_WINNER) {
					if (mWL.table.pots.size()>0&&mWL.table.pots.get(mWL.table.displayedPotIndex).potStack.get(0).isTouched) {
						mWL.table.pots.get(mWL.table.displayedPotIndex).potStack.get(0).isTouched=false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (pointer==0&&mWL.game.screenState==ScreenState.GAMEPLAY) {
			float touchX=mWL.worldRenderer.xTouchScreenToWorld(screenX);
			float touchY=mWL.worldRenderer.yTouchScreenToWorld(screenY);
			lastTouch.set(touchX,touchY);
			if (mWL.homeDeviceAnimation.hostSprite.getIsTouched()) {
				if (!mWL.game.mFL.homeUIAnimation.hostButton.getIsTouched()&&
						!mWL.homeDeviceAnimation.hostSprite.pointContained(touchX, touchY)) {
					mWL.homeDeviceAnimation.hostSprite.setIsTouched(false);
					mWL.game.mFL.homeUIAnimation.hostButton.setIsTouched(false);
				} else {
					mWL.game.mFL.homeUIAnimation.hostButton.setIsTouched(true);
				}
			}
			if (mWL.homeDeviceAnimation.p1Sprite.getIsTouched()) {
				if (!mWL.game.mFL.homeUIAnimation.joinButton.getIsTouched()&&
						!mWL.homeDeviceAnimation.p1Sprite.pointContained(touchX, touchY)) {
					mWL.homeDeviceAnimation.p1Sprite.setIsTouched(false);
					mWL.game.mFL.homeUIAnimation.joinButton.setIsTouched(false);
				} else {
					mWL.game.mFL.homeUIAnimation.joinButton.setIsTouched(true);
				}
			}
			if (mWL.thisPlayer.pickedUpChip!=null&&mWL.thisPlayer.pickedUpChip.isTouched) {
				mWL.thisPlayer.pickedUpChip.setDest(touchX-deltaTouch.x,touchY-deltaTouch.y,mWL.thisPlayer.pickedUpChip.getDestZ());
			}
			// move the bet stack
			else if (mWL.thisPlayer.betStack.size()>0&&mWL.thisPlayer.betStack.get(0).isTouched) {
				mWL.thisPlayer.betStack.setY(touchY-deltaTouch.y);
			}
			if (mWL.thisPlayer.checkButton.getIsTouched()) {
				if (!mWL.thisPlayer.checkButton.pointContained(touchX, touchY)) {
					mWL.thisPlayer.checkButton.setIsTouched(false);
				}
			}
			if (mWL.table.gameState==Table.STATE_LOBBY||mWL.table.gameState==Table.STATE_GAME_REARRANGE) {
				Player pickedUpPlayer_=mWL.table.pickedUpPlayer;
				if (pickedUpPlayer_!=null) {
					if (pickedUpPlayer_.getIsTouched()) {
						mWL.table.pickedUpPlayerDragged(touchX-deltaTouch.x,touchY-deltaTouch.y);
					}
				}
			} else if (mWL.table.gameState==Table.STATE_SELECTING_DEALER) {
				for (int i=0;i<Table.NUM_SEATS;i++) {
					if (mWL.table.seats[i].player!=null&&mWL.table.seats[i].player.getIsTouched()) {
						if (!mWL.table.seats[i].player.pointContained(touchX,touchY)) {
							mWL.table.seats[i].player.setIsTouched(false);
						}
					}
				}
			} else if (mWL.table.gameState==Table.STATE_GAME) {
				for (int i=0;i<Table.NUM_SEATS;i++) {
					if (mWL.table.seats[i].player!=null&&mWL.table.seats[i].player.getIsTouched()) {
						if (!mWL.table.seats[i].player.pointContained(touchX,touchY)) {
							mWL.table.seats[i].player.setIsTouched(false);
							break;
						}
					}
					if (mWL.table.seats[i].undoButton.getIsTouched()) {
						if (!mWL.table.seats[i].undoButton.pointContained(touchX, touchY)) {
							mWL.table.seats[i].undoButton.setIsTouched(false);
						}
					}
				}
				if (mWL.table.animationState==Table.ANIM_SELECT_WINNER) {
					if (mWL.table.pots.get(mWL.table.displayedPotIndex).potStack.get(0).isTouched) {
						mWL.table.updatePotRotation();
						mWL.table.pots.get(mWL.table.displayedPotIndex).potStack.setY(touchY-deltaTouch.y);
						mWL.table.pots.get(mWL.table.displayedPotIndex).potStack.setX(touchX-deltaTouch.x);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	public void setScreen(WorldLayer mWL_) {
		mWL=mWL_;
	}

	public void setTypingFocus(int typingFocus) {
		Logger.log(LOG_TAG,"setTypingFocus("+typingFocus+")");
		this.typingFocus=typingFocus;
	}

}
