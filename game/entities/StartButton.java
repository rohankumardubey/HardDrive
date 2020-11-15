package game.entities;

import game.engine.*;
import java.awt.*;

/**
 * Button to click to start the game
 */
public class StartButton extends Entity {

  private static final int BUTTON_WIDTH  = 300;
  private static final int BUTTON_HEIGHT = 50;

  private static final String BUTTON_TEXT = "Start";
  private static final Font BUTTON_FONT   = new Font("Times", Font.PLAIN, 24);

  private static final Color TEXT_COLOR     = Color.BLACK;
  private static final Color NO_HOVER_COLOR = new Color(0, 255, 0, 128);
  private static final Color ON_HOVER_COLOR = new Color(0, 255, 0, 255);
  private static final Color BORDER_COLOR   = Color.BLACK;

  public StartButton() {
    this.mask = new Mask(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
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
  protected void onStep() {
    Game game = this.getScene().getGame();
    // if (game.isLeftMousePressed() && this.isMouseInside()) { game.setScene(new MainScene()); }
  }

  @Override
  protected void draw(Graphics2D g2d) {
    g2d.translate(this.position.x - BUTTON_WIDTH / 2, this.position.y - BUTTON_HEIGHT / 2);

    if (this.isMouseInside()) {
      g2d.setColor(ON_HOVER_COLOR);
    } else {
      g2d.setColor(NO_HOVER_COLOR);
    }

    g2d.fillRect(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);

    g2d.setColor(BORDER_COLOR);
    g2d.drawRect(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);

    g2d.setColor(TEXT_COLOR);
    Helpers.drawCenteredString(g2d, BUTTON_TEXT, new Rectangle(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT),
                               BUTTON_FONT);
  }
}
