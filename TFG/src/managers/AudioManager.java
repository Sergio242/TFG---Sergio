package managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import game.Parametros;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {
	public static Music currentMusic;
	public static String currentMusicName = "";
	private static Map<String, Long> activeSounds = new HashMap<>();

	public static void playMusic(String path) {
		if (!currentMusicName.equals(path)) {
			if (currentMusic != null) {
				currentMusic.stop();
			}

			currentMusicName = path;
			currentMusic = ResourceManager.getMusic(path);
			currentMusic.setVolume(Parametros.musicVolume);
			currentMusic.setLooping(true);
			currentMusic.play();
		}
	}

	public static void playSound(String path) {
		Sound sound = ResourceManager.getSound(path);
		long soundId = sound.play(Parametros.soundVolume);
		activeSounds.put(path, soundId);
	}

	public static boolean isSoundPlaying(String path) {
		return activeSounds.containsKey(path);
	}

	public static void stopSpecificSound(String path) {
		if (activeSounds.containsKey(path)) {
			Sound sound = ResourceManager.getSound(path);
			sound.stop(activeSounds.get(path));
			activeSounds.remove(path);
		}
	}

	public static void stopAllSounds() {
		for (String path : activeSounds.keySet()) {
			Sound sound = ResourceManager.getSound(path);
			sound.stop(activeSounds.get(path));
		}
		activeSounds.clear();
	}

	public static void stopMusic() {
		if (currentMusic != null) {
			currentMusic.stop();
		}
	}

	public static void applyVolume() {
		if (currentMusic != null) {
			currentMusic.setVolume(Parametros.musicVolume);
		}
	}
}
