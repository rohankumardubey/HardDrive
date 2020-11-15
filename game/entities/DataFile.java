package game.entities;

import game.engine.*;
import game.scenes.*;

/**
 * Data file to destroy
 */
public class DataFile extends HealthEntity {

  private static final int TILE_SIZE = 2;
  private static final int HEALTH    = 4;

  public DataFile(Point2d position) {
    super(HEALTH);

    this.sprite.addFrames(GameAssets.getLoadedImage("data-file"));
    this.sprite.size.setSize(GameScene.TILE_SIZE * TILE_SIZE, GameScene.TILE_SIZE * TILE_SIZE);
    this.mask = sprite.getMask();
    this.position.set(position);
  }

  @Override
  protected void onCreate() {}

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onStep() {
    super.onStep();
    checkBulletCollisions();
  }

  private void checkBulletCollisions() {
    for (Bullet bullet: this.getScene().findEntities(Bullet.class)) {
      if (this.isCollidingWith(bullet)) {
        bullet.destroy();
        this.hit(1);
      }
    }
  }
}
