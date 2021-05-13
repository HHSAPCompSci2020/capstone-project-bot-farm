import java.util.ArrayList;
import processing.core.PImage;

/**
 * represents a projectile
 * @author Zackery He
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
	 * 
	 * @param img image displayed to represent the bot
	 * @param x x coord of the bot
	 * @param y y coord of the bot
	 * @param w width of the image 
	 * @param h height of the image
	 * @param owner owner of the projectile
	 * @param delay delay of the projectile
	 */
	public Projectile(PImage img, double x, double y, int w, int h, String owner, int delay) {
		super(img, x, y, w, h);
		// The field owner should equal the parameter owner.
		this.owner = owner;
		stepX = 0;
		stepY = -5;
		timer = 0;
		this.delay = delay;

	}

	/**
	 * 
	 * @param img image displayed to represent the bot
	 * @param x x coord of the bot
	 * @param y y coord of the bot
	 * @param w width of the image 
	 * @param h height of the image
	 * @param owner owner of the projectile
	 * @param angle angle the projectile will be shot from
	 * @param delay delay of the projectile
	 */
	public Projectile(PImage img, double x, double y, int w, int h, String owner, double angle, int delay) {
		super(img, x, y, w, h);
		// The field owner should equal the parameter owner.
		this.owner = owner;
		this.angle = angle;
		this.delay = delay;
		double stepX = Math.cos(angle);
		double stepY = Math.sin(angle);

		this.stepX = stepX * 5;
		this.stepY = stepY * 5;

	}
	
	/**
	 * handles how the projectile will act in game
	 * @param list list of the all the MovingImages
	 * @return the bot that will be removed in this process
	 */
	public MovingImage act(ArrayList<MovingImage> list) {
		// If owner is equal to "player", call "this.moveByAmount()" and move it by a
		// negative number in the y direction.
		this.moveByAmount(stepX, stepY);
		// Else if owner is equal to "enemy", move it by a positive number in the y
		// direction
		// If "this" is not in window, return "this".
		timer++;
		if (!this.isInWindow() || timer > delay) {
			return this;
		}

		for (MovingImage m : list) { // Collision detection (do not modify) m scrolls thru list
			if (this.intersects(m) && m instanceof Block && !(m instanceof NoClipBlock)) {
				return this;
			}
			if (this != m && this.intersects(m) && !this.owner.equals(m.toString()) && !(m instanceof Projectile)) {
				return m;
			}
		}
		return null; // If all else fails, return null.

	}

}
