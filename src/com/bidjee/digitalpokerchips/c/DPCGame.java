package com.bidjee.digitalpokerchips.c;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.bidjee.digitalpokerchips.i.IActivity;
import com.bidjee.digitalpokerchips.i.ITextFactory;
import com.bidjee.digitalpokerchips.m.ScreenState;
import com.bidjee.util.Logger;

public class DPCGame extends Game {
	
	// MASTER
	
	public static final int RESOLUTION_LOW = 0;
	public static final int RESOLUTION_MEDIUM = 1;
	public static final int RESOLUTION_HIGH = 2;
	
	public static final String LOG_TAG = "DPCGame";
	
	/********** State Variables **********
	 * renderNumber: Allows two frames to be drawn before anything is done to
	 * 				avoid issue where app opening animation doesn't transition
	 *				smoothly to my rendering
	 * screenState: Manages whether screen is loading or in play
	 * landscape: Only do anything when we are in landscape
	 * eglContext: To say whether we need to throw up the splash screen
	 * settingsC,helpC,exitC: When transitioning, freezes animation for a few frames
	 * 						to ensure a smooth transition
	 * freezeAnimation: used to freeze animation for a couple of frames when
	 * 					a smooth transition is needed
	 * exitPending: used to say the app should exit after some operation
	 * 
	 */
	int renderNumber;
	int screenState;
	boolean worldInitialised;
	boolean landscape;
	boolean eglContextLost;
	private int settingsC;
	private int exitC;
	boolean freezeAnimation;
	boolean exitPending;
	public boolean wifiEnabled;
	String ipAddress;
	public int resolutionSetting;
	public boolean runTutorialArrangement=true;
	// Objects Contained //
	public WorldLayer mWL;
	public ForegroundLayer mFL;
	TitleScreen mTS;
	InputMultiplexer im;
	AssetManager manager;
	// References //
	public IActivity activity;
	public static ITextFactory textFactory;
	// Debugging //
	private FPSLogger fps;
	public static boolean debugMode=false;
	public static boolean runAllTutorials=true;
	
	public DPCGame(IActivity activity_) {
		renderNumber=0;
		screenState=ScreenState.NONE;
		eglContextLost=true;
		settingsC=-1;
		exitC=-1;
		freezeAnimation=true;
		exitPending=false;
		activity=activity_;
		
		Logger.loggingMasterOn=true;
		Logger.loggingOn=true;
	}
	
	////////////////////Life-cycle Events ////////////////////
	@Override
	public void create() {
		Texture.setEnforcePotImages(false);
	}
	
	public void initGame() {
		Logger.log(LOG_TAG,"** initGame() **");
		mTS=new TitleScreen(this);
		textFactory=activity.getITextFactory();
		im=new InputMultiplexer();
		manager=new AssetManager();
		Texture.setAssetManager(manager);
		loadAssets();
		resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		screenState=ScreenState.LOADING;
		resume();
		fps=new FPSLogger();
	}
	
	@Override
	public void resize(int width,int height) {
		Logger.log(LOG_TAG, "resize("+width+","+height+")");
		if (width < 500) {
			resolutionSetting = RESOLUTION_LOW;
		} else if (width < 1000) {
			resolutionSetting = RESOLUTION_MEDIUM;
		} else {
			resolutionSetting = RESOLUTION_HIGH;
		}
		landscape=width>height;
		if (landscape) {
			if (mTS!=null) {
				//mTS.resize(width,height);
			}
			if (mWL!=null) {
				mWL.resize(width,height);
			}
			if (mFL!=null) {
				mFL.resize(width,height);
			}
		}
	}
	
	@Override
	public void pause() {
		Logger.log(LOG_TAG, "pause()");
		if (im!=null) {
			im.touchUp(0,0,0,0);
		}
		Gdx.input.setCatchBackKey(false);
		Gdx.input.setOnscreenKeyboardVisible(false);
		Gdx.input.setInputProcessor(null);
		super.pause();
	}
	
	@Override
	public void resume() {
		Logger.log(LOG_TAG, "resume()");
		if (screenState!=ScreenState.NONE) {
			Gdx.input.setCatchBackKey(true);
			Gdx.input.setInputProcessor(im);
			screenState=ScreenState.LOADING;
			eglContextLost=false;
		}
	}
	
	public void onSaveInstanceState() {}
	
	public void onRestoreInstanceState() {}
	
	@Override
	public void dispose() {
		Logger.log(LOG_TAG, "dispose()");
		mTS.dispose();
		mWL.dispose();
		mFL.dispose();
		DPCGame.textFactory.dispose();
		manager.dispose();
	}
	
	@Override
	public void render () {
		float delta=Gdx.graphics.getDeltaTime();
		delta=Math.min(delta,0.05f);
		if (renderNumber<2) {
			Logger.log(LOG_TAG, "render() frame: "+renderNumber);
			renderNumber++;
			Gdx.gl.glClearColor(0,0,0,1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		} else if (renderNumber==2) {
			renderNumber++;
			initGame();
		}
		// TODO manage the separate cases of eglContext lost or not
		if (screenState==ScreenState.LOADING) {
			if (manager.update()) {
				if (mTS.checkCompletion()&&landscape) {
					if (!worldInitialised) {
						worldInitialised=true;
						mWL.start();
						mFL.start();
					}
					mWL.resume();
					mFL.resume();
					screenState=ScreenState.GAMEPLAY;
					mTS.fadeOut();
				} else {
					mTS.render(delta);
				}
			} else {
				if (!eglContextLost) {
					eglContextLost=true;
					mTS.resume();
				}
				if (landscape) {
					mTS.render(delta);
				}
			}
		}
		if (screenState==ScreenState.GAMEPLAY) {
			mWL.render(delta);
			mFL.render(delta);
			if (mTS.screenOpacity>0&&eglContextLost) {
				mTS.render(delta);
			}
		}
		
		if (renderNumber>2) {
			checkTransitionQueue();
		}
		if (fps!=null) {
			//fps.log();
		}
	}

	public float getLoadPercent() {
		return manager.getProgress();
	}
	
	public void loadSynchronous() {
		Logger.log(LOG_TAG, "loadSynchronous()");
		if (mWL==null&&mFL==null) {
			mWL=new WorldLayer(this,activity.getIPlayerNetwork(),activity.getIHostNetwork(),activity.getITableStore());
			mFL=new ForegroundLayer(this,activity.getHelpWebView());
			im.addProcessor(mFL.gestureInput);
			im.addProcessor(mFL.input);
			im.addProcessor(mWL.gestureInput);
			im.addProcessor(mWL.input);
			if (wifiEnabled) {
				mWL.table.wifiOn(ipAddress);
				mWL.thisPlayer.wifiOn();
			} else {
				mWL.table.wifiOff();
				mWL.thisPlayer.wifiOff();
			}
		}
		resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		//textFactory.dispose();
		//mFL.foregroundRenderer.loadLabels();
		//mWL.worldRenderer.loadLabels();
	}
	
	private void checkTransitionQueue() {
		// seems libgdx takes a snap shot so allow 2 frames to ensure the correct state
		if (settingsC>=0) {
			if (settingsC==2) {
				settingsC=-1;
				activity.launchSettings();
			} else {
				settingsC++;
			}
		}
		if (exitC>=0) {
			if (exitC==2) {
				Gdx.app.exit();
			} else {
				exitC++;
			}
		}
	}
	
	public void loadAssets() {
		Logger.log(LOG_TAG, "loadAssets()");
		TextureParameter tParam=new TextureParameter();
		tParam.minFilter=TextureFilter.Linear;
		tParam.magFilter=TextureFilter.Linear;
		tParam.format=Format.RGBA8888;
		
		if (resolutionSetting==RESOLUTION_LOW) {
			manager.load("background.png",Texture.class,tParam);
		} else if (resolutionSetting==RESOLUTION_MEDIUM||
				resolutionSetting==RESOLUTION_HIGH) {
			manager.load("background.png",Texture.class,tParam);
		}
		
		
		manager.load("black_hole.png",Texture.class,tParam);
		manager.load("rectangle_rounded.png",Texture.class,tParam);
		manager.load("black_circle.png",Texture.class,tParam);
		manager.load("hand.png",Texture.class,tParam);
		manager.load("table_button.png",Texture.class,tParam);
		manager.load("base_login_popup.png",Texture.class,tParam);
		manager.load("btn_close.png",Texture.class,tParam);
		manager.load("btn_okay.png",Texture.class,tParam);
		manager.load("btn_login_facebook.png",Texture.class,tParam);
		
		manager.load("envelope.png",Texture.class,tParam);
		manager.load("buyin_box.png",Texture.class,tParam);
		manager.load("btn_plus.png",Texture.class,tParam);
		manager.load("btn_minus.png",Texture.class,tParam);
		manager.load("btn_okay_buyin.png",Texture.class,tParam);
		manager.load("btn_cancel.png",Texture.class,tParam);
		
		manager.load("plant.png",Texture.class,tParam);
		manager.load("lamp.png",Texture.class,tParam);
		
		manager.load("dev_host.png",Texture.class,tParam);
		manager.load("dev_host_shine.png",Texture.class,tParam);
		manager.load("dev_player1.png",Texture.class,tParam);
		manager.load("dev_player1_shine.png",Texture.class,tParam);
		manager.load("dev_player2.png",Texture.class,tParam);
		manager.load("dev_player3.png",Texture.class,tParam);
		manager.load("home_settings.png",Texture.class,tParam);
		manager.load("home_help.png",Texture.class,tParam);
		manager.load("home_shop.png",Texture.class,tParam);
		manager.load("selection_orange.png",Texture.class,tParam);
		manager.load("host_button.png",Texture.class,tParam);
		manager.load("join_button.png",Texture.class,tParam);
		
		manager.load("ok_button.png",Texture.class,tParam);
		manager.load("anon.jpeg",Texture.class,tParam);
		
		manager.load("dashboard.png",Texture.class,tParam);
		manager.load("dashboard_id.png",Texture.class,tParam);
		manager.load("dashboard_status.png",Texture.class,tParam);
		manager.load("btn_back.png",Texture.class,tParam);
		manager.load("searching.png",Texture.class,tParam);
		manager.load("arrange.png",Texture.class,tParam);
		manager.load("dealer_icon.png",Texture.class,tParam);
		manager.load("btn_bell.png",Texture.class,tParam);
		manager.load("game_panel.png",Texture.class,tParam);
		
		manager.load("cancel_button.png",Texture.class,tParam);
		manager.load("table_highlight.png",Texture.class,tParam);
		manager.load("fold_button.png",Texture.class,tParam);
		manager.load("dealer_chip.png",Texture.class,tParam);
		manager.load("shadow.png",Texture.class,tParam);
		manager.load("chip_0_0.png",Texture.class,tParam);
		manager.load("chip_0_1.png",Texture.class,tParam);
		manager.load("chip_0_2.png",Texture.class,tParam);
		manager.load("chip_1_0.png",Texture.class,tParam);
		manager.load("chip_1_1.png",Texture.class,tParam);
		manager.load("chip_1_2.png",Texture.class,tParam);
		manager.load("chip_2_0.png",Texture.class,tParam);
		manager.load("chip_2_1.png",Texture.class,tParam);
		manager.load("chip_2_2.png",Texture.class,tParam);
		manager.load("cursor.png",Texture.class,tParam);
		manager.load("arrow.png",Texture.class,tParam);
		manager.load("arrow_pot.png",Texture.class,tParam);
		manager.load("wifi_red.png",Texture.class,tParam);
		manager.load("text_field.png",Texture.class,tParam);
		manager.load("split_button.png",Texture.class,tParam);
		manager.load("speech_bubble_gold.png",Texture.class,tParam);
		manager.load("join_coin.png",Texture.class,tParam);
		manager.load("arrow_white.png",Texture.class,tParam);
		manager.load("button_red.png",Texture.class,tParam);
		manager.load("button_green.png",Texture.class,tParam);
		manager.load("close_button.png",Texture.class,tParam);
		manager.load("galaxy_frame.png",Texture.class,tParam);
		manager.load("next_button.png",Texture.class,tParam);
		manager.load("dialog_black_fill.png",Texture.class,tParam);
		manager.load("dialog_black_corner.png",Texture.class,tParam);
		manager.load("cursor.png",Texture.class,tParam);
		manager.load("ping.png",Texture.class,tParam);
		manager.load("ping_hub.png",Texture.class,tParam);
		manager.load("dpc_logo.png",Texture.class,tParam);
		manager.load("dpc_logo_01.png",Texture.class,tParam);
		manager.load("dpc_logo_02.png",Texture.class,tParam);
		manager.load("dpc_logo_03.png",Texture.class,tParam);
		manager.load("dpc_logo_04.png",Texture.class,tParam);
		manager.load("dpc_logo_05.png",Texture.class,tParam);
		manager.load("dpc_logo_06.png",Texture.class,tParam);
		manager.load("dpc_logo_07.png",Texture.class,tParam);
		manager.load("dpc_logo_08.png",Texture.class,tParam);
		manager.load("dpc_logo_09.png",Texture.class,tParam);
		manager.load("dpc_logo_10.png",Texture.class,tParam);
		for (int i=0;i<27;i++) {
			manager.load("connecting/connecting_"+String.format("%02d",i)+".png",Texture.class,tParam);
		}
		for (int i=0;i<26;i++) {
			manager.load("connected/connected_"+String.format("%02d",i)+".png",Texture.class,tParam);
		}
		manager.load("button_blue.png",Texture.class,tParam);
		manager.load("back.png",Texture.class,tParam);
		manager.load("connection_blob.png",Texture.class,tParam);
		manager.load("suit_hearts.png",Texture.class,tParam);
		manager.load("suit_diamonds.png",Texture.class,tParam);
		manager.load("suit_clubs.png",Texture.class,tParam);
		manager.load("suit_spades.png",Texture.class,tParam);
		manager.load("card_back.png",Texture.class,tParam);
		manager.load("arrow_handdrawn.png",Texture.class,tParam);
		manager.load("button_bell_red.png",Texture.class,tParam);
		manager.load("split_button.png",Texture.class,tParam);
		manager.load("table_slot.png",Texture.class,tParam);
		manager.load("dialog_w_arrow.png",Texture.class,tParam);
		// sounds
		manager.load("sound/change.wav",Sound.class);
		manager.load("sound/check.wav",Sound.class);
		manager.load("sound/fold.wav",Sound.class);
		manager.load("sound/chip_clink.mp3",Sound.class);
		manager.load("sound/ching.mp3",Sound.class);
		manager.load("sound/bell.wav",Sound.class);
	}
	
	public void makeToast(String msg_) {
		activity.makeToast(msg_);
	}
	
	public void setWifiEnabled(boolean en,String ipAddress) {
		boolean switchedOn=(wifiEnabled==false&&en==true);
		boolean switchedOff=(wifiEnabled==true&&en==false);
		wifiEnabled=en;
		this.ipAddress=ipAddress;
		if (mWL!=null) {
			if (switchedOn) {
				mWL.wifiOn(ipAddress);
			} else if (switchedOff) {
				mWL.wifiOff();
			}
		}
	}
	
	public void performFacebookClick() {
		// TODO might need to consider synch here because this runs on android UI thread
		activity.performFacebookClick();
	}
	
	public void launchSettings() {
		settingsC=0;
		freezeAnimation();
	}
	
	public void exitApp() {
		exitC=0;
		freezeAnimation();
	}

	public void unfreezeAnimation() {
		freezeAnimation=false;
	}
	
	public void freezeAnimation() {
		freezeAnimation=true;
	}
	 
	/////////////////// Utility Functions ////////////////////
	public int calculateAzimuth() {
		int azimuth_=(int)(Gdx.input.getAzimuth());
		if (azimuth_<0) {
			azimuth_+=360;
		}
		int screenOrientation_=activity.getScreenOrientation();
		azimuth_+=screenOrientation_;
		if (azimuth_>=360) {
			azimuth_=azimuth_-360;
		}
		return azimuth_;
	}
	
	public static String getTimeStamp() {
    	Calendar c = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	return sdf.format(c.getTime());
    }
	
}


