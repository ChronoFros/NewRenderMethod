package Entity;

import java.awt.image.BufferedImage;

import DisplayManager.DisplayManager;
import SpriteSheet.Loader;
import SpriteSheet.SpriteSheet;

public class Map {
	public int width,height;
	DisplayManager manager;
	Loader loader = new Loader();
	
	public BufferedImage Village = new SpriteSheet("Village.png").getImage();
	
	public Map(int width,int height,DisplayManager manager){
		this.width=width;
		this.height=height;
		this.manager=manager;
		Update();
	}
	
	public void Update(){

	}
	
	public void Render(){
			for(int x=0;x<Village.getWidth();x++){
				for(int y=0;y<Village.getHeight();y++){
					manager.pixel[x][y] = Village.getRGB(x, y);
				}
			}
	}
}
