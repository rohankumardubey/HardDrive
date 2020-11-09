package game.entities;

import game.engine.*;
import game.scenes.MainScene;
import java.util.ArrayList;

/**
 * Rocket ship that you control
 */
public class Player extends Entity {

  // Constants
  private final static int MAX_X       = 640; /* Max X when scrolling */
  private final static int SHIP_PPF    = 15;
  private final static int MAX_BULLETS = 3;

  private final static int FLAME_ANIMATION_SPEED = 2;
  private final static int FRAME_NO_FLAME        = 0;
  private final static int FRAME_WITH_FLAME      = 1;

  // Timers
  private final static int FLAME_TIMER = 0;

  private boolean flameOn         = false;
  private boolean flameTick       = false;
  private boolean screenScrolling = true;

  public Player() {
    super();

    // Initialize player size
    this.sprite.addFrames(GameAssets.getLoadedImage("rocket"));
    this.sprite.addFrames(GameAssets.getLoadedImage("rocket-flame"));
    this.sprite.size.setSize(27, 61);
    this.sprite.setAngleDegrees(90);
    this.mask                    = sprite.getMask();
    this.mask.size.width         = 44;
    this.mask.relativePosition.x = -13;
  }

  /**
   * Indicate that the screen is no longer scrolling
   */
  public void disableScrolling() {
    this.screenScrolling = false;
  }

  @Override
  protected void onCreate() {
    // Move to center of screen
    this.position.setLocation(MAX_X / 2, this.getScene().size.height / 2);

    this.setTimer(FLAME_TIMER, FLAME_ANIMATION_SPEED, true);
  }

  @Override
  protected void onDestroy() {}

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == FLAME_TIMER) { this.flameTick = !this.flameTick; }
  }

  @Override
  protected void onStep() {
    boolean shipMoved = this.movePlayer();
    this.setFlameEnabled(shipMoved);

    this.clampBoundaries();

    this.createBullet();
    this.checkForAsteroidCollision();
  }

  private boolean movePlayer() {
    Game game = this.getScene().getGame();

    boolean shipMoved = false;
    if (game.isKeyPressed(Key.UP)) {
      this.position.y -= SHIP_PPF;
      shipMoved = true;
    }
    if (game.isKeyPressed(Key.DOWN)) {
      this.position.y += SHIP_PPF;
      shipMoved = true;
    }
    if (game.isKeyPressed(Key.LEFT)) {
      this.position.x -= SHIP_PPF;
      shipMoved = true;
    }
    if (game.isKeyPressed(Key.RIGHT)) {
      this.position.x += SHIP_PPF;
      shipMoved = true;
    }

    return shipMoved;
  }

  private void setFlameEnabled(boolean shipMoved) {
    if (shipMoved) {
      if (!this.flameOn) { this.flameTick = true; }
      this.flameOn = true;
    } else {
      this.flameOn = false;
    }

    if (this.flameOn && this.flameTick) {
      this.sprite.setFrameIndex(FRAME_WITH_FLAME);
    } else {
      this.sprite.setFrameIndex(FRAME_NO_FLAME);
    }
  }

  private void clampBoundaries() {
    Scene scene = this.getScene();

    this.position.x = Math.min(Math.max(this.position.x, scene.mainView.getLeftBoundary()),
                               scene.mainView.getRightBoundary());

    this.position.y = Math.min(Math.max(this.position.y, scene.mainView.getTopBoundary()),
                               scene.mainView.getBottomBoundary());
  }

  private void createBullet() {
    Scene scene = this.getScene();
    Game game   = scene.getGame();

    if (game.hasKeyBeenPressed(Key.SPACE)) {
      ArrayList<Bullet> bullets = scene.findEntities(Bullet.class);
      if (bullets.size() < MAX_BULLETS) {
        GameAssets.getLoadedSound("shoot").playSound();
        scene.createEntity(new Bullet(this.position));
      }
    }
  }

  private void checkForAsteroidCollision() {
    Scene scene = this.getScene();

    for (Asteroid asteroid: scene.findEntities(Asteroid.class)) {
      if (this.isCollidingWith(asteroid)) {
        this.destroy();
        GameAssets.getLoadedSound("ship-explosion").playSound();
        scene.createEntity(new PlayerExplosion(this.position));
      }
    }
  }
}
