package game.entities;

import game.engine.*;
import game.resources.Score;

/**
 * Asteroid object in space
 */
public class Asteroid extends Entity {

  private static final int BOUNDARY = 120; /* Boundary before asteroid respawns */

  // Generation parameters
  private static final double MIN_SCALE          = 0.25;
  private static final double MAX_SCALE          = 1.5;
  private static final int MIN_HEALTH            = 2;
  private static final int MAX_HEALTH            = 8;
  private static final double MIN_SPINNING_SPEED = -2.0;
  private static final double MAX_SPINNING_SPEED = 2.0;
  private static final double MIN_ANGLE_RAD      = Math.PI * 3 / 4;
  private static final double MAX_ANGLE_RAD      = Math.PI * 5 / 4;
  private static final double MIN_SPEED          = 1.0;
  private static final double MAX_SPEED          = 8.0;

  // Timers
  private static final int ROTATION_TIMER       = 0;
  private static final int ROTATION_TIMER_SPEED = 1;

  private double scale;
  private int health;
  private double spinningSpeed;
  private double dx, dy;

  public Asteroid() {
    super();

    this.sprite.addFrames(GameAssets.getLoadedImage("asteroid"));
    this.mask = this.sprite.getMask();
  }

  @Override
  protected void onCreate() {
    this.reset();
    this.setTimer(ROTATION_TIMER, ROTATION_TIMER_SPEED, true);
  }

  /**
   * Reset the asteroid's parameters without creating a new asteroid
   */
  private void reset() {
    this.randomSizeSpeedAndHealth();
    this.randomOrientationAndSpin();
    this.randomMovement();
    this.randomStartingLocation();
  }

  private void randomSizeSpeedAndHealth() {
    this.sprite.autoSize();

    this.scale = Helpers.randomRange(MIN_SCALE, MAX_SCALE);
    this.sprite.size.width *= scale;
    this.sprite.size.height *= scale;

    // Update mask to match size
    this.sprite.angleRadians = 0.0;
    this.mask                = sprite.getMask();

    // Make speed based on the scale factor
    this.spinningSpeed = Helpers.map(MAX_SCALE - scale, MIN_SCALE, MAX_SCALE, MIN_SPINNING_SPEED,
                                     MAX_SPINNING_SPEED);

    // Make health based on the scale factor
    this.health = (int) Helpers.map(scale, MIN_SCALE, MAX_SCALE, MIN_HEALTH, MAX_HEALTH);
  }

  private void randomOrientationAndSpin() {
    this.sprite.angleRadians = Math.random() * Math.PI * 2;
  }

  private void randomMovement() {
    /// Some % of asteroids should not move
    if (shouldMove()) {
      double angle = Helpers.randomRange(MIN_ANGLE_RAD, MAX_ANGLE_RAD);
      double speed = Helpers.randomRange(MIN_SPEED, MAX_SPEED);

      this.dx = speed * Math.cos(angle);
      this.dy = -1 * speed * Math.sin(angle);
    } else {
      this.dx = 0;
      this.dy = 0;
    }
  }

  private static boolean shouldMove() {
    return Math.random() * 10 > 2;
  }

  private void randomStartingLocation() {
    Scene scene = this.getScene();

    // Start off screen
    this.position.x = scene.mainView.position.x + scene.mainView.size.width + 50;
    this.position.y = (int) (Math.random() * scene.mainView.size.height);
  }

  @Override
  protected void onDestroy() {}

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == ROTATION_TIMER) { this.sprite.addAngleDegrees(this.spinningSpeed); }
  }

  @Override
  protected void onStep() {
    this.moveAsteroid();
    this.testIfOutsideView();
    this.testBulletCollision();
    this.testAsteroidDestroyed();
  }

  private void moveAsteroid() {
    this.position.x += dx;
    this.position.y += dy;
  }

  private void testIfOutsideView() {
    Scene scene = this.getScene();

    int topBoundary    = scene.mainView.getTopBoundary() - BOUNDARY;
    int bottomBoundary = scene.mainView.getBottomBoundary() + BOUNDARY;
    int leftBoundary   = scene.mainView.getLeftBoundary() - BOUNDARY;

    if (this.position.y < topBoundary || this.position.y > bottomBoundary ||
        this.position.x < leftBoundary) {
      this.reset();
    }
  }

  private void testBulletCollision() {
    for (Bullet bullet: this.getScene().findEntities(Bullet.class)) {
      if (this.isCollidingWith(bullet)) {
        GameAssets.getLoadedSound("asteroid-hit").playSound();
        bullet.destroy();
        this.health -= 1;

        Score score = this.getScene().getGame().getResouce(Score.class);
        score.asteroidHit();
      }
    }
  }

  private void testAsteroidDestroyed() {
    if (this.health <= 0) {
      GameAssets.getLoadedSound("asteroid-explosion").playSound();
      this.createExplosion();
      this.reset();

      Score score = this.getScene().getGame().getResouce(Score.class);
      score.asteroidDestroyed();
    }
  }

  public void createExplosion() {
    this.getScene().createEntity(new AsteroidExplosion(this.position, this.scale));
  }
}
