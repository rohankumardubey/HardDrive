package game.engine;

import java.awt.Dimension;

/**
 * Represents a rectangular window into a scene
 */
public class View {

  public Point2d position;
  public Dimension size;

  public View(Point2d position, Dimension size) {
    this.position = position;
    this.size     = size;
  }

  public int getTopBoundary() {
    return (int) position.y;
  }

  public int getBottomBoundary() {
    return (int) position.y + size.height;
  }

  public int getLeftBoundary() {
    return (int) position.x;
  }

  public int getRightBoundary() {
    return (int) position.x + size.width;
  }
}
