package graphics;

public class Sprite {
	public final int SIZE;
	public int x,y,width,height;
	public Spritesheet sp;
	public int[] pixels;
	
	public static Sprite ship = new Sprite(0,0,32,Spritesheet.ship);
	
	public Sprite(int x,int y,int width,int height,Spritesheet sp) {
		SIZE = (width == height) ? width : -1;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sp = sp;
	}
	
	public Sprite(int x,int y,int size,Spritesheet sp) {
		SIZE = size;
		this.x = x;
		this.y = y;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.sp = sp;
	}
	
	public Sprite(int[] pixels, int width, int height, Spritesheet sp) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.sp = sp;
		this.pixels = new int[pixels.length];
		for(int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}
	
	public static Sprite rotate(Sprite sprite, double angle){
		return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height, sprite.sp);
	}
	
	private static int[] rotate(int[] pixels, int width, int height, double angle) {
		int [] result = new int[width * height];
		
		double nx_x = rot_x(-angle, 1.0, 0.0);
		double nx_y = rot_y(-angle, 1.0, 0.0);
		double ny_x = rot_x(-angle, 0.0, 1.0);
		double ny_y = rot_y(-angle, 0.0, 1.0);
		
		double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rot_x(-angle, -width / 2.0, -height / 2.0) + height / 2.0;
		
		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < height; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int col = 0;
				if(xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xffff00ff;
				result[x + y * width] = col;
				x1 += nx_x;
				y1 += nx_y;
			}
			x0 += ny_x;
			y0 += ny_y;
		}
		return result;
	}
	
	private static double rot_x(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * cos + y * -sin;
	}
	
	private static double rot_y(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * sin + y * cos;
	}
//	public static Sprite[] split(Spritesheet sp) {
//		Sprite sprites;
//		
//		return sprites;
//	}
}
