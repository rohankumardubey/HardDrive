package game.entities;

import game.engine.*;
import game.scenes.*;

/**
 * Funny smiley that displays when you win the game
 */
public class WinSmiley extends Entity {

  private final static int SCALE_FACTOR        = 10;
  private final static int ROTATION_FACTOR_DEG = 10;

  private final static int WIN_TIMER = 0;
  private final static int WIN_TICKS = 80;

  public WinSmiley() {
    this.sprite.addFrames(GameAssets.getLoadedImage("smiley"));
    this.sprite.size.setSize(1, 1);
  }

  @Override
  protected void onCreate() {
    // Center in the scene
    Scene scene     = this.getScene();
    this.position.x = scene.mainView.getLeftBoundary() + scene.mainView.size.width / 2;
    this.position.y = scene.mainView.getTopBoundary() + scene.mainView.size.height / 2;

    this.setTimer(WIN_TIMER, WIN_TICKS, false);
  }

  @Override
  protected void onDestroy() {}

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == WIN_TIMER) { this.getScene().getGame().setScene(new TitleScene()); }
  }

  @Override
  protected void onStep() {
    this.sprite.size.width += SCALE_FACTOR;
    this.sprite.size.height += SCALE_FACTOR;
    this.sprite.addAngleDegrees(ROTATION_FACTOR_DEG);
  }
}
