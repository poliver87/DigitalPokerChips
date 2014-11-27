package com.bidjee.digitalpokerchips.c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.m.GameLogic;
import com.bidjee.digitalpokerchips.m.GameMenuData;
import com.bidjee.digitalpokerchips.m.PlayerMenuItem;

public class GameMenu {
	
	private GameMenuData gameMenuData;
	
	public GameMenuPanel gamePanel;
	public ArrayList<PlayerMenuPanel> playerPanels;
	
	int margin;
	
	Vector2 gamePanelOffscreen=new Vector2();
	Vector2 gamePanelOnscreen=new Vector2();

	float xPlayer;
	float yPlayerOffscreen;
	float yPlayerTop;
	float yPlayerPitch;
	
	boolean show=false;
	
	public GameMenu() {
		gamePanel = new GameMenuPanel();
		playerPanels = new ArrayList<PlayerMenuPanel>();
	}
	
	public void setDimensions(int radiusX,int radiusY) {
		margin=(int) Math.max(radiusY*0.005f, 1);
		int unitRadiusY = (int) (0.5f*(radiusY*2f - margin*10f)/10f);
		gamePanel.setDimensions(radiusX, unitRadiusY*2);
		PlayerMenuPanel.setStaticDimensions((int)(radiusX*0.66f),unitRadiusY);
	}
	
	public void setPositions(float xRight,float yTop) {
		gamePanelOffscreen.set(xRight-margin-gamePanel.radiusX,yTop + gamePanel.radiusY*3f);
		gamePanelOnscreen.set(gamePanelOffscreen.x,yTop - margin - gamePanel.radiusY);
		gamePanel.setPosition(gamePanelOffscreen);
		xPlayer=xRight-margin-PlayerMenuPanel.panelRadiusX;
		yPlayerOffscreen=yTop + PlayerMenuPanel.panelRadiusY*3f;
		yPlayerTop=gamePanelOnscreen.y-gamePanel.radiusY-margin-PlayerMenuPanel.panelRadiusY;
		yPlayerPitch=PlayerMenuPanel.panelRadiusY*2+margin;
	}
	
	public void animate(float delta) {
		gamePanel.animate(delta);
		for (int i=0;i<playerPanels.size();i++) {
			playerPanels.get(i).animate(delta);
		}
	}
	
	public void setData(GameMenuData gameMenuData) {
		this.gameMenuData=gameMenuData;
		if (!show) {
			show=true;
			gamePanel.setDest(gamePanelOnscreen);
		}
		updateView();
	}
	
	public void updateView() {
		// show game panel if hidden
		gamePanel.set(GameLogic.getDealStageString(gameMenuData.dealStage),
						gameMenuData.potTotal);		

		// mark all panels for deletion, unmark when found
		for (int i=0;i<playerPanels.size();i++) {
			playerPanels.get(i).markedForDeletion=true;
		}
		
		// update existing players and create new players
		for (int i=0;i<gameMenuData.players.size();i++) {
			PlayerMenuItem thisPlayer = gameMenuData.players.get(i);
			boolean newPlayer=true;
			for (int j=0;j<playerPanels.size();j++) {
				if (playerPanels.get(j).nameLabel.getText().equals(thisPlayer.name)) {
					newPlayer=false;
					playerPanels.get(j).setAmounts(thisPlayer.betTotal, false, thisPlayer.chipTotal);
					playerPanels.get(j).markedForDeletion=false;
					break;
				}
			}
			if (newPlayer) {
				PlayerMenuPanel newPanel = new PlayerMenuPanel(thisPlayer.name);
				newPanel.setDimensionsStatic();
				newPanel.setAmounts(thisPlayer.betTotal, false, thisPlayer.chipTotal);
				newPanel.markedForDeletion=false;
				newPanel.setPosition(xPlayer,yPlayerOffscreen);
				playerPanels.add(newPanel);
			}
		}
		// delete panels marked for deletion
		for (Iterator<PlayerMenuPanel> iterator = playerPanels.iterator(); iterator.hasNext();) {
		    PlayerMenuPanel thisPanel = iterator.next();
		    if (thisPanel.markedForDeletion) {
		        iterator.remove();
		    }
		}
		// sort player panels
		Collections.sort(playerPanels);
		// update y positions
		for (int i=0;i<playerPanels.size();i++) {
			playerPanels.get(i).setDest(xPlayer,yPlayerTop-i*yPlayerPitch);
		}
		
	}
	
	public void hide() {
		show=false;
		gamePanel.setDest(gamePanelOffscreen);
	}
	
	
	

}
