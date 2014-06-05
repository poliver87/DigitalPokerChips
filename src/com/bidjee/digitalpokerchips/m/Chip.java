package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.Gdx;

public class Chip implements Comparable<Chip> {
	
	public static final int animationMargin=1;
	
	public static final int CHIP_ROTATION_0 = 0;
	public static final int CHIP_ROTATION_135 = 1;
	public static final int CHIP_ROTATION_202 = 2;
	public static final int CHIP_ROTATION_N = 3;
	
	public static final float perspectiveGradient=0.012f;
	public static final float Z_Y_OFFSET_RATIO=0.045f;
	public static final float Z_X_OFFSET_RATIO=0.04f;
	
	
	public static int radiusX;
	public static int radiusY;
	
	public int chipType;
	
	public float x;
	public float y;
	public float z;
	public float destX;
	public float destY;
	public float destZ;
	public boolean isTouched;
	public Seat destSeat;
	public float rotation;
	public boolean pooling;
	public int imgRotation;
	
	public Chip(int chipType_,float x_,float y_,float z_) {
		chipType=chipType_;
		if (chipType==ChipCase.CHIP_A||chipType==ChipCase.CHIP_B||chipType==ChipCase.CHIP_C) {} else {
			Gdx.app.debug("Chip.java","Invalid Chip Type");
		}
		x=x_;
		y=y_;
		z=z_;
		destX=x_;
		destY=y_;
		destZ=z_;
		isTouched=false;
		destSeat=null;
		pooling=false;
		imgRotation=(int) (Math.random()*2.99);
	}

	public boolean animateToDest(float delta) {
		boolean isAtDest=false;
		if (Math.abs(y-destY)<animationMargin&&
				Math.abs(x-destX)<animationMargin&&
				Math.abs(z-destZ)<animationMargin) {
			isAtDest=true;
			setXYZtoDest();
		} else {
			float timeFactor = delta*9;
			if (isTouched) {
				timeFactor*=3;
			}
			timeFactor=Math.min(timeFactor, 1);
			float xDelta=timeFactor*(destX-x);
			float yDelta=timeFactor*(destY-y);
			double dist=Math.sqrt(xDelta*xDelta+yDelta*yDelta);
			if (dist>radiusX&&chipType==0) {
				double scale=radiusX/dist;
				xDelta*=scale;
				yDelta*=scale;
			}
			y=(float)(y+yDelta);
			x=(float)(x+xDelta);
			z=(float)(z-timeFactor*(z-destZ));
		}
		return isAtDest;
	}
	
	public boolean checkAtDest() {
		if (Math.abs(y-destY)<animationMargin&&
				Math.abs(x-destX)<animationMargin&&
				Math.abs(z-destZ)<animationMargin) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean pointContained(float touchX_,float touchY_) {
		boolean contained_=false;
		float radiusXChip_=radiusX*(1+z*perspectiveGradient);
		float radiusYChip_=radiusY*(1+z*perspectiveGradient);
		int xCoeff_=0;
		int yCoeff_=0;
		if (rotation==0||rotation==360) {
			yCoeff_=1;
		} else if (rotation==-90||rotation==270) {
			xCoeff_=1;
		} else if (rotation==180||rotation==-180) {
			yCoeff_=-1;
		} else if (rotation==90||rotation==-270) {
			xCoeff_=-1;
		}
		float zyOffset_=Z_Y_OFFSET_RATIO*radiusY*z*(1+0.5f*perspectiveGradient*(z-1));
		float deltaTouchXA_=touchX_-(x+xCoeff_*zyOffset_);
		float deltaTouchYA_=touchY_-(y+yCoeff_*zyOffset_);
		float xDist_=Math.abs(deltaTouchXA_)/radiusXChip_;
		float yDist_=Math.abs(deltaTouchYA_)/radiusYChip_;
		float totalDistA = (float) (xDist_*xDist_+yDist_*yDist_);
		if (totalDistA < 1) {
			contained_=true;
		}
		return contained_;
	}
	
	public void scalePosition(float scaleX_,float scaleY_) {
		x*=scaleX_;
		y*=scaleY_;
		destX*=scaleX_;
		destY*=scaleY_;
	}

	public float getProjectedY() {
		int rotationCoeff=0;
		if (rotation==0||rotation==360) {
			rotationCoeff=1;
		} else if (rotation==180||rotation==-180) {
			rotationCoeff=-1;
		}
		return y+rotationCoeff*Z_Y_OFFSET_RATIO*radiusY*z*(1+0.5f*perspectiveGradient*(z-1));
	}
	
	public float getProjectedX() {
		int rotationCoeff=0;
		if (rotation==-90||rotation==270) {
			rotationCoeff=1;
		} else if (rotation==90||rotation==-270) {
			rotationCoeff=-1;
		}
		return x+rotationCoeff*Z_Y_OFFSET_RATIO*radiusY*z*(1+0.5f*perspectiveGradient*(z-1));
	}
	
	
	public void setDest(float x_,float y_,float z_) {
		destX=x_;
		destY=y_;
		destZ=z_;
	}
	public void setDestZ(float z_) {
		destZ=z_;
	}
	public float getDestZ() {return destZ;}
	public void setXYZtoDest() {
		x=destX;
		y=destY;
		z=destZ;
	}
	public int getProjectedRadiusX() {return (int) (radiusX*(1+z*Chip.perspectiveGradient));}
	public int getProjectedRadiusY() {return (int) (radiusY*(1+z*Chip.perspectiveGradient));}
	public static int getProjectedRadiusX(float z_) {return (int) (radiusX*(1+z_*Chip.perspectiveGradient));}
	public static int getProjectedRadiusY(float z_) {return (int) (radiusY*(1+z_*Chip.perspectiveGradient));}
	public void setProjectedY(float projY_) {y=projY_-Z_Y_OFFSET_RATIO*radiusY*z*(1+0.5f*perspectiveGradient*(z-1));}
	public static int projectRadius(int radius_,float z_) {return (int) (radius_*(1+z_*Chip.perspectiveGradient));}
	public static float getProjectedDelta(float z_) {return Z_Y_OFFSET_RATIO*radiusY*z_*(1+0.5f*perspectiveGradient*(z_-1));}
	public static float getProjectedDelta(float z_,int radiusY_) {
		return Z_Y_OFFSET_RATIO*radiusY_*z_*(1+0.5f*perspectiveGradient*(z_-1));
	}

	@Override
	public String toString() {
		String str_="";
		if (chipType==ChipCase.CHIP_A) {
			str_+="A";
		} else if (chipType==ChipCase.CHIP_B) {
			str_+="B";
		} else if (chipType==ChipCase.CHIP_C) {
			str_+="C";
		}
		str_+=Integer.toString(imgRotation);
		return str_;
	}
	
	static Chip parseChip(String chipStr_) {
		Chip chip_=null;
		char char_=chipStr_.charAt(0);
		if (char_=='A') {
			chip_=new Chip(ChipCase.CHIP_A,0,0,0);
		} else if (char_=='B') {
			chip_=new Chip(ChipCase.CHIP_B,0,0,0);
		} else if (char_=='C') {
			chip_=new Chip(ChipCase.CHIP_C,0,0,0);
		}
		chip_.imgRotation=Integer.parseInt(chipStr_.substring(1));
		return chip_;
	}

	@Override
	public int compareTo(Chip o) {
		if (this.z>o.z) {
			return 1;
		} else if (this.z<o.z) {
			return -1;
		} else {
			if (this.y<o.y) {
				return 1;
			} else if (this.y>o.y) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	public static float testOverlap(float x1,float y1,float z1,float x2,float y2,float z2) {
		float yOverlap=0;
		y1+=Chip.getProjectedDelta(z1);
		y2+=Chip.getProjectedDelta(z2);
		float radiusX1=Chip.getProjectedRadiusX(z1);
		float radiusY1=Chip.getProjectedRadiusY(z1);
		float radiusX2=Chip.getProjectedRadiusX(z2);
		float radiusY2=Chip.getProjectedRadiusY(z2);
		
		radiusY2=(Chip.getProjectedRadiusY(z2)*(1-Chip.Z_Y_OFFSET_RATIO));
		if (Math.abs(x1-x2)<radiusX1+radiusX2) {
			float dxR1=Math.abs(x1-x2)*radiusX2/(radiusX1+radiusX2);
			float dyR1=(float) (Math.sqrt(((radiusX2*radiusX2-dxR1*dxR1)*radiusY2*radiusY2)/(radiusX2*radiusX2)));
			float dyR2=dyR1*(radiusY1/radiusY2);
			float dyR=dyR1+dyR2;
			float dy12=Math.abs(y1-y2);
			if (dy12<=dyR) {
				yOverlap=(y1<y2)?dyR-dy12:dy12-dyR;
			}
		}
		return yOverlap;
	}
	
}
