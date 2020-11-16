package game.engine;

/**
 * Two dimensional vector object
 *
 * Much of this code has been taken and modified from the "vecmath" library:
 * https://github.com/hharrison/vecmath/blob/master/src/javax/vecmath/Vector2d.java
 */
public class Vector2d extends Point2d {

  private static final long serialVersionUID = -7463947550551089233L;

  /**
   * Constructs and initializes a Vector2d from the specified xy coordinates.
   *
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public Vector2d(double x, double y) {
    super(x, y);
  }

  /**
   * Constructs and initializes a Vector2d from the specified Vector2d.
   *
   * @param v1 the Vector2d containing the initialization x y data
   */
  public Vector2d(Vector2d v1) {
    super(v1);
  }

  /**
   * Constructs and initializes a Vector2d from the specified Point2d.
   *
   * @param t1 the Point2d containing the initialization x y data
   */
  public Vector2d(Point2d t1) {
    super(t1);
  }

  /**
   * Constructs and initializes a Vector2d to (0,0).
   */
  public Vector2d() {
    super();
  }

  /**
   * Construct a new Vector2d from polar coordinates
   *
   * @param r       Radius from the center
   * @param theta   Angle in radians
   * @return        New point
   */
  public static Vector2d fromPolarCoordinates(double r, double theta) {
    return new Vector2d(Point2d.fromPolarCoordinates(r, theta));
  }

  /**
   * Computes the dot product of the this vector and vector v1.
   *
   * @param v1 the other vector
   */
  public final double dot(Vector2d v1) {
    return (this.x * v1.x + this.y * v1.y);
  }

  /**
   * Returns the length of this vector.
   *
   * @return the length of this vector
   */
  public final double length() {
    return (double) Math.sqrt(this.x * this.x + this.y * this.y);
  }

  /**
   * Returns the squared length of this vector.
   *
   * @return the squared length of this vector
   */
  public final double lengthSquared() {
    return (this.x * this.x + this.y * this.y);
  }

  /**
   * Sets the value of this vector to the normalization of vector v1.
   *
   * @param v1 the un-normalized vector
   */
  public final void normalize(Vector2d v1) {
    double norm = (double) (1.0 / Math.sqrt(v1.x * v1.x + v1.y * v1.y));
    this.x      = v1.x * norm;
    this.y      = v1.y * norm;
  }

  /**
   * Normalizes this vector in place.
   */
  public final void normalize() {
    double norm = (double) (1.0 / Math.sqrt(this.x * this.x + this.y * this.y));
    this.x *= norm;
    this.y *= norm;
  }

  /**
   *   Returns the angle in radians between this vector and the vector
   *   parameter; the return value is constrained to the range [0,PI].
   *
   *   @param v1    the other vector
   *   @return   the angle in radians in the range [0,PI]
   */
  public final double angle(Vector2d v1) {
    double vDot = this.dot(v1) / (this.length() * v1.length());
    if (vDot < -1.0) vDot = -1.0;
    if (vDot > 1.0) vDot = 1.0;
    return ((double) (Math.acos(vDot)));
  }

 /**
  *   Adds the other vector to this vector.
  *
  *   @param v1 the other vector
  */
  public final void add(Vector2d v1) {
    this.x += v1.x;
	 this.y += v1.y;
  }

  /**
   *   Returns the sum of the two vectors.
   *
   *   @param v1 the first vector
   *   @param v2 the second vector
   *   @return   the sum of the vectors
   */
  public static final Vector2d add(Vector2d v1, Vector2d v2) {
    return new Vector2d (v1.x + v2.x, v1.y + v2.y);
  }	

 /**
  *   Scale by the given factor
  *
  *   @param scalar scaling factor
  */
  public final void scaleBy(double scalar) {
    this.x *= scalar;
    this.y *= scalar;
  }
}
