import java.util.ArrayList;
import processing.core.PImage;

/**
 * Represents an ExploBot
 * @author Zackery He
 *
 */
public class ExploBot extends Bot {
	
	/**
	 * Constructs an ExploBot.
	 * @param image The image that corresponds to this ExploBot.
	 * @param x The x-coordinate of the top left corner of the ExploBot.
	 * @param y The y-coordinate of the top left corner of the ExploBot.
	 * @param width The width of the ExploBot.
	 * @param height The height of the ExploBot.
	 * @param hp The amount of HP the ExploBot has.
	 */
	public ExploBot(PImage image, double x, double y, int width, int height, int hp) {
		super(image, x,y,width,height,hp);
	}
	
	/**
	 * Handles the movement and shooting patterns of the ExploBot.
	 * @param list The list of entities currently in the game.
	 * @return The ExploBot to be removed in this process.
	 */
	public MovingImage act(ArrayList<MovingImage> list) {
		if(counter%100 == 0 && counter != 0) {
			loseHP(5);
		}
		if(counter%300 == 0)
		list.add(new ExploBotBaby(DrawingSurface.explobb, (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight()));
		counter++;
		if(!this.isInWindow() || this.isDead())
			return this;
		return null;
	}
	
	
	/**
	 * Does nothing because ExploBot does not shoot.
	 * @param x Does nothing.
	 * @param y Does nothing.
	 */
	@Override
	public ArrayList<Projectile> shoot(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Returns the ID of the ExploBot.
	 * @return the ID of the ExploBot.
	 */
	public String toString() {
		return "explobot";
	}

}
