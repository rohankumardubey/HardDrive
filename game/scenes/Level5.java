package game.scenes;

/**
 * Level 5: Rocky
 */
public class Level5 extends GameScene {
  public Level5() {
    super("rocky-bg", 5);
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
