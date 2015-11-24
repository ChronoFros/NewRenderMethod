package Entity;

import java.awt.image.BufferedImage;

public class Block extends Entity{

	public Block(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}

	@Override
	public void Update() {
		
	}

	@Override
	public BufferedImage Render() {
		return this.getSprite();
	}

}
