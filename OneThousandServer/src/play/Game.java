package play;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import server.ServerMain;
import utils.Communicator;
import utils.Rotate;

public class Game {
	
	public int[] scores = new int[ServerMain.userCount];
	public Socket[] online = new Socket[ServerMain.userCount];
	public String[] names = new String[ServerMain.userCount];
	public boolean disconnection;
	
	public void play() {
		sendNames();
		int chosen = new Random().nextInt(ServerMain.userCount);
		while(noWinner()) {
			new Match(chosen, this);
			if(disconnection) {
				close();
				return;
			}
			chosen = Rotate.passPlay(chosen);
		}
		close();
	}
	
	private void close() {
		for(int i = 0; i < ServerMain.userCount; i++) {
			try {
				online[i].close();
				System.out.println("Closing connection with " + names[i] + ".");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendNames() {
		String message = "";
		for(int i = 0; i < ServerMain.userCount; i++) message += names[i] + ",";
		message = message.substring(0, message.length() - 1);
		Communicator.sendToAll(message, this);
	}
	
	public boolean noWinner() {
		for(int i = 0; i < scores.length; i++) {
			if(scores[i] >= 1000) return false;
		}
		return true;
	}

}
