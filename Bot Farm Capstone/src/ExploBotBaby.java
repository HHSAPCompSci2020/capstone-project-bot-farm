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
	public ExploBotBaby(PImage image, double x, double y, int width, int height) {
		super(image, x,y,width,height,50);
		
	}
	/**
	 * handles how the bot will act
	 * @param list list of all the MovingImages in the game
	 * @return the bot that will be removed in this process
	 */
	//taken from blind bot
	public MovingImage act(ArrayList<MovingImage> list){
		Player p = null;
		for (MovingImage m : list) {
			if (m instanceof Player) {
				p = (Player) m;
			}
		}
		if (counter%100 == 0){
			int pX = (int) p.getX();
			int pY = (int) p.getY();
			for (MovingImage m : this.shoot(pX, pY))
				list.add(m);
		} else if(counter%20 == 0){
			double angle = Math.atan2(p.getY() - this.y, p.getX() - this.x);
			vY = (int) (SPEED*Math.sin(angle));
			vX = (int) (SPEED*Math.cos(angle));
		} 
		else {
			vY /= 1.5;
			vX /= 1.5;
		}
		counter++; //Adds to counter
		if(!this.isInWindow() || this.isDead()){
			return this;
		}
		this.moveByAmount(vX, vY);
		return null;

	}
	/**
	 * kills the bot
	 */
	public void die() {
		shoot((int)getX(), (int)getY());
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
