package game;

import game.engine.*;
import game.scenes.TestScene;

public class Main {
  public static void main(String[] args) {
    try {
      loadAssets();
    } catch (Exception ex) {
      System.out.println("Failed to load game assets: " + ex);
      return;
    }

    Game g = new Game(new TestScene());
    g.run();
  }

  private static void loadAssets() throws Exception {
    GameAssets.loadImage("smiley", "/assets/images/Smiley.png");
    GameAssets.loadImage("boxy", "/assets/images/Boxy.png");
    GameAssets.loadSound("coin", "/assets/sounds/coin.wav");
    GameAssets.loadSound("explosion", "/assets/sounds/explosion.wav");
  }
}