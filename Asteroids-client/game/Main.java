package game;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ship.Rock;
import ship.Ship;
import game.gamestates.GS_Game;
import graphics.Screen;
import input.Keyboard;
import input.Mouse;
import server.UDPClient;
//import server.UDPServer;

public class Main  extends Canvas implements Runnable{
	public static boolean RUN = false;
	public static int points = 0;
	public static final String TITLE = "Asteroids";
	public static final int WIDTH = 1024 , HEIGHT = 576;
	private static final int FRAMERATE = 60;
	public static String s = "";
	private boolean RUNNING = false;
	private JFrame frame;
	private static JFrame frame2;
	private static JLabel label;
	private int time=0;
	private static JButton button;
	private static JSlider slider;
	private static JTextField text;
	private static String ip;
	
	private static int la;
	private Screen screen;
	private Keyboard keyboard = new Keyboard();
	private Mouse mouse = new Mouse();
	private GameStateManager gsm;
	Graphics g;
	public static ArrayList<Rock> r = new ArrayList<Rock>();
	public static Rock[] ro;
	public static Rectangle[] poly;
	public static boolean[] onscreen;
	private Ship ship;
	
	//private UDPServer udpServer;
	private UDPClient  udpClient;
	public Main() {
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setMinimumSize(new Dimension(WIDTH,HEIGHT));
		setMaximumSize(new Dimension(WIDTH,HEIGHT));
		
		onscreen = new boolean[la];
		ro = new Rock[la];
		poly = new Rectangle[la];
		
		for(int i=0;i<la;i++)
		onscreen[i] = true;
		
		for(int i =0;i<la;i++)
			s += "true;";
			
		frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this, new BorderLayout().CENTER);
		
		addKeyListener(keyboard);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		int i = 32;
		screen = new Screen(16 * i,9 * i);
		gsm = new GameStateManager();
		
		
	}
	
	private void start() {
		if(RUNNING)return;
		RUNNING=true;
		
		
	//if(JOptionPane.showConfirmDialog(this, "Chcesz włączyć server")==0) {
		//udpServer = new UDPServer(this);
		//udpServer.start();
	//}
		udpClient = new UDPClient(ip);
		udpClient.start();
		new Thread(this,"Game" + TITLE).start();
		
	}
	
	private void stop() {
		if(!RUNNING)return;
		RUNNING=false;
		frame.dispose();
		System.exit(0);
	}
	
	private double timer = System.currentTimeMillis();
	private int FPS = 0;
	private int UPS = 0;
	private double delta;
	private double frametime = 1000000000 / FRAMERATE;
	private long timeNOW = System.nanoTime();
	private long timeLAST = System.nanoTime();
	
	public void run() {
		
		while(RUNNING) {
			timeNOW = System.nanoTime();
			delta += (timeNOW - timeLAST) / frametime;
			timeLAST = timeNOW;
			
			while(delta >= 1) {
				update();
				delta -= 1;
				UPS++;
			}
			
			render();
			FPS++;
			
			if(System.currentTimeMillis() - timer >= 1000) {
				timer = System.currentTimeMillis();
				frame.setTitle(TITLE + "---FPS: " + FPS + "---points:"+points);
				FPS = 0;
				UPS = 0;
			}
		}
		stop();
	}
	
	private void update() {
		keyboard.update();
		gsm.update();
		Mouse.update();
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		 g = bs.getDrawGraphics();
		
		screen.clear(0x66);
		gsm.render(screen);
		
		
		g.drawImage(screen.getImage(),0,0,WIDTH,HEIGHT,null);
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint( Color.WHITE);
		
		String poz2 = UDPClient.message2.trim();
		String poz4 = UDPClient.message4.trim();
		
		String[] splitedArray = null;
		splitedArray = poz2.split(";");
		
		String[] splitedArray2 = null;
		splitedArray2 = poz4.split(";");
		
		for(int i = 0; i<la; i++)
			onscreen[i]=Boolean.parseBoolean(splitedArray2[i]);
		
		
			for(int i = 0; i<la; i++) {
				ro[i] = new Rock(Rock.getXarray(Integer.parseInt(splitedArray[i])),Rock.getYarray(Integer.parseInt(splitedArray[i+1])),13,Integer.parseInt(splitedArray[i]),Integer.parseInt(splitedArray[i]));
				
				if(onscreen[i]==true)
				g2.draw(ro[i]);
		
				poly[i] = new Rectangle(Integer.parseInt(splitedArray[i]),Integer.parseInt(splitedArray[i+1]),26,31);
				
				Rectangle shootBox = Ship.rectshoot;
				Rectangle shipBox = Ship.rectship;
						g2.draw(shipBox);
						g2.draw(shootBox);
						//g2.draw(poly[i]);
				
			if(shootBox.intersects(poly[i])){
				//points++;
				 onscreen[i]=false;
				 points = 0;
				 for(int a = 0; a<la; a++)
				 points += !onscreen[a] ? 1 : 0;
				 }
			
			if(time>200) {
			if(shipBox.intersects(poly[i]) && onscreen[i]){
				System.out.println("Koniec gry");
				System.exit(0);
			}
			
			}
			}
			
		g.dispose();
		bs.show();
		
		s = "";
		for(int i = 0; i<la; i++)
		s += ""+onscreen[i]+";"; 
		time++;
		//if(points == la) la++;
		
		//System.out.println(s);
	}

	
	public static void main(String[] args) {
		
		frame2 = new JFrame(TITLE);
		button = new JButton("Start gry");
		text = new JTextField("10.68.20.204");
		ip = text.getText();
		slider = new JSlider(JSlider.HORIZONTAL,2,19,2);
		label = new JLabel("Asteroids:"+slider.getValue());
		frame2.add(slider,BorderLayout.NORTH);
		frame2.add(label,BorderLayout.WEST);
		frame2.add(text,BorderLayout.EAST);
		frame2.add(button,BorderLayout.SOUTH);
		frame2.setPreferredSize(new Dimension(200,200));
		frame2.setMinimumSize(new Dimension(200,200));
		frame2.setMaximumSize(new Dimension(200,200));
		frame2.setLocationRelativeTo(null);
		frame2.setResizable(false);
		frame2.setVisible(true);
		
		slider.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e) {
		    la = slider.getValue();
		    label.setText("Asteroids:"+slider.getValue());
		}
		}
		);
		
		button.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		    	new Main().start();
		    	frame2.setVisible(false);
	    }  
	    });
		
			
		
	}

}
