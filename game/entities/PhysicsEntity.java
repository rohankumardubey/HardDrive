package game.entities;

import game.engine.*;
import game.scenes.GameScene;
import java.awt.Dimension;

/**
 * Represents entity with physics
 */
public abstract class PhysicsEntity extends HealthEntity {

  // collision elasticity and damage
  protected static final double COLLISION_ELASTICITY = 0.5;
  protected static final double COLLISION_DAMAGE     = 0.05;

  // public Point2d position inherited from Entity
  public Vector2d velocity;
  public Vector2d acceleration;

  // angles stored in radians
  public double angle           = 0;
  public double angularVelocity = 0;

  protected double mass   = 0;
  protected double weight = 0; // From Scene's gravitational acceleration

  protected Vector2d originalFrictionCoefficient;
  protected Vector2d masterFrictionCoefficient; // Specific coefficient of friction with scene in
                                                // relative x y directions
  protected Vector2d frictionCoefficient;       // Current coefficient of friction

  private Vector2d friction; // Current force of friction

  // Constructor
  public PhysicsEntity(int health, Vector2d fricCoef, double mass) {
    super(health);

    this.position     = new Vector2d();
    this.velocity     = new Vector2d();
    this.acceleration = new Vector2d();

    this.originalFrictionCoefficient = new Vector2d(fricCoef);
    this.masterFrictionCoefficient   = new Vector2d(fricCoef);
    this.frictionCoefficient         = new Vector2d(fricCoef);
    this.friction                    = new Vector2d();

    this.mass   = mass;
    this.weight = mass; // this will be removed if/when gravity is implemented

  } // end Constructor

  public PhysicsEntity(int health, double fricCoef, double mass) {
    this(health, new Vector2d(fricCoef, fricCoef), mass);
  }

  public Vector2d getPosition() {
    return new Vector2d(this.position);
  }

  public Vector2d getVelocity() {
    return new Vector2d(this.velocity);
  }

  public Vector2d getAcceleration() {
    return new Vector2d(this.acceleration);
  }

  protected void onCreate() {
    GameScene scene = (GameScene) this.getScene();
    this.setFriction(scene.getFriction());
  }

  /*
    Called from GameScene subclass setFriction().

    Set master friction coefficient to the product of original friction
    coefficient and the incoming friction coefficient from the scene.
  */
  public void setFriction(double friction) {
    this.masterFrictionCoefficient.set(this.originalFrictionCoefficient.x * friction,
                                       this.originalFrictionCoefficient.y * friction);

    this.frictionCoefficient.set(this.masterFrictionCoefficient);

  } // end setFriction()

  // apply kinematics and friction
  protected void onStep() {
    super.onStep();

    this.velocity.add(this.acceleration);
    this.position.add(this.velocity);

    this.angle += this.angularVelocity;
    this.sprite.addAngleRadians(this.angularVelocity);

    this.checkBoundaryCollision();

    this.applyFriction();

    this.mask = sprite.getMask();

  } // end onStep()

  // don't let the entity go out of the map
  protected void checkBoundaryCollision() {
    Scene scene = this.getScene();

    if (this.position.x < 0 || this.position.x > scene.size.width) {
      GameAssets.getLoadedSound("hit").playSound();

      // reflect about y-axis
      this.velocity.x *= -0.5;

      this.angle -= Math.toRadians(90);
      this.angle *= -1;
      this.angle += Math.toRadians(90);

      this.sprite.setAngleRadians(this.angle);

      // teleport back inside
      if (this.position.x < 0) {
        this.getScene().createEntity(
            new BinaryExplosion(new Point2d(0, this.position.y), new Dimension(50, 50)));

        this.position.x += this.mask.size.width;
      } else {
        this.getScene().createEntity(new BinaryExplosion(
            new Point2d(scene.size.width, this.position.y), new Dimension(50, 50)));

        this.position.x -= this.mask.size.width;
      }
    }

    if (this.position.y < 0 || this.position.y > scene.size.height) {
      GameAssets.getLoadedSound("hit").playSound();
      this.getScene().createEntity(new BinaryExplosion(this.position, new Dimension(50, 50)));

      // reflect about y-axis

      // reflect about x-axis
      this.velocity.y *= -0.5;
      this.angle *= -1;
      this.sprite.setAngleRadians(this.angle);

      // teleport back inside
      if (this.position.y < 0) {
        this.getScene().createEntity(
            new BinaryExplosion(new Point2d(this.position.x, 0), new Dimension(50, 50)));

        this.position.y += this.mask.size.height;
      } else {
        this.getScene().createEntity(new BinaryExplosion(
            new Point2d(this.position.x, scene.size.height), new Dimension(50, 50)));

        this.position.y -= this.mask.size.height;
      }
    }
  }

  // apply friction
  protected void applyFriction() {
    // get friction direction
    this.friction.set(this.frictionCoefficient);

    // get friction magnitude
    this.friction.scaleBy(this.weight / this.mass);

    // rotate velocity to original orientation
    this.velocity.rotate(-1 * this.angle);

    // make friction oppose movement
    if (this.velocity.x > 0)
      this.friction.x = Math.abs(this.friction.x) * -1;
    else
      this.friction.x = Math.abs(this.friction.x);

    if (this.velocity.y > 0)
      this.friction.y = Math.abs(this.friction.y) * -1;
    else
      this.friction.y = Math.abs(this.friction.y);

    // apply friction without overshooting
    if (Math.abs(this.friction.x) > (Math.abs(this.velocity.x)))
      this.velocity.x = 0;
    else
      this.velocity.x += this.friction.x;

    if (Math.abs(this.friction.y) > (Math.abs(this.velocity.y)))
      this.velocity.y = 0;
    else
      this.velocity.y += this.friction.y;

    // rotate friction'd velocity to current orientation
    this.velocity.rotate(this.angle);

  } // end applyFriction()

  // collide with another PhysicsEntity
  public void collideWith(PhysicsEntity e) {
    this.applyCollisionDamages(e);
    this.applyCollisionForces(e);
  }

  // calculate new velocities according to physics formulas
  protected void applyCollisionForces(PhysicsEntity e) {
    Vector2d newVelocity =
        new Vector2d(newVelocityAfterCollision(this.mass, this.velocity.x, e.mass, e.velocity.x),
                     newVelocityAfterCollision(this.mass, this.velocity.y, e.mass, e.velocity.y));

    e.velocity.set(COLLISION_ELASTICITY * (this.velocity.x - e.velocity.x) + newVelocity.x,
                   COLLISION_ELASTICITY * (this.velocity.y - e.velocity.y) + newVelocity.y);

    this.velocity.set(newVelocity);
  }

  private double newVelocityAfterCollision(double m1, double v1, double m2, double v2) {
    double v1f = ((m1 * v1) + (m2 * v2) - (m2 * COLLISION_ELASTICITY * (v1 - v2))) / (m1 + m2);

    return v1f;
  }

  // get the momentum as the product of velocity and mass
  protected Vector2d getMomentum(double percentage) {
    Vector2d momentum = new Vector2d(this.velocity);
    momentum.scaleBy(this.mass * percentage);

    return momentum;
  }

  /*
    apply the given force for a frame

    also works for instantaneous momentum, since a continuous force is
    simulated by repeated
    applications of momentum
  */
  protected void applyForce(Vector2d force) {
    // convert to acceleration
    force.scaleBy(1 / this.mass);

    // apply
    this.velocity.add(force);

  } // end applyForce()

  // apply the damage done by a force
  protected void applyCollisionDamages(PhysicsEntity e) {
    Vector2d momentum = this.getMomentum(COLLISION_DAMAGE);
    momentum.sub(e.getMomentum(COLLISION_DAMAGE));

    e.hit((int) momentum.length());
    this.hit((int) momentum.length());
  }

} // end class PhysicsEntity
