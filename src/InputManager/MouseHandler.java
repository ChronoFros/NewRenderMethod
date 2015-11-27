package InputManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener,MouseMotionListener{

	public int x,y;
	public boolean LOOTED;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getX() > 468 && e.getX() < 606){
			if(e.getY() > 424 && e.getY() < 476){
				LOOTED=true;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x=e.getX();
		y=e.getY();
	}

}
