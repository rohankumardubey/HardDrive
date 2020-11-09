package game;

import game.engine.*;
import game.scenes.*;

/**
 * Main entry point for the game
 */
public class Main {
  public static void main(String[] args) {
    try {
      loadAssets();
    } catch (Exception ex) {
      System.out.println("Failed to load game assets: " + ex);
      return;
    }

    Game g = new Game(new GameScene());
    g.setTitle("Car Game");
    g.run();
  }

  private static void loadAssets() throws Exception {
    GameAssets.loadImage("grass-bg", "/assets/backgrounds/grass.png");
    GameAssets.loadImage("sand-bg", "/assets/backgrounds/sand.png");
    GameAssets.loadImage("snow-bg", "/assets/backgrounds/snow.png");
    GameAssets.loadImage("dirt-bg", "/assets/backgrounds/dirt.jpg");
    GameAssets.loadImage("rocky-bg", "/assets/backgrounds/rocky.png");
    GameAssets.loadImage("space-bg", "/assets/backgrounds/space.jpg");
    GameAssets.loadImage("stars-tiled", "/assets/backgrounds/stars-tiled.png");

    GameAssets.loadImage("rocket", "/assets/sprites/rocket.png");
    GameAssets.loadImage("rocket-flame", "/assets/sprites/rocket-flame.png");
    GameAssets.loadImage("bullet", "/assets/sprites/bullet.png");
    GameAssets.loadImage("asteroid", "/assets/sprites/asteroid.png");
    GameAssets.loadImage("smiley", "/assets/sprites/smiley.png");

    GameAssets.loadImage("worm", "/assets/sprites/worm.png");
    GameAssets.loadImage("swooper", "/assets/sprites/swooper.png");
    GameAssets.loadImage("booger", "/assets/sprites/booger.png");

    for (int i = 1; i <= 8; i += 1) {
      for (int j = 1; j <= 8; j += 1) {
        if (i == 8 && j > 6) { continue; }
        GameAssets.loadImage("ant-" + ((i - 1) * 8 + (j)),
                             "/assets/sprites/ant/row-" + i + "-col-" + j + ".png");
      }
    }

    for (int i = 1; i <= 4; i += 1) {
      GameAssets.loadImage("ship-explosion-" + i,
                           "/assets/sprites/small-explosion/small-explosion-" + i + ".png");
    }

    for (int i = 1; i <= 13; i += 1) {
      GameAssets.loadImage("asteroid-explosion-" + i,
                           "/assets/sprites/big-explosion/big-explosion-" + i + ".png");
    }

    GameAssets.loadSound("shoot", "/assets/sounds/shoot.wav");
    GameAssets.loadSound("asteroid-hit", "/assets/sounds/asteroid-hit.wav");
    GameAssets.loadSound("asteroid-explosion", "/assets/sounds/asteroid-explosion.wav");
    GameAssets.loadSound("ship-explosion", "/assets/sounds/ship-explosion.wav");
    GameAssets.loadSound("win", "/assets/sounds/win.wav");

    GameAssets.loadSound("corruption-bgm", "/assets/bgm/data-corruption.wav");
  }
}