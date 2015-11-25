package InputManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import NewRenderMethod.NewRenderMethod;

public class KeyHandler implements KeyListener{
	
	public boolean UP,DOWN,LEFT,RIGHT,ONKEY,SPECIAL;
	
	@Override
	public void keyPressed(KeyEvent e) {
		setKEY(e,true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		setKEY(e,false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void setKEY(KeyEvent e,boolean KeyState){
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
			UP=KeyState;
			ONKEY=KeyState;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
			DOWN=KeyState;
			ONKEY=KeyState;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
			LEFT=KeyState;
			ONKEY=KeyState;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
			RIGHT=KeyState;
			ONKEY=KeyState;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE ){
			SPECIAL=KeyState;
		}
	}
}
