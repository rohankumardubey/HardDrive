package game.engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Background for the scene object
 */
public final class Background extends AnimatedImage {
  public BackgroundType type;
  public Color backgroundColor;

  /**
   * Construct an empty animated background
   */
  public Background() {
    super();
    this.type            = BackgroundType.Tiled;
    this.backgroundColor = new Color(0, 0, 0);
  }

  /**
   * Construct a new solid color background
   *
   * @param color  Background color
   */
  public Background(Color color) {
    super();
    this.type            = BackgroundType.None;
    this.backgroundColor = color;
  }

  /**
   * Construct a new animated sprite from one or more images
   * @param images Images for the animation
   */
  public Background(BufferedImage... images) {
    super(images);
    this.type            = BackgroundType.Tiled;
    this.backgroundColor = new Color(0, 0, 0);
  }

  /**
   * Draw the background to the graphics object.
   * Replaces any existing content on the image.
   *
   * @param g2d   Graphics to draw to
   * @param size  Size of the image
   */
  public void draw(Graphics2D g2d, Dimension size) {
    g2d.setColor(this.backgroundColor);
    g2d.fillRect(0, 0, size.width, size.height);

    switch (this.type) {
      case None:
        break;

      case Image:
        Image i = this.getImage();
        g2d.drawImage(i, 0, 0, i.getWidth(null), i.getHeight(null), null);
        break;

      case Tiled:
        tileImage(g2d, size, this.getImage());
        break;

      case Stretched:
        g2d.drawImage(this.getImage(), 0, 0, size.width, size.height, null);
        break;
    }
  }

  /**
   * Helper method to draw a tiled image to the graphics object
   *
   * @param g2d   Graphics object
   * @param size  Size of the graphics object
   * @param i     Image to draw
   */
  static private void tileImage(Graphics2D g2d, Dimension size, BufferedImage i) {
    final int imageWidth  = i.getWidth();
    final int imageHeight = i.getHeight();

    for (int tileX = 0; tileX < size.width; tileX += imageWidth) {
      for (int tileY = 0; tileY < size.height; tileY += imageHeight) {
        int subimgWidth  = Math.min(imageWidth, size.width - tileX);
        int subimgHeight = Math.min(imageHeight, size.height - tileY);

        BufferedImage subimg = i.getSubimage(0, 0, subimgWidth, subimgHeight);
        g2d.drawImage(subimg, tileX, tileY, subimg.getWidth(), subimg.getHeight(), null);
      }
    }
  }
}
