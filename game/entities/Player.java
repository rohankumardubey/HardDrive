package game.entities;

import game.engine.*;

public class Player extends Entity {

  public Player() {
    super();

    this.sprite.addFrames(GameAssets.getLoadedImage("boxy"));
    this.mask = this.sprite.getMask();
    this.position.setLocation(100, 100);
  }

  @Override
  protected void onCreate() {}

  @Override
  protected void onDestroy() {
    // TODO Auto-generated method stub
  }

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onStep() {
    Game g = this.getScene().getGame();
    if (g.isKeyPressed(Key.UP)) { this.position.y -= 5; }
    if (g.isKeyPressed(Key.DOWN)) { this.position.y += 5; }
    if (g.isKeyPressed(Key.LEFT)) { this.position.x -= 5; }
    if (g.isKeyPressed(Key.RIGHT)) { this.position.x += 5; }

    if (this.isMouseInside() && g.isLeftMousePressed()) {
      GameAssets.getLoadedSound("explosion").playSound();
      this.destroy();
    }
  }
}
