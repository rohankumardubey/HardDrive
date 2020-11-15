package game.entities.ui;

import game.engine.*;
import game.scenes.*;
import java.awt.*;

/**
 * Go back to the title screen
 */
public class BackToLevelSelectButton extends Button {

  private static final int BUTTON_WIDTH  = 300;
  private static final int BUTTON_HEIGHT = 50;

  public BackToLevelSelectButton(String text, Point2d position) {
    super(text, new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    this.setBackgroundColor(Color.ORANGE);
    this.position.set(position);
  }

  @Override
  protected void onCreate() {}

  @Override
  protected void onDestroy() {}

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onClick() {
    GameAssets.getLoadedSound("corruption-bgm").stopSound();
    this.getScene().getGame().setScene(new LevelSelectScene());
  }
}
