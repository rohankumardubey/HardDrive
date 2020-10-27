package game.scenes;

import game.engine.*;
import game.entities.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class TestScene extends Scene {

  public TestScene() {
    super(640 * 2, 480 * 2);
    this.mainView.size = new Dimension(640, 480);

    this.background.addFrames(GameAssets.getLoadedImage("smiley"));
    this.background.type = BackgroundType.Tiled;
    this.backgroundColor = new Color(255, 0, 0);
    this.outsideColor    = new Color(0, 0, 0);

    this.setTimer(0, 10, true);
  }

  @Override
  protected void onCreate() {
    this.createEntity(new Player());
  }

  @Override
  protected void onTimer(int timerIndex) {
    this.background.mirrorVertical = !this.background.mirrorVertical;
  }

  @Override
  protected void onStep() {
    Game g = this.getGame();
    if (g.isKeyPressed(Key.ESCAPE)) { g.end(); }
    if (g.isKeyPressed(Key.RIGHT)) { this.mainView.position.x += 100; }
    this.mainView.position.x -= 1;
  }

  @Override
  protected void onDraw(Graphics2D g2d) {
    // TODO Auto-generated method stub
  }
}
