package game.entities.ui;

import game.engine.*;
import game.resources.UnlockedLevels;
import game.scenes.*;
import java.awt.*;

/**
 * Button used to start a level
 */
public class LevelSelectButton extends Button {

  private static final int BUTTON_SIZE          = 64;
  private static final String LEVEL_LOCKED_TEXT = "🔒";

  /// Level this button represents
  private int level;

  public LevelSelectButton(int level, Point2d position) {
    super(LEVEL_LOCKED_TEXT, new Dimension(BUTTON_SIZE, BUTTON_SIZE));

    this.level = level;
    this.position.set(position);
  }

  @Override
  protected void onClick() {
    if (!this.isLevelUnlocked()) { return; }

    GameScene level = GameScene.getLevelScene(this.level);
    if (level != null) { this.getScene().getGame().setScene(new LoadingScene(level)); }
  }

  /**
   * Test if this level is unlocked
   */
  private boolean isLevelUnlocked() {
    UnlockedLevels unlocked = this.getScene().getGame().getResouce(UnlockedLevels.class);
    if (unlocked == null) { return false; }
    return unlocked.isLevelUnlocked(this.level);
  }

  @Override
  protected void onCreate() {}

  @Override
  protected void onDestroy() {}

  @Override
  protected void onStep() {
    super.onStep();

    if (this.isLevelUnlocked()) {
      this.text = Integer.toString(this.level);
      this.setBackgroundColor(Color.GREEN);
    } else {
      this.text = LEVEL_LOCKED_TEXT;
      this.setBackgroundColor(Color.RED);
    }
  }

  @Override
  protected void onTimer(int timerIndex) {}
}
