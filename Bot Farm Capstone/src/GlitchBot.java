import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;


/**
 * Represnts a GlitchBot.
 * @author Harry Guan
 */
public class GlitchBot extends Bot {

	protected PImage o2;
	protected int vX, vY; //Velocity x and y
	protected int counter; //Tracks the time. Increases by 1 once every 1/60th of a second.
    protected boolean edead; //true or false dead
    protected int shootSpriteTimer;
    public final int SPEED;
	
    /**
	 * Constructs a GlitchBot.
	 * @param image The image that corresponds to this bot
	 * @param x The x-coordinate of the top left corner of the bot
	 * @param y The y-coordinate of the top left corner of the bot
	 * @param width The width of the bot
	 * @param height The height of the bot
	 * @param hp The amount of HP the bot has
	 */
    public GlitchBot(PImage image, PImage i2, int x, int y, int width, int height){
		super(image, x, y, width, height, 30);
		this.SPEED = 5;
        this.vX = 0;
        this.vY = 0;
        this.counter = 0;
        this.ehp = 30;
        this.edead = false; //not dead :(
        this.o2 = i2;
        this.shootSpriteTimer = 0;
        o2.resize(width,height);
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
        shootSpriteTimer = 10;
        double sX = x - this.getX();
        double sY = y - this.getY();
        double tann = sY/sX;
        double angle = Math.atan(tann);
        if(x < this.getX()) {
            angle = angle + Math.PI;
        }
    
        ArrayList<Projectile> pattern = new ArrayList<Projectile>();
        pattern.add(new GlitchProjectile(DrawingSurface.glitchbullet, (int) this.getX(), (int) this.getY(), 10, 20, "glitchbot", angle, 10000));
        return pattern;
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
