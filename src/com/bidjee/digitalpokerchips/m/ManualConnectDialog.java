package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.Gdx;
import com.bidjee.digitalpokerchips.c.DPCGame;
import com.bidjee.digitalpokerchips.v.ForegroundRenderer;


public class ManualConnectDialog extends Dialog {
	
	private int typingFocus;
	
	public TextLabel titleLabel;
	public Button okButton;
	public Button cancelButton;
	
	public TextField[] ipQuads=new TextField[4];
	
	public ManualConnectDialog() {
		titleLabel=new TextLabel("Enter Table's IP Address",0,true,0,false);
		okButton=new Button(true,0,"");
		okButton.opacity=0;
		cancelButton=new Button(true,0,"");
		cancelButton.opacity=0;
		ipQuads[0]=new TextField("192",3,true,false);
		ipQuads[0].label.bodyColor=ForegroundRenderer.blackColor;
		ipQuads[1]=new TextField("168",3,true,false);
		ipQuads[1].label.bodyColor=ForegroundRenderer.blackColor;
		ipQuads[2]=new TextField("1",3,true,false);
		ipQuads[2].label.bodyColor=ForegroundRenderer.blackColor;
		ipQuads[3]=new TextField("1",3,true,false);
		ipQuads[3].label.bodyColor=ForegroundRenderer.blackColor;
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		titleLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.15f));
		titleLabel.setTextSizeToMax();
		okButton.setDimensions((int)(radiusY*0.15f),(int)(radiusY*0.15f));
		cancelButton.setDimensions((int)(radiusY*0.15f),(int)(radiusY*0.15f));
		for (int i=0;i<ipQuads.length;i++) {
			ipQuads[i].setDimensions((int)(radiusY*0.25f),(int)(radiusY*0.12f));
			ipQuads[i].label.setTextSizeToMax("999");
		}
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x,y);
		titleLabel.setPosition(x,y+radiusY*0.86f);
		okButton.setPosition(x+radiusX*0.78f,y-radiusY*0.78f);
		cancelButton.setPosition(x-radiusX*0.78f,y-radiusY*0.78f);
		float yIpQuads=y+radiusY*0.3f;
		float xPitchIpQuads=radiusY*0.6f;
		for (int i=0;i<ipQuads.length;i++) {
			ipQuads[i].setPosition(x-xPitchIpQuads*(1.5f-i),yIpQuads);
		}
	}
	
	@Override
	public void scalePosition(float scaleX,float scaleY) {
		super.scalePosition(scaleX,scaleY);
		titleLabel.scalePosition(scaleX,scaleY);
		okButton.scalePosition(scaleX,scaleY);
		cancelButton.scalePosition(scaleX,scaleY);
		ipQuads[0].scalePosition(scaleX,scaleY);
		ipQuads[1].scalePosition(scaleX,scaleY);
		ipQuads[2].scalePosition(scaleX,scaleY);
		ipQuads[3].scalePosition(scaleX,scaleY);
	}
	
	@Override
	public void start() {
		titleLabel.fadeIn();
		okButton.fadeIn();
		okButton.setTouchable(true);
		cancelButton.fadeIn();
		cancelButton.setTouchable(true);
		ipQuads[0].fadeIn();
		ipQuads[0].setTouchable(true);
		ipQuads[1].fadeIn();
		ipQuads[1].setTouchable(true);
		ipQuads[2].fadeIn();
		ipQuads[2].setTouchable(true);
		ipQuads[3].fadeIn();
		ipQuads[3].setTouchable(true);
		typingFocus=3;
		ipQuads[typingFocus].setFocus(true);
		Gdx.input.setOnscreenKeyboardVisible(true);
	}
	
	@Override
	public void stop() {
		super.fadeOut();
		titleLabel.fadeOut();
		okButton.fadeOut();
		okButton.setTouchable(false);
		cancelButton.fadeOut();
		cancelButton.setTouchable(false);
		ipQuads[0].fadeOut();
		ipQuads[0].setTouchable(false);
		ipQuads[1].fadeOut();
		ipQuads[1].setTouchable(false);
		ipQuads[2].fadeOut();
		ipQuads[2].setTouchable(false);
		ipQuads[3].fadeOut();
		ipQuads[3].setTouchable(false);
		Gdx.input.setOnscreenKeyboardVisible(false);
	}
	
	public void disappear() {
		titleLabel.opacity=0;
		okButton.opacity=0;
		cancelButton.opacity=0;
		ipQuads[0].opacity=0;
		ipQuads[1].opacity=0;
		ipQuads[2].opacity=0;
		ipQuads[3].opacity=0;
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		titleLabel.animate(delta);
		okButton.animate(delta);
		cancelButton.animate(delta);
		ipQuads[0].animate(delta);
		ipQuads[1].animate(delta);
		ipQuads[2].animate(delta);
		ipQuads[3].animate(delta);
	}

	public byte[] getHostBytes() {
		byte[] hostBytes=new byte[4];
		boolean validIP=true;
		try {
			hostBytes[0]=(byte) (Integer.parseInt(ipQuads[0].getText())&0xFF);
			hostBytes[1]=(byte) (Integer.parseInt(ipQuads[1].getText())&0xFF);
			hostBytes[2]=(byte) (Integer.parseInt(ipQuads[2].getText())&0xFF);
			hostBytes[3]=(byte) (Integer.parseInt(ipQuads[3].getText())&0xFF);
		} catch (NumberFormatException nfe) {
			validIP=false;
			Gdx.app.log(DPCGame.DEBUG_LOG_NETWORK_TAG,"ManualConnectDialog - getHostBytes() - invalid host bytes");
		}
		if (validIP) {
			return hostBytes;
		} else {
			return null;
		}
	}

	public void backspace() {
		ipQuads[typingFocus].backspace();
	}
	
	public void keyTyped(String chars) {
		ipQuads[typingFocus].append(chars);
	}

	public void ipQuadClicked(int i) {
		typingFocus=i;
		ipQuads[i].setFocus(true);
	}

}