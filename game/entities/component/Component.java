package game.entities.component;

import game.entities.*;
import game.engine.Vector2d;

/**
 * Represents an electronic component that can be destoryed
 */
public abstract class Component extends HealthEntity {

  public Component(Vector2d fricCoef, double mass, int health) {
    super (fricCoef, mass, health);
    this.drawingPriority = 50;
  }

  public Component (double fricCoef, double mass, int health) {
    this (new Vector2d (fricCoef, fricCoef), mass, health);
  }

  @Override
  protected void onCreate() {}

  @Override
  protected void onTimer(int timerIndex) {}
}
