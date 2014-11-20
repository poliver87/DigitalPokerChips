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
import com.bidjee.digitalpokerchips.m.Chip;
import com.bidjee.digitalpokerchips.m.ChipCase;
import com.bidjee.digitalpokerchips.m.ChipStack;
import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.TableStatusMenu;
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
	
	Texture blackTexture;
	
	Texture plantTexture;
	Texture lampTexture;
	
	Texture[] logoDPCTextures;
	Texture helpButtonTexture;
	Texture settingsButtonTexture;
	Texture shopButtonTexture;
	
	Texture selectionTexture;
	Texture hostButtonTexture;
	Texture joinButtonTexture;
	
	Texture buttonBlueTexture;
	Texture buttonOkTexture;
	Texture buttonLoginFacebookTexture;
	
	Texture textFieldTexture;
	
	Texture wifiRedTexture;
	
	Texture backTexture;
	
	Texture cursorTexture;
	
	Texture envelopeTexture;
	Texture buyinFrameTexture;
	Texture buttonPlusTexture;
	Texture buttonMinusTexture;
	Texture buttonOkayTextureBuyin;
	Texture buttonCancelTextureBuyin;
	
	Texture playerDashboardTexture;
	Texture dashboardIdTexture;
	Texture dashboardStatusTexture;
	Texture backButtonTexture;
	
	TextureRegion blackRegion;
	TextureRegion blackHoleRegion;
	TextureRegion blackRectangleRoundedRegion;
	TextureRegion blackCircleRegion;
	
	Texture handTexture;
	
	Texture arrowTexture;
	Texture okButtonTexture;
	Texture cancelButtonTexture;
	Texture tableButtonTexture;
	
	Texture foldButtonTexture;
	
	Texture[] chipTextures;
	
	Texture closeButtonTexture;
	Texture deviceFrameTexture;
	Texture nextButtonTexture;
	
	TextureRegion[] blackDialogRegions=new TextureRegion[9];
	
	Texture buttonRedTexture;

	Texture arrowHanddrawnTexture;
	
	Texture buttonBellRed;
	
	Texture arrowPotTexture;
	
	Texture splitButtonTexture;
	
	Texture saveSlotHighlightTexture;
	Texture tableSlotTexture;
	Texture playGameTexture;
	
	Texture dialogWArrowTexture;
	
	Texture profilePicTexture;
	
	Texture dialogTexture;
	
	public ForegroundRenderer(ForegroundLayer mFL) {
		this.mFL=mFL;
		batch=new SpriteBatch(200);
		chipTextures=new Texture[ChipCase.CHIP_TYPES*Chip.CHIP_ROTATION_N];
		logoDPCTextures=new Texture[10];
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
		selectionTexture=manager.get("selection_orange.png",Texture.class);
		hostButtonTexture=manager.get("host_button.png",Texture.class);
		joinButtonTexture=manager.get("join_button.png",Texture.class);
		
		textFieldTexture=manager.get("text_field.png",Texture.class);
		cursorTexture=manager.get("cursor.png",Texture.class);
		
		envelopeTexture=manager.get("envelope.png",Texture.class);
		buyinFrameTexture=manager.get("buyin_box.png",Texture.class);
		buttonPlusTexture=manager.get("btn_plus.png",Texture.class);
		buttonMinusTexture=manager.get("btn_minus.png",Texture.class);
		buttonOkayTextureBuyin=manager.get("btn_okay_buyin.png",Texture.class);
		buttonCancelTextureBuyin=manager.get("btn_cancel.png",Texture.class);
		
		playerDashboardTexture=manager.get("dashboard.png",Texture.class);
		dashboardIdTexture=manager.get("dashboard_id.png",Texture.class);
		dashboardStatusTexture=manager.get("dashboard_status.png",Texture.class);
		backButtonTexture=manager.get("btn_back.png",Texture.class);
		
		buttonBlueTexture=manager.get("button_blue.png",Texture.class);
		buttonOkTexture=manager.get("btn_okay.png",Texture.class);
		buttonLoginFacebookTexture=manager.get("btn_login_facebook.png",Texture.class);
		wifiRedTexture=manager.get("wifi_red.png",Texture.class);
		backTexture=manager.get("back.png",Texture.class);
		
		blackTexture=manager.get("black_hole.png",Texture.class);
		blackHoleRegion=new TextureRegion(blackTexture,0,0,463,243);
		blackRegion=new TextureRegion(blackTexture,1,1,2,2);
		Texture blackRectangleRoundedTexture_=manager.get("rectangle_rounded.png",Texture.class);
		blackRectangleRoundedRegion=new TextureRegion(blackRectangleRoundedTexture_,0,0,747,246);
		Texture blackCircleTexture_=manager.get("black_circle.png",Texture.class);
		blackCircleRegion=new TextureRegion(blackCircleTexture_,0,0,128,128);
		
		handTexture=manager.get("hand.png",Texture.class);
		
		arrowTexture=manager.get("arrow.png",Texture.class);
		okButtonTexture=manager.get("ok_button.png",Texture.class);
		cancelButtonTexture=manager.get("cancel_button.png",Texture.class);
		
		foldButtonTexture=manager.get("fold_button.png",Texture.class);
		
		chipTextures[ChipCase.CHIP_A*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_0]=manager.get("chip_0_0.png",Texture.class);
		chipTextures[ChipCase.CHIP_A*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_135]=manager.get("chip_0_1.png",Texture.class);
		chipTextures[ChipCase.CHIP_A*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_202]=manager.get("chip_0_2.png",Texture.class);
		chipTextures[ChipCase.CHIP_B*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_0]=manager.get("chip_1_0.png",Texture.class);
		chipTextures[ChipCase.CHIP_B*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_135]=manager.get("chip_1_1.png",Texture.class);
		chipTextures[ChipCase.CHIP_B*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_202]=manager.get("chip_1_2.png",Texture.class);
		chipTextures[ChipCase.CHIP_C*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_0]=manager.get("chip_2_0.png",Texture.class);
		chipTextures[ChipCase.CHIP_C*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_135]=manager.get("chip_2_1.png",Texture.class);
		chipTextures[ChipCase.CHIP_C*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_202]=manager.get("chip_2_2.png",Texture.class);
		
		closeButtonTexture=manager.get("btn_close.png",Texture.class);
		deviceFrameTexture=manager.get("galaxy_frame.png",Texture.class);
		nextButtonTexture=manager.get("next_button.png",Texture.class);
		
		Texture dialogBlackFillTexture=manager.get("dialog_black_fill.png",Texture.class);
		Texture dialogBlackCornerTexture=manager.get("dialog_black_corner.png",Texture.class);
		TextureRegion dialogBlackFillRegion=new TextureRegion(dialogBlackFillTexture,0,0,16,16);
		
		blackDialogRegions[0]=new TextureRegion(dialogBlackCornerTexture,200,200);
		blackDialogRegions[1]=dialogBlackFillRegion;
		blackDialogRegions[2]=new TextureRegion(dialogBlackCornerTexture,200,0,-200,200);
		blackDialogRegions[3]=dialogBlackFillRegion;
		blackDialogRegions[4]=dialogBlackFillRegion;
		blackDialogRegions[5]=dialogBlackFillRegion;
		blackDialogRegions[6]=new TextureRegion(dialogBlackCornerTexture,0,200,200,-200);
		blackDialogRegions[7]=dialogBlackFillRegion;
		blackDialogRegions[8]=new TextureRegion(dialogBlackCornerTexture,200,200,-200,-200);
		
		tableButtonTexture=manager.get("table_button.png",Texture.class);

		buttonRedTexture=manager.get("button_red.png",Texture.class);
		
		arrowHanddrawnTexture=manager.get("arrow_handdrawn.png",Texture.class);
		
		buttonBellRed=manager.get("button_bell_red.png",Texture.class);
		arrowPotTexture=manager.get("arrow_pot.png",Texture.class);
		splitButtonTexture=manager.get("split_button.png",Texture.class);
		
		saveSlotHighlightTexture=manager.get("connection_blob.png",Texture.class);
		tableSlotTexture=manager.get("table_slot.png",Texture.class);
		playGameTexture=manager.get("table_slot.png",Texture.class);
		dialogWArrowTexture=manager.get("dialog_w_arrow.png",Texture.class);
		
		mFL.manualConnectDialog.ipQuads[0].texture=manager.get("speech_bubble_gold.png",Texture.class);
		mFL.manualConnectDialog.ipQuads[1].texture=manager.get("speech_bubble_gold.png",Texture.class);
		mFL.manualConnectDialog.ipQuads[2].texture=manager.get("speech_bubble_gold.png",Texture.class);
		mFL.manualConnectDialog.ipQuads[3].texture=manager.get("speech_bubble_gold.png",Texture.class);
		
		profilePicTexture=manager.get("anon.jpeg",Texture.class);
		
		dialogTexture=manager.get("base_login_popup.png",Texture.class);
	}
	
	public void loadLabels() {
		Gdx.app.log("DPCLifecycle", "ForegroundRenderer - loadLabels()");
		mFL.enterTableName1Label.loadTexture(whiteColor,blackColor);
		mFL.enterTableName2Label.loadTexture(whiteColor,blackColor);
		mFL.wifiLabel.loadTexture(greyColor,blackColor);
		mFL.reconnect1Label.loadTexture(greyColor,blackColor);
		mFL.reconnect2Label.loadTexture(greyColor,blackColor);
		mFL.tutorialArrangement.instrLabel.loadTexture(whiteColor,blackColor);
		mFL.selectingDealerLabel.loadTexture(greyColor,blackColor);
		mFL.gotoGameButton.getLabel().loadTexture(greyColor,blackColor);
		mFL.splitButton.getLabel().loadTexture(greyColor,blackColor);
		mFL.setValuesLabel.loadTexture(whiteColor,blackColor);
		mFL.divisibilityDialog.messageLabel.loadTexture(whiteColor,blackColor);
		mFL.autosaveDialog.titleLabel.loadTexture();
		mFL.loadDialog.titleLabel.loadTexture();
		mFL.manualConnectDialog.titleLabel.loadTexture();
		mFL.bootDialog.bootButton.getLabel().loadTexture();
		mFL.bootDialog.sitOutButton.getLabel().loadTexture();
		mFL.waitNextHandLabel.loadTexture();

		if (!mFL.winLabel.getText().equals("")) {
			mFL.winLabel.loadTexture();
		}
		if (!mFL.tableStatusMenu.tableName.getText().equals("")) {
			mFL.tableStatusMenu.tableName.loadTexture();
		}
		for (int i=0;i<mFL.tableStatusMenu.playerList.size();i++) {
			mFL.tableStatusMenu.playerList.get(i).name.loadTexture();
			mFL.tableStatusMenu.playerList.get(i).amount.loadTexture();
		}
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
        
        renderHome();
        
        if (mFL.enterNameDoneButton.opacity!=0) {
        	renderEnterName();
        }
        if (mFL.enterTableNameDoneButton.opacity!=0) {
        	renderEnterTableName();
        }
        
        if (mFL.wifiButton.opacity!=0&&mFL.wifiLabel.opacity!=0) {
        	renderNoWifi();
        }
        
        if (mFL.valueUpArrows!=null&&mFL.valueUpArrows[0]!=null&&mFL.valueUpArrows[0].opacity!=0) {
        	alphaShader=batch.getColor();
    		batch.setColor(alphaShader.r,alphaShader.g,
            		alphaShader.b,mFL.valueUpArrows[0].opacity);
        	for (int i=0;i<mFL.valueUpArrows.length;i++) {
        		batch.draw(arrowTexture,
        				mFL.valueUpArrows[i].x-mFL.valueUpArrows[i].radiusX,
        				mFL.valueUpArrows[i].y-mFL.valueUpArrows[i].radiusY,
        				mFL.valueUpArrows[i].radiusX*2,mFL.valueUpArrows[i].radiusY*2,
        				0,0,128,128,false,false);
        		batch.draw(arrowTexture,
        				mFL.valueDownArrows[i].x-mFL.valueDownArrows[i].radiusX,
        				mFL.valueDownArrows[i].y-mFL.valueDownArrows[i].radiusY,
        				mFL.valueDownArrows[i].radiusX*2,mFL.valueDownArrows[i].radiusY*2,
        				0,0,128,128,false,true);
        	}
        	batch.draw(okButtonTexture,
    				mFL.setValuesOkButton.x-mFL.setValuesOkButton.radiusX,
    				mFL.setValuesOkButton.y-mFL.setValuesOkButton.radiusY,
    				mFL.setValuesOkButton.radiusX*2,mFL.setValuesOkButton.radiusY*2,
    				0,0,214,214,false,false);
        	if (mFL.setValuesLabel.texture==null) {
				mFL.setValuesLabel.loadTexture(whiteColor,blackColor);
			}
        	batch.draw(mFL.setValuesLabel.texture,
					mFL.setValuesLabel.x-mFL.setValuesLabel.radiusX,
					mFL.setValuesLabel.y-mFL.setValuesLabel.radiusY,
					mFL.setValuesLabel.radiusX*2,mFL.setValuesLabel.radiusY*2,
					0,0,mFL.setValuesLabel.radiusX*2,mFL.setValuesLabel.radiusY*2,false,false);
        	alphaShader=batch.getColor();
            batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
            
        }
        

		renderFoldButton();
        
        if (mFL.tableStatusMenu.opacity!=0) {
			renderTableStatusMenu();
		}
        
		renderHostButtons();
        
        renderHostPrompts();
		
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
		
		renderStackValueLabels();
		
		if (mFL.dialogWArrowWindow.opacity!=0) {
			renderDialogWArrow();
		}
		
		if (mFL.helpDialog.titleLabel.opacity!=0) {
			renderHelpDialog();
		}
		
		
		if (mFL.playerLoginDialog.guestTitleLabel.opacity!=0) {
			renderPlayerLoginDialog();
		}
		
		if (mFL.buyinDialog.closeButton.opacity!=0) {
			renderBuyinDialog();
		}
		
		
		
		if (mFL.manualConnectDialog.titleLabel.opacity!=0) {
			renderManualConnectDialog();
		}
		
		if (mFL.bootDialog.bootButton.opacity!=0) {
			renderBootDialog();
		}
		
		if (mFL.leaveTableDialog.titleLabel.opacity!=0) {
			renderLeaveTableDialog();
		}
		
		if (mFL.destroyTableDialog.titleLabel.opacity!=0) {
			renderDestroyTableDialog();
		}
		
		if (mFL.autosaveDialog.titleLabel.opacity!=0) {
			renderAutosaveDialog();
		}
		
		if (mFL.loadDialog.titleLabel.opacity!=0) {
			renderLoadDialog();
		}
		
		if (mFL.divisibilityDialog.opacity!=0) {
			renderDivisibilityDialog();
		}
		
		renderPotArrows();
		renderSplitUI();
		
		if (mFL.tutorialArrangement.highlight.opacity!=0) {
			renderTutorialArrangement();
		}
		
		//renderHomeTutorial();
		
		if (DPCGame.debugMode) {
			renderGuides();
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
	

	private void renderEnterName() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.enterNameDoneButton.opacity);
        batch.draw(okButtonTexture,
				mFL.enterNameDoneButton.x-mFL.enterNameDoneButton.radiusX,
				mFL.enterNameDoneButton.y-mFL.enterNameDoneButton.radiusY,
				mFL.enterNameDoneButton.radiusX*2,mFL.enterNameDoneButton.radiusY*2,
				0,0,214,214,false,false);
        alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
	}
	
	private void renderEnterTableName() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.enterTableNameDoneButton.opacity);
        batch.draw(okButtonTexture,
				mFL.enterTableNameDoneButton.x-mFL.enterTableNameDoneButton.radiusX,
				mFL.enterTableNameDoneButton.y-mFL.enterTableNameDoneButton.radiusY,
				mFL.enterTableNameDoneButton.radiusX*2,mFL.enterTableNameDoneButton.radiusY*2,
				0,0,214,214,false,false);
        batch.draw(mFL.enterTableName1Label.texture,
				mFL.enterTableName1Label.x-mFL.enterTableName1Label.radiusX,
				mFL.enterTableName1Label.y-mFL.enterTableName1Label.radiusY,
				mFL.enterTableName1Label.radiusX*2,mFL.enterTableName1Label.radiusY*2,
				0,0,mFL.enterTableName1Label.radiusX*2,mFL.enterTableName1Label.radiusY*2,false,false);
        batch.draw(mFL.enterTableName2Label.texture,
				mFL.enterTableName2Label.x-mFL.enterTableName2Label.radiusX,
				mFL.enterTableName2Label.y-mFL.enterTableName2Label.radiusY,
				mFL.enterTableName2Label.radiusX*2,mFL.enterTableName2Label.radiusY*2,
				0,0,mFL.enterTableName2Label.radiusX*2,mFL.enterTableName2Label.radiusY*2,false,false);
        alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
	}
	
	public void renderNoWifi() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.wifiButton.opacity);
		batch.draw(wifiRedTexture,
				mFL.wifiButton.x-mFL.wifiButton.radiusX,
				mFL.wifiButton.y-mFL.wifiButton.radiusY,
				mFL.wifiButton.radiusX*2,mFL.wifiButton.radiusY*2,
				0,0,128,128, false,false);
		batch.draw(mFL.wifiLabel.texture,
				mFL.wifiLabel.x-mFL.wifiLabel.radiusX,
				mFL.wifiLabel.y-mFL.wifiLabel.radiusY,
				mFL.wifiLabel.radiusX*2,mFL.wifiLabel.radiusY*2,
				0,0,mFL.wifiLabel.radiusX*2,mFL.wifiLabel.radiusY*2,false,false);
		batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
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
        		alphaShader.b,mFL.playerLoginDialog.guestTitleLabel.opacity);
        
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
        		alphaShader.b,mFL.playerLoginDialog.guestTitleLabel.opacity);
        
        batch.draw(dialogTexture,
				mFL.playerLoginDialog.x-mFL.playerLoginDialog.radiusX,
				mFL.playerLoginDialog.y-mFL.playerLoginDialog.radiusY,
				mFL.playerLoginDialog.radiusX*2,mFL.playerLoginDialog.radiusY*2,
				0,0,582,396,false,false);
        batch.draw(closeButtonTexture,
				mFL.playerLoginDialog.closeButton.x-mFL.playerLoginDialog.closeButton.radiusX,
				mFL.playerLoginDialog.closeButton.y-mFL.playerLoginDialog.closeButton.radiusY,
				mFL.playerLoginDialog.closeButton.radiusX*2,mFL.playerLoginDialog.closeButton.radiusY*2,
				0,0,54,54,false,false);
        // title and text field left anchored
        if (mFL.playerLoginDialog.guestTitleLabel.texture==null) {
        	mFL.playerLoginDialog.guestTitleLabel.loadTexture(new Color(0.88f,0.62f,0.09f,1),new Color(0,0,0.3f,1));
        }
		int x=(int) (mFL.playerLoginDialog.guestTitleLabel.x-mFL.playerLoginDialog.guestTitleLabel.radiusX);
		int y=(int) (mFL.playerLoginDialog.guestTitleLabel.y-mFL.playerLoginDialog.guestTitleLabel.radiusY);
		batch.draw(mFL.playerLoginDialog.guestTitleLabel.texture,x,y,
				0,0,mFL.playerLoginDialog.guestTitleLabel.radiusX*2,
				mFL.playerLoginDialog.guestTitleLabel.radiusY*2);
		
		x=(int) (mFL.playerLoginDialog.nameField.x-mFL.playerLoginDialog.nameField.radiusX);
		y=(int) (mFL.playerLoginDialog.nameField.y-mFL.playerLoginDialog.nameField.radiusY);
		batch.draw(textFieldTexture,x,y,
				mFL.playerLoginDialog.nameField.radiusX*2,
				mFL.playerLoginDialog.nameField.radiusY*2,
				0,0,366,54,false,false);
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
					0,0,32,128,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.playerLoginDialog.guestTitleLabel.opacity);
		}
		
		x=(int) (mFL.playerLoginDialog.guestOKButton.x-mFL.playerLoginDialog.guestOKButton.radiusX);
		y=(int) (mFL.playerLoginDialog.guestOKButton.y-mFL.playerLoginDialog.guestOKButton.radiusY);
		batch.draw(buttonOkTexture,x,y,
				mFL.playerLoginDialog.guestOKButton.radiusX*2,
				mFL.playerLoginDialog.guestOKButton.radiusY*2,
				0,0,126,54,false,false);
		
		if (mFL.playerLoginDialog.guestOKButton.getLabel().texture==null) {
        	mFL.playerLoginDialog.guestOKButton.getLabel().loadTexture(whiteColor,new Color(0,0.4f,0,1));
        }
		x=(int) (mFL.playerLoginDialog.guestOKButton.getLabel().x-mFL.playerLoginDialog.guestOKButton.getLabel().radiusX);
		y=(int) (mFL.playerLoginDialog.guestOKButton.getLabel().y-mFL.playerLoginDialog.guestOKButton.getLabel().radiusY);
		batch.draw(mFL.playerLoginDialog.guestOKButton.getLabel().texture,x,y,
				0,0,mFL.playerLoginDialog.guestOKButton.getLabel().radiusX*2,
				mFL.playerLoginDialog.guestOKButton.getLabel().radiusY*2);
		
		
		if (mFL.playerLoginDialog.orLabel.texture==null) {
        	mFL.playerLoginDialog.orLabel.loadTexture(new Color(0.88f,0.62f,0.09f,1),new Color(0,0,0.3f,1));
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
				0,0,502,54,false,false);
		if (mFL.playerLoginDialog.facebookButton.getLabel().texture==null) {
        	mFL.playerLoginDialog.facebookButton.getLabel().loadTexture(whiteColor,new Color(0,0,0.4f,1));
        }
		x=(int) (mFL.playerLoginDialog.facebookButton.getLabel().x-mFL.playerLoginDialog.facebookButton.getLabel().radiusX);
		y=(int) (mFL.playerLoginDialog.facebookButton.getLabel().y-mFL.playerLoginDialog.facebookButton.getLabel().radiusY);
		batch.draw(mFL.playerLoginDialog.facebookButton.getLabel().texture,x,y,
				0,0,mFL.playerLoginDialog.facebookButton.getLabel().radiusX*2,
				mFL.playerLoginDialog.facebookButton.getLabel().radiusY*2);
		
		batch.setColor(alphaShader);

	}
	
	private void renderBuyinDialog() {
        
        batch.draw(dialogTexture,
				mFL.buyinDialog.x-mFL.buyinDialog.radiusX,
				mFL.buyinDialog.y-mFL.buyinDialog.radiusY,
				mFL.buyinDialog.radiusX*2,mFL.buyinDialog.radiusY*2,
				0,0,582,396,false,false);
        batch.draw(closeButtonTexture,
				mFL.buyinDialog.closeButton.x-mFL.buyinDialog.closeButton.radiusX,
				mFL.buyinDialog.closeButton.y-mFL.buyinDialog.closeButton.radiusY,
				mFL.buyinDialog.closeButton.radiusX*2,mFL.buyinDialog.closeButton.radiusY*2,
				0,0,54,54,false,false);
        batch.draw(envelopeTexture,
				mFL.buyinDialog.envelope.x-mFL.buyinDialog.envelope.radiusX,
				mFL.buyinDialog.envelope.y-mFL.buyinDialog.envelope.radiusY,
				mFL.buyinDialog.envelope.radiusX*2,mFL.buyinDialog.envelope.radiusY*2,
				0,0,110,70,false,false);
        if (mFL.buyinDialog.buyinLabel.texture==null) {
        	mFL.buyinDialog.buyinLabel.loadTexture();
        }
		int x=(int) (mFL.buyinDialog.buyinLabel.x-mFL.buyinDialog.buyinLabel.radiusX);
		int y=(int) (mFL.buyinDialog.buyinLabel.y-mFL.buyinDialog.buyinLabel.radiusY);
		batch.draw(mFL.buyinDialog.buyinLabel.texture,x,y,
				0,0,mFL.buyinDialog.buyinLabel.radiusX*2,
				mFL.buyinDialog.buyinLabel.radiusY*2);
        batch.draw(buyinFrameTexture,
				mFL.buyinDialog.buyinFrameSprite.x-mFL.buyinDialog.buyinFrameSprite.radiusX,
				mFL.buyinDialog.buyinFrameSprite.y-mFL.buyinDialog.buyinFrameSprite.radiusY,
				mFL.buyinDialog.buyinFrameSprite.radiusX*2,mFL.buyinDialog.buyinFrameSprite.radiusY*2,
				0,0,496,152,false,false);
        if (mFL.buyinDialog.amountTitleLabel.texture==null) {
        	mFL.buyinDialog.amountTitleLabel.loadTexture();
        }
		x=(int) (mFL.buyinDialog.amountTitleLabel.x-mFL.buyinDialog.amountTitleLabel.radiusX);
		y=(int) (mFL.buyinDialog.amountTitleLabel.y-mFL.buyinDialog.amountTitleLabel.radiusY);
		batch.draw(mFL.buyinDialog.amountTitleLabel.texture,x,y,
				0,0,mFL.buyinDialog.amountTitleLabel.radiusX*2,
				mFL.buyinDialog.amountTitleLabel.radiusY*2);
		batch.draw(buttonMinusTexture,
				mFL.buyinDialog.minusButton.x-mFL.buyinDialog.minusButton.radiusX,
				mFL.buyinDialog.minusButton.y-mFL.buyinDialog.minusButton.radiusY,
				mFL.buyinDialog.minusButton.radiusX*2,mFL.buyinDialog.minusButton.radiusY*2,
				0,0,54,54,false,false);
		batch.draw(buttonPlusTexture,
				mFL.buyinDialog.plusButton.x-mFL.buyinDialog.plusButton.radiusX,
				mFL.buyinDialog.plusButton.y-mFL.buyinDialog.plusButton.radiusY,
				mFL.buyinDialog.plusButton.radiusX*2,mFL.buyinDialog.plusButton.radiusY*2,
				0,0,54,54,false,false);
		batch.draw(textFieldTexture,
				mFL.buyinDialog.amountBackground.x-mFL.buyinDialog.amountBackground.radiusX,
				mFL.buyinDialog.amountBackground.y-mFL.buyinDialog.amountBackground.radiusY,
				mFL.buyinDialog.amountBackground.radiusX*2,mFL.buyinDialog.amountBackground.radiusY*2,
				0,0,366,54,false,false);
		if (mFL.buyinDialog.amountNumberLabel.texture==null) {
        	mFL.buyinDialog.amountNumberLabel.loadTexture();
        }
		x=(int) (mFL.buyinDialog.amountNumberLabel.x-mFL.buyinDialog.amountNumberLabel.radiusX);
		y=(int) (mFL.buyinDialog.amountNumberLabel.y-mFL.buyinDialog.amountNumberLabel.radiusY);
		batch.draw(mFL.buyinDialog.amountNumberLabel.texture,x,y,
				0,0,mFL.buyinDialog.amountNumberLabel.radiusX*2,
				mFL.buyinDialog.amountNumberLabel.radiusY*2);
		batch.draw(buttonCancelTextureBuyin,
				mFL.buyinDialog.cancelButton.x-mFL.buyinDialog.cancelButton.radiusX,
				mFL.buyinDialog.cancelButton.y-mFL.buyinDialog.cancelButton.radiusY,
				mFL.buyinDialog.cancelButton.radiusX*2,mFL.buyinDialog.cancelButton.radiusY*2,
				0,0,246,54,false,false);
		if (mFL.buyinDialog.cancelButton.getLabel().texture==null) {
        	mFL.buyinDialog.cancelButton.getLabel().loadTexture();
        }
		x=(int) (mFL.buyinDialog.cancelButton.getLabel().x-mFL.buyinDialog.cancelButton.getLabel().radiusX);
		y=(int) (mFL.buyinDialog.cancelButton.getLabel().y-mFL.buyinDialog.cancelButton.getLabel().radiusY);
		batch.draw(mFL.buyinDialog.cancelButton.getLabel().texture,x,y,
				0,0,mFL.buyinDialog.cancelButton.getLabel().radiusX*2,
				mFL.buyinDialog.cancelButton.getLabel().radiusY*2);
		batch.draw(buttonOkayTextureBuyin,
				mFL.buyinDialog.okayButton.x-mFL.buyinDialog.okayButton.radiusX,
				mFL.buyinDialog.okayButton.y-mFL.buyinDialog.okayButton.radiusY,
				mFL.buyinDialog.okayButton.radiusX*2,mFL.buyinDialog.okayButton.radiusY*2,
				0,0,246,54,false,false);
		if (mFL.buyinDialog.okayButton.getLabel().texture==null) {
        	mFL.buyinDialog.okayButton.getLabel().loadTexture();
        }
		x=(int) (mFL.buyinDialog.okayButton.getLabel().x-mFL.buyinDialog.okayButton.getLabel().radiusX);
		y=(int) (mFL.buyinDialog.okayButton.getLabel().y-mFL.buyinDialog.okayButton.getLabel().radiusY);
		batch.draw(mFL.buyinDialog.okayButton.getLabel().texture,x,y,
				0,0,mFL.buyinDialog.okayButton.getLabel().radiusX*2,
				mFL.buyinDialog.okayButton.getLabel().radiusY*2);
	}

	private void renderPlayerDashboard() {
		batch.draw(playerDashboardTexture,mFL.playerDashboard.x-mFL.playerDashboard.radiusX,
				mFL.playerDashboard.y-mFL.playerDashboard.radiusY,
				mFL.playerDashboard.radiusX*2,mFL.playerDashboard.radiusY*2,
				0,0,1142,110,false,false);
		
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
		batch.setColor(alphaShader);
		batch.draw(backButtonTexture,mFL.playerDashboard.backButton.x-mFL.playerDashboard.backButton.radiusX,
				mFL.playerDashboard.backButton.y-mFL.playerDashboard.backButton.radiusY,
				mFL.playerDashboard.backButton.radiusX*2,mFL.playerDashboard.backButton.radiusY*2,
				0,0,238,94,false,false);
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
	
	private void renderManualConnectDialog() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.manualConnectDialog.okButton.opacity);
        
        batch.draw(mFL.manualConnectDialog.titleLabel.texture,mFL.manualConnectDialog.titleLabel.x-mFL.manualConnectDialog.titleLabel.radiusX,
				mFL.manualConnectDialog.titleLabel.y-mFL.manualConnectDialog.titleLabel.radiusY,
				mFL.manualConnectDialog.titleLabel.radiusX*2,mFL.manualConnectDialog.titleLabel.radiusY*2,0,0,
				mFL.manualConnectDialog.titleLabel.radiusX*2,mFL.manualConnectDialog.titleLabel.radiusY*2,false,false);
        batch.draw(okButtonTexture,mFL.manualConnectDialog.okButton.x-mFL.manualConnectDialog.okButton.radiusX,
				mFL.manualConnectDialog.okButton.y-mFL.manualConnectDialog.okButton.radiusY,
				mFL.manualConnectDialog.okButton.radiusX*2,mFL.manualConnectDialog.okButton.radiusY*2,
				0,0,214,214,false,false);
		batch.draw(cancelButtonTexture,mFL.manualConnectDialog.cancelButton.x-mFL.manualConnectDialog.cancelButton.radiusX,
				mFL.manualConnectDialog.cancelButton.y-mFL.manualConnectDialog.cancelButton.radiusY,
				mFL.manualConnectDialog.cancelButton.radiusX*2,mFL.manualConnectDialog.cancelButton.radiusY*2,
				0,0,300,300,false,false);
		for (int i=0;i<mFL.manualConnectDialog.ipQuads.length;i++) {
			batch.draw(mFL.manualConnectDialog.ipQuads[i].texture,
					mFL.manualConnectDialog.ipQuads[i].x-mFL.manualConnectDialog.ipQuads[i].radiusX,
					mFL.manualConnectDialog.ipQuads[i].y-mFL.manualConnectDialog.ipQuads[i].radiusY,
					mFL.manualConnectDialog.ipQuads[i].radiusX*2,mFL.manualConnectDialog.ipQuads[i].radiusY*2,
					0,0,440,251,false,false);
			if (mFL.manualConnectDialog.ipQuads[i].label.texture!=null) {
				batch.draw(mFL.manualConnectDialog.ipQuads[i].label.texture,
						mFL.manualConnectDialog.ipQuads[i].label.x-mFL.manualConnectDialog.ipQuads[i].label.radiusX,
						mFL.manualConnectDialog.ipQuads[i].label.y-mFL.manualConnectDialog.ipQuads[i].label.radiusY,
						mFL.manualConnectDialog.ipQuads[i].label.radiusX*2,mFL.manualConnectDialog.ipQuads[i].label.radiusY*2,0,0,
						mFL.manualConnectDialog.ipQuads[i].label.radiusX*2,mFL.manualConnectDialog.ipQuads[i].label.radiusY*2,false,false);
			}
		}
		batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,1);
	}
	
	private void renderBootDialog() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,mFL.bootDialog.bootButton.getLabel().opacity);
        batch.draw(mFL.bootDialog.bootButton.getLabel().texture,
        		mFL.bootDialog.bootButton.getLabel().x-mFL.bootDialog.bootButton.getLabel().radiusX,
				mFL.bootDialog.bootButton.getLabel().y-mFL.bootDialog.bootButton.getLabel().radiusY,
				mFL.bootDialog.bootButton.getLabel().radiusX,mFL.bootDialog.bootButton.getLabel().radiusY,
				mFL.bootDialog.bootButton.getLabel().radiusX*2,mFL.bootDialog.bootButton.getLabel().radiusY*2,
				1,1,mFL.bootDialog.rotation,0,0,
				mFL.bootDialog.bootButton.getLabel().radiusX*2,mFL.bootDialog.bootButton.getLabel().radiusY*2,false,false);
        batch.draw(mFL.bootDialog.sitOutButton.getLabel().texture,
        		mFL.bootDialog.sitOutButton.getLabel().x-mFL.bootDialog.sitOutButton.getLabel().radiusX,
				mFL.bootDialog.sitOutButton.getLabel().y-mFL.bootDialog.sitOutButton.getLabel().radiusY,
				mFL.bootDialog.sitOutButton.getLabel().radiusX,mFL.bootDialog.sitOutButton.getLabel().radiusY,
				mFL.bootDialog.sitOutButton.getLabel().radiusX*2,mFL.bootDialog.sitOutButton.getLabel().radiusY*2,
				1,1,mFL.bootDialog.rotation,0,0,
				mFL.bootDialog.sitOutButton.getLabel().radiusX*2,mFL.bootDialog.sitOutButton.getLabel().radiusY*2,false,false);
        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
	}
	
	private void renderLeaveTableDialog() {
		
		if (mFL.leaveTableDialog.okButton.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.leaveTableDialog.okButton.opacity);
	        
	        batch.draw(mFL.leaveTableDialog.titleLabel.texture,mFL.leaveTableDialog.titleLabel.x-mFL.leaveTableDialog.titleLabel.radiusX,
					mFL.leaveTableDialog.titleLabel.y-mFL.leaveTableDialog.titleLabel.radiusY,
					mFL.leaveTableDialog.titleLabel.radiusX*2,mFL.leaveTableDialog.titleLabel.radiusY*2,0,0,
					mFL.leaveTableDialog.titleLabel.radiusX*2,mFL.leaveTableDialog.titleLabel.radiusY*2,false,false);
	        batch.draw(okButtonTexture,mFL.leaveTableDialog.okButton.x-mFL.leaveTableDialog.okButton.radiusX,
					mFL.leaveTableDialog.okButton.y-mFL.leaveTableDialog.okButton.radiusY,
					mFL.leaveTableDialog.okButton.radiusX*2,mFL.leaveTableDialog.okButton.radiusY*2,
					0,0,214,214,false,false);
			batch.draw(cancelButtonTexture,mFL.leaveTableDialog.cancelButton.x-mFL.leaveTableDialog.cancelButton.radiusX,
					mFL.leaveTableDialog.cancelButton.y-mFL.leaveTableDialog.cancelButton.radiusY,
					mFL.leaveTableDialog.cancelButton.radiusX*2,mFL.leaveTableDialog.cancelButton.radiusY*2,
					0,0,300,300,false,false);
			
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
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

	private void renderAutosaveDialog() {
		
		if (mFL.autosaveDialog.okButton.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.autosaveDialog.okButton.opacity);
	        
	        batch.draw(mFL.autosaveDialog.titleLabel.texture,mFL.autosaveDialog.titleLabel.x-mFL.autosaveDialog.titleLabel.radiusX,
					mFL.autosaveDialog.titleLabel.y-mFL.autosaveDialog.titleLabel.radiusY,
					mFL.autosaveDialog.titleLabel.radiusX*2,mFL.autosaveDialog.titleLabel.radiusY*2,0,0,
					mFL.autosaveDialog.titleLabel.radiusX*2,mFL.autosaveDialog.titleLabel.radiusY*2,false,false);
	        
			batch.draw(okButtonTexture,mFL.autosaveDialog.okButton.x-mFL.autosaveDialog.okButton.radiusX,
					mFL.autosaveDialog.okButton.y-mFL.autosaveDialog.okButton.radiusY,
					mFL.autosaveDialog.okButton.radiusX*2,mFL.autosaveDialog.okButton.radiusY*2,
					0,0,214,214,false,false);
			
			for (int i=0;i<mFL.autosaveDialog.saveSlots.length;i++) {
				DPCSprite thisSelectionHighlight=mFL.autosaveDialog.saveSlots[i].selectionHighlight;
				if (thisSelectionHighlight.opacity!=0) {
					alphaShader=batch.getColor();
					batch.setColor(alphaShader.r,alphaShader.g,
			        		alphaShader.b,thisSelectionHighlight.opacity);
					batch.draw(saveSlotHighlightTexture,thisSelectionHighlight.x-thisSelectionHighlight.radiusX,
							thisSelectionHighlight.y-thisSelectionHighlight.radiusY,
							thisSelectionHighlight.radiusX*2,thisSelectionHighlight.radiusY*2,
							0,0,312,192,false,false);
					batch.setColor(alphaShader);
				}
				if (mFL.autosaveDialog.saveSlots[i].slotLabel.opacity!=0) {
					alphaShader=batch.getColor();
					batch.setColor(alphaShader.r,alphaShader.g,
							alphaShader.b,mFL.autosaveDialog.saveSlots[i].slotLabel.opacity);
					batch.draw(mFL.autosaveDialog.saveSlots[i].slotLabel.texture,
							mFL.autosaveDialog.saveSlots[i].slotLabel.x-mFL.autosaveDialog.saveSlots[i].slotLabel.radiusX,
							mFL.autosaveDialog.saveSlots[i].slotLabel.y-mFL.autosaveDialog.saveSlots[i].slotLabel.radiusY,
							mFL.autosaveDialog.saveSlots[i].slotLabel.radiusX*2,mFL.autosaveDialog.saveSlots[i].slotLabel.radiusY*2,
							0,0,mFL.autosaveDialog.saveSlots[i].slotLabel.radiusX*2,mFL.autosaveDialog.saveSlots[i].slotLabel.radiusY*2,false,false);
					batch.setColor(alphaShader);
				}
				if (mFL.autosaveDialog.saveSlots[i].tableIcon.opacity!=0) {
					alphaShader=batch.getColor();
					batch.setColor(alphaShader.r,alphaShader.g,
							alphaShader.b,mFL.autosaveDialog.saveSlots[i].tableIcon.opacity);
					batch.draw(tableSlotTexture,
							mFL.autosaveDialog.saveSlots[i].tableIcon.x-mFL.autosaveDialog.saveSlots[i].tableIcon.radiusX,
							mFL.autosaveDialog.saveSlots[i].tableIcon.y-mFL.autosaveDialog.saveSlots[i].tableIcon.radiusY,
							mFL.autosaveDialog.saveSlots[i].tableIcon.radiusX*2,mFL.autosaveDialog.saveSlots[i].tableIcon.radiusY*2,
							0,0,300,150,false,false);
					batch.setColor(alphaShader);
				}
			}
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
	}
	
	private void renderLoadDialog() {
		if (mFL.loadDialog.okButton.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.loadDialog.okButton.opacity);
	        
	        batch.draw(mFL.loadDialog.titleLabel.texture,mFL.loadDialog.titleLabel.x-mFL.loadDialog.titleLabel.radiusX,
					mFL.loadDialog.titleLabel.y-mFL.loadDialog.titleLabel.radiusY,
					mFL.loadDialog.titleLabel.radiusX*2,mFL.loadDialog.titleLabel.radiusY*2,0,0,
					mFL.loadDialog.titleLabel.radiusX*2,mFL.loadDialog.titleLabel.radiusY*2,false,false);
	        
			batch.draw(okButtonTexture,mFL.loadDialog.okButton.x-mFL.loadDialog.okButton.radiusX,
					mFL.loadDialog.okButton.y-mFL.loadDialog.okButton.radiusY,
					mFL.loadDialog.okButton.radiusX*2,mFL.loadDialog.okButton.radiusY*2,
					0,0,214,214,false,false);
			batch.draw(cancelButtonTexture,mFL.loadDialog.cancelButton.x-mFL.loadDialog.cancelButton.radiusX,
					mFL.loadDialog.cancelButton.y-mFL.loadDialog.cancelButton.radiusY,
					mFL.loadDialog.cancelButton.radiusX*2,mFL.loadDialog.cancelButton.radiusY*2,
					0,0,300,300,false,false);
			
			for (int i=0;i<mFL.loadDialog.loadSlots.length;i++) {
				DPCSprite thisSelectionHighlight=mFL.loadDialog.loadSlots[i].selectionHighlight;
				if (thisSelectionHighlight.opacity!=0) {
					alphaShader=batch.getColor();
					batch.setColor(alphaShader.r,alphaShader.g,
			        		alphaShader.b,thisSelectionHighlight.opacity);
					batch.draw(saveSlotHighlightTexture,thisSelectionHighlight.x-thisSelectionHighlight.radiusX,
							thisSelectionHighlight.y-thisSelectionHighlight.radiusY,
							thisSelectionHighlight.radiusX*2,thisSelectionHighlight.radiusY*2,
							0,0,312,192,false,false);
					batch.setColor(alphaShader);
				}
				if (mFL.loadDialog.loadSlots[i].slotLabel.opacity!=0) {
					alphaShader=batch.getColor();
					batch.setColor(alphaShader.r,alphaShader.g,
							alphaShader.b,mFL.loadDialog.loadSlots[i].slotLabel.opacity);
					batch.draw(mFL.loadDialog.loadSlots[i].slotLabel.texture,
							mFL.loadDialog.loadSlots[i].slotLabel.x-mFL.loadDialog.loadSlots[i].slotLabel.radiusX,
							mFL.loadDialog.loadSlots[i].slotLabel.y-mFL.loadDialog.loadSlots[i].slotLabel.radiusY,
							mFL.loadDialog.loadSlots[i].slotLabel.radiusX*2,mFL.loadDialog.loadSlots[i].slotLabel.radiusY*2,
							0,0,mFL.loadDialog.loadSlots[i].slotLabel.radiusX*2,mFL.loadDialog.loadSlots[i].slotLabel.radiusY*2,false,false);
					batch.setColor(alphaShader);
				}
				if (mFL.loadDialog.loadSlots[i].tableIcon.opacity!=0) {
					alphaShader=batch.getColor();
					batch.setColor(alphaShader.r,alphaShader.g,
							alphaShader.b,mFL.loadDialog.loadSlots[i].tableIcon.opacity);
					batch.draw(tableSlotTexture,
							mFL.loadDialog.loadSlots[i].tableIcon.x-mFL.loadDialog.loadSlots[i].tableIcon.radiusX,
							mFL.loadDialog.loadSlots[i].tableIcon.y-mFL.loadDialog.loadSlots[i].tableIcon.radiusY,
							mFL.loadDialog.loadSlots[i].tableIcon.radiusX*2,mFL.loadDialog.loadSlots[i].tableIcon.radiusY*2,
							0,0,300,150,false,false);
					batch.setColor(alphaShader);
				}
			}
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
	
	private void renderHostPrompts() {

		if (mFL.selectingDealerLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.selectingDealerLabel.opacity);
			batch.draw(mFL.selectingDealerLabel.texture,mFL.selectingDealerLabel.x-mFL.selectingDealerLabel.radiusX,
					mFL.selectingDealerLabel.y-mFL.selectingDealerLabel.radiusY,
					mFL.selectingDealerLabel.radiusX*2,mFL.selectingDealerLabel.radiusY*2,0,0,
					mFL.selectingDealerLabel.radiusX*2,mFL.selectingDealerLabel.radiusY*2,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
		
		if (mFL.winLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.winLabel.opacity);
			batch.draw(mFL.winLabel.texture,mFL.winLabel.x-mFL.winLabel.radiusX,
					mFL.winLabel.y-mFL.winLabel.radiusY,
					mFL.winLabel.radiusX*2,mFL.winLabel.radiusY*2,0,0,mFL.winLabel.radiusX*2,mFL.winLabel.radiusY*2,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
		if (mFL.blindsInLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.blindsInLabel.opacity);
	        if (mFL.blindsInLabel.texture==null) {
	        	mFL.blindsInLabel.loadTexture();
	        }
			batch.draw(mFL.blindsInLabel.texture,mFL.blindsInLabel.x-mFL.blindsInLabel.radiusX,
					mFL.blindsInLabel.y-mFL.blindsInLabel.radiusY,
					mFL.blindsInLabel.radiusX*2,mFL.blindsInLabel.radiusY*2,0,0,
					mFL.blindsInLabel.radiusX*2,mFL.blindsInLabel.radiusY*2,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
		if (mFL.flopLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.flopLabel.opacity);
	        if (mFL.flopLabel.texture==null) {
	        	mFL.flopLabel.loadTexture();
	        }
			batch.draw(mFL.flopLabel.texture,mFL.flopLabel.x-mFL.flopLabel.radiusX,
					mFL.flopLabel.y-mFL.flopLabel.radiusY,
					mFL.flopLabel.radiusX*2,mFL.flopLabel.radiusY*2,0,0,
					mFL.flopLabel.radiusX*2,mFL.flopLabel.radiusY*2,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
		if (mFL.turnLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.turnLabel.opacity);
	        if (mFL.turnLabel.texture==null) {
	        	mFL.turnLabel.loadTexture();
	        }
			batch.draw(mFL.turnLabel.texture,mFL.turnLabel.x-mFL.turnLabel.radiusX,
					mFL.turnLabel.y-mFL.turnLabel.radiusY,
					mFL.turnLabel.radiusX*2,mFL.turnLabel.radiusY*2,0,0,
					mFL.turnLabel.radiusX*2,mFL.turnLabel.radiusY*2,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
		if (mFL.riverLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.riverLabel.opacity);
	        if (mFL.riverLabel.texture==null) {
	        	mFL.riverLabel.loadTexture();
	        }
			batch.draw(mFL.riverLabel.texture,mFL.riverLabel.x-mFL.riverLabel.radiusX,
					mFL.riverLabel.y-mFL.riverLabel.radiusY,
					mFL.riverLabel.radiusX*2,mFL.riverLabel.radiusY*2,0,0,
					mFL.riverLabel.radiusX*2,mFL.riverLabel.radiusY*2,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
		if (mFL.selectWinnerLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.selectWinnerLabel.opacity);
	        if (mFL.selectWinnerLabel.texture==null) {
	        	mFL.selectWinnerLabel.loadTexture();
	        }
			batch.draw(mFL.selectWinnerLabel.texture,mFL.selectWinnerLabel.x-mFL.selectWinnerLabel.radiusX,
					mFL.selectWinnerLabel.y-mFL.selectWinnerLabel.radiusY,
					mFL.selectWinnerLabel.radiusX*2,mFL.selectWinnerLabel.radiusY*2,0,0,
					mFL.selectWinnerLabel.radiusX*2,mFL.selectWinnerLabel.radiusY*2,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
		if (mFL.ipAddress.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.ipAddress.opacity);
			batch.draw(mFL.ipAddress.texture,mFL.ipAddress.x-mFL.ipAddress.radiusX,
					mFL.ipAddress.y-mFL.ipAddress.radiusY,
					mFL.ipAddress.radiusX*2,mFL.ipAddress.radiusY*2,0,0,
					mFL.ipAddress.radiusX*2,mFL.ipAddress.radiusY*2,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
	}
	
	private void renderTableStatusMenu() {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,0.7f);
		batch.draw(blackRegion,mFL.tableStatusMenu.x-mFL.tableStatusMenu.radiusX,
				mFL.tableStatusMenu.y-mFL.tableStatusMenu.radiusY,
				mFL.tableStatusMenu.radiusX*2,mFL.tableStatusMenu.radiusY*2);
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
        TableStatusMenu menu=mFL.tableStatusMenu;
		for (int i=0;i<menu.playerList.size();i++) {
			batch.draw(menu.playerList.get(i).name.texture,menu.xPlayerEntryNameLeft,menu.yPlayerEntryBottom-i*menu.yPlayerEntryPitch,
					menu.playerList.get(i).name.radiusX*2,menu.playerList.get(i).name.radiusY*2,
					0,0,menu.playerList.get(i).name.radiusX*2,menu.playerList.get(i).name.radiusY*2,false,false);
			batch.draw(menu.playerList.get(i).amount.texture,menu.xPlayerEntryAmountLeft,menu.yPlayerEntryBottom-i*menu.yPlayerEntryPitch,
					menu.playerList.get(i).amount.radiusX*2,menu.playerList.get(i).amount.radiusY*2,
					0,0,menu.playerList.get(i).amount.radiusX*2,menu.playerList.get(i).amount.radiusY*2,false,false);
		}
		if (mFL.tableStatusMenu.bellButton.opacity!=0) {
			batch.draw(buttonBellRed,menu.bellButton.x-menu.bellButton.radiusX,
					menu.bellButton.y-menu.bellButton.radiusY,
					menu.bellButton.radiusX*2,menu.bellButton.radiusY*2,
					0,0,118,118,false,false);
		}
		batch.draw(buttonRedTexture,mFL.tableStatusMenu.leaveButton.x-mFL.tableStatusMenu.leaveButton.radiusX,
				mFL.tableStatusMenu.leaveButton.y-mFL.tableStatusMenu.leaveButton.radiusY,
				mFL.tableStatusMenu.leaveButton.radiusX*2,mFL.tableStatusMenu.leaveButton.radiusY*2,
				0,0,152,37,false,false);
		if (mFL.tableStatusMenu.leaveButton.getLabel()==null) {
			mFL.tableStatusMenu.leaveButton.getLabel().loadTexture();
		}
		batch.draw(mFL.tableStatusMenu.leaveButton.getLabel().texture,mFL.tableStatusMenu.leaveButton.getLabel().x-mFL.tableStatusMenu.leaveButton.getLabel().radiusX,
				mFL.tableStatusMenu.leaveButton.getLabel().y-mFL.tableStatusMenu.leaveButton.getLabel().radiusY,
				mFL.tableStatusMenu.leaveButton.getLabel().radiusX*2,mFL.tableStatusMenu.leaveButton.getLabel().radiusY*2,
				0,0,mFL.tableStatusMenu.leaveButton.getLabel().radiusX*2,mFL.tableStatusMenu.leaveButton.getLabel().radiusY*2,false,false);
        batch.draw(tableButtonTexture,mFL.tableStatusMenu.handle.x-mFL.tableStatusMenu.handle.radiusX,
				mFL.tableStatusMenu.handle.y-mFL.tableStatusMenu.handle.radiusY,
				mFL.tableStatusMenu.handle.radiusX*2,mFL.tableStatusMenu.handle.radiusY*2,
				0,0,100,400,false,false);
        batch.draw(mFL.tableStatusMenu.tableName.texture,mFL.tableStatusMenu.tableName.x-mFL.tableStatusMenu.tableName.radiusX,
				mFL.tableStatusMenu.tableName.y-mFL.tableStatusMenu.tableName.radiusY,
				mFL.tableStatusMenu.tableName.radiusX*2,mFL.tableStatusMenu.tableName.radiusY*2,
				0,0,mFL.tableStatusMenu.tableName.radiusX*2,mFL.tableStatusMenu.tableName.radiusY*2,false,false);
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
	}
	
	private void renderHostButtons() {
        if (mFL.gotoGameButton.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.gotoGameButton.opacity);
	        batch.draw(playGameTexture,mFL.gotoGameButton.x-mFL.gotoGameButton.radiusX,
					mFL.gotoGameButton.y-mFL.gotoGameButton.radiusY,
					mFL.gotoGameButton.radiusX*2,mFL.gotoGameButton.radiusY*2,
					0,0,300,150,false,false);
			batch.draw(mFL.gotoGameButton.getLabel().texture,
					mFL.gotoGameButton.getLabel().x-mFL.gotoGameButton.getLabel().radiusX,
					mFL.gotoGameButton.getLabel().y-mFL.gotoGameButton.getLabel().radiusY,
					mFL.gotoGameButton.getLabel().radiusX*2,mFL.gotoGameButton.getLabel().radiusY*2,0,0,
					mFL.gotoGameButton.getLabel().radiusX*2,mFL.gotoGameButton.getLabel().radiusY*2,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
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
			 TextLabel label=mFL.splitButton.getLabel();
			 batch.draw(label.texture,
					 label.x-label.radiusX,
					 label.y-label.radiusY,
					 label.radiusX*2,label.radiusY*2,
						0,0,label.radiusX*2,label.radiusY*2,false,false);
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
		if (mFL.selectWinnersSplitLabel.opacity!=0) {
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.selectWinnersSplitLabel.opacity);
			if (mFL.selectWinnersSplitLabel.texture==null) {
				mFL.selectWinnersSplitLabel.loadTexture();
			}
			batch.draw(mFL.selectWinnersSplitLabel.texture,mFL.selectWinnersSplitLabel.x-mFL.selectWinnersSplitLabel.radiusX,
					mFL.selectWinnersSplitLabel.y-mFL.selectWinnersSplitLabel.radiusY,
					mFL.selectWinnersSplitLabel.radiusX*2,mFL.selectWinnersSplitLabel.radiusY*2,
					0,0,mFL.selectWinnersSplitLabel.radiusX*2,mFL.selectWinnersSplitLabel.radiusY*2,false,false);
		}
		 batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
	}
	
	private void renderTutorialArrangement() {
		renderHighlightDarkened(mFL.tutorialArrangement.highlight,blackCircleRegion);
		if (mFL.tutorialArrangement.instrLabel.opacity!=0) {
			alphaShader=batch.getColor();
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.tutorialArrangement.instrLabel.opacity);
			batch.draw(mFL.tutorialArrangement.instrLabel.texture,
					mFL.tutorialArrangement.instrLabel.x-mFL.tutorialArrangement.instrLabel.radiusX,
					mFL.tutorialArrangement.instrLabel.y-mFL.tutorialArrangement.instrLabel.radiusY,
					mFL.tutorialArrangement.instrLabel.radiusX*2,mFL.tutorialArrangement.instrLabel.radiusY*2,
					0,0,mFL.tutorialArrangement.instrLabel.radiusX*2,mFL.tutorialArrangement.instrLabel.radiusY*2,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
	}
	
	private void renderHighlightDarkened(DPCSprite highlight_,TextureRegion highlightRegion_) {
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,
        		alphaShader.b,highlight_.opacity);
        
        int left_=(int) (highlight_.x-highlight_.radiusX);
        int right_=(int) (highlight_.x+highlight_.radiusX);
        int bottom_=(int) (highlight_.y-highlight_.radiusY);
        int top_=(int) (highlight_.y+highlight_.radiusY);
		batch.draw(highlightRegion_,left_,bottom_,right_-left_,top_-bottom_);
		int width_=0;
		int height_=0;
		width_=left_;
		if (width_>0) {
			batch.draw(blackRegion,0,0,width_,mFL.screenHeight);
		}
		width_=(int) (mFL.screenWidth-right_);
		if (width_>0) {
			batch.draw(blackRegion,right_,0,width_,mFL.screenHeight);
		}
		height_=bottom_;
		if (height_>0) {
			batch.draw(blackRegion,left_,0,right_-left_,height_);
		}
		height_=(int) (mFL.screenHeight-top_);
		if (height_>0) {
			batch.draw(blackRegion,left_,top_,right_-left_,height_);
		}
		alphaShader=batch.getColor();
        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
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

	private void renderGuides() {
		for (int i=1;i<=10;i++) {
			batch.draw(handTexture,0,screenHeight*0.1f*i,
					4,4,213,201,1,1,false,false);
		}
		for (int i=1;i<=10;i++) {
			batch.draw(handTexture,screenWidth*0.1f*i,1,
					4,4,213,201,1,1,false,false);
		}
	}

	
}
