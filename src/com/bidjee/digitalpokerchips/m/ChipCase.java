package com.bidjee.digitalpokerchips.m;

import com.badlogic.gdx.Gdx;

public class ChipCase {

	public static final int CHIP_A=0;
	public static final int CHIP_B=1;
	public static final int CHIP_C=2;
	public static final int CHIP_TYPES=3;
	
	public static final int[] legalValues={1,2,4,5,8,
		10,20,25,40,50,80,
		100,200,250,400,500,800,
		1000,2000,2500,4000,5000,8000,
		10000,20000,25000,40000,50000,80000,
		100000,200000,250000,400000,500000,800000};
	
	public static int[] values=new int[CHIP_TYPES];
	
	public ChipCase(int[] values_) {
		for (int i=0;i<values_.length;i++) {
			values[i]=values_[i];
		}
	}
	
	public static void setValue(int chipIndex_,int value_) {
		values[chipIndex_]=value_;
	}
	
	public void valueDown(int chipIndex_) {
		int valIndex_=0;
		// get the index of the current value
		for (;legalValues[valIndex_]!=values[chipIndex_];valIndex_++);
		// ensure we aren't at the minimum value
		if (valIndex_>chipIndex_) {
			valIndex_--;
			// check that it isn't now smaller than the one below its
			if (chipIndex_==0||(legalValues[valIndex_]>values[chipIndex_-1])) {
				setValue(chipIndex_,legalValues[valIndex_]);
			}
		}
		
	}
	
	public void valueUp(int chipIndex_) {
		int valIndex_=0;
		// get the index of the current value
		for (;legalValues[valIndex_]!=values[chipIndex_];valIndex_++);
		// ensure we aren't at the maximum value
		if (valIndex_<legalValues.length-(CHIP_TYPES-chipIndex_)) {
			valIndex_++;
			// check that it isn't now bigger thant he one above it
			if ((chipIndex_==CHIP_TYPES-1)||(legalValues[valIndex_]<values[chipIndex_+1])) {
				setValue(chipIndex_,legalValues[valIndex_]);
			}
		}
	}

	public static boolean checkValuesDivisibility() {
		boolean result=true;
		if (ChipCase.values[ChipCase.CHIP_C]%ChipCase.values[ChipCase.CHIP_B]!=0) {
			result=false;
		}
		if (ChipCase.values[ChipCase.CHIP_B]%ChipCase.values[ChipCase.CHIP_A]!=0) {
			result=false;
		}
		return result;
	}

	public static String valuesToString() {
		String output="";
		for (int i=0;i<CHIP_TYPES;i++) {
			output+="<VAL_"+Integer.toString(i)+">"+Integer.toString(values[i])+"<VAL_"+Integer.toString(i)+"/>";
		}
		return output;
	}
	
	public static void setValuesFromString(String input) {
		for (int i=0;i<CHIP_TYPES;i++) {
			String openTag="<VAL_"+Integer.toString(i)+">";
			String closeTag="<VAL_"+Integer.toString(i)+"/>";
			int startIndex=input.indexOf(openTag)+openTag.length();
			int endIndex=input.indexOf(closeTag);
			if (startIndex>0&&endIndex>startIndex) {
				setValue(i,Integer.parseInt(input.substring(startIndex,endIndex)));
			} else {
				Gdx.app.log("DPCError", "Couldn't set values from string");
			}
		}
	}
	
    public static int[] calculateSimplestBuild(int value_) {
    	int[] num_=new int[ChipCase.CHIP_TYPES];
    	while (value_>=ChipCase.values[ChipCase.CHIP_A]) {
    		for(int chipType=ChipCase.CHIP_TYPES-1;chipType>=0;chipType--) {
    			if (value_>=ChipCase.values[chipType]) {
    				num_[chipType]++;
    				value_-=ChipCase.values[chipType];
    				break;
    			}
    		}
    	}
    	return num_;
    }
	
}
