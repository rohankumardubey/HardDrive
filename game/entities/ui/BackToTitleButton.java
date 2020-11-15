package game.entities.ui;

import game.engine.*;
import game.scenes.*;
import java.awt.*;

/**
 * Go back to the title screen
 */
public class BackToTitleButton extends Button {

  private static final int BUTTON_WIDTH  = 300;
  private static final int BUTTON_HEIGHT = 50;

  public BackToTitleButton(String text) {
    super(text, new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    this.setBackgroundColor(Color.ORANGE);
  }

  @Override
  protected void onCreate() {
    // Move to center of bottom of the screen
    this.position.setLocation(this.getScene().size.width / 2, this.getScene().size.height - 70);
  }

  @Override
  protected void onDestroy() {}

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onClick() {
    this.getScene().getGame().setScene(new TitleScene());
  }
}
