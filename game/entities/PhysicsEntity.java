package game.entities;

import game.engine.*;
import game.scenes.MainScene;

public abstract class PhysicsEntity extends Entity
{
	//pblc Point2d position inherited from Entity
	public Vector2d velocity;
	public Vector2d acceleration;
	public Vector2d friction; // Acceleration due to friction

	public int ang_pos = 0;
	public int ang_vel = 0;

	public int mass = 0;
	private Point2d myCoefFric; // General coefficient of friction in relative x, y directions
	private Point2d coefFric;   // Coefficient of friction with my scene specific scene in relative x y directions

	public PhysicsEntity()
	{
		super();

		this.position     = new Vector2d();
		this.velocity     = new Vector2d();
		this.acceleration = new Vector2d();

		this.myCoefFric   = new Vector2d();
		this.coefFric     = new Vector2d();

	//apply kinematics
	protected void onStep()
	{
		this.velocity.add (this.acceleration);
		this.position.add (this.velocity);
		this.friction = 

		this.ang_pos += this.ang_vel;
