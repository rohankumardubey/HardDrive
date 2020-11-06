package game.engine;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

/**
 * Abstract data type (ADT) for the game engine
 */
public final class Game {
  private Scene scene;
  private Scene newScene;
  private Map<Class<? extends Resource>, Resource> resources;

  private Set<Key> pressedKeys;
  private Set<Key> oneTimePresedKeys;
  private boolean leftMousePressed;
  private boolean rightMousePressed;
  private Point2d mouseViewLocation;

  private boolean isFullscreen;
  private GameFrame frame;

  /**
   * Construct a new game object and give it an initial game state
   * @param initialState  Initial state
   */
  public Game(Scene initialScene) {
    this.scene     = initialScene;
    this.newScene  = null;
    this.resources = new HashMap<>();

    this.pressedKeys       = new HashSet<>();
    this.oneTimePresedKeys = new HashSet<>();
    this.oneTimePresedKeys = new HashSet<>();
    this.leftMousePressed  = false;
    this.rightMousePressed = false;
    this.mouseViewLocation = new Point2d(0, 0);

    // Configure JFrame
    this.isFullscreen = false;
    this.frame        = new GameFrame(this);
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
    if (this.newScene == null) { this.newScene = scene; }
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
    this.scene.setGame(this);
    this.scene.onCreate();

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

  /**
   * Tick all of the events inside of the game
   */
  void tick() {
    if (this.newScene != null) {
      this.scene    = this.newScene;
      this.newScene = null;
      this.scene.setGame(this);
      this.scene.onCreate();
    }

    this.scene.tickTimers();
    this.scene.onStep();

    this.scene.createEntities();
    this.scene.destroyEntities();
    this.scene.tickEntityTimers();
    this.scene.stepEntities();
  }

  /**
   * Test if a key is currently pressed
   *
   * @param key    The key to test
   * @return       True if the key is currently pressed, false otherwise
   */
  public boolean isKeyPressed(Key key) {
    return this.pressedKeys.contains(key);
  }

  /**
   * Test if a key has been pressed since the last time this method has been called.
   *
   * @param key    The key to test
   * @return       True if the key has been pressed
   */
  public boolean hasKeyBeenPressed(Key key) {
    return this.pressedKeys.remove(key);
  }

  /**
   * Internal event to mark a key as pressed
   * @param key   The key being pressed
   */
  void setKeyPressed(Key key) {
    this.pressedKeys.add(key);
    this.oneTimePresedKeys.add(key);
  }

  /**
   * Internal event to mark a key as released
   * @param key   The key being released
   */
  void setKeyReleased(Key key) {
    this.pressedKeys.remove(key);
  }

  /**
   * Test whether or not the left mouse button is currently pressed
   *
   * @return    Left mouse pressed
   */
  public boolean isLeftMousePressed() {
    return this.leftMousePressed;
  }

  /**
   * Test whether or not the right mouse button is currently pressed
   *
   * @return    Right mouse pressed
   */
  public boolean isRightMousePressed() {
    return this.rightMousePressed;
  }

  /**
   * Indicate that the left mouse has been clicked or released
   */
  void setLeftMousePressed(boolean pressed) {
    this.leftMousePressed = pressed;
  }

  /**
   * Indicate that the right mouse has been clicked or released
   */
  void setRightMousePressed(boolean pressed) {
    this.rightMousePressed = pressed;
  }

  /**
   * Get the X,Y position of the mouse relative to the main scene view.
   * Point (0,0) of the view is the top left corner of the screen.
   *
   * @return    X,Y location of mouse in the view
   */
  public Point2d getMouseViewPosition() {
    return this.mouseViewLocation;
  }

  /**
   * Get the X,Y position of the mouse relative to the scene.
   * Point (0,0) of the scene is the top left corner.
   *
   * @return   X,Y location of mouse in the scene
   */
  public Point2d getMouseScenePosition() {
    return new Point2d(this.mouseViewLocation.x + this.scene.mainView.position.x,
                       this.mouseViewLocation.y + this.scene.mainView.position.y);
  }

  /**
   * Set the mouse view position.
   * Has to adjust the range of values for the scaled canvas size.
   *
   * @param   newPosition     New position of the mouse inside the view
   * @param   canvasSize      Current scaled size of Canvas
   */
  void setMouseViewPosition(Point2d newPosition, Dimension canvasSize) {
    // Adjust for scaling of canvas
    double newX = Helpers.map(newPosition.x, 0, canvasSize.width, 0, this.scene.size.width);
    double newY = Helpers.map(newPosition.y, 0, canvasSize.height, 0, this.scene.size.height);

    this.mouseViewLocation = new Point2d(newX, newY);
  }

  /**
   * Put the game into fullscreen mode
   */
  public void setFullscreen() {
    this.frame.fullscreen();
    this.isFullscreen = true;
  }

  /**
   * Put the game into windowed mode
   */
  public void setWindowed() {
    this.frame.windowed();
    this.isFullscreen = false;
  }

  /**
   * Put the game into a windowed state of a given size
   *
   * @param size    Size of the window
   */
  public void setWindowed(Dimension size) {
    this.frame.windowed(size);
    this.isFullscreen = false;
  }

  /**
   * Test if the game is currently fullscreen or not
   *
   * @return   Whether or not the game is fullscreen
   */
  public boolean isFullscreen() {
    return this.isFullscreen;
  }

  /**
   * Toggle the game between fullscreen and window mode
   */
  public void toggleFullscreen() {
    if (this.isFullscreen) {
      this.setWindowed();
    } else {
      this.setFullscreen();
    }
  }

  /**
   * Toggle the game between fullscreen and window mode
   * Specify a custom size for windowed mode.
   *
   * @param size    Size of the window
   */
  public void toggleFullscreen(Dimension size) {
    if (this.isFullscreen) {
      this.setWindowed(size);
    } else {
      this.setFullscreen();
    }
  }
}

/**
 * JFrame that actually runs the game
 */
class GameFrame extends JFrame {
  private static final long serialVersionUID         = -8402788080584404131L;
  private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(640, 480);

  GameCanvas canvas;

  public GameFrame(Game game) {
    super("Java Game Engine");

    this.canvas = new GameCanvas(game);
    this.add(canvas, BorderLayout.CENTER);

    // Configure JFrame
    this.setSize(DEFAULT_WINDOW_SIZE);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
  }

  /**
   * Make JFrame go fullscreen
   */
  public void fullscreen() {
    SwingUtilities.invokeLater(() -> {
      this.dispose();
      this.setExtendedState(JFrame.MAXIMIZED_BOTH);
      this.setUndecorated(true);
      this.setVisible(true);
    });
  }

  /**
   * Make JFrame go windowed with default size
   */
  public void windowed() {
    this.windowed(DEFAULT_WINDOW_SIZE);
  }

  /**
   * Make JFrame go windowed with custom size
   */
  public void windowed(Dimension size) {
    SwingUtilities.invokeLater(() -> {
      this.dispose();
      this.setUndecorated(false);
      this.setExtendedState(JFrame.NORMAL);
      this.setSize(size);
      this.setLocationRelativeTo(null);
      this.setVisible(true);
    });
  }

  /**
   * Make the canvas visible and start the timer
   */
  public void start() {
    SwingUtilities.invokeLater(() -> {
      this.setVisible(true);
      this.canvas.startTimer();
    });
  }

  /**
   * Close this JFrame window to terminate the game
   */
  public void terminate() {
    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }
}

/**
 * JPanel that draws the game
 */
class GameCanvas extends JPanel implements ActionListener, KeyListener, MouseInputListener {
  private static final long serialVersionUID = -5915721891511946875L;
  private static final int TIMER_DELAY       = 50;

  /** Reference to the game object to keep the JPanel active */
  private Game game;

  /**
   * This swing timer will call actionPerformed every TIMER_DELAY Milliseconds
   *   This is the main loop for the game
   */
  private Timer timer;

  public GameCanvas(Game game) {
    this.game  = game;
    this.timer = new Timer(TIMER_DELAY, this);

    this.addKeyListener(this);
    this.addMouseListener(this);
    this.addMouseMotionListener(this);

    this.setFocusable(true);
    this.requestFocusInWindow();
  }

  /**
   * Start the timer
   */
  public void startTimer() {
    this.timer.start();
  }

  /**
   * Handle the various form events
   */
  public void actionPerformed(ActionEvent e) {
    this.game.tick();
    this.repaint();
  }

  /**
   * Paint the game to the screen
   */
  @Override
  protected void paintComponent(Graphics g) {
    // Start with the outside color
    g.setColor(this.game.getScene().outsideColor);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());

    // Then draw the game scene
    this.game.getScene().drawScene((Graphics2D) g, this.getSize());
  }

  /**
   * Keyboard Events
   */
  public void keyPressed(KeyEvent e) {
    Key key = Key.findKeyFromCode(e.getKeyCode());
    if (key != null) { this.game.setKeyPressed(key); }
  }

  public void keyReleased(KeyEvent e) {
    Key key = Key.findKeyFromCode(e.getKeyCode());
    if (key != null) { this.game.setKeyReleased(key); }
  }

  public void keyTyped(KeyEvent e) {
    /* Not Used */
  }

  /**
   * Mouse Events
   */
  public void mousePressed(MouseEvent e) {
    switch (e.getButton()) {
      case MouseEvent.BUTTON1:
        this.game.setLeftMousePressed(true);
        break;

      case MouseEvent.BUTTON3:
        this.game.setRightMousePressed(true);
        break;
    }
  }

  public void mouseReleased(MouseEvent e) {
    switch (e.getButton()) {
      case MouseEvent.BUTTON1:
        this.game.setLeftMousePressed(false);
        break;

      case MouseEvent.BUTTON3:
        this.game.setRightMousePressed(false);
        break;
    }
  }

  public void mouseEntered(MouseEvent e) {
    /* Not Used */
  }

  public void mouseExited(MouseEvent e) {
    /* Not Used */
  }

  public void mouseClicked(MouseEvent e) {
    /* Not Used */
  }

  public void mouseMoved(MouseEvent e) {
    this.game.setMouseViewPosition(new Point2d(e.getX(), e.getY()), this.getSize());
  }

  public void mouseDragged(MouseEvent e) {
    this.game.setMouseViewPosition(new Point2d(e.getX(), e.getY()), this.getSize());
  }
}
