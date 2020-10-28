package game.entities;

import game.engine.*;
import game.scenes.*;
import java.awt.*;

/**
 * Player explosion before going back to title screen
 */
public class PlayerExplosion extends Entity {

  private final static int ANIMATION_SPEED      = 2;
  private final static int NUM_EXPLOSION_FRAMES = 4;

  // Timers
  private final static int ANIMATION_TIMER = 0;

  public PlayerExplosion(Point position) {
    this.position.setLocation(position);

    // Load explosion sprites
    for (int i = 1; i <= NUM_EXPLOSION_FRAMES; i += 1) {
      this.sprite.addFrames(GameAssets.getLoadedImage("ship-explosion-" + i));
    }
  }

  @Override
  protected void onCreate() {
    this.setTimer(ANIMATION_TIMER, ANIMATION_SPEED, true);
  }

  @Override
  protected void onDestroy() {
    // Go back to the title screen
    this.getScene().getGame().setScene(new TitleScene());
  }

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == ANIMATION_TIMER) {
      this.sprite.nextFrame();
      if (this.sprite.getFrameIndex() == 0) { this.destroy(); }
    }
  }

  @Override
  protected void onStep() {}
}
