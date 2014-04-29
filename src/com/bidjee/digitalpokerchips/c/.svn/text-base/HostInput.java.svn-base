package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.InputProcessor;

public class HostInput implements InputProcessor {
	
	static final int TOUCH_LOBBY = 0;
	static final int TOUCH_MAIN_GAME = 1;
	static final int TOUCH_MENU_TABLE = 2;
	static final int TOUCH_EXIT_PENDING = 3;
	
	int touchFocus;
	
	float deltaTouchX;
	float deltaTouchY;

	@Override
	public boolean keyDown(int keycode) {
		boolean handled=false;
		/*
		if (keycode==Keys.BACK) {
			if (touchFocus==TOUCH_LOBBY||touchFocus==TOUCH_MAIN_GAME) {
				mHS.openDestroyDialog();
			} else if (mHS.game.screenState==DPCGame.STATE_GAMEPLAY) {
				if (touchFocus==TOUCH_MENU_TABLE) {
					mHS.closeMenuTable();
					touchFocus=TOUCH_MAIN_GAME;
				} else if (touchFocus==TOUCH_EXIT_PENDING){
				} else {
					mHS.game.exitApp();
				}
			}
			
			handled=true;
		}
		*/
		return handled;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		/*
		if (pointer==0&&mHS.game.screenState==DPCGame.STATE_GAMEPLAY) {
			float touchX=screenX;
			float touchY=mHS.game.renderer.yViewToWorld(screenY);
			if (touchFocus==TOUCH_LOBBY&&mHS.dealerChip.velocityX==0&&mHS.dealerChip.velocityY==0) {
				if (mHS.pickedUpPlayer==null&&mHS.swapPlayer==null&&mHS.dealerChip.pointContained(touchX, touchY)) {
					mHS.dealerChip.setTouched(true,touchX,touchY);
					deltaTouchX=touchX-mHS.dealerChip.x;
					deltaTouchY=touchY-mHS.dealerChip.y;
				} else if (mHS.pickedUpPlayer==null&&mHS.swapPlayer==null) {
					for (int i=0;i<HostScreen.NUM_SEATS;i++) {
						if (mHS.seats[i].player!=null&&mHS.seats[i].pointContained(touchX,touchY)) {
							deltaTouchX=touchX-mHS.seats[i].player.getX();
							deltaTouchY=touchY-mHS.seats[i].player.getY();
							mHS.setPickedUpPlayer(i);
						}
					}
				}
				if (mHS.resumeButton.opacity==1&&mHS.resumeButton.pointContained(touchX,touchY)) {
					mHS.resumeButton.setIsTouched(true);
				}
			} else if (touchFocus==TOUCH_MAIN_GAME) {
				// check if menu table is touched
				mHS.menuTable.isTouched=mHS.menuTable.pointContained(touchX, touchY);
				if (mHS.gameStage==HostScreen.STAGE_SELECT_WINNER) {
					ChipStack currPotStack=mHS.pots.get(mHS.pots.size()-1).potStack;
					if (currPotStack.velocityX==0&&currPotStack.velocityY==0&&
							currPotStack.pointContained(touchX,touchY)) {
						currPotStack.setTouched(true,touchX,touchY);
						deltaTouchX=touchX-currPotStack.getX();
						deltaTouchY=touchY-currPotStack.getY();
					} else if (mHS.splitButton.pointContained(touchX, touchY)) {
						mHS.splitButton.setIsTouched(true);
					}
				} else if (mHS.gameStage==HostScreen.STAGE_SELECT_WINNERS_SPLIT) {
					if (mHS.cancelSplitButton.pointContained(touchX, touchY)) {
						mHS.cancelSplitButton.setIsTouched(true);
					}
					for (int i=0;i<HostScreen.NUM_SEATS;i++) {
						if (mHS.seats[i].player!=null&&
								mHS.pots.get(mHS.pots.size()-1).playersEntitled.contains(i)&&
								mHS.seats[i].pointContained(touchX,touchY)) {
							mHS.notifyPlayerSelected(i);
						}
					}
					if (mHS.countSelectedPlayers()>=2&&mHS.splitDoneButton.pointContained(touchX, touchY)) {
						mHS.splitDoneButton.setIsTouched(true);
					}
				} else {
					if (mHS.arrowRight.opacity==1&&mHS.arrowRight.pointContained(touchX, touchY)) {
						mHS.arrowRight.setIsTouched(true);
					}
					if (mHS.arrowLeft.opacity==1&&mHS.arrowLeft.pointContained(touchX, touchY)) {
						mHS.arrowLeft.setIsTouched(true);
					}
				}
			} else if (touchFocus==TOUCH_MENU_TABLE) {
				if (!mHS.menuTable.pointContained(touchX, touchY)) {
					touchFocus=TOUCH_MAIN_GAME;
					mHS.closeMenuTable();
				} else if (mHS.menuTable.isOpen) {
					if (mHS.menuTable.getCurrentScreen()==HostMenuTable.SCREEN_NO_WIFI) {
						if (mHS.menuTable.settingsButton.pointContained(touchX, touchY)) {
							mHS.menuTable.settingsButton.setIsTouched(true);
						}
					} else if (mHS.menuTable.getCurrentScreen()==HostMenuTable.SCREEN_MAIN) {
						if (mHS.menuTable.destroyButton.pointContained(touchX, touchY)) {
							mHS.menuTable.destroyButton.setIsTouched(true);
						}
					}
				}
			}
			
		}
		*/
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		/*
		if (pointer==0&&mHS.game.screenState==DPCGame.STATE_GAMEPLAY) {
			if (touchFocus==TOUCH_LOBBY) {
				if (mHS.pickedUpPlayer!=null&&mHS.pickedUpPlayer.isTouched) {
					mHS.pickedUpPlayer.isTouched=false;
					mHS.checkPickedUpPlayerOverChair();
				}
				if (mHS.dealerChip.isTouched()) {
					mHS.dealerChip.setTouched(false,0,0);
					mHS.checkDealerChipOverChair();
				}
				if (mHS.resumeButton.getIsTouched()) {
					mHS.resumeButton.setIsTouched(false);
					mHS.doResumeLoadedGame();
					touchFocus=TOUCH_MAIN_GAME;
				}
			} else if (touchFocus==TOUCH_MAIN_GAME) {
				mHS.flingDemo.unpause();
				if (mHS.pots.size()>0&&mHS.pots.get(mHS.pots.size()-1).potStack.isTouched) {
					mHS.pots.get(mHS.pots.size()-1).potStack.isTouched=false;
					mHS.checkPotOverChair();
				}
				if (mHS.splitButton.getIsTouched()) {
					mHS.splitButton.setIsTouched(false);
					mHS.doSplitSelect();
				}
				if (mHS.cancelSplitButton.getIsTouched()) {
					mHS.cancelSplitButton.setIsTouched(false);
					mHS.cancelSplitSelect();
				}
				if (mHS.splitDoneButton.getIsTouched()) {
					mHS.splitDoneButton.setIsTouched(false);
					mHS.doSplitPot();
				}
				if (mHS.arrowRight.getIsTouched()) {
					mHS.arrowRight.setIsTouched(false);
					mHS.doRightArrow();
				}
				if (mHS.arrowLeft.getIsTouched()) {
					mHS.arrowLeft.setIsTouched(false);
					mHS.doLeftArrow();
				}
				if (mHS.menuTable.isTouched) {
					mHS.menuTable.isTouched=false;
					touchFocus=TOUCH_MENU_TABLE;
					mHS.openMenuTable();
				}
			} else if (touchFocus==TOUCH_MENU_TABLE) {
				if (mHS.menuTable.getCurrentScreen()==HostMenuTable.SCREEN_NO_WIFI) {
					if (mHS.menuTable.settingsButton.getIsTouched()) {
						mHS.menuTable.settingsButton.setIsTouched(false);
						mHS.launchSettings();
					}
				} else if (mHS.menuTable.getCurrentScreen()==HostMenuTable.SCREEN_MAIN) {
					if (mHS.menuTable.destroyButton.getIsTouched()) {
						mHS.menuTable.destroyButton.setIsTouched(false);
						mHS.openDestroyDialog();
					}
				}
			}
		}
		*/
		return true;
	}
	

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		/*
		if (pointer==0&&mHS.game.screenState==DPCGame.STATE_GAMEPLAY) {
			float touchX=screenX;
			float touchY=mHS.game.renderer.yViewToWorld(screenY);
			if (touchFocus==TOUCH_LOBBY) {
				if (mHS.pickedUpPlayer!=null&&mHS.pickedUpPlayer.isTouched) {
					mHS.pickedUpPlayer.setX(touchX-deltaTouchX);
					mHS.pickedUpPlayer.setY(touchY-deltaTouchY);
				}
				if (mHS.dealerChip.isTouched()) {
					mHS.dealerChip.x=touchX-deltaTouchX;
					mHS.dealerChip.y=touchY-deltaTouchY;
					mHS.dealerChip.touchDragged(touchX,touchY);
				}
				if (mHS.resumeButton.getIsTouched()) {
					if (!mHS.resumeButton.pointContained(touchX, touchY)) {
						mHS.resumeButton.setIsTouched(false);
					}
				}
			} else if (touchFocus==TOUCH_MAIN_GAME) {
				if (mHS.menuTable.isTouched) {
					mHS.menuTable.isTouched=mHS.menuTable.pointContained(touchX, touchY);
				}
				if (mHS.pots.size()>0&&mHS.pots.get(mHS.pots.size()-1).potStack.isTouched) {
					mHS.pots.get(mHS.pots.size()-1).potStack.setX(touchX-deltaTouchX);
					mHS.pots.get(mHS.pots.size()-1).potStack.setY(touchY-deltaTouchY);
					mHS.pots.get(mHS.pots.size()-1).potStack.touchDragged(touchX,touchY);
				}
				if (mHS.splitButton.getIsTouched()) {
					if (!mHS.splitButton.pointContained(touchX, touchY)) {
						mHS.splitButton.setIsTouched(false);
					}
				}
				if (mHS.cancelSplitButton.getIsTouched()) {
					if (!mHS.cancelSplitButton.pointContained(touchX, touchY)) {
						mHS.cancelSplitButton.setIsTouched(false);
					}
				}
				if (mHS.splitDoneButton.getIsTouched()) {
					if (!mHS.splitDoneButton.pointContained(touchX, touchY)) {
						mHS.splitDoneButton.setIsTouched(false);
					}
				}
				if (mHS.arrowRight.getIsTouched()) {
					if (!mHS.arrowRight.pointContained(touchX, touchY)) {
						mHS.arrowRight.setIsTouched(false);
					}
				}
				if (mHS.arrowLeft.getIsTouched()) {
					if (!mHS.arrowLeft.pointContained(touchX, touchY)) {
						mHS.arrowLeft.setIsTouched(false);
					}
				}
			} else if (touchFocus==TOUCH_MENU_TABLE) {
				if (mHS.menuTable.getCurrentScreen()==HostMenuTable.SCREEN_NO_WIFI) {
					if (mHS.menuTable.settingsButton.getIsTouched()) {
						if (!mHS.menuTable.settingsButton.pointContained(touchX, touchY)) {
							mHS.menuTable.settingsButton.setIsTouched(false);
						}
					}
				} else if (mHS.menuTable.getCurrentScreen()==HostMenuTable.SCREEN_MAIN) {
					if (mHS.menuTable.destroyButton.getIsTouched()) {
						if (!mHS.menuTable.destroyButton.pointContained(touchX, touchY)) {
							mHS.menuTable.destroyButton.setIsTouched(false);
						}
					}
				}
			}
		}
		*/
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {return false;}
	@Override
	public boolean mouseMoved(int arg0, int arg1) {return false;}
	@Override
	public boolean scrolled(int arg0) {return false;}

}
