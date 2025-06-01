package ev.projects.onethousand.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JFrame;

import ev.projects.onethousand.display.Screen;
import ev.projects.onethousand.input.Keyboard;
import ev.projects.onethousand.input.Mouse;
import ev.projects.onethousand.play.Game;
import ev.projects.onethousand.utils.Communicator;

public class Main extends Canvas implements Runnable {
	
	public static String IP = "192.168.1.69";
	public static int port = 1000;
	
	private Socket socket;
	
	private static final int width = 1900;
	private static final int height = 800;
	
	private static final long serialVersionUID = 1L;
	
	private Thread run;
	private Thread render;
	private Thread listen;
	
	public static DataOutputStream dos;
	public static DataInputStream dis;
	
	private JFrame frame;
	
	private Game game;
	
	private Screen screen;
	
	public Main(String name) {
		addMouseListener(new Mouse());
		addKeyListener(new Keyboard());
		run = new Thread(this, "Run");
		frame = new JFrame();
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		frame.setResizable(false);
		frame.add(this);
		frame.setTitle("Thousand (" + name + ")");
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		screen = new Screen(this, height);
		game = new Game(screen);
		run.start();
		init(name);
	}
	
	private void init(String name) {
		listen = new Thread("Listen") {
		public void run() {
		connect();
		Communicator.send(name);
		screen.setInfoMessage("Waiting for other players to join.");
		String message = Communicator.receive();
		if(message.equals("/s")) game.start();
		}
		};
		listen.start();
	}
	
	private void connect() {
		try {
			socket = new Socket(IP, port);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			System.out.println("Connected.");
		} catch (Exception e) {
			System.out.println("failed to connect!");
			System.exit(0);
		}
	}

	public void run() {
		render = new Thread("Render") {
			public void run() {
				while(true) {
				render();
				}
			}
		};
		render.start();
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		screen.render(g, game);
		g.dispose();
		bs.show();
	}

}
