package ev.projects.onethousand.utils;

import java.io.IOException;

import ev.projects.onethousand.game.Main;

public class Communicator {

	public static String receive() {
		String message = null;
		try {
		message = Main.dis.readUTF();
		} catch(Exception e) {
			System.out.println("Connection to server lost! Exiting.");
			System.exit(0);
		}
		return message;
	}
	
	public static void send(String message) {
		try {
			Main.dos.writeUTF(message);
			Main.dos.flush();
		} catch (IOException e) {
			System.out.println("Failed to send");
		}
	}
	
}
