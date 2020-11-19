package game.entities;

import game.engine.*;
import game.scenes.MainScene;

public abstract class PhysicsEntity extends Entity
{
	//pblc Point2d position inherited from Entity
	public Vector2d velocity;
	public Vector2d acceleration;

	public double angle = 0;
	public double angularVelocity = 0;

	protected int mass = 0;
	protected double weight = 0; // From Scene's gravitational acceleration

	protected Vector2d originalFrictionCoefficient;
	protected Vector2d masterFrictionCoefficient; // Specific coefficient of friction with scene in relative x y directions
	protected Vector2d frictionCoefficient; // Current coefficient of friction

	private Vector2d friction; // Current force of friction

	// Constructor
	public PhysicsEntity (Vector2d fricCoef, int mass)
	{
		super();

		this.position     = new Vector2d();
		this.velocity     = new Vector2d();
		this.acceleration = new Vector2d();

		this.originalFrictionCoefficient = new Vector2d (fricCoef);
		this.masterFrictionCoefficient   = new Vector2d (fricCoef);
		this.frictionCoefficient         = new Vector2d (fricCoef);

		this.friction = new Vector2d();

	} //end Constructor

	/*
		Set master friction coefficient to the average of original friction
		coefficient and the incoming friction coefficient from the scene.
	*/
	public void setFriction setFriction (double friction);
	{
		this.masterFrictionCoefficient.set
		(
			(this.originalFrictionCoefficient.x + friction) / 2,
			(this.originalFrictionCoefficient.y + friction) / 2
		);

		this.frictionCoefficient.set (this.masterFrictionCoefficient)
	
	}

	//apply kinematics
	protected void onStep()
	{
		this.velocity.add (this.acceleration);
		this.position.add (this.velocity);

		this.angle += this.angularVelocity;
		this.sprite.addAngleRadians (this.angle);

		this.applyFriction();

		this.mask = sprite.getMask();

	} //end onStep()

	//apply friction
	protected void applyFriction()
	{
		//get friction direction
		this.friction.set (this.frictionCoefficient);
		this.friction.rotate (this.angle);

		//get friction magnitude
		this.friction.scaleBy (this.weight);

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
		if (Math.abs(this.friction.x) > (Math.abs(this.velocity.x)))
			this.velocity.x = 0;
		else
			this.velocity.x += this.friction.x;

		if (Math.abs(this.friction.y) > (Math.abs(this.velocity.y)))
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

} //end class PhysicsEntity
