package game.engine;

/**
 * Rendering type for the Background
 */
public enum BackgroundType {
  None,      // Only render the background color
  Image,     // Render the single image, then fill the rest with background color
  Tiled,     // Tile the image across the entire background
  Stretched, // Stretch the image across the background
}
