import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class GlitchBot extends Bot {

	protected PImage o2;
	protected int vX, vY; //Velocity x and y
	protected int counter; //Tracks the time. Increases by 1 once every 1/60th of a second.
    protected boolean edead; //true or false dead
    protected int shootSpriteTimer;
	
    public GlitchBot(PImage image, PImage i2, int x, int y, int width, int height){
        super(image, x, y, width, height, 30);
	//Initialize all variables. Make sure you use the super() constructor, passing in the image, x, y, width, and height.
	//velocity should start at 0, as should counter
        this.vX = 0;
        this.vY = 0;
        this.counter = 0;
        this.ehp = 30;
        this.edead = false; //not dead :(
        this.o2 = i2;
        this.shootSpriteTimer = 0;
        o2.resize(width,height);
    }
    
    public Projectile shoot(int x, int y){
        shootSpriteTimer = 10;
        //To get the x and the y coordinates of the enemy
        double sX = x - this.getX();
        double sY = y - this.getY();
        double tann = sY/sX;
        double angle = Math.atan(tann);
        if(x < this.getX()) {
            angle = angle + Math.PI;
        }
        
        return new Projectile(DrawingSurface.slowShot, (int) this.getX(), (int) this.getY(), 10, 20, "enemy", angle, 10000);
    }
    
    public void glitch() {
    	
    }
	
    public String toString() {
    	return "bot";
    }
	
}
