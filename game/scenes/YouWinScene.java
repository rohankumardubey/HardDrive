package game.scenes;

import game.engine.*;
import game.entities.ui.*;
import java.awt.*;

/**
 * Scene that shows at the very end of the game
 */
public class YouWinScene extends Scene {

  public YouWinScene() {
    super(640, 480);

    this.background.type = BackgroundType.None;
    this.backgroundColor = Color.BLACK;
  }

  @Override
  protected void onCreate() {
    this.createEntity(new BinaryFlicker());

    this.createEntity(
        new BackToLevelSelectButton("Continue", new Point2d(size.width / 2, size.height - 70)));
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

    Font font = new Font("Times", Font.BOLD, 32);

    g2d.setColor(Color.WHITE);
    Helpers.drawCenteredString(g2d, "All servers have been wiped",
                               new Rectangle(0, 30, this.size.width, 100), font);

    Helpers.drawCenteredString(g2d, "Any records of your crimes have been destroyed",
                               new Rectangle(0, 100, this.size.width, 100),
                               new Font("Times", Font.BOLD, 26));

    Helpers.drawCenteredString(g2d, "Your name has been cleared...",
                               new Rectangle(0, 170, this.size.width, 100), font);
  }
}
