package game.entities.walls;

import game.engine.*;
import game.scenes.GameScene;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Generic cactus entity
 */
public class Cactus extends Wall {

  private static final int TILE_WIDTH  = 2;
  private static final int TILE_HEIGHT = 2;

  private static final int NUM_CACTI = 5;

  public Cactus(Point2d position) {
    super();

    BufferedImage cactus = Cactus.pickRandomCactus();
    this.sprite.addFrames(cactus);
    this.sprite.size.setSize(GameScene.TILE_SIZE * TILE_WIDTH, GameScene.TILE_SIZE * TILE_HEIGHT);

    // Adjust the mask
    int maskWidth  = Helpers.map(40, 0, cactus.getWidth(), 0, GameScene.TILE_SIZE * TILE_WIDTH);
    int maskHeight = Helpers.map(40, 0, cactus.getHeight(), 0, GameScene.TILE_SIZE * TILE_HEIGHT);
    this.mask      = new Mask(new Point2d(-maskWidth / 2, -maskHeight / 2),
                         new Dimension(maskWidth, maskHeight));

    this.position.set(position);
  }

  private static BufferedImage pickRandomCactus() {
    return GameAssets.getLoadedImage("cactus-" + (int) Helpers.randomRange(1, NUM_CACTI));
  }
}
