package game.entities;

import game.engine.*;
import java.awt.*;

/**
 * Represents an entity that has a health bar
 */
public abstract class HealthEntity extends PhysicsEntity {

  private static final int HEALTH_BAR_HEIGHT = 10;

  protected int health;
  protected int maxHealth;

  /**
   * Construct a new antivirus with a given amount of health
   */
  protected HealthEntity(int health) {
    super();

    this.health    = health;
    this.maxHealth = health;
  }

  /**
   * Get the current eneity health
   * @return    Entity health
   */
  public int getHealth() {
    return this.health;
  }

  /**
   * Cause damage to this health entity
   *
   * @param damage    Amount of damage to cause
   */
  public void hit(int damage) {
    this.health = Math.max(0, this.health - damage);
    checkNoHealth();
  }

  /**
   * Restore full health to this entity
   */
  public void heal() {
    this.heal(this.maxHealth);
  }

  /**
   * Restore some health to this entity
   * @param heal    Amount of health to heal
   */
  public void heal(int heal) {
    this.health = Math.min(this.maxHealth, this.health + heal);
  }

  /**
   * Default method creates a binary explosion of the entity
   */
  @Override
  protected void onDestroy() {
    GameAssets.getLoadedSound("small-explosion").playSound();
    this.getScene().createEntity(
        new BinaryExplosion(this.position, this.sprite.getRotatedImageDimensions()));
  }

  /**
   * Default method destroys the entity of it runs out of health
   */
  @Override
  protected void onStep() {
    checkNoHealth();
	 super.onStep();
  }

  private void checkNoHealth() {
    if (this.health <= 0) { this.destroy(); }
  }

  /**
   * Default method draws the sprite followed by the health bar
   */
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
