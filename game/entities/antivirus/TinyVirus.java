package game.entities.antivirus;

import game.engine.*;
import game.entities.*;
import game.scenes.GameScene;
import java.awt.image.*;

/**
 * Tiny virus that floats around the scene
 */
public class TinyVirus extends AntiVirus {

  private static final double TINY_VIRUS_SPEED = 80;
  private static final double TINY_VIRUS_DRAG  = 0.6;

  private Vector2d velocity;

  /// Uses initial velocity for explosion effect
  public TinyVirus(Point2d position, Vector2d initialVelocity) {
    super(1);

    this.sprite.addFrames(randomVirus());
    this.sprite.size.setSize(GameScene.TILE_SIZE, GameScene.TILE_SIZE);
    this.mask            = this.sprite.getMask();
    this.drawingPriority = 190;

    this.position.set(position);
    this.velocity = initialVelocity;
  }

  /**
   * Pick a random color for this virus
   */
  private BufferedImage randomVirus() {
    return GameAssets.getLoadedImage("virus-" + (int) Helpers.randomRange(1, 5));
  }

  @Override
  protected void onCreate() {}

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onStep() {
    super.onStep();

    moveTinyVirus();
    testForBoundaryCollision();
    testForPlayerCollision();
    applyDrag();
    pickNewDirection();
  }

  /**
   * Move the virus by applying the velocity vector
   */
  private void moveTinyVirus() {
    this.position.add(this.velocity);
  }

  /**
   * Bounce the virus off the edges of the boundary
   */
  private void testForBoundaryCollision() {
    Scene scene    = this.getScene();
    boolean bounce = false;
    if (this.position.x < 0 || this.position.x > scene.size.width) {
      this.velocity.x *= -1;
      bounce = true;
    }
    if (this.position.y < 0 || this.position.y > scene.size.height) {
      this.velocity.y *= -1;
      bounce = true;
    }

    if (bounce) { this.position.add(this.velocity); }
  }

  /**
   * Destroy the virus if colliding with the player
   */
  private void testForPlayerCollision() {
    for (Player player: this.getScene().findEntities(Player.class)) {
      if (this.isCollidingWith(player)) {
        player.hit((int) Helpers.randomRange(0, 1.5));
        this.destroy();
      }
    }
  }

  /**
   * Apply a drag coefficient to slow down the virus movement
   */
  private void applyDrag() {
    this.velocity.scale(TINY_VIRUS_DRAG);
  }

  /**
   * Pick a new direction if the speed is too slow
   */
  private void pickNewDirection() {
    if (this.velocity.polarDistance() >= 0.1) { return; }
    pointRandomly();
  }

  /**
   * Pick a random direction to move
   */
  private void pointRandomly() {
    Vector2d vector =
        Vector2d.fromPolarCoordinates(TINY_VIRUS_SPEED, Helpers.randomRange(0, 2 * Math.PI));
    this.velocity = vector;
  }
}
