package ev.projects.onethousandserver.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import ev.projects.onethousandserver.play.Game;

public class Communicator {

	public static String receive(Socket socket, Game game) {
		String message = null;
		try {
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		message = dis.readUTF();
		} catch(Exception e) {
			System.out.println("Player disconnected!");
			game.disconnection = true;
		}
		return message;
	}
	
	public static void send(Socket socket, String message) {
		try {
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		dos.writeUTF(message);
		dos.flush();
		} catch(Exception e) {
			System.err.println("Failed to send.");
		}
	}
	
	public static void sendToAll(String message, Game game) {
		Socket[] sockets = game.online;
		for(int i = 0; i < sockets.length; i++) send(sockets[i], message);
	}
	
	public static void sendToAllExcluding(String message, int excluded, Game game) {
		Socket[] sockets = game.online;
		for(int i = 0; i < sockets.length; i++) {
			if(i == excluded) continue;
			send(sockets[i], message);
		}
	}
	
}
