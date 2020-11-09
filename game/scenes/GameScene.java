package game.scenes;

import game.engine.*;
import game.entities.*;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class GameScene extends Scene {

  private static final int VIEW_THRESHHOLD = 200;

  public GameScene() {
    super(1000, 800);
    this.mainView.size.setSize(640, 480);

    this.background.addFrames(GameAssets.getLoadedImage("grass-bg"));
    this.background.type = BackgroundType.Tiled;
  }

  @Override
  protected void onCreate() {
    this.createEntity(new Player());
    this.createEntity(new Worm());

    this.setTimer(0, 300, true);
  }

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == 0) { this.createEntity(new Swooper()); }
  }

  @Override
  protected void onStep() {
    Game game = this.getGame();
    if (game.isKeyPressed(Key.ESCAPE)) { game.end(); }
    if (game.hasKeyBeenPressed(Key.R)) { game.setScene(new GameScene()); }

    moveViewToPlayer();
  }

  private void moveViewToPlayer() {
    Player player = this.findFirstEntity(Player.class);
    if (player == null) { return; }

    double leftThreshhold   = player.position.x - VIEW_THRESHHOLD;
    double rightThreshhold  = player.position.x + VIEW_THRESHHOLD;
    double topThreshhold    = player.position.y - VIEW_THRESHHOLD;
    double bottomThreshhold = player.position.y + VIEW_THRESHHOLD;

    if (leftThreshhold < mainView.getLeftBoundary()) {
      mainView.position.x = Math.max(0, leftThreshhold);
    }
    if (rightThreshhold > mainView.getRightBoundary()) {
      mainView.position.x =
          Math.min(this.size.width - mainView.size.width, rightThreshhold - mainView.size.width);
    }

    if (topThreshhold < mainView.getTopBoundary()) {
      mainView.position.y = Math.max(0, topThreshhold);
    }
    if (bottomThreshhold > mainView.getBottomBoundary()) {
      mainView.position.y = Math.min(this.size.height - mainView.size.height,
                                     bottomThreshhold - mainView.size.height);
    }
  }

  @Override
  protected void onDraw(Graphics2D g2d) {
    // TODO Auto-generated method stub
  }
}
