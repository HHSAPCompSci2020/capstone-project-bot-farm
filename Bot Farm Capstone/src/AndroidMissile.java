
import java.awt.geom.Point2D;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class AndroidMissile extends Projectile {
	private final int RADIUS = 100;
	public AndroidMissile(PImage img, double x, double y, int w, int h, String owner, double angle, int delay) {
        super(img, x, y, w, h, owner, angle, delay);        
	}
	public ArrayList<MovingImage> explode(ArrayList<MovingImage> list) {
		ArrayList<MovingImage> exploded = new ArrayList<MovingImage>();
		Point2D.Double pos = new Point2D.Double(getCenterX(), getCenterY());
		for (MovingImage s : list) {
			if (pos.distance(s.getCenterX(), s.getCenterY()) <= RADIUS) {
				if (s instanceof Bot) {
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
