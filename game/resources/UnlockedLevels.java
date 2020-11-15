package game.resources;

import game.engine.*;

/**
 * Stores all of the unlocked game levels
 */
public class UnlockedLevels implements Resource {
  private int maxLevelComplete;

  public UnlockedLevels() {
    maxLevelComplete = 0;
  }

  /**
   * Test if a level has been unlocked
   *
   * @param level   Level to test
   * @return        Whether or not the level is unlocked
   */
  public boolean isLevelUnlocked(int level) {
    return level <= (this.maxLevelComplete + 1);
  }

  /**
   * Mark a level as completed
   */
  public void completeLevel(int level) {
    this.maxLevelComplete = Math.max(this.maxLevelComplete, level);
  }
}
