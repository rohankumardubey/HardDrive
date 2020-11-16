package game.entities;

import game.engine.*;
import game.scenes.MainScene;
import java.util.ArrayList;

/**
 * Car that you control
 */
public class Player extends Entity {

	// Constants
	private final static int MAX_X       = 300; //Max X when scrolling

	private static int MAX_SPEED = 10;
	private static int MAX_ACC   = 10;
	private static int TURN_RAD  = 10;

/*  private final static int SHIP_PPF    = 5;
	private final static int MAX_BULLETS = 3;

	private final static int FLAME_ANIMATION_SPEED = 2;
	private final static int FRAME_NO_FLAME	      = 0;
	private final static int FRAME_WITH_FLAME      = 1;

	// Timers
	private final static int FLAME_TIMER = 0;

	private boolean flameOn         = false;
	private boolean flameTick       = false;
	private boolean screenScrolling = true;
*/
	public Player() {
		super();

		// Initialize player size
		this.sprite.addFrames(GameAssets.getLoadedImage("car"));
		this.sprite.size.setSize(27, 61);
		this.sprite.setAngleDegrees(90);
		this.mask                    = sprite.getMask();
		this.mask.relativePosition.x = -13;
	}

	@Override
	protected void onCreate() {
		// Move to center of screen
		this.position.setLocation(MAX_X / 2, this.getScene().size.height / 2);

	}

	@Override
	protected void onDestroy()
	{}

	@Override
	protected void onTimer(int timerIndex)
	{}

	@Override
	protected void onStep() {
		this.movePlayer();

		this.scrollWithShip();
		this.clampBoundaries();

		this.createBullet();
		this.checkForAsteroidCollision();
	}

	private boolean movePlayer() {
		Game game = this.getScene().getGame();

		boolean shipMoved = false;
		if (game.isKeyPressed(Key.UP)) {
			this.position.y -= SHIP_PPF;
			shipMoved = true;
		}
		if (game.isKeyPressed(Key.DOWN)) {
			this.position.y += SHIP_PPF;
			shipMoved = true;
		}
		if (game.isKeyPressed(Key.LEFT)) {
			this.position.x -= SHIP_PPF;
			shipMoved = true;
		}
		if (game.isKeyPressed(Key.RIGHT)) {
			this.position.x += SHIP_PPF;
			shipMoved = true;
		}

		return shipMoved;
	}

	private void scrollWithShip() {
		if (this.screenScrolling) { this.position.x += MainScene.SCROLL_SPEED; }
	}

  private void clampBoundaries() {
    Scene scene = this.getScene();

    int maxX = this.screenScrolling ? MAX_X : scene.mainView.size.width;

    this.position.x = Math.min(Math.max(this.position.x, scene.mainView.position.x),
                               scene.mainView.position.x + maxX);

    this.position.y = Math.min(Math.max(this.position.y, 0), scene.mainView.size.height);
  }
