import java.util.ArrayList;

import processing.core.PImage;
/**
 * represents a explobot baby
 * @author Zackery He
 *
 */
public class ExploBotBaby extends Bot{
	private final int SPEED = 10;
	/**
	 * 
	 * @param image image displayed to represent the bot
	 * @param i2 image used as an attack animation
	 * @param x x coord of the bot
	 * @param y y coord of the bot
	 * @param width width of the image
	 * @param height height of the image
	 */
	public ExploBotBaby(PImage image, PImage i2, double x, double y, int width, int height) {
		super(image, x,y,width,height,50);
		
	}
	/**
	 * handles how the bot will act
	 * @param list list of all the MovingImages in the game
	 * @return the bot that will be removed in this process
	 */
	//taken from blind bot
	public MovingImage act(ArrayList<MovingImage> list){
		if (counter%100 == 0){
			Player p = null;
			for (MovingImage m : list) {
				if (m instanceof Player) {
					p = (Player) m;
				}
			}
			int pX = (int) p.getX();
			int pY = (int) p.getY();
//			list.add(this.shoot(pX, pY));  doesnt shoot anything
		} else if(counter%20 == 0){
			Player p = null;
			for (MovingImage m : list) {
				if (m instanceof Player) {
					p = (Player) m;
				}
			}
			double angle = Math.tan((p.getY() - this.y) / (p.getX() - this.x));

			vY = SPEED*(int)Math.sin(angle);
			vX = SPEED*(int)Math.cos(angle);
		}
		else {
			vY /= 2;
			vX /= 2;
		}
		counter++; //Adds to counter
		if(!this.isInWindow() || this.isDead()){
			return this;
		}    
		return null;

	}
	/**
	 * kills the bot
	 */
	public void die() {
		
	}
	
	/**
	 * shoots projectiles in the attack pattern of the bot
	 * @return an arraylist of the bullet pattern
	 */
	public ArrayList<Projectile> shoot(int x, int y) {
		ArrayList<Projectile> pattern = new ArrayList<Projectile>();
		for(int i = 0; i < 8; i++) {
			pattern.add(new ExploBotBabyProjectile(DrawingSurface.explobullet, x, y, 20, 20, "explobotbaby", 45*i*Math.PI/180, 0));

		}
		return pattern;
	}
	/**
	 * returns the id of the bot
	 * @return the id of the bot
	 */
	public String toString() {
		return "explobotbaby";
	}
	
	

}
