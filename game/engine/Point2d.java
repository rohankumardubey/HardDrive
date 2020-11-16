package game.engine;

import java.awt.geom.Point2D;

/**
 * Two-Dimensional Point
 *
 * Much of this code has been taken and modified from the "vecmath" library:
 * https://github.com/hharrison/vecmath/blob/master/src/javax/vecmath/Point2d.java
 */
public class Point2d extends Point2D.Double {

  private static final long serialVersionUID = -4231366033820754285L;

  /**
   * Constructs and initializes a Point2d from the specified xy coordinates.
   *
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public Point2d(double x, double y) {
    super(x, y);
  }

  /**
   * Constructs and initializes a Point2d from the specified Point2d.
   *
   * @param t1 the Point2d containing the initialization x y data
   */
  public Point2d(Point2d t1) {
    super(t1.x, t1.y);
  }

  /**
   * Constructs and initializes a Point2d to (0,0).
   */
  public Point2d() {
    super();
  }

  /**
   * Construct a new Point2d from polar coordinates
   *
   * @param r       Radius from the center
   * @param theta   Angle in radians
   * @return        New point
   */
  public static Point2d fromPolarCoordinates(double r, double theta) {
    double x = r * Math.cos(theta);
    double y = r * Math.sin(theta);
    return new Point2d(x, y);
  }

  /**
   * Sets the value of this point to the specified xy coordinates.
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public final void set(double x, double y) {
    super.setLocation(x, y);
  }

  /**
   * Sets the value of this point to the value of the Point2d argument.
   *
   * @param t1 the point to be copied
   */
  public final void set(Point2d t1) {
    super.setLocation(t1);
  }

  /**
   * Sets the value of this point to the vector sum of points t1 and t2.
   *
   * @param t1 the first point
   * @param t2 the second point
   */
  public final void add(Point2d t1, Point2d t2) {
    this.x = t1.x + t2.x;
    this.y = t1.y + t2.y;
  }

  /**
   * Sets the value of this point to the vector sum of itself and point t1.
   *
   * @param t1 the other point
   */
  public final void add(Point2d t1) {
    this.x += t1.x;
    this.y += t1.y;
  }

  /**
   * Sets the value of this point to the vector sum of itself and x, y
   *
   * @param dx  Change in x
   * @param dy  Change in y
   */
  public final void translate(double dx, double dy) {
    this.x += dx;
    this.y += dy;
  }

  /**
   * Sets the value of this point to the vector difference of
   * point t1 and t2 (this = t1 - t2).
   *
   * @param t1 the first point
   * @param t2 the second point
   */
  public final void sub(Point2d t1, Point2d t2) {
    this.x = t1.x - t2.x;
    this.y = t1.y - t2.y;
  }

  /**
   * Sets the value of this point to the vector difference of
   * itself and point t1 (this = this - t1).
   *
   * @param t1 the other vector
   */
  public final void sub(Point2d t1) {
    this.x -= t1.x;
    this.y -= t1.y;
  }

  /**
   * Sets the value of this point to the negation of point t1.
   *
   * @param t1 the source vector
   */
  public final void negate(Point2d t1) {
    this.x = -t1.x;
    this.y = -t1.y;
  }

  /**
   * Negates the value of this vector in place.
   */
  public final void negate() {
    this.x = -this.x;
    this.y = -this.y;
  }

  /**
   * Sets the value of this point to the scalar multiplication
   * of point t1.
   *
   * @param s the scalar value
   * @param t1 the source point
   */
  public final void scale(double s, Point2d t1) {
    this.x = s * t1.x;
    this.y = s * t1.y;
  }

  /**
   * Sets the value of this point to the scalar multiplication
   * of itself.
   *
   * @param s the scalar value
   */
  public final void scale(double s) {
    this.x *= s;
    this.y *= s;
  }

  /**
   * Sets the value of this point to the scalar multiplication
   * of point t1 and then adds point t2 (this = s*t1 + t2).
   *
   * @param s the scalar value
   * @param t1 the point to be multipled
   * @param t2 the point to be added
   */
  public final void scaleAdd(double s, Point2d t1, Point2d t2) {
    this.x = s * t1.x + t2.x;
    this.y = s * t1.y + t2.y;
  }

  /**
   * Sets the value of this point to the scalar multiplication
   * of itself and then adds point t1 (this = s*this + t1).
   *
   * @param s the scalar value
   * @param t1 the point to be added
   */
  public final void scaleAdd(double s, Point2d t1) {
    this.x = s * this.x + t1.x;
    this.y = s * this.y + t1.y;
  }

  /**
   * Returns true if all of the data members of Point2d t1 are
   * equal to the corresponding data members in this Point2d.
   *
   * @param t1  the vector with which the comparison is made
   * @return  true or false
   */
  public boolean equals(Point2d t1) {
    try {
      return (this.x == t1.x && this.y == t1.y);
    } catch (NullPointerException e2) { return false; }
  }

  /**
   * Returns true if the Object t1 is of type Point2d and all of the
   * data members of t1 are equal to the corresponding data members in
   * this Point2d.
   *
   * @param t1  the object with which the comparison is made
   * @return  true or false
   */
  @Override
  public boolean equals(Object t1) {
    try {
      Point2d t2 = (Point2d) t1;
      return (this.x == t2.x && this.y == t2.y);
    } catch (NullPointerException e2) { return false; } catch (ClassCastException e1) {
      return false;
    }
  }

  /**
   * Returns true if the L-infinite distance between this point
   * and point t1 is less than or equal to the epsilon parameter,
   * otherwise returns false.  The L-infinite
   * distance is equal to MAX[abs(x1-x2), abs(y1-y2)].
   *
   * @param t1  the point to be compared to this point
   * @param epsilon  the threshold value
   * @return  true or false
   */
  public boolean epsilonEquals(Point2d t1, double epsilon) {
    double diff;

    diff = x - t1.x;
    if (java.lang.Double.isNaN(diff)) return false;
    if ((diff < 0 ? -diff : diff) > epsilon) return false;

    diff = y - t1.y;
    if (java.lang.Double.isNaN(diff)) return false;
    if ((diff < 0 ? -diff : diff) > epsilon) return false;

    return true;
  }

  /**
   * Returns a string that contains the values of this Point2d.
   * The form is (x,y).
   *
   * @return the String representation
   */
  @Override
  public String toString() {
    return ("(" + this.x + ", " + this.y + ")");
  }

  /**
   *  Clamps the point parameter to the range [low, high] and
   *  places the values into this point.
   *
   *  @param min   the lowest value in the point after clamping
   *  @param max  the highest value in the point after clamping
   *  @param t   the source point, which will not be modified
   */
  public final void clamp(double min, double max, Point2d t) {
    if (t.x > max) {
      x = max;
    } else if (t.x < min) {
      x = min;
    } else {
      x = t.x;
    }

    if (t.y > max) {
      y = max;
    } else if (t.y < min) {
      y = min;
    } else {
      y = t.y;
    }
  }

  /**
   *  Clamps the minimum value of the point parameter to the min
   *  parameter and places the values into this point.
   *
   *  @param min   the lowest value in the point after clamping
   *  @param t   the source point, which will not be modified
   */
  public final void clampMin(double min, Point2d t) {
    if (t.x < min) {
      x = min;
    } else {
      x = t.x;
    }

    if (t.y < min) {
      y = min;
    } else {
      y = t.y;
    }
  }

  /**
   *  Clamps the maximum value of the point parameter to the max
   *  parameter and places the values into this point.
   *
   *  @param max   the highest value in the point after clamping
   *  @param t   the source point, which will not be modified
   */
  public final void clampMax(double max, Point2d t) {
    if (t.x > max) {
      x = max;
    } else {
      x = t.x;
    }

    if (t.y > max) {
      y = max;
    } else {
      y = t.y;
    }
  }

  /**
   *  Sets each component of the point parameter to its absolute
   *  value and places the modified values into this point.
   *  @param t   the source point, which will not be modified
   */
  public final void absolute(Point2d t) {
    x = Math.abs(t.x);
    y = Math.abs(t.y);
  }

  /**
   *  Clamps this point to the range [low, high].
   *
   *  @param min  the lowest value in this point after clamping
   *  @param max  the highest value in this point after clamping
   */
  public final void clamp(double min, double max) {
    if (x > max) {
      x = max;
    } else if (x < min) {
      x = min;
    }

    if (y > max) {
      y = max;
    } else if (y < min) {
      y = min;
    }
  }

  /**
   *  Clamps the minimum value of this point to the min parameter.
   *
   *  @param min   the lowest value in this point after clamping
   */
  public final void clampMin(double min) {
    if (x < min) { x = min; }
    if (y < min) { y = min; }
  }

  /**
   *  Clamps the maximum value of this point to the max parameter.
   *
   *  @param max   the highest value in the point after clamping
   */
  public final void clampMax(double max) {
    if (x > max) { x = max; }
    if (y > max) { y = max; }
  }

  /**
   *  Sets each component of this point to its absolute value.
   */
  public final void absolute() {
    x = Math.abs(x);
    y = Math.abs(y);
  }

  /**
   *  Linearly interpolates between points t1 and t2 and places the
   *  result into this point:  this = (1-alpha)*t1 + alpha*t2.
   *  @param t1  the first point
   *  @param t2  the second point
   *  @param alpha  the alpha interpolation parameter
   */
  public final void interpolate(Point2d t1, Point2d t2, double alpha) {
    this.x = (1 - alpha) * t1.x + alpha * t2.x;
    this.y = (1 - alpha) * t1.y + alpha * t2.y;
  }

  /**
   *  Linearly interpolates between this point and point t1 and
   *  places the result into this point:  this = (1-alpha)*this + alpha*t1.
   *  @param t1  the first point
   *  @param alpha  the alpha interpolation parameter
   */
  public final void interpolate(Point2d t1, double alpha) {
    this.x = (1 - alpha) * this.x + alpha * t1.x;
    this.y = (1 - alpha) * this.y + alpha * t1.y;
  }

  /**
   * Get the polar distance from 0,0
   *
   * @return  Polar distance
   */
  public final double polarDistance() {
    return this.distance(0.0, 0.0);
  }

  /**
   * Get the polar angle from theta = 0
   *
   * @return Polar angle
   */
  public final double polarAngle() {
    return Math.atan2(this.y, this.x);
  }

  /**
   * Set the angle of this point without changing the distance
   *
   * @param theta   New angle
   */
  public final void setAngle(double theta) {
    double distance = this.polarDistance();
    this.x          = distance * Math.cos(theta);
    this.y          = distance * Math.sin(theta);
  }

  /**
   * Set the distance of this point without changing the angle
   *
   * @param distance    New distance
   */
  public final void setDistance(double distance) {
    double theta = this.polarAngle();
    this.x       = distance * Math.cos(theta);
    this.y       = distance * Math.sin(theta);
  }

  /**
   * Rotate the point without changing the distance
   *
   * @param r   Amount to rotate by
   */
  public final void rotateBy(double r) {
    this.setAngle(this.polarAngle() + r);
  }
}
