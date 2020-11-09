package game.entities;

import game.engine.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Cool binary explosion effect
 */
public class BinaryExplosion extends Entity {

  private static final double NUM_SHRINK_FRAMES = 15;

  private static final Font GRAPHICS_FONT   = new Font("monospace", Font.PLAIN, 8);
  private static final Color GRAPHICS_COLOR = new Color(0, 255, 0);
  private static final int NUM_IMAGES       = 10;
  private static final int IMAGE_WIDTH      = 100;
  private static final int IMAGE_HEIGHT     = 100;

  private static final ArrayList<BufferedImage> images = generateAllRandomImages();

  // Shrink the explosion
  private double shrinkWidth, shrinkHeight;
  private double currentWidth, currentHeight;

  public BinaryExplosion(Point2d position, Dimension size) {
    super();

    this.shrinkWidth   = size.width / NUM_SHRINK_FRAMES;
    this.shrinkHeight  = size.height / NUM_SHRINK_FRAMES;
    this.currentWidth  = size.getWidth();
    this.currentHeight = size.getHeight();

    for (BufferedImage i: images) { this.sprite.addFrames(i); }
    this.sprite.size.setSize(size);

    this.position.set(position);
  }

  /**
   * Generate a static set of random images to use for the binary explosion effect
   */
  private static ArrayList<BufferedImage> generateAllRandomImages() {
    ArrayList<BufferedImage> images = new ArrayList<>();
    for (int i = 0; i < NUM_IMAGES; i += 1) { images.add(generateRandomBinaryImage()); }
    return images;
  }

  /**
   * Generate a single random binary image
   */
  private static BufferedImage generateRandomBinaryImage() {
    BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);

    FontMetrics metrics = image.getGraphics().getFontMetrics(GRAPHICS_FONT);
    int numCharWidth    = (int) Math.ceil((double) image.getWidth() / metrics.stringWidth("0"));
    int numCharHeight   = (int) Math.ceil((double) image.getHeight() / metrics.getHeight());
    int charHeight      = metrics.getHeight();

    Graphics2D g2d = (Graphics2D) image.getGraphics();
    g2d.setFont(GRAPHICS_FONT);
    g2d.setColor(GRAPHICS_COLOR);

    for (int i = 0; i < numCharHeight; i += 1) {
      g2d.drawString(randomBinaryString(numCharWidth), 0, i * charHeight);
    }

    return image;
  }

  /**
   * Generate a random string of "0" and "1" of a given length
   */
  private static String randomBinaryString(int numCharWidth) {
    String str = "";
    for (int i = 0; i < numCharWidth; i += 1) {
      if (Math.random() < 0.5) {
        str += "0";
      } else {
        str += "1";
      }
    }
    return str;
  }

  @Override
  protected void onCreate() {}

  @Override
  protected void onDestroy() {}

  @Override
  protected void onTimer(int timerIndex) {}

  @Override
  protected void onStep() {
    this.shrinkExplosion();
    if (currentWidth <= 1 || currentHeight <= 1) {
      this.sprite.size.setSize(1, 1);
      this.destroy();
    }
  }

  /**
   * Make explosion get smaller in size
   */
  private void shrinkExplosion() {
    this.currentWidth -= shrinkWidth;
    this.currentHeight -= shrinkHeight;
    this.sprite.size.setSize(currentWidth, currentHeight);
    this.sprite.nextFrame();
  }
}
