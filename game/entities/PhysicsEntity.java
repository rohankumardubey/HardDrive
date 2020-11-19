package game.entities;

import game.engine.*;
import game.scenes.MainScene;

public abstract class PhysicsEntity extends Entity
{
	//pblc Point2d position inherited from Entity
	public Vector2d velocity;
	public Vector2d acceleration;

	public int ang_pos = 0;
	public int ang_vel = 0;

	private int mass = 0;
	private int weight = 0; // Calculated from Scene's gravitational acceleration

	private Point2d myFrictionCoefficient; // Original general coefficient of friction in relative x, y directions
	private Point2d frictionCoefficient;   // Current specific coefficient of friction with scene in relative x y directions
	private Vector2d friction;             // Current force of friction

	// Constructor
	public PhysicsEntity()
	{
		super();

		this.position     = new Vector2d();
		this.velocity     = new Vector2d();
		this.acceleration = new Vector2d();

		this.myFrictionCoefficient = new Point2d();
		this.frictionCoefficient   = new Point2d();

	} //end Constructor

	//apply kinematics
	protected void onStep()
	{
		this.velocity.add (this.acceleration);
		this.position.add (this.velocity);

		this.ang_pos += this.ang_vel;

		this.applyFriction();

	} //end onStep()

	//apply friction
	protected void applyFriction()
	{
		//get friction direction
		this.friction.set (this.frictionCeofficient);
		this.friction.rotate (this.ang_pos);

		//get friction magnitude
		this.friction.scaleBy (this.weight)

		//make friction oppose movement
		if (this.velocity.x > 0)
			this.friction.x = Math.abs(this.friction.x) * -1;
		else
			this.friction.x = Math.abs(this.friction.x);

		if (this.velocity.y > 0)
			this.friction.y = Math.abs(this.friction.y) * -1;
		else
			this.friction.y = Math.abs(this.friction.y);

		//friction currently a force; convert to acceleration
		this.friction.scaleBy (1 / this.mass);

		//apply friction without overshooting
		if (Math.abs(this.friction.x) > (Math.abs(this.velocity.x))
			this.velocity.x = 0;
		else
			this.velocity.x += this.friction.x;

		if (Math.abs(this.friction.y) > (Math.abs(this.velocity.y))
			this.velocity.y = 0;
		else
			this.velocity.y += this.friction.y;

	} //end applyFriction()

	/*
		apply the given force for a frame

		also works for instantaneous momentum, since a continuous force is
		simulated by repeated
		applications of momentum
	*/
	protected void applyForce (Vector2d force)
	{
		//convert to acceleration
		force.scaleBy (1 / this.mass);

		//apply
		this.velocity.add (force);

	} //end applyingForce()
