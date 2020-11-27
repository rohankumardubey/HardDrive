package game.entities;

import game.engine.*;
import game.entities.component.*;
import game.resources.*;
import game.scenes.*;
import java.awt.*;

/**
 * Explosion for the player
 */
public class PlayerExplosion extends BinaryExplosion {

  public PlayerExplosion(Point2d position, Dimension size) {
    super(position, size);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    GameScene scene = (GameScene) this.getScene();

    // Only kill the player if the last data file hasn't been destroyed
    //   Otherwise, the player was destroyed by destroying the last data file
    if (scene.findEntities(DataFile.class).size() > 0) {
      Lives lives = scene.getGame().getResouce(Lives.class);
      lives.playerKilled();
      scene.respawnPlayer();
    }
  }
}
