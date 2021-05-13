import java.util.ArrayList;
import processing.core.PImage;
/**
 * Represnts a BlindBot.
 * @author Michael Chen
 *
 */
public class BlindBot extends Bot {
	private final double DMG_RADIUS = 100;
	private final int AOE_DMG = 5;
	private final int SPEED = 15;
	
	/**
	 * 
	 * @param image The image that corresponds to this bot
	 * @param x The x-coordinate of the top left corner of the bot
	 * @param y The y-coordinate of the top left corner of the bot
	 * @param width The width of the bot
	 * @param height The height of the bot
	 * @param hp The amount of HP the bot has
	 */
	public BlindBot(PImage image, int x, int y, int width, int height, int hp) {
		super(image, x, y, width, height, hp);
	}

	/**
	 * Damages any nearby players
	 * @param player The player to be damaged
	 */
	public void damage(Player player) {
		double distance = Math.sqrt(Math.pow(player.getY() - this.y, 2) + Math.pow(player.getX() - this.x, 2));
		if (distance <= DMG_RADIUS)
			for (int i = 0; i < AOE_DMG; i++)
				player.loseHP();
	}
	/**
	 * Handles the movement and shooting patterns of the bot
	 * @param list The list of entities currently in the game
	 * @return the bot to be removed in this process
	 */
	public MovingImage act(ArrayList<MovingImage> list){
		Player p = null;
		for (MovingImage m : list) {
			if (m instanceof Player) {
				p = (Player) m;
			}
		}
		damage(p);
		if (counter%100 == 0){
			int pX = (int) p.getX();
			int pY = (int) p.getY();
			for (MovingImage m : this.shoot(pX, pY))
				list.add(m);
		} else if(counter%20 == 0){
			double angle = Math.tan((p.getY() - this.y) / (p.getX() - this.x));
			vY = (int) (SPEED*Math.sin(angle));
			vX = (int) (SPEED*Math.cos(angle));
		}
		else {
			vY /= 1.25;
			vX /= 1.25;
		}
		counter++; //Adds to counter
		if(!this.isInWindow() || this.isDead()){
			return this;
		}
		this.moveByAmount(vX, vY);
		return null;

	}
	/**
	 * Shoots a BlindProjectile and returns it
	 * @param x The x-coordinate of the destination of the projectile
	 * @param y The y-coordinate of the destination of the projectile
	 * @return The BlindProjectile fired
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
