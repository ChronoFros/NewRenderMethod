package NewRenderMethod;

import java.awt.image.*;
import DisplayManager.*;
import InputManager.*;
import javax.swing.*;
import SpriteSheet.*;
import java.awt.*;
import Entity.*;

public class NewRenderMethod extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	public static int Width = 618, Height = 502,
					  xx = -1, yy = 0, PlayerID=0;
	
	private KeyHandler input = new KeyHandler();
	private MouseHandler mouse =  new MouseHandler();;
	private DisplayManager manager = new DisplayManager(449,Height);

	public Thread thread = new Thread(this);
	private Loader loader = new Loader();
	private JFrame frame = new JFrame();
	
	private Map map = new Map(Width,Height,manager);
	private Player player = new Player(225, 206);
	
	private BufferedImage BackGroundGUI = loader.Load(0, 0, Width, Height, "SkyCloud.png");
	private BufferedImage InventoryGUI = loader.Load(0, 0, 612, 473, "Stats.png");
	
	public NewRenderMethod(){

		manager.addMap(map);
		manager.addPlayer(player);

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
		frame.setTitle("-Mortals Domain");
		manager.CheckCollision(mouse.LOOTED);
		manager.Update(manager.xOffset, manager.yOffset);
		manager.MovePlayer(4, manager.xOffset, manager.yOffset,
				1054,
				808,
				mouse.x, mouse.y,
				input.UP, input.DOWN, input.LEFT,
				input.RIGHT, input.ONKEY,
				input.SPECIAL);
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
		g.drawImage(manager.Manager, manager.xxOffset, manager.yyOffset, null);
		manager.Render(g); 
		manager.RenderGUI(g,PlayerID,InventoryGUI);
		
		g.dispose();
		bs.show();
	}

	public void Init(){
		manager.addEnemy(new Enemy(100,260,200));
		manager.addEnemy(new Enemy(30,235,200));
		manager.addEnemy(new Enemy(-70,-150,200));
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
	
	public static void main(String[] args){
		new NewRenderMethod();
	}
}
