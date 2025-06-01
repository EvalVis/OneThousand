package ev.projects.onethousand.cards;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ev.projects.onethousand.utils.ImageLoader;

public abstract class Card {
	
	private Type color;
	private int x;
	private int y;
	private boolean selectedToDiscard;
	
	private BufferedImage sprite;
	private static BufferedImage XIcon = ImageLoader.loadImage("x.png");
	
	public Card(int x, int y, String path, Type color) {
		this.x = x;
		this.y = y;
		sprite = ImageLoader.loadImage(path);
		this.color = color;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x, y, null);
		if(selectedToDiscard) g.drawImage(XIcon, x, y, null);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return sprite.getWidth();
	}
	
	public int getHeight() {
		return sprite.getHeight();
	}
	
	public void select() {
		selectedToDiscard = !selectedToDiscard;
	}
	
	public boolean selected() {
		return selectedToDiscard;
	}
	
	public Type getColor() {
		return color;
	}

}
