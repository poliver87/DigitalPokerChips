package com.bidjee.digitalpokerchips.i;


public interface IActivity {
	
	public void makeToast(String msg);
	public void launchSettings();
	public ITextFactory getITextFactory();
	public IPlayerNetwork getIPlayerNetwork();
	public IHostNetwork getIHostNetwork();
	public ITableStore getITableStore();
	public IDPCSprite getHelpWebView();
	public int getScreenOrientation();
	public void performFacebookClick();
	public void brightenScreen();
	public void dimScreen();
}
