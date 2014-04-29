package com.bidjee.digitalpokerchips.i;

import com.badlogic.gdx.graphics.Color;
import com.bidjee.digitalpokerchips.m.TextLabel;

public interface ITextFactory {
	int getMaxTextSize(TextLabel label);
	boolean isWithinBounds(String text,TextLabel label);
	public void createTextureForLabel(TextLabel label,Color color,Color outlineColor,boolean powerOfTwo);
	public void dispose(TextLabel label);
	public void dispose();
}
