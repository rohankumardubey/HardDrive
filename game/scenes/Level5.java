package game.scenes;

/**
 * Level 5: Rocky
 */
public class Level5 extends GameScene {

  public Level5() {
    super("rocky-bg", 5);
	 this.setFriction (0.88);
  }

  @Override
  protected String[] getLevelLayout() {
    return new String[] {
        "                                      *                                                                                                                                                                 ",
        "                                                                                                                              *                                                                         ",
        "                                      *                                                                                       *                                                                         ",
        "                                               t                      t                      t                      t                                                                                   ",
        "                                      *                                                                                       *                             T                      T   T                ",
        "                #                                                                                                                                                                                       ",
        "                                      * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *                                                                         ",
        "                                                                                                                                                                                                        ",
        "                                      *                                                                                       *                                                    T   T                ",
        "     T T                                                                                                                                                                                                ",
        "                                      *                                                                                       *                                                                         ",
        "                                                                                                                                                                                                        ",
        "                                      *                                                                                       R                                       *                                 ",
        "                        T T                                                                                                                                                                             ",
        "                                      *                       0                              |                                                                        *                                 ",
        "                                                                                                                              R                                                                         ",
        "              |                                                                                                                              T T T                    *                                 ",
        "                                                                                                                                                                                                        ",
        "                                                                                                                              R              T T T                    *                                 ",
        "                                                                                                                                                                                       #                ",
        "                                                                                                                              *                                       *                                 ",
        "                                                                                                                                                                                                        ",
        "              |                                                                  *                                            *                                       *                                 ",
        "                                                                                                                                                                                                        ",
        "                                                                                 *                                            *                                       *                                 ",
        "                                                                                                                                                                                                        ",
        "                                                       T                         *                      0                                                                                               ",
        "                                                                                                                                                                                                        ",
        "              -                                                                  *                                                                                                                      ",
        "                                                                                                                                                                                                        ",
        "                                                                                 *                                        r   r                              R                                          ",
        "* * * * * * * * * * * * * * * * * * * * *                                                                                   r                                                                           ",
        "                                                                                 *                                        r   r                                                                         ",
        "                                                                                                                                                                                                        ",
        "      -                                                                          *         T T                                                                                                          ",
        "                                                                                                                                       T                                                                ",
        "                                                                                 *                                                                                                                      ",
        "                                                                                                                                                                                                        ",
        "                                                           |                     * * * * * * * r  r  r  r  * * * * * * * * * * * *                                     * * * * * * * * * * * * * * * * *",
        "                                                                                                                                                                                                        ",
        "                           T                                                     *                                                                                                                      ",
        "                                                                                                                                                                                                        ",
        "                                                                         A       *                                                                                                                      ",
        "                                                                                                                                                                                          t t t         ",
        "                                                                                 *                                                               |             a                                        ",
        "                  T T                                                                                                                                                                                   ",
        "                                         * * * * * * * * * * * * * * * * * * * * *                                  T                                                                                   ",
        "                                                                                                                                                                                                        ",
        "                  T T                                                                                                                                                                                   ",
        "                                                                                                                                                                                                        ",
        "                                                                                                                                                                                                        ",
        "                                                                                                                                                                                                        ",
        "          0 0                                                                                                                                                                T                          ",
        "                                                                                                                                                                                                        ",
        "                                                                                                                                                                                                        ",
        "         0  0                                                                                                                                                                                           ",
        "                                                                                                                              * * * * * * * * * * * * * * * * * * * * * *                               ",
        "                                                                                                                                                                                                        ",
        "                                 a                             T                                                           *                                                                            ",
        "                                                                                                                                                                                                        ",
        "                                                                                                                        *                                                                               ",
        "* * * * * r  r  r  r  r  r  r * * * * * *                                         * * * * * * * * * * * * * * * * * * *                                                                                 ",
        "                                                                                                                                                                                                        ",
        "                                                                                                                                                                                                        ",
        "                                                                                                                                                                                                        ",
        "                                                                                                                                    * * * * * * * * * * * * *                                           ",
        "                                                                                                                                                                                           T            ",
        "                                                                                                                                                                                                        ",
        "                                                                                                                                                                                                        ",
        "                                                                                                                                                                                                        ",
        "        a                               *                                        *                                 * * * *                                 T T T                                        ",
        "                                                                                                                           *                                                                            ",
        "                                        *                                        *                                 * * * *   * * * * * * * * * * * * * * * T T T * * * * * *                    * * * * ",
        "                                                                                                                                                                                                        ",
        "                                        *                                        *                                 *                                                                                    ",
        "                                                                                                    s                                                                                                   ",
        "                                        *                                        *                                 T                                                                                    ",
        "                                                                                                                                                                                                        ",
        "                                        *                            T           *                                 T                                                                                    ",
        "                         0 0                                                                                                                                             t t t                          ",
        "                                        *                                        *                                 *                                                                                    ",
        "                                                                                                                                                 *                                                     *",
        "                         0 0            *                                        *                                 *                                                                                    ",
        "                                                                                                                                                 *                                                 * * *",
        "                                        *                       0 0                                                                                                                                     ",
        "                                                                                                                                                 *                                             * * * * *",
        "                                        *                                                                                                                                                               ",
        "                                                    T                                                                                            *                 * * * * * * * * * * * * * * * * * * *",
        "                  #                     *                                                                                                                                                               ",
        "                                                                                                                                                                                                        ",
        "                                        *                                                                                                                                                               ",
        "                                                                                                                                                                                           A            ",
        "                                        *                                                                                                                                                               ",
        "                                                                                 * * * * * * * r  r  r  r  r  r  r * * * * * * * *                                                                      ",
        "* * * * * * * * * * * * * * * * * * * * *                                                                                                                                                               ",
        "                                                                                 *                                                                                                                      ",
        "                                        *                                                                                                                                                               ",
        "                                                                                 *                                                                                                                      ",
        "                                        *                                                                                                                  t   t    t                                   ",
        "                                                              T                  *                                                                                                                      ",
        "                                        *                                                     #                                                                                                         ",
        "                                                                                 *                                                                                                                      ",
        "                                        *                                                                                                                                                               ",
        "                                                                                 *                                                                                                                      ",
        "                                        R                                                                                                                                                               ",
        "                                                                                 * * * * * * * * * * r   r  r  r  r  * * * * * * * * * * * * * * * * * * * * * * * * *                     * * * * * * *",
        "                                                                                                                                                                                                        ",
        "                                        R                                                                                                                            *                                  ",
        "                                                                                                                                                       -                                                ",
        "                                                                                                    R                                                                *                                  ",
        "                                        R                                                                                                                                                               ",
        "              A                                                                                                                                                      *            T                     ",
        "                                                                                                                                                                                                        ",
        "                                        R                                                                                                                            *                                  ",
        "                                                                                                            * * * * * * * * * * * *                                                                     ",
        "                                                                                 *                                                                                   *                                  ",
        "                                        R                                                                                         *                                                                     ",
        "                                                                                 *                                                                                   *                                  ",
        "                                        *                                                                                         *      R                                                              ",
        "                                                                                 *                                                                                                                      ",
        "                                        *                                                                                         *                                                                     ",
        "                                                                                 *                                                                                                                      ",
        "                                                                                               T T T                              *                                                                     ",
        "                                                                                 *                                                                                                                      ",
        "    t                                                                                          T T T                              *                                                                     ",
        "                                                        R                        *                                                                          R                                           ",
        "                                                                                                                                  *                                  *                        T         ",
        "                                                                                 *                                                                                                                      ",
        "                                                                                                                                  *                                  *                                  ",
        "                                                                                 *                                                                                                                      ",
        "                                                                                                                                  *                                  *                                  ",
        "                                        *                                        * * * *  r  r  r  r  r *  *                                                                                            ",
        "                                                                                                                                  *                                  *                                  ",
        "                                        *                                                                                                                                                               ",
        "                                                                                                                                  *                                  *                                  ",
        "                                        *                                                                                                                                                               ",
        "                                                                                                                                  *                                  *                                  ",
        "                                        *                                                                                                          #                             T                      ",
        "                                                                                                                                  *                                  *                                  ",
        "                                        *                                                                                                                                                               ",
        "                                                             r   r  r                                                             *                                  *                                  ",
        "                                        *                                                                                                                                                               ",
        "                                                                                * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *                                 ",
        "                                        *                                                                                                                                                  T            ",
        "                                                                                                                                                                                                        ",
        "                  #                     *                                                                                                                                                               ",
        "                                                                                          T T                          R               R             R                                                  ",
        "                                        *                                                                                                                                                               ",
        "                                               t  t                                       T T                                                                              T                            ",
        "                                        *                                                                                                                                                               ",
        "                                                                                                                                                                                                        ",
    };
  }
}
