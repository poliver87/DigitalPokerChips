package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.math.Vector2;
import com.bidjee.digitalpokerchips.c.ForegroundLayer;

public class HostRearrangeDialog extends Dialog {
	
	public DPCSprite ringSprite;
	public DPCSprite lineSprite;
	public TextLabel nameLabel;
	public TextLabel rearrangeLabel;

	
	public Button doneButton;
	
	Vector2 posStart;
	Vector2 posOnscreen;
	
	public HostRearrangeDialog() {
		
		ringSprite=new DPCSprite();
		lineSprite=new DPCSprite();
		
		rearrangeLabel=new TextLabel("Arrange Player Positions",0,false,0,false);
		rearrangeLabel.setFontFace("coolvetica_rg.ttf");
		rearrangeLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		
		nameLabel=new TextLabel("",0,false,0,false);
		nameLabel.setFontFace("coolvetica_rg.ttf");
		nameLabel.bodyColor=ForegroundLayer.brightestGoldColor;
		
		doneButton=new Button(false,1,"DONE");
		doneButton.getLabel().setFontFace("coolvetica_rg.ttf");
		doneButton.getLabel().bodyColor = ForegroundLayer.brightestGoldColor;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		ringSprite.setDimensions((int)(radiusX*0.9f),(int)(radiusY*0.68f));
		lineSprite.setDimensions((int)(radiusX*0.7f),(int)(radiusY*0.01f));
		rearrangeLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.14f));
		rearrangeLabel.setTextSizeToMax();
		nameLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.28f));
		nameLabel.setTextSizeToMax("CoolGame");
		doneButton.setDimensions((int)(radiusX*1.0f),(int)(radiusY*0.3f),0.6f,0.7f);
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x, y);
		ringSprite.setPosition(x,y+radiusY*0.3f);
		lineSprite.setPosition(ringSprite.x,ringSprite.y);
		nameLabel.setPosition(x,y+radiusY*0.6f);
		rearrangeLabel.setPosition(x,y+radiusY*0.00f);
		doneButton.setPosition(x,y-radiusY+doneButton.radiusY);
	}
	
	public void setPositions(float xStart,float yStart,float xOnscreen,float yOnscreen) {		
		posStart=new Vector2(xStart,yStart);
		posOnscreen=new Vector2(xOnscreen,yOnscreen);
	}
	
	public void initialisePosition() {
		setPosition(posStart);
	}
	
	@Override
	public void scalePosition(float scaleX_,float scaleY_) {
		super.scalePosition(scaleX_,scaleY_);
	}
	
	@Override
	public void start() {}
	@Override
	public void stop() {}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		rearrangeLabel.animate(delta);
		nameLabel.animate(delta);
		doneButton.animate(delta);
	}
	
	public void show(String name) {
		nameLabel.setText(name);
		nameLabel.loadTexture();
		this.setDest(posOnscreen);
		nameLabel.opacity=1;
		doneButton.setTouchable(true);
		rearrangeLabel.startFlashing();
	}
	
	public void hide() {
		doneButton.setTouchable(false);
		rearrangeLabel.stopFlashing();
		rearrangeLabel.fadeOut();
		this.setDest(posStart);
	}

}