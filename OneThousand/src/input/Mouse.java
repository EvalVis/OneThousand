package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener{
	
	public static int X;
	public static int Y;
	public static int Button = -1;

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		X = e.getX();
		Y = e.getY();
		Button = e.getButton();
	}

	public void mouseReleased(MouseEvent e) {
		Button = -1;
	}
	
	public static void resetButton() {
		Button = -1;
	}

}
