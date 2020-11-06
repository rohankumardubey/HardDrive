package game.engine;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Main image for the entity in a game
 */
public final class Sprite extends AnimatedImage {

  /** Angle of rotation for the sprite */
  public double angleRadians;

  /**
   * Construct an empty animated sprite
   */
  public Sprite() {
    super();
    this.angleRadians = 0.0;
  }

  /**
   * Construct a new animated sprite from one or more images
   * @param images Images for the animation
   */
  public Sprite(BufferedImage... images) {
    super(images);
    this.angleRadians = 0.0;
  }

  /**
   * Get the rotation angle of the image in radians
   */
  public double getAngleRadians() {
    return this.angleRadians;
  }

  /**
   * Get the rotation angle of the image in degrees
   */
  public double getAngleDegrees() {
    return Math.toDegrees(this.angleRadians);
  }

  /**
   * Set the rotation angle of the image in radians
   */
  public void setAngleRadians(double radians) {
    this.angleRadians = radians;
  }

  /**
   * Set the rotation angle of the image in degrees
   */
  public void setAngleDegrees(double degrees) {
    this.angleRadians = Math.toRadians(degrees);
  }

  /**
   * Add to the rotation angle of the image in degrees
   */
  public void addAngleDegrees(double degrees) {
    this.angleRadians += Math.toRadians(degrees);
  }

  /**
   * Add to the rotation angle of the image in radians
   */
  public void addAngleRadians(double radians) {
    this.angleRadians += radians;
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
    g2d.rotate(this.angleRadians, 0, 0);
    g2d.drawImage(i, leftX, topY, i.getWidth(), i.getHeight(), null);
  }

  /**
   * Compute the rectangular mask for the image.
   *  Resizes the rectangle to match the image rotation.
   *
   * @return Rectangular Mask
   */
  @Override
  public Mask getMask() {
    Dimension d = this.getRotatedImageDimensions();
    int leftX   = 0 - d.width / 2;
    int leftY   = 0 - d.height / 2;
    return new Mask(new Point2d(leftX, leftY), d);
  }

  /**
   * Compute the image dimensions for a rotated image
   *
   * @return  Image dimensions
   */
  private final Dimension getRotatedImageDimensions() {

    double sin = Math.abs(Math.sin(this.angleRadians));
    double cos = Math.abs(Math.cos(this.angleRadians));

    int newWidth  = (int) Math.floor(this.size.width * cos + this.size.height * sin);
    int newHeight = (int) Math.floor(this.size.height * cos + this.size.width * sin);

    return new Dimension(newWidth, newHeight);
  }
}
