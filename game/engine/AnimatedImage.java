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

  private ArrayList<BufferedImage> frames;
  private int currentFrame;

  // Transformation
  public Dimension size;
  public boolean mirrorVertical, mirrorHorizontal;

  /**
   * Construct a new empty animated image.
   */
  public AnimatedImage() {
    this.frames       = new ArrayList<>();
    this.currentFrame = 0;

    this.size             = new Dimension(1, 1);
    this.mirrorVertical   = false;
    this.mirrorHorizontal = false;
  }

  /**
   * Create a animated image. with one or more frames
   * @param images Images for the animation
   */
  public AnimatedImage(BufferedImage... images) {
    this();

    this.addFrames(images);
    this.autoSize();
  }

  /**
   * Add one or more image frames to the animation
   * @param images Images to add
   */
  public void addFrames(BufferedImage... images) {
    for (BufferedImage image: images) { this.frames.add(image); }
    this.autoSize();
  }

  /**
   * Set the width and height based on the first image in the frame
   */
  public void autoSize() {
    if (this.getFrameCount() > 0) {
      int width  = frames.get(0).getWidth();
      int height = frames.get(0).getHeight();
      this.size  = new Dimension(width, height);
    }
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
  public BufferedImage getCurrentFrameImage() {
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

    final BufferedImage frame = this.getCurrentFrameImage();
    if (frame == null) { return buf; }

    Graphics2D g2d = (Graphics2D) buf.getGraphics();

    if (this.mirrorHorizontal) {
      g2d.scale(-1.0, 1.0);
      g2d.translate(-this.size.width, 0);
    }
    if (this.mirrorVertical) {
      g2d.scale(1.0, -1.0);
      g2d.translate(0, -this.size.height);
    }

    g2d.drawImage(frame, 0, 0, this.size.width, this.size.height, null);

    return buf;
  }
}
