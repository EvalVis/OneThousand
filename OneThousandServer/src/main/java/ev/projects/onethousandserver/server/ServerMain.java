package ev.projects.onethousandserver.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ev.projects.onethousandserver.play.Game;
import ev.projects.onethousandserver.utils.Communicator;

public class ServerMain implements Runnable{
	
	private Thread listen;
	private ServerSocket server;
	
	public static final int userCount = 3;
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Please provide server port.");
			System.exit(1);
		}
		try {
			int port = Integer.parseInt(args[0]);
			new ServerMain(port);
		} catch (NumberFormatException e) {
			System.out.println("Error: Port must be a number");
			System.exit(1);
		}
	}
	
	private ServerMain(int port) {
		listen = new Thread(this, "Run");
		init(port);
		listen.start();
	}
	
	private void init(int port) {
		try {
			server = new ServerSocket(port, 6);
			System.out.println("Server started on port " + port);
		} catch (Exception e) {
			System.out.println("Failed to initialize server on port " + port);
			System.exit(0);
		}
	}

	public void run() {
		List<Thread> threads = new ArrayList<Thread>();
		List<Game> games = new ArrayList<Game>();
		while(true) {
		Game game = new Game();
		listenForConnections(game);
		if(game.disconnection) continue;
		Thread play = new Thread() {
			public void run() {
				Communicator.sendToAll("/s", game);
				game.play();
			}
		};
		play.start();
		threads.add(play);
		games.add(game);
		}
	}
	
	private void listenForConnections(Game game) {
		for(int i = 0; i < userCount; i++) {
		try {
			Socket socket = server.accept();
			game.online[i] = socket;
			game.names[i] = Communicator.receive(socket, game);
			System.out.println("A new client named " + game.names[i] + ".");
		} catch(Exception e) {
			System.out.println("Failed while listening for connections.");
		}
		}
	}

}
