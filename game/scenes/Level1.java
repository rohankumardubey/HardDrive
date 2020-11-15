package game.scenes;

/**
 * Level 1: Grass
 */
public class Level1 extends GameScene {

  public Level1() {
    super("grass-bg");
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
                         "                                                       ",
                         "                                                       ",
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

  /**
   * Restart the current level
   */
  protected GameScene restartLevel() {
    return new Level1();
  }
}
