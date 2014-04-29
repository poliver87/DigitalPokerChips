package com.bidjee.digitalpokerchips.m;


public class Card extends DPCSprite {

	
	public static final int HEARTS = 0;
	public static final int DIAMONDS = 1;
	public static final int CLUBS = 2;
	public static final int SPADES = 3;
	
	public int face;
	public int suit;
	
	public TextLabel faceLabel;
	
	public static int radiusX;
	public static int radiusY;
	public static int textSize;
	
	public Card(int face, int suit) {
		this.face = face;
		this.suit = suit;
		this.opacity=0;
		
		faceLabel=new TextLabel("A",0,false,0,false);
		faceLabel.bodyColor=TextLabel.blackColor;
		
		String faceString;
		if (face==9) {
			faceString="J";
		} else if (face==10) {
			faceString="Q";
		} else if (face==11) {
			faceString="K";
		} else if (face==12) {
			faceString="A";
		} else {
			faceString=Integer.toString(face+2);
		}
		faceLabel.setText(faceString);
	}
	        
	public int getFace() {
		return face;
	}
	public int getSuit() {
		return suit;
	}

	public float getRank() {
		return face+suit*0.1f;
	}
	
	public void loadLabel() {
		faceLabel.setTextSize(textSize);
		faceLabel.loadTexture();
	}

}
