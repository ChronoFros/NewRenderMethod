package SpriteSheet;

import java.awt.image.BufferedImage;

public class Loader {
	
	public BufferedImage Load(int x, int y, int width, int height, String image){
		SpriteSheet sheet = new SpriteSheet(image);
		BufferedImage newimage=sheet.getImage();
		newimage=newimage.getSubimage(x, y, width, height);
		return newimage;
	}

}
