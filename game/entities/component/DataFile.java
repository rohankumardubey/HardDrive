package game.entities.component;

import game.engine.*;
import game.scenes.*;

/**
 * Data file to destroy
 */
public class DataFile extends Component {

  private static final int TILE_SIZE = 2;
  private static final int HEALTH    = 18;

  public DataFile(Point2d position) {
    super(HEALTH);

    this.sprite.addFrames(GameAssets.getLoadedImage("data-file"));
    this.sprite.size.setSize(GameScene.TILE_SIZE * TILE_SIZE, GameScene.TILE_SIZE * TILE_SIZE);
    this.mask = sprite.getMask();
    this.position.set(position);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ((GameScene) this.getScene()).dataFileDestroyed();
  }
}
