package play;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import server.ServerMain;
import utils.Communicator;
import utils.Misc;
import utils.Rotate;

public class Match {
	
	private List<Integer> cards = new ArrayList<Integer>();
	private int call = 100;
	public boolean[] callers = new boolean[ServerMain.userCount];
	public boolean[] black = new boolean[ServerMain.userCount];
	public int[] scores = new int[ServerMain.userCount];
	private int callWinner;
	public int Trump = -1;
	public int firstPlayer;
	private Game game;
	
	public Match(int playerToCall, Game game) {
		this.game = game;
		for(int i = 0; i < ServerMain.userCount; i++) {
			callers[i] = true;
			black[i] = true;
		}
		shuffle();
		callPhase(playerToCall);
		if(game.disconnection) return;
		revealPhase();
		getDiscardCards();
		if(game.disconnection) return;
		Communicator.send(game.online[callWinner], "" + call);
		String mesage = Communicator.receive(game.online[callWinner], game);
		if(game.disconnection) return;
		call = Integer.parseInt(mesage);
		Communicator.sendToAll("/begin" + call + "," + game.names[callWinner] + "," + black[callWinner], game);
		makeRounds();
		if(game.disconnection) return;
		distributeScore();
	}
	
	/*private void finalPhase() {
		String message = Communicator.receive(ServerMain.online[callWinner]);
		if(message.equals("/notUse60")) {
			raiseCallPhase();
			getDiscardCards();
		}
		else if(message.equals("/use60")) {
			int priceToPay = black[callWinner] ? 120 : 60;
			Communicator.sendToAll("/end" + priceToPay + "," + callWinner);
			for(int i = 0; i < ServerMain.userCount; i++) {
				if(i == callWinner) game.scores[i] += 0;
				else game.scores[i] += priceToPay;
			}
		}
	}*/
	
	private void shuffle() {
		for(int i = 0; i < 24; i++) {
			cards.add(i);
		}
		String[] messages = new String[ServerMain.userCount];
		boolean run = true;
		while(run) {
		Collections.shuffle(cards);
		run = false;
		for(int i = 0; i < ServerMain.userCount; i++) {
			String message = "";
			for(int r = 0; r < 7; r++) message += (cards.get(i*7 + r)) + ",";
			message = message.substring(0, message.length() - 1);
			messages[i] = message;
			if(Misc.contains3Nines(message)) {
				run = true;
				continue;
			}
		}
		}
		for(int i = 0; i < messages.length; i++) Communicator.send(game.online[i], messages[i]);
	}
	
	private void getDiscardCards() {
		String message = Communicator.receive(game.online[callWinner], game);
		if(game.disconnection) return;
		if(message.startsWith("/discarded")) {
			String cut = message.substring(10);
			String[] parts = cut.split(",");
			int[] codes = new int[parts.length];
			for(int i = 0; i < parts.length; i++) codes[i] = Integer.parseInt(parts[i]);
			scores[callWinner] += Misc.calculateScore(codes);
		}
	}
	
	/*private void raiseCallPhase() {
		Communicator.send(ServerMain.online[callWinner], "" + call);
		String message = Communicator.receive(ServerMain.online[callWinner]);
		call = Integer.parseInt(message);
		Communicator.sendToAll(message + "," + ServerMain.names[callWinner] + "," + black[callWinner]);
	}*/
	
	private void callPhase(int caller) {
		callWinner = caller;
		Communicator.send(game.online[caller], "/p");
		askForRelevation(caller);
		if(game.disconnection) return;
		while(true) {
		Communicator.sendToAll("/s" + call + "," + game.names[callWinner] + "," + black[callWinner], game);
		caller = Rotate.passCall(caller, callers);
		System.out.println("Winning:" + callWinner + ", " + caller);
		Communicator.send(game.online[caller], "/c" + call);
		if(black[caller]) askForRelevation(caller);
		if(askForRaiseOrPass(caller)) break;
		if(game.disconnection) return;
		}
		if(game.disconnection) return;
		Communicator.sendToAllExcluding("/ecn" + game.names[callWinner] + "," + black[callWinner], callWinner, game);
		Communicator.send(game.online[callWinner], "/ecw" + call);
	}
	
	private void revealPhase() {
		String message = "";
		int start = cards.size() - 3;
		for(int i = 0; i < 3; i++) {
			message += (cards.get(start + i)) + ",";
		}
		message = message.substring(0, message.length() - 1);
		Communicator.sendToAll(message, game);
	}
	
	private void distributeScore() {
		int sign = (scores[callWinner] >= call) ? 1 : -1;
		int multiplier = black[callWinner] ? 2 : 1;
		int callersScore = (call * sign * multiplier);
		game.scores[callWinner] += callersScore;
		String message = "";
		for(int i = 0; i < ServerMain.userCount; i++) {
			if(i == callWinner) {
				message += callersScore + ",";
				continue;
			}
			int rounded = Math.round(scores[i] / 10) * 10;
			game.scores[i] += rounded;
			message += rounded + ",";
		}
		message = message.substring(0, message.length() - 1);
		Communicator.sendToAll(message, game);
	}
	
	private void makeRounds() {
		firstPlayer = callWinner;
		for(int i = 0; i < 7; i++) {
		Communicator.sendToAll("/round", game);
		new Round(firstPlayer, this, game);
		if(game.disconnection) return;
		}
	}
	
	private boolean askForRaiseOrPass(int caller) {
		String message = Communicator.receive(game.online[caller], game);
		if(game.disconnection) return false;
		if(message.equals("/raise")) {
			call+=10;
			callWinner = caller;
		}
		else if(message.equals("/pass")) {
			callers[caller] = false;
			if(Misc.othersPassed(callers)) return true;
		}
		return false;
	}
	
	private void askForRelevation(int caller) {
		String message = Communicator.receive(game.online[caller], game);
		if(game.disconnection) return;
		if(message.equals("/reveal")) {
			black[caller] = false;
		}
	}
	
	public Game getGame() {
		return game;
	}

}
