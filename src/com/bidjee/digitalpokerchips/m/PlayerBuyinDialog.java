package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class PlayerBuyinDialog extends DPCSprite {
	
	static final String BUYIN_TITLE = "Buy-In to ";
	
	public DPCSprite envelope=new DPCSprite();
	public TextLabel buyinLabel;
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
	
	private int amountInterval;
	private int amount;
	
	public PlayerBuyinDialog() {
		envelope.opacity=0;
		buyinLabel=new TextLabel("",0,false,0,false);
		buyinLabel.setFontFace("coolvetica_rg.ttf");
		buyinLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		amountTitleLabel=new TextLabel("Buy-In Amount",0.03f,true,0,false);
		amountTitleLabel.setFontFace("coolvetica_rg.ttf");
		amountTitleLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		minusButton=new Button(true,1,"");
		minusButton.setTouchAreaMultiplier(1.5f);
		plusButton=new Button(true,1,"");
		plusButton.setTouchAreaMultiplier(1.5f);
		amountNumberLabel=new TextLabel("",0,false,0,false);
		amountNumberLabel.setFontFace("coolvetica_rg.ttf");
		amountNumberLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		cancelButton=new Button(false,1,"CANCEL");
		cancelButton.getLabel().setFontFace("coolvetica_rg.ttf");
		cancelButton.getLabel().bodyColor=ForegroundLayer.brightestGoldColor;
		okayButton=new Button(false,1,"OKAY");
		okayButton.getLabel().setFontFace("coolvetica_rg.ttf");
		okayButton.getLabel().bodyColor=ForegroundLayer.brightestGoldColor;
		
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		margin=(int) (radiusY*0.1f);
		
		envelope.setDimensions((int)(radiusY*0.25f*1.5f),(int)(radiusY*0.25f));
		buyinLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.15f));
		buyinLabel.setTextSizeToMax(BUYIN_TITLE+"THEBESTGAMEEVA");
		
		minusButton.setDimensions((int)(radiusY*0.18f),(int)(radiusY*0.18f));
		plusButton.setDimensions((int)(radiusY*0.18f),(int)(radiusY*0.18f));
		amountBackground.setDimensions((int)(radiusX*0.70f),(int)(radiusY*0.25f));
		amountNumberLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.2f));
		amountNumberLabel.setTextSizeToMax("10000000000");
		amountTitleLabel.setMaxDimensions((int)(radiusX*0.6f),(int)(radiusY*0.08f));
		amountTitleLabel.setTextSizeToMax();
		
		cancelButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.2f),0.6f,0.7f);
		okayButton.setDimensions((int)(radiusX*0.5f),(int)(radiusY*0.2f),0.6f,0.7f);

	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		envelope.setPosition(x,y+radiusY*0.99f);
		// left aligned
		buyinLabel.setPosition(x,y+radiusY*0.47f);

		minusButton.setPosition(x-radiusX*0.8f,y-radiusY*0.01f);
		plusButton.setPosition(x+radiusX*0.8f,minusButton.y);
		amountBackground.setPosition(x,minusButton.y);
		amountNumberLabel.setPosition(x,minusButton.y);
		amountTitleLabel.setPosition(x,y-radiusY*0.35f);
		
		cancelButton.setPosition(x-radiusX*0.5f+1,y-radiusY+cancelButton.radiusY);
		okayButton.setPosition(x+radiusX*0.5f-1,y-radiusY+okayButton.radiusY);
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
	
	public void show(String tableName,int[] chipValues) {
		setTableName(tableName);
		int defaultBuyin = 8*chipValues[ChipCase.CHIP_A]+
				4*chipValues[ChipCase.CHIP_B]+
				2*chipValues[ChipCase.CHIP_C];
		amountInterval = chipValues[ChipCase.CHIP_A];
		setAmount(defaultBuyin);
		setPosition(posOffscreen);
		this.setDest(posOnscreen);
		
		minusButton.setTouchable(true);
		plusButton.setTouchable(true);
		cancelButton.setTouchable(true);
		okayButton.setTouchable(true);
		envelope.opacity = 1;
	}
	
	public void hide() {
		this.setDest(posOffscreen);
		minusButton.setTouchable(false);
		plusButton.setTouchable(false);
		cancelButton.setTouchable(false);
		okayButton.setTouchable(false);
	}
	
	public void increaseAmount() {
		setAmount(amount+amountInterval);
	}
	
	public void decreaseAmount() {
		if (amount-amountInterval>0) {
			setAmount(amount-amountInterval);
		}
	}
	
	private void setTableName(String tableName) {
		buyinLabel.setText(BUYIN_TITLE+tableName+" ?");
		buyinLabel.loadTexture();
	}
	
	private void setAmount(int amount) {
		this.amount = amount; 
		amountNumberLabel.setText("$ "+Integer.toString(amount));
		amountNumberLabel.loadTexture();
		
	}
	
	public int getAmount() {
		return amount;
	}

}