import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class GlitchBot extends Bot {

	protected PImage o2;
	protected int vX, vY; //Velocity x and y
	protected int counter; //Tracks the time. Increases by 1 once every 1/60th of a second.
    protected boolean edead; //true or false dead
    protected int shootSpriteTimer;
    public final int SPEED;
	
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
			list.add(this.shoot(pX, pY));
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
    
    public ArrayList<Projectile> shoot(int x, int y){
        shootSpriteTimer = 10;
        //To get the x and the y coordinates of the enemy
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
    
    public void glitch() {
    	this.moveByAmount(Math.random() * 10, Math.random() * 10);
    }
	
    public String toString() {
    	return "glitchbot";
    }
	
}
