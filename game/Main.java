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

    Game g = new Game(new MainScene());
    g.run();
  }

  private static void loadAssets() throws Exception {
    GameAssets.loadImage("space-bg", "/assets/backgrounds/space.jpg");
    GameAssets.loadImage("stars-tiled", "/assets/backgrounds/stars-tiled.png");

    GameAssets.loadImage("rocket", "/assets/sprites/rocket.png");
    GameAssets.loadImage("rocket-flame", "/assets/sprites/rocket-flame.png");
    GameAssets.loadImage("bullet", "/assets/sprites/bullet.png");
    GameAssets.loadImage("asteroid", "/assets/sprites/asteroid.png");
    GameAssets.loadImage("smiley", "/assets/sprites/smiley.png");

    for (int i = 1; i <= 4; i += 1) {
      GameAssets.loadImage("ship-explosion-" + i, "/assets/sprites/ship-explosion-" + i + ".png");
    }

    for (int i = 1; i <= 13; i += 1) {
      GameAssets.loadImage("asteroid-explosion-" + i,
                           "/assets/sprites/asteroid-explosion-" + i + ".png");
    }

    GameAssets.loadSound("shoot", "/assets/sounds/shoot.wav");
    GameAssets.loadSound("asteroid-hit", "/assets/sounds/asteroid-hit.wav");
    GameAssets.loadSound("asteroid-explosion", "/assets/sounds/asteroid-explosion.wav");
    GameAssets.loadSound("ship-explosion", "/assets/sounds/ship-explosion.wav");
    GameAssets.loadSound("win", "/assets/sounds/win.wav");
  }
}