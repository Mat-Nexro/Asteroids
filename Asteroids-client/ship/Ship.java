package ship;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.net.DatagramPacket;

import game.math.Vec2;
import graphics.Camera;
import graphics.Screen;
import graphics.Sprite;
import graphics.Spritesheet;
import input.Keyboard;
import server.UDPClient;
import game.Main;

public class Ship {
	
	public Vec2 pos;
	public float ac;
	public float speed;
	public double angle;
	public float t;
	public int dir;
	public float vmax;
	public float vx,vy;
	public Vec2 v = new Vec2(vx,vy);
	public static Rectangle rectship,rectshoot;
	public int xp,yp;
	
	public Ship(Vec2 pos) {
		this.pos = pos;
		ac = 1.7f;
		t = 0f;
		vmax = 5f;
		v.x = 0;
		v.y = 0;
		angle = 0;
	}
	int data = 0;
	
	public void update() {
		String dat = String.valueOf(data);
		dat = "";
		/*if(Keyboard.getKey(KeyEvent.VK_W)) {
			data = KeyEvent.VK_W;
			dat = String.valueOf(data);
		}
		
		if(Keyboard.getKey(KeyEvent.VK_S )) {
			data = KeyEvent.VK_S;
			dat = String.valueOf(data);
		}*/
		
		if(Keyboard.getKey(KeyEvent.VK_A)) {
			data = KeyEvent.VK_A;
			dat = String.valueOf(data);
		}
		
		if(Keyboard.getKey(KeyEvent.VK_D)) {
			data = KeyEvent.VK_D;
			dat = String.valueOf(data);
		}
		
		if(Keyboard.getKey(KeyEvent.VK_SPACE)) {
			data = KeyEvent.VK_SPACE;
			dat = String.valueOf(data);
		}
		
		//Sprite.rotate(Sprite.ship, angle);
		UDPClient.sendData4(Main.s.getBytes());
		UDPClient.sendData(dat.getBytes());
		}
		
	public void move() {
		
		}
	
	public void render(Screen s,Camera c) {
		
		String poz = UDPClient.message;
		String poz3 = UDPClient.message3.trim();
		
		String[] splitedArray = null;
		splitedArray = poz.split(";");
		
		float x = Float.parseFloat(splitedArray [0]);
		float y = Float.parseFloat(splitedArray [1]);
		
		rectshoot = new Rectangle(2*((int)x+5), 2*(int)y, 0, 0);
		//System.out.println(poz3);
		s.frect((int)x, (int)y, 16, 16, 0xfff000ff, angle);
		rectship = new Rectangle(2*(int)x, 2*(int)y, 2*16, 2*16);
		
		if(!poz3.equals("np")) {
			//String y = yp.trim();
			yp = Integer.parseInt(poz3.trim());
			rectshoot = new Rectangle(2*((int)x+5), 0, 2*5, 2*yp);
			while(yp>0) {
				s.frect((int)x+5, yp-5, 5, 5, 0xff0000, angle);
				yp--;
			}
		}
		
		
	}
	
	public Rectangle getRecktField() { //zwraca kwadrat okrslajace granice statku , dla sprawdzenie kolizji
		return rectship;
		} 
	
}
