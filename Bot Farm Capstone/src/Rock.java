import java.util.ArrayList;

import processing.core.PImage;

public class Rock extends Block {

	private int respawnTimer;
	public Rock(PImage image, int x, int y, int width, int height) {
		super(image, x, y, width, height);
		respawnTimer = 0;
	}
	public void destroy() {
		respawnTimer = 1200;
	}
	public boolean isBroken() {
		return respawnTimer > 0;
	}
	public MovingImage act(ArrayList<MovingImage> list) {
		if (respawnTimer > 0)
			respawnTimer--;
		return super.act(list);
	}
}
