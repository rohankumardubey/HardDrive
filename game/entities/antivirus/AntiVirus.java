package game.entities.antivirus;

import game.entities.*;

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
}
