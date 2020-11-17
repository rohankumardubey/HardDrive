package game.engine;

import java.awt.*;

/**
 * Utility class with various helper methods
 */
public class Helpers {

  /** Can't be instantiated */
  private Helpers() {}

  /**
   * Draw a String centered in the middle of a Rectangle.
   *
   * Helper method from:
   *
   * https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
   *
   * @param g The Graphics instance.
   * @param text The String to draw.
   * @param rect The Rectangle to center the text in.
   */
  static public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
    FontMetrics metrics = g.getFontMetrics(font);

    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

    g.setFont(font);
    g.drawString(text, x, y);
  }

  /**
   * Draw a health bar. The health should be between 0.0 and 1.0
   */
  static public void drawHealthBar(Graphics g, double health, Rectangle rect, Color fullHealth,
                                   Color noHealth) {

    health      = clamp(health, 0, 1);
    Color color = interpolateColor(fullHealth, noHealth, health);

    // Inside bar
    g.setColor(color);
    g.fillRect(rect.x, rect.y, (int) (rect.width * health), rect.height);

    // Outline
    g.setColor(Color.BLACK);
    g.drawRect(rect.x, rect.y, rect.width, rect.height);
  }

  /**
   * Interpolate between two colors
   */
  static public Color interpolateColor(Color one, Color two, double interpolation) {
    double red   = linearInterpolation(one.getRed(), two.getRed(), interpolation);
    double green = linearInterpolation(one.getGreen(), two.getGreen(), interpolation);
    double blue  = linearInterpolation(one.getBlue(), two.getBlue(), interpolation);
    return new Color((float) red / 255, (float) green / 255, (float) blue / 255);
  }

  /**
   * Map one range of integers to another
   *
   * From: https://stackoverflow.com/questions/7505991/arduino-map-equivalent-function-in-java
   */
  public static int map(int x, int inMin, int inMax, int outMin, int outMax) {
    return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
  }

  /**
   * Map one range of doubles to another
   *
   * From: https://stackoverflow.com/questions/7505991/arduino-map-equivalent-function-in-java
   */
  public static double map(double x, double inMin, double inMax, double outMin, double outMax) {
    return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
  }

  /**
   * Clamp the input between two values
   *
   * @param input   Value to clamp
   * @param min     Minimum input
   * @param max     Maximum input
   * @return        Clamped value
   */
  public static double clamp(double input, double min, double max) {
    return Math.min(Math.max(input, min), max);
  }

  /**
   * Do a linear interpolation between two numbers.
   * The blend value should be between 0.0 and 1.0
   */
  public static double linearInterpolation(double one, double two, double blend) {
    blend = clamp(blend, 0, 1);
    return one * blend + two * (1 - blend);
  }

  /**
   * Generate a random number in a given range
   *   Both ends are inclusive
   */
  public static double randomRange(double min, double max) {
    return Math.random() * (max - min) + min;
  }

  /**
   * Test if a value is between two other values.
   * The order of the two numbers does not matter.
   *
   * @param value   Value to test
   * @param num1    First value in range
   * @param num2    Second value in range
   * @return        True if value is between the two numbers
   */
  public static boolean isBetween(double value, double num1, double num2) {
    return Math.min(num1, num2) <= value && Math.max(num1, num2) >= value;
  }

  /**
   * Test if an angel is between two other angles
   *
   * @param angle   Angle to test
   * @param a       First angle to test against
   * @param b       Second angle to test against
   * @return        True if the angle is between the two angles
   */
  public static boolean isAngleBetween(double angle, double a, double b) {
    final double maxDistance = Math.PI / 2; // 90 degrees

    return Math.abs(normalizeAngle(a - angle)) < maxDistance &&
        Math.abs(normalizeAngle(b - angle)) < maxDistance;
  }

  /**
   * Normalize angle between -PI and +PI radians
   *
   * @param angle     Angle to normalize
   * @return          Normalized angle
   */
  public static double normalizeAngle(double angle) {
    return Math.atan2(Math.sin(angle), Math.cos(angle));
  }

  /**
   * Comptue the manhattan distance between two points
   */
  public static double manhattanDistance(Point one, Point two) {
    return Math.abs(two.x - one.x) + Math.abs(two.y - one.y);
  }
}
