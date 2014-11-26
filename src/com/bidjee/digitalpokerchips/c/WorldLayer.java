package com.bidjee.digitalpokerchips.c;

import android.net.MailTo;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.bidjee.digitalpokerchips.i.IHostNetwork;
import com.bidjee.digitalpokerchips.i.IPlayerNetwork;
import com.bidjee.digitalpokerchips.i.ITableStore;
import com.bidjee.digitalpokerchips.m.CameraPosition;
import com.bidjee.digitalpokerchips.m.Chip;
import com.bidjee.digitalpokerchips.m.ChipCase;
import com.bidjee.digitalpokerchips.m.DPCSprite;
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
	public CameraPosition camPosNone=new CameraPosition("None");
	public CameraPosition camPosHome=new CameraPosition("Home");
	public CameraPosition camPosPlayer=new CameraPosition("Player");
	public CameraPosition camPosTable=new CameraPosition("Table");
	
	//////////////////// Controllers ////////////////////
	public WorldInput input;
	GestureDetector gestureInput;
	public ThisPlayer thisPlayer;
	public Table table;
	SoundFX soundFX;
	
	//////////////////// Models ////////////////////
	public ChipCase chipCase;
	public DPCSprite backgroundSprite=new DPCSprite();
	public HomeDeviceAnimation homeDeviceAnimation;
	
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
		cameraDestination=camPosNone;
		thisPlayer=new ThisPlayer(this,playerNetworkInterface);
		table=new Table(this,hostNetworkInterface,tableStore);
		int[] values_={25,100,200};
		chipCase=new ChipCase(values_);
		homeDeviceAnimation=new HomeDeviceAnimation();
	}
	
	public void setDimensions(float worldWidth,float worldHeight) {
		int backgroundRadiusX=(int)(Math.max(worldWidth,worldHeight*0.89f)+1);
		backgroundRadiusX=(int) (worldWidth*0.5f);
		backgroundSprite.setDimensions(backgroundRadiusX,(int)(worldHeight*0.5f)+1);
		homeDeviceAnimation.setDimensions(worldWidth, worldHeight);
		Chip.radiusY=(int) (homeDeviceAnimation.p1Sprite.radiusY*0.23f);
		Chip.radiusX=(int) (Chip.radiusY*1.02f);
		limChipFlingVel=(int) (worldHeight*0.15f);
		
		table.setDimensions(worldWidth,worldHeight);
		
		thisPlayer.setDimensions(homeDeviceAnimation.p1Sprite.radiusX,homeDeviceAnimation.p1Sprite.radiusY);
	}
	
	public void setPositions(int worldWidth,int worldHeight) {
		backgroundSprite.setPosition(worldWidth*0.5f,worldHeight*0.5f);
		camPosHome.set(worldWidth*0.5f,worldHeight*0.5f,0.25f);
		camPosPlayer.set(worldWidth*0.5f,worldHeight*0.42f,2.7f);
		camPosTable.set(worldWidth*0.5f,worldHeight*0.62f,1.4f);
		homeDeviceAnimation.setPositions(worldWidth, worldHeight,camPosTable.getY(),camPosPlayer.getY());
		thisPlayer.setPositions(homeDeviceAnimation.posP1Stop.x,homeDeviceAnimation.posP1Stop.y);
		table.setPositions(worldWidth,worldHeight);
		
	}
	
	public void scalePositions(float scaleX,float scaleY) {
		thisPlayer.scalePositions(scaleX,scaleY);
		table.scalePositions(scaleX,scaleY);
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
		homeDeviceAnimation.begin(1000);
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
		homeDeviceAnimation.animate(delta);
		thisPlayer.animate(delta);
		table.animate(delta);
	}
	
	public void collisionDetector() {
		thisPlayer.collisionDetector();
		table.collisionDetector();
	}
	
	private void controlLogic() {
		if (cameraDestination==camPosHome) {
			if (homeDeviceAnimation.loopedOnce&&!game.mFL.homeUIAnimation.running ) {
				game.mFL.startHomeUI();
				homeDeviceAnimation.pause();
			}
			if (game.mFL.homeUIAnimation.done&&homeDeviceAnimation.paused) {
				homeDeviceAnimation.resume();
				homeDeviceAnimation.setDevicesTouchable(true);
			}
			if (!game.mFL.homeForegroundAnimation.running&&
					worldRenderer.camera.testZoomLessThan(0.2f*(camPosPlayer.getZoomFactor()+camPosHome.getZoomFactor()))) {
				game.mFL.homeForegroundAnimation.begin(50);
			}
		}
		thisPlayer.controlLogic();
		table.controlLogic();
	}
	
	
	//////////////////// Navigation Control ////////////////////	
	public void sendCameraTo(CameraPosition camPos) {
		cameraLeftPosition(cameraDestination);
		cameraDestination=camPos;
		worldRenderer.camera.sendTo(camPos);
		if (cameraDestination==camPosHome) {
			homeDeviceAnimation.fadeInShine();
		} else if (cameraDestination==camPosPlayer) {
			;
		} else if (cameraDestination==camPosTable) {
			;
		} else {
			//
		}
		Logger.log(LOG_TAG,"sendCameraTo("+camPos.toString()+")");
	}
	
	private void cameraLeftPosition(CameraPosition camPos) {
		if (camPos==camPosHome) {
			game.mFL.stopHomeUI();
			game.mFL.stopHomeForeground();
			homeDeviceAnimation.pause();
			homeDeviceAnimation.resetChips();
			homeDeviceAnimation.setDevicesTouchable(false);
			homeDeviceAnimation.fadeOutShine();
		} else if (camPos==camPosPlayer) {
			thisPlayer.notifyLeftPlayerPosition();
		} else if (camPos==camPosTable) {
			table.notifyLeftTablePosition();
		}
		if (camPos!=null) {
			Logger.log(LOG_TAG,"sendCameraTo("+camPos.toString()+")");
		}
		
	}
	
	public void cameraAtDestination() {
		if (cameraDestination==camPosHome) {
			
		} else if (cameraDestination==camPosPlayer) {
			thisPlayer.notifyAtPlayerPosition();
		} else if (cameraDestination==camPosTable) {
			table.notifyAtTablePosition();
		} else {
			//
		}
		Logger.log(LOG_TAG,"sendCameraTo("+cameraDestination.toString()+")");
	}
	
	public void navigateBack() {
		if (cameraDestination==camPosHome) {
			game.exitApp();
		} else if (cameraDestination==camPosPlayer) {
			if (!thisPlayer.backPressed()) {
				sendCameraTo(camPosHome);
			}
		} else if (cameraDestination==camPosTable) {
			if (!table.backPressed()) {
				sendCameraTo(camPosHome);
			}
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
	
	//// Messages from Input ////
	public void hostSelected() {
		sendCameraTo(camPosTable);
	}
	
	public void joinSelected() {
		sendCameraTo(camPosPlayer);
	}
	
	public void helpSelected() {
		game.mFL.startHelpDialog();
	}
	
	public void helpDone() {
		game.mFL.stopHelpDialog();
	}

}
