import java.awt.geom.Point2D;
import java.util.ArrayList;

import processing.core.PImage;

/**
 * Represents an AndroidMissile.
 * @author Harry Guan, Michael Chen
 */
public class AndroidMissile extends Projectile {
	
	private final int RADIUS = 200;
	
	/**
	 * Constructs an AndroidMissile.
	 * @param img The image to be used.
	 * @param x The x-coordinate of the AndroidMissile.
	 * @param y The y-coordinate of the AndroidMissile. 
	 * @param w The width of the AndroidMissile.
	 * @param h The height of the AndroidMissile.
	 * @param owner The owner of the AndroidMissile (will be Player).
	 * @param angle The angle to fire the AndroidMissile.
	 * @param delay The range (or delay before the AndroidMissile disappears).
	 */
	public AndroidMissile(PImage img, double x, double y, int w, int h, String owner, double angle, int delay) {
        super(img, x, y, w, h, owner, angle, delay);        
	}
	
	/**
	 * Makes the action of the Missile explosion. 
	 * @param list The list of entities currently in the game.
	 * @return The entities to be removed in this process.
	 */
	public ArrayList<MovingImage> explode(ArrayList<MovingImage> list) {
		ArrayList<MovingImage> exploded = new ArrayList<MovingImage>();
		Point2D.Double pos = new Point2D.Double(getCenterX(), getCenterY());
		for (MovingImage s : list) {
			if (pos.distance(s.getCenterX(), s.getCenterY()) <= RADIUS) {
				if (s instanceof Bot) {
					if (s instanceof ExploBotBaby)
						((ExploBotBaby) s).explode(list);
					((Bot)s).die();
					exploded.add(s);
				}
				else if (s instanceof Rock)
					exploded.add(s);
					
			}
		}
		return exploded;
	}
}
