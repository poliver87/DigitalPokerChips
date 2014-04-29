package com.bidjee.digitalpokerchips.s;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;


public class SoundFX {
	
	public Sound changeSound;
	public Sound checkSound;
	public Sound foldSound;
	public Sound clinkSound;
	public Sound chingSound;
	public Sound bellSound;
	
	public void loadSounds(AssetManager manager_) {
		changeSound=manager_.get("sound/change.wav",Sound.class);
		checkSound=manager_.get("sound/check.wav",Sound.class);
		foldSound=manager_.get("sound/fold.wav",Sound.class);
		clinkSound=manager_.get("sound/chip_clink.mp3",Sound.class);
		chingSound=manager_.get("sound/ching.mp3",Sound.class);
		bellSound=manager_.get("sound/bell.wav",Sound.class);
	}
	
	public void disposeSounds() {
		if (changeSound!=null)
			changeSound.dispose();
		changeSound=null;
		if (checkSound!=null)
			checkSound.dispose();
		checkSound=null;
		if (foldSound!=null)
			foldSound.dispose();
		foldSound=null;
		if (bellSound!=null)
			bellSound.dispose();
		bellSound=null;
	}
	
	public void dispose() {
		disposeSounds();
	}

}
