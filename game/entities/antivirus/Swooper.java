package game.entities.antivirus;

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
    moveToRandomSide();

    this.setTimer(0, SWOOP_SPEED / NUM_BOOGERS, true);
  }

  /**
   * Pick a random starting si
   */
  private void moveToRandomSide() {
    View view = this.getScene().mainView;
    if (Math.random() > 0.5) {
      // Left
      this.position.x              = view.getLeftBoundary() - this.sprite.size.width / 2;
      this.velocity.x              = view.size.width / SWOOP_SPEED;
      this.sprite.mirrorHorizontal = true;
    } else {
      // Right
      this.position.x              = view.getRightBoundary() + this.sprite.size.width / 2;
      this.velocity.x              = -view.size.width / SWOOP_SPEED;
      this.sprite.mirrorHorizontal = false;
    }
  }

  @Override
  protected void onTimer(int timerIndex) {
    this.getScene().createEntity(new Booger(this.position));
  }

  @Override
  protected void onStep() {
    super.onStep();

    this.position.add(this.velocity);
    this.position.y = this.getScene().mainView.getTopBoundary() + this.sprite.size.height / 2;
  }
}
