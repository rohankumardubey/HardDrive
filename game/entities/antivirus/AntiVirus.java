package game.entities.antivirus;

import game.engine.*;
import game.entities.*;
import java.awt.*;

/**
 * Generic class to represent an antivirus in the game
 */
public abstract class AntiVirus extends HealthEntity {

  /**
   * Construct a new antivirus with a given amount of health
   */
  protected AntiVirus(int health) {
    super(health);
  }

  @Override
  protected void onStep() {
    super.onStep();
    checkBulletCollisions();
  }

  private void checkBulletCollisions() {
    for (Bullet bullet: this.getScene().findEntities(Bullet.class)) {
      if (this.isCollidingWith(bullet)) {
        bullet.destroy();
        this.health -= 1;
      }
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
