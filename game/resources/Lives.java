package game.resources;

import game.engine.*;

/**
 * Represents the number of player lives left
 */
public class Lives implements Resource {
  private final static int DEFAULT_LIVES = 3;

  private int lives = DEFAULT_LIVES;

  public void resetLives() {
    this.lives = DEFAULT_LIVES;
  }

  public int getLives() {
    return this.lives;
  }

  public void lifeCollected() {
    this.lives += 1;
  }

  public void playerKilled() {
    this.lives -= 1;
  }

  public boolean playerHasLivesLeft() {
    return this.lives > 0;
  }
}
