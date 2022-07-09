package play;

import display.Screen;
import utils.Communicator;
import utils.SMTPClient;

public class Game {
	
	public Match match;
	private Screen screen;
	private Scoreboard board;
	public boolean used60;
	
	public Game(Screen screen) {
		this.screen = screen;
	}
	
	public void start() {
		String message = Communicator.receive();
		String[] names = message.split(",");
		SMTPClient.setTextWithNames(names);
		board = new Scoreboard();
		board.addNames(names);
		while(true) {
		match = new Match(screen, board, this);
		match.start();
		}
	}

}
