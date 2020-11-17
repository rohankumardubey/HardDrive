package game.entities;

import game.engine.*;
import game.entities.component.*;
import game.entities.walls.*;
import game.scenes.GameScene;
import java.util.ArrayList;

/**
 * Rocket ship that you control
 */
public class Player extends HealthEntity {

  // Constants
  private static final int PLAYER_HEALTH   = 25;
  private static final double SPIN_SPEED   = (2 * Math.PI) / 40;
  private static final double ACCELERATION = 3.0;
  private static final double MAX_SPEED    = 30;
  private static final int MAX_DAMAGE      = 20;

  private Vector2d velocity;

  public Player(Point2d position) {
    super(PLAYER_HEALTH);

    // Initialize player size and sprite
    this.sprite.addFrames(GameAssets.getLoadedImage("car"));
    this.sprite.size.setSize(64, 32);
    this.sprite.setAngleDegrees(270);
    this.mask = sprite.getMask();

    // Position and velocity
    this.position.setLocation(position);
    this.velocity = new Vector2d();
  }

  @Override
  protected void onCreate() {}

  @Override
  protected void onDestroy() {
    GameAssets.getLoadedSound("small-explosion").playSound();
    this.getScene().createEntity(
        new PlayerExplosion(this.position, this.sprite.getRotatedImageDimensions()));
  }

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onStep() {
    super.onStep();

    readInput();
    movePlayer();
    applyDrag();

    testForWallCollision();
    testForComponentCollisions();
    testForBoundaryCollision();
  }

  /**
   * Read keyboard input and change the player velocity & direction
   */
  private void readInput() {
    Game game = this.getScene().getGame();
    if (game.isKeyPressed(Key.UP)) {
      this.velocity.add(Vector2d.fromPolarCoordinates(ACCELERATION, this.sprite.getAngleRadians()));
      this.velocity.setDistance(Math.min(MAX_SPEED, this.velocity.polarDistance()));
    }
    if (game.isKeyPressed(Key.DOWN)) {
      this.velocity.sub(Vector2d.fromPolarCoordinates(ACCELERATION, this.sprite.getAngleRadians()));
      this.velocity.setDistance(Math.min(MAX_SPEED, this.velocity.polarDistance()));
    }
    if (game.isKeyPressed(Key.LEFT)) { this.changeAngleByRadians(-SPIN_SPEED); }
    if (game.isKeyPressed(Key.RIGHT)) { this.changeAngleByRadians(SPIN_SPEED); }
  }

  /**
   * Rotate both the player and sprite by a given number of radians.
   *
   * Also updates the sprite mask
   */
  private void changeAngleByRadians(double radians) {
    this.sprite.addAngleRadians(radians);
    this.velocity.rotateBy(radians);
    this.mask = sprite.getMask();
  }

  /**
   * Move the player in the room using the velocity vector
   */
  private void movePlayer() {
    this.position.add(this.velocity);
  }

  /**
   * Apply friction (drag) to the player
   */
  private void applyDrag() {
    GameScene scene = (GameScene) this.getScene();
    this.velocity.scale(scene.getFriction());
  }

  private void testForWallCollision() {
    for (Wall wall: this.getScene().findEntities(Wall.class)) {
      if (this.isCollidingWith(wall)) { collideWithWall(); }
    }
  }

  private void testForComponentCollisions() {
    for (Component component: this.getScene().findEntities(Component.class)) {
      if (this.isCollidingWith(component)) { collideWithComponent(component); }
    }
  }

  private void testForBoundaryCollision() {
    Scene scene = this.getScene();
    if (this.position.x < 0 || this.position.x > scene.size.width || this.position.y < 0 ||
        this.position.y > scene.size.height) {
      this.collideWithWall();
    }
  }

  /**
   * Action that occurs when the player collides with a wall
   */
  private void collideWithWall() {
    GameAssets.getLoadedSound("hit").playSound();
    this.hit((int) Helpers.map(this.velocity.polarDistance(), 0, MAX_SPEED, 0, 5));
    this.position.sub(this.velocity);
    this.velocity.scale(-0.5);
  }

  /**
   * Action that occurs when the player collides with a component
   */
  private void collideWithComponent(Component component) {
    int damage = (int) Helpers.map(this.velocity.polarDistance(), 0, MAX_SPEED, 0, MAX_DAMAGE);

    int currentHealth = component.getHealth();
    component.hit(damage);
    int healthLost = currentHealth - component.getHealth();

    this.velocity.scale(1 - Helpers.map(healthLost, 0, MAX_DAMAGE, 0, 0.1));
    if (component.isDestroyed()) {
      GameAssets.getLoadedSound("large-explosion").playSound();
    } else {
      GameAssets.getLoadedSound("hit").playSound();
      this.hit((int) Helpers.map(healthLost, 0, MAX_DAMAGE, 0, 4));
      this.position.sub(this.velocity);
      this.velocity.scale(-0.5);
    }
  }
}
