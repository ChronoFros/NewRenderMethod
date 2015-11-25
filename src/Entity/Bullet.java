package Entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bullet extends Entity{

	public int Updates=0;
	public Rectangle Box;
	
	public Bullet(int x, int y, int xx, int yy, int LifeSpan, String Sprite) {
		super(x, y, xx, yy, LifeSpan, Sprite);
		Box = new Rectangle(x,y,Render().getWidth(),Render().getHeight());
	}
	
	@Override
	public void Update() {
		Updates+=1;
		x+=xx;
		y+=yy;
		Box = new Rectangle(x,y,Render().getWidth(),Render().getHeight());
	}
	
	@Override
	public BufferedImage Render() {
		return this.getSprite();
	}

}
