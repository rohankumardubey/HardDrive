package game.entities;

import game.engine.*;
import java.awt.*;

/**
 * Rocket ship bullet
 */
public class Bullet extends Entity {

  private final static int BULLET_SPEED = 20;

  public Bullet(Point startPoint) {
    this.position = new Point(startPoint);
    this.sprite.addFrames(GameAssets.getLoadedImage("bullet"));
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
    this.moveBullet();
    this.testIfOutsideView();
  }

  private void moveBullet() {
    this.position.x += BULLET_SPEED;
  }

  private void testIfOutsideView() {
    Scene scene = this.getScene();
    if (this.position.x > scene.mainView.getRightBoundary()) { this.destroy(); }
  }
}
