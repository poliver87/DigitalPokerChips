package com.bidjee.digitalpokerchips.m;

import com.bidjee.digitalpokerchips.c.WorldLayer;

public class Player extends DPCSprite {
	// constants //
	public static final int DURATION_START_STACK = 200;
	public static final int DURATION_ARROW_WAIT = 100;
	
	public static final int STATE_NONE = 0;
	public static final int STATE_RXING_JOIN_COIN = 1;
	public static final int STATE_PAUSING_START_STACK = 2;
	public static final int STATE_SENDING_START_STACK = 3;
	
	public static final int ARROW_NONE = 0;
	public static final int ARROW_WAITING = 1;
	public static final int ARROW_SENDING = 2;
	// state variables //
	public TextLabel name;
	public String hostName;
	public int color;
	public int state;
	private int startStackTimer;
	public boolean isFolded;
	public boolean isAllIn;
	public boolean hasBet;
	public boolean exitPending;
	public boolean sittingOut;
	public boolean selected;
	public boolean getsRemainder;
	public int chipAmount;
	public boolean waitingSetupACK;
	public boolean waitingWinningsACK;
	public boolean isConnected;
	private int[] buyinBuild;
	public int azimuth;
	public boolean connectionShowing;
	// Objects //
	public DPCSprite joinToken=new DPCSprite();
	public DPCSprite connectionBlob;
	public ChipStack bettingStack;
	public ChipStack betStack;
	public ChipStack winStack;
	
	public float xCoeff;
	public float yCoeff;
	
	private float nameOffset;
	
	private float connectionBlobOffset;
	
	public DPCSprite selectionHighlight;
	
	private Card card;
	
	public Player(String name_,String hostName_,DPCSprite connectionBlob) {
		hostName=hostName_;
		name=new TextLabel(name_,0,false,0,false);
		name.maxOpacity=0.5f;
		isTouched=false;
		rotation=0;
		isFolded=false;
		isAllIn=false;
		hasBet=false;
		exitPending=false;
		sittingOut=false;
		selected=false;
		getsRemainder=false;
		bettingStack=new ChipStack();
		betStack=new ChipStack();
		winStack=new ChipStack();
		chipAmount=0;
		waitingSetupACK=false;
		waitingWinningsACK=false;
		isConnected=false;
		joinToken.opacity=0;
		if (connectionBlob!=null) {
			copyConnectionBlobFrom(connectionBlob);
		}
		selectionHighlight=new DPCSprite();
		selectionHighlight.opacity=0;
		selectionHighlight.setFadeInSpeed(10f);
		selectionHighlight.setFadeOutSpeed(10f);
	}
	
	public void reset() {
		joinToken.opacity=0;
		hasBet=false;
		isFolded=false;
		isAllIn=false;
		isTouched=false;
		sittingOut=false;
		selected=false;
		getsRemainder=false;
		state=STATE_NONE;
		resetBettingStack();
		resetBetStack();
		resetWinStack();
		// TODO decide what to do with chip amounts etc
		waitingSetupACK=false;
		waitingWinningsACK=false;
		selectionHighlight.fadeOut();
		selectionHighlight.opacity=0;
	}
	
	public void resetWinStack() {
		winStack.clear();
		winStack.setX(x);
		winStack.setY(y);
		winStack.setVelocityX(0);
		winStack.setVelocityY(0);
	}
	
	public void resetBettingStack() {
		bettingStack.clear();
		bettingStack.setVelocityX(0);
		bettingStack.setVelocityY(0);
	}
	
	public void resetBetStack() {
		betStack.clear();
		betStack.setVelocityX(0);
		betStack.setVelocityY(0);
	}
	
	public void setDimensions(int radiusX_,int radiusY_) {
		radiusX=radiusX_;
		radiusY=radiusY_;
		betStack.scaleLabel();
		name.setMaxDimensions((int)(radiusX_*0.6f),(int)(radiusY_*0.3f));
		name.setTextSizeToMax(WorldLayer.NAME_MEASURE);
		joinToken.setDimensions((int)(radiusY*0.80f),(int)(radiusY*0.80f));
		selectionHighlight.setDimensions((int)(radiusY*0.80f),(int)(radiusY*0.80f));
	}
	
	public void setPosition(float x_,float y_,float rotation_) {
		nameOffset=radiusY*0.2f;
		setRotation(rotation_);
		joinToken.setPosition((int)(x_+Math.sin(Math.toRadians(rotation_))*(Seat.radiusY+joinToken.radiusY+2)),
				(int)(y_-Math.cos(Math.toRadians(rotation_))*(Seat.radiusY+joinToken.radiusY+2)));
		joinToken.rotation=rotation_;
		connectionBlobOffset=Seat.radiusY;
		setX(x_);
		setY(y_);
	}
	
	public void scalePosition(float scaleX_,float scaleY_) {
		setX(x*scaleX_);
		setY(y*scaleY_);
		if (bettingStack.size()>0) {
			bettingStack.scalePosition(scaleX_,scaleY_);
		}
		if (betStack.size()>0) {
			betStack.scalePosition(scaleX_,scaleY_);
		}
		if (winStack.size()>0) {
			winStack.scalePosition(scaleX_,scaleY_);
		}
		joinToken.scalePosition(scaleX_,scaleY_);
		connectionBlob.scalePosition(scaleX_,scaleY_);
	}
	
	@Override
	public void setIsTouched(boolean isTouched) {
		super.setIsTouched(isTouched);
	}
	
	public void animate(float delta) {
		super.animate(delta);
		if (!isTouched) {
			name.animate(delta);
			connectionBlob.animate(delta);
		}
		selectionHighlight.animate(delta);
	}
	
	public void fadeOut(float delta) {
		if (opacity>0) {
			opacity-=delta*5f;
			if (opacity<0)
				opacity=0;
		}
	}
	
	public void fadeIn(float delta) {
		if (opacity<1) {
			opacity+=delta*2f;
			if (opacity>1)
				opacity=1;
		}
	}
	
	public void setOpacity(float o_) {
		opacity=o_;
		name.opacity=o_;
	}

	public void setX(float x) {
		this.x=x;
		name.x=x+xCoeff*nameOffset;
		connectionBlob.x=x+xCoeff*connectionBlobOffset;
		selectionHighlight.x=x;
	}
	public void setY(float y) {
		this.y=y;
		name.y=y+yCoeff*nameOffset;
		connectionBlob.y=y+yCoeff*connectionBlobOffset;
		selectionHighlight.y=y;
	}
	
	public float getX() {return x;}
	public float getY() {return y;}

	public void copyWinStackFrom(ChipStack stack_) {
		winStack.clear();
		winStack=stack_.copy();
		winStack.setRotation(rotation);
		winStack.setX(x);
		winStack.setY(y);
	}
	
	public void setBettingStack(ChipStack bettingStack_) {
		bettingStack=bettingStack_;
		bettingStack.setRotation(rotation);
		float z_=betStack.size();
		for (int i=0;i<bettingStack.size();i++) {
			bettingStack.get(i).z=z_+i;
		}
		/*
		if (rotation==0||rotation==360) {
			bettingStack.setX(x);
			bettingStack.setY(0-(bettingStack.getLastRendered().getProjectedY()-bettingStack.get(0).getProjectedY())-
					bettingStack.getLastRendered().getProjectedRadiusY());
			bettingStack.setDest(x,y,z_);
		} else if (rotation==-90||rotation==270) {
			bettingStack.setY(y);
			bettingStack.setX(0-(bettingStack.getLastRendered().getProjectedX()-bettingStack.get(0).getProjectedX())-
					bettingStack.getLastRendered().getProjectedRadiusY());
			bettingStack.setDest(x,y,z_);
		} else if (rotation==180||rotation==-180) {
			bettingStack.setX(x);
			bettingStack.setY(Gdx.graphics.getHeight()+(bettingStack.get(0).getProjectedY()-bettingStack.getLastRendered().getProjectedY())+
					bettingStack.getLastRendered().getProjectedRadiusY());
			bettingStack.setDest(x,y,z_);
		} else if (rotation==90||rotation==-270) {
			bettingStack.setY(y);
			bettingStack.setX(Gdx.graphics.getWidth()+(bettingStack.get(0).getProjectedX()-bettingStack.getLastRendered().getProjectedX())+
					bettingStack.getLastRendered().getProjectedRadiusY());
			bettingStack.setDest(x,y,z_);
		}
		*/
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setRotation(float rotation_) {
		rotation=rotation_;
		bettingStack.setRotation(rotation_);
		betStack.setRotation(rotation_);
		winStack.setRotation(rotation_);
		joinToken.rotation=rotation_;
		
		xCoeff=Math.round(Math.sin(Math.toRadians(rotation_)));
		yCoeff=Math.round(-1*Math.cos(Math.toRadians(rotation_)));
		setX(x);
		setY(y);
	}

	public void buildWinStackFrom(int[] build_) {
		winStack.clear();
		winStack.buildStackFrom(build_);
		winStack.setRotation(rotation);
		winStack.setX(x);
		winStack.setY(y);
		winStack.setZ(0);
		winStack.setOpacity(0);
	}

	public void changeJoinCoin() {
		joinToken.opacity=0;
		state=STATE_PAUSING_START_STACK;
		startStackTimer=0;
		winStack.clear();
		winStack.buildStackFrom(buyinBuild);
		winStack.setRotation(rotation);
		winStack.setX(x);
		winStack.setY(y);
		winStack.setZ(0);
		winStack.setOpacity(1);
	}

	public void seat(Seat seat) {
		betStack.scaleLabel();
		opacity=1;
	}
	
	public void copyConnectionBlobFrom(DPCSprite connectionBlob) {
		this.connectionBlob=new DPCSprite(connectionBlob);
		this.connectionBlob.fadeOut();
		this.connectionBlob.opacity=0;
	}
	
	public void doRxJoinCoin() {
		state=STATE_RXING_JOIN_COIN;
		joinToken.opacity=1;
		waitingSetupACK=true;
	}

	public void pauseStartStack(float delta_) {
		startStackTimer+=delta_*1000;
		if (startStackTimer>DURATION_START_STACK) {
			state=STATE_SENDING_START_STACK;
		}
	}

	public void sentStartStack() {
		state=STATE_NONE;
		chipAmount=winStack.value();
		winStack.clear();
	}

	public void setName(String name_) {
		name.setText(name_);
		// set x and y to centre name
		setX(x);
		setY(y);
	}
	
	public void setBuyinBuild(int[] build) {
		buyinBuild=build;
	}
	
	public void giveCard(Card card) {
		card.setPosition(x-xCoeff*Seat.radiusY*0.5f,y-yCoeff*Seat.radiusY*0.25f);
		this.card=card;
	}
	
	public Card getCard() {
		return card;
	}
	
	public void setSelected(boolean selected) {
		this.selected=selected;
		if (selected) {
			selectionHighlight.fadeIn();
		} else {
			selectionHighlight.fadeOut();
		}
	}

	public void setConnectionShowing(boolean showing) {
		connectionShowing=showing;
		if (showing) {
			connectionBlob.fadeIn();
		} else {
			connectionBlob.fadeOut();
		}
	}
	
}