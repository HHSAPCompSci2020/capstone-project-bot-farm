import java.util.ArrayList;

import processing.core.PImage;

public class BlindBot extends Bot {
	private final double DMG_RADIUS = 5;
	private final int AOE_DMG = 5;
	private final int SPEED = 5;
	public BlindBot(PImage image, int x, int y, int width, int height, int hp) {
		super(image, x, y, width, height, hp);
	}

	public void damage(Player player) {
		double distance = Math.sqrt(Math.pow(player.getY() - this.y, 2) + Math.pow(player.getX() - this.x, 2));
		if (distance <= DMG_RADIUS)
			for (int i = 0; i < AOE_DMG; i++)
				player.loseHP();
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
			for (MovingImage m : this.shoot(pX, pY))
				list.add(m);
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
	public ArrayList<Projectile> shoot(int x, int y) {
		BlindProjectile proj = new BlindProjectile(image, this.x, this.y, (int)width, (int)height, 
				"blindbot", Math.tan((double)y / (double)x), 0);
		ArrayList<Projectile> projs = new ArrayList<Projectile>();
		projs.add(proj);
		return projs;
	}

	public String toString() {
		return "blindbot";
	}

	public void die() {
		
	}
	
	
}
