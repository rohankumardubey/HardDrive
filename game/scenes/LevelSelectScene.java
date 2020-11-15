package game.scenes;

import game.engine.*;
import game.entities.ui.*;
import java.awt.*;

/**
 * Allows you to select a level in the game
 */
public class LevelSelectScene extends Scene {

  public LevelSelectScene() {
    super(640, 480);

    this.background.addFrames(GameAssets.getLoadedImage("circuit-bg"));
    this.background.type = BackgroundType.Image;
  }

  @Override
  protected void onCreate() {
    this.createEntity(new BinaryFlicker());

    // Create level select entities
    int width = this.size.width;
    this.createEntity(new LevelSelectButton(1, new Point2d(width / 3 - (width / 6), 170)));
    this.createEntity(new LevelSelectButton(2, new Point2d(2 * width / 3 - (width / 6), 170)));
    this.createEntity(new LevelSelectButton(3, new Point2d(width - (width / 6), 170)));

    this.createEntity(new LevelSelectButton(4, new Point2d(width / 3 - (width / 6), 270)));
    this.createEntity(new LevelSelectButton(5, new Point2d(2 * width / 3 - (width / 6), 270)));
    this.createEntity(new LevelSelectButton(6, new Point2d(width - (width / 6), 270)));

    this.createEntity(new BackToTitleButton("Go Back"));
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

    Helpers.drawCenteredString(g2d, "Select Level:", new Rectangle(0, 0, this.size.width, 100),
                               new Font("Times", Font.BOLD, 48));
  }
}
