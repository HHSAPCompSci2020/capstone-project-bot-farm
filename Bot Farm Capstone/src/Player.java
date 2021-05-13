import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends MovingImage {
	protected int vX, vY;
	protected double hp;
	protected boolean dead;
	protected final int ohp;

	public Player(PImage image, double x, double y, int width, int height) {
		super(image, x, y, width, height);
		//vX and vY should be initially 0, hp should start at 20, and dead should be false.
		vX = 0;
		vY = 0;
		hp = 100;
		dead = false;
		ohp = (int)hp;
	}


	public Projectile shoot(int x, int y) {
		double sX = x - this.getX();
		double sY = y - this.getY();
		double tann = sY/sX;
		double angle = Math.atan(tann);

		if(x < this.getX()) {
			angle = angle + Math.PI;
		}
		if (isDead() == false){
			return new Projectile(new PImage(), (int)this.getX(), (int)this.getY(), 20, 35, "player", angle,30);
		} else {
			return null;
		}
	}

	public void setvX(int x) {
		vX = x;
	}

	public void setvY(int y) {
		vY = y;
	}


	public void loseHP() {
		//If hp is less than 0, die
		hp --;
		if (hp <= 0) {
			die();
		}

	}

	private void die() {
		dead = true;
		//Sets the dead boolean to true
	}

	public boolean isDead() {
		//Change the below statement to return an accurate value.
		return dead;
	}

	public void draw(PApplet marker) {
		super.draw(marker);
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
			marker.rect((float) this.getX(), (float) (this.getCenterY()), (float) (40 * hp / 40.0), 10f);
		}

	}

	public MovingImage act(ArrayList<MovingImage> list) { 
		this.moveByAmount(vX, vY);
		if (!isInWindow()) {
			this.moveByAmount(-vX, -vY);
		}   

		for (MovingImage s : list) {
			if (this.intersects(s) && s instanceof Block && !(s instanceof NoClipBlock)) {
				this.moveByAmount(-vX, -vY);
			}
			if (this != s && this.intersects(s)) {
				return s;
			}

		}
		return null;
	}

	public String toString(){
		return "player";
	}

}
