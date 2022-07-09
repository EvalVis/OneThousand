package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import play.Game;
import utils.Communicator;

public class ServerMain implements Runnable{
	
	private Thread listen;
	private ServerSocket server;
	
	public static final int userCount = 3;
	
	public static void main(String[] args) {
		new ServerMain();
	}
	
	private ServerMain() {
		listen = new Thread(this, "Run");
		init();
		listen.start();
	}
	
	private void init() {
		System.out.println("Please enter port:");
		Scanner in = new Scanner(System.in); 
		int port = in.nextInt();
		in.close();
		try {
			server = new ServerSocket(port, 6);
			System.out.println("Server started.");
		} catch (Exception e) {
			System.out.println("Failed to initialize server.");
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
