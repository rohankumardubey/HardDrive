package game.scenes;

/**
 * Level 3: Dirt
 */
public class Level3 extends GameScene {
  public Level3() {
    super("dirt-bg", 3);
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
