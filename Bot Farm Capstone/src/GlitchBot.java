import java.util.ArrayList;
import processing.core.PImage;

/**
 * Represnts a GlitchBot.
 * @author Harry Guan
 */
public class GlitchBot extends Bot {
	
    public final int SPEED = 5;
	
    /**
	 * Constructs an GlitchBot.
	 * @param image The image that corresponds to this GlitchBot.
	 * @param x The x-coordinate of the top left corner of the GlitchBot.
	 * @param y The y-coordinate of the top left corner of the GlitchBot.
	 * @param width The width of the GlitchBot.
	 * @param height The height of the GlitchBot.
	 * @param hp The amount of HP the GlitchBot has.
	 */
    public GlitchBot(PImage image, int x, int y, int width, int height, int hp){
		super(image, x, y, width, height, hp);
    }
    
    /**
	 * Handles the shooting patterns of the GlitchBot.
	 * Also periodically glitches around teleporting to random nearby locations.
	 * @param list The list of entities currently in the game.
	 * @return The GlitchBot to be removed in this process.
	 */
    public MovingImage act(ArrayList<MovingImage> list){
    	Player p = null;
		for (MovingImage m : list) {
			if (m instanceof Player)
				p = (Player) m;
		}
		if (counter%75 == 0){
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
	 * Shoots a GlitchProjectile and returns it.
	 * @param x The x-coordinate of the destination of the GlitchProjectile.
	 * @param y The y-coordinate of the destination of the GlitchProjectile.
	 * @return The GlitchProjectile fired.
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
     * Cannot be outside the grid.
     */
    public void glitch() {
    	double newx = (Math.random() * 200) - 100;
    	double newy = (Math.random() * 200) - 100;
    	GlitchBot temp = new GlitchBot(DrawingSurface.glitchb, (int)newx, (int)newy, 50, 50, 100);
    	if (!(temp.intersects(DrawingSurface.getBorder())))
    		this.moveByAmount(newx, newy);
    }
	
    /**
	 * Returns the ID of this bot
	 * @return the ID of this bot
	 */
    public String toString() {
    	return "glitchbot";
    }
}
