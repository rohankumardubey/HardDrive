package game.entities.component;

import game.entities.*;

/**
 * Represents an electronic component that can be destoryed
 */
public abstract class Component extends HealthEntity {

  public Component(int health) {
    super(health);
    this.drawingPriority = 50;
  }

  @Override
  protected void onCreate() {}

  @Override
  protected void onTimer(int timerIndex) {}
}