package game.entities;

import game.engine.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

  @Override
  protected void onCreate() {
    BufferedImage i = this.getScene().getGame().getLoadedImage("boxy");
    this.sprite.addFrames(i);
    this.setTimer(0, 10, true);
    this.setTimer(1, 7, true);
    this.setTimer(2, 1, true);
  }

  @Override
  protected void onDestroy() {
    // TODO Auto-generated method stub
  }

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == 0) { this.sprite.mirrorVertical = !this.sprite.mirrorVertical; }
    if (timerIndex == 1) { this.sprite.mirrorHorizontal = !this.sprite.mirrorHorizontal; }
    if (timerIndex == 2) {
      View v = this.getScene().mainView;
      v.position.x += 5;
      v.position.y += 5;
    }
  }

  @Override
  protected void onStep() {
    this.position.x += 1;
    this.position.y += 1;
    this.sprite.angleRadians += Math.PI / 12;
  }
}
