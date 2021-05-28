import processing.core.PImage;

/**
 * Represents a Projectile shot by a GlitchBot. 
 * @author Harry Guan
 *
 */
public class GlitchProjectile extends Projectile {
	/**
	 * Constructs an GlitchProjectile.
	 * @param img The image to be used.
	 * @param x The x-coordinate of the GlitchProjectile.
	 * @param y The y-coordinate of the GlitchProjectile. 
	 * @param w The width of the GlitchProjectile.
	 * @param h The height of the GlitchProjectile.
	 * @param owner The owner of the GlitchProjectile (will be GlitchBot).
	 * @param angle The angle to fire the GlitchProjectile.
	 * @param delay The range (or delay before the GlitchProjectile disappears).
	 */
	public GlitchProjectile(PImage img, double x, double y, int w, int h, String owner, double angle, int delay) {
        super(img, x, y, w, h, owner, angle, delay);           
	}
}
