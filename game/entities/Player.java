package game.entities;

import game.engine.*;

public class Player extends Entity {

  public Player() {
    super();

    this.sprite.addFrames(GameAssets.getLoadedImage("boxy"));
    this.mask = this.sprite.getMask();
  }

  @Override
  protected void onCreate() {
    this.setTimer(0, 10, true);
    this.setTimer(1, 12, true);
  }

  @Override
  protected void onDestroy() {
    // TODO Auto-generated method stub
  }

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == 0) { this.sprite.mirrorVertical = !this.sprite.mirrorVertical; }
    if (timerIndex == 1) { GameAssets.getLoadedSound("explosion").playSound(); }
    if (timerIndex == 2) { GameAssets.getLoadedSound("explosion").playSound(); }
  }

  @Override
  protected void onStep() {
    this.position.x += 1;
    this.position.y += 1;
    this.sprite.angleRadians += Math.PI / 12;
  }
}
