import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import processing.core.PImage;

/**
 * Represnts a BlindBot.
 * @author Michael Chen
 */
public class BlindBot extends Bot {
	
	private final double DMG_RADIUS = 50;
	private final int AOE_DMG = 5;
	private final int SPEED = 15;
	/**
	 * Constructs a BlindBot.
	 * @param image The image that corresponds to this BlindBot.
	 * @param x The x-coordinate of the top left corner of the BlindBot.
	 * @param y The y-coordinate of the top left corner of the BlindBot.
	 * @param width The width of the BlindBot.
	 * @param height The height of the BlindBot.
	 * @param hp The amount of HP the BlindBot has.
	 */
	public BlindBot(PImage image, int x, int y, int width, int height, int hp) {
		super(image, x, y, width, height, hp);
	}

	/**
	 * Damages any nearby players.
	 * @param player The player to be damaged.
	 */
	public void damage(Player player) {
		double distance = Math.sqrt(Math.pow(player.getY() - this.y, 2) + Math.pow(player.getX() - this.x, 2));
		if (distance <= DMG_RADIUS)
			for (int i = 0; i < AOE_DMG; i++)
				player.loseHP();
	}
	/**
	 * Handles the movement and shooting patterns of the BlindBot.
	 * @param list The list of entities currently in the game.
	 * @return The BlindBot to be removed in this process.
	 */
	public MovingImage act(ArrayList<MovingImage> list){
		Player p = null;
		for (MovingImage m : list) {
			if (m instanceof Player)
				p = (Player) m;
		}
		if (counter%100 == 0){
			int pX = (int) p.getX();
			int pY = (int) p.getY();
			for (MovingImage m : this.shoot(pX, pY))
				list.add(m);
		} else if(counter%20 == 0){
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
		} else if (counter % 8 == 0) 
			damage(p);
		else {
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
			vY /= 1.5;
			vX /= 1.5;
		}
		counter++; //Adds to counter
		if(!this.isInWindow() || this.isDead())
			return this;
		this.moveByAmount(vX, vY);
		return null;

	}
	/**
	 * Shoots a BlindProjectile and returns it.
	 * @param x The x-coordinate of the destination of the BlindProjectile.
	 * @param y The y-coordinate of the destination of the BlindProjectile.
	 * @return The BlindProjectile fired.
	 */
	public ArrayList<Projectile> shoot(int x, int y) {
		BlindProjectile proj = new BlindProjectile(DrawingSurface.blindbullet, this.x, this.y, (int)width, (int)height, 
				"blindbot", Math.atan2((double)y - this.y, (double)x - this.x), 240);
		ArrayList<Projectile> projs = new ArrayList<Projectile>();
		projs.add(proj);
		return projs;
	}

	/**
	 * Returns the ID of this BlindBot.
	 * @return the ID of this BlindBot.
	 */
	public String toString() {
		return "blindbot";
	}
}
