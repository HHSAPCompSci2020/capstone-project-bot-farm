import processing.core.PImage;

/**
 * Represents a Basic Attack from an Android (Player).
 * @author Harry Guan
 */
public class AndroidBasicProjectile extends Projectile{

	/**
	 * Constructs an AndroidBasicProjectile.
	 * @param img The image to be used.
	 * @param x The x-coordinate of the AndroidBasicProjectile.
	 * @param y The y-coordinate of the AndroidBasicProjectile. 
	 * @param w The width of the AndroidBasicProjectile.
	 * @param h The height of the AndroidBasicProjectile.
	 * @param owner The owner of the AndroidBasicProjectile (will be Player).
	 * @param angle The angle to fire the AndroidBasicProjectile.
	 * @param delay The range (or delay before the AndroidBasicProjectile disappears).
	 */
	public AndroidBasicProjectile(PImage img, int x, int y, int w, int h, String owner, double angle, int delay) {
		super(img, x, y, w, h, owner, angle, delay);
	}

}
