package game.entities.component;

import game.engine.*;
import game.scenes.*;
import java.awt.*;
import java.awt.image.*;

/**
 * Integrated Circuit Chip
 */
public class Chip extends Component {

  private static final int CHIP_HEALTH = 30;
  private static final Dimension SIZE =
      new Dimension(6 * GameScene.TILE_SIZE, 3 * GameScene.TILE_SIZE);

  public Chip(Point2d position, boolean isVertical) {
    super(CHIP_HEALTH);

    this.sprite.addFrames(randomChip());
    this.sprite.size.setSize(SIZE);
    if (isVertical) { this.sprite.setAngleDegrees(90); }

    this.mask = sprite.getMask();
    this.position.set(position);
  }

  private static BufferedImage randomChip() {
    return GameAssets.getLoadedImage("chip-" + (int) Helpers.randomRange(1, 8));
  }
}
