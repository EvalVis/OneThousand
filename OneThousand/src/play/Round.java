package play;

import cards.Card;
import cards.CardManager;
import cards.Type;
import display.Screen;
import utils.Communicator;
import utils.MatchCapacitor;
import utils.Misc;
import utils.MouseTarget;
import utils.SMTPClient;

public class Round {
	
	private Type First = Type.Undefined;
	
	private CardManager cardManager;
	
	private Screen screen;
	
	private MatchCapacitor matchCapacitor;
	
	public Round(CardManager cardManager, Screen screen, MatchCapacitor matchCapacitor) {
		this.cardManager = cardManager;
		this.screen = screen;
		this.matchCapacitor = matchCapacitor;
	}
	
	public void start() {
		while(true) {
		String message = Communicator.receive();
		if(message.equals("/t")) {
			Card card = playCard();
			int code = cardManager.encodeCard(card);
			if(cardManager.hasPair(card)) Communicator.send("/k" + code);
			else Communicator.send("/p" + code);
		}
		else if(message.startsWith("/i")) {
			String cut = message.substring(2);
			int cardCount = cardManager.cardsInPlay.size();
			Card card = cardManager.decodeCard(Integer.parseInt(cut), cardCount * screen.hiddenCard.getWidth() + 420, 100);
			cardManager.cardsInPlay.add(card);
			if(First == Type.Undefined) First = card.getColor();
		}
		else if(message.startsWith("/s")) {
			String cut = message.substring(2);
			String[] split = cut.split(",");
			int cardCount = cardManager.cardsInPlay.size();
			Card card = cardManager.decodeCard(Integer.parseInt(split[0]), cardCount * screen.hiddenCard.getWidth() + 420, 100);
			cardManager.cardsInPlay.add(card);
			if(First == Type.Undefined) First = card.getColor();
			screen.setInfoMessage("Player " + split[1] + " says " + split[2] + ". Trump is now " + card.getColor() + ".");
		}
		else if(message.equals("/e")) break;
		}
		if(SMTPClient.emailSet()) matchCapacitor.captureImage();
	}
	
	public Card playCard() {
		while(true) {
			Misc.sleep();
			Card card = MouseTarget.getTargetedCard(cardManager.cards);
			if(card != null) {
				if(cardManager.canPlay(card, First)) return card;
			}
		}
	}

}
