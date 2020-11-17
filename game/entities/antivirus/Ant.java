package game.entities.antivirus;

import game.engine.*;
import game.entities.*;
import game.scenes.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Ants that craw along in large groups
 */
public class Ant extends AntiVirus {

  private static final int ANT_SPEED         = 5;
  private static final int ANT_HEALTH        = 3;
  private static final int MAX_DISTANCE_AWAY = 40 * GameScene.TILE_SIZE;

  private LinkedList<Point2d> movementList;

  public Ant(Point2d start) {
    super(ANT_HEALTH);

    for (int i = 1; i <= 62; i += 1) {
      this.sprite.addFrames(GameAssets.getLoadedImage("ant-" + i));
    }
    this.sprite.size.setSize(32, 32);
    this.position.set(start);
    this.mask = this.sprite.getMask();

    this.movementList = new LinkedList<>();
  }

  @Override
  protected void onCreate() {
    recomputePath();
    this.setTimer(0, 5, true);
  }

  /**
   * Recompute the path-finding path towards the player
   */
  private void recomputePath() {
    GameScene scene = (GameScene) this.getScene();

    Player player = scene.findFirstEntity(Player.class);
    if (player == null) { return; }

    Point currentTile = getTilePosition(this.position);
    Point playerTile  = getTilePosition(player.position);

    // Make sure path finding is valid tile
    if (isTileOutsideScene(playerTile) || isTileOutsideScene(currentTile)) { return; }

    boolean[][] walls      = scene.getWallsMap();
    LinkedList<Point> path = Node.getPath(walls, playerTile, currentTile);

    if (path == null) {
      this.destroy();
      return;
    }

    this.movementList = new LinkedList<>();
    for (Point p: path) { this.movementList.push(getPointPosition(p)); }
  }

  /**
   * Convert an absolute point to a tile position
   */
  private static Point getTilePosition(Point2d point) {
    return new Point((int) Math.floor(point.x / GameScene.TILE_SIZE),
                     (int) Math.floor(point.y / GameScene.TILE_SIZE));
  }

  /**
   * Convert a tile point into the center of a tile absolute position
   */
  private static Point2d getPointPosition(Point tilePoint) {
    return new Point2d(tilePoint.x * GameScene.TILE_SIZE + GameScene.TILE_SIZE / 2,
                       tilePoint.y * GameScene.TILE_SIZE + GameScene.TILE_SIZE / 2);
  }

  /**
   * Test if a tile point is outside of the grid of tiles
   */
  private boolean isTileOutsideScene(Point point) {
    GameScene scene = (GameScene) this.getScene();
    return point.x < 0 || point.x >= scene.getTilesWide() || point.y < 0 ||
        point.y > scene.getTilesHigh();
  }

  /**
   * Test if the entity is inside the target tile
   */
  private boolean isCurrentlyInsideTile(Point2d tilePoint) {
    Mask tileMask = new Mask(new Point2d(-GameScene.TILE_SIZE / 2, -GameScene.TILE_SIZE / 2),
                             new Dimension(GameScene.TILE_SIZE, GameScene.TILE_SIZE));
    return this.mask.isCollidingWith(tileMask, this.position, tilePoint);
  }

  @Override
  protected void onDestroy() {}

  @Override
  protected void onTimer(int timerIndex) {
    if (timerIndex == 0) { this.recomputePath(); }
  }

  @Override
  protected void onStep() {
    super.onStep();

    advanceAnimation();
    moveAlongPath();
    testIfInsidePoint();
    testPlayerCollision();
    testIfTooFarAway();
  }

  private void advanceAnimation() {
    this.sprite.nextFrame();
    this.sprite.nextFrame();
    this.sprite.nextFrame();
    this.sprite.nextFrame();
  }

  private void moveAlongPath() {
    if (this.movementList.size() == 0) { return; }

    Vector2d vector = new Vector2d(this.movementList.peekFirst());
    vector.sub(this.position);
    vector.normalize();
    vector.scale(ANT_SPEED);

    this.sprite.angleRadians = vector.polarAngle() + (Math.PI / 2);
    this.position.add(vector);
  }

  private void testIfInsidePoint() {
    if (this.movementList.size() == 0) { return; }
    if (this.isCurrentlyInsideTile(this.movementList.peekFirst())) { this.movementList.pop(); }
  }

  private void testPlayerCollision() {
    Player player = this.getScene().findFirstEntity(Player.class);
    if (player == null) { return; }

    if (this.isCollidingWith(player)) {
      player.hit((int) Helpers.randomRange(0, 2));
      this.getScene().createEntity(
          new BinaryExplosion(this.position, this.sprite.getRotatedImageDimensions()));
      this.destroy();
    }
  }

  private void testIfTooFarAway() {
    Player player = this.getScene().findFirstEntity(Player.class);
    if (player == null) { return; }

    Vector2d vector = new Vector2d(player.position);
    vector.sub(this.position);
    if (vector.polarDistance() > MAX_DISTANCE_AWAY) { this.destroy(); }
  }
}

/**
 * Node used for Ant A* path finding
 */
class Node implements Comparable<Node> {

  public static final double ORTHOGONAL_DIST = 10;
  public static final double DIAGONAL_DIST   = 14;
  private static final int MAX_ITERATIONS    = 1000;

  public Point point;
  public double G, H;
  public Node parent;

  public Node(Point point) {
    this.point = new Point(point);
  }

  public Node(Point point, double g, double h) {
    this.point  = new Point(point);
    this.G      = g;
    this.H      = h;
    this.parent = null;
  }

  public double getF() {
    return G + H;
  }

  /**
   * Recursively traverse the linked list to get the path
   */
  public LinkedList<Point> getPath() {
    LinkedList<Point> path = new LinkedList<>();

    Node current = this.parent;
    while (current != null) {
      path.push(current.point);
      current = current.parent;
    }

    return path;
  }

  /**
   * Compare two nodes based on the F value
   */
  @Override
  public int compareTo(Node other) {
    return Double.compare(this.getF(), other.getF());
  }

  /** Only compare two nodes on their points */
  @Override
  public boolean equals(Object o) {
    if (o == this) { return true; }
    if (!(o instanceof Node)) { return false; }

    Node other = (Node) o;
    return this.point.equals(other.point);
  }

  /** Only compare two nodes on their points */
  @Override
  public int hashCode() {
    return this.point.hashCode();
  }

  /**
   * Get all neighbors that aren't a wall
   */
  public static List<Node> getNeighbors(boolean[][] grid, Point start) {
    // Only do orthogonal neighbors
    List<Node> pointList = new LinkedList<>();
    if (start.x + 1 < grid.length && !grid[start.x + 1][start.y]) {
      pointList.add(new Node(new Point(start.x + 1, start.y)));
    }
    if (start.x - 1 >= 0 && !grid[start.x - 1][start.y]) {
      pointList.add(new Node(new Point(start.x - 1, start.y)));
    }

    if (start.y + 1 < grid[0].length && !grid[start.x][start.y + 1]) {
      pointList.add(new Node(new Point(start.x, start.y + 1)));
    }
    if (start.y - 1 >= 0 && !grid[start.x][start.y - 1]) {
      pointList.add(new Node(new Point(start.x, start.y - 1)));
    }

    return pointList;
  }

  /**
   * Run A* algorithm to get the path
   *
   * @param grid          Grid of walls
   * @param from          Starting location
   * @param destination   Ending location
   * @return              The path, or null if not found
   */
  public static LinkedList<Point> getPath(boolean[][] grid, Point from, Point destination) {
    Node goalNode  = new Node(destination, 0, 0);
    Node startNode = new Node(from, 0, Helpers.manhattanDistance(from, destination));

    SortedSet<Node> currentNodes = new TreeSet<Node>();
    Set<Node> visitedNodes       = new HashSet<Node>();
    currentNodes.add(startNode);

    int iteration = 0;
    while (currentNodes.size() > 0) {

      Node node = currentNodes.first(); // Get node with lowest F value
      if (node.point.equals(goalNode.point)) { return node.getPath(); /* Found it! */ }
      if (iteration++ == MAX_ITERATIONS) { return null; /* Timeout! */ }

      currentNodes.remove(node);
      visitedNodes.add(node);

      List<Node> neighbors = Node.getNeighbors(grid, node.point);
      for (Node n: neighbors) {
        if (visitedNodes.contains(n)) { continue; }
        if (!currentNodes.contains(n)) {
          n.parent = node;
          n.G      = node.G + 1;
          n.H      = Helpers.manhattanDistance(n.point, goalNode.point);
          if (!currentNodes.contains(n)) { currentNodes.add(n); }
        }
      }
    }

    return null; /* Not found */
  }
}