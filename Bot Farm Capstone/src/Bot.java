import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
/**
 * The superclass for all Bots.
 * @author Michael Chen
 */
public abstract class Bot extends MovingImage {
    
    protected int ehp, vX, vY; //enemy hp, velocity x and velocity y
    protected final int OEHP; //max hp
    protected boolean edead; //true or false dead
    
    /**
     * Constructs a Bot.
     * @param image The image for the Bot.
     * @param x The x-coordinate of the top left corner of the Bot.
     * @param y The y-coordinate of the top left corner of the Bot.
     * @param width The width of the Bot.
     * @param height The height of the Bot.
     * @param hp The amount of HP the Bot has.
     */
    public Bot(PImage image, double x, double y, int width, int height, int hp){
            super(image, x, y, width, height);
            this.OEHP = hp;
            this.ehp = OEHP;
            this.vX = 0;
            this.vY = 0;
            this.edead = false; //not dead :(
    }
    
    /**
     * Sets the velocity of the Bot in the x-direction.
     * @param x The new x-velocity.
     */
    public void setvX(int x){
        vX = x;
    }

    /**
     * Sets the velocity of the Bot in the y-direction.
     * @param y The new y-velocity. 
     */
    public void setvY(int y) {
        vY = y;
    }
  
    /**
     * Causes the Bot to lose exactly 1 hp, automatically dies if ehp is below 0.
     */
    public void loseHP(){
        ehp -= 1;
        if(ehp <= 0){
            die(); //dies when hp less than zero
        }
    }
    
    /**
     * Causes the Bot to lose n hp, automatically dies of ehp is below 0. 
     * @param n The amount of hp to lose.
     */
    public void loseHP(int n) {
    	ehp -=n;
    	if(ehp <= 0)die();
    }
    
    /**
     * Causes the Bot to die; sets the boolean to true.
     */
    public void die() {
    	edead = true;
    }
    
    /**
     * Draws the health indicator for the Bot.
     * @param marker The PApplet to draw the health indicator on. 
     */
    public void draw(PApplet marker) {
		super.draw(marker);
		marker.fill(200);
		marker.rect((float) (this.getX()), (float) (this.getCenterY()-height), (float) (OEHP/2), 10f);
		if (ehp > OEHP * 0.75) {
			marker.fill(200, 0, 0); //stats at bright red
		} else if (ehp > OEHP / 2) {
			marker.fill(150, 0 , 0); // change bar to red
		} else if (ehp > OEHP * 0.25) {
			marker.fill(100, 0, 0); //change bar to dark red
		} else {
			marker.fill(50, 0, 0); //change bar to blackish red
		} 
		if (ehp > 0) {
			marker.rect((float) (this.getX()), (float) (this.getCenterY()-height), (float) (40 * ehp/2 / 40.0), 10f);
		}

	}

    /**
     * Returns if the Bot is dead or not.
     * @return Whether the Bot is dead or not.
     */
    public boolean isDead(){
        return edead; //Change the below statement to return an accurate value.
    }
    
    /**
     * Shoots Projectiles and returns them.
     * @param x The x-coordinate of the destination of the Projectile.
     * @param y The y-coordinate of the destination of the Projectile.
     * @return The Projectiles fired.
     */
    public abstract ArrayList<Projectile> shoot(int x, int y);
    
    
}