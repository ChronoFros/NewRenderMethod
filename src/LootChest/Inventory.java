package LootChest;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import SpriteSheet.SpriteSheet;

public class Inventory {
	
	int PointWidth=46, PointHeight=42;
	int[] PointState = new int[9];
	int[] BuffState = new int[3];
	BufferedImage Inventory[] = new BufferedImage[9];
	BufferedImage Equips[] = new BufferedImage[3];

	public Inventory(){
		for(int x=0;x<Inventory.length;x++){
			Inventory[x]=new SpriteSheet("BlankPoint.png").getImage();
			PointState[x]=0;
		}
		for(int x=0;x<Equips.length;x++){
			Equips[x]=new SpriteSheet("BlankPoint.png").getImage();
			BuffState[x]=0;
		}
	}
	
	public void Render(Graphics g){
		for(int x=0;x<3;x++){
				g.drawImage(Equips[x], (x*48)+467, 232, null);
		}
		int z=0;
		for(int x=0;x<3;x++){
			for(int y=0;y<3;y++){
				g.drawImage(Inventory[z++], (y*48)+467, (x*44)+295, null);
			}
		}
	}
	
	public void EquipItem(BufferedImage item){
		for(int x=0;x<BuffState.length;x++){
			if(BuffState[x]==0){
				Equips[x]=item;
				BuffState[x]=1;
				break;
			}
		}
	}
	
	public void addItem(BufferedImage item){
		for(int x=0;x<PointState.length;x++){
			if(PointState[x]==0){
				Inventory[x]=item;
				PointState[x]=1;
				break;
			}
		}
	}
	
	public void removeItem(int index){
		Inventory[index]=new SpriteSheet("BlankPoint.png").getImage();
	}
}
