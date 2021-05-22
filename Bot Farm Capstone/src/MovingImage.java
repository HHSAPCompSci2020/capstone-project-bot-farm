import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

@SuppressWarnings("serial")
public class MovingImage extends Rectangle2D.Double {
	
	protected int counter;
	protected PImage image;
	
	/**
	 * Constructs a MovingImage. 
	 * @param img The image to be used
	 * @param x The x-coordinate of the MovingImage
	 * @param y The y-coordinate of the MovingImage
	 * @param w The width of the MovingImage
	 * @param h The height of the MovingImage
	 */
	public MovingImage(PImage img, double x, double y, int w, int h) {
		super(x,y,w,h);
		image = img;
		image.resize(w, h);
	}
	
	/**
	 * Moves the MovingImage to (x, y)
	 * @param x The x-coordinate to move to
	 * @param y The y-coordinate to move to
	 */
	public void moveToLocation(double x, double y) {
		super.x = x;
		super.y = y;
	}
	
	/**
	 * Moves the MovingImage by x units to the right and y units towards the bottom
	 * @param x The x amount to move by
	 * @param y The y amount to move by
	 */
	public void moveByAmount(double x, double y) {
		super.x += x;
		super.y += y;
	}
	
	/**
	 * Apply the window limits for this MovingImage
	 * @param windowWidth window width to use
	 * @param windowHeight window height to use
	 */
	public void applyWindowLimits(int windowWidth, int windowHeight) {
		x = Math.min(x,windowWidth-width);
		y = Math.min(y,windowHeight-height);
		x = Math.max(0,x);
		y = Math.max(0,y);
	}
	
    /**
     * Draw this MovingImage on the PApplet.
     * @param marker The PApplet to draw on
     */
	public void draw(PApplet marker) {
        marker.image(image, (float)((int)(this.getX())), (float)((int)(this.getY())));
	}
	
	/**
	 * Act method for MovingImage
	 * @param list The ArrayList of MovingImages
	 * @return null
	 */
	public MovingImage act(ArrayList<MovingImage> list){
		return null;
	}
	
	/**
	 * Check if the MovingImage is in the window
	 * @return if the MovingImage is in the window
	 */
	public boolean isInWindow(){
		return this.intersects(DrawingSurface.getBorder());
	}
}