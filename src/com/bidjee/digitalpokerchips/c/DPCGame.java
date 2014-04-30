package com.bidjee.digitalpokerchips.c;

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

public class DPCGame extends Game {
	
	public static final int RESOLUTION_LOW = 0;
	public static final int RESOLUTION_MEDIUM = 1;
	public static final int RESOLUTION_HIGH = 2;
	
	public static final String DEBUG_LOG_LIFECYCLE_TAG = "DPCLifecycle";
	public static final String DEBUG_LOG_NETWORK_TAG = "DPCNetwork";
	public static final String DEBUG_LOG_PLAYER_TAG = "DPCPlayer";
	public static final String DEBUG_LOG_TABLE_TAG = "DPCTable";
	public static final String DEBUG_LOG_GAME_STATE_TAG = "DPCGameState";
	public static final String DEBUG_LOG_WORLD_NAVIGATION_TAG = "DPCWorldNavigation";
	public static final String DEBUG_LOG_TOUCH_FOCUS_TAG = "DPCTouchFocus";
	public static final String DEBUG_LOG_SAVE_LOAD_TAG = "DPCSaveLoad";
	public static final String DEBUG_LOG_GAME_MOVES_TAG = "DPCGameMoves";
	
	
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
	private IActivity activity;
	public static ITextFactory textFactory;
	// Debugging //
	private FPSLogger fps;
	public static boolean debugMode=true;
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
	}
	
	////////////////////Life-cycle Events ////////////////////
	public void initGame() {
		Gdx.app.log(DEBUG_LOG_LIFECYCLE_TAG,"DPCGame - initGame()");
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
	public void create () {
		Gdx.app.log(DEBUG_LOG_LIFECYCLE_TAG, "************PlayerGame_create()************");		
	}
	
	@Override
	public void resize(int width,int height) {
		Gdx.app.log(DEBUG_LOG_LIFECYCLE_TAG, "DPCGame - resize("+width+","+height+")");
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
				mTS.resize(width,height);
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
		Gdx.app.log(DEBUG_LOG_LIFECYCLE_TAG, "DPCGame - pause()");
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
		Gdx.app.log(DEBUG_LOG_LIFECYCLE_TAG, "DPCGame - resume()");
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
		Gdx.app.log(DEBUG_LOG_LIFECYCLE_TAG, "DPCGame - dispose()");
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
			Gdx.app.log(DEBUG_LOG_LIFECYCLE_TAG, "DPCGame - render()");
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
		Gdx.app.log(DEBUG_LOG_LIFECYCLE_TAG, "DPCGame - loadSynchronous()");
		if (mWL==null&&mFL==null) {
			mWL=new WorldLayer(this,activity.getIPlayerNetwork(),activity.getIHostNetwork(),activity.getITableStore());
			mFL=new ForegroundLayer(this);
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
		textFactory.dispose();
		mFL.foregroundRenderer.loadLabels();
		mWL.worldRenderer.loadLabels();
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
		Gdx.app.log(DEBUG_LOG_LIFECYCLE_TAG, "DPCGame - loadAssets()");
		TextureParameter tParam=new TextureParameter();
		tParam.minFilter=TextureFilter.Linear;
		tParam.magFilter=TextureFilter.Linear;
		tParam.format=Format.RGBA8888;
		
		if (resolutionSetting==RESOLUTION_LOW) {
			manager.load("background_low_res.png",Texture.class,tParam);
		} else if (resolutionSetting==RESOLUTION_MEDIUM||
				resolutionSetting==RESOLUTION_HIGH) {
			manager.load("background_lb.png",Texture.class,tParam);
			manager.load("background_lt.png",Texture.class,tParam);
			manager.load("background_mb.png",Texture.class,tParam);
			manager.load("background_mt.png",Texture.class,tParam);
			manager.load("background_rb.png",Texture.class,tParam);
			manager.load("background_rt.png",Texture.class,tParam);
		}
		
		manager.load("black_hole.png",Texture.class,tParam);
		manager.load("rectangle_rounded.png",Texture.class,tParam);
		manager.load("black_circle.png",Texture.class,tParam);
		manager.load("hand.png",Texture.class,tParam);
		manager.load("table_button.png",Texture.class,tParam);
		
		manager.load("ok_button.png",Texture.class,tParam);
		manager.load("cancel_button.png",Texture.class,tParam);
		manager.load("chip_case.png",Texture.class,tParam);
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
		manager.load("background_help.png",Texture.class,tParam);
		manager.load("next_button.png",Texture.class,tParam);
		manager.load("dialog_black_fill.png",Texture.class,tParam);
		manager.load("dialog_black_corner.png",Texture.class,tParam);
		manager.load("cursor.png",Texture.class,tParam);
		manager.load("ping.png",Texture.class,tParam);
		manager.load("ping_hub.png",Texture.class,tParam);
		manager.load("logo_dpc.png",Texture.class,tParam);
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
	
}


