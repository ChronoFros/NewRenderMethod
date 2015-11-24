package Entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Animation.Animation;
import SpriteSheet.Loader;

public class Player{

	Loader loader = new Loader();
	Animation AnimUP;
	Animation AnimDOWN;
	Animation AnimLEFT;
	Animation AnimRIGHT;
	
	BufferedImage UP[] = new BufferedImage[3];
	BufferedImage DOWN[] = new BufferedImage[3];
	BufferedImage LEFT[] = new BufferedImage[3];
	BufferedImage RIGHT[] = new BufferedImage[3];
	
	public int x,y;
	
	public Player(int x, int y) {
		this.x=x;
		this.y=y;
		
		UP[0]=loader.Load(0, 132, 26, 45, "Player.png");
		UP[1]=loader.Load(27, 132, 26, 45, "Player.png");
		UP[2]=loader.Load(54, 132, 26, 45, "Player.png");

		DOWN[0]=loader.Load(0, 0, 26, 45, "Player.png");
		DOWN[1]=loader.Load(27, 0, 26, 45, "Player.png");
		DOWN[2]=loader.Load(54, 0, 26, 45, "Player.png");
		
		LEFT[0]=loader.Load(0, 44, 26, 45, "Player.png");
		LEFT[1]=loader.Load(27, 44, 26, 45, "Player.png");
		LEFT[2]=loader.Load(54, 44, 26, 45, "Player.png");
		
		RIGHT[0]=loader.Load(0, 88, 26, 45, "Player.png");
		RIGHT[1]=loader.Load(27, 88, 26, 45, "Player.png");
		RIGHT[2]=loader.Load(54, 88, 26, 45, "Player.png");
		
		AnimUP = new Animation(4, UP[0], UP[1], UP[2]);
		AnimDOWN = new Animation(4, DOWN[0], DOWN[1], DOWN[2]);
		AnimLEFT = new Animation(4, LEFT[0], LEFT[1], LEFT[2]);
		AnimRIGHT = new Animation(4, RIGHT[0], RIGHT[1], RIGHT[2]);
		

	}

	public void Update() {
		AnimUP.runAnimation();
		AnimDOWN.runAnimation();
		AnimLEFT.runAnimation();
		AnimRIGHT.runAnimation();
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
		g.drawImage(loader.Load(54, 132, 26, 45, "Player.png"), x, y, null);
	}
	
	public void RenderDOWNSTILL(Graphics g) {
		g.drawImage(loader.Load(54, 0, 26, 45, "Player.png"), x, y, null);
	}
	
	public void RenderLEFTSTILL(Graphics g) {
		g.drawImage(loader.Load(54, 44, 26, 45, "Player.png"), x, y, null);
	}
	
	public void RenderRIGHTSTILL(Graphics g) {
		g.drawImage(loader.Load(54, 88, 26, 45, "Player.png"), x, y, null);
	}

}
