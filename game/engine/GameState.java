package game.engine;

/**
 * Represents a state in the game
 */
public interface GameState {
  /**
   * Called when the state is pushed onto the stack
   * @param game  Current game object
   */
  default void onStart(Game game) {}

  /**
   * Called when this state is popped off the top of the stack
   * @param game  Current game object
   */
  default void onStop(Game game) {}

  /**
   * Called when this state is replaced by another state on the stack
   *   This is equivalent to a pop followed by a push.
   * @param game  Current game object
   */
  default void onReplace(Game game) {}

  /**
   * Called when another state is pushed on top of this state
   * @param game  Current game object
   */
  default void onPause(Game game) {}

  /**
   * Called when another state is popped from on top of this state
   * @param game  Current game object
   */
  default void onResume(Game game) {}
}
