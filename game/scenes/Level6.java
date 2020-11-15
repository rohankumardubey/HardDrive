package game.scenes;

/**
 * Level 6: Circuit
 */
public class Level6 extends GameScene {
  public Level6() {
    super("circuit-bg", 6);
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
