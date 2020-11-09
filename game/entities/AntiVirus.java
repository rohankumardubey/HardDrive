package game.entities;

import game.engine.*;

/**
 * Generic class to represent an antivirus in the game
 */
public abstract class AntiVirus extends Entity {
  @Override
  protected void onDestroy() {
    // TODO: Play Sound
    this.getScene().createEntity(
        new BinaryExplosion(this.position, this.sprite.getRotatedImageDimensions()));
  }
}
