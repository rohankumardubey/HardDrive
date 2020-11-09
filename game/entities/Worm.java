package game.entities;

import game.engine.*;

/**
 * Worm antivirus program
 */
public class Worm extends AntiVirus {

  private static final double CHARGING_SPEED = 15;
  private static final double SPIN_SPEED     = Math.PI / 24;
  private static final double STRAF_SPEED    = Math.PI / 400;
  private static final double MAX_STRAF      = Math.PI / 12;
  private static final int WORM_HEALTH       = 15;

  WormPhase state;
  Vector2d chargingVector;
  boolean insideView;

  // Used for computing straffing
  double leftChargingAngle, rightChargingAngle;

  public Worm() {
    super(WORM_HEALTH);

    this.state          = WormPhase.Spinning;
    this.chargingVector = new Vector2d();
    this.insideView     = false;

    this.sprite.addFrames(GameAssets.getLoadedImage("worm"));
    this.sprite.size.setSize(128, 64);
    this.sprite.mirrorHorizontal = true;
  }

  @Override
  protected void onCreate() {
    Scene scene = this.getScene();

    // Worm starts on the right side of the view
    this.position.set(scene.mainView.position);
    this.position.add(new Point2d(scene.mainView.size.width, scene.mainView.size.height / 2));
    this.position.x -= this.sprite.size.width * 2;

    this.setTimer(0, 20, true);
  }

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onStep() {
    switch (this.state) {
      case Spinning:
        this.spinWorm();
        break;

      case Charging:
        this.testWhenOutsideView();
        // this.matchAngleToPlayer();
        this.chargeWorm();
        break;
    }
  }

  /**
   * Spin the worm until it is aligned with the player
   */
  private void spinWorm() {
    Vector2d targetVector = this.getTargetVector();
    if (targetVector == null) {
      // Spin forever if no player exists
      this.sprite.angleRadians += SPIN_SPEED;
      return;
    }

    double targetAngle = targetVector.polarAngle();
    double newAngle    = this.sprite.angleRadians + SPIN_SPEED;
    if (Helpers.isAngleBetween(targetAngle, this.sprite.angleRadians, newAngle)) {
      // Angle is aligned, so charge worm
      this.computeChargingVector(targetAngle);
      this.leftChargingAngle  = targetAngle - MAX_STRAF;
      this.rightChargingAngle = targetAngle + MAX_STRAF;
      this.state              = WormPhase.Charging;
    } else {
      // Angle is not yet aligned
      this.sprite.angleRadians = newAngle;
    }
  }

  /**
   * Get angle between the worm and the player to decide which direction to charge
   */
  private Vector2d getTargetVector() {
    Scene scene = this.getScene();

    Player player = scene.findFirstEntity(Player.class);
    if (player == null) { return null; }

    Vector2d vector = new Vector2d(player.position);
    vector.sub(this.position);

    return vector;
  }

  private void computeChargingVector(double targetAngle) {
    this.sprite.angleRadians = targetAngle;
    this.chargingVector      = Vector2d.fromPolarCoordinates(CHARGING_SPEED, targetAngle);
  }

  private void matchAngleToPlayer() {
    Vector2d targetVector = this.getTargetVector();
    if (targetVector == null) { return; }

    if (targetVector.polarDistance() < CHARGING_SPEED) { return; }

    double targetAngle  = targetVector.polarAngle();
    double currentAngle = this.chargingVector.polarAngle();
    double newAngle;
    if (targetAngle > currentAngle) {
      newAngle = targetAngle + STRAF_SPEED;
    } else {
      newAngle = targetAngle - STRAF_SPEED;
    }

    if (Helpers.isAngleBetween(newAngle, leftChargingAngle, rightChargingAngle)) {
      this.computeChargingVector(newAngle);
    }
  }

  /**
   * Charge the work across the screen
   * */
  private void chargeWorm() {
    this.position.add(this.chargingVector);
  }

  /**
   * Test when the worm is outside the screen to decide when to spin it again
   */
  private void testWhenOutsideView() {
    if (!this.insideView) {
      if (this.isInsideView()) { this.insideView = true; }
    } else {
      if (this.isOutsideView()) {
        this.state      = WormPhase.Spinning;
        this.insideView = false;
      }
    }
  }
}

/**
 * Current state for the worm object
 */
enum WormPhase {
  Spinning,
  Charging,
}