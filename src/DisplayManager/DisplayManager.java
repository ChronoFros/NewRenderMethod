package DisplayManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import Entity.Block;
import Entity.Bullet;
import Entity.Enemy;
import Entity.Map;
import Entity.Player;

public class DisplayManager {
	
	public LinkedList<Block> block = new LinkedList<Block>();
	private LinkedList<Enemy> enemy = new LinkedList<Enemy>();
	private LinkedList<Player> player = new LinkedList<Player>();
	private LinkedList<Bullet> bullet = new LinkedList<Bullet>();
	
	public boolean UP,DOWN,LEFT,RIGHT,ONKEY;
	public int xOffset = 430, yOffset = 670, xxOffset = 0, yyOffset = 0;
	
	private int[] EnemyX = new int[10],
				 EnemyY = new int[10],
				 EnemyTicks = new int[10];
	private String[] EnemyDirection = new String[10];
	String LASTDIR="UP";
	
	public BufferedImage Manager;
	public int[][] pixel; 
	
	public DisplayManager(int width, int height){
		Manager = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	}
	
	public void addMap(Map map){
		pixel = new int[(map.map.length)*(map.TileSize()+1)][(map.map.length)*(map.TileSize()+1)];
		map.Render();
	}
	
	public void Update(int xx, int yy){
		for(int x =0;x<player.size();x++){
			for(int y =0;y<enemy.size();y++){
				if(player.get(x).Box.intersects(enemy.get(y).Box)){
					if(player.get(x).Health >= 2)player.get(x).Health-=1;
				}
			}
		}
		
		for(int x =0;x<bullet.size();x++){
			for(int y =0;y<enemy.size();y++){
				if(bullet.get(x).Box.intersects(enemy.get(y).Box)){
					if(enemy.get(y).HP<=1)removeEnemy(y);
					if(enemy.get(y).HP>=1)enemy.get(y).HP-=5;
				} 
			}
		}
		
		for(int x=0;x<bullet.size();x++){
			if(bullet.get(x).Updates >= bullet.get(x).LifeSpan){
				removeBullet(x);
			}
		}
		
		for(int x=0;x<block.size();x++){
			block.get(x).Update();
		}
		
		for(int x=0;x<bullet.size();x++){
			bullet.get(x).Update();
		}

		for(int x=0;x<player.size();x++){
			player.get(x).Update();
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
	
	public void Render(Graphics g){
		for(int x=0;x<Manager.getWidth();x++){
			for(int y=0;y<Manager.getHeight();y++){
				Manager.setRGB(x, y, pixel[x+xOffset][y+yOffset]);
			}
		}
		
		for(int x=0;x<bullet.size();x++){
			g.drawImage(bullet.get(x).Render(),bullet.get(x).x,bullet.get(x).y,null);
		}
		
		for(int x=0;x<player.size();x++){
			if(ONKEY){
				if(UP)
					player.get(x).RenderUP(g);
				if(DOWN)
					player.get(x).RenderDOWN(g);
				if(LEFT)
					player.get(x).RenderLEFT(g);
				if(RIGHT)
					player.get(x).RenderRIGHT(g);
			} else {
				if(UP)
					player.get(x).RenderUPSTILL(g);
				if(DOWN)
					player.get(x).RenderDOWNSTILL(g);
				if(LEFT)
					player.get(x).RenderLEFTSTILL(g);
				if(RIGHT)
					player.get(x).RenderRIGHTSTILL(g);
			}
				if(!UP && !DOWN && !LEFT && !RIGHT)
					player.get(x).RenderUPSTILL(g);
		}
		
		for(int x=0;x<enemy.size();x++){
			enemy.get(x).Render(g);
		}
	}
	
	public void RenderGUI(Graphics g, int ID, BufferedImage InventoryGUI){
		g.setColor(Color.RED);
		g.fillRect(467, 175, (141*player.get(ID).Health)/100, 14);
		g.setColor(Color.BLUE);
		g.fillRect(467, 200, (141*player.get(ID).Mana)/100, 14);

		g.drawImage(InventoryGUI,0,0,null);
		
		g.setFont(new Font ("OCR A Extended", Font.BOLD , 12));
		g.setColor(Color.WHITE);
		g.drawString(player.get(ID).Health+"/"+player.get(ID).MaxHealth, 510, 185);
		g.drawString(player.get(ID).Mana+"/"+player.get(ID).MaxMana, 510, 210);
	}
	
	public void addBullet(Bullet enemy){
		this.bullet.add(enemy);
	}
	
	public void removeBullet(int index){
		this.bullet.remove(index);
	}
	
	public void addEnemy(Enemy enemy){
		this.enemy.add(enemy);
	}
	
	public void removeEnemy(int index){
		this.enemy.remove(index);
	}
	
	public void addPlayer(Player player){
		this.player.add(player);
	}
	
	public void removePlayer(int index){
		this.player.remove(index);
	}
	
	public void MovePlayer(int speed, int x, int y, int width, int height, int mouseX, int mouseY, boolean UP, boolean DOWN, boolean LEFT, boolean RIGHT, boolean ONKEY, boolean SPECIAL, boolean FIREING){
		if(y >= 4 ){
			if(UP){
				this.UP=true;
				this.DOWN=false;
				this.LEFT=false;
				this.RIGHT=false;
				ONKEY=true;
				LASTDIR="UP";
				if(!LEFT || !RIGHT){
					this.yOffset-=speed;
				} else {
					this.yOffset-=speed/2;
				}
			}
		}
		if(y <= height ){
			if(DOWN){
	 			this.UP=false;
				this.DOWN=true;
				this.LEFT=false;
				this.RIGHT=false;
				ONKEY=true;
				LASTDIR="DOWN";
				if(!LEFT || !RIGHT){
					this.yOffset+=speed;
				} else {
					this.yOffset+=speed/2;
				}
			}
		}
		if(x >= 4 ){
			if(LEFT){
				this.UP=false;
				this.DOWN=false;
				this.LEFT=true;
				this.RIGHT=false;
				ONKEY=true;
				LASTDIR="LEFT";
				if(!UP || !DOWN){
					this.xOffset-=speed;
				} else {
					this.xOffset-=speed/2;
				}
			}
		}
		if(x <= width ){
			if(RIGHT==true){
				this.UP=false;
				this.DOWN=false;
				this.LEFT=false;
				this.RIGHT=true;
				ONKEY=true;
				LASTDIR="RIGHT";
				if(!UP || !DOWN){
					this.xOffset+=speed;
				} else {
					this.xOffset+=speed/2;
				}
			}
		}
		if(SPECIAL){
			if(bullet.size()<=0){
				if(LASTDIR=="UP"){
					addBullet(new Bullet(player.get(0).x+5, player.get(0).y+15, 0, -player.get(0).Dex, player.get(0).Range, "Bullet.png"));
				}
				if(LASTDIR=="DOWN"){
					addBullet(new Bullet(player.get(0).x+5, player.get(0).y+15, 0, player.get(0).Dex, player.get(0).Range, "Bullet.png"));
				}
				if(LASTDIR=="LEFT"){
					addBullet(new Bullet(player.get(0).x+5, player.get(0).y+15, -player.get(0).Dex, 0, player.get(0).Range, "Bullet.png"));
				}
				if(LASTDIR=="RIGHT"){
					addBullet(new Bullet(player.get(0).x+5, player.get(0).y+15, player.get(0).Dex, 0, player.get(0).Range, "Bullet.png"));
				}
			}
		}
		if(!ONKEY){
			if(SPECIAL){
				if(player.get(0).Health <= player.get(0).MaxHealth-1)player.get(0).Health+=1;
				if(player.get(0).Mana >= 1)player.get(0).Mana-=1;
				if(player.get(0).Mana <= 1)player.get(0).MaxHealth+=2;
			}
		} else {
				if(player.get(0).Mana <= player.get(0).MaxMana-1)player.get(0).Mana+=1;
		}
				this.ONKEY=ONKEY;
	}
}
