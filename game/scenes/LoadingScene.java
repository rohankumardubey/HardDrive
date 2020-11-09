package game.scenes;

import game.engine.*;
import java.awt.*;

/**
 * Scene effect to show logging into the game
 */
public class LoadingScene extends Scene {

  private static final String LINE_1 = "Logging in user...";
  private static final String LINE_2 = "Loading server...";

  private static final Font GRAPHICS_FONT   = new Font("monospace", Font.PLAIN, 40);
  private static final Color GRAPHICS_COLOR = new Color(0, 255, 0);

  private Scene nextScene;
  private String line1, line2;

  public LoadingScene(Scene nextScene) {
    super(640, 480);
    this.mainView.size.setSize(640, 480);

    this.background.type = BackgroundType.None;
    this.backgroundColor = Color.BLACK;

    this.nextScene = nextScene;
    this.line1     = "";
    this.line2     = "";
  }

  @Override
  protected void onCreate() {
    GameAssets.getLoadedSound("typing").playSound();
    this.setTimer(0, 2, true);
  }

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == 0) { typeLines(); }
    if (timerIndex == 1) {}
  }

  @Override
  protected void onStep() {
    Game game = this.getGame();
    if (game.isKeyPressed(Key.ESCAPE)) { game.end(); }

    testForTypingFinished();
  }

  private void typeLines() {

    if (line1.length() < LINE_1.length()) {
      line1 += LINE_1.charAt(line1.length());
      return;
    }

    if (line2.length() < LINE_2.length()) {
      line2 += LINE_2.charAt(line2.length());
      return;
    }
  }

  private void testForTypingFinished() {
    if (GameAssets.getLoadedSound("typing").isPlaying()) { return; }
    GameAssets.getLoadedSound("corruption-bgm").loopSound();
    this.getGame().setScene(this.nextScene);
  }

  @Override
  protected void onDraw(Graphics2D g2d) {
    g2d.setColor(GRAPHICS_COLOR);

    Helpers.drawCenteredString(g2d, line1, new Rectangle(0, 75, 640, 150), GRAPHICS_FONT);
    Helpers.drawCenteredString(g2d, line2, new Rectangle(0, 150, 640, 250), GRAPHICS_FONT);
  }
}
