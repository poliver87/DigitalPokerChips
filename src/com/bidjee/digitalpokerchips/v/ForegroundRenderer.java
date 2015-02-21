package com.bidjee.digitalpokerchips.v;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.bidjee.digitalpokerchips.c.DPCGame;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;
import com.bidjee.digitalpokerchips.c.PlayerMenuPanel;
import com.bidjee.digitalpokerchips.c.Table;
import com.bidjee.digitalpokerchips.m.ChipCase;
import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.Player;
import com.bidjee.digitalpokerchips.m.TextLabel;

public class ForegroundRenderer {
	
	public static final Color whiteColor=new Color(1,1,1,1);
	public static final Color blackColor=new Color(0,0,0,1);
	public static final Color goldColor=new Color(0.6f,0.57f,0,1);
	public static final Color greyColor=new Color(0.7f,0.7f,0.7f,1);
	public static final Color darkBlueColor=new Color(0.0f,0.0f,0.5f,1);
	
	AssetManager textureManager;
	
	SpriteBatch batch;
	final Matrix4 viewMatrix=new Matrix4();
	Color alphaShader;
	
	ForegroundLayer mFL;

	int screenWidth;
	int screenHeight;
	
	Texture plantTexture;
	Texture lampTexture;
	
	Texture[] logoDPCTextures;
	Texture helpButtonTexture;
	Texture settingsButtonTexture;
	Texture shopButtonTexture;
	
	Texture hostButtonTexture;
	Texture joinButtonTexture;
	
	Texture buttonLoginFacebookTexture;
	
	TextureRegion textFieldRegion;
	TextureRegion[] textFieldRegions;
	
	Texture cursorTexture;
	
	Texture envelopeTexture;
	TextureRegion buttonPlusRegion;
	TextureRegion buttonMinusRegion;
	
	Texture playerDashboardTexture;
	Texture dashboardIdTexture;
	Texture dashboardStatusTexture;
	Texture backButtonTexture;
	Texture bellButtonTexture;
	
	Texture okButtonTexture;
	Texture cancelButtonTexture;
	
	Texture foldButtonTexture;
	
	Texture closeButtonTexture;
	
	TextureRegion[] blackDialogRegions=new TextureRegion[9];
	
	Texture arrowPotTexture;
	
	Texture splitButtonTexture;
	
	Texture dialogWArrowTexture;
	
	Texture profilePicTexture;
	
	Texture gamePanelTexture;
	
	TextureRegion chipRegions[];
	
	Texture anonTexture;
	TextureRegion btnDialog;
	TextureRegion btnDialogLeft;
	TextureRegion btnDialogRight;
	
	TextureRegion case1BackRegion;
	TextureRegion standardCaseRegion;
	TextureRegion case2BackRegion;
	TextureRegion goldLineRegion;
	
	TextureRegion hostExitRegion;
	TextureRegion hostUndoRegion;
	
	public ForegroundRenderer(ForegroundLayer mFL) {
		this.mFL=mFL;
		batch=new SpriteBatch(200);
		logoDPCTextures=new Texture[10];
		chipRegions=new TextureRegion[ChipCase.CHIP_TYPES];
	}
	
	public void resize(int width,int height) {
		Gdx.app.log("DPCLifecycle", "ForegroundRenderer - resize("+width+","+height+")");
		viewMatrix.setToOrtho2D(0,0,width,height);
		batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batch.setProjectionMatrix(viewMatrix);
		screenWidth=width;
		screenHeight=height;
	}
	
	public void dispose() {
		Gdx.app.log("DPCLifecycle", "ForegroundRenderer - dispose()");
		batch.dispose();
		mFL=null;
	}
	
	public void loadTextures(AssetManager manager) {
		Gdx.app.log("DPCLifecycle", "ForegroundRenderer - loadTextures()");
		this.textureManager=manager;
		plantTexture=manager.get("plant.png",Texture.class);
		lampTexture=manager.get("lamp.png",Texture.class);
		logoDPCTextures[0]=manager.get("dpc_logo_01.png",Texture.class);
		logoDPCTextures[1]=manager.get("dpc_logo_02.png",Texture.class);
		logoDPCTextures[2]=manager.get("dpc_logo_03.png",Texture.class);
		logoDPCTextures[3]=manager.get("dpc_logo_04.png",Texture.class);
		logoDPCTextures[4]=manager.get("dpc_logo_05.png",Texture.class);
		logoDPCTextures[5]=manager.get("dpc_logo_06.png",Texture.class);
		logoDPCTextures[6]=manager.get("dpc_logo_07.png",Texture.class);
		logoDPCTextures[7]=manager.get("dpc_logo_08.png",Texture.class);
		logoDPCTextures[8]=manager.get("dpc_logo_09.png",Texture.class);
		logoDPCTextures[9]=manager.get("dpc_logo_10.png",Texture.class);
		shopButtonTexture=manager.get("home_shop.png",Texture.class);
		settingsButtonTexture=manager.get("home_settings.png",Texture.class);
		helpButtonTexture=manager.get("home_help.png",Texture.class);
		hostButtonTexture=manager.get("host_button.png",Texture.class);
		joinButtonTexture=manager.get("join_button.png",Texture.class);
		
		textFieldRegion=new TextureRegion(manager.get("text_field.png",Texture.class),469,70);
		textFieldRegions=new TextureRegion[3];
		textFieldRegions[0]=new TextureRegion(manager.get("text_field.png",Texture.class),0,0,33,70);
		textFieldRegions[1]=new TextureRegion(manager.get("text_field.png",Texture.class),200,0,33,70);
		textFieldRegions[2]=new TextureRegion(manager.get("text_field.png",Texture.class),436,0,33,70);
		
		cursorTexture=manager.get("cursor.png",Texture.class);
		
		envelopeTexture=manager.get("envelope.png",Texture.class);
		buttonPlusRegion=new TextureRegion(manager.get("btn_plus.png",Texture.class),47,49);
		buttonMinusRegion=new TextureRegion(manager.get("btn_minus.png",Texture.class),47,49);
		
		playerDashboardTexture=manager.get("dashboard.png",Texture.class);
		dashboardIdTexture=manager.get("dashboard_id.png",Texture.class);
		dashboardStatusTexture=manager.get("dashboard_status.png",Texture.class);
		backButtonTexture=manager.get("btn_back.png",Texture.class);
		bellButtonTexture=manager.get("btn_bell.png",Texture.class);
		gamePanelTexture=manager.get("game_panel.png",Texture.class);
		
		buttonLoginFacebookTexture=manager.get("btn_fb.png",Texture.class);

		okButtonTexture=manager.get("ok_button.png",Texture.class);
		cancelButtonTexture=manager.get("cancel_button.png",Texture.class);
		
		foldButtonTexture=manager.get("fold_button.png",Texture.class);
		
		closeButtonTexture=manager.get("btn_close.png",Texture.class);
		
		blackDialogRegions[0]=new TextureRegion(manager.get("dialog_tl.png",Texture.class),20,20);
		blackDialogRegions[1]=new TextureRegion(manager.get("dialog_t.png",Texture.class),20,20);
		blackDialogRegions[2]=new TextureRegion(manager.get("dialog_tr.png",Texture.class),20,20);
		blackDialogRegions[3]=new TextureRegion(manager.get("dialog_l.png",Texture.class),20,200);
		blackDialogRegions[4]=new TextureRegion(manager.get("dialog_m.png",Texture.class),20,20);
		blackDialogRegions[5]=new TextureRegion(manager.get("dialog_r.png",Texture.class),20,200);
		blackDialogRegions[6]=new TextureRegion(manager.get("dialog_bl.png",Texture.class),20,20);
		blackDialogRegions[7]=new TextureRegion(manager.get("dialog_b.png",Texture.class),20,20);
		blackDialogRegions[8]=new TextureRegion(manager.get("dialog_br.png",Texture.class),20,20);
		
		arrowPotTexture=manager.get("arrow_pot.png",Texture.class);
		splitButtonTexture=manager.get("split_button.png",Texture.class);
		
		dialogWArrowTexture=manager.get("dialog_w_arrow.png",Texture.class);
		
		profilePicTexture=manager.get("anon.jpeg",Texture.class);
		
		chipRegions[0]=new TextureRegion(manager.get("chip_0_0.png",Texture.class),256,252);
		chipRegions[1]=new TextureRegion(manager.get("chip_1_0.png",Texture.class),256,252);
		chipRegions[2]=new TextureRegion(manager.get("chip_2_0.png",Texture.class),256,252);
		
		anonTexture = manager.get("anon_circle.png",Texture.class);
		btnDialog=new TextureRegion(manager.get("btn_dialog.png",Texture.class),200,46);
		btnDialogLeft=new TextureRegion(manager.get("btn_dialog.png",Texture.class),277,46);
		btnDialogRight=new TextureRegion(manager.get("btn_dialog.png",Texture.class),277,0,-277,46);
		
		case1BackRegion=new TextureRegion(manager.get("case1back.png",Texture.class),215,179);
		standardCaseRegion=new TextureRegion(manager.get("chips2.png",Texture.class),400,422);
		case2BackRegion=new TextureRegion(manager.get("case2back.png",Texture.class),215,179);
		goldLineRegion=new TextureRegion(manager.get("gold_line.png",Texture.class),2,2);
		
		hostExitRegion=new TextureRegion(manager.get("host_exit.png",Texture.class),81,81);
		hostUndoRegion=new TextureRegion(manager.get("host_undo.png",Texture.class),81,81);
	}
	
	public float yViewToWorld(float yView_) {return screenHeight-yView_;}
	
	public void render() {
		
		
		alphaShader=batch.getColor();
		batch.begin();
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.wifiButton.opacity);
		
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
        
        //if (mFL.helpButton.opacity!=0) {
        //	batch.draw(helpTexture,
    	//			mFL.helpButton.x-mFL.helpButton.radiusX,
    	//			mFL.helpButton.y-mFL.helpButton.radiusY,
    	//			mFL.helpButton.radiusX*2,mFL.helpButton.radiusY*2,
    	//			0,0,256,256,false,false);
        //}
        
        renderPlayerNames();
        
        renderHome();
        

		renderFoldButton();
        
		renderHostButtons();
		
		if (mFL.dialogWindow.opacity!=0) {
			alphaShader=batch.getColor();
            batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,mFL.dialogWindow.opacity);
			renderRectangleRounded(blackDialogRegions,(int)mFL.dialogWindow.x,(int)mFL.dialogWindow.y,
					mFL.dialogWindow.radiusX,mFL.dialogWindow.radiusY);
			alphaShader=batch.getColor();
            batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		
		if (mFL.playerDashboard!=null) {
			renderPlayerDashboard();
		}
		
		if (mFL.betTotalDialog!=null) {
			renderBetTotalDialog();
		}
		
		if (mFL.gameMenu!=null) {
			renderGameMenu();
		}
		
		renderStackValueLabels();
		
		if (mFL.dialogWArrowWindow.opacity!=0) {
			renderDialogWArrow();
		}
		
		if (mFL.helpDialog.titleLabel.opacity!=0) {
			renderHelpDialog();
		}
		
		
		if (mFL.playerLoginDialog.enterNameLabel.opacity!=0) {
			renderPlayerLoginDialog();
		}
		
		if (mFL.hostNameDialog.enterNameLabel.opacity!=0) {
			renderHostNameDialog();
		}
		
		if (mFL.hostChipCaseDialog.selectLabel.opacity!=0) {
			renderHostChipCaseDialog();
		}
		
		if (mFL.hostChipSetupDialog.chipCaseSprite.opacity!=0) {
			renderHostChipSetupDialog();
		}
		
		if (mFL.hostLobbyDialog.nameLabel.opacity!=0) {
			renderHostLobbyDialog();
		}
		
		if (mFL.hostRearrangeDialog.nameLabel.opacity!=0) {
			renderHostRearrangeDialog();
		}
		
		if (mFL.playerBuyinDialog.envelope.opacity!=0) {
			renderBuyinDialog();
		}
		
		if (mFL.bootDialog.bootButton.opacity!=0) {
			renderBootDialog();
		}
		
		if (mFL.playerLeaveDialog.text1Label.opacity!=0) {
			renderPlayerLeaveDialog();
		}
		
		if (mFL.destroyTableDialog.titleLabel.opacity!=0) {
			renderDestroyTableDialog();
		}
		
		if (mFL.divisibilityDialog.opacity!=0) {
			renderDivisibilityDialog();
		}
		
		renderPotArrows();
		renderSplitUI();
		
		//renderHomeTutorial();
		
		if (DPCGame.debugMode) {
			if (mFL.azimuthLabel.texture!=null) {
				batch.draw(mFL.azimuthLabel.texture,
						mFL.azimuthLabel.x-mFL.azimuthLabel.radiusX,
						mFL.azimuthLabel.y-mFL.azimuthLabel.radiusY,
						mFL.azimuthLabel.radiusX*2,mFL.azimuthLabel.radiusY*2,
						0,0,mFL.azimuthLabel.radiusX*2,mFL.azimuthLabel.radiusY*2,false,false);
			}
		}
		
		batch.end();
	}
	

	private void renderFoldButton() {
		// draw fold button
		if (mFL.foldButton.opacity>0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.foldButton.opacity);
			batch.draw(foldButtonTexture,
					mFL.foldButton.x-mFL.foldButton.radiusX,
					mFL.foldButton.y-mFL.foldButton.radiusY,
					mFL.foldButton.radiusX*2,mFL.foldButton.radiusY*2,
					0,0,256,256, false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
	}
	
	private void renderHostButtons() {
		if (mFL.hostHelpButton.opacity>0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.hostHelpButton.opacity);
			batch.draw(helpButtonTexture,
					mFL.hostHelpButton.x-mFL.hostHelpButton.radiusX,
					mFL.hostHelpButton.y-mFL.hostHelpButton.radiusY,
					mFL.hostHelpButton.radiusX*2,mFL.hostHelpButton.radiusY*2,
					0,0,81,81, false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.hostUndoButton.opacity>0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.hostUndoButton.opacity);
			batch.draw(hostUndoRegion,
					mFL.hostUndoButton.x-mFL.hostUndoButton.radiusX,
					mFL.hostUndoButton.y-mFL.hostUndoButton.radiusY,
					mFL.hostUndoButton.radiusX*2,mFL.hostUndoButton.radiusY*2);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.hostExitButton.opacity>0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.hostExitButton.opacity);
			batch.draw(hostExitRegion,
					mFL.hostExitButton.x-mFL.hostExitButton.radiusX,
					mFL.hostExitButton.y-mFL.hostExitButton.radiusY,
					mFL.hostExitButton.radiusX*2,mFL.hostExitButton.radiusY*2);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
	}
	
	private void renderPlayerNames() {
		Table table = mFL.game.mWL.table;
		Player thisPlayer;
		Camera camera = mFL.game.mWL.worldRenderer.camera;
		for (int i=0;i<Table.NUM_SEATS+1;i++) {
			if (i<Table.NUM_SEATS) {
				thisPlayer=table.seats[i].player;
			} else {
				thisPlayer=table.pickedUpPlayer;
			}
			if (thisPlayer!=null&&thisPlayer.name.opacity!=0) {
				alphaShader=batch.getColor();
				batch.setColor(alphaShader.r,alphaShader.g,
						alphaShader.b,thisPlayer.name.opacity);
				
				int x = (int) ((thisPlayer.name.x-camera.x)*camera.zoom+screenWidth*0.5f);
				int y = (int) (screenHeight*0.5f-(camera.y-thisPlayer.name.y)*camera.zoom);
				batch.draw(thisPlayer.name.texture,x-thisPlayer.name.radiusX,
						y-thisPlayer.name.radiusY,
						thisPlayer.name.radiusX,thisPlayer.name.radiusY,
						thisPlayer.name.radiusX*2,thisPlayer.name.radiusY*2,
						1,1,thisPlayer.getRotation(),
						0,0,thisPlayer.name.radiusX*2,thisPlayer.name.radiusY*2,false,false);
				
				batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
			}
		}
	}

	private void renderHome() {
		int x=0;
		int y=0;
		if (mFL.homeForegroundAnimation.treeSprite.opacity!=0) {
			x=(int) (mFL.homeForegroundAnimation.treeSprite.x-mFL.homeForegroundAnimation.treeSprite.radiusX);
			y=(int) (mFL.homeForegroundAnimation.treeSprite.y-mFL.homeForegroundAnimation.treeSprite.radiusY);
			batch.draw(plantTexture,x,y,
					mFL.homeForegroundAnimation.treeSprite.radiusX*2,mFL.homeForegroundAnimation.treeSprite.radiusY*2,
					0,0,220,364,false,false);
		}
		if (mFL.homeForegroundAnimation.lightLeftSprite.opacity!=0) {
			x=(int) (mFL.homeForegroundAnimation.lightLeftSprite.x-mFL.homeForegroundAnimation.lightLeftSprite.radiusX);
			y=(int) (mFL.homeForegroundAnimation.lightLeftSprite.y-mFL.homeForegroundAnimation.lightLeftSprite.radiusY);
			batch.draw(lampTexture,x,y,
					mFL.homeForegroundAnimation.lightLeftSprite.radiusX*2,mFL.homeForegroundAnimation.lightLeftSprite.radiusY*2,
					0,0,400,360,false,false);
		}
		if (mFL.homeForegroundAnimation.lightRightSprite.opacity!=0) {
			x=(int) (mFL.homeForegroundAnimation.lightRightSprite.x-mFL.homeForegroundAnimation.lightRightSprite.radiusX);
			y=(int) (mFL.homeForegroundAnimation.lightRightSprite.y-mFL.homeForegroundAnimation.lightRightSprite.radiusY);
			batch.draw(lampTexture,x,y,
					mFL.homeForegroundAnimation.lightRightSprite.radiusX*2,mFL.homeForegroundAnimation.lightRightSprite.radiusY*2,
					0,0,400,360,false,false);
		}
		if (mFL.homeUIAnimation.helpButton.opacity!=0) {
			x=(int) (mFL.homeUIAnimation.helpButton.x-mFL.homeUIAnimation.helpButton.radiusX);
			y=(int) (mFL.homeUIAnimation.helpButton.y-mFL.homeUIAnimation.helpButton.radiusY);
			batch.draw(helpButtonTexture,x,y,
					mFL.homeUIAnimation.helpButton.radiusX*2,mFL.homeUIAnimation.helpButton.radiusY*2,
					0,0,81,81,false,false);
		}
		if (mFL.homeUIAnimation.shopSprite.opacity!=0) {
			x=(int) (mFL.homeUIAnimation.shopSprite.x-mFL.homeUIAnimation.shopSprite.radiusX);
			y=(int) (mFL.homeUIAnimation.shopSprite.y-mFL.homeUIAnimation.shopSprite.radiusY);
			batch.draw(shopButtonTexture,x,y,
					mFL.homeUIAnimation.shopSprite.radiusX*2,mFL.homeUIAnimation.shopSprite.radiusY*2,
					0,0,81,80,false,false);
		}
		if (mFL.homeUIAnimation.settingsSprite.opacity!=0) {
			x=(int) (mFL.homeUIAnimation.settingsSprite.x-mFL.homeUIAnimation.settingsSprite.radiusX);
			y=(int) (mFL.homeUIAnimation.settingsSprite.y-mFL.homeUIAnimation.settingsSprite.radiusY);
			batch.draw(settingsButtonTexture,x,y,
					mFL.homeUIAnimation.settingsSprite.radiusX*2,mFL.homeUIAnimation.settingsSprite.radiusY*2,
					0,0,81,81,false,false);
		}
		if (mFL.homeUIAnimation.logoSprite.opacity!=0) {
			x=(int) (mFL.homeUIAnimation.logoSprite.x-mFL.homeUIAnimation.logoSprite.radiusX);
			y=(int) (mFL.homeUIAnimation.logoSprite.y-mFL.homeUIAnimation.logoSprite.radiusY);
			batch.draw(logoDPCTextures[mFL.homeUIAnimation.logoSprite.frame],x,y,
					mFL.homeUIAnimation.logoSprite.radiusX*2,mFL.homeUIAnimation.logoSprite.radiusY*2,
					0,0,615,631,false,false);
		}
		if (mFL.homeUIAnimation.hostButton.opacity!=0) {
			x=(int) (mFL.homeUIAnimation.hostButton.x-mFL.homeUIAnimation.hostButton.radiusX);
			y=(int) (mFL.homeUIAnimation.hostButton.y-mFL.homeUIAnimation.hostButton.radiusY);
			batch.draw(hostButtonTexture,x,y,
					mFL.homeUIAnimation.hostButton.radiusX*2,mFL.homeUIAnimation.hostButton.radiusY*2,
					0,0,250,136,false,false);
			/*
			if (mFL.homeUIAnimation.hostButton.getLabel().texture==null) {
				mFL.homeUIAnimation.hostButton.getLabel().loadTexture(whiteColor,darkBlueColor);
			}
			x=(int) (mFL.homeUIAnimation.hostButton.getLabel().x-mFL.homeUIAnimation.hostButton.getLabel().radiusX);
			y=(int) (mFL.homeUIAnimation.hostButton.getLabel().y-mFL.homeUIAnimation.hostButton.getLabel().radiusY);
			batch.draw(mFL.homeUIAnimation.hostButton.getLabel().texture,x,y,
					0,0,mFL.homeUIAnimation.hostButton.getLabel().radiusX*2,
					mFL.homeUIAnimation.hostButton.getLabel().radiusY*2);
					*/
		}
		if (mFL.homeUIAnimation.joinButton.opacity!=0) {
			x=(int) (mFL.homeUIAnimation.joinButton.x-mFL.homeUIAnimation.joinButton.radiusX);
			y=(int) (mFL.homeUIAnimation.joinButton.y-mFL.homeUIAnimation.joinButton.radiusY);
			batch.draw(joinButtonTexture,x,y,
					mFL.homeUIAnimation.joinButton.radiusX*2,mFL.homeUIAnimation.joinButton.radiusY*2,
					0,0,250,136,false,false);
			/*
			if (mFL.homeUIAnimation.joinButton.getLabel().texture==null) {
				mFL.homeUIAnimation.joinButton.getLabel().loadTexture(whiteColor,darkBlueColor);
			}
			x=(int) (mFL.homeUIAnimation.joinButton.getLabel().x-mFL.homeUIAnimation.joinButton.getLabel().radiusX);
			y=(int) (mFL.homeUIAnimation.joinButton.getLabel().y-mFL.homeUIAnimation.joinButton.getLabel().radiusY);
			batch.draw(mFL.homeUIAnimation.joinButton.getLabel().texture,x,y,
					0,0,mFL.homeUIAnimation.joinButton.getLabel().radiusX*2,
					mFL.homeUIAnimation.joinButton.getLabel().radiusY*2);
					*/
		}
	}
	
	private void renderDialogWArrow() {
		alphaShader=batch.getColor();
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.dialogWArrowWindow.opacity);
		batch.draw(dialogWArrowTexture,
				mFL.dialogWArrowWindow.x-mFL.dialogWArrowWindow.radiusX,
				mFL.dialogWArrowWindow.y-mFL.dialogWArrowWindow.radiusY,
				mFL.dialogWArrowWindow.radiusX,mFL.dialogWArrowWindow.radiusY,
				mFL.dialogWArrowWindow.radiusX*2,mFL.dialogWArrowWindow.radiusY*2,
				1,1,mFL.dialogWArrowWindow.rotation,
				0,0,550,260,false,false);
		batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
	}
	
	private void renderHelpDialog() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.playerLoginDialog.enterNameLabel.opacity);
        
        if (mFL.helpDialog.titleLabel.texture==null) {
        	mFL.helpDialog.titleLabel.loadTexture();
        }
		int x=(int) (mFL.helpDialog.titleLabel.x-mFL.helpDialog.titleLabel.radiusX);
		int y=(int) (mFL.helpDialog.titleLabel.y-mFL.helpDialog.titleLabel.radiusY);
		batch.draw(mFL.helpDialog.titleLabel.texture,x,y,
				0,0,mFL.helpDialog.titleLabel.radiusX*2,
				mFL.helpDialog.titleLabel.radiusY*2);
		
		batch.setColor(alphaShader);
	}
	
	private void renderPlayerLoginDialog() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.playerLoginDialog.enterNameLabel.opacity);

        int x = (int) (mFL.playerLoginDialog.x);
        int y = (int) (mFL.playerLoginDialog.y);
        renderRectangleRounded(blackDialogRegions,x,y,
				mFL.playerLoginDialog.radiusX,mFL.playerLoginDialog.radiusY);
        batch.draw(anonTexture,
				mFL.playerLoginDialog.anonSprite.x-mFL.playerLoginDialog.anonSprite.radiusX,
				mFL.playerLoginDialog.anonSprite.y-mFL.playerLoginDialog.anonSprite.radiusY,
				mFL.playerLoginDialog.anonSprite.radiusX*2,mFL.playerLoginDialog.anonSprite.radiusY*2,
				0,0,70,71,false,false);
        // title and text field left anchored
        if (mFL.playerLoginDialog.enterNameLabel.texture==null) {
        	mFL.playerLoginDialog.enterNameLabel.loadTexture();
        }
		x=(int) (mFL.playerLoginDialog.enterNameLabel.x-mFL.playerLoginDialog.enterNameLabel.radiusX);
		y=(int) (mFL.playerLoginDialog.enterNameLabel.y-mFL.playerLoginDialog.enterNameLabel.radiusY);
		batch.draw(mFL.playerLoginDialog.enterNameLabel.texture,x,y,
				0,0,mFL.playerLoginDialog.enterNameLabel.radiusX*2,
				mFL.playerLoginDialog.enterNameLabel.radiusY*2);
		
		x=(int) (mFL.playerLoginDialog.nameField.x-mFL.playerLoginDialog.nameField.radiusX);
		y=(int) (mFL.playerLoginDialog.nameField.y-mFL.playerLoginDialog.nameField.radiusY);
		batch.draw(textFieldRegion,x,y,
				mFL.playerLoginDialog.nameField.radiusX*2,
				mFL.playerLoginDialog.nameField.radiusY*2);
		if (mFL.playerLoginDialog.nameField.label.texture!=null) {
			x=(int) (mFL.playerLoginDialog.nameField.label.x-mFL.playerLoginDialog.nameField.label.radiusX);
			y=(int) (mFL.playerLoginDialog.nameField.label.y-mFL.playerLoginDialog.nameField.label.radiusY);
			batch.draw(mFL.playerLoginDialog.nameField.label.texture,x,y,
					0,0,mFL.playerLoginDialog.nameField.label.radiusX*2,
					mFL.playerLoginDialog.nameField.label.radiusY*2);
		}
		if (mFL.playerLoginDialog.nameField.cursor.opacity!=0) {
			batch.setColor(alphaShader.r,alphaShader.g,
		        		alphaShader.b,mFL.playerLoginDialog.nameField.cursor.opacity);
			x=(int) (mFL.playerLoginDialog.nameField.cursor.x-mFL.playerLoginDialog.nameField.cursor.radiusX);
			y=(int) (mFL.playerLoginDialog.nameField.cursor.y-mFL.playerLoginDialog.nameField.cursor.radiusY);
			batch.draw(cursorTexture,x,y,
					mFL.playerLoginDialog.nameField.cursor.radiusX*2,
					mFL.playerLoginDialog.nameField.cursor.radiusY*2,
					0,0,8,64,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.playerLoginDialog.enterNameLabel.opacity);
		}
		
		
		if (mFL.playerLoginDialog.orLabel.texture==null) {
        	mFL.playerLoginDialog.orLabel.loadTexture();
        }
		x=(int) (mFL.playerLoginDialog.orLabel.x-mFL.playerLoginDialog.orLabel.radiusX);
		y=(int) (mFL.playerLoginDialog.orLabel.y-mFL.playerLoginDialog.orLabel.radiusY);
		batch.draw(mFL.playerLoginDialog.orLabel.texture,x,y,
				0,0,mFL.playerLoginDialog.orLabel.radiusX*2,
				mFL.playerLoginDialog.orLabel.radiusY*2);
		
		x=(int) (mFL.playerLoginDialog.facebookButton.x-mFL.playerLoginDialog.facebookButton.radiusX);
		y=(int) (mFL.playerLoginDialog.facebookButton.y-mFL.playerLoginDialog.facebookButton.radiusY);
		batch.draw(buttonLoginFacebookTexture,x,y,
				mFL.playerLoginDialog.facebookButton.radiusX*2,
				mFL.playerLoginDialog.facebookButton.radiusY*2,
				0,0,430,49,false,false);
		if (mFL.playerLoginDialog.facebookButton.getLabel().texture==null) {
        	mFL.playerLoginDialog.facebookButton.getLabel().loadTexture(whiteColor,new Color(0,0,0.4f,1));
        }
		x=(int) (mFL.playerLoginDialog.facebookButton.getLabel().x-mFL.playerLoginDialog.facebookButton.getLabel().radiusX);
		y=(int) (mFL.playerLoginDialog.facebookButton.getLabel().y-mFL.playerLoginDialog.facebookButton.getLabel().radiusY);
		batch.draw(mFL.playerLoginDialog.facebookButton.getLabel().texture,x,y,
				0,0,mFL.playerLoginDialog.facebookButton.getLabel().radiusX*2,
				mFL.playerLoginDialog.facebookButton.getLabel().radiusY*2);
		
		x=(int) (mFL.playerLoginDialog.backButton.x-mFL.playerLoginDialog.backButton.radiusX);
		y=(int) (mFL.playerLoginDialog.backButton.y-mFL.playerLoginDialog.backButton.radiusY);
		batch.draw(btnDialogLeft,x,y,
				mFL.playerLoginDialog.backButton.radiusX*2,
				mFL.playerLoginDialog.backButton.radiusY*2);
		if (mFL.playerLoginDialog.backButton.getLabel().texture==null) {
        	mFL.playerLoginDialog.backButton.getLabel().loadTexture();
        }
		x=(int) (mFL.playerLoginDialog.backButton.getLabel().x-mFL.playerLoginDialog.backButton.getLabel().radiusX);
		y=(int) (mFL.playerLoginDialog.backButton.getLabel().y-mFL.playerLoginDialog.backButton.getLabel().radiusY);
		batch.draw(mFL.playerLoginDialog.backButton.getLabel().texture,x,y,
				0,0,mFL.playerLoginDialog.backButton.getLabel().radiusX*2,
				mFL.playerLoginDialog.backButton.getLabel().radiusY*2);
		
		
		x=(int) (mFL.playerLoginDialog.okButton.x-mFL.playerLoginDialog.okButton.radiusX);
		y=(int) (mFL.playerLoginDialog.okButton.y-mFL.playerLoginDialog.okButton.radiusY);
		batch.draw(btnDialogRight,x,y,
				mFL.playerLoginDialog.okButton.radiusX*2,
				mFL.playerLoginDialog.okButton.radiusY*2);
		if (mFL.playerLoginDialog.okButton.getLabel().texture==null) {
        	mFL.playerLoginDialog.okButton.getLabel().loadTexture();
        }
		x=(int) (mFL.playerLoginDialog.okButton.getLabel().x-mFL.playerLoginDialog.okButton.getLabel().radiusX);
		y=(int) (mFL.playerLoginDialog.okButton.getLabel().y-mFL.playerLoginDialog.okButton.getLabel().radiusY);
		batch.draw(mFL.playerLoginDialog.okButton.getLabel().texture,x,y,
				0,0,mFL.playerLoginDialog.okButton.getLabel().radiusX*2,
				mFL.playerLoginDialog.okButton.getLabel().radiusY*2);
		
		batch.setColor(alphaShader);

	}
	
	private void renderHostNameDialog() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.hostNameDialog.enterNameLabel.opacity);
        
        int x = (int) (mFL.hostNameDialog.x);
        int y = (int) (mFL.hostNameDialog.y);
        renderRectangleRounded(blackDialogRegions,x,y,
				mFL.hostNameDialog.radiusX,mFL.hostNameDialog.radiusY);
        
        batch.draw(anonTexture,
				mFL.hostNameDialog.tableSprite.x-mFL.hostNameDialog.tableSprite.radiusX,
				mFL.hostNameDialog.tableSprite.y-mFL.hostNameDialog.tableSprite.radiusY,
				mFL.hostNameDialog.tableSprite.radiusX*2,mFL.hostNameDialog.tableSprite.radiusY*2,
				0,0,70,71,false,false);
        
        if (mFL.hostNameDialog.enterNameLabel.texture==null) {
        	mFL.hostNameDialog.enterNameLabel.loadTexture();
        }
		x=(int) (mFL.hostNameDialog.enterNameLabel.x-mFL.hostNameDialog.enterNameLabel.radiusX);
		y=(int) (mFL.hostNameDialog.enterNameLabel.y-mFL.hostNameDialog.enterNameLabel.radiusY);
		batch.draw(mFL.hostNameDialog.enterNameLabel.texture,x,y,
				0,0,mFL.hostNameDialog.enterNameLabel.radiusX*2,
				mFL.hostNameDialog.enterNameLabel.radiusY*2);
		
		x=(int) (mFL.hostNameDialog.nameField.x-mFL.hostNameDialog.nameField.radiusX);
		y=(int) (mFL.hostNameDialog.nameField.y-mFL.hostNameDialog.nameField.radiusY);
		batch.draw(textFieldRegion,x,y,
				mFL.hostNameDialog.nameField.radiusX*2,
				mFL.hostNameDialog.nameField.radiusY*2);
		if (mFL.hostNameDialog.nameField.label.texture!=null) {
			x=(int) (mFL.hostNameDialog.nameField.label.x-mFL.hostNameDialog.nameField.label.radiusX);
			y=(int) (mFL.hostNameDialog.nameField.label.y-mFL.hostNameDialog.nameField.label.radiusY);
			batch.draw(mFL.hostNameDialog.nameField.label.texture,x,y,
					0,0,mFL.hostNameDialog.nameField.label.radiusX*2,
					mFL.hostNameDialog.nameField.label.radiusY*2);
		}
		if (mFL.hostNameDialog.nameField.cursor.opacity!=0) {
			batch.setColor(alphaShader.r,alphaShader.g,
		        		alphaShader.b,mFL.hostNameDialog.nameField.cursor.opacity);
			x=(int) (mFL.hostNameDialog.nameField.cursor.x-mFL.hostNameDialog.nameField.cursor.radiusX);
			y=(int) (mFL.hostNameDialog.nameField.cursor.y-mFL.hostNameDialog.nameField.cursor.radiusY);
			batch.draw(cursorTexture,x,y,
					mFL.hostNameDialog.nameField.cursor.radiusX*2,
					mFL.hostNameDialog.nameField.cursor.radiusY*2,
					0,0,8,64,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.hostNameDialog.enterNameLabel.opacity);
		}
		
		if (mFL.hostNameDialog.infoLabel.texture==null) {
        	mFL.hostNameDialog.infoLabel.loadTexture();
        }
		x=(int) (mFL.hostNameDialog.infoLabel.x-mFL.hostNameDialog.infoLabel.radiusX);
		y=(int) (mFL.hostNameDialog.infoLabel.y-mFL.hostNameDialog.infoLabel.radiusY);
		batch.draw(mFL.hostNameDialog.infoLabel.texture,x,y,
				0,0,mFL.hostNameDialog.infoLabel.radiusX*2,
				mFL.hostNameDialog.infoLabel.radiusY*2);
		
		x=(int) (mFL.hostNameDialog.backButton.x-mFL.hostNameDialog.backButton.radiusX);
		y=(int) (mFL.hostNameDialog.backButton.y-mFL.hostNameDialog.backButton.radiusY);
		batch.draw(btnDialogLeft,x,y,
				mFL.hostNameDialog.backButton.radiusX*2,
				mFL.hostNameDialog.backButton.radiusY*2);
		if (mFL.hostNameDialog.backButton.getLabel().texture==null) {
        	mFL.hostNameDialog.backButton.getLabel().loadTexture();
        }
		x=(int) (mFL.hostNameDialog.backButton.getLabel().x-mFL.hostNameDialog.backButton.getLabel().radiusX);
		y=(int) (mFL.hostNameDialog.backButton.getLabel().y-mFL.hostNameDialog.backButton.getLabel().radiusY);
		batch.draw(mFL.hostNameDialog.backButton.getLabel().texture,x,y,
				0,0,mFL.hostNameDialog.backButton.getLabel().radiusX*2,
				mFL.hostNameDialog.backButton.getLabel().radiusY*2);
		
		x=(int) (mFL.hostNameDialog.okButton.x-mFL.hostNameDialog.okButton.radiusX);
		y=(int) (mFL.hostNameDialog.okButton.y-mFL.hostNameDialog.okButton.radiusY);
		batch.draw(btnDialogRight,x,y,
				mFL.hostNameDialog.okButton.radiusX*2,
				mFL.hostNameDialog.okButton.radiusY*2);
		if (mFL.hostNameDialog.okButton.getLabel().texture==null) {
        	mFL.hostNameDialog.okButton.getLabel().loadTexture();
        }
		x=(int) (mFL.hostNameDialog.okButton.getLabel().x-mFL.hostNameDialog.okButton.getLabel().radiusX);
		y=(int) (mFL.hostNameDialog.okButton.getLabel().y-mFL.hostNameDialog.okButton.getLabel().radiusY);
		batch.draw(mFL.hostNameDialog.okButton.getLabel().texture,x,y,
				0,0,mFL.hostNameDialog.okButton.getLabel().radiusX*2,
				mFL.hostNameDialog.okButton.getLabel().radiusY*2);
		
		
		batch.setColor(alphaShader);

	}
	
	private void renderHostChipCaseDialog() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.hostChipCaseDialog.selectLabel.opacity);
        
        int x = (int) (mFL.hostChipCaseDialog.x);
        int y = (int) (mFL.hostChipCaseDialog.y);
        renderRectangleRounded(blackDialogRegions,x,y,
				mFL.hostChipCaseDialog.radiusX,mFL.hostChipCaseDialog.radiusY);
        
        batch.draw(anonTexture,
				mFL.hostChipCaseDialog.chipCaseSprite.x-mFL.hostChipCaseDialog.chipCaseSprite.radiusX,
				mFL.hostChipCaseDialog.chipCaseSprite.y-mFL.hostChipCaseDialog.chipCaseSprite.radiusY,
				mFL.hostChipCaseDialog.chipCaseSprite.radiusX*2,mFL.hostChipCaseDialog.chipCaseSprite.radiusY*2,
				0,0,70,71,false,false);
        
        if (mFL.hostChipCaseDialog.selectLabel.texture==null) {
        	mFL.hostChipCaseDialog.selectLabel.loadTexture();
        }
		x=(int) (mFL.hostChipCaseDialog.selectLabel.x-mFL.hostChipCaseDialog.selectLabel.radiusX);
		y=(int) (mFL.hostChipCaseDialog.selectLabel.y-mFL.hostChipCaseDialog.selectLabel.radiusY);
		batch.draw(mFL.hostChipCaseDialog.selectLabel.texture,x,y,
				0,0,mFL.hostChipCaseDialog.selectLabel.radiusX*2,
				mFL.hostChipCaseDialog.selectLabel.radiusY*2);
		
		batch.draw(case1BackRegion,
				mFL.hostChipCaseDialog.standardBackground.x-mFL.hostChipCaseDialog.standardBackground.radiusX,
				mFL.hostChipCaseDialog.standardBackground.y-mFL.hostChipCaseDialog.standardBackground.radiusY,
				mFL.hostChipCaseDialog.standardBackground.radiusX*2,mFL.hostChipCaseDialog.standardBackground.radiusY*2);
		
		if (mFL.hostChipCaseDialog.standardTitle.texture==null) {
        	mFL.hostChipCaseDialog.standardTitle.loadTexture();
        }
		x=(int) (mFL.hostChipCaseDialog.standardTitle.x-mFL.hostChipCaseDialog.standardTitle.radiusX);
		y=(int) (mFL.hostChipCaseDialog.standardTitle.y-mFL.hostChipCaseDialog.standardTitle.radiusY);
		batch.draw(mFL.hostChipCaseDialog.standardTitle.texture,x,y,
				0,0,mFL.hostChipCaseDialog.standardTitle.radiusX*2,
				mFL.hostChipCaseDialog.standardTitle.radiusY*2);
		
		batch.draw(standardCaseRegion,
				mFL.hostChipCaseDialog.standardImage.x-mFL.hostChipCaseDialog.standardImage.radiusX,
				mFL.hostChipCaseDialog.standardImage.y-mFL.hostChipCaseDialog.standardImage.radiusY,
				mFL.hostChipCaseDialog.standardImage.radiusX*2,mFL.hostChipCaseDialog.standardImage.radiusY*2);
		
		if (mFL.hostChipCaseDialog.standardText1.texture==null) {
        	mFL.hostChipCaseDialog.standardText1.loadTexture();
        }
		x=(int) (mFL.hostChipCaseDialog.standardText1.x-mFL.hostChipCaseDialog.standardText1.radiusX);
		y=(int) (mFL.hostChipCaseDialog.standardText1.y-mFL.hostChipCaseDialog.standardText1.radiusY);
		batch.draw(mFL.hostChipCaseDialog.standardText1.texture,x,y,
				0,0,mFL.hostChipCaseDialog.standardText1.radiusX*2,
				mFL.hostChipCaseDialog.standardText1.radiusY*2);
		if (mFL.hostChipCaseDialog.standardText2.texture==null) {
        	mFL.hostChipCaseDialog.standardText2.loadTexture();
        }
		x=(int) (mFL.hostChipCaseDialog.standardText2.x-mFL.hostChipCaseDialog.standardText2.radiusX);
		y=(int) (mFL.hostChipCaseDialog.standardText2.y-mFL.hostChipCaseDialog.standardText2.radiusY);
		batch.draw(mFL.hostChipCaseDialog.standardText2.texture,x,y,
				0,0,mFL.hostChipCaseDialog.standardText2.radiusX*2,
				mFL.hostChipCaseDialog.standardText2.radiusY*2);
		if (mFL.hostChipCaseDialog.standardPrice.texture==null) {
        	mFL.hostChipCaseDialog.standardPrice.loadTexture();
        }
		x=(int) (mFL.hostChipCaseDialog.standardPrice.x-mFL.hostChipCaseDialog.standardPrice.radiusX);
		y=(int) (mFL.hostChipCaseDialog.standardPrice.y-mFL.hostChipCaseDialog.standardPrice.radiusY);
		batch.draw(mFL.hostChipCaseDialog.standardPrice.texture,x,y,
				0,0,mFL.hostChipCaseDialog.standardPrice.radiusX*2,
				mFL.hostChipCaseDialog.standardPrice.radiusY*2);
		
		batch.draw(case2BackRegion,
				mFL.hostChipCaseDialog.customBackground.x-mFL.hostChipCaseDialog.customBackground.radiusX,
				mFL.hostChipCaseDialog.customBackground.y-mFL.hostChipCaseDialog.customBackground.radiusY,
				mFL.hostChipCaseDialog.customBackground.radiusX*2,mFL.hostChipCaseDialog.customBackground.radiusY*2);
		
		if (mFL.hostChipCaseDialog.customTitle.texture==null) {
        	mFL.hostChipCaseDialog.customTitle.loadTexture();
        }
		x=(int) (mFL.hostChipCaseDialog.customTitle.x-mFL.hostChipCaseDialog.customTitle.radiusX);
		y=(int) (mFL.hostChipCaseDialog.customTitle.y-mFL.hostChipCaseDialog.customTitle.radiusY);
		batch.draw(mFL.hostChipCaseDialog.customTitle.texture,x,y,
				0,0,mFL.hostChipCaseDialog.customTitle.radiusX*2,
				mFL.hostChipCaseDialog.customTitle.radiusY*2);
		
		batch.draw(standardCaseRegion,
				mFL.hostChipCaseDialog.customImage.x-mFL.hostChipCaseDialog.customImage.radiusX,
				mFL.hostChipCaseDialog.customImage.y-mFL.hostChipCaseDialog.customImage.radiusY,
				mFL.hostChipCaseDialog.customImage.radiusX*2,mFL.hostChipCaseDialog.customImage.radiusY*2);
		
		if (mFL.hostChipCaseDialog.customText1.texture==null) {
        	mFL.hostChipCaseDialog.customText1.loadTexture();
        }
		x=(int) (mFL.hostChipCaseDialog.customText1.x-mFL.hostChipCaseDialog.customText1.radiusX);
		y=(int) (mFL.hostChipCaseDialog.customText1.y-mFL.hostChipCaseDialog.customText1.radiusY);
		batch.draw(mFL.hostChipCaseDialog.customText1.texture,x,y,
				0,0,mFL.hostChipCaseDialog.customText1.radiusX*2,
				mFL.hostChipCaseDialog.customText1.radiusY*2);
		if (mFL.hostChipCaseDialog.customText2.texture==null) {
        	mFL.hostChipCaseDialog.customText2.loadTexture();
        }
		x=(int) (mFL.hostChipCaseDialog.customText2.x-mFL.hostChipCaseDialog.customText2.radiusX);
		y=(int) (mFL.hostChipCaseDialog.customText2.y-mFL.hostChipCaseDialog.customText2.radiusY);
		batch.draw(mFL.hostChipCaseDialog.customText2.texture,x,y,
				0,0,mFL.hostChipCaseDialog.customText2.radiusX*2,
				mFL.hostChipCaseDialog.customText2.radiusY*2);
		if (mFL.hostChipCaseDialog.customPrice.texture==null) {
        	mFL.hostChipCaseDialog.customPrice.loadTexture();
        }
		x=(int) (mFL.hostChipCaseDialog.customPrice.x-mFL.hostChipCaseDialog.customPrice.radiusX);
		y=(int) (mFL.hostChipCaseDialog.customPrice.y-mFL.hostChipCaseDialog.customPrice.radiusY);
		batch.draw(mFL.hostChipCaseDialog.customPrice.texture,x,y,
				0,0,mFL.hostChipCaseDialog.customPrice.radiusX*2,
				mFL.hostChipCaseDialog.customPrice.radiusY*2);
		
		x=(int) (mFL.hostChipCaseDialog.backButton.x-mFL.hostChipCaseDialog.backButton.radiusX);
		y=(int) (mFL.hostChipCaseDialog.backButton.y-mFL.hostChipCaseDialog.backButton.radiusY);
		batch.draw(btnDialog,x,y,
				mFL.hostChipCaseDialog.backButton.radiusX*2,
				mFL.hostChipCaseDialog.backButton.radiusY*2);
		if (mFL.hostChipCaseDialog.backButton.getLabel().texture==null) {
        	mFL.hostChipCaseDialog.backButton.getLabel().loadTexture();
        }
		x=(int) (mFL.hostChipCaseDialog.backButton.getLabel().x-mFL.hostChipCaseDialog.backButton.getLabel().radiusX);
		y=(int) (mFL.hostChipCaseDialog.backButton.getLabel().y-mFL.hostChipCaseDialog.backButton.getLabel().radiusY);
		batch.draw(mFL.hostChipCaseDialog.backButton.getLabel().texture,x,y,
				0,0,mFL.hostChipCaseDialog.backButton.getLabel().radiusX*2,
				mFL.hostChipCaseDialog.backButton.getLabel().radiusY*2);

		
		
		batch.setColor(alphaShader);

	}
	
	private void renderHostChipSetupDialog() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.hostChipSetupDialog.chipCaseSprite.opacity);
        
        int x = (int) (mFL.hostChipSetupDialog.x);
        int y = (int) (mFL.hostChipSetupDialog.y);
        renderRectangleRounded(blackDialogRegions,x,y,
				mFL.hostChipSetupDialog.radiusX,mFL.hostChipSetupDialog.radiusY);
        
        batch.draw(anonTexture,
				mFL.hostChipSetupDialog.chipCaseSprite.x-mFL.hostChipSetupDialog.chipCaseSprite.radiusX,
				mFL.hostChipSetupDialog.chipCaseSprite.y-mFL.hostChipSetupDialog.chipCaseSprite.radiusY,
				mFL.hostChipSetupDialog.chipCaseSprite.radiusX*2,mFL.hostChipSetupDialog.chipCaseSprite.radiusY*2,
				0,0,70,71,false,false);
        
        
        batch.draw(chipRegions[0],
				mFL.hostChipSetupDialog.chipASprite.x-mFL.hostChipSetupDialog.chipASprite.radiusX,
				mFL.hostChipSetupDialog.chipASprite.y-mFL.hostChipSetupDialog.chipASprite.radiusY,
				mFL.hostChipSetupDialog.chipASprite.radiusX*2,mFL.hostChipSetupDialog.chipASprite.radiusY*2);
        batch.draw(buttonMinusRegion,
				mFL.hostChipSetupDialog.minusAButton.x-mFL.hostChipSetupDialog.minusAButton.radiusX,
				mFL.hostChipSetupDialog.minusAButton.y-mFL.hostChipSetupDialog.minusAButton.radiusY,
				mFL.hostChipSetupDialog.minusAButton.radiusX*2,mFL.hostChipSetupDialog.minusAButton.radiusY*2);
        batch.draw(buttonPlusRegion,
				mFL.hostChipSetupDialog.plusAButton.x-mFL.hostChipSetupDialog.plusAButton.radiusX,
				mFL.hostChipSetupDialog.plusAButton.y-mFL.hostChipSetupDialog.plusAButton.radiusY,
				mFL.hostChipSetupDialog.plusAButton.radiusX*2,mFL.hostChipSetupDialog.plusAButton.radiusY*2);
        renderTextField(textFieldRegions,
				(int)mFL.hostChipSetupDialog.amountABackground.x,
				(int)mFL.hostChipSetupDialog.amountABackground.y,
				mFL.hostChipSetupDialog.amountABackground.radiusX,mFL.hostChipSetupDialog.amountABackground.radiusY);
        if (mFL.hostChipSetupDialog.amountALabel.texture==null) {
        	mFL.hostChipSetupDialog.amountALabel.loadTexture();
        }
		x=(int) (mFL.hostChipSetupDialog.amountALabel.x-mFL.hostChipSetupDialog.amountALabel.radiusX);
		y=(int) (mFL.hostChipSetupDialog.amountALabel.y-mFL.hostChipSetupDialog.amountALabel.radiusY);
		batch.draw(mFL.hostChipSetupDialog.amountALabel.texture,x,y,
				0,0,mFL.hostChipSetupDialog.amountALabel.radiusX*2,
				mFL.hostChipSetupDialog.amountALabel.radiusY*2);
		if (mFL.hostChipSetupDialog.infoALabel.texture==null) {
        	mFL.hostChipSetupDialog.infoALabel.loadTexture();
        }
		x=(int) (mFL.hostChipSetupDialog.infoALabel.x-mFL.hostChipSetupDialog.infoALabel.radiusX);
		y=(int) (mFL.hostChipSetupDialog.infoALabel.y-mFL.hostChipSetupDialog.infoALabel.radiusY);
		batch.draw(mFL.hostChipSetupDialog.infoALabel.texture,x,y,
				0,0,mFL.hostChipSetupDialog.infoALabel.radiusX*2,
				mFL.hostChipSetupDialog.infoALabel.radiusY*2);
		
		batch.draw(chipRegions[1],
				mFL.hostChipSetupDialog.chipBSprite.x-mFL.hostChipSetupDialog.chipBSprite.radiusX,
				mFL.hostChipSetupDialog.chipBSprite.y-mFL.hostChipSetupDialog.chipBSprite.radiusY,
				mFL.hostChipSetupDialog.chipBSprite.radiusX*2,mFL.hostChipSetupDialog.chipBSprite.radiusY*2);
        batch.draw(buttonMinusRegion,
				mFL.hostChipSetupDialog.minusBButton.x-mFL.hostChipSetupDialog.minusBButton.radiusX,
				mFL.hostChipSetupDialog.minusBButton.y-mFL.hostChipSetupDialog.minusBButton.radiusY,
				mFL.hostChipSetupDialog.minusBButton.radiusX*2,mFL.hostChipSetupDialog.minusBButton.radiusY*2);
        batch.draw(buttonPlusRegion,
				mFL.hostChipSetupDialog.plusBButton.x-mFL.hostChipSetupDialog.plusBButton.radiusX,
				mFL.hostChipSetupDialog.plusBButton.y-mFL.hostChipSetupDialog.plusBButton.radiusY,
				mFL.hostChipSetupDialog.plusBButton.radiusX*2,mFL.hostChipSetupDialog.plusBButton.radiusY*2);
        renderTextField(textFieldRegions,
				(int)mFL.hostChipSetupDialog.amountBBackground.x,
				(int)mFL.hostChipSetupDialog.amountBBackground.y,
				mFL.hostChipSetupDialog.amountBBackground.radiusX,mFL.hostChipSetupDialog.amountBBackground.radiusY);
        if (mFL.hostChipSetupDialog.amountBLabel.texture==null) {
        	mFL.hostChipSetupDialog.amountBLabel.loadTexture();
        }
		x=(int) (mFL.hostChipSetupDialog.amountBLabel.x-mFL.hostChipSetupDialog.amountBLabel.radiusX);
		y=(int) (mFL.hostChipSetupDialog.amountBLabel.y-mFL.hostChipSetupDialog.amountBLabel.radiusY);
		batch.draw(mFL.hostChipSetupDialog.amountBLabel.texture,x,y,
				0,0,mFL.hostChipSetupDialog.amountBLabel.radiusX*2,
				mFL.hostChipSetupDialog.amountBLabel.radiusY*2);
		if (mFL.hostChipSetupDialog.infoBLabel.texture==null) {
        	mFL.hostChipSetupDialog.infoBLabel.loadTexture();
        }
		x=(int) (mFL.hostChipSetupDialog.infoBLabel.x-mFL.hostChipSetupDialog.infoBLabel.radiusX);
		y=(int) (mFL.hostChipSetupDialog.infoBLabel.y-mFL.hostChipSetupDialog.infoBLabel.radiusY);
		batch.draw(mFL.hostChipSetupDialog.infoBLabel.texture,x,y,
				0,0,mFL.hostChipSetupDialog.infoBLabel.radiusX*2,
				mFL.hostChipSetupDialog.infoBLabel.radiusY*2);
		
		batch.draw(chipRegions[2],
				mFL.hostChipSetupDialog.chipCSprite.x-mFL.hostChipSetupDialog.chipCSprite.radiusX,
				mFL.hostChipSetupDialog.chipCSprite.y-mFL.hostChipSetupDialog.chipCSprite.radiusY,
				mFL.hostChipSetupDialog.chipCSprite.radiusX*2,mFL.hostChipSetupDialog.chipCSprite.radiusY*2);
        batch.draw(buttonMinusRegion,
				mFL.hostChipSetupDialog.minusCButton.x-mFL.hostChipSetupDialog.minusCButton.radiusX,
				mFL.hostChipSetupDialog.minusCButton.y-mFL.hostChipSetupDialog.minusCButton.radiusY,
				mFL.hostChipSetupDialog.minusCButton.radiusX*2,mFL.hostChipSetupDialog.minusCButton.radiusY*2);
        batch.draw(buttonPlusRegion,
				mFL.hostChipSetupDialog.plusCButton.x-mFL.hostChipSetupDialog.plusCButton.radiusX,
				mFL.hostChipSetupDialog.plusCButton.y-mFL.hostChipSetupDialog.plusCButton.radiusY,
				mFL.hostChipSetupDialog.plusCButton.radiusX*2,mFL.hostChipSetupDialog.plusCButton.radiusY*2);
        renderTextField(textFieldRegions,
				(int)mFL.hostChipSetupDialog.amountCBackground.x,
				(int)mFL.hostChipSetupDialog.amountCBackground.y,
				mFL.hostChipSetupDialog.amountCBackground.radiusX,mFL.hostChipSetupDialog.amountCBackground.radiusY);
        if (mFL.hostChipSetupDialog.amountCLabel.texture==null) {
        	mFL.hostChipSetupDialog.amountCLabel.loadTexture();
        }
		x=(int) (mFL.hostChipSetupDialog.amountCLabel.x-mFL.hostChipSetupDialog.amountCLabel.radiusX);
		y=(int) (mFL.hostChipSetupDialog.amountCLabel.y-mFL.hostChipSetupDialog.amountCLabel.radiusY);
		batch.draw(mFL.hostChipSetupDialog.amountCLabel.texture,x,y,
				0,0,mFL.hostChipSetupDialog.amountCLabel.radiusX*2,
				mFL.hostChipSetupDialog.amountCLabel.radiusY*2);
		if (mFL.hostChipSetupDialog.infoCLabel.texture==null) {
        	mFL.hostChipSetupDialog.infoCLabel.loadTexture();
        }
		x=(int) (mFL.hostChipSetupDialog.infoCLabel.x-mFL.hostChipSetupDialog.infoCLabel.radiusX);
		y=(int) (mFL.hostChipSetupDialog.infoCLabel.y-mFL.hostChipSetupDialog.infoCLabel.radiusY);
		batch.draw(mFL.hostChipSetupDialog.infoCLabel.texture,x,y,
				0,0,mFL.hostChipSetupDialog.infoCLabel.radiusX*2,
				mFL.hostChipSetupDialog.infoCLabel.radiusY*2);
		
		batch.draw(buttonMinusRegion,
				mFL.hostChipSetupDialog.minusBuyinButton.x-mFL.hostChipSetupDialog.minusBuyinButton.radiusX,
				mFL.hostChipSetupDialog.minusBuyinButton.y-mFL.hostChipSetupDialog.minusBuyinButton.radiusY,
				mFL.hostChipSetupDialog.minusBuyinButton.radiusX*2,mFL.hostChipSetupDialog.minusBuyinButton.radiusY*2);
        batch.draw(buttonPlusRegion,
				mFL.hostChipSetupDialog.plusBuyinButton.x-mFL.hostChipSetupDialog.plusBuyinButton.radiusX,
				mFL.hostChipSetupDialog.plusBuyinButton.y-mFL.hostChipSetupDialog.plusBuyinButton.radiusY,
				mFL.hostChipSetupDialog.plusBuyinButton.radiusX*2,mFL.hostChipSetupDialog.plusBuyinButton.radiusY*2);
        x=(int) (mFL.hostChipSetupDialog.amountBuyinBackground.x);
        y=(int) (mFL.hostChipSetupDialog.amountBuyinBackground.y);
        renderTextField(textFieldRegions,x,y,
				mFL.hostChipSetupDialog.amountBuyinBackground.radiusX,
				mFL.hostChipSetupDialog.amountBuyinBackground.radiusY);
        if (mFL.hostChipSetupDialog.amountBuyinLabel.texture==null) {
        	mFL.hostChipSetupDialog.amountBuyinLabel.loadTexture();
        }
		x=(int) (mFL.hostChipSetupDialog.amountBuyinLabel.x-mFL.hostChipSetupDialog.amountBuyinLabel.radiusX);
		y=(int) (mFL.hostChipSetupDialog.amountBuyinLabel.y-mFL.hostChipSetupDialog.amountBuyinLabel.radiusY);
		batch.draw(mFL.hostChipSetupDialog.amountBuyinLabel.texture,x,y,
				0,0,mFL.hostChipSetupDialog.amountBuyinLabel.radiusX*2,
				mFL.hostChipSetupDialog.amountBuyinLabel.radiusY*2);
		if (mFL.hostChipSetupDialog.infoBuyinLabel.texture==null) {
        	mFL.hostChipSetupDialog.infoBuyinLabel.loadTexture();
        }
		x=(int) (mFL.hostChipSetupDialog.infoBuyinLabel.x-mFL.hostChipSetupDialog.infoBuyinLabel.radiusX);
		y=(int) (mFL.hostChipSetupDialog.infoBuyinLabel.y-mFL.hostChipSetupDialog.infoBuyinLabel.radiusY);
		batch.draw(mFL.hostChipSetupDialog.infoBuyinLabel.texture,x,y,
				0,0,mFL.hostChipSetupDialog.infoBuyinLabel.radiusX*2,
				mFL.hostChipSetupDialog.infoBuyinLabel.radiusY*2);
        
		x=(int) (mFL.hostChipSetupDialog.backButton.x-mFL.hostChipSetupDialog.backButton.radiusX);
		y=(int) (mFL.hostChipSetupDialog.backButton.y-mFL.hostChipSetupDialog.backButton.radiusY);
		batch.draw(btnDialogLeft,x,y,
				mFL.hostChipSetupDialog.backButton.radiusX*2,
				mFL.hostChipSetupDialog.backButton.radiusY*2);
		if (mFL.hostChipSetupDialog.backButton.getLabel().texture==null) {
        	mFL.hostChipSetupDialog.backButton.getLabel().loadTexture();
        }
		x=(int) (mFL.hostChipSetupDialog.backButton.getLabel().x-mFL.hostChipSetupDialog.backButton.getLabel().radiusX);
		y=(int) (mFL.hostChipSetupDialog.backButton.getLabel().y-mFL.hostChipSetupDialog.backButton.getLabel().radiusY);
		batch.draw(mFL.hostChipSetupDialog.backButton.getLabel().texture,x,y,
				0,0,mFL.hostChipSetupDialog.backButton.getLabel().radiusX*2,
				mFL.hostChipSetupDialog.backButton.getLabel().radiusY*2);

		x=(int) (mFL.hostChipSetupDialog.okayButton.x-mFL.hostChipSetupDialog.okayButton.radiusX);
		y=(int) (mFL.hostChipSetupDialog.okayButton.y-mFL.hostChipSetupDialog.okayButton.radiusY);
		batch.draw(btnDialogRight,x,y,
				mFL.hostChipSetupDialog.okayButton.radiusX*2,
				mFL.hostChipSetupDialog.okayButton.radiusY*2);
		if (mFL.hostChipSetupDialog.okayButton.getLabel().texture==null) {
        	mFL.hostChipSetupDialog.okayButton.getLabel().loadTexture();
        }
		x=(int) (mFL.hostChipSetupDialog.okayButton.getLabel().x-mFL.hostChipSetupDialog.okayButton.getLabel().radiusX);
		y=(int) (mFL.hostChipSetupDialog.okayButton.getLabel().y-mFL.hostChipSetupDialog.okayButton.getLabel().radiusY);
		batch.draw(mFL.hostChipSetupDialog.okayButton.getLabel().texture,x,y,
				0,0,mFL.hostChipSetupDialog.okayButton.getLabel().radiusX*2,
				mFL.hostChipSetupDialog.okayButton.getLabel().radiusY*2);
		
		batch.setColor(alphaShader);

	}
	
	private void renderHostLobbyDialog() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.hostLobbyDialog.nameLabel.opacity);
        
        int x = (int) (mFL.hostLobbyDialog.x);
        int y = (int) (mFL.hostLobbyDialog.y);
        renderRectangleRounded(blackDialogRegions,x,y,
				mFL.hostLobbyDialog.radiusX,mFL.hostLobbyDialog.radiusY);
        
        renderTextField(textFieldRegions, mFL.hostLobbyDialog.ringSprite);
        
        x=(int) (mFL.hostLobbyDialog.lineSprite.x-mFL.hostLobbyDialog.lineSprite.radiusX);
		y=(int) (mFL.hostLobbyDialog.lineSprite.y-mFL.hostLobbyDialog.lineSprite.radiusY);
		batch.draw(goldLineRegion,x,y,
				mFL.hostLobbyDialog.lineSprite.radiusX*2,mFL.hostLobbyDialog.lineSprite.radiusY*2);
		
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.hostLobbyDialog.waitingLabel.opacity);
        if (mFL.hostLobbyDialog.waitingLabel.texture==null) {
        	mFL.hostLobbyDialog.waitingLabel.loadTexture();
        }
		x=(int) (mFL.hostLobbyDialog.waitingLabel.x-mFL.hostLobbyDialog.waitingLabel.radiusX);
		y=(int) (mFL.hostLobbyDialog.waitingLabel.y-mFL.hostLobbyDialog.waitingLabel.radiusY);
		batch.draw(mFL.hostLobbyDialog.waitingLabel.texture,x,y,
				0,0,mFL.hostLobbyDialog.waitingLabel.radiusX*2,
				mFL.hostLobbyDialog.waitingLabel.radiusY*2);
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
		
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.hostLobbyDialog.dealerLabel.opacity);
        if (mFL.hostLobbyDialog.dealerLabel.texture==null) {
        	mFL.hostLobbyDialog.dealerLabel.loadTexture();
        }
		x=(int) (mFL.hostLobbyDialog.dealerLabel.x-mFL.hostLobbyDialog.dealerLabel.radiusX);
		y=(int) (mFL.hostLobbyDialog.dealerLabel.y-mFL.hostLobbyDialog.dealerLabel.radiusY);
		batch.draw(mFL.hostLobbyDialog.dealerLabel.texture,x,y,
				0,0,mFL.hostLobbyDialog.dealerLabel.radiusX*2,
				mFL.hostLobbyDialog.dealerLabel.radiusY*2);
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
		
		
		if (mFL.hostLobbyDialog.nameLabel.texture==null) {
        	mFL.hostLobbyDialog.nameLabel.loadTexture();
        }
		x=(int) (mFL.hostLobbyDialog.nameLabel.x-mFL.hostLobbyDialog.nameLabel.radiusX);
		y=(int) (mFL.hostLobbyDialog.nameLabel.y-mFL.hostLobbyDialog.nameLabel.radiusY);
		batch.draw(mFL.hostLobbyDialog.nameLabel.texture,x,y,
				0,0,mFL.hostLobbyDialog.nameLabel.radiusX*2,
				mFL.hostLobbyDialog.nameLabel.radiusY*2);
		
		x=(int) (mFL.hostLobbyDialog.backButton.x-mFL.hostLobbyDialog.backButton.radiusX);
		y=(int) (mFL.hostLobbyDialog.backButton.y-mFL.hostLobbyDialog.backButton.radiusY);
		batch.draw(btnDialogLeft,x,y,
				mFL.hostLobbyDialog.backButton.radiusX*2,
				mFL.hostLobbyDialog.backButton.radiusY*2);
		if (mFL.hostLobbyDialog.backButton.getLabel().texture==null) {
        	mFL.hostLobbyDialog.backButton.getLabel().loadTexture();
        }
		x=(int) (mFL.hostLobbyDialog.backButton.getLabel().x-mFL.hostLobbyDialog.backButton.getLabel().radiusX);
		y=(int) (mFL.hostLobbyDialog.backButton.getLabel().y-mFL.hostLobbyDialog.backButton.getLabel().radiusY);
		batch.draw(mFL.hostLobbyDialog.backButton.getLabel().texture,x,y,
				0,0,mFL.hostLobbyDialog.backButton.getLabel().radiusX*2,
				mFL.hostLobbyDialog.backButton.getLabel().radiusY*2);
		
		x=(int) (mFL.hostLobbyDialog.startButton.x-mFL.hostLobbyDialog.startButton.radiusX);
		y=(int) (mFL.hostLobbyDialog.startButton.y-mFL.hostLobbyDialog.startButton.radiusY);
		batch.draw(btnDialogRight,x,y,
				mFL.hostLobbyDialog.startButton.radiusX*2,
				mFL.hostLobbyDialog.startButton.radiusY*2);
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.hostLobbyDialog.startButton.getLabel().opacity);
		if (mFL.hostLobbyDialog.startButton.getLabel().texture==null) {
        	mFL.hostLobbyDialog.startButton.getLabel().loadTexture();
        }
		x=(int) (mFL.hostLobbyDialog.startButton.getLabel().x-mFL.hostLobbyDialog.startButton.getLabel().radiusX);
		y=(int) (mFL.hostLobbyDialog.startButton.getLabel().y-mFL.hostLobbyDialog.startButton.getLabel().radiusY);
		batch.draw(mFL.hostLobbyDialog.startButton.getLabel().texture,x,y,
				0,0,mFL.hostLobbyDialog.startButton.getLabel().radiusX*2,
				mFL.hostLobbyDialog.startButton.getLabel().radiusY*2);
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
		
		batch.setColor(alphaShader);

	}
	
	private void renderHostRearrangeDialog() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.hostRearrangeDialog.nameLabel.opacity);
        
        int x = (int) (mFL.hostRearrangeDialog.x);
        int y = (int) (mFL.hostRearrangeDialog.y);
        renderRectangleRounded(blackDialogRegions,x,y,
				mFL.hostRearrangeDialog.radiusX,mFL.hostRearrangeDialog.radiusY);
        
        renderTextField(textFieldRegions, mFL.hostRearrangeDialog.ringSprite);
        
        x=(int) (mFL.hostRearrangeDialog.lineSprite.x-mFL.hostRearrangeDialog.lineSprite.radiusX);
		y=(int) (mFL.hostRearrangeDialog.lineSprite.y-mFL.hostRearrangeDialog.lineSprite.radiusY);
		batch.draw(goldLineRegion,x,y,
				mFL.hostRearrangeDialog.lineSprite.radiusX*2,mFL.hostRearrangeDialog.lineSprite.radiusY*2);
		
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.hostRearrangeDialog.rearrangeLabel.opacity);
        if (mFL.hostRearrangeDialog.rearrangeLabel.texture==null) {
        	mFL.hostRearrangeDialog.rearrangeLabel.loadTexture();
        }
		x=(int) (mFL.hostRearrangeDialog.rearrangeLabel.x-mFL.hostRearrangeDialog.rearrangeLabel.radiusX);
		y=(int) (mFL.hostRearrangeDialog.rearrangeLabel.y-mFL.hostRearrangeDialog.rearrangeLabel.radiusY);
		batch.draw(mFL.hostRearrangeDialog.rearrangeLabel.texture,x,y,
				0,0,mFL.hostRearrangeDialog.rearrangeLabel.radiusX*2,
				mFL.hostRearrangeDialog.rearrangeLabel.radiusY*2);
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
		
		
		if (mFL.hostRearrangeDialog.nameLabel.texture==null) {
        	mFL.hostRearrangeDialog.nameLabel.loadTexture();
        }
		x=(int) (mFL.hostRearrangeDialog.nameLabel.x-mFL.hostRearrangeDialog.nameLabel.radiusX);
		y=(int) (mFL.hostRearrangeDialog.nameLabel.y-mFL.hostRearrangeDialog.nameLabel.radiusY);
		batch.draw(mFL.hostRearrangeDialog.nameLabel.texture,x,y,
				0,0,mFL.hostRearrangeDialog.nameLabel.radiusX*2,
				mFL.hostRearrangeDialog.nameLabel.radiusY*2);
		
		x=(int) (mFL.hostRearrangeDialog.doneButton.x-mFL.hostRearrangeDialog.doneButton.radiusX);
		y=(int) (mFL.hostRearrangeDialog.doneButton.y-mFL.hostRearrangeDialog.doneButton.radiusY);
		batch.draw(btnDialog,x,y,
				mFL.hostRearrangeDialog.doneButton.radiusX*2,
				mFL.hostRearrangeDialog.doneButton.radiusY*2);
		if (mFL.hostRearrangeDialog.doneButton.getLabel().texture==null) {
        	mFL.hostRearrangeDialog.doneButton.getLabel().loadTexture();
        }
		x=(int) (mFL.hostRearrangeDialog.doneButton.getLabel().x-mFL.hostRearrangeDialog.doneButton.getLabel().radiusX);
		y=(int) (mFL.hostRearrangeDialog.doneButton.getLabel().y-mFL.hostRearrangeDialog.doneButton.getLabel().radiusY);
		batch.draw(mFL.hostRearrangeDialog.doneButton.getLabel().texture,x,y,
				0,0,mFL.hostRearrangeDialog.doneButton.getLabel().radiusX*2,
				mFL.hostRearrangeDialog.doneButton.getLabel().radiusY*2);
		
		batch.setColor(alphaShader);

	}
	
	private void renderBuyinDialog() {
        
        renderRectangleRounded(blackDialogRegions, (int)mFL.playerBuyinDialog.x, (int)mFL.playerBuyinDialog.y,
        		mFL.playerBuyinDialog.radiusX, mFL.playerBuyinDialog.radiusY);
        batch.draw(envelopeTexture,
				mFL.playerBuyinDialog.envelope.x-mFL.playerBuyinDialog.envelope.radiusX,
				mFL.playerBuyinDialog.envelope.y-mFL.playerBuyinDialog.envelope.radiusY,
				mFL.playerBuyinDialog.envelope.radiusX*2,mFL.playerBuyinDialog.envelope.radiusY*2,
				0,0,110,70,false,false);
        if (mFL.playerBuyinDialog.buyinLabel.texture==null) {
        	mFL.playerBuyinDialog.buyinLabel.loadTexture();
        }
		int x=(int) (mFL.playerBuyinDialog.buyinLabel.x-mFL.playerBuyinDialog.buyinLabel.radiusX);
		int y=(int) (mFL.playerBuyinDialog.buyinLabel.y-mFL.playerBuyinDialog.buyinLabel.radiusY);
		batch.draw(mFL.playerBuyinDialog.buyinLabel.texture,x,y,
				0,0,mFL.playerBuyinDialog.buyinLabel.radiusX*2,
				mFL.playerBuyinDialog.buyinLabel.radiusY*2);
        if (mFL.playerBuyinDialog.amountTitleLabel.texture==null) {
        	mFL.playerBuyinDialog.amountTitleLabel.loadTexture();
        }
		x=(int) (mFL.playerBuyinDialog.amountTitleLabel.x-mFL.playerBuyinDialog.amountTitleLabel.radiusX);
		y=(int) (mFL.playerBuyinDialog.amountTitleLabel.y-mFL.playerBuyinDialog.amountTitleLabel.radiusY);
		batch.draw(mFL.playerBuyinDialog.amountTitleLabel.texture,x,y,
				0,0,mFL.playerBuyinDialog.amountTitleLabel.radiusX*2,
				mFL.playerBuyinDialog.amountTitleLabel.radiusY*2);
		batch.draw(buttonMinusRegion,
				mFL.playerBuyinDialog.minusButton.x-mFL.playerBuyinDialog.minusButton.radiusX,
				mFL.playerBuyinDialog.minusButton.y-mFL.playerBuyinDialog.minusButton.radiusY,
				mFL.playerBuyinDialog.minusButton.radiusX*2,mFL.playerBuyinDialog.minusButton.radiusY*2);
		batch.draw(buttonPlusRegion,
				mFL.playerBuyinDialog.plusButton.x-mFL.playerBuyinDialog.plusButton.radiusX,
				mFL.playerBuyinDialog.plusButton.y-mFL.playerBuyinDialog.plusButton.radiusY,
				mFL.playerBuyinDialog.plusButton.radiusX*2,mFL.playerBuyinDialog.plusButton.radiusY*2);
		batch.draw(textFieldRegion,
				mFL.playerBuyinDialog.amountBackground.x-mFL.playerBuyinDialog.amountBackground.radiusX,
				mFL.playerBuyinDialog.amountBackground.y-mFL.playerBuyinDialog.amountBackground.radiusY,
				mFL.playerBuyinDialog.amountBackground.radiusX*2,mFL.playerBuyinDialog.amountBackground.radiusY*2);
		if (mFL.playerBuyinDialog.amountNumberLabel.texture==null) {
        	mFL.playerBuyinDialog.amountNumberLabel.loadTexture();
        }
		x=(int) (mFL.playerBuyinDialog.amountNumberLabel.x-mFL.playerBuyinDialog.amountNumberLabel.radiusX);
		y=(int) (mFL.playerBuyinDialog.amountNumberLabel.y-mFL.playerBuyinDialog.amountNumberLabel.radiusY);
		batch.draw(mFL.playerBuyinDialog.amountNumberLabel.texture,x,y,
				0,0,mFL.playerBuyinDialog.amountNumberLabel.radiusX*2,
				mFL.playerBuyinDialog.amountNumberLabel.radiusY*2);
		batch.draw(btnDialogLeft,
				mFL.playerBuyinDialog.cancelButton.x-mFL.playerBuyinDialog.cancelButton.radiusX,
				mFL.playerBuyinDialog.cancelButton.y-mFL.playerBuyinDialog.cancelButton.radiusY,
				mFL.playerBuyinDialog.cancelButton.radiusX*2,mFL.playerBuyinDialog.cancelButton.radiusY*2);
		if (mFL.playerBuyinDialog.cancelButton.getLabel().texture==null) {
        	mFL.playerBuyinDialog.cancelButton.getLabel().loadTexture();
        }
		x=(int) (mFL.playerBuyinDialog.cancelButton.getLabel().x-mFL.playerBuyinDialog.cancelButton.getLabel().radiusX);
		y=(int) (mFL.playerBuyinDialog.cancelButton.getLabel().y-mFL.playerBuyinDialog.cancelButton.getLabel().radiusY);
		batch.draw(mFL.playerBuyinDialog.cancelButton.getLabel().texture,x,y,
				0,0,mFL.playerBuyinDialog.cancelButton.getLabel().radiusX*2,
				mFL.playerBuyinDialog.cancelButton.getLabel().radiusY*2);
		batch.draw(btnDialogRight,
				mFL.playerBuyinDialog.okayButton.x-mFL.playerBuyinDialog.okayButton.radiusX,
				mFL.playerBuyinDialog.okayButton.y-mFL.playerBuyinDialog.okayButton.radiusY,
				mFL.playerBuyinDialog.okayButton.radiusX*2,mFL.playerBuyinDialog.okayButton.radiusY*2);
		if (mFL.playerBuyinDialog.okayButton.getLabel().texture==null) {
        	mFL.playerBuyinDialog.okayButton.getLabel().loadTexture();
        }
		x=(int) (mFL.playerBuyinDialog.okayButton.getLabel().x-mFL.playerBuyinDialog.okayButton.getLabel().radiusX);
		y=(int) (mFL.playerBuyinDialog.okayButton.getLabel().y-mFL.playerBuyinDialog.okayButton.getLabel().radiusY);
		batch.draw(mFL.playerBuyinDialog.okayButton.getLabel().texture,x,y,
				0,0,mFL.playerBuyinDialog.okayButton.getLabel().radiusX*2,
				mFL.playerBuyinDialog.okayButton.getLabel().radiusY*2);
	}

	private void renderPlayerDashboard() {
		batch.draw(playerDashboardTexture,mFL.playerDashboard.x-mFL.playerDashboard.radiusX,
				mFL.playerDashboard.y-mFL.playerDashboard.radiusY,
				mFL.playerDashboard.radiusX*2,mFL.playerDashboard.radiusY*2,
				0,0,1142,115,false,false);
		
		batch.draw(dashboardStatusTexture,mFL.playerDashboard.statusBackground.x-mFL.playerDashboard.statusBackground.radiusX,
				mFL.playerDashboard.statusBackground.y-mFL.playerDashboard.statusBackground.radiusY,
				mFL.playerDashboard.statusBackground.radiusX*2,mFL.playerDashboard.statusBackground.radiusY*2,
				0,0,640,88,false,false);
		if (mFL.playerDashboard.profilePic.texture!=null) {
			profilePicTexture=mFL.playerDashboard.profilePic.texture;
		}
		int x=(int) (mFL.playerDashboard.profilePic.x-mFL.playerDashboard.profilePic.radiusX);
		int y=(int) (mFL.playerDashboard.profilePic.y-mFL.playerDashboard.profilePic.radiusY);
		batch.draw(profilePicTexture,x,y,
				mFL.playerDashboard.profilePic.radiusX*2,mFL.playerDashboard.profilePic.radiusY*2);
		batch.draw(dashboardIdTexture,mFL.playerDashboard.idBackground.x-mFL.playerDashboard.idBackground.radiusX,
				mFL.playerDashboard.idBackground.y-mFL.playerDashboard.idBackground.radiusY,
				mFL.playerDashboard.idBackground.radiusX*2,mFL.playerDashboard.idBackground.radiusY*2,
				0,0,232,88,false,false);
		if (mFL.playerDashboard.nameLabel.texture!=null) {
			x=(int) (mFL.playerDashboard.nameLabel.x);
			y=(int) (mFL.playerDashboard.nameLabel.y-mFL.playerDashboard.nameLabel.radiusY);
			batch.draw(mFL.playerDashboard.nameLabel.texture,x,y,
					0,0,mFL.playerDashboard.nameLabel.radiusX*2,
					mFL.playerDashboard.nameLabel.radiusY*2);
		}
		if (mFL.playerDashboard.amountLabel.texture!=null) {
			x=(int) (mFL.playerDashboard.amountLabel.x);
			y=(int) (mFL.playerDashboard.amountLabel.y-mFL.playerDashboard.amountLabel.radiusY);
			batch.draw(mFL.playerDashboard.amountLabel.texture,x,y,
					0,0,mFL.playerDashboard.amountLabel.radiusX*2,
					mFL.playerDashboard.amountLabel.radiusY*2);
		}
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.playerDashboard.winLabel.opacity);
		if (mFL.playerDashboard.winLabel.texture!=null) {
			x=(int) (mFL.playerDashboard.winLabel.x-mFL.playerDashboard.winLabel.radiusX);
			y=(int) (mFL.playerDashboard.winLabel.y-mFL.playerDashboard.winLabel.radiusY);
			batch.draw(mFL.playerDashboard.winLabel.texture,x,y,
					0,0,mFL.playerDashboard.winLabel.radiusX*2,
					mFL.playerDashboard.winLabel.radiusY*2);
		}
		batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.playerDashboard.statusMessage.opacity);
		if (!mFL.playerDashboard.statusIconTexture.equals("")) {
			x=(int) (mFL.playerDashboard.statusIcon.x-mFL.playerDashboard.statusIcon.radiusX);
			y=(int) (mFL.playerDashboard.statusIcon.y-mFL.playerDashboard.statusIcon.radiusY);
			batch.draw(textureManager.get(mFL.playerDashboard.statusIconTexture,Texture.class),
					mFL.playerDashboard.statusIcon.x-mFL.playerDashboard.statusIcon.radiusX,
					mFL.playerDashboard.statusIcon.y-mFL.playerDashboard.statusIcon.radiusY,
					mFL.playerDashboard.statusIcon.radiusX*2,mFL.playerDashboard.statusIcon.radiusY*2,
					0,0,62,62,false,false);
		}
		if (mFL.playerDashboard.statusMessage.texture!=null) {
			x=(int) (mFL.playerDashboard.statusMessage.x-mFL.playerDashboard.statusMessage.radiusX);
			y=(int) (mFL.playerDashboard.statusMessage.y-mFL.playerDashboard.statusMessage.radiusY);
			batch.draw(mFL.playerDashboard.statusMessage.texture,x,y,
					0,0,mFL.playerDashboard.statusMessage.radiusX*2,
					mFL.playerDashboard.statusMessage.radiusY*2);
		}
		batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		batch.draw(backButtonTexture,mFL.playerDashboard.backButton.x-mFL.playerDashboard.backButton.radiusX,
				mFL.playerDashboard.backButton.y-mFL.playerDashboard.backButton.radiusY,
				mFL.playerDashboard.backButton.radiusX*2,mFL.playerDashboard.backButton.radiusY*2,
				0,0,238,94,false,false);
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.playerDashboard.bellButton.opacity);
		batch.draw(bellButtonTexture,mFL.playerDashboard.bellButton.x-mFL.playerDashboard.bellButton.radiusX,
				mFL.playerDashboard.bellButton.y-mFL.playerDashboard.bellButton.radiusY,
				mFL.playerDashboard.bellButton.radiusX*2,mFL.playerDashboard.bellButton.radiusY*2,
				0,0,70,78,false,false);
		batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
	}
	
	private final void renderBetTotalDialog() {
		batch.draw(dashboardStatusTexture,mFL.betTotalDialog.x-mFL.betTotalDialog.radiusX,
				mFL.betTotalDialog.y-mFL.betTotalDialog.radiusY,
				mFL.betTotalDialog.radiusX*2,mFL.betTotalDialog.radiusY*2,
				0,0,640,88,false,false);
		if (mFL.betTotalDialog.titleLabel.texture==null) {
        	mFL.betTotalDialog.titleLabel.loadTexture();
        }
		int x=(int) (mFL.betTotalDialog.titleLabel.x-mFL.betTotalDialog.titleLabel.radiusX);
		int y=(int) (mFL.betTotalDialog.titleLabel.y-mFL.betTotalDialog.titleLabel.radiusY);
		batch.draw(mFL.betTotalDialog.titleLabel.texture,x,y,
				0,0,mFL.betTotalDialog.titleLabel.radiusX*2,
				mFL.betTotalDialog.titleLabel.radiusY*2);
		if (mFL.betTotalDialog.amountLabel.texture!=null) {
			x=(int) (mFL.betTotalDialog.amountLabel.x-mFL.betTotalDialog.amountLabel.radiusX);
			y=(int) (mFL.betTotalDialog.amountLabel.y-mFL.betTotalDialog.amountLabel.radiusY);
			batch.draw(mFL.betTotalDialog.amountLabel.texture,x,y,
					0,0,mFL.betTotalDialog.amountLabel.radiusX*2,
					mFL.betTotalDialog.amountLabel.radiusY*2);
		}
	}
	
	private final void renderGameMenu() {
		batch.draw(gamePanelTexture,mFL.gameMenu.gamePanel.x-mFL.gameMenu.gamePanel.radiusX,
				mFL.gameMenu.gamePanel.y-mFL.gameMenu.gamePanel.radiusY,
				mFL.gameMenu.gamePanel.radiusX*2,mFL.gameMenu.gamePanel.radiusY*2,
				0,0,203,81,false,false);
		if (mFL.gameMenu.gamePanel.dealLabel.texture!=null) {
			int x=(int) (mFL.gameMenu.gamePanel.dealLabel.x-mFL.gameMenu.gamePanel.dealLabel.radiusX);
			int y=(int) (mFL.gameMenu.gamePanel.dealLabel.y-mFL.gameMenu.gamePanel.dealLabel.radiusY);
			batch.draw(mFL.gameMenu.gamePanel.dealLabel.texture,x,y,
					0,0,mFL.gameMenu.gamePanel.dealLabel.radiusX*2,
					mFL.gameMenu.gamePanel.dealLabel.radiusY*2);
		}
		if (mFL.gameMenu.gamePanel.potLabel.texture!=null) {
			int x=(int) (mFL.gameMenu.gamePanel.potLabel.x-mFL.gameMenu.gamePanel.potLabel.radiusX);
			int y=(int) (mFL.gameMenu.gamePanel.potLabel.y-mFL.gameMenu.gamePanel.potLabel.radiusY);
			batch.draw(mFL.gameMenu.gamePanel.potLabel.texture,x,y,
					0,0,mFL.gameMenu.gamePanel.potLabel.radiusX*2,
					mFL.gameMenu.gamePanel.potLabel.radiusY*2);
		}
		for (int i=0;i<mFL.gameMenu.playerPanels.size();i++) {
			PlayerMenuPanel thisPanel = mFL.gameMenu.playerPanels.get(i);
			batch.draw(dashboardStatusTexture,thisPanel.x-thisPanel.radiusX,
					thisPanel.y-thisPanel.radiusY,
					thisPanel.radiusX*2,thisPanel.radiusY*2,
					0,0,640,88,false,false);
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,thisPanel.opacity);
			if (thisPanel.nameLabel.texture!=null) {
				int x=(int) (thisPanel.nameLabel.x-thisPanel.nameLabel.radiusX);
				int y=(int) (thisPanel.nameLabel.y-thisPanel.nameLabel.radiusY);
				batch.draw(thisPanel.nameLabel.texture,x,y,
						0,0,thisPanel.nameLabel.radiusX*2,
						thisPanel.nameLabel.radiusY*2);
			}
			if (thisPanel.amountsLabel.texture!=null) {
				int x=(int) (thisPanel.amountsLabel.x-thisPanel.amountsLabel.radiusX);
				int y=(int) (thisPanel.amountsLabel.y-thisPanel.amountsLabel.radiusY);
				batch.draw(thisPanel.amountsLabel.texture,x,y,
						0,0,thisPanel.amountsLabel.radiusX*2,
						thisPanel.amountsLabel.radiusY*2);
			}
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
	}
	
	private final void renderStackValueLabels() {
		int x,y;
		TextLabel thisLabel = mFL.mainStackValueLabels[ChipCase.CHIP_A];
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,thisLabel.opacity);
		if (thisLabel.texture!=null) {
			x=(int) (thisLabel.x-thisLabel.radiusX);
			y=(int) (thisLabel.y-thisLabel.radiusY);
			batch.draw(thisLabel.texture,x,y,
					0,0,thisLabel.radiusX*2,
					thisLabel.radiusY*2);
		}
		thisLabel =  mFL.mainStackValueLabels[ChipCase.CHIP_B];
		if (thisLabel.texture!=null) {
			x=(int) (thisLabel.x-thisLabel.radiusX);
			y=(int) (thisLabel.y-thisLabel.radiusY);
			batch.draw(thisLabel.texture,x,y,
					0,0,thisLabel.radiusX*2,
					thisLabel.radiusY*2);
		}
		thisLabel = mFL.mainStackValueLabels[ChipCase.CHIP_C];
		if (thisLabel.texture!=null) {
			x=(int) (thisLabel.x-thisLabel.radiusX);
			y=(int) (thisLabel.y-thisLabel.radiusY);
			batch.draw(thisLabel.texture,x,y,
					0,0,thisLabel.radiusX*2,
					thisLabel.radiusY*2);
		}
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
	}
	
	private void renderBootDialog() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.bootDialog.bootButton.getLabel().opacity);
        if (mFL.bootDialog.bootButton.getLabel().texture==null) {
        	mFL.bootDialog.bootButton.getLabel().loadTexture();
        }
        batch.draw(mFL.bootDialog.bootButton.getLabel().texture,
        		mFL.bootDialog.bootButton.getLabel().x-mFL.bootDialog.bootButton.getLabel().radiusX,
				mFL.bootDialog.bootButton.getLabel().y-mFL.bootDialog.bootButton.getLabel().radiusY,
				mFL.bootDialog.bootButton.getLabel().radiusX,mFL.bootDialog.bootButton.getLabel().radiusY,
				mFL.bootDialog.bootButton.getLabel().radiusX*2,mFL.bootDialog.bootButton.getLabel().radiusY*2,
				1,1,mFL.bootDialog.rotation,0,0,
				mFL.bootDialog.bootButton.getLabel().radiusX*2,mFL.bootDialog.bootButton.getLabel().radiusY*2,false,false);
        if (mFL.bootDialog.sitOutButton.getLabel().texture==null) {
        	mFL.bootDialog.sitOutButton.getLabel().loadTexture();
        }
        batch.draw(mFL.bootDialog.sitOutButton.getLabel().texture,
        		mFL.bootDialog.sitOutButton.getLabel().x-mFL.bootDialog.sitOutButton.getLabel().radiusX,
				mFL.bootDialog.sitOutButton.getLabel().y-mFL.bootDialog.sitOutButton.getLabel().radiusY,
				mFL.bootDialog.sitOutButton.getLabel().radiusX,mFL.bootDialog.sitOutButton.getLabel().radiusY,
				mFL.bootDialog.sitOutButton.getLabel().radiusX*2,mFL.bootDialog.sitOutButton.getLabel().radiusY*2,
				1,1,mFL.bootDialog.rotation,0,0,
				mFL.bootDialog.sitOutButton.getLabel().radiusX*2,mFL.bootDialog.sitOutButton.getLabel().radiusY*2,false,false);
        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
	}
	
	private void renderPlayerLeaveDialog() {
		
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.playerLeaveDialog.text1Label.opacity);
        
        int x = (int) (mFL.playerLeaveDialog.x);
        int y = (int) (mFL.playerLeaveDialog.y);
        renderRectangleRounded(blackDialogRegions,x,y,
				mFL.playerLeaveDialog.radiusX,mFL.playerLeaveDialog.radiusY);
        batch.draw(anonTexture,
				mFL.playerLeaveDialog.anonSprite.x-mFL.playerLeaveDialog.anonSprite.radiusX,
				mFL.playerLeaveDialog.anonSprite.y-mFL.playerLeaveDialog.anonSprite.radiusY,
				mFL.playerLeaveDialog.anonSprite.radiusX*2,mFL.playerLeaveDialog.anonSprite.radiusY*2,
				0,0,70,71,false,false);
        
        if (mFL.playerLeaveDialog.text1Label.texture==null) {
        	mFL.playerLeaveDialog.text1Label.loadTexture();
        }
		x=(int) (mFL.playerLeaveDialog.text1Label.x-mFL.playerLeaveDialog.text1Label.radiusX);
		y=(int) (mFL.playerLeaveDialog.text1Label.y-mFL.playerLeaveDialog.text1Label.radiusY);
		batch.draw(mFL.playerLeaveDialog.text1Label.texture,x,y,
				0,0,mFL.playerLeaveDialog.text1Label.radiusX*2,
				mFL.playerLeaveDialog.text1Label.radiusY*2);
		
		if (mFL.playerLeaveDialog.text2Label.texture==null) {
        	mFL.playerLeaveDialog.text2Label.loadTexture();
        }
		x=(int) (mFL.playerLeaveDialog.text2Label.x-mFL.playerLeaveDialog.text2Label.radiusX);
		y=(int) (mFL.playerLeaveDialog.text2Label.y-mFL.playerLeaveDialog.text2Label.radiusY);
		batch.draw(mFL.playerLeaveDialog.text2Label.texture,x,y,
				0,0,mFL.playerLeaveDialog.text2Label.radiusX*2,
				mFL.playerLeaveDialog.text2Label.radiusY*2);
		
		x=(int) (mFL.playerLeaveDialog.cancelButton.x-mFL.playerLeaveDialog.cancelButton.radiusX);
		y=(int) (mFL.playerLeaveDialog.cancelButton.y-mFL.playerLeaveDialog.cancelButton.radiusY);
		batch.draw(btnDialogLeft,x,y,
				mFL.playerLeaveDialog.cancelButton.radiusX*2,
				mFL.playerLeaveDialog.cancelButton.radiusY*2);
		if (mFL.playerLeaveDialog.cancelButton.getLabel().texture==null) {
        	mFL.playerLeaveDialog.cancelButton.getLabel().loadTexture();
        }
		x=(int) (mFL.playerLeaveDialog.cancelButton.getLabel().x-mFL.playerLeaveDialog.cancelButton.getLabel().radiusX);
		y=(int) (mFL.playerLeaveDialog.cancelButton.getLabel().y-mFL.playerLeaveDialog.cancelButton.getLabel().radiusY);
		batch.draw(mFL.playerLeaveDialog.cancelButton.getLabel().texture,x,y,
				0,0,mFL.playerLeaveDialog.cancelButton.getLabel().radiusX*2,
				mFL.playerLeaveDialog.cancelButton.getLabel().radiusY*2);
		
		
		x=(int) (mFL.playerLeaveDialog.leaveButton.x-mFL.playerLeaveDialog.leaveButton.radiusX);
		y=(int) (mFL.playerLeaveDialog.leaveButton.y-mFL.playerLeaveDialog.leaveButton.radiusY);
		batch.draw(btnDialogRight,x,y,
				mFL.playerLeaveDialog.leaveButton.radiusX*2,
				mFL.playerLeaveDialog.leaveButton.radiusY*2);
		if (mFL.playerLeaveDialog.leaveButton.getLabel().texture==null) {
        	mFL.playerLeaveDialog.leaveButton.getLabel().loadTexture();
        }
		x=(int) (mFL.playerLeaveDialog.leaveButton.getLabel().x-mFL.playerLeaveDialog.leaveButton.getLabel().radiusX);
		y=(int) (mFL.playerLeaveDialog.leaveButton.getLabel().y-mFL.playerLeaveDialog.leaveButton.getLabel().radiusY);
		batch.draw(mFL.playerLeaveDialog.leaveButton.getLabel().texture,x,y,
				0,0,mFL.playerLeaveDialog.leaveButton.getLabel().radiusX*2,
				mFL.playerLeaveDialog.leaveButton.getLabel().radiusY*2);
		
		batch.setColor(alphaShader);
	
	}
	
	private void renderDestroyTableDialog() {
		if (mFL.destroyTableDialog.okButton.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.destroyTableDialog.okButton.opacity);
	        batch.draw(mFL.destroyTableDialog.titleLabel.texture,mFL.destroyTableDialog.titleLabel.x-mFL.destroyTableDialog.titleLabel.radiusX,
					mFL.destroyTableDialog.titleLabel.y-mFL.destroyTableDialog.titleLabel.radiusY,
					mFL.destroyTableDialog.titleLabel.radiusX*2,mFL.destroyTableDialog.titleLabel.radiusY*2,0,0,
					mFL.destroyTableDialog.titleLabel.radiusX*2,mFL.destroyTableDialog.titleLabel.radiusY*2,false,false);
	        batch.draw(okButtonTexture,mFL.destroyTableDialog.okButton.x-mFL.destroyTableDialog.okButton.radiusX,
					mFL.destroyTableDialog.okButton.y-mFL.destroyTableDialog.okButton.radiusY,
					mFL.destroyTableDialog.okButton.radiusX*2,mFL.destroyTableDialog.okButton.radiusY*2,
					0,0,214,214,false,false);
			batch.draw(cancelButtonTexture,mFL.destroyTableDialog.cancelButton.x-mFL.destroyTableDialog.cancelButton.radiusX,
					mFL.destroyTableDialog.cancelButton.y-mFL.destroyTableDialog.cancelButton.radiusY,
					mFL.destroyTableDialog.cancelButton.radiusX*2,mFL.destroyTableDialog.cancelButton.radiusY*2,
					0,0,300,300,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
	}
	
	private void renderDivisibilityDialog() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.divisibilityDialog.opacity);
        renderRectangleRounded(blackDialogRegions,(int)mFL.divisibilityDialog.x,(int)mFL.divisibilityDialog.y,
        		mFL.divisibilityDialog.radiusX,mFL.divisibilityDialog.radiusY);
        batch.draw(mFL.divisibilityDialog.messageLabel.texture,mFL.divisibilityDialog.messageLabel.x-mFL.divisibilityDialog.messageLabel.radiusX,
				mFL.divisibilityDialog.messageLabel.y-mFL.divisibilityDialog.messageLabel.radiusY,
				mFL.divisibilityDialog.messageLabel.radiusX*2,mFL.divisibilityDialog.messageLabel.radiusY*2,0,0,
				mFL.divisibilityDialog.messageLabel.radiusX*2,mFL.divisibilityDialog.messageLabel.radiusY*2,false,false);
        
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
	}
	
	private void renderPotArrows() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.potArrowLeft.opacity);
		batch.draw(arrowPotTexture,
				mFL.potArrowLeft.x-mFL.potArrowLeft.radiusX,
				mFL.potArrowLeft.y-mFL.potArrowLeft.radiusY,
				mFL.potArrowLeft.radiusX*2,mFL.potArrowLeft.radiusY*2,
				0,0,128,128,false,false);
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.potArrowRight.opacity);
		batch.draw(arrowPotTexture,
				mFL.potArrowRight.x-mFL.potArrowRight.radiusX,
				mFL.potArrowRight.y-mFL.potArrowRight.radiusY,
				mFL.potArrowRight.radiusX*2,mFL.potArrowRight.radiusY*2,
				0,0,128,128,true,true);
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
	}
	
	private void renderSplitUI() {
		alphaShader=batch.getColor();
		if (mFL.splitButton.opacity!=0) {
			 batch.setColor(alphaShader.r,alphaShader.g,
		        		alphaShader.b,mFL.splitButton.opacity);
			 batch.draw(splitButtonTexture,
						mFL.splitButton.x-mFL.splitButton.radiusX,
						mFL.splitButton.y-mFL.splitButton.radiusY,
						mFL.splitButton.radiusX*2,mFL.splitButton.radiusY*2,
						0,0,256,128,false,false);
		}
		if (mFL.splitCancelButton.opacity!=0) {
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.splitCancelButton.opacity);
			batch.draw(cancelButtonTexture,
					mFL.splitCancelButton.x-mFL.splitCancelButton.radiusX,
					mFL.splitCancelButton.y-mFL.splitCancelButton.radiusY,
					mFL.splitCancelButton.radiusX*2,mFL.splitCancelButton.radiusY*2,
					0,0,300,300,false,false);
		}
		if (mFL.splitDoneButton.opacity!=0) {
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.splitDoneButton.opacity);
			batch.draw(okButtonTexture,mFL.splitDoneButton.x-mFL.splitDoneButton.radiusX,
					mFL.splitDoneButton.y-mFL.splitDoneButton.radiusY,
					mFL.splitDoneButton.radiusX*2,mFL.splitDoneButton.radiusY*2,
					0,0,214,214,false,false);
		}
		 batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
	}
	
	private void renderRectangleRounded(TextureRegion[] textureRegions,int x,int y,int radiusX,int radiusY) {
		int cornerDim_=(int) (0.15f*Math.sqrt(radiusX*radiusY));
		int x0=x-radiusX;
		int x1=x0+cornerDim_;
		int x3=x+radiusX;
		int x2=x3-cornerDim_;
		int y0=y-radiusY;
		int y1=y0+cornerDim_;
		int y3=y+radiusY;
		int y2=y3-cornerDim_;
		
		batch.draw(textureRegions[0],x0,y2,x1-x0,y3-y2);
		batch.draw(textureRegions[1],x1,y2,x2-x1,y3-y2);
		batch.draw(textureRegions[2],x2,y2,x3-x2,y3-y2);
		
		batch.draw(textureRegions[3],x0,y1,x1-x0,y2-y1);
		batch.draw(textureRegions[4],x1,y1,x2-x1,y2-y1);
		batch.draw(textureRegions[5],x2,y1,x3-x2,y2-y1);
		
		batch.draw(textureRegions[6],x0,y0,x1-x0,y1-y0);
		batch.draw(textureRegions[7],x1,y0,x2-x1,y1-y0);
		batch.draw(textureRegions[8],x2,y0,x3-x2,y1-y0);
	}
	
	public void renderTextField(TextureRegion[] textureRegions,int x,int y,int radiusX,int radiusY) {
		float edge1Aspect = (float)(textureRegions[0].getRegionWidth())/
				(float)(textureRegions[0].getRegionHeight());
		float edge2Aspect = (float)(textureRegions[2].getRegionWidth())/
				(float)(textureRegions[2].getRegionHeight());
		int height = radiusY*2;
		int x0=x-radiusX;
		int x1=(int) (x0+height*edge1Aspect);
		int x3=x+radiusX;
		int x2=(int) (x3-height*edge2Aspect);
		
		batch.draw(textureRegions[0],x0,y-radiusY,x1-x0,height);
		batch.draw(textureRegions[1],x1,y-radiusY,x2-x1,height);
		batch.draw(textureRegions[2],x2,y-radiusY,x3-x2,height);
	}
	
	private void renderTextField(TextureRegion[] textureRegions,DPCSprite sprite) {
		renderTextField(textureRegions, (int)sprite.x, (int)sprite.y, sprite.radiusX, sprite.radiusY);
	}

	
}
