package DisplayManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.util.LinkedList;

import Entity.Block;
import Entity.Bullet;
import Entity.Enemy;
import Entity.Map;
import Entity.Player;
import LootChest.Inventory;
import LootChest.LootChest;
import NewRenderMethod.NewRenderMethod;
import SpriteSheet.SpriteSheet;

public class DisplayManager {
	
	public LinkedList<Block> block = new LinkedList<Block>();
	public LinkedList<Enemy> enemy = new LinkedList<Enemy>();
	public LinkedList<Player> player = new LinkedList<Player>();

	private LinkedList<Bullet> bullet = new LinkedList<Bullet>();
	private LinkedList<LootChest> chest = new LinkedList<LootChest>();
	
	private LinkedList<Integer> EnemyX = new LinkedList<Integer>();
	private LinkedList<Integer> EnemyY = new LinkedList<Integer>();
	
	public LinkedList<Integer> PlayerX = new LinkedList<Integer>();
	public LinkedList<Integer> PlayerY = new LinkedList<Integer>();
	
	public LinkedList<Integer> PlayerXOffset = new LinkedList<Integer>();
	public LinkedList<Integer> PlayerYOffset = new LinkedList<Integer>();
	
	private int[] EnemyTicks = new int[10];
	private String[] EnemyDirection = new String[10];
	
	public BufferedImage Manager;
	public BufferedImage MiniMap;
	
	public int[][] pixel;
	
	public int ID, xOffset=455, yOffset=581, xDim, yDim, OriginX, OriginY;
	
	private Inventory inventory = new Inventory();
	
	public DisplayManager(int width, int height, int ID){
		Manager = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		inventory.EquipItem(new SpriteSheet("Blade.png").getImage());
		inventory.EquipItem(new SpriteSheet("Armor.png").getImage());
		inventory.EquipItem(new SpriteSheet("Boots.png").getImage());
		this.ID=ID;
	}
	
	public void addMap(Map map){
		pixel = new int[map.Village.getWidth()][map.Village.getHeight()];
		MiniMap = new SpriteSheet("MiniMap.png").getImage();
		map.Render();
	}
	
	public void CheckCollision(boolean LOOTED){

		player.get(ID).LOOTABLE=false;
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
						removeEnemy(y);
					}
					
				} 
			}
		}
		
			for(int y =0;y<chest.size();y++){
				if(player.get(ID).Box.intersects(chest.get(y).Box)){
					chest.get(y).ChestOpen=true;
					player.get(ID).LOOTABLE=true;
					if(LOOTED && player.get(ID).LOOTABLE){
						int loot=(int) (Math.random()*3);
						if(loot == 0)inventory.addItem(new SpriteSheet("Blade.png").getImage());
						if(loot == 1)inventory.addItem(new SpriteSheet("Armor.png").getImage());
						if(loot == 2)inventory.addItem(new SpriteSheet("Boots.png").getImage());
						removeChest(y);
						player.get(ID).LOOTABLE=false;
					}
				}
			}
	}
	
	public void Update(){
		
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
			player.get(x).Update(xOffset,yOffset);	
		}
		
		for(int x=0;x<chest.size();x++){
			chest.get(x).Update(xOffset+chest.get(x).xx,yOffset+chest.get(x).yy);
		}

		for(int x=0;x<enemy.size();x++){
			EnemyTicks[x]+=1;
			MoveEnemy(x);
			enemy.get(x).Update(EnemyX.get(x).intValue()+xOffset,EnemyY.get(x).intValue()+yOffset,EnemyDirection[x]);
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
					Manager.setRGB(x, y,
							pixel[x+player.get(NewRenderMethod.PlayerID).x+12]
									[y+player.get(NewRenderMethod.PlayerID).y+12]);
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
			if(NewRenderMethod.PlayerID != x){
				if(player.get(x).ONKEY){
					if(player.get(x).KEYUP)
						player.get(x).RenderUP(g, PlayerX.get(x), PlayerY.get(x));
					if(player.get(x).KEYDOWN)
						player.get(x).RenderDOWN(g, PlayerX.get(x), PlayerY.get(x));
					if(player.get(x).KEYLEFT)
						player.get(x).RenderLEFT(g, PlayerX.get(x), PlayerY.get(x));
					if(player.get(x).KEYRIGHT)
						player.get(x).RenderRIGHT(g, PlayerX.get(x), PlayerY.get(x));
				} else {
					if(player.get(x).KEYUP)
						player.get(x).RenderUPSTILL(g, PlayerX.get(x), PlayerY.get(x));
					if(player.get(x).KEYDOWN)
						player.get(x).RenderDOWNSTILL(g, PlayerX.get(x), PlayerY.get(x));
					if(player.get(x).KEYLEFT)
						player.get(x).RenderLEFTSTILL(g, PlayerX.get(x), PlayerY.get(x));
					if(player.get(x).KEYRIGHT)
						player.get(x).RenderRIGHTSTILL(g, PlayerX.get(x), PlayerY.get(x));
				}
					if(!player.get(x).KEYUP && !player.get(x).KEYDOWN && !player.get(x).KEYLEFT && !player.get(x).KEYRIGHT)
						player.get(x).RenderUPSTILL(g, PlayerX.get(x), PlayerY.get(x));
				
						player.get(x).ONKEY=false;
			} else if(NewRenderMethod.PlayerID == x){
				if(player.get(x).ONKEY){
					if(player.get(x).KEYUP)
						player.get(x).RenderUP(g, 225, 206);
					if(player.get(x).KEYDOWN)
						player.get(x).RenderDOWN(g, 225, 206);
					if(player.get(x).KEYLEFT)
						player.get(x).RenderLEFT(g, 225, 206);
					if(player.get(x).KEYRIGHT)
						player.get(x).RenderRIGHT(g, 225, 206);
				} else {
					if(player.get(x).KEYUP)
						player.get(x).RenderUPSTILL(g, 225, 206);
					if(player.get(x).KEYDOWN)
						player.get(x).RenderDOWNSTILL(g, 225, 206);
					if(player.get(x).KEYLEFT)
						player.get(x).RenderLEFTSTILL(g, 225, 206);
					if(player.get(x).KEYRIGHT)
						player.get(x).RenderRIGHTSTILL(g, 225, 206);
				}
					if(!player.get(x).KEYUP && !player.get(x).KEYDOWN && !player.get(x).KEYLEFT && !player.get(x).KEYRIGHT)
						player.get(x).RenderUPSTILL(g, 225, 206);
				
						player.get(x).ONKEY=false;
			}
		}
		
		for(int x=0;x<enemy.size();x++){
			enemy.get(x).Render(g);
		}
	}
	
	public void RenderGUI(Graphics g, BufferedImage InventoryGUI){
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
		if(player.get(ID).LOOTABLE)g.drawImage(new SpriteSheet("Loot.png").getImage(), 468, 424, null);
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
		this.EnemyX.remove(index);
		this.EnemyY.remove(index);
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
}