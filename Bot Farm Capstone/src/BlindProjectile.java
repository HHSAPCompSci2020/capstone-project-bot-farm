import processing.core.PImage;

/**
 * Represents a BlindProjectile. 
 * @author Harry Guan
 */
public class BlindProjectile extends Projectile {
	
	/**
	 * Constructs an BlindProjectile.
	 * @param img The image to be used.
	 * @param x The x-coordinate of the BlindProjectile.
	 * @param y The y-coordinate of the BlindProjectile. 
	 * @param w The width of the BlindProjectile.
	 * @param h The height of the BlindProjectile.
	 * @param owner The owner of the BlindProjectile (will be BlindBot).
	 * @param angle The angle to fire the BlindProjectile.
	 * @param delay The range (or delay before the BlindProjectile disappears).
	 */
	public BlindProjectile(PImage img, double x, double y, int w, int h, String owner, double angle, int delay) {
		super(img, x, y, w, h, owner, angle, delay);
	}
}
