import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the Player
 * @author Zackery He, Harry Guan
 *
 */
public class Player extends MovingImage {
	protected int vX, vY, cooldown, slowed;
	protected double hp, mana;
	protected boolean dead;
	protected final double ohp, maxMana;
	private final int maxCooldown;

	/**
	 * Constructs a Player. 
	 * @param image The image that corresponds to this Player.
	 * @param x The x-coordinate of the top left corner of the Player.
	 * @param y The y-coordinate of the top left corner of the Player.
	 * @param width The width of the Player.
	 * @param height The height of the Player.
	 */
	public Player(PImage image, double x, double y, int width, int height) {
		super(image, x, y, width, height);
		maxCooldown = 450;
		cooldown = maxCooldown;
		vX = 0;
		vY = 0;
		hp = 100;
		mana = 100;
		dead = false;
		ohp = hp;
		maxMana = mana;
		slowed = 0;
	}

	/**
	 * Shoots an AndroidBasicProjectile and returns it.
	 * @param x The x-coordinate of the destination of the AndroidBasicProjectile.
	 * @param y The y-coordinate of the destination of the AndroidBasicProjectile.
	 * @return The AndroidBasicProjectile fired.
	 */
	public Projectile shoot(int x, int y) {
		if (mana >= 2) {
			mana -= 2;
			double sX = x - this.getX();
			double sY = y - this.getY();
			double angle = Math.atan2(sY, sX);
			if (isDead() == false)
				return new AndroidBasicProjectile(DrawingSurface.androidbullet, (int)this.getCenterX() - 10, (int)this.getCenterY() - 17, 20, 35, "player", angle,50);
			else
				return null;
		}
		return null;
	}
	
	/**
	 * Shoots an AndroidMissile and returns it. 
	 * @param x The x-coordinate of the destination of the AndroidMissile.
	 * @param y The y-coordinate of the destination of the AndroidMissile.
	 * @return The AndroidMissile fired.
	 */
	public Projectile launchMissile(int x, int y) {
		if (cooldown <= 0 && mana >= 20) {
			mana -= 20;
			if (mana < 0) mana = 0;
			cooldown = maxCooldown;
			double sX = x - this.getX();
			double sY = y - this.getY();
			double angle = Math.atan2(sY, sX);
			return new AndroidMissile(DrawingSurface.missile, (int)this.getCenterX() - 20, (int)this.getCenterY() - 20, 40, 40, "player", angle, 500);
		}
		return null;
	}

	/**
	 * Changes the x-velocity of the Player.
	 * @param x The x-velocity of the Player to be set.
	 */
	public void setvX(int x) {
		vX = x;
	}
	
	/**
	 * Changes the y-velocity of the Player.
	 * @param y The y-velocity of the Player to be set.
	 */
	public void setvY(int y) {
		vY = y;
	}
	/**
	 * Returns the x-velocity of the Player.
	 * @return The x-velocity of the Player.
	 */
	public int getVx() {
		return vX;
	}
	/**
	 * Returns the y-velocity of the Player.
	 * @return The y-velocity of the Player.
	 */
	public int getVy() {
		return vY;
	}

	/**
	 * Causes the Player to lose 1 hp. If hp falls less than 0, die.
	 */
	public void loseHP() {
		hp --;
		if (hp <= 0) die();
	}
	/**
	 * Causes the Player to lose n hp. If hp falls less than 0, die. 
	 * @param n The amount of hp to lose.
	 */
	public void loseHP(int n) {
		hp -= n;
		if (hp <= 0) die();
	}

	private void die() {
		dead = true;
	}

	/**
	 * Causes the player to take damage from poisonous gas. If hp falls less than 0, die.
	 */
	public void poisonDamage() {
        hp -= 0.2;
        if (hp <= 0)
            die();
    } 
	/**
	 * Checks if the player is dead.
	 * @return Whether the player is dead.
	 */
	public boolean isDead() {
		//Change the below statement to return an accurate value.
		return dead;
	}
	/**
	 * Draws the hp bar of the player.
	 * @param marker The PApplet to draw on. 
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
		marker.pushStyle();
		marker.textSize(20);
		marker.textAlign(marker.CENTER, marker.CENTER);
		marker.fill(0);
		marker.text("Q", (float)(this.getX() + maxMana - 5), (float) (this.getCenterY()-height-6));
		marker.popStyle();
		marker.popMatrix();
	}
	/**
	 * Handles movement and collision between Blocks.
	 * @param list The list containing all entities currently in the game. 
	 */
	public MovingImage act(ArrayList<MovingImage> list) { 
		MovingImage image = null;
		cooldown--;
		if(slowed > 0) {
			vX*=0.5;
			vY*=0.5;
			slowed--;
		}
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
			if(mana + 2 < maxMana)mana+=2;
			else mana = maxMana;
		}
		counter++;

		for (MovingImage s : list) {
			if (posX.intersects(s) && s instanceof Block && !(s instanceof NoClipBlock))
				vX = 0;
			if (posY.intersects(s) && s instanceof Block && !(s instanceof NoClipBlock))
				vY = 0;
			else if (this.intersects(s) && s instanceof NoClipBlock) {
                poisonDamage();
                slowed = 30;
			}
			if (this != s && (posX.intersects(s) || posY.intersects(s)))
				image = s;

		}
		return image;
	}
	/**
	 * Returns the ID of the Player.
	 * @return The ID of the Player.
	 */
	public String toString(){
		return "player";
	}

}
