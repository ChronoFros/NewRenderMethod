package SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public String path;
	public int width;
	public int height;
	BufferedImage image = null;
	
	public SpriteSheet (String path) {
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
			width=image.getWidth();
			height=image.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
