package ev.projects.onethousand.utils;

import java.util.List;

import ev.projects.onethousand.cards.Card;
import ev.projects.onethousand.input.Mouse;

public class MouseTarget {
	
	public static Card getTargetedCard(List<Card> cards) {
		if(Mouse.Button != 1) return null;
		for(int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			int startX = card.getX();
			int startY = card.getY();
			int endX = card.getX() + card.getWidth();
			int endY = card.getY() + card.getHeight();
			if(Mouse.X > startX && Mouse.X < endX && Mouse.Y > startY && Mouse.Y < endY) return card;
		}
		return null;
	}

}
