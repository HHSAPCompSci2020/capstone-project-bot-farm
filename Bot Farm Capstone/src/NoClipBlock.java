import processing.core.PImage;

/**
 * Represents a Block which can be moved through by all entities. 
 * @author Harry Guan 
 *
 */
public class NoClipBlock extends Block {
	/**
	 * Constructs a NoClipBlock.
	 * @param image The image to be used.
	 * @param x The x-coordinate of this NoClipBlock.
	 * @param y The y-coordinate of this NoClipBlock.
	 * @param width The width of this NoClipBlock.
	 * @param height The height of this NoClipBlock.
	 */
	public NoClipBlock(PImage image, int x, int y, int width, int height) {
        super(image,x,y,width,height);
    }
}
