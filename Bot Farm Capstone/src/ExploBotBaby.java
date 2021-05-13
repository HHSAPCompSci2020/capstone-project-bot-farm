import java.util.ArrayList;

import processing.core.PImage;

public class ExploBotBaby extends Bot{
	public ExploBotBaby(PImage image, PImage i2, int x, int y, int width, int height) {
		super(image, x,y,width,height,50);
		
	}
	public MovingImage act(ArrayList<MovingImage> list){
		return super.act(list);
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
