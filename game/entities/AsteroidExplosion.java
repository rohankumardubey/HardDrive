package game.entities;

import game.engine.*;
import java.awt.*;

/**
 * Explosion that happens after asteroid is destroyed
 */
public class AsteroidExplosion extends Entity {

  private final static int ANIMATION_SPEED      = 2;
  private final static int NUM_EXPLOSION_FRAMES = 13;

  // Timers
  private final static int ANIMATION_TIMER = 0;

  public AsteroidExplosion(Point position, double scale) {
    this.position.setLocation(position);

    for (int i = 1; i <= NUM_EXPLOSION_FRAMES; i += 1) {
      this.sprite.addFrames(GameAssets.getLoadedImage("asteroid-explosion-" + i));
    }

    // Scale the sprite to the correct size
    this.sprite.size.width *= scale;
    this.sprite.size.height *= scale;
  }

  @Override
  protected void onCreate() {
    this.setTimer(ANIMATION_TIMER, ANIMATION_SPEED, true);
  }

  @Override
  protected void onDestroy() {}

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == ANIMATION_TIMER) {
      this.sprite.nextFrame();
      if (this.sprite.getFrameIndex() == 0) { this.destroy(); }
    }
  }

  @Override
  protected void onStep() {}
}
