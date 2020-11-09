package game.entities;

import game.engine.*;

/**
 * Ants that craw along in large groups
 */
public class Ant extends AntiVirus {

  private static final int ANT_SPEED  = 5;
  private static final int ANT_HEALTH = 3;

  public Ant(Point2d start) {
    super(ANT_HEALTH);

    for (int i = 1; i <= 62; i += 1) {
      this.sprite.addFrames(GameAssets.getLoadedImage("ant-" + i));
    }
    this.sprite.size.setSize(32, 32);
    this.position.set(start);
    this.mask = this.sprite.getMask();
  }

  @Override
  protected void onCreate() {}

  @Override
  protected void onDestroy() {}

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onStep() {
    super.onStep();

    advanceAnimation();
    moveTowardsPlayer();
    testPlayerCollision();
    testIfOutsideView();
  }

  private void advanceAnimation() {
    this.sprite.nextFrame();
    this.sprite.nextFrame();
    this.sprite.nextFrame();
    this.sprite.nextFrame();
  }

  private void moveTowardsPlayer() {
    Scene scene = this.getScene();

    Player player = scene.findFirstEntity(Player.class);
    if (player == null) { return; }

    Vector2d vector = new Vector2d(player.position);
    vector.sub(this.position);
    vector.normalize();
    vector.scale(ANT_SPEED);

    this.sprite.angleRadians = vector.polarAngle() + (Math.PI / 2);
    this.position.add(vector);
  }

  private void testPlayerCollision() {
    Player player = this.getScene().findFirstEntity(Player.class);
    if (player == null) { return; }

    if (this.isCollidingWith(player)) {
      this.getScene().createEntity(
          new BinaryExplosion(this.position, this.sprite.getRotatedImageDimensions()));
      this.destroy();
    }
  }

  private void testIfOutsideView() {
    if (this.isOutsideView()) {
      this.getScene().createEntity(
          new BinaryExplosion(this.position, this.sprite.getRotatedImageDimensions()));
      this.destroy();
    }
  }
}
