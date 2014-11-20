package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlayerDashboard extends DPCSprite {
	
	public static final String MESSAGE_SEARCHING = "SEARCHING FOR GAMES";
	public static final String MESSAGE_CONNECTED = "Connected to ";
	public static final String MESSAGE_ARRANGE = "ARRANGE POSITIONS";
	public static final String MESSAGE_SELECT_DEALER = "SELECT DEALER";
	
	public DPCSprite profilePic = new DPCSprite();
	public TextLabel nameLabel;
	public TextLabel amountLabel;
	public String gameName;
	
	public TextLabel statusMessage;
	public DPCSprite statusIcon=new DPCSprite();
	public String statusIconTexture;
	
	public Button backButton;
	
	Vector2 posOffscreen;
	public Vector2 posOnscreen;
	
	public DPCSprite idBackground=new DPCSprite();
	public DPCSprite statusBackground=new DPCSprite();
	
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
		gameName="";
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX, radiusY);
		margin = (int) (radiusY*0.1f);
		
		backButton.setDimensions((int)(radiusY*0.8f*2.5f),(int)(radiusY*0.8f));
		idBackground.setDimensions(backButton.radiusX,backButton.radiusY);
		statusBackground.setDimensions(radiusX-backButton.radiusX*2-margin*2,
				(int)(radiusY*0.8f));
		
		profilePic.setDimensions((int)(idBackground.radiusY*0.82f),(int)(idBackground.radiusY*0.82f));
		nameLabel.setMaxDimensions((int)(idBackground.radiusX-profilePic.radiusX),(int)(idBackground.radiusY*0.5f));
		nameLabel.setTextSizeToMax("Peniseater");
		amountLabel.setMaxDimensions((int)(idBackground.radiusX-profilePic.radiusX),(int)(idBackground.radiusY*0.35f));
		amountLabel.setTextSizeToMax("$100000000");
		
		statusMessage.setMaxDimensions((int)(statusBackground.radiusX*0.7f),(int)(statusBackground.radiusY*0.7f));
		statusMessage.setTextSizeToMax("SEARCHING FOR GAMES!!!");
		statusIcon.setDimensions(statusMessage.maxRadiusY,statusMessage.maxRadiusY);
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		backButton.setPosition(x+radiusX-backButton.radiusX-margin,y);
		idBackground.setPosition(x-radiusX+idBackground.radiusX+margin,y);
		statusBackground.setPosition(x,y);
		// left aligned
		profilePic.setPosition(idBackground.x-idBackground.radiusX+profilePic.radiusX+margin,y);
		nameLabel.setPosition(profilePic.x+profilePic.radiusX+margin*2,y+idBackground.radiusY*0.35f);
		amountLabel.setPosition(nameLabel.x,y-idBackground.radiusY*0.35f);
		statusMessage.setPosition(statusBackground.x,statusBackground.y);
		statusIcon.setPosition(statusBackground.x-statusMessage.maxRadiusX-statusIcon.radiusX,statusBackground.y);
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		statusMessage.animate(delta);
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
		
		statusMessage.stopFlashing();
		statusMessage.opacity=0;
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
		}
	}
	
	public void setGameName(String gameName) {
		this.gameName = gameName;
		// TODO change back to game button with name on top
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
