package game.engine;

/**
 * Plain-old data class for running the timers in Scene and Entity
 */
class TimerEntry {
  private int ticksLeft;
  private int ticks;
  private boolean looping;
  private boolean running;

  public TimerEntry(int ticks, boolean looping) {
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