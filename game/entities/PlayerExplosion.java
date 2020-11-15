package game.entities;

import game.engine.*;
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
    Lives lives     = scene.getGame().getResouce(Lives.class);
    lives.playerKilled();

    scene.respawnPlayer();
  }
}
