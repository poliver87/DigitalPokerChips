package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.i.IDPCSprite;
import com.bidjee.digitalpokerchips.m.BetTotalDialog;
import com.bidjee.digitalpokerchips.m.Button;
import com.bidjee.digitalpokerchips.m.ChipCase;
import com.bidjee.digitalpokerchips.m.ChipStack;
import com.bidjee.digitalpokerchips.m.ClosedDialog;
import com.bidjee.digitalpokerchips.m.DialogWindow;
import com.bidjee.digitalpokerchips.m.HelpDialog;
import com.bidjee.digitalpokerchips.m.HostChipCaseDialog;
import com.bidjee.digitalpokerchips.m.HostChipSetupDialog;
import com.bidjee.digitalpokerchips.m.HostDestroyDialog;
import com.bidjee.digitalpokerchips.m.HostGameNameDialog;
import com.bidjee.digitalpokerchips.m.HostLobbyDialog;
import com.bidjee.digitalpokerchips.m.HostNameDialog;
import com.bidjee.digitalpokerchips.m.HostPlayerDialog;
import com.bidjee.digitalpokerchips.m.HostPotDialog;
import com.bidjee.digitalpokerchips.m.HostRearrangeDialog;
import com.bidjee.digitalpokerchips.m.ManualConnectDialog;
import com.bidjee.digitalpokerchips.m.Player;
import com.bidjee.digitalpokerchips.m.PlayerBuyinDialog;
import com.bidjee.digitalpokerchips.m.PlayerDashboard;
import com.bidjee.digitalpokerchips.m.PlayerLeaveDialog;
import com.bidjee.digitalpokerchips.m.PlayerLoginDialog;
import com.bidjee.digitalpokerchips.m.Seat;
import com.bidjee.digitalpokerchips.m.TextLabel;
import com.bidjee.digitalpokerchips.v.ForegroundRenderer;
import com.bidjee.util.Logger;

public class ForegroundLayer {
	
	public static final String LOG_TAG = "DPCUILayer";
	
	public static final Color whiteColor=new Color(1,1,1,1);
	public static final Color blackColor=new Color(0,0,0,1);
	public static final Color goldColor=new Color(0.88f,0.62f,0.09f,1);
	public static final Color brightGoldColor=new Color(1f,0.88f,0.55f,1);
	public static final Color brightestGoldColor=new Color(1f,0.94f,0.77f,1);
	public static final Color navyBlueColor=new Color(0.1f,0.23f,0.32f,1);
	public static final Color darkGreenColor=new Color(0,0.4f,0,1);
	public static final Color darkBlueColor=new Color(0,0,0.4f,1);
	public static final Color darkRedColor=new Color(0.4f,0,0,1);
	public static final Color redChipColor=new Color(0.58f,0.19f,0.2f,1);
	public static final Color blueChipColor=new Color(0.16f,0.38f,0.65f,1);
	public static final Color blackChipColor=new Color(0.19f,0.19f,0.19f,1);
	
	//////////////////// Constants ////////////////////
	static final String WIN_TEXT = " Wins Dealer!";
	static final String MEASURE_IP = "999.999.999.999";
	
	//////////////////// State Variables ////////////////////
	boolean screenLaidOut;
	boolean loadDialogOpen;
	boolean runTutorialArrangement;
	boolean openHelpOnStartup;
	
	//////////////////// Screen Scale & Layout ////////////////////
	public float screenWidth;
	public float screenHeight;
	float oldScreenWidth;
	float oldScreenHeight;
	float limFlingVelocity;
	Vector2 posHostUndoOffscreen=new Vector2();
	Vector2 posHostUndoOnscreen=new Vector2();
	Vector2 posHostExitOffscreen=new Vector2();
	Vector2 posHostExitOnscreen=new Vector2();
	
	//////////////////// Controllers ////////////////////
	GestureDetector gestureInput;
	public ForegroundInput input;
	public HomeUIAnimation homeUIAnimation=new HomeUIAnimation();
	public HomeForegroundAnimation homeForegroundAnimation=new HomeForegroundAnimation();
	public GameMenu gameMenu;
	
	//////////////////// Models ////////////////////
	public DialogWindow dialogWindow;
	public DialogWindow dialogWArrowWindow;
	public ClosedDialog helpDialogSmall;
	public HelpDialog helpDialog;
	public PlayerLoginDialog playerLoginDialog;
	public PlayerLeaveDialog playerLeaveDialog;
	public HostNameDialog hostNameDialog;
	public HostChipCaseDialog hostChipCaseDialog;
	public HostChipSetupDialog hostChipSetupDialog;
	public HostLobbyDialog hostLobbyDialog;
	public HostDestroyDialog hostDestroyDialog;
	public HostRearrangeDialog hostRearrangeDialog;
	public HostGameNameDialog hostGameNameDialog;
	public PlayerBuyinDialog playerBuyinDialog;
	public PlayerDashboard playerDashboard;
	public BetTotalDialog betTotalDialog;
	
	public ClosedDialog loadDialogSmall;
	public ClosedDialog destroyTableDialogSmall;
	public HostPlayerDialog[] hostPlayerDialogs;
	public HostPotDialog hostPotDialog;
	public DivisibilityDialog divisibilityDialog;
	public LoadDialog loadDialog;
	public ManualConnectDialog manualConnectDialog;
	
	//////////////////// Sprites ////////////////////
	public Button wifiButton;
	public Button foldButton;
	public Button hostUndoButton;
	public Button hostExitButton;
	public Button potArrowRight;
	public Button potArrowLeft;
	
	//////////////////// Text Labels ////////////////////
	public TextLabel[] mainStackValueLabels;
	
	//////////////////// Renderers ////////////////////
	public ForegroundRenderer foregroundRenderer;

	//////////////////// References ////////////////////
	public DPCGame game;
	
	//////////////////// Debugging ////////////////////
	public TextLabel azimuthLabel;
	int secondTimer=0;
	
	public ForegroundLayer(DPCGame game,IDPCSprite helpWebView) {		
		this.game=game;
		foregroundRenderer=new ForegroundRenderer(this);
		
		input=new ForegroundInput();
		input.setScreen(this);
		ForegroundGestureInput mFGI=new ForegroundGestureInput();
		gestureInput=new GestureDetector(mFGI);
		mFGI.setScreen(this);
		runTutorialArrangement=false;

		openHelpOnStartup=false;
		
		gameMenu=new GameMenu();
		
		wifiButton=new Button(true,0,"");

		divisibilityDialog=new DivisibilityDialog();
		dialogWindow=new DialogWindow();
		dialogWindow.opacity=0;
		dialogWArrowWindow=new DialogWindow();
		dialogWArrowWindow.opacity=0;
		manualConnectDialog=new ManualConnectDialog();
		manualConnectDialog.opacity=0;
		destroyTableDialogSmall=new ClosedDialog(dialogWindow);
		destroyTableDialogSmall.opacity=0;
		hostPlayerDialogs = new HostPlayerDialog[Table.NUM_SEATS];
		for (int i=0;i<Table.NUM_SEATS;i++) {
			hostPlayerDialogs[i] = new HostPlayerDialog();
			hostPlayerDialogs[i].opacity = 0;
		}
		hostPotDialog = new HostPotDialog();
		
		potArrowLeft=new Button(true,0,"");
		potArrowRight=new Button(true,0,"");
		
		helpDialogSmall=new ClosedDialog(dialogWindow);
		helpDialog=new HelpDialog(helpWebView);
		playerLoginDialog=new PlayerLoginDialog();
		playerLeaveDialog=new PlayerLeaveDialog();
		hostNameDialog=new HostNameDialog();
		hostChipCaseDialog=new HostChipCaseDialog();
		hostChipSetupDialog=new HostChipSetupDialog();
		hostLobbyDialog=new HostLobbyDialog();
		hostDestroyDialog=new HostDestroyDialog();
		hostRearrangeDialog=new HostRearrangeDialog();
		hostGameNameDialog= new HostGameNameDialog();
		playerBuyinDialog=new PlayerBuyinDialog();
		
		playerDashboard=new PlayerDashboard();
		betTotalDialog=new BetTotalDialog();
		
		helpDialogSmall=new ClosedDialog(dialogWindow);
		helpDialogSmall.opacity=0;
		loadDialog=new LoadDialog();
		loadDialog.opacity=0;
		loadDialogSmall=new ClosedDialog(dialogWindow);
		loadDialogSmall.opacity=0;
		foldButton=new Button(true,0,"");
		
		hostUndoButton=new Button(true,1,"");
		hostExitButton=new Button(true,1,"");
		
		mainStackValueLabels=new TextLabel[ChipCase.CHIP_TYPES];
		for (int chip_=ChipCase.CHIP_A;chip_<ChipCase.CHIP_TYPES;chip_++) {
			mainStackValueLabels[chip_]=new TextLabel("",0,false,0.8f,false);
		}
		mainStackValueLabels[ChipCase.CHIP_A].bodyColor = ForegroundLayer.redChipColor;
		mainStackValueLabels[ChipCase.CHIP_B].bodyColor = ForegroundLayer.blueChipColor;
		mainStackValueLabels[ChipCase.CHIP_C].bodyColor = ForegroundLayer.blackChipColor;
		
		azimuthLabel=new TextLabel("360",0,true,1,false);
	}
	
	public void dispose() {
		foregroundRenderer.dispose();
		playerDashboard.dispose();
		game=null;
	}
	
	public void resize(int screenWidth,int screenHeight) {
		foregroundRenderer.resize(screenWidth,screenHeight);
		if ((screenWidth!=this.screenWidth||screenHeight!=this.screenHeight)&&
				(screenWidth>screenHeight)) {
			this.screenWidth=screenWidth;
			this.screenHeight=screenHeight;
			setDimensions(screenWidth,screenHeight);
			// TODO change this to allow for scaling dimensions
			if (!screenLaidOut) {
				setPositions(screenWidth,screenHeight);
				screenLaidOut=true;
			} else {
				float scaleX=screenWidth/this.screenWidth;
				float scaleY=screenHeight/this.screenHeight;
				scalePositions(scaleX,scaleY);
			}
		}
	}
	
	//////////////////// Scale & Layout ////////////////////
	private void setDimensions(int screenWidth,int screenHeight) {
		limFlingVelocity=screenHeight*0.2f;
		
		TextLabel dummyStackTotal = new TextLabel("$999999", 0, true, 1, false);
		dummyStackTotal.setMaxDimensions((int)(screenWidth*0.15f),(int)(screenHeight*0.03f));
		ChipStack.totalTextSize = DPCGame.textFactory.getMaxTextSize(dummyStackTotal);
		
		homeUIAnimation.setDimensions(screenWidth,screenHeight);
		homeForegroundAnimation.setDimensions(screenWidth,screenHeight);
		wifiButton.setDimensions((int)(screenHeight*0.08f),(int)(screenHeight*0.08f));
		foldButton.setDimensions((int)(screenHeight*0.15f),(int)(screenHeight*0.15f));
		
		hostUndoButton.setDimensions((int)(screenHeight*0.07f),(int)(screenHeight*0.07f));
		hostExitButton.setDimensions((int)(screenHeight*0.07f),(int)(screenHeight*0.07f));
		
		helpDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.45f));
		helpDialogSmall.setDimensions(1,1);
		
		playerLoginDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.36f));
		playerBuyinDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.30f));
		playerLeaveDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.30f));
		hostNameDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.30f));
		hostChipCaseDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.36f));
		hostChipSetupDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.36f));
		hostLobbyDialog.setDimensions((int)(screenHeight*0.45f),(int)(screenHeight*0.2f));
		hostDestroyDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.30f));
		hostRearrangeDialog.setDimensions((int)(screenHeight*0.45f),(int)(screenHeight*0.2f));
		hostGameNameDialog.setDimensions((int)(screenHeight*0.16f),(int)(screenHeight*0.07f));
		
		
		playerDashboard.setDimensions((int)(screenWidth*0.5f),(int)(screenHeight*0.09f));
		betTotalDialog.setDimensions((int)(screenHeight*0.08f*2.5f),(int)(screenHeight*0.08f));
		
		gameMenu.setDimensions((int)(screenHeight*0.08f*2.5f),(int)(screenHeight*0.46f));
		
		mainStackValueLabels[ChipCase.CHIP_A].setMaxDimensions((int)(screenWidth*0.33f),(int)(screenHeight*0.025f));
		mainStackValueLabels[ChipCase.CHIP_A].setTextSizeToMax("$1000000000");
		int valueSize = mainStackValueLabels[ChipCase.CHIP_A].getTextSize();
		mainStackValueLabels[ChipCase.CHIP_B].setMaxDimensions((int)(screenWidth*0.33f),(int)(screenHeight*0.023f));
		mainStackValueLabels[ChipCase.CHIP_B].setTextSize(valueSize);
		mainStackValueLabels[ChipCase.CHIP_C].setMaxDimensions((int)(screenWidth*0.33f),(int)(screenHeight*0.023f));
		mainStackValueLabels[ChipCase.CHIP_C].setTextSize(valueSize);
		
		divisibilityDialog.setDimensions((int)(screenWidth*0.3f),(int)(screenHeight*0.06f));
		manualConnectDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.45f));
		destroyTableDialogSmall.setDimensions(1,1);
		for (int i=0;i<Table.NUM_SEATS;i++) {
			hostPlayerDialogs[i].setDimensions((int)(screenWidth*0.12f),(int)(screenHeight*0.07f));
		}
		hostPotDialog.setDimensions((int)(screenWidth*0.12f),(int)(screenHeight*0.13f));
		loadDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.45f));
		potArrowLeft.setDimensions((int)(screenHeight*0.08f),(int)(screenHeight*0.08f));
		potArrowRight.setDimensions((int)(screenHeight*0.08f),(int)(screenHeight*0.08f));
		azimuthLabel.setMaxDimensions((int)(screenHeight*0.1f),(int)(screenHeight*0.05f));
		azimuthLabel.setTextSizeToMax();
	}
	
	private void setPositions(float screenWidth,float screenHeight) {
		homeUIAnimation.setPositions(screenWidth,screenHeight);
		homeForegroundAnimation.setPositions(screenWidth,screenHeight);
		
		wifiButton.setPosition(screenWidth*0.5f,screenHeight*0.85f);
		foldButton.setPosition(screenWidth*0.1f,screenHeight*0.76f);
		
		posHostUndoOffscreen.set(screenWidth-screenHeight*0.1f,screenHeight*1.2f);
		posHostUndoOnscreen.set(screenWidth-screenHeight*0.1f,screenHeight*0.9f);
		posHostExitOffscreen.set(0+screenHeight*0.1f,screenHeight*1.2f);
		posHostExitOnscreen.set(0+screenHeight*0.1f,screenHeight*0.75f);
		
		hostUndoButton.setPosition(posHostUndoOffscreen);
		hostExitButton.setPosition(posHostExitOffscreen);
		hostGameNameDialog.setPositions(0+hostGameNameDialog.radiusX+screenHeight*0.02f,screenHeight*1.3f,
				0+hostGameNameDialog.radiusX+screenHeight*0.02f,screenHeight-hostGameNameDialog.radiusY-screenHeight*0.03f);
		
		helpDialog.setPosition(screenWidth*0.5f,screenHeight*0.5f);
		
		playerLoginDialog.setPositions(screenWidth*0.5f,-screenHeight*0.5f,screenWidth*0.5f,screenHeight*0.5f);
		playerBuyinDialog.setPositions(screenWidth*0.5f,screenHeight*1.5f,screenWidth*0.5f,screenHeight*0.5f);
		playerLeaveDialog.setPositions(screenWidth*0.5f,screenHeight*1.5f,screenWidth*0.5f,screenHeight*0.5f);
		hostNameDialog.setPositions(screenWidth*0.5f,screenHeight*1.5f,
				screenWidth*0.5f,screenHeight*0.5f,
				-screenWidth*0.5f,screenHeight*0.5f);
		hostChipCaseDialog.setPositions(screenWidth*1.5f,screenHeight*0.5f,
				screenWidth*0.5f,screenHeight*0.5f,
				-screenWidth*0.5f,screenHeight*0.5f);
		hostChipSetupDialog.setPositions(screenWidth*1.5f,screenHeight*0.5f,
				screenWidth*0.5f,screenHeight*0.5f,
				-screenWidth*0.5f,screenHeight*0.5f);
		hostLobbyDialog.setPositions(screenWidth*1.5f,screenHeight*0.5f,
				screenWidth*0.5f,screenHeight*0.5f,
				-screenWidth*0.5f,screenHeight*0.5f);
		hostRearrangeDialog.setPositions(screenWidth*0.5f,screenHeight*1.5f,
				screenWidth*0.5f,screenHeight*0.5f);
		hostPotDialog.setPositions(screenWidth*0.73f,screenHeight*1.5f,
				screenWidth*0.73f,screenHeight*0.5f);
		
		hostDestroyDialog.setPositions(screenWidth*0.5f,screenHeight*1.5f,screenWidth*0.5f,screenHeight*0.5f);
		
		playerDashboard.setPositions(screenWidth*0.5f,-playerDashboard.radiusY*2,screenWidth*0.5f,playerDashboard.radiusY);
		int margin = (int) (screenHeight*0.01f);
		betTotalDialog.setPositions(0+margin+betTotalDialog.radiusX,screenHeight+betTotalDialog.radiusY*4f,
									0+margin+betTotalDialog.radiusX,screenHeight-margin-betTotalDialog.radiusY);
		
		gameMenu.setPositions(screenWidth, screenHeight);
		
		divisibilityDialog.setPosition(screenWidth*0.5f,screenHeight*0.08f);
		manualConnectDialog.setPosition(screenWidth*0.5f,screenHeight*0.5f);
		destroyTableDialogSmall.setPosition(screenWidth*0.5f,screenHeight*0.95f);
		loadDialog.setPosition(screenWidth*0.5f,screenHeight*0.5f);
		potArrowLeft.setPosition(screenWidth*0.35f,screenHeight*0.5f);
		potArrowRight.setPosition(screenWidth*0.65f,screenHeight*0.5f);
		azimuthLabel.setPosition(azimuthLabel.maxRadiusX,azimuthLabel.maxRadiusY);
	}
	
	private void scalePositions(float scaleX,float scaleY) {
		wifiButton.scalePosition(scaleX,scaleY);

		divisibilityDialog.scalePosition(scaleX,scaleY);
		dialogWindow.scalePosition(scaleX,scaleY);
		dialogWArrowWindow.scalePosition(scaleX,scaleY);
		manualConnectDialog.scalePosition(scaleX,scaleY);
	}
	
	public void start() {
		foregroundRenderer.loadTextures(game.manager);
	}
	
	public void resume() {
		
	}
	
	public void render(float delta) {
		controlLogic();
		if (!game.freezeAnimation) {
			animate(delta);
		}
		foregroundRenderer.render();
	}
	
	//////////////////// Controllers ////////////////////
	public void controlLogic() {
		if (openHelpOnStartup) {
			openHelpOnStartup=false;
			openHelp();
			stopHomeUI();
		}
	}
	
	public void animate(float delta) {
		homeUIAnimation.animate(delta);
		homeForegroundAnimation.animate(delta);
		
		wifiButton.animate(delta);
		
		dialogWindow.animate(delta);
		dialogWArrowWindow.animate(delta);
		helpDialog.animate(delta);
		playerLoginDialog.animate(delta);
		playerLeaveDialog.animate(delta);
		hostNameDialog.animate(delta);
		hostChipCaseDialog.animate(delta);
		hostChipSetupDialog.animate(delta);
		hostLobbyDialog.animate(delta);
		hostRearrangeDialog.animate(delta);
		hostGameNameDialog.animate(delta);
		hostDestroyDialog.animate(delta);
		playerBuyinDialog.animate(delta);
		playerDashboard.animate(delta);
		betTotalDialog.animate(delta);
		gameMenu.animate(delta);
		manualConnectDialog.animate(delta);
		for (int i=0;i<Table.NUM_SEATS;i++) {
			hostPlayerDialogs[i].animate(delta);
		}
		hostPotDialog.animate(delta);
		loadDialog.animate(delta);
		divisibilityDialog.animate(delta);
		potArrowLeft.animate(delta);
		potArrowRight.animate(delta);
		
		foldButton.animate(delta);
		
		hostUndoButton.animate(delta);
		hostExitButton.animate(delta);
		
		secondTimer+=delta*1000;
		if (DPCGame.debugMode&&secondTimer>=300) {
			secondTimer=0;
			azimuthLabel.setText(Integer.toString(game.calculateAzimuth()));
			azimuthLabel.loadTexture();
		}
	}

	//////////////////// Instructions from World ////////////////////
	public void startHomeForeground() {
		Logger.log(LOG_TAG,"startHomeForeground()");
		homeForegroundAnimation.begin(0);
	}
	
	public void stopHomeForeground() {
		Logger.log(LOG_TAG,"startHomeForeground()");
		homeForegroundAnimation.end();
	}
	
	public void startHomeUI() {
		Logger.log(LOG_TAG,"startHomeUI()");
		input.pushTouchFocus(ForegroundInput.TOUCH_HOME);
		homeUIAnimation.begin(0);
	}
	
	public void stopHomeUI() {
		Logger.log(LOG_TAG,"stopHomeUI()");
		input.popTouchFocus(ForegroundInput.TOUCH_HOME);
		homeUIAnimation.end();
	}
	
	//////////////////// Instructions from Foreground ////////////////////
	public void openHelp() {
		Logger.log(LOG_TAG,"openHelp()");
	}

	public void closeHelp() {
		Logger.log(LOG_TAG,"closeHelp()");
	}
	
	public void openLoadDialog() {
		Logger.log(LOG_TAG,"openLoadDialog()");
		loadDialogOpen=true;
		dialogWindow.opacity=1;
		dialogWindow.setPosition(loadDialogSmall.x,loadDialogSmall.y);
		dialogWindow.setDimensions(loadDialogSmall.radiusX,loadDialogSmall.radiusY);
		dialogWindow.sendTo(loadDialog);
		loadDialog.disappear();
		String[] tableNames=game.mWL.table.tableStore.getTableNames(Table.SAVE_NUM_SLOTS);
		loadDialog.populateSlots(tableNames);
		int defaultSelection=-1;
		for (int i=0;i<tableNames.length;i++) {
			if (tableNames[i]!=null) {
				defaultSelection=i;
				break;
			}
		}
		loadDialog.slotSelected(defaultSelection);
		input.pushTouchFocus(ForegroundInput.TOUCH_LOAD_DIALOG);
	}
	
	public void closeLoadDialog() {
		Logger.log(LOG_TAG,"closeLoadDialog()");
		loadDialogOpen=false;
		dialogWindow.sendTo(loadDialogSmall);
		loadDialog.stop();
		input.popTouchFocus(ForegroundInput.TOUCH_LOAD_DIALOG);
	}
	
	public void loadDialogDone(boolean actionCompleted) {
		if (actionCompleted) {
			int loadSlot=loadDialog.getSelectedSlot()+1;
			game.mWL.table.loadTable(loadSlot);
		} else {
			startHomeUI();
		}
		closeLoadDialog();
	}
	
	public void howSelected() {
		stopHomeUI();
		openHelp();
	}
	
	public void loadSelected() {
		stopHomeUI();
		openLoadDialog();
	}
	
	//////////////////// Instructions from Player ////////////////////
	public void startWifiPrompt() {
		Logger.log(LOG_TAG,"startWifiPrompt()");
		wifiButton.startFlashing();
		wifiButton.setTouchable(true);
		input.pushTouchFocus(ForegroundInput.TOUCH_NO_WIFI);
	}
	
	public void stopWifiPrompt() {
		Logger.log(LOG_TAG,"stopWifiPrompt()");
		wifiButton.fadeOut();
		wifiButton.setTouchable(false);
		input.popTouchFocus(ForegroundInput.TOUCH_NO_WIFI);
	}
	
	public void startSearchForGames() {
		Logger.log(LOG_TAG,"startSearchForGames()");
		playerDashboard.setStatusMessage("SEARCHING FOR GAMES");
	}
	
	public void stopSearchForGames() {
		Logger.log(LOG_TAG,"stopSearchForGames()");
		playerDashboard.setStatusMessage("");
	}
	

	
	public void startManualConnectDialog() {
		Logger.log(LOG_TAG,"startManualConnectDialog()");
		dialogWindow.setOpacity(1);
		dialogWindow.sendTo(manualConnectDialog);
		manualConnectDialog.disappear();
		input.pushTouchFocus(ForegroundInput.TOUCH_MANUAL_CONNECT);
		input.setTypingFocus(ForegroundInput.TYPING_MANUAL_CONNECT);
	}
	
	public void stopManualConnectDialog() {
		Logger.log(LOG_TAG,"startManualConnectDialog()");
		dialogWindow.remove();
		manualConnectDialog.stop();
		input.popTouchFocus(ForegroundInput.TOUCH_MANUAL_CONNECT);
		input.setTypingFocus(ForegroundInput.TYPING_NONE);
	}
	
	public void startHelpDialog() {
		Logger.log(LOG_TAG,"startPlayerLoginDialog()");
		dialogWindow.setOpacity(1);
		dialogWindow.setPosition(homeUIAnimation.helpButton.x,homeUIAnimation.helpButton.y);
		dialogWindow.setDimensions(helpDialogSmall.radiusX,helpDialogSmall.radiusY);
		dialogWindow.sendTo(helpDialog);
		helpDialog.disappear();
		input.pushTouchFocus(ForegroundInput.TOUCH_HELP_DIALOG);
	}
	
	public void stopHelpDialog() {
		Logger.log(LOG_TAG,"stopPlayerLoginDialog()");
		dialogWindow.remove();
		helpDialog.stop();
		input.popTouchFocus(ForegroundInput.TOUCH_HELP_DIALOG);
	}
	
	public void startPlayerLoginDialog() {
		Logger.log(LOG_TAG,"startPlayerLoginDialog()");
		playerLoginDialog.show();
		input.pushTouchFocus(ForegroundInput.TOUCH_PLAYER_LOGIN);
	}
	
	public void stopPlayerLoginDialog() {
		Logger.log(LOG_TAG,"stopPlayerLoginDialog()");
		playerLoginDialog.hide();
		Gdx.input.setOnscreenKeyboardVisible(false);
		input.popTouchFocus(ForegroundInput.TOUCH_PLAYER_LOGIN);
	}
	
	public void startPlayerLeaveDialog(String tableName) {
		Logger.log(LOG_TAG,"startPlayerLeaveDialog("+tableName+")");
		playerLeaveDialog.setTableName(tableName);
		playerLeaveDialog.show();
		input.pushTouchFocus(ForegroundInput.TOUCH_LEAVE_TABLE);
	}
	
	public void stopPlayerLeaveDialog() {
		Logger.log(LOG_TAG,"stopLeaveTableDialog()");
		playerLeaveDialog.hide();
		input.popTouchFocus(ForegroundInput.TOUCH_LEAVE_TABLE);
	}
	
	public void startHostNameDialog() {
		Logger.log(LOG_TAG,"startHostNameDialog()");
		hostNameDialog.show();
		input.pushTouchFocus(ForegroundInput.TOUCH_HOST_CREATE);
	}
	
	public void backHostNameDialog() {
		Logger.log(LOG_TAG,"backHostNameDialog()");
		hostNameDialog.back();
		Gdx.input.setOnscreenKeyboardVisible(false);
		input.popTouchFocus(ForegroundInput.TOUCH_HOST_CREATE);
	}
	
	public void nextHostNameDialog() {
		Logger.log(LOG_TAG,"nextHostNameDialog()");
		hostNameDialog.next();
		Gdx.input.setOnscreenKeyboardVisible(false);
		input.popTouchFocus(ForegroundInput.TOUCH_HOST_CREATE);
	}
	
	public void startHostChipCaseDialog() {
		Logger.log(LOG_TAG,"startHostChipCaseDialog()");
		hostChipCaseDialog.show();
		input.pushTouchFocus(ForegroundInput.TOUCH_HOST_CHIPCASE);
	}
	
	public void backHostChipCaseDialog() {
		Logger.log(LOG_TAG,"backHostChipCaseDialog()");
		hostChipCaseDialog.back();
		input.popTouchFocus(ForegroundInput.TOUCH_HOST_CHIPCASE);
	}
	
	public void nextHostChipCaseDialog() {
		Logger.log(LOG_TAG,"nextHostChipCaseDialog()");
		hostChipCaseDialog.next();
		input.popTouchFocus(ForegroundInput.TOUCH_HOST_CHIPCASE);
	}
	
	public void startHostChipSetupDialog() {
		Logger.log(LOG_TAG,"startHostChipSetupDialog()");
		hostChipSetupDialog.show();
		input.pushTouchFocus(ForegroundInput.TOUCH_HOST_CHIP_SETUP);
	}
	
	public void backHostChipSetupDialog() {
		Logger.log(LOG_TAG,"backHostChipSetupDialog()");
		hostChipSetupDialog.back();
		input.popTouchFocus(ForegroundInput.TOUCH_HOST_CHIP_SETUP);
	}
	
	public void nextHostChipSetupDialog() {
		Logger.log(LOG_TAG,"nextHostChipSetupDialog()");
		hostChipSetupDialog.next();
		input.popTouchFocus(ForegroundInput.TOUCH_HOST_CHIP_SETUP);
	}
	
	public void startHostLobbyDialog() {
		Logger.log(LOG_TAG,"startHostLobbyDialog()");
		hostLobbyDialog.show(game.mWL.table.gameName);
		hostLobbyDialog.setStartEnabled(false);
		input.pushTouchFocus(ForegroundInput.TOUCH_HOST_LOBBY);
	}
	
	public void backHostLobbyDialog() {
		Logger.log(LOG_TAG,"backHostLobbyDialog()");
		hostLobbyDialog.back();
		input.popTouchFocus(ForegroundInput.TOUCH_HOST_LOBBY);
	}
	
	public void nextHostLobbyDialog() {
		Logger.log(LOG_TAG,"nextHostLobbyDialog()");
		hostLobbyDialog.next();
		input.popTouchFocus(ForegroundInput.TOUCH_HOST_LOBBY);
	}
	
	public void startHostDealerDialog() {
		Logger.log(LOG_TAG,"startHostDealerDialog()");
		hostLobbyDialog.showDealerDialog();
	}
	
	public void backHostDealerDialog() {
		Logger.log(LOG_TAG,"backHostDealerDialog()");
		hostLobbyDialog.showWaitingDialog();
	}
	
	public void nextHostDealerDialog() {
		Logger.log(LOG_TAG,"nextHostDealerDialog()");
		hostLobbyDialog.next();
		input.popTouchFocus(ForegroundInput.TOUCH_HOST_LOBBY);
	}
	
	public void startHostRearrangeDialog() {
		Logger.log(LOG_TAG,"startHostRearrangeDialog()");
		hostRearrangeDialog.show(game.mWL.table.gameName);
		input.pushTouchFocus(ForegroundInput.TOUCH_HOST_REARRANGE);
	}
	
	public void nextHostRearrangeDialog() {
		Logger.log(LOG_TAG,"backHostRearrangeDialog()");
		hostRearrangeDialog.hide();
		input.popTouchFocus(ForegroundInput.TOUCH_HOST_REARRANGE);
	}
	
	public void startBuyin(String tableName) {
		Logger.log(LOG_TAG,"startBuyin()");
		playerBuyinDialog.show(tableName,ChipCase.values);
		input.pushTouchFocus(ForegroundInput.TOUCH_BUYIN);
	}
	
	public void stopBuyin() {
		Logger.log(LOG_TAG,"stopBuyin()");
		playerBuyinDialog.hide();
		input.popTouchFocus(ForegroundInput.TOUCH_BUYIN);
	}
	
	public boolean checkBuyinOffscreen() {
		boolean offscreen=false;
		if (playerBuyinDialog.y-playerBuyinDialog.radiusY>screenHeight) {
			offscreen=true;
		}
		return offscreen;
	}
	
	public void showPlayerDashboard(String playerName) {
		Logger.log(LOG_TAG,"showPlayerDashboard()");
		playerDashboard.setName(playerName);
		playerDashboard.show();
	}
	
	public void hidePlayerDashboard() {
		Logger.log(LOG_TAG,"hidePlayerDashboard()");
		playerDashboard.hide();
	}
	
	public void showBetTotalDialog() {
		Logger.log(LOG_TAG,"showBetTotalDialog()");
		betTotalDialog.show();
	}
	
	public void hideBetTotalDialog() {
		Logger.log(LOG_TAG,"hideBetTotalDialog()");
		betTotalDialog.hide();
	}
	
	public void startDestroyTableDialog(String tableName) {
		Logger.log(LOG_TAG,"startDestroyTableDialog("+tableName+")");
		hostDestroyDialog.setTableName(tableName);
		hostDestroyDialog.show();
		input.pushTouchFocus(ForegroundInput.TOUCH_HOST_DESTROY);
	}
	
	public void stopDestroyTableDialog() {
		Logger.log(LOG_TAG,"stopDestroyTableDialog()");
		hostDestroyDialog.hide();
		input.popTouchFocus(ForegroundInput.TOUCH_HOST_DESTROY);
	}
	
	public void notifyAtPlayerPosition() {
		Logger.log(LOG_TAG,"notifyAtPlayerPosition()");
		input.pushTouchFocus(ForegroundInput.TOUCH_PLAYER);
	}
	
	public void notifyLeftPlayerPosition() {
		Logger.log(LOG_TAG,"notifyLeftPlayerPosition()");
		input.popTouchFocus(ForegroundInput.TOUCH_PLAYER);
	}
	
	public void notifyAtHostPosition() {
		Logger.log(LOG_TAG,"notifyAtPlayerPosition()");
		input.pushTouchFocus(ForegroundInput.TOUCH_HOST);
		hostNameDialog.initialisePosition();
		hostChipCaseDialog.initialisePosition();
		hostChipSetupDialog.initialisePosition();
		hostLobbyDialog.initialisePosition();
		hostRearrangeDialog.initialisePosition();
	}
	
	public void notifyLeftHostPosition() {
		Logger.log(LOG_TAG,"notifyLeftPlayerPosition()");
		input.popTouchFocus(ForegroundInput.TOUCH_HOST);
	}
	
	public void startReconnect() {
		Logger.log(LOG_TAG,"startReconnect()");
	}
	
	public void stopReconnect() {
		Logger.log(LOG_TAG,"stopReconnect()");
	}
	
	public void stopWaitNextHand() {
		Logger.log(LOG_TAG,"stopWaitNextHand()");
	}
	
	//////////////////// Instructions from Table ////////////////////

	
	public void startLobbyLoaded() {
		Logger.log(LOG_TAG,"startLobbyLoaded()");
		input.pushTouchFocus(ForegroundInput.TOUCH_LOBBY_LOADED);
	}
	
	public void notifyGameStarting(String gameName) {
		Logger.log(LOG_TAG,"notifyGameStarting()");
		//hostUndoButton.setDest(posHostUndoOnscreen);
		//hostUndoButton.setTouchable(true);
		hostExitButton.setDest(posHostExitOnscreen);
		hostExitButton.setTouchable(true);
		hostGameNameDialog.setGameName(gameName);
		hostGameNameDialog.setStateText("Game Starting");
		hostGameNameDialog.show();
		input.pushTouchFocus(ForegroundInput.TOUCH_TABLE_GAMEPLAY);
	}
	
	public void stopGame() {
		Logger.log(LOG_TAG,"stopGame()");
		hostUndoButton.setDest(posHostUndoOffscreen);
		hostUndoButton.setTouchable(false);
		hostExitButton.setDest(posHostExitOffscreen);
		hostExitButton.setTouchable(false);
		hostGameNameDialog.hide();
		input.popTouchFocus(ForegroundInput.TOUCH_TABLE_GAMEPLAY);
	}
	
	public void notifyGameCanStart(boolean fadeButton) {
		Logger.log(LOG_TAG,"notifyGameCanStart()");
		hostLobbyDialog.setStartEnabled(true);
	}
	
	public void notifyGameCantStart() {
		Logger.log(LOG_TAG,"notifyGameCantStart()");
		hostLobbyDialog.setStartEnabled(false);
	}

	public void startSelectWinner() {
		Logger.log(LOG_TAG,"startSelectWinner()");
		hostPotDialog.setStateSingleWinner();
		hostPotDialog.setTitle("Main Pot");
		hostPotDialog.setSplitEnabled(false);
		hostPotDialog.show();
	}
	
	public void stopSelectWinner() {
		Logger.log(LOG_TAG,"stopSelectWinner()");
		hostPotDialog.hide();
	}
	
	public void startPlayerDialog(Player currBetter,int seatIndex) {
		Logger.log(LOG_TAG,"startPlayerDialog()");
		hostPlayerDialogs[seatIndex].setRotation(currBetter.rotation);
		float xOffscreen = (float) (currBetter.x+Math.sin(Math.toRadians(currBetter.rotation))*Seat.radiusY*2);
		float yOffscreen = (float) (currBetter.y-Math.cos(Math.toRadians(currBetter.rotation))*Seat.radiusY*2);
		float xOnscreen = currBetter.x;
		float yOnscreen = currBetter.y;
		if (currBetter.betStack.size()>0) {
			Vector2 topPixel = currBetter.betStack.getTopPixel();
			xOnscreen = topPixel.x;
			yOnscreen = topPixel.y;
		}
		xOffscreen = game.mWL.worldRenderer.xWorldToScreen(xOffscreen);
		yOffscreen = game.mWL.worldRenderer.yWorldToScreen(yOffscreen);
		xOnscreen = (float) (game.mWL.worldRenderer.xWorldToScreen(xOnscreen)-
				Math.sin(Math.toRadians(currBetter.rotation))*hostPlayerDialogs[seatIndex].radiusY*1.2);
		yOnscreen = (float) (game.mWL.worldRenderer.yWorldToScreen(yOnscreen)+
				Math.cos(Math.toRadians(currBetter.rotation))*hostPlayerDialogs[seatIndex].radiusY*1.2);
		hostPlayerDialogs[seatIndex].setPositions(xOffscreen,yOffscreen,xOnscreen,yOnscreen);
		hostPlayerDialogs[seatIndex].setPlayerName(currBetter.name.getText());
		hostPlayerDialogs[seatIndex].show();
	}
	
	public void stopPlayerDialog(int seatIndex) {
		Logger.log(LOG_TAG,"stopPlayerDialog()");
		hostPlayerDialogs[seatIndex].hide();
	}

	
}
