import java.util.ArrayList;
import processing.core.PImage;

public class Projectile extends MovingImage {

	private String owner;
	double angle = 0;
	double stepX;
	double stepY;
	protected int timer;
	double delay;

	public Projectile(PImage img, double x, double y, int w, int h, String owner, int delay) {
		super(img, x, y, w, h);
		// The field owner should equal the parameter owner.
		this.owner = owner;
		stepX = 0;
		stepY = -5;
		timer = 0;
		this.delay = delay;

	}

	
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
