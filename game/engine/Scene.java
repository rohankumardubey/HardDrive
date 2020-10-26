package game.engine;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

/**
 * Represents a collection of entities
 */
public class Scene extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

  private int width, height;
  private View window;

  public Scene(int width, int height) {
    super();

    this.setSize(new DimensionUIResource(width, height));
    this.setPreferredSize(new DimensionUIResource(width, height));
    this.window = new View(width, height);
  }

  /**
   * Get the window for this scene
   * @return Scene window
   */
  public View getWindow() {
    return this.window;
  }

  /**
   * Draw the scene
   * @param g Graphics object
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Resize to match the window
    Dimension d = this.getSize();
    System.out.println(d);
    if (d.width != this.window.width || d.height != this.window.height) {
      this.setPreferredSize(new Dimension(this.window.width, this.window.height));

      // JFrame frame = (JFrame) this.getParent();
      // frame.pack();

      System.out.println("Resized");
    }

    g.setColor(Color.RED);
    g.drawRect(0, 0, 100, 100);
    g.setColor(Color.BLACK);
    g.drawLine(0, 0, 10, 100);
  }

  /**
   * Keyboard Events
   */
  public void keyPressed(KeyEvent e) {}

  public void keyReleased(KeyEvent e) {}

  public void keyTyped(KeyEvent e) {}

  /**
   * Mouse Events
   */
  public void mousePressed(MouseEvent e) {}

  public void mouseReleased(MouseEvent e) {}

  public void mouseEntered(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void mouseClicked(MouseEvent e) {}

  public void mouseMoved(MouseEvent e) {}

  public void mouseDragged(MouseEvent e) {}
}
