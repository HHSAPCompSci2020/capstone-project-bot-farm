import java.util.ArrayList;

import processing.core.PImage;

/**
 * Represents a rock on the map
 * @author Michael Chen
 *
 */
public class Rock extends Block {

	private int respawnTimer;
	/**
	 * Creates a rock with image, x, y, width, and height
	 * @param image The image texture for the rock
	 * @param x The x coordinate of the top left corner of the rock
	 * @param y The y coordinate of the top left corner of the rock
	 * @param width The width of the rock
	 * @param height The height of the rock
	 */
	public Rock(PImage image, int x, int y, int width, int height) {
		super(image, x, y, width, height);
		respawnTimer = 0;
	}
	/**
	 * Puts this rock on a respawn timer
	 */
	public void destroy() {
		respawnTimer = 1200;
	}
	/**
	 * Returns whether the rock is on a respawn timer or not
	 * @return whether the rock is on a respawn timer or not
	 */
	public boolean isBroken() {
		return respawnTimer > 0;
	}
	/**
	 * Decrements the respawn timer of the rock
	 * @param list The list of movingimages on the map
	 */
	public MovingImage act(ArrayList<MovingImage> list) {
		if (respawnTimer > 0)
			respawnTimer--;
		return super.act(list);
	}
}
