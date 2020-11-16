package game.scenes;

import game.engine.*;
import game.entities.antivirus.*;

/**
 * Level 1: Grass
 */
public class Level1 extends GameScene {

  public Level1() {
    super("grass-bg", 1);
  }

  @Override
  protected void onCreate() {
    super.onCreate();
    this.createEntity(new Amalgamate(new Point2d(100, 100)));
  }

  @Override
  protected String[] getLevelLayout() {
    return new String[] {
        "                                                                                                                                       ",
        "                                                       ",
        "                                                       ",
        "                                                       ",
        "      t                                                ",
        "         t                                0             ",
        "                            T                   R        R          r    r   r    ",
        "                                                       ",
        "                                                ",
        "                            T             0   0 R        ",
        "                                   s                 ",
        "                                                 ",
        "                            T           #     R       ",
        "          r                               0        ",
        "                                                 ",
        "                                                       ",
        "                                                       ",
        "                                                       ",
        "         * *                                           ",
        "                   T T                                                c            C     C     C        c       ",
        "                                                       ",
        "                                                       ",
        "                  T  T                                 ",
        "                                                       ",
        "                                    ^  ^  ^  ^         ",
        "                                                       ",
        "                                                       ",
        "                                                       ",
        "                                                       ",
        "    *                                                  ",
        "                           ! !  ",
        "! ! ! ! ! ! ! ! !                         ",
        "                                                       ",
        "                                                       "};
  }
}
