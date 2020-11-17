package game.entities.antivirus;

import game.engine.*;
import game.entities.BinaryExplosion;
import game.entities.Player;
import java.awt.*;

/**
 * Explosion from the booger class
 */
public class BoogerExplosion extends Entity {

  private final static int ANIMATION_SPEED      = 2;
  private final static int NUM_EXPLOSION_FRAMES = 4;

  // Timers
  private final static int ANIMATION_TIMER  = 0;
  private final static int RESET_TIMER      = 1;
  private final static int RESET_WAIT_TICKS = 50;

  public BoogerExplosion(Point2d position, Dimension size) {
    this.position.setLocation(position);

    // Load explosion sprites
    for (int i = 1; i <= NUM_EXPLOSION_FRAMES; i += 1) {
      this.sprite.addFrames(GameAssets.getLoadedImage("small-explosion-" + i));
    }

    this.sprite.size.setSize(size);
    this.mask            = sprite.getMask();
    this.drawingPriority = 140;
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
      if (this.sprite.getFrameIndex() == 0) {
        this.clearTimer(ANIMATION_TIMER);
        this.setTimer(RESET_TIMER, RESET_WAIT_TICKS, false);
        this.isVisible = false;
      }
    }

    if (timerIndex == RESET_TIMER) { this.destroy(); }
  }

  @Override
  protected void onStep() {
    testPlayerCollision();
  }

  private void testPlayerCollision() {
    Player player = this.getScene().findFirstEntity(Player.class);
    if (player == null) { return; }

    if (this.isCollidingWith(player)) {
      Sound hit = GameAssets.getLoadedSound("hit");
      if (!hit.isPlaying()) { hit.playSound(); }

      player.hit((int) Helpers.randomRange(2, 5));

      this.getScene().createEntity(
          new BinaryExplosion(this.position, this.sprite.getRotatedImageDimensions()));

      this.destroy();
    }
  }
}
