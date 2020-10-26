package game.engine;

/**
 * Represents a rectangular window into a scene
 */
public class View {

  int width, height;
  int x, y;

  public View(int width, int height) {
    this.width  = width;
    this.height = height;
    this.x      = 0;
    this.y      = 0;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public void setWidth(int newWidth) {
    this.width = newWidth;
  }

  public void setHeight(int newHeight) {
    this.height = newHeight;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public void setX(int newX) {
    this.x = newX;
  }

  public void setY(int newY) {
    this.y = newY;
  }
}
