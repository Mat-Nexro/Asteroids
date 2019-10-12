package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	public static final Spritesheet ship = new Spritesheet("/Dwarf.png");
	
	public int WIDTH, HEIGHT;
	private int width, height;
	public int[] pixels;
	
	public Spritesheet(String path) {
		try {
			BufferedImage image = ImageIO.read(Spritesheet.class.getResource(path));
			WIDTH = image.getWidth();
			HEIGHT = image.getHeight();
			pixels = new int[WIDTH * HEIGHT];
			
			image.getRGB(0,0,WIDTH,HEIGHT,pixels,0,WIDTH);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Spritesheet(String path, int size) {
		WIDTH = size;
		HEIGHT = size;
		pixels = new int[WIDTH*HEIGHT];
		load(path);
	}
	
	public Spritesheet(String path, int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[WIDTH*HEIGHT];
		load(path);
	}
	
	private void load(String path) {
		try {
			System.out.println("Tying to load: "+ path);
			BufferedImage image = ImageIO.read(Spritesheet.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width*height];
			image.getRGB(0,0,width,height,pixels,0,width);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
