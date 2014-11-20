package com.bidjee.digitalpokerchips.c;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.i.IPlayerNetwork;
import com.bidjee.digitalpokerchips.m.Button;
import com.bidjee.digitalpokerchips.m.Chip;
import com.bidjee.digitalpokerchips.m.ChipCase;
import com.bidjee.digitalpokerchips.m.ChipStack;
import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.DiscoveredTable;
import com.bidjee.digitalpokerchips.m.GameLogic;
import com.bidjee.digitalpokerchips.m.Move;
import com.bidjee.digitalpokerchips.m.MovePrompt;
import com.bidjee.digitalpokerchips.m.PlayerDashboard;
import com.bidjee.digitalpokerchips.m.PlayerEntry;
import com.bidjee.util.Logger;

public class ThisPlayer {
	
	public static final String LOG_TAG = "DPCPlayer";
	
	//////////////////// Constants ////////////////////
	static final int[] defaultChipNums={0,0,0};
	
	static final Color blackColor = new Color(0,0,0,1);
	
	public static final int DURATION_SEARCH_HOLDOFF = 2000;
	static final int RECONNECT_INTERVAL = 3000;
	
	public static final int CONN_NONE = 0;
	public static final int CONN_LOGIN = 1;
	public static final int CONN_IDLE = 2;
	public static final int CONN_SEARCH_HOLDOFF = 3;
	public static final int CONN_SEARCHING = 4;
	public static final int CONN_BUYIN = 5;
	public static final int CONN_CONNECTING = 6;
	public static final int CONN_CONNECTED = 7;
	public static final int CONN_POLL_RECONNECT = 8;
	public static final int CONN_CONNECTED_NO_WIFI = 9;
	
	public static final int CONN_ANIM_NONE = 0;	
	public static final int CONN_ANIM_FORMING = 1;
	public static final int CONN_ANIM_FORMING_ACTIVE = 2;
	public static final int CONN_ANIM_STATIC = 3;
	public static final int CONN_ANIM_ACTIVE = 4;
	public static final int CONN_ANIM_RECONNECTING = 5;
	
	//////////////////// State Variables ////////////////////
	public boolean buyinPending;
	public boolean showStackTotals;
	private int connectivityStatus;
	public boolean wifiEnabled;
	public String tableName;
	public int color;
	public boolean isDealer;
	private int searchHoldoffTimer;
	private int reconnectTimer;
	public int stake;
	public boolean betEnabled;
	public boolean waitingOnHost;
	public boolean checkEnabled;
	public boolean foldEnabled;
	int chipAmount;
	int connectionAnimationState;
	String betterName;
		
	//////////////////// Screen Scale & Layout ////////////////////
	float x;
	float y;
	int radiusX;
	int radiusY;
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
	String playerName;
	public DiscoveredTable connectingTable;
	public ChipStack[] mainStacks;
	
	public ChipStack bettingStack;
	public ChipStack betStack;
	public ChipStack cancellingStack;
	public ChipStack cancelStack;
	public Chip pickedUpChip;
	public DPCSprite connectionSprite=new DPCSprite();
	public DPCSprite connectedSprite=new DPCSprite();
	public DPCSprite dealerButton=new DPCSprite();
	public Button checkButton;
	
	//////////////////// Text Labels ////////////////////
	
	//////////////////// References ////////////////////
	WorldLayer mWL;
	IPlayerNetwork networkInterface;
	
	public ThisPlayer(WorldLayer mWL,IPlayerNetwork networkInterface) {
		this.mWL=mWL;
		this.networkInterface=networkInterface;
		networkInterface.setPlayer(this);
		showStackTotals=false;
		connectivityStatus=CONN_NONE;
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
		playerName="";
		connectionSprite.setFrameAnimation(27,50,false,0);
		connectionSprite.opacity=0;
		connectedSprite.setFrameAnimation(26,40,true,0);
		connectedSprite.opacity=0;
		dealerButton.opacity=0;
		checkButton=new Button(true,0,"");
		tableName="";
		
	}
	
	//////////////////// Scale & Layout ////////////////////
	public void setDimensions(int radiusX,int radiusY) {
		this.radiusX=radiusX;
		this.radiusY=radiusY;
		mainStacks[ChipCase.CHIP_A].setMaxRenderNum(20);
		mainStacks[ChipCase.CHIP_A].scaleLabel();
		mainStacks[ChipCase.CHIP_B].setMaxRenderNum(20);
		mainStacks[ChipCase.CHIP_B].scaleLabel();
		mainStacks[ChipCase.CHIP_C].setMaxRenderNum(20);
		mainStacks[ChipCase.CHIP_C].scaleLabel();
		
		betStack.setMaxRenderNum(20);
		betStack.scaleLabel();
		connectionSprite.setDimensions((int)(radiusY*0.22f*1.6f),(int)(radiusY*0.22f));
		connectedSprite.setDimensions((int)(radiusY*0.22f*1.6f),(int)(radiusY*0.22f));
		dealerButton.setDimensions((int)(radiusY*0.15f),(int)(radiusY*0.15f));
		checkButton.setDimensions((int)(radiusY*0.3f*2f),(int)(radiusY*0.3f));
	} // setDimensions(float width_,float height_)
	
	public void setPositions(float x,float y) {
		float yStacks = y-radiusY*0.2f;
		mainStacks[ChipCase.CHIP_A].setY(yStacks);
		mainStacks[ChipCase.CHIP_B].setY(yStacks);
		mainStacks[ChipCase.CHIP_C].setY(yStacks);
		int stackSpacing = (int) (radiusX*0.30f);
		mainStacks[ChipCase.CHIP_A].setX(x-stackSpacing);
		mainStacks[ChipCase.CHIP_B].setX(x);
		mainStacks[ChipCase.CHIP_C].setX(x+stackSpacing);

		limYBetStackTop=y+radiusY*0.8f;
		limYBetStackBottom=mainStacks[ChipCase.CHIP_B].getY()+Chip.radiusY*2f;
		limYBetStackCancel=limYBetStackBottom+Chip.radiusY*0.2f;
		betStackOrigin.set(x,limYBetStackCancel+Chip.radiusY*0.3f);
		betStack.setPosition(betStackOrigin.x,betStackOrigin.y,0);
		//joinTokenStop.set(worldWidth_*0.5f,worldHeight_*0.5f);
		//joinTokenStart.set(worldWidth_*0.5f,worldHeight_*0.38f);
		winStackOrigin.set(x,y+radiusY*1.1f);
		connectionSprite.setPosition(x,y+radiusY*0.63f);
		connectedSprite.setPosition(x,y+radiusY*0.63f);
		yDealerButtonOffscreen=y+radiusY*1.5f;  
		yDealerButtonOnscreen=y-radiusY*0.3f;
		dealerButton.setPosition(x-radiusX*0.49f,yDealerButtonOffscreen);
		checkButton.setPosition(x,y+radiusY*0.4f);
	} // setPositions(float width_,float height_)
	
	public void scalePositions(float scaleX_,float scaleY_) {
		mainStacks[ChipCase.CHIP_A].scalePosition(scaleX_,scaleY_);
		mainStacks[ChipCase.CHIP_B].scalePosition(scaleX_,scaleY_);
		mainStacks[ChipCase.CHIP_C].scalePosition(scaleX_,scaleY_);
		betStack.scalePosition(scaleX_,scaleY_);
		if (pickedUpChip!=null) {pickedUpChip.scalePosition(scaleX_,scaleY_);}
		if (bettingStack.size()>0) {bettingStack.scalePosition(scaleX_,scaleY_);}
		if (cancellingStack.size()>0) {cancellingStack.scalePosition(scaleX_,scaleY_);}
		if (cancelStack.size()>0) {cancelStack.scalePosition(scaleX_,scaleY_);}
		//connectionBlob.scalePosition(scaleX_,scaleY_);
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
		} else if (connectivityStatus==CONN_POLL_RECONNECT) {
			reconnectTimer+=delta*1000;
			if (reconnectTimer>=RECONNECT_INTERVAL) {
				networkInterface.requestConnect(connectingTable,mWL.game.calculateAzimuth(),null);
				reconnectTimer=0;
			}
		}
		connectionSprite.animate(delta);
		if (connectionAnimationState==CONN_ANIM_FORMING) {
			if (!connectionSprite.frameAnimationRunning) {
				setConnectionAnimationState(CONN_ANIM_STATIC);
			}
		} else if (connectionAnimationState==CONN_ANIM_FORMING_ACTIVE) {
			if (!connectionSprite.frameAnimationRunning) {
				setConnectionAnimationState(CONN_ANIM_ACTIVE);
			}
		}
		connectedSprite.animate(delta);
		checkButton.animate(delta);
		
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
					mWL.game.mFL.betTotalDialog.setAmount(betStack.value());
					if (betStack.size()==1) {
						mWL.game.mFL.betTotalDialog.show();
					}
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
		if (connectionAnimationState==CONN_ANIM_RECONNECTING) {
			connectedSprite.opacity=mWL.game.mFL.playerDashboard.statusMessage.opacity;
		}
	} // animate(float delta)
	
	public void collisionDetector() {
		if (buyinPending) {
			if (mWL.game.mFL.checkBuyinOffscreen()) {
				buyinPending=false;
				connectivityStatus=CONN_CONNECTING;
				int[] chipNumbers=calculateSimpleBuyin(mWL.game.mFL.buyinDialog.getAmount());
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
				(mainStacks[ChipCase.CHIP_C].size()==0)&&
				bettingStack.size()==0&&
				cancellingStack.size()==0&&
				cancelStack.size()==0);
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
		mWL.game.mFL.betTotalDialog.hide();
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
		if (betStack.size()==0) {
			mWL.game.mFL.foldButton.fadeIn();
			mWL.game.mFL.foldButton.setTouchable(true);
		}
	}
	private void disableFold() {
		foldEnabled=false;
		mWL.game.mFL.foldButton.fadeOut();
		mWL.game.mFL.foldButton.setTouchable(false);
	}
	
	private void notifyTableDisconnected() {
		Logger.log(LOG_TAG,"notifyTableDisconnected()");
		cancelMoveState();
		setDealer(false);
		waitingOnHost=false;
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			mWL.game.mFL.mainStackValueLabels[i].setText("");
			mWL.game.mFL.mainStackValueLabels[i].loadTexture();
			
		}
		mWL.game.mFL.reconnect1Label.fadeOut();
		mWL.game.mFL.reconnect1Label.opacity=0;
		mWL.game.mFL.reconnect2Label.fadeOut();
		mWL.game.mFL.reconnect2Label.opacity=0;
		// TODO if table status menu open will hold touch focus!
		mWL.game.mFL.tableStatusMenu.remove();
		mWL.game.mFL.stopWaitNextHand();
		setConnectionAnimationState(CONN_ANIM_NONE);
	}
	
	private void cancelMoveState() {
		Logger.log(LOG_TAG,"cancelMoveState()");
		// TODO change connection sprite
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
		mWL.game.mFL.betTotalDialog.hide();
		bettingStack.clear();
		cancellingStack.clear();
		cancelStack.clear();
		pickedUpChip=null;
		setPlayerAmount(0);
	}
	
	//////////////////// Input to Player Messages ////////////////////
	public void setPlayerName(String playerName) {
		this.playerName=playerName;
		networkInterface.setName(playerName);
	}
	
	public void setPlayerAmount(int amount) {
		chipAmount=amount;
		mWL.game.mFL.playerDashboard.setAmount(amount);
	}
	
	public void playerLoginDone(String playerName,Texture tex) {
		if (connectivityStatus==CONN_LOGIN) {
			mWL.game.mFL.stopPlayerLoginDialog();
			setPlayerName(playerName);
			setPlayerAmount(0);
			if (tex!=null) {
				mWL.game.mFL.playerDashboard.setProfilePicTexture(tex);
			}
			mWL.game.mFL.showPlayerDashboard(playerName);
			connectivityStatus=CONN_IDLE;
			notifyReadyToSearch();
		}
	}
	
	public void buyinDialogDone(boolean actionCompleted) {
		mWL.game.mFL.stopBuyin();
		if (actionCompleted) {
			buyinPending=true;
		} else {
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
	
	public void playerLoginDone(boolean actionCompleted) {
		mWL.game.mFL.stopPlayerLoginDialog();
		mWL.sendCameraTo(mWL.camPosHome);
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
		float left=mainStacks[ChipCase.CHIP_A].getX()+Chip.radiusX*0.1f;
		float right=mainStacks[ChipCase.CHIP_C].getX()-Chip.radiusX*0.1f;
		float bottom=mainStacks[ChipCase.CHIP_A].getY()+Chip.radiusY*0.1f;
		int diff = (int) (xPUC - left);
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
	
	public void toggleStackTotals() {
		showStackTotals=!showStackTotals;
	}
	
	public void bellButtonPressed() {
		networkInterface.sendBell(betterName);
	}
	
	//////////////////// World to Player Messages ////////////////////
	public void notifyAtPlayerPosition() {
		mWL.game.mFL.notifyAtPlayerPosition();
		if (playerName.equals("")) {
			connectivityStatus=CONN_LOGIN;
			mWL.game.mFL.startPlayerLoginDialog();
		} else if (connectivityStatus==CONN_NONE) {
			connectivityStatus=CONN_IDLE;
			mWL.game.mFL.showPlayerDashboard(playerName);
			if (wifiEnabled) {
				notifyReadyToSearch();
			} else {
				wifiOff();
			}
		}
		setPlayerAmount(calculateChipAmount());
	}
	
	public void notifyLeftPlayerPosition() {
		mWL.game.mFL.notifyLeftPlayerPosition();
		mWL.game.mFL.stopWifiPrompt();
		mWL.game.mFL.stopReconnect();
		dealerButton.opacity=0;
		connectivityStatus=CONN_NONE;
	}
	
	/** Notify the player to handle the back press
	 * @return return true if back was handled
	 */
	public boolean backPressed() {
		boolean backHandled=false;
		if (connectivityStatus==CONN_NONE) {
			
		} else if (connectivityStatus==CONN_IDLE) {
			
		} else if (connectivityStatus==CONN_SEARCHING) {
			stopSearchForGames();
		} else if (connectivityStatus==CONN_SEARCH_HOLDOFF) {
			;
		} else if (connectivityStatus==CONN_CONNECTING) {
			backHandled=true;
		} else if (connectivityStatus==CONN_CONNECTED) {
			doLeaveDialog();
			backHandled=true;
		} else if (connectivityStatus==CONN_POLL_RECONNECT) {
			leaveTable();
			backHandled=true;
		} else if (connectivityStatus==CONN_CONNECTED_NO_WIFI) {
			leaveTable();
			backHandled=true;
		}
		if (!backHandled) {
			mWL.game.mFL.hidePlayerDashboard();
		}
		return backHandled;
	}
	
	public void notifyReadyToSearch() {
		// TODO deal with case where player zooms out at any time in the process
		Logger.log(LOG_TAG,"notifyReadyToSearch()");
		if (connectivityStatus==CONN_IDLE||connectivityStatus==CONN_SEARCH_HOLDOFF) {
			if (wifiEnabled) {
				connectivityStatus=CONN_SEARCHING;
				mWL.game.mFL.startSearchForGames();
				networkInterface.startRequestGames();
			} else {
				connectivityStatus=CONN_IDLE;
				mWL.game.mFL.startWifiPrompt();
			}
		}
	}
	
	public void searchHoldoff() {
		Logger.log(LOG_TAG,"searchHoldoff()");
		connectivityStatus=CONN_SEARCH_HOLDOFF;
		searchHoldoffTimer=0;
		mWL.game.mFL.playerDashboard.setStatusMessage("");
	}
	
	public void startPollReconnect() {
		connectivityStatus=CONN_POLL_RECONNECT;
		reconnectTimer=RECONNECT_INTERVAL;
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
		Logger.log(LOG_TAG,"wifiOn()");
		wifiEnabled=true;
		if (connectivityStatus==CONN_IDLE) {
			mWL.game.mFL.stopWifiPrompt();
			notifyReadyToSearch();
		} else if (connectivityStatus==CONN_CONNECTED) {
			mWL.game.mFL.stopWifiPrompt();
		} else if (connectivityStatus==CONN_CONNECTED_NO_WIFI) {
			mWL.game.mFL.stopWifiPrompt();
			mWL.game.mFL.startReconnect();
			startPollReconnect();
			mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_CONNECTION_LOST);
			setConnectionAnimationState(CONN_ANIM_RECONNECTING);
		}
	}
	
	public void wifiOff() {
		Logger.log(LOG_TAG,"wifiOff()");
		wifiEnabled=false;
		if (connectivityStatus==CONN_IDLE) {
			mWL.game.mFL.startWifiPrompt();
		} else if (connectivityStatus==CONN_SEARCH_HOLDOFF) {
			mWL.game.mFL.startWifiPrompt();
		} else if (connectivityStatus==CONN_SEARCHING) {
			stopSearchForGames();
			mWL.game.mFL.startWifiPrompt();
		} else if (connectivityStatus==CONN_BUYIN) {
			mWL.game.mFL.startWifiPrompt();
			cancelBuyin();
		} else if (connectivityStatus==CONN_CONNECTING) {
			mWL.game.mFL.startWifiPrompt();
			connectivityStatus=CONN_IDLE;
			networkInterface.stopListen();
		} else if (connectivityStatus==CONN_CONNECTED) {
			connectivityStatus=CONN_CONNECTED_NO_WIFI;
			networkInterface.stopListen();
			waitingOnHost=true;
			cancelMoveState();
			mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_NO_WIFI);
			setConnectionAnimationState(CONN_ANIM_RECONNECTING);
		} else if (connectivityStatus==CONN_POLL_RECONNECT) {
			connectivityStatus=CONN_CONNECTED_NO_WIFI;
			mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_NO_WIFI);
			setConnectionAnimationState(CONN_ANIM_RECONNECTING);
		}
		// TODO deal with the other cases e.g. connecting and buyin
	}
	
	//////////////////// Player to Table Messages ////////////////////
	
	private void submitBet() {
		Logger.log(LOG_TAG,"submitBet()");
		int moveType;
		if (!isAllIn()) {
			moveType=GameLogic.MOVE_BET;
		} else {
			moveType=GameLogic.MOVE_ALL_IN;
		}
		sendMove(new Move(moveType,betStack.toString()));
		setPlayerAmount(chipAmount-betStack.value());
		betStack.clear();
		betStack.setPosition(betStackOrigin.x,betStackOrigin.y,0);
		mWL.game.mFL.betTotalDialog.hide();
		
		mWL.game.mFL.hideTextMessage();
		// TODO change connection sprite
	}
	
	public void doCheck() {
		Logger.log(LOG_TAG,"doCheck()");
		if (!waitingOnHost) {
			mWL.soundFX.checkSound.play();
			sendMove(new Move(GameLogic.MOVE_CHECK,""));

			// TODO change connection sprite
			mWL.game.mFL.hideTextMessage();
		}
	}
	
	public void doFold() {
		Logger.log(LOG_TAG,"doFold()");
		if (!waitingOnHost) {
			mWL.soundFX.foldSound.play();
			sendMove(new Move(GameLogic.MOVE_FOLD,""));

			// TODO change connection blob
			mWL.game.mFL.hideTextMessage();
		}
	}
	
	public void sendMove(Move move) {
		disableBet();
		disableCheck();
		disableFold();
		setConnectionAnimationState(CONN_ANIM_STATIC);
		mWL.game.mFL.playerDashboard.setStatusMessage("");
		networkInterface.submitMove(move);
		mWL.game.activity.dimScreen();
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
		if (connectivityStatus==CONN_CONNECTED||connectivityStatus==CONN_POLL_RECONNECT||
				connectivityStatus==CONN_CONNECTED_NO_WIFI) {
			connectivityStatus=CONN_IDLE;
			networkInterface.leaveTable();
			notifyTableDisconnected();
			searchHoldoff();
		}
	}
	
	//////////////////// Table to Player Messages ////////////////////
	public void notifyTableFound(DiscoveredTable table,boolean connectNow) {
		if (connectivityStatus==CONN_SEARCHING) {
			connectingTable=table;
			stopSearchForGames();
			ChipCase.values=table.vals;
			if (!connectNow) {
				connectivityStatus=CONN_BUYIN;
				clearAllStacks();
				mWL.game.mFL.startBuyin(table.getName());
			} else {
				connectivityStatus=CONN_CONNECTING;
				networkInterface.requestConnect(connectingTable,mWL.game.calculateAzimuth(),null);
			}
		}
		Logger.log(LOG_TAG,"notifyTableFound("+table.getName()+","+connectNow+")");
	}
	
	public void notifyConnectResult(boolean result,String tableName) {
		Logger.log(LOG_TAG,"notifyConnectResult("+result+")");
		if (result) {
			connectivityStatus=CONN_CONNECTED;
			this.tableName=tableName;
			mWL.game.mFL.stopReconnect();
			
			// TODO change back button to table menu button
			waitingOnHost=false;
			mWL.game.mFL.playerDashboard.setGameName(tableName);
			mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_CONNECTED);
			for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
				mWL.game.mFL.mainStackValueLabels[i].setText(Integer.toString(ChipCase.values[i]));
				mWL.game.mFL.mainStackValueLabels[i].loadTexture();
				float xScreen = mWL.worldRenderer.xWorldToScreen(mainStacks[i].getX());
				float yScreen = mWL.game.mFL.playerDashboard.posOnscreen.y+
						mWL.game.mFL.playerDashboard.radiusY*0.888f+
						mWL.game.mFL.mainStackValueLabels[i].maxRadiusY;
				mWL.game.mFL.mainStackValueLabels[i].setPosition(xScreen, yScreen);
			}
		} else {
			if (connectivityStatus==CONN_CONNECTING) {
				cancelBuyin();
			}
		}
	}
	
	public void setConnectionAnimationState(int state) {
		Logger.log(LOG_TAG,"setConnectionAnimationState("+state+")");
		if (this.connectionAnimationState==CONN_ANIM_FORMING&&
				state==CONN_ANIM_ACTIVE) {
			state = CONN_ANIM_FORMING_ACTIVE;
		}
		this.connectionAnimationState=state;
		if (state==CONN_ANIM_NONE) {
			connectionSprite.opacity = 0;
			connectionSprite.stopFrameAnimation(true);
			connectedSprite.opacity = 0;
			connectedSprite.stopFlashing();
			connectedSprite.stopFrameAnimation(true);
		} else if (state==CONN_ANIM_FORMING) {
			connectedSprite.opacity = 0;
			connectedSprite.stopFlashing();
			connectedSprite.stopFrameAnimation(true);
			connectionSprite.opacity = 1;
			connectionSprite.startFrameAnimation();
		} else if (state==CONN_ANIM_FORMING_ACTIVE) {
			//
		} else if (state==CONN_ANIM_ACTIVE) {
			connectionSprite.opacity = 0;
			connectionSprite.stopFrameAnimation(true);
			connectedSprite.opacity = 1;
			connectedSprite.stopFlashing();
			connectedSprite.startFrameAnimation();
		} else if (state==CONN_ANIM_STATIC) {
			connectionSprite.opacity = 0;
			connectionSprite.stopFrameAnimation(true);
			connectedSprite.opacity = 1;
			connectedSprite.stopFlashing();
			connectedSprite.stopFrameAnimation(true);
		} else if (state==CONN_ANIM_RECONNECTING) {
			connectionSprite.opacity = 0;
			connectionSprite.stopFrameAnimation(true);
			connectedSprite.stopFrameAnimation(true);
			connectedSprite.opacity = 1;
			// coupled to dashboard text in animate method
		}
	}
	
	public void notifyConnectionLost() {
		Logger.log(LOG_TAG,"notifyConnectionLost()");
		if (connectivityStatus==CONN_CONNECTED) {
			waitingOnHost=true;
			cancelMoveState();
			mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_CONNECTION_LOST);
			setConnectionAnimationState(CONN_ANIM_RECONNECTING);
			startPollReconnect();
		}
	}
	
	public void syncStatusMenu(ArrayList<PlayerEntry> playerList) {
		mWL.game.mFL.tableStatusMenu.syncStatusMenu(playerList);
	}
	
	public void setColor(int color) {
		Logger.log(LOG_TAG,"setColor()");
		this.color=color;
		setConnectionAnimationState(CONN_ANIM_FORMING);
	}
	
	public void setDealer(boolean isDealer) {
		Logger.log(LOG_TAG,"setDealer("+isDealer+")");
		this.isDealer=isDealer;
		if (isDealer) {
			dealerButton.opacity=1;
		}
	}
	
	public void notifyWaitNextHand() {
		Logger.log(LOG_TAG,"notifyWaitNextHand()");
		mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_WAIT_NEXT_HAND);
	}
	
	public void promptMove(MovePrompt movePrompt) {
		Logger.log(LOG_TAG,"promptMove("+movePrompt.stake+","+movePrompt.blinds+")");
		mWL.game.activity.brightenScreen();
		if (movePrompt.blinds==MovePrompt.BLINDS_NONE) {
			enableFold();
		}
		if (movePrompt.stake==0) {
			enableCheck();
			if (betStack.size()>0) {
				checkButton.setTouchable(false);
				checkButton.fadeOut();
			}
		}
		enableBet();
		stake=movePrompt.stake;
		mWL.soundFX.bellSound.play();
		setConnectionAnimationState(CONN_ANIM_ACTIVE);
		if (movePrompt.blinds!=MovePrompt.BLINDS_NONE) {
			mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_BLINDS,movePrompt.blinds);
		} else if (stake==0) {
			mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_BET_OR_CHECK);
		} else {
			mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_CALL,movePrompt.stake);
		}
		
		
		
	}
	
	public void cancelMove() {
		cancelMoveState();
	}
	
	public void doWin(ChipStack winStack) {
		mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_WIN,winStack.value());
		sendChips(winStack);
	}
	
	public void sendChips(ChipStack winStack) {
		Logger.log(LOG_TAG,"doWin("+winStack.toString()+")");
		mWL.game.activity.brightenScreen();
		int[] chipCount={0,0,0};
		for (int i=0;i<winStack.size();i++) {
			winStack.get(i).x=winStackOrigin.x;
			winStack.get(i).y=winStackOrigin.y;
			winStack.get(i).setDest(mainStacks[winStack.get(i).chipType].getX(),
					mainStacks[winStack.get(i).chipType].getY()+Chip.radiusY*2+
					(mainStacks[winStack.get(i).chipType].renderSize()+
							chipCount[winStack.get(i).chipType])*1,
					mainStacks[winStack.get(i).chipType].renderSize()+chipCount[winStack.get(i).chipType]);
			chipCount[winStack.get(i).chipType]++;
			cancellingStack.add(winStack.get(i));
		}
		setPlayerAmount(chipAmount+cancellingStack.value());
		if (pickedUpChip!=null) {
			float newZ_=mainStacks[pickedUpChip.chipType].renderSize()+cancellingStack.size();
			if (newZ_>pickedUpChip.z) {
				pickedUpChip.setDestZ(newZ_);
			}
		}
		mWL.game.mFL.stopWaitNextHand();
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
	
	public void notifyArrange() {
		setConnectionAnimationState(CONN_ANIM_ACTIVE);
		mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_ARRANGE);
	}
	
	public void notifySelectDealer() {
		setConnectionAnimationState(CONN_ANIM_STATIC);
		mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_SELECT_DEALER);
	}
	
	public void promptDeal(int dealStage) {
		Logger.log(LOG_TAG,"promptDeal("+dealStage+")");
		mWL.soundFX.bellSound.play();
		mWL.game.activity.brightenScreen();
		setConnectionAnimationState(CONN_ANIM_STATIC);
		mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_DEAL,dealStage);
	}
	
	public void notifyWaitDealer(String dealerName, int dealStage) {
		Logger.log(LOG_TAG,"promptDeal("+dealerName+","+dealStage+")");
		setConnectionAnimationState(CONN_ANIM_STATIC);
		mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_DEAL_WAIT,dealStage,dealerName);
	}
	
	public void notifyWaitBet(String betterName) {
		Logger.log(LOG_TAG,"notifyWaitBet("+betterName+")");
		setConnectionAnimationState(CONN_ANIM_STATIC);
		mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_BET_WAIT,betterName);
		this.betterName=betterName;
	}
	
	public void promptShowCards() {
		Logger.log(LOG_TAG,"promptShowCards()");
		mWL.game.activity.brightenScreen();
		setConnectionAnimationState(CONN_ANIM_ACTIVE);
		mWL.game.mFL.playerDashboard.setStatusMessage(PlayerDashboard.MESSAGE_SHOW_CARDS);
	}
	
	
	public void syncChips(int chipAmount) {
		int difference=chipAmount-this.chipAmount;
		Logger.log(LOG_TAG,"syncChips("+chipAmount+") difference = "+difference);
		if (difference>0) {
			ChipStack syncStack=new ChipStack();
			syncStack.buildStackFrom(ChipCase.calculateSimplestBuild(difference));
			sendChips(syncStack);
		} else if (difference<0) {
			clearAllStacks();
			ChipStack syncStack=new ChipStack();
			syncStack.buildStackFrom(ChipCase.calculateSimplestBuild(chipAmount));
			sendChips(syncStack);
		}
	}
	
	private int calculateChipAmount() {
		int amount=0;
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			amount+=mainStacks[i].value();
		}
		amount+=bettingStack.value();
		amount+=betStack.value();
		amount+=cancellingStack.value();
		amount+=cancelStack.value();
		Logger.log(LOG_TAG,"calculateChipAmount() = "+amount);
		return amount;
	}
	
	private void cancelBuyin() {
		connectivityStatus=CONN_IDLE;
		searchHoldoff();
		mWL.game.mFL.stopBuyin();
	}
	
	private int[] calculateSimpleBuyin(int amount) {
		Logger.log(LOG_TAG,"calculateSimpleBuyin("+amount+")");
		int[] simpleBuyin = ChipCase.calculateSimplestBuild(amount);
		if (simpleBuyin[ChipCase.CHIP_A]==0) {
			if (simpleBuyin[ChipCase.CHIP_B]>0) {
				int numToGain=ChipCase.values[ChipCase.CHIP_B]/ChipCase.values[ChipCase.CHIP_A];
				simpleBuyin[ChipCase.CHIP_B]--;
				simpleBuyin[ChipCase.CHIP_A]+=numToGain;
			} else if (simpleBuyin[ChipCase.CHIP_C]>0) {
				int numToGain=ChipCase.values[ChipCase.CHIP_C]/ChipCase.values[ChipCase.CHIP_B];
				simpleBuyin[ChipCase.CHIP_C]--;
				simpleBuyin[ChipCase.CHIP_B]+=numToGain;
				numToGain=ChipCase.values[ChipCase.CHIP_B]/ChipCase.values[ChipCase.CHIP_A];
				simpleBuyin[ChipCase.CHIP_B]--;
				simpleBuyin[ChipCase.CHIP_A]+=numToGain;
			}
		}
		if (simpleBuyin[ChipCase.CHIP_B]==0) {
			if (simpleBuyin[ChipCase.CHIP_C]>0) {
				int numToGain=ChipCase.values[ChipCase.CHIP_C]/ChipCase.values[ChipCase.CHIP_B];
				simpleBuyin[ChipCase.CHIP_C]--;
				simpleBuyin[ChipCase.CHIP_B]+=numToGain;
			}
		}
		return simpleBuyin;
	}
	
	public float getMainStackXScreen(int stack) {
		float x = mainStacks[stack].getX();
		return mWL.worldRenderer.xWorldToScreen(x);
	}
	
}
