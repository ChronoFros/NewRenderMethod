package DisplayManager;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import Entity.Block;
import Entity.Enemy;
import Entity.Map;
import NewRenderMethod.NewRenderMethod;

public class DisplayManager {
	
	public LinkedList<Block> block;
	public LinkedList<Enemy> enemy;
	
	public int[] EnemyX = new int[10];
	public int[] EnemyY = new int[10];
	public int[] EnemyTicks = new int[10];
	public String[] EnemyDirection = new String[10];
	
	public BufferedImage Manager;
	public int[][] pixel;
	
	public DisplayManager(int width, int height){
		Manager = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		block = new LinkedList<Block>();
		enemy = new LinkedList<Enemy>();
		
		
		//Render row of entitys
		enemy.add(new Enemy(0,0));
		enemy.add(new Enemy(100,100));
		enemy.add(new Enemy(100,200));
		enemy.add(new Enemy(100,300));
		enemy.add(new Enemy(100,400));
		enemy.add(new Enemy(100,500));
		enemy.add(new Enemy(100,600));
	}
	
	public void addMap(Map map){
		pixel = new int[(map.map.length)*(map.TileSize()+1)][(map.map.length)*(map.TileSize()+1)];
		map.Render();
	}
	
	public void Update(int xx, int yy){
		
		for(int x=0;x<block.size();x++){
			block.get(x).Update();
		}
		for(int x=0;x<enemy.size();x++){
			EnemyTicks[x]+=1;
			
			if(EnemyTicks[x] >= 0 && EnemyTicks[x] <= 100){
				EnemyY[x]-=1;
				EnemyDirection[x]="DOWN";
				
			} else if(EnemyTicks[x] >= 101 && EnemyTicks[x] <= 200){
				EnemyX[x]-=1;
				EnemyDirection[x]="RIGHT";
				
			} else if(EnemyTicks[x] >= 201 && EnemyTicks[x] <= 300){
				EnemyY[x]+=1;
				EnemyDirection[x]="UP";
				
			} else if(EnemyTicks[x] >= 301 && EnemyTicks[x] <= 400){
				EnemyX[x]+=1;
				EnemyDirection[x]="LEFT";
				
			} else {
				EnemyTicks[x]=0;
			}
				enemy.get(x).Update(EnemyX[x]+xx,EnemyY[x]+yy,EnemyDirection[x]);	
		}
	}
	
	public void Render(Graphics g, int xOffset, int yOffset){
		for(int x=0;x<Manager.getWidth();x++){
			for(int y=0;y<Manager.getHeight();y++){
				Manager.setRGB(x, y, pixel[x+xOffset][y+yOffset]);
			}
		}
		g.drawImage(Manager, 0, 0, null);
		
		for(int x=0;x<enemy.size();x++){
			enemy.get(x).Render(g);
		}
	}
}
