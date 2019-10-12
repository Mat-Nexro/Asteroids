package game;

import game.gamestates.GS_Game;
import graphics.Screen;

public class GameStateManager {

	public static final int GAME_STATE_NGAME = -2;
	public static final int GAME_STATE_EXIT = -1;
	public static final int GAME_STATE_MENU = 0;
	public static final int GAME_STATE_GAME = 1;
	public static final int GAME_STATE_EQ = 2;
	
	private static GameState gs;
	public static boolean exit = false;
	
	static GS_Game game = new GS_Game();
	
	public static void ChangeGameState(int ID) {
		if(ID == GAME_STATE_NGAME)gs = new GS_Game();
		if(ID == GAME_STATE_GAME)gs = game;
	}
	
	public GameStateManager() {
		ChangeGameState(GAME_STATE_NGAME);
	}
	
	public void update() {
		gs.update();
	}
	
	public void render(Screen s) {
		gs.render(s);
	}
	
}
