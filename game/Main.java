package game;

import game.engine.*;
import game.resources.*;
import game.scenes.*;
import java.awt.*;
import java.awt.image.*;

/**
 * Main entry point for the game
 */
public class Main {
  public static void main(String[] args) {

    try {
      loadBackgrounds();
      loadSprites();
      loadSounds();
    } catch (Exception ex) {
      System.out.println("Failed to load game assets: " + ex);
      ex.printStackTrace();
      return;
    }

    Game g = new Game(new TitleScene());
    g.setTitle("Hard Drive");
    g.setResource(new UnlockedLevels());
    g.setResource(new Lives());
    g.run();
  }

  /**
   * Load all background assets
   */
  private static void loadBackgrounds() throws Exception {
    GameAssets.loadImage("grass-bg", "/assets/backgrounds/grass.png");
    GameAssets.loadImage("sand-bg", "/assets/backgrounds/sand.png");
    GameAssets.loadImage("snow-bg", "/assets/backgrounds/snow.png");
    GameAssets.loadImage("dirt-bg", "/assets/backgrounds/dirt.jpg");
    GameAssets.loadImage("rocky-bg", "/assets/backgrounds/rocky.png");
    GameAssets.loadImage("circuit-bg", "/assets/backgrounds/circuit.png");
  }

  /**
   * Load all sprite assets
   */
  private static void loadSprites() throws Exception {
    // Player
    GameAssets.loadImage("car", "/assets/sprites/car.png");

    // Antiviruses
    GameAssets.loadImage("worm", "/assets/sprites/worm.png");
    GameAssets.loadImage("swooper", "/assets/sprites/swooper.png");
    GameAssets.loadImage("booger", "/assets/sprites/booger.png");
    GameAssets.loadImage("amalgamate", "/assets/sprites/amalgamate.png");
    GameAssets.loadImage("amalgamate-angry", "/assets/sprites/amalgamate-angry.png");
    GameAssets.loadImage("virus-1", "/assets/sprites/red-virus.png");
    GameAssets.loadImage("virus-2", "/assets/sprites/yellow-virus.png");
    GameAssets.loadImage("virus-3", "/assets/sprites/green-virus.png");
    GameAssets.loadImage("virus-4", "/assets/sprites/blue-virus.png");
    GameAssets.loadImage("virus-5", "/assets/sprites/pink-virus.png");

    // Walls
    GameAssets.loadImage("tree", "/assets/sprites/tree.png");
    GameAssets.loadImage("snow-tree", "/assets/sprites/snow-tree.png");
    GameAssets.loadImage("rock", "/assets/sprites/rock.png");
    GameAssets.loadImage("pyramid-1", "/assets/sprites/gold-pyramid-front.png");
    GameAssets.loadImage("pyramid-2", "/assets/sprites/gold-pyramid-back.png");
    GameAssets.loadImage("pyramid-3", "/assets/sprites/light-pyramid-front.png");
    GameAssets.loadImage("pyramid-4", "/assets/sprites/light-pyramid-back.png");

    // Cactus
    for (int i = 1; i <= 5; i += 1) {
      GameAssets.loadImage("cactus-" + i, "/assets/sprites/cactus-" + i + ".png");
    }

    // Components
    GameAssets.loadImage("transistor", "/assets/sprites/transistor.png");
    GameAssets.loadImage("capacitor", "/assets/sprites/capacitor.png");
    GameAssets.loadImage("resistor", "/assets/sprites/resistor.png");
    GameAssets.loadImage("data-file", "/assets/sprites/data-file.png");

    // Chip
    for (int i = 1; i <= 8; i += 1) {
      GameAssets.loadImage("chip-" + i, "/assets/sprites/chip-" + i + ".png");
    }

    // Ant sprite
    for (int i = 1; i <= 8; i += 1) {
      for (int j = 1; j <= 8; j += 1) {
        if (i == 8 && j > 6) { continue; }
        GameAssets.loadImage("ant-" + ((i - 1) * 8 + (j)),
                             "/assets/sprites/ant/row-" + i + "-col-" + j + ".png");
      }
    }

    // Small explosion
    for (int i = 1; i <= 4; i += 1) {
      GameAssets.loadImage("small-explosion-" + i,
                           "/assets/sprites/small-explosion/small-explosion-" + i + ".png");
    }

    // Big explosion
    for (int i = 1; i <= 13; i += 1) {
      GameAssets.loadImage("large-explosion-" + i,
                           "/assets/sprites/big-explosion/big-explosion-" + i + ".png");
    }

    // Random binary text
    for (int i = 1; i <= 10; i += 1) {
      GameAssets.addImage("binary-" + i, generateRandomBinaryImage());
    }
  }

  /**
   * Load all sound assets
   */
  private static void loadSounds() throws Exception {
    GameAssets.loadSound("shoot", "/assets/sounds/shoot.wav");
    GameAssets.loadSound("hit", "/assets/sounds/hit.wav");
    GameAssets.loadSound("large-explosion", "/assets/sounds/large-explosion.wav");
    GameAssets.loadSound("small-explosion", "/assets/sounds/small-explosion.wav");
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
    int numCharWidth    = (int) Math.ceil(((double) image.getWidth()) / metrics.stringWidth("0"));
    int numCharHeight   = (int) Math.ceil(((double) image.getHeight()) / metrics.getHeight()) + 1;
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
