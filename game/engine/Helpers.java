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
   * Generate a random number in a given range
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
}
