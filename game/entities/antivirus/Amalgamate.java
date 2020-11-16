package game.entities.antivirus;

import game.engine.*;
import game.entities.*;

/**
 * Amalgamate virus - Explodes into tiny viruses
 */
public class Amalgamate extends AntiVirus {

  private static final int AMALGAMATE_SPEED      = 50;
  private static final double AMALGAMATE_DRAG    = 0.90;
  private static final double RANDOM_ANGLE_RANGE = Math.PI / 4;

  private static final int MIN_POPPING_TIME = 50;
  private static final int MAX_POPPING_TIME = 200;
  private static final int MIN_TINY_VIRUS   = 10;
  private static final int MAX_TINY_VIRUS   = 30;
  private static final int INITIAL_SPEED    = 100;

  private Vector2d velocity;
  private boolean chasingPlayer;
  private boolean popping;

  public Amalgamate(Point2d start) {
    super(1);

    this.sprite.addFrames(GameAssets.getLoadedImage("amalgamate"));
    this.sprite.addFrames(GameAssets.getLoadedImage("amalgamate-angry"));
    this.sprite.size.setSize(128, 128);
    this.drawingPriority = 200;

    this.position.set(start);
    this.mask = this.sprite.getMask();

    this.velocity      = new Vector2d();
    this.chasingPlayer = true;
    this.popping       = false;
  }

  @Override
  protected void onCreate() {
    pointTowardsPlayer();
  }

  @Override
  protected void onDestroy() {
    GameAssets.getLoadedSound("ship-explosion").playSound();
    this.getScene().createEntity(
        new BoogerExplosion(this.position, this.sprite.getRotatedImageDimensions()));

    createTinyViruses();
  }

  /**
   * Create all of the tiny viruses that explode out in all directions
   */
  private void createTinyViruses() {
    int toCreate        = (int) Helpers.randomRange(MIN_TINY_VIRUS, MAX_TINY_VIRUS);
    double deltaAngle   = (2 * Math.PI) / toCreate;
    double initialAngle = Helpers.randomRange(0, 2 * Math.PI);

    Scene scene = this.getScene();
    for (int i = 0; i < toCreate; i += 1) {
      Vector2d vector = Vector2d.fromPolarCoordinates(INITIAL_SPEED, initialAngle + i * deltaAngle);
      scene.createEntity(new TinyVirus(this.position, vector));
    }
  }

  /**
   * Change vector to point towards the player
   */
  private void pointTowardsPlayer() {
    Player player = this.getScene().findFirstEntity(Player.class);
    if (player == null) { return; }

    Vector2d vector = new Vector2d(player.position);
    vector.sub(this.position);
    vector.normalize();
    vector.scale(AMALGAMATE_SPEED);
    this.velocity = vector;

    this.chasingPlayer = true;
  }

  /**
   * Point randomly in the general direction of the player
   */
  private void pointRandomly() {
    Player player = this.getScene().findFirstEntity(Player.class);
    if (player == null) { return; }

    Vector2d playerVector = new Vector2d(player.position);
    playerVector.sub(this.position);
    double angle = playerVector.polarAngle();

    Vector2d vector = Vector2d.fromPolarCoordinates(
        AMALGAMATE_SPEED,
        Helpers.randomRange(angle - RANDOM_ANGLE_RANGE, angle + RANDOM_ANGLE_RANGE));
    this.velocity = vector;
  }

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == 0) { this.destroy(); }
  }

  @Override
  protected void onStep() {
    super.onStep();

    moveAmalgamate();
    testForBoundaryCollision();
    testForPlayerCollision();
    applyDrag();
    flipState();
  }

  /**
   * Move the amalgamate using the velocity vector
   */
  private void moveAmalgamate() {
    this.position.add(this.velocity);
  }

  /**
   * Bounce off the edges of the boundary
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
   * Turn "Angry" if colliding with the player
   */
  private void testForPlayerCollision() {
    if (this.popping) { return; }

    Player player = this.getScene().findFirstEntity(Player.class);
    if (this.isCollidingWith(player)) {
      this.popping = true;
      this.sprite.nextFrame();
      this.setTimer(0, (int) Helpers.randomRange(MIN_POPPING_TIME, MAX_POPPING_TIME), false);
    }
  }

  /**
   * Apply a drag coefficient to the amalgamate movement
   */
  private void applyDrag() {
    this.velocity.scale(AMALGAMATE_DRAG);
  }

  /**
   * Flip the state when the amalgamate speed gets too slow
   */
  private void flipState() {
    if (this.velocity.polarDistance() >= 0.1) { return; }
    if (this.chasingPlayer) {
      pointRandomly();
    } else {
      pointTowardsPlayer();
    }
  }
}
