package com.bidjee.digitalpokerchips.c;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.i.IHostNetwork;
import com.bidjee.digitalpokerchips.i.ITableStore;
import com.bidjee.digitalpokerchips.m.Card;
import com.bidjee.digitalpokerchips.m.Chip;
import com.bidjee.digitalpokerchips.m.ChipCase;
import com.bidjee.digitalpokerchips.m.ChipStack;
import com.bidjee.digitalpokerchips.m.ColorPool;
import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.Deck;
import com.bidjee.digitalpokerchips.m.GameLogic;
import com.bidjee.digitalpokerchips.m.MovePrompt;
import com.bidjee.digitalpokerchips.m.Player;
import com.bidjee.digitalpokerchips.m.Pot;
import com.bidjee.digitalpokerchips.m.Seat;
import com.bidjee.digitalpokerchips.m.TextField;
import com.bidjee.digitalpokerchips.m.TextLabel;
import com.bidjee.util.Logger;

public class Table {
	
	public static final String LOG_TAG = "DPCTable";
	
	//////////////////// Constants ////////////////////
	public static final int STATE_NONE = 0;
	public static final int STATE_LOBBY = 1;
	public static final int STATE_LOBBY_LOADED = 2;
	public static final int STATE_SELECTING_DEALER = 3;
	public static final int STATE_SENDING_DEALER_BUTTON = 4;
	public static final int STATE_GAME = 5;
	
	public static final int ANIM_NONE = 0;
	public static final int ANIM_BETTING = 1;
	public static final int ANIM_FORM_POTS = 2;
	public static final int ANIM_FADEOUT_POT = 3;
	public static final int ANIM_FADEIN_POT = 4;
	public static final int ANIM_SELECT_WINNER = 5;
	public static final int ANIM_SELECT_WINNERS_SPLIT = 6;
	public static final int ANIM_WINNER_BY_DEFAULT = 7;
	public static final int ANIM_SENDING_POTS = 8;
	public static final int ANIM_SPLITTING_POTS = 9;
	
	public static final int SAVE_SLOT_NONE = 0;
	public static final int SAVE_SLOT_1 = 1;
	public static final int SAVE_SLOT_2 = 2;
	public static final int SAVE_SLOT_3 = 3;
	public static final int SAVE_SLOT_4 = 4;
	public static final int SAVE_SLOT_5 = 5;
	public static final int SAVE_SLOT_6 = 6;
	public static final int SAVE_NUM_SLOTS = 6;
	
	public static final int NUM_SEATS = 8;
	public static final int MAX_STACK_RENDER_NUM = 8;
	static final int DURATION_WIN_LABEL = 1600;
	
	public static final String TABLE_NAME_DEFAULT = "MyTable";
	
	static final int DEAL_PROMPT_DURATION = 5000;
	
	//////////////////// State Variables ////////////////////
	boolean wifiEnabled;
	int winLabelTimer;
	int gameState;
	public int animationState;
	public int closestSeatToPickedUp;
	boolean inSetupFlow;
	boolean loadedGame=false;
	public int displayedPotIndex;
	public int winner;
	public int bootDialogPlayer=-1;
	public int saveSlot;
	boolean gameCanStart;
	long dealPromptTimer;
	boolean timeDealPrompt;
	
	//////////////////// Models ////////////////////
	public GameLogic gameLogic;
	public DPCSprite dealerButton=new DPCSprite();
	public Seat[] seats;
	public Player pickedUpPlayer;
	public Deck deck;
	public ArrayList<Pot> pots;
	public ArrayList<Player> players=new ArrayList<Player>();
	
	//////////////////// Text Labels ////////////////////
	public TextField tableNameField;
	public TextLabel startLabel;
	
	//////////////////// Screen Scale & Layout ////////////////////
	public float sendOffscreenVel;
	float xLeft;
	float xRight;
	float yTop;
	float yBottom;
	float dealerButtonOffset;
	public static int radiusXSuit;
	public static int radiusYSuit;
	Vector2 posDealerButtonStart=new Vector2();
	
	//////////////////// References ////////////////////
	public WorldLayer mWL;
	IHostNetwork networkInterface;
	ITableStore tableStore;
	
	public Table(WorldLayer mWL,IHostNetwork networkInterface,ITableStore tableStore) {
		this.mWL=mWL;
		this.networkInterface=networkInterface;
		networkInterface.setTable(this);
		this.tableStore=tableStore;
		tableNameField=new TextField(TABLE_NAME_DEFAULT,0,false,false);
		tableNameField.label.setFontFace("ar_delaney.ttf");
		tableNameField.label.opacity=0.8f;
		startLabel=new TextLabel("Ready to Start",0,true,0,false);
		startLabel.maxOpacity=0.75f;
		dealerButton.opacity=0;
		seats=new Seat[NUM_SEATS];
		for (int i=0;i<NUM_SEATS;i++) {
			seats[i]=new Seat(i);
		}
		pickedUpPlayer=null;
		deck=new Deck();
		gameLogic=new GameLogic(this);
		pots=new ArrayList<Pot>();
		winner=-1;
		saveSlot=SAVE_SLOT_NONE;
	}
	
	//////////////////// Scale & Layout ////////////////////
	public void setDimensions(float worldWidth,float worldHeight) {
		int radiusYTableName_=(int)(worldHeight*0.03f);
		tableNameField.setDimensions((int)(worldWidth*0.06f),radiusYTableName_);
		String tmp_=tableNameField.getText();
		tableNameField.label.setText(WorldLayer.NAME_MEASURE);
		tableNameField.label.setTextSizeToMax();
		tableNameField.label.setText(tmp_);
		dealerButton.setDimensions((int)(worldHeight*0.022f),(int)(worldHeight*0.022f));
		int textSize_=Math.min(DPCGame.textFactory.getMaxTextSize(startLabel),
				DPCGame.textFactory.getMaxTextSize(startLabel));
		startLabel.setTextSize(textSize_);
		for (int i=0;i<NUM_SEATS;i++) {
			seats[i].setDimensions((int)(worldHeight*0.068f),(int)(worldHeight*0.04f));
		}
		if (pickedUpPlayer!=null) {
			pickedUpPlayer.setDimensions(Seat.radiusX,Seat.radiusY);
		}
		dealerButtonOffset=Seat.radiusY*0.5f;
		radiusXSuit=(int) (Seat.radiusY*0.2f);
		radiusYSuit=radiusXSuit;
		TextLabel faceLabel=new TextLabel("A",0,false,0,false);
		faceLabel.setMaxDimensions(Table.radiusXSuit,Table.radiusYSuit);
		Card.textSize=DPCGame.textFactory.getMaxTextSize(faceLabel);
		gameLogic.setDimensions(worldWidth, worldHeight);
	}
	
	public void setPositions(float worldWidth,float worldHeight) {
		sendOffscreenVel=worldWidth*0.1f;
		Pot.originX=worldWidth*0.5f;
		Pot.originY=worldHeight*0.5f;
		xLeft=worldWidth*0.375f;
		xRight=worldWidth*0.625f;
		yTop=worldHeight*0.625f;
		yBottom=worldHeight*0.375f;
		tableNameField.setPosition(worldWidth*0.5f,worldHeight*0.5f);
		posDealerButtonStart.set(worldWidth*0.5f,worldHeight*0.46f);
		
		Seat.distToOffscreen=Seat.radiusY+2*Chip.radiusY*(1+MAX_STACK_RENDER_NUM*Chip.Z_Y_OFFSET_RATIO);
		float seatOffsetX=Seat.radiusX*1.5f;
		seats[0].setPosition(worldWidth*0.5f+seatOffsetX,worldHeight*0.375f+Seat.radiusY,0);
		seats[1].setPosition(worldWidth*0.5f,worldHeight*0.375f+Seat.radiusY,0);
		seats[2].setPosition(worldWidth*0.5f-seatOffsetX,worldHeight*0.375f+Seat.radiusY,0);
		seats[3].setPosition(worldWidth*0.375f+Seat.radiusY,worldHeight*0.5f,-90);
		seats[4].setPosition(worldWidth*0.5f-seatOffsetX,worldHeight*0.625f-Seat.radiusY,180);
		seats[5].setPosition(worldWidth*0.5f,worldHeight*0.625f-Seat.radiusY,180);
		seats[6].setPosition(worldWidth*0.5f+seatOffsetX,worldHeight*0.625f-Seat.radiusY,180);
		seats[7].setPosition(worldWidth*0.625f-Seat.radiusY,worldHeight*0.5f,90);
		gameLogic.setPositions(worldWidth,worldHeight);
	}
	
	public void scalePositions(float scaleX,float scaleY) {
		sendOffscreenVel*=scaleX;
		Pot.originX*=scaleX;
		Pot.originY*=scaleY;
		tableNameField.scalePosition(scaleX,scaleY);
		dealerButton.scalePosition(scaleX,scaleY);
		startLabel.scalePosition(scaleX,scaleY);
		
		seats[1].scalePosition(scaleX,scaleY);
		seats[2].scalePosition(scaleX,scaleY);
		seats[3].scalePosition(scaleX,scaleY);
		seats[4].scalePosition(scaleX,scaleY);
		seats[5].scalePosition(scaleX,scaleY);
		seats[6].scalePosition(scaleX,scaleY);
		seats[7].scalePosition(scaleX,scaleY);
		// things that can move that aren't initialised
		if (pickedUpPlayer!=null) {
			pickedUpPlayer.scalePosition(scaleX,scaleY);
		}
		gameLogic.scalePositions(scaleX, scaleY);
	}
	
	//////////////////// Controllers ////////////////////
	public void controlLogic() {
		if (gameState==STATE_GAME) {
			gameLogic.updateLogic();
		}
		if (gameState==STATE_LOBBY) {
			if (mWL.game.runTutorialArrangement&&countPlayersLobby()==1) {
				mWL.game.runTutorialArrangement=false;
				mWL.game.mFL.startTutorialArrangement(getAnyPlayer());
			}
		}
		
		if (gameState==STATE_LOBBY) {
			if (!gameCanStart&&countPlayers()>=2) {
				gameCanStart=true;
				mWL.game.mFL.notifyGameCanStart(true);
			} else if (gameCanStart&&countPlayers()<2) {
				gameCanStart=false;
				mWL.game.mFL.notifyGameCantStart();
			}
		} else if (gameState==STATE_LOBBY_LOADED) {
			if (allPlayersSetup()) {
				String gameStateString=tableStore.getGameState(saveSlot);
				gameLogic.setGameStateFromString(gameStateString);
				setGameState(STATE_SENDING_DEALER_BUTTON);
			}
		}
	}
	
	public void animate(float delta) {
		startLabel.animate(delta);
		tableNameField.animate(delta);
		dealerButton.animate(delta);
		for (int i=0;i<NUM_SEATS;i++) {
			seats[i].animate(delta);
			Player thisPlayer=seats[i].player;
			if (thisPlayer!=null) {
				thisPlayer.animate(delta);
				if (thisPlayer.getCard()!=null) {
					thisPlayer.getCard().animate(delta);
				}
				if (thisPlayer.state==Player.STATE_RXING_JOIN_COIN) {
					if (Math.abs(thisPlayer.joinToken.y-seats[i].y)<2&&
							Math.abs(thisPlayer.joinToken.x-seats[i].x)<2) {
						mWL.soundFX.chingSound.play();
						thisPlayer.changeJoinCoin();
					} else {
						float deltaY_=delta*5*(thisPlayer.joinToken.y-seats[i].y);
						thisPlayer.joinToken.y-=deltaY_;
						float deltaX_=delta*5*(thisPlayer.joinToken.x-seats[i].x);
						thisPlayer.joinToken.x-=deltaX_;
					}
				} else if (thisPlayer.state==Player.STATE_PAUSING_START_STACK) {
					thisPlayer.pauseStartStack(delta);
				} else if (thisPlayer.state==Player.STATE_SENDING_START_STACK) {
					if (Math.abs(thisPlayer.winStack.getY()-seats[i].yOffscreen)<2&&
							Math.abs(thisPlayer.winStack.getX()-seats[i].xOffscreen)<2) {
						// shouldn't happen
					} else {
						float deltaY_=delta*5*(thisPlayer.winStack.getY()-seats[i].yOffscreen);
						thisPlayer.winStack.setY(thisPlayer.winStack.getY()-deltaY_);
						float deltaX_=delta*5*(thisPlayer.winStack.getX()-seats[i].xOffscreen);
						thisPlayer.winStack.setX(thisPlayer.winStack.getX()-deltaX_);
					}
				}
			}
		}
		for (int i=0;i<pots.size();i++) {
			pots.get(i).potStack.animate(delta);
		}
		if (gameState==STATE_LOBBY||gameState==STATE_LOBBY_LOADED) {
			Player thisPlayer_;
			for (int i=0;i<NUM_SEATS;i++) {
				seats[i].animate(delta);
				thisPlayer_=seats[i].player;
				if (thisPlayer_!=null) {
					if (Math.abs(thisPlayer_.y-seats[i].y)<1&&
							Math.abs(thisPlayer_.x-seats[i].x)<1) {
						;
					} else {
						float timeFactor = delta*9;
						thisPlayer_.setY((float)(thisPlayer_.y-timeFactor*(thisPlayer_.y-seats[i].y)));
						thisPlayer_.setX((float)(thisPlayer_.x-timeFactor*(thisPlayer_.x-seats[i].x)));
						rotateAndProjectPlayer(thisPlayer_);
					}
					
				}
			}
			thisPlayer_=pickedUpPlayer;
			if (thisPlayer_!=null) {
				thisPlayer_.animate(delta);
			}
		} else if (gameState==STATE_SELECTING_DEALER) {
			if (dealerButton.opacity==1) {
				if (gameLogic.getDealer()==GameLogic.NO_PLAYER) {
					boolean allCardsVisible=true;
					for (int seatIndex=0;seatIndex<NUM_SEATS;seatIndex++) {
						
						if (seats[seatIndex].player!=null) {
							seats[seatIndex].player.getCard().opacity+=delta*0.75f;
							if (seats[seatIndex].player.getCard().opacity>1) {
								seats[seatIndex].player.getCard().opacity=1;
							}
							if (seats[seatIndex].player.getCard().opacity!=1) {
								allCardsVisible=false;
								break;
							}
						}
					}
					if (allCardsVisible) {
						setDealer(calculateDealer());
						mWL.game.mFL.stopDealerSelect();
						mWL.game.mFL.showWinLabel(seats[gameLogic.getDealer()].player.name); 
						winLabelTimer=0;
					}
				} else if (winLabelTimer<DURATION_WIN_LABEL) {
					winLabelTimer+=delta*1000;
				} else {
					setGameState(STATE_SENDING_DEALER_BUTTON);
				}
			}
		} else if (gameState==STATE_SENDING_DEALER_BUTTON) {
			if (dealerButton.opacity==1) {
				if (!dealerButton.atDest) {
					Seat thisSeat_=seats[gameLogic.getDealer()];
					float destX_=thisSeat_.x-thisSeat_.player.xCoeff*dealerButtonOffset;
					float destY_=thisSeat_.y-thisSeat_.player.yCoeff*dealerButtonOffset;
					if (Math.abs(dealerButton.y-destY_)<2&&
							Math.abs(dealerButton.x-destX_)<2) {
						dealerButton.atDest=true;
					} else {
						float deltaY_=delta*5*(destY_-dealerButton.y);
						dealerButton.y+=deltaY_;
						float deltaX_=delta*5*(destX_-dealerButton.x);
						dealerButton.x+=deltaX_;
						dealerButton.atDest=false;
					}
				} else {
					if (Math.abs(dealerButton.y-seats[gameLogic.getDealer()].yOffscreen)<2&&
							Math.abs(dealerButton.x-seats[gameLogic.getDealer()].xOffscreen)<2) {
						// shouldn't happen - collision detector should clear it
					} else {
						float deltaY=delta*5*(seats[gameLogic.getDealer()].yOffscreen-dealerButton.y);
						dealerButton.y+=deltaY;
						float deltaX=delta*5*(seats[gameLogic.getDealer()].xOffscreen-dealerButton.x);
						dealerButton.x+=deltaX;
					}
				}
			}
		} else if (gameState==STATE_GAME) {
			//gameLogic.animate(delta);
			for (int seat=0;seat<Table.NUM_SEATS;seat++) {
				if (seats[seat].player!=null) {
					seats[seat].player.animate(delta);
				}
			}
			if (timeDealPrompt) {
				dealPromptTimer+=delta*1000;
				if (dealPromptTimer>DEAL_PROMPT_DURATION) {
					timeDealPrompt=false;
					gameLogic.dealPromptDone();
				}
			}
			if (animationState==ANIM_BETTING) {
				for (int seatIndex=0;seatIndex<NUM_SEATS;seatIndex++) {
					if (seats[seatIndex].player!=null) {
						if (seats[seatIndex].player.bettingStack.size()>0) {
							if (seats[seatIndex].player.bettingStack.animateToDest(delta)) {
								seats[seatIndex].player.betStack.appendStack(seats[seatIndex].player.bettingStack);
								seats[seatIndex].player.resetBettingStack();
								animationState=ANIM_NONE;
								gameLogic.bettingStackArrived();
								seats[seatIndex].player.betStack.updateTotalLabel();
							}
						}
					}
				}
			} else if (animationState==ANIM_FORM_POTS) {
				boolean formed_=true;
				for (int i=0;i<Table.NUM_SEATS;i++) {
					if (seats[i].player!=null) {
						for (int chip_=0;chip_<seats[i].player.betStack.size();chip_++) {
							if (seats[i].player.betStack.get(chip_).pooling) {
								if (!seats[i].player.betStack.get(chip_).animateToDest(delta))
									formed_=false;
							}
						}
					}
				}
				if (formed_) {
					for (int i=0;i<Table.NUM_SEATS;i++) {
						int seat_=Seat.zOrder[i];
						if (seats[seat_].player!=null) {
							for (int j=0;j<seats[seat_].player.betStack.size();j++) {
								if (seats[seat_].player.betStack.get(j).pooling) {
									pots.get(displayedPotIndex).potStack.add(seats[seat_].player.betStack.get(j));
									seats[seat_].player.betStack.get(j).pooling=false;
									seats[seat_].player.betStack.remove(j);
									j--;
								}
							}
							seats[seat_].player.betStack.flashXYZ();
						}
					}
					pots.get(displayedPotIndex).potStack.setCoordsToBottomChip();
					pots.get(displayedPotIndex).potStack.updateTotalLabel();
					if (displayedPotIndex<pots.size()-1) {
						animationState=ANIM_FADEOUT_POT;
					} else {
						animationState=ANIM_NONE;
						gameLogic.formPotsDone();
					}
				}
			} else if (animationState==ANIM_FADEOUT_POT) {
				if (pots.get(displayedPotIndex).potStack.getOpacity()>0) {
					pots.get(displayedPotIndex).potStack.fadeOut();
				} else {
					animationState=ANIM_NONE;
					gameLogic.formPotsDone();
				}
			} else if (animationState==ANIM_FADEIN_POT) {
				if (pots.get(displayedPotIndex).potStack.getOpacity()<1) {
					pots.get(displayedPotIndex).potStack.fadeIn();
				} else {
					animationState=ANIM_NONE;
					gameLogic.fadeInPotDone();
				}
			} else if (animationState==ANIM_SELECT_WINNER) {
				Pot thisPot=pots.get(displayedPotIndex);
				if (thisPot.potStack.velocityX!=0||thisPot.potStack.velocityY!=0) {
					thisPot.potStack.setX(thisPot.potStack.getX()+delta*thisPot.potStack.velocityX);
					thisPot.potStack.setY(thisPot.potStack.getY()+delta*thisPot.potStack.velocityY);
					updatePotRotation();
				}
			} else if (animationState==ANIM_WINNER_BY_DEFAULT) {
				ChipStack potStack=pots.get(displayedPotIndex).potStack;
				if (Math.abs(potStack.getY()-seats[winner].y)<2&&
						Math.abs(potStack.getX()-seats[winner].x)<2) {
					potStack.setVelocitiesForOffScreen(sendOffscreenVel);
					animationState=ANIM_SENDING_POTS;
				} else {
					float deltaY=delta*5*(seats[winner].y-potStack.getY());
					float deltaX=delta*5*(seats[winner].x-potStack.getX());
					potStack.setX(potStack.getX()+deltaX);
					potStack.setY(potStack.getY()+deltaY);
				}
			} else if (animationState==ANIM_SPLITTING_POTS) {
				if (pots.get(displayedPotIndex).potStack.size()>0) {
					boolean allChipsArrived=true;
					for (int i=0;i<pots.get(displayedPotIndex).potStack.size();i++) {
						allChipsArrived&=pots.get(displayedPotIndex).potStack.get(i).animateToDest(delta);
					}
					if (allChipsArrived) {
						while (pots.get(displayedPotIndex).potStack.size()>0) {
							seats[pots.get(displayedPotIndex).potStack.get(0).destSeat.position].player.winStack.add
							(pots.get(displayedPotIndex).potStack.get(0));
							pots.get(displayedPotIndex).potStack.remove(0);
						}
						for (int i=0;i<Table.NUM_SEATS;i++) {
							if (seats[i].player!=null&&seats[i].player.winStack.size()>0) {
								seats[i].player.winStack.setRotation(seats[i].rotation);
								seats[i].player.winStack.setVelocitiesForOffScreen(sendOffscreenVel);
								seats[i].player.winStack.setX(seats[i].player.winStack.getX());
								seats[i].player.winStack.setY(seats[i].player.winStack.getY());
								seats[i].player.winStack.setZ(0);
							}
						}
					}
				} else {
					for (int i=0;i<Table.NUM_SEATS;i++) {
						if (seats[i].player!=null&&seats[i].player.winStack.size()>0) {
							seats[i].player.winStack.animate(delta);
						}
					}
				}
			} else if (animationState==ANIM_SENDING_POTS) {
				pots.get(displayedPotIndex).potStack.animate(delta);
			}
		}
		
	} // animate(float delta)
	
	public void collisionDetector() {
		for (int i=0;i<NUM_SEATS;i++) {
			Player thisPlayer=seats[i].player;
			if (thisPlayer!=null) {
				if (thisPlayer.state==Player.STATE_SENDING_START_STACK) {
					if (!mWL.worldRenderer.camera.isOnScreen(thisPlayer.winStack.getTopPixel())) {						
						if (thisPlayer.isConnected) {
							sendSetupInfo(thisPlayer);
							
						}
					}
				}
			}
		}
		if (gameState==STATE_SENDING_DEALER_BUTTON) {
			if (dealerButton.atDest&&!mWL.worldRenderer.camera.isOnScreen(dealerButton)) {
				setGameState(STATE_GAME);
			}
		} else if (gameState==STATE_GAME) {
			if (animationState==ANIM_SELECT_WINNER) {
				Pot thisPot=pots.get(displayedPotIndex);
				if (thisPot.potStack.getY()<yBottom) {
					boolean collision=true;
					for (int i=0;i<=2;i++) {
						if (thisPot.playersEntitled.contains(i)) {
							if (Math.abs(thisPot.potStack.getX()-seats[i].x)<Seat.radiusX-Chip.radiusX*0.5f) {
								collision=false;
								winner=i;
							}
						}
					}
					if (collision) {
						thisPot.potStack.setY(yBottom);
						thisPot.potStack.setVelocityX(0);
						thisPot.potStack.setVelocityY(0);
					}
				}
				if (thisPot.potStack.getY()>yTop) {
					boolean collision=true;
					for (int i=4;i<=6;i++) {
						if (thisPot.playersEntitled.contains(i)) {
							if (Math.abs(thisPot.potStack.getX()-seats[i].x)<Seat.radiusX-Chip.radiusX*0.5f) {
								collision=false;
								winner=i;
							}
						}
					}
					if (collision) {
						thisPot.potStack.setY(yTop);
						thisPot.potStack.setVelocityX(0);
						thisPot.potStack.setVelocityY(0);
					}
				}
				if (thisPot.potStack.getX()<xLeft) {
					boolean collision=true;
					if (thisPot.playersEntitled.contains(3)) {
						if (Math.abs(thisPot.potStack.getY()-seats[3].y)<Seat.radiusX-Chip.radiusX*0.5f) {
							collision=false;
							winner=3;
						}
					}
					if (collision) {
						thisPot.potStack.setX(xLeft);
						thisPot.potStack.setVelocityX(0);
						thisPot.potStack.setVelocityY(0);
					}
				}
				if (thisPot.potStack.getX()>xRight) {
					boolean collision=true;
					if (thisPot.playersEntitled.contains(7)) {
						if (Math.abs(thisPot.potStack.getY()-seats[7].y)<Seat.radiusX-Chip.radiusX*0.5f) {
							collision=false;
							winner=7;
						}
					}
					if (collision) {
						thisPot.potStack.setX(xRight);
						thisPot.potStack.setVelocityX(0);
						thisPot.potStack.setVelocityY(0);
					}
				}
				if (winner>=0&&winner<NUM_SEATS) {
					thisPot.potStack.setVelocitiesForOffScreen(sendOffscreenVel);
					animationState=ANIM_SENDING_POTS;
					mWL.game.mFL.stopSelectWinner();
				}
			} else if (animationState==ANIM_SENDING_POTS) {
				if (!mWL.worldRenderer.camera.isOnScreen(pots.get(displayedPotIndex).potStack.getTopPixel())) {
					seats[winner].player.winStack=pots.get(displayedPotIndex).potStack.copy();
					animationState=ANIM_NONE;
					gameLogic.sendWinnings();
					winner=-1;
					for (int i=0;i<NUM_SEATS;i++) {
						if (seats[i].player!=null) {
							seats[i].player.name.fadeOut();
							setConnectionShowing(seats[i].player,false);
						}
					}
				}
			} else if (animationState==ANIM_SPLITTING_POTS) {
				if (pots.get(displayedPotIndex).potStack.size()==0) {
					boolean allStacksOffScreen=true;
					for (int i=0;i<NUM_SEATS;i++) {
						if (seats[i].player!=null&&seats[i].player.winStack.size()>0) {
							if (mWL.worldRenderer.camera.isOnScreen(seats[i].player.winStack.getTopPixel())) {
								allStacksOffScreen=false;
							}
						}
					}
					if (allStacksOffScreen) {
						animationState=ANIM_NONE;
						gameLogic.sendWinnings();
						for (int i=0;i<NUM_SEATS;i++) {
							if (seats[i].player!=null) {
								seats[i].player.name.fadeOut();
								setConnectionShowing(seats[i].player,false);
							}
						}
					}
				}
			} else {
				if (pots.size()>0&&!pots.get(displayedPotIndex).potStack.wasFlung()) {
					ChipStack currPotStack=pots.get(displayedPotIndex).potStack;
					if (currPotStack.getX()<xLeft) {
						currPotStack.setX(xLeft);
					}
					if (currPotStack.getX()>xRight) {
						currPotStack.setX(xRight);
					}
					if (currPotStack.getY()<yBottom) {
						currPotStack.setY(yBottom);
					}
					if (currPotStack.getY()>yTop) {
						currPotStack.setY(yTop);
					}
				}
			}
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////
	////////////////////////////// State Management //////////////////////////////
	//////////////////////////////////////////////////////////////////////////////
	
	private void setGameState(int gameState) {
		leftGameState(this.gameState);
		Logger.log(LOG_TAG,"setGameState("+gameState+")");
		this.gameState=gameState;
		if (gameState==STATE_NONE) {
			//
		} else if (gameState==STATE_LOBBY) {
			mWL.game.mFL.startLobby();
			if (!wifiEnabled) {
				wifiOff();
			}
			gameCanStart=false;
		} else if (gameState==STATE_LOBBY_LOADED) {
			mWL.game.mFL.startLobbyLoaded();
			if (!wifiEnabled) {
				wifiOff();
			}
			for (int i=0;i<NUM_SEATS;i++) {
				//seats[i].notifyAtLobby();
				if (seats[i].player!=null) {
					seats[i].player.connectionBlob.startFlashing();
					seats[i].player.name.fadeIn();
				}
			}
			gameCanStart=false;
		} else if (gameState==STATE_SELECTING_DEALER) {
			networkInterface.selectDealer();
			mWL.game.mFL.startDealerSelect();
			dealerButton.fadeIn();
			dealerButton.setPosition(posDealerButtonStart);
			dealCards();
		} else if (gameState==STATE_SENDING_DEALER_BUTTON) {
			dealerButton.fadeIn();
			dealerButton.setPosition(posDealerButtonStart);
		} else if (gameState==STATE_GAME) {
			inSetupFlow=false;
			gameLogic.start();
			for (int i=0;i<NUM_SEATS;i++) {
				if (seats[i].player!=null) {
					setConnectionShowing(seats[i].player,false);
					seats[i].player.name.fadeOut();
				}
			}
			mWL.game.mFL.notifyGameStarting();
		}
	}
	
	private void leftGameState(int gameState) {
		Logger.log(LOG_TAG,"leftGameState("+gameState+")");
		if (gameState==STATE_LOBBY) {
			for (int i=0;i<NUM_SEATS;i++) {
				seats[i].playerSlot.fadeOut();
				seats[i].playerSlot.opacity=0;
			}
			mWL.game.mFL.stopLobby();
		} else if (gameState==STATE_LOBBY_LOADED) {
			loadedGame=false;
			for (int i=0;i<NUM_SEATS;i++) {
				seats[i].playerSlot.fadeOut();
				seats[i].playerSlot.opacity=0;
				if (seats[i].player!=null) {
					seats[i].player.isLoadedPlayer=false;
				}
			}
			mWL.game.mFL.stopLobby();
		} else if (gameState==STATE_SELECTING_DEALER) {
			mWL.game.mFL.stopDealerSelect();
			for (int i=0;i<NUM_SEATS;i++) {
				if (seats[i].player!=null&&seats[i].player.getCard()!=null) {
					seats[i].player.getCard().fadeOut();
				}
			}
			dealerButton.fadeOut();
		} else if (gameState==STATE_SENDING_DEALER_BUTTON) {
			mWL.game.mFL.stopDealerSelect();
			for (int i=0;i<NUM_SEATS;i++) {
				if (seats[i].player!=null&&seats[i].player.getCard()!=null) {
					seats[i].player.getCard().fadeOut();
				}
			}
			dealerButton.fadeOut();
		} else if (gameState==STATE_GAME) {
			gameLogic.removeAllPrompts();
			mWL.game.mFL.stopGame();
		}
	}
	
	/** Notify the table to handle the back press
	 * @return return true if world can back out of this area
	 */
	public void backPressed() {
		Logger.log(LOG_TAG,"backPressed()");
		if (gameState==STATE_NONE) {
			
		} else if (gameState==STATE_LOBBY) {
			setGameState(STATE_NONE);
			destroyTable();
			mWL.sendCameraTo(mWL.camPosChipCase);
		} else if (gameState==STATE_LOBBY_LOADED) {
			destroyTable();
			setGameState(STATE_NONE);
			mWL.sendCameraTo(mWL.camPosHome);
		} else if (gameState==STATE_SELECTING_DEALER) {
			setGameState(STATE_LOBBY);
		} else if (gameState==STATE_GAME) {
			doDestroyDialog();
		}
	}
	
	public void startTableSetup() {
		Logger.log(LOG_TAG,"startTableSetup()");
		inSetupFlow=true;
		mWL.sendCameraTo(mWL.camPosTableName);
		saveSlot=SAVE_SLOT_NONE;
	}
	
	public void notifyAtTablePosition() {
		Logger.log(LOG_TAG,"notifyAtTablePosition()");
		if (!loadedGame) {
			gameLogic.resetTable();
			setGameState(STATE_LOBBY);
		} else {
			setGameState(STATE_LOBBY_LOADED);
		}
		createTable();
	}
	
	public void notifyLeftTablePosition() {
		Logger.log(LOG_TAG,"notifyLeftTablePosition()");
		mWL.game.mFL.stopWifiPrompt();
	}
	
	public void notifyAtTableNamePosition() {
		Logger.log(LOG_TAG,"notifyAtTableNamePosition()");
		mWL.game.mFL.startEnterTableName();
		tableNameField.setFocus(true);
		Gdx.input.setOnscreenKeyboardVisible(true);
		mWL.input.setTypingFocus(WorldInput.TYPING_TABLE_NAME);
	}
	
	public void notifyLeftTableNamePosition() {
		Logger.log(LOG_TAG,"notifyLeftTableNamePosition()");
		mWL.game.mFL.stopEnterTableName();
		tableNameField.setFocus(false);
		mWL.input.setTypingFocus(WorldInput.TYPING_NOTHING);
		Gdx.input.setOnscreenKeyboardVisible(false);
	}
	
	public void notifyAtChipCasePosition() {
		Logger.log(LOG_TAG,"notifyAtChipCasePosition()");
		mWL.game.mFL.startSetValues();
	}
	
	public void notifyLeftChipCasePosition() {
		Logger.log(LOG_TAG,"notifyLeftChipCasePosition()");
		mWL.game.mFL.stopSetValues();
	}
	
	//////////////////////////////////////////////////////////////////////////////
	////////////////////////////// WiFi Connectivity /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////
	
	
	public void wifiOn(String ipAddressStr) {
		Logger.log(LOG_TAG,"wifiOn("+ipAddressStr+")");
		wifiEnabled=true;
		mWL.game.mFL.setIpAddress(ipAddressStr);
		if (gameState==STATE_LOBBY) {
			networkInterface.createTable(tableNameField.getText());
			mWL.game.mFL.startLobby();
			mWL.game.mFL.stopWifiPrompt();
		} else if (gameState==STATE_LOBBY_LOADED) {
			networkInterface.createTable(tableNameField.getText());
			mWL.game.mFL.startLobbyLoaded();
			mWL.game.mFL.stopWifiPrompt();
		} else if (gameState==STATE_SELECTING_DEALER) {
			networkInterface.createTable(tableNameField.getText());
			
			mWL.game.mFL.stopWifiPrompt();
		} else if (gameState==STATE_SENDING_DEALER_BUTTON) {
			networkInterface.createTable(tableNameField.getText());
			
			mWL.game.mFL.stopWifiPrompt();
		} else if (gameState==STATE_GAME) {
			networkInterface.createTable(tableNameField.getText());
			
			mWL.game.mFL.stopWifiPrompt();
		}
		
	}
	
	public void wifiOff() {
		Logger.log(LOG_TAG,"wifiOff()");
		wifiEnabled=false;
		mWL.game.mFL.hideIpAddress();
		if (gameState==STATE_LOBBY) {
			networkInterface.destroyTable();
			mWL.game.mFL.stopLobby();
			mWL.game.mFL.startWifiPrompt();
		} else if (gameState==STATE_LOBBY_LOADED) {
			networkInterface.destroyTable();
			mWL.game.mFL.stopLobby();
			mWL.game.mFL.startWifiPrompt();
		} else if (gameState==STATE_SELECTING_DEALER) {
			networkInterface.destroyTable();
			
			mWL.game.mFL.startWifiPrompt();
		} else if (gameState==STATE_SENDING_DEALER_BUTTON) {
			networkInterface.destroyTable();
			
			mWL.game.mFL.startWifiPrompt();
		} else if (gameState==STATE_GAME) {
			networkInterface.destroyTable();
			
			mWL.game.mFL.startWifiPrompt();
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////
	//////////////////////// Table Creation and Destruction //////////////////////
	//////////////////////////////////////////////////////////////////////////////
	
	private void createTable() {
		networkInterface.createTable(tableNameField.getText());
	}
	
	private void destroyTable() {
		Logger.log(LOG_TAG,"destroyTable()");
		networkInterface.destroyTable();
		ColorPool.unassignAll();
		for (int i=0;i<Table.NUM_SEATS;i++) {
			seats[i].removePlayer();
		}
		removeAllPlayers();
		pots.clear();
		gameLogic.destroyTable();
	}
	
	public void saveTable() {
		Logger.log(LOG_TAG,"saveTable()");
		if (saveSlot==SAVE_SLOT_NONE) {
			saveSlotSelected(SAVE_SLOT_1);
			mWL.game.mFL.startAutosaveDialog(saveSlot,tableStore.getTableNames(SAVE_NUM_SLOTS));
		} else {
			tableStore.saveGame(saveSlot,tableNameField.getText(),buildTableStateString(),gameLogic.buildGameStateString());
			gameLogic.saveDone();
		}
	}
	
	public void loadTable(int loadSlot) {
		Logger.log(LOG_TAG,"loadTable("+loadSlot+")");
		saveSlot=loadSlot;
		String tableName=tableStore.getTableName(loadSlot);
		String tableStateString=tableStore.getTableState(loadSlot);
		
		tableNameField.setText(tableName);
		setTableStateFromString(tableStateString);
		
		loadedGame=true;
		mWL.sendCameraTo(mWL.camPosTable);
	}
	
	//////////////////////////////////////////////////////////////////////////////
	//////////////////////////// Player Connectivity /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////
	
	public void notifyPlayerConnected(String playerName,int azimuth,int[] chipNumbers) {
		Logger.log(LOG_TAG,"notifyPlayerConnected("+playerName+","+azimuth+")");
		// Player buying in for first time
		// Player reconnecting
		// Player rebuying
		// Player reconnecting after crashing with too few chips
		// Player reconnecting after crashing with too many chips
		// Player reconnects after booted
		// Player reconnects after new table created
		// Player buying into loaded game, leaving and buying in again
		// Player rejoining loaded game, leaving and joining again
		// Player reconnects after table loaded
		Player connectedPlayer=getPlayerFromPlayerName(playerName);
		if (connectedPlayer==null) {
			if (chipNumbers!=null) {
				connectedPlayer=new Player(playerName,null);
				connectedPlayer.azimuth=azimuth;
				addPlayer(connectedPlayer);
				connectedPlayer.setBuyinBuild(chipNumbers);
			}
		}
		if (connectedPlayer!=null) {
			if (!connectedPlayer.hasBoughtIn) {
				if (gameState==STATE_LOBBY||gameState==STATE_LOBBY_LOADED) {
					doPlayerBuyin(connectedPlayer);
				} else if (gameState==STATE_GAME||gameState==STATE_SELECTING_DEALER||gameState==STATE_SENDING_DEALER_BUTTON) {
					doPlayerBuyinNextHand(connectedPlayer);
				} else {
					Logger.log(LOG_TAG,"notifyPlayerConnected("+playerName+","+azimuth+") connected in invalid state");
				}
			} else {
				notifyPlayerReconnected(playerName);
			}
			connectedPlayer.isConnected=true;
		} else {
			disconnectPlayer(playerName);
		}
	}
	
	private void notifyPlayerReconnected(String playerName) {
		int seatIndex=getSeatFromPlayerName(playerName);
		Player reconnectedPlayer=seats[seatIndex].player;
		networkInterface.setColor(reconnectedPlayer.name.getText(),reconnectedPlayer.color);
		setConnectionShowing(reconnectedPlayer,reconnectedPlayer.connectionShowing);
		gameLogic.notifyPlayerReconnected(seatIndex);
		reconnectedPlayer.setTouchable(true);
		syncAllTableStatusMenu();
		// TODO set connection showing during loaded lobby
	}
	
	public void disconnectPlayer(String playerName) {
		networkInterface.removePlayer(playerName);
		Player player=getPlayerFromPlayerName(playerName);
		if (player!=null) {
			player.isConnected=false;
		}
	}
	
	public void removeFromTable(String playerName) {
		disconnectPlayer(playerName);
		removePlayer(playerName);
		int seat=getSeatFromPlayerName(playerName);
		if (seat>=0&&seat<NUM_SEATS) {
			Logger.log(LOG_TAG,"removeFromTable("+playerName+") seat: "+seat);
			if (gameState==STATE_LOBBY||gameState==STATE_LOBBY_LOADED) {
				removeFromSeat(seat);
			} else if (gameState==STATE_GAME) {
				gameLogic.notifyPlayerLeft(seat);
				seats[seat].player.name.fadeOut();
				setConnectionShowing(seats[seat].player,false);
				if (seats[seat].player.selected) {
					seats[seat].player.setSelected(false);
					updateSplitButtons();
				}
			}
		} else if (pickedUpPlayer!=null&&pickedUpPlayer.name.getText().equals(playerName)) {
			Logger.log(LOG_TAG,"removeFromTable("+playerName+") PUC");
			removePickedUpPlayer();
		} else {
			Logger.log(LOG_TAG,"removeFromTable("+playerName+") not seated");
		}
		syncAllTableStatusMenu();
	}
	
	public void notifyPlayerLeft(String playerName) {
		Logger.log(LOG_TAG,"notifyPlayerLeft("+playerName+")");
		Player player=getPlayerFromPlayerName(playerName);
		if (player.isLoadedPlayer) {
			disconnectPlayer(playerName);
			player.connectionBlob.startFlashing();
			player.setTouchable(false);
		} else {
			removeFromTable(playerName);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////
	////////////////////////// Player Buy In and Exit ////////////////////////////
	//////////////////////////////////////////////////////////////////////////////
	
	public void doPlayerBuyin(Player player) {
		Logger.log(LOG_TAG,"doPlayerBuyin("+player.name.getText()+")");
		if (!player.isSeated) {
			int nextFreeSeat=getNextFreeSeat(player.azimuth);
			if (nextFreeSeat>=0&&nextFreeSeat<NUM_SEATS) {
				player.copyConnectionBlobFrom(seats[nextFreeSeat].playerSlot);
				seats[nextFreeSeat].seatPlayer(player);
				seats[nextFreeSeat].player.color=ColorPool.assignColor();
				seats[nextFreeSeat].player.name.loadTexture();
			}
		}
		if (player.isSeated) {
			player.setTouchable(true);
			player.doRxJoinCoin();
		}
	}
	
	public void doPlayerBuyinNextHand(Player player) {
		networkInterface.promptWaitNextHand(player.name.getText());
		Logger.log(LOG_TAG,"doPlayerBuyinNextHand("+player.name.getText()+")");
	}
	
	public void processBuyins() {
		Logger.log(LOG_TAG,"processBuyins()");
		Player anyUnseatedPlayer=getAnyUnseatedPlayer();
		if (anyUnseatedPlayer!=null) {
			while (getAnyUnseatedPlayer()!=null) {
				doPlayerBuyin(getAnyUnseatedPlayer());
			}
		} else {
			gameLogic.buyinsDone();
		}
	}
	
	private void removeFromSeat(int seat) {
		Logger.log(LOG_TAG,"removeFromSeat("+seat+")");
		ColorPool.unassignColor(seats[seat].player.color);
		seats[seat].removePlayer();
	}
	
	private void removePickedUpPlayer() {
		Logger.log(LOG_TAG,"removePickedUpPlayer()");
		ColorPool.unassignColor(pickedUpPlayer.color);
		pickedUpPlayer=null;
	}
	
	public void unseatDisconnectedPlayers() {
		Logger.log(LOG_TAG,"unseatDisconnectedPlayers()");
		for (int i=0;i<NUM_SEATS;i++) {
			if (seats[i].player!=null&&!seats[i].player.isConnected) {
				ColorPool.unassignColor(seats[i].player.color);
				seats[i].removePlayer();
			}
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////
	////////////////////////// Table to Player Messages //////////////////////////
	//////////////////////////////////////////////////////////////////////////////
	
	public void sendSetupInfo(Player player) {
		Logger.log(LOG_TAG,"sendSetupInfo("+player.name.getText()+")");
		networkInterface.setColor(player.name.getText(),player.color);
		networkInterface.sendChipsBuyin(player.name.getText(),player.winStack.toString());
		player.hasBoughtIn=true;
		player.sentStartStack();
		if (gameState==STATE_LOBBY||gameState==STATE_LOBBY_LOADED) {
			setConnectionShowing(player,true);
			player.name.fadeIn();
			if (countPlayers()==2) {
				networkInterface.arrange();
			}
		}
		if (gameState==STATE_GAME) {
			if (allPlayersSetup()) {
				gameLogic.buyinsDone();
			}
		}
		syncAllTableStatusMenu();
	}
	
	public void promptMove(int currBetter,MovePrompt movePrompt) {
		Logger.log(LOG_TAG,"promptMove("+currBetter+","+movePrompt.stake+","+movePrompt.blinds+")");
		networkInterface.promptMove(seats[currBetter].player.name.getText(),movePrompt,seats[currBetter].player.chipAmount);
		seats[currBetter].player.name.fadeIn();
		setConnectionShowing(seats[currBetter].player,true);
		for (int i=0;i<NUM_SEATS;i++) {
			if (seats[i].player!=null&&i!=currBetter) {
				networkInterface.notifyPlayerWaitBet(seats[i].player.name.getText(),seats[currBetter].player.name.getText());
			}
		}
	}
	
	public void promptDealer(int dealer,int dealStage) {
		Logger.log(LOG_TAG,"promptDealer("+dealer+","+dealStage+")");
		networkInterface.promptDealer(seats[dealer].player.name.getText(),dealStage);
		for (int i=0;i<NUM_SEATS;i++) {
			if (seats[i].player!=null&&i!=dealer) {
				networkInterface.notifyPlayerWaitDealer(seats[i].player.name.getText(),seats[dealer].player.name.getText(),dealStage);
			}
		}
		timeDealPrompt=true;
		dealPromptTimer=0;
	}
	
	public void sendWinnings(int player,ChipStack winStack) {
		Logger.log(LOG_TAG,"sendWinnings("+player+","+winStack.value());
		networkInterface.sendChipsWin(seats[player].player.name.getText(),winStack.toString());
		seats[player].player.chipAmount+=seats[player].player.winStack.value();
		seats[player].player.resetWinStack();
		syncAllTableStatusMenu();
	}
	
	public void sendDealerButton(int dealer) {
		Logger.log(LOG_TAG,"sendDealerButton("+dealer+")");
		networkInterface.sendDealerChip(seats[dealer].player.name.getText());
	}
	
	public void recallDealerButton(int dealer) {
		Logger.log(LOG_TAG,"recallDealerButton("+dealer+")");
		networkInterface.recallDealerChip(seats[dealer].player.name.getText());
	}
	
	private void setConnectionShowing(Player player,boolean showing) {
		Logger.log(LOG_TAG,"setConnectionShowing()");
		if (showing) {
			player.setConnectionShowing(true);
		} else {
			player.setConnectionShowing(false);
		}
	}
	
	public void cancelMove(int seat) {
		Logger.log(LOG_TAG,"cancelMove("+seat+")");
		networkInterface.cancelMove(seats[seat].player.name.getText());
	}
	
	public void syncPlayersChips(int seat) {
		Logger.log(LOG_TAG,"syncPlayersChips("+seat+")");
		networkInterface.syncPlayersChips(seats[seat].player.name.getText(),seats[seat].player.chipAmount);
	}
	
	private void syncAllTableStatusMenu() {
		Logger.log(LOG_TAG,"syncAllTableStatusMenu()");
		ArrayList<Player> players=new ArrayList<Player>();
		for (int i=0;i<NUM_SEATS;i++) {
			if (seats[i].player!=null) {
				players.add(seats[i].player);
			}
		}
		networkInterface.syncAllTableStatusMenu(players);
	}
	
	//////////////////////////////////////////////////////////////////////////////
	////////////////////////// Player to Table Messages //////////////////////////
	//////////////////////////////////////////////////////////////////////////////	
	
	public void moveRxd(String playerName,int move,String chipString) {
		
		// TODO maybe revoke belling for this player 
		int moveRxdPlayer=getSeatFromPlayerName(playerName);
		seats[moveRxdPlayer].player.name.fadeOut();
		setConnectionShowing(seats[moveRxdPlayer].player,false);
		closeBootDialog();
		if (!chipString.equals("")) {
			seats[moveRxdPlayer].player.setBettingStack(ChipStack.parseStack(chipString));
			seats[moveRxdPlayer].player.bettingStack.setX(seats[moveRxdPlayer].xOffscreen);
			seats[moveRxdPlayer].player.bettingStack.setY(seats[moveRxdPlayer].yOffscreen);
			seats[moveRxdPlayer].player.bettingStack.setDest(seats[moveRxdPlayer].x,seats[moveRxdPlayer].y,seats[moveRxdPlayer].player.betStack.size());
			animationState=ANIM_BETTING;
		}
		gameLogic.moveRxd(moveRxdPlayer,move);
		syncAllTableStatusMenu();
		Logger.log(LOG_TAG,"moveRxd("+playerName+","+move+","+chipString+")"+seats[moveRxdPlayer].player.name.getText());
	}
    
	public void bellRxd(String destPlayerName) {
		if (gameLogic.currBetter>=0&&gameLogic.currBetter<=NUM_SEATS) {
			if (seats[gameLogic.currBetter].player!=null&&seats[gameLogic.currBetter].player.name.getText().equals(destPlayerName)) {
				networkInterface.sendBell(destPlayerName);
			}
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////
	//////////////////////////////// Game Logic //////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////
	
	private void dealCards() {
		Logger.log(LOG_TAG,"dealCards()");
		deck.shuffle();
		for (int seat=0;seat<NUM_SEATS;seat++) {
			if (seats[seat].player!=null) {
				seats[seat].player.giveCard(deck.drawTopCard());
				seats[seat].player.getCard().loadLabel();
			}
		}
	}
	
	private int calculateDealer() {
		float highestRank=0;
		int highestPlayer=-1;
		for (int seat=0;seat<NUM_SEATS;seat++) {
			if (seats[seat].player!=null) {
				float rank=seats[seat].player.getCard().getRank();
				if (rank>highestRank) {
					highestRank=rank;
					highestPlayer=seat;
				}
			}
		}
		Logger.log(LOG_TAG,"calculateDealer() = "+highestPlayer);
		return highestPlayer;
	}
	
	public void setDealer(int dealer) {
		Logger.log(LOG_TAG,"setDealer("+dealer+")");
		gameLogic.setDealer(dealer);
		if (dealer==GameLogic.NO_PLAYER) {
			dealerButton.rotation=seats[0].rotation;
		} else {
			dealerButton.rotation=seats[dealer].rotation;
		}
		dealerButton.atDest=false;
	}
	
	public void formPots() {
		Logger.log(LOG_TAG,"formPots()");
		displayedPotIndex=pots.size()-1;
		animationState=ANIM_FORM_POTS;
	}
	
	public void clearPots() {
		Logger.log(LOG_TAG,"clearPots()");
		displayedPotIndex=0;
		pots.clear();
	}
	
	public void makeNewPot(ArrayList<Integer> playersEntitled) {
		Logger.log(LOG_TAG,"makeNewPot()");
		pots.add(new Pot());
		pots.get(pots.size()-1).playersEntitled=playersEntitled;
		pots.get(pots.size()-1).potStack.setX(Pot.originX);
		pots.get(pots.size()-1).potStack.setY(Pot.originY);
		pots.get(pots.size()-1).potStack.scaleLabel();
	}
	
	public void fadeInNextPot() {
		displayedPotIndex=pots.size()-1;
		pots.get(displayedPotIndex).potStack.setOpacity(0);
		animationState=ANIM_FADEIN_POT;
	}
	
	public void showLastPot() {
		displayedPotIndex=pots.size()-1;
	}
	
	public void enablePotArrows() {
		if (displayedPotIndex>0) {
			mWL.game.mFL.potArrowRight.fadeIn();
			mWL.game.mFL.potArrowRight.setTouchable(true);
		}
		if (displayedPotIndex<pots.size()-1) {
			mWL.game.mFL.potArrowLeft.fadeIn();
			mWL.game.mFL.potArrowLeft.setTouchable(true);
		}
	}
	
	public void disablePotArrows() {
		mWL.game.mFL.potArrowLeft.fadeOut();
		mWL.game.mFL.potArrowLeft.setTouchable(false);
		mWL.game.mFL.potArrowRight.fadeOut();
		mWL.game.mFL.potArrowRight.setTouchable(false);
	}
	
	public void startSelectWinner() {
		animationState=ANIM_SELECT_WINNER;
		mWL.game.mFL.startSelectWinner();
		for (int i=0;i<NUM_SEATS;i++) {
			if (seats[i].player!=null&&pots.get(displayedPotIndex).playersEntitled.contains(i)) {
				seats[i].player.name.fadeIn();
				setConnectionShowing(seats[i].player,true);
				networkInterface.promptShowCards(seats[i].player.name.getText());
			}
		}
	}
	
	public void startWinnerByDefault(int winner) {
		animationState=ANIM_WINNER_BY_DEFAULT;
		this.winner=winner;
		seats[winner].player.name.fadeIn();
		setConnectionShowing(seats[winner].player,true);
		pots.get(displayedPotIndex).potStack.setRotation(seats[winner].rotation);
	}
	
	//////////////////////////////////////////////////////////////////////////////
	////////////////////////////// Input Callbacks ///////////////////////////////
	//////////////////////////////////////////////////////////////////////////////	
	
	public void tableNameDone() {
		Logger.log(LOG_TAG,"tableNameDone()");
		if (tableNameField.getText().equals("")) {
			tableNameField.setText(TABLE_NAME_DEFAULT);
		}
		mWL.sendCameraTo(mWL.camPosChipCase);
	}
	
	public void setValuesDone() {
		Logger.log(LOG_TAG,"setValuesDone()");
		if (ChipCase.checkValuesDivisibility()) {
			mWL.sendCameraTo(mWL.camPosTable);
		} else {
			mWL.game.mFL.divisibilityDialog.show();
		}
	}
	
	public void setPickedUpPlayer(int seatIndex_) {
		pickedUpPlayer=seats[seatIndex_].player;
		seats[seatIndex_].removePlayer();
		pickedUpPlayer.setIsTouched(true);
		pickedUpPlayer.atDest=false;
	}
	
	public void pickedUpPlayerDropped() {
		// check if that seat is occupied
		if (seats[closestSeatToPickedUp].player!=null) {
			boolean clockwise=true;
			// if so, check which side we are on
			if (seats[closestSeatToPickedUp].rotation==0) {
				if (pickedUpPlayer.x<seats[closestSeatToPickedUp].x) {
					clockwise=false;
				}
			} else if (seats[closestSeatToPickedUp].rotation==-90) {
				if (pickedUpPlayer.y>seats[closestSeatToPickedUp].y) {
					clockwise=false;
				}
			} else if (seats[closestSeatToPickedUp].rotation==180) {
				if (pickedUpPlayer.x>seats[closestSeatToPickedUp].x) {
					clockwise=false;
				}
			} else if (seats[closestSeatToPickedUp].rotation==90) {
				if (pickedUpPlayer.y<seats[closestSeatToPickedUp].y) {
					clockwise=false;
				}
			}
			// travel around the table until a free spot is found
			int nextFree=closestSeatToPickedUp;
			int distAway=0;
			for (int i=0;i<NUM_SEATS;i++) {
				if (seats[nextFree].player==null) {
					break;
				} else {
					if (clockwise) {
						nextFree++;
						if (nextFree>=NUM_SEATS) {
							nextFree=0;
						}
					} else {
						nextFree--;
						if (nextFree<0) {
							nextFree=NUM_SEATS-1;
						}
					}
					distAway++;
				}
			}
			// shuffle each player around one
			int thisSeat=nextFree;
			for (int i=0;i<distAway;i++) {
				int nextSeat;
				if (clockwise) {
					nextSeat=thisSeat-1;
					if (nextSeat<0) {
						nextSeat=NUM_SEATS-1;
					}
				} else {
					nextSeat=thisSeat+1;
					if (nextSeat>=NUM_SEATS) {
						nextSeat=0;
					}
				}
				seats[thisSeat].setPlayer(seats[nextSeat].player);
				thisSeat=nextSeat;
			}
		}
		
		// put picked up player in closest seat
		seats[closestSeatToPickedUp].setPlayer(pickedUpPlayer);
		// get rid of picked up player
		pickedUpPlayer=null;
	}
	
	public void playButtonClicked() {
		if (countPlayers()>=2) {
			setGameState(STATE_SELECTING_DEALER);
		} else {
			// TODO implement error checking stuff
		}
	}
	
	public void doDestroyDialog() {
		mWL.game.mFL.startDestroyTableDialog(tableNameField.getText());
	}
	
	public void destroyDialogDone(boolean actionCompleted) {
		mWL.game.mFL.stopDestroyTableDialog();
		if (actionCompleted) {
			destroyTable();
			setGameState(STATE_NONE);
			mWL.sendCameraTo(mWL.camPosHome);
		}
	}
	
	public void sitOutButtonClicked() {
		cancelMove(bootDialogPlayer);
		gameLogic.sitPlayerOut(bootDialogPlayer);
		seats[bootDialogPlayer].player.name.fadeOut();
		setConnectionShowing(seats[bootDialogPlayer].player,false);
		closeBootDialog();
	}
	
	public void bootButtonClicked() {
		removeFromTable(seats[bootDialogPlayer].player.name.getText());
		closeBootDialog();
	}
	
	public void doPotRightArrow() {
		mWL.game.mFL.potArrowLeft.fadeIn();
		mWL.game.mFL.potArrowLeft.setTouchable(true);
		if (displayedPotIndex>0) {
			displayedPotIndex--;
		}
		if (displayedPotIndex==0) {
			mWL.game.mFL.potArrowRight.fadeOut();
			mWL.game.mFL.potArrowRight.setTouchable(false);
		}
		pots.get(displayedPotIndex).potStack.setOpacity(1);
	}
	
	public void doPotLeftArrow() {
		mWL.game.mFL.potArrowRight.fadeIn();
		mWL.game.mFL.potArrowRight.setTouchable(true);
		if (displayedPotIndex<pots.size()-1) {
			displayedPotIndex++;
		}
		if (displayedPotIndex==pots.size()-1) {
			mWL.game.mFL.potArrowLeft.fadeOut();
			mWL.game.mFL.potArrowLeft.setTouchable(false);
		}
		pots.get(displayedPotIndex).potStack.setOpacity(1);
	}
	
	public void startSplit() {
		// TODO deal with case where pots on its way to winner and this happens
		mWL.game.mFL.stopSelectWinner();
		mWL.game.mFL.startSelectWinnersSplit();
		animationState=ANIM_SELECT_WINNERS_SPLIT;
	}
	
	public void cancelSplit() {
		mWL.game.mFL.stopSelectWinnersSplit();
		mWL.game.mFL.startSelectWinner();
		for (int i=0;i<NUM_SEATS;i++) {
			if (seats[i].player!=null) {
				seats[i].player.setSelected(false);
			}
		}
		animationState=ANIM_SELECT_WINNER;
	}
	
	public void splitDone() {		
		Pot currPot=pots.get(displayedPotIndex);
		currPot.potStack.setRotation(0);
		// count the number of winners selected
		int N=0;
		for (int i=0;i<seats.length;i++) {
			if (seats[i].player!=null&&seats[i].player.selected) {
				N++;
			}
		}
		// make change if needed to split evenly
		int remainder_=Pot.makeChange(currPot.potStack,currPot.potStack.value(),N);
		int currPlayer=gameLogic.getDealer();
		// distribute remainder clockwise from dealer
		while (remainder_>0) {
			currPlayer++;
			if (currPlayer==Table.NUM_SEATS)
				currPlayer=0;
			if (seats[currPlayer].player!=null&&seats[currPlayer].player.selected) {
				seats[currPlayer].player.getsRemainder=true;
				remainder_--;
			}
		}
		// divide pot between winners
		Logger.log(LOG_TAG,"* Splitting pot worth "+currPot.potStack.value()+" between "+N+" *");
		//appendLog("Splitting pot worth "+currPot.potStack.value()+" between "+N);
		currPot.formSplitPots(seats,N);
		// initiate animation
		animationState=ANIM_SPLITTING_POTS;
		// clear everything
		mWL.game.mFL.stopSelectWinnersSplit();
		for (int i=0;i<NUM_SEATS;i++) {
			if (seats[i].player!=null) {
				seats[i].player.setSelected(false);
			}
		}
		currPot.potStack.totalLabel.opacity=0;
	}
	
	public void playerClicked(int seat) {
		if (animationState==ANIM_SELECT_WINNERS_SPLIT) {
			if (pots.get(displayedPotIndex).playersEntitled.contains(seat)) {
				seats[seat].player.setSelected(!seats[seat].player.selected);
				updateSplitButtons();
			}
		} else if (gameState==STATE_GAME) {
			if (gameLogic.isBetting(seat)) {
				float xSeat=mWL.worldRenderer.xWorldToScreen(seats[seat].x);
				float ySeat=mWL.worldRenderer.yWorldToScreen(seats[seat].y);
				bootDialogPlayer=seat;
				mWL.game.mFL.startBootDialog(xSeat,ySeat,seats[seat].rotation);
			}
		}
	}
	
	public void undoClicked(int seat) {
		//
	}
	
	private void updateSplitButtons() {
		int playersSelected=0;
		for (int i=0;i<NUM_SEATS;i++) {
			if (seats[i].player!=null&&seats[i].player.selected) {
				playersSelected++;
			}
		}
		if (playersSelected==1) {
			mWL.game.mFL.splitDoneButton.fadeOut();
			mWL.game.mFL.splitDoneButton.setTouchable(false);
		} else if (playersSelected==2) {
			mWL.game.mFL.splitDoneButton.fadeIn();
			mWL.game.mFL.splitDoneButton.setTouchable(true);
		}
	}
	
	public void saveSlotSelected(int slot) {
		saveSlot=slot;
		mWL.game.mFL.autosaveDialog.slotSelected(slot);
	}
	
	public void autosaveDialogDone() {
		mWL.game.mFL.stopAutosaveDialog();
		saveTable();
	}
	
	public void closeBootDialog() {
		mWL.game.mFL.stopBootDialog();
		bootDialogPlayer=-1;
	}
	
	public void enableUndo(int seat) {
		seats[seat].undoButton.fadeIn();
		seats[seat].undoButton.setTouchable(true);
	}
	
	public void disableAllUndo() {
		for (int i=0;i<NUM_SEATS;i++) {
			seats[i].undoButton.fadeOut();
			seats[i].undoButton.setTouchable(false);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////
	////////////////////////////// Private Helpers ///////////////////////////////
	//////////////////////////////////////////////////////////////////////////////
	
	Object playersLock=new Object();
	
	private void addPlayer(Player player) {
		synchronized (playersLock) {
			players.add(player);
		}
	}
	
	private void removePlayer(String playerName) {
		synchronized (playersLock) {
			for (int i=players.size()-1;i>=0;i--) {
				if (players.get(i).name.getText().equals(playerName)) {
					players.remove(i);
				}
			}
		}
	}
	
	private void removeAllPlayers() {
		synchronized (playersLock) {
			players.clear();
		}
	}
	
	public boolean checkPlayerConnected(String playerName) {
		boolean connected=false;
		synchronized (playersLock) {
			for (int i=players.size()-1;i>=0;i--) {
				if (players.get(i).name.getText().equals(playerName)) {
					connected=true;
				}
			}
		}
		return connected;
	}
	
	private Player getAnyPlayer() {
		Player thisPlayer=null;
		synchronized (playersLock) {
			if (players.size()>0) {
				thisPlayer=players.get(0);
			} else {
				Logger.log(LOG_TAG,"getAnyPlayer() - Error: no players connected");
			}
		}
		return thisPlayer;
	}
	
	private Player getAnyUnseatedPlayer() {
		Player thisPlayer=null;
		synchronized (playersLock) {
			for (int i=players.size()-1;i>=0;i--) {
				if (!players.get(i).isSeated) {
					thisPlayer=players.get(i);
					break;
				}
			}
		}
		return thisPlayer;
	}
	
	public void rotateAndProjectPlayer(Player player) {
		// check the screen edges and rotate player if necessary
		player.setRotation(seats[calculateClosestSeatToPlayer(player)].rotation);
		if (player.rotation==0) {
			player.setY(mWL.table.seats[0].y);
		} else if (player.rotation==-90) {
			player.setX(mWL.table.seats[3].x);
		} else if (player.rotation==180) {
			player.setY(mWL.table.seats[4].y);
		} else if (player.rotation==90) {
			player.setX(mWL.table.seats[7].x);
		}
	}
	
	public void calculateClosestSeatToPickedUp() {
		closestSeatToPickedUp=calculateClosestSeatToPlayer(pickedUpPlayer);
	}
	
	public int calculateClosestSeatToPlayer(Player player) {
		int closestSeat=0;
		double minDist=Math.pow((player.x-seats[0].x),2)+Math.pow((player.y-seats[0].y),2);
		for (int seatIndex=0;seatIndex<NUM_SEATS;seatIndex++) {
			if (Math.abs(player.rotation-seats[seatIndex].rotation)!=180) {
				double dist=Math.pow((player.x-seats[seatIndex].x),2)+Math.pow((player.y-seats[seatIndex].y),2);
				if (dist<minDist) {
					minDist=dist;
					closestSeat=seatIndex;
				}
			}
		}
		return closestSeat;
	}
	
	private int countPlayersLobby() {
		int count_=0;
		for (int i=0;i<NUM_SEATS;i++) {
			if (seats[i].player!=null) {
				count_++;
			}
		}
		if (pickedUpPlayer!=null&&pickedUpPlayer.isConnected)
			count_++;
		return count_;
	}
	
	private boolean allPlayersSetup() {
		boolean allPlayersSetup=true;
		for (int i=0;i<NUM_SEATS;i++) {
			if (seats[i].player!=null) {
				if (!seats[i].player.isConnected) {
					allPlayersSetup=false;
				}
			}
		}
		return allPlayersSetup;
	}
	
	public int countPlayers() {
		int count_=0;
		for (int i=0;i<NUM_SEATS;i++) {
			if (seats[i].player!=null&&seats[i].player.hasBoughtIn) {
				count_++;
			}
		}
		if (pickedUpPlayer!=null&&!pickedUpPlayer.hasBoughtIn)
			count_++;
		return count_;
	}
	
	private int getSeatFromPlayerName(String playerName) {
		int index=-1;
		for (int i=0;i<NUM_SEATS;i++) {
			if (seats[i].player!=null&&seats[i].player.name.getText().equals(playerName)) {
				index=i;
				break;
			}
		}
		Logger.log(LOG_TAG,"getIndexFromPlayerName("+playerName+") = "+index);
		return index;
	}
	
	private Player getPlayerFromPlayerName(String playerName) {
		Player thisPlayer=null;
		synchronized (playersLock) {
			for (int i=players.size()-1;i>=0;i--) {
				if (players.get(i).name.getText().equals(playerName)) {
					thisPlayer=players.get(i);
					break;
				}
			}
		}
		return thisPlayer;
	}
	
	public void updatePotRotation() {
		// calculate min pot distance from each entitled player
		int closestPlayer=-1;
		float closestDistanceSquared=0;
		for (int i=0;i<NUM_SEATS;i++) {
			if (seats[i].player!=null&&pots.get(displayedPotIndex).playersEntitled.contains(i)) {
				float distToPlayerSquared=(float) (Math.pow(Math.abs(seats[i].x-pots.get(displayedPotIndex).potStack.getX()),2)+
						Math.pow(Math.abs(seats[i].y-pots.get(displayedPotIndex).potStack.getY()),2));
				if (closestPlayer==-1||distToPlayerSquared<closestDistanceSquared) {
					closestDistanceSquared=distToPlayerSquared;
					closestPlayer=i;
				}
			}
		}
		if (pots.get(displayedPotIndex).potStack.getRotation()!=seats[closestPlayer].rotation) {
			pots.get(displayedPotIndex).potStack.setRotation(seats[closestPlayer].rotation);
		}
	}
	
	private String buildTableStateString() {
		String outString=ChipCase.valuesToString();
		for (int i=0;i<Table.NUM_SEATS;i++) {
			if (seats[i].player!=null){
				outString=outString.concat("<PLAYER>"+i+"<PLAYER/><NAME>"+
						seats[i].player.name.getText()+"<NAME/>"+"<AMOUNT>"+
						seats[i].player.chipAmount+"<AMOUNT/>");
			}
		}
		return outString;
	}
	
	private void setTableStateFromString(String tableStateStr) {
		ChipCase.setValuesFromString(tableStateStr);
		while (tableStateStr.contains("<PLAYER>")&&tableStateStr.contains("<AMOUNT/>")) {
    		int startIndex=tableStateStr.indexOf("<PLAYER>")+"<PLAYER>".length();
    		int endIndex=tableStateStr.indexOf("<PLAYER/>");
    		int playerIndex=Integer.parseInt(tableStateStr.substring(startIndex,endIndex));
    		startIndex=tableStateStr.indexOf("<NAME>")+"<NAME>".length();
    		endIndex=tableStateStr.indexOf("<NAME/>");
    		String name=tableStateStr.substring(startIndex,endIndex);
    		startIndex=tableStateStr.indexOf("<AMOUNT>")+"<AMOUNT>".length();
    		endIndex=tableStateStr.indexOf("<AMOUNT/>");
    		int amount=Integer.parseInt(tableStateStr.substring(startIndex,endIndex));
    		
    		Player thisPlayer=new Player(name,seats[playerIndex].playerSlot);
    		addPlayer(thisPlayer);
    		seats[playerIndex].seatPlayer(thisPlayer);
    		thisPlayer.isConnected=false;
    		thisPlayer.isLoadedPlayer=true;
    		thisPlayer.hasBoughtIn=true;
    		thisPlayer.color=ColorPool.assignColor();
    		thisPlayer.chipAmount=amount;
    		thisPlayer.setBuyinBuild(ChipCase.calculateSimplestBuild(amount));
    		thisPlayer.name.loadTexture();
    		tableStateStr=tableStateStr.substring(endIndex+"<AMOUNT/>".length());
    	}
	}
	
	public int getNextFreeSeat(int azimuth) {
		int nextFree=-1;
		int thisAzimuth=mWL.game.calculateAzimuth();
		int deltaAzimuth_=thisAzimuth-azimuth;
		if (deltaAzimuth_<0) {
			deltaAzimuth_+=360;
		}
		int startingSeat_=Seat.getStartingSeat(deltaAzimuth_);
		for (int i=0;i<NUM_SEATS;i++) {
			int thisSeat=startingSeat_+((i+1)/2)*(int)(Math.pow(-1,i%2+1));
			if (thisSeat>=NUM_SEATS) {
				thisSeat-=NUM_SEATS;
			} else if (thisSeat<0) {
				thisSeat+=NUM_SEATS;
			}
			if (seats[thisSeat].player==null) {
				nextFree=thisSeat;
				break;
			}
		}
		return nextFree;
	}
	
}
