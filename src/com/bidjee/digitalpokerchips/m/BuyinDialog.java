package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class BuyinDialog extends DPCSprite {
	
	static final String BUYIN_TITLE = "Buy-In to ";
	
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
	
	private int amountInterval;
	private int amount;
	
	public BuyinDialog() {
		closeButton=new Button(true,0,"");
		buyinLabel=new TextLabel("",0,true,0,false);
		buyinLabel.setFontFace("coolvetica_rg.ttf");
		buyinLabel.bodyColor=ForegroundLayer.goldColor;
		amountTitleLabel=new TextLabel("Buy-In Amount",0.03f,true,0,false);
		amountTitleLabel.setFontFace("coolvetica_rg.ttf");
		amountTitleLabel.bodyColor=ForegroundLayer.goldColor;
		minusButton=new Button(true,1,"");
		minusButton.setTouchAreaMultiplier(1.5f);
		plusButton=new Button(true,1,"");
		plusButton.setTouchAreaMultiplier(1.5f);
		amountNumberLabel=new TextLabel("",0,false,0,false);
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
		
		envelope.setDimensions((int)(radiusY*0.18f*1.5f),(int)(radiusY*0.18f)); // 0.13 to 0.18
		buyinLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.3f));
		buyinLabel.setTextSizeToMax(BUYIN_TITLE+"THEBESTGAMEEVA");
		
		buyinFrameSprite.setDimensions((int)(radiusX*0.85f),(int)(radiusY*0.3f)); // 0.7 to 0.85
		amountTitleLabel.setMaxDimensions((int)(radiusX*0.6f),(int)(radiusY*0.08f)); //0.1 to 0.08
		amountTitleLabel.setTextSizeToMax();
		minusButton.setDimensions((int)(radiusY*0.13f),(int)(radiusY*0.13f));
		plusButton.setDimensions((int)(radiusY*0.13f),(int)(radiusY*0.13f));
		amountBackground.setDimensions((int)(radiusX*0.53f),(int)(radiusY*0.13f)); // 0.5 to 0.53
		amountNumberLabel.setMaxDimensions((int)(radiusX*0.4f),(int)(radiusY*0.1f));
		amountNumberLabel.setTextSizeToMax("10000000000");
		
		cancelButton.setDimensions((int)(radiusX*0.4f),(int)(radiusY*0.15f),0.7f,0.7f); //0.2 to 0.4 / 0.13 to 0.15
		okayButton.setDimensions((int)(radiusX*0.4f),(int)(radiusY*0.15f),0.7f,0.7f);

	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		closeButton.setPosition(x+radiusX*0.96f,y+radiusY*0.78f);
		envelope.setPosition(x,y+radiusY*0.78f);
		// left aligned
		buyinLabel.setPosition(x,y+radiusY*0.47f);
		
		buyinFrameSprite.setPosition(x,y);
		amountTitleLabel.setPosition(x,y+radiusY*0.2f);
		minusButton.setPosition(x-radiusX*0.65f,y-radiusY*0.05f); // 0.6 to 0.65 / 0.1 to 0.05
		plusButton.setPosition(x+radiusX*0.65f,minusButton.y);
		amountBackground.setPosition(x,minusButton.y);
		amountNumberLabel.setPosition(x,minusButton.y);
		
		cancelButton.setPosition(x-radiusX*0.45f,y-radiusY*0.55f); //0.7 to 55
		okayButton.setPosition(x+radiusX*0.45f,cancelButton.y);
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