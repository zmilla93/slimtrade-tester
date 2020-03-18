package com.slimtrade.core.audio;

import com.slimtrade.core.saving.SoundElement;

public class AudioManager {

	public static final int MIN_VOLUME = -30;
	public static final int MAX_VOLUME = 6;
	public static final int RANGE = Math.abs(MIN_VOLUME) + MAX_VOLUME;

	public static void play(SoundElement component) {
		if (component.volumeF <= AudioManager.MIN_VOLUME) {
			return;
		}
		AudioRunner runner = new AudioRunner(component.sound, component.volumeF);
		Thread thread = new Thread(runner);
		thread.start();
	}

	public static void playRaw(Sound sound, float volume) {
		if (volume <= AudioManager.MIN_VOLUME) {
			return;
		}
		AudioRunner runner = new AudioRunner(sound, volume);
		Thread thread = new Thread(runner);
		thread.start();
	}

}
