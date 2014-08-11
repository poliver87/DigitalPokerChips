package com.bidjee.digitalpokerchips.m;

import java.util.ArrayList;

public class Hand {
	
	static final int DO_NOTHING = 0;
	static final int ADD_BET_CHIP = 1;
	static final int ENABLE_BET_FOLLOW_HAND = 2;
	static final int DISABLE_BET_FOLLOW_HAND = 3;;
	static final int PICK_UP_CHIP = 4;
	static final int DROP_CHIP = 5;
	static final int FINISH = 6;
	
	float x;
	float y;
	float xOrigin;
	float yOrigin;
	int radiusX;
	int radiusY;
	float destX;
	float destY;
	
	int stopIndex;
	
	boolean doingHelp;
	
	float opacity;
	
	float velocityX;
	float velocityY;
	
	ArrayList<HandStop> stops;
	ArrayList<TextLabel> instructions;
	
	public Hand() {
		stops=new ArrayList<HandStop>();
		instructions=new ArrayList<TextLabel>();
		stopIndex=0;
		doingHelp=false;
		opacity=0;
		velocityX=0;
		velocityY=0;
		instructions.add(new TextLabel("Tap Here to Check",0,true,0,false));
		instructions.add(new TextLabel("Tap Here to Fold",0,true,0,false));
		instructions.add(new TextLabel("Tap Stack to Build Bet",0,true,0,false));
		instructions.add(new TextLabel("Fling Up to Bet",0,true,0,false));
		instructions.add(new TextLabel("Drag Chip to Make Change",0,true,0,false));
	}
	/*
	public void setDimensions(int radiusX_,int radiusY_) {
		radiusX=radiusX_;
		radiusY=radiusY_;
	}
	
	public void setPosition(float x_,float y_,float scaleX_,float scaleY_) {
		xOrigin=x_;
		yOrigin=y_;
		if (!doingHelp) {
			x=x_;
			y=y_;
		} else {
			x*=scaleX_;
			y*=scaleY_;
		}
	}
	
	public void scaleInstructions(int screenWidth_,int screenHeight_) {
		int tmpTextSize=2000;
		int index_=0;
		instructions.get(index_).setMaxSize((int)(screenWidth_*0.2f),(int)(screenHeight_*0.08f));
		tmpTextSize=Math.min(tmpTextSize,TextLabel.getMaxTextSize(instructions.get(index_)));
		index_++;
		instructions.get(index_).setMaxSize((int)(screenWidth_*0.2f),(int)(screenHeight_*0.08f));
		tmpTextSize=Math.min(tmpTextSize,TextLabel.getMaxTextSize(instructions.get(index_)));
		index_++;
		instructions.get(index_).setMaxSize((int)(screenWidth_*0.2f),(int)(screenHeight_*0.08f));
		tmpTextSize=Math.min(tmpTextSize,TextLabel.getMaxTextSize(instructions.get(index_)));
		index_++;
		instructions.get(index_).setMaxSize((int)(screenWidth_*0.2f),(int)(screenHeight_*0.08f));
		tmpTextSize=Math.min(tmpTextSize,TextLabel.getMaxTextSize(instructions.get(index_)));
		index_++;
		instructions.get(index_).setMaxSize((int)(screenWidth_*0.2f),(int)(screenHeight_*0.08f));
		tmpTextSize=Math.min(tmpTextSize,TextLabel.getMaxTextSize(instructions.get(index_)));
		for (int i=0;i<instructions.size();i++) {
			instructions.get(i).setTextSize(tmpTextSize);
		}
	}
	
	public void layoutInstructions(int screenWidth_,int screenHeight_) {
		int index_=0;
		instructions.get(index_).setPosition(screenWidth_*0.5f,screenHeight_*0.7f);
		index_++;
		instructions.get(index_).setPosition(screenWidth_*0.7f,screenHeight_*0.7f);
		index_++;
		instructions.get(index_).setPosition(screenWidth_*0.5f,screenHeight_*0.26f);
		index_++;
		instructions.get(index_).setPosition(screenWidth_*0.2f,screenHeight_*0.9f);
		index_++;
		instructions.get(index_).setPosition(screenWidth_*0.5f,screenHeight_*0.26f);
	}
	
	public void initStops(int screenWidth,int screenHeight,GameScreen mGS) {
		stops.clear();
		// check motion
		int instructionIndex_=0;
		stops.add(new HandStop(mGS.checkButton.x-radiusX,mGS.checkButton.y+radiusY*2,instructionIndex_++,0));
		stops.add(new HandStop(mGS.checkButton.x-radiusX,mGS.checkButton.y+radiusY*0.5f,-1,0));
		stops.add(new HandStop(mGS.checkButton.x-radiusX,mGS.checkButton.y+radiusY*2,-1,0));
		// fold motion
		stops.add(new HandStop(mGS.foldButton.x-radiusX,mGS.foldButton.y+radiusY*2,instructionIndex_++,0));
		stops.add(new HandStop(mGS.foldButton.x-radiusX,mGS.foldButton.y+radiusY*0.5f,-1,0));
		stops.add(new HandStop(mGS.foldButton.x-radiusX,mGS.foldButton.y+radiusY*2,-1,0));
		// build bet motion
		//int numTop=Math.min(mGS.mainStacks[Chip.CHIP_B].size(),mGS.mainStacks[Chip.CHIP_B].maxRenderNum);
		float yTop=mGS.mainStacks[Chip.CHIP_B].getY();//+numTop*Chip.height;
		stops.add(new HandStop(mGS.mainStacks[Chip.CHIP_B].getX()-radiusX,yTop+radiusY*2f,instructionIndex_++,0));
		stops.add(new HandStop(mGS.mainStacks[Chip.CHIP_B].getX()-radiusX,yTop+radiusY*0.5f,-1,ADD_BET_CHIP));
		stops.add(new HandStop(mGS.mainStacks[Chip.CHIP_B].getX()-radiusX,yTop+radiusY*2f,-1,0));
		// fling up to bet motion
		stops.add(new HandStop(mGS.betStack.getX()-radiusX,mGS.betStack.getY()+radiusY,instructionIndex_++,ENABLE_BET_FOLLOW_HAND));
		stops.add(new HandStop(mGS.betStack.getX()-radiusX,mGS.limBetStackTopY,-1,0));
		// bring back bet motion
		stops.add(new HandStop(mGS.mainStacks[Chip.CHIP_B].getX()+radiusX,yTop+radiusY*0.5f,-1,DISABLE_BET_FOLLOW_HAND));
		// make change motion
		stops.add(new HandStop(mGS.mainStacks[Chip.CHIP_B].getX()-radiusX,yTop+radiusY*2f,instructionIndex_++,0));
		stops.add(new HandStop(mGS.mainStacks[Chip.CHIP_B].getX()-radiusX,yTop+radiusY*0.5f,-1,PICK_UP_CHIP));
		stops.add(new HandStop(mGS.mainStacks[Chip.CHIP_A].getX()-radiusX,mGS.mainStacks[Chip.CHIP_A].getY()+radiusY,-1,DROP_CHIP));
		// end
		stops.add(new HandStop(xOrigin,yOrigin,-1,FINISH));
		destX=stops.get(stopIndex).x;
		destY=stops.get(stopIndex).y;
	}
	
	public void start() {
		doingHelp=true;
		destX=stops.get(stopIndex).x;
		destY=stops.get(stopIndex).y;
	}
	
	public void kill() {
		if (stopIndex>0&&stops.get(stopIndex-1).instructionIndex>=0) {
			instructions.get(stops.get(stopIndex-1).instructionIndex).fadeOut();
		}
		stopIndex=stops.size()-1;
		destX=xOrigin;
		destY=yOrigin;
	}
	
	public void animate(float delta,GameScreen mGS_) {
		if (Math.abs(y-destY)<2&&
				Math.abs(x-destX)<1) {
			if (stopIndex!=0&&stops.get(stopIndex-1).instructionIndex>=0) {
				instructions.get(stops.get(stopIndex-1).instructionIndex).fadeOut();
			}
			if (stops.get(stopIndex).instructionIndex>=0) {
				instructions.get(stops.get(stopIndex).instructionIndex).fadeIn();
			}
			if (stops.get(stopIndex).methodCall>0) {
				//mGS_.handCallback(stops.get(stopIndex).methodCall);
			}
			stopIndex++;
			if (stopIndex<stops.size()) {
				destX=stops.get(stopIndex).x;
				destY=stops.get(stopIndex).y;
			} else {
				doingHelp=false;
				stopIndex=0;
				for (int i=0;i<stops.size();i++) {
					if (stops.get(i).instructionIndex>=0) {
						instructions.get(stops.get(i).instructionIndex).opacity=0;
					}
				}
			}
		} else {
			float timeFactor = delta*4;
			y=(float)(y-timeFactor*(y-destY));
			x=(float)(x-timeFactor*(x-destX));
		}
	}
	
	
*/
}
