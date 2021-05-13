import java.util.ArrayList;

import processing.core.PImage;
/**
 * Represents a BlindBot, which has an area of effect attack and a ranged attack which blinds.
 * @author Michael Chen
 *
 */
public class BlindBot extends Bot {
	private final double DMG_RADIUS = 5;
	private final int AOE_DMG = 5;
	private final int SPEED = 5;
	
	/**
	 * Creates a BlindBot with a specified image and hitbox
	 * @param image The image for this bot
	 * @param x The x-coordinate for this bot
	 * @param y The y-coordinate for this bot
	 * @param width The width of the image
	 * @param height The height of the image
	 * @param hp The amount of health the bot has
	 * @pre image cannot be null
	 */
	public BlindBot(PImage image, int x, int y, int width, int height, int hp) {
		super(image, x, y, width, height, hp);
	}

	/**
	 * Damages players within a certain radius of the bot
	 * @param player
	 */
	public void damage(Player player) {
		double distance = Math.sqrt(Math.pow(player.getY() - this.y, 2) + Math.pow(player.getX() - this.x, 2));
		if (distance <= DMG_RADIUS)
			for (int i = 0; i < AOE_DMG; i++)
				player.loseHP();
	}
	/**
	 * Controls the bots movement and shooting patterns
	 * @param list The list of entities in the game
	 * @return The entities to be removed in this frame
	 */
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
			for (MovingImage m : this.shoot(pX, pY))
				list.add(m);
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
	 * Shoots the projectiles that correspond to this bot
	 * @param x The x-coordinate of the destination of the projectile
	 * @param y The y-coordinate of the destination of the projectile
	 * @return The projectiles fired
	 */
	public ArrayList<Projectile> shoot(int x, int y) {
		BlindProjectile proj = new BlindProjectile(image, this.x, this.y, (int)width, (int)height, 
				"blindbot", Math.tan((double)y / (double)x), 0);
		ArrayList<Projectile> projs = new ArrayList<Projectile>();
		projs.add(proj);
		return projs;
	}
	
	/**
	 * Returns the ID of this bot
	 * @return the ID of this bot
	 */
	public String toString() {
		return "blindbot";
	}

}
