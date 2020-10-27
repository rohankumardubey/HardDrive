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
    this.background.type            = BackgroundType.Tiled;
    this.background.backgroundColor = new Color(255, 0, 0);
  }

  @Override
  protected void onCreate() {
    this.createEntity(new Player());
  }

  @Override
  protected void onStep() {
    // TODO Auto-generated method stub
  }

  @Override
  protected void onDraw(Graphics2D g2d) {
    // TODO Auto-generated method stub
  }
}
