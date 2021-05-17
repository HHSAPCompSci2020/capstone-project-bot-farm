
import java.util.ArrayList;

import processing.core.PImage;
/**
 * The superclass for all bots
 * @author Michael Chen
 *
 */
public abstract class Bot extends MovingImage {
    
    protected int ehp, vX, vY; //enemy hp, velocity x and velocity y
    protected final int OEHP; //max hp
    protected boolean edead; //true or false dead
    
    /**
     * 
     * @param image The image for the bot
     * @param x The x-coordinate of the top left corner of the bot
     * @param y The y-coordinate of the top left corner of the bot
     * @param width The width of the bot
     * @param height The height of the bot
     * @param hp The amount of HP the bot has
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
     * Sets the velocity of the bot in the x-direction
     * @param x The new velocity 
     */
    public void setvX(int x){
        vX = x;
    }

    /**
     * Sets the velocity of the bot in the y-direction
     * @param y The new velocity 
     */
    public void setvY(int y) {
        vY = y;
    }
  
    /**
     * Causes the bot to lose exactly 1 hp, automatically dies if ehp is below 0
     */
    public void loseHP(){
        ehp -= 1;
        if(ehp <= 0){
            die(); //dies when hp less than zero
        }
    }
    
    /**
     * causes the bot to lose n hp
     * @param n the amount of hp to lose
     */
    public void loseHP(int n) {
    	ehp -=n;
    	if(ehp <= 0)die();
    }

    public static void sound(String filepath) {
       
    }

    
    /**
     * Causes the bot to die
     */
    public void die() {
    	edead = true;
    }

    /**
     * Returns if the bot is dead or not
     * @return whether the bot is dead or not
     */
    public boolean isDead(){
        return edead; //Change the below statement to return an accurate value.
    }
    
    /**
     * Shoots projectiles from and returns them 
     * @param x The x-coordinate of the destination of the projectile
     * @param y The y-coordinate of the destination of the projectile
     * @return The projectiles fired
     */
    public abstract ArrayList<Projectile> shoot(int x, int y);
    
    
}