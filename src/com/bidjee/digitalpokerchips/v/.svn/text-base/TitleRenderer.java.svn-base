package com.bidjee.digitalpokerchips.v;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.bidjee.digitalpokerchips.c.TitleScreen;

public class TitleRenderer {
	
	int screenWidth;
	int screenHeight;
	
	SpriteBatch batch;
	final Matrix4 viewMatrix=new Matrix4();
	Color alphaShader;
	
	TitleScreen mTS;
	
	Texture logoTexture;
	Texture blackRectangleTexture;
	
	Texture loadBarTexture;
	
	public TitleRenderer(TitleScreen mTS_) {
		mTS=mTS_;
		batch=new SpriteBatch(100);
	}
	
	public void resize(int width,int height) {
		Gdx.app.log("DPCLifecycle", "TitleRenderer - resize("+width+","+height+")");
		viewMatrix.setToOrtho2D(0,0,width,height);
		batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batch.setProjectionMatrix(viewMatrix);
		screenWidth=width;
		screenHeight=height;
	}
	
	public void dispose() {
		Gdx.app.log("DPCLifecycle", "TitleRenderer - dispose()");
		if (blackRectangleTexture!=null)
			blackRectangleTexture.dispose();
		blackRectangleTexture=null;
		if (logoTexture!=null)
			logoTexture.dispose();
		logoTexture=null;
		if (loadBarTexture!=null)
			loadBarTexture.dispose();
		loadBarTexture=null;

		batch.dispose();
		mTS=null;
	}
	
	public void loadLogoTextures() {
		Gdx.app.log("DPCLifecycle", "TitleRenderer - loadLogoTextures()");
		if (blackRectangleTexture!=null)
			blackRectangleTexture.dispose();
		blackRectangleTexture=null;
		if (logoTexture!=null)
			logoTexture.dispose();
		logoTexture=null;
		logoTexture=new Texture(Gdx.files.internal("logo_bidjee.png"), Format.RGBA8888, true);
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		blackRectangleTexture=new Texture(Gdx.files.internal("black.png"), Format.RGBA8888, true);
		blackRectangleTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	}
	
	public void loadDemoTextures() {
		Gdx.app.log("DPCLifecycle", "TitleRenderer - loadDemoTextures()");
		if (loadBarTexture!=null)
			loadBarTexture.dispose();
		loadBarTexture=null;
		loadBarTexture=new Texture(Gdx.files.internal("load_bar.png"),Format.RGBA8888,true);
		loadBarTexture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
	}
	
	public void render() {
		batch.begin();
		
		alphaShader=batch.getColor();
		batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,mTS.screenOpacity);
		batch.draw(blackRectangleTexture,0,0,screenWidth,screenHeight);
		if (mTS.logo.opacity!=0) {
			batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,mTS.logo.opacity);
			batch.draw(logoTexture,
					mTS.logo.x-mTS.logo.radiusX,mTS.logo.y-mTS.logo.radiusY,
					mTS.logo.radiusX*2,mTS.logo.radiusY*2,
					0,0,744,744,false,false);
	        alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
        if (mTS.loadBar.opacity!=0) {
        	alphaShader=batch.getColor();
    		batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,mTS.loadBar.opacity);
    		batch.draw(loadBarTexture,
    				mTS.loadBar.x-mTS.loadBar.radiusX,mTS.loadBar.y-mTS.loadBar.radiusY,
    				mTS.loadBar.radiusX*2,mTS.loadBar.radiusY*2,
    				5,1,232,35,false,false);
    		batch.draw(loadBarTexture,
    				mTS.loadBar.x-mTS.loadBar.radiusX,mTS.loadBar.y-mTS.loadBar.radiusY,
    				mTS.loadBar.radiusX*2*mTS.loadProgress,mTS.loadBar.radiusY*2,
    				1,1,2,35,false,false);
    		 alphaShader=batch.getColor();
    		 batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
        }
       
        batch.end();
	}
	
	 public void checkGlError(String op) {
         int error;
         while ((error = Gdx.gl20.glGetError()) !=  GL20.GL_NO_ERROR) {
             Gdx.app.log("DPC", op + ": glError " + error);
             throw new RuntimeException(op + ": glError " + error);
         }
     }

}
