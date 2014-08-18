package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.m.AutosaveDialog;
import com.bidjee.digitalpokerchips.m.BootDialog;
import com.bidjee.digitalpokerchips.m.Button;
import com.bidjee.digitalpokerchips.m.BuyinDialog;
import com.bidjee.digitalpokerchips.m.ChipCase;
import com.bidjee.digitalpokerchips.m.ClosedDialog;
import com.bidjee.digitalpokerchips.m.DestroyTableDialog;
import com.bidjee.digitalpokerchips.m.DialogWindow;
import com.bidjee.digitalpokerchips.m.GameLogic;
import com.bidjee.digitalpokerchips.m.LeaveTableDialog;
import com.bidjee.digitalpokerchips.m.ManualConnectDialog;
import com.bidjee.digitalpokerchips.m.Player;
import com.bidjee.digitalpokerchips.m.PlayerIDDialog;
import com.bidjee.digitalpokerchips.m.PlayerLoginDialog;
import com.bidjee.digitalpokerchips.m.SearchingAnimation;
import com.bidjee.digitalpokerchips.m.TableStatusMenu;
import com.bidjee.digitalpokerchips.m.TextLabel;
import com.bidjee.digitalpokerchips.v.ForegroundRenderer;
import com.bidjee.util.Logger;

public class ForegroundLayer {
	
	public static final String LOG_TAG = "DPCUILayer";
	
	//////////////////// Constants ////////////////////
	static final String WIN_TEXT = " Wins Dealer!";
	static final String MEASURE_IP = "999.999.999.999";
	
	//////////////////// State Variables ////////////////////
	boolean screenLaidOut;
	boolean loadDialogOpen;
	boolean runTutorialArrangement;
	boolean openHelpOnStartup;
	boolean showPlayerPrompt;
	
	//////////////////// Screen Scale & Layout ////////////////////
	public float screenWidth;
	public float screenHeight;
	float oldScreenWidth;
	float oldScreenHeight;
	float limFlingVelocity;
	Vector2 posPlayerPromptOffscreen=new Vector2();
	Vector2 posPlayerPromptOnscreen=new Vector2();
	
	//////////////////// Controllers ////////////////////
	GestureDetector gestureInput;
	public ForegroundInput input;
	public HomeUIAnimation homeUIAnimation=new HomeUIAnimation();
	public TutorialArrangement tutorialArrangement;
	
	//////////////////// Models ////////////////////
	public DialogWindow dialogWindow;
	public DialogWindow playerIDWindow;
	public DialogWindow dialogWArrowWindow;
	public ClosedDialog playerLoginDialogSmall;
	public PlayerLoginDialog playerLoginDialog;
	public ClosedDialog playerIDDialogSmall;
	public PlayerIDDialog playerIDDialog;
	public ClosedDialog helpDialogSmall;
	public ClosedDialog loadDialogSmall;
	private ClosedDialog buyinDialogSmall;
	public ClosedDialog leaveTableDialogSmall;
	public ClosedDialog destroyTableDialogSmall;
	public ClosedDialog bootDialogSmall;
	public BuyinDialog buyinDialog;
	public LeaveTableDialog leaveTableDialog;
	public DestroyTableDialog destroyTableDialog;
	public BootDialog bootDialog;
	public AutosaveDialog autosaveDialog;
	public ClosedDialog autosaveDialogSmall;
	public DivisibilityDialog divisibilityDialog;
	public LoadDialog loadDialog;
	public ManualConnectDialog manualConnectDialog;
	
	public TableStatusMenu tableStatusMenu;
	public SearchingAnimation searchingAnimation;
	
	//////////////////// Sprites ////////////////////
	public Button wifiButton;
	public Button enterNameDoneButton;
	public Button enterTableNameDoneButton;
	public Button setValuesOkButton;
	public Button[] valueUpArrows=new Button[ChipCase.CHIP_TYPES];
	public Button[] valueDownArrows=new Button[ChipCase.CHIP_TYPES];
	public Button gotoGameButton;
	public Button foldButton;
	public Button potArrowRight;
	public Button potArrowLeft;
	public Button splitButton;
	public Button splitCancelButton;
	public Button splitDoneButton;
	
	//////////////////// Text Labels ////////////////////
	public TextLabel wifiLabel;
	public TextLabel playerPrompt;
	public TextLabel stateChangePrompt;
	public TextLabel enterName1Label;
	public TextLabel enterName2Label;
	public TextLabel enterTableName1Label;
	public TextLabel enterTableName2Label;
	public TextLabel searchingLabel;
	public TextLabel setValuesLabel;
	public TextLabel selectingDealerLabel;
	public TextLabel winLabel;
	public TextLabel blindsInLabel;
	public TextLabel flopLabel;
	public TextLabel turnLabel;
	public TextLabel riverLabel;
	public TextLabel selectWinnerLabel;
	public TextLabel selectWinnersSplitLabel;
	public TextLabel reconnect1Label;
	public TextLabel reconnect2Label;
	public TextLabel waitNextHandLabel;
	public TextLabel ipAddress;
	
	//////////////////// Renderers ////////////////////
	public ForegroundRenderer foregroundRenderer;

	//////////////////// References ////////////////////
	public DPCGame game;
	
	//////////////////// Debugging ////////////////////
	public TextLabel azimuthLabel;
	int secondTimer=0;
	
	public ForegroundLayer(DPCGame game) {		
		this.game=game;
		foregroundRenderer=new ForegroundRenderer(this);
		
		input=new ForegroundInput();
		input.setScreen(this);
		ForegroundGestureInput mFGI=new ForegroundGestureInput();
		gestureInput=new GestureDetector(mFGI);
		mFGI.setScreen(this);
		runTutorialArrangement=false;

		openHelpOnStartup=false;
		tutorialArrangement=new TutorialArrangement();
		
		wifiButton=new Button(true,0,"");
		wifiLabel=new TextLabel("Please Connect to WiFi",0,true,0,false);
		wifiLabel.setFontFace("segoe_print.ttf");
		
		playerPrompt=new TextLabel("",0,true,0,false);
		stateChangePrompt=new TextLabel("",0,true,0,false);
		stateChangePrompt.setFontFace("segoe_print.ttf");
		
		enterName1Label=new TextLabel("Enter Player's",0,true,0,false);
		enterName1Label.setFontFace("segoe_print.ttf");
		enterName2Label=new TextLabel("Name",0,true,0,false);
		enterName2Label.setFontFace("segoe_print.ttf");
		enterNameDoneButton=new Button(true,0,"");
		
		enterTableName1Label=new TextLabel("Enter Table's",0,true,0,false);
		enterTableName1Label.setFontFace("segoe_print.ttf");
		enterTableName2Label=new TextLabel("Name",0,true,0,false);
		enterTableName2Label.setFontFace("segoe_print.ttf");
		enterTableNameDoneButton=new Button(true,0,"");
		
		searchingLabel=new TextLabel("Searching for Tables",0,true,0,false);
		searchingLabel.setFontFace("segoe_print.ttf");
		
		setValuesLabel=new TextLabel("Set Chip Values",0,true,0,false);
		setValuesLabel.setFontFace("segoe_print.ttf");
		setValuesOkButton=new Button(true,0,"");
		setValuesOkButton.setTouchable(true);
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			valueUpArrows[i]=new Button(true,0,"");
			valueUpArrows[i].setTouchable(true);
			valueUpArrows[i].setTouchAreaMultiplier(2f);
			valueDownArrows[i]=new Button(true,0,"");
			valueDownArrows[i].setTouchable(true);
			valueDownArrows[i].setTouchAreaMultiplier(2f);
		}
		divisibilityDialog=new DivisibilityDialog();
		dialogWindow=new DialogWindow();
		dialogWindow.opacity=0;
		playerIDWindow=new DialogWindow();
		playerIDWindow.opacity=0;
		dialogWArrowWindow=new DialogWindow();
		dialogWArrowWindow.opacity=0;
		buyinDialog=new BuyinDialog();
		buyinDialog.opacity=0;
		buyinDialogSmall=new ClosedDialog(dialogWindow);
		buyinDialogSmall.opacity=0;
		manualConnectDialog=new ManualConnectDialog();
		manualConnectDialog.opacity=0;
		leaveTableDialog=new LeaveTableDialog();
		leaveTableDialog.opacity=0;
		leaveTableDialogSmall=new ClosedDialog(dialogWindow);
		leaveTableDialogSmall.opacity=0;
		destroyTableDialog=new DestroyTableDialog();
		destroyTableDialog.opacity=0;
		destroyTableDialogSmall=new ClosedDialog(dialogWindow);
		destroyTableDialogSmall.opacity=0;
		bootDialog=new BootDialog();
		bootDialog.opacity=0;
		bootDialogSmall=new ClosedDialog(dialogWArrowWindow);
		bootDialogSmall.opacity=0;
		autosaveDialog=new AutosaveDialog();
		autosaveDialog.opacity=0;
		autosaveDialogSmall=new ClosedDialog(dialogWindow);
		autosaveDialogSmall.opacity=0;
		tableStatusMenu=new TableStatusMenu(this);
		tableStatusMenu.opacity=0;
		
		searchingAnimation=new SearchingAnimation();
		searchingAnimation.opacity=0;
		
		gotoGameButton=new Button(true,0,"Play Game");
		gotoGameButton.opacity=0;
		gotoGameButton.getLabel().outline=true;
		
		potArrowLeft=new Button(true,0,"");
		potArrowRight=new Button(true,0,"");
		
		splitButton=new Button(true,0,"Split");
		splitCancelButton=new Button(true,0,"Cancel");
		splitDoneButton=new Button(true,0,"Done");
		
		playerLoginDialogSmall=new ClosedDialog(dialogWindow);
		playerLoginDialog=new PlayerLoginDialog();
		
		playerIDDialogSmall=new ClosedDialog(playerIDWindow);
		playerIDDialog=new PlayerIDDialog();
		
		helpDialogSmall=new ClosedDialog(dialogWindow);
		helpDialogSmall.opacity=0;
		loadDialog=new LoadDialog();
		loadDialog.opacity=0;
		loadDialogSmall=new ClosedDialog(dialogWindow);
		loadDialogSmall.opacity=0;
		foldButton=new Button(true,0,"");
		
		selectingDealerLabel=new TextLabel("Selecting Dealer",0,true,0,false);
		selectingDealerLabel.setFontFace("segoe_print.ttf");
		winLabel=new TextLabel("",0,true,0,false);
		winLabel.setFontFace("segoe_print.ttf");
		
		blindsInLabel=new TextLabel("Blinds In Please",0,true,0,false);
		blindsInLabel.setFontFace("segoe_print.ttf");
		flopLabel=new TextLabel("Flop",0,true,0,false);
		flopLabel.setFontFace("segoe_print.ttf");
		turnLabel=new TextLabel("Turn",0,true,0,false);
		turnLabel.setFontFace("segoe_print.ttf");
		riverLabel=new TextLabel("River",0,true,0,false);
		riverLabel.setFontFace("segoe_print.ttf");
		selectWinnerLabel=new TextLabel("Fling Pot to Winner",0,true,0,false);
		selectWinnerLabel.setFontFace("segoe_print.ttf");
		selectWinnersSplitLabel=new TextLabel("Tap All Winners",0,true,0,false);
		selectWinnersSplitLabel.setFontFace("segoe_print.ttf");
		reconnect1Label=new TextLabel("Connection Lost",0,true,0,false);
		reconnect1Label.setFontFace("segoe_print.ttf");
		reconnect2Label=new TextLabel("Attempting to Reconnect",0,true,0,false);
		reconnect2Label.setFontFace("segoe_print.ttf");
		waitNextHandLabel=new TextLabel("Wait for Next Hand",0,true,0,false);
		waitNextHandLabel.setFontFace("segoe_print.ttf");
		ipAddress=new TextLabel("",0,true,0,false);
		
		azimuthLabel=new TextLabel("360",0,true,1,false);
	}
	
	public void dispose() {
		foregroundRenderer.dispose();
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
		homeUIAnimation.setDimensions(screenWidth,screenHeight);
		wifiButton.setDimensions((int)(screenHeight*0.08f),(int)(screenHeight*0.08f));
		foldButton.setDimensions((int)(screenHeight*0.15f),(int)(screenHeight*0.15f));
		playerPrompt.setMaxDimensions((int)(screenWidth*0.20f),(int)(screenHeight*0.04f));
		playerPrompt.setText(GameLogic.PROMPT_MEASURE);
		playerPrompt.setTextSizeToMax();
		playerPrompt.setText("");
		stateChangePrompt.setMaxDimensions((int)(screenWidth*0.20f),(int)(screenHeight*0.1f));
		stateChangePrompt.setText(GameLogic.PROMPT_MEASURE);
		stateChangePrompt.setTextSizeToMax();
		stateChangePrompt.setText("");
		enterName1Label.setMaxDimensions((int)(screenWidth*0.14f),(int)(screenHeight*0.2f));
		enterName2Label.setMaxDimensions((int)(screenWidth*0.14f),(int)(screenHeight*0.2f));
		int textSize=Math.min(DPCGame.textFactory.getMaxTextSize(enterName1Label),
				DPCGame.textFactory.getMaxTextSize(enterName2Label));
		enterName1Label.setTextSize(textSize);
		enterName2Label.setTextSize(textSize);
		enterNameDoneButton.setDimensions((int)(screenHeight*0.08f),(int)(screenHeight*0.08f));
		enterTableName1Label.setMaxDimensions((int)(screenWidth*0.12f),(int)(screenHeight*0.1f));
		enterTableName2Label.setMaxDimensions((int)(screenWidth*0.12f),(int)(screenHeight*0.1f));
		textSize=Math.min(DPCGame.textFactory.getMaxTextSize(enterTableName1Label),
				DPCGame.textFactory.getMaxTextSize(enterTableName2Label));
		enterTableName1Label.setTextSize(textSize);
		enterTableName2Label.setTextSize(textSize);
		enterTableNameDoneButton.setDimensions((int)(screenHeight*0.08f),(int)(screenHeight*0.08f));
		searchingLabel.setMaxDimensions((int)(screenWidth*0.24f),(int)(screenHeight*0.1f));
		wifiLabel.setMaxDimensions((int)(screenWidth*0.24f),(int)(screenHeight*0.1f));
		reconnect1Label.setMaxDimensions((int)(screenWidth*0.24f),(int)(screenHeight*0.1f));
		reconnect2Label.setMaxDimensions((int)(screenWidth*0.24f),(int)(screenHeight*0.1f));
		waitNextHandLabel.setMaxDimensions((int)(screenWidth*0.24f),(int)(screenHeight*0.1f));
		textSize=Math.min(DPCGame.textFactory.getMaxTextSize(searchingLabel),
				DPCGame.textFactory.getMaxTextSize(wifiLabel));
		textSize=Math.min(textSize,DPCGame.textFactory.getMaxTextSize(reconnect1Label));
		textSize=Math.min(textSize,DPCGame.textFactory.getMaxTextSize(reconnect2Label));
		textSize=Math.min(textSize,DPCGame.textFactory.getMaxTextSize(waitNextHandLabel));
		searchingLabel.setTextSize(textSize);
		wifiLabel.setTextSize(textSize);
		reconnect1Label.setTextSize(textSize);
		reconnect2Label.setTextSize(textSize);
		waitNextHandLabel.setTextSize(textSize);
		setValuesLabel.setMaxDimensions((int)(screenWidth*0.14f),(int)(screenHeight*0.2f));
		setValuesLabel.setTextSizeToMax();
		setValuesOkButton.setDimensions((int)(screenHeight*0.08f),(int)(screenHeight*0.08f));
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			valueDownArrows[i].setDimensions((int)(screenHeight*0.05f),(int)(screenHeight*0.05f));
			valueUpArrows[i].setDimensions((int)(screenHeight*0.05f),(int)(screenHeight*0.05f));
		}
		
		playerLoginDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.45f));
		playerLoginDialogSmall.setDimensions(1,1);
		
		playerIDDialog.setDimensions((int)(screenHeight*0.3f),(int)(screenHeight*0.13f));
		
		divisibilityDialog.setDimensions((int)(screenWidth*0.3f),(int)(screenHeight*0.06f));
		tutorialArrangement.setDimensions(screenWidth,screenHeight);
		buyinDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.45f));
		buyinDialogSmall.setDimensions(1,1);
		manualConnectDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.45f));
		leaveTableDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.45f));
		leaveTableDialogSmall.setDimensions(1,1);
		destroyTableDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.45f));
		destroyTableDialogSmall.setDimensions(1,1);
		bootDialog.setDimensions((int)(screenWidth*0.15f),(int)(screenHeight*0.08f));
		bootDialogSmall.setDimensions(1,1);
		autosaveDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.45f));
		autosaveDialogSmall.setDimensions(1,1);
		loadDialog.setDimensions((int)(screenHeight*0.6f),(int)(screenHeight*0.45f));
		tableStatusMenu.setDimensions((int)(screenWidth*0.16f),(int)(screenHeight*0.45f));
		gotoGameButton.setDimensions((int)(screenHeight*0.16f),(int)(screenHeight*0.08f));
		textSize=Math.min(gotoGameButton.getLabel().getTextSize(),gotoGameButton.getLabel().getTextSize());
		gotoGameButton.getLabel().setTextSize(textSize);
		potArrowLeft.setDimensions((int)(screenHeight*0.08f),(int)(screenHeight*0.08f));
		potArrowRight.setDimensions((int)(screenHeight*0.08f),(int)(screenHeight*0.08f));
		splitButton.setDimensions((int)(screenHeight*0.14f),(int)(screenHeight*0.07f));
		splitButton.yLabelOffset=1.4f;
		splitButton.setLabelDims(0.6f,0.6f);
		splitCancelButton.setDimensions((int)(screenHeight*0.07f),(int)(screenHeight*0.07f));
		splitDoneButton.setDimensions((int)(screenHeight*0.07f),(int)(screenHeight*0.07f));
		searchingAnimation.setDimensions((int)(screenHeight*0.04f),(int)(screenHeight*0.04f));
		int radiusXHostPrompts=(int)(screenWidth*0.3f);
		int radiusYHostPrompts=(int)(screenHeight*0.1f);
		selectingDealerLabel.setMaxDimensions(radiusXHostPrompts,radiusYHostPrompts);
		winLabel.setMaxDimensions(radiusXHostPrompts,radiusYHostPrompts);
		blindsInLabel.setMaxDimensions(radiusXHostPrompts,radiusYHostPrompts);
		flopLabel.setMaxDimensions(radiusXHostPrompts,radiusYHostPrompts);
		turnLabel.setMaxDimensions(radiusXHostPrompts,radiusYHostPrompts);
		riverLabel.setMaxDimensions(radiusXHostPrompts,radiusYHostPrompts);
		selectWinnerLabel.setMaxDimensions(radiusXHostPrompts,radiusYHostPrompts);
		selectWinnersSplitLabel.setMaxDimensions(radiusXHostPrompts,radiusYHostPrompts);
		winLabel.setText(WorldLayer.NAME_MEASURE+WIN_TEXT);
		textSize=DPCGame.textFactory.getMaxTextSize(selectingDealerLabel);
		textSize=Math.min(DPCGame.textFactory.getMaxTextSize(winLabel),textSize);
		textSize=Math.min(DPCGame.textFactory.getMaxTextSize(blindsInLabel),textSize);
		textSize=Math.min(DPCGame.textFactory.getMaxTextSize(flopLabel),textSize);
		textSize=Math.min(DPCGame.textFactory.getMaxTextSize(turnLabel),textSize);
		textSize=Math.min(DPCGame.textFactory.getMaxTextSize(riverLabel),textSize);
		textSize=Math.min(DPCGame.textFactory.getMaxTextSize(selectWinnerLabel),textSize);
		textSize=Math.min(DPCGame.textFactory.getMaxTextSize(selectWinnersSplitLabel),textSize);
		selectingDealerLabel.setTextSize(textSize);
		winLabel.setTextSize(textSize);
		blindsInLabel.setTextSize(textSize);
		flopLabel.setTextSize(textSize);
		turnLabel.setTextSize(textSize);
		riverLabel.setTextSize(textSize);
		selectWinnerLabel.setTextSize(textSize);
		selectWinnersSplitLabel.setTextSize(textSize);
		winLabel.setText("");
		ipAddress.setMaxDimensions((int)(screenHeight*0.15f),(int)(screenHeight*0.05f));
		String tmp=ipAddress.getText();
		ipAddress.setText(MEASURE_IP);
		ipAddress.setTextSizeToMax();
		ipAddress.setText(tmp);
		azimuthLabel.setMaxDimensions((int)(screenHeight*0.1f),(int)(screenHeight*0.05f));
		azimuthLabel.setTextSizeToMax();
	}
	
	private void setPositions(float screenWidth,float screenHeight) {
		homeUIAnimation.setPositions(screenWidth,screenHeight);
		wifiButton.setPosition(screenWidth*0.5f,screenHeight*0.85f);
		foldButton.setPosition(screenWidth*0.1f,screenHeight*0.76f);
		posPlayerPromptOffscreen.set(screenWidth*0.8f,screenHeight+playerPrompt.maxRadiusY*1.2f);
		posPlayerPromptOnscreen.set(screenWidth*0.8f,screenHeight-playerPrompt.maxRadiusY*1.1f);
		stateChangePrompt.setPosition(screenWidth*0.5f,screenHeight*0.7f);
		enterName1Label.setPosition(screenWidth*0.16f,screenHeight*0.72f);
		enterName2Label.setPosition(screenWidth*0.16f,screenHeight*0.64f);
		enterNameDoneButton.setPosition(screenWidth*0.76f,screenHeight*0.68f);
		enterTableName1Label.setPosition(screenWidth*0.14f,screenHeight*0.77f);
		enterTableName2Label.setPosition(screenWidth*0.14f,screenHeight*0.69f);
		enterTableNameDoneButton.setPosition(screenWidth*0.8f,screenHeight*0.73f);
		searchingLabel.setPosition(screenWidth*0.5f,screenHeight*0.7f);
		wifiLabel.setPosition(screenWidth*0.5f,screenHeight*0.7f);
		reconnect1Label.setPosition(screenWidth*0.5f,screenHeight*0.7f+reconnect1Label.maxRadiusY);
		reconnect2Label.setPosition(screenWidth*0.5f,screenHeight*0.7f);
		waitNextHandLabel.setPosition(screenWidth*0.5f,screenHeight*0.7f);
		setValuesLabel.setPosition(screenWidth*0.16f,screenHeight*0.34f);
		setValuesOkButton.setPosition(screenWidth*0.76f,screenHeight*0.34f);
		float xValueSpacing_=screenWidth*0.125f;
		float xValueStart_=screenWidth*0.5f-xValueSpacing_;
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			valueDownArrows[i].setPosition(xValueStart_+i*xValueSpacing_,screenHeight*0.2f);
			valueUpArrows[i].setPosition(xValueStart_+i*xValueSpacing_,screenHeight*0.48f);
		}
		
		playerLoginDialog.setPosition(screenWidth*0.5f,screenHeight*0.5f);
		playerLoginDialogSmall.setPosition(screenWidth*0.5f,screenHeight*0.95f);
		
		float padding=screenHeight*0.03f;
		playerIDDialog.setPosition(0+playerIDDialog.radiusX+padding,0+playerIDDialog.radiusY+padding);
		playerIDDialogSmall.setPosition(playerIDDialog.x,0-playerIDDialog.radiusY*1.1f);
		
		divisibilityDialog.setPosition(screenWidth*0.5f,screenHeight*0.08f);
		buyinDialog.setPosition(screenWidth*0.5f,screenHeight*0.5f);
		buyinDialogSmall.setPosition(screenWidth*0.5f,screenHeight*0.95f);
		manualConnectDialog.setPosition(screenWidth*0.5f,screenHeight*0.5f);
		leaveTableDialog.setPosition(screenWidth*0.5f,screenHeight*0.5f);
		leaveTableDialogSmall.setPosition(screenWidth*0.5f,screenHeight*0.95f);
		destroyTableDialog.setPosition(screenWidth*0.5f,screenHeight*0.5f);
		destroyTableDialogSmall.setPosition(screenWidth*0.5f,screenHeight*0.95f);
		autosaveDialog.setPosition(screenWidth*0.5f,screenHeight*0.5f);
		autosaveDialogSmall.setPosition(screenWidth*0.5f,screenHeight*0.95f);
		loadDialog.setPosition(screenWidth*0.5f,screenHeight*0.5f);
		tableStatusMenu.setPosition(screenWidth-tableStatusMenu.radiusX,screenHeight*0.445f);
		searchingAnimation.setPosition(screenWidth*0.5f,screenHeight*0.85f);
		tutorialArrangement.setPositions(screenWidth,screenHeight);
		float xHostPrompts=screenWidth*0.5f;
		float yHostPrompts=screenHeight*0.65f;
		selectingDealerLabel.setPosition(xHostPrompts,yHostPrompts);
		winLabel.setPosition(xHostPrompts,yHostPrompts);
		yHostPrompts=screenHeight*0.3f;
		blindsInLabel.setPosition(xHostPrompts,yHostPrompts);
		flopLabel.setPosition(xHostPrompts,yHostPrompts);
		turnLabel.setPosition(xHostPrompts,yHostPrompts);
		riverLabel.setPosition(xHostPrompts,yHostPrompts);
		selectWinnerLabel.setPosition(xHostPrompts,yHostPrompts);
		selectWinnersSplitLabel.setPosition(xHostPrompts,yHostPrompts);
		gotoGameButton.setPosition(screenWidth*0.9f,screenHeight*0.1f);
		potArrowLeft.setPosition(screenWidth*0.35f,screenHeight*0.5f);
		potArrowRight.setPosition(screenWidth*0.65f,screenHeight*0.5f);
		splitButton.setPosition(screenWidth*0.75f,screenHeight*0.5f);
		splitDoneButton.setPosition(screenWidth*0.75f,screenHeight*0.5f);
		splitCancelButton.setPosition(screenWidth*0.25f,screenHeight*0.5f);
		ipAddress.setPosition(screenHeight*0.15f,screenHeight*0.95f);
		azimuthLabel.setPosition(azimuthLabel.maxRadiusX,azimuthLabel.maxRadiusY);
	}
	
	private void scalePositions(float scaleX,float scaleY) {
		wifiButton.scalePosition(scaleX,scaleY);
		enterName1Label.scalePosition(scaleX,scaleY);
		enterName2Label.scalePosition(scaleX,scaleY);
		enterNameDoneButton.scalePosition(scaleX,scaleY);
		enterTableName1Label.scalePosition(scaleX,scaleY);
		enterTableName2Label.scalePosition(scaleX,scaleY);
		enterTableNameDoneButton.scalePosition(scaleX,scaleY);
		searchingLabel.scalePosition(scaleX,scaleY);
		wifiLabel.scalePosition(scaleX,scaleY);
		reconnect1Label.scalePosition(scaleX,scaleY);
		reconnect2Label.scalePosition(scaleX,scaleY);
		waitNextHandLabel.scalePosition(scaleX,scaleY);
		setValuesLabel.scalePosition(scaleX,scaleY);
		setValuesOkButton.scalePosition(scaleX,scaleY);
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			valueDownArrows[i].scalePosition(scaleX,scaleY);
			valueUpArrows[i].scalePosition(scaleX,scaleY);
		}
		divisibilityDialog.scalePosition(scaleX,scaleY);
		dialogWindow.scalePosition(scaleX,scaleY);
		dialogWArrowWindow.scalePosition(scaleX,scaleY);
		buyinDialog.scalePosition(scaleX,scaleY);
		manualConnectDialog.scalePosition(scaleX,scaleY);
		leaveTableDialog.scalePosition(scaleX,scaleY);
		destroyTableDialog.scalePosition(scaleX,scaleY);
		bootDialog.scalePosition(scaleX,scaleY);
		tableStatusMenu.scalePosition(scaleX,scaleY);
		searchingAnimation.scalePosition(scaleX, scaleY);
		gotoGameButton.scalePosition(scaleX,scaleY);
		tutorialArrangement.scalePositions(scaleX,scaleY);
		selectingDealerLabel.scalePosition(scaleX,scaleY);
		winLabel.scalePosition(scaleX,scaleY);
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
		tutorialArrangement.animate(delta);
		foregroundRenderer.render();
	}
	
	//////////////////// Controllers ////////////////////
	public void controlLogic() {
		if (openHelpOnStartup) {
			openHelpOnStartup=false;
			openHelp();
			stopHome();
		}
	}
	
	public void animate(float delta) {
		homeUIAnimation.animate(delta);
		
		selectingDealerLabel.animate(delta);
		winLabel.animate(delta);
		blindsInLabel.animate(delta);
		flopLabel.animate(delta);
		riverLabel.animate(delta);
		turnLabel.animate(delta);
		selectWinnerLabel.animate(delta);
		selectWinnersSplitLabel.animate(delta);
		
		wifiButton.animate(delta);
		ipAddress.animate(delta);
		
		if (showPlayerPrompt) {
			if (Math.abs(playerPrompt.y-posPlayerPromptOnscreen.y)>1) {
				float deltaY_=9*delta*(posPlayerPromptOnscreen.y-playerPrompt.y);
				playerPrompt.y+=deltaY_;
			}
		} else {
			if (Math.abs(playerPrompt.y-posPlayerPromptOffscreen.y)>1) {
				float deltaY_=9*delta*(posPlayerPromptOffscreen.y-playerPrompt.y);
				playerPrompt.y+=deltaY_;
			} else {
				playerPrompt.opacity=0;
			}
		}
		
		stateChangePrompt.animate(delta);
		
		enterName1Label.animate(delta);
		enterName2Label.animate(delta);
		enterNameDoneButton.animate(delta);
		
		enterTableName1Label.animate(delta);
		enterTableName2Label.animate(delta);
		enterTableNameDoneButton.animate(delta);
		
		searchingLabel.animate(delta);
		searchingAnimation.animate(delta);
		if (searchingLabel.getIsFlashing()&&searchingLabel.opacity==0) {
			searchingAnimation.ping();
		}
		wifiLabel.animate(delta);
		reconnect1Label.animate(delta);
		reconnect2Label.animate(delta);
		waitNextHandLabel.animate(delta);
		
		setValuesLabel.animate(delta);
		setValuesOkButton.animate(delta);
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			valueDownArrows[i].animate(delta);
			valueUpArrows[i].animate(delta);
		}
		
		dialogWindow.animate(delta);
		playerIDWindow.animate(delta);
		dialogWArrowWindow.animate(delta);
		playerLoginDialog.animate(delta);
		playerIDDialog.animate(delta);
		buyinDialog.animate(delta);
		manualConnectDialog.animate(delta);
		leaveTableDialog.animate(delta);
		destroyTableDialog.animate(delta);
		bootDialog.animate(delta);
		autosaveDialog.animate(delta);
		loadDialog.animate(delta);
		tableStatusMenu.animate(delta);
		divisibilityDialog.animate(delta);
		if (!tutorialArrangement.isRunning()) {
			gotoGameButton.animate(delta);
		}
		potArrowLeft.animate(delta);
		potArrowRight.animate(delta);
		
		splitButton.animate(delta);
		splitCancelButton.animate(delta);
		splitDoneButton.animate(delta);
		
		foldButton.animate(delta);
		
		secondTimer+=delta*1000;
		if (secondTimer>=300) {
			secondTimer=0;
			azimuthLabel.setText(Integer.toString(game.calculateAzimuth()));
			azimuthLabel.loadTexture();
		}
	}

	//////////////////// Instructions from World ////////////////////
	public void startHome() {
		Logger.log(LOG_TAG,"startHome()");
		Gdx.app.log("HomeUIAnimation", "startHome");
		input.pushTouchFocus(ForegroundInput.TOUCH_HOME);
		homeUIAnimation.begin(0);
	}
	
	public void stopHome() {
		Logger.log(LOG_TAG,"stopHome()");
		Gdx.app.log("HomeUIAnimation", "stopHome");
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
			startHome();
		}
		closeLoadDialog();
	}
	
	public void howSelected() {
		stopHome();
		openHelp();
	}
	
	public void loadSelected() {
		stopHome();
		openLoadDialog();
	}
	
	//////////////////// Instructions from Player ////////////////////
	public void startWifiPrompt() {
		Logger.log(LOG_TAG,"startWifiPrompt()");
		wifiButton.startFlashing();
		wifiButton.setTouchable(true);
		wifiLabel.startFlashing();
		input.pushTouchFocus(ForegroundInput.TOUCH_NO_WIFI);
	}
	
	public void stopWifiPrompt() {
		Logger.log(LOG_TAG,"stopWifiPrompt()");
		wifiButton.fadeOut();
		wifiButton.setTouchable(false);
		wifiLabel.fadeOut();
		input.popTouchFocus(ForegroundInput.TOUCH_NO_WIFI);
	}
	
	public void startSearchForGames() {
		Logger.log(LOG_TAG,"startSearchForGames()");
		searchingLabel.startFlashing();
		searchingAnimation.ping();
		searchingAnimation.setTouchable(true);
	}
	
	public void stopSearchForGames() {
		Logger.log(LOG_TAG,"stopSearchForGames()");
		searchingAnimation.stop();
		searchingLabel.stopFlashing();
		searchingLabel.opacity=0;
		searchingAnimation.setTouchable(false);
	}
	
	public void startBuyin(String tableName) {
		Logger.log(LOG_TAG,"stopSearchForGames()");
		dialogWindow.setOpacity(1);
		dialogWindow.setPosition(buyinDialogSmall.x,buyinDialogSmall.y);
		dialogWindow.setDimensions(buyinDialogSmall.radiusX,buyinDialogSmall.radiusY);
		dialogWindow.sendTo(buyinDialog);
		buyinDialog.disappear();
		buyinDialog.setTableName(tableName);
		input.pushTouchFocus(ForegroundInput.TOUCH_BUYIN);
	}
	
	public void stopBuyin() {
		Logger.log(LOG_TAG,"stopBuyin()");
		dialogWindow.remove();
		buyinDialog.stop();
		input.popTouchFocus(ForegroundInput.TOUCH_BUYIN);
	}
	
	public void startManualConnectDialog() {
		Logger.log(LOG_TAG,"startManualConnectDialog()");
		dialogWindow.setOpacity(1);
		dialogWindow.setPosition(buyinDialogSmall.x,buyinDialogSmall.y);
		dialogWindow.setDimensions(buyinDialogSmall.radiusX,buyinDialogSmall.radiusY);
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
	
	public void startLeaveTableDialog(String tableName) {
		Logger.log(LOG_TAG,"startLeaveTableDialog("+tableName+")");
		dialogWindow.setOpacity(1);
		dialogWindow.setPosition(leaveTableDialogSmall.x,leaveTableDialogSmall.y);
		dialogWindow.setDimensions(leaveTableDialogSmall.radiusX,leaveTableDialogSmall.radiusY);
		dialogWindow.sendTo(leaveTableDialog);
		leaveTableDialog.disappear();
		leaveTableDialog.setTableName(tableName);
		input.pushTouchFocus(ForegroundInput.TOUCH_LEAVE_TABLE);
	}
	
	public void stopLeaveTableDialog() {
		Logger.log(LOG_TAG,"stopLeaveTableDialog()");
		dialogWindow.remove();
		leaveTableDialog.stop();
		input.popTouchFocus(ForegroundInput.TOUCH_LEAVE_TABLE);
	}
	
	public void startPlayerLoginDialog() {
		Logger.log(LOG_TAG,"startPlayerLoginDialog()");
		dialogWindow.setOpacity(1);
		dialogWindow.setPosition(playerLoginDialogSmall.x,playerLoginDialogSmall.y);
		dialogWindow.setDimensions(playerLoginDialogSmall.radiusX,playerLoginDialogSmall.radiusY);
		dialogWindow.sendTo(playerLoginDialog);
		playerLoginDialog.disappear();
		input.pushTouchFocus(ForegroundInput.TOUCH_PLAYER_LOGIN);
	}
	
	public void stopPlayerLoginDialog() {
		Logger.log(LOG_TAG,"stopPlayerLoginDialog()");
		dialogWindow.remove();
		playerLoginDialog.stop();
		input.popTouchFocus(ForegroundInput.TOUCH_PLAYER_LOGIN);
	}
	
	public void startPlayerIDDialog(String playerName) {
		Logger.log(LOG_TAG,"startPlayerIDDialog()");
		playerIDWindow.setOpacity(1);
		playerIDWindow.setPosition(playerIDDialogSmall.x,playerIDDialogSmall.y);
		playerIDWindow.setDimensions(playerIDDialog.radiusX,playerIDDialog.radiusY);
		playerIDWindow.sendTo(playerIDDialog);
		playerIDDialog.disappear();
		playerIDDialog.setPlayerName(playerName);
	}
	
	public void stopPlayerIDDialog() {
		Logger.log(LOG_TAG,"stopPlayerIDDialog()");
		playerIDWindow.remove();
		playerIDDialog.stop();
	}
	
	public void startDestroyTableDialog(String tableName) {
		Logger.log(LOG_TAG,"startDestroyTableDialog("+tableName+")");
		dialogWindow.setOpacity(1);
		dialogWindow.setPosition(destroyTableDialogSmall.x,destroyTableDialogSmall.y);
		dialogWindow.setDimensions(destroyTableDialogSmall.radiusX,destroyTableDialogSmall.radiusY);
		dialogWindow.sendTo(destroyTableDialog);
		destroyTableDialog.disappear();
		destroyTableDialog.setTableName(tableName);
		input.pushTouchFocus(ForegroundInput.TOUCH_DESTROY_TABLE);
	}
	
	public void stopDestroyTableDialog() {
		Logger.log(LOG_TAG,"stopDestroyTableDialog()");
		dialogWindow.remove();
		destroyTableDialog.stop();
		input.popTouchFocus(ForegroundInput.TOUCH_DESTROY_TABLE);
	}
	
	public void startAutosaveDialog(int defaultSlot,String[] tableNames) {
		Logger.log(LOG_TAG,"startAutosaveDialog()");
		dialogWindow.setOpacity(1);
		dialogWindow.setPosition(autosaveDialogSmall.x,autosaveDialogSmall.y);
		dialogWindow.setDimensions(autosaveDialogSmall.radiusX,autosaveDialogSmall.radiusY);
		dialogWindow.sendTo(autosaveDialog);
		autosaveDialog.disappear();
		autosaveDialog.slotSelected(defaultSlot);
		autosaveDialog.populateSlots(tableNames);
		input.pushTouchFocus(ForegroundInput.TOUCH_AUTOSAVE_DIALOG);
	}
	
	public void stopAutosaveDialog() {
		Logger.log(LOG_TAG,"stopAutosaveDialog()");
		dialogWindow.remove();
		autosaveDialog.stop();
		input.popTouchFocus(ForegroundInput.TOUCH_AUTOSAVE_DIALOG);
	}
	
	public void notifyAtPlayerPosition() {
		Logger.log(LOG_TAG,"notifyAtPlayerPosition()");
		input.pushTouchFocus(ForegroundInput.TOUCH_PLAYER);
	}
	
	public void notifyLeftPlayerPosition() {
		Logger.log(LOG_TAG,"notifyLeftPlayerPosition()");
		input.popTouchFocus(ForegroundInput.TOUCH_PLAYER);
	}
	
	public void showTableStatusMenu(String tableName) {
		Logger.log(LOG_TAG,"showTableStatusMenu()");
		tableStatusMenu.setTableName(tableName);
		tableStatusMenu.show();
	}
	
	public void showTextMessage(String textMessage) {
		Logger.log(LOG_TAG,"showTextMessage("+textMessage+")");
		playerPrompt.setPosition(posPlayerPromptOffscreen);
		playerPrompt.setText(textMessage);
		playerPrompt.loadTexture();
		playerPrompt.x=screenWidth-playerPrompt.radiusX*1.1f;
		playerPrompt.opacity=1;
		showPlayerPrompt=true;
	}
	
	public void hideTextMessage() {
		Logger.log(LOG_TAG,"hideTextMessage()");
		showPlayerPrompt=false;
	}

	public void promptStateChange(String messageStateChange) {
		Logger.log(LOG_TAG,"promptStateChange("+messageStateChange+")");
		stateChangePrompt.setText(messageStateChange);
		stateChangePrompt.loadTexture();
		stateChangePrompt.fadeIn();
		input.pushTouchFocus(ForegroundInput.TOUCH_PLAYER_STATE_CHANGE);
	}

	public void stateChangeACKed() {
		Logger.log(LOG_TAG,"stateChangeACKed()");
		input.popTouchFocus(ForegroundInput.TOUCH_PLAYER_STATE_CHANGE);
		stateChangePrompt.setText("");
		stateChangePrompt.fadeOut();
	}
	
	public void pauseStateChangePrompt() {
		input.popTouchFocus(ForegroundInput.TOUCH_PLAYER_STATE_CHANGE);
		stateChangePrompt.fadeOut();
	}
	
	public void resumeStateChangePrompt() {
		stateChangePrompt.fadeIn();
		input.pushTouchFocus(ForegroundInput.TOUCH_PLAYER_STATE_CHANGE);
	}
	
	public void startReconnect() {
		Logger.log(LOG_TAG,"startReconnect()");
		reconnect1Label.fadeIn();
		reconnect2Label.startFlashing();
	}
	
	public void stopReconnect() {
		Logger.log(LOG_TAG,"stopReconnect()");
		reconnect1Label.fadeOut();
		reconnect2Label.fadeOut();
	}
	
	public void startWaitNextHand() {
		Logger.log(LOG_TAG,"startWaitNextHand()");
		waitNextHandLabel.startFlashing();
	}
	
	public void stopWaitNextHand() {
		Logger.log(LOG_TAG,"stopWaitNextHand()");
		waitNextHandLabel.fadeOut();
	}
	
	//////////////////// Instructions from Table ////////////////////
	public void startEnterTableName() {
		Logger.log(LOG_TAG,"startEnterTableName()");
		enterTableName1Label.fadeIn();
		enterTableName2Label.fadeIn();
		enterTableNameDoneButton.fadeIn();
		enterTableNameDoneButton.setTouchable(true);
		input.pushTouchFocus(ForegroundInput.TOUCH_TABLES_NAME);
	}
	
	public void stopEnterTableName() {
		Logger.log(LOG_TAG,"stopEnterTableName()");
		enterTableName1Label.fadeOut();
		enterTableName2Label.fadeOut();
		enterTableNameDoneButton.fadeOut();
		enterTableNameDoneButton.setTouchable(false);
		input.popTouchFocus(ForegroundInput.TOUCH_TABLES_NAME);
	}
	
	public void startSetValues() {
		Logger.log(LOG_TAG,"startSetValues()");
		setValuesLabel.fadeIn();
		setValuesOkButton.fadeIn();
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			valueUpArrows[i].fadeIn();
			valueDownArrows[i].fadeIn();
		}
		input.pushTouchFocus(ForegroundInput.TOUCH_CHIP_VALUES);
	}

	public void stopSetValues() {
		Logger.log(LOG_TAG,"stopSetValues()");
		setValuesLabel.fadeOut();
		setValuesOkButton.fadeOut();
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			valueUpArrows[i].fadeOut();
			valueDownArrows[i].fadeOut();
		}
		input.popTouchFocus(ForegroundInput.TOUCH_CHIP_VALUES);
	}
	
	public void startLobby() {
		Logger.log(LOG_TAG,"startLobby()");
		showIpAddress();
		if (game.mWL.table.countPlayers()>=2) {
			gotoGameButton.fadeIn();
			gotoGameButton.setTouchable(true);
		}
		input.pushTouchFocus(ForegroundInput.TOUCH_LOBBY);
	}
	
	public void stopLobby() {
		Logger.log(LOG_TAG,"stopLobby()");
		hideIpAddress();
		gotoGameButton.fadeOut();
		gotoGameButton.setTouchable(false);
		input.popTouchFocus(ForegroundInput.TOUCH_LOBBY);
	}
	
	public void startLobbyLoaded() {
		Logger.log(LOG_TAG,"startLobbyLoaded()");
		input.pushTouchFocus(ForegroundInput.TOUCH_LOBBY_LOADED);
	}
	
	public void notifyGameStarting() {
		Logger.log(LOG_TAG,"notifyGameStarting()");
		input.pushTouchFocus(ForegroundInput.TOUCH_TABLE_GAMEPLAY);
	}
	
	public void stopGame() {
		Logger.log(LOG_TAG,"stopGame()");
		input.popTouchFocus(ForegroundInput.TOUCH_TABLE_GAMEPLAY);
	}
	
	public void startTutorialArrangement(Player player) {
		Logger.log(LOG_TAG,"startTutorialArrangement()");
		input.pushTouchFocus(ForegroundInput.TOUCH_TUTORIAL_ARRANGEMENT);
		tutorialArrangement.start(player,this);
	}
	
	public void stopTutorialArrangement() {
		Logger.log(LOG_TAG,"stopTutorialArrangement()");
		tutorialArrangement.stop();
		input.popTouchFocus(ForegroundInput.TOUCH_TUTORIAL_ARRANGEMENT);
	}
	
	public void notifyGameCanStart(boolean fadeButton) {
		Logger.log(LOG_TAG,"notifyGameCanStart()");
		gotoGameButton.fadeIn();
		gotoGameButton.setTouchable(true);
		if (!fadeButton) {
			gotoGameButton.setOpacity(1);
		}
	}
	
	public void notifyGameCantStart() {
		Logger.log(LOG_TAG,"notifyGameCantStart()");
		gotoGameButton.fadeOut();
		gotoGameButton.setTouchable(false);
	}
	
	public void startDealerSelect() {
		Logger.log(LOG_TAG,"startDealerSelect()");
		selectingDealerLabel.startFlashing();
	}
	
	public void stopDealerSelect() {
		Logger.log(LOG_TAG,"stopDealerSelect()");
		selectingDealerLabel.fadeOut();
		winLabel.fadeOut();
	}
	
	public void showWinLabel(TextLabel name) {
		Logger.log(LOG_TAG,"showWinLabel("+name.getText()+")");
		winLabel.setText(name.getText()+WIN_TEXT);
		winLabel.loadTexture();
		winLabel.fadeIn();
	}

	public void startSelectWinner() {
		Logger.log(LOG_TAG,"startSelectWinner()");
		selectWinnerLabel.startFlashing();
		splitButton.fadeIn();
		splitButton.setTouchable(true);
	}
	
	public void stopSelectWinner() {
		Logger.log(LOG_TAG,"stopSelectWinner()");
		selectWinnerLabel.fadeOut();
		splitButton.fadeOut();
		splitButton.setTouchable(false);
	}
	
	public void startSelectWinnersSplit() {
		Logger.log(LOG_TAG,"startSelectWinnersSplit()");
		selectWinnersSplitLabel.startFlashing();
		splitCancelButton.fadeIn();
		splitCancelButton.setTouchable(true);
	}
	
	public void stopSelectWinnersSplit() {
		Logger.log(LOG_TAG,"stopSelectWinnersSplit()");
		selectWinnersSplitLabel.fadeOut();
		splitCancelButton.fadeOut();
		splitCancelButton.setTouchable(false);
		splitDoneButton.fadeOut();
		splitDoneButton.setTouchable(false);
	} 

	public void startBootDialog(float xSeat,float ySeat,float rotation) {
		Logger.log(LOG_TAG,"startBootDialog()");
		dialogWArrowWindow.setOpacity(1);
		bootDialogSmall.setPosition(xSeat,ySeat);
		dialogWArrowWindow.setPosition(bootDialogSmall.x,bootDialogSmall.y);
		dialogWArrowWindow.setDimensions(bootDialogSmall.radiusX,bootDialogSmall.radiusY);
		dialogWArrowWindow.rotation=rotation;
		bootDialog.setPosition((float)(xSeat-Math.sin(Math.toRadians(rotation))*bootDialog.radiusY*1),
				(float)(ySeat+Math.cos(Math.toRadians(rotation))*bootDialog.radiusY*1),rotation);
		dialogWArrowWindow.sendTo(bootDialog);
		bootDialog.disappear();
		input.pushTouchFocus(ForegroundInput.TOUCH_BOOT_DIALOG);
	}
	
	public void stopBootDialog() {
		Logger.log(LOG_TAG,"stopBootDialog()");
		dialogWArrowWindow.remove();
		bootDialog.stop();
		input.popTouchFocus(ForegroundInput.TOUCH_BOOT_DIALOG);
	}
	
	public void setIpAddress(String ipAddressStr) {
		Logger.log(LOG_TAG,"setIpAddress("+ipAddressStr+")");
		ipAddress.setText(ipAddressStr);
	}
	
	public void showIpAddress() {
		Logger.log(LOG_TAG,"showIpAddress()");
		ipAddress.loadTexture();
		ipAddress.fadeIn();
	}
	
	public void hideIpAddress() {
		Logger.log(LOG_TAG,"hideIpAddress()");
		ipAddress.fadeOut();
	}
	
}
