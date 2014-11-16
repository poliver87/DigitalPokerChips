package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class BuyinDialog extends DPCSprite {
	
	public static final String BUYIN_TITLE = "BUY-IN TO ";
	
	public Button closeButton;
	public DPCSprite envelope=new DPCSprite();
	public TextLabel buyinLabel;
	public DPCSprite buyinFrameSprite=new DPCSprite();
	public TextLabel amountTitleLabel;
	public Button minusButton;
	public Button plusButton;
	public DPCSprite amountBackground=new DPCSprite();
	public TextLabel amountNumberLabel;
	public Button cancelButton;
	public Button okayButton;
	
	Vector2 posOffscreen;
	Vector2 posOnscreen;
	int margin;
	
	public BuyinDialog() {
		closeButton=new Button(true,0,"");
		buyinLabel=new TextLabel("",0,true,0,false);
		buyinLabel.setFontFace("coolvetica_rg.ttf");
		buyinLabel.bodyColor=ForegroundLayer.goldColor;
		amountTitleLabel=new TextLabel("Buy-In Amount",0.03f,true,0,false);
		amountTitleLabel.setFontFace("coolvetica_rg.ttf");
		amountTitleLabel.bodyColor=ForegroundLayer.goldColor;
		minusButton=new Button(true,1,"");
		plusButton=new Button(true,1,"");
		amountNumberLabel=new TextLabel("",0.03f,true,0,false);
		amountNumberLabel.setFontFace("coolvetica_rg.ttf");
		amountNumberLabel.bodyColor=ForegroundLayer.navyBlueColor;
		cancelButton=new Button(true,1,"CANCEL");
		cancelButton.getLabel().setFontFace("coolvetica_rg.ttf");
		cancelButton.getLabel().outline=true;
		cancelButton.getLabel().outlineColor=ForegroundLayer.darkRedColor;
		okayButton=new Button(true,1,"OKAY");
		okayButton.getLabel().setFontFace("coolvetica_rg.ttf");
		okayButton.getLabel().outline=true;
		okayButton.getLabel().outlineColor=ForegroundLayer.darkGreenColor;
		
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		margin=(int) (radiusY*0.1f);
		
		closeButton.setDimensions((int)(radiusY*0.15f),(int)(radiusY*0.15f));
		
		envelope.setDimensions((int)(radiusY*0.2f),(int)(radiusY*0.13f));
		buyinLabel.setMaxDimensions((int)(radiusX*0.6f),(int)(radiusY*0.11f));
		buyinLabel.setTextSizeToMax(BUYIN_TITLE+"THEBESTGAMEEVA");
		
		buyinFrameSprite.setDimensions((int)(radiusX*0.7f),(int)(radiusY*0.3f));
		amountTitleLabel.setMaxDimensions((int)(radiusX*0.6f),(int)(radiusY*0.11f));
		amountTitleLabel.setTextSizeToMax();
		minusButton.setDimensions((int)(radiusY*0.13f),(int)(radiusY*0.13f));
		plusButton.setDimensions((int)(radiusY*0.13f),(int)(radiusY*0.13f));
		amountBackground.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.13f));
		amountNumberLabel.setMaxDimensions((int)(radiusX*0.4f),(int)(radiusY*0.13f));
		amountNumberLabel.setTextSizeToMax("10000000");
		
		cancelButton.setDimensions((int)(radiusX*0.2f),(int)(radiusY*0.13f));
		okayButton.setDimensions((int)(radiusX*0.2f),(int)(radiusY*0.13f));

	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		closeButton.setPosition(x+radiusX*0.96f,y+radiusY*0.78f);
		envelope.setPosition(x-radiusX*0.8f,y+radiusY*0.8f);
		// left aligned
		buyinLabel.setPosition(envelope.x+envelope.radiusX+margin,envelope.y);
		
		buyinFrameSprite.setPosition(x,y);
		amountTitleLabel.setPosition(x,y+radiusY*0.2f);
		minusButton.setPosition(x-radiusX*0.5f,y-radiusY*0.78f);
		plusButton.setPosition(x+radiusX*0.5f,minusButton.y);
		amountBackground.setPosition(x,minusButton.y);
		amountNumberLabel.setPosition(x,minusButton.y);
		
		cancelButton.setPosition(x-radiusX*0.5f,y-radiusY*0.7f);
		okayButton.setPosition(x+radiusX*0.5f,cancelButton.y);
	}
	
	public void setPositions(float xOffscreen,float yOffscreen,float xOnscreen,float yOnscreen) {		
		posOffscreen=new Vector2(xOffscreen,yOffscreen);
		posOnscreen=new Vector2(xOnscreen,yOnscreen);
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
		closeButton.opacity=1;
		setPosition(posOffscreen);
		this.setDest(posOnscreen);
		
		closeButton.setTouchable(true);
		minusButton.setTouchable(true);
		plusButton.setTouchable(true);
		cancelButton.setTouchable(true);
		okayButton.setTouchable(true);
	}
	
	public void hide() {
		this.setDest(posOffscreen);
		closeButton.setTouchable(false);
		minusButton.setTouchable(false);
		plusButton.setTouchable(false);
		cancelButton.setTouchable(false);
		okayButton.setTouchable(false);
	}

}