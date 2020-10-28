package game.entities;

/**
 * Used to calculate angular motion of an object
 */
public class AngularMotion {
  public double angleRadians;
  public int speed;

  public AngularMotion() {
    this(0.0, 0);
  }

  public AngularMotion(double angleRadians, int speed) {
    this.angleRadians = angleRadians;
    this.speed        = speed;
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
   * Get change in X for this motion
   */
  public double getDx() {
    return this.speed * Math.cos(this.angleRadians + Math.PI / 2);
  }

  /**
   * Get change in X for this motion
   */
  public double getDy() {
    return this.speed * Math.sin(this.angleRadians - Math.PI / 2);
  }
}
