package game.entities;

import game.engine.*;

/**
 * Swoops into the game and drops viruses
 */
public class Swooper extends AntiVirus {

  private static final int SWOOP_SPEED    = 40;
  private static final int SWOOPER_HEALTH = 25;
  private static final int NUM_BOOGERS    = 10;

  private Vector2d velocity;

  public Swooper() {
    super(SWOOPER_HEALTH);

    this.sprite.addFrames(GameAssets.getLoadedImage("swooper"));
    this.mask     = sprite.getMask();
    this.velocity = new Vector2d();
  }

  @Override
  protected void onCreate() {
    moveToRandomCorner();

    this.setTimer(0, SWOOP_SPEED / NUM_BOOGERS, true);
  }

  /**
   * Pick a random starting corner for the sprite
   */
  private void moveToRandomCorner() {
    View view = this.getScene().mainView;

    // X coordinate
    if (Math.random() > 0.5) {
      this.position.x              = view.getLeftBoundary() - this.sprite.size.width / 2;
      this.velocity.x              = view.size.width / SWOOP_SPEED;
      this.sprite.mirrorHorizontal = true;
    } else {
      this.position.x              = view.getRightBoundary() + this.sprite.size.width / 2;
      this.velocity.x              = -view.size.width / SWOOP_SPEED;
      this.sprite.mirrorHorizontal = false;
    }

    this.position.y = view.getTopBoundary() + this.sprite.size.height / 2;

    // Y coordinate
    // if (false) {
    //   this.position.y = view.getTopBoundary() - this.sprite.size.height / 2;
    //   this.velocity.y = view.size.height / SWOOP_SPEED;
    // } else {
    //   this.position.y = view.getBottomBoundary() + this.sprite.size.height / 2;
    //   this.velocity.y = -view.size.height / SWOOP_SPEED;
    // }
  }

  @Override
  protected void onTimer(int timerIndex) {
    this.getScene().createEntity(new Booger(this.position));
  }

  @Override
  protected void onStep() {
    super.onStep();
    this.position.add(this.velocity);
  }
}
