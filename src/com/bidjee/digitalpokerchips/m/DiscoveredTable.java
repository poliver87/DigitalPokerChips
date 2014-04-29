package com.bidjee.digitalpokerchips.m;

public class DiscoveredTable {

	public static final int TIMEOUT_DURATION = 5000;
	
    private byte[] hostBytes;
    long lastTime;
    private String name;
    
    public int[] vals;
    
    public DiscoveredTable(byte[] hostBytes_,String tableName_,int[] vals) {
    	hostBytes=hostBytes_;
    	name=tableName_;
    	lastTime = System.currentTimeMillis();
    	this.vals=vals;
    }
    
    public byte[] getHostBytes() {
    	return hostBytes;
    }
    
    public String getName() {
    	return name;
    }

	public void reset() {
		hostBytes=null;
		name=null;
	}


    
}