import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * represents an ExploBot
 * @author Zackery He
 *
 */
public class ExploBot extends Bot {
	protected int counter;
	protected boolean dead;
	
	/**
	 * 
	 * @param image image that will be displayed on the location of the bot
	 * @param i2 a second image that will be used as an attack animation
	 * @param x the x coord of the bot
	 * @param y the y coord of the bot
	 * @param width the width of the image displayed
	 * @param height the height of the image displayed
	 */
	public ExploBot(PImage image, PImage i2, double x, double y, int width, int height) {
		super(image, x,y,width,height,50);
		this.counter = 0;
	}
	
	/**
	 * Handles behavior of the bot
	 * @param list an arraylist containing all movingimages
	 */
	public MovingImage act(ArrayList<MovingImage> list) {
		list.add(new ExploBotBaby(image, image, (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight()));
		return super.act(list);
	}
	/**
	 * does nothing because this bot doesn't shoot
	 * @param x does nothing
	 * @param y does nothing
	 */
	@Override
	public ArrayList<Projectile> shoot(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * returns the id of the bot
	 * @return the id of the bot
	 */
	public String toString() {
		return "explobot";
	}

}
