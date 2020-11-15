package game.scenes;

import game.engine.*;
import game.entities.ui.*;
import java.awt.*;

/**
 * Game title screen
 */
public class TitleScene extends Scene {

  public TitleScene() {
    super(640, 480);

    this.background.addFrames(GameAssets.getLoadedImage("circuit-bg"));
    this.background.type = BackgroundType.Image;
  }

  @Override
  protected void onCreate() {
    this.createEntity(new BinaryFlicker());
    this.createEntity(new ChangeSceneButton("Start", Color.GREEN, new LevelSelectScene(),
                                            new Point2d(size.width / 2, size.height - 100)));
  }

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onStep() {
    Game game = this.getGame();
    if (game.isKeyPressed(Key.ESCAPE)) { game.end(); }
    if (game.hasKeyBeenPressed(Key.F4)) { game.toggleFullscreen(); }
  }

  @Override
  protected void onDraw(Graphics2D g2d) {

    g2d.setColor(Color.WHITE);

    Helpers.drawCenteredString(g2d, "Hard Drive", new Rectangle(0, 0, this.size.width, 100),
                               new Font("Times", Font.BOLD, 96));

    Helpers.drawCenteredString(g2d, "By Bryan McClain and Caleb Wilson",
                               new Rectangle(0, 50, this.size.width, 150),
                               new Font("Times", Font.BOLD, 18));

    Dimension size = this.size;
    g2d.drawImage(GameAssets.getLoadedImage("data-file"), (size.width / 2) - 64,
                  (size.height / 2) - 64, 128, 128, null);
  }
}
