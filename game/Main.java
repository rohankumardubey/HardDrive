package game;

import game.engine.*;
import game.scenes.*;
import java.awt.*;
import java.awt.image.*;

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
    GameAssets.loadImage("circuit-bg", "/assets/backgrounds/circuit.png");
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

    for (int i = 1; i <= 10; i += 1) {
      GameAssets.addImage("binary-" + i, generateRandomBinaryImage());
    }

    GameAssets.loadSound("shoot", "/assets/sounds/shoot.wav");
    GameAssets.loadSound("asteroid-hit", "/assets/sounds/asteroid-hit.wav");
    GameAssets.loadSound("asteroid-explosion", "/assets/sounds/asteroid-explosion.wav");
    GameAssets.loadSound("ship-explosion", "/assets/sounds/ship-explosion.wav");
    GameAssets.loadSound("win", "/assets/sounds/win.wav");

    GameAssets.loadSound("typing", "/assets/sounds/typing.wav");

    GameAssets.loadSound("corruption-bgm", "/assets/bgm/data-corruption.wav");
  }

  /**
   * Generate a single random binary image
   */
  private static BufferedImage generateRandomBinaryImage() {
    final Font GRAPHICS_FONT   = new Font("monospace", Font.PLAIN, 8);
    final Color GRAPHICS_COLOR = new Color(0, 255, 0);
    final int IMAGE_WIDTH      = 100;
    final int IMAGE_HEIGHT     = 100;

    BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);

    FontMetrics metrics = image.getGraphics().getFontMetrics(GRAPHICS_FONT);
    int numCharWidth    = (int) Math.ceil((double) image.getWidth() / metrics.stringWidth("0"));
    int numCharHeight   = (int) Math.ceil((double) image.getHeight() / metrics.getHeight());
    int charHeight      = metrics.getHeight();

    Graphics2D g2d = (Graphics2D) image.getGraphics();
    g2d.setFont(GRAPHICS_FONT);
    g2d.setColor(GRAPHICS_COLOR);

    for (int i = 0; i < numCharHeight; i += 1) {
      g2d.drawString(randomBinaryString(numCharWidth), 0, i * charHeight);
    }

    return image;
  }

  /**
   * Generate a random string of "0" and "1" of a given length
   */
  private static String randomBinaryString(int numCharWidth) {
    String str = "";
    for (int i = 0; i < numCharWidth; i += 1) {
      if (Math.random() < 0.5) {
        str += "0";
      } else {
        str += "1";
      }
    }
    return str;
  }
}