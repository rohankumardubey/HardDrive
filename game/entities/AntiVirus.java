package game.entities;

import game.engine.*;
import java.awt.*;

/**
 * Generic class to represent an antivirus in the game
 */
public abstract class AntiVirus extends Entity {
  private static final int HEALTH_BAR_HEIGHT = 10;

  protected int health;
  protected int maxHealth;

  /**
   * Construct a new antivirus with a given amount of health
   */
  protected AntiVirus(int health) {
    this.health    = health;
    this.maxHealth = health;
  }

  @Override
  protected void onStep() {
    checkBulletCollisions();
    checkNoHealth();
  }

  private void checkBulletCollisions() {
    for (Bullet bullet: this.getScene().findEntities(Bullet.class)) {
      if (this.isCollidingWith(bullet)) {
        bullet.destroy();
        this.health -= 1;
      }
    }
  }

  private void checkNoHealth() {
    if (this.health <= 0) {
      this.getScene().createEntity(
          new BinaryExplosion(this.position, this.sprite.getRotatedImageDimensions()));
      this.destroy();
    }
  }

  @Override
  protected void onDestroy() {
    // TODO: Play Sound
  }

  @Override
  protected void draw(Graphics2D g2d) {
    super.draw(g2d);

    // Don't draw health bar if at full health
    if (health >= maxHealth) { return; }

    Dimension d  = this.sprite.getRotatedImageDimensions();
    double leftX = this.position.x - d.getWidth() / 4;
    double topY  = this.position.y - HEALTH_BAR_HEIGHT;

    Helpers.drawHealthBar(
        g2d, (double) health / maxHealth,
        new Rectangle((int) leftX, (int) topY, (int) d.getWidth() / 2, HEALTH_BAR_HEIGHT),
        Color.GREEN, Color.RED);
  }
}
