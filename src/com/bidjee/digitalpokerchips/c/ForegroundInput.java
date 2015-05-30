package com.bidjee.digitalpokerchips.c;

import java.util.Stack;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.bidjee.digitalpokerchips.m.HostPlayerDialog;
import com.bidjee.util.Logger;

public class ForegroundInput implements InputProcessor {
	
	public static final String LOG_TAG = "DPCInput";
	
	public static final String TOUCH_NOTHING = "TOUCH_NOTHING";
	public static final String TOUCH_HOME = "TOUCH_HOME";
	public static final String TOUCH_BUYIN = "TOUCH_BUYIN";
	public static final String TOUCH_MANUAL_CONNECT = "TOUCH_MANUAL_CONNECT";
	public static final String TOUCH_LEAVE_TABLE = "TOUCH_LEAVE_TABLE";
	public static final String TOUCH_HELP_DIALOG = "TOUCH_HELP_DIALOG";
	public static final String TOUCH_PLAYER_LOGIN = "TOUCH_PLAYER_LOGIN";
	public static final String TOUCH_HOST_CREATE = "TOUCH_HOST_CREATE";
	public static final String TOUCH_HOST_CHIPCASE = "TOUCH_HOST_CHIPCASE";
	public static final String TOUCH_HOST_CHIP_SETUP = "TOUCH_HOST_CHIP_SETUP";
	public static final String TOUCH_HOST_LOBBY = "TOUCH_HOST_LOBBY";
	public static final String TOUCH_HOST_REARRANGE = "TOUCH_HOST_REARRANGE";
	public static final String TOUCH_HOST_DESTROY = "TOUCH_HOST_DESTROY";
	public static final String TOUCH_LOAD_DIALOG = "TOUCH_LOAD_DIALOG";
	public static final String TOUCH_TUTORIAL_TABLE_NAME = "TOUCH_TUTORIAL_TABLE_NAME";
	public static final String TOUCH_PLAYER = "TOUCH_PLAYER";
	public static final String TOUCH_HOST = "TOUCH_HOST";
	public static final String TOUCH_LOBBY_LOADED = "TOUCH_LOBBY_LOADED";
	public static final String TOUCH_NO_WIFI = "TOUCH_NO_WIFI";
	public static final String TOUCH_TABLE_GAMEPLAY = "TOUCH_TABLE_GAMEPLAY";
	
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
		} else if (getLastTouchFocus().equals(TOUCH_HOST_CREATE)) {
			handled=true;
			if (character=='\b') {
				mFL.hostNameDialog.backspace();
			} else if (character=='\n') {
				mFL.hostNameDialog.enterTyped();
			} else if ((int)(character)!=0) {
				mFL.hostNameDialog.keyTyped(""+character);
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
				mFL.game.mWL.thisPlayer.playerLoginCancel();
			} else if (getLastTouchFocus().equals(TOUCH_HOST_CREATE)) {
				mFL.game.mWL.table.hostCreateCancel();
			} else if (getLastTouchFocus().equals(TOUCH_HOST_CHIPCASE)) {
				mFL.game.mWL.table.hostChipCaseBack();
			} else if (getLastTouchFocus().equals(TOUCH_HOST_CHIP_SETUP)) {
				mFL.game.mWL.table.hostChipSetupBack();
			} else if (getLastTouchFocus().equals(TOUCH_HOST_LOBBY)) {
				mFL.game.mWL.table.hostLobbyBack();
			} else if (getLastTouchFocus().equals(TOUCH_HOST_REARRANGE)) {
				mFL.game.mWL.table.hostRearrangeDone();
			} else if (getLastTouchFocus().equals(TOUCH_LEAVE_TABLE)) {
				mFL.game.mWL.thisPlayer.leaveDialogDone(false);
			} else if (getLastTouchFocus().equals(TOUCH_HOST_DESTROY)) {
				mFL.game.mWL.table.destroyDialogDone(false);
			} else if (getLastTouchFocus().equals(TOUCH_LOAD_DIALOG)) {
				mFL.closeLoadDialog();
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

		mFL.game.activity.brightenScreen();

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
			} else if (getLastTouchFocus().equals(TOUCH_BUYIN)) {
				handled_=true;
				if (mFL.playerBuyinDialog.minusButton.getTouchable()&&
						mFL.playerBuyinDialog.minusButton.pointContained(touchX, touchY)) {
					mFL.playerBuyinDialog.minusButton.setIsTouched(true);
				} else if (mFL.playerBuyinDialog.plusButton.getTouchable()&&
						mFL.playerBuyinDialog.plusButton.pointContained(touchX, touchY)) {
					mFL.playerBuyinDialog.plusButton.setIsTouched(true);
				} else if (mFL.playerBuyinDialog.cancelButton.getTouchable()&&
						mFL.playerBuyinDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.playerBuyinDialog.cancelButton.setIsTouched(true);
				} else if (mFL.playerBuyinDialog.okayButton.getTouchable()&&
						mFL.playerBuyinDialog.okayButton.pointContained(touchX, touchY)) {
					mFL.playerBuyinDialog.okayButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_PLAYER_LOGIN)) {
				handled_=true;
				if (mFL.playerLoginDialog.nameField.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.setFieldFocus(mFL.playerLoginDialog.nameField);
				} else if (mFL.playerLoginDialog.facebookButton.getTouchable()&&
						mFL.playerLoginDialog.facebookButton.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.facebookButton.setIsTouched(true);
				} else if (mFL.playerLoginDialog.backButton.getTouchable()&&
						mFL.playerLoginDialog.backButton.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.backButton.setIsTouched(true);
				} else if (mFL.playerLoginDialog.okButton.getTouchable()&&
						mFL.playerLoginDialog.okButton.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.okButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_HOST_CREATE)) {
				handled_=true;
				if (mFL.hostNameDialog.nameField.pointContained(touchX, touchY)) {
					mFL.hostNameDialog.setFieldFocus(mFL.hostNameDialog.nameField);
				} else if (mFL.hostNameDialog.backButton.getTouchable()&&
						mFL.hostNameDialog.backButton.pointContained(touchX, touchY)) {
					mFL.hostNameDialog.backButton.setIsTouched(true);
				} else if (mFL.hostNameDialog.okButton.getTouchable()&&
						mFL.hostNameDialog.okButton.pointContained(touchX, touchY)) {
					mFL.hostNameDialog.okButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_HOST_CHIPCASE)) {
				handled_=true;
				if (mFL.hostChipCaseDialog.standardBackground.getTouchable()&&
						mFL.hostChipCaseDialog.standardBackground.pointContained(touchX, touchY)) {
					mFL.hostChipCaseDialog.standardBackground.setIsTouched(true);
				} else if (mFL.hostChipCaseDialog.customBackground.getTouchable()&&
						mFL.hostChipCaseDialog.customBackground.pointContained(touchX, touchY)) {
					mFL.hostChipCaseDialog.customBackground.setIsTouched(true);
				} else if (mFL.hostChipCaseDialog.backButton.getTouchable()&&
						mFL.hostChipCaseDialog.backButton.pointContained(touchX, touchY)) {
					mFL.hostChipCaseDialog.backButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_HOST_CHIP_SETUP)) {
				handled_=true;
				if (mFL.hostChipSetupDialog.backButton.getTouchable()&&
						mFL.hostChipSetupDialog.backButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.backButton.setIsTouched(true);
				} else if (mFL.hostChipSetupDialog.okayButton.getTouchable()&&
						mFL.hostChipSetupDialog.okayButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.okayButton.setIsTouched(true);
				} else if (mFL.hostChipSetupDialog.minusAButton.getTouchable()&&
						mFL.hostChipSetupDialog.minusAButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.minusAButton.setIsTouched(true);
				} else if (mFL.hostChipSetupDialog.plusAButton.getTouchable()&&
						mFL.hostChipSetupDialog.plusAButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.plusAButton.setIsTouched(true);
				} else if (mFL.hostChipSetupDialog.minusBButton.getTouchable()&&
						mFL.hostChipSetupDialog.minusBButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.minusBButton.setIsTouched(true);
				} else if (mFL.hostChipSetupDialog.plusBButton.getTouchable()&&
						mFL.hostChipSetupDialog.plusBButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.plusBButton.setIsTouched(true);
				} else if (mFL.hostChipSetupDialog.minusCButton.getTouchable()&&
						mFL.hostChipSetupDialog.minusCButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.minusCButton.setIsTouched(true);
				} else if (mFL.hostChipSetupDialog.plusCButton.getTouchable()&&
						mFL.hostChipSetupDialog.plusCButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.plusCButton.setIsTouched(true);
				} else if (mFL.hostChipSetupDialog.minusBuyinButton.getTouchable()&&
						mFL.hostChipSetupDialog.minusBuyinButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.minusBuyinButton.setIsTouched(true);
				} else if (mFL.hostChipSetupDialog.plusBuyinButton.getTouchable()&&
						mFL.hostChipSetupDialog.plusBuyinButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.plusBuyinButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_HOST_LOBBY)) {
				
				if (mFL.hostLobbyDialog.backButton.getTouchable()&&
						mFL.hostLobbyDialog.backButton.pointContained(touchX, touchY)) {
					mFL.hostLobbyDialog.backButton.setIsTouched(true);
					handled_=true;
				} else if (mFL.hostLobbyDialog.startButton.getTouchable()&&
						mFL.hostLobbyDialog.startButton.pointContained(touchX, touchY)) {
					mFL.hostLobbyDialog.startButton.setIsTouched(true);
					handled_=true;
				}
			} else if (getLastTouchFocus().equals(TOUCH_HOST_REARRANGE)) {
				
				if (mFL.hostRearrangeDialog.doneButton.getTouchable()&&
						mFL.hostRearrangeDialog.doneButton.pointContained(touchX, touchY)) {
					mFL.hostRearrangeDialog.doneButton.setIsTouched(true);
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
				if (mFL.playerLeaveDialog.cancelButton.getTouchable()&&
						mFL.playerLeaveDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.playerLeaveDialog.cancelButton.setIsTouched(true);
				} else if (mFL.playerLeaveDialog.leaveButton.getTouchable()&&
						mFL.playerLeaveDialog.leaveButton.pointContained(touchX, touchY)) {
					mFL.playerLeaveDialog.leaveButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_HOST_DESTROY)) {
				handled_=true;
				if (mFL.hostDestroyDialog.cancelButton.getTouchable()&&
						mFL.hostDestroyDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.hostDestroyDialog.cancelButton.setIsTouched(true);
				} else if (mFL.hostDestroyDialog.quitButton.getTouchable()&&
						mFL.hostDestroyDialog.quitButton.pointContained(touchX, touchY)) {
					mFL.hostDestroyDialog.quitButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_PLAYER)) {
				if (mFL.foldButton.getTouchable()&&
						mFL.foldButton.pointContained(touchX, touchY)) {
						handled_=true;
						mFL.foldButton.setIsTouched(true);
				} else if (mFL.playerDashboard.backButton.getTouchable()&&
						mFL.playerDashboard.backButton.pointContained(touchX, touchY)) {
					mFL.playerDashboard.backButton.setIsTouched(true);
					handled_=true;
				} else if (mFL.playerDashboard.bellButton.getTouchable()&&
						mFL.playerDashboard.bellButton.pointContained(touchX, touchY)) {
					mFL.playerDashboard.bellButton.setIsTouched(true);
					handled_=true;
				}
			} else if (getLastTouchFocus().equals(TOUCH_NO_WIFI)) {
				if (mFL.wifiButton.getTouchable()&&mFL.wifiButton.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.wifiButton.setIsTouched(true);
				}
			} else if (getLastTouchFocus().equals(TOUCH_TABLE_GAMEPLAY)) {
				if (mFL.hostUndoButton.getTouchable()&&mFL.hostUndoButton.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.hostUndoButton.setIsTouched(true);
				}
				if (mFL.hostExitButton.getTouchable()&&mFL.hostExitButton.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.hostExitButton.setIsTouched(true);
				}
				if (mFL.potArrowLeft.getTouchable()&&mFL.potArrowLeft.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.potArrowLeft.setIsTouched(true);
				}
				if (mFL.potArrowRight.getTouchable()&&mFL.potArrowRight.pointContained(touchX, touchY)) {
					handled_=true;
					mFL.potArrowRight.setIsTouched(true);
				}
				for (int i=0;i<Table.NUM_SEATS;i++) {
					if (mFL.hostPlayerDialogs[i].pointContained(touchX,touchY)) {
						if (mFL.hostPlayerDialogs[i].bootButton.getTouchable()&&
								mFL.hostPlayerDialogs[i].bootButton.pointContained(touchX, touchY)) {
							handled_=true;
							mFL.hostPlayerDialogs[i].bootButton.setIsTouched(true);
						} else if (mFL.hostPlayerDialogs[i].foldButton.getTouchable()&&
								mFL.hostPlayerDialogs[i].foldButton.pointContained(touchX, touchY)) {
							handled_=true;
							mFL.hostPlayerDialogs[i].foldButton.setIsTouched(true);
						} else if (mFL.hostPlayerDialogs[i].cancelButton.getTouchable()&&
								mFL.hostPlayerDialogs[i].cancelButton.pointContained(touchX, touchY)) {
							handled_=true;
							mFL.hostPlayerDialogs[i].cancelButton.setIsTouched(true);
						} else if (mFL.hostPlayerDialogs[i].okButton.getTouchable()&&
								mFL.hostPlayerDialogs[i].okButton.pointContained(touchX, touchY)) {
							handled_=true;
							mFL.hostPlayerDialogs[i].okButton.setIsTouched(true);
						}
					}
				}
				if (mFL.hostPotDialog.pointContained(touchX, touchY)) {
					if (mFL.hostPotDialog.splitButton.getTouchable()&&
							mFL.hostPotDialog.splitButton.pointContained(touchX, touchY)) {
						handled_=true;
						mFL.hostPotDialog.splitButton.setIsTouched(true);
					}
					if (mFL.hostPotDialog.cancelButton.getTouchable()&&
							mFL.hostPotDialog.cancelButton.pointContained(touchX, touchY)) {
						handled_=true;
						mFL.hostPotDialog.cancelButton.setIsTouched(true);
					}
					if (mFL.hostPotDialog.okButton.getTouchable()&&
							mFL.hostPotDialog.okButton.pointContained(touchX, touchY)) {
						handled_=true;
						mFL.hostPotDialog.okButton.setIsTouched(true);
					}
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
				handled_=false;
				mFL.homeUIAnimation.hostButton.setIsTouched(false);
				mFL.game.mWL.hostSelected();
			}
			if (mFL.homeUIAnimation.joinButton.getIsTouched()) {
				handled_=true;
				mFL.homeUIAnimation.joinButton.setIsTouched(false);
				mFL.game.mWL.joinSelected();
			}
			if (mFL.playerLoginDialog.facebookButton.getIsTouched()) {
				handled_=true;
				mFL.playerLoginDialog.facebookButton.setIsTouched(false);
				mFL.game.performFacebookClick();
			}
			if (mFL.playerLoginDialog.backButton.getIsTouched()) {
				handled_=true;
				mFL.playerLoginDialog.backButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.playerLoginCancel();
			}
			if (mFL.playerLoginDialog.okButton.getIsTouched()) {
				handled_=true;
				mFL.playerLoginDialog.okButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.playerLoginComplete(mFL.playerLoginDialog.nameField.getText(), null);
			}
			if (mFL.hostNameDialog.okButton.getIsTouched()) {
				handled_=true;
				mFL.hostNameDialog.okButton.setIsTouched(false);
				mFL.game.mWL.table.hostCreateComplete(mFL.hostNameDialog.nameField.getText());
			}
			if (mFL.hostNameDialog.backButton.getIsTouched()) {
				handled_=true;
				mFL.hostNameDialog.backButton.setIsTouched(false);
				mFL.game.mWL.table.hostCreateCancel();
			}
			if (mFL.hostChipCaseDialog.standardBackground.getIsTouched()) {
				handled_=true;
				mFL.hostChipCaseDialog.standardBackground.setIsTouched(false);
				mFL.game.mWL.table.standardChipCaseSelected();
			}
			if (mFL.hostChipCaseDialog.customBackground.getIsTouched()) {
				handled_=true;
				mFL.hostChipCaseDialog.customBackground.setIsTouched(false);
				mFL.game.mWL.table.customChipCaseSelected();
			}
			if (mFL.hostChipCaseDialog.backButton.getIsTouched()) {
				handled_=true;
				mFL.hostChipCaseDialog.backButton.setIsTouched(false);
				mFL.game.mWL.table.hostChipCaseBack();
			}
			if (mFL.hostChipSetupDialog.backButton.getIsTouched()) {
				handled_=true;
				mFL.hostChipSetupDialog.backButton.setIsTouched(false);
				mFL.game.mWL.table.hostChipSetupBack();
			}
			if (mFL.hostChipSetupDialog.okayButton.getIsTouched()) {
				handled_=true;
				mFL.hostChipSetupDialog.okayButton.setIsTouched(false);
				mFL.game.mWL.table.hostChipSetupDone();
			}
			if (mFL.hostChipSetupDialog.minusAButton.getIsTouched()) {
				handled_=true;
				mFL.hostChipSetupDialog.minusAButton.setIsTouched(false);
				mFL.hostChipSetupDialog.minusA();
			}
			if (mFL.hostChipSetupDialog.plusAButton.getIsTouched()) {
				handled_=true;
				mFL.hostChipSetupDialog.plusAButton.setIsTouched(false);
				mFL.hostChipSetupDialog.plusA();
			}
			if (mFL.hostChipSetupDialog.minusBButton.getIsTouched()) {
				handled_=true;
				mFL.hostChipSetupDialog.minusBButton.setIsTouched(false);
				mFL.hostChipSetupDialog.minusB();
			}
			if (mFL.hostChipSetupDialog.plusBButton.getIsTouched()) {
				handled_=true;
				mFL.hostChipSetupDialog.plusBButton.setIsTouched(false);
				mFL.hostChipSetupDialog.plusB();
			}
			if (mFL.hostChipSetupDialog.minusCButton.getIsTouched()) {
				handled_=true;
				mFL.hostChipSetupDialog.minusCButton.setIsTouched(false);
				mFL.hostChipSetupDialog.minusC();
			}
			if (mFL.hostChipSetupDialog.plusCButton.getIsTouched()) {
				handled_=true;
				mFL.hostChipSetupDialog.plusCButton.setIsTouched(false);
				mFL.hostChipSetupDialog.plusC();
			}
			if (mFL.hostChipSetupDialog.minusBuyinButton.getIsTouched()) {
				handled_=true;
				mFL.hostChipSetupDialog.minusBuyinButton.setIsTouched(false);
				mFL.hostChipSetupDialog.minusBuyin();
			}
			if (mFL.hostChipSetupDialog.plusBuyinButton.getIsTouched()) {
				handled_=true;
				mFL.hostChipSetupDialog.plusBuyinButton.setIsTouched(false);
				mFL.hostChipSetupDialog.plusBuyin();
			}
			if (mFL.hostLobbyDialog.backButton.getIsTouched()) {
				handled_=true;
				mFL.hostLobbyDialog.backButton.setIsTouched(false);
				mFL.game.mWL.table.hostLobbyBack();
			}
			if (mFL.hostLobbyDialog.startButton.getIsTouched()) {
				handled_=true;
				mFL.hostLobbyDialog.startButton.setIsTouched(false);
				mFL.game.mWL.table.hostLobbyStart();
			}
			if (mFL.hostRearrangeDialog.doneButton.getIsTouched()) {
				handled_=true;
				mFL.hostRearrangeDialog.doneButton.setIsTouched(false);
				mFL.game.mWL.table.hostRearrangeDone();
			}
			if (mFL.hostDestroyDialog.cancelButton.getIsTouched()) {
				handled_=true;
				mFL.hostDestroyDialog.cancelButton.setIsTouched(false);
				mFL.game.mWL.table.destroyDialogDone(false);
			}
			if (mFL.hostDestroyDialog.quitButton.getIsTouched()) {
				handled_=true;
				mFL.hostDestroyDialog.quitButton.setIsTouched(false);
				mFL.game.mWL.table.destroyDialogDone(true);
			}
			if (mFL.playerBuyinDialog.minusButton.getIsTouched()) {
				handled_=true;
				mFL.playerBuyinDialog.minusButton.setIsTouched(false);
				mFL.playerBuyinDialog.decreaseAmount();
			}
			if (mFL.playerBuyinDialog.plusButton.getIsTouched()) {
				handled_=true;
				mFL.playerBuyinDialog.plusButton.setIsTouched(false);
				mFL.playerBuyinDialog.increaseAmount();
			}
			if (mFL.playerBuyinDialog.cancelButton.getIsTouched()) {
				handled_=true;
				mFL.playerBuyinDialog.cancelButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.buyinDialogDone(false);
			}
			if (mFL.playerBuyinDialog.okayButton.getIsTouched()) {
				handled_=true;
				mFL.playerBuyinDialog.okayButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.buyinDialogDone(true);
			}
			if (mFL.playerDashboard.backButton.getIsTouched()) {
				handled_=true;
				mFL.playerDashboard.backButton.setIsTouched(false);
				mFL.game.mWL.navigateBack();
			}
			if (mFL.playerDashboard.bellButton.getIsTouched()) {
				handled_=true;
				mFL.playerDashboard.bellButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.bellButtonPressed();
			}
			if (mFL.wifiButton.getIsTouched()) {
				handled_=true;
				mFL.wifiButton.setIsTouched(false);
				mFL.game.launchSettings();
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
			if (mFL.playerLeaveDialog.leaveButton.getIsTouched()) {
				handled_=true;
				mFL.playerLeaveDialog.leaveButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.leaveDialogDone(true);
			}
			if (mFL.playerLeaveDialog.cancelButton.getIsTouched()) {
				handled_=true;
				mFL.playerLeaveDialog.cancelButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.leaveDialogDone(false);
			}
			for (int i=0;i<Table.NUM_SEATS;i++) {
				if (mFL.hostPlayerDialogs[i].bootButton.getIsTouched()) {
					handled_=true;
					mFL.hostPlayerDialogs[i].bootButton.setIsTouched(false);
					mFL.hostPlayerDialogs[i].bootTapped();
				}
				if (mFL.hostPlayerDialogs[i].foldButton.getIsTouched()) {
					handled_=true;
					mFL.hostPlayerDialogs[i].foldButton.setIsTouched(false);
					mFL.hostPlayerDialogs[i].foldTapped();
				}
				if (mFL.hostPlayerDialogs[i].cancelButton.getIsTouched()) {
					handled_=true;
					mFL.hostPlayerDialogs[i].cancelButton.setIsTouched(false);
					mFL.hostPlayerDialogs[i].cancelTapped();
				}
				if (mFL.hostPlayerDialogs[i].okButton.getIsTouched()) {
					handled_=true;
					mFL.hostPlayerDialogs[i].okButton.setIsTouched(false);
					if (mFL.hostPlayerDialogs[i].state==HostPlayerDialog.STATE_CONFIRM_BOOT) {
						mFL.game.mWL.table.bootButtonClicked(i);
					} else {
						mFL.game.mWL.table.foldButtonClicked(i);
					}
					
				}
			}
			
			if (mFL.hostPotDialog.splitButton.getIsTouched()) {
				handled_=true;
				mFL.hostPotDialog.splitButton.setIsTouched(false);
				mFL.game.mWL.table.startSplit();
			}
			if (mFL.hostPotDialog.cancelButton.getIsTouched()) {
				handled_=true;
				mFL.hostPotDialog.cancelButton.setIsTouched(false);
				mFL.game.mWL.table.cancelSplit();
			}
			if (mFL.hostPotDialog.okButton.getIsTouched()) {
				handled_=true;
				mFL.hostPotDialog.okButton.setIsTouched(false);
				mFL.game.mWL.table.splitDone();
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
			if (mFL.foldButton.getIsTouched()) {
				handled_=true;
				mFL.foldButton.setIsTouched(false);
				mFL.game.mWL.thisPlayer.doFold();
			}
			if (mFL.hostUndoButton.getIsTouched()) {
				handled_=true;
				mFL.hostUndoButton.setIsTouched(false);
				mFL.game.mWL.table.doUndo();
			}
			if (mFL.hostExitButton.getIsTouched()) {
				handled_=true;
				mFL.hostExitButton.setIsTouched(false);
				mFL.game.mWL.table.doDestroyDialog();
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
			if (mFL.playerLoginDialog.facebookButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerLoginDialog.facebookButton.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.facebookButton.setIsTouched(false);
				}
			}
			if (mFL.playerLoginDialog.backButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerLoginDialog.backButton.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.backButton.setIsTouched(false);
				}
			}
			if (mFL.playerLoginDialog.okButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerLoginDialog.okButton.pointContained(touchX, touchY)) {
					mFL.playerLoginDialog.okButton.setIsTouched(false);
				}
			}
			if (mFL.hostNameDialog.backButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostNameDialog.backButton.pointContained(touchX, touchY)) {
					mFL.hostNameDialog.backButton.setIsTouched(false);
				}
			}
			if (mFL.hostNameDialog.okButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostNameDialog.okButton.pointContained(touchX, touchY)) {
					mFL.hostNameDialog.okButton.setIsTouched(false);
				}
			}
			if (mFL.hostChipCaseDialog.standardBackground.getIsTouched()) {
				handled_=true;
				if (!mFL.hostChipCaseDialog.standardBackground.pointContained(touchX, touchY)) {
					mFL.hostChipCaseDialog.standardBackground.setIsTouched(false);
				}
			}
			if (mFL.hostChipCaseDialog.customBackground.getIsTouched()) {
				handled_=true;
				if (!mFL.hostChipCaseDialog.customBackground.pointContained(touchX, touchY)) {
					mFL.hostChipCaseDialog.customBackground.setIsTouched(false);
				}
			}
			if (mFL.hostChipCaseDialog.backButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostChipCaseDialog.backButton.pointContained(touchX, touchY)) {
					mFL.hostChipCaseDialog.backButton.setIsTouched(false);
				}
			}
			if (mFL.hostChipSetupDialog.okayButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostChipSetupDialog.okayButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.okayButton.setIsTouched(false);
				}
			}
			if (mFL.hostChipSetupDialog.backButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostChipSetupDialog.backButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.backButton.setIsTouched(false);
				}
			}
			if (mFL.hostChipSetupDialog.minusAButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostChipSetupDialog.minusAButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.minusAButton.setIsTouched(false);
				}
			}
			if (mFL.hostChipSetupDialog.plusAButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostChipSetupDialog.plusAButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.plusAButton.setIsTouched(false);
				}
			}
			if (mFL.hostChipSetupDialog.minusBButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostChipSetupDialog.minusBButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.minusBButton.setIsTouched(false);
				}
			}
			if (mFL.hostChipSetupDialog.plusBButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostChipSetupDialog.plusBButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.plusBButton.setIsTouched(false);
				}
			}
			if (mFL.hostChipSetupDialog.minusCButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostChipSetupDialog.minusCButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.minusCButton.setIsTouched(false);
				}
			}
			if (mFL.hostChipSetupDialog.plusCButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostChipSetupDialog.plusCButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.plusCButton.setIsTouched(false);
				}
			}
			if (mFL.hostChipSetupDialog.minusBuyinButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostChipSetupDialog.minusBuyinButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.minusBuyinButton.setIsTouched(false);
				}
			}
			if (mFL.hostChipSetupDialog.plusBuyinButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostChipSetupDialog.plusBuyinButton.pointContained(touchX, touchY)) {
					mFL.hostChipSetupDialog.plusBuyinButton.setIsTouched(false);
				}
			}
			if (mFL.hostLobbyDialog.backButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostLobbyDialog.backButton.pointContained(touchX, touchY)) {
					mFL.hostLobbyDialog.backButton.setIsTouched(false);
				}
			}
			if (mFL.hostLobbyDialog.startButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostLobbyDialog.startButton.pointContained(touchX, touchY)) {
					mFL.hostLobbyDialog.startButton.setIsTouched(false);
				}
			}
			if (mFL.hostRearrangeDialog.doneButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostRearrangeDialog.doneButton.pointContained(touchX, touchY)) {
					mFL.hostRearrangeDialog.doneButton.setIsTouched(false);
				}
			}
			if (mFL.hostDestroyDialog.quitButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostDestroyDialog.quitButton.pointContained(touchX, touchY)) {
					mFL.hostDestroyDialog.quitButton.setIsTouched(false);
				}
			}
			if (mFL.hostDestroyDialog.cancelButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostDestroyDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.hostDestroyDialog.cancelButton.setIsTouched(false);
				}
			}
			if (mFL.playerBuyinDialog.minusButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerBuyinDialog.minusButton.pointContained(touchX, touchY)) {
					mFL.playerBuyinDialog.minusButton.setIsTouched(false);
				}
			}
			if (mFL.playerBuyinDialog.plusButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerBuyinDialog.plusButton.pointContained(touchX, touchY)) {
					mFL.playerBuyinDialog.plusButton.setIsTouched(false);
				}
			}
			if (mFL.playerBuyinDialog.cancelButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerBuyinDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.playerBuyinDialog.cancelButton.setIsTouched(false);
				}
			}
			if (mFL.playerBuyinDialog.okayButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerBuyinDialog.okayButton.pointContained(touchX, touchY)) {
					mFL.playerBuyinDialog.okayButton.setIsTouched(false);
				}
			}
			if (mFL.playerDashboard.backButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerDashboard.backButton.pointContained(touchX, touchY)) {
					mFL.playerDashboard.backButton.setIsTouched(false);
				}
			}
			if (mFL.playerDashboard.bellButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerDashboard.bellButton.pointContained(touchX, touchY)) {
					mFL.playerDashboard.bellButton.setIsTouched(false);
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
			if (mFL.playerLeaveDialog.leaveButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerLeaveDialog.leaveButton.pointContained(touchX, touchY)) {
					mFL.playerLeaveDialog.leaveButton.setIsTouched(false);
				}
			}
			if (mFL.playerLeaveDialog.cancelButton.getIsTouched()) {
				handled_=true;
				if (!mFL.playerLeaveDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.playerLeaveDialog.cancelButton.setIsTouched(false);
				}
			}
			for (int i=0;i<Table.NUM_SEATS;i++) {
				if (mFL.hostPlayerDialogs[i].bootButton.getIsTouched()) {
					handled_=true;
					if (!mFL.hostPlayerDialogs[i].bootButton.pointContained(touchX, touchY)) {
						mFL.hostPlayerDialogs[i].bootButton.setIsTouched(false);
					}
				}
				if (mFL.hostPlayerDialogs[i].foldButton.getIsTouched()) {
					handled_=true;
					if (!mFL.hostPlayerDialogs[i].foldButton.pointContained(touchX, touchY)) {
						mFL.hostPlayerDialogs[i].foldButton.setIsTouched(false);
					}
				}
				if (mFL.hostPlayerDialogs[i].okButton.getIsTouched()) {
					handled_=true;
					if (!mFL.hostPlayerDialogs[i].okButton.pointContained(touchX, touchY)) {
						mFL.hostPlayerDialogs[i].okButton.setIsTouched(false);
					}
				}
				if (mFL.hostPlayerDialogs[i].cancelButton.getIsTouched()) {
					handled_=true;
					if (!mFL.hostPlayerDialogs[i].cancelButton.pointContained(touchX, touchY)) {
						mFL.hostPlayerDialogs[i].cancelButton.setIsTouched(false);
					}
				}
			}
			if (mFL.hostPotDialog.splitButton.getIsTouched()) {
				handled_ = true;
				if (!mFL.hostPotDialog.splitButton.pointContained(touchX, touchY)) {
					mFL.hostPotDialog.splitButton.setIsTouched(false);
				}
			}
			if (mFL.hostPotDialog.cancelButton.getIsTouched()) {
				handled_ = true;
				if (!mFL.hostPotDialog.cancelButton.pointContained(touchX, touchY)) {
					mFL.hostPotDialog.cancelButton.setIsTouched(false);
				}
			}
			if (mFL.hostPotDialog.okButton.getIsTouched()) {
				handled_ = true;
				if (!mFL.hostPotDialog.okButton.pointContained(touchX, touchY)) {
					mFL.hostPotDialog.okButton.setIsTouched(false);
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
			if (mFL.foldButton.getIsTouched()) {
				handled_=true;
				if (!mFL.foldButton.pointContained(touchX, touchY)) {
					mFL.foldButton.setIsTouched(false);
				}
			}
			if (mFL.hostUndoButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostUndoButton.pointContained(touchX, touchY)) {
					mFL.hostUndoButton.setIsTouched(false);
				}
			}
			if (mFL.hostExitButton.getIsTouched()) {
				handled_=true;
				if (!mFL.hostExitButton.pointContained(touchX, touchY)) {
					mFL.hostExitButton.setIsTouched(false);
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

