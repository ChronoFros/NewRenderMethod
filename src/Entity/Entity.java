package Entity;

import java.awt.image.BufferedImage;

import DisplayManager.DisplayManager;
import SpriteSheet.SpriteSheet;

abstract class Entity {
	public int x,y,xx,yy,LifeSpan;
	public BufferedImage sprite;
	public SpriteSheet sheet;
	
	public Entity(int x,int y,String Sprite){
		sheet = new SpriteSheet(Sprite);
		this.sprite=sheet.getImage();
		this.x=x;
		this.y=y;
	}
	
	public Entity(int x,int y,BufferedImage sprite){
		this.sprite=sprite;
		this.x=x;
		this.y=y;
	}
	public Entity(int x,int y, int xx, int yy, int LifeSpan, String Sprite){
		sheet = new SpriteSheet(Sprite);
		this.sprite=sheet.getImage();
		this.x=x;
		this.y=y;
		this.xx=xx;
		this.yy=yy;
		this.LifeSpan=LifeSpan;
	}
	
	public abstract void Update();
	public abstract BufferedImage Render();
	
	public void setX(int x){
		this.x=x;
	}
	
	public void setY(int y){
		this.y=y;
	}
	
	public void setY(BufferedImage sprite){
		this.sprite=sprite;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public BufferedImage getSprite(){
		return sprite;
	}
}
