package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.Gdx;
import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.v.TitleRenderer;

public class TitleScreen {
	
	
	// Constants //
	private static final int DURATION_LOGO = 1000;
	
	static final int STATE_LOGO_FADE_IN = 0;
	static final int STATE_LOAD_SYNCH = 1;
	static final int STATE_LOGO_DISPLAY = 2;
	static final int STATE_LOGO_LOADING = 3;
	static final int STATE_LOGO_SHRUNK = 4;
	static final int STATE_FADE_OUT = 20;
	// State Variables //
	boolean screenLaidOut;
	int animationState;
	long startLogoTime;
	public float screenOpacity;
	public float initLoadProgress;
	public float loadProgress;
	public boolean loadingDone;
	public boolean finished;
	// Screen Scale & Layout //
	float screenWidth;
	float screenHeight;
	// Objects Contained //
	TitleRenderer titleRenderer;
	public DPCSprite logo;
	public DPCSprite loadBar;
	// References //
	DPCGame game;
	// Debugging //	
	
	public TitleScreen(DPCGame game_) {
		Gdx.app.log("DPCLifecycle", "TitleScreen - constructor()");
		game=game_;
		titleRenderer=new TitleRenderer(this);
		logo=new DPCSprite();
		loadBar=new DPCSprite();
		screenLaidOut=false;
		
	}
	
	public void dispose() {
		Gdx.app.log("DPCLifecycle", "TitleScreen - dispose()");
		titleRenderer.dispose();
		game=null;
	}
	
	public void resize(int screenWidth,int screenHeight) {
		Gdx.app.log("DPCLifecycle", "TitleScreen - resize("+screenWidth+","+screenHeight+")");
		titleRenderer.resize(screenWidth,screenHeight);
		if ((screenWidth!=this.screenWidth)||(screenHeight!=this.screenHeight)) {
			setDimensions(screenWidth,screenHeight);
			if (!screenLaidOut) {
				setPositions(screenWidth,screenHeight);
				screenLaidOut=true;
			} else {
				float scaleX=screenWidth/this.screenWidth;
				float scaleY=screenHeight/this.screenHeight;
				scalePositions(scaleX,scaleY);
			}
			this.screenWidth=screenWidth;
			this.screenHeight=screenHeight;
		}
	}
	
	private void setDimensions(int screenWidth,int screenHeight) {
		Gdx.app.log("DPCLifecycle", "TitleScreen - setDimensions("+screenWidth+","+screenHeight+")");
		logo.setDimensions((int)(screenHeight*0.5f),(int)(screenHeight*0.5f));
		loadBar.setDimensions((int) (screenHeight*0.3f),(int) (screenHeight*0.03f));
	}
	
	private void setPositions(int screenWidth,int screenHeight) {
		Gdx.app.log("DPCLifecycle", "TitleScreen - setPositions("+screenWidth+","+screenHeight+")");
		logo.setPosition(screenWidth*0.5f,screenHeight*0.5f);
		loadBar.setPosition(logo.x,screenHeight*0.2f);
	}
	
	private void scalePositions(float scaleX,float scaleY) {
		Gdx.app.log("DPCLifecycle", "TitleScreen - scalePositions("+scaleX+","+scaleY+")");
		logo.scalePosition(scaleX,scaleY);
		loadBar.scalePosition(scaleX,scaleY);
	}
	
	public void resume() {
		Gdx.app.log("DPCLifecycle", "TitleScreen - resume()");
		titleRenderer.loadLogoTextures();
		animationState=STATE_LOGO_FADE_IN;
		finished=false;
		screenOpacity=1;
		loadProgress=0;
		loadingDone=false;
		logo.opacity=0;
		loadBar.opacity=0;
	}
	
	public void render(float delta_) {
		animate(delta_);
		titleRenderer.render();
	}
	
	public void animate(float delta_) {
		if (animationState==STATE_LOGO_FADE_IN) {
			logo.opacity+=delta_*1.5f;
			if (logo.opacity>1) {
				logo.opacity=1;
				animationState=STATE_LOAD_SYNCH;
			}
		} else if (animationState==STATE_LOAD_SYNCH) { 
			startLogoTime=System.currentTimeMillis();
			game.loadSynchronous();
			titleRenderer.loadDemoTextures();
			animationState=STATE_LOGO_DISPLAY;
		} else if (animationState==STATE_LOGO_DISPLAY) {
			if ((System.currentTimeMillis()-startLogoTime)>DURATION_LOGO) {
				animationState=STATE_LOGO_LOADING;
				loadBar.opacity=1;
				initLoadProgress=game.getLoadPercent();
			}
		} else if (animationState==STATE_LOGO_LOADING) {
			loadProgress=(game.getLoadPercent()-initLoadProgress)/(1-initLoadProgress);
			if (game.getLoadPercent()==1) {
				finished=true;
			}
		} else if (animationState==STATE_FADE_OUT) {
			screenOpacity-=delta_*1f;
			if (screenOpacity<=0) {
				screenOpacity=0;
			}
			logo.opacity-=delta_*2f;
			if (logo.opacity<=0) {
				logo.opacity=0;
			}
			loadBar.opacity-=delta_*2f;
			if (loadBar.opacity<=0) {
				loadBar.opacity=0;
			}
		}
	}
	
	public boolean checkCompletion() {
		return finished;
	}
	
	public void fadeOut() {
		animationState=STATE_FADE_OUT;
	}

}
