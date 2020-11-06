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
  public Point2d position;

  // Graphic to draw
  public Sprite sprite;

  // Collision mask
  public Mask mask;

  // Whether or not the entity is visible in the room.
  //  Turns off collision and drawing, but does NOT affect other entity events
  public boolean isVisible;

  // Has the entity been destroyed?
  private boolean isDestroyed;

  // List of timers
  private Map<Integer, TimerEntry> timers;

  // Scene object (might be null)
  private WeakReference<Scene> scene;

  /**
   * Construct a new entity
   */
  public Entity() {
    this.position    = new Point2d();
    this.sprite      = new Sprite();
    this.mask        = new Mask();
    this.isVisible   = true;
    this.isDestroyed = false;
    this.timers      = new HashMap<>();
    this.scene       = null;
  }

  /**
   * Called when the entity is first created
   */
  abstract protected void onCreate();

  /**
   * Called when the entity is destroyed
   */
  abstract protected void onDestroy();

  /**
   * Called whenever a timer is fired
   *
   * @param timerIndex   Index of the timer that fired
   */
  abstract protected void onTimer(int timerIndex);

  /**
   * Called on each tick of the game.
   */
  abstract protected void onStep();

  /**
   * Default drawing implementation draws the sprite.
   *   Can be overridden to draw more complex objects.
   *
   * @param g2d  Graphics object
   */
  protected void draw(Graphics2D g2d) {
    if (this.isDestroyed) { return; }
    if (!this.isVisible) { return; }
    if (this.sprite == null) { return; }

    final AffineTransform oldTransform = g2d.getTransform();
    g2d.translate(this.position.x, this.position.y);

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
   * Test if two entities are collising using the entity masks.
   * Invisible entities fail the collision check.
   *
   * @param other   Other entity to check
   * @return        True if they are colliding
   */
  protected boolean isCollidingWith(Entity other) {
    if (!this.isVisible || !other.isVisible) { return false; }
    return this.mask.isCollidingWith(other.mask, this.position, other.position);
  }

  /**
   * Test if the mouse is currently inside the mask of this entity
   *
   * @return  True if the mouse is inside the entity
   */
  protected boolean isMouseInside() {
    Point2d mousePoint = this.scene.get().getGame().getMouseScenePosition();
    return this.mask.isPointInside(mousePoint, this.position);
  }

  /**
   * Called when an entity is added to a scene.
   *  This method should not be called manually.
   *
   * @param scene  Scene object
   */
  final void setScene(Scene scene) {
    this.scene = new WeakReference<Scene>(scene);
  }

  /**
   * Get the current scene object
   * @return  Scene object
   */
  public final Scene getScene() {
    return this.scene.get();
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
   * Mark an entity to be destroyed.
   * The entity won't actually be destroyed until the next game tick.
   */
  public final void destroy() {
    this.isDestroyed = true;
  }

  /**
   * Test if an entity has been destroyed
   * @return  True if entity has been destroyed
   */
  public final boolean isDestroyed() {
    return this.isDestroyed;
  }
}
