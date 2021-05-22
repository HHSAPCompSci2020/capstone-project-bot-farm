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
    	Player p = null;
		for (MovingImage m : list) {
			if (m instanceof Player)
				p = (Player) m;
		}
		if (counter%100 == 0){
			glitch();
			int pX = (int) p.getX();
			int pY = (int) p.getY();
			for (MovingImage m : this.shoot(pX, pY))
				list.add(m);
		} 
		
		counter++; //Adds to counter
		if(!this.isInWindow() || this.isDead())
			return this;
		return null;

	}
    
    /**
	 * Shoots a GlitchProjectile and returns it
	 * @param x The x-coordinate of the destination of the projectile
	 * @param y The y-coordinate of the destination of the projectile
	 * @return The BlindProjectile fired
	 */
    public ArrayList<Projectile> shoot(int x, int y){
    	GlitchProjectile proj = new GlitchProjectile(DrawingSurface.glitchbullet, this.x, this.y, (int)width, (int)height, 
				"glitchbot", Math.atan2((double)y - this.y, (double)x - this.x), 240);
		ArrayList<Projectile> projs = new ArrayList<Projectile>();
		projs.add(proj);
		return projs;
    }
    
    /**
     * Teleports to random location nearby.
     */
    public void glitch() {
    	this.moveByAmount((Math.random() * 200) - 100, (Math.random() * 200) - 100);
    }
	
    /**
	 * Returns the ID of this bot
	 * @return the ID of this bot
	 */
    public String toString() {
    	return "glitchbot";
    }
}
