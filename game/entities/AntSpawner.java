package game.entities;

import game.engine.*;

/**
 * Spawns ants into the game
 */
public class AntSpawner extends Entity {
  private final static int MAX_ANTS        = 20;
  private final static int ANT_SPAWN_DELAY = 10;

  public AntSpawner(Point2d position) {
    super();

    this.position.set(position);
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
