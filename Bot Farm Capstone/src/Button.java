import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;
/**
 * Represents a Button. 
 * @author Michael Chen
 */
public class Button extends MovingImage {
	private String text;
	private int textSize;
	
	/**
	 * Constructs a Button. 
	 * @param img The image to be used. 
	 * @param centerX The x-coordinate of the center of the Button.
	 * @param centerY The y-coordinate of the center of the Button.
	 * @param w The width of the Button.
	 * @param h The height of the Button.
	 * @param text The text to be displayed on the Button.
	 * @param textSize The text size for the text. 
	 */
	public Button(PImage img, double centerX, double centerY, int w, int h, String text, int textSize) {
		super(img, centerX - w/2, centerY - h/2, w, h);
		this.text = text;
		this.textSize = textSize;
	}
	
	/**
	 * Checks to see if the given coordinates are over the Button. 
	 * @param mouseX The x-coordinate (of the mouse).
	 * @param mouseY The x-coordinate (of the mouse).
	 * @return Whether or not the given coordinates are hovering over the Button. 
	 */
	public boolean isHovered(int mouseX, int mouseY) {
		return this.contains(new Point(mouseX, mouseY));
	}
	
	/**
	 * Draws the Button to the PApplet. 
	 * @param marker The PApplet for the Button to be drawn.
	 */
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
