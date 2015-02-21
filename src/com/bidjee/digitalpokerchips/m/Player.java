package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;
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
	
	public static final int CONN_ANIM_NONE = 0;	
	public static final int CONN_ANIM_FORMING = 1;
	public static final int CONN_ANIM_STATIC = 2;
	public static final int CONN_ANIM_ACTIVE = 3;
	// state variables //
	public TextLabel name;
	public int color;
	public int state;
	private int startStackTimer;
	public boolean isFolded;
	public boolean isAllIn;
	public boolean hasBet;
	public boolean sittingOut;
	public boolean selected;
	public boolean getsRemainder;
	public int chipAmount;
	public boolean isConnected;
	public boolean isSeated;
	public boolean hasBoughtIn;
	public boolean isLoadedPlayer;
	public int[] buyinBuild;
	public int azimuth;
	public int connectionAnimationState;
	public boolean active;
	public boolean arrowsShowing;
	// Objects //
	public DPCSprite joinToken=new DPCSprite();
	public DPCSprite connectionSprite;
	public DPCSprite connectedSprite;
	public DPCSprite connectionArrows;
	public ChipStack bettingStack;
	public ChipStack betStack;
	public ChipStack winStack;
	
	public float xCoeff;
	public float yCoeff;
	
	private float nameOffset;
	private float connectionBlobOffset;
	private float arrowsOffset;
	
	public DPCSprite selectionHighlight;
	
	private Vector2 posJoinTokenStart=new Vector2();
	
	public Player(String name_) {
		name=new TextLabel(name_,0,false,0,false);
		name.bodyColor = ForegroundLayer.whiteColor;
		name.fontFace = "coolvetica_rg.ttf";
		name.maxOpacity=0.5f;
		isTouched=false;
		rotation=0;
		isFolded=false;
		isAllIn=false;
		hasBet=false;
		sittingOut=false;
		selected=false;
		getsRemainder=false;
		bettingStack=new ChipStack();
		betStack=new ChipStack();
		winStack=new ChipStack();
		chipAmount=0;
		isConnected=false;
		hasBoughtIn=false;
		joinToken.opacity=0;
		connectionSprite = new DPCSprite();
		connectionSprite.setFrameAnimation(27,50,false,0);
		connectedSprite = new DPCSprite();
		connectedSprite.setFrameAnimation(26,40,true,0);
		setConnectionAnimationState(CONN_ANIM_NONE);
		connectionArrows = new DPCSprite();
		connectionArrows.opacity=0;
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
		name.setMaxDimensions((int)(radiusX_*1.0f),(int)(radiusY_*0.6f));
		name.setTextSizeToMax(WorldLayer.NAME_MEASURE);
		joinToken.setDimensions((int)(radiusY*0.50f*1.57f),(int)(radiusY*0.50f));
		selectionHighlight.setDimensions((int)(radiusY*0.80f),(int)(radiusY*0.80f));
		connectionSprite.setDimensions((int)(radiusY*1.0f*1.6f),(int)(radiusY*1.0f));
		connectedSprite.setDimensions((int)(radiusY*1.0f*1.6f),(int)(radiusY*1.0f));
		connectionArrows.setDimensions((int)(radiusY*0.25f*6.2f),(int)(radiusY*0.25f));
	}
	
	public void setPosition(float x_,float y_,float rotation_) {
		nameOffset=radiusY*0.2f;
		arrowsOffset=radiusY*-0.80f;
		setRotation(rotation_);
		posJoinTokenStart.set((int)(x_+Math.sin(Math.toRadians(rotation_))*(Seat.radiusY+joinToken.radiusY+2)),
				(int)(y_-Math.cos(Math.toRadians(rotation_))*(Seat.radiusY+joinToken.radiusY+2)));
		joinToken.rotation=rotation_;
		connectionBlobOffset=-1*(Seat.radiusY-connectionSprite.radiusY*0.7f);
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
	}
	
	@Override
	public void setIsTouched(boolean isTouched) {
		super.setIsTouched(isTouched);
	}
	
	public void animate(float delta) {
		super.animate(delta);
		if (!isTouched) {
			name.animate(delta);
			
		}
		connectionSprite.animate(delta);
		if (connectionAnimationState==CONN_ANIM_FORMING) {
			if (!connectionSprite.frameAnimationRunning) {
				if (active) {
					setConnectionAnimationState(CONN_ANIM_ACTIVE);
				} else {
					setConnectionAnimationState(CONN_ANIM_STATIC);
				}
				if (arrowsShowing) {
					connectionArrows.fadeIn();
				} else {
					connectionArrows.fadeOut();
				}
			}
		}
		connectedSprite.animate(delta);
		connectionArrows.animate(delta);
		selectionHighlight.animate(delta);
	}
	
	private void setConnectionAnimationState(int state) {
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
			connectionArrows.fadeOut();
		}
	}
	
	public void startAnimateConnection() {
		setConnectionAnimationState(CONN_ANIM_FORMING);
	}
	
	public void setActive(boolean active) {
		this.active=active;
		if (connectionAnimationState==CONN_ANIM_STATIC||
				connectionAnimationState==CONN_ANIM_NONE||
				connectionAnimationState==CONN_ANIM_ACTIVE) {
			if (active) {
				setConnectionAnimationState(CONN_ANIM_ACTIVE);
			} else {
				setConnectionAnimationState(CONN_ANIM_STATIC);
			}
		}
	}
	
	public void setArrowsShowing(boolean showing) {
		this.arrowsShowing=showing;
		if (connectionAnimationState==CONN_ANIM_STATIC||
				connectionAnimationState==CONN_ANIM_NONE||
				connectionAnimationState==CONN_ANIM_ACTIVE) {
			if (showing) {
				connectionArrows.fadeIn();
			} else {
				connectionArrows.fadeOut();
			}
		}
	}
	
	public void disconnectedFromGame() {
		setConnectionAnimationState(CONN_ANIM_NONE);
		name.fadeOut();
		if (selected) {
			setSelected(false);
		}
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
		connectionSprite.x=x+xCoeff*connectionBlobOffset;
		connectedSprite.x=x+xCoeff*connectionBlobOffset;
		connectionArrows.x=x+xCoeff*arrowsOffset;
		selectionHighlight.x=x;
	}
	public void setY(float y) {
		this.y=y;
		name.y=y+yCoeff*nameOffset;
		connectionSprite.y=y+yCoeff*connectionBlobOffset;
		connectedSprite.y=y+yCoeff*connectionBlobOffset;
		connectionArrows.y=y+yCoeff*arrowsOffset;
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
		connectedSprite.rotation=rotation_+180;
		connectionSprite.rotation=rotation_+180;
		connectionArrows.rotation=rotation_;
		xCoeff=Math.round(-1*Math.sin(Math.toRadians(rotation_)));
		yCoeff=Math.round(Math.cos(Math.toRadians(rotation_)));
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

	public void seat() {
		betStack.scaleLabel();
		opacity=1;
		isSeated=true;
	}
	
	public void doRxJoinCoin() {
		state=STATE_RXING_JOIN_COIN;
		joinToken.opacity=1;
		joinToken.setPosition(posJoinTokenStart);
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
	
	public void setSelected(boolean selected) {
		this.selected=selected;
		if (selected) {
			selectionHighlight.fadeIn();
		} else {
			selectionHighlight.fadeOut();
		}
	}
	
}