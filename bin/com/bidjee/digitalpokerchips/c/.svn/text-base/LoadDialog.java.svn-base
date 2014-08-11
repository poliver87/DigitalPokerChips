package com.bidjee.digitalpokerchips.c;

import com.bidjee.digitalpokerchips.m.Button;
import com.bidjee.digitalpokerchips.m.DPCSprite;
import com.bidjee.digitalpokerchips.m.Dialog;
import com.bidjee.digitalpokerchips.m.TextLabel;

public class LoadDialog extends Dialog {
	
	public int LOAD_SLOT_ROWS = 2;
	public int LOAD_SLOT_COLUMNS = 3;
	
	public TextLabel titleLabel;
	public Button okButton;
	public Button cancelButton;
	public LoadSlot[] loadSlots=new LoadSlot[Table.SAVE_NUM_SLOTS];
	
	public LoadDialog() {
		titleLabel=new TextLabel("Select a Table",0,true,0,false);
		okButton=new Button(true,0,"");
		okButton.opacity=0;
		cancelButton=new Button(true,0,"");
		cancelButton.opacity=0;
		for (int i=0;i<loadSlots.length;i++) {
			loadSlots[i]=new LoadSlot();
		}
	}
	
	@Override
	public void setDimensions(int radiusX,int radiusY) {
		super.setDimensions(radiusX,radiusY);
		titleLabel.setMaxDimensions((int)(radiusX*0.8f),(int)(radiusY*0.15f));
		titleLabel.setTextSizeToMax();
		okButton.setDimensions((int)(radiusY*0.15f),(int)(radiusY*0.15f));
		cancelButton.setDimensions((int)(radiusY*0.15f),(int)(radiusY*0.15f));
		for (int i=0;i<loadSlots.length;i++) {
			loadSlots[i].setDimensions((int)(radiusY*0.3f),(int)(radiusY*0.3f));
		}
	}
	
	@Override
	public void setPosition(float x,float y) {
		super.setPosition(x,y);
		titleLabel.setPosition(x,y+radiusY*0.86f);
		okButton.setPosition(x+radiusX*0.78f,y-radiusY*0.78f);
		cancelButton.setPosition(x-radiusX*0.78f,y-radiusY*0.78f);
		float xSlotPitch=radiusX*0.5f;
		float ySlotPitch=radiusY*0.6f;
		float xSlotStart=x-xSlotPitch;
		float ySlotStart=y+ySlotPitch*0.5f;
		for (int row=0;row<LOAD_SLOT_ROWS;row++) {
			for (int column=0;column<LOAD_SLOT_COLUMNS;column++) {
				loadSlots[row*LOAD_SLOT_COLUMNS+column].setPosition(xSlotStart+xSlotPitch*column, ySlotStart-ySlotPitch*row);
			}
		}
	}
	
	@Override
	public void scalePosition(float scaleX_,float scaleY_) {
		super.scalePosition(scaleX_,scaleY_);
		titleLabel.scalePosition(scaleX_,scaleY_);
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
	}
	
	@Override
	public void stop() {
		super.fadeOut();
		titleLabel.fadeOut();
		okButton.fadeOut();
		okButton.setTouchable(false);
		cancelButton.fadeOut();
		cancelButton.setTouchable(false);
		for (int i=0;i<loadSlots.length;i++) {
			loadSlots[i].fadeOut();
			loadSlots[i].setTouchable(false);
			loadSlots[i].setSelected(false);
		}
	}
	
	public void disappear() {
		titleLabel.fadeOut();
		titleLabel.opacity=0;
		okButton.fadeOut();
		okButton.opacity=0;
		cancelButton.fadeOut();
		cancelButton.opacity=0;
		for (int i=0;i<loadSlots.length;i++) {
			loadSlots[i].fadeOut();
			loadSlots[i].setOpacity(0);
			loadSlots[i].setTouchable(false);
			loadSlots[i].setSelected(false);
		}
	}
	
	@Override
	public void animate(float delta) {
		super.animate(delta);
		titleLabel.animate(delta);
		okButton.animate(delta);
		cancelButton.animate(delta);
		for (int i=0;i<loadSlots.length;i++) {
			loadSlots[i].animate(delta);
		}
	}
	
	public void slotSelected(int slot) {
		for (int i=0;i<loadSlots.length;i++) {
			loadSlots[i].setSelected(false);
		}
		loadSlots[slot].setSelected(true);
	}
	
	public void populateSlots(String[] tableNames) {
		for (int i=0;i<tableNames.length;i++) {
			if (tableNames[i]==null) {
				loadSlots[i].clearSlot(Integer.toString(i+1));
			} else {
				loadSlots[i].fillSlot(tableNames[i]);
			}
		}
	}
	
	public int getSelectedSlot() {
		int selectedSlot=-1;
		for (int i=0;i<loadSlots.length;i++) {
			if (loadSlots[i].selected) {
				selectedSlot=i;
				break;
			}
		}
		return selectedSlot;
	}
	
	public class LoadSlot extends DPCSprite {
		
		private boolean selected;
		
		public DPCSprite selectionHighlight=new DPCSprite();
		public TextLabel slotLabel;
		public DPCSprite tableIcon=new DPCSprite();
		
		public LoadSlot() {
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
				selectionHighlight.opacity=0.0f;
			}
		}
		
		public boolean getSelected() {
			return selected;
		}
		
		public void clearSlot(String slotText) {
			slotLabel.setText("");
			slotLabel.loadTexture();
			slotLabel.opacity=0;
			tableIcon.opacity=0;
			this.setTouchable(false);
		}
		
		public void fillSlot(String tableName) {
			this.setTouchable(true);
			slotLabel.setText(tableName);
			slotLabel.loadTexture();
			slotLabel.opacity=1;
			tableIcon.opacity=1;
		}
	}


}