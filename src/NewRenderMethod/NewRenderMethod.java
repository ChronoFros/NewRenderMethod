package NewRenderMethod;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import DisplayManager.DisplayManager;
import Entity.Bullet;
import Entity.Enemy;
import Entity.Map;
import Entity.Player;
import InputManager.KeyHandler;
import InputManager.MouseHandler;
import SpriteSheet.Loader;

public class NewRenderMethod extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	public static int Width=618, Height=502, x=430, y=670,xx=-1,yy=0;
	long desiredFPS = 60;
    long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
	
    public boolean UP=true,DOWN,LEFT,RIGHT;
	public static boolean ONKEY;
    
    public Font font = new Font ("OCR A Extended", Font.BOLD , 12);
	public DisplayManager manager;
	public KeyHandler input;
	public MouseHandler mouse;
	public Thread thread;
	public Map map;
	public Loader loader = new Loader();
	Player player = new Player(225, 206);
	
	private BufferedImage stats = loader.Load(0, 0, 612, 473, "Stats.png");
	JFrame frame = new JFrame("NewRenderMethod | "+x+" - "+y);
	private int speed=4;
	
	public NewRenderMethod(){
		thread = new Thread(this);
		input = new KeyHandler();
		mouse= new MouseHandler();
		manager = new DisplayManager(449,Height);
		map = new Map(Width,Height,manager);
		manager.addMap(map);
		this.addKeyListener(input);
		this.addMouseListener(mouse);
		frame.setPreferredSize(new Dimension(Width,Height));
		frame.setMaximumSize(new Dimension(Width,Height));
		frame.setMinimumSize(new Dimension(Width,Height));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.setFocusableWindowState(true);
		frame.add(this);
		thread.start();
	}
	
	public void Update(){
		frame.setTitle("NewRenderMethod | "+x+" - "+y+" | "+mouse.x+" - "+mouse.y);
		manager.Update(x,y);
		player.Update();
		Controls();
	}
	
	public void Render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(3);
		}
		bs = this.getBufferStrategy();
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(loader.Load(0, 0, Width, Height, "SkyCloud.png"),0,0,null);
		manager.Render(g, x, y); 
		
		//Health
		g.setColor(Color.RED);
		g.fillRect(467, 175, 141, 14);
		//Mana
		g.setColor(Color.BLUE);
		g.fillRect(467, 200, 141, 14);
		//Stats
		g.drawImage(stats,0,0,null);
		
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("100/100", 510, 185);
		g.drawString("100/100", 510, 210);
		
		
		if(ONKEY == true){
			if(UP==true)player.RenderUP(g);
			if(DOWN==true)player.RenderDOWN(g);
			if(LEFT==true)player.RenderLEFT(g);
			if(RIGHT==true)player.RenderRIGHT(g);
		} else {
			if(UP==true)player.RenderUPSTILL(g);
			if(DOWN==true)player.RenderDOWNSTILL(g);
			if(LEFT==true)player.RenderLEFTSTILL(g);
			if(RIGHT==true)player.RenderRIGHTSTILL(g);
		}
		
		g.dispose();
		bs.show();
	}

	@Override
	public void run() {
		 long beginLoopTime;
	      long endLoopTime;
	      long currentUpdateTime = System.nanoTime();
	      long lastUpdateTime;
	      long deltaLoop;
	      
	      while(true){
	         beginLoopTime = System.nanoTime();
	         
	         Render();
	         
	         lastUpdateTime = currentUpdateTime;
	         currentUpdateTime = System.nanoTime();
	         Update();
	         
	         endLoopTime = System.nanoTime();
	         deltaLoop = endLoopTime - beginLoopTime;
	           
	           if(deltaLoop > desiredDeltaLoop){

	           }else{
	               try{
	                   Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
	               }catch(InterruptedException e){
	               }
	           }
	      }
	}
	
	public void Controls(){
		if(y >= 4 ){
			if(input.UP){
				UP=true;
				DOWN=false;
				LEFT=false;
				RIGHT=false;
				ONKEY=true;
				if(!input.LEFT || !input.RIGHT){
					y-=speed;
				} else {
					y-=speed/2;
				}
			}
		}
		if(y <= map.TileSize()*((map.map.length/2)+5) ){
			if(input.DOWN){
				UP=false;
				DOWN=true;
				LEFT=false;
				RIGHT=false;
				ONKEY=true;
				if(!input.LEFT || !input.RIGHT){
					y+=speed;
				} else {
					y+=speed/2;
				}
			}
		}
		if(x >= 4 ){
			if(input.LEFT){
				UP=false;
				DOWN=false;
				LEFT=true;
				RIGHT=false;
				ONKEY=true;
				if(!input.UP || !input.DOWN){
					x-=speed;
				} else {
					x-=speed/2;
				}
			}
		}
		if(x <= map.TileSize()*((map.map.length/2)+6) ){
			if(input.RIGHT==true){
				UP=false;
				DOWN=false;
				LEFT=false;
				RIGHT=true;
				ONKEY=true;
				if(!input.UP || !input.DOWN){
					x+=speed;
				} else {
					x+=speed/2;
				}
			}
		}
	}
	
	public static void main(String[] args){
		new NewRenderMethod();
	}
}
