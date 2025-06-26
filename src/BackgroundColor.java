import java.awt.*;
import javax.swing.*;

public class BackgroundColor {
	
	private Color backgroundColor;
	
	public BackgroundColor() {
		
		backgroundColor = new Color(35,47,62);
		
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new BackgroundColor();
	            }
	        });
	    }

}
