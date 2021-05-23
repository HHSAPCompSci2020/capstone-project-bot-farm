import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public class Button extends MovingImage {
	private String text;
	private int textSize;
	public Button(PImage img, double centerX, double centerY, int w, int h, String text, int textSize) {
		super(img, centerX - w/2, centerY - h/2, w, h);
		this.text = text;
		this.textSize = textSize;
	}
	public boolean isHovered(int mouseX, int mouseY) {
		return this.contains(new Point(mouseX, mouseY));
	}
	public void draw(PApplet marker) {
		marker.pushMatrix();
		marker.pushStyle();
		if (isHovered(marker.mouseX, marker.mouseY))
			marker.tint(255, 255, 0, 255);
		super.draw(marker);
		marker.textSize(textSize);
		marker.textAlign(marker.CENTER, marker.CENTER);
		marker.text(text, (float)(x + width/2), (float)(y - 5 + height/2));
		marker.popStyle();
		marker.popMatrix();
	}
}
