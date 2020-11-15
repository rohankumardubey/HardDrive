package game.scenes;

import game.engine.*;
import game.entities.*;
import game.entities.walls.*;
import game.resources.*;
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
  private static final int NUM_FADE_OUT_FRAMES = 30;

  /// Level number this game represents
  private final int level;

  /// Zoom in and zoom out effects
  private int zoomFrameNumber;
  private int fadeFrameNumber;
  private Point2d playerStartPosition;

  /**
   * Construct a new game scene
   *
   * @param background  Background type for the scene
   */
  public GameScene(String background, int level) {
    super(640, 480);
    this.mainView.size.setSize(VIEW_SIZE);

    this.background.addFrames(GameAssets.getLoadedImage(background));
    this.background.type = BackgroundType.Tiled;

    this.level               = level;
    this.zoomFrameNumber     = 0;
    this.fadeFrameNumber     = 0;
    this.playerStartPosition = new Point2d();
  }

  /**
   * Get a new instance of the level scene
   *
   * @param   level   Level to get the scene for
   * @return  Level scene
   */
  public static GameScene getLevelScene(int level) {
    if (level == 1) { return new Level1(); }
    if (level == 2) { return new Level2(); }
    return null;
  }

  /**
   * Must override this method to create the level when the view is created
   */
  protected abstract String[] getLevelLayout();

  /**
   * Create method
   */
  @Override
  protected void onCreate() {
    resetLives();
    defineLevelTiles(this.getLevelLayout());
    this.setTimer(-1, 1, true);
  }

  /**
   * Reset the number of player lives
   */
  private void resetLives() {
    Lives lives = this.getGame().getResouce(Lives.class);
    lives.resetLives();
  }

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
    if (type == 's') {
      this.playerStartPosition = position;
      return new Player(position);
    }

    if (type == ' ') { return null; }
    if (type == 't') { return new Tree(position); }
    if (type == 'T') { return new SnowTree(position); }
    if (type == '!') { return new Cactus(position); }
    if (type == '*') { return new Rock(position); }
    if (type == '^') { return new Pyramid(position); }
    if (type == '#') { return new DataFile(position); }
    return null;
  }

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == -1) { zoomOut(); }
    if (timerIndex == -2) { fadeOut(); }
  }

  /**
   * Zoom-out effect for when the scene first starts
   */
  private void zoomOut() {
    this.zoomFrameNumber += 1;
    if (this.zoomFrameNumber == NUM_ZOOM_OUT_FRAMES) { this.clearTimer(-1); }
  }

  /**
   * Fade out effect when the level is completed
   */
  private void fadeOut() {
    this.fadeFrameNumber += 1;
    if (this.fadeFrameNumber == NUM_FADE_OUT_FRAMES) {
      this.clearTimer(-2);

      Lives lives = this.getGame().getResouce(Lives.class);
      if (lives.playerHasLivesLeft()) {
        // Go to next scene
        this.getGame().setScene(new LevelCompleteScene(GameScene.getLevelScene(this.level + 1)));
      } else {
        // Restart this scene
        this.getGame().setScene(new GameOverScene(GameScene.getLevelScene(this.level)));
      }
    }
  }

  @Override
  protected void onStep() {
    Game game = this.getGame();
    if (game.isKeyPressed(Key.ESCAPE)) { game.end(); }
    if (game.hasKeyBeenPressed(Key.F4)) { game.toggleFullscreen(); }
    if (game.hasKeyBeenPressed(Key.R)) { game.setScene(GameScene.getLevelScene(this.level)); }

    moveViewToPlayer();
    testIfAllDataFilesDestroyed();
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

  /**
   * Test if the level has been completed by destroying all data files
   */
  private void testIfAllDataFilesDestroyed() {
    if ((this.fadeFrameNumber == 0) && (this.zoomFrameNumber == NUM_ZOOM_OUT_FRAMES) &&
        (this.findEntities(DataFile.class).size() == 0)) {

      // Destroy all entities
      for (Entity e: this.getAllEntities()) { e.destroy(); }

      this.fadeFrameNumber = 1;
      this.setTimer(-2, 1, true);
    }
  }

  /**
   * Respawn the player in the room, or end the room
   */
  public void respawnPlayer() {
    Lives lives = this.getGame().getResouce(Lives.class);
    if (!lives.playerHasLivesLeft()) {
      // Destroy all entities
      for (Entity e: this.getAllEntities()) { e.destroy(); }

      // Show fade-out animation
      this.fadeFrameNumber = 1;
      this.setTimer(-2, 1, true);
    } else {
      this.createEntity(new Player(this.playerStartPosition));
    }
  }

  @Override
  protected void onDraw(Graphics2D g2d) {
    drawHud(g2d);
    drawZoomOut(g2d);
    drawFadeOut(g2d);
  }

  /**
   * Draw the game heads-up display
   */
  private void drawHud(Graphics2D g2d) {
    g2d.translate(this.mainView.position.x, this.mainView.position.y);

    // Draw the number of lives left
    Lives lives    = this.getGame().getResouce(Lives.class);
    Rectangle rect = new Rectangle(40, 12, 150, 32);
    g2d.setColor(Color.BLACK);
    g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
    g2d.setColor(Color.WHITE);
    g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
    Helpers.drawCenteredString(g2d, "Lives: " + lives.getLives(), rect,
                               new Font("Times", Font.PLAIN, 28));

    // Draw the data files left
    int dataFilesLeft                 = this.findEntities(DataFile.class).size();
    int width                         = this.mainView.size.width;
    final BufferedImage dataFileImage = GameAssets.getLoadedImage("data-file");
    for (int i = 0; i < dataFilesLeft; i += 1) {
      g2d.drawImage(dataFileImage, width - (12 + 40 * (i + 1)), 12, 32, 32, null);
    }

    g2d.translate(-this.mainView.position.x, -this.mainView.position.y);
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
    imageG2d.drawImage(randomBinaryImage, 0, -10, image.getWidth(), image.getHeight() + 10, null);

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

    // Draw the flashing text
    if (zoomFrameNumber % 10 > 2) {
      Rectangle rect =
          new Rectangle(0, 3 * image.getHeight() / 8, image.getWidth(), image.getHeight() / 4);

      imageG2d.setColor(Color.BLACK);
      imageG2d.fillRect(rect.x, rect.y, rect.width, rect.height);
      imageG2d.setColor(Color.WHITE);
      imageG2d.drawRect(rect.x, rect.y, rect.width - 1, rect.height);

      Helpers.drawCenteredString(imageG2d, "Destroy All Data Files...", rect,
                                 new Font("Times", Font.BOLD, 36));
    }

    // Now draw the image over the view
    g2d.translate(this.mainView.position.x, this.mainView.position.y);
    g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    g2d.translate(-this.mainView.position.x, -this.mainView.position.y);
  }

  /**
   * Draw the cool binary fade out
   */
  private void drawFadeOut(Graphics2D g2d) {
    if (this.fadeFrameNumber == 0) { return; }

    BufferedImage randomBinaryImage =
        GameAssets.getLoadedImage("binary-" + (int) Helpers.randomRange(1, 10));

    g2d.translate(this.mainView.position.x, this.mainView.position.y);
    g2d.setComposite(
        AlphaComposite.SrcOver.derive(((float) this.fadeFrameNumber) / NUM_FADE_OUT_FRAMES));

    g2d.setColor(Color.BLACK);
    g2d.fillRect(0, 0, this.mainView.size.width, this.mainView.size.height);

    Lives lives = this.getGame().getResouce(Lives.class);
    if (lives.playerHasLivesLeft()) {
      g2d.drawImage(randomBinaryImage, 0, -10, this.mainView.size.width,
                    this.mainView.size.height + 10, null);
    }

    g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
    g2d.translate(-this.mainView.position.x, -this.mainView.position.y);
  }
}
