package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.bidjee.digitalpokerchips.i.IHostNetwork;
import com.bidjee.digitalpokerchips.i.IPlayerNetwork;
import com.bidjee.digitalpokerchips.i.ITableStore;
import com.bidjee.digitalpokerchips.m.CameraPosition;
import com.bidjee.digitalpokerchips.m.Chip;
import com.bidjee.digitalpokerchips.m.ChipCase;
import com.bidjee.digitalpokerchips.s.SoundFX;
import com.bidjee.digitalpokerchips.v.WorldRenderer;
import com.bidjee.util.Logger;

public class WorldLayer implements Screen {
	
	public static final String LOG_TAG = "DPCWorldLayer";
	
	//////////////////// Constants ////////////////////
	public static final String NAME_MEASURE = "AAbJakyQl";
	static final int WORLD_TO_SCREEN_RATIO = 4;
	
	//////////////////// State Variables ////////////////////
	boolean screenLaidOut;
	public CameraPosition cameraDestination;
	
	//////////////////// World Scale & Layout ////////////////////
	public float worldWidth;
	public float worldHeight;
	float oldWorldWidth;
	float oldWorldHeight;
	int limChipFlingVel;
	public CameraPosition camPosHome=new CameraPosition("Home");
	public CameraPosition camPosPlayer=new CameraPosition("Player");
	public CameraPosition camPosTable=new CameraPosition("Table");
	public CameraPosition camPosPlayersName=new CameraPosition("Player's Name");
	public CameraPosition camPosTableName=new CameraPosition("Table's Name");
	public CameraPosition camPosChipCase=new CameraPosition("Chip Case");
	
	//////////////////// Controllers ////////////////////
	public WorldInput input;
	GestureDetector gestureInput;
	public ThisPlayer thisPlayer;
	public Table table;
	SoundFX soundFX;
	
	//////////////////// Models ////////////////////
	public ChipCase chipCase;
	
	//////////////////// Renderers ////////////////////
	WorldRenderer worldRenderer;
	
	//////////////////// References ////////////////////
	public DPCGame game;
	
	public WorldLayer(DPCGame game,IPlayerNetwork playerNetworkInterface,IHostNetwork hostNetworkInterface,ITableStore tableStore) {
		screenLaidOut=false;
		this.game=game;
		if (DPCGame.runAllTutorials) {
			;
		} else {
			;
		}
		worldRenderer=new WorldRenderer(this);
		input=new WorldInput();
		input.setScreen(this);
		WorldGestureInput mWGI=new WorldGestureInput();
		gestureInput=new GestureDetector(mWGI);
		mWGI.setScreen(this);
		soundFX=new SoundFX();
		thisPlayer=new ThisPlayer(this,playerNetworkInterface);
		table=new Table(this,hostNetworkInterface,tableStore);
		int[] values_={25,100,200};
		chipCase=new ChipCase(values_);
	}
	
	public void setDimensions(float worldWidth,float worldHeight) {
		Chip.radiusX=(int) (worldWidth*0.02f);
		Chip.radiusY=(int) (Chip.radiusX*0.98f);
		limChipFlingVel=(int) (worldHeight*0.15f);
		thisPlayer.setDimensions(worldWidth,worldHeight);
		table.setDimensions(worldWidth,worldHeight);
		chipCase.setDimensions((int)(worldHeight*0.12f),(int)(worldHeight*0.12f));
	}
	
	public void setPositions(int worldWidth,int worldHeight) {
		camPosHome.set(worldWidth*0.5f,worldHeight*0.5f,0.25f);
		camPosPlayer.set(worldWidth*0.5f,worldHeight*0.3f,1f);
		camPosTable.set(worldWidth*0.5f,worldHeight*0.5f,1f);
		camPosPlayersName.set(worldWidth*0.5f,worldHeight*0.12f,1f);
		camPosTableName.set(worldWidth*0.5f,worldHeight*0.44f,1f);
		camPosChipCase.set(worldWidth*0.5f,worldHeight*0.75f,1f);
		thisPlayer.setPositions(worldWidth,worldHeight);
		table.setPositions(worldWidth,worldHeight);
		chipCase.setPosition(worldWidth*0.5f,worldHeight*0.8f);
		
	}
	
	public void scalePositions(float scaleX,float scaleY) {
		thisPlayer.scalePositions(scaleX,scaleY);
		table.scalePositions(scaleX,scaleY);
		chipCase.scalePosition(scaleX,scaleY);
	}
	
	@Override
	public void resize(int width,int height) {
		int worldWidth=WORLD_TO_SCREEN_RATIO*width;
		int worldHeight=WORLD_TO_SCREEN_RATIO*height;
		worldRenderer.resize(width,height);
		if ((worldWidth!=this.worldWidth||worldHeight!=this.worldHeight)&&(width>height)) {
			setDimensions(worldWidth,worldHeight);
			if (!screenLaidOut) {
				setPositions(worldWidth,worldHeight);
				screenLaidOut=true;
			} else {
				float scaleX=worldWidth/this.worldWidth;
				float scaleY=worldHeight/this.worldHeight;
				scalePositions(scaleX,scaleY);
			}
			this.worldWidth=worldWidth;
			this.worldHeight=worldHeight;
		}
	}
	
	@Override
	public void resume() {
		worldRenderer.loadTextures(game.manager);
		soundFX.loadSounds(game.manager);
		game.unfreezeAnimation();
		
	}
	
	public void start() {
		sendCameraTo(camPosHome);
		worldRenderer.camera.setTo(cameraDestination);
	}
	
	@Override
	public void pause () {}
	@Override
	public void show() {}
	@Override
	public void hide() {}
	
	@Override
	public void dispose () {
		if (soundFX!=null) {
			soundFX.dispose();
		}
		worldRenderer.dispose();
		game=null;
	}
	
	Object renderLock=new Object();
	@Override
	public void render(float delta) {
		synchronized (renderLock) {
			if (!game.freezeAnimation) {
				animate(delta);
				collisionDetector();
				controlLogic();
			}
			worldRenderer.render();
		}
	}

	public void animate(float delta) {
		worldRenderer.camera.animate(delta);
		thisPlayer.animate(delta);
		table.animate(delta);
	}
	
	public void collisionDetector() {
		thisPlayer.collisionDetector();
		table.collisionDetector();
	}
	
	private void controlLogic() {
		thisPlayer.controlLogic();
		table.controlLogic();
	}
	
	//////////////////// Navigation Control ////////////////////	
	public void sendCameraTo(CameraPosition camPos) {
		cameraLeftPosition(cameraDestination);
		cameraDestination=camPos;
		worldRenderer.camera.sendTo(camPos);
		if (cameraDestination==camPosHome) {
			game.mFL.startHome();
		} else if (cameraDestination==camPosPlayer) {
			;
		} else if (cameraDestination==camPosPlayersName) {
			;
		} else if (cameraDestination==camPosTable) {
			;
		} else if (cameraDestination==camPosTableName) {
			;
		} else if (cameraDestination==camPosChipCase) {
			;
		} else {
			//
		}
		Logger.log(LOG_TAG,"sendCameraTo("+camPos.toString()+")");
	}
	
	private void cameraLeftPosition(CameraPosition camPos) {
		boolean validPosition=false;
		if (camPos==camPosHome) {
			game.mFL.stopHome();
			validPosition=true;
		} else if (camPos==camPosPlayersName) {
			thisPlayer.notifyLeftNamePosition();
			validPosition=true;
		} else if (camPos==camPosPlayer) {
			thisPlayer.notifyLeftPlayerPosition();
			validPosition=true;
		} else if (camPos==camPosTableName) {
			table.notifyLeftTableNamePosition();
			validPosition=true;
		} else if (camPos==camPosChipCase) {
			table.notifyLeftChipCasePosition();
			validPosition=true;
		} else if (camPos==camPosTable) {
			table.notifyLeftTablePosition();
			validPosition=true;
		}
		if (camPos!=null) {
			Logger.log(LOG_TAG,"sendCameraTo("+camPos.toString()+")");
		}
		
	}
	
	public void cameraAtDestination() {
		if (cameraDestination==camPosHome) {
			;
		} else if (cameraDestination==camPosPlayer) {
			thisPlayer.notifyAtPlayerPosition();
		} else if (cameraDestination==camPosPlayersName) {
			thisPlayer.notifyAtNamePosition();
		} else if (cameraDestination==camPosTable) {
			table.notifyAtTablePosition();
		} else if (cameraDestination==camPosTableName) {
			table.notifyAtTableNamePosition();
		} else if (cameraDestination==camPosChipCase) {
			table.notifyAtChipCasePosition();
		} else {
			//
		}
		Logger.log(LOG_TAG,"sendCameraTo("+cameraDestination.toString()+")");
	}
	
	public void navigateBack() {
		if (cameraDestination==camPosHome) {
			game.exitApp();
		} else if (cameraDestination==camPosPlayersName) {
			;
		} else if (cameraDestination==camPosPlayer) {
			if (thisPlayer.backPressed()) {
				sendCameraTo(camPosHome);
			}
		} else if (cameraDestination==camPosTableName) {
			sendCameraTo(camPosHome);
		} else if (cameraDestination==camPosChipCase) {
			sendCameraTo(camPosTableName);
		} else if (cameraDestination==camPosTable) {
			table.backPressed();
		} else {
			//
		}
		Logger.log(LOG_TAG,"navigateBack() cameraDestination: "+cameraDestination.toString());
	}

	public void wifiOn(String ipAddressStr) {
		thisPlayer.wifiOn();
		table.wifiOn(ipAddressStr);
	}
	
	public void wifiOff() {
		thisPlayer.wifiOff();
		table.wifiOff();
	}

}
