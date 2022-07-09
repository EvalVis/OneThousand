package utils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import display.Screen;
import play.Game;

public class MatchCapacitor {
	
	public List<BufferedImage> capturedImages = new ArrayList<BufferedImage>();
	private Screen screen;
	private Game game;
	
	public MatchCapacitor(Screen screen, Game game) {
		this.screen = screen;
		this.game = game;
	}
	
	
	public void captureImage() {
		BufferedImage image = ImageLoader.captureImage(screen, game);
		capturedImages.add(image);
	}
	
	public List<byte[]> convertToBytes() {
		List<byte[]> byteList = new ArrayList<byte[]>();
		for(int i = 0; i < capturedImages.size(); i++) {
			BufferedImage image = capturedImages.get(i);
			if(image == null) return null;
			byte[] imageBytes = ImageLoader.convertImageToBytes(image);
			byteList.add(imageBytes);
		}
		return byteList;
	}

}
