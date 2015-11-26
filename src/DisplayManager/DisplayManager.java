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
import LootChest.Inventory;
import LootChest.LootChest;
import SpriteSheet.Loader;
import SpriteSheet.SpriteSheet;

public class DisplayManager {
	
	public LinkedList<Block> block = new LinkedList<Block>();
	public LinkedList<Enemy> enemy = new LinkedList<Enemy>();
	private LinkedList<Player> player = new LinkedList<Player>();
	private LinkedList<Bullet> bullet = new LinkedList<Bullet>();
	private LinkedList<LootChest> chest = new LinkedList<LootChest>();
	
	private LinkedList<Integer> EnemyX = new LinkedList<Integer>();
	private LinkedList<Integer> EnemyY = new LinkedList<Integer>();
	
	public boolean UP,DOWN,LEFT,RIGHT,ONKEY;
	public int xOffset = 430, yOffset = 670, xxOffset, yyOffset, OriginX, OriginY;
	
	private int[] EnemyTicks = new int[10];
	private String[] EnemyDirection = new String[10];
	String LASTDIR="UP";
	
	public BufferedImage Manager;
	public BufferedImage MiniMap;
	public int[][] pixel; 
	
	private Inventory inventory = new Inventory();
	
	public DisplayManager(int width, int height){
		Manager = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		inventory.EquipItem(new SpriteSheet("Blade.png").getImage());
		inventory.EquipItem(new SpriteSheet("Armor.png").getImage());
		inventory.EquipItem(new SpriteSheet("Boots.png").getImage());
	}
	
	public void addMap(Map map){
		pixel = new int[(map.map.length)*(map.TileSize()+1)][(map.map.length)*(map.TileSize()+1)];
		MiniMap = new SpriteSheet("MiniMap.png").getImage();
		//for(int x=0; x< map.map.length;x++){
		//	for(int y=0; y< map.map.length;y++){
		//		if(map.map[x][y]!=0)MiniMap.setRGB((int)(x*2.52)+2, (int)(y*2.1)+2, 0);
		//	}	
		//}
		map.Render();
	}
	
	public void CheckCollision(){
		for(int x =0;x<bullet.size();x++){
			for(int y =0;y<enemy.size();y++){
				if(bullet.get(x).Box.intersects(enemy.get(y).Box)){
					
					if(enemy.get(y).HP<=1){
						int[] TempEnemyX = new int[EnemyX.size()];
						int[] TempEnemyY = new int[EnemyY.size()];
						int i=0, v=0;
						while(i < enemy.size()-1){
							v++;
							if(i != y) {
								x++;
							} else i+=2;
							TempEnemyX[v]=EnemyX.get(i).intValue();
							TempEnemyY[v]=EnemyY.get(i).intValue();
						}
						for(int z =0; z < enemy.size();z++){
							EnemyX.set(z, TempEnemyX[z]);
							EnemyY.set(z, TempEnemyY[z]);
						}
					}
					if(enemy.get(y).HP>=1)enemy.get(y).HP-=5;
					if(enemy.get(y).HP<1){
						addChest(new LootChest(xOffset-(OriginX-enemy.get(y).XOffset),yOffset-(OriginY-enemy.get(y).YOffset)));
						chest.getLast().setLocation(EnemyX.get(x).intValue()+xOffset,EnemyY.get(x).intValue()+yOffset);
						int loot=(int) (Math.random()*3);
						if(loot == 0)inventory.addItem(new SpriteSheet("Blade.png").getImage());
						if(loot == 1)inventory.addItem(new SpriteSheet("Armor.png").getImage());
						if(loot == 2)inventory.addItem(new SpriteSheet("Boots.png").getImage());
						removeEnemy(y);
					}
					
				} 
			}
		}
		
		for(int x =0;x<player.size();x++){
			for(int y =0;y<enemy.size();y++){
				if(player.get(x).Box.intersects(enemy.get(y).Box)){
					if(player.get(x).Health >= 2)player.get(x).Health-=1;
				}
			}
		}
		
		for(int x =0;x<player.size();x++){
			for(int y =0;y<chest.size();y++){
				if(player.get(x).Box.intersects(chest.get(y).Box)){
					chest.get(y).ChestOpen=true;
				}
			}
		}
	}
	
	public void Update(int xx, int yy){
		
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
		
		for(int x=0;x<chest.size();x++){
			chest.get(x).Update(xx+chest.get(x).xx,yy+chest.get(x).yy);
		}

		for(int x=0;x<enemy.size();x++){
			EnemyTicks[x]+=1;
			MoveEnemy(x);
			enemy.get(x).Update(EnemyX.get(x).intValue()+xx,EnemyY.get(x).intValue()+yy,EnemyDirection[x]);
		}
	}
	
	public void MoveEnemy(int x){
		if(EnemyTicks[x] >= 0 && EnemyTicks[x] <= 100){
			EnemyY.set(x, EnemyY.get(x).intValue()-1);
			EnemyDirection[x]="DOWN";
			
		} else if(EnemyTicks[x] >= 101 && EnemyTicks[x] <= 200){
			EnemyX.set(x, EnemyX.get(x).intValue()-1);
			EnemyDirection[x]="RIGHT";
			
		} else if(EnemyTicks[x] >= 201 && EnemyTicks[x] <= 300){
			EnemyY.set(x, EnemyY.get(x).intValue()+1);
			EnemyDirection[x]="UP";
			
		} else if(EnemyTicks[x] >= 301 && EnemyTicks[x] <= 400){
			EnemyX.set(x, EnemyX.get(x).intValue()+1);
			EnemyDirection[x]="LEFT";
			
		} else {
			EnemyTicks[x]=0;
		}
	}
	
	public void Render(Graphics g){
		for(int x=0;x<Manager.getWidth();x++){
			for(int y=0;y<Manager.getHeight();y++){
				Manager.setRGB(x, y, pixel[x+xOffset][y+yOffset]);
			}
		}
		
		g.drawImage(MiniMap, 486,25, null);
		
		for(int x=0;x<bullet.size();x++){
			g.drawImage(bullet.get(x).Render(),bullet.get(x).x,bullet.get(x).y,null);
		}
		
		for(int x=0;x<chest.size();x++){
			chest.get(x).Render(g);
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
		
		inventory.Render(g);
	}
	
	public void addBullet(Bullet enemy){
		this.bullet.add(enemy);
	}
	
	public void removeBullet(int index){
		this.bullet.remove(index);
	}
	
	public void addEnemy(Enemy enemy){
		this.enemy.add(enemy);
		this.EnemyX.add(0);
		this.EnemyY.add(0);
		if(this.enemy.size()==0){
			OriginX=this.enemy.get(0).XOffset;
			OriginY=this.enemy.get(0).YOffset;
		}
	}
	
	public void removeEnemy(int index){
		this.enemy.remove(index);
		this.EnemyX.remove(0);
		this.EnemyY.remove(0);
	}
	
	public void addPlayer(Player player){
		this.player.add(player);
	}
	
	public void removePlayer(int index){
		this.player.remove(index);
	}
	
	public void addChest(LootChest chest){
		this.chest.add(chest);
	}
	
	public void removeChest(int index){
		this.chest.remove(index);
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