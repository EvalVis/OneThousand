package play;

import server.ServerMain;
import utils.Communicator;
import utils.Misc;
import utils.Rotate;

public class Round {
	
	private int playCount;
	public int[] cardsPlayed = new int[ServerMain.userCount];
	private int firstPlayer;
	private Match match;
	private Game game;
	
	public Round(int firstToPlay, Match match, Game game) {
		this.game = game;
		this.match = match;
		firstPlayer = firstToPlay;
		for(int i = 0; i < cardsPlayed.length; i++) cardsPlayed[i] = -1;
		int player = firstToPlay;
		while(playCount < 3) {
			play(player);
			if(game.disconnection) return;
			player = Rotate.passPlay(player);
		}
		int winner = findWinner();
		match.scores[winner] += Misc.calculateScore(cardsPlayed);
		match.firstPlayer = winner;
		Communicator.sendToAll("/e", game);
	}
	
	public void play(int player) {
		playCount++;
		Communicator.send(game.online[player], "/t");
		String message = Communicator.receive(game.online[player], game);
		if(game.disconnection) return;
		String cut = message.substring(2);
		int code = Integer.parseInt(cut);
		cardsPlayed[player] = code;
		if(message.startsWith("/k") && playCount == 1) {
			System.out.println(true);
			match.Trump = code % 4;
			match.scores[player] += calculateTrumpValues();
			Communicator.sendToAll("/s" + code + "," + game.names[player] + "," + calculateTrumpValues(), game);
		}
		else Communicator.sendToAll("/i" + code, game);
	}
	
	public int findWinner() {
		int currentWinner = firstPlayer;
		for(int i = 0; i < cardsPlayed.length; i++) {
			int code1 = cardsPlayed[currentWinner];
			int code2 = cardsPlayed[i];
			if(Misc.sameColor(code1, code2)) {
				if(code2 > code1) currentWinner = i;
			}
			else {
				if(match.Trump != -1) {
					if(Misc.sameColor(match.Trump, code2)) currentWinner = i;
				}
			}
		}
		return currentWinner;
	}
	
	public int calculateTrumpValues() {
		if(match.Trump == 0) return 40;
		else if(match.Trump == 1) return 60;
		else if(match.Trump == 2) return 80;
		else if(match.Trump == 3) return 100;
		return 0;
	}

}
