package game.engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a collection of entities
 */
public abstract class Scene {

  // Size of this scene for game objects
  public Dimension size;

  /** Main view into part or all of the scene */
  public View mainView;

  /** Background image for the scene */
  public Background background;
  public Color backgroundColor;
  public Color outsideColor;

  /** List of timers for scene events */
  private Map<Integer, TimerEntry> timers;

  /** List of all entities in the scene */
  private Map<Class<? extends Entity>, Set<Entity>> allEntities;

  /** Entities to create after the next tick */
  private List<Entity> toCreate;

  /** Game object for this scene */
  private WeakReference<Game> game;

  /**
   * Create a new scene with the given dimensions
   * @param width
   * @param height
   */
  public Scene(int width, int height) {
    this.size         = new Dimension(width, height);
    this.mainView     = new View(new Point(0, 0), new Dimension(width, height));
    this.background   = new Background();
    this.outsideColor = new Color(0, 0, 0);
    this.timers       = new HashMap<>();
    this.allEntities  = new HashMap<>();
    this.toCreate     = new ArrayList<>();
    this.game         = null;
  }

  /**
   * Called when the scene is first created
   */
  protected abstract void onCreate();

  /**
   * Called whenever a timer is fired
   *
   * @param timerIndex   Index of the timer that fired
   */
  abstract protected void onTimer(int timerIndex);

  /**
   * Called on each tick of the game
   */
  protected abstract void onStep();

  /**
   * Called after all entities have been drawn to the scene
   *
   * @param g2d  Graphics object for the scene
   */
  protected abstract void onDraw(Graphics2D g2d);

  /**
   * Create a new entity in the scene.
   * The entity will be created on the next game tick.
   *
   * @param e   Entity to create
   */
  public final void createEntity(Entity e) {
    this.toCreate.add(e);
  }

  /**
   * Get all entities for this scene
   *
   * @return  List of all entities
   */
  public final ArrayList<Entity> getAllEntities() {
    ArrayList<Entity> entities = new ArrayList<>();
    for (Set<Entity> set: this.allEntities.values()) { entities.addAll(set); }
    return entities;
  }

  /**
   * Find all entities using the class type as a lookup
   *
   * @param c   Class type of entity to find
   * @return    List of all entities that match the class type
   */
  public final <T extends Entity> ArrayList<T> findEntities(Class<T> c) {
    Set<Entity> set   = this.allEntities.get(c);
    ArrayList<T> list = new ArrayList<T>();
    if (set != null) {
      // This is a safe cast
      for (Entity e: set) { list.add((T) e); }
    }

    return new ArrayList<T>();
  }

  /**
   * Set a timer to fire after a certain number of scene ticks.
   *  Safe to call from within the timerFired() method.
   *
   * @param index    Unique index for the timer
   * @param ticks    Number of ticks to wait. Minimum is 1.
   * @param looping  Should timer loop forever?
   */
  protected final void setTimer(int index, int ticks, boolean looping) {
    if (ticks < 1) { return; }
    this.timers.put(index, new TimerEntry(ticks, looping));
  }

  /**
   * Clear and remove the timer.
   *  Safe to call from within the timerFired() method.
   *
   * @param index   Unique index for the timer
   */
  protected final void clearTimer(int index) {
    this.timers.remove(index);
  }

  /**
   * Tick all timers and fire any events
   */
  final void tickTimers() {
    final ArrayList<Integer> timersFired = new ArrayList<Integer>();
    for (Map.Entry<Integer, TimerEntry> entry: timers.entrySet()) {
      int index        = entry.getKey();
      TimerEntry timer = entry.getValue();

      if (timer.isRunning()) {
        if (timer.tick()) { timersFired.add(index); }
      }
    }

    for (int index: timersFired) { this.onTimer(index); }
  }

  /**
   * Called when the game scene is changed
   *  This method should not be called manually.
   *
   * @param   game    Game object
   */
  final void setGame(Game game) {
    this.game = new WeakReference<Game>(game);
  }

  /**
   * Get the current game object
   * @return  Game object
   */
  public final Game getGame() {
    return this.game.get();
  }

  /**
   * Internal method to create new entities in the game
   */
  final void createEntities() {
    // Add every entity to the map
    for (Entity e: this.toCreate) {
      Set<Entity> set =
          this.allEntities.computeIfAbsent(e.getClass(), (value) -> { return new HashSet<>(); });
      set.add(e);
      e.setScene(this);
    }

    // Clear the list of created entities
    List<Entity> created = this.toCreate;
    this.toCreate        = new ArrayList<>();

    // Call the "onCreate" handler for each new entity
    for (Entity e: created) { e.onCreate(); }
  }

  /**
   * Internal method to destroy entities from the game
   */
  final void destroyEntities() {
    // Destroy the entities marked for deletion
    List<Entity> destroyed = new ArrayList<>();
    for (Set<Entity> set: this.allEntities.values()) {
      List<Entity> toDestroy = new ArrayList<>();
      for (Entity e: set) {
        if (e.isDestroyed()) { toDestroy.add(e); }
      }

      set.removeAll(toDestroy);
      destroyed.addAll(toDestroy);
    }

    // Call the "onDestroy" event handler for each entity
    for (Entity e: destroyed) { e.onDestroy(); }
  }

  /**
   * Internal method to tick all entity timers
   */
  final void tickEntityTimers() {
    for (Set<Entity> set: this.allEntities.values()) {
      for (Entity e: set) { e.tickTimers(); }
    }
  }

  /**
   * Internal method to tick all entity timers
   */
  final void stepEntities() {
    for (Set<Entity> set: this.allEntities.values()) {
      for (Entity e: set) { e.onStep(); }
    }
  }

  /**
   * Draw the scene to the graphics object
   *
   * @param   g2d   Graphics object to draw to
   * @param   size  Current size of the screen
   */
  final void drawScene(Graphics2D g2d, Dimension size) {

    // Only draw to the main view
    BufferedImage viewImage = new BufferedImage(this.mainView.size.width, this.mainView.size.height,
                                                BufferedImage.TYPE_INT_ARGB);
    Graphics2D imgG2d       = (Graphics2D) viewImage.getGraphics();
    imgG2d.translate(-1 * this.mainView.position.x, -1 * this.mainView.position.y);

    // Draw the background color
    imgG2d.setColor(this.backgroundColor);
    imgG2d.fillRect(0, 0, this.size.width, this.size.height);

    // Draw the entire scene to the buffered image
    this.background.draw(imgG2d, this.size);

    for (Set<Entity> set: this.allEntities.values()) {
      for (Entity e: set) { e.draw(imgG2d); }
    }

    this.onDraw(imgG2d);

    // Then draw the image to the screen
    g2d.drawImage(viewImage, 0, 0, size.width, size.height, null);
  }
}
