package game.scenes;

/**
 * Level 1: Grass
 */
public class Level1 extends GameScene {

  public Level1() {
    super("grass-bg", 1);
  }

  @Override
  protected String[] getLevelLayout() {
    return new String[] {
        "                                                                                                                                       ",
        "                             #                         ",
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
        "                            T           #     R                                     A",
        "          r                               0        ",
        "                                                 ",
        "     #                                                 ",
        "                                                       ",
        "                                                       ",
        "         * *                                           ",
        "                   T T                                                c            C     C     C        c       ",
        "                                         #             ",
        "                                                       ",
        "                  T  T                                 ",
        "                                                       ",
        "                                    ^  ^  ^  ^         ",
        "                                                       ",
        "                                                                    #",
        "    #                                                  ",
        "                                                       ",
        "    *                                                  ",
        "                           ! !  ",
        "! ! ! ! ! ! ! ! !                         ",
        "                                                       ",
        "                                                       "};
  }
}
