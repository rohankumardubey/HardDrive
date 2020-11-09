package game.engine;

import javax.sound.sampled.Clip;

/**
 * Wrapper to make sound object easier to use
 */
public class Sound {

  Clip clip;

  public Sound(Clip clip) {
    this.clip = clip;
  }

  /**
   * Play this sound.
   * If the sound is already playing, then the current sound is stopped.
   */
  public void playSound() {
    this.stopSound();

    this.clip.setFramePosition(0);
    this.clip.loop(0);
    this.clip.start();
  }

  /**
   * Play the sound, looping indefinitely.
   * If the sound is already playing, then the current sound is stopped.
   */
  public void loopSound() {
    this.stopSound();

    this.clip.setFramePosition(0);
    this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    this.clip.start();
  }

  /**
   * Stop playing the sound
   */
  public void stopSound() {
    if (this.clip.isActive()) { this.clip.stop(); }
  }

  /**
   * Test if the sound is currently playing or not
   */
  public boolean isPlaying() {
    return this.clip.isActive();
  }
}
