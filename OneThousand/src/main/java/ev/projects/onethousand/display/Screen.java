package ev.projects.onethousand.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ev.projects.onethousand.game.Main;
import ev.projects.onethousand.play.Game;
import ev.projects.onethousand.utils.ImageLoader;

public class Screen {
	
	private BufferedImage table;
	public BufferedImage hiddenCard;
	
	private String infoMessage = "Trying to connect.";
	private String statusMessage = "";
	
	private Main main;
	public int height;
	
	public Screen(Main main, int height) {
		this.main = main;
		this.height = height;
		table = ImageLoader.loadImage("table.png");
		hiddenCard = ImageLoader.loadImage("back.png");
	}
	
	public void render(Graphics g, Game game) {
		g.drawImage(table, 0, 0, main.getWidth(), main.getHeight(), null);
		if(game != null && game.match != null) {
			for(int i = 0; i < 3; i++) {
				if(!game.match.callEnded) g.drawImage(hiddenCard, i * hiddenCard.getWidth() + 800, 70, null);
			}
			if(game.match.black) {
				for(int i = 0; i < 7; i++) {
					g.drawImage(hiddenCard, i * hiddenCard.getWidth(), main.getHeight() - 10 - hiddenCard.getHeight(), null);
				}
			}
			else {
				if(game.match.cardManager != null) {
					for(int i = 0; i < game.match.cardManager.cards.size(); i++) {
						game.match.cardManager.cards.get(i).render(g);
					}
					for(int i = 0; i < game.match.cardManager.cardsInPlay.size(); i++) {
						game.match.cardManager.cardsInPlay.get(i).render(g);
					}
				}
			}
		}
		Font font = new Font("TimesRoman", Font.PLAIN, 24);
		g.setFont(font);
		g.setColor(Color.YELLOW);
		g.drawString(infoMessage, 140, 140);
		g.setColor(Color.RED);
		g.drawString(statusMessage, 140, 50);
	}
	
	
	public void setInfoMessage(String message) {
		infoMessage = message;
	}
	
	public void setStatusMessage(String message) {
		statusMessage = message;
	}
	
	public int getWidth() {
		return main.getWidth();
	}
	
	public int getHeight() {
		return main.getHeight();
	}

}
