package game.scenes;

import game.engine.*;
import game.entities.StartButton;
import java.awt.*;

/**
 * Game title screen
 */
public class TitleScene extends Scene {

  public TitleScene() {
    super(640, 480);

    this.background.addFrames(GameAssets.getLoadedImage("space-bg"));
    this.background.type = BackgroundType.Stretched;
  }

  @Override
  protected void onCreate() {
    this.createEntity(new StartButton());
  }

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onStep() {
    Game game = this.getGame();
    if (game.isKeyPressed(Key.ESCAPE)) { game.end(); }
  }

  @Override
  protected void onDraw(Graphics2D g2d) {
    g2d.setColor(Color.WHITE);

    Helpers.drawCenteredString(g2d, "Space Game Demo", new Rectangle(0, 0, this.size.width, 100),
                               new Font("Times", Font.BOLD, 48));

    Helpers.drawCenteredString(g2d, "Created by Bryan McClain",
                               new Rectangle(0, 50, this.size.width, 150),
                               new Font("Times", Font.BOLD, 24));
  }
}
