package game.entities.walls;

import game.engine.*;
import game.scenes.GameScene;

/**
 * Rock Wall
 */
public class Rock extends Wall {

  private static final int TILE_WIDTH  = 2;
  private static final int TILE_HEIGHT = 2;

  public Rock(Point2d position) {
    super();

    this.sprite.addFrames(GameAssets.getLoadedImage("rock"));
    this.sprite.size.setSize(GameScene.TILE_SIZE * TILE_WIDTH, GameScene.TILE_SIZE * TILE_HEIGHT);
    this.mask = sprite.getMask();
    this.position.set(position);
  }
}
