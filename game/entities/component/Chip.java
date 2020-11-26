package game.entities.component;

import game.engine.*;
import game.scenes.*;
import java.awt.*;
import java.awt.image.*;

/**
 * Integrated Circuit Chip
 */
public class Chip extends Component {

  private final static int CHIP_HEALTH      = 50;
  private final static double CHIP_FRICTION = 2;
  private final static double CHIP_MASS     = 20;
  private final static Dimension SIZE =
      new Dimension(6 * GameScene.TILE_SIZE, 3 * GameScene.TILE_SIZE);

  public Chip(Point2d position, boolean isVertical) {
    super(CHIP_HEALTH, CHIP_FRICTION, CHIP_MASS);

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
