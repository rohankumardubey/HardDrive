package game.scenes;

import game.engine.*;

/**
 * Level 2: Desert
 */
public class Level2 extends GameScene {
  public Level2() {
    super("sand-bg", 2);
  }

  @Override
  protected String[] getLevelLayout() {
    return new String[] {"                                                       ",
                         "                                                       ",
                         "                                                       ",
                         "                                                       ",
                         "      t                                                ",
                         "         t                                             ",
                         "                                                       ",
                         "                                                       ",
                         "                             t t            t t        ",
                         "                                                       ",
                         "                                    s                 ",
                         "                             t t            t t        ",
                         "                                        #              ",
                         "                                                   ",
                         "                             t t            t t        ",
                         "                                                       ",
                         "                                                       ",
                         "                                                       ",
                         "         * *                                           ",
                         "                   T T                                 ",
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
