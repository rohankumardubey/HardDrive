package game.engine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Singleton class that stores loaded game assets in memory
 */
public final class GameAssets {

  private static Map<String, BufferedImage> loadedImages = new HashMap<>();
  private static Map<String, Sound> loadedSounds         = new HashMap<>();

  /**
   * Use private constructor to make sure class cannot be instantiated
   */
  private GameAssets() {}

  /**
   * Load an image into in-memory cache
   *
   * @param name        Name of the image to load
   * @param jarPath     Path inside of the JAR file
   */
  public synchronized static void loadImage(String name, String jarPath) throws IOException {
    BufferedImage i = ImageIO.read(GameAssets.class.getResource(jarPath));
    loadedImages.put(name, i);
  }

  /**
   * Load a sound into in-memory cache
   *
   * @param name        Name of the sound to load
   * @param jarPath     Path inside of the JAR file
   */
  public synchronized static void loadSound(String name, String jarPath)
      throws IOException, UnsupportedAudioFileException, LineUnavailableException {

    AudioInputStream audioIn =
        AudioSystem.getAudioInputStream(GameAssets.class.getResource(jarPath));
    Clip clip = AudioSystem.getClip();
    clip.open(audioIn);

    Sound sound = new Sound(clip);
    loadedSounds.put(name, sound);
  }

  /**
   * Get a loaded image, or null if the image does not exist
   *
   * @param name    Image to get
   * @return        Loaded image or null
   */
  public synchronized static BufferedImage getLoadedImage(String name) {
    return loadedImages.get(name);
  }

  /**
   * Get a loaded sound, or null if the sound does not exist
   *
   * @param name  Sound to get
   * @return      Sound or null
   */
  public synchronized static Sound getLoadedSound(String name) {
    return loadedSounds.get(name);
  }
}
