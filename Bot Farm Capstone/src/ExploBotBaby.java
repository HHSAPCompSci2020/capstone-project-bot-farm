import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PImage;
/**
 * Represents a ExploBotBaby.
 * @author Zackery He
 *
 */
public class ExploBotBaby extends Bot{
	private final int SPEED = 5;
	private final int DMG_RADIUS = 100;

	/**
	 * Constructs an ExploBotBaby.
	 * @param image The image that corresponds to this ExploBotBaby.
	 * @param x The x-coordinate of the top left corner of the ExploBotBaby.
	 * @param y The y-coordinate of the top left corner of the ExploBotBaby.
	 * @param width The width of the ExploBotBaby.
	 * @param height The height of the ExploBotBaby.
	 */
	public ExploBotBaby(PImage image, double x, double y, int width, int height) {
		super(image, x,y,width,height,10);
	}
	
	/**
	 * Handles the movement and shooting patterns of the ExploBotBaby.
	 * @param list The list of entities currently in the game.
	 * @return The ExploBotBaby to be removed in this process.
	 */
	public MovingImage act(ArrayList<MovingImage> list){
		Player p = null;
		for (MovingImage m : list) {
			if (m instanceof Player)
				p = (Player) m;
		} 
		if (counter % 20 == 0) {
			Point2D.Double pointP = new Point2D.Double(p.getX(), p.getY());
			double angle = Math.atan2(p.getY() - this.y, p.getX() - this.x);
			double finalAngle = angle;
			double smallestDistance = Integer.MAX_VALUE;
			for (int i = 0; i < 360; i++) {
				double newAngle = angle + Math.toRadians(i);
				Rectangle2D.Double newPos = new Rectangle2D.Double(x + SPEED*Math.cos(newAngle),
						y + SPEED*Math.sin(newAngle), width, height);
				boolean intersects = false;
				for (MovingImage s : list) {
					if (!newPos.intersects(DrawingSurface.getBorder()) ||
							(newPos.intersects(s) && s instanceof Block && !(s instanceof NoClipBlock))) {
						intersects = true;
					}
				}
				if (!intersects) {
					double distance = pointP.distance(newPos.getX(), newPos.getY());
					if (distance < smallestDistance) {
						smallestDistance = distance;
						finalAngle = newAngle;
					}
				}
			}
			vY = (int) (SPEED*Math.sin(finalAngle));
			vX = (int) (SPEED*Math.cos(finalAngle));
		}
		Rectangle2D.Double posX = new Rectangle2D.Double(x + vX, y, width, height);
		Rectangle2D.Double posY = new Rectangle2D.Double(x, y + vY, width, height);
		for (MovingImage s : list) {
			if (this != s) {
				if (posX.intersects(s) && s instanceof Block && !(s instanceof NoClipBlock))
					vX = 0;
				if (posY.intersects(s) && s instanceof Block && !(s instanceof NoClipBlock))
					vY = 0;
			}
		}
		double distance = Math.sqrt(Math.pow(p.getY() - this.y, 2) + Math.pow(p.getX() - this.x, 2));
		if (distance <= DMG_RADIUS) {
			explode(list);
		}
		
			
		counter++; //Adds to counter
		if(!this.isInWindow() || this.isDead())
			return this;
		this.moveByAmount(vX, vY);
		return null;

	}
	
	/**
	 * explodes the bot
	 */
	public void explode(ArrayList<MovingImage> list) {
		Player p = null;
		for (MovingImage m : list) {
			if (m instanceof Player)
				p = (Player) m;
		} 
		for (MovingImage m : shoot((int)p.getX(),(int)p.getY()))
			list.add(m);
		super.die();
	}
	
	/**
	 * Shoots a ExploBotBabyProjectile and returns it.
	 * @param x The x-coordinate of the destination of the ExploBotBabyProjectile.
	 * @param y The y-coordinate of the destination of the ExploBotBabyProjectile.
	 * @return The ExploBotBabyProjectile fired.
	 */
	public ArrayList<Projectile> shoot(int x, int y) {
		ArrayList<Projectile> pattern = new ArrayList<Projectile>();
		for(int i = 0; i < 8; i++) {
			pattern.add(new ExploBotBabyProjectile(DrawingSurface.explobullet, (int)getX(), (int)getY(), 20, 20, "explobotbaby", 45*i*Math.PI/180, 100));

		}
		return pattern;
	}
	
	/**
	 * Returns the ID of the ExploBotBaby.
	 * @return The ID of the ExploBotBaby.
	 */
	public String toString() {
		return "explobotbaby";
	}
	
}
