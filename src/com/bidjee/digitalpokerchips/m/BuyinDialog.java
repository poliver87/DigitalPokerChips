package com.bidjee.digitalpokerchips.m;

import com.bidjee.digitalpokerchips.c.WorldLayer;

public class BuyinDialog extends Dialog {
	
	static final int[] DEFAULT_CHIP_AMOUNTS={8,4,2};
	
	static final String TITLE_STRING = "Would you like to join ";
	
	public TextLabel titleLabel;
	public TextLabel instrLabel;
	public Button[] upArrows=new Button[ChipCase.CHIP_TYPES];
	public Button[] downArrows=new Button[ChipCase.CHIP_TYPES];
	public ChipStack[] chipStacks=new ChipStack[ChipCase.CHIP_TYPES];
	public TextLabel totalLabel;
	public TextLabel totalNumberLabel;
	public Button okButton;
	public Button cancelButton;
	
	public BuyinDialog() {
		titleLabel=new TextLabel("",0,true,0,false);
		instrLabel=new TextLabel("Set Buy-In Amount:",0,true,0,false);
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			upArrows[i]=new Button(true,0,"");
			upArrows[i].setTouchable(true);
			upArrows[i].setTouchAreaMultiplier(2f);
			downArrows[i]=new Button(true,0,"");
			downArrows[i].setTouchable(true);
			downArrows[i].setTouchAreaMultiplier(2f);
			chipStacks[i]=new ChipStack();
			chipStacks[i].add(i,DEFAULT_CHIP_AMOUNTS[i]);
			chipStacks[i].totalLabel.opacity=0;
		}
		totalLabel=new TextLabel("Total Buy-In:",0,true,0,false);
		totalNumberLabel=new TextLabel("",0,true,0,false);
		okButton=new Button(true,0,"");
		okButton.opacity=0;
		cancelButton=new Button(true,0,"");
		cancelButton.opacity=0;
	}
	
	@Override
	public void setDimensions(int radiusX_,int radiusY_) {
		super.setDimensions(radiusX_,radiusY_);
		titleLabel.setMaxDimensions((int)(radiusX_*0.8f),(int)(radiusY_*0.15f));
		titleLabel.setText(TITLE_STRING+WorldLayer.NAME_MEASURE);
		titleLabel.setTextSizeToMax();titleLabel.setText("");
		instrLabel.setMaxDimensions((int)(radiusX_*0.8f),(int)(radiusY_*0.1f));
		instrLabel.setTextSizeToMax();
		
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			upArrows[i].setDimensions((int)(radiusY_*0.12f),(int)(radiusY_*0.12f));
			downArrows[i].setDimensions((int)(radiusY_*0.12f),(int)(radiusY_*0.12f));
			chipStacks[i].setMaxRenderNum(22);
			chipStacks[i].scaleLabel();
			chipStacks[i].radiusX=(int)(radiusY_*0.2f);
			chipStacks[i].radiusY=(int)(radiusY_*0.2f);
		}
		totalLabel.setMaxDimensions((int)(radiusX_*0.4f),(int)(radiusY_*0.12f));
		totalLabel.setTextSizeToMax();
		totalNumberLabel.setTextSize(totalLabel.getTextSize());
		okButton.setDimensions((int)(radiusY_*0.15f),(int)(radiusY_*0.15f));
		cancelButton.setDimensions((int)(radiusY_*0.15f),(int)(radiusY_*0.15f));
	}
	
	@Override
	public void setPosition(float x_,float y_) {
		super.setPosition(x_,y_);
		titleLabel.setPosition(x_,y_+radiusY*0.86f);
		instrLabel.setPosition(x_,y_+radiusY*0.70f);
		float xValueSpacing_=radiusX*0.5f;
		float xValueStart_=x_-xValueSpacing_;
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			upArrows[i].setPosition(xValueStart_+i*xValueSpacing_,y_+radiusY*0.5f);
			downArrows[i].setPosition(xValueStart_+i*xValueSpacing_,y_-radiusY*0.5f);
			chipStacks[i].setPosition(xValueStart_+i*xValueSpacing_,y_-radiusY*0.13f,0);
		}
		totalLabel.setPosition(x_-radiusX*0.3f,y_-radiusY*0.8f);
		totalNumberLabel.setPosition(x_+radiusX*0.3f,totalLabel.y);
		okButton.setPosition(x_+radiusX*0.78f,y_-radiusY*0.78f);
		cancelButton.setPosition(x_-radiusX*0.78f,y_-radiusY*0.78f);
	}
	
	@Override
	public void scalePosition(float scaleX_,float scaleY_) {
		super.scalePosition(scaleX_,scaleY_);
		titleLabel.scalePosition(scaleX_,scaleY_);
		instrLabel.scalePosition(scaleX_,scaleY_);
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			upArrows[i].scalePosition(scaleX_,scaleY_);
			downArrows[i].scalePosition(scaleX_,scaleY_);
			chipStacks[i].scalePosition(scaleX_,scaleY_);
		}
		totalLabel.scalePosition(scaleX_,scaleY_);
		totalNumberLabel.scalePosition(scaleX_,scaleY_);
		okButton.scalePosition(scaleX_,scaleY_);
		cancelButton.scalePosition(scaleX_,scaleY_);
	}
	
	@Override
	public void start() {
		titleLabel.fadeIn();
		okButton.fadeIn();
		okButton.setTouchable(true);
		cancelButton.fadeIn();
		cancelButton.setTouchable(true);
		instrLabel.fadeIn();
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			upArrows[i].fadeIn();
			upArrows[i].setTouchable(true);
			downArrows[i].fadeIn();
			downArrows[i].setTouchable(true);
			chipStacks[i].totalLabel.fadeIn();
			chipStacks[i].updateTotalLabel();
		}
		totalLabel.fadeIn();
		totalNumberLabel.fadeIn();
		updateBuyinTotal();
	}
	
	@Override
	public void stop() {
		super.fadeOut();
		titleLabel.fadeOut();
		instrLabel.fadeOut();
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			upArrows[i].fadeOut();
			upArrows[i].setTouchable(false);
			downArrows[i].fadeOut();
			downArrows[i].setTouchable(false);
			chipStacks[i].totalLabel.fadeOut();
		}
		totalLabel.fadeOut();
		totalNumberLabel.fadeOut();
		okButton.fadeOut();
		okButton.setTouchable(false);
		cancelButton.fadeOut();
		cancelButton.setTouchable(false);
	}
	
	public void disappear() {
		titleLabel.opacity=0;
		instrLabel.opacity=0;
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			upArrows[i].opacity=0;
			downArrows[i].opacity=0;
			chipStacks[i].totalLabel.opacity=0;
		}
		totalLabel.opacity=0;
		totalNumberLabel.opacity=0;
		okButton.opacity=0;
		cancelButton.opacity=0;
	}
	
	@Override
	public void animate(float delta_) {
		super.animate(delta_);
		titleLabel.animate(delta_);
		instrLabel.animate(delta_);
		for (int i=0;i<ChipCase.CHIP_TYPES;i++) {
			upArrows[i].animate(delta_);
			downArrows[i].animate(delta_);
			chipStacks[i].totalLabel.animate(delta_);
		}
		totalLabel.animate(delta_);
		totalNumberLabel.animate(delta_);
		okButton.animate(delta_);
		cancelButton.animate(delta_);
		
	}
	
	private void updateBuyinTotal() {
		int buyinTotal_=0;
		for (int chip_=0;chip_<ChipCase.CHIP_TYPES;chip_++) {
			buyinTotal_+=chipStacks[chip_].value();
		}
		totalNumberLabel.setText(Integer.toString(buyinTotal_));
		totalNumberLabel.loadTexture();
	}

	public void amountUp(int chipIndex_) {
		chipStacks[chipIndex_].add(chipIndex_,1);
		chipStacks[chipIndex_].updateTotalLabel();
		updateBuyinTotal();
	}
	
	public void amountDown(int chipIndex_) {
		if (chipStacks[chipIndex_].size()>0&&addTotalChips()>1) {
			chipStacks[chipIndex_].removeLast();
			chipStacks[chipIndex_].updateTotalLabel();
			updateBuyinTotal();
		}
	}
	
	public int[] getStartBuild() {
		int[] build_=new int[ChipCase.CHIP_TYPES];
		for (int i=0;i<build_.length;i++) {
			build_[i]=chipStacks[i].size();
		}
		return build_;
	}
	
	private int addTotalChips() {
		int total_=0;
		for (int chipStack_=0;chipStack_<ChipCase.CHIP_TYPES;chipStack_++) {
			total_+=chipStacks[chipStack_].size();
		}
		return total_;
	}

	public void setTableName(String tableName) {
		titleLabel.setText(TITLE_STRING+tableName+"?");
		titleLabel.loadTexture();
	}

}
