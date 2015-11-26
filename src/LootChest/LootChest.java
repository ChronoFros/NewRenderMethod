package LootChest;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import NewRenderMethod.NewRenderMethod;
import SpriteSheet.Loader;

public class LootChest {
	
	BufferedImage[] Open = new BufferedImage[2];
	Loader loader = new Loader();
	public boolean ChestOpen=false;
	public int x, y, xx, yy, XOffset, YOffset, ID;
	public Rectangle Box;
	
	public LootChest(int x, int y){
		this.XOffset=x;
		this.YOffset=y;
		
		Open[0]=loader.Load(0, 0, 31, 30, "Chests.png");
		Open[1]=loader.Load(0, 30, 31, 32, "Chests.png");
		
		Box = new Rectangle(x,y,Open[0].getWidth(),Open[0].getHeight());
	}
	
	public void Update(int x, int y){
		this.x=(NewRenderMethod.Width-x)+XOffset;
		this.y=(NewRenderMethod.Height-y)+YOffset;
		
		Box = new Rectangle(this.x,this.y,Open[0].getWidth(),Open[0].getHeight());
	}
	
	public void setLocation(int x, int y){
		this.xx=x;
		this.yy=y;
	}
	public void Render(Graphics g){
		if(ChestOpen){
			g.drawImage(Open[1], x , y, null);
			ChestOpen=false;
		} else {
			g.drawImage(Open[0], x , y, null);
		}
	}
	
}
