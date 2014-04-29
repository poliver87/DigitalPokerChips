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
	
	SpriteBatch batch;
	final Matrix4 viewMatrix=new Matrix4();
	Color alphaShader;
	
	ForegroundLayer mFL;

	int screenWidth;
	int screenHeight;
	
	Texture blackTexture;
	
	Texture logoDPCTexture;
	Texture buttonBlueTexture;
	
	Texture wifiRedTexture;
	
	Texture backTexture;
	
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
	Texture backgroundHelpTexture;
	Texture nextButtonTexture;
	
	TextureRegion[] blackDialogRegions=new TextureRegion[9];
	
	Texture pingTexture;
	Texture pingHubTexture;
	
	Texture buttonRedTexture;

	Texture arrowHanddrawnTexture;
	
	Texture buttonBellRed;
	
	Texture arrowPotTexture;
	
	Texture splitButtonTexture;
	
	Texture saveSlotHighlightTexture;
	Texture tableSlotTexture;
	Texture playGameTexture;
	
	Texture dialogWArrowTexture;
	
	public ForegroundRenderer(ForegroundLayer mFL) {
		this.mFL=mFL;
		batch=new SpriteBatch(200);
		chipTextures=new Texture[ChipCase.CHIP_TYPES*Chip.CHIP_ROTATION_N];
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
	
	public void loadTextures(AssetManager manager_) {
		Gdx.app.log("DPCLifecycle", "ForegroundRenderer - loadTextures()");
		logoDPCTexture=manager_.get("logo_dpc.png",Texture.class);
		buttonBlueTexture=manager_.get("button_blue.png",Texture.class);
		wifiRedTexture=manager_.get("wifi_red.png",Texture.class);
		backTexture=manager_.get("back.png",Texture.class);
		
		blackTexture=manager_.get("black_hole.png",Texture.class);
		blackHoleRegion=new TextureRegion(blackTexture,0,0,463,243);
		blackRegion=new TextureRegion(blackTexture,1,1,2,2);
		Texture blackRectangleRoundedTexture_=manager_.get("rectangle_rounded.png",Texture.class);
		blackRectangleRoundedRegion=new TextureRegion(blackRectangleRoundedTexture_,0,0,747,246);
		Texture blackCircleTexture_=manager_.get("black_circle.png",Texture.class);
		blackCircleRegion=new TextureRegion(blackCircleTexture_,0,0,128,128);
		
		handTexture=manager_.get("hand.png",Texture.class);
		
		arrowTexture=manager_.get("arrow.png",Texture.class);
		okButtonTexture=manager_.get("ok_button.png",Texture.class);
		cancelButtonTexture=manager_.get("cancel_button.png",Texture.class);
		
		foldButtonTexture=manager_.get("fold_button.png",Texture.class);
		
		chipTextures[ChipCase.CHIP_A*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_0]=manager_.get("chip_0_0.png",Texture.class);
		chipTextures[ChipCase.CHIP_A*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_135]=manager_.get("chip_0_1.png",Texture.class);
		chipTextures[ChipCase.CHIP_A*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_202]=manager_.get("chip_0_2.png",Texture.class);
		chipTextures[ChipCase.CHIP_B*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_0]=manager_.get("chip_1_0.png",Texture.class);
		chipTextures[ChipCase.CHIP_B*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_135]=manager_.get("chip_1_1.png",Texture.class);
		chipTextures[ChipCase.CHIP_B*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_202]=manager_.get("chip_1_2.png",Texture.class);
		chipTextures[ChipCase.CHIP_C*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_0]=manager_.get("chip_2_0.png",Texture.class);
		chipTextures[ChipCase.CHIP_C*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_135]=manager_.get("chip_2_1.png",Texture.class);
		chipTextures[ChipCase.CHIP_C*Chip.CHIP_ROTATION_N+Chip.CHIP_ROTATION_202]=manager_.get("chip_2_2.png",Texture.class);
		
		closeButtonTexture=manager_.get("close_button.png",Texture.class);
		deviceFrameTexture=manager_.get("galaxy_frame.png",Texture.class);
		backgroundHelpTexture=manager_.get("background_help.png",Texture.class);
		nextButtonTexture=manager_.get("next_button.png",Texture.class);
		
		Texture dialogBlackFillTexture=manager_.get("dialog_black_fill.png",Texture.class);
		Texture dialogBlackCornerTexture=manager_.get("dialog_black_corner.png",Texture.class);
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
		
		tableButtonTexture=manager_.get("table_button.png",Texture.class);
		
		pingTexture=manager_.get("ping.png",Texture.class);
		pingHubTexture=manager_.get("ping_hub.png",Texture.class);
		buttonRedTexture=manager_.get("button_red.png",Texture.class);
		
		arrowHanddrawnTexture=manager_.get("arrow_handdrawn.png",Texture.class);
		
		buttonBellRed=manager_.get("button_bell_red.png",Texture.class);
		arrowPotTexture=manager_.get("arrow_pot.png",Texture.class);
		splitButtonTexture=manager_.get("split_button.png",Texture.class);
		
		saveSlotHighlightTexture=manager_.get("connection_blob.png",Texture.class);
		tableSlotTexture=manager_.get("table_slot.png",Texture.class);
		playGameTexture=manager_.get("table_slot.png",Texture.class);
		dialogWArrowTexture=manager_.get("dialog_w_arrow.png",Texture.class);
		
		mFL.manualConnectDialog.ipQuads[0].texture=manager_.get("speech_bubble_gold.png",Texture.class);
		mFL.manualConnectDialog.ipQuads[1].texture=manager_.get("speech_bubble_gold.png",Texture.class);
		mFL.manualConnectDialog.ipQuads[2].texture=manager_.get("speech_bubble_gold.png",Texture.class);
		mFL.manualConnectDialog.ipQuads[3].texture=manager_.get("speech_bubble_gold.png",Texture.class);
	}
	
	public void loadLabels() {
		Gdx.app.log("DPCLifecycle", "ForegroundRenderer - loadLabels()");
		mFL.helpDialog.overviewSlide.titleLabel.loadTexture();
		mFL.helpDialog.overviewSlide.text1Label.loadTexture();
		mFL.helpDialog.overviewSlide.tableCentreLabel.loadTexture();
		mFL.helpDialog.overviewSlide.playerLabel.loadTexture();
		mFL.helpDialog.tableSetupSlide.titleLabel.loadTexture();
		mFL.helpDialog.tableSetupSlide.text1Label.loadTexture();
		mFL.helpDialog.tableSetupSlide.text2Label.loadTexture();
		mFL.helpDialog.tableSetupSlide.waitingLabel.loadTexture();
		mFL.helpDialog.playerSetupSlide.titleLabel.loadTexture();
		mFL.helpDialog.playerSetupSlide.text1Label.loadTexture();
		mFL.helpDialog.playerSetupSlide.text2Label.loadTexture();
		mFL.helpDialog.enjoySlide.titleLabel.loadTexture();
		mFL.tableStatusMenu.leaveButton.getLabel().loadTexture();
		mFL.blindsInLabel.loadTexture();
		mFL.flopLabel.loadTexture();
		mFL.turnLabel.loadTexture();
		mFL.riverLabel.loadTexture();
		mFL.selectWinnerLabel.loadTexture();
		mFL.selectWinnersSplitLabel.loadTexture();
		mFL.homeMenu.createButton.getLabel().loadTexture(whiteColor,darkBlueColor);
		mFL.homeMenu.loadButton.getLabel().loadTexture(whiteColor,darkBlueColor);
		mFL.homeMenu.joinButton.getLabel().loadTexture(whiteColor,darkBlueColor);
		mFL.homeMenu.howButton.getLabel().loadTexture(whiteColor,darkBlueColor);
		mFL.enterName1Label.loadTexture(whiteColor,blackColor);
		mFL.enterName2Label.loadTexture(whiteColor,blackColor);
		mFL.enterTableName1Label.loadTexture(whiteColor,blackColor);
		mFL.enterTableName2Label.loadTexture(whiteColor,blackColor);
		mFL.searchingLabel.loadTexture(greyColor,blackColor);
		mFL.wifiLabel.loadTexture(greyColor,blackColor);
		mFL.reconnect1Label.loadTexture(greyColor,blackColor);
		mFL.reconnect2Label.loadTexture(greyColor,blackColor);
		mFL.tutorialArrangement.instrLabel.loadTexture(whiteColor,blackColor);
		mFL.lobbyStatusLabel.loadTexture(greyColor,blackColor);
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
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			mFL.buyinDialog.chipStacks[i].totalLabel.setText(Integer.toString(mFL.buyinDialog.chipStacks[i].value()));
			mFL.buyinDialog.chipStacks[i].totalLabel.loadTexture(whiteColor,blackColor);
		}
		mFL.buyinDialog.instrLabel.loadTexture(whiteColor,blackColor);
		mFL.buyinDialog.totalLabel.loadTexture(whiteColor,blackColor);
		
		if (!mFL.playerPrompt.getText().equals("")) {
			mFL.playerPrompt.loadTexture();
		}
		if (!mFL.stateChangePrompt.getText().equals("")) {
			mFL.stateChangePrompt.loadTexture();
		}
		if (!mFL.winLabel.getText().equals("")) {
			mFL.winLabel.loadTexture();
		}
		if (!mFL.buyinDialog.totalNumberLabel.getText().equals("")) {
			mFL.buyinDialog.totalNumberLabel.loadTexture();
		}
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			mFL.buyinDialog.chipStacks[i].totalLabel.loadTexture();
		}
		if (!mFL.tableStatusMenu.tableName.getText().equals("")) {
			mFL.tableStatusMenu.tableName.loadTexture();
		}
		for (int i=0;i<mFL.tableStatusMenu.playerList.size();i++) {
			mFL.tableStatusMenu.playerList.get(i).name.loadTexture();
			mFL.tableStatusMenu.playerList.get(i).amount.loadTexture();
		}
		// TODO marker for load labels
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
        
        renderSearching();
        
        renderPlayerPrompts();
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
		
		if (mFL.dialogWArrowWindow.opacity!=0) {
			renderDialogWArrow();
		}
		
		if (mFL.buyinDialog.titleLabel.opacity!=0) {
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
		
		if (mFL.helpDialog.opacity!=0) {
			renderHelpDialog();
		}
		
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
	
	private void renderPlayerPrompts() {
		
		if (mFL.playerPrompt.opacity!=0) {
			batch.draw(mFL.playerPrompt.texture,
					mFL.playerPrompt.x-mFL.playerPrompt.radiusX,
					mFL.playerPrompt.y-mFL.playerPrompt.radiusY,
					mFL.playerPrompt.radiusX*2,mFL.playerPrompt.radiusY*2,
					0,0,mFL.playerPrompt.radiusX*2,mFL.playerPrompt.radiusY*2,false,false);
		}
		if (mFL.stateChangePrompt.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,mFL.stateChangePrompt.opacity);
			batch.draw(mFL.stateChangePrompt.texture,
					mFL.stateChangePrompt.x-mFL.stateChangePrompt.radiusX,
					mFL.stateChangePrompt.y-mFL.stateChangePrompt.radiusY,
					mFL.stateChangePrompt.radiusX*2,mFL.stateChangePrompt.radiusY*2,
					0,0,mFL.stateChangePrompt.radiusX*2,mFL.stateChangePrompt.radiusY*2,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.searchingLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,mFL.searchingLabel.opacity);
			batch.draw(mFL.searchingLabel.texture,
					mFL.searchingLabel.x-mFL.searchingLabel.radiusX,
					mFL.searchingLabel.y-mFL.searchingLabel.radiusY,
					mFL.searchingLabel.radiusX*2,mFL.searchingLabel.radiusY*2,
					0,0,mFL.searchingLabel.radiusX*2,mFL.searchingLabel.radiusY*2,false,false);
			 batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.reconnect1Label.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,mFL.reconnect1Label.opacity);
			batch.draw(mFL.reconnect1Label.texture,
					mFL.reconnect1Label.x-mFL.reconnect1Label.radiusX,
					mFL.reconnect1Label.y-mFL.reconnect1Label.radiusY,
					mFL.reconnect1Label.radiusX*2,mFL.reconnect1Label.radiusY*2,
					0,0,mFL.reconnect1Label.radiusX*2,mFL.reconnect1Label.radiusY*2,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,mFL.reconnect2Label.opacity);
			batch.draw(mFL.reconnect2Label.texture,
					mFL.reconnect2Label.x-mFL.reconnect2Label.radiusX,
					mFL.reconnect2Label.y-mFL.reconnect2Label.radiusY,
					mFL.reconnect2Label.radiusX*2,mFL.reconnect2Label.radiusY*2,
					0,0,mFL.reconnect2Label.radiusX*2,mFL.reconnect2Label.radiusY*2,false,false);
			 batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.waitNextHandLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,mFL.waitNextHandLabel.opacity);
			batch.draw(mFL.waitNextHandLabel.texture,
					mFL.waitNextHandLabel.x-mFL.waitNextHandLabel.radiusX,
					mFL.waitNextHandLabel.y-mFL.waitNextHandLabel.radiusY,
					mFL.waitNextHandLabel.radiusX*2,mFL.waitNextHandLabel.radiusY*2,
					0,0,mFL.waitNextHandLabel.radiusX*2,mFL.waitNextHandLabel.radiusY*2,false,false);
			 batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
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
		if (mFL.homeMenu.logoDPC.opacity!=0) {
			batch.draw(logoDPCTexture,
					mFL.homeMenu.logoDPC.x-mFL.homeMenu.logoDPC.radiusX,
					mFL.homeMenu.logoDPC.y-mFL.homeMenu.logoDPC.radiusY,
					mFL.homeMenu.logoDPC.radiusX*2,mFL.homeMenu.logoDPC.radiusY*2,
					0,0,516,172,false,false);
		}
		if (mFL.homeMenu.createButton.opacity!=0) {
			batch.draw(buttonBlueTexture,
					mFL.homeMenu.createButton.x-mFL.homeMenu.createButton.radiusX,
					mFL.homeMenu.createButton.y-mFL.homeMenu.createButton.radiusY,
					mFL.homeMenu.createButton.radiusX*2,mFL.homeMenu.createButton.radiusY*2,
					0,0,600,113,false,false);
			batch.draw(mFL.homeMenu.createButton.getLabel().texture,
					mFL.homeMenu.createButton.getLabel().x-mFL.homeMenu.createButton.getLabel().radiusX,
					mFL.homeMenu.createButton.getLabel().y-mFL.homeMenu.createButton.getLabel().radiusY,
					mFL.homeMenu.createButton.getLabel().radiusX*2,mFL.homeMenu.createButton.getLabel().radiusY*2,
					0,0,mFL.homeMenu.createButton.getLabel().radiusX*2,mFL.homeMenu.createButton.getLabel().radiusY*2,false,false);
		}
		if (mFL.homeMenu.loadButton.opacity!=0) {
			batch.draw(buttonBlueTexture,
					mFL.homeMenu.loadButton.x-mFL.homeMenu.loadButton.radiusX,
					mFL.homeMenu.loadButton.y-mFL.homeMenu.loadButton.radiusY,
					mFL.homeMenu.loadButton.radiusX*2,mFL.homeMenu.loadButton.radiusY*2,
					0,0,600,113,false,false);
			batch.draw(mFL.homeMenu.loadButton.getLabel().texture,
					mFL.homeMenu.loadButton.getLabel().x-mFL.homeMenu.loadButton.getLabel().radiusX,
					mFL.homeMenu.loadButton.getLabel().y-mFL.homeMenu.loadButton.getLabel().radiusY,
					mFL.homeMenu.loadButton.getLabel().radiusX*2,mFL.homeMenu.loadButton.getLabel().radiusY*2,
					0,0,mFL.homeMenu.loadButton.getLabel().radiusX*2,mFL.homeMenu.loadButton.getLabel().radiusY*2,false,false);
		}
		if (mFL.homeMenu.joinButton.opacity!=0) {
			batch.draw(buttonBlueTexture,
					mFL.homeMenu.joinButton.x-mFL.homeMenu.joinButton.radiusX,
					mFL.homeMenu.joinButton.y-mFL.homeMenu.joinButton.radiusY,
					mFL.homeMenu.joinButton.radiusX*2,mFL.homeMenu.joinButton.radiusY*2,
					0,0,600,113,false,false);
			batch.draw(mFL.homeMenu.joinButton.getLabel().texture,
					mFL.homeMenu.joinButton.getLabel().x-mFL.homeMenu.joinButton.getLabel().radiusX,
					mFL.homeMenu.joinButton.getLabel().y-mFL.homeMenu.joinButton.getLabel().radiusY,
					mFL.homeMenu.joinButton.getLabel().radiusX*2,mFL.homeMenu.joinButton.getLabel().radiusY*2,
					0,0,mFL.homeMenu.joinButton.getLabel().radiusX*2,mFL.homeMenu.joinButton.getLabel().radiusY*2,false,false);
		}
		if (mFL.homeMenu.howButton.opacity!=0) {
			batch.draw(buttonBlueTexture,
					mFL.homeMenu.howButton.x-mFL.homeMenu.howButton.radiusX,
					mFL.homeMenu.howButton.y-mFL.homeMenu.howButton.radiusY,
					mFL.homeMenu.howButton.radiusX*2,mFL.homeMenu.howButton.radiusY*2,
					0,0,600,113,false,false);
			batch.draw(mFL.homeMenu.howButton.getLabel().texture,
					mFL.homeMenu.howButton.getLabel().x-mFL.homeMenu.howButton.getLabel().radiusX,
					mFL.homeMenu.howButton.getLabel().y-mFL.homeMenu.howButton.getLabel().radiusY,
					mFL.homeMenu.howButton.getLabel().radiusX*2,mFL.homeMenu.howButton.getLabel().radiusY*2,
					0,0,mFL.homeMenu.howButton.getLabel().radiusX*2,mFL.homeMenu.howButton.getLabel().radiusY*2,false,false);
		}
	}
	
	private void renderSearching() {
		if (mFL.searchingAnimation.opacity!=0) {
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.searchingAnimation.opacity);
			batch.draw(pingTexture,
					mFL.searchingAnimation.ping.x-mFL.searchingAnimation.ping.radiusX,
					mFL.searchingAnimation.ping.y,
					mFL.searchingAnimation.ping.radiusX*2,mFL.searchingAnimation.ping.radiusY*2,
					0,0,640,320,false,false);
			batch.draw(pingHubTexture,
					mFL.searchingAnimation.x-mFL.searchingAnimation.radiusX,
					mFL.searchingAnimation.y-mFL.searchingAnimation.radiusY,
					mFL.searchingAnimation.radiusX*2,mFL.searchingAnimation.radiusY*2,
					0,0,100,100,false,false);
			
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
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
        batch.draw(mFL.enterName1Label.texture,
				mFL.enterName1Label.x-mFL.enterName1Label.radiusX,
				mFL.enterName1Label.y-mFL.enterName1Label.radiusY,
				mFL.enterName1Label.radiusX*2,mFL.enterName1Label.radiusY*2,
				0,0,mFL.enterName1Label.radiusX*2,mFL.enterName1Label.radiusY*2,false,false);
        batch.draw(mFL.enterName2Label.texture,
				mFL.enterName2Label.x-mFL.enterName2Label.radiusX,
				mFL.enterName2Label.y-mFL.enterName2Label.radiusY,
				mFL.enterName2Label.radiusX*2,mFL.enterName2Label.radiusY*2,
				0,0,mFL.enterName2Label.radiusX*2,mFL.enterName2Label.radiusY*2,false,false);
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

	private void renderBuyinDialog() {
		
		if (mFL.buyinDialog.okButton.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.buyinDialog.okButton.opacity);
	        
	        batch.draw(mFL.buyinDialog.titleLabel.texture,mFL.buyinDialog.titleLabel.x-mFL.buyinDialog.titleLabel.radiusX,
					mFL.buyinDialog.titleLabel.y-mFL.buyinDialog.titleLabel.radiusY,
					mFL.buyinDialog.titleLabel.radiusX*2,mFL.buyinDialog.titleLabel.radiusY*2,0,0,
					mFL.buyinDialog.titleLabel.radiusX*2,mFL.buyinDialog.titleLabel.radiusY*2,false,false);
	        batch.draw(okButtonTexture,mFL.buyinDialog.okButton.x-mFL.buyinDialog.okButton.radiusX,
					mFL.buyinDialog.okButton.y-mFL.buyinDialog.okButton.radiusY,
					mFL.buyinDialog.okButton.radiusX*2,mFL.buyinDialog.okButton.radiusY*2,
					0,0,214,214,false,false);
			batch.draw(cancelButtonTexture,mFL.buyinDialog.cancelButton.x-mFL.buyinDialog.cancelButton.radiusX,
					mFL.buyinDialog.cancelButton.y-mFL.buyinDialog.cancelButton.radiusY,
					mFL.buyinDialog.cancelButton.radiusX*2,mFL.buyinDialog.cancelButton.radiusY*2,
					0,0,300,300,false,false);
	        
			if (mFL.buyinDialog.instrLabel.opacity!=0) {
				for (int chipIndex_=0;chipIndex_<mFL.buyinDialog.upArrows.length;chipIndex_++) {
		        	renderChipStack(chipIndex_);
	        		batch.draw(arrowTexture,
	        				mFL.buyinDialog.upArrows[chipIndex_].x-mFL.buyinDialog.upArrows[chipIndex_].radiusX,
	        				mFL.buyinDialog.upArrows[chipIndex_].y-mFL.buyinDialog.upArrows[chipIndex_].radiusY,
	        				mFL.buyinDialog.upArrows[chipIndex_].radiusX*2,mFL.buyinDialog.upArrows[chipIndex_].radiusY*2,
	        				0,0,128,128,false,false);
	        		batch.draw(arrowTexture,
	        				mFL.buyinDialog.downArrows[chipIndex_].x-mFL.buyinDialog.downArrows[chipIndex_].radiusX,
	        				mFL.buyinDialog.downArrows[chipIndex_].y-mFL.buyinDialog.downArrows[chipIndex_].radiusY,
	        				mFL.buyinDialog.downArrows[chipIndex_].radiusX*2,mFL.buyinDialog.downArrows[chipIndex_].radiusY*2,
	        				0,0,128,128,false,true);
	        		
	        	}
				batch.draw(mFL.buyinDialog.instrLabel.texture,mFL.buyinDialog.instrLabel.x-mFL.buyinDialog.instrLabel.radiusX,
						mFL.buyinDialog.instrLabel.y-mFL.buyinDialog.instrLabel.radiusY,
						mFL.buyinDialog.instrLabel.radiusX*2,mFL.buyinDialog.instrLabel.radiusY*2,0,0,
						mFL.buyinDialog.instrLabel.radiusX*2,mFL.buyinDialog.instrLabel.radiusY*2,false,false);
				batch.draw(mFL.buyinDialog.totalLabel.texture,mFL.buyinDialog.totalLabel.x-mFL.buyinDialog.totalLabel.radiusX,
						mFL.buyinDialog.totalLabel.y-mFL.buyinDialog.totalLabel.radiusY,
						mFL.buyinDialog.totalLabel.radiusX*2,mFL.buyinDialog.totalLabel.radiusY*2,0,0,
						mFL.buyinDialog.totalLabel.radiusX*2,mFL.buyinDialog.totalLabel.radiusY*2,false,false);			
				batch.draw(mFL.buyinDialog.totalNumberLabel.texture,mFL.buyinDialog.totalNumberLabel.x-mFL.buyinDialog.totalNumberLabel.radiusX,
						mFL.buyinDialog.totalNumberLabel.y-mFL.buyinDialog.totalNumberLabel.radiusY,
						mFL.buyinDialog.totalNumberLabel.radiusX*2,mFL.buyinDialog.totalNumberLabel.radiusY*2,0,0,
						mFL.buyinDialog.totalNumberLabel.radiusX*2,mFL.buyinDialog.totalNumberLabel.radiusY*2,false,false);	
			}
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
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
		if (mFL.lobbyStatusLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.lobbyStatusLabel.opacity);
			batch.draw(mFL.lobbyStatusLabel.texture,mFL.lobbyStatusLabel.x-mFL.lobbyStatusLabel.radiusX,
					mFL.lobbyStatusLabel.y-mFL.lobbyStatusLabel.radiusY,
					mFL.lobbyStatusLabel.radiusX*2,mFL.lobbyStatusLabel.radiusY*2,0,0,
					mFL.lobbyStatusLabel.radiusX*2,mFL.lobbyStatusLabel.radiusY*2,false,false);
			batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
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
			batch.draw(mFL.selectWinnersSplitLabel.texture,mFL.selectWinnersSplitLabel.x-mFL.selectWinnersSplitLabel.radiusX,
					mFL.selectWinnersSplitLabel.y-mFL.selectWinnersSplitLabel.radiusY,
					mFL.selectWinnersSplitLabel.radiusX*2,mFL.selectWinnersSplitLabel.radiusY*2,
					0,0,mFL.selectWinnersSplitLabel.radiusX*2,mFL.selectWinnersSplitLabel.radiusY*2,false,false);
		}
		 batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
	}
	
	private void renderHelpDialog() {
		if (mFL.helpDialog.closeButton.opacity!=0) {
			batch.draw(closeButtonTexture,mFL.helpDialog.closeButton.x-mFL.helpDialog.closeButton.radiusX,
					mFL.helpDialog.closeButton.y-mFL.helpDialog.closeButton.radiusY,
					mFL.helpDialog.closeButton.radiusX*2,mFL.helpDialog.closeButton.radiusY*2,
					0,0,114,114,false,false);
		}
		if (mFL.helpDialog.nextButton.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.nextButton.opacity);
			batch.draw(nextButtonTexture,mFL.helpDialog.nextButton.x-mFL.helpDialog.nextButton.radiusX,
					mFL.helpDialog.nextButton.y-mFL.helpDialog.nextButton.radiusY,
					mFL.helpDialog.nextButton.radiusX*2,mFL.helpDialog.nextButton.radiusY*2,
					0,0,128,128,false,false);
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
		if (mFL.helpDialog.previousButton.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.previousButton.opacity);
			batch.draw(nextButtonTexture,mFL.helpDialog.previousButton.x-mFL.helpDialog.previousButton.radiusX,
					mFL.helpDialog.previousButton.y-mFL.helpDialog.previousButton.radiusY,
					mFL.helpDialog.previousButton.radiusX*2,mFL.helpDialog.previousButton.radiusY*2,
					0,0,128,128,true,false);
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
		if (mFL.helpDialog.doneButton.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.doneButton.opacity);
			batch.draw(okButtonTexture,
					mFL.helpDialog.doneButton.x-mFL.helpDialog.doneButton.radiusX,
					mFL.helpDialog.doneButton.y-mFL.helpDialog.doneButton.radiusY,
					mFL.helpDialog.doneButton.radiusX*2,mFL.helpDialog.doneButton.radiusY*2,
					0,0,214,214,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.helpDialog.overviewSlide.titleLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.overviewSlide.titleLabel.opacity);
	        batch.draw(mFL.helpDialog.overviewSlide.titleLabel.texture,
					mFL.helpDialog.overviewSlide.titleLabel.x-mFL.helpDialog.overviewSlide.titleLabel.radiusX,
					mFL.helpDialog.overviewSlide.titleLabel.y-mFL.helpDialog.overviewSlide.titleLabel.radiusY,
					mFL.helpDialog.overviewSlide.titleLabel.radiusX*2,mFL.helpDialog.overviewSlide.titleLabel.radiusY*2,
					0,0,mFL.helpDialog.overviewSlide.titleLabel.radiusX*2,mFL.helpDialog.overviewSlide.titleLabel.radiusY*2,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.helpDialog.overviewSlide.text1Label.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.overviewSlide.text1Label.opacity);
	        batch.draw(mFL.helpDialog.overviewSlide.text1Label.texture,
					mFL.helpDialog.overviewSlide.text1Label.x-mFL.helpDialog.overviewSlide.text1Label.radiusX,
					mFL.helpDialog.overviewSlide.text1Label.y-mFL.helpDialog.overviewSlide.text1Label.radiusY,
					mFL.helpDialog.overviewSlide.text1Label.radiusX*2,mFL.helpDialog.overviewSlide.text1Label.radiusY*2,
					0,0,mFL.helpDialog.overviewSlide.text1Label.radiusX*2,mFL.helpDialog.overviewSlide.text1Label.radiusY*2,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.helpDialog.overviewSlide.tableCentreLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.overviewSlide.tableCentreLabel.opacity);
	        batch.draw(mFL.helpDialog.overviewSlide.tableCentreLabel.texture,
					mFL.helpDialog.overviewSlide.tableCentreLabel.x-mFL.helpDialog.overviewSlide.tableCentreLabel.radiusX,
					mFL.helpDialog.overviewSlide.tableCentreLabel.y-mFL.helpDialog.overviewSlide.tableCentreLabel.radiusY,
					mFL.helpDialog.overviewSlide.tableCentreLabel.radiusX*2,mFL.helpDialog.overviewSlide.tableCentreLabel.radiusY*2,
					0,0,mFL.helpDialog.overviewSlide.tableCentreLabel.radiusX*2,mFL.helpDialog.overviewSlide.tableCentreLabel.radiusY*2,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		
		if (mFL.helpDialog.overviewSlide.playerLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.overviewSlide.playerLabel.opacity);
	        batch.draw(mFL.helpDialog.overviewSlide.playerLabel.texture,
					mFL.helpDialog.overviewSlide.playerLabel.x-mFL.helpDialog.overviewSlide.playerLabel.radiusX,
					mFL.helpDialog.overviewSlide.playerLabel.y-mFL.helpDialog.overviewSlide.playerLabel.radiusY,
					mFL.helpDialog.overviewSlide.playerLabel.radiusX*2,mFL.helpDialog.overviewSlide.playerLabel.radiusY*2,
					0,0,mFL.helpDialog.overviewSlide.playerLabel.radiusX*2,mFL.helpDialog.overviewSlide.playerLabel.radiusY*2,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.helpDialog.tableSetupSlide.titleLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.tableSetupSlide.titleLabel.opacity);
	        batch.draw(mFL.helpDialog.tableSetupSlide.titleLabel.texture,
					mFL.helpDialog.tableSetupSlide.titleLabel.x-mFL.helpDialog.tableSetupSlide.titleLabel.radiusX,
					mFL.helpDialog.tableSetupSlide.titleLabel.y-mFL.helpDialog.tableSetupSlide.titleLabel.radiusY,
					mFL.helpDialog.tableSetupSlide.titleLabel.radiusX*2,mFL.helpDialog.tableSetupSlide.titleLabel.radiusY*2,
					0,0,mFL.helpDialog.tableSetupSlide.titleLabel.radiusX*2,mFL.helpDialog.tableSetupSlide.titleLabel.radiusY*2,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.helpDialog.tableSetupSlide.text1Label.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.tableSetupSlide.text1Label.opacity);
	        batch.draw(mFL.helpDialog.tableSetupSlide.text1Label.texture,
					mFL.helpDialog.tableSetupSlide.text1Label.x-mFL.helpDialog.tableSetupSlide.text1Label.radiusX,
					mFL.helpDialog.tableSetupSlide.text1Label.y-mFL.helpDialog.tableSetupSlide.text1Label.radiusY,
					mFL.helpDialog.tableSetupSlide.text1Label.radiusX*2,mFL.helpDialog.tableSetupSlide.text1Label.radiusY*2,
					0,0,mFL.helpDialog.tableSetupSlide.text1Label.radiusX*2,mFL.helpDialog.tableSetupSlide.text1Label.radiusY*2,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.helpDialog.tableSetupSlide.text2Label.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.tableSetupSlide.text2Label.opacity);
	        batch.draw(mFL.helpDialog.tableSetupSlide.text2Label.texture,
					mFL.helpDialog.tableSetupSlide.text2Label.x-mFL.helpDialog.tableSetupSlide.text2Label.radiusX,
					mFL.helpDialog.tableSetupSlide.text2Label.y-mFL.helpDialog.tableSetupSlide.text2Label.radiusY,
					mFL.helpDialog.tableSetupSlide.text2Label.radiusX*2,mFL.helpDialog.tableSetupSlide.text2Label.radiusY*2,
					0,0,mFL.helpDialog.tableSetupSlide.text2Label.radiusX*2,mFL.helpDialog.tableSetupSlide.text2Label.radiusY*2,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		
		if (mFL.helpDialog.playerSetupSlide.titleLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.playerSetupSlide.titleLabel.opacity);
	        batch.draw(mFL.helpDialog.playerSetupSlide.titleLabel.texture,
					mFL.helpDialog.playerSetupSlide.titleLabel.x-mFL.helpDialog.playerSetupSlide.titleLabel.radiusX,
					mFL.helpDialog.playerSetupSlide.titleLabel.y-mFL.helpDialog.playerSetupSlide.titleLabel.radiusY,
					mFL.helpDialog.playerSetupSlide.titleLabel.radiusX*2,mFL.helpDialog.playerSetupSlide.titleLabel.radiusY*2,
					0,0,mFL.helpDialog.playerSetupSlide.titleLabel.radiusX*2,mFL.helpDialog.playerSetupSlide.titleLabel.radiusY*2,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.helpDialog.playerSetupSlide.text1Label.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.playerSetupSlide.text1Label.opacity);
	        batch.draw(mFL.helpDialog.playerSetupSlide.text1Label.texture,
					mFL.helpDialog.playerSetupSlide.text1Label.x-mFL.helpDialog.playerSetupSlide.text1Label.radiusX,
					mFL.helpDialog.playerSetupSlide.text1Label.y-mFL.helpDialog.playerSetupSlide.text1Label.radiusY,
					mFL.helpDialog.playerSetupSlide.text1Label.radiusX*2,mFL.helpDialog.playerSetupSlide.text1Label.radiusY*2,
					0,0,mFL.helpDialog.playerSetupSlide.text1Label.radiusX*2,mFL.helpDialog.playerSetupSlide.text1Label.radiusY*2,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.helpDialog.playerSetupSlide.text2Label.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.playerSetupSlide.text2Label.opacity);
	        batch.draw(mFL.helpDialog.playerSetupSlide.text2Label.texture,
					mFL.helpDialog.playerSetupSlide.text2Label.x-mFL.helpDialog.playerSetupSlide.text2Label.radiusX,
					mFL.helpDialog.playerSetupSlide.text2Label.y-mFL.helpDialog.playerSetupSlide.text2Label.radiusY,
					mFL.helpDialog.playerSetupSlide.text2Label.radiusX*2,mFL.helpDialog.playerSetupSlide.text2Label.radiusY*2,
					0,0,mFL.helpDialog.playerSetupSlide.text2Label.radiusX*2,mFL.helpDialog.playerSetupSlide.text2Label.radiusY*2,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.helpDialog.enjoySlide.titleLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.enjoySlide.titleLabel.opacity);
	        batch.draw(mFL.helpDialog.enjoySlide.titleLabel.texture,
					mFL.helpDialog.enjoySlide.titleLabel.x-mFL.helpDialog.enjoySlide.titleLabel.radiusX,
					mFL.helpDialog.enjoySlide.titleLabel.y-mFL.helpDialog.enjoySlide.titleLabel.radiusY,
					mFL.helpDialog.enjoySlide.titleLabel.radiusX*2,mFL.helpDialog.enjoySlide.titleLabel.radiusY*2,
					0,0,mFL.helpDialog.enjoySlide.titleLabel.radiusX*2,mFL.helpDialog.enjoySlide.titleLabel.radiusY*2,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.helpDialog.tableCentre.opacity!=0) {
			int textureWidth_=1280;
			int textureHeight_=800;
			int regionWidth_=(int)(textureWidth_/(4f*mFL.helpDialog.zoomTableCentre));
			int regionHeight_=(int)(textureHeight_/(4f*mFL.helpDialog.zoomTableCentre));
			int xOffset_=(int) ((textureWidth_-regionWidth_)/2f);
			int yOffset_=(int) ((textureHeight_-regionHeight_)/2.1f);
			batch.draw(backgroundHelpTexture,mFL.helpDialog.tableCentre.x-mFL.helpDialog.tableCentre.radiusX*0.95f,
					mFL.helpDialog.tableCentre.y-mFL.helpDialog.tableCentre.radiusY*0.95f,
					mFL.helpDialog.tableCentre.radiusX*1.9f,mFL.helpDialog.tableCentre.radiusY*1.9f,
					xOffset_,yOffset_,regionWidth_,regionHeight_,false,false);
		}
		if (mFL.helpDialog.tableSetupSlide.waitingLabel.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.tableSetupSlide.waitingLabel.opacity);
	        batch.draw(mFL.helpDialog.tableSetupSlide.waitingLabel.texture,
					mFL.helpDialog.tableSetupSlide.waitingLabel.x-mFL.helpDialog.tableSetupSlide.waitingLabel.radiusX,
					mFL.helpDialog.tableSetupSlide.waitingLabel.y-mFL.helpDialog.tableSetupSlide.waitingLabel.radiusY,
					mFL.helpDialog.tableSetupSlide.waitingLabel.radiusX*2,mFL.helpDialog.tableSetupSlide.waitingLabel.radiusY*2,
					0,0,mFL.helpDialog.tableSetupSlide.waitingLabel.radiusX*2,mFL.helpDialog.tableSetupSlide.waitingLabel.radiusY*2,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.helpDialog.player1.opacity!=0) {
			int textureWidth_=1280;
			int textureHeight_=800;
			int regionWidth_=(int)(textureWidth_/(4f*mFL.helpDialog.zoomPlayer));
			int regionHeight_=(int)(textureHeight_/(4f*mFL.helpDialog.zoomPlayer));
			int xOffset_=(int) ((textureWidth_-regionWidth_)/2f);
			int yOffset_=(int) ((textureHeight_-regionHeight_)/1.4f);
			batch.draw(backgroundHelpTexture,mFL.helpDialog.player1.x-mFL.helpDialog.player1.radiusX*0.92f,
					mFL.helpDialog.player1.y-mFL.helpDialog.player1.radiusY*0.92f,
					mFL.helpDialog.player1.radiusX*1.84f,mFL.helpDialog.player1.radiusY*1.84f,
					xOffset_,yOffset_,regionWidth_,regionHeight_,false,false);
		}
		if (mFL.helpDialog.player2.opacity!=0) {
			int textureWidth_=1280;
			int textureHeight_=800;
			int regionWidth_=(int)(textureWidth_/(4f*mFL.helpDialog.zoomPlayer));
			int regionHeight_=(int)(textureHeight_/(4f*mFL.helpDialog.zoomPlayer));
			int xOffset_=(int) ((textureWidth_-regionWidth_)/2f);
			int yOffset_=(int) ((textureHeight_-regionHeight_)/1.4f);
			batch.draw(backgroundHelpTexture,mFL.helpDialog.player2.x-mFL.helpDialog.player2.radiusX*0.95f,
					mFL.helpDialog.player2.y-mFL.helpDialog.player2.radiusY*0.95f,
					mFL.helpDialog.player2.radiusX*0.95f,mFL.helpDialog.player2.radiusY*0.95f,
					mFL.helpDialog.player2.radiusX*1.9f,mFL.helpDialog.player2.radiusY*1.9f,
					1,1,180,
					xOffset_,yOffset_,regionWidth_,regionHeight_,false,false);
		}
		if (mFL.helpDialog.player3.opacity!=0) {
			int textureWidth_=1280;
			int textureHeight_=800;
			int regionWidth_=(int)(textureWidth_/(4f*mFL.helpDialog.zoomPlayer));
			int regionHeight_=(int)(textureHeight_/(4f*mFL.helpDialog.zoomPlayer));
			int xOffset_=(int) ((textureWidth_-regionWidth_)/2f);
			int yOffset_=(int) ((textureHeight_-regionHeight_)/1.4f);
			batch.draw(backgroundHelpTexture,mFL.helpDialog.player3.x-mFL.helpDialog.player3.radiusX*0.95f,
					mFL.helpDialog.player3.y-mFL.helpDialog.player3.radiusY*0.95f,
					mFL.helpDialog.player3.radiusX*0.95f,mFL.helpDialog.player3.radiusY*0.95f,
					mFL.helpDialog.player3.radiusX*1.9f,mFL.helpDialog.player3.radiusY*1.9f,
					1,1,270,
					xOffset_,yOffset_,regionWidth_,regionHeight_,false,false);
		}
		if (mFL.helpDialog.player4.opacity!=0) {
			int textureWidth_=1280;
			int textureHeight_=800;
			int regionWidth_=(int)(textureWidth_/(4f*mFL.helpDialog.zoomPlayer));
			int regionHeight_=(int)(textureHeight_/(4f*mFL.helpDialog.zoomPlayer));
			int xOffset_=(int) ((textureWidth_-regionWidth_)/2f);
			int yOffset_=(int) ((textureHeight_-regionHeight_)/1.4f);
			batch.draw(backgroundHelpTexture,mFL.helpDialog.player4.x-mFL.helpDialog.player4.radiusX*0.95f,
					mFL.helpDialog.player4.y-mFL.helpDialog.player4.radiusY*0.95f,
					mFL.helpDialog.player4.radiusX*0.95f,mFL.helpDialog.player4.radiusY*0.95f,
					mFL.helpDialog.player4.radiusX*1.9f,mFL.helpDialog.player4.radiusY*1.9f,
					1,1,90,
					xOffset_,yOffset_,regionWidth_,regionHeight_,false,false);
		}
		
		if (mFL.helpDialog.chip1.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.chip1.opacity);
	        batch.draw(chipTextures[0],mFL.helpDialog.chip1.x-mFL.helpDialog.chip1.radiusX,
					mFL.helpDialog.chip1.y-mFL.helpDialog.chip1.radiusY,
					mFL.helpDialog.chip1.radiusX,mFL.helpDialog.chip1.radiusY,
					mFL.helpDialog.chip1.radiusX*2,mFL.helpDialog.chip1.radiusY*2,
					1,1,0,0,0,256,256,false,false);
	        batch.draw(chipTextures[0],mFL.helpDialog.chip2.x-mFL.helpDialog.chip2.radiusX,
					mFL.helpDialog.chip2.y-mFL.helpDialog.chip2.radiusY,
					mFL.helpDialog.chip2.radiusX,mFL.helpDialog.chip2.radiusY,
					mFL.helpDialog.chip2.radiusX*2,mFL.helpDialog.chip2.radiusY*2,
					1,1,180,0,0,256,256,false,false);
	        batch.draw(chipTextures[0],mFL.helpDialog.chip3.x-mFL.helpDialog.chip3.radiusX,
					mFL.helpDialog.chip3.y-mFL.helpDialog.chip3.radiusY,
					mFL.helpDialog.chip3.radiusX,mFL.helpDialog.chip3.radiusY,
					mFL.helpDialog.chip3.radiusX*2,mFL.helpDialog.chip3.radiusY*2,
					1,1,270,0,0,256,256,false,false);
	        batch.draw(chipTextures[0],mFL.helpDialog.chip4.x-mFL.helpDialog.chip4.radiusX,
					mFL.helpDialog.chip4.y-mFL.helpDialog.chip4.radiusY,
					mFL.helpDialog.chip4.radiusX,mFL.helpDialog.chip4.radiusY,
					mFL.helpDialog.chip4.radiusX*2,mFL.helpDialog.chip4.radiusY*2,
					1,1,90,0,0,256,256,false,false);
	        alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,1);
		}
		
		if (mFL.helpDialog.tableCentre.opacity!=0) {
			batch.draw(deviceFrameTexture,mFL.helpDialog.tableCentre.x-mFL.helpDialog.tableCentre.radiusX,
					mFL.helpDialog.tableCentre.y-mFL.helpDialog.tableCentre.radiusY,
					mFL.helpDialog.tableCentre.radiusX*2,mFL.helpDialog.tableCentre.radiusY*2,
					0,0,600,375,false,false);
		}
		if (mFL.helpDialog.player1.opacity!=0) {
			batch.draw(deviceFrameTexture,mFL.helpDialog.player1.x-mFL.helpDialog.player1.radiusX,
					mFL.helpDialog.player1.y-mFL.helpDialog.player1.radiusY,
					mFL.helpDialog.player1.radiusX*2,mFL.helpDialog.player1.radiusY*2,
					0,0,600,375,false,false);
		}
		if (mFL.helpDialog.player2.opacity!=0) {
			batch.draw(deviceFrameTexture,mFL.helpDialog.player2.x-mFL.helpDialog.player2.radiusX,
					mFL.helpDialog.player2.y-mFL.helpDialog.player2.radiusY,
					mFL.helpDialog.player2.radiusX,mFL.helpDialog.player2.radiusY,
					mFL.helpDialog.player2.radiusX*2,mFL.helpDialog.player2.radiusY*2,
					1,1,180,0,0,600,375,false,false);
		}
		if (mFL.helpDialog.player3.opacity!=0) {
			batch.draw(deviceFrameTexture,mFL.helpDialog.player3.x-mFL.helpDialog.player3.radiusX,
					mFL.helpDialog.player3.y-mFL.helpDialog.player3.radiusY,
					mFL.helpDialog.player3.radiusX,mFL.helpDialog.player3.radiusY,
					mFL.helpDialog.player3.radiusX*2,mFL.helpDialog.player3.radiusY*2,
					1,1,270,0,0,600,375,false,false);
		}
		if (mFL.helpDialog.player4.opacity!=0) {
			batch.draw(deviceFrameTexture,mFL.helpDialog.player4.x-mFL.helpDialog.player4.radiusX,
					mFL.helpDialog.player4.y-mFL.helpDialog.player4.radiusY,
					mFL.helpDialog.player4.radiusX,mFL.helpDialog.player4.radiusY,
					mFL.helpDialog.player4.radiusX*2,mFL.helpDialog.player4.radiusY*2,
					1,1,90,0,0,600,375,false,false);
		}
		if (mFL.helpDialog.overviewSlide.tableCentreArrow.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.overviewSlide.tableCentreArrow.opacity);
	        batch.draw(arrowHanddrawnTexture,
					mFL.helpDialog.overviewSlide.tableCentreArrow.x-mFL.helpDialog.overviewSlide.tableCentreArrow.radiusX,
					mFL.helpDialog.overviewSlide.tableCentreArrow.y-mFL.helpDialog.overviewSlide.tableCentreArrow.radiusY,
					mFL.helpDialog.overviewSlide.tableCentreArrow.radiusX,mFL.helpDialog.overviewSlide.tableCentreArrow.radiusY,
					mFL.helpDialog.overviewSlide.tableCentreArrow.radiusX*2,mFL.helpDialog.overviewSlide.tableCentreArrow.radiusY*2,
					1,1,mFL.helpDialog.overviewSlide.tableCentreArrow.rotation,
					0,0,162,127,true,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
		if (mFL.helpDialog.overviewSlide.playerArrow.opacity!=0) {
			alphaShader=batch.getColor();
	        batch.setColor(alphaShader.r,alphaShader.g,
	        		alphaShader.b,mFL.helpDialog.overviewSlide.playerArrow.opacity);
	        batch.draw(arrowHanddrawnTexture,
					mFL.helpDialog.overviewSlide.playerArrow.x-mFL.helpDialog.overviewSlide.playerArrow.radiusX,
					mFL.helpDialog.overviewSlide.playerArrow.y-mFL.helpDialog.overviewSlide.playerArrow.radiusY,
					mFL.helpDialog.overviewSlide.playerArrow.radiusX,mFL.helpDialog.overviewSlide.playerArrow.radiusY,
					mFL.helpDialog.overviewSlide.playerArrow.radiusX*2,mFL.helpDialog.overviewSlide.playerArrow.radiusY*2,
					1,1,mFL.helpDialog.overviewSlide.playerArrow.rotation,
					0,0,162,127,false,false);
	        batch.setColor(alphaShader.r,alphaShader.g,alphaShader.b,1);
		}
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
	
	private void renderChipStack(int chip_) {
		float yStack_=mFL.buyinDialog.chipStacks[chip_].getY();
		int radiusX0_=mFL.buyinDialog.chipStacks[chip_].radiusX;
		int radiusY0_=mFL.buyinDialog.chipStacks[chip_].radiusY;
		float perspectiveGradient_=Chip.perspectiveGradient;
		float zyOffsetRatio_=Chip.Z_Y_OFFSET_RATIO;
		float xStack_=mFL.buyinDialog.chipStacks[chip_].getX();
		int numStack_=mFL.buyinDialog.chipStacks[chip_].renderSize();
		ChipStack stack_=mFL.buyinDialog.chipStacks[chip_];
		if (numStack_>0) {
			float zyOffset_=0;
			int N;
			for (N=0;N<numStack_;N++) {
				float radiusXChip_=radiusX0_*(1+N*perspectiveGradient_);
				float radiusYChip_=radiusY0_*(1+N*perspectiveGradient_);
				zyOffset_=zyOffsetRatio_*radiusY0_*N*(1+0.5f*perspectiveGradient_*(N-1));
				batch.draw(chipTextures[chip_*Chip.CHIP_ROTATION_N+stack_.get(N).imgRotation],
						xStack_-radiusXChip_,
						yStack_-radiusYChip_+zyOffset_,
						radiusXChip_*2,radiusYChip_*2,
						0,0,256,252,false,false);
			}
			batch.draw(mFL.buyinDialog.chipStacks[chip_].totalLabel.texture,
					mFL.buyinDialog.chipStacks[chip_].getX()-mFL.buyinDialog.chipStacks[chip_].totalLabel.radiusX,
					mFL.buyinDialog.chipStacks[chip_].getTopY(mFL.buyinDialog.chipStacks[chip_].radiusY)-mFL.buyinDialog.chipStacks[chip_].totalLabel.radiusY,
					mFL.buyinDialog.chipStacks[chip_].totalLabel.radiusX*2,mFL.buyinDialog.chipStacks[chip_].totalLabel.radiusY*2,
					0,0,mFL.buyinDialog.chipStacks[chip_].totalLabel.radiusX*2,mFL.buyinDialog.chipStacks[chip_].totalLabel.radiusY*2,false,false);
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
