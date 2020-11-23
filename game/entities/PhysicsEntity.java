package game.entities;

import game.engine.*;
import game.scenes.MainScene;

public abstract class PhysicsEntity extends HealthEntity
{
	//pblc Point2d position inherited from Entity
	public Vector2d velocity;
	public Vector2d acceleration;

	public double angle = 0;
	public double angularVelocity = 0;

	protected double mass = 0;
	protected double weight = 0; // From Scene's gravitational acceleration

	protected Vector2d originalFrictionCoefficient;
	protected Vector2d masterFrictionCoefficient; // Specific coefficient of friction with scene in relative x y directions
	protected Vector2d frictionCoefficient; // Current coefficient of friction

	private Vector2d friction; // Current force of friction

	// Constructor
	public PhysicsEntity (Vector2d fricCoef, double mass, int health)
	{
		super (health);

		this.position     = new Vector2d();
		this.velocity     = new Vector2d();
		this.acceleration = new Vector2d();

		this.originalFrictionCoefficient = new Vector2d (fricCoef);
		this.masterFrictionCoefficient   = new Vector2d (fricCoef);
		this.frictionCoefficient         = new Vector2d (fricCoef);
		this.friction = new Vector2d();

		this.mass = mass;
		this.weight = mass; // this will be removed if/when gravity is implemented

	} //end Constructor

	/*
		Called from GameScene subclass constructors.

		Set master friction coefficient to the average of original friction
		coefficient and the incoming friction coefficient from the scene.
	*/
	public void setFriction (double friction)
	{
		this.masterFrictionCoefficient.set
		(
			(this.originalFrictionCoefficient.x + friction) / 2,
			(this.originalFrictionCoefficient.y + friction) / 2
		);

		this.frictionCoefficient.set (this.masterFrictionCoefficient);

		System.out.println ("Friction set to (" + this.frictionCoefficient.x + ", " + this.frictionCoefficient.y + ")\n");
	
	} //end setFriction()

	//apply kinematics and friction
	protected void onStep()
	{
		super.onStep();

		this.velocity.add (this.acceleration);
		this.position.add (this.velocity);

		this.angle += this.angularVelocity;
		this.sprite.addAngleRadians (this.angularVelocity);

		this.applyFriction();

		this.mask = sprite.getMask();

		System.out.println ("-\n\nPosition: (" + this.position.x + ", " + this.position.y + ")\n");
		System.out.println ("Velocity: (" + this.velocity.x + ", " + this.velocity.y + ")\n");
		System.out.println ("Acceleration: (" + this.acceleration.x + ", " + this.acceleration.y + ")\n\n");
		System.out.println ("Angle: " + this.angle);

	} //end onStep()

	//apply friction
	protected void applyFriction()
	{
		//get friction direction
		this.friction.set (this.frictionCoefficient);

		//get friction magnitude
		this.friction.scaleBy (this.weight / this.mass);

		//rotate velocity to original orientation
		this.velocity.rotate (-1 * this.angle);

		//make friction oppose movement
		if (this.velocity.x > 0)
			this.friction.x = Math.abs(this.friction.x) * -1;
		else
			this.friction.x = Math.abs(this.friction.x);

		if (this.velocity.y > 0)
			this.friction.y = Math.abs(this.friction.y) * -1;
		else
			this.friction.y = Math.abs(this.friction.y);

		System.out.println ("(" + this.friction.x + ", " + this.friction.y + ")\n");

		//apply friction without overshooting
		if (Math.abs(this.friction.x) > (Math.abs(this.velocity.x)))
			this.velocity.x = 0;
		else
			this.velocity.x += this.friction.x;

		if (Math.abs(this.friction.y) > (Math.abs(this.velocity.y)))
			this.velocity.y = 0;
		else
			this.velocity.y += this.friction.y;

		//rotate friction'd velocity to current orientation
		this.velocity.rotate (this.angle);

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

} //end class PhysicsEntity
