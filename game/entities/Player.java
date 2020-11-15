package game.entities;

import game.engine.*;
import game.entities.walls.*;
import java.util.ArrayList;

/**
 * Rocket ship that you control
 */
public class Player extends HealthEntity {

  // Constants
  private final static int SHIP_PPF    = 15;
  private final static int MAX_BULLETS = 3;

  public Player(Point2d position) {
    super(15);

    // Initialize player size
    this.sprite.addFrames(GameAssets.getLoadedImage("rocket"));
    this.sprite.size.setSize(27, 61);
    this.sprite.setAngleDegrees(90);

    this.mask                    = sprite.getMask();
    this.mask.size.width         = 44;
    this.mask.relativePosition.x = -13;

    this.position.setLocation(position);
  }

  @Override
  protected void onCreate() {}

  @Override
  protected void onDestroy() {
    GameAssets.getLoadedSound("ship-explosion").playSound();
    this.getScene().createEntity(
        new PlayerExplosion(this.position, this.sprite.getRotatedImageDimensions()));
  }

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onStep() {
    this.movePlayer();
    this.clampBoundaries();

    this.createBullet();
  }

  private void movePlayer() {
    Game game            = this.getScene().getGame();
    Point2d tryMovementX = new Point2d();
    Point2d tryMovementY = new Point2d();

    if (game.isKeyPressed(Key.UP)) { tryMovementY.y -= SHIP_PPF; }
    if (game.isKeyPressed(Key.DOWN)) { tryMovementY.y += SHIP_PPF; }
    if (game.isKeyPressed(Key.LEFT)) { tryMovementX.x -= SHIP_PPF; }
    if (game.isKeyPressed(Key.RIGHT)) { tryMovementX.x += SHIP_PPF; }
    if (game.isKeyPressed(Key.D)) { this.destroy(); }

    this.position.add(tryMovementX);
    if (this.collidingWithWall()) { this.position.sub(tryMovementX); }

    this.position.add(tryMovementY);
    if (this.collidingWithWall()) { this.position.sub(tryMovementY); }
  }

  private void clampBoundaries() {
    Scene scene = this.getScene();

    this.position.x = Math.min(Math.max(this.position.x, 0), scene.size.width);
    this.position.y = Math.min(Math.max(this.position.y, 0), scene.size.height);
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

  private boolean collidingWithWall() {
    for (Wall wall: this.getScene().findEntities(Wall.class)) {
      if (this.isCollidingWith(wall)) { return true; }
    }

    return false;
  }
}
