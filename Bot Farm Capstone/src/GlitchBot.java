import java.util.ArrayList;
import processing.core.PImage;

/**
 * Represnts a GlitchBot.
 * @author Harry Guan
 */
public class GlitchBot extends Bot {
	
    public final int SPEED = 5;
	
    /**
	 * Constructs a GlitchBot.
	 * @param image The image that corresponds to this bot
	 * @param x The x-coordinate of the top left corner of the bot
	 * @param y The y-coordinate of the top left corner of the bot
	 * @param width The width of the bot
	 * @param height The height of the bot
	 * @param hp The amount of HP the bot has
	 */
    public GlitchBot(PImage image, int x, int y, int width, int height, int hp){
		super(image, x, y, width, height, hp);
    }
    
    /**
	 * Handles the movement and shooting patterns of the bot
	 * Also periodically glitches around teleporting to random locations nearby
	 * @param list The list of entities currently in the game
	 * @return the Bot to be removed in this process
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
				this.add(m);
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
		glitch();
		counter++; //Adds to counter
		if(!this.isInWindow() || this.isDead()){
			return this;
		}    
		return null;

	}
    
    /**
	 * Shoots a GlitchProjectile and returns it
	 * @param x The x-coordinate of the destination of the projectile
	 * @param y The y-coordinate of the destination of the projectile
	 * @return The BlindProjectile fired
	 */
    public ArrayList<Projectile> shoot(int x, int y){
    	GlitchProjectile proj = new GlitchProjectile(image, this.x, this.y, (int)width, (int)height, 
				"glitchbot", Math.tan((double)y / (double)x), 0);
		ArrayList<Projectile> projs = new ArrayList<Projectile>();
		projs.add(proj);
		return projs;
    }
    
    /**
     * Teleports to random location nearby.
     */
    public void glitch() {
    	this.moveByAmount(Math.random() * 10, Math.random() * 10);
    }
	
    /**
	 * Returns the ID of this bot
	 * @return the ID of this bot
	 */
    public String toString() {
    	return "glitchbot";
    }
}
