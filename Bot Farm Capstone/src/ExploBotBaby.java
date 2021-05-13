import java.util.ArrayList;

import processing.core.PImage;

public class ExploBotBaby extends Bot{
	private final int SPEED = 10;
	public ExploBotBaby(PImage image, PImage i2, double x, double y, int width, int height) {
		super(image, x,y,width,height,50);
		
	}
	//taken from blind bot
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
//			list.add(this.shoot(pX, pY));  doesnt shoot anything
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
	
	public Projectile[] shoot(int x, int y) {
		Projectile[] pattern;
		pattern
	}
	
	public String toString() {
		return "bot";
	}
	
	

}
