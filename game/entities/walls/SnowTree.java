package game.entities.walls;

import game.engine.*;
import game.scenes.GameScene;

/**
 * IceTree Wall
 */
public class SnowTree extends Wall {

  private static final int TILE_WIDTH  = 2;
  private static final int TILE_HEIGHT = 3;

  public SnowTree(Point2d position) {
    super();

    this.sprite.addFrames(GameAssets.getLoadedImage("snow-tree"));
    this.sprite.size.setSize(GameScene.TILE_SIZE * TILE_WIDTH, GameScene.TILE_SIZE * TILE_HEIGHT);
    this.mask = sprite.getMask();
    this.position.set(position);

    // Adjust the mask
    this.mask.relativePosition.y += GameScene.TILE_SIZE;
    this.mask.size.height -= GameScene.TILE_SIZE;

    // Calculate position from the base of the tree
    this.position.sub(new Point2d(0, GameScene.TILE_SIZE));
  }
}
