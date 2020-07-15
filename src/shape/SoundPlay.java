package shape;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlay extends Thread {

	private File soundFile;
	private AudioInputStream ais;
	private Clip clip;
	private boolean stop;
	
	public SoundPlay(File soundFile) {
		this.soundFile = soundFile;
		
		this.stop = false;
		
		try {
			ais = AudioSystem.getAudioInputStream(this.soundFile);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	}
	public void initialize() {
		
	}
	public void run() {
		try {
			while(!stop) {
				
				clip = AudioSystem.getClip();
				clip.open(ais);
				clip.start();
			}
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
		
	}
	public void stop(boolean stop) {
		this.stop = stop;
	}
	public void finalize() {
		try {
			this.ais.close();
			this.clip = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
