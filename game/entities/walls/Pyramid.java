package game.entities.walls;

import game.engine.*;
import game.scenes.GameScene;
import java.awt.image.BufferedImage;

/**
 * Generic cactus entity
 */
public class Pyramid extends Wall {

  private static final int TILE_WIDTH  = 3;
  private static final int TILE_HEIGHT = 4;

  private static final int NUM_PYRAMIDS = 4;

  public Pyramid(Point2d position) {
    super();

    this.sprite.addFrames(Pyramid.pickRandomPyramid());
    this.sprite.size.setSize(GameScene.TILE_SIZE * TILE_WIDTH, GameScene.TILE_SIZE * TILE_HEIGHT);
    this.mask = sprite.getMask();
    this.mask.relativePosition.y += 2 * GameScene.TILE_SIZE;
    this.mask.size.height -= 2 * GameScene.TILE_SIZE;

    this.position.set(position);
  }

  private static BufferedImage pickRandomPyramid() {
    return GameAssets.getLoadedImage("pyramid-" + (int) Helpers.randomRange(1, NUM_PYRAMIDS));
  }
}
