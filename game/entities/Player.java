package game.entities;

import game.engine.*;
import game.entities.component.*;
import game.entities.walls.*;
import game.scenes.GameScene;

/**
 * Car that you control
 */
public class Player extends PhysicsEntity {

  // Constants
  private static final int PLAYER_HEALTH   = 25;
  private static final double TURN_RADIUS  = 80;
  private static final double ACCELERATION = 3.0;
  private static final double MAX_SPEED    = 30;
  private static final int MAX_DAMAGE      = 20;
  private static final double PLAYER_MASS  = 10;

  // Track whether the player is currently drifting
  private boolean isDrifting = false;

  public Player(Point2d position) {
    super(new Vector2d (0.1, 100), PLAYER_MASS, PLAYER_HEALTH);

    // Initialize player size and sprite
    this.sprite.addFrames(GameAssets.getLoadedImage("car"));
    this.sprite.size.setSize(64, 32);
    this.sprite.setAngleDegrees(270);
    this.mask = sprite.getMask();

    // Position, mass, and weight
    this.position.setLocation(position);
	 this.angle = Math.toRadians(270.0);
	 this.mass = 10;

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
    move();

    testForWallCollision();
    testForComponentCollisions();
    testForBoundaryCollision();
  }

  /**
   * Read keyboard input and change the player velocity and direction
   */
	private void move() {
		Game game = this.getScene().getGame();
		//get inputs as ints
		int forward  = game.isKeyPressed (Key.UP)    ? 1 : 0;
		int backward = game.isKeyPressed (Key.DOWN)  ? 1 : 0;
	 	int left     = game.isKeyPressed (Key.LEFT)  ? 1 : 0;
		int right    = game.isKeyPressed (Key.RIGHT) ? 1 : 0;

		//get angular velocity
		this.angularVelocity = this.velocity.length() / this.TURN_RADIUS;
		this.angularVelocity *= (right - left);

		//get tangiental acceleration
		this.acceleration = Vector2d.fromPolarCoordinates
		(
			this.ACCELERATION * (forward - backward),
			this.angle
		);

		/*
			Centripetal acceleration should be a consequence of friction, as defined
			in class PhysicsEntity. Drifting is implemented by reducing the car's
			coefficient of friction while the drift button is pressed.
		*/

		this.frictionCoefficient.set (masterFrictionCoefficient);

		if (game.isKeyPressed (Key.D))
		{
			this.isDrifting = true;
			this.frictionCoefficient.scaleBy (0.05);
		}
		//if the player just stopped drifting, send them in the right direction
		else if (this.isDrifting)
		{
			this.isDrifting = false;
			this.velocity.setAngle (this.angle);
		}

		//apply kinematics
		super.onStep();

		//enforce speed limit
		if (this.velocity.length() > this.MAX_SPEED)
		{
			this.velocity.scaleTo (this.MAX_SPEED);
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
    this.hit((int) Helpers.map(this.velocity.polarDistance(), 0, MAX_SPEED, 0, 3));
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
      this.hit((int) Helpers.map(healthLost, 0, MAX_DAMAGE, 0, 3));
      this.position.sub(this.velocity);
      this.velocity.scale(-0.5);
    }
  }
}
