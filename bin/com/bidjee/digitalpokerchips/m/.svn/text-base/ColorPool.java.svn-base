package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.graphics.Color;

public class ColorPool {

	public static final Color[] colors = new Color[]{new Color(1f,0,0,1f),new Color(0,1f,0,1f),
										new Color(0,0,1f,1f),new Color(0,1f,1f,1f),
										new Color(1f,0,1f,1f),new Color(1f,1f,0,1f),
										new Color(1f,1f,1f,1f),new Color(0,0,0,1f),};
	
	static boolean[] assignment = new boolean[colors.length];
	
	public ColorPool() {}
	
	public static int assignColor() {
		int index=-1;
		for (int i=0;i<colors.length;i++) {
			if (!assignment[i]) {
				index=i;
				assignment[i]=true;
				break;
			}
		}
		return index;
	}
	
	public static void unassignColor(int index) {
		assignment[index]=false;
	}

	public static void unassignAll() {
		for (int i=0;i<colors.length;i++) {
			assignment[i]=false;
		}
	}

}
