package game.engine;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Represents an animated image. Can also be transformed.
 */
public class AnimatedImage {

  private ArrayList<Image> frames;
  private int currentFrame;

  // Transformation
  public Dimension size;
  public boolean mirrorVertical, mirrorHorizontal;
  public double angleRadians; // Rotation

  /**
   * Construct a new empty animated image.
   */
  public AnimatedImage() {
    this.frames       = new ArrayList<Image>();
    this.currentFrame = 0;

    this.size             = new Dimension(0, 0);
    this.mirrorVertical   = false;
    this.mirrorHorizontal = false;
    this.angleRadians     = 0.0;
  }

  /**
   * Create a animated image. with one or more frames
   * @param images Images for the animation
   */
  public AnimatedImage(Image... images) {
    this();

    this.addFrames(images);
    this.autoSize();
  }

  /**
   * Add one or more image frames to the animation
   * @param images Images to add
   */
  public void addFrames(Image... images) {
    for (Image image: images) { this.frames.add(image); }
  }

  /**
   * Set the width and height based on the first image in the frame
   */
  public void autoSize() {
    if (this.frames.size() > 0) {
      int width  = frames.get(0).getWidth(null);
      int height = frames.get(0).getHeight(null);
      this.size  = new Dimension(width, height);
    }
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
   * Get number of frames in the animation
   * @return Number of frames
   */
  public int getFrameCount() {
    return this.frames.size();
  }

  /**
   * Go to the next frame in the animation
   */
  public void nextFrame() {
    final int frameCount = this.getFrameCount();
    if (frameCount > 0) {
      // Use modular arithmetic
      this.currentFrame = Math.floorMod(this.currentFrame + 1, frameCount);
    }
  }

  /**
   * Get the current frame index
   * @return Current frame index
   */
  public int getFrameIndex() {
    return this.currentFrame;
  }

  /**
   * Set the frame index in the animation. Rolls around using modular arithmetic.
   * @param newFrame New frame index
   */
  public void setFrameIndex(int newFrame) {
    final int frameCount = this.getFrameCount();
    if (frameCount > 0) {
      // Use modular arithmetic for frames
      this.currentFrame = Math.floorMod(newFrame, frameCount);
    }
  }

  /**
   * Get the current frame image
   * @return Current frame image
   */
  public Image getCurrentFrameImage() {
    final int frameCount = this.getFrameCount();
    if (frameCount == 0) { return null; }
    return this.frames.get(Math.floorMod(this.currentFrame, frameCount));
  }

  /**
   * Compute the rectangular mask for the image
   *
   * @return Rectangular Mask
   */
  public Mask getMask() {
    // TODO: Compute mask based on image angle

    int leftX = 0 - this.size.width / 2;
    int leftY = 0 - this.size.height / 2;
    return new Mask(new Point(leftX, leftY), this.size);
  }

  /**
   * Get the current image as a transformed image.
   * The image will be reflected first and then rotated.
   * The image will then be resized to fit the specifications.
   *
   * @return  Image for the animation
   */
  public BufferedImage getImage() {

    BufferedImage buf =
        new BufferedImage(this.size.width, this.size.height, BufferedImage.TYPE_INT_ARGB);

    final Image frame = this.getCurrentFrameImage();
    if (frame == null) { return buf; }

    Graphics2D g2d = (Graphics2D) buf.getGraphics();

    // Apply image transformations (Reflect before rotation)
    if (this.mirrorHorizontal) { g2d.transform(AffineTransform.getScaleInstance(-1, 1)); }
    if (this.mirrorVertical) { g2d.transform(AffineTransform.getScaleInstance(1, -1)); }
    g2d.rotate(this.angleRadians);

    // TODO: Get rotations working properly

    g2d.drawImage(frame, 0, 0, this.size.width, this.size.height, null);

    return buf;
  }
}
