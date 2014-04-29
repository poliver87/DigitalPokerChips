package com.bidjee.digitalpokerchips.m;

import com.bidjee.digitalpokerchips.c.WorldLayer;


public class AutosaveDialog extends Dialog {
	
	public int SAVE_SLOT_ROWS = 2;
	public int SAVE_SLOT_COLUMNS = 3;
	
	public TextLabel titleLabel;
	public Button okButton;
	public SaveSlot[] saveSlots=new SaveSlot[6];
	
	public AutosaveDialog() {
		titleLabel=new TextLabel("Select an Autosave Slot",0,true,0,false);
		okButton=new Button(true,0,"");
		okButton.opacity=0;
		for (int i=0;i<saveSlots.length;i++) {
			saveSlots[i]=new SaveSlot();
		}
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		titleLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.15f));
		titleLabel.setTextSizeToMax();
		okButton.setDimensions((int)(radiusY*0.15f),(int)(radiusY*0.15f));
		for (int i=0;i<saveSlots.length;i++) {
			saveSlots[i].setDimensions((int)(radiusY*0.3f),(int)(radiusY*0.3f));
		}
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x,y);
		titleLabel.setPosition(x,y+radiusY*0.86f);
		okButton.setPosition(x+radiusX*0.78f,y-radiusY*0.78f);
		float xSlotPitch=radiusX*0.5f;
		float ySlotPitch=radiusY*0.6f;
		float xSlotStart=x-xSlotPitch;
		float ySlotStart=y+ySlotPitch*0.5f;
		for (int row=0;row<SAVE_SLOT_ROWS;row++) {
			for (int column=0;column<SAVE_SLOT_COLUMNS;column++) {
				saveSlots[row*SAVE_SLOT_COLUMNS+column].setPosition(xSlotStart+xSlotPitch*column, ySlotStart-ySlotPitch*row);
			}
		}
	}
	
	@Override
	public void scalePosition(float scaleX_,float scaleY_) {
		super.scalePosition(scaleX_,scaleY_);
		titleLabel.scalePosition(scaleX_,scaleY_);
		okButton.scalePosition(scaleX_,scaleY_);
	}
	
	@Override
	public void start() {
		titleLabel.fadeIn();
		okButton.fadeIn();
		okButton.setTouchable(true);
		for (int i=0;i<saveSlots.length;i++) {
			saveSlots[i].fadeIn();
			saveSlots[i].setTouchable(true);
		}
	}
	
	@Override
	public void stop() {
		super.fadeOut();
		titleLabel.fadeOut();
		okButton.fadeOut();
		okButton.setTouchable(false);
		for (int i=0;i<saveSlots.length;i++) {
			saveSlots[i].fadeOut();
			saveSlots[i].setTouchable(false);
			saveSlots[i].setSelected(false);
		}
	}
	
	public void disappear() {
		titleLabel.fadeOut();
		titleLabel.opacity=0;
		okButton.fadeOut();
		okButton.opacity=0;
		for (int i=0;i<saveSlots.length;i++) {
			saveSlots[i].fadeOut();
			saveSlots[i].setOpacity(0);
			saveSlots[i].setTouchable(false);
			saveSlots[i].setSelected(false);
		}
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		titleLabel.animate(delta);
		okButton.animate(delta);
		for (int i=0;i<saveSlots.length;i++) {
			saveSlots[i].animate(delta);
		}
	}
	
	public void slotSelected(int slot) {
		for (int i=0;i<saveSlots.length;i++) {
			saveSlots[i].setSelected(false);
		}
		saveSlots[slot-1].setSelected(true);
	}
	
	public void populateSlots(String[] tableNames) {
		for (int i=0;i<tableNames.length;i++) {
			if (tableNames[i]==null) {
				saveSlots[i].clearSlot(Integer.toString(i+1));
			} else {
				saveSlots[i].fillSlot(tableNames[i]);
			}
		}
	}
	
	public class SaveSlot extends DPCSprite {
		
		private boolean selected;
		
		public DPCSprite selectionHighlight=new DPCSprite();
		public TextLabel slotLabel;
		public DPCSprite tableIcon=new DPCSprite();
		
		public SaveSlot() {
			slotLabel=new TextLabel("",0,false,0,false);
		}
		
		
		@Override
		public void setDimensions(int radiusX,int radiusY) {
			super.setDimensions(radiusX, radiusY);
			selectionHighlight.setDimensions(radiusX, radiusY);
			slotLabel.setMaxDimensions((int)(radiusX*1.2f),(int)(radiusY*0.3f));
			String tmp=slotLabel.getText();
			slotLabel.setText(WorldLayer.NAME_MEASURE);
			slotLabel.setTextSizeToMax();
			slotLabel.setText(tmp);
			tableIcon.setDimensions((int)(radiusY*0.8f),(int)(radiusY*0.4f));
		}
		
		@Override
		public void setPosition(float x,float y) {
			super.setPosition(x,y);
			selectionHighlight.setPosition(x,y);
			slotLabel.setPosition(x,y-radiusY*0.9f);
			tableIcon.setPosition(x, y);
		}
		
		@Override
		public void scalePosition(float scaleX,float scaleY) {
			super.scalePosition(scaleX, scaleY);
			selectionHighlight.scalePosition(scaleX,scaleY);
			slotLabel.scalePosition(scaleX,scaleY);
			tableIcon.scalePosition(scaleX,scaleY);
		}
		
		@Override
		public void animate(float delta) {
			super.animate(delta);
			selectionHighlight.animate(delta);
			slotLabel.animate(delta);
			tableIcon.animate(delta);
		}
		
		@Override
		public void fadeIn() {
			super.fadeIn();
			slotLabel.fadeIn();
		}
		
		@Override
		public void fadeOut() {
			super.fadeOut();
			selectionHighlight.fadeOut();
			slotLabel.fadeOut();
			tableIcon.fadeOut();
		}
		
		@Override
		public void setOpacity(float opacity) {
			super.setOpacity(opacity);
			selectionHighlight.setOpacity(opacity);
			slotLabel.setOpacity(opacity);
			tableIcon.setOpacity(opacity);
		}
		
		public void setSelected(boolean selected) {
			this.selected=selected;
			if (selected) {
				selectionHighlight.opacity=1;
			} else {
				selectionHighlight.opacity=0.3f;
			}
		}
		
		public boolean getSelected() {
			return selected;
		}
		
		public void clearSlot(String slotText) {
			slotLabel.setText("Slot "+slotText);
			slotLabel.loadTexture();
			tableIcon.opacity=0;
		}
		
		public void fillSlot(String tableName) {
			slotLabel.setText(tableName);
			slotLabel.loadTexture();
			tableIcon.opacity=1;
		}
	}

}


