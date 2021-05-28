import processing.core.PImage;
/**
 * Represents the Projectiles shot by the ExploBotBaby
 * @author Zackery He
 *
 */
public class ExploBotBabyProjectile extends Projectile {

	/**
	 * Constructs an ExploBotBabyProjectile.
	 * @param img The image to be used.
	 * @param x The x-coordinate of the ExploBotBabyProjectile.
	 * @param y The y-coordinate of the ExploBotBabyProjectile. 
	 * @param w The width of the ExploBotBabyProjectile.
	 * @param h The height of the ExploBotBabyProjectile.
	 * @param owner The owner of the ExploBotBabyProjectile (will be ExploBotBaby).
	 * @param angle The angle to fire the ExploBotBabyProjectile.
	 * @param delay The range (or delay before the ExploBotBabyProjectile disappears).
	 */
	public ExploBotBabyProjectile(PImage img, int x, int y, int w, int h, String owner, double angle, int delay) {
		super(img, x, y, w, h, owner, angle, delay);
	}

}
