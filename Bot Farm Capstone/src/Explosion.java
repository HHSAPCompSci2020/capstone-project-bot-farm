import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents an explosion visual from a missile
 * @author Michael Chen
 *
 */
public class Explosion extends MovingImage {
	
	/**
	 * Makes an Explosion with img, x, y, w, and h
	 * @param img The image of the explosion
	 * @param x The x coordinate of the center of the explosion
	 * @param y The y coordinate of the center of the explosion
	 * @param w The width of the explosion
	 * @param h The height of the explosion
	 */
	public Explosion(PImage img, double x, double y, int w, int h) {
		super(img, x, y, w, h);
		counter = 0;
	}
	/**
	 * Returns whether the explosion has faded or not
	 * @return whether the explosion has faded or not
	 */
	public boolean isExpired() {
		return counter > 120;
	}
	/**
	 * Draws this explosion, which gradually fades and makes a yellow flash when first made
	 * @param marker The PApplet to be drawn on.
	 */
	public void draw(PApplet marker) {
		counter++;
		marker.pushStyle();
		marker.pushMatrix();
		marker.tint(255, (float)(120-counter)/120.0f * 255);
		marker.imageMode(marker.CENTER);
		super.draw(marker);
		if (counter <= 3) {
			marker.fill(255, 255, 0);
			marker.rect(0, 0, marker.displayWidth, marker.displayHeight);
		}
		marker.popMatrix();
		marker.popStyle();
	}
}
