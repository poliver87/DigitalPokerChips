package com.bidjee.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.badlogic.gdx.Gdx;

public class Logger {
	
	public static boolean loggingOn=false;
	public static boolean loggingMasterOn=false;
	
	public static void log(String tag,String msg) {
		Gdx.app.log(tag,msg);
		if (loggingOn) {
			appendLog(tag,msg);
		}
		if (loggingMasterOn) {
			appendLog("DPCMaster",tag+": "+msg);
			Gdx.app.log("DPCMaster",tag+": "+msg);
		}
	}

	
	private static void appendLog(String fileName,String msg) {
	   File logFile = new File("sdcard/logs/"+fileName+".file");
	   if (!logFile.exists()) {
	      try {
	         logFile.createNewFile();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }
	   try {
	      BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
	      msg=getTimeStamp()+": "+msg;
	      buf.append(msg);
	      buf.newLine();
	      buf.close();
	   } catch (IOException e) {
	      e.printStackTrace();
	   }
	}
	
	private static String getTimeStamp() {
    	Calendar c = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	return sdf.format(c.getTime());
    }
}
