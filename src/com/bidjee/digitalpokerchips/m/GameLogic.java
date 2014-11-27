package com.bidjee.digitalpokerchips.m;

import java.util.ArrayList;

import com.bidjee.digitalpokerchips.c.Table;
import com.bidjee.util.Logger;

public class GameLogic {
	
	public static final String LOG_TAG = "DPCGameLogic";
	
	public static final String PROMPT_MEASURE = "Open River Betting";
	
	public static final String PRE_FLOP_STRING = "Pre-Flop";
	public static final String FLOP_STRING = "Flop";
	public static final String TURN_STRING = "Turn";
	public static final String RIVER_STRING = "River";

	public static final String STATE_NONE="";
	public static final String STATE_WAITING_BUYINS="STATE_WAITING_BUYINS";
	public static final String STATE_NEXT_BET="STATE_NEXT_BET";
	public static final String STATE_WAITING_BET="STATE_WAITING_BET";
	public static final String STATE_END_ROUND="STATE_END_ROUND";
	public static final String STATE_WAIT_DEAL_PROMPT="STATE_WAIT_DEAL_PROMPT";
	public static final String STATE_FORM_POT="STATE_FORM_POT";
	public static final String STATE_WAIT_POOL_STACKS="STATE_WAIT_POOL_STACKS";
	public static final String STATE_FADEOUT_POT="STATE_FADEOUT_POT";
	public static final String STATE_WAIT_FADEIN_POT="STATE_WAIT_FADEIN_POT";
	public static final String STATE_PROCESS_POTS="STATE_PROCESS_POTS";
	public static final String STATE_SELECT_WINNER="STATE_SELECT_WINNER";
	public static final String STATE_SEND_WINNINGS="STATE_SEND_WINNINGS";
	public static final String STATE_RECALL_DEALER="STATE_RECALL_DEALER";
	public static final String STATE_REMOVE_PLAYERS="STATE_REMOVE_PLAYERS";
	public static final String STATE_START_HAND="STATE_START_HAND";
	public static final String STATE_FINISHED="STATE_FINISHED";
	public static final String STATE_EXIT_PENDING="STATE_EXIT_PENDING";
	public static final String STATE_FREEZE="STATE_FREEZE";
	public static final String STATE_WAIT_SAVE="STATE_WAIT_SAVE";
	
	public static final int DEAL_PRE_FLOP = 0;
	public static final int DEAL_FLOP = 1;
	public static final int DEAL_TURN = 2;
	public static final int DEAL_RIVER = 3;
	
	public static final int MOVE_CHECK = 0;
	public static final int MOVE_BET = 1;
	public static final int MOVE_FOLD = 2;
	public static final int MOVE_ALL_IN = 3;
	
	public static final int NO_PLAYER=-1;
	// References //
	Table table;
	
	// State Variables //
	public String state=STATE_NONE;
	public int dealStage;
	private int dealer;
	public int currBetter;
	int currStake;
	boolean smallBlindsIn;
	boolean bigBlindsIn;
	boolean openingBet;
	boolean waitingBuyins;
	boolean waitingForBet;
	boolean waitingDealPrompt;
	boolean waitingSave;
	
	public GameLogic(Table table) {
		this.table=table;
		dealer=NO_PLAYER;
		currBetter=NO_PLAYER;
	}
	
	public void setDimensions(float worldWidth,float worldHeight) {
	}
	
	public void setPositions(float worldWidth,float worldHeight) {
	}
	
	public void scalePositions(float scaleX,float scaleY) {
	}
	
	public void start() {
		setGameState(STATE_START_HAND);
	}
	
	public void setDealer(int dealer) {
		this.dealer=dealer;
	}
	
	public int getDealer() {
		return dealer;
	}
	
	public void collisionDetector() {

	}
	
	private void setGameState(String state) {
		this.state=state;
		Logger.log(LOG_TAG,"setGameState("+state+")");
	}
	
	public void updateLogic() {
		if (state.equals(STATE_START_HAND)) {
			resetGame();
			table.sendDealerButton(dealer);
			setGameState(STATE_NEXT_BET);
		} else if (state.equals(STATE_NEXT_BET)) {
			if (checkRoundComplete()) {
				currBetter=NO_PLAYER;
				if (currStake==0) {
					// no bets were placed so don't wait for pots to form
					setGameState(STATE_END_ROUND);
				} else {
					setGameState(STATE_FORM_POT);
				}
			} else {
				// if round not complete, open betting to next player
				openBetTo(getNextBetter(currBetter));
				setGameState(STATE_WAITING_BET);
			}
		} else if (state.equals(STATE_WAITING_BET)) {
			if (!waitingForBet) {
				setGameState(STATE_NEXT_BET);
			}
		} else if (state.equals(STATE_FORM_POT)) {
			table.showLastPot();
			table.disablePotArrows();
			if (formPotNeeded()) {
				table.formPots();
				if (formPoolStacks()) {
					makeNewPot();
				}
				setGameState(STATE_WAIT_POOL_STACKS);
			} else {
				setGameState(STATE_END_ROUND);
			}
		} else if (state.equals(STATE_END_ROUND)) {
			if (checkHandComplete()) {
				setGameState(STATE_PROCESS_POTS);
				table.showLastPot();
				table.disablePotArrows();
			} else {
				// if hand not complete, prompt dealer to deal next cards
				currStake=0;
				currBetter=dealer;
				for (int i=0;i<Table.NUM_SEATS;i++) {
					if (table.seats[i].player!=null)
						table.seats[i].player.hasBet=false;
				}
				switch (dealStage) {
				case DEAL_PRE_FLOP :
		    		dealStage=DEAL_FLOP;
		    		Logger.log(LOG_TAG,"*** FLOP ***");
					break;
				case DEAL_FLOP:
					dealStage=DEAL_TURN;
					Logger.log(LOG_TAG,"*** TURN ***");
					break;
				case DEAL_TURN:
					dealStage=DEAL_RIVER;
					Logger.log(LOG_TAG,"*** RIVER ***");
					break;
				default:
					break;
				} // end switch (dealStage)
				table.enablePotArrows();
				table.promptDealer(dealer,dealStage);
				table.syncAllTableStatusMenu();
				waitingDealPrompt=true;
				setGameState(STATE_WAIT_DEAL_PROMPT);
			}
		} else if (state.equals(STATE_WAIT_DEAL_PROMPT)) {
			if (!waitingDealPrompt) {
				setGameState(STATE_NEXT_BET);
			}
		} else if (state.equals(STATE_PROCESS_POTS)) {
			for (int i=0;i<Table.NUM_SEATS;i++) {
				if (table.seats[i].player!=null) {
					table.seats[i].player.selected=false;
					table.seats[i].player.getsRemainder=false;
				}
			}
			if (table.pots.size()==0) {
				setGameState(STATE_RECALL_DEALER);
			} else {
				if (table.pots.get(table.displayedPotIndex).potStack.value()==0) {
					// for when side pots are created but no one bets
					table.pots.remove(table.displayedPotIndex);
					if (table.pots.size()==0) {
						setGameState(STATE_RECALL_DEALER);
					} else {
						setGameState(STATE_WAIT_FADEIN_POT);
						table.fadeInNextPot();
					}
				} else {
					setGameState(STATE_SELECT_WINNER);
					if (table.pots.get(table.displayedPotIndex).playersEntitled.size()==1) {
						table.startWinnerByDefault(table.pots.get(table.displayedPotIndex).playersEntitled.get(0));
					} else {
						table.startSelectWinner();
					}
				}
			}
		} else if (state.equals(STATE_SELECT_WINNER)) {
			// do nothing - waiting for user interaction
		} else if (state.equals(STATE_SEND_WINNINGS)) {
			for (int i=0;i<Table.NUM_SEATS;i++) {
				if (table.seats[i].player!=null&&table.seats[i].player.winStack.size()>0) {
					Logger.log(LOG_TAG,table.seats[i].player.name.getText()+" wins "+
							table.seats[i].player.winStack.value());
					table.sendWinnings(i,table.seats[i].player.winStack);					
					table.seats[i].player.isAllIn=false;
				}
			}
			//flingPotLabel.fadeOut();
			//flingDemo.stop();
			table.pots.remove(table.displayedPotIndex);
			table.syncAllTableStatusMenu();
			if (table.pots.size()==0) {
				setGameState(STATE_RECALL_DEALER);
			} else {
				setGameState(STATE_WAIT_FADEIN_POT);
				table.fadeInNextPot();
			}	
		} else if (state.equals(STATE_RECALL_DEALER)) {
			setGameState(STATE_REMOVE_PLAYERS);
			table.recallDealerButton(dealer);
		} else if (state.equals(STATE_REMOVE_PLAYERS)) {
			for (int i=0;i<Table.NUM_SEATS;i++) {
				if (table.seats[i].player!=null) {
					if (table.seats[i].player.isAllIn) {
						table.removeFromTable(table.seats[i].player.name.getText());
					}
				}
			}
			table.unseatDisconnectedPlayers();
			if (table.countPlayers()>1) {
				setDealer(findNextDealer(dealer));
				waitingSave=true;
				table.saveTable();
				setGameState(STATE_WAIT_SAVE);
			} else {
				setGameState(STATE_FINISHED);
			}
		} else if (state.equals(STATE_WAIT_SAVE)) {
			if (!waitingSave) {
				waitingBuyins=true;
				table.processBuyins();
				setGameState(STATE_WAITING_BUYINS);
			}
		} else if (state.equals(STATE_WAITING_BUYINS)) {
			if (!waitingBuyins) {
				setGameState(STATE_START_HAND);
			}
		} else if (state.equals(STATE_FINISHED)) {
			// TODO clear saved game
		}
	}
	
	public void resetTable() {
		Logger.log(LOG_TAG,"resetTable()");
		currBetter=NO_PLAYER;
		setDealer(NO_PLAYER);
		removeAllPrompts();
	}
	
	public void removeAllPrompts() {
		table.mWL.game.mFL.blindsInLabel.fadeOut();
		table.mWL.game.mFL.flopLabel.fadeOut();
		table.mWL.game.mFL.turnLabel.fadeOut();
		table.mWL.game.mFL.riverLabel.fadeOut();
		table.mWL.game.mFL.selectWinnerLabel.fadeOut();
		table.mWL.game.mFL.selectWinnersSplitLabel.fadeOut();
		// TODO remove split button, side pot buttons, etc
	}
	
	///////////////////////// Private Helper Methods //////////////////////////
	private void resetGame() {
		Logger.log(LOG_TAG,"resetGame()");
		currBetter=dealer;
		currStake=0;
		dealStage=DEAL_PRE_FLOP;
		smallBlindsIn=false;
		bigBlindsIn=false;
		openingBet=false;
		for (int i=0;i<Table.NUM_SEATS;i++) {
			if (table.seats[i].player!=null) {
				table.seats[i].player.reset();
			}
		}
		table.clearPots();
		makeNewPot();
		//game.application.saveGame(buildGameStateString());
	}
	
	private int findNextDealer(int dealer_) {
		dealer_=dealer_+1;
		if (dealer_>=Table.NUM_SEATS) {
			dealer_=0;
		}
		while (table.seats[dealer_].player==null) {
			dealer_++;
			if (dealer_>=Table.NUM_SEATS) {
				dealer_=0;
			}
		}
		Logger.log(LOG_TAG,"findNextDealer("+dealer+")"+" = "+dealer_);
		return dealer_;
	}
	
	private int countFoldedPlayers() {
		int count_=0;
		for (int i=0;i<Table.NUM_SEATS;i++) {
			if (table.seats[i].player!=null&&table.seats[i].player.isFolded)
				count_++;
		}
		return count_;
	}
	
	private int countAllInPlayers() {
		int count_=0;
		for (int i=0;i<Table.NUM_SEATS;i++) {
			if (table.seats[i].player!=null&&table.seats[i].player.isAllIn)
				count_++;
		}
		return count_;
	}
	
	public int countSelectedPlayers() {
		int count_=0;
		for (int i=0;i<Table.NUM_SEATS;i++) {
			if (table.seats[i].player!=null&&table.seats[i].player.selected)
				count_++;
		}
		return count_;
	}
	
	private boolean formPotNeeded() {
		boolean moreBets=false;
		for (int i=0;i<Table.NUM_SEATS;i++) {
			if (table.seats[i].player!=null&&!table.seats[i].player.isFolded&&table.seats[i].player.betStack.value()>0)
				moreBets=true;
    	}
		Logger.log(LOG_TAG,"formPotNeeded() = "+moreBets);
		return moreBets;
	}
	
	private void makeNewPot() {
		ArrayList<Integer> playersEntitled_ = new ArrayList<Integer>();
		String entitled_="";
		for (int i=0;i<Table.NUM_SEATS;i++) {
			// add all players to the entitlement who aren't all in
			// also add players who are all in but still have some money in as they will be part of the next side pots
			if (table.seats[i].player!=null&&!table.seats[i].player.isFolded&&
					(!table.seats[i].player.isAllIn||table.seats[i].player.betStack.hasNonPoolingChips())) {
				playersEntitled_.add(i);
				entitled_=entitled_+" "+i;
			}
		}
		Logger.log(LOG_TAG,"makeNewPot()");
		table.makeNewPot(playersEntitled_);
	}
	
	public void removePlayer(int seat_) {
		
	}
	
	private void openBetTo(int nextBetter) {
		// open the betting to the next non-folded non-all in player
		Logger.log(LOG_TAG,"openBetTo("+nextBetter+")");
		currBetter=nextBetter;
		MovePrompt thisMovePrompt=new MovePrompt();
		if (!smallBlindsIn) {
			thisMovePrompt.stake=1;
			thisMovePrompt.blinds=MovePrompt.BLINDS_SMALL;
			table.mWL.game.mFL.blindsInLabel.startFlashing();
		} else if (!bigBlindsIn) {
			thisMovePrompt.stake=Math.max(currStake,1);
			thisMovePrompt.blinds=MovePrompt.BLINDS_BIG;
		} else if (openingBet) {
			thisMovePrompt.blinds=MovePrompt.BLINDS_NONE;
			if (dealStage==DEAL_FLOP) {
				table.mWL.game.mFL.flopLabel.fadeIn();
			} else if (dealStage==DEAL_TURN) {
				table.mWL.game.mFL.turnLabel.fadeIn();
			} else if (dealStage==DEAL_RIVER) {
				table.mWL.game.mFL.riverLabel.fadeIn();
			}
			thisMovePrompt.stake=0;
		} else {
			thisMovePrompt.blinds=MovePrompt.BLINDS_NONE;
			int amountToCall=currStake-table.seats[currBetter].player.betStack.value();
			thisMovePrompt.stake=amountToCall;
		}
		table.promptMove(currBetter,thisMovePrompt);
		waitingForBet=true;
	}
	
	private int getNextBetter(int currBetter) {
		int nextBetter=currBetter+1;
		if (nextBetter>=Table.NUM_SEATS) {
			nextBetter=0;
		}
		while (table.seats[nextBetter].player==null||
				table.seats[nextBetter].player.isFolded||
				table.seats[nextBetter].player.isAllIn) {
			nextBetter++;
			if (nextBetter>=Table.NUM_SEATS) {
				nextBetter=0;
			}
		}
		Logger.log(LOG_TAG,"getNextBetter("+currBetter+") = "+nextBetter);
		return nextBetter;
	}
	
	private boolean checkRoundComplete() {
    	// betting round is complete when all non-folded non-allin players have total bets equal to the current stake
    	// unless it's the pre-flop big blinds' first bet
    	boolean roundComplete=true;
    	for (int i=0;i<Table.NUM_SEATS;i++) {
   			if (table.seats[i].player!=null&&!table.seats[i].player.isFolded&&!table.seats[i].player.isAllIn) {
   				if (table.seats[i].player.betStack.value()!=currStake) {
   					roundComplete=false;
   				} else if (countFoldedPlayers()+countAllInPlayers()>=table.countPlayers()-1) {
   					// all but one is folded or all in
   					roundComplete=true;
   				} else if (!table.seats[i].player.hasBet) {
   					// not everyone has bet yet
   					roundComplete=false;
   				}
   			}
   		}
    	Logger.log(LOG_TAG,"checkRoundComplete() = "+roundComplete);
    	return roundComplete;    	
    } // end checkRoundComplete()
	
	public boolean checkHandComplete() {
		boolean complete=false;
		// if only one or less player remains unfolded and not all-in finish the hand
		if (countFoldedPlayers()+countAllInPlayers()>=table.countPlayers()-1) {
			complete=true;
		} else if (dealStage==DEAL_RIVER) {
			complete=true;
		}
		Logger.log(LOG_TAG,"checkHandComplete() = "+complete);
		return complete;
	}
	
    public boolean formPoolStacks() {
    	Logger.log(LOG_TAG,"formPoolStacks()");
    	boolean sidePotNeeded_=false;
    	int minBetAmount_=0;
    	int numPlayersBet_=0;
    	// find any non-folded player that bet this hand for a reference when calculating the minimum
    	for (int i=0;i<Table.NUM_SEATS;i++) {
    		if (table.seats[i].player!=null&&!table.seats[i].player.isFolded&&table.seats[i].player.betStack.value()>0) {
    			minBetAmount_=table.seats[i].player.betStack.value();
    			numPlayersBet_++;
    		}
    	}
		// find the lowest bet from a non-folded player
		for (int i=0;i<Table.NUM_SEATS;i++) {
			if (table.seats[i].player!=null&&!table.seats[i].player.isFolded) {
				if (table.seats[i].player.betStack.value()>0&&table.seats[i].player.betStack.value()<=minBetAmount_) {
					minBetAmount_=table.seats[i].player.betStack.value();
					if (table.seats[i].player.isAllIn&&numPlayersBet_>1) {
						sidePotNeeded_=true;
					}
				}
			}
		}
		// build up pool stacks
		int chipTally_=0;
		int numTop_=table.pots.get(table.displayedPotIndex).potStack.renderSize();
		for (int i=0;i<Table.NUM_SEATS;i++) {
			int seat_=Seat.zOrder[i];
			if (table.seats[seat_].player!=null) {
				if (table.seats[seat_].player.betStack.value()>minBetAmount_) {
					// make change if needed, i.e. for a side pot
					Pot.makeChange(table.seats[seat_].player.betStack,minBetAmount_,1);
    				// take minBetAmount out of bet stack and put into pool stack, this function alters the bet stack internally
					buildPoolStack(table.seats[seat_].player,minBetAmount_);
					table.seats[seat_].player.betStack.updateTotalLabel();
					table.seats[seat_].player.betStack.totalLabel.loadTexture();
				} else if (table.seats[seat_].player.betStack.value()>0) {
					for (int chip_=0;chip_<table.seats[seat_].player.betStack.size();chip_++) {
						table.seats[seat_].player.betStack.get(chip_).pooling=true;
					}
				}
				// set coordinates for pool stacks
				int poolCount_=0;
				int maxNum_=table.seats[seat_].player.betStack.maxRenderNum;
				int chipsPastMax_=0;
				float xDir_=Math.round(-1*Math.sin(table.seats[seat_].rotation*Math.PI/180));
				float yDir_=Math.round(Math.cos(table.seats[seat_].rotation*Math.PI/180));
				for (int chip_=0;chip_<table.seats[seat_].player.betStack.size();chip_++) {
					if (table.seats[seat_].player.betStack.get(chip_).pooling) {
						table.seats[seat_].player.betStack.get(chip_).setDest(table.pots.get(table.displayedPotIndex).potStack.getX(),
								table.pots.get(table.displayedPotIndex).potStack.getY(),numTop_+chipTally_+poolCount_);
						table.seats[seat_].player.betStack.get(chip_).rotation=0;
						if (chip_>=maxNum_) {
							table.seats[seat_].player.betStack.get(chip_).x=table.seats[seat_].player.betStack.getX()+xDir_*(maxNum_+chipsPastMax_);
							table.seats[seat_].player.betStack.get(chip_).y=table.seats[seat_].player.betStack.getY()+yDir_*(maxNum_+chipsPastMax_);
							chipsPastMax_++;
						}
						if (poolCount_<maxNum_)
							poolCount_++;
					}
				}
				chipTally_+=poolCount_;
			}
		}
		boolean remainingBets_=false;
		for (int i=0;i<Table.NUM_SEATS;i++) {
			if (table.seats[i].player!=null) {
				remainingBets_|=table.seats[i].player.betStack.hasNonPoolingChips();
			}
		}
		if (checkHandComplete()&&!remainingBets_)
			sidePotNeeded_=false;
		
    	return sidePotNeeded_;
    }
    
    private void buildPoolStack(Player player_,int amount_) {
    	Logger.log(LOG_TAG,"buildPoolStack("+player_.name.getText()+","+amount_);
    	int[] chipRequirement_=ChipCase.calculateSimplestBuild(amount_);
    	int[] canTake_=new int[ChipCase.CHIP_TYPES];
    	int[] numChip_=new int[ChipCase.CHIP_TYPES];
    	for (int chip_=0;chip_<ChipCase.CHIP_TYPES;chip_++) {
    		numChip_[chip_]=player_.betStack.countChipType(chip_);
    	}
    	for (int chip_=ChipCase.CHIP_C;chip_>=ChipCase.CHIP_A;chip_--) {
			if (numChip_[chip_]>=chipRequirement_[chip_]) {
				canTake_[chip_]=chipRequirement_[chip_];
			} else {
				canTake_[chip_]=numChip_[chip_];
				int remainder_=chipRequirement_[chip_]-numChip_[chip_];
				chipRequirement_[chip_-1]+=remainder_*(ChipCase.values[chip_]/ChipCase.values[chip_-1]);
			}
		}
    	for (int j=player_.betStack.size()-1;j>=0;j--) {
    		int thisChip_=player_.betStack.get(j).chipType;
			if (canTake_[thisChip_]>0) {
				player_.betStack.get(j).pooling=true;
				numChip_[thisChip_]--;
				canTake_[thisChip_]--;
			}
		}
    }
    
	public String buildGameStateString() {
		String outString_="";
		outString_=outString_.concat("<DEALER>"+dealer+"<DEALER/>");
		return outString_;
	}
	
	public void setGameStateFromString(String gameStateStr) {
		if (gameStateStr.contains("<DEALER>")&&gameStateStr.contains("<DEALER/>")) {
    		int startIndex=gameStateStr.indexOf("<DEALER>")+"<DEALER>".length();
    		int endIndex=gameStateStr.indexOf("<DEALER/>");
    		table.setDealer(Integer.parseInt(gameStateStr.substring(startIndex,endIndex)));
    	}
	}
	
	public void destroyTable() {
		Logger.log(LOG_TAG,"destroyTable()");
		resetTable();
	}
	
	public void notifyPlayerLeft(int seat) {
		Logger.log(LOG_TAG,"notifyPlayerLeft("+seat+")");
		table.seats[seat].player.isFolded=true;
		for (int i=0;i<table.pots.size();i++) {
			if (table.pots.get(i).playersEntitled.contains(seat)) {
				table.pots.get(i).playersEntitled.remove
					(table.pots.get(i).playersEntitled.indexOf(seat));
			}
    	}
		skipPlayer(seat);
	}
	
	public void notifyPlayerReconnected(int seat) {
		Logger.log(LOG_TAG,"notifyPlayerReconnected("+seat+")");
		if (dealer==seat) {
			table.sendDealerButton(seat);
		} else {
			table.recallDealerButton(seat);
		}
		if (currBetter==seat) {
			openBetTo(currBetter);
		} else {
			table.syncPlayersChips(seat);
		}
	}
	
	public void sitPlayerOut(int seat) {
		Logger.log(LOG_TAG,"sitPlayerOut("+seat+")");
		table.seats[seat].player.isFolded=true;
		for (int i=0;i<table.pots.size();i++) {
			if (table.pots.get(i).playersEntitled.contains(seat)) {
				table.pots.get(i).playersEntitled.remove
					(table.pots.get(i).playersEntitled.indexOf(seat));
			}
    	}
		skipPlayer(seat);
	}
	
	private void skipPlayer(int seat) {
		Logger.log(LOG_TAG,"skipPlayer("+seat+")");
		if (waitingForBet&&currBetter==seat) {
			waitingForBet=false;
			if (!smallBlindsIn) {
				smallBlindsIn=true;
			} else if (!bigBlindsIn) {
				bigBlindsIn=true;
				table.mWL.game.mFL.blindsInLabel.fadeOut();
			}
			if (openingBet) {
				openingBet=false;
			}
		}
	}

	public void moveRxd(int seatIndex,int move) {
		// every non-folded non-all-in must bet or check once, except in win by default cases
		//table.disableAllUndo();
		if (!smallBlindsIn) {
			smallBlindsIn=true;
		} else if (!bigBlindsIn) {
			bigBlindsIn=true;
			table.mWL.game.mFL.blindsInLabel.fadeOut();
		} else {
			table.seats[seatIndex].player.hasBet=true;
		}
		if (openingBet) {
			openingBet=false;
		}
		if (move==MOVE_CHECK) {
			//table.enableUndo(seatIndex);
			waitingForBet=false;
			table.seats[seatIndex].player.hasBet=true;
			Logger.log(LOG_TAG,"moveRxd - "+table.seats[seatIndex].player.name.getText()+" - CHECK");
		} else if (move==MOVE_FOLD) {
			waitingForBet=false;
			table.seats[seatIndex].player.isFolded=true;
			// remove player from entitlements to pots
			for (int i=0;i<table.pots.size();i++) {
				if (table.pots.get(i).playersEntitled.contains(seatIndex)) {
					table.pots.get(i).playersEntitled.remove
						(table.pots.get(i).playersEntitled.indexOf(seatIndex));
				}
        	}
			Logger.log(LOG_TAG,"moveRxd - "+table.seats[seatIndex].player.name.getText()+" - FOLD");
		} else if (move==MOVE_BET||move==MOVE_ALL_IN) {
			// set current stake if this is a raise
			int betValue=table.seats[seatIndex].player.bettingStack.value();
			int newTotal_=betValue+table.seats[seatIndex].player.betStack.value();
			if (newTotal_>currStake) {
				currStake=newTotal_;
			}
			table.seats[seatIndex].player.chipAmount-=
					table.seats[seatIndex].player.bettingStack.value();
			// set all in if this player just went all in
			if (move==MOVE_ALL_IN) {
				table.seats[seatIndex].player.isAllIn=true;
				Logger.log(LOG_TAG,"moveRxd - "+table.seats[seatIndex].player.name.getText()+" - ALL IN - "+betValue);
			} else {
				Logger.log(LOG_TAG,"moveRxd - "+table.seats[seatIndex].player.name.getText()+" - BET - "+betValue);
			}
		}
		table.mWL.game.mFL.flopLabel.fadeOut();
		table.mWL.game.mFL.turnLabel.fadeOut();
		table.mWL.game.mFL.riverLabel.fadeOut();
	}
	
	public void bettingStackArrived() {
		waitingForBet=false;
	}
	
	public void formPotsDone() {
		setGameState(STATE_FORM_POT);
	}
	
	public void fadeInPotDone() {
		setGameState(STATE_PROCESS_POTS);
	}
	
	public void sendWinnings() {
		setGameState(STATE_SEND_WINNINGS);
	}
	
	public void saveDone() {
		waitingSave=false;
	}
	
	public void buyinsDone() {
		waitingBuyins=false;
	}

	public boolean isBetting(int seat) {
		if (waitingForBet&&currBetter==seat) {
			return true;
		} else {
			return false;
		}
	}
	
	public void dealPromptDone() {
		waitingDealPrompt=false;
	}
	
	public static String getDealStageString(int dealStage) {
		String str="";
		if (dealStage==DEAL_PRE_FLOP) {
			str = PRE_FLOP_STRING;
		} else if (dealStage==DEAL_FLOP) {
			str = FLOP_STRING;
		} else if (dealStage==DEAL_TURN) {
			str = TURN_STRING;
		} else if (dealStage==DEAL_RIVER) {
			str = RIVER_STRING;
		}
		
		return str;
	}

}