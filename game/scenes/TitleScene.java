package game.scenes;

import game.engine.*;
import game.entities.ui.*;
import java.awt.*;

/**
 * Game title screen
 */
public class TitleScene extends Scene {

  private int hintIndex = 0;

  private static final Font HINT_FONT = new Font("monospace", Font.BOLD, 16);
  private static final int HINT_DELAY = 75;
  private static final String[] HINTS = new String[] {

      "Use arrow keys to move, hold D to drift.",
      "Drifting completely eliminates friction.",
      "Drift around corners for a speed boost at the end!",
      "The longer you drift, the bigger the speed boost!",
      "Destroying a component heals you more if you hit it at higher speeds.",
      "You only take damage from hitting a component if it doesn't break.",
      "Drive through a large group of small components to quickly restore health.",
      "Some obstacles are indestructible. Watch out!",
      "Try drifting without accelerating to turn around without changing direction.",
      "It takes a couple seconds to completely recover from a drift.",
      "If you hit a boundary, you'll bounce off without taking damage.",
      "The faster you're going when you hit a component, the more damage it takes.",
      "Try drift boosting into components to deal extra damage!",
      "Be careful; not every environment has the same friction!",
      "Watch out for Anti-Viruses!",
      "Drive faster to see more of the environment and find data files more easily.",
      "Drift and accelerate while staying aimed at a specific point to do donuts!",
      "Some computer chips can spawn enemies.",
      "Cylindrical components roll more easily than they slide.",
      "Try to avoid heavy components; some of them can't be destroyed in one hit!",
      "You can toggle fullscreen mode using F4",
      "Press Escape to exit the game at any time"};

  public TitleScene() {
    super(640, 480);

    this.background.addFrames(GameAssets.getLoadedImage("circuit-bg"));
    this.background.type = BackgroundType.Image;
  }

  @Override
  protected void onCreate() {
    this.createEntity(new BinaryFlicker());
    this.createEntity(new ChangeSceneButton("Start", Color.GREEN, new LevelSelectScene(),
                                            new Point2d(size.width / 2, size.height - 80)));

    this.setTimer(1, HINT_DELAY, true);
  }

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == 1) { this.hintIndex = (hintIndex + 1) % HINTS.length; }
  }

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

    // Draw hint
    Helpers.drawCenteredString(g2d, HINTS[hintIndex], new Rectangle(0, 310, 640, 50), HINT_FONT);
  }
}
