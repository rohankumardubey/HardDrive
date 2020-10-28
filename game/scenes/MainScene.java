package game.scenes;

import game.engine.*;
import game.entities.*;
import game.resources.*;
import java.awt.*;

/**
 * Main gameplay scene
 */
public class MainScene extends Scene {

  public final static int SCROLL_SPEED = 3;

  private final static int NUM_ROOMS     = 6;
  private final static int NUM_ASTEROIDS = 10;

  // Timers
  private final static int WIN_TIMER       = 0;
  private final static int WIN_TIMER_TICKS = 30;

  private boolean eventTriggered = false;

  public MainScene() {
    super(NUM_ROOMS * 640, 480);
    this.mainView.size.setSize(640, 480);

    this.background.addFrames(GameAssets.getLoadedImage("stars-tiled"));
    this.background.type = BackgroundType.Tiled;
  }

  @Override
  protected void onCreate() {
    this.getGame().setResource(new Score());
    this.createEntity(new Player());
    for (int i = 0; i < NUM_ASTEROIDS; i += 1) { this.createEntity(new Asteroid()); }
  }

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == WIN_TIMER) {
      GameAssets.getLoadedSound("win").playSound();
      this.createEntity(new WinSmiley());
    }
  }

  @Override
  protected void onStep() {
    Game game = this.getGame();
    if (game.isKeyPressed(Key.ESCAPE)) { game.end(); }
    if (game.hasKeyBeenPressed(Key.F4)) { game.toggleFullscreen(); }

    scrollWindow();
    testWinCondition();
  }

  private void scrollWindow() {
    if (!this.eventTriggered) { this.mainView.position.x += SCROLL_SPEED; }
  }

  private void testWinCondition() {
    if (this.eventTriggered) { return; }

    // Make sure player hasn't already died
    if (this.findEntities(PlayerExplosion.class).size() > 0) {
      this.eventTriggered = true;
      return;
    }

    // Test if view has scrolled to end of the level
    if (this.mainView.position.x + this.mainView.size.width > (this.size.width - SCROLL_SPEED)) {
      // Destroy all asteroids
      for (Asteroid asteroid: this.findEntities(Asteroid.class)) {
        asteroid.createExplosion();
        asteroid.destroy();
      }

      // Make sure player stops moving to the right
      for (Player player: this.findEntities(Player.class)) { player.disableScrolling(); }

      GameAssets.getLoadedSound("asteroid-explosion").playSound();
      this.eventTriggered = true;

      // Show smiley face in a few ticks
      this.setTimer(WIN_TIMER, WIN_TIMER_TICKS, false);
    }
  }

  @Override
  protected void onDraw(Graphics2D g2d) {
    Score score = this.getGame().getResouce(Score.class);
    g2d.translate(mainView.position.x, mainView.position.y);

    // Draw the score
    g2d.setColor(Color.WHITE);
    Helpers.drawCenteredString(g2d, "Score: " + score.getScore(),
                               new Rectangle(0, 0, mainView.size.width, 50),
                               new Font("Times", Font.ITALIC, 20));
  }
}
