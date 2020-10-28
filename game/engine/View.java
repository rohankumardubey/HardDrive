package game.engine;

import java.awt.Dimension;
import java.awt.Point;

/**
 * Represents a rectangular window into a scene
 */
public class View {

  public Point position;
  public Dimension size;

  public View(Point position, Dimension size) {
    this.position = position;
    this.size     = size;
  }

  public int getTopBoundary() {
    return position.y;
  }

  public int getBottomBoundary() {
    return position.y + size.height;
  }

  public int getLeftBoundary() {
    return position.x;
  }

  public int getRightBoundary() {
    return position.x + size.width;
  }
}
