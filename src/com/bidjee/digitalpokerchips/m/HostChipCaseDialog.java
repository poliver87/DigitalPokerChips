package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class HostChipCaseDialog extends Dialog {
	
	TextField focussedField;
	
	public DPCSprite chipCaseSprite;
	public TextLabel selectLabel;
	
	public Button standardBackground;
	public TextLabel standardTitle;
	public DPCSprite standardImage;
	public TextLabel standardText1;
	public TextLabel standardText2;
	public TextLabel standardPrice;
	
	public Button customBackground;
	public TextLabel customTitle;
	public DPCSprite customImage;
	public TextLabel customText1;
	public TextLabel customText2;
	public TextLabel customPrice;
	
	public Button backButton;
	
	Vector2 posStart;
	Vector2 posOnscreen;
	Vector2 posNext;
	
	public HostChipCaseDialog() {
		chipCaseSprite = new DPCSprite();
		selectLabel=new TextLabel("Select Chip Case",0,false,0,false);
		selectLabel.setFontFace("coolvetica_rg.ttf");
		selectLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		
		standardBackground = new Button(false,1,"");
		standardTitle = new TextLabel("Standard",0,false,1,false);
		standardTitle.setFontFace("coolvetica_rg.ttf");
		standardTitle.bodyColor = ForegroundLayer.whiteColor;
		standardImage = new DPCSprite();
		standardText1 = new TextLabel("Fixed Chip Values",0,false,1,false);
		standardText1.setFontFace("coolvetica_rg.ttf");
		standardText1.bodyColor = ForegroundLayer.whiteColor;
		standardText2 = new TextLabel("Max Buy In $400",0,false,1,false);
		standardText2.setFontFace("coolvetica_rg.ttf");
		standardText2.bodyColor = ForegroundLayer.whiteColor;
		standardPrice = new TextLabel("FREE",0,false,1,false);
		standardPrice.setFontFace("coolvetica_rg.ttf");
		standardPrice.bodyColor = ForegroundLayer.brightGoldColor;
		
		customBackground = new Button(false,1,"");
		customTitle = new TextLabel("Custom",0,false,1,false);
		customTitle.setFontFace("coolvetica_rg.ttf");
		customTitle.bodyColor = ForegroundLayer.whiteColor;
		customImage = new DPCSprite();
		customText1 = new TextLabel("Customizable Chip Values",0,false,1,false);
		customText1.setFontFace("coolvetica_rg.ttf");
		customText1.bodyColor = ForegroundLayer.whiteColor;
		customText2 = new TextLabel("Max Buy In Unlimited",0,false,1,false);
		customText2.setFontFace("coolvetica_rg.ttf");
		customText2.bodyColor = ForegroundLayer.whiteColor;
		customPrice = new TextLabel("$4.99",0,false,1,false);
		customPrice.setFontFace("coolvetica_rg.ttf");
		customPrice.bodyColor = ForegroundLayer.brightGoldColor;
		
		backButton=new Button(false,1,"BACK");
		backButton.getLabel().setFontFace("coolvetica_rg.ttf");
		backButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		chipCaseSprite.setDimensions((int)(radiusY*0.25f),(int)(radiusY*0.22f));
		selectLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.10f));
		selectLabel.setTextSizeToMax();
		standardBackground.setDimensions((int)(radiusY*0.5f),(int)(radiusY*0.5f));
		standardTitle.setMaxDimensions((int)(radiusX*0.4f),(int)(radiusY*0.08f));
		standardTitle.setTextSizeToMax();
		standardImage.setDimensions((int)(radiusY*0.18f),(int)(radiusY*0.18f));
		standardText1.setMaxDimensions((int)(radiusX*0.4f),(int)(radiusY*0.05f));
		standardText1.setTextSizeToMax();
		standardText2.setMaxDimensions((int)(radiusX*0.4f),(int)(radiusY*0.05f));
		standardText2.setTextSizeToMax();
		standardPrice.setMaxDimensions((int)(radiusX*0.4f),(int)(radiusY*0.07f));
		standardPrice.setTextSizeToMax();
		
		customBackground.setDimensions(standardBackground.radiusX,standardBackground.radiusY);
		customTitle.setTextSize(standardTitle.getTextSize());
		customImage.setDimensions(standardImage.radiusX, standardImage.radiusY);
		customText1.setTextSize(standardText1.getTextSize());
		customText2.setTextSize(standardText2.getTextSize());
		customPrice.setTextSize(standardPrice.getTextSize());
		
		backButton.setDimensions((int)(radiusX*1f),(int)(radiusY*0.16f),0.6f,0.7f);
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		chipCaseSprite.setPosition(x,y+radiusY*0.99f);
		selectLabel.setPosition(x,y+radiusY*0.62f);
		standardBackground.setPosition(x-radiusX*0.45f,y-radiusY*0.05f);
		standardTitle.setPosition(standardBackground.x,standardBackground.y+standardBackground.radiusY*0.75f);
		standardImage.setPosition(standardBackground.x,standardBackground.y+standardBackground.radiusY*0.2f);
		standardText1.setPosition(standardBackground.x,standardBackground.y-standardBackground.radiusY*0.30f);
		standardText2.setPosition(standardBackground.x,standardBackground.y-standardBackground.radiusY*0.50f);
		standardPrice.setPosition(standardBackground.x,standardBackground.y-standardBackground.radiusY*0.75f);
		customBackground.setPosition(x+radiusX*0.45f,y-radiusY*0.05f);
		customTitle.setPosition(customBackground.x,customBackground.y+customBackground.radiusY*0.75f);
		customImage.setPosition(customBackground.x,customBackground.y+customBackground.radiusY*0.2f);
		customText1.setPosition(customBackground.x,customBackground.y-customBackground.radiusY*0.30f);
		customText2.setPosition(customBackground.x,customBackground.y-customBackground.radiusY*0.50f);
		customPrice.setPosition(customBackground.x,customBackground.y-customBackground.radiusY*0.75f);
		backButton.setPosition(x,y-radiusY+backButton.radiusY);
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
	public void start() {
		
	}
	
	@Override
	public void stop() {
		
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		chipCaseSprite.animate(delta);
		backButton.animate(delta);
		selectLabel.animate(delta);
		standardBackground.animate(delta);
		standardTitle.animate(delta);
		standardImage.animate(delta);
		standardText1.animate(delta);
		standardText2.animate(delta);
		standardPrice.animate(delta);
		
		customBackground.animate(delta);
		customTitle.animate(delta);
		customImage.animate(delta);
		customText1.animate(delta);
		customText2.animate(delta);
		customPrice.animate(delta);
		backButton.animate(delta);
	}
	
	public void show() {
		selectLabel.opacity=1;
		
		this.setDest(posOnscreen);
		
		standardBackground.setTouchable(true);
		customBackground.setTouchable(true);
		backButton.setTouchable(true);
	}
	
	private void hide() {
		standardBackground.setTouchable(false);
		customBackground.setTouchable(false);
		backButton.setTouchable(false);
	}
	
	public void back() {
		this.setDest(posStart);
		hide();
	}
	
	public void next() {
		this.setDest(posNext);
		hide();
	}

}