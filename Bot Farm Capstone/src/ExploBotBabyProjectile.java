import processing.core.PImage;
/**
 * represents the projectiles shot by the ExploBotBaby
 * @author Zackery He
 *
 */
public class ExploBotBabyProjectile extends Projectile {

	/**
	 * 
	 * @param img img displayed at the coords of the projectile
	 * @param x x coords of the projectile
	 * @param y y coords of the projectile
	 * @param w width of the image
	 * @param h height of the image
	 * @param owner id of the enemy who shot the bullet
	 * @param angle angle the projectile will fly
	 * @param delay delay of when the projectile will be launched
	 */
	public ExploBotBabyProjectile(PImage img, int x, int y, int w, int h, String owner, double angle, int delay) {
		super(img, x, y, w, h, owner, angle, delay);
		// TODO Auto-generated constructor stub
	}

}
