package Entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Animation.Animation;
import NewRenderMethod.NewRenderMethod;
import SpriteSheet.Loader;

public class Enemy {

	private static Loader loader = new Loader();
	private static Animation AnimUP;
	private static Animation AnimDOWN;
	private static Animation AnimLEFT;
	private static Animation AnimRIGHT;
	
	private static BufferedImage UP[] = new BufferedImage[3];
	private static BufferedImage DOWN[] = new BufferedImage[3];
	private static BufferedImage LEFT[] = new BufferedImage[3];
	private static BufferedImage RIGHT[] = new BufferedImage[3];
	
	public int x,y,XOffset,YOffset;
	int xir=2;
	
	public Enemy(int x, int y) {
		this.XOffset=x;
		this.YOffset=y;
		UP[0]=loader.Load(0, 132, 26, 45, "Enemy.png");
		UP[1]=loader.Load(27, 132, 26, 45, "Enemy.png");
		UP[2]=loader.Load(54, 132, 26, 45, "Enemy.png");

		DOWN[0]=loader.Load(0, 0, 26, 45, "Enemy.png");
		DOWN[1]=loader.Load(27, 0, 26, 45, "Enemy.png");
		DOWN[2]=loader.Load(54, 0, 26, 45, "Enemy.png");
		
		LEFT[0]=loader.Load(0, 44, 26, 45, "Enemy.png");
		LEFT[1]=loader.Load(27, 44, 26, 45, "Enemy.png");
		LEFT[2]=loader.Load(54, 44, 26, 45, "Enemy.png");
		
		RIGHT[0]=loader.Load(0, 88, 26, 45, "Enemy.png");
		RIGHT[1]=loader.Load(27, 88, 26, 45, "Enemy.png");
		RIGHT[2]=loader.Load(54, 88, 26, 45, "Enemy.png");
		
		AnimUP = new Animation(4, UP[0], UP[1], UP[2]);
		AnimDOWN = new Animation(4, DOWN[0], DOWN[1], DOWN[2]);
		AnimLEFT = new Animation(4, LEFT[0], LEFT[1], LEFT[2]);
		AnimRIGHT = new Animation(4, RIGHT[0], RIGHT[1], RIGHT[2]);

	}

	public void Update(int x, int y,String xir) {
		if(xir=="UP"){
			this.xir=1;
		} else if(xir=="DOWN"){
			this.xir=2;
		} else if(xir=="LEFT"){
			this.xir=3;
		} else if(xir=="RIGHT"){
			this.xir=4;
		}
		AnimUP.runAnimation();
		AnimDOWN.runAnimation();
		AnimLEFT.runAnimation();
		AnimRIGHT.runAnimation();

		this.x=(NewRenderMethod.Width-x)+XOffset;
		this.y=(NewRenderMethod.Height-y)+YOffset;
	}

	public void Render(Graphics g){
		if(x < 449 && y < 502){
			if(x > 0 && y > 0){
				if(xir==1){
					RenderUP(g);
				} else if(xir==2){
					RenderDOWN(g);
				} else if(xir==3){
					RenderLEFT(g);
				} else if(xir==4){
					RenderRIGHT(g);
				}
			}
		}
	}
	public void RenderUP(Graphics g) {
		AnimUP.drawAnimation(g, x, y, 0);
	}
	
	public void RenderDOWN(Graphics g) {
		AnimDOWN.drawAnimation(g, x, y, 0);
	}
	
	public void RenderLEFT(Graphics g) {
		AnimLEFT.drawAnimation(g, x, y, 0);
	}
	
	public void RenderRIGHT(Graphics g) {
		AnimRIGHT.drawAnimation(g, x, y, 0);
	}
	
	public void RenderUPSTILL(Graphics g) {
		g.drawImage(loader.Load(54, 132, 26, 45, "Enemy.png"), x, y, null);
	}
	
	public void RenderDOWNSTILL(Graphics g) {
		g.drawImage(loader.Load(54, 0, 26, 45, "Enemy.png"), x, y, null);
	}
	
	public void RenderLEFTSTILL(Graphics g) {
		g.drawImage(loader.Load(54, 44, 26, 45, "Enemy.png"), x, y, null);
	}
	
	public void RenderRIGHTSTILL(Graphics g) {
		g.drawImage(loader.Load(54, 88, 26, 45, "Enemy.png"), x, y, null);
	}

}
