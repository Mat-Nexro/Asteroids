package game.gamestates;

import java.awt.event.KeyEvent;

import game.GameState;
import game.math.Vec2;
import graphics.Camera;
import graphics.Screen;
import ship.Ship;

public class GS_Game extends GameState{

	public Ship ship;
	public Camera camera;
	float speed = (float) 1.75;
	
	public GS_Game() {
		camera = new Camera(0,0);
		ship = new Ship(new Vec2(64.0f,64.0f));
	}
	
	public void update() {
		ship.update();
	}
	
	public void render(Screen s) {
		ship.render(s,camera);
	}
	
}
