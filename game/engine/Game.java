package game.engine;

import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import javax.swing.*;

/**
 * Abstract data type (ADT) for the game engine
 */
public final class Game {
  private Stack<GameState> state;
  private Scene scene;
  private GameFrame frame;

  /**
   * Construct a new game object and give it an initial game state
   * @param initialState  Initial state
   */
  public Game(GameState initialState) {
    // Configure JFrame
    this.frame = new GameFrame();

    // Configure state
    this.state = new Stack<GameState>();
    this.state.push(initialState);
    initialState.onStart(this);

    // Initialize the scene
    this.scene = null;
  }

  /**
   * Push another state onto the stack
   * @param newState  New state to push onto the stack
   */
  public void pushState(GameState newState) {
    this.state.peek().onPause(this);
    this.state.push(newState);
    newState.onStart(this);
  }

  /**
   * Pop the current state of the stack.
   * If there are no more states remaining, then the game is terminated.
   */
  public void popState() {
    this.state.peek().onStart(this);
    this.state.pop();
    if (this.state.isEmpty()) {
      // Kill the game
      this.end();
    } else {
      this.state.peek().onResume(this);
    }
  }

  /**
   * Replace the top state on the stack with a new state.
   *   This is equivalent to a pop followed by a push.
   *
   * @param newState  New current game state
   */
  public void replaceState(GameState newState) {
    this.state.peek().onReplace(this);
    this.state.pop();
    this.state.push(newState);
    newState.onStart(this);
  }

  /**
   * Get the current game state on top of the stack
   * @return Current game state
   */
  public GameState getCurrentState() {
    return this.state.peek();
  }

  /**
   * Get the current game scene.
   * @return  Current scene
   */
  public Scene getScene() {
    return this.scene;
  }

  /**
   * Set the current game scene
   * @param scene  New Scene
   */
  public void setScene(Scene scene) {
    this.scene = scene;
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

class GameCanvas extends JPanel implements ActionListener {
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
}
