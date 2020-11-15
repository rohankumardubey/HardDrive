package game.entities.ui;

import game.engine.*;
import java.awt.*;

/**
 * Cool binary flicker effect on the title screen
 */
public class BinaryFlicker extends Entity {

  public BinaryFlicker() {
    for (int i = 1; i < 10; i += 1) {
      this.sprite.addFrames(GameAssets.getLoadedImage("binary-" + i));
    }

    this.drawingPriority = Integer.MIN_VALUE;
  }

  @Override
  protected void onCreate() {
    this.setTimer(0, 3, true);
  }

  @Override
  protected void onDestroy() {}

  @Override
  protected void onTimer(int timerIndex) {
    this.sprite.setFrameIndex((int) (Math.random() * this.sprite.getFrameCount()));
  }

  @Override
  protected void onStep() {}

  @Override
  protected void draw(Graphics2D g2d) {
    Dimension size = this.getScene().size;

    g2d.drawImage(this.sprite.getCurrentFrameImage(), 0, -10, size.width, size.height + 10, null);

    g2d.setColor(new Color(0, 0, 0, 128));
    g2d.fillRect(0, 0, size.width, size.height);
  }
}
