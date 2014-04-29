package com.bidjee.digitalpokerchips.c;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.m.Button;
import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.Dialog;
import com.bidjee.digitalpokerchips.m.TextLabel;

public class HelpDialog extends Dialog {
	
	// State Variables //
	int currentSlide;
	private int numSlidesCompleted;
	
	// Global Buttons //
	public Button closeButton;
	public Button nextButton;
	public Button previousButton;
	public Button doneButton;
	// Global Objects //
	public DPCSprite tableCentre=new DPCSprite();
	public float zoomTableCentre;
	public DPCSprite player1=new DPCSprite();	
	public DPCSprite player2=new DPCSprite();
	public DPCSprite player3=new DPCSprite();
	public DPCSprite player4=new DPCSprite();
	public float zoomPlayer;
	public DPCSprite chip1=new DPCSprite();
	public DPCSprite chip2=new DPCSprite();
	public DPCSprite chip3=new DPCSprite();
	public DPCSprite chip4=new DPCSprite();
	// Slides //
	public OverviewSlide overviewSlide;
	public TableSetupSlide tableSetupSlide;
	public PlayerSetupSlide playerSetupSlide;
	public EnjoySlide enjoySlide;
	private DPCSprite[] slides=new DPCSprite[4];
	// References //
	ForegroundLayer mFL;
	
	public HelpDialog(ForegroundLayer mFL_) {
		mFL=mFL_;
		setFirstOpenMode(false);
		closeButton=new Button(false,0,"");
		nextButton=new Button(false,0,"");
		previousButton=new Button(false,0,"");
		doneButton=new Button(false,0,"");
		
		overviewSlide=new OverviewSlide();
		tableSetupSlide=new TableSetupSlide();
		playerSetupSlide=new PlayerSetupSlide();
		enjoySlide=new EnjoySlide();
		
		slides[0]=overviewSlide;
		slides[1]=tableSetupSlide;
		slides[2]=playerSetupSlide;
		slides[3]=enjoySlide;
		
		tableCentre.opacity=0;
		player1.opacity=0;
		player2.opacity=0;
		player3.opacity=0;
		player4.opacity=0;
		chip1.opacity=0;
		chip2.opacity=0;
		chip3.opacity=0;
		chip4.opacity=0;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		closeButton.setDimensions((int)(radiusY*0.12f),(int)(radiusY*0.12f));
		nextButton.setDimensions((int)(radiusY*0.15f),(int)(radiusY*0.15f));
		previousButton.setDimensions((int)(radiusY*0.15f),(int)(radiusY*0.15f));
		doneButton.setDimensions((int)(radiusY*0.15f),(int)(radiusY*0.15f));
		
		int radiusXTitle=(int)(radiusX*0.8f);
		int radiusYTitle=(int)(radiusY*0.15f);
		overviewSlide.titleLabel.setMaxDimensions(radiusXTitle,radiusYTitle);
		tableSetupSlide.titleLabel.setMaxDimensions(radiusXTitle,radiusYTitle);
		playerSetupSlide.titleLabel.setMaxDimensions(radiusXTitle,radiusYTitle);
		enjoySlide.titleLabel.setMaxDimensions(radiusXTitle,radiusYTitle);
		int textSize=Math.min(DPCGame.textFactory.getMaxTextSize(overviewSlide.titleLabel),
				DPCGame.textFactory.getMaxTextSize(tableSetupSlide.titleLabel));
		textSize=Math.min(textSize,DPCGame.textFactory.getMaxTextSize(playerSetupSlide.titleLabel));
		textSize=Math.min(textSize,DPCGame.textFactory.getMaxTextSize(enjoySlide.titleLabel));
		overviewSlide.titleLabel.setTextSize(textSize);
		tableSetupSlide.titleLabel.setTextSize(textSize);
		playerSetupSlide.titleLabel.setTextSize(textSize);
		enjoySlide.titleLabel.setTextSize(textSize);
		
		int radiusXText=(int)(radiusX*0.8f);
		int radiusYText=(int)(radiusY*0.1f);
		overviewSlide.text1Label.setMaxDimensions(radiusXText,radiusYText);
		tableSetupSlide.text1Label.setMaxDimensions(radiusXText,radiusYText);
		tableSetupSlide.text2Label.setMaxDimensions(radiusXText,radiusYText);
		playerSetupSlide.text1Label.setMaxDimensions(radiusXText,radiusYText);
		playerSetupSlide.text2Label.setMaxDimensions(radiusXText,radiusYText);
		textSize=Math.min(DPCGame.textFactory.getMaxTextSize(overviewSlide.text1Label),
				DPCGame.textFactory.getMaxTextSize(tableSetupSlide.text1Label));
		textSize=Math.min(textSize,DPCGame.textFactory.getMaxTextSize(tableSetupSlide.text2Label));
		textSize=Math.min(textSize,DPCGame.textFactory.getMaxTextSize(playerSetupSlide.text1Label));
		textSize=Math.min(textSize,DPCGame.textFactory.getMaxTextSize(playerSetupSlide.text2Label));
		overviewSlide.text1Label.setTextSize(textSize);
		overviewSlide.tableCentreLabel.setTextSize(textSize);
		overviewSlide.playerLabel.setTextSize(textSize);
		tableSetupSlide.text1Label.setTextSize(textSize);
		tableSetupSlide.text2Label.setTextSize(textSize);
		playerSetupSlide.text1Label.setTextSize(textSize);
		playerSetupSlide.text2Label.setTextSize(textSize);
		
		tableCentre.setDimensions((int)(radiusY*0.48f),(int)(radiusY*0.3f));
		player1.setDimensions((int)(radiusY*0.24f),(int)(radiusY*0.15f));
		player2.setDimensions((int)(radiusY*0.24f),(int)(radiusY*0.15f));
		player3.setDimensions((int)(radiusY*0.24f),(int)(radiusY*0.15f));
		player4.setDimensions((int)(radiusY*0.24f),(int)(radiusY*0.15f));
		chip1.setDimensions((int)(radiusY*0.04f),(int)(radiusY*0.04f));
		chip2.setDimensions(chip1.radiusX,chip1.radiusY);
		chip3.setDimensions(chip1.radiusX,chip1.radiusY);
		chip4.setDimensions(chip1.radiusX,chip1.radiusY);
		
		overviewSlide.setDimensions(radiusX, radiusY);
		tableSetupSlide.setDimensions(radiusX, radiusY);
		playerSetupSlide.setDimensions(radiusX, radiusY);
		enjoySlide.setDimensions(radiusX, radiusY);
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x,y);
		closeButton.setPosition(x+radiusX-closeButton.radiusX*1.5f,y+radiusY-closeButton.radiusY*1.5f);
		nextButton.setPosition(x+radiusX*0.9f,y-radiusY*0.15f);
		previousButton.setPosition(x-radiusX*0.9f,y-radiusY*0.15f);
		doneButton.setPosition(x+radiusX*0.85f,y-radiusY*0.8f);
		
		float xTitle=x;
		float yTitle=y+radiusY*0.85f;
		overviewSlide.titleLabel.setPosition(xTitle,yTitle);
		playerSetupSlide.titleLabel.setPosition(xTitle,yTitle);
		tableSetupSlide.titleLabel.setPosition(xTitle,yTitle);
		enjoySlide.titleLabel.setPosition(xTitle,yTitle);
		
		overviewSlide.setPosition(x,y);
		playerSetupSlide.setPosition(x,y);
		tableSetupSlide.setPosition(x,y);
		enjoySlide.setPosition(x,y);
	}
	
	@Override
	public void scalePosition(float scaleX,float scaleY) {
		super.scalePosition(scaleX,scaleY);
		closeButton.scalePosition(scaleX,scaleY);
		nextButton.scalePosition(scaleX,scaleY);
		previousButton.scalePosition(scaleX,scaleY);
		doneButton.scalePosition(scaleX,scaleY);
		overviewSlide.scalePosition(scaleX, scaleY);
		tableSetupSlide.scalePosition(scaleX, scaleY);
		playerSetupSlide.scalePosition(scaleX, scaleY);
		enjoySlide.scalePosition(scaleX, scaleY);
		tableCentre.scalePosition(scaleX,scaleY);
		player1.scalePosition(scaleX,scaleY);
		player2.scalePosition(scaleX,scaleY);
		player3.scalePosition(scaleX,scaleY);
		player4.scalePosition(scaleX,scaleY);
		chip1.scalePosition(scaleX,scaleY);
		chip2.scalePosition(scaleX,scaleY);
		chip3.scalePosition(scaleX,scaleY);
		chip4.scalePosition(scaleX,scaleY);
	}
	
	@Override
	public void start() {
		opacity=1;
		mFL.input.pushTouchFocus(ForegroundInput.TOUCH_HELP_DIALOG);
		currentSlide=0;
		if (numSlidesCompleted>0) {
			nextButton.fadeIn();
			nextButton.setTouchable(true);
		}
		if (numSlidesCompleted>=slides.length-1) {
			closeButton.opacity=1;
			closeButton.setTouchable(true);
		}
		slides[currentSlide].init();
	}
	
	@Override
	public void stop() {
		opacity=0;
		mFL.input.popTouchFocus(ForegroundInput.TOUCH_HELP_DIALOG);
		closeButton.opacity=0;
		closeButton.setTouchable(false);
		nextButton.fadeOut();
		nextButton.opacity=0;
		nextButton.setTouchable(false);
		previousButton.fadeOut();
		previousButton.opacity=0;
		previousButton.setTouchable(false);
		doneButton.fadeOut();
		doneButton.opacity=0;
		doneButton.setTouchable(false);
		slides[currentSlide].clear();
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		closeButton.animate(delta);
		nextButton.animate(delta);
		previousButton.animate(delta);
		doneButton.animate(delta);
		slides[currentSlide].animate(delta);
	}

	public void next() {
		slides[currentSlide].clear();
		currentSlide++;
		slides[currentSlide].init();
		previousButton.fadeIn();
		previousButton.setTouchable(true);
		if (currentSlide>=numSlidesCompleted) {
			nextButton.fadeOut();
			nextButton.opacity=0;
			nextButton.setTouchable(false);
		}
		if (currentSlide==slides.length-1) {
			nextButton.fadeOut();
			nextButton.opacity=0;
			nextButton.setTouchable(false);
			doneButton.fadeIn();
			doneButton.setTouchable(true);
			closeButton.fadeIn();
			closeButton.setTouchable(true);
		}
	}
	
	public void previous() {
		slides[currentSlide].clear();
		currentSlide--;
		slides[currentSlide].init();
		nextButton.fadeIn();
		nextButton.setTouchable(true);
		doneButton.fadeOut();
		doneButton.opacity=0;
		doneButton.setTouchable(false);
		if (currentSlide==0) {
			previousButton.fadeOut();
			previousButton.opacity=0;
			previousButton.setTouchable(false);
		}
	}
	
	public void slideComplete() {
		if (currentSlide>=numSlidesCompleted) {
			numSlidesCompleted++;
		}
		if (currentSlide<slides.length-1) {
			nextButton.fadeIn();
			nextButton.setTouchable(true);
		}
	}
	
	public void setFirstOpenMode(boolean en) {
		if (en) {
			numSlidesCompleted=0;
		} else {
			numSlidesCompleted=slides.length-1;
		}
	}

	public boolean getCloseEnabled() {
		return (numSlidesCompleted>=slides.length-1);
	}
	
	public class OverviewSlide extends DPCSprite {
		
		static final int STATE_NONE = 0;
		static final int STATE_TITLE = 1;
		static final int STATE_ANIMATE_CENTRE = 2;
		static final int STATE_ANIMATE_P1 = 3;
		static final int STATE_ANIMATE_P2 = 4;
		static final int STATE_ANIMATE_P3 = 5;
		static final int STATE_ANIMATE_P4 = 6;
		static final int STATE_CHIPS_FADE_IN = 7;
		static final int STATE_CHIP_1 = 8;
		static final int STATE_CHIP_2 = 9;
		static final int STATE_CHIP_3 = 10;
		static final int STATE_CHIP_4 = 11;
		static final int STATE_CHIPS_FADE_OUT = 12;
		
		int state;
		
		public TextLabel titleLabel;
		public TextLabel text1Label;
		public TextLabel tableCentreLabel;
		public TextLabel playerLabel;
		
		public DPCSprite tableCentreArrow=new DPCSprite();
		public DPCSprite playerArrow=new DPCSprite();
		
		Vector2 tableCentreStart=new Vector2();
		Vector2 tableCentreStop=new Vector2();
		Vector2 player1Start=new Vector2();
		Vector2 player1Stop=new Vector2();
		Vector2 player2Start=new Vector2();
		Vector2 player2Stop=new Vector2();
		Vector2 player3Start=new Vector2();
		Vector2 player3Stop=new Vector2();
		Vector2 player4Start=new Vector2();
		Vector2 player4Stop=new Vector2();
		Vector2 chip1Start=new Vector2();
		Vector2 chip1Stop=new Vector2();
		Vector2 chip2Start=new Vector2();
		Vector2 chip2Stop=new Vector2();
		Vector2 chip3Start=new Vector2();
		Vector2 chip3Stop=new Vector2();
		Vector2 chip4Start=new Vector2();
		Vector2 chip4Stop=new Vector2();
		
		public OverviewSlide() {
			titleLabel=new TextLabel("How to Arrange your Devices",0,true,0,false);
			titleLabel.setFontFace("segoe_print.ttf");
			titleLabel.bold=true;
			
			text1Label=new TextLabel("Connect all devices to the same WiFi network",0,true,0,false);
			text1Label.setFontFace("segoe_print.ttf");
			text1Label.setFadeInSpeed(0.8f);
			
			tableCentreLabel=new TextLabel("Table Center",0,true,0,false);
			tableCentreLabel.setFontFace("segoe_print.ttf");
			playerLabel=new TextLabel("Player's Device",0,true,0,false);
			playerLabel.setFontFace("segoe_print.ttf");
			
			tableCentreArrow.opacity=0;
			playerArrow.opacity=0;
		}
		
		@Override
		public void setDimensions(int radiusX,int radiusY) {
			super.setDimensions(radiusX, radiusY);
			tableCentreArrow.setDimensions((int)(radiusY*0.24f),(int)(radiusY*0.15f));
			playerArrow.setDimensions((int)(radiusY*0.24f),(int)(radiusY*0.15f));
		}
		
		@Override
		public void setPosition(float x,float y) {
			super.setPosition(x, y);
			tableCentreStop.set(x,y+radiusY*0.0f);
			tableCentreStart.set(tableCentreStop.x,0-tableCentre.radiusY*1.1f);
			player1Stop.set(x,tableCentreStop.y-tableCentre.radiusY-player1.radiusY);
			player1Start.set(player1Stop.x,0-player1.radiusY*1.1f);
			player2Stop.set(x,tableCentreStop.y+tableCentre.radiusY+player2.radiusY);
			player2Start.set(player2Stop.x,mFL.screenHeight+player2.radiusY*1.1f);
			player3Stop.set(tableCentreStop.x-tableCentre.radiusX-player3.radiusY,tableCentreStop.y);
			player3Start.set(0-player3.radiusY*1.1f,tableCentreStop.y);
			player4Stop.set(tableCentreStop.x+tableCentre.radiusX+player4.radiusY,tableCentreStop.y);
			player4Start.set(mFL.screenWidth+player3.radiusY*1.1f,tableCentreStop.y);
			chip1Start.set(player1Stop.x,player1Stop.y+player1.radiusY*0.4f);
			chip1Stop.set(player1Stop.x,tableCentreStop.y-tableCentre.radiusY*0.65f);
			chip2Start.set(player2Stop.x,player2Stop.y-player2.radiusY*0.4f);
			chip2Stop.set(player2Stop.x,tableCentreStop.y+tableCentre.radiusY*0.65f);
			chip3Start.set(player3Stop.x+player3.radiusY*0.4f,player3Stop.y);
			chip3Stop.set(tableCentreStop.x-tableCentre.radiusX*0.7f,tableCentreStop.y);
			chip4Start.set(player4Stop.x-player4.radiusY*0.4f,player4Stop.y);
			chip4Stop.set(tableCentreStop.x+tableCentre.radiusX*0.7f,tableCentreStop.y);
			text1Label.setPosition(x,y-radiusY*0.8f);
			tableCentreLabel.setPosition(player3Stop.x-player3.radiusX,player2Stop.y+player2.radiusY);
			playerLabel.setPosition(player4Stop.x+player4.radiusX,player2Stop.y+player2.radiusY);
			tableCentreArrow.setPosition(tableCentreStop.x-tableCentre.radiusX,tableCentreStop.y+tableCentre.radiusY);
			playerArrow.setPosition(player4Stop.x+player4.radiusY*1.1f,tableCentreStop.y+tableCentre.radiusY*0.7f);
			playerArrow.rotation=30;
		}
		
		@Override
		public void scalePosition(float scaleX,float scaleY) {
			super.scalePosition(scaleX, scaleY);
			titleLabel.scalePosition(scaleX,scaleY);
			text1Label.scalePosition(scaleX,scaleY);
			tableCentreLabel.scalePosition(scaleX,scaleY);
			playerLabel.scalePosition(scaleX,scaleY);
			tableCentreArrow.scalePosition(scaleX,scaleY);
			playerArrow.scalePosition(scaleX,scaleY);
		}
		
		@Override
		public void clear() {
			super.clear();
			state=STATE_NONE;
			titleLabel.fadeOut();
			titleLabel.opacity=0;
			text1Label.fadeOut();
			text1Label.opacity=0;
			tableCentreLabel.fadeOut();
			tableCentreLabel.opacity=0;
			playerLabel.fadeOut();
			playerLabel.opacity=0;
			tableCentre.fadeOut();
			tableCentre.opacity=0;
			tableCentreArrow.fadeOut();
			tableCentreArrow.opacity=0;
			playerArrow.fadeOut();
			playerArrow.opacity=0;
			player1.fadeOut();
			player1.opacity=0;
			player2.fadeOut();
			player2.opacity=0;
			player3.fadeOut();
			player3.opacity=0;
			player4.fadeOut();
			player4.opacity=0;
			chip1.fadeOut();
			chip1.opacity=0;
			chip2.fadeOut();
			chip2.opacity=0;
			chip3.fadeOut();
			chip3.opacity=0;
			chip4.fadeOut();
			chip4.opacity=0;
		}
		
		@Override
		public void init() {
			super.init();
			state=STATE_TITLE;
			titleLabel.fadeIn();
		}
		
		@Override
		public void animate(float delta) {
			super.animate(delta);
			if (state==STATE_TITLE) {
				titleLabel.animate(delta);
				if (titleLabel.opacity==1) {
					state=STATE_ANIMATE_CENTRE;
					tableCentre.opacity=1;
					tableCentre.setPosition(tableCentreStart);
					zoomTableCentre=1f;
				}
			} else if (state==STATE_ANIMATE_CENTRE) {
				if (Math.abs(tableCentre.y-tableCentreStop.y)<2) {
					tableCentre.y=tableCentreStop.y;
					state=STATE_ANIMATE_P1;
					player1.opacity=1;
					player1.setPosition(player1Start);
					zoomPlayer=1f;
					tableCentreLabel.fadeIn();
					tableCentreArrow.fadeIn();
				} else {
					float timeFactor = delta*9;
					tableCentre.y=(float)(tableCentre.y-timeFactor*(tableCentre.y-tableCentreStop.y));
				}
			} else if (state==STATE_ANIMATE_P1) {
				tableCentreLabel.animate(delta);
				if (tableCentreLabel.opacity>1) {
					tableCentreLabel.opacity=1;
				}
				tableCentreArrow.opacity=tableCentreLabel.opacity;
				if (Math.abs(player1.y-player1Stop.y)<2) {
					if (tableCentreLabel.opacity==1) {
						player1.y=player1Stop.y;
						state=STATE_ANIMATE_P2;
						player2.opacity=1;
						player2.setPosition(player2Start);
						
					}
				} else {
					float timeFactor = delta*9;
					player1.y=(float)(player1.y-timeFactor*(player1.y-player1Stop.y));
				}
			} else if (state==STATE_ANIMATE_P2) {
				
				if (Math.abs(player2.y-player2Stop.y)<2) {
					player2.y=player2Stop.y;
					state=STATE_ANIMATE_P3;
					player3.opacity=1;
					player3.setPosition(player3Start);
				} else {
					float timeFactor = delta*9;
					player2.y=(float)(player2.y-timeFactor*(player2.y-player2Stop.y));
				}
			} else if (state==STATE_ANIMATE_P3) {
				if (Math.abs(player3.x-player3Stop.x)<2) {
					player3.x=player3Stop.x;
					state=STATE_ANIMATE_P4;
					player4.opacity=1;
					player4.setPosition(player4Start);
				} else {
					float timeFactor = delta*9;
					player3.x=(float)(player3.x-timeFactor*(player3.x-player3Stop.x));
				}
			} else if (state==STATE_ANIMATE_P4) {
				if (Math.abs(player4.x-player4Stop.x)<2) {
					player4.x=player4Stop.x;
					state=STATE_CHIPS_FADE_IN;
					chip1.fadeIn();
					chip2.fadeIn();
					chip3.fadeIn();
					chip4.fadeIn();
					chip1.setPosition(chip1Start);
					chip2.setPosition(chip2Start);
					chip3.setPosition(chip3Start);
					chip4.setPosition(chip4Start);
					text1Label.fadeIn();
					playerLabel.fadeIn();
					playerArrow.fadeIn();
				} else {
					float timeFactor = delta*9;
					player4.x=(float)(player4.x-timeFactor*(player4.x-player4Stop.x));
				}
			} else if (state==STATE_CHIPS_FADE_IN) {
				chip1.animate(delta);
				chip2.opacity=chip1.opacity;
				chip3.opacity=chip1.opacity;
				chip4.opacity=chip1.opacity;
				text1Label.animate(delta);
				playerLabel.animate(delta);
				if (playerLabel.opacity>1) {
					playerLabel.opacity=1;
				}
				playerArrow.opacity=playerLabel.opacity;
				if (chip1.opacity==1&&text1Label.opacity==1&&playerLabel.opacity==1) {
					state=STATE_CHIP_1;
				}
			} else if (state==STATE_CHIP_1) {
				if (Math.abs(chip1.y-chip1Stop.y)<2) {
					chip1.y=chip1Stop.y;
					state=STATE_CHIP_3;
				} else {
					float timeFactor = delta*6;
					chip1.y=(float)(chip1.y-timeFactor*(chip1.y-chip1Stop.y));
				}
			} else if (state==STATE_CHIP_3) {
				if (Math.abs(chip3.x-chip3Stop.x)<2) {
					chip3.x=chip3Stop.x;
					state=STATE_CHIP_2;
				} else {
					float timeFactor = delta*6;
					chip3.x=(float)(chip3.x-timeFactor*(chip3.x-chip3Stop.x));
				}
			} else if (state==STATE_CHIP_2) {
				if (Math.abs(chip2.y-chip2Stop.y)<2) {
					chip2.y=chip2Stop.y;
					state=STATE_CHIP_4;
				} else {
					float timeFactor = delta*6;
					chip2.y=(float)(chip2.y-timeFactor*(chip2.y-chip2Stop.y));
				}
			} else if (state==STATE_CHIP_4) {
				if (Math.abs(chip4.x-chip4Stop.x)<2) {
					chip4.x=chip4Stop.x;
					state=STATE_CHIPS_FADE_OUT;
					chip1.fadeOut();
					chip2.fadeOut();
					chip3.fadeOut();
					chip4.fadeOut();
				} else {
					float timeFactor = delta*6;
					chip4.x=(float)(chip4.x-timeFactor*(chip4.x-chip4Stop.x));
				}
			} else if (state==STATE_CHIPS_FADE_OUT) {
				slideComplete();
				chip1.animate(delta);
				chip2.opacity=chip1.opacity;
				chip3.opacity=chip1.opacity;
				chip4.opacity=chip1.opacity;
				if (chip1.opacity==0) {
					state=STATE_CHIPS_FADE_IN;
					chip1.fadeIn();
					chip2.fadeIn();
					chip3.fadeIn();
					chip4.fadeIn();
					chip1.setPosition(chip1Start);
					chip2.setPosition(chip2Start);
					chip3.setPosition(chip3Start);
					chip4.setPosition(chip4Start);
				}
			}
		}
	}
	
	public class TableSetupSlide extends DPCSprite {
		
		static final int STATE_NONE = 0;
		static final int STATE_TITLE = 1;
		static final int STATE_ANIMATE_CENTRE = 2;
		static final int STATE_TEXT_1 = 3;
		static final int STATE_ZOOM = 4;
		static final int STATE_TEXT_2 = 5;
		
		int state;
		
		public TextLabel titleLabel;
		public TextLabel text1Label;
		public TextLabel text2Label;
		public TextLabel waitingLabel;
		
		Vector2 tableCentreStart=new Vector2();
		Vector2 tableCentreStop=new Vector2();
		
		public TableSetupSlide() {
			titleLabel=new TextLabel("Setting up the Table Centre",0,true,0,false);
			titleLabel.setFontFace("segoe_print.ttf");
			titleLabel.bold=true;
			text1Label=new TextLabel("Choose 'Create a Table'",0,true,0,false);
			text1Label.setFontFace("segoe_print.ttf");
			text1Label.setFadeInSpeed(0.8f);
			text2Label=new TextLabel("Set your table up, then players can join",0,true,0,false);
			text2Label.setFontFace("segoe_print.ttf");
			text2Label.setFadeInSpeed(0.8f);
			waitingLabel=new TextLabel("Waiting for Players",0,true,0,false);
			waitingLabel.setFontFace("segoe_print.ttf");
		}
		
		@Override
		public void setDimensions(int radiusX,int radiusY) {
			super.setDimensions(radiusX, radiusY);
			waitingLabel.setMaxDimensions((int)(tableCentre.radiusX*0.6f),(int)(tableCentre.radiusY*0.3f));
			waitingLabel.setTextSizeToMax();
		}
		
		@Override
		public void setPosition(float x,float y) {
			super.setPosition(x, y);
			tableCentreStop.set(x,y+radiusY*0.0f);
			tableCentreStart.set(tableCentreStop.x,0-tableCentre.radiusY*1.1f);
			text1Label.setPosition(x,y-radiusY*0.65f);
			text2Label.setPosition(x,y-radiusY*0.8f);
			waitingLabel.setPosition(tableCentreStop.x,tableCentreStop.y+tableCentre.radiusY*0.2f);
		}
		
		@Override
		public void scalePosition(float scaleX,float scaleY) {
			super.scalePosition(scaleX, scaleY);
			titleLabel.scalePosition(scaleX,scaleY);
			text1Label.scalePosition(scaleX,scaleY);
			text2Label.scalePosition(scaleX,scaleY);
			waitingLabel.scalePosition(scaleX, scaleY);
		}
		
		@Override
		public void clear() {
			super.clear();
			state=STATE_NONE;
			titleLabel.fadeOut();
			titleLabel.opacity=0;
			text1Label.fadeOut();
			text1Label.opacity=0;
			text2Label.fadeOut();
			text2Label.opacity=0;
			tableCentre.fadeOut();
			tableCentre.opacity=0;
			waitingLabel.fadeOut();
			waitingLabel.opacity=0;
		}
		
		@Override
		public void init() {
			super.init();
			state=STATE_TITLE;
			titleLabel.fadeIn();
		}
		
		@Override
		public void animate(float delta) {
			super.animate(delta);
			if (state==STATE_TITLE) {
				titleLabel.animate(delta);
				if (titleLabel.opacity==1) {
					state=STATE_ANIMATE_CENTRE;
					tableCentre.opacity=1;
					//tableCentre.setDimensions(tableCentreDimsBig);
					tableCentre.setPosition(tableCentreStart);
					zoomTableCentre=0.25f;
				}
			} else if (state==STATE_ANIMATE_CENTRE) {
				if (Math.abs(tableCentre.y-tableCentreStop.y)<2) {
					state=STATE_TEXT_1;
					tableCentre.y=tableCentreStop.y;
					text1Label.fadeIn();
				} else {
					float timeFactor = delta*9;
					tableCentre.y=(float)(tableCentre.y-timeFactor*(tableCentre.y-tableCentreStop.y));
				}
			} else if (state==STATE_TEXT_1) {
				text1Label.animate(delta);
				if (text1Label.opacity==1) {
					state=STATE_ZOOM;
				}
			} else if (state==STATE_ZOOM) {
				if (Math.abs(zoomTableCentre-1)<0.005) {
					state=STATE_TEXT_2;
					text2Label.fadeIn();
					waitingLabel.startFlashing();
					slideComplete();
				} else {
					float deltaZoom_=delta*2f*(zoomTableCentre-1);
					if (deltaZoom_<-0.03f) {
						deltaZoom_=-0.03f;
					} else if (deltaZoom_>0.03f) {
						deltaZoom_=0.03f;
					}
					zoomTableCentre-=deltaZoom_;
				}
			} else if (state==STATE_TEXT_2) {
				text2Label.animate(delta);
				waitingLabel.animate(delta);
			}
		}
	}
	
	public class PlayerSetupSlide extends DPCSprite {
		
		static final int STATE_NONE = 0;
		static final int STATE_TITLE = 1;
		static final int STATE_ANIMATE_CENTRE = 2;
		static final int STATE_ANIMATE_PLAYER = 3;
		static final int STATE_TEXT_1 = 4;
		static final int STATE_ZOOM = 5;
		static final int STATE_TEXT_2 = 6;
		static final int STATE_CONNECT = 7;
		
		int state;
		
		public TextLabel titleLabel;
		public TextLabel text1Label;
		public TextLabel text2Label;
		
		Vector2 tableCentreStart=new Vector2();
		Vector2 tableCentreStop=new Vector2();
		Vector2 player1Start=new Vector2();
		Vector2 player1Stop=new Vector2();
		
		public PlayerSetupSlide() {
			titleLabel=new TextLabel("Setting up the Players",0,true,0,false);
			titleLabel.setFontFace("segoe_print.ttf");
			titleLabel.bold=true;
			text1Label=new TextLabel("Choose 'Join a Table'",0,true,0,false);
			text1Label.setFontFace("segoe_print.ttf");
			text1Label.setFadeInSpeed(0.8f);
			text2Label=new TextLabel("The Table Centre will invite you to join",0,true,0,false);
			text2Label.setFontFace("segoe_print.ttf");
			text2Label.setFadeInSpeed(0.8f);
		}
		
		@Override
		public void setDimensions(int radiusX,int radiusY) {
			super.setDimensions(radiusX, radiusY);
		}
		
		@Override
		public void setPosition(float x,float y) {
			super.setPosition(x, y);
			tableCentreStop.set(x,y+radiusY*0.2f);
			tableCentreStart.set(tableCentreStop.x,0-tableCentre.radiusY*1.1f);
			player1Stop.set(x,tableCentreStop.y-tableCentre.radiusY-player1.radiusY);
			player1Start.set(player1Stop.x,0-player1.radiusY*1.1f);
			text1Label.setPosition(x,y-radiusY*0.65f);
			text2Label.setPosition(x,y-radiusY*0.8f);
		}
		
		@Override
		public void scalePosition(float scaleX,float scaleY) {
			super.scalePosition(scaleX, scaleY);
			titleLabel.scalePosition(scaleX,scaleY);
			text1Label.scalePosition(scaleX,scaleY);
			text2Label.scalePosition(scaleX,scaleY);
		}
		
		@Override
		public void clear() {
			super.clear();
			state=STATE_NONE;
			titleLabel.fadeOut();
			titleLabel.opacity=0;
			text1Label.fadeOut();
			text1Label.opacity=0;
			text2Label.fadeOut();
			text2Label.opacity=0;
			tableCentre.fadeOut();
			tableCentre.opacity=0;
			player1.fadeOut();
			player1.opacity=0;
		}
		
		@Override
		public void init() {
			super.init();
			state=STATE_TITLE;
			titleLabel.fadeIn();
		}
		
		@Override
		public void animate(float delta) {
			super.animate(delta);
			if (state==STATE_TITLE) {
				titleLabel.animate(delta);
				if (titleLabel.opacity==1) {
					state=STATE_ANIMATE_CENTRE;
					tableCentre.opacity=1;
					tableCentre.setPosition(tableCentreStart);
					zoomTableCentre=1f;
				}
			} else if (state==STATE_ANIMATE_CENTRE) {
				if (Math.abs(tableCentre.y-tableCentreStop.y)<2) {
					state=STATE_ANIMATE_PLAYER;
					tableCentre.y=tableCentreStop.y;
					player1.opacity=1;
					player1.setPosition(player1Start);
					zoomPlayer=0.25f;
				} else {
					float timeFactor = delta*9;
					tableCentre.y=(float)(tableCentre.y-timeFactor*(tableCentre.y-tableCentreStop.y));
				}
			} else if (state==STATE_ANIMATE_PLAYER) {
				if (Math.abs(player1.y-player1Stop.y)<2) {
					state=STATE_TEXT_1;
					player1.y=player1Stop.y;
					text1Label.fadeIn();
				} else {
					float timeFactor = delta*9;
					player1.y=(float)(player1.y-timeFactor*(player1.y-player1Stop.y));
				}
			} else if (state==STATE_TEXT_1) {
				text1Label.animate(delta);
				if (text1Label.opacity==1) {
					state=STATE_ZOOM;
				}
			} else if (state==STATE_ZOOM) {
				if (Math.abs(zoomPlayer-1)<0.005) {
					state=STATE_TEXT_2;
					text2Label.fadeIn();
				} else {
					float deltaZoom_=delta*2f*(zoomPlayer-1);
					if (deltaZoom_<-0.03f) {
						deltaZoom_=-0.03f;
					} else if (deltaZoom_>0.03f) {
						deltaZoom_=0.03f;
					}
					zoomPlayer-=deltaZoom_;
				}
			} else if (state==STATE_TEXT_2) {
				text2Label.animate(delta);
				if (text2Label.opacity==1) {
					state=STATE_CONNECT;
					slideComplete();
				}
			} else if (state==STATE_CONNECT) {
				// add connection blobs here
			}
		}
	}
	
	public class EnjoySlide extends DPCSprite {
		
		static final int STATE_NONE = 0;
		static final int STATE_TITLE = 1;
		static final int STATE_ANIMATE_CENTRE = 2;
		static final int STATE_ANIMATE_P1 = 3;
		static final int STATE_ANIMATE_P2 = 4;
		static final int STATE_ANIMATE_P3 = 5;
		static final int STATE_ANIMATE_P4 = 6;
		static final int STATE_CHIPS_FADE_IN = 7;
		static final int STATE_CHIP_1 = 8;
		static final int STATE_CHIP_2 = 9;
		static final int STATE_CHIP_3 = 10;
		static final int STATE_CHIP_4 = 11;
		static final int STATE_CHIPS_FADE_OUT = 12;
		
		int state;
		
		public TextLabel titleLabel;
		
		Vector2 tableCentreStart=new Vector2();
		Vector2 tableCentreStop=new Vector2();
		Vector2 player1Start=new Vector2();
		Vector2 player1Stop=new Vector2();
		Vector2 player2Start=new Vector2();
		Vector2 player2Stop=new Vector2();
		Vector2 player3Start=new Vector2();
		Vector2 player3Stop=new Vector2();
		Vector2 player4Start=new Vector2();
		Vector2 player4Stop=new Vector2();
		Vector2 chip1Start=new Vector2();
		Vector2 chip1Stop=new Vector2();
		Vector2 chip2Start=new Vector2();
		Vector2 chip2Stop=new Vector2();
		Vector2 chip3Start=new Vector2();
		Vector2 chip3Stop=new Vector2();
		Vector2 chip4Start=new Vector2();
		Vector2 chip4Stop=new Vector2();
		
		public EnjoySlide() {
			titleLabel=new TextLabel("Enjoy your Game!",0,true,0,false);
			titleLabel.setFontFace("segoe_print.ttf");
			titleLabel.bold=true;
		}
		
		@Override
		public void setDimensions(int radiusX,int radiusY) {
			super.setDimensions(radiusX, radiusY);
		}
		
		@Override
		public void setPosition(float x,float y) {
			super.setPosition(x, y);
			tableCentreStop.set(x,y-radiusY*0.15f);
			tableCentreStart.set(tableCentreStop.x,0-tableCentre.radiusY*1.1f);
			player1Stop.set(x,tableCentreStop.y-tableCentre.radiusY-player1.radiusY);
			player1Start.set(player1Stop.x,0-player1.radiusY*1.1f);
			player2Stop.set(x,tableCentreStop.y+tableCentre.radiusY+player2.radiusY);
			player2Start.set(player2Stop.x,mFL.screenHeight+player2.radiusY*1.1f);
			player3Stop.set(tableCentreStop.x-tableCentre.radiusX-player3.radiusY,tableCentreStop.y);
			player3Start.set(0-player3.radiusY*1.1f,tableCentreStop.y);
			player4Stop.set(tableCentreStop.x+tableCentre.radiusX+player4.radiusY,tableCentreStop.y);
			player4Start.set(mFL.screenWidth+player3.radiusY*1.1f,tableCentreStop.y);
			chip1Start.set(player1Stop.x,player1Stop.y+player1.radiusY*0.4f);
			chip1Stop.set(player1Stop.x,tableCentreStop.y-tableCentre.radiusY*0.65f);
			chip2Start.set(player2Stop.x,player2Stop.y-player2.radiusY*0.4f);
			chip2Stop.set(player2Stop.x,tableCentreStop.y+tableCentre.radiusY*0.65f);
			chip3Start.set(player3Stop.x+player3.radiusY*0.4f,player3Stop.y);
			chip3Stop.set(tableCentreStop.x-tableCentre.radiusX*0.7f,tableCentreStop.y);
			chip4Start.set(player4Stop.x-player4.radiusY*0.4f,player4Stop.y);
			chip4Stop.set(tableCentreStop.x+tableCentre.radiusX*0.7f,tableCentreStop.y);
		}
		
		@Override
		public void scalePosition(float scaleX,float scaleY) {
			super.scalePosition(scaleX, scaleY);
			titleLabel.scalePosition(scaleX,scaleY);
		}
		
		@Override
		public void clear() {
			super.clear();
			state=STATE_NONE;
			titleLabel.fadeOut();
			titleLabel.opacity=0;
			tableCentre.fadeOut();
			tableCentre.opacity=0;
			player1.fadeOut();
			player1.opacity=0;
			player2.fadeOut();
			player2.opacity=0;
			player3.fadeOut();
			player3.opacity=0;
			player4.fadeOut();
			player4.opacity=0;
			chip1.fadeOut();
			chip1.opacity=0;
			chip2.fadeOut();
			chip2.opacity=0;
			chip3.fadeOut();
			chip3.opacity=0;
			chip4.fadeOut();
			chip4.opacity=0;
		}
		
		@Override
		public void init() {
			super.init();
			state=STATE_TITLE;
			titleLabel.fadeIn();
		}
		
		@Override
		public void animate(float delta) {
			super.animate(delta);
			if (state==STATE_TITLE) {
				titleLabel.animate(delta);
				if (titleLabel.opacity==1) {
					state=STATE_ANIMATE_CENTRE;
					tableCentre.opacity=1;
					tableCentre.setPosition(tableCentreStart);
					zoomTableCentre=1f;
				}
			} else if (state==STATE_ANIMATE_CENTRE) {
				if (Math.abs(tableCentre.y-tableCentreStop.y)<2) {
					tableCentre.y=tableCentreStop.y;
					state=STATE_ANIMATE_P1;
					player1.opacity=1;
					player1.setPosition(player1Start);
					zoomPlayer=1f;
				} else {
					float timeFactor = delta*9;
					tableCentre.y=(float)(tableCentre.y-timeFactor*(tableCentre.y-tableCentreStop.y));
				}
			} else if (state==STATE_ANIMATE_P1) {
				if (Math.abs(player1.y-player1Stop.y)<2) {
					player1.y=player1Stop.y;
					state=STATE_ANIMATE_P2;
					player2.opacity=1;
					player2.setPosition(player2Start);
				} else {
					float timeFactor = delta*9;
					player1.y=(float)(player1.y-timeFactor*(player1.y-player1Stop.y));
				}
			} else if (state==STATE_ANIMATE_P2) {
				if (Math.abs(player2.y-player2Stop.y)<2) {
					player2.y=player2Stop.y;
					state=STATE_ANIMATE_P3;
					player3.opacity=1;
					player3.setPosition(player3Start);
				} else {
					float timeFactor = delta*9;
					player2.y=(float)(player2.y-timeFactor*(player2.y-player2Stop.y));
				}
			} else if (state==STATE_ANIMATE_P3) {
				if (Math.abs(player3.x-player3Stop.x)<2) {
					player3.x=player3Stop.x;
					state=STATE_ANIMATE_P4;
					player4.opacity=1;
					player4.setPosition(player4Start);
				} else {
					float timeFactor = delta*9;
					player3.x=(float)(player3.x-timeFactor*(player3.x-player3Stop.x));
				}
			} else if (state==STATE_ANIMATE_P4) {
				if (Math.abs(player4.x-player4Stop.x)<2) {
					player4.x=player4Stop.x;
					state=STATE_CHIPS_FADE_IN;
					chip1.fadeIn();
					chip2.fadeIn();
					chip3.fadeIn();
					chip4.fadeIn();
					chip1.setPosition(chip1Start);
					chip2.setPosition(chip2Start);
					chip3.setPosition(chip3Start);
					chip4.setPosition(chip4Start);
				} else {
					float timeFactor = delta*9;
					player4.x=(float)(player4.x-timeFactor*(player4.x-player4Stop.x));
				}
			} else if (state==STATE_CHIPS_FADE_IN) {
				chip1.animate(delta);
				chip2.opacity=chip1.opacity;
				chip3.opacity=chip1.opacity;
				chip4.opacity=chip1.opacity;
				if (chip1.opacity==1) {
					state=STATE_CHIP_1;
				}
			} else if (state==STATE_CHIP_1) {
				if (Math.abs(chip1.y-chip1Stop.y)<2) {
					chip1.y=chip1Stop.y;
					state=STATE_CHIP_3;
				} else {
					float timeFactor = delta*6;
					chip1.y=(float)(chip1.y-timeFactor*(chip1.y-chip1Stop.y));
				}
			} else if (state==STATE_CHIP_3) {
				if (Math.abs(chip3.x-chip3Stop.x)<2) {
					chip3.x=chip3Stop.x;
					state=STATE_CHIP_2;
				} else {
					float timeFactor = delta*6;
					chip3.x=(float)(chip3.x-timeFactor*(chip3.x-chip3Stop.x));
				}
			} else if (state==STATE_CHIP_2) {
				if (Math.abs(chip2.y-chip2Stop.y)<2) {
					chip2.y=chip2Stop.y;
					state=STATE_CHIP_4;
				} else {
					float timeFactor = delta*6;
					chip2.y=(float)(chip2.y-timeFactor*(chip2.y-chip2Stop.y));
				}
			} else if (state==STATE_CHIP_4) {
				if (Math.abs(chip4.x-chip4Stop.x)<2) {
					chip4.x=chip4Stop.x;
					state=STATE_CHIPS_FADE_OUT;
					chip1.fadeOut();
					chip2.fadeOut();
					chip3.fadeOut();
					chip4.fadeOut();
				} else {
					float timeFactor = delta*6;
					chip4.x=(float)(chip4.x-timeFactor*(chip4.x-chip4Stop.x));
				}
			} else if (state==STATE_CHIPS_FADE_OUT) {
				slideComplete();
				chip1.animate(delta);
				chip2.opacity=chip1.opacity;
				chip3.opacity=chip1.opacity;
				chip4.opacity=chip1.opacity;
				if (chip1.opacity==0) {
					state=STATE_CHIPS_FADE_IN;
					chip1.fadeIn();
					chip2.fadeIn();
					chip3.fadeIn();
					chip4.fadeIn();
					chip1.setPosition(chip1Start);
					chip2.setPosition(chip2Start);
					chip3.setPosition(chip3Start);
					chip4.setPosition(chip4Start);
				}
			}
		}
		
	}

}
