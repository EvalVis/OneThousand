package play;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import cards.Card;
import cards.CardManager;
import display.Screen;
import input.Keyboard;
import input.Mouse;
import utils.Communicator;
import utils.MatchCapacitor;
import utils.Misc;
import utils.MouseTarget;
import utils.SMTPClient;

public class Match {
	
	public boolean black = true;
	public CardManager cardManager = new CardManager();
	private Scoreboard board;
	
	private Screen screen;
	
	public boolean callEnded;
	private boolean wonCall;
	private boolean hideCentralCards;
	private MatchCapacitor matchCapacitor;
	
	public Match(Screen screen, Scoreboard board, Game game) {
		this.screen = screen;
		this.board = board;
		matchCapacitor = new MatchCapacitor(screen, game);
	}
	
	public void start() {
		screen.setStatusMessage("");
		screen.setInfoMessage("Waiting for the first player to start the game.");
		receiveCards();
		callPhase();
		revealPhase();
		String message = Communicator.receive();
		if(message.startsWith("/begin")) {
			String cut = message.substring(6);
			String parts[] = cut.split(",");
			String additional = Boolean.parseBoolean(parts[2]) ? "He is playing \"black\"." : "";
			screen.setStatusMessage("Player's " + parts[1] + " final call is " + parts[0] + ". " + additional);
			doRounds();
		}
		board.updateSumScore();
		board.activate();
		BufferedImage image = board.saveFrame();
		matchCapacitor.capturedImages.add(image);
		if(SMTPClient.emailSet()) SMTPClient.send(matchCapacitor);
	}
	
	private void doRounds() {
		for(int i = 0; i < 7; i++) {
		String message = Communicator.receive();
		if(message.equals("/round")) {
			Round round = new Round(cardManager, screen, matchCapacitor);
			round.start();
			Misc.sleep(3000);
			cardManager.resetCardsInPlay();
		}
		}
		String message = Communicator.receive();
		String[] parts = message.split(",");
		int[] scores = new int[parts.length];
		for(int i = 0; i < parts.length; i++) scores[i] = Integer.parseInt(parts[i]);
		board.addScores(scores);
	}
	
	private void receiveCards() {
		cardManager.decodeCards(Communicator.receive(), screen.hiddenCard.getWidth(), 0, screen.height - 10 - screen.hiddenCard.getHeight());
	}
	
	private void callPhase() {
		while(true) {
		String message = Communicator.receive();
		if(message.equals("/p")) {
			screen.setInfoMessage("Your call is forced: 100. Reveal the cards? Press Y for yes, N for no.");
			waitForRevealOrNot();
			screen.setInfoMessage("Your call is forced: 100.");
		}
		else if(message.startsWith("/c")) {
			if(black) {
			screen.setInfoMessage("Current call is " + message.substring(2) + ". Do you want to reveal your cards? Press Y for yes, N for no.");
			waitForRevealOrNot();
			}
			screen.setInfoMessage("Current call is " + message.substring(2) + ". Will you raise it by 10 or pass? Press R for raise, P for pass.");
			waitForRaiseOrPass();
		}
		else if(message.startsWith("/s")) {
			String tail = message.substring(2);
			String[] split = tail.split(",");
			String additional = Boolean.parseBoolean(split[2]) ? "He is playing \"black\"." : "";
			screen.setStatusMessage("Highest call " + split[0] + " was made by " +  split[1] + "." + additional);
		}
		else if(message.startsWith("/ecn")) {
			String tail = message.substring(4);
			String[] split = tail.split(",");
			hideCentralCards = Boolean.parseBoolean(split[1]);
			String additional = hideCentralCards ? "He won playing \"black\"." : "";
			screen.setInfoMessage("Player " + split[0] + " won the call. " + additional);
			break;
		}
		else if(message.startsWith("/ecw")) {
			wonCall = true;
			screen.setInfoMessage("You won the call.");
			break;
		}
		}
		black = false;
	}
	
	private void revealPhase() {
		callEnded = true;
		String message = Communicator.receive();
		if(!hideCentralCards) {
			cardManager.decodeCards(message, screen.hiddenCard.getWidth(), 800, 70);
			if(SMTPClient.emailSet()) matchCapacitor.captureImage();
			if(wonCall) {
				discardCards();
				String call = Communicator.receive();
				new RaiseCall(Integer.parseInt(call));
			}
			else {
			Misc.sleep(10000);
			cardManager.discardShowcaseCards();
			}
		}
		else if(SMTPClient.emailSet()) matchCapacitor.captureImage();
	}
	
	/*private void raiseCallPhase() {
		String call = Communicator.receive();
		new RaiseCall(Integer.parseInt(call));
	}
	
	private void receiveRaisedCallStatus() {
		String message = Communicator.receive();
		if(message.startsWith("/end")) doEnd(message);
		String parts[] = message.split(",");
		String blackStatus = Boolean.parseBoolean(parts[2]) ? "He was calling \"black\"" : "";
		screen.setStatusMessage("Call winner: " + parts[1] + ". Called for: " + parts[0] + ". " + blackStatus);
	}*/
	
	/*private void askForUseOf60() {
		int priceToPay = blackWhenCalled ? 120 : 60;
		screen.setInfoMessage("You can refuse to play by given others "  + priceToPay + ". Do you wish to do that? Press Y for yes, N for no.");
		while(true) {
			Misc.sleep();
			if(Keyboard.Key == KeyEvent.VK_Y) {
				Communicator.send("/use60");
				game.used60 = true;
				break;
			}
			else if(Keyboard.Key == KeyEvent.VK_N) {
				screen.setInfoMessage("");
				Communicator.send("/notUse60");
				raiseAndDiscard();
				break;
			}
		}
	}*/
	
	private void discardCards() {
		while(true) {
			Misc.sleep();
			Card card = MouseTarget.getTargetedCard(cardManager.cards);
			if(card != null) card.select();
			Mouse.resetButton();
			if(Keyboard.Key == KeyEvent.VK_ENTER && cardManager.cardsForDiscard() == 3) break;
		}
		int[] codes = cardManager.selectedCardCodes();
		String message = "/discarded";
		for(int i = 0; i < codes.length; i++) message += codes[i] + ",";
		message = message.substring(0, message.length() - 1);
		Communicator.send(message);
		cardManager.discardCards();
		cardManager.relocate(screen.height);
	}
	
	public void waitForRevealOrNot() {
		while(true) {
			Misc.sleep();
			if(Keyboard.Key == KeyEvent.VK_Y) {
				Communicator.send("/reveal");
				black = false;
				break;
			}
			else if(Keyboard.Key == KeyEvent.VK_N) {
				Communicator.send("/black");
				break;
			}
		}
	}
	
	public void waitForRaiseOrPass() {
		while(true) {
			Misc.sleep();
			if(Keyboard.Key == KeyEvent.VK_P) {
				screen.setInfoMessage("You passed the call.");
				Communicator.send("/pass");
				break;
			}
			else if(Keyboard.Key == KeyEvent.VK_R) {
				screen.setInfoMessage("You raised the call.");
				Communicator.send("/raise");
				break;
			}
		}
	}

}
