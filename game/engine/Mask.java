package game.engine;

import java.awt.Dimension;
import java.awt.Point;

/**
 * Rectangular collision mask.
 *
 * NOTE that masks are computed from the top left point, NOT the center of the mask
 */
public class Mask {
  /** Top left point of the mask relative to the center of the sprite */
  public Point relativePosition;

  /** Size of the rectangular mask */
  public Dimension size;

  public Mask() {
    this.relativePosition = new Point(0, 0);
    this.size             = new Dimension(0, 0);
  }

  public Mask(Point relativePosition, Dimension size) {
    this.relativePosition = relativePosition;
    this.size             = size;
  }

  /**
   * Set relative position based on the size.
   * Assume that the center of the rectangle is 0,0.
   *
   * @param size   Size of the mask
   */
  public Mask(Dimension size) {
    this.size             = size;
    this.relativePosition = new Point(0 - (size.width / 2), 0 - (size.height / 2));
  }

  /**
   * Test if two masks are colliding.
   * This function must know the absolute position of the masks in the room
   *
   * @param other             Other Mask
   * @param thisAbsPosition   Absolute x,y position of this mask
   * @param otherAbsPosition  Absolute x,y position of the other mask
   * @return                  True if the two masks are colliding (overlapping)
   */
  public boolean isCollidingWith(Mask other, Point thisAbsPosition, Point otherAbsPosition) {
    Point thisTopLeft  = (Point) thisAbsPosition.clone();
    Point otherTopLeft = (Point) otherAbsPosition.clone();

    thisTopLeft.translate(this.relativePosition.x, this.relativePosition.y);
    otherTopLeft.translate(other.relativePosition.x, other.relativePosition.y);

    int thisLeft   = thisTopLeft.x;
    int thisRight  = thisTopLeft.x + this.size.width;
    int thisTop    = thisTopLeft.y;
    int thisBottom = thisTopLeft.y + this.size.height;

    int otherLeft   = otherTopLeft.x;
    int otherRight  = otherTopLeft.x + other.size.width;
    int otherTop    = otherTopLeft.y;
    int otherBottom = otherTopLeft.y + other.size.height;

    // Determine non-colliding states
    if ((thisBottom < otherTop) || (thisTop > otherBottom) || (thisRight < otherLeft) ||
        (thisLeft > otherRight)) {
      return false;
    }

    return true;
  }

  /**
   * Test if a point is inside the mask given the absolute position of the point
   *
   * @param point             Point to test
   * @param thisAbsPosition   Absolute position of this mask
   * @return                  True if the point is inside the mask, false otherwise
   */
  public boolean isPointInside(Point point, Point thisAbsPosition) {
    Point thisTopLeft = (Point) thisAbsPosition.clone();
    thisTopLeft.translate(this.relativePosition.x, this.relativePosition.y);

    int thisLeft   = thisTopLeft.x;
    int thisRight  = thisTopLeft.x + this.size.width;
    int thisTop    = thisTopLeft.y;
    int thisBottom = thisTopLeft.y + this.size.height;

    // Determine non-colliding states
    if ((thisBottom < point.y) || (thisTop > point.y) || (thisRight < point.x) ||
        (thisLeft > point.x)) {
      return false;
    }

    return true;
  }
}
