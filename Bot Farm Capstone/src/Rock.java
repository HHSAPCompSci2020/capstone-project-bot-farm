import processing.core.PImage;

/**
 * Represents a Rock. 
 * @author Michael Chen
 *
 */
public class Rock extends Block {

	/**
	 * Constructs a Rock.
	 * @param image The image to be used.
	 * @param x The x-coordinate of this Rock.
	 * @param y The y-coordinate of this Rock.
	 * @param width The width of this Rock.
	 * @param height The height of this Rock.
	 */
	public Rock(PImage image, int x, int y, int width, int height) {
		super(image, x, y, width, height);
	}

}
