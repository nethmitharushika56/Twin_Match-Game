package util;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Manages sound effects for the game
 */
public class SoundManager {
    private static boolean soundEnabled = true;
    
    // Simple beep sound for tile flip
    public static void playTileFlipSound() {
        if (!soundEnabled) return;
        playBeep(800, 100); // 800Hz for 100ms
    }
    
    // Success sound for match
    public static void playMatchSound() {
        if (!soundEnabled) return;
        playBeep(1200, 200); // Higher pitch for success
    }
    
    // Error sound for mismatch
    public static void playMismatchSound() {
        if (!soundEnabled) return;
        playBeep(400, 150); // Lower pitch for error
    }
    
    // Game over sound
    public static void playGameOverSound() {
        if (!soundEnabled) return;
        playBeep(600, 300);
    }
    
    // Button click sound
    public static void playButtonClickSound() {
        if (!soundEnabled) return;
        playBeep(1000, 50);
    }
    
    // Tile click sound
    public static void playTileClickSound() {
        if (!soundEnabled) return;
        playBeep(800, 80);
    }
    
    private static void playBeep(int frequency, int duration) {
        try {
            // Generate a simple sine wave
            byte[] data = new byte[duration * 8];
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (Math.sin(2 * Math.PI * frequency * i / 8000) * 127);
            }
            
            AudioFormat format = new AudioFormat(8000, 8, 1, true, false);
            AudioInputStream ais = new AudioInputStream(
                new ByteArrayInputStream(data), format, data.length);
            
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (Exception e) {
            // Silently ignore sound errors
        }
    }
    
    public static void setSoundEnabled(boolean enabled) {
        soundEnabled = enabled;
    }
    
    public static boolean isSoundEnabled() {
        return soundEnabled;
    }
} 