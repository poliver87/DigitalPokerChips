package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlayerDashboard extends DPCSprite {
	
	public static final String MESSAGE_SEARCHING = "SEARCHING FOR GAMES";
	public static final String MESSAGE_CONNECTED = "Connected to ";
	public static final String MESSAGE_ARRANGE = "ARRANGE POSITIONS";
	public static final String MESSAGE_SELECT_DEALER = "SELECT DEALER";
	public static final String MESSAGE_BLINDS = "MESSAGE_BLINDS";
	public static final String MESSAGE_BET_OR_CHECK = "Check or Bet";
	public static final String MESSAGE_CALL = " to Call";
	public static final String MESSAGE_WIN = "$$ CHING CHING $$";
	public static final String MESSAGE_DEAL = "Deal ";
	public static final String MESSAGE_DEAL_WAIT = " is Dealing ";
	public static final String MESSAGE_BET_WAIT = " is Betting";
	public static final String MESSAGE_SHOW_CARDS = "Show Your Cards";
	public static final String MESSAGE_WAIT_NEXT_HAND = "Wait for Next Hand";
	public static final String MESSAGE_CONNECTION_LOST = "Attempting to Reconnect";
	public static final String MESSAGE_NO_WIFI = "Please Connect to WiFi";
	
	public DPCSprite profilePic = new DPCSprite();
	public TextLabel nameLabel;
	public TextLabel amountLabel;
	public String gameName;
	
	public TextLabel statusMessage;
	public DPCSprite statusIcon=new DPCSprite();
	public String statusIconTexture;
	
	public Button backButton;
	public Button bellButton;
	
	Vector2 posOffscreen;
	public Vector2 posOnscreen;
	
	public DPCSprite idBackground=new DPCSprite();
	public DPCSprite statusBackground=new DPCSprite();
	
	public TextLabel winLabel;
	Vector2 winLabelStart;
	Vector2 winLabelStop;
	
	int margin;
	
	public PlayerDashboard() {
		nameLabel = new TextLabel("",0,false,0,false);
		nameLabel.setFontFace("coolvetica_rg.ttf");
		nameLabel.bodyColor=new Color(1f,0.94f,0.77f,1);
		amountLabel = new TextLabel("",0,false,1,false);
		amountLabel.setFontFace("coolvetica_rg.ttf");
		amountLabel.bodyColor=new Color(1f,0.88f,0.55f,1);
		statusMessage = new TextLabel("",0,false,0,false);
		statusMessage.setFontFace("coolvetica_rg.ttf");
		statusMessage.bodyColor=new Color(1f,0.91f,0.66f,1);
		statusIconTexture="";
		backButton = new Button(false,1,"");
		bellButton = new Button(false,0,"");
		gameName="";
		winLabel = new TextLabel("",0,false,1,false);
		winLabel.setFontFace("coolvetica_rg.ttf");
		winLabel.bodyColor=new Color(1f,0.88f,0.55f,1);
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX, radiusY);
		margin = (int) (radiusY*0.1f);
		
		backButton.setDimensions((int)(radiusY*0.8f*2.5f),(int)(radiusY*0.8f));
		
		idBackground.setDimensions(backButton.radiusX,backButton.radiusY);
		statusBackground.setDimensions(radiusX-backButton.radiusX*2-margin*2,
				(int)(radiusY*0.8f));
		bellButton.setDimensions((int)(statusBackground.radiusY*0.9f*0.9f),(int)(statusBackground.radiusY*0.9f));
		
		profilePic.setDimensions((int)(idBackground.radiusY*0.82f),(int)(idBackground.radiusY*0.82f));
		nameLabel.setMaxDimensions((int)(idBackground.radiusX-profilePic.radiusX),(int)(idBackground.radiusY*0.5f));
		nameLabel.setTextSizeToMax("Peniseater");
		amountLabel.setMaxDimensions((int)(idBackground.radiusX-profilePic.radiusX),(int)(idBackground.radiusY*0.35f));
		amountLabel.setTextSizeToMax("$100000000");
		
		statusMessage.setMaxDimensions((int)(statusBackground.radiusX*0.7f),(int)(statusBackground.radiusY*0.7f));
		statusMessage.setTextSizeToMax("SEARCHING FOR GAMES!!!");
		statusIcon.setDimensions(statusMessage.maxRadiusY,statusMessage.maxRadiusY);
		winLabel.setTextSize(statusMessage.getTextSize());
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		float yCenter = y-radiusY*0.1f;
		backButton.setPosition(x+radiusX-backButton.radiusX-margin,yCenter);
		idBackground.setPosition(x-radiusX+idBackground.radiusX+margin,yCenter);
		statusBackground.setPosition(x,yCenter);
		// left aligned
		profilePic.setPosition(idBackground.x-idBackground.radiusX+profilePic.radiusX+margin,yCenter);
		nameLabel.setPosition(profilePic.x+profilePic.radiusX+margin*2,yCenter+idBackground.radiusY*0.35f);
		amountLabel.setPosition(nameLabel.x,yCenter-idBackground.radiusY*0.35f);
		statusMessage.setPosition(statusBackground.x,statusBackground.y);
		statusIcon.setPosition(statusBackground.x-statusMessage.maxRadiusX-statusIcon.radiusX,statusBackground.y);
		bellButton.setPosition(statusBackground.x+statusBackground.radiusX-margin-bellButton.radiusX,yCenter);
		winLabelStart=new Vector2(idBackground.x,yCenter+radiusY);
		winLabelStop=new Vector2(idBackground.x,amountLabel.y+radiusY*20f);
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		statusMessage.animate(delta);
		winLabel.animate(delta);
		bellButton.animate(delta);
	}
	
	public void setPositions(float xOffscreen,float yOffscreen,float xOnscreen,float yOnscreen) {		
		posOffscreen=new Vector2(xOffscreen,yOffscreen);
		posOnscreen=new Vector2(xOnscreen,yOnscreen);
		
		setPosition(xOffscreen, yOffscreen);
	}
	
	public void setName(String playerName) {
		nameLabel.setText(playerName);
		nameLabel.loadTexture();
	}
	
	public void setProfilePicTexture(Texture tex) {
		if (profilePic.texture!=null) {
			profilePic.texture.dispose();
		}
		profilePic.texture=tex;
	}
	
	public void setAmount(int amount) {
		String amountStr = "$ "+Integer.toString(amount);
		amountLabel.setText(amountStr);
		amountLabel.loadTexture();
	}
	
	public void setStatusMessage(String message) {
		setStatusMessage(message,0,"");
	}
	
	public void setStatusMessage(String message,int argInt) {
		setStatusMessage(message, argInt, "");
	}
	
	public void setStatusMessage(String message,String argStr) {
		setStatusMessage(message, 0, argStr);
	}
	
	public void setStatusMessage(String message,int argInt, String argStr) {
		bellButton.fadeOut();
		bellButton.setTouchable(false);
		statusMessage.stopFlashing();
		if (message.equals("")) {
			statusMessage.fadeOut();
		} else {
			statusMessage.opacity=0;
		}
		if (message.equals(MESSAGE_SEARCHING)) {
			statusMessage.setText(message);
			statusMessage.loadTexture();
			statusIconTexture="searching.png";
			statusMessage.startFlashing();
		} else if (message.equals(MESSAGE_CONNECTED)) {
			statusMessage.setText(message+gameName);
			statusMessage.loadTexture();
			statusMessage.fadeIn();
			statusIconTexture="";
		} else if (message.equals(MESSAGE_ARRANGE)) {
			statusMessage.setText(message);
			statusMessage.loadTexture();
			statusIconTexture="arrange.png";
			statusMessage.fadeIn();
		} else if (message.equals(MESSAGE_SELECT_DEALER)) {
			statusMessage.setText(message);
			statusMessage.loadTexture();
			statusIconTexture="dealer_icon.png";
			statusMessage.fadeIn();
		} else if (message.equals(MESSAGE_BLINDS)) {
			int blinds=argInt;
			if (blinds==MovePrompt.BLINDS_SMALL) {
				statusMessage.setText("Small Blinds");
			} else if (blinds==MovePrompt.BLINDS_BIG) {
				statusMessage.setText("Big Blinds");
			}
			statusMessage.loadTexture();
			statusIconTexture="";
			statusMessage.fadeIn();
		} else if (message.equals(MESSAGE_BET_OR_CHECK)) {
			statusMessage.setText(message);
			statusMessage.loadTexture();
			statusIconTexture="";
			statusMessage.fadeIn();
		} else if (message.equals(MESSAGE_CALL)) {
			int amountToCall = argInt;
			statusMessage.setText("$ "+amountToCall+message);
			statusMessage.loadTexture();
			statusIconTexture="";
			statusMessage.fadeIn();
		} else if (message.equals(MESSAGE_WIN)) {
			statusMessage.setText(message);
			statusMessage.loadTexture();
			statusIconTexture="";
			statusMessage.fadeIn();
			int winAmount=argInt;
			doWinAnimation(winAmount);
		} else if (message.equals(MESSAGE_DEAL)) {
			int dealStage = argInt;
			statusMessage.setText(message+GameLogic.getDealStageString(dealStage));
			statusMessage.loadTexture();
			statusIconTexture="card_icon.png";
			statusMessage.startFlashing();
		} else if (message.equals(MESSAGE_DEAL_WAIT)) {
			String dealerName = argStr;
			int dealStage = argInt;
			statusMessage.setText(dealerName+message+GameLogic.getDealStageString(dealStage));
			statusMessage.loadTexture();
			statusIconTexture="";
			statusMessage.startFlashing();
			
		} else if (message.equals(MESSAGE_BET_WAIT)) {
			String betterName = argStr;
			statusMessage.setText(betterName+message);
			statusMessage.loadTexture();
			statusIconTexture="";
			statusMessage.fadeIn();
			bellButton.setTouchable(true);
			bellButton.fadeIn();
		} else if (message.equals(MESSAGE_SHOW_CARDS)) {
			statusMessage.setText(message);
			statusMessage.loadTexture();
			statusIconTexture="card_icon.png";
			statusMessage.fadeIn();
		} else if (message.equals(MESSAGE_WAIT_NEXT_HAND)) {
			statusMessage.setText(message);
			statusMessage.loadTexture();
			statusIconTexture="";
			statusMessage.startFlashing();
		} else if (message.equals(MESSAGE_CONNECTION_LOST)) {
			statusMessage.setText(message);
			statusMessage.loadTexture();
			statusIconTexture="";
			statusMessage.startFlashing();
		} else if (message.equals(MESSAGE_NO_WIFI)) {
			statusMessage.setText(message);
			statusMessage.loadTexture();
			statusIconTexture="searching.png";
			statusMessage.startFlashing();
		}
	}
	
	public void setGameName(String gameName) {
		this.gameName = gameName;
		// TODO change back to game button with name on top
	}
	
	public void doWinAnimation(int winAmount) {
		winLabel.setText("+ $ "+Integer.toString(winAmount));
		winLabel.loadTexture();
		winLabel.setPosition(winLabelStart);
		winLabel.opacity=1;
		winLabel.setMoveLinear(new Vector2(0,radiusY*0.8f));
		winLabel.setDest(winLabelStop);
		winLabel.setFadeOutSpeed(0.4f);
		winLabel.fadeOut();
	}
	
	public void show() {
		setPosition(posOffscreen);
		this.setDest(posOnscreen);
		backButton.setTouchable(true);
	}
	
	public void hide() {
		this.setDest(posOffscreen);
		statusMessage.fadeOut();
		backButton.setTouchable(false);
	}
	
	public void dispose() {
		if (profilePic.texture!=null) {
			profilePic.texture.dispose();
		}
	}
}
