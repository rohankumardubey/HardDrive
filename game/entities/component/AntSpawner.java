package game.entities.component;

import game.engine.*;
import game.entities.antivirus.*;
import game.scenes.*;
import java.awt.*;
import java.awt.image.*;

/**
 * Spawns ants into the game
 */
public class AntSpawner extends Component {
  private final static int ANT_SPAWNER_HEALTH = 30;
  private final static int MAX_ANTS           = 30;
  private final static int ANT_SPAWN_DELAY    = 10;
  private static final Dimension SIZE =
      new Dimension(6 * GameScene.TILE_SIZE, 3 * GameScene.TILE_SIZE);

  public AntSpawner(Point2d position, boolean isVertical) {
    super(ANT_SPAWNER_HEALTH);

    this.sprite.addFrames(randomChip());
    this.sprite.size.setSize(SIZE);
    if (isVertical) { this.sprite.setAngleDegrees(90); }

    this.mask = sprite.getMask();
    this.position.set(position);
  }

  private static BufferedImage randomChip() {
    return GameAssets.getLoadedImage("chip-" + (int) Helpers.randomRange(1, 8));
  }

  @Override
  protected void onCreate() {
    this.setTimer(0, ANT_SPAWN_DELAY, true);
  }

  @Override
  protected void onDestroy() {}

  @Override
  protected void onTimer(int timerIndex) {
    Scene scene = this.getScene();
    if (scene.findEntities(Ant.class).size() < MAX_ANTS) {
      scene.createEntity(new Ant(this.position));
    }
  }

  @Override
  protected void onStep() {}
}
