package Entity;

import java.awt.image.BufferedImage;

public class Bullet extends Entity{

	public Bullet(int x, int y, String Sprite) {
		super(x, y, Sprite);
	}
	
	@Override
	public BufferedImage Render() {
		return this.getSprite();
	}

	@Override
	public void Update() {
		y-=10;
	}

}
