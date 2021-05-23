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
	protected int vX, vY, cooldown;
	protected double hp, mana;
	protected boolean dead;
	protected final double ohp, maxMana;
	private final int maxCooldown;

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
		maxCooldown = 600;
		cooldown = 0;
		vX = 0;
		vY = 0;
		hp = 100;
		mana = 100;
		dead = false;
		ohp = hp;
		maxMana = mana;
	}

	/**
	 * shoots a projectile for the player
	 * @param x x coordinate of the initial position for the projectile
	 * @param y y coordinate of the initial position for the projectile
	 * @return the projectile being shot
	 */
	public Projectile shoot(int x, int y) {
		if (mana >= 2) {
			mana -= 2;
			double sX = x - this.getX();
			double sY = y - this.getY();
			double angle = Math.atan2(sY, sX);
			if (isDead() == false)
				return new AndroidBasicProjectile(DrawingSurface.androidbullet, (int)this.getX(), (int)this.getY(), 20, 35, "player", angle,50);
			else
				return null;
		}
		return null;
	}
	
	
	public Projectile launchMissile(int x, int y) {
		if (cooldown <= 0 && mana >= 20) {
			mana -= 20;
			if (mana < 0) mana = 0;
			cooldown = maxCooldown;
			double sX = x - this.getX();
			double sY = y - this.getY();
			double angle = Math.atan2(sY, sX);
			return new AndroidMissile(DrawingSurface.missile, (int)this.getX(), (int)this.getY(), 20, 35, "player", angle, 500);
		}
		return null;
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
	/**
	 * returns the x velocity of the player
	 * @return the x velocity of the player
	 */
	public int getVx() {
		return vX;
	}
	/**
	 * returns the y velocity of the player
	 * @return the y velocity of the player
	 */
	public int getVy() {
		return vY;
	}

	/**
	 * causes the player to lose 1 hp
	 */
	public void loseHP() {
		hp --;
		if (hp <= 0) die();
	}
	/**
	 * causes the player to lose n hp
	 * @param n amount of hp to lose
	 */
	public void loseHP(int n) {
		hp -= n;
		if (hp <= 0) die();
	}

	/**
	 * causes the player to die
	 */
	private void die() {
		dead = true;
	}

	/**
	 * causes the player to take damage from lava
	 */
	public void lavaDamage() {
        hp -= 0.2;
        if (hp <= 0)
            die();
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
		marker.pushMatrix();
		marker.fill(200);
		marker.rect((float) (this.getX()-width/1.5), (float) (this.getCenterY()-height - 15), (float) (ohp), 10f);
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
			marker.rect((float) (this.getX()-width/1.5), (float) (this.getCenterY()-height - 15), (float) (40 * hp / 40.0), 10f);
		}
		marker.fill(200);
		marker.rect((float) (this.getX()-width/1.5), (float) (this.getCenterY()-height), (float) (maxMana), 10f);
		marker.fill(0, 178, 255);
		marker.rect((float) (this.getX()-width/1.5), (float) (this.getCenterY()-height), (float) (40 * mana / 40.0), 10f);
		marker.fill(150);
		if (cooldown <= 0 && mana >= 20)
			marker.fill(255);
		marker.arc((float)(this.getX() + maxMana - 5), (float) (this.getCenterY()-height-3), 25f, 25f, -(float)Math.PI/2f,
				(float)(2*Math.PI * ((float)(maxCooldown -cooldown)/(float)maxCooldown) - Math.PI/2f), marker.PIE);
		marker.popMatrix();
	}
	/**
	 * handles movement and collision between blocks
	 * @param list list containing all the MovingImages
	 */
	public MovingImage act(ArrayList<MovingImage> list) { 
		MovingImage image = null;
		cooldown--;
		Rectangle2D.Double posX = new Rectangle2D.Double(x + vX, y, width, height);
		Rectangle2D.Double posY = new Rectangle2D.Double(x, y + vY, width, height);
		//this.moveByAmount(vX, vY);
		if (!isInWindow()) {
			vX = 0;
			vY = 0;
			return this;
		} 
		if(counter % 40 == 0) {
			if(hp + 2 < ohp)hp+=2;
			else hp = ohp;
			if(mana + 5 < maxMana)mana+=2;
			else mana = maxMana;
		}
		counter++;

		for (MovingImage s : list) {
			if (posX.intersects(s) && s instanceof Block && !(s instanceof NoClipBlock))
				vX = 0;
			if (posY.intersects(s) && s instanceof Block && !(s instanceof NoClipBlock))
				vY = 0;
			else if (this.intersects(s) && s instanceof NoClipBlock)
                lavaDamage();
			if (this != s && (posX.intersects(s) || posY.intersects(s)))
				image = s;

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
