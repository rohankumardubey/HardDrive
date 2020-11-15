package game.scenes;

import game.engine.*;
import game.entities.ui.*;
import java.awt.*;

/**
 * Scene that shows when you run out of lives
 */
public class LevelCompleteScene extends Scene {

  private GameScene nextScene;

  public LevelCompleteScene(GameScene nextScene) {
    super(640, 480);

    this.nextScene       = nextScene;
    this.background.type = BackgroundType.None;
    this.backgroundColor = Color.BLACK;
  }

  @Override
  protected void onCreate() {
    this.createEntity(new BinaryFlicker());

    this.createEntity(new ChangeSceneButton("Next Level", Color.GREEN, this.nextScene,
                                            new Point2d(size.width / 2, size.height - 170)));

    this.createEntity(
        new BackToLevelSelectButton("Level Select", new Point2d(size.width / 2, size.height - 70)));
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
    Helpers.drawCenteredString(g2d, "Data Files Wiped!", new Rectangle(0, 30, this.size.width, 100),
                               new Font("Times", Font.BOLD, 60));

    g2d.drawImage(GameAssets.getLoadedImage("data-file"), (size.width / 2) - 24,
                  (size.height / 2) - 64, 48, 48, null);
  }
}
