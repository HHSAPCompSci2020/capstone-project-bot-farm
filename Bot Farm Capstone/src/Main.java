import java.awt.*;
import javax.swing.JFrame;

public class Main { 
    
	public static void main(String[] args) {
      	DrawingSurface drawing = new DrawingSurface();
      	JFrame window = new JFrame();
		window.setSize(200,200);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.requestFocus();
		window.setVisible(true);
	}
     
}