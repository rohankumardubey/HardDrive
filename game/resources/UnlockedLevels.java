package game.resources;

import game.engine.*;
import java.nio.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Stores all of the unlocked game levels
 */
public class UnlockedLevels implements Resource {

  private static final Path SAVE_FILE     = Paths.get("save.dat");
  private static final String HASH_SECRET = "SECRET";

  private int maxLevelComplete;

  public UnlockedLevels() {
    this.maxLevelComplete = 0;

    // Potentially load an existing save file
    this.tryReadFile();
    this.tryWriteFile();
  }

  /**
   * Test if a level has been unlocked
   *
   * @param level   Level to test
   * @return        Whether or not the level is unlocked
   */
  public boolean isLevelUnlocked(int level) {
    return level <= (this.maxLevelComplete + 1);
  }

  /**
   * Mark a level as completed
   */
  public void completeLevel(int level) {
    this.maxLevelComplete = Math.max(this.maxLevelComplete, level);

    // Save the user progress
    this.tryWriteFile();
  }

  /**
   * Attempt to read the save file into memory
   */
  private void tryReadFile() {
    try {
      byte[] fileArray = Files.readAllBytes(SAVE_FILE);
      ByteBuffer buf   = ByteBuffer.wrap(fileArray);

      // Extract the count
      int count = buf.getInt();

      // Extract the checksum hash
      byte[] hash = new byte[32];
      buf.get(hash);

      // Make sure checksum matches
      if (Arrays.equals(hash, getHash(count))) { this.maxLevelComplete = count; }

    } catch (Exception ex) {}
  }

  /**
   * Try to write to the save file
   */
  private void tryWriteFile() {
    try {
      ByteBuffer buf = ByteBuffer.allocate(4 + 32);
      buf.putInt(this.maxLevelComplete);
      buf.put(getHash(this.maxLevelComplete));

      Files.write(SAVE_FILE, buf.array());
    } catch (Exception ex) {}
  }

  /**
   * Get the checksum hash for the given inputs.
   * This is to ensure the data file has not been tampered with.
   *
   * @param count   Current level count
   * @return        Integrity Hash
   * @throws NoSuchAlgorithmException
   */
  private static byte[] getHash(int count) throws NoSuchAlgorithmException {
    String hashString    = Integer.toString(count) + HASH_SECRET;
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    return digest.digest(hashString.getBytes(StandardCharsets.UTF_8));
  }
}
