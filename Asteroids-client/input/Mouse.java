package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import game.Main;

public class Mouse implements MouseListener, MouseMotionListener{

	public static int X,Y,Xpixel,Ypixel;
	public static boolean button_left, button_right, button_clicked_left, button_clicked_right;
	private static boolean b1 = false;
	private static boolean b2 = false;
	
	public static void update() {
		if(!b1 && button_left) {
			b1 = true;
			button_clicked_left = true;
		}
		else{
			button_clicked_left = false;
		}
		
		if(!button_left) {
			b1 = false;
		}
		
		if(!b2 && button_right) {
			b2 = true;
			button_clicked_right = true;
		}
		else{
			button_clicked_right = false;
		}
		
		if(!button_right) {
			b2 = false;
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		X = e.getX();
		Y = e.getY();
		Xpixel = (int)((float)X*((16.0f*32.0f)/(float)Main.WIDTH));
		Ypixel = (int)((float)Y*((9.0f*32.0f)/(float)Main.HEIGHT));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseMoved(e);
		
		if(e.getButton() == e.BUTTON1) { //LPM
			button_clicked_left=true;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseMoved(e);
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseMoved(e);
		
		if(e.getButton() == e.BUTTON1) { //LPM
			button_left=true;
		}
		
		if(e.getButton() == e.BUTTON2) { //SCROLL
			
		}
		
		if(e.getButton() == e.BUTTON3) { //PPM
			button_right=true;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseMoved(e);
		
		if(e.getButton() == e.BUTTON1) { //LPM
			button_left=false;
		}
		
		if(e.getButton() == e.BUTTON2) { //SCROLL
			
		}
		
		if(e.getButton() == e.BUTTON3) { //PPM
			button_right=false;
		}
		
	}

}
