package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javax.imageio.ImageIO;

import display.Screen;
import play.Game;

public class ImageLoader {
	
	public static BufferedImage loadImage(String path) {
		URL resourcePath = Objects.requireNonNull(ImageLoader.class.getClassLoader().getResource(path));
		try {
			return ImageIO.read(resourcePath);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static byte[] convertImageToBytes(BufferedImage image) {
		ByteArrayOutputStream baos = null;
		byte[] imageBytes = null;
		try {
		baos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", baos);
		baos.flush();
		imageBytes = baos.toByteArray();
		}
		catch(Exception e) {
			System.out.println("Failed to capture an image.");
		}
		if(baos != null) {
			try {
				baos.close();
			} catch (IOException e) {
				System.out.println("Failed o close byte array output stream.");
			}
		}
		return imageBytes;
	}
	
	public static BufferedImage captureImage(Screen screen, Game game) {
		BufferedImage image = new BufferedImage(screen.getWidth(), screen.getHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, screen.getWidth(), screen.getHeight());
		screen.render(g2, game);
		g2.dispose();
		return image;
	}
	
	public static void saveCanvasAsPicture(Screen screen, Game game) {
		BufferedImage image = new BufferedImage(screen.getWidth(), screen.getHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, screen.getWidth(), screen.getHeight());
		screen.render(g2, game);
		try {
			File file = new File("canvas.png");
			file.createNewFile();
			ImageIO.write(image, "png", file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		g2.dispose();
	}

}
