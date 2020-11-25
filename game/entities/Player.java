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
  private static final int    PLAYER_HEALTH =  25;
  private static final double   TURN_RADIUS = 120;
  private static final double  ACCELERATION = 3.0;
  private static final double     MAX_SPEED =  30;
  private static final int       MAX_DAMAGE =  20;
  private static final double   PLAYER_MASS =  10;
  private static Vector2d        DRIFT_REC;

  private double driftBoost = 0;

  public Player(Point2d position) {
    super(new Vector2d (0.5, 25), PLAYER_MASS, PLAYER_HEALTH);

    // Initialize player size and sprite
    this.sprite.addFrames(GameAssets.getLoadedImage("car"));
    this.sprite.size.setSize(64, 32);
    this.sprite.setAngleDegrees(270);
    this.mask = sprite.getMask();

    // Position, mass, and weight
    this.position.setLocation(position);
	 this.angle = Math.toRadians(270.0);
	 this.mass = 10;

	 // How quickly the car recovers from drift
	 this.DRIFT_REC = new Vector2d (this.masterFrictionCoefficient);
	 this.DRIFT_REC.scaleBy (0.0125);

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
   * Get the max speed of the car
   *
   * @return the max speed
   */
  public double getMaxSpeed() {
    return this.MAX_SPEED;
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
			ACCELERATION * (forward - backward),
			this.angle
		);

		/*
			Centripetal acceleration should be a consequence of friction, as defined
			in class PhysicsEntity. Drifting is implemented by reducing the car's
			coefficient of friction while the drift button is pressed.
		*/

//		this.frictionCoefficient.set (masterFrictionCoefficient);

		//if drifting
		if (game.isKeyPressed (Key.D))
		{
			//disable friction
			this.frictionCoefficient.scaleTo (0);

			//increase drift boost, with cap
			if (this.driftBoost + this.velocity.length() > 1.5 * this.MAX_SPEED)
			{
				this.driftBoost = (this.MAX_SPEED * 2) - this.velocity.length();
			}

			else this.driftBoost += 1;

		}

		//gradually recover from drift
		else
		{
			//apply and reset drift boost
			this.velocity.add (Vector2d.fromPolarCoordinates(this.driftBoost, this.angle));
			this.driftBoost = 0;

			if (this.frictionCoefficient.x < this.masterFrictionCoefficient.x)
				this.frictionCoefficient.add (this.DRIFT_REC);
		}

		//apply kinematics
		super.onStep();

		//enforce speed limit
		if (this.velocity.length() > this.MAX_SPEED)
		{
			System.out.println ("TOO FAST");
			if (this.velocity.length() - (ACCELERATION * 2) < this.MAX_SPEED)
				this.velocity.scaleTo (this.MAX_SPEED);
			else
			{
				System.out.println (", SLOW DOWN\n");
				this.velocity.scaleTo (this.velocity.length() - (ACCELERATION * 2));
			}
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
