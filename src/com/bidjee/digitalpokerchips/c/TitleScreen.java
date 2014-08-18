package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.Gdx;
import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.TextLabel;
import com.bidjee.digitalpokerchips.v.TitleRenderer;

public class TitleScreen {
	
	
	// Constants //
	private static final int DURATION_LOGO = 1000;
	
	static final int STATE_LOGO_FADE_IN = 0;
	static final int STATE_LOAD_SYNCH = 1;
	static final int STATE_LOGO_LOADING = 3;
	static final int STATE_LOGO_SHRUNK = 4;
	static final int STATE_FADE_OUT = 20;
	// State Variables //
	boolean screenLaidOut;
	int animationState;
	public float screenOpacity;
	public boolean loadingDone;
	public boolean finished;
	// Screen Scale & Layout //
	float screenWidth;
	float screenHeight;
	// Objects Contained //
	TitleRenderer titleRenderer;
	public DPCSprite logo;
	public TextLabel loadingLabel;
	// References //
	DPCGame game;
	// Debugging //	
	
	public TitleScreen(DPCGame game_) {
		Gdx.app.log("DPCLifecycle", "TitleScreen - constructor()");
		game=game_;
		titleRenderer=new TitleRenderer(this);
		logo=new DPCSprite();
		loadingLabel=new TextLabel("- Loading -",0,true,0,false);
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
		logo.setDimensions((int)(screenHeight*0.89f),(int)(screenHeight*0.5f));
		loadingLabel.setMaxDimensions((int) (screenHeight*0.3f),(int) (screenHeight*0.03f));
		loadingLabel.setTextSizeToMax();
	}
	
	private void setPositions(int screenWidth,int screenHeight) {
		Gdx.app.log("DPCLifecycle", "TitleScreen - setPositions("+screenWidth+","+screenHeight+")");
		logo.setPosition(screenWidth*0.5f,screenHeight*0.5f);
		loadingLabel.setPosition(screenWidth*0.5f,screenHeight*0.15f);
	}
	
	private void scalePositions(float scaleX,float scaleY) {
		Gdx.app.log("DPCLifecycle", "TitleScreen - scalePositions("+scaleX+","+scaleY+")");
		logo.scalePosition(scaleX,scaleY);
	}
	
	public void resume() {
		Gdx.app.log("DPCLifecycle", "TitleScreen - resume()");
		titleRenderer.loadLogoTextures();
		animationState=STATE_LOGO_FADE_IN;
		finished=false;
		screenOpacity=1;
		loadingDone=false;
		logo.opacity=0;
		loadingLabel.opacity=0;
		resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	}
	
	public void render(float delta) {
		animate(delta);
		titleRenderer.render();
	}
	
	public void animate(float delta) {
		if (animationState==STATE_LOGO_FADE_IN) {
			logo.opacity+=delta*1.5f;
			if (logo.opacity>1) {
				logo.opacity=1;
				titleRenderer.loadDemoTextures();
				loadingLabel.fadeIn();
				animationState=STATE_LOAD_SYNCH;
			}
		} else if (animationState==STATE_LOAD_SYNCH) {
			game.loadSynchronous();
			animationState=STATE_LOGO_LOADING;
		} else if (animationState==STATE_LOGO_LOADING) {
			loadingLabel.animate(delta);
			if (game.getLoadPercent()==1) {
				finished=true;
			}
		} else if (animationState==STATE_FADE_OUT) {
			screenOpacity-=delta*1f;
			if (screenOpacity<=0) {
				screenOpacity=0;
			}
			logo.opacity-=delta*2f;
			if (logo.opacity<=0) {
				logo.opacity=0;
			}
			loadingLabel.opacity-=delta*2f;
			if (loadingLabel.opacity<=0) {
				loadingLabel.opacity=0;
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
