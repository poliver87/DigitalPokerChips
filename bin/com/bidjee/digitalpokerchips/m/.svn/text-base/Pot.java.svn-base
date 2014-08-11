package com.bidjee.digitalpokerchips.m;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.bidjee.digitalpokerchips.c.Table;

public class Pot {
	
	public static float originX;
	public static float originY;
	
	public ArrayList<Integer> playersEntitled;
	public ChipStack potStack;
	private Seat destinationSeat;
	
	public Pot() {
		playersEntitled=new ArrayList<Integer>();
		potStack=new ChipStack();
	}
	
	public void setPosition(float x_,float y_,float widthScale_,float heightScale_) {
		potStack.setPosition(x_,y_,0);
	}
	
	public void scalePosition(float scaleX_, float scaleY_) {
		potStack.scalePosition(scaleX_, scaleY_);
	}
	
	public void setDest(Seat seat_) {
		destinationSeat=seat_;
		potStack.setRotation(seat_.rotation);
		potStack.setDest(seat_.x, seat_.y,0);
	}

	public Seat getDest() {return destinationSeat;}
    
    public static int makeChange(ChipStack stack_,int value_,int N_) {
    	// iterate over stack to find if there are M x N of chip A
    	// where M is the number of chip A in the simplest build
		// and N is the number of winners to be paid
		// if not replace the first occurrence (top to bottom) of chip B with chip As
    	// if there's no chip B change chip C down to chip B then loop again
		// once there are enough chip A check for enough chip B
    	// if there's not enough chip B check if there's enough value in chip As excluding
    	// what's needed to satisfy the chip A requirement, if not break down a chip C and loop
    	boolean keepLooking = true;
    	int[] simplestBuild_=ChipCase.calculateSimplestBuild(value_/N_);
    	int valueSimplestBuild_=simplestBuild_[ChipCase.CHIP_A]*ChipCase.values[ChipCase.CHIP_A]+
    			simplestBuild_[ChipCase.CHIP_B]*ChipCase.values[ChipCase.CHIP_B]+
    			simplestBuild_[ChipCase.CHIP_C]*ChipCase.values[ChipCase.CHIP_C];
    	int remainder_=(value_-valueSimplestBuild_*N_)/ChipCase.values[ChipCase.CHIP_A];
    	int numBNeeded_ = simplestBuild_[ChipCase.CHIP_B]*N_;
    	int numANeeded_ = simplestBuild_[ChipCase.CHIP_A]*N_+remainder_;
    	while (keepLooking) {
    		keepLooking=false;
    		int numA_=stack_.countChipType(ChipCase.CHIP_A);
    		// If there aren't enough As make change and loop
    		if (numA_<numANeeded_) {
    			keepLooking=true;
    			if (!stack_.changeDown(ChipCase.CHIP_B)) {
    				stack_.changeDown(ChipCase.CHIP_C);
    			}
    		}
    		numA_=stack_.countChipType(ChipCase.CHIP_A);
    		int numB_=stack_.countChipType(ChipCase.CHIP_B);
    		int excessAs=Math.max(numA_-numANeeded_,0);
    		if (numB_*ChipCase.values[ChipCase.CHIP_B]+excessAs*ChipCase.values[ChipCase.CHIP_A]<numBNeeded_*ChipCase.values[ChipCase.CHIP_B]) {
    			keepLooking=true;
    			stack_.changeDown(ChipCase.CHIP_C);
    		}
    	}
    	return remainder_;
    }
    
    public void formSplitPots(Seat[] seats_,int N_) {
    	// for each player, form stack but be sure to leave at least M x N of each chip
    	// where M is the number of that chip in the simplest build
		// and N is the number of winners' stacks yet to be built
    	Gdx.app.log("DPC", "potStack: "+potStack.toString());
    	int[] simplestBuild_=ChipCase.calculateSimplestBuild(potStack.value()/N_);
    	int[] numChip_=new int[ChipCase.CHIP_TYPES];
    	for (int chip_=0;chip_<ChipCase.CHIP_TYPES;chip_++) {
    		numChip_[chip_]=potStack.countChipType(chip_);
    		Gdx.app.log("DPC", "simplestBuild_"+chip_+": "+simplestBuild_[chip_]);
    		Gdx.app.log("DPC", "numChip_"+chip_+": "+numChip_[chip_]);
    	}
    	int chipsPastMax_=0;
    	for (int i=Table.NUM_SEATS-1;i>=0;i--) {
    		int seat_=Seat.zOrder[i];
    		if (seats_[seat_].player!=null&&seats_[seat_].player.selected) {
    			Gdx.app.log("DPC", "Player: "+seat_);
    			int heightCounter_=0;
    			int[] canTake_=new int[ChipCase.CHIP_TYPES];
    			int[] chipRequirement_=Arrays.copyOf(simplestBuild_,ChipCase.CHIP_TYPES);
    			if (seats_[seat_].player.getsRemainder) {
    				chipRequirement_[ChipCase.CHIP_A]++;
    				Gdx.app.log("DPC", "gets remainder");
    			}
    			for (int chip_=ChipCase.CHIP_C;chip_>=ChipCase.CHIP_A;chip_--) {
    				if (numChip_[chip_]>=chipRequirement_[chip_]) {
        				canTake_[chip_]=chipRequirement_[chip_];
        			} else {
        				canTake_[chip_]=numChip_[chip_];
        				int remainder_=chipRequirement_[chip_]-numChip_[chip_];
        				if (chip_>0) {
        					chipRequirement_[chip_-1]+=remainder_*(ChipCase.values[chip_]/ChipCase.values[chip_-1]);
        				} else {
        					Gdx.app.log("DPC", "formSplitPots remainder: "+remainder_+" chipARequirement: "+chipRequirement_[ChipCase.CHIP_A]+" numA: "+numChip_[ChipCase.CHIP_A]);
        				}
        			}
    			}
    			for (int chip_=0;chip_<ChipCase.CHIP_TYPES;chip_++) {
    	    		Gdx.app.log("DPC", "chipRequirement_"+chip_+": "+chipRequirement_[chip_]);
    	    		Gdx.app.log("DPC", "canTake_"+chip_+": "+canTake_[chip_]);
    	    	}
    			for (int j=0;j<potStack.size();j++) {
    				if (potStack.get(j).destSeat==null) {
    					int thisChip_=potStack.get(j).chipType;
    					if (canTake_[thisChip_]>0) {
    						potStack.get(j).setDest(seats_[seat_].x,seats_[seat_].y,heightCounter_);
    						potStack.get(j).destSeat=seats_[seat_];
    						numChip_[thisChip_]--;
    						canTake_[thisChip_]--;
    						if (j>=potStack.maxRenderNum&&heightCounter_<potStack.maxRenderNum) {
    							// TODO test this
    							potStack.get(j).z=chipsPastMax_+potStack.maxRenderNum;
    							chipsPastMax_++;
    						}
    						heightCounter_++;
        				}
    				}
    			}
    		}
    	}
    }
	
    @Override
    public String toString() {
    	return "pot: "+potStack.toString();
    }
}
