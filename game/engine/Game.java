package game.engine;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

/**
 * Abstract data type (ADT) for the game engine
 */
public final class Game {
  private Scene scene;
  private Scene newScene;
  private GameFrame frame;
  private Map<Class<? extends Resource>, Resource> resources;

  /**
   * Construct a new game object and give it an initial game state
   * @param initialState  Initial state
   */
  public Game(Scene initialScene) {
    // Configure JFrame
    this.frame = new GameFrame();

    this.scene     = initialScene;
    this.newScene  = null;
    this.resources = new HashMap<>();
  }

  /**
   * Get the current game scene.
   * @return  Current scene
   */
  public Scene getScene() {
    return this.scene;
  }

  /**
   * Set the new game scene. This scene will be changed on the <b>next</b> game tick.
   * If one game tick has multiple calls to setScene(), only the first call will succeed.
   *
   * @param scene  New Scene
   */
  public void setScene(Scene scene) {
    if (this.newScene != null) { this.newScene = scene; }
  }

  /**
   * Set the game title
   * @param newTitle  New game title
   */
  public void setTitle(String newTitle) {
    SwingUtilities.invokeLater(() -> { this.frame.setTitle(newTitle); });
  }

  /**
   * Start the game
   */
  public void run() {
    this.frame.start();
  }

  /**
   * Stop the game and kill the program
   */
  public void end() {
    this.frame.terminate();
  }

  /**
   * Get a global resource given the object class type
   *
   * @param <T>   Type of the resource
   * @param c     Resource class
   * @return      Resource
   */
  public <T extends Resource> T getResouce(Class<T> c) {
    // This is safe because of setResource()
    return (T) this.resources.get(c);
  }

  /**
   * Set a global resource using the resource class as the key.
   * Replaces the current instance of the resource.
   *
   * @param resource  Resource to set
   */
  public void setResource(Resource resource) {
    this.resources.put(resource.getClass(), resource);
  }
}

class GameFrame extends JFrame {
  private static final long serialVersionUID = -8402788080584404131L;
  private GameCanvas canvas;

  public GameFrame() {
    super("Java Game Engine");

    this.canvas = new GameCanvas();
    this.add(canvas, BorderLayout.CENTER);

    // Configure JFrame
    this.setSize(640, 480);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
  }

  public void start() {
    SwingUtilities.invokeLater(() -> { this.setVisible(true); });
  }

  public void terminate() {
    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }
}

class GameCanvas extends JPanel implements ActionListener, KeyListener, MouseInputListener {
  private static final long serialVersionUID = -5915721891511946875L;

  // This swing timer will call actionPerformed every TIMER_DELAY Milliseconds
  private Timer timer;

  public GameCanvas() {
    this.timer = new Timer(1, this);
    timer.start();
  }

  /**
   * Handle the various button and form events
   */
  public void actionPerformed(ActionEvent e) {
    System.out.println("Repaint");
    this.repaint();
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
