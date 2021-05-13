
import java.util.ArrayList;

import processing.core.PImage;

public abstract class Bot extends MovingImage {
    
    protected int ehp, vX, vY; //enemy hp, velocity x and velocity y
    protected final int OEHP; //max hp
    protected boolean edead; //true or false dead
    
    public Bot(PImage image, double x, double y, int width, int height, int hp){
            super(image, x, y, width, height);
            this.OEHP = hp;
            this.ehp = OEHP;
            this.vX = 0;
            this.vY = 0;
            this.edead = false; //not dead :(
    }
    
    public void setvX(int x){
        vX = x;
    }

    public void setvY(int y) {
        vY = y;
    }
  
    
    public void loseHP(){
        ehp -= 5;
        if(ehp <= 0){
            die(); //dies when hp less than zero
        }
    }

    public static void sound(String filepath) {
       
    }

    
        
    public void die() {
    	edead = true;
    }

    public boolean isDead(){
        return edead; //Change the below statement to return an accurate value.
    }
    
    public abstract ArrayList<Projectile> shoot(int x, int y);
    
    
}