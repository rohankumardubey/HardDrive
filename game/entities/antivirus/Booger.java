package game.entities.antivirus;

import game.engine.*;

/**
 * Booger antivirus that is dropped from the swooper
 */
public class Booger extends AntiVirus {

  private final static int BOOGER_HEALTH = 2;

  public Booger(Point2d start) {
    super(BOOGER_HEALTH);

    this.position.set(start);
    this.sprite.addFrames(GameAssets.getLoadedImage("booger"));
    this.sprite.size.setSize(64, 64);
    this.mask            = this.sprite.getMask();
    this.drawingPriority = 150;
  }

  @Override
  protected void onCreate() {
    this.setTimer(0, (int) Helpers.randomRange(5, 20), false);
  }

  @Override
  protected void onDestroy() {
    Sound explode = GameAssets.getLoadedSound("small-explosion");
    if (!explode.isPlaying()) { explode.playSound(); }

    this.getScene().createEntity(new BoogerExplosion(this.position, this.sprite.size));
  }

  @Override
  protected void onTimer(int timerIndex) {
    this.destroy();
  }

  @Override
  protected void onStep() {
    super.onStep();

    this.position.y += 15;
  }
}
