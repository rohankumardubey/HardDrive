package game.entities.ui;

import game.engine.*;
import java.awt.*;

/**
 * Abstract button class
 */
public abstract class Button extends Entity {

  private static final Font BUTTON_FONT = new Font("Times", Font.PLAIN, 24);

  protected String text;
  protected Dimension size;

  // Adjustable button properties
  protected Color textColor   = Color.BLACK;
  protected Color borderColor = Color.BLACK;
  protected Color noHoverColor;
  protected Color hoverColor;

  private boolean hasMouseReleased = false;

  public Button(String text, Dimension size) {
    super();

    this.text = text;
    this.size = new Dimension(size);
    this.mask = new Mask(new Point2d(-size.width / 2, -size.height / 2),
                         new Dimension(size.width, size.height));

    this.setBackgroundColor(Color.WHITE);
  }

  @Override
  protected void onStep() {
    Game game = this.getScene().getGame();
    if (!game.isLeftMousePressed()) { this.hasMouseReleased = true; }
    if (this.hasMouseReleased && game.isLeftMousePressed() && this.isMouseInside()) {
      this.onClick();
    }
  }

  /**
   * Method to call when the button is clicked
   */
  protected abstract void onClick();

  /**
   * Sets both colors using the default opacity
   */
  protected void setBackgroundColor(Color color) {
    this.hoverColor   = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
    this.noHoverColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 128);
  }

  @Override
  protected void draw(Graphics2D g2d) {
    g2d.translate(this.position.x - this.size.width / 2, this.position.y - this.size.height / 2);

    if (this.isMouseInside()) {
      g2d.setColor(this.hoverColor);
    } else {
      g2d.setColor(this.noHoverColor);
    }

    g2d.fillRect(0, 0, this.size.width, this.size.height);

    g2d.setColor(this.borderColor);
    g2d.drawRect(0, 0, this.size.width, this.size.height);

    g2d.setColor(this.textColor);
    Helpers.drawCenteredString(g2d, this.text,
                               new Rectangle(0, 0, this.size.width, this.size.height), BUTTON_FONT);
  }
}
