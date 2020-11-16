package game.entities;

import game.engine.*;
import java.awt.*;

/**
 * Cool binary explosion effect
 */
public class BinaryExplosion extends Entity {

  private static final double NUM_SHRINK_FRAMES = 15;

  // Shrink the explosion
  private double shrinkWidth, shrinkHeight;
  private double currentWidth, currentHeight;

  public BinaryExplosion(Point2d position, Dimension size) {
    super();

    this.shrinkWidth   = size.width / NUM_SHRINK_FRAMES;
    this.shrinkHeight  = size.height / NUM_SHRINK_FRAMES;
    this.currentWidth  = size.getWidth();
    this.currentHeight = size.getHeight();

    for (int i = 1; i < 10; i += 1) {
      this.sprite.addFrames(GameAssets.getLoadedImage("binary-" + i));
    }
    this.sprite.size.setSize(size);

    this.position.set(position);
  }

  @Override
  protected void onCreate() {}

  @Override
  protected void onDestroy() {}

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onStep() {
    this.shrinkExplosion();
    this.sprite.nextFrame();

    // When to destroy the explosion?
    if (currentWidth <= 1 || currentHeight <= 1) {
      this.sprite.size.setSize(1, 1);
      this.destroy();
    }
  }

  /**
   * Make explosion get smaller in size
   */
  private void shrinkExplosion() {
    this.currentWidth -= shrinkWidth;
    this.currentHeight -= shrinkHeight;
    this.sprite.size.setSize(currentWidth, currentHeight);
  }
}
