package game.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
  public Sprite(BufferedImage... images) {
    super(images);
  }

  /**
   * Draw the sprite centered at position (0,0)
   *
   * @param g2d   Graphics to draw to
   */
  public void draw(Graphics2D g2d) {
    BufferedImage i = this.getImage();
    int leftX       = 0 - (i.getWidth() / 2);
    int topY        = 0 - (i.getHeight() / 2);
    g2d.drawImage(i, leftX, topY, i.getWidth(), i.getHeight(), null);
  }
}
