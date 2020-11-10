package game.scenes;

import game.engine.*;
import game.entities.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.*;

public class GameScene extends Scene {

  private static final int VIEW_THRESHHOLD     = 200;
  private static final int NUM_ZOOM_OUT_FRAMES = 30;

  private int zoomFrameNumber;

  public GameScene() {
    super(5000, 8000);
    this.mainView.size.setSize(640, 480);

    this.background.addFrames(GameAssets.getLoadedImage("circuit-bg"));
    this.background.type = BackgroundType.Tiled;

    this.zoomFrameNumber = 0;
  }

  @Override
  protected void onCreate() {
    this.createEntity(new Player());
    this.createEntity(new Worm());
    this.createEntity(new AntSpawner(new Point2d(150, 150)));
    this.createEntity(new AntSpawner(new Point2d(400, 400)));
    this.createEntity(new AntSpawner(new Point2d(900, 900)));

    this.setTimer(0, 1, true);
    this.setTimer(1, 300, true);
  }

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == 0) { zoomOut(); }
    if (timerIndex == 1) { this.createEntity(new Swooper()); }
  }

  private void zoomOut() {
    this.zoomFrameNumber += 1;
    if (this.zoomFrameNumber == NUM_ZOOM_OUT_FRAMES) { this.clearTimer(0); }
  }

  @Override
  protected void onStep() {
    Game game = this.getGame();
    if (game.isKeyPressed(Key.ESCAPE)) { game.end(); }
    if (game.hasKeyBeenPressed(Key.R)) { game.setScene(new GameScene()); }

    moveViewToPlayer();
  }

  /**
   * Move the view around to scroll with the player
   */
  private void moveViewToPlayer() {
    Player player = this.findFirstEntity(Player.class);
    if (player == null) { return; }

    double leftThreshhold   = player.position.x - VIEW_THRESHHOLD;
    double rightThreshhold  = player.position.x + VIEW_THRESHHOLD;
    double topThreshhold    = player.position.y - VIEW_THRESHHOLD;
    double bottomThreshhold = player.position.y + VIEW_THRESHHOLD;

    if (leftThreshhold < mainView.getLeftBoundary()) {
      mainView.position.x = Math.max(0, leftThreshhold);
    }
    if (rightThreshhold > mainView.getRightBoundary()) {
      mainView.position.x =
          Math.min(this.size.width - mainView.size.width, rightThreshhold - mainView.size.width);
    }

    if (topThreshhold < mainView.getTopBoundary()) {
      mainView.position.y = Math.max(0, topThreshhold);
    }
    if (bottomThreshhold > mainView.getBottomBoundary()) {
      mainView.position.y = Math.min(this.size.height - mainView.size.height,
                                     bottomThreshhold - mainView.size.height);
    }
  }

  @Override
  protected void onDraw(Graphics2D g2d) {
    drawZoomOut(g2d);
  }

  /**
   * Draw the cool binary zoom out effect at the start of the level
   */
  private void drawZoomOut(Graphics2D g2d) {
    if (this.zoomFrameNumber == NUM_ZOOM_OUT_FRAMES) { return; }

    BufferedImage image = new BufferedImage(this.mainView.size.width, this.mainView.size.height,
                                            BufferedImage.TYPE_INT_ARGB);

    BufferedImage randomBinaryImage =
        GameAssets.getLoadedImage("binary-" + (int) Helpers.randomRange(1, 10));

    Graphics2D imageG2d = (Graphics2D) image.getGraphics();
    imageG2d.setColor(Color.BLACK);
    imageG2d.fillRect(0, 0, image.getWidth(), image.getHeight());
    imageG2d.drawImage(randomBinaryImage, 0, 0, image.getWidth(), image.getHeight(), null);

    // "Chisel out" the image from the center
    int centerX      = image.getWidth() / 2;
    int centerY      = image.getHeight() / 2;
    int chiselWidth  = (zoomFrameNumber * this.mainView.size.width) / NUM_ZOOM_OUT_FRAMES;
    int chiselHeight = (zoomFrameNumber * this.mainView.size.height) / NUM_ZOOM_OUT_FRAMES;

    int leftX = centerX - chiselWidth / 2;
    int topY  = centerY - chiselHeight / 2;

    // Chisel out the viewport
    imageG2d.setComposite(AlphaComposite.Clear);
    imageG2d.fillRect(leftX, topY, chiselWidth, chiselHeight);
    imageG2d.setComposite(AlphaComposite.SrcOver);

    // Now draw the image over the view
    g2d.translate(this.mainView.position.x, this.mainView.position.y);
    g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    g2d.translate(-this.mainView.position.x, -this.mainView.position.y);
  }
}
