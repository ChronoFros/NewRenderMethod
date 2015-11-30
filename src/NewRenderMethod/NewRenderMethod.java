package NewRenderMethod;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JFrame;

import DisplayManager.DisplayManager;
import Entity.Map;
import Entity.Player;
import InputManager.KeyHandler;
import InputManager.MouseHandler;
import MortalClient.Input;
import SpriteSheet.Loader;

public class NewRenderMethod extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	public static int Width = 618, Height = 502;
	
	public static DataOutputStream out;
	public static DataInputStream in;
	public static Socket sSocket;
	public static int PlayerID;
	
	private KeyHandler input = new KeyHandler();
	private MouseHandler mouse =  new MouseHandler();;
	private DisplayManager manager = new DisplayManager(449,Height,PlayerID);

	public Thread thread = new Thread(this);
	private Loader loader = new Loader();
	private JFrame frame = new JFrame();
	
	private Map map = new Map(Width,Height,manager);
	
	private BufferedImage BackGroundGUI = loader.Load(0, 0, Width, Height, "SkyCloud.png");
	private BufferedImage InventoryGUI = loader.Load(0, 0, 612, 473, "Stats.png");
	
	public NewRenderMethod(){
		try{
		sSocket = new Socket("localhost",7214);
		in = new DataInputStream(sSocket.getInputStream());
		PlayerID=in.readInt();
		out = new DataOutputStream(sSocket.getOutputStream());
		Input input = new Input(in,this);
		Thread thread = new Thread(input);
		thread.start();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		manager.addMap(map);
		manager.addPlayer(new Player(445,575));
		manager.PlayerX.add(0);
		manager.PlayerXOffset.add(-220);
		manager.PlayerY.add(0);
		manager.PlayerYOffset.add(-370);
		manager.addPlayer(new Player(445,575));
		manager.PlayerX.add(0);
		manager.PlayerXOffset.add(-220);
		manager.PlayerY.add(0);
		manager.PlayerYOffset.add(-370);
		manager.addPlayer(new Player(445,575));
		manager.PlayerX.add(0);
		manager.PlayerXOffset.add(-220);
		manager.PlayerY.add(0);
		manager.PlayerYOffset.add(-370);
		
		this.addKeyListener(input);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		
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
		int speed = 2;
		manager.player.get(PlayerID).ONKEY=false;
			if(input.UP){
				manager.player.get(PlayerID).KEYUP=true;
				manager.player.get(PlayerID).KEYDOWN=false;
				manager.player.get(PlayerID).KEYLEFT=false;
				manager.player.get(PlayerID).KEYRIGHT=false;
				manager.player.get(PlayerID).ONKEY=true;
				if(!input.LEFT || !input.RIGHT){
					manager.player.get(PlayerID).y-=speed;
					for(int x=0;x<manager.player.size();x++)if(PlayerID!=x)manager.PlayerYOffset.set(x, manager.PlayerYOffset.get(x)+speed);
				} else {
					manager.player.get(PlayerID).y-=speed/2;
					for(int x=0;x<manager.player.size();x++)if(PlayerID!=x)manager.PlayerYOffset.set(x, manager.PlayerYOffset.get(x)+speed/2);
				}
			}
			if(input.DOWN){
				manager.player.get(PlayerID).KEYUP=false;
				manager.player.get(PlayerID).KEYDOWN=true;
				manager.player.get(PlayerID).KEYLEFT=false;
				manager.player.get(PlayerID).KEYRIGHT=false;
				manager.player.get(PlayerID).ONKEY=true;
				if(!input.LEFT || !input.RIGHT){
					manager.player.get(PlayerID).y+=speed;
					for(int x=0;x<manager.player.size();x++)if(PlayerID!=x)manager.PlayerYOffset.set(x, manager.PlayerYOffset.get(x)-speed);
				} else {
					manager.player.get(PlayerID).y+=speed/2;
					for(int x=0;x<manager.player.size();x++)if(PlayerID!=x)manager.PlayerYOffset.set(x, manager.PlayerYOffset.get(x)-speed/2);
				}
			}
			if(input.LEFT){
				manager.player.get(PlayerID).KEYUP=false;
				manager.player.get(PlayerID).KEYDOWN=false;
				manager.player.get(PlayerID).KEYLEFT=true;
				manager.player.get(PlayerID).KEYRIGHT=false;
				manager.player.get(PlayerID).ONKEY=true;
				if(!input.UP || !input.DOWN){
					manager.player.get(PlayerID).x-=speed;
					for(int x=0;x<manager.player.size();x++)if(PlayerID!=x)manager.PlayerXOffset.set(x, manager.PlayerXOffset.get(x)+speed);
				} else {
					manager.player.get(PlayerID).x-=speed/2;
					for(int x=0;x<manager.player.size();x++)if(PlayerID!=x)manager.PlayerXOffset.set(x, manager.PlayerXOffset.get(x)+speed/2);
				}
			}
			if(input.RIGHT==true){
				manager.player.get(PlayerID).KEYUP=false;
				manager.player.get(PlayerID).KEYDOWN=false;
				manager.player.get(PlayerID).KEYLEFT=false;
				manager.player.get(PlayerID).KEYRIGHT=true;
				manager.player.get(PlayerID).ONKEY=true;
				if(!input.UP || !input.DOWN){
					manager.player.get(PlayerID).x+=speed;
					for(int x=0;x<manager.player.size();x++)if(PlayerID!=x)manager.PlayerXOffset.set(x, manager.PlayerXOffset.get(x)-speed);
				} else {
					manager.player.get(PlayerID).x+=speed/2;
					for(int x=0;x<manager.player.size();x++)if(PlayerID!=x)manager.PlayerXOffset.set(x, manager.PlayerXOffset.get(x)-speed/2);
				}
			}
		if(input.UP || input.DOWN || input.LEFT || input.RIGHT){
			try{
				out.writeInt(PlayerID);
				out.writeInt(manager.player.get(PlayerID).x);
				out.writeInt(manager.player.get(PlayerID).y);
				out.writeBoolean(manager.player.get(PlayerID).ONKEY);
				
				out.writeBoolean(manager.player.get(PlayerID).KEYUP);
				out.writeBoolean(manager.player.get(PlayerID).KEYDOWN);
				out.writeBoolean(manager.player.get(PlayerID).KEYLEFT);
				out.writeBoolean(manager.player.get(PlayerID).KEYRIGHT);
			}catch(Exception e){
				
			}
		}
		
		for(int x=0;x<manager.player.size();x++)manager.PlayerX.set( x, manager.player.get(x).x+manager.PlayerXOffset.get(x));
		for(int x=0;x<manager.player.size();x++)manager.PlayerY.set( x, manager.player.get(x).y+manager.PlayerYOffset.get(x));
		
		frame.setTitle("-Mortals Domain ID:"+PlayerID+"    X:"+manager.player.get(PlayerID).x+"  Y:"+manager.player.get(PlayerID).y);
		manager.CheckCollision(mouse.LOOTED);
		manager.Update();
		mouse.LOOTED=false;
	}
	
	public void Render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(3);
		}
		bs = this.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(BackGroundGUI,0,0,null);
		g.drawImage(manager.Manager, 0, 0, null);
		manager.Render(g); 
		manager.RenderGUI(g,InventoryGUI);
		
		g.dispose();
		bs.show();
	}

	public void Init(){
	//	manager.addEnemy(new Enemy(100,260,200));
		//manager.addEnemy(new Enemy(30,235,200));
		//manager.addEnemy(new Enemy(-70,-150,200));
	}
	@Override
	public void run() {
		  long beginLoopTime, endLoopTime, deltaLoop,
		  	desiredFPS = 60,
	   	    desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
		  Init();
	      while(true){
	         beginLoopTime = System.nanoTime();
	         	Render();
	         	Update();
	         endLoopTime = System.nanoTime();
	         deltaLoop = endLoopTime - beginLoopTime;
	           if(deltaLoop < desiredDeltaLoop){
	               try{
	                   Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
	               }catch(InterruptedException e){
	               }
	           }
	      }
	}
	
	public void UpdateChords(int pid , int x, int y, boolean ONKEY, boolean KEYUP, boolean KEYDOWN, boolean KEYLEFT, boolean KEYRIGHT){
		manager.player.get(pid).x=x;
		manager.player.get(pid).y=y;
		
		manager.player.get(pid).ONKEY=ONKEY;
		
		manager.player.get(pid).KEYUP=KEYUP;
		manager.player.get(pid).KEYDOWN=KEYDOWN;
		manager.player.get(pid).KEYLEFT=KEYLEFT;
		manager.player.get(pid).KEYRIGHT=KEYRIGHT;
		
	}
	
	public static void main(String[] args){
		new NewRenderMethod();
	}
}
