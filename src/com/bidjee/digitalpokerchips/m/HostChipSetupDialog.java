package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class HostChipSetupDialog extends DPCSprite {
	
	static final String BUYIN_TITLE = "Buy-In to ";
	
	public DPCSprite chipCaseSprite;
	
	public DPCSprite chipASprite;
	public Button minusAButton;
	public Button plusAButton;
	public DPCSprite amountABackground;
	public TextLabel amountALabel;
	public TextLabel infoALabel;
	
	public DPCSprite chipBSprite;
	public Button minusBButton;
	public Button plusBButton;
	public DPCSprite amountBBackground;
	public TextLabel amountBLabel;
	public TextLabel infoBLabel;
	
	public DPCSprite chipCSprite;
	public Button minusCButton;
	public Button plusCButton;
	public DPCSprite amountCBackground;
	public TextLabel amountCLabel;
	public TextLabel infoCLabel;
	
	public Button minusBuyinButton;
	public Button plusBuyinButton;
	public DPCSprite amountBuyinBackground;
	public TextLabel amountBuyinLabel;
	public TextLabel infoBuyinLabel;
	
	public Button backButton;
	public Button okayButton;
	
	Vector2 posStart;
	Vector2 posOnscreen;
	Vector2 posNext;
	
	
	private int valueA;
	private int valueB;
	private int valueC;
	private int buyinDivideValueA;
	
	public HostChipSetupDialog() {
		chipCaseSprite=new DPCSprite();
		chipCaseSprite.opacity=0;
		chipASprite=new DPCSprite();
		minusAButton=new Button(false,1,"");
		plusAButton=new Button(false,1,"");
		amountABackground=new DPCSprite();
		amountALabel=new TextLabel("",0,false,1,false);
		amountALabel.setFontFace("coolvetica_rg.ttf");
		amountALabel.bodyColor=ForegroundLayer.brightestGoldColor;
		infoALabel=new TextLabel("Chip Value",0,false,1,false);
		infoALabel.setFontFace("coolvetica_rg.ttf");
		infoALabel.bodyColor=ForegroundLayer.brightestGoldColor;
		
		chipBSprite=new DPCSprite();
		minusBButton=new Button(false,1,"");
		plusBButton=new Button(false,1,"");
		amountBBackground=new DPCSprite();
		amountBLabel=new TextLabel("",0,false,1,false);
		amountBLabel.setFontFace("coolvetica_rg.ttf");
		amountBLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		infoBLabel=new TextLabel("Chip Value",0,false,1,false);
		infoBLabel.setFontFace("coolvetica_rg.ttf");
		infoBLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		
		chipCSprite=new DPCSprite();
		minusCButton=new Button(false,1,"");
		plusCButton=new Button(false,1,"");
		amountCBackground=new DPCSprite();
		amountCLabel=new TextLabel("",0,false,1,false);
		amountCLabel.setFontFace("coolvetica_rg.ttf");
		amountCLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		infoCLabel=new TextLabel("Chip Value",0,false,1,false);
		infoCLabel.setFontFace("coolvetica_rg.ttf");
		infoCLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		
		minusBuyinButton=new Button(false,1,"");
		plusBuyinButton=new Button(false,1,"");
		amountBuyinBackground=new DPCSprite();
		amountBuyinLabel=new TextLabel("",0,false,1,false);
		amountBuyinLabel.setFontFace("coolvetica_rg.ttf");
		amountBuyinLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		infoBuyinLabel=new TextLabel("Max Buy In",0,false,1,false);
		infoBuyinLabel.setFontFace("coolvetica_rg.ttf");
		infoBuyinLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		backButton=new Button(false,1,"BACK");
		backButton.getLabel().setFontFace("coolvetica_rg.ttf");
		backButton.getLabel().bodyColor=ForegroundLayer.brightestGoldColor;
		okayButton=new Button(false,1,"OKAY");
		okayButton.getLabel().setFontFace("coolvetica_rg.ttf");
		okayButton.getLabel().bodyColor=ForegroundLayer.brightestGoldColor;
		
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		
		chipCaseSprite.setDimensions((int)(radiusY*0.23f),(int)(radiusY*0.23f));

		chipASprite.setDimensions((int)(radiusY*0.22f),(int)(radiusY*0.22f));
		minusAButton.setDimensions((int)(radiusY*0.09f),(int)(radiusY*0.09f));
		plusAButton.setDimensions((int)(radiusY*0.09f),(int)(radiusY*0.09f));
		amountABackground.setDimensions((int)(radiusX*0.16f),(int)(radiusY*0.10f));
		amountALabel.setMaxDimensions((int)(radiusX*0.2f),(int)(radiusY*0.07f));
		amountALabel.setTextSizeToMax();
		infoALabel.setMaxDimensions((int)(radiusX*0.5f),(int)(radiusY*0.05f));
		infoALabel.setTextSizeToMax("$100000");
		
		chipBSprite.setDimensions(chipASprite.radiusX,chipASprite.radiusY);
		minusBButton.setDimensions(minusAButton.radiusX,minusAButton.radiusY);
		plusBButton.setDimensions(plusAButton.radiusX,plusAButton.radiusY);
		amountBBackground.setDimensions(amountABackground.radiusX,amountABackground.radiusY);
		amountBLabel.setTextSize(amountALabel.getTextSize());
		infoBLabel.setTextSize(infoALabel.getTextSize());
		
		chipCSprite.setDimensions(chipASprite.radiusX,chipASprite.radiusY);
		minusCButton.setDimensions(minusAButton.radiusX,minusAButton.radiusY);
		plusCButton.setDimensions(plusAButton.radiusX,plusAButton.radiusY);
		amountCBackground.setDimensions(amountABackground.radiusX,amountABackground.radiusY);
		amountCLabel.setTextSize(amountALabel.getTextSize());
		infoCLabel.setTextSize(infoALabel.getTextSize());
		
		minusBuyinButton.setDimensions((int)(radiusY*0.15f),(int)(radiusY*0.15f));
		plusBuyinButton.setDimensions(minusBuyinButton.radiusX,minusBuyinButton.radiusY);
		amountBuyinBackground.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.18f));
		amountBuyinLabel.setMaxDimensions((int)(radiusX*0.5f),(int)(radiusY*0.15f));
		amountBuyinLabel.setTextSizeToMax("$1000000");
		infoBuyinLabel.setMaxDimensions((int)(radiusX*0.5f),(int)(radiusY*0.06f));
		infoBuyinLabel.setTextSizeToMax();
		
		backButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.16f),0.6f,0.7f);
		okayButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.16f),0.6f,0.7f);

	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		chipCaseSprite.setPosition(x,y+radiusY*0.99f);
		
		chipASprite.setPosition(x-radiusX*0.60f,y+radiusY*0.44f);
		minusAButton.setPosition(x-radiusX*0.82f,y+radiusY*0.1f);
		plusAButton.setPosition(x-radiusX*0.38f,minusAButton.y);
		amountABackground.setPosition(chipASprite.x,minusAButton.y);
		amountALabel.setPosition(chipASprite.x,minusAButton.y);
		infoALabel.setPosition(chipASprite.x,y-radiusY*0.05f);
		
		chipBSprite.setPosition(x,chipASprite.y);
		minusBButton.setPosition(x-radiusX*0.22f,minusAButton.y);
		plusBButton.setPosition(x+radiusX*0.22f,minusAButton.y);
		amountBBackground.setPosition(chipBSprite.x,minusAButton.y);
		amountBLabel.setPosition(chipBSprite.x,minusAButton.y);
		infoBLabel.setPosition(chipBSprite.x,infoALabel.y);
		
		chipCSprite.setPosition(x+radiusX*0.60f,chipASprite.y);
		minusCButton.setPosition(x+radiusX*0.38f,minusAButton.y);
		plusCButton.setPosition(x+radiusX*0.82f,minusAButton.y);
		amountCBackground.setPosition(chipCSprite.x,minusAButton.y);
		amountCLabel.setPosition(chipCSprite.x,minusAButton.y);
		infoCLabel.setPosition(chipCSprite.x,infoALabel.y);
		
		minusBuyinButton.setPosition(x-radiusX*0.6f,y-radiusY*0.35f);
		plusBuyinButton.setPosition(x+radiusX*0.6f,minusBuyinButton.y);
		amountBuyinBackground.setPosition(x,minusBuyinButton.y);
		amountBuyinLabel.setPosition(amountBuyinBackground.x,minusBuyinButton.y);
		infoBuyinLabel.setPosition(amountBuyinBackground.x,y-radiusY*0.58f);
		
		backButton.setPosition(x-radiusX*0.5f+1,y-radiusY+backButton.radiusY);
		okayButton.setPosition(x+radiusX*0.5f-1,y-radiusY+okayButton.radiusY);
	}
	
	public void setPositions(float xStart,float yStart,float xOnscreen,float yOnscreen,float xNext,float yNext) {		
		posStart=new Vector2(xStart,yStart);
		posOnscreen=new Vector2(xOnscreen,yOnscreen);
		posNext=new Vector2(xNext,yNext);
	}
	
	public void initialisePosition() {
		setPosition(posStart);
	}
	
	@Override
	public void scalePosition(float scaleX_,float scaleY_) {
		super.scalePosition(scaleX_,scaleY_);
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
	}
	
	public void show() {

		this.setDest(posOnscreen);
		
		if (valueA==0) {
			setChipValues(ChipCase.defaultValues);
		}
		
		minusAButton.setTouchable(true);
		plusAButton.setTouchable(true);
		minusBButton.setTouchable(true);
		plusBButton.setTouchable(true);
		minusCButton.setTouchable(true);
		plusCButton.setTouchable(true);
		minusBuyinButton.setTouchable(true);
		plusBuyinButton.setTouchable(true);
		backButton.setTouchable(true);
		okayButton.setTouchable(true);
		chipCaseSprite.opacity = 1;
	}
	
	public void hide() {
		minusAButton.setTouchable(false);
		plusAButton.setTouchable(false);
		minusBButton.setTouchable(false);
		plusBButton.setTouchable(false);
		minusCButton.setTouchable(false);
		plusCButton.setTouchable(false);
		minusBuyinButton.setTouchable(false);
		plusBuyinButton.setTouchable(false);
		backButton.setTouchable(false);
		okayButton.setTouchable(false);
	}
	
	public void back() {
		this.setDest(posStart);
		hide();
	}
	
	public void next() {
		this.setDest(posNext);
		hide();
	}
	
	public void setChipValues(int values[]) {
		valueA = values[ChipCase.CHIP_A];
		amountALabel.setText("$"+Integer.toString(valueA));
		amountALabel.loadTexture();
		valueB = values[ChipCase.CHIP_B];
		amountBLabel.setText("$"+Integer.toString(valueB));
		amountBLabel.loadTexture();
		valueC = values[ChipCase.CHIP_C];
		amountCLabel.setText("$"+Integer.toString(valueC));
		amountCLabel.loadTexture();
		buyinDivideValueA = 20;
		amountBuyinLabel.setText("$"+Integer.toString(buyinDivideValueA*valueA));
		amountBuyinLabel.loadTexture();
	}

	public void minusA() {
		int valIndex=ChipCase.getLegalValueIndex(valueA);
		if (valIndex>0) {
			valueA=ChipCase.legalValues[valIndex-1];
			amountALabel.setText("$"+Integer.toString(valueA));
			amountALabel.loadTexture();
			setBuyin(buyinDivideValueA);
		}
	}
	
	public void plusA() {
		int valIndex=ChipCase.getLegalValueIndex(valueA);
		if (valIndex<ChipCase.legalValues.length-3) {
			valueA=ChipCase.legalValues[valIndex+1];
			amountALabel.setText("$"+Integer.toString(valueA));
			amountALabel.loadTexture();
			if (valueA>=valueB) {
				plusB();
			}
			setBuyin(buyinDivideValueA);
		}
	}
	
	public void minusB() {
		int valIndex=ChipCase.getLegalValueIndex(valueB);
		if (valIndex>1) {
			valueB=ChipCase.legalValues[valIndex-1];
			amountBLabel.setText("$"+Integer.toString(valueB));
			amountBLabel.loadTexture();
			if (valueB<=valueA) {
				minusA();
			}
		}
	}
	
	public void plusB() {
		int valIndex=ChipCase.getLegalValueIndex(valueB);
		if (valIndex<ChipCase.legalValues.length-2) {
			valueB=ChipCase.legalValues[valIndex+1];
			amountBLabel.setText("$"+Integer.toString(valueB));
			amountBLabel.loadTexture();
			if (valueB>=valueC) {
				plusC();
			}
		}
	}
	
	public void minusC() {
		int valIndex=ChipCase.getLegalValueIndex(valueC);
		if (valIndex>2) {
			valueC=ChipCase.legalValues[valIndex-1];
			amountCLabel.setText("$"+Integer.toString(valueC));
			amountCLabel.loadTexture();
		}
		if (valueC<=valueB) {
			minusB();
		}
	}
	
	public void plusC() {
		int valIndex=ChipCase.getLegalValueIndex(valueC);
		if (valIndex<ChipCase.legalValues.length-1) {
			valueC=ChipCase.legalValues[valIndex+1];
			amountCLabel.setText("$"+Integer.toString(valueC));
			amountCLabel.loadTexture();
		}
	}
	
	public void minusBuyin() {
		if (buyinDivideValueA>1) {
			setBuyin(buyinDivideValueA-1);
		}
	}
	
	public void plusBuyin() {
		setBuyin(buyinDivideValueA+1);
	}
	
	public void setBuyin(int buyinDivideValueA) {
		this.buyinDivideValueA=buyinDivideValueA;
		amountBuyinLabel.setText("$"+Integer.toString(buyinDivideValueA*valueA));
		amountBuyinLabel.loadTexture();
	}

	public int[] getChipValues() {
		int[] values = new int[ChipCase.CHIP_TYPES];
		values[0] = valueA;
		values[1] = valueB;
		values[2] = valueC;
		return values;
	}

}