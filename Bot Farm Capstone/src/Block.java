import processing.core.PImage;

/**
 * Represents a Block (tile of DrawingSurface).
 * @author Harry Guan 
 */
public class Block extends MovingImage {
	
	/**
	 * Constructs a Block.
	 * @param image The image to be used.
	 * @param x The x-coordinate of this Block.
	 * @param y The y-coordinate of this Block.
	 * @param width The width of this Block.
	 * @param height The height of this Block.
	 */
	public Block(PImage image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
    }
}
