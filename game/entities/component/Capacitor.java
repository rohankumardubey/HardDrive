package game.entities.component;

import game.engine.*;
import game.scenes.*;
import java.awt.*;

/**
 * Capacitor component
 */
public class Capacitor extends Component {

  private static final      int CAPACITOR_HEALTH   = 20;
  private static final Vector2d CAPACITOR_FRICTION = new Vector2d (5, 10);
  private static final   double CAPACITOR_MASS     = 20;

  private static final Dimension SIZE =
      new Dimension(2 * GameScene.TILE_SIZE, GameScene.TILE_SIZE * 3);

  public Capacitor(Point2d position) {
    super (CAPACITOR_HEALTH, CAPACITOR_FRICTION, CAPACITOR_MASS);

    this.sprite.addFrames(GameAssets.getLoadedImage("capacitor"));

    this.sprite.size.setSize(SIZE);
    this.mask = this.sprite.getMask();
    this.position.set(position);
  }
}
