package com.bidjee.digitalpokerchips.c;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.i.IPlayerNetwork;
import com.bidjee.digitalpokerchips.m.Button;
import com.bidjee.digitalpokerchips.m.Chip;
import com.bidjee.digitalpokerchips.m.ChipCase;
import com.bidjee.digitalpokerchips.m.ChipStack;
import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.DiscoveredTable;
import com.bidjee.digitalpokerchips.m.GameLogic;
import com.bidjee.digitalpokerchips.m.PlayerEntry;
import com.bidjee.digitalpokerchips.m.TextField;
import com.bidjee.digitalpokerchips.m.TextLabel;
import com.bidjee.util.Logger;

public class ThisPlayer {
	
	public static final String LOG_TAG = "DPCPlayer";
	
	//////////////////// Constants ////////////////////
	static final int[] defaultChipNums={8,4,2};
	
	public static final int DURATION_SEARCH_HOLDOFF = 2000;
	
	public static final int CONN_NONE = 0;
	public static final int CONN_IDLE = 1;
	public static final int CONN_SEARCHING = 2;
	public static final int CONN_BUYIN = 3;
	public static final int CONN_CONNECTING = 4;
	public static final int CONN_CONNECTED = 5;
	public static final int CONN_SEARCH_HOLDOFF = 6;
	
	//////////////////// State Variables ////////////////////
	public boolean sendingJoinToken;
	public boolean showStackTotals;
	private int connectivityStatus;
	public boolean wifiEnabled;
	public String tableName;
	public int color;
	public int[] chipNumbers;
	public boolean isDealer;
	private int searchHoldoffTimer;
	public int stake;
	public boolean betEnabled;
	public boolean waitingOnHost;
	public boolean checkEnabled;
	public boolean foldEnabled;
		
	//////////////////// Screen Scale & Layout ////////////////////
	float limYBetStackTop;
	float limYBetStackBottom;
	float limYBetStackCancel;
	Vector2 joinTokenStart=new Vector2();
	Vector2 joinTokenStop=new Vector2();
	Vector2 winStackOrigin=new Vector2();
	Vector2 betStackOrigin=new Vector2();
	float yDealerButtonOffscreen;
	float yDealerButtonOnscreen;
	
	//////////////////// Models ////////////////////
	public DiscoveredTable connectingTable;
	public DPCSprite plaque=new DPCSprite();
	public DPCSprite joinToken=new DPCSprite();
	public ChipStack[] mainStacks;
	public ChipStack bettingStack;
	public ChipStack betStack;
	public ChipStack cancellingStack;
	public ChipStack cancelStack;
	public Chip pickedUpChip;
	public DPCSprite connectionBlob=new DPCSprite();
	public DPCSprite dealerButton=new DPCSprite();
	public Button checkButton;
	
	//////////////////// Text Labels ////////////////////
	public TextField nameField;
	
	//////////////////// References ////////////////////
	WorldLayer mWL;
	IPlayerNetwork networkInterface;
	
	public ThisPlayer(WorldLayer mWL,IPlayerNetwork networkInterface) {
		this.mWL=mWL;
		this.networkInterface=networkInterface;
		networkInterface.setPlayer(this);
		showStackTotals=false;
		connectivityStatus=CONN_NONE;
		joinToken.opacity=0;
		mainStacks=new ChipStack[ChipCase.CHIP_TYPES];
		for (int chip_=ChipCase.CHIP_A;chip_<ChipCase.CHIP_TYPES;chip_++) {
			mainStacks[chip_]=new ChipStack();
			mainStacks[chip_].add(chip_,defaultChipNums[chip_]);
		}
		bettingStack=new ChipStack();
		betStack=new ChipStack();
		cancellingStack=new ChipStack();
		cancelStack=new ChipStack();
		pickedUpChip=null;
		nameField=new TextField("",0,false,false);
		nameField.label=new TextLabel("",0.01f,true,1,false);
		connectionBlob.opacity=0;
		connectionBlob.flashVisibleTime=100;
		connectionBlob.flashInvisibleTime=0;
		connectionBlob.setFadeInSpeed(1);
		connectionBlob.setFadeOutSpeed(3);
		dealerButton.opacity=0;
		checkButton=new Button(true,0,"");
		tableName="";
		
	}
	
	//////////////////// Scale & Layout ////////////////////
	public void setDimensions(float worldWidth_,float worldHeight_) {
		plaque.setDimensions((int)(worldWidth_*0.044f),(int)(worldHeight_*0.03f));
		joinToken.setDimensions((int)(worldHeight_*0.0288f),(int)(worldHeight_*0.0288f));
		nameField.setDimensions((int)(plaque.radiusX*0.8f),(int)(plaque.radiusY*0.9f));
		String tmp_=nameField.label.getText();
		nameField.label.setText(WorldLayer.NAME_MEASURE);
		nameField.label.setTextSizeToMax();
		nameField.label.setText(tmp_);
		mainStacks[ChipCase.CHIP_A].setMaxRenderNum(20);
		mainStacks[ChipCase.CHIP_A].scaleLabel();
		mainStacks[ChipCase.CHIP_B].setMaxRenderNum(20);
		mainStacks[ChipCase.CHIP_B].scaleLabel();
		mainStacks[ChipCase.CHIP_C].setMaxRenderNum(20);
		mainStacks[ChipCase.CHIP_C].scaleLabel();
		betStack.setMaxRenderNum(20);
		betStack.scaleLabel();
		connectionBlob.setDimensions((int)(worldHeight_*0.08f),(int)(worldHeight_*0.024f));
		dealerButton.setDimensions((int)(worldHeight_*0.022f),(int)(worldHeight_*0.022f));
		checkButton.setDimensions((int)(worldHeight_*0.1f),(int)(worldHeight_*0.04f));
	} // setDimensions(float width_,float height_)
	
	public void setPositions(float worldWidth_,float worldHeight_) {
		plaque.setPosition(worldWidth_*0.5f,worldHeight_*0.165f);
		joinToken.setPosition(worldWidth_*0.5f,worldHeight_*0.4f);
		nameField.setPosition(plaque.x,plaque.y);
		mainStacks[ChipCase.CHIP_A].setY(worldHeight_*0.25f);
		mainStacks[ChipCase.CHIP_B].setY(mainStacks[ChipCase.CHIP_A].getY());
		mainStacks[ChipCase.CHIP_C].setY(mainStacks[ChipCase.CHIP_A].getY());
		mainStacks[ChipCase.CHIP_A].setX(worldWidth_*0.43f);
		mainStacks[ChipCase.CHIP_B].setX(worldWidth_*0.5f);
		mainStacks[ChipCase.CHIP_C].setX(worldWidth_*0.57f);
		limYBetStackTop=mWL.camPosPlayer.getY()+mWL.worldRenderer.screenHeight*0.5f;
		limYBetStackBottom=mainStacks[ChipCase.CHIP_B].getY()+Chip.radiusY*2f;
		limYBetStackCancel=limYBetStackBottom+Chip.radiusY*0.2f;
		betStackOrigin.set(worldWidth_*0.5f,limYBetStackCancel+Chip.radiusY);
		betStack.setPosition(betStackOrigin.x,betStackOrigin.y,0);
		joinTokenStop.set(worldWidth_*0.5f,worldHeight_*0.5f);
		joinTokenStart.set(worldWidth_*0.5f,worldHeight_*0.38f);
		winStackOrigin.set(worldWidth_*0.5f,worldHeight_*0.63f);
		connectionBlob.setPosition(worldWidth_*0.5f,worldHeight_*0.425f);
		yDealerButtonOffscreen=worldHeight_*0.5f;
		yDealerButtonOnscreen=worldHeight_*0.25f;
		dealerButton.setPosition(worldWidth_*0.39f,yDealerButtonOffscreen);
		checkButton.setPosition(worldWidth_*0.5f,worldHeight_*0.35f);
	} // setPositions(float width_,float height_)
	
	public void scalePositions(float scaleX_,float scaleY_) {
		plaque.scalePosition(scaleX_,scaleY_);
		nameField.scalePosition(scaleX_,scaleY_);
		mainStacks[ChipCase.CHIP_A].scalePosition(scaleX_,scaleY_);
		mainStacks[ChipCase.CHIP_B].scalePosition(scaleX_,scaleY_);
		mainStacks[ChipCase.CHIP_C].scalePosition(scaleX_,scaleY_);
		betStack.scalePosition(scaleX_,scaleY_);
		if (pickedUpChip!=null) {pickedUpChip.scalePosition(scaleX_,scaleY_);}
		if (bettingStack.size()>0) {bettingStack.scalePosition(scaleX_,scaleY_);}
		if (cancellingStack.size()>0) {cancellingStack.scalePosition(scaleX_,scaleY_);}
		if (cancelStack.size()>0) {cancelStack.scalePosition(scaleX_,scaleY_);}
		connectionBlob.scalePosition(scaleX_,scaleY_);
	} // scalePositions(float scaleX_,float scaleY_)
	
	//////////////////// Controllers ////////////////////
	public void controlLogic() {
		
	}
	
	public void animate(float delta) {
		if (connectivityStatus==CONN_SEARCH_HOLDOFF) {
			searchHoldoffTimer+=delta*1000;
			if (searchHoldoffTimer>DURATION_SEARCH_HOLDOFF) {
				notifyReadyToSearch();
			}
		}
		joinToken.animate(delta);
		connectionBlob.animate(delta);
		checkButton.animate(delta);
		if (sendingJoinToken) {
			if (Math.abs(joinToken.y-joinTokenStop.y)<2) {
				
			} else {
				float deltaY_=delta*4*(joinToken.y-joinTokenStop.y);
				joinToken.y-=deltaY_;
			}
		}
		
		if (isDealer) {
			if (Math.abs(dealerButton.y-yDealerButtonOnscreen)<2) {
				;
			} else {
				float deltaY_=delta*5*(yDealerButtonOnscreen-dealerButton.y);
				dealerButton.y+=deltaY_;
			}
		} else {
			if (Math.abs(dealerButton.y-yDealerButtonOffscreen)<2) {
				;
			} else {
				float deltaY_=delta*5*(yDealerButtonOffscreen-dealerButton.y);
				dealerButton.y+=deltaY_;
			}
		}
		
		if (cancelStack.size()>0) {
			boolean allArrived_=true;
			for (int i=0;i<cancelStack.size();i++) {
				if (!cancelStack.get(i).animateToDest(delta)) {
					allArrived_=false;
				}
			}
			if (allArrived_) {
				for (int i=0;i<cancelStack.size();i++) {
					mainStacks[cancelStack.get(0).chipType].add(cancelStack.get(0));
					cancelStack.remove(0);
					i--;
				}
				updateStackTotals();
			}
			
		}
		if (cancellingStack.size()>0) {
			boolean allArrived=true;
			for (int i=0;i<cancellingStack.size();i++) {
				if (!cancellingStack.get(i).animateToDest(delta)) {
					allArrived=false;
				}
			}
			if (allArrived) {
				int[] chipCount={0,0,0};
				for (int i=0;i<cancellingStack.size();i++) {
					cancellingStack.get(i).setDest(mainStacks[cancellingStack.get(i).chipType].getX(),
							mainStacks[cancellingStack.get(i).chipType].getY(),
							mainStacks[cancellingStack.get(i).chipType].renderSize()+
							chipCount[cancellingStack.get(i).chipType]);
					chipCount[cancellingStack.get(i).chipType]++;
					cancelStack.add(cancellingStack.get(i));
				}
				cancellingStack.clear();
			}
			
		}
		for (int i=0;i<bettingStack.size();i++) {
			if (bettingStack.get(i).animateToDest(delta)) {
				if (i==0) {
					betStack.add(bettingStack.get(i));bettingStack.remove(i);
					i--;
					betStack.updateTotalLabel();
				}
			}
			
		}
		if (pickedUpChip!=null) {
			// calculate next position of chip, limit to maximum delta
			float timeFactor=delta*9;
			if (pickedUpChip.isTouched) {
				timeFactor*=3;
			}
			timeFactor=Math.min(timeFactor, 1);
			float xDelta=timeFactor*(pickedUpChip.destX-pickedUpChip.x);
			float yDelta=timeFactor*(pickedUpChip.destY-pickedUpChip.y);
			double dist=Math.sqrt(xDelta*xDelta+yDelta*yDelta);
			if (dist>Chip.radiusX) {
				double scale=Chip.radiusX/dist;
				xDelta*=scale;
				yDelta*=scale;
			}
			float yNew=(float)(pickedUpChip.y+yDelta);
			float xNew=(float)(pickedUpChip.x+xDelta);
			pickedUpChip.z=(float)(pickedUpChip.z-timeFactor*(pickedUpChip.z-pickedUpChip.destZ));
			// detect collision with other stacks
			if (!updatePUCCollisions(xNew,yNew,pickedUpChip.z)) {
				// if no collisions, set to new position
				pickedUpChip.x=xNew;
				pickedUpChip.y=yNew;
				// if not touched and at dest, return to stack
				if (!pickedUpChip.isTouched&&pickedUpChip.checkAtDest()) {
					pickedUpChip.setXYZtoDest();
					mainStacks[pickedUpChip.chipType].add(pickedUpChip);
					updateStackTotals();
					pickedUpChip=null;
				}
			}
		}
		
		// animate flung bet stack
		if (betStack.velocityY!=0) {
			betStack.setY(betStack.getY()+delta*betStack.velocityY);
		}
		
		for (int stack_=0;stack_<ChipCase.CHIP_TYPES;stack_++) {
			mainStacks[stack_].animate(delta);
		}
		betStack.animate(delta);
		bettingStack.animate(delta);
		cancellingStack.animate(delta);
		cancelStack.animate(delta);
	} // animate(float delta)
	
	public void collisionDetector() {
		if (sendingJoinToken) {
			if (joinToken.y>=limYBetStackTop) {
				joinToken.opacity=0;
				sendingJoinToken=false;
				connectivityStatus=CONN_CONNECTING;
				networkInterface.requestConnect(connectingTable,mWL.game.calculateAzimuth(),chipNumbers);
			}
		}
		if (betStack.size()>0) {
			if (betStack.getY()>=limYBetStackTop) {
				if ((betEnabled)&&!waitingOnHost&&(betStack.value()>=stake||isAllIn())) {
					if (betStack.getY()>limYBetStackTop) {
						submitBet();
					}
				} else {
					betStack.setY(limYBetStackTop);
					betStack.setVelocityY(0);
				}
			} else if (betStack.getY()<limYBetStackBottom) {
				betStack.setY(limYBetStackBottom);
				betStack.setVelocityY(0);
			}
			if (betStack.getY()<limYBetStackCancel&&!betStack.get(0).isTouched) {
				initiateCancelBet();
			}
		}
	} // collisionDetector()
	
	public boolean updatePUCCollisions(float xNew,float yNew,float z) {
		boolean collisionOccured=false;
		for (int chipType=ChipCase.CHIP_A;chipType<ChipCase.CHIP_TYPES;chipType++) {
			ChipStack thisStack=mainStacks[chipType];
			float xTestChip=thisStack.getX();
			float yTestChip=thisStack.getY();
			float zTestChip=0;
			if (thisStack.size()>0) {
				int testChipIndex=(int) Math.min(thisStack.renderSize()-1,pickedUpChip.z);
				zTestChip=thisStack.get(testChipIndex).z;
			}
			// check for overlap
			float yOverlap=Chip.testOverlap(xNew,yNew,z,xTestChip,yTestChip,zTestChip);
			if (yOverlap!=0) {
				// make change if available
				if (pickedUpChip.isTouched&&(pickedUpChip.chipType!=chipType)) {
					int numNeeded = ChipCase.values[chipType]/ChipCase.values[pickedUpChip.chipType]-1;
					if (mainStacks[pickedUpChip.chipType].size()>=numNeeded) {
						collisionOccured=true;
						int numToGain=Math.max(1,ChipCase.values[pickedUpChip.chipType]/ChipCase.values[chipType]);
						int numToLose=Math.max(0,ChipCase.values[chipType]/ChipCase.values[pickedUpChip.chipType]-1);
						mainStacks[chipType].add(chipType,numToGain);
						mainStacks[pickedUpChip.chipType].removeChipType(pickedUpChip.chipType,numToLose);
						pickedUpChip=null;
						mWL.soundFX.changeSound.play();
						updateStackTotals();
						break;
					}
				}
				if (pickedUpChip!=null) {
					if (thisStack.renderSize()-1>=z) {
						collisionOccured=true;
						// constrain to boundary if change isn't available
						pickedUpChip.x=xNew;
						pickedUpChip.y=yNew-yOverlap;
						// if constrained and touched, drop chip if no longer touched
						if (pickedUpChip.isTouched) {
							if (!pickedUpChip.pointContained(mWL.input.lastTouch.x,mWL.input.lastTouch.y)){
								pickedUpChip.isTouched=false;
								doPickedUpChipDropped();
								break;
							}
						}
					}
				}
			}
		}
		return collisionOccured;
	}
	
	//////////////////// Internal Methods ////////////////////
	private void updateStackTotals() {
		for (int chip_=ChipCase.CHIP_A;chip_<ChipCase.CHIP_TYPES;chip_++) {
			mainStacks[chip_].updateTotalLabel();
		}
	}
	
	private boolean isAllIn() {
		return ((mainStacks[ChipCase.CHIP_A].size()==0)&&
				(mainStacks[ChipCase.CHIP_B].size()==0)&&
				(mainStacks[ChipCase.CHIP_C].size()==0));
	}
	
	private void initiateCancelBet() {
		int[] chipCount={0,0,0};
		float[] ys_=new float[ChipCase.CHIP_TYPES];
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			if (mainStacks[i].size()>0) {
				ys_[i]=mainStacks[i].getY()+mainStacks[i].getLastRendered().getProjectedRadiusY()*2;
			} else {
				ys_[i]=mainStacks[i].getY()+Chip.radiusY*2;
			}
		}
		for (Chip chip : betStack.stack) {
			chip.setDest(mainStacks[chip.chipType].getX(),
					ys_[chip.chipType],
					mainStacks[chip.chipType].renderSize()+chipCount[chip.chipType]);
			chipCount[chip.chipType]++;
			cancellingStack.add(chip);
		}
		betStack.clear();
		betStack.setPosition(betStackOrigin.x,betStackOrigin.y,0);
		if (foldEnabled) {
			mWL.game.mFL.foldButton.fadeIn();
			mWL.game.mFL.foldButton.setTouchable(true);
		}
		if (checkEnabled) {
			checkButton.fadeIn();
			checkButton.setTouchable(true);
		}
	}
	
	private void removeLastFromMainStack(int chip_) {
		mainStacks[chip_].removeLast();
		updateStackTotals();
	}
	
	private void addBetChip(Chip chip) {
		int numTop_=Math.min(betStack.size()+bettingStack.size(),betStack.maxRenderNum);
		chip.setDest(betStack.getX(),betStack.getY(),numTop_);
		bettingStack.add(chip);
		checkButton.fadeOut();
		checkButton.setTouchable(false);
		mWL.game.mFL.foldButton.fadeOut();
		mWL.game.mFL.foldButton.setTouchable(false);
	}
	
	private void enableBet() {
		betEnabled=true;
	}
	private void disableBet() {
		betEnabled=false;
	}
	private void enableCheck() {
		checkEnabled=true;
		checkButton.fadeIn();
		checkButton.setTouchable(true);
	}
	private void disableCheck() {
		checkEnabled=false;
		checkButton.fadeOut();
		checkButton.setTouchable(false);
	}
	private void enableFold() {
		foldEnabled=true;
		mWL.game.mFL.foldButton.fadeIn();
		mWL.game.mFL.foldButton.setTouchable(true);
	}
	private void disableFold() {
		foldEnabled=false;
		mWL.game.mFL.foldButton.fadeOut();
		mWL.game.mFL.foldButton.setTouchable(false);
	}
	
	private void notifyTableDisconnected() {
		cancelMoveState();
		setDealer(false);
		waitingOnHost=false;
		mWL.game.mFL.reconnect1Label.fadeOut();
		mWL.game.mFL.reconnect1Label.opacity=0;
		mWL.game.mFL.reconnect2Label.fadeOut();
		mWL.game.mFL.reconnect2Label.opacity=0;
		// TODO if table status menu open will hold touch focus!
		mWL.game.mFL.tableStatusMenu.remove();
		mWL.game.mFL.stopWaitNextHand();
		Logger.log(LOG_TAG,"notifyTableDisconnected()");
	}
	
	private void cancelMoveState() {
		Logger.log(LOG_TAG,"notifyTableDisconnected()");
		mWL.game.mFL.stateChangeACKed();
		connectionBlob.fadeOut();
		connectionBlob.opacity=0;
		disableBet();
		disableCheck();
		disableFold();
		mWL.game.mFL.hideTextMessage();
	}
	
	private void clearAllStacks() {
		mainStacks[ChipCase.CHIP_A].clear();
		mainStacks[ChipCase.CHIP_B].clear();
		mainStacks[ChipCase.CHIP_C].clear();
		betStack.clear();
		betStack.setPosition(betStackOrigin.x,betStackOrigin.y,0);
		bettingStack.clear();
		cancellingStack.clear();
		cancelStack.clear();
		pickedUpChip=null;
	}
	
	//////////////////// Input to Player Messages ////////////////////
	public void nameDone() {
		if (nameField.label.getText().equals("")) {
			int num_=(int) (Math.random()*9+1);
			nameField.setText("Player "+num_);
			networkInterface.setName(nameField.getText());
		}
		mWL.sendCameraTo(mWL.camPosPlayer);
	}
	
	public void plaqueTouched() {
		if (connectivityStatus==CONN_SEARCHING) {
			stopSearchForGames();
		}
		mWL.sendCameraTo(mWL.camPosPlayersName);
	}
	
	public void buyinDialogDone(int[] chipNumbers) {
		mWL.game.mFL.stopBuyin();
		if (chipNumbers!=null) {
			sendJoinToken(chipNumbers);
		} else {
			joinToken.fadeOut();
			joinToken.opacity=0;
			searchHoldoff();
		}
	}
	
	public void searchingAnimationClicked() {
		mWL.game.mFL.startManualConnectDialog();
	}
	
	public void manualConnectDialogDone(boolean actionCompleted) {
		mWL.game.mFL.stopManualConnectDialog();
		byte[] hostBytes=mWL.game.mFL.manualConnectDialog.getHostBytes();
		if (actionCompleted&&hostBytes!=null) {
			networkInterface.requestInvitation(hostBytes);
		}
	}
	
	public void leaveButtonPressed() {
		doLeaveDialog();
	}
	
	public void doPickedUpChipDropped() {
		boolean isBet_=true;
		float xPUC_=pickedUpChip.x;
		float yPUC_=pickedUpChip.getProjectedY();
		float radiusXPUC_=pickedUpChip.getProjectedRadiusX();
		float radiusYPUC_=pickedUpChip.getProjectedRadiusY();
		
		float left_=mainStacks[ChipCase.CHIP_A].getX();
		float right_=mainStacks[ChipCase.CHIP_C].getX();
		float bottom_=limYBetStackBottom;
		
		if (xPUC_<left_||xPUC_>right_||yPUC_<bottom_) {
			isBet_=false;
		} else {
			// check if over stacks
			for (int chipType_=0;chipType_<ChipCase.CHIP_TYPES;chipType_++) {
				if (mainStacks[chipType_].renderSize()<=pickedUpChip.z&&mainStacks[chipType_].size()>0) {
					int chip_=mainStacks[chipType_].renderSize()-1;
					float x_=mainStacks[chipType_].getX();
					float y_=mainStacks[chipType_].get(chip_).getProjectedY();
					int radiusX_=mainStacks[chipType_].get(chip_).getProjectedRadiusX();
					int radiusY_=mainStacks[chipType_].get(chip_).getProjectedRadiusY();
					if (Math.abs(xPUC_-x_)<radiusXPUC_+radiusX_) {
						float dx1_=Math.abs(xPUC_-x_)*radiusX_/(radiusX_+radiusXPUC_);
						float dy1_=(float) (Math.sqrt(((radiusX_*radiusX_-dx1_*dx1_)*radiusY_*radiusY_)/(radiusX_*radiusX_)));
						float dy2_=dy1_*(radiusYPUC_/radiusY_);
						float dy_=dy1_+dy2_;
						if (yPUC_>=y_-dy_&&yPUC_<=y_+dy_) {
							isBet_=false;
						}
					}
				}
			}
		}
		if (isBet_) {
			addBetChip(pickedUpChip);
			pickedUpChip=null;
		} else {
			float destZ_=mainStacks[pickedUpChip.chipType].renderSize()+cancellingStack.size()+cancelStack.size();
			pickedUpChip.setDest(mainStacks[pickedUpChip.chipType].getX(),
					mainStacks[pickedUpChip.chipType].getY(),destZ_);
		}
	}
	
	public void doPickedUpChipFlung() {
		float xPUC=pickedUpChip.x;
		float yPUC=pickedUpChip.getProjectedY();
		float left=mainStacks[ChipCase.CHIP_A].getX()+Chip.radiusX*0.3f;
		float right=mainStacks[ChipCase.CHIP_C].getX()-Chip.radiusX*0.3f;
		float bottom=mainStacks[ChipCase.CHIP_A].getY()+Chip.radiusY*0.3f;
		
		if (xPUC>left&&xPUC<right&&yPUC>bottom) {
			addBetChip(pickedUpChip);
			pickedUpChip=null;
		} else {
			doPickedUpChipDropped();
		}
	}
	
	public void setPickedUpChip(int chip_,float destX_,float destY_) {
		pickedUpChip=mainStacks[chip_].getLast();
		if (pickedUpChip.z>mainStacks[chip_].maxRenderNum) {
			pickedUpChip.z=mainStacks[chip_].maxRenderNum;
		}
		float destZ=pickedUpChip.z;
		int zBetStackTop=betStack.renderSize()+bettingStack.size();
		if (zBetStackTop>pickedUpChip.z) {
			destZ=zBetStackTop;
		}
		pickedUpChip.setDest(destX_,destY_,destZ);
		removeLastFromMainStack(chip_);
	}
	
	public void sendJoinToken(int[] chipNumbers) {
		this.chipNumbers=chipNumbers;
		sendingJoinToken=true;
	}
	
	public void toggleStackTotals() {
		showStackTotals=!showStackTotals;
	}
	
	public void stateChangeACKed() {
		mWL.game.mFL.stateChangeACKed();
		promptMove(stake,foldEnabled,mWL.game.mFL.playerPrompt.getText());
	}
	
	public void bellButtonPressed() {
		String hostName=mWL.game.mFL.tableStatusMenu.nudgeHostName;
		networkInterface.sendBell(hostName);
	}
	
	//////////////////// World to Player Messages ////////////////////
	public void notifyAtPlayerPosition() {
		mWL.game.mFL.notifyAtPlayerPosition();
		if (nameField.label.getText().equals("")) {
			mWL.sendCameraTo(mWL.camPosPlayersName);
		} else if (connectivityStatus==CONN_NONE) {
			connectivityStatus=CONN_IDLE;
			plaque.setTouchable(true);
			if (wifiEnabled) {
				notifyReadyToSearch();
			} else {
				wifiOff();
			}
		}
	}
	
	public void notifyLeftPlayerPosition() {
		mWL.game.mFL.notifyLeftPlayerPosition();
		mWL.game.mFL.stopWifiPrompt();
		dealerButton.opacity=0;
		connectivityStatus=CONN_NONE;
	}
	
	public void notifyAtNamePosition() {
		mWL.game.mFL.startEnterPlayerName();
		Gdx.input.setOnscreenKeyboardVisible(true);
		mWL.input.setTypingFocus(WorldInput.TYPING_PLAYER_NAME);
	}
	
	public void notifyLeftNamePosition() {
		mWL.game.mFL.stopEnterPlayerName();
		mWL.input.setTypingFocus(WorldInput.TYPING_NOTHING);
		Gdx.input.setOnscreenKeyboardVisible(false);
	}
	
	/** Notify the player to handle the back press
	 * @return return true if the camera may leave the area
	 */
	public boolean backPressed() {
		boolean playerFinished=true;
		if (connectivityStatus==CONN_NONE) {
			
		} else if (connectivityStatus==CONN_IDLE) {
			
		} else if (connectivityStatus==CONN_SEARCHING) {
			stopSearchForGames();
		} else if (connectivityStatus==CONN_SEARCH_HOLDOFF) {
			;
		} else if (connectivityStatus==CONN_CONNECTING) {
			playerFinished=false;
		} else if (connectivityStatus==CONN_CONNECTED) {
			doLeaveDialog();
			playerFinished=false;
		}
		return playerFinished;
	}
	
	public void notifyReadyToSearch() {
		// TODO deal with case where player zooms out at any time in the process
		Logger.log(LOG_TAG,"notifyReadyToSearch()");
		if (connectivityStatus==CONN_IDLE||connectivityStatus==CONN_SEARCH_HOLDOFF) {
			connectivityStatus=CONN_SEARCHING;
			mWL.game.mFL.startSearchForGames();
			networkInterface.startRequestGames();
		}
	}
	
	public void searchHoldoff() {
		Logger.log(LOG_TAG,"searchHoldoff()");
		connectivityStatus=CONN_SEARCH_HOLDOFF;
		searchHoldoffTimer=0;
	}
	
	public int getConnectivityStatus() {
		return connectivityStatus;
	}
	
	public void stopSearchForGames() {
		Logger.log(LOG_TAG,"stopSearchForGames()");
		connectivityStatus=CONN_IDLE;
		mWL.game.mFL.stopSearchForGames();
		networkInterface.stopRequestGames();
	}
	
	public void wifiOn() {
		wifiEnabled=true;
		if (connectivityStatus==CONN_IDLE) {
			mWL.game.mFL.stopWifiPrompt();
			notifyReadyToSearch();
		} else if (connectivityStatus==CONN_CONNECTED) {
			mWL.game.mFL.stopWifiPrompt();
			// TODO deal with the case during the game, later
		}
		// TODO deal with the other cases e.g. connecting and buyin
	}
	
	public void wifiOff() {
		wifiEnabled=false;
		if (connectivityStatus==CONN_IDLE) {
			mWL.game.mFL.startWifiPrompt();
		} else if (connectivityStatus==CONN_SEARCHING) {
			stopSearchForGames();
			mWL.game.mFL.startWifiPrompt();
		} else if (connectivityStatus==CONN_CONNECTED) {
			mWL.game.mFL.startWifiPrompt();
		}
		// TODO deal with the other cases e.g. connecting and buyin
	}
	
	//////////////////// Player to Table Messages ////////////////////
	public void appendName(char character) {
		if (character=='\b') {
			nameField.backspace();
		} else if (character=='\n') {
			nameDone();
		} else if ((int)(character)!=0) {
			nameField.append(""+character);
		}
		networkInterface.setName(nameField.getText());
	}
	
	private void submitBet() {
		Logger.log(LOG_TAG,"submitBet()");
		int move;
		if (!isAllIn()) {
			move=GameLogic.MOVE_BET;
		} else {
			move=GameLogic.MOVE_ALL_IN;
		}
		networkInterface.submitMove(move,betStack.toString());
		betStack.clear();
		betStack.setPosition(betStackOrigin.x,betStackOrigin.y,0);
		disableBet();
		disableCheck();
		disableFold();
		mWL.game.mFL.hideTextMessage();
		connectionBlob.fadeOut();
	}
	
	public void doCheck() {
		Logger.log(LOG_TAG,"doCheck()");
		if (!waitingOnHost) {
			mWL.soundFX.checkSound.play();
			networkInterface.submitMove(GameLogic.MOVE_CHECK,"");
			disableBet();
			disableCheck();
			disableFold();
			connectionBlob.fadeOut();
			mWL.game.mFL.hideTextMessage();
		}
	}
	
	public void doFold() {
		Logger.log(LOG_TAG,"doFold()");
		if (!waitingOnHost) {
			mWL.soundFX.foldSound.play();
			networkInterface.submitMove(GameLogic.MOVE_FOLD,"");
			disableBet();
			disableCheck();
			disableFold();
			connectionBlob.fadeOut();
			mWL.game.mFL.hideTextMessage();
		}
	}
	
	public void doLeaveDialog() {
		mWL.game.mFL.startLeaveTableDialog(tableName);
	}
	
	public void leaveDialogDone(boolean actionCompleted) {
		mWL.game.mFL.stopLeaveTableDialog();
		if (actionCompleted) {
			leaveTable();
		}
	}
	
	public void leaveTable() {
		if (connectivityStatus==CONN_CONNECTED) {
			networkInterface.leaveTable();
			notifyTableDisconnected();
			searchHoldoff();
		}
	}
	
	//////////////////// Table to Player Messages ////////////////////
	public void notifyTableFound(DiscoveredTable table,boolean loadedGame) {
		if (connectivityStatus==CONN_SEARCHING) {
			connectivityStatus=CONN_BUYIN;
			connectingTable=table;
			stopSearchForGames();
			clearAllStacks();
			ChipCase.values=table.vals;
			joinToken.fadeIn();
			joinToken.setPosition(joinTokenStart);
			mWL.game.mFL.startBuyin(table.getName(),loadedGame);
		}
		Logger.log(LOG_TAG,"notifyTableFound("+table.getName()+","+loadedGame+")");
	}
	
	public void notifyConnectResult(boolean result,String tableName) {
		Logger.log(LOG_TAG,"notifyConnectResult("+result+")");
		if (result) {
			connectivityStatus=CONN_CONNECTED;
			this.tableName=tableName;
			plaque.setTouchable(false);
		} else {
			// TODO notify player
			connectivityStatus=CONN_IDLE;
			searchHoldoff();
			mWL.game.mFL.stopBuyin();
		}
		connectingTable=null;
	}
	
	public void notifyConnectionLost() {
		Logger.log(LOG_TAG,"notifyConnectionLost()");
		waitingOnHost=true;
		cancelMoveState();
		mWL.game.mFL.startReconnect();
	}
	
	public void notifyReconnected() {
		Logger.log(LOG_TAG,"notifyReconnected()");
		waitingOnHost=false;
		mWL.game.mFL.stopReconnect();
	}
	
	public void syncStatusMenu(ArrayList<PlayerEntry> playerList) {
		mWL.game.mFL.tableStatusMenu.syncStatusMenu(playerList);
	}
	
	public void setupChips(ChipStack setupStack,int color) {
		Logger.log(LOG_TAG,"setupChips()");
		clearAllStacks();
		mainStacks[ChipCase.CHIP_A].setOpacity(1);
		mainStacks[ChipCase.CHIP_B].setOpacity(1);
		mainStacks[ChipCase.CHIP_C].setOpacity(1);
		betStack.setOpacity(1);
		bettingStack.setOpacity(1);
		cancellingStack.setOpacity(1);
		cancelStack.setOpacity(1);
		doWin(setupStack);
		this.color=color;
		mWL.game.mFL.showTableStatusMenu(tableName);
		mWL.game.mFL.stopWaitNextHand();
	}
	
	public void setDealer(boolean isDealer) {
		Logger.log(LOG_TAG,"setupChips("+isDealer+")");
		this.isDealer=isDealer;
		if (isDealer) {
			dealerButton.opacity=1;
		}
	}
	
	public void notifyWaitNextHand() {
		Logger.log(LOG_TAG,"notifyWaitNextHand()");
		mWL.game.mFL.startWaitNextHand();
	}
	
	public void promptMove(int betStake,boolean foldEnabled,String message) {
		Logger.log(LOG_TAG,"promptMove("+betStack+","+foldEnabled+","+message+")");
		if (foldEnabled) {
			enableFold();
		}
		if (betStake==0) {
			enableCheck();
			if (betStack.size()>0) {
				checkButton.setTouchable(false);
				checkButton.fadeOut();
			}
		}
		enableBet();
		stake=betStake;
		mWL.soundFX.bellSound.play();
		textMessage(message);
		connectionBlob.fadeIn();
	}
	
	public void promptStateChange(String messageStateChange,int stake,boolean foldEnabled,String message) {
		Logger.log(LOG_TAG,"promptStateChange("+messageStateChange+","+stake+","+foldEnabled+","+message+")");
		mWL.game.mFL.promptStateChange(messageStateChange);
		this.stake=stake;
		this.foldEnabled=foldEnabled;
		mWL.game.mFL.playerPrompt.setText(message);
	}
	
	public void cancelMove() {
		cancelMoveState();
	}
	
	public void doWin(ChipStack winStack_) {
		Logger.log(LOG_TAG,"doWin()");
		int[] chipCount={0,0,0};
		for (int i=0;i<winStack_.size();i++) {
			winStack_.get(i).x=winStackOrigin.x;
			winStack_.get(i).y=winStackOrigin.y;
			winStack_.get(i).setDest(mainStacks[winStack_.get(i).chipType].getX(),
					mainStacks[winStack_.get(i).chipType].getY()+Chip.radiusY*2+
					(mainStacks[winStack_.get(i).chipType].renderSize()+
							chipCount[winStack_.get(i).chipType])*1,
					mainStacks[winStack_.get(i).chipType].renderSize()+chipCount[winStack_.get(i).chipType]);
			chipCount[winStack_.get(i).chipType]++;
			cancellingStack.add(winStack_.get(i));
		}
		if (pickedUpChip!=null) {
			float newZ_=mainStacks[pickedUpChip.chipType].renderSize()+cancellingStack.size();
			if (newZ_>pickedUpChip.z) {
				pickedUpChip.setDestZ(newZ_);
			}
		}
	}

	public void textMessage(String message) {
		Logger.log(LOG_TAG,"textMessage("+message+")");
		if (message.equals("")) {
			mWL.game.mFL.hideTextMessage();
		} else {
			mWL.game.mFL.showTextMessage(message);
		}
	}
	
	public void doBell() {
		Gdx.input.vibrate(300);
		mWL.soundFX.bellSound.play();
	}
	
	public void notifyBootedByHost() {
		Logger.log(LOG_TAG,"notifyBootedByHost()");
		if (connectivityStatus==CONN_CONNECTED) {
			connectivityStatus=CONN_IDLE;
			notifyTableDisconnected();
			notifyReadyToSearch();
		}
	}

	public void enableNudge(String hostName) {
		mWL.game.mFL.tableStatusMenu.enableNudge(hostName);
	}
	
	public void disableNudge() {
		mWL.game.mFL.tableStatusMenu.disableNudge();
	}

	public void showConnection() {
		connectionBlob.fadeIn();
	}
	
	public void hideConnection() {
		connectionBlob.fadeOut();
	}
	
}
