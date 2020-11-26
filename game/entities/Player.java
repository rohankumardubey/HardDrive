package game.entities;

import game.engine.*;
import game.entities.walls.*;

/**
 * Car that you control
 */
public class Player extends PhysicsEntity {

  // Constants
  private static final int PLAYER_HEALTH   = 100;
  private static final double TURN_RADIUS  = 120;
  private static final double ACCELERATION = 3.0;
  public static final double MAX_SPEED     = 30;
  private static final double PLAYER_MASS  = 5;
  private static Vector2d DRIFT_REC;

  private double driftBoost = 0;

  private boolean collidedWithWall;

  public Player(Point2d position) {
    super(PLAYER_HEALTH, new Vector2d(0.25, 50), PLAYER_MASS);

    // Initialize player size and sprite
    this.sprite.addFrames(GameAssets.getLoadedImage("car"));
    this.sprite.size.setSize(64, 32);
    this.sprite.setAngleDegrees(270);
    this.mask = sprite.getMask();

    // Position, mass, and weight
    this.position.setLocation(position);
    this.angle = Math.toRadians(270.0);
    this.mass  = 10;

    // How quickly the car recovers from drift
    this.DRIFT_REC = new Vector2d(this.masterFrictionCoefficient);
    this.DRIFT_REC.scaleBy(0.0125);
  }

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
    move();
    testForWallCollision();
  }

  /**
   * Read keyboard input and change the player velocity and direction
   */
  private void move() {
    Game game = this.getScene().getGame();
    // get inputs as ints
    int forward  = game.isKeyPressed(Key.UP) ? 1 : 0;
    int backward = game.isKeyPressed(Key.DOWN) ? 1 : 0;
    int left     = game.isKeyPressed(Key.LEFT) ? 1 : 0;
    int right    = game.isKeyPressed(Key.RIGHT) ? 1 : 0;

    // get angular velocity
    this.angularVelocity = this.velocity.length() / this.TURN_RADIUS;
    this.angularVelocity *= (right - left);

    // get tangiental acceleration
    this.acceleration =
        Vector2d.fromPolarCoordinates(ACCELERATION * (forward - backward), this.angle);

    /*
      Centripetal acceleration should be a consequence of friction, as defined
      in class PhysicsEntity. Drifting is implemented by reducing the car's
      coefficient of friction while the drift button is pressed.
    */

    //		this.frictionCoefficient.set (masterFrictionCoefficient);

    // if drifting
    if (game.isKeyPressed(Key.D)) {
      // disable friction
      this.frictionCoefficient.scaleTo(0);

      // increase drift boost, with cap
      if (this.driftBoost + this.velocity.length() > 1.5 * this.MAX_SPEED) {
        this.driftBoost = (this.MAX_SPEED * 2) - this.velocity.length();
      }

      else
        this.driftBoost += 1;

    }

    // gradually recover from drift
    else {
      // apply and reset drift boost
      this.velocity.add(Vector2d.fromPolarCoordinates(this.driftBoost, this.angle));
      this.driftBoost = 0;

      if (this.frictionCoefficient.x < this.masterFrictionCoefficient.x)
        this.frictionCoefficient.add(this.DRIFT_REC);
    }

    // apply kinematics
    super.onStep();

    // enforce speed limit
    if (this.velocity.length() > this.MAX_SPEED) {
      if (this.velocity.length() - (ACCELERATION * 2) < this.MAX_SPEED)
        this.velocity.scaleTo(this.MAX_SPEED);
      else
        this.velocity.scaleTo(this.velocity.length() - (ACCELERATION * 2));
    }
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

  // only take damage if other entity is not destroyed, and heal if it is
  protected void applyCollisionDamages(PhysicsEntity e) {
    Vector2d momentum = this.getMomentum(COLLISION_DAMAGE);
    momentum.sub(e.getMomentum(COLLISION_DAMAGE));

    e.hit((int) momentum.length());

    if (e.isDestroyed()) {
      GameAssets.getLoadedSound("large-explosion").playSound();
      this.heal((int) momentum.length());

    } else {
      this.hit((int) momentum.length());
      GameAssets.getLoadedSound("hit").playSound();
    }
  }

  private void testForWallCollision() {
    boolean collision = false;
    for (Wall wall: this.getScene().findEntities(Wall.class)) {
      if (this.isCollidingWith(wall)) {
        collideWithWall(wall);
        collision = true;
      }
    }
    this.collidedWithWall = collision;
  }

  /**
   * Action that occurs when the player collides with a wall
   */
  private void collideWithWall(Wall wall) {
    if (this.collidedWithWall) {
      Vector2d shuntAngleVector = new Vector2d();
      shuntAngleVector.sub(this.position, wall.position);

      Point2d shunt = Point2d.fromPolarCoordinates(
          (this.mask.size.width + this.mask.size.height) / 4, shuntAngleVector.polarAngle());

      this.position.add(shunt);
    } else {
      GameAssets.getLoadedSound("hit").playSound();
      this.hit((int) Helpers.map(this.velocity.polarDistance(), 0, MAX_SPEED, 0, 3));

      this.velocity.scale(-0.5);
    }
  }
}
