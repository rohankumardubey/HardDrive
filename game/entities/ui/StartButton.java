package game.entities.ui;

import game.engine.*;
import game.scenes.*;
import java.awt.*;

/**
 * Button to click to start the game
 */
public class StartButton extends Button {

  private static final int BUTTON_WIDTH  = 300;
  private static final int BUTTON_HEIGHT = 50;

  public StartButton() {
    super("Start", new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    this.setBackgroundColor(Color.GREEN);
  }

  @Override
  protected void onCreate() {
    // Move to center of bottom of the screen
    this.position.setLocation(this.getScene().size.width / 2, this.getScene().size.height - 100);
  }

  @Override
  protected void onDestroy() {}

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onClick() {
    this.getScene().getGame().setScene(new LevelSelectScene());
  }
}
