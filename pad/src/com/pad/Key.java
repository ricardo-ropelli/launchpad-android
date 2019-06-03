package com.pad;

import android.media.MediaPlayer;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class Key {
	
	private boolean gravou = false;
	private ImageButton key;
	private String path;
	private boolean loop = false;
	private Thread thread;
	private MediaPlayer player;
	private SeekBar volume;

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}	

	public boolean isLoop() {
		return loop;
	}
	
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	public Key(ImageButton b) {
		key = b;		
	}
	public void setKey(ImageButton key) {
		this.key = key;
	}
	
	public boolean getGravar() {
		return gravou;
	}
	public void setGravar(boolean gravou) {
		this.gravou = gravou;
	}

	public ImageButton getKey() {
		return key;
	}

	public MediaPlayer getPlayer() {
		return player;
	}

	public void setPlayer(MediaPlayer player) {
		this.player = player;
	}

	public SeekBar getVolume() {
		return volume;
	}

	public void setVolume(SeekBar volume) {
		this.volume = volume;
	}	
}
