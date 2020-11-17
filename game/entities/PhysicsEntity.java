package game.entities;

import game.engine.*;
import game.scenes.MainScene;

public abstract class PhysicsEntity extends Entity
{
	//pblc Point2d position inherited from Entity
	public Vector2d velocity;
	public Vector2d acceleration;
	public Vector2d friction; // Acceleration due to friction

	public int ang_pos;
	public int ang_vel;

	public int mass;
	public Point2d coefFric; // Coefficient of friction in relative x y directions

	public elastic_collision (

	//apply kinematics
	protected void onStep()
	{
		this.velocity.add (this.acceleration);
		this.position.add (this.velocity);
		this.friction = 

		this.ang_pos += this.ang_vel;
