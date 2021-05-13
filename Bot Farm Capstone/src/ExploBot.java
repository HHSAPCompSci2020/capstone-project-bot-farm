import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class ExploBot extends Bot {
	protected int counter;
	protected boolean dead;
	public ExploBot(PImage image, PImage i2, double x, double y, int width, int height) {
		super(image, x,y,width,height,50);
		this.counter = 0;
	}
	
	
	public MovingImage act(ArrayList<MovingImage> list) {
		list.add(new ExploBotBaby(image, image, (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight()));
		return super.act(list);
	}

	@Override
	public ArrayList<Projectile> shoot(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString() {
		return "explobot";
	}


	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}
}
