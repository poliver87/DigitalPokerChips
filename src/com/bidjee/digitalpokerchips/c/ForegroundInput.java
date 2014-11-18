package com.bidjee.digitalpokerchips.c;

import java.util.Stack;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.bidjee.digitalpokerchips.m.BuyinDialog;
import com.bidjee.digitalpokerchips.m.ChipCase;
import com.bidjee.util.Logger;

public class ForegroundInput implements InputProcessor {
	
	public static final String LOG_TAG = "DPCInput";
	
	public static final String TOUCH_NOTHING = "TOUCH_NOTHING";
	public static final String TOUCH_HOME = "TOUCH_HOME";
	public static final String TOUCH_TABLES_NAME = "TOUCH_TABLES_NAME";
	public static final String TOUCH_CHIP_VALUES = "TOUCH_CHIP_VALUES";
	public static final String TOUCH_BUYIN = "TOUCH_BUYIN";
	public static final String TOUCH_MANUAL_CONNECT = "TOUCH_MANUAL_CONNECT";
	public static final String TOUCH_LEAVE_TABLE = "TOUCH_LEAVE_TABLE";
	public static final String TOUCH_HELP_DIALOG = "TOUCH_HELP_DIALOG";
	public static final String TOUCH_PLAYER_LOGIN = "TOUCH_PLAYER_LOGIN";
	public static final String TOUCH_PLAYER_DASHBOARD = "TOUCH_PLAYER_DASHBOARD";
	public static final String TOUCH_DESTROY_TABLE = "TOUCH_DESTROY_TABLE";
	public static final String TOUCH_BOOT_DIALOG = "TOUCH_BOOT_DIALOG";
	public static final String TOUCH_LOAD_DIALOG = "TOUCH_LOAD_DIALOG";
	public static final String TOUCH_TUTORIAL_TABLE_NAME = "TOUCH_TUTORIAL_TABLE_NAME";
	public static final String TOUCH_PLAYER = "TOUCH_PLAYER";
	public static final String TOUCH_TABLE_STATUS = "TOUCH_TABLE_STATUS";
	public static final String TOUCH_TUTORIAL_ARRANGEMENT = "TOUCH_TUTORIAL_ARRANGEMENT";
	public static final String TOUCH_LOBBY = "TOUCH_LOBBY";
	public static final String TOUCH_LOBBY_LOADED = "TOUCH_LOBBY_LOADED";
	public static final String TOUCH_PLAYER_STATE_CHANGE = "TOUCH_PLAYER_STATE_CHANGE";
	public static final String TOUCH_NO_WIFI = "TOUCH_NO_WIFI";
	public static final String TOUCH_TABLE_GAMEPLAY = "TOUCH_TABLE_GAMEPLAY";
	public static final String TOUCH_AUTOSAVE_DIALOG = "TOUCH_AUTOSAVE_DIALOG";
	
	public static final String TYPING_NONE = "TYPING_NONE";
	public static final String TYPING_MANUAL_CONNECT = "TYPING_MANUAL_CONNECT";
	public static final String TYPING_NAME_FIELD = "TYPING_NAME_FIELD";
	
	ForegroundLayer mFL;
	
	boolean openBetPromptShowing;

	private Stack<String> touchFocus=new Stack<String>();
	private String typingFocus="";
	
	public void setScreen(ForegroundLayer mFL_) {
		mFL=mFL_;
	}
	
	@Override
	public boolean keyTyped(char character) {
		boolean handled=false;
		if (typingFocus.equals(TYPING_MANUAL_CONNECT)) {
			handled=true;
			if (character=='\b') {
				mFL.manualConnectDialog.backspace();
			} else if (character=='\n') {
				mFL.game.mWL.thisPlayer.manualConnectDialogDone(true);
			} else if ((int)(character)!=0) {
				mFL.manualConnectDialog.keyTyped(""+character);
			}
		}
		if (getLastTouchFocus().equals(TOUCH_PLAYER_LOGIN)) {
			handled=true;
			if (character=='\b') {
				mFL.playerLoginDialog.backspace();
			} else if (character=='\n') {
				mFL.playerLoginDialog.enterTyped();
			} else if ((int)(character)!=0) {
				mFL.playerLoginDialog.keyTyped(""+character);
			}
		}
		return handled;
	}

	@Override
	public boolean keyDown(int keycode) {
		boolean handled=false;
		if (keycode==Keys.BACK) {
			handled=true;
			backPressed();
		}
		return handled;
	}
	
	public void backPressed() {
		if (touchFocus.size()>0) {
			Logger.log(LOG_TAG,"backPressed() - touchFocus = "+getLastTouchFocus());
			if (getLastTouchFocus().equals(TOUCH_BUYIN)) {
				mFL.game.mWL.thisPlayer.buyinDialogDone(false);
			} else if (getLastTouchFocus().equals(TOUCH_MANUAL_CONNECT)) {
				mFL.game.mWL.thisPlayer.manualConnectDialogDone(false);
			} else if (getLastTouchFocus().equals(TOUCH_HELP_DIALOG)) {
				mFL.game.mWL.helpDone();
			} else if (getLastTouchFocus().equals(TOUCH_PLAYER_LOGIN)) {
				mFL.game.mWL.thisPlayer.playerLoginDone(false);
			} else if (getLastTouchFocus().equals(TOUCH_LEAVE_TABLE)) {
				mFL.game.mWL.thisPlayer.leaveDialogDone(false);
			} else if (getLastTouchFocus().equals(TOUCH_DESTROY_TABLE)) {
				mFL.game.mWL.table.destroyDialogDone(false);
			} else if (getLastTouchFocus().equals(TOUCH_AUTOSAVE_DIALOG)) {
				mFL.game.mWL.table.autosaveDialogDone();
			} else if (getLastTouchFocus().equals(TOUCH_TABLE_STATUS)) {
				mFL.tableStatusMenu.close();
			} else if (getLastTouchFocus().equals(TOUCH_LOAD_DIALOG)) {
				mFL.closeLoadDialog();
			} else if (getLastTouchFocus().equals(TOUCH_PLAYER_STATE_CHANGE)) {
				mFL.game.mWL.thisPlayer.stateChangeACKed();
			} else if (getLastTouchFocus().equals(TOUCH_TUTORIAL_ARRANGEMENT)) {
				mFL.stopTutorialArrangement();
			} else if (getLastTouchFocus().equals(TOUCH_BOOT_DIALOG)) {
				mFL.game.mWL.table.closeBootDialog();
			} else {
				mFL.game.mWL.navigateBack();
			}
		} else {
			Logger.log(LOG_TAG,"backPressed() - no touch focus");
			mFL.game.mWL.navigateBack();
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX,int screenY,int pointer,int button) {
		boolean handled_=false;
		float touchX=screenX;
		float touchY=mFL.foregroundRenderer.yViewToWorld(screenY);
		if (pointer==0&&touchFocus.size()>0) {
			Logger.log(LOG_TAG,"touchDown("+touchX+","+touchY+") touchFocus = "+getLastTouchFocus());
			
			if (getLastTouchFocus().equals(TOUCH_TUTORIAL_TABLE_NAME)) {
				handled_=true;
			} else if (getLastTouchFocus().equals(TOUCH_HOME)) {
				if (mFL.homeUIAnimation.logoSprite.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.homeUIAnimation.logoSprite.setIsTouched(true);
				}
				if (mFL.homeUIAnimation.helpButton.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.homeUIAnimation.helpButton.setIsTouched(true);
				}
				if (mFL.homeUIAnimation.hostButton.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.homeUIAnimation.hostButton.setIsTouched(true);
					mFL.game.mWL.homeDeviceAnimation.hostSprite.setIsTouched(true);
				}
				if (mFL.homeUIAnimation.joinButton.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.homeUIAnimation.joinButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_TABLES_NAME)) {
				if (mFL.enterTableNameDoneButton.getTouchable()&&
						mFL.enterTableNameDoneButton.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.enterTableNameDoneButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_LOAD_DIALOG)) {
				handled_=true;
				if (mFL.dialogWindow.pointContained(touchX,touchY)) {
					if (mFL.loadDialog.cancelButton.getTouchable()&&
							mFL.loadDialog.cancelButton.pointContained(touchX,touchY)) {
						mFL.loadDialog.cancelButton.setIsTouched(true);
					} else if (mFL.loadDialog.okButton.getTouchable()&&
							mFL.loadDialog.okButton.pointContained(touchX,touchY)) {
						mFL.loadDialog.okButton.setIsTouched(true);
					} else {
						for (int i=0;i<mFL.loadDialog.loadSlots.length;i++) {
							if (mFL.loadDialog.loadSlots[i].getTouchable()&&
									mFL.loadDialog.loadSlots[i].pointContained(touchX, touchY)) {
								mFL.loadDialog.slotSelected(i);
								break;
							}
						}
					}
				} else {
					mFL.loadDialogDone(false);
				}
			}  else if (getLastTouchFocus().equals(TOUCH_CHIP_VALUES)) {

				if (mFL.divisibilityDialog.opacity!=0) {
					handled_=true;
					mFL.divisibilityDialog.hide();
				}
				if (mFL.valueDownArrows!=null) {
					for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
						if (mFL.valueDownArrows[i].getTouchable()&&mFL.valueDownArrows[i].pointContained(touchX, touchY)) {
							handled_=true;
							mFL.valueDownArrows[i].setIsTouched(true);
						}
					}
				}
				if (mFL.valueUpArrows!=null) {
					for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
						if (mFL.valueUpArrows[i].getTouchable()&&mFL.valueUpArrows[i].pointContained(touchX, touchY)) {
							handled_=true;
							mFL.valueUpArrows[i].setIsTouched(true);
						}
					}
				}
				if (mFL.setValuesOkButton.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.setValuesOkButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_BUYIN)) {
				handled_=true;
				if (mFL.buyinDialog.closeButton.getTouchable()&&
						mFL.buyinDialog.closeButton.pointContained(touchX, touchY)) {
					mFL.buyinDialog.closeButton.setIsTouched(true);
				} else if (mFL.buyinDialog.minusButton.getTouchable()&&
						mFL.buyinDialog.minusButton.pointContained(touchX, touchY)) {
					mFL.buyinDialog.minusButton.setIsTouched(true);
				} else if (mFL.buyinDialog.plusButton.getTouchable()&&
						mFL.buyinDialog.plusButton.pointContained(touchX, touchY)) {
					mFL.buyinDialog.plusButton.setIsTouched(true);
				} else if (mFL.buyinDialog.cancelButton.getTouchable()&&
						mFL.buyinDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.buyinDialog.cancelButton.setIsTouched(true);
				} else if (mFL.buyinDialog.okayButton.getTouchable()&&
						mFL.buyinDialog.okayButton.pointContained(touchX, touchY)) {
					mFL.buyinDialog.okayButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_PLAYER_LOGIN)) {
				handled_=true;
				if (mFL.playerLoginDialog.closeButton.getTouchable()&&
						mFL.playerLoginDialog.closeButton.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.closeButton.setIsTouched(true);
				} else if (mFL.playerLoginDialog.nameField.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.setFieldFocus(mFL.playerLoginDialog.nameField);
				} else if (mFL.playerLoginDialog.guestOKButton.getTouchable()&&
						mFL.playerLoginDialog.guestOKButton.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.guestOKButton.setIsTouched(true);
				} else if (mFL.playerLoginDialog.facebookButton.getTouchable()&&
						mFL.playerLoginDialog.facebookButton.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.facebookButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_PLAYER_DASHBOARD)) {
				if (mFL.playerDashboard.backButton.getTouchable()&&
						mFL.playerDashboard.backButton.pointContained(touchX, touchY)) {
					mFL.playerDashboard.backButton.setIsTouched(true);
					handled_=true;
				}
			} else if (getLastTouchFocus().equals(TOUCH_MANUAL_CONNECT)) {
				handled_=true;
				for (int i=0;i<mFL.manualConnectDialog.ipQuads.length;i++) {
					if (mFL.manualConnectDialog.ipQuads[i].pointContained(touchX, touchY)) {
						mFL.manualConnectDialog.ipQuadClicked(i);
						break;
					}
				}
				if (mFL.manualConnectDialog.okButton.getTouchable()&&
						mFL.manualConnectDialog.okButton.pointContained(touchX, touchY)) {
					mFL.manualConnectDialog.okButton.setIsTouched(true);
				} else if (mFL.manualConnectDialog.cancelButton.getTouchable()&&
						mFL.manualConnectDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.manualConnectDialog.cancelButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_LEAVE_TABLE)) {
				handled_=true;
				if (mFL.leaveTableDialog.okButton.getTouchable()&&
						mFL.leaveTableDialog.okButton.pointContained(touchX, touchY)) {
					mFL.leaveTableDialog.okButton.setIsTouched(true);
				} else if (mFL.leaveTableDialog.cancelButton.getTouchable()&&
						mFL.leaveTableDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.leaveTableDialog.cancelButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_DESTROY_TABLE)) {
				handled_=true;
				if (mFL.destroyTableDialog.okButton.getTouchable()&&
						mFL.destroyTableDialog.okButton.pointContained(touchX, touchY)) {
					mFL.destroyTableDialog.okButton.setIsTouched(true);
				} else if (mFL.destroyTableDialog.cancelButton.getTouchable()&&
						mFL.destroyTableDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.destroyTableDialog.cancelButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_AUTOSAVE_DIALOG)) {
				handled_=true;
				if (mFL.autosaveDialog.okButton.getTouchable()&&
						mFL.autosaveDialog.okButton.pointContained(touchX, touchY)) {
					mFL.autosaveDialog.okButton.setIsTouched(true);
				} else {
					for (int i=0;i<mFL.autosaveDialog.saveSlots.length;i++) {
						if (mFL.autosaveDialog.saveSlots[i].getTouchable()&&
								mFL.autosaveDialog.saveSlots[i].pointContained(touchX, touchY)) {
							mFL.game.mWL.table.saveSlotSelected(i+1);
							break;
						}
					}
				}
			} else if (getLastTouchFocus().equals(TOUCH_PLAYER)) {
				if (mFL.tableStatusMenu.handle.getTouchable()&&
						mFL.tableStatusMenu.handle.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.tableStatusMenu.handle.setIsTouched(true);
				} else if (mFL.foldButton.getTouchable()&&
						mFL.foldButton.pointContained(touchX, touchY)) {
						handled_=true;
						mFL.foldButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_TABLE_STATUS)) {
				if (mFL.tableStatusMenu.pointContained(touchX,touchY)||mFL.tableStatusMenu.handle.pointContained(touchX,touchY)) {
					handled_=true;
					if (mFL.tableStatusMenu.handle.getTouchable()&&
							mFL.tableStatusMenu.handle.pointContained(touchX, touchY)) {
						mFL.tableStatusMenu.handle.setIsTouched(true);
					} else if (mFL.tableStatusMenu.leaveButton.getTouchable()&&
							mFL.tableStatusMenu.leaveButton.pointContained(touchX,touchY)) {
						mFL.tableStatusMenu.leaveButton.setIsTouched(true);
					}
					if (mFL.tableStatusMenu.bellButton.getTouchable()&&
							mFL.tableStatusMenu.bellButton.pointContained(touchX,touchY)) {
						mFL.tableStatusMenu.bellButton.setIsTouched(true);
					}
				} else {
					handled_=true;
					mFL.tableStatusMenu.close();
				}
			} else if (getLastTouchFocus().equals(TOUCH_TUTORIAL_ARRANGEMENT)) {
				if (mFL.tutorialArrangement.isReadyToStop()) {
					mFL.stopTutorialArrangement();
				} else {
					handled_=true;
				}
			} else if (getLastTouchFocus().equals(TOUCH_LOBBY)) {
				if (mFL.gotoGameButton.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.gotoGameButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_PLAYER_STATE_CHANGE)) {
				handled_=true;
				mFL.game.mWL.thisPlayer.stateChangeACKed();
			} else if (getLastTouchFocus().equals(TOUCH_NO_WIFI)) {
				if (mFL.wifiButton.getTouchable()&&mFL.wifiButton.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.wifiButton.setIsTouched(true);
				}	
			} else if (getLastTouchFocus().equals(TOUCH_BOOT_DIALOG)) {
				handled_=true;
				if (mFL.bootDialog.pointContained(touchX,touchY)) {
					if (mFL.bootDialog.bootButton.getTouchable()&&
							mFL.bootDialog.bootButton.pointContained(touchX, touchY)) {
						handled_=true;
						mFL.bootDialog.bootButton.setIsTouched(true);
					} else if (mFL.bootDialog.sitOutButton.getTouchable()&&
							mFL.bootDialog.sitOutButton.pointContained(touchX, touchY)) {
						handled_=true;
						mFL.bootDialog.sitOutButton.setIsTouched(true);
					}
				} else {
					mFL.game.mWL.table.closeBootDialog();
				}
			} else if (getLastTouchFocus().equals(TOUCH_TABLE_GAMEPLAY)) {
				if (mFL.potArrowLeft.getTouchable()&&mFL.potArrowLeft.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.potArrowLeft.setIsTouched(true);
				}
				if (mFL.potArrowRight.getTouchable()&&mFL.potArrowRight.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.potArrowRight.setIsTouched(true);
				}
				if (mFL.splitButton.getTouchable()&&mFL.splitButton.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.splitButton.setIsTouched(true);
				}
				if (mFL.splitCancelButton.getTouchable()&&mFL.splitCancelButton.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.splitCancelButton.setIsTouched(true);
				}
				if (mFL.splitDoneButton.getTouchable()&&mFL.splitDoneButton.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.splitDoneButton.setIsTouched(true);
				}
			}
		}
		return handled_;
	}

	@Override
	public boolean touchUp(int screenX,int screenY,int pointer,int button) {
		boolean handled_=false;
		if (pointer==0&&touchFocus.size()>0) {
			if (mFL.homeUIAnimation.helpButton.getIsTouched()) {
				handled_=true;
				mFL.homeUIAnimation.helpButton.setIsTouched(false);
				mFL.game.mWL.helpSelected();
			}
			if (mFL.homeUIAnimation.logoSprite.getIsTouched()) {
				handled_=true;
				mFL.homeUIAnimation.logoSprite.setIsTouched(false);
				mFL.homeUIAnimation.logoTapped();
			}
			if (mFL.homeUIAnimation.hostButton.getIsTouched()) {
				handled_=true;
				mFL.homeUIAnimation.hostButton.setIsTouched(false);
				mFL.game.mWL.hostSelected();
			}
			if (mFL.homeUIAnimation.joinButton.getIsTouched()) {
				handled_=true;
				mFL.homeUIAnimation.joinButton.setIsTouched(false);
				mFL.game.mWL.joinSelected();
			}
			if (mFL.playerLoginDialog.guestOKButton.getIsTouched()) {
				handled_=true;
				mFL.playerLoginDialog.guestOKButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.playerLoginDone(mFL.playerLoginDialog.nameField.getText(),null);
			}
			if (mFL.playerLoginDialog.facebookButton.getIsTouched()) {
				handled_=true;
				mFL.playerLoginDialog.facebookButton.setIsTouched(false);
				mFL.game.performFacebookClick();
			}
			if (mFL.playerLoginDialog.closeButton.getIsTouched()) {
				handled_=true;
				mFL.playerLoginDialog.closeButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.playerLoginDone(false);
			}
			if (mFL.buyinDialog.closeButton.getIsTouched()) {
				handled_=true;
				mFL.buyinDialog.closeButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.buyinDialogDone(false);
			}
			if (mFL.buyinDialog.minusButton.getIsTouched()) {
				handled_=true;
				mFL.buyinDialog.minusButton.setIsTouched(false);
				mFL.buyinDialog.decreaseAmount();
			}
			if (mFL.buyinDialog.plusButton.getIsTouched()) {
				handled_=true;
				mFL.buyinDialog.plusButton.setIsTouched(false);
				mFL.buyinDialog.increaseAmount();
			}
			if (mFL.buyinDialog.cancelButton.getIsTouched()) {
				handled_=true;
				mFL.buyinDialog.cancelButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.buyinDialogDone(false);
			}
			if (mFL.buyinDialog.okayButton.getIsTouched()) {
				handled_=true;
				mFL.buyinDialog.okayButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.buyinDialogDone(true);
			}
			if (mFL.playerDashboard.backButton.getIsTouched()) {
				handled_=true;
				mFL.playerDashboard.backButton.setIsTouched(false);
				mFL.game.mWL.navigateBack();
			}
			if (mFL.wifiButton.getIsTouched()) {
				handled_=true;
				mFL.wifiButton.setIsTouched(false);
				mFL.game.launchSettings();
			}
			if (mFL.enterTableNameDoneButton.getIsTouched()) {
				handled_=true;
				mFL.enterTableNameDoneButton.setIsTouched(false);
				mFL.game.mWL.table.tableNameDone();
			}			
			if (mFL.setValuesOkButton.getIsTouched()) {
				handled_=true;
				mFL.setValuesOkButton.setIsTouched(false);
				mFL.game.mWL.table.setValuesDone();
			}
			if (mFL.valueDownArrows!=null) {
				for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
					if (mFL.valueDownArrows[i].getIsTouched()) {
						handled_=true;
						mFL.valueDownArrows[i].setIsTouched(false);
						mFL.game.mWL.chipCase.valueDown(i);
					}
				}
			}
			if (mFL.valueUpArrows!=null) {
				for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
					if (mFL.valueUpArrows[i].getIsTouched()) {
						handled_=true;
						mFL.valueUpArrows[i].setIsTouched(false);
						mFL.game.mWL.chipCase.valueUp(i);
					}
				}
			}
			if (mFL.manualConnectDialog.okButton.getIsTouched()) {
				handled_=true;
				mFL.manualConnectDialog.okButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.manualConnectDialogDone(true);
			}
			if (mFL.manualConnectDialog.cancelButton.getIsTouched()) {
				handled_=true;
				mFL.manualConnectDialog.cancelButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.manualConnectDialogDone(false);
			}
			if (mFL.leaveTableDialog.okButton.getIsTouched()) {
				handled_=true;
				mFL.leaveTableDialog.okButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.leaveDialogDone(true);
			}
			if (mFL.leaveTableDialog.cancelButton.getIsTouched()) {
				handled_=true;
				mFL.leaveTableDialog.cancelButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.leaveDialogDone(false);
			}
			if (mFL.destroyTableDialog.okButton.getIsTouched()) {
				handled_=true;
				mFL.destroyTableDialog.okButton.setIsTouched(false);
				mFL.game.mWL.table.destroyDialogDone(true);
			}
			if (mFL.destroyTableDialog.cancelButton.getIsTouched()) {
				handled_=true;
				mFL.destroyTableDialog.cancelButton.setIsTouched(false);
				mFL.game.mWL.table.destroyDialogDone(false);
			}
			if (mFL.bootDialog.bootButton.getIsTouched()) {
				handled_=true;
				mFL.bootDialog.bootButton.setIsTouched(false);
				mFL.game.mWL.table.bootButtonClicked();
			}
			if (mFL.bootDialog.sitOutButton.getIsTouched()) {
				handled_=true;
				mFL.bootDialog.sitOutButton.setIsTouched(false);
				mFL.game.mWL.table.sitOutButtonClicked();
			}
			if (mFL.autosaveDialog.okButton.getIsTouched()) {
				handled_=true;
				mFL.autosaveDialog.okButton.setIsTouched(false);
				mFL.game.mWL.table.autosaveDialogDone();
			}
			if (mFL.loadDialog.okButton.getIsTouched()) {
				handled_=true;
				mFL.loadDialog.okButton.setIsTouched(false);
				mFL.loadDialogDone(true);
			}
			if (mFL.loadDialog.cancelButton.getIsTouched()) {
				handled_=true;
				mFL.loadDialog.cancelButton.setIsTouched(false);
				mFL.loadDialogDone(false);
			}
			if (mFL.tableStatusMenu.handle.getIsTouched()) {
				handled_=true;
				mFL.tableStatusMenu.handle.setIsTouched(false);
				mFL.tableStatusMenu.handleDropped();
			}
			if (mFL.tableStatusMenu.leaveButton.getIsTouched()) {
				handled_=true;
				mFL.tableStatusMenu.leaveButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.leaveButtonPressed();
			}
			if (mFL.tableStatusMenu.bellButton.getIsTouched()) {
				handled_=true;
				mFL.tableStatusMenu.bellButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.bellButtonPressed();
			}
			if (mFL.foldButton.getIsTouched()) {
				handled_=true;
				mFL.foldButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.doFold();
			}
			if (mFL.gotoGameButton.getIsTouched()) {
				handled_=true;
				mFL.gotoGameButton.setIsTouched(false);
				mFL.game.mWL.table.playButtonClicked();
			}
			if (mFL.potArrowLeft.getIsTouched()) {
				handled_=true;
				mFL.potArrowLeft.setIsTouched(false);
				mFL.game.mWL.table.doPotLeftArrow();
			}
			if (mFL.potArrowRight.getIsTouched()) {
				handled_=true;
				mFL.potArrowRight.setIsTouched(false);
				mFL.game.mWL.table.doPotRightArrow();
			}
			if (mFL.splitButton.getIsTouched()) {
				handled_=true;
				mFL.splitButton.setIsTouched(false);
				mFL.game.mWL.table.startSplit();
			}
			if (mFL.splitCancelButton.getIsTouched()) {
				handled_=true;
				mFL.splitCancelButton.setIsTouched(false);
				mFL.game.mWL.table.cancelSplit();
			}
			if (mFL.splitDoneButton.getIsTouched()) {
				handled_=true;
				mFL.splitDoneButton.setIsTouched(false);
				mFL.game.mWL.table.splitDone();
			}
		}
		return handled_;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		boolean handled_=false;
		if (pointer==0&&touchFocus.size()>0) {
			float touchX=screenX;
			float touchY=mFL.foregroundRenderer.yViewToWorld(screenY);
			if (mFL.wifiButton.getIsTouched()) {
				handled_=true;
				if (!mFL.wifiButton.pointContained(touchX, touchY)) {
					mFL.wifiButton.setIsTouched(false);
				}
			}
			if (mFL.homeUIAnimation.logoSprite.getIsTouched()) {
				handled_=true;
				if (!mFL.homeUIAnimation.logoSprite.pointContained(touchX, touchY)) {
					mFL.homeUIAnimation.logoSprite.setIsTouched(false);
				}
			}
			if (mFL.homeUIAnimation.helpButton.getIsTouched()) {
				handled_=true;
				if (!mFL.homeUIAnimation.helpButton.pointContained(touchX, touchY)) {
					mFL.homeUIAnimation.helpButton.setIsTouched(false);
				}
			}
			if (mFL.homeUIAnimation.hostButton.getIsTouched()) {
				handled_=false;
				if (!mFL.homeUIAnimation.hostButton.pointContained(touchX, touchY)) {
					mFL.homeUIAnimation.hostButton.setIsTouched(false);
				}
			}
			if (mFL.homeUIAnimation.joinButton.getIsTouched()) {
				handled_=false;
				if (!mFL.homeUIAnimation.joinButton.pointContained(touchX, touchY)) {
					mFL.homeUIAnimation.joinButton.setIsTouched(false);
				}
			}
			if (mFL.playerLoginDialog.guestOKButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerLoginDialog.guestOKButton.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.guestOKButton.setIsTouched(false);
				}
			}
			if (mFL.playerLoginDialog.facebookButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerLoginDialog.facebookButton.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.facebookButton.setIsTouched(false);
				}
			}
			if (mFL.playerLoginDialog.closeButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerLoginDialog.closeButton.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.closeButton.setIsTouched(false);
				}
			}
			if (mFL.buyinDialog.closeButton.getIsTouched()) {
				handled_=true;
				if (!mFL.buyinDialog.closeButton.pointContained(touchX, touchY)) {
					mFL.buyinDialog.closeButton.setIsTouched(false);
				}
			}
			if (mFL.buyinDialog.minusButton.getIsTouched()) {
				handled_=true;
				if (!mFL.buyinDialog.minusButton.pointContained(touchX, touchY)) {
					mFL.buyinDialog.minusButton.setIsTouched(false);
				}
			}
			if (mFL.buyinDialog.plusButton.getIsTouched()) {
				handled_=true;
				if (!mFL.buyinDialog.plusButton.pointContained(touchX, touchY)) {
					mFL.buyinDialog.plusButton.setIsTouched(false);
				}
			}
			if (mFL.buyinDialog.cancelButton.getIsTouched()) {
				handled_=true;
				if (!mFL.buyinDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.buyinDialog.cancelButton.setIsTouched(false);
				}
			}
			if (mFL.buyinDialog.okayButton.getIsTouched()) {
				handled_=true;
				if (!mFL.buyinDialog.okayButton.pointContained(touchX, touchY)) {
					mFL.buyinDialog.okayButton.setIsTouched(false);
				}
			}
			if (mFL.playerDashboard.backButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerDashboard.backButton.pointContained(touchX, touchY)) {
					mFL.playerDashboard.backButton.setIsTouched(false);
				}
			}
			if (mFL.enterNameDoneButton.getIsTouched()) {
				handled_=true;
				if (!mFL.enterNameDoneButton.pointContained(touchX, touchY)) {
					mFL.enterNameDoneButton.setIsTouched(false);
				}
			}
			if (mFL.enterTableNameDoneButton.getIsTouched()) {
				handled_=true;
				if (!mFL.enterTableNameDoneButton.pointContained(touchX, touchY)) {
					mFL.enterTableNameDoneButton.setIsTouched(false);
				}
			}
			if (mFL.setValuesOkButton.getIsTouched()) {
				handled_=true;
				if (!mFL.setValuesOkButton.pointContained(touchX, touchY)) {
					mFL.setValuesOkButton.setIsTouched(false);
				}
			}
			if (mFL.valueDownArrows!=null) {
				for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
					if (mFL.valueDownArrows[i].getIsTouched()) {
						handled_=true;
						if (!mFL.valueDownArrows[i].pointContained(touchX, touchY)) {
							mFL.valueDownArrows[i].setIsTouched(false);
						}
					}
				}
			}
			if (mFL.valueUpArrows!=null) {
				for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
					if (mFL.valueUpArrows[i].getIsTouched()) {
						handled_=true;
						if (!mFL.valueUpArrows[i].pointContained(touchX, touchY)) {
							mFL.valueUpArrows[i].setIsTouched(false);
						}
					}
				}
			}
			if (mFL.manualConnectDialog.okButton.getIsTouched()) {
				handled_=true;
				if (!mFL.manualConnectDialog.okButton.pointContained(touchX, touchY)) {
					mFL.manualConnectDialog.okButton.setIsTouched(false);
				}
			}
			if (mFL.manualConnectDialog.cancelButton.getIsTouched()) {
				handled_=true;
				if (!mFL.manualConnectDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.manualConnectDialog.cancelButton.setIsTouched(false);
				}
			}
			if (mFL.leaveTableDialog.okButton.getIsTouched()) {
				handled_=true;
				if (!mFL.leaveTableDialog.okButton.pointContained(touchX, touchY)) {
					mFL.leaveTableDialog.okButton.setIsTouched(false);
				}
			}
			if (mFL.leaveTableDialog.cancelButton.getIsTouched()) {
				handled_=true;
				if (!mFL.leaveTableDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.leaveTableDialog.cancelButton.setIsTouched(false);
				}
			}
			if (mFL.destroyTableDialog.okButton.getIsTouched()) {
				handled_=true;
				if (!mFL.destroyTableDialog.okButton.pointContained(touchX, touchY)) {
					mFL.destroyTableDialog.okButton.setIsTouched(false);
				}
			}
			if (mFL.bootDialog.bootButton.getIsTouched()) {
				handled_=true;
				if (!mFL.bootDialog.bootButton.pointContained(touchX, touchY)) {
					mFL.bootDialog.bootButton.setIsTouched(false);
				}
			}
			if (mFL.bootDialog.sitOutButton.getIsTouched()) {
				handled_=true;
				if (!mFL.bootDialog.sitOutButton.pointContained(touchX, touchY)) {
					mFL.bootDialog.sitOutButton.setIsTouched(false);
				}
			}
			if (mFL.destroyTableDialog.cancelButton.getIsTouched()) {
				handled_=true;
				if (!mFL.destroyTableDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.destroyTableDialog.cancelButton.setIsTouched(false);
				}
			}
			if (mFL.autosaveDialog.okButton.getIsTouched()) {
				handled_=true;
				if (!mFL.autosaveDialog.okButton.pointContained(touchX, touchY)) {
					mFL.autosaveDialog.okButton.setIsTouched(false);
				}
			}
			if (mFL.loadDialog.okButton.getIsTouched()) {
				handled_=true;
				if (!mFL.loadDialog.okButton.pointContained(touchX, touchY)) {
					mFL.loadDialog.okButton.setIsTouched(false);
				}
			}
			if (mFL.loadDialog.cancelButton.getIsTouched()) {
				handled_=true;
				if (!mFL.loadDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.loadDialog.cancelButton.setIsTouched(false);
				}
			}
			if (mFL.tableStatusMenu.handle.getIsTouched()) {
				handled_=true;
				mFL.tableStatusMenu.translateX(touchX-mFL.tableStatusMenu.handle.x);
				
				if (!mFL.tableStatusMenu.handle.pointContained(touchX, touchY)) {
					mFL.tableStatusMenu.handle.setIsTouched(false);
					mFL.tableStatusMenu.handleDropped();
				}
			}
			if (mFL.tableStatusMenu.leaveButton.getIsTouched()) {
				handled_=true;
				if (!mFL.tableStatusMenu.leaveButton.pointContained(touchX, touchY)) {
					mFL.tableStatusMenu.leaveButton.setIsTouched(false);
				}
			}
			if (mFL.tableStatusMenu.bellButton.getIsTouched()) {
				handled_=true;
				if (!mFL.tableStatusMenu.bellButton.pointContained(touchX, touchY)) {
					mFL.tableStatusMenu.bellButton.setIsTouched(false);
				}
			}
			if (mFL.foldButton.getIsTouched()) {
				handled_=true;
				if (!mFL.foldButton.pointContained(touchX, touchY)) {
					mFL.foldButton.setIsTouched(false);
				}
			}
			if (mFL.gotoGameButton.getIsTouched()) {
				handled_=true;
				if (!mFL.gotoGameButton.pointContained(touchX, touchY)) {
					mFL.gotoGameButton.setIsTouched(false);
				}
			}
			if (mFL.potArrowLeft.getIsTouched()) {
				handled_=true;
				if (!mFL.potArrowLeft.pointContained(touchX, touchY)) {
					mFL.potArrowLeft.setIsTouched(false);
				}
			}
			if (mFL.potArrowRight.getIsTouched()) {
				handled_=true;
				if (!mFL.potArrowRight.pointContained(touchX, touchY)) {
					mFL.potArrowRight.setIsTouched(false);
				}
			}
			if (mFL.splitButton.getIsTouched()) {
				handled_=true;
				if (!mFL.splitButton.pointContained(touchX, touchY)) {
					mFL.splitButton.setIsTouched(false);
				}
			}
			if (mFL.splitCancelButton.getIsTouched()) {
				handled_=true;
				if (!mFL.splitCancelButton.pointContained(touchX, touchY)) {
					mFL.splitCancelButton.setIsTouched(false);
				}
			}
			if (mFL.splitDoneButton.getIsTouched()) {
				handled_=true;
				if (!mFL.splitDoneButton.pointContained(touchX, touchY)) {
					mFL.splitDoneButton.setIsTouched(false);
				}
			}
		}
		return handled_;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	public void pushTouchFocus(String touchFocus) {
		if (!this.getLastTouchFocus().equals(touchFocus)) {
			this.touchFocus.push(touchFocus);
		}
		Logger.log(LOG_TAG,"Pushed touch focus "+touchFocus);
	}
	
	public void popTouchFocus(String touchFocus) {
		if (this.getLastTouchFocus().equals(touchFocus)) {
			this.touchFocus.pop();
			Logger.log(LOG_TAG,"Popped touch focus "+touchFocus);
		}
	}

	public String getLastTouchFocus() {
		if (touchFocus.size()>0) {
			return touchFocus.lastElement();
		} else {
			return "";
		}
	}
	
	public void setTypingFocus(String typingFocus) {
		this.typingFocus=typingFocus;
	}
	
	public String getTypingFocus() {
		return typingFocus;
	}
	
}

