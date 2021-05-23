import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents a Projectile
 * @author Zackery He, Harry Guan
 *
 */
public class Projectile extends MovingImage {

	private String owner;
	double angle = 0;
	double stepX;
	double stepY;
	protected int timer;
	double delay;
	/**
	 * Constructs a Projectile.
	 * @param img The image to be used.
	 * @param x The x-coordinate of the Projectile.
	 * @param y The y-coordinate of the Projectile. 
	 * @param w The width of the Projectile.
	 * @param h The height of the Projectile.
	 * @param owner The owner of the Projectile.
	 * @param delay The range (or delay before the Projectile disappears).
	 */
	public Projectile(PImage img, double x, double y, int w, int h, String owner, int delay) {
		super(img, x, y, w, h);
		this.owner = owner;
		stepX = 0;
		stepY = -5;
		timer = 0;
		this.delay = delay;
		
	}

	/**
	 * Constructs a Projectile.
	 * @param img The image to be used.
	 * @param x The x-coordinate of the Projectile.
	 * @param y The y-coordinate of the Projectile. 
	 * @param w The width of the Projectile.
	 * @param h The height of the Projectile.
	 * @param owner The owner of the Projectile.
	 * @param angle The angle to fire the Projectile.
	 * @param delay The range (or delay before the Projectile disappears).
	 */
	public Projectile(PImage img, double x, double y, int w, int h, String owner, double angle, int delay) {
		super(img, x, y, w, h);
		this.owner = owner;
		this.angle = angle;
		this.delay = delay;
		double stepX = Math.cos(angle);
		double stepY = Math.sin(angle);

		this.stepX = stepX * 5;
		this.stepY = stepY * 5;

	}
	
	/**
	 * Handles how the Projectile will act in game.
	 * @param list The list of all entities currently in the game.
	 * @return The Projectile that will be removed in this process
	 */
	public MovingImage act(ArrayList<MovingImage> list) {
		this.moveByAmount(stepX, stepY);
		timer++;
		if (!this.isInWindow() || timer > delay)
			return this;

		for (MovingImage m : list) { 
			if (this.intersects(m) && m instanceof Block && !(m instanceof NoClipBlock))
				return this;
			if (this != m && this.intersects(m) && !this.owner.equals(m.toString()) && !(m instanceof Projectile))
				return m;
		}
		return null; // If all else fails, return null.

	}
	
	/**
	 * Draws the Projectile to the PApplet.
	 * @param marker The PApplet to draw to. 
	 */
	public void draw(PApplet marker) {
		marker.pushMatrix();
		marker.translate((float)(x + width/2), (float)(y + height/2));
		marker.rotate((float)angle + (float)Math.PI/2);
		marker.image(image, -(float)width/2, -(float)height/2, (float)this.width, (float)this.height);
		marker.popMatrix();
	}

}
