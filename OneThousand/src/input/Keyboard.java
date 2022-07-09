package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	
	public static int Key = -1;

	public void keyPressed(KeyEvent e) {
		Key = e.getKeyCode();
	}

	public void keyReleased(KeyEvent e) {
		Key = -1;
	}

	public void keyTyped(KeyEvent e) {
	}

}
