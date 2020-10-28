package game.resources;

import game.engine.*;

/**
 * Represents the current score of a player
 */
public class Score implements Resource {
  private int score = 0;

  public int getScore() {
    return this.score;
  }

  public void asteroidHit() {
    this.score += 10;
  }

  public void asteroidDestroyed() {
    this.score += 500;
  }
}
