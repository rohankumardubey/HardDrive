package game.scenes;

import game.engine.*;
import game.entities.*;
import game.entities.walls.*;
import java.awt.*;
import java.awt.image.*;

/**
 * Parent class of all game levels
 */
public abstract class GameScene extends Scene {

  // Size, in pixels, of each tile in the grid
  public static final int TILE_SIZE       = 32;
  public static final Dimension VIEW_SIZE = new Dimension(640, 480);

  private static final int VIEW_THRESHHOLD     = 200;
  private static final int NUM_ZOOM_OUT_FRAMES = 30;

  private int zoomFrameNumber;

  /**
   * Construct a new game scene
   *
   * @param background  Background type for the scene
   */
  public GameScene(String background) {
    super(640, 480);
    this.mainView.size.setSize(VIEW_SIZE);

    this.background.addFrames(GameAssets.getLoadedImage(background));
    this.background.type = BackgroundType.Tiled;

    this.zoomFrameNumber = 0;
  }

  @Override
  protected void onCreate() {
    this.defineLevelTiles(this.getLevelLayout());
    this.setTimer(0, 1, true);
  }

  /**
   * Must override this method to create the level when the view is created
   */
  protected abstract String[] getLevelLayout();

  /**
   * Define the level tiles one-by-one
   */
  private final void defineLevelTiles(String[] rows) {

    // Width of the scene is the maximum row string
    int maxWidth = VIEW_SIZE.width;

    for (int row = 0; row < rows.length; row += 1) {
      String rowString    = rows[row];
      int rowStringLength = rowString.length();

      maxWidth = Math.max(maxWidth, rowStringLength * TILE_SIZE);

      // Create all entities for each row
      for (int col = 0; col < rowStringLength; col += 1) {
        char c = rowString.charAt(col);
        Point2d creationPosition =
            new Point2d((TILE_SIZE / 2) + (col * TILE_SIZE), (TILE_SIZE / 2) + (row * TILE_SIZE));

        Entity e = this.createEntity(c, creationPosition);
        if (e != null) { this.createEntity(e); }
      }
    }

    // Update the size of the scene
    this.size.setSize(maxWidth, rows.length * TILE_SIZE);
  }

  /**
   * Create a single entity in the room
   *
   * @param   type      Character representation of the entity to create
   * @param   position  Position of the entity in the room
   */
  private final Entity createEntity(char type, Point2d position) {
    if (type == ' ') { return null; }
    if (type == 's') { return new Player(position); }
    if (type == 't') { return new Tree(position); }
    if (type == 'T') { return new SnowTree(position); }
    if (type == '!') { return new Cactus(position); }
    if (type == '*') { return new Rock(position); }
    if (type == '^') { return new Pyramid(position); }
    return null;
  }

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == 0) { zoomOut(); }
  }

  /**
   * Zoom-out effect for when the scene first starts
   */
  private void zoomOut() {
    this.zoomFrameNumber += 1;
    if (this.zoomFrameNumber == NUM_ZOOM_OUT_FRAMES) { this.clearTimer(0); }
  }

  @Override
  protected void onStep() {
    Game game = this.getGame();
    if (game.isKeyPressed(Key.ESCAPE)) { game.end(); }
    if (game.hasKeyBeenPressed(Key.R)) { game.setScene(this.restartLevel()); }

    moveViewToPlayer();
  }

  /**
   * Returns a new instance of the current level
   */
  protected abstract GameScene restartLevel();

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
