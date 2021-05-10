import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class ExploBot extends Bot {
	protected int counter;
	protected boolean dead;
	public ExploBot(PImage image, int x, int y, int width, int height) {
		super(image, x,y,width,height,50);
		
	}
	
	public void draw(PApplet marker) {
		super.draw(marker);
	}
	
	public MovingImage act(ArrayList<MovingImage> list){
		return null;
	}

	@Override
	public Projectile shoot(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString() {
		return "bot";
	}
}
