import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * represents the player
 * @author Zackery He
 *
 */
public class Player extends MovingImage {
	protected int vX, vY;
	protected double hp, mp;
	protected boolean dead;
	protected final int ohp;

	/**
	 * 
	 * @param image image displayed at the players coords
	 * @param x x coord of the player
	 * @param y y coord of the player
	 * @param width width of the image
	 * @param height height of the image
	 */
	public Player(PImage image, double x, double y, int width, int height) {
		super(image, x, y, width, height);
		//vX and vY should be initially 0, hp should start at 20, and dead should be false.
		vX = 0;
		vY = 0;
		hp = 100;
		dead = false;
		ohp = (int)hp;
	}

	/**
	 * shoots a projectile for the player
	 * @param x x coordinate of the initial position for the projectile
	 * @param y y coordinate of the initial position for the projectile
	 * @return the projectile being shot
	 */
	public Projectile shoot(int x, int y) {
		double sX = x - this.getX();
		double sY = y - this.getY();
		double tann = sY/sX;
		double angle = Math.atan(tann);

		if(x < this.getX()) {
			angle = angle + Math.PI;
		}
		if (isDead() == false){
			return new AndroidBasicProjectile(DrawingSurface.androidbullet, (int)this.getX(), (int)this.getY(), 20, 35, "player", angle,50);
		} else {
			return null;
		}
	}
	
	
	public Projectile launchMissile(int x, int y) {
		return new AndroidMissile(DrawingSurface.missile, (int)this.getX(), (int)this.getY(), 20, 35, "player", 500);
	}

	/**
	 * changes the x velocity of the player
	 * @param x the velocity of the player to be set
	 */
	public void setvX(int x) {
		vX = x;
	}
	/**
	 * changes the y velocity of the player
	 * @param y the velocity of the player to be set
	 */
	public void setvY(int y) {
		vY = y;
	}
	public int getVx() {
		return vX;
	}
	public int getVy() {
		return vY;
	}

	/**
	 * causes the player to lose 1 hp
	 */
	public void loseHP() {
		//If hp is less than 0, die
		hp --;
		if (hp <= 0) {
			die();
		}

	}
	/**
	 * causes the player to lose n hp
	 * @param n amount of hp to lose
	 */
	public void loseHP(int n) {
		hp-=n;
		if(hp<=0)die();
	}

	/**
	 * causes the player to die
	 */
	private void die() {
		dead = true;
		//Sets the dead boolean to true
	}

	/**
	 * checks if the player is dead
	 * @return true if the player is dead, false if not
	 */
	public boolean isDead() {
		//Change the below statement to return an accurate value.
		return dead;
	}
	/**
	 * draws the hp bar of the player
	 */
	public void draw(PApplet marker) {
		super.draw(marker);
		marker.fill(200);
		marker.rect((float) (this.getX()-width/1.5), (float) (this.getCenterY()-height), (float) (ohp), 10f);
		if (hp > ohp * 0.75) {
			marker.fill(0, 255, 0); //stats at green
		} else if (hp > ohp / 2) {
			marker.fill(255, 255, 0); // change bar to yellow
		} else if (hp > ohp * 0.25) {
			marker.fill(255, 165, 0); //change bar to orange
		} else {
			marker.fill(255, 0, 0); //change bar to red
		} 
		if (hp > 0) {
			marker.rect((float) (this.getX()-width/1.5), (float) (this.getCenterY()-height), (float) (40 * hp / 40.0), 10f);
		}

	}
	/**
	 * handles movement and collision between blocks
	 * @param list list containing all the MovingImages
	 */
	public MovingImage act(ArrayList<MovingImage> list) { 
		Player player = (Player) this.clone();
		player.moveByAmount(-vX, -vY);
		//this.moveByAmount(vX, vY);
		if (!isInWindow()) {
			vX = 0;
			vY = 0;
			return this;
		} 
		if(counter%100 == 0) {
			if(hp + 5 < ohp)hp+=5;
			else hp = ohp;
		}
		counter++;

		for (MovingImage s : list) {
			if (player.intersects(s) && s instanceof Block && !(s instanceof NoClipBlock)) {
				vX = 0;
				vY = 0;
			}
			if (this != s && player.intersects(s)) {
				return s;
			}

		}
		return null;
	}
	/**
	 * returns the id of the player
	 * @return returns the id of the player
	 */
	public String toString(){
		return "player";
	}

}
