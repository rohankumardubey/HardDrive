package game.engine;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 * Main image for the entity in a game
 */
public final class Sprite extends AnimatedImage {

  /**
   * Construct an empty animated sprite
   */
  public Sprite() {
    super();
  }

  /**
   * Construct a new animated sprite from one or more images
   * @param images Images for the animation
   */
  public Sprite(Image... images) {
    super(images);
  }

  /**
   * Draw the sprite centered at position (0,0)
   *
   * @param g2d   Graphics to draw to
   */
  public void draw(Graphics2D g2d) {
    Image i   = this.getImage();
    int leftX = 0 - i.getWidth(null);
    int topY  = 0 - i.getHeight(null);
    g2d.drawImage(i, leftX, topY, i.getWidth(null), i.getHeight(null), null);
  }
}
