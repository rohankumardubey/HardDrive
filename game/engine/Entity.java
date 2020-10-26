package game.engine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an object in the scene
 */
public abstract class Entity {

  // Position in the scene
  protected int x, y;

  // Graphic to draw
  protected Sprite sprite;

  // List of timers
  private HashMap<Integer, EntityTimer> timers;

  // Scene object (might be null)
  WeakReference<Scene> scene;

  /**
   * Construct a new entity
   */
  public Entity() {
    this.x      = 0;
    this.y      = 0;
    this.sprite = null;
    this.timers = new HashMap<Integer, EntityTimer>();
    this.scene  = null;
  }

  /**
   * Called on each tick of the game
   */
  abstract protected void onUpdate();

  /**
   * Called whenever a timer is fired
   *
   * @param timerIndex   Index of the timer that fired
   */
  abstract protected void timerFired(int timerIndex);

  /**
   * Default drawing implementation draws the sprite.
   *   Can be overridden to draw more complex objects.
   *
   * @param g2d  Graphics object
   */
  protected void draw(Graphics2D g2d) {
    if (this.sprite == null) { return; }

    final AffineTransform oldTransform = g2d.getTransform();
    g2d.translate(this.x, this.y);

    this.sprite.draw(g2d);

    g2d.setTransform(oldTransform);
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
    this.timers.put(index, new EntityTimer(ticks, looping));
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
   * Called when an entity is added to a scene.
   *  This method should not be called manually.
   *
   * @param scene  Scene object
   */
  void setScene(Scene scene) {
    this.scene = new WeakReference<Scene>(scene);
  }

  /**
   * Get the current scen
   * @return  Scene object
   */
  public final Scene getScene() {
    return this.scene.get();
  }

  /**
   * Method used by the Scene object to update the entity
   */
  void runEntity() {
    this.tickTimers();
  }

  /**
   * Tick all timers and fire any events
   */
  private void tickTimers() {
    final ArrayList<Integer> timersFired = new ArrayList<Integer>();
    for (Map.Entry<Integer, EntityTimer> entry: timers.entrySet()) {
      int index         = entry.getKey();
      EntityTimer timer = entry.getValue();

      if (timer.isRunning()) {
        if (timer.tick()) { timersFired.add(index); }
      }
    }

    for (int index: timersFired) { this.timerFired(index); }
  }
}

/**
 * Plain-old data class for running the entity timer
 */
class EntityTimer {
  private int ticksLeft;
  private int ticks;
  private boolean looping;
  private boolean running;

  public EntityTimer(int ticks, boolean looping) {
    this.ticksLeft = ticks;
    this.ticks     = ticks;
    this.looping   = looping;
    this.running   = true;
  }

  /**
   * Test whether or not a timer is running
   * @return  Timer running
   */
  public boolean isRunning() {
    return this.running;
  }

  /**
   * Used to pause and resume a timer
   * @param running   Set timer running
   */
  public void setRunning(boolean running) {
    this.running = running;
  }

  /**
   * One tick on the timer. Returns true if the timer should fire.
   * @return True if the timer should fire
   */
  public boolean tick() {
    switch (this.ticksLeft) {
      case 0:
        return false; // No more ticks left in timer

      case 1:
        if (this.looping) {
          this.ticksLeft = ticks;
        } else {
          this.ticksLeft = 0;
        }
        return true;

      default:
        this.ticksLeft -= 1;
        return false; // Still waiting
    }
  }
}