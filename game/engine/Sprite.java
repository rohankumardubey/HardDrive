package game.engine;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Represents a game sprite. Can also be transformed.
 */
public final class Sprite {

  private ArrayList<Image> frames;
  private int currentFrame;

  // Transformation
  private int width, height;
  private boolean mirrorVertical, mirrorHorizontal;
  private double angle; // Rotation

  /**
   * Construct a new empty sprite
   */
  public Sprite() {
    this.frames       = new ArrayList<Image>();
    this.currentFrame = 0;

    this.width  = 0;
    this.height = 0;

    this.mirrorVertical   = false;
    this.mirrorHorizontal = false;
    this.angle            = 0.0;
  }

  /**
   * Create a sprite with one or more frames
   * @param images Images for the sprite
   */
  public Sprite(Image... images) {
    this();

    this.addFrames(images);
    this.autoSize();
  }

  /**
   * Create a sprite from image files
   * @param imageFiles File names
   * @throws IOException
   */
  public Sprite(String... imageFiles) throws IOException {
    this();

    this.addFrames(imageFiles);
    this.autoSize();
  }

  /**
   * Add one or more image frames to the sprite
   * @param images Images to add
   */
  public void addFrames(Image... images) {
    for (Image image: images) { this.frames.add(image); }
  }

  /**
   * Load one or more images and add them to the sprite
   * @param imageFiles Image files to add
   * @throws IOException
   */
  public void addFrames(String... imageFiles) throws IOException {
    for (String file: imageFiles) {
      // Load all images from the files
      this.frames.add(ImageIO.read(new File(file)));
    }
  }

  /**
   * Get scaled width of the image
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Get scaled height of the image
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Set scaled width of the image
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Set scaled height of the image
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Set the width and height based on the first image in the frame
   */
  public void autoSize() {
    if (this.frames.size() > 0) {
      this.width  = frames.get(0).getWidth(null);
      this.height = frames.get(0).getHeight(null);
    }
  }

  /**
   * Horizontally mirror the image
   */
  public boolean getMirrorHorizontal() {
    return this.mirrorHorizontal;
  }

  /**
   * Horizontally mirror the image
   */
  public void setMirrorHorizontal(boolean mirror) {
    this.mirrorHorizontal = mirror;
  }

  /**
   * Vertically mirror the image
   */
  public boolean getMirrorVertical() {
    return this.mirrorVertical;
  }

  /**
   * Vertically mirror the image
   */
  public void setMirrorVertical(boolean mirror) {
    this.mirrorVertical = mirror;
  }

  /**
   * Rotation angle of the image
   */
  public double getAngle() {
    return this.angle;
  }

  /**
   * Rotation angle of the image
   */
  public void setAngle(double angle) {
    this.angle = angle;
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
   * Draw the sprite centered at location 0, 0
   *
   * @param g2d   Graphics object to draw to
   */
  public void draw(Graphics2D g2d) {

    final Image frame = this.getCurrentFrameImage();
    if (frame == null) { return; }

    final AffineTransform oldTransform = g2d.getTransform();

    if (this.mirrorHorizontal) { g2d.transform(AffineTransform.getScaleInstance(-1, 1)); }
    if (this.mirrorVertical) { g2d.transform(AffineTransform.getScaleInstance(1, -1)); }
    g2d.transform(AffineTransform.getRotateInstance(this.angle));

    // Draw image centered
    final int topLeftX = 0 - (this.width / 2);
    final int topLeftY = 0 - (this.height / 2);
    g2d.drawImage(frame, topLeftX, topLeftY, this.width, this.height, null);

    g2d.setTransform(oldTransform);
  }
}
