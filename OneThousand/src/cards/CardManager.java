package cards;

import java.util.ArrayList;
import java.util.List;

import cards.aces.AceOfClubs;
import cards.aces.AceOfDiamonds;
import cards.aces.AceOfHearts;
import cards.aces.AceOfSpades;
import cards.jacks.JackOfClubs;
import cards.jacks.JackOfDiamonds;
import cards.jacks.JackOfHearts;
import cards.jacks.JackOfSpades;
import cards.kings.KingOfClubs;
import cards.kings.KingOfDiamonds;
import cards.kings.KingOfHearts;
import cards.kings.KingOfSpades;
import cards.nines.NineOfClubs;
import cards.nines.NineOfDiamonds;
import cards.nines.NineOfHearts;
import cards.nines.NineOfSpades;
import cards.queens.QueenOfClubs;
import cards.queens.QueenOfDiamonds;
import cards.queens.QueenOfHearts;
import cards.queens.QueenOfSpades;
import cards.tens.TenOfClubs;
import cards.tens.TenOfDiamonds;
import cards.tens.TenOfHearts;
import cards.tens.TenOfSpades;

public class CardManager {
	
	public List<Card> cards = new ArrayList<Card>();
	public List<Card> cardsInPlay = new ArrayList<Card>();
	
	public int encodeCard(Card card) {
		if(card instanceof NineOfSpades) return 0;
		else if(card instanceof NineOfClubs) return 1;
		else if(card instanceof NineOfDiamonds) return 2;
		else if(card instanceof NineOfHearts) return 3;
		else if(card instanceof JackOfSpades) return 4;
		else if(card instanceof JackOfClubs) return 5;
		else if(card instanceof JackOfDiamonds) return 6;
		else if(card instanceof JackOfHearts) return 7;
		else if(card instanceof QueenOfSpades) return 8;
		else if(card instanceof QueenOfClubs) return 9;
		else if(card instanceof QueenOfDiamonds) return 10;
		else if(card instanceof QueenOfHearts) return 11;
		else if(card instanceof KingOfSpades) return 12;
		else if(card instanceof KingOfClubs) return 13;
		else if(card instanceof KingOfDiamonds) return 14;
		else if(card instanceof KingOfHearts) return 15;
		else if(card instanceof TenOfSpades) return 16;
		else if(card instanceof TenOfClubs) return 17;
		else if(card instanceof TenOfDiamonds) return 18;
		else if(card instanceof TenOfHearts) return 19;
		else if(card instanceof AceOfSpades) return 20;
		else if(card instanceof AceOfClubs) return 21;
		else if(card instanceof AceOfDiamonds) return 22;
		return 23;
	}
	
	public Card decodeCard(int code, int x, int y) {
		switch(code) {
		case 0:
			return new NineOfSpades(x, y);
		case 1:
			return new NineOfClubs(x, y);
		case 2:
			return new NineOfDiamonds(x, y);
		case 3:
			return new NineOfHearts(x, y);
		case 4:
			return new JackOfSpades(x, y);
		case 5:
			return new JackOfClubs(x, y);
		case 6:
			return new JackOfDiamonds(x, y);
		case 7:
			return new JackOfHearts(x, y);
		case 8:
			return new QueenOfSpades(x, y);
		case 9:
			return new QueenOfClubs(x, y);
		case 10:
			return new QueenOfDiamonds(x, y);
		case 11:
			return new QueenOfHearts(x, y);
		case 12:
			return new KingOfSpades(x, y);
		case 13:
			return new KingOfClubs(x, y);
		case 14:
			return new KingOfDiamonds(x, y);
		case 15:
			return new KingOfHearts(x, y);
		case 16:
			return new TenOfSpades(x, y);
		case 17:
			return new TenOfClubs(x, y);
		case 18:
			return new TenOfDiamonds(x, y);
		case 19:
			return new TenOfHearts(x, y);
		case 20:
			return new AceOfSpades(x, y);
		case 21:
			return new AceOfClubs(x, y);
		case 22:
			return new AceOfDiamonds(x, y);
		case 23:
			return new AceOfHearts(x, y);
		default:
			return null;
		}
	}
	
	public void decodeCards(String message, int cardWidth, int xs, int y) {
		String[] split = message.split(",");
		for(int i = 0; i < split.length; i++) {
			int x = cardWidth * i + xs;
			Card card = decodeCard(Integer.parseInt(split[i]), x, y);
			cards.add(card);
		}
	}
	
	public int cardsForDiscard() {
		int count = 0;
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).selected()) count++;
		}
		return count;
	}
	
	public void discardCards() {
		for(int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			if(card.selected()) {
				cards.remove(card);
				i--;
			}
		}
	}
	
	public int[] selectedCardCodes() {
		int[] codes = new int[cardsForDiscard()];
		int index = 0;
		for(int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			if(card.selected()) {
				codes[index] = encodeCard(card);
				index++;
			}
		}
		return codes;
	}
	
	public void relocate(int height) {
		for(int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			card.setX(i * card.getWidth() + 10);
			card.setY(height - 10 - card.getHeight());
		}
	}
	
	public void relocate() {
		for(int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			card.setX(i * card.getWidth() + 10);
		}
	}
	
	public void discardShowcaseCards() {
		int size = cards.size();
		cards.remove(size - 1);
		cards.remove(size - 2);
		cards.remove(size - 3);
	}
	
	public void resetCardsInPlay() {
		cardsInPlay.clear();
	}
	
	public boolean canPlay(Card card, Type firstPlayedColor) {
		if(card.getColor() == firstPlayedColor || ColorNotFound(firstPlayedColor)) {
			cards.remove(card);
			relocate();
			return true;
		}
		return false;
	}
	
	public boolean ColorNotFound(Type color) {
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).getColor() == color) return false;
		}
		return true;
	}
	
	public boolean hasPair(Card card) {
		int code1 = encodeCard(card);
		if(code1 > 7 && code1 < 16) {
			for(int i = 0; i < cards.size(); i++) {
				int code2 = encodeCard(cards.get(i));
				if(Math.abs(code1 - code2) == 4) return true;
			}
		}
		return false;
	}
	
}
