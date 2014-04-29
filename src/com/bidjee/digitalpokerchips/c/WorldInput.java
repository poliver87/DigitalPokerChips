package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.m.ChipCase;
import com.bidjee.digitalpokerchips.m.Player;
import com.bidjee.digitalpokerchips.m.ScreenState;

public class WorldInput implements InputProcessor {
	
	static final int TYPING_NOTHING = 0;
	static final int TYPING_PLAYER_NAME = 1;
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
		if (typingFocus==TYPING_PLAYER_NAME) {
			mWL.thisPlayer.appendName(character);
		} else if (typingFocus==TYPING_TABLE_NAME) {
			if (character=='\b') {
				mWL.table.tableNameField.backspace();
			} else if (character=='\n') {
				mWL.table.tableNameDone();
			} else if ((int)(character)!=0) {
				mWL.table.tableNameField.append(""+character);
			}
		}
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (pointer==0&&mWL.game.screenState==ScreenState.GAMEPLAY) {
			float touchX=mWL.worldRenderer.xTouchScreenToWorld(screenX);
			float touchY=mWL.worldRenderer.yTouchScreenToWorld(screenY);
			lastTouch.set(touchX,touchY);
			if (mWL.cameraDestination==mWL.camPosHome) {
				;
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
				} else if (mWL.thisPlayer.plaque.getTouchable()&&
						mWL.thisPlayer.plaque.pointContained(touchX, touchY)) {
					mWL.thisPlayer.plaqueTouched();
				}
				if (mWL.thisPlayer.checkButton.getTouchable()&&
						mWL.thisPlayer.checkButton.pointContained(touchX, touchY)) {
					mWL.thisPlayer.checkButton.setIsTouched(true);
				}
			} else if (mWL.cameraDestination==mWL.camPosPlayersName) {
				if (mWL.thisPlayer.plaque.pointContained(touchX, touchY)) {
					Gdx.input.setOnscreenKeyboardVisible(true);
				}
			} else if (mWL.cameraDestination==mWL.camPosTableName) {
				if (mWL.table.tableNameField.pointContained(touchX, touchY)) {
					Gdx.input.setOnscreenKeyboardVisible(true);
				}
			} else if (mWL.cameraDestination==mWL.camPosTable) {
				if (mWL.table.gameState==Table.STATE_LOBBY) {
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
			if (mWL.table.gameState==Table.STATE_LOBBY) {
				if (mWL.table.pickedUpPlayer!=null) {
					if (mWL.table.pickedUpPlayer.getIsTouched()) {
						mWL.table.pickedUpPlayer.setIsTouched(false);
						mWL.table.pickedUpPlayerDropped();
					}
				}
			}
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
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (pointer==0&&mWL.game.screenState==ScreenState.GAMEPLAY) {
			float touchX=mWL.worldRenderer.xTouchScreenToWorld(screenX);
			float touchY=mWL.worldRenderer.yTouchScreenToWorld(screenY);
			lastTouch.set(touchX,touchY);
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
			if (mWL.table.gameState==Table.STATE_LOBBY) {
				Player pickedUpPlayer_=mWL.table.pickedUpPlayer;
				if (pickedUpPlayer_!=null) {
					if (pickedUpPlayer_.getIsTouched()) {
						pickedUpPlayer_.setX(touchX-deltaTouch.x);
						pickedUpPlayer_.setY(touchY-deltaTouch.y);
						mWL.table.rotateAndProjectPlayer(pickedUpPlayer_);
						mWL.table.calculateClosestSeatToPickedUp();
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
		this.typingFocus=typingFocus;
	}

}
