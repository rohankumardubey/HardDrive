package game;

import game.engine.*;
import game.scenes.TestScene;
import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    Game g = new Game(new TestScene());
    try {
      g.loadImage("smiley", "/assets/images/Smiley.png");
      g.loadImage("boxy", "/assets/images/Boxy.png");
    } catch (IOException ex) {
      System.out.println("Failed to load game assets: " + ex);
      return;
    }

    g.run();
  }
}