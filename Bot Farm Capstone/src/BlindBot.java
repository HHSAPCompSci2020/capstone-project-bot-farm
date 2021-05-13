import java.util.ArrayList;

import processing.core.PImage;

public class BlindBot extends Bot {
	private final double DMG_RADIUS = 5;
	private final int AOE_DMG = 5;
	public BlindBot(PImage image, int x, int y, int width, int height, int hp) {
		super(image, x, y, width, height, hp);
	}

	public void damage(Player player) {
		double distance = Math.sqrt(Math.pow(player.getY() - this.y, 2) + Math.pow(player.getX() - this.x, 2));
		if (distance <= DMG_RADIUS)
			for (int i = 0; i < AOE_DMG; i++)
				player.loseHP();
	}
	public MovingImage act(ArrayList<MovingImage> list) {
		// pathfinding work in progress
		return super.act(list);
	}
	public Projectile shoot(int x, int y) {
		BlindProjectile proj = new BlindProjectile(image, this.x, this.y, (int)width, (int)height, 
				"blindbot", Math.tan((double)y / (double)x), 0);
		return proj;
	}

}
