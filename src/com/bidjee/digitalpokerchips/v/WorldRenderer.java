package com.bidjee.digitalpokerchips.v;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.bidjee.digitalpokerchips.c.DPCGame;
import com.bidjee.digitalpokerchips.c.Table;
import com.bidjee.digitalpokerchips.c.WorldLayer;
import com.bidjee.digitalpokerchips.m.Chip;
import com.bidjee.digitalpokerchips.m.ChipCase;
import com.bidjee.digitalpokerchips.m.ChipStack;
import com.bidjee.digitalpokerchips.m.ColorPool;
import com.bidjee.digitalpokerchips.m.GameLogic;
import com.bidjee.digitalpokerchips.m.Player;
import com.bidjee.digitalpokerchips.m.Seat;
import com.bidjee.graphics.ChipBatch;

public class WorldRenderer {
	
	// Constants //
	static final float shadowDownsampleFactor=0.4f;
	static final int chip_img_height=252;
	
	static final Color blackColor=new Color(0,0,0,1);
	static final Color whiteColor=new Color(1,1,1,1);
	public static final Color plaqueColor=new Color(0.48f,0.43f,0.34f,1);
	public static final Color tableNameColor=new Color(0.45f,0.45f,0.06f,1);
	public static final Color chipValueColor=new Color(0.3f,0.3f,0.3f,1);
	// State Variables //
	int[] potChipsRendered=new int[Table.NUM_SEATS];
	int[] mainStackTop=new int[ChipCase.CHIP_TYPES];
	// Contained Objects //
	SpriteBatch batch;
	ChipBatch chipBatch;
	FrameBuffer shadowsFBO;
	TextureRegion shadows;
	final Matrix4 screenMatrix = new Matrix4();
	Color alphaShader;
	public Camera camera;
	// References //
	WorldLayer mWL;
	// Debugging //
	// Scale & Layout //
	int screenWidth;
	public int screenHeight;
	 // Textures //
	Texture backgroundTexture;
	Texture devHostTexture;
	Texture devHostShineTexture;
	Texture devPlayer1Texture;
	Texture devPlayer1ShineTexture;
	Texture devPlayer2Texture;
	Texture devPlayer3Texture;
	
	Texture tableHighlightTexture;
	Texture dealerButtonTexture;
	Texture[] chipTextures;
	Texture shadowTexture;
	
	Texture[] connectingTextures;
	Texture[] connectedTextures;
	TextureRegion connectionArrowsRegion;
	
	TextureRegion envelopeRegion;
	
	public WorldRenderer(WorldLayer mWL_) {
		mWL=mWL_;
		batch=new SpriteBatch();
		chipBatch=new ChipBatch();
		camera=new Camera(this);
		mWL=mWL_;
		chipTextures=new Texture[ChipCase.CHIP_TYPES*Chip.CHIP_ROTATION_N];
		connectingTextures=new Texture[27];
		connectedTextures=new Texture[26];
	}
	
	public void resize(int width,int height) {
		Gdx.app.log("DPCLifecycle", "WorldRenderer - resize("+width+","+height+")");
		screenWidth=width;
		screenHeight=height;
		screenMatrix.setToOrtho2D(0,0,screenWidth,screenHeight);
		batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		chipBatch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		camera.resize(width,height);
		if (shadowsFBO==null) {
			shadowsFBO=new FrameBuffer(Format.RGBA8888,screenWidth,screenHeight,false);
			int screenWidthScaled=(int)(screenWidth*shadowDownsampleFactor);
			int screenHeightScaled=(int)(screenHeight*shadowDownsampleFactor);
			shadows=new TextureRegion(shadowsFBO.getColorBufferTexture(),screenWidthScaled,screenHeightScaled);
			shadows.flip(false,true);
		}
	}
	
	public float xTouchScreenToWorld(float xScreen_) {
		return (xScreen_-screenWidth*0.5f)/camera.zoom+camera.x;
	}
	
	public float yTouchScreenToWorld(float yScreen_) {
		return (screenHeight*0.5f-yScreen_)/camera.zoom+camera.y;
	}
	
	public float xScreenToWorld(float xScreen_) {
		return (xScreen_-screenWidth*0.5f)/camera.zoom+camera.x;
	}
	
	public float yScreenToWorld(float yScreen_) {
		return (yScreen_-screenHeight*0.5f)/camera.zoom+camera.y;
	}
	
	public float xWorldToScreen(float xWorld_) {
		return (xWorld_-camera.x)*camera.zoom+screenWidth*0.5f;
	}
	
	public float yWorldToScreen(float yWorld_) {
		return screenHeight*0.5f-(camera.y-yWorld_)*camera.zoom;
	}
	
	public void dispose() {
		Gdx.app.log("DPCLifecycle", "WorldRenderer - dispose()");
		if (batch!=null)
			batch.dispose();
		if (chipBatch!=null)
			chipBatch.dispose();
		shadowsFBO.dispose();
		mWL=null;
	}
	
	public void loadTextures(AssetManager manager) {
		Gdx.app.log("DPCLifecycle", "WorldRenderer - loadTextures()");
		
		if (mWL.game.resolutionSetting==DPCGame.RESOLUTION_LOW) {
			backgroundTexture=manager.get("background.png",Texture.class);
		} else if (mWL.game.resolutionSetting==DPCGame.RESOLUTION_MEDIUM||
				mWL.game.resolutionSetting==DPCGame.RESOLUTION_HIGH) {
			backgroundTexture=manager.get("background.png",Texture.class);
		}
		
		devHostTexture=manager.get("dev_host.png", Texture.class);
		devHostShineTexture=manager.get("dev_host_shine.png", Texture.class);
		devPlayer1Texture=manager.get("dev_player1.png", Texture.class);
		devPlayer1ShineTexture=manager.get("dev_player1_shine.png", Texture.class);
		devPlayer2Texture=manager.get("dev_player2.png", Texture.class);
		devPlayer3Texture=manager.get("dev_player3.png", Texture.class);
		tableHighlightTexture=manager.get("table_highlight.png",Texture.class);
		dealerButtonTexture=manager.get("dealer_chip.png",Texture.class);
		shadowTexture=manager.get("shadow.png",Texture.class);
		chipTextures[ChipCase.CHIP_A*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_0]=manager.get("chip_0_0.png",Texture.class);
		chipTextures[ChipCase.CHIP_A*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_135]=manager.get("chip_0_1.png",Texture.class);
		chipTextures[ChipCase.CHIP_A*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_202]=manager.get("chip_0_2.png",Texture.class);
		chipTextures[ChipCase.CHIP_B*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_0]=manager.get("chip_1_0.png",Texture.class);
		chipTextures[ChipCase.CHIP_B*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_135]=manager.get("chip_1_1.png",Texture.class);
		chipTextures[ChipCase.CHIP_B*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_202]=manager.get("chip_1_2.png",Texture.class);
		chipTextures[ChipCase.CHIP_C*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_0]=manager.get("chip_2_0.png",Texture.class);
		chipTextures[ChipCase.CHIP_C*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_135]=manager.get("chip_2_1.png",Texture.class);
		chipTextures[ChipCase.CHIP_C*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_202]=manager.get("chip_2_2.png",Texture.class);
		
		for (int i=0;i<27;i++) {
			connectingTextures[i]=manager.get("connecting/connecting_"+String.format("%02d",i)+".png",Texture.class);
		}
		for (int i=0;i<26;i++) {
			connectedTextures[i]=manager.get("connected/connected_"+String.format("%02d",i)+".png",Texture.class);
		}
		connectionArrowsRegion=new TextureRegion(manager.get("connection_arrows.png",Texture.class),243,39);
		envelopeRegion=new TextureRegion(manager.get("envelope.png",Texture.class),110,70);
	}
	
	public void loadLabels() {
		Gdx.app.log("DPCLifecycle", "WorldRenderer - loadLabels()");
		if (mWL.thisPlayer.betStack.size()>0) {
			mWL.thisPlayer.betStack.totalLabel.loadTexture();
		}
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			mWL.thisPlayer.mainStacks[i].totalLabel.loadTexture();
		}
		for (int i=0;i<Table.NUM_SEATS;i++) {
			if (mWL.table.seats[i].player!=null) {
				if (mWL.table.seats[i].player.betStack.size()>0) {
					mWL.table.seats[i].player.betStack.totalLabel.loadTexture();
				}
			}
		}
		for (int i=0;i<mWL.table.pots.size();i++) {
			mWL.table.pots.get(i).potStack.totalLabel.loadTexture();
		}
		mWL.table.seats[0].undoButton.getLabel().loadTexture();
	}
	
	public void render() {
		renderShadowsToBuffer();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		batch.setProjectionMatrix(camera.getViewMatrix());
		chipBatch.setProjectionMatrix(camera.getViewMatrix());
		batch.begin();
		
		renderBackground();
		
		renderLobby();
	
		renderShadows();
		
		renderDealerButton();
		
		renderConnectionStuff();
		
		renderPlayerChips();
		
		
		renderWinStacks();
		
		if (mWL.table.pots.size()>0) {
			renderPot();
		}
		
		renderBetStacks();
		
		renderBettingStacks();
		
		batch.end();
	}
	
	private void renderPlayerChips() {
		
		// TODO don't render half chips if fading opacity
		
		ArrayList<Chip> chips=new ArrayList<Chip>();
		chips.addAll(mWL.thisPlayer.betStack.stack.subList(0,mWL.thisPlayer.betStack.renderSize()));
		chips.addAll(mWL.thisPlayer.bettingStack.stack);
		chips.addAll(mWL.thisPlayer.cancellingStack.stack);
		
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			chips.addAll(mWL.thisPlayer.mainStacks[i].stack.subList(0,mWL.thisPlayer.mainStacks[i].renderSize()));
		}
		chips.addAll(mWL.thisPlayer.cancelStack.stack);
		if (mWL.thisPlayer.pickedUpChip!=null) {
			chips.add(mWL.thisPlayer.pickedUpChip);
		}
		Collections.sort(chips);
		
		int betStackTopZ=mWL.thisPlayer.betStack.renderSize()-1;
		for (int mainStack=0;mainStack<ChipCase.CHIP_TYPES;mainStack++) {
			mainStackTop[mainStack]=mWL.thisPlayer.mainStacks[mainStack].pastMax()?
					mWL.thisPlayer.mainStacks[mainStack].renderSize()-1:-1;
		}
		
		for (int i=0;i<chips.size();i++) {
			Chip chip=chips.get(i);
			boolean halfChip=false;
			float radiusXChip=Chip.radiusX*(1+chip.z*Chip.perspectiveGradient);
			float radiusYChip=Chip.radiusY*(1+chip.z*Chip.perspectiveGradient);
			float radiusYMult=2f;
			if (halfChip) {
				radiusYMult=1f;
			}
			float zyOffset=Chip.Z_Y_OFFSET_RATIO*Chip.radiusY*chip.z*
					(1+0.5f*Chip.perspectiveGradient*(chip.z-1));
			int srcYOffset=halfChip?chip_img_height/2:0;
			int srcHeight=halfChip?chip_img_height/2:chip_img_height;
			
			batch.draw(chipTextures[chip.chipType*Chip.CHIP_ROTATION_N+chip.imgRotation],
					chip.x-radiusXChip,chip.y-radiusYChip+zyOffset,
					radiusXChip*2,radiusYChip*radiusYMult,
					0,srcYOffset,256,srcHeight,false,false);
			boolean renderTotals=false;
			if (i+1<chips.size()) {
				if (chips.get(i+1).z>chips.get(i).z) {
					renderTotals=true;
				}
			} else {
				renderTotals=true;
			}
			if (renderTotals) {
				if (chips.get(i).z==betStackTopZ) {
					/*
					batch.draw(mWL.thisPlayer.betStack.totalLabel.texture,
							mWL.thisPlayer.betStack.totalLabel.x-mWL.thisPlayer.betStack.totalLabel.radiusX,
							mWL.thisPlayer.betStack.totalLabel.y-mWL.thisPlayer.betStack.totalLabel.radiusY,
							mWL.thisPlayer.betStack.totalLabel.radiusX*2,
							mWL.thisPlayer.betStack.totalLabel.radiusY*2,
							0,0,mWL.thisPlayer.betStack.totalLabel.radiusX*2,
							mWL.thisPlayer.betStack.totalLabel.radiusY*2,false,false);
							*/
				}
				for (int mainStack=0;mainStack<ChipCase.CHIP_TYPES;mainStack++) {
					/*
					if (chips.get(i).z==mainStackTop[mainStack]) {
						batch.draw(mWL.thisPlayer.mainStacks[mainStack].totalLabel.texture,
								mWL.thisPlayer.mainStacks[mainStack].totalLabel.x-mWL.thisPlayer.mainStacks[mainStack].totalLabel.radiusX,
								mWL.thisPlayer.mainStacks[mainStack].totalLabel.y-mWL.thisPlayer.mainStacks[mainStack].totalLabel.radiusY,
								mWL.thisPlayer.mainStacks[mainStack].totalLabel.radiusX*2,
								mWL.thisPlayer.mainStacks[mainStack].totalLabel.radiusY*2,
								0,0,mWL.thisPlayer.mainStacks[mainStack].totalLabel.radiusX*2,
								mWL.thisPlayer.mainStacks[mainStack].totalLabel.radiusY*2,false,false);
					}
					*/
				}
			}
		}
		
	}

	private void renderConnectionStuff() {
		if (mWL.thisPlayer.connectionSprite.opacity!=0) {
			alphaShader=batch.getColor();
			batch.setColor(ColorPool.colors[mWL.thisPlayer.color].r,ColorPool.colors[mWL.thisPlayer.color].g,
	        		ColorPool.colors[mWL.thisPlayer.color].b,mWL.thisPlayer.connectionSprite.opacity);
			batch.draw(connectingTextures[mWL.thisPlayer.connectionSprite.frame],
					mWL.thisPlayer.connectionSprite.x-mWL.thisPlayer.connectionSprite.radiusX,
					mWL.thisPlayer.connectionSprite.y-mWL.thisPlayer.connectionSprite.radiusY,
					mWL.thisPlayer.connectionSprite.radiusX*2,mWL.thisPlayer.connectionSprite.radiusY*2,
					0,0,400,250,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mWL.thisPlayer.connectedSprite.opacity!=0) {
			alphaShader=batch.getColor();
			batch.setColor(ColorPool.colors[mWL.thisPlayer.color].r,ColorPool.colors[mWL.thisPlayer.color].g,
	        		ColorPool.colors[mWL.thisPlayer.color].b,mWL.thisPlayer.connectedSprite.opacity);
			batch.draw(connectedTextures[mWL.thisPlayer.connectedSprite.frame],
					mWL.thisPlayer.connectedSprite.x-mWL.thisPlayer.connectedSprite.radiusX,
					mWL.thisPlayer.connectedSprite.y-mWL.thisPlayer.connectedSprite.radiusY,
					mWL.thisPlayer.connectedSprite.radiusX*2,mWL.thisPlayer.connectedSprite.radiusY*2,
					0,0,400,250,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		
		
	}

	private void renderBackground () {
		alphaShader=batch.getColor();
		
		if (mWL.game.resolutionSetting==DPCGame.RESOLUTION_LOW) {
			batch.draw(backgroundTexture,mWL.backgroundSprite.x-mWL.backgroundSprite.radiusX,
					mWL.backgroundSprite.y-mWL.backgroundSprite.radiusY,
					mWL.backgroundSprite.radiusX*2,mWL.backgroundSprite.radiusY*2);
		} else if (mWL.game.resolutionSetting==DPCGame.RESOLUTION_MEDIUM||
				mWL.game.resolutionSetting==DPCGame.RESOLUTION_HIGH) {
			batch.draw(backgroundTexture,mWL.backgroundSprite.x-mWL.backgroundSprite.radiusX,
					mWL.backgroundSprite.y-mWL.backgroundSprite.radiusY,
					mWL.backgroundSprite.radiusX*2,mWL.backgroundSprite.radiusY*2);
		}
		
		float radiusXScaled=mWL.homeDeviceAnimation.hostSprite.radiusX*(1+mWL.homeDeviceAnimation.hostSprite.z*Chip.perspectiveGradient);
		float radiusYScaled=mWL.homeDeviceAnimation.hostSprite.radiusY*(1+mWL.homeDeviceAnimation.hostSprite.z*Chip.perspectiveGradient);
		float zyOffset=Chip.Z_Y_OFFSET_RATIO*mWL.homeDeviceAnimation.hostSprite.radiusY*mWL.homeDeviceAnimation.hostSprite.z*
				(1+0.5f*Chip.perspectiveGradient*(mWL.homeDeviceAnimation.hostSprite.z-1));
		zyOffset=0;
		batch.draw(devHostTexture,
				mWL.homeDeviceAnimation.hostSprite.x-radiusXScaled,
				mWL.homeDeviceAnimation.hostSprite.y-radiusYScaled+zyOffset,
				radiusXScaled*2,radiusYScaled*2,
				0,0,1200,553,false,false);
		radiusXScaled=mWL.homeDeviceAnimation.p1Sprite.radiusX*(1+mWL.homeDeviceAnimation.p1Sprite.z*Chip.perspectiveGradient);
		radiusYScaled=mWL.homeDeviceAnimation.p1Sprite.radiusY*(1+mWL.homeDeviceAnimation.p1Sprite.z*Chip.perspectiveGradient);
		zyOffset=Chip.Z_Y_OFFSET_RATIO*mWL.homeDeviceAnimation.p1Sprite.radiusY*mWL.homeDeviceAnimation.p1Sprite.z*
				(1+0.5f*Chip.perspectiveGradient*(mWL.homeDeviceAnimation.p1Sprite.z-1));
		zyOffset=0;
		batch.draw(devPlayer1Texture,
				mWL.homeDeviceAnimation.p1Sprite.x-radiusXScaled,
				mWL.homeDeviceAnimation.p1Sprite.y-radiusYScaled,
				radiusXScaled*2,radiusYScaled*2,
				0,0,1248,516, false,false);
		batch.draw(devPlayer2Texture,
				mWL.homeDeviceAnimation.p2Sprite.x-mWL.homeDeviceAnimation.p2Sprite.radiusX,
				mWL.homeDeviceAnimation.p2Sprite.y-mWL.homeDeviceAnimation.p2Sprite.radiusY,
				mWL.homeDeviceAnimation.p2Sprite.radiusX*2,mWL.homeDeviceAnimation.p2Sprite.radiusY*2,
				0,0,140,160, false,false);
		batch.draw(devPlayer3Texture,
				mWL.homeDeviceAnimation.p3Sprite.x-mWL.homeDeviceAnimation.p3Sprite.radiusX,
				mWL.homeDeviceAnimation.p3Sprite.y-mWL.homeDeviceAnimation.p3Sprite.radiusY,
				mWL.homeDeviceAnimation.p3Sprite.radiusX*2,mWL.homeDeviceAnimation.p3Sprite.radiusY*2,
				0,0,140,160,false,false);
		
		batch.flush();
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mWL.homeDeviceAnimation.chip1Sprite.opacity);
		for (int i=0;i<4;i++) {
			Rectangle scissors = new Rectangle(505,286,262,200);
			
			if (i==0) {
				scissors.set(xWorldToScreen(mWL.homeDeviceAnimation.hostClippingRect.x),
						yWorldToScreen(mWL.homeDeviceAnimation.hostClippingRect.y),
						xWorldToScreen(mWL.homeDeviceAnimation.hostClippingRect.width),
						yWorldToScreen(mWL.homeDeviceAnimation.hostClippingRect.height));
			} else if (i==1) {
				scissors.set(xWorldToScreen(mWL.homeDeviceAnimation.p1ClippingRect.x),
						yWorldToScreen(mWL.homeDeviceAnimation.p1ClippingRect.y),
						xWorldToScreen(mWL.homeDeviceAnimation.p1ClippingRect.width),
						yWorldToScreen(mWL.homeDeviceAnimation.p1ClippingRect.height));
			} else if (i==2) {
				scissors.set(xWorldToScreen(mWL.homeDeviceAnimation.p2ClippingRect.x),
						yWorldToScreen(mWL.homeDeviceAnimation.p2ClippingRect.y),
						xWorldToScreen(mWL.homeDeviceAnimation.p2ClippingRect.width),
						yWorldToScreen(mWL.homeDeviceAnimation.p2ClippingRect.height));
			} else if (i==3) {
				scissors.set(xWorldToScreen(mWL.homeDeviceAnimation.p3ClippingRect.x),
						yWorldToScreen(mWL.homeDeviceAnimation.p3ClippingRect.y),
						xWorldToScreen(mWL.homeDeviceAnimation.p3ClippingRect.width),
						yWorldToScreen(mWL.homeDeviceAnimation.p3ClippingRect.height));
			}
			
			if (ScissorStack.pushScissors(scissors)) {
				radiusXScaled=mWL.homeDeviceAnimation.chip1Sprite.radiusX*(1+mWL.homeDeviceAnimation.chip1Sprite.z*Chip.perspectiveGradient);
				radiusYScaled=mWL.homeDeviceAnimation.chip1Sprite.radiusY*(1+mWL.homeDeviceAnimation.chip1Sprite.z*Chip.perspectiveGradient);
				zyOffset=Chip.Z_Y_OFFSET_RATIO*mWL.homeDeviceAnimation.chip1Sprite.radiusY*mWL.homeDeviceAnimation.chip1Sprite.z*
						(1+0.5f*Chip.perspectiveGradient*(mWL.homeDeviceAnimation.chip1Sprite.z-1));
				zyOffset=0;
				batch.draw(chipTextures[0],
						mWL.homeDeviceAnimation.chip1Sprite.x-radiusXScaled,
						mWL.homeDeviceAnimation.chip1Sprite.y-radiusYScaled,
						radiusXScaled*2,radiusYScaled*2,
						0,0,256,chip_img_height,false,false);
				batch.draw(chipTextures[0],
						mWL.homeDeviceAnimation.chip2Sprite.x-mWL.homeDeviceAnimation.chip2Sprite.radiusX,
						mWL.homeDeviceAnimation.chip2Sprite.y-mWL.homeDeviceAnimation.chip2Sprite.radiusY,
						mWL.homeDeviceAnimation.chip2Sprite.radiusX*2,mWL.homeDeviceAnimation.chip2Sprite.radiusY*2,
						0,0,256,chip_img_height,false,false);
				batch.draw(chipTextures[0],
						mWL.homeDeviceAnimation.chip3Sprite.x-mWL.homeDeviceAnimation.chip3Sprite.radiusX,
						mWL.homeDeviceAnimation.chip3Sprite.y-mWL.homeDeviceAnimation.chip3Sprite.radiusY,
						mWL.homeDeviceAnimation.chip3Sprite.radiusX*2,mWL.homeDeviceAnimation.chip3Sprite.radiusY*2,
						0,0,256,chip_img_height,false,false);
				
				batch.flush();
				ScissorStack.popScissors();
			}
			
		}
		
		if (mWL.homeDeviceAnimation.hostShineSprite.opacity!=0) {
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mWL.homeDeviceAnimation.hostShineSprite.opacity);
			batch.draw(devHostShineTexture,
					mWL.homeDeviceAnimation.hostShineSprite.x-mWL.homeDeviceAnimation.hostShineSprite.radiusX,
					mWL.homeDeviceAnimation.hostShineSprite.y-mWL.homeDeviceAnimation.hostShineSprite.radiusY,
					mWL.homeDeviceAnimation.hostShineSprite.radiusX*2,mWL.homeDeviceAnimation.hostShineSprite.radiusY*2,
					0,0,1200,553, false,false);
		}
		if (mWL.homeDeviceAnimation.p1ShineSprite.opacity!=0) {
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mWL.homeDeviceAnimation.p1ShineSprite.opacity);
			batch.draw(devPlayer1ShineTexture,
					mWL.homeDeviceAnimation.p1ShineSprite.x-mWL.homeDeviceAnimation.p1ShineSprite.radiusX,
					mWL.homeDeviceAnimation.p1ShineSprite.y-mWL.homeDeviceAnimation.p1ShineSprite.radiusY,
					mWL.homeDeviceAnimation.p1ShineSprite.radiusX*2,mWL.homeDeviceAnimation.p1ShineSprite.radiusY*2,
					0,0,1248,516, false,false);
		}
		
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
		
		// draw check highlight
		if (mWL.thisPlayer.checkButton.opacity!=0) {
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mWL.thisPlayer.checkButton.opacity);
			batch.draw(tableHighlightTexture,
					mWL.thisPlayer.checkButton.x-mWL.thisPlayer.checkButton.radiusX,
					mWL.thisPlayer.checkButton.y-mWL.thisPlayer.checkButton.radiusY,
					mWL.thisPlayer.checkButton.radiusX*2,mWL.thisPlayer.checkButton.radiusY*2,
					0,0,200,200,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		
		if (mWL.game.wifiEnabled) {
			/*
			batch.draw(helpTexture,
					mWL.thisPlayer.helpButton.x-mWL.thisPlayer.helpButton.radiusX,
					mWL.thisPlayer.helpButton.y-mWL.thisPlayer.helpButton.radiusY,
					mWL.thisPlayer.helpButton.radiusX*2,mWL.thisPlayer.helpButton.radiusY*2,
					0,0,128,128, false,false);
			batch.draw(hostTexture,
					mWL.thisPlayer.hostButton.x-mWL.thisPlayer.hostButton.radiusX,
					mWL.thisPlayer.hostButton.y-mWL.thisPlayer.hostButton.radiusY,
					mWL.thisPlayer.hostButton.radiusX*2,mWL.thisPlayer.hostButton.radiusY*2,
					0,0,256,256, false,false);
			batch.end();
			DPCGame.textRenderer.renderButtonLabels(batch.getProjectionMatrix().getValues());
			batch.begin();
			*/
		} else {
			/*
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mWL.thisPlayer.wifiButton.opacity);
			batch.draw(wifiTexture,
					mWL.thisPlayer.wifiButton.x-mWL.thisPlayer.wifiButton.radiusX,
					mWL.thisPlayer.wifiButton.y-mWL.thisPlayer.wifiButton.radiusY,
					mWL.thisPlayer.wifiButton.radiusX*2,mWL.thisPlayer.wifiButton.radiusY*2,
					0,0,128,128, false,false);
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
	        */
		}
		
		
	}
	
	private void renderLobby() {
		
		Player thisPlayer_;
		
		for (int i=0;i<Table.NUM_SEATS;i++) {
			thisPlayer_=mWL.table.seats[i].player;
			if (thisPlayer_!=null) {
				renderPlayerInLobby(thisPlayer_);
				alphaShader=batch.getColor();
			} else {
				
			}
		}
		
		thisPlayer_=mWL.table.pickedUpPlayer;
		if (thisPlayer_!=null) {
			renderPlayerInLobby(thisPlayer_);
		}
		
		for (int i=0;i<Table.NUM_SEATS;i++) {
			thisPlayer_=mWL.table.seats[i].player;
			if (thisPlayer_!=null) {
				if (thisPlayer_.joinToken.opacity!=0) {
					batch.draw(envelopeRegion,
							thisPlayer_.joinToken.x-thisPlayer_.joinToken.radiusX,
							thisPlayer_.joinToken.y-thisPlayer_.joinToken.radiusY,
							thisPlayer_.joinToken.radiusX,thisPlayer_.joinToken.radiusY,
							thisPlayer_.joinToken.radiusX*2,thisPlayer_.joinToken.radiusY*2,
							1,1,thisPlayer_.getRotation());
				}
			}
		}
	}
	
	private void renderPlayerInLobby(Player thisPlayer) {
        
        if (thisPlayer.connectionSprite.opacity!=0) {
			alphaShader=batch.getColor();
			batch.setColor(ColorPool.colors[thisPlayer.color].r,ColorPool.colors[thisPlayer.color].g,
	        		ColorPool.colors[thisPlayer.color].b,thisPlayer.connectionSprite.opacity);
			batch.draw(connectingTextures[thisPlayer.connectionSprite.frame],
					thisPlayer.connectionSprite.x-thisPlayer.connectionSprite.radiusX,
					thisPlayer.connectionSprite.y-thisPlayer.connectionSprite.radiusY,
					thisPlayer.connectionSprite.radiusX,thisPlayer.connectionSprite.radiusY,
					thisPlayer.connectionSprite.radiusX*2,thisPlayer.connectionSprite.radiusY*2,
					1,1,thisPlayer.connectionSprite.rotation,
					0,0,400,250,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (thisPlayer.connectedSprite.opacity!=0) {
			alphaShader=batch.getColor();
			batch.setColor(ColorPool.colors[thisPlayer.color].r,ColorPool.colors[thisPlayer.color].g,
	        		ColorPool.colors[thisPlayer.color].b,thisPlayer.connectedSprite.opacity);
			batch.draw(connectedTextures[thisPlayer.connectedSprite.frame],
					thisPlayer.connectedSprite.x-thisPlayer.connectedSprite.radiusX,
					thisPlayer.connectedSprite.y-thisPlayer.connectedSprite.radiusY,
					thisPlayer.connectedSprite.radiusX,thisPlayer.connectedSprite.radiusY,
					thisPlayer.connectedSprite.radiusX*2,thisPlayer.connectedSprite.radiusY*2,
					1,1,thisPlayer.connectedSprite.rotation,
					0,0,400,250,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (thisPlayer.connectionArrows.opacity!=0) {
			alphaShader=batch.getColor();
			batch.setColor(ColorPool.colors[thisPlayer.color].r,ColorPool.colors[thisPlayer.color].g,
	        		ColorPool.colors[thisPlayer.color].b,thisPlayer.connectionArrows.opacity);
			batch.draw(connectionArrowsRegion,
					thisPlayer.connectionArrows.x-thisPlayer.connectionArrows.radiusX,
					thisPlayer.connectionArrows.y-thisPlayer.connectionArrows.radiusY,
					thisPlayer.connectionArrows.radiusX,thisPlayer.connectionArrows.radiusY,
					thisPlayer.connectionArrows.radiusX*2,thisPlayer.connectionArrows.radiusY*2,
					1,1,thisPlayer.connectionArrows.rotation);
			batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}

	}

	private void renderShadowsToBuffer() {
		batch.setBlendFunction(GL11.GL_ONE,GL11.GL_ONE);
		batch.setProjectionMatrix(screenMatrix);
		shadowsFBO.begin();
		batch.begin();
		Gdx.gl.glClearColor(0f,0f,0f,0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		int radiusX0_=(int) (Chip.radiusX*shadowDownsampleFactor*1.1f);
		int radiusY0_=(int) (Chip.radiusY*shadowDownsampleFactor*1.1f);
		float zxOffset_=Chip.Z_X_OFFSET_RATIO*radiusX0_;
		float zyOffset_=Chip.Z_Y_OFFSET_RATIO*radiusY0_;
		ChipStack stack_;
		float yStack_;
		float xStack_;
		int numStack_;
		// chip stacks
		for (int chip_=0;chip_<ChipCase.CHIP_TYPES;chip_++) {
			stack_=mWL.thisPlayer.mainStacks[chip_];
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,stack_.getOpacity());
			yStack_=(stack_.getY()-(mWL.camPosPlayer.getY()-screenHeight*0.5f))*shadowDownsampleFactor;
			xStack_=(stack_.getX()-(mWL.camPosPlayer.getX()-screenWidth*0.5f))*shadowDownsampleFactor;
			numStack_=stack_.renderSize();
			int N=0;
			for (;N<numStack_;N++) {
				batch.draw(shadowTexture,
						xStack_-radiusX0_+zxOffset_*(N+1),
						yStack_-radiusY0_+zyOffset_*(N+1),
						radiusX0_*2,radiusY0_*2,
						0,0,32,32,false,false);
			}
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
		// picked up chip
		if (mWL.thisPlayer.pickedUpChip!=null) {
			Chip PUC_=mWL.thisPlayer.pickedUpChip;
			batch.draw(shadowTexture,
					(PUC_.x-(mWL.camPosPlayer.getX()-screenWidth*0.5f))*shadowDownsampleFactor-radiusX0_+zxOffset_*(PUC_.z+1),
					(PUC_.y-(mWL.camPosPlayer.getY()-screenHeight*0.5f))*shadowDownsampleFactor-radiusY0_+zyOffset_*(PUC_.z+1),
					radiusX0_*2,radiusY0_*2,
					0,0,32,32,false,false);
		}
		// betting stack
		stack_=mWL.thisPlayer.bettingStack;
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,stack_.getOpacity());
		numStack_=stack_.size();
		for (int N=0;N<numStack_;N++) {
			Chip thisChip_=stack_.get(N);
			batch.draw(shadowTexture,
					(thisChip_.x-(mWL.camPosPlayer.getX()-screenWidth*0.5f))*shadowDownsampleFactor-radiusX0_+zxOffset_*(thisChip_.z+1),
					(thisChip_.y-(mWL.camPosPlayer.getY()-screenHeight*0.5f))*shadowDownsampleFactor-radiusY0_+zyOffset_*(thisChip_.z+1),
					radiusX0_*2,radiusY0_*2,
					0,0,32,32,false,false);
		}
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
		// bet stack
		stack_=mWL.thisPlayer.betStack;
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,stack_.getOpacity());
		yStack_=(stack_.getY()-(mWL.camPosPlayer.getY()-screenHeight*0.5f))*shadowDownsampleFactor;
		xStack_=(stack_.getX()-(mWL.camPosPlayer.getX()-screenWidth*0.5f))*shadowDownsampleFactor;
		numStack_=stack_.renderSize();
		for (int N=0;N<numStack_;N++) {
			batch.draw(shadowTexture,
					xStack_-radiusX0_+zxOffset_*(N+1),
					yStack_-radiusY0_+zyOffset_*(N+1),
					radiusX0_*2,radiusY0_*2,
					0,0,32,32,false,false);
		}
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
		// cancelling stack
		stack_=mWL.thisPlayer.cancellingStack;
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,stack_.getOpacity());
		numStack_=stack_.size();
		int numA_=0;
		int numB_=0;
		int numC_=0;
		int maxA_=mWL.thisPlayer.mainStacks[ChipCase.CHIP_A].maxRenderNum;
		int maxB_=mWL.thisPlayer.mainStacks[ChipCase.CHIP_B].maxRenderNum;
		int maxC_=mWL.thisPlayer.mainStacks[ChipCase.CHIP_C].maxRenderNum;
		for (int N=0;N<numStack_;N++) {
			Chip thisChip_=stack_.get(N);
			int chipType_=thisChip_.chipType;
			boolean render_=true;
			if (chipType_==ChipCase.CHIP_A) {
				numA_++;
				if (numA_>maxA_)
					render_=false;
			} else if (chipType_==ChipCase.CHIP_B) {
				numB_++;
				if (numB_>maxB_)
					render_=false;
			} else if (chipType_==ChipCase.CHIP_C) {
				numC_++;
				if (numC_>maxC_)
					render_=false;
			}
			if (render_) {
				batch.draw(shadowTexture,
						(thisChip_.x-(mWL.camPosPlayer.getX()-screenWidth*0.5f))*shadowDownsampleFactor-radiusX0_+zxOffset_*(thisChip_.z+1),
						(thisChip_.y-(mWL.camPosPlayer.getY()-screenHeight*0.5f))*shadowDownsampleFactor-radiusY0_+zyOffset_*(thisChip_.z+1),
						radiusX0_*2,radiusY0_*2,
						0,0,32,32,false,false);
			}
		}
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
		// cancel stack
		numA_=0;
		numB_=0;
		numC_=0;
		stack_=mWL.thisPlayer.cancelStack;
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,stack_.getOpacity());
		numStack_=stack_.size();
		for (int N=0;N<numStack_;N++) {
			Chip thisChip_=stack_.get(N);
			int chipType_=thisChip_.chipType;
			boolean render_=true;
			if (chipType_==ChipCase.CHIP_A) {
				numA_++;
				if (numA_>maxA_)
					render_=false;
			} else if (chipType_==ChipCase.CHIP_B) {
				numB_++;
				if (numB_>maxB_)
					render_=false;
			} else if (chipType_==ChipCase.CHIP_C) {
				numC_++;
				if (numC_>maxC_)
					render_=false;
			}
			if (render_) {
				batch.draw(shadowTexture,
						(thisChip_.x-(mWL.camPosPlayer.getX()-screenWidth*0.5f))*shadowDownsampleFactor-radiusX0_+zxOffset_*(thisChip_.z+1),
						(thisChip_.y-(mWL.camPosPlayer.getY()-screenHeight*0.5f))*shadowDownsampleFactor-radiusY0_+zyOffset_*(thisChip_.z+1),
						radiusX0_*2,radiusY0_*2,
						0,0,32,32,false,false);
			}
		}
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
		
		batch.end();
		shadowsFBO.end();
		batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batch.setProjectionMatrix(camera.getViewMatrix());
	}
	
	public void renderShadows() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,0.23f);
		batch.draw(shadows,mWL.camPosPlayer.getX()-screenWidth*0.5f,mWL.camPosPlayer.getY()-screenHeight*0.5f,screenWidth,screenHeight);
		alphaShader=batch.getColor();
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
	}
	
	private void renderDealerButton() {
		alphaShader=batch.getColor();
		if (mWL.table.dealerButton.opacity!=0) {
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mWL.table.dealerButton.opacity);
			batch.draw(dealerButtonTexture,
					mWL.table.dealerButton.x-mWL.table.dealerButton.radiusX,
					mWL.table.dealerButton.y-mWL.table.dealerButton.radiusY,
					mWL.table.dealerButton.radiusX,mWL.table.dealerButton.radiusY,
					mWL.table.dealerButton.radiusX*2,mWL.table.dealerButton.radiusY*2,
					1,1,mWL.table.dealerButton.rotation,
					0,0,256,256,false,false);
		} else if (mWL.thisPlayer.dealerButton.opacity!=0) {
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mWL.thisPlayer.dealerButton.opacity);
			batch.draw(dealerButtonTexture,
					mWL.thisPlayer.dealerButton.x-mWL.thisPlayer.dealerButton.radiusX,
					mWL.thisPlayer.dealerButton.y-mWL.thisPlayer.dealerButton.radiusY,
					mWL.thisPlayer.dealerButton.radiusX,mWL.thisPlayer.dealerButton.radiusY,
					mWL.thisPlayer.dealerButton.radiusX*2,mWL.thisPlayer.dealerButton.radiusY*2,
					1,1,mWL.thisPlayer.dealerButton.rotation,
					0,0,256,256,false,false);
		}
		batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
	}

	
	private void renderWinStacks() {
		for (int seat=0;seat<Table.NUM_SEATS;seat++) {
			Player thisPlayer_=mWL.table.seats[seat].player;
			if (thisPlayer_!=null) {
				ChipStack stack_=thisPlayer_.winStack;
				float rotation_=stack_.getRotation();
				int xCoeff_=0;
				int yCoeff_=0;
				if (rotation_==0||rotation_==360) {
					yCoeff_=1;
				} else if (rotation_==-90||rotation_==270) {
					xCoeff_=1;
				} else if (rotation_==180||rotation_==-180) {
					yCoeff_=-1;
				} else if (rotation_==90||rotation_==-270) {
					xCoeff_=-1;
				}
				alphaShader=batch.getColor();
				batch.setColor(alphaShader.r,alphaShader.g,
		        		alphaShader.b,stack_.getOpacity());
				for (int i=0;i<stack_.renderSize();i++) {
					Chip chip_=stack_.get(i);
					float radiusXChip_=Chip.radiusX*(1+chip_.z*Chip.perspectiveGradient);
					float radiusYChip_=Chip.radiusY*(1+chip_.z*Chip.perspectiveGradient);
					float zyOffset_=Chip.Z_Y_OFFSET_RATIO*Chip.radiusY*chip_.z*
							(1+0.5f*Chip.perspectiveGradient*(chip_.z-1));
					batch.draw(chipTextures[chip_.chipType*Chip.CHIP_ROTATION_N+chip_.imgRotation],
							chip_.x-radiusXChip_+xCoeff_*zyOffset_,
							chip_.y-radiusYChip_+yCoeff_*zyOffset_,
							radiusXChip_,radiusYChip_,
							radiusXChip_*2,radiusYChip_*2,
							1,1,rotation_,
							0,0,256,chip_img_height,false,false);
				}
				alphaShader=batch.getColor();
				batch.setColor(alphaShader.r,alphaShader.g,
		        		alphaShader.b,1);
			}
		}
	}
	
	private void renderPot() {
		int chipIndex_=0;
		ChipStack stack_=mWL.table.pots.get(mWL.table.displayedPotIndex).potStack;
		float rotation_=stack_.getRotation();
		int xCoeff_=0;
		int yCoeff_=0;
		if (rotation_==0||rotation_==360) {
			yCoeff_=1;
		} else if (rotation_==-90||rotation_==270) {
			xCoeff_=1;
		} else if (rotation_==180||rotation_==-180) {
			yCoeff_=-1;
		} else if (rotation_==90||rotation_==-270) {
			xCoeff_=-1;
		}
		alphaShader=batch.getColor();
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,stack_.getOpacity());
		if (mWL.table.animationState==Table.ANIM_SPLITTING_POTS&&stack_.pastMax()) {
			// because chips stay in pot until they reach the seat hence win stacks won't be drawn if above max render num of pot
			int maxNum=stack_.maxRenderNum;
			int[] chipsRendered=new int[Table.NUM_SEATS];
			for (chipIndex_=0;chipIndex_<stack_.size();chipIndex_++) {
				Chip chip_=stack_.get(chipIndex_);
				float radiusXChip_=Chip.radiusX*(1+chip_.z*Chip.perspectiveGradient);
				float radiusYChip_=Chip.radiusY*(1+chip_.z*Chip.perspectiveGradient);
				float zyOffset_=Chip.Z_Y_OFFSET_RATIO*Chip.radiusY*chip_.z*
						(1+0.5f*Chip.perspectiveGradient*(chip_.z-1));
				if (chipsRendered[chip_.destSeat.position]<maxNum) {
					batch.draw(chipTextures[chip_.chipType*Chip.CHIP_ROTATION_N+chip_.imgRotation],
							chip_.x-radiusXChip_+xCoeff_*zyOffset_,
							chip_.y-radiusYChip_+yCoeff_*zyOffset_,
							radiusXChip_,radiusYChip_,
							radiusXChip_*2,radiusYChip_*2,
							1,1,rotation_,
							0,0,256,chip_img_height,false,false);
					chipsRendered[chip_.destSeat.position]++;
				}
			}
		} else {
			
			for (chipIndex_=0;chipIndex_<stack_.renderSize();chipIndex_++) {
				Chip chip_=stack_.get(chipIndex_);
				float radiusXChip_=Chip.radiusX*(1+chip_.z*Chip.perspectiveGradient);
				float radiusYChip_=Chip.radiusY*(1+chip_.z*Chip.perspectiveGradient);
				float zyOffset_=Chip.Z_Y_OFFSET_RATIO*Chip.radiusY*chip_.z*
						(1+0.5f*Chip.perspectiveGradient*(chip_.z-1));
				batch.draw(chipTextures[chip_.chipType*Chip.CHIP_ROTATION_N+chip_.imgRotation],
						chip_.x-radiusXChip_+xCoeff_*zyOffset_,
						chip_.y-radiusYChip_+yCoeff_*zyOffset_,
						radiusXChip_,radiusYChip_,
						radiusXChip_*2,radiusYChip_*2,
						1,1,rotation_,
						0,0,256,chip_img_height,false,false);
			}
			if (stack_.totalLabel.texture!=null&&stack_.totalLabel.opacity!=0) {
				/*
				batch.draw(stack_.totalLabel.texture,
						stack_.getTopX()-stack_.totalLabel.radiusX,
						stack_.getTopY()-stack_.totalLabel.radiusY,
						stack_.totalLabel.radiusX,stack_.totalLabel.radiusY,
						stack_.totalLabel.radiusX*2,stack_.totalLabel.radiusY*2,
						1,1,rotation_,
						0,0,stack_.totalLabel.radiusX*2,stack_.totalLabel.radiusY*2,false,false);
						*/
			}
		}
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
	}
	
	private void renderBetStacks() {
		for (int i=0;i<Table.NUM_SEATS;i++) {
			int seat=Seat.zOrder[i];
			if (mWL.table.seats[seat].player!=null) {
				ChipStack stack_=mWL.table.seats[seat].player.betStack;
				int maxRenNum=mWL.table.seats[seat].player.betStack.maxRenderNum;
				int numStack=stack_.renderSize();
				int numPooling=0;
				int chipIndex_=0;
				int topChip_=-1;
				for (int j=numStack-1;j>=0;j--) {
					if (!mWL.table.seats[seat].player.betStack.get(j).pooling) {
						topChip_=j;
						break;
					}
				}
				alphaShader=batch.getColor();
		       batch.setColor(alphaShader.r,alphaShader.g,
		        		alphaShader.b,stack_.getOpacity());
				for (;chipIndex_<numStack;chipIndex_++) {
					Chip chip_=stack_.get(chipIndex_);
					float radiusXChip_=Chip.radiusX*(1+chip_.z*Chip.perspectiveGradient);
					float radiusYChip_=Chip.radiusY*(1+chip_.z*Chip.perspectiveGradient);
					float zyOffset_=Chip.Z_Y_OFFSET_RATIO*Chip.radiusY*chip_.z*
							(1+0.5f*Chip.perspectiveGradient*(chip_.z-1));
					float rotation_=chip_.rotation;
					int xCoeff_=0;
					int yCoeff_=0;
					if (rotation_==0||rotation_==360) {
						yCoeff_=1;
					} else if (rotation_==-90||rotation_==270) {
						xCoeff_=1;
					} else if (rotation_==180||rotation_==-180) {
						yCoeff_=-1;
					} else if (rotation_==90||rotation_==-270) {
						xCoeff_=-1;
					}
					batch.draw(chipTextures[chip_.chipType*Chip.CHIP_ROTATION_N+chip_.imgRotation],
							chip_.x-radiusXChip_+xCoeff_*zyOffset_,
							chip_.y-radiusYChip_+yCoeff_*zyOffset_,
							radiusXChip_,radiusYChip_,
							radiusXChip_*2,radiusYChip_*2,
							1,1,rotation_,
							0,0,256,chip_img_height,false,false);
					if (chip_.pooling)
						numPooling++;
				}
				// make sure max render num of chips in the pool stack are drawn
				if (mWL.table.gameLogic.state==GameLogic.STATE_WAIT_POOL_STACKS) {
					for (;chipIndex_<stack_.size()&&numPooling<maxRenNum;chipIndex_++) {
						Chip chip_=stack_.get(chipIndex_);
						if (chip_.pooling) {
							float radiusXChip_=Chip.radiusX*(1+chip_.z*Chip.perspectiveGradient);
							float radiusYChip_=Chip.radiusY*(1+chip_.z*Chip.perspectiveGradient);
							float zyOffset_=Chip.Z_Y_OFFSET_RATIO*Chip.radiusY*chip_.z*
									(1+0.5f*Chip.perspectiveGradient*(chip_.z-1));
							batch.draw(chipTextures[chip_.chipType*Chip.CHIP_ROTATION_N+chip_.imgRotation],
									chip_.x-radiusXChip_,
									chip_.y-radiusYChip_+zyOffset_,
									radiusXChip_*2,radiusYChip_*2,
									0,0,256,chip_img_height,false,false);
							numPooling++;
						}
					}
				}
			}
		}
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
	}
	
	private void renderBettingStacks() {
		for (int seat=0;seat<Table.NUM_SEATS;seat++) {
			if (mWL.table.seats[seat].player!=null) {
				ChipStack stack_=mWL.table.seats[seat].player.bettingStack;
				float rotation_=stack_.getRotation();
				int xCoeff_=0;
				int yCoeff_=0;
				if (rotation_==0||rotation_==360) {
					yCoeff_=1;
				} else if (rotation_==-90||rotation_==270) {
					xCoeff_=1;
				} else if (rotation_==180||rotation_==-180) {
					yCoeff_=-1;
				} else if (rotation_==90||rotation_==-270) {
					xCoeff_=-1;
				}
				alphaShader=batch.getColor();
		        batch.setColor(alphaShader.r,alphaShader.g,
		        		alphaShader.b,stack_.getOpacity());
				for (int chipIndex=0;chipIndex<stack_.renderSize();chipIndex++) {
					Chip chip_=stack_.get(chipIndex);
					float radiusXChip_=Chip.radiusX*(1+chip_.z*Chip.perspectiveGradient);
					float radiusYChip_=Chip.radiusY*(1+chip_.z*Chip.perspectiveGradient);
					float zyOffset_=Chip.Z_Y_OFFSET_RATIO*Chip.radiusY*chip_.z*
							(1+0.5f*Chip.perspectiveGradient*(chip_.z-1));
					batch.draw(chipTextures[chip_.chipType*Chip.CHIP_ROTATION_N+chip_.imgRotation],
							chip_.x-radiusXChip_+xCoeff_*zyOffset_,
							chip_.y-radiusYChip_+yCoeff_*zyOffset_,
							radiusXChip_,radiusYChip_,
							radiusXChip_*2,radiusYChip_*2,
							1,1,rotation_,
							0,0,256,chip_img_height,false,false);
				}
			}
		}
		alphaShader=batch.getColor();
       batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
	}
	

}
