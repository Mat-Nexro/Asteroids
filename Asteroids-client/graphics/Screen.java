package graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen {
	
	public final int WIDTH;
	public final int HEIGHT;
	
	private BufferedImage image;
	private int[] pixels;
	
	
	public Screen(int w, int h) {
		WIDTH = w;
		HEIGHT = h;
		//pixels = new int[w * h];
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}
	
	
	
	public void clear(int color) {
		for(int i = 0; i<WIDTH * HEIGHT;i++) {
			pixels[i] = color;
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void renderSprite(int px,int py,Sprite s,Camera c) {
		if(c==null)c = Camera.zero;
		for(int y = 0;y < s.height; y++)
			for(int x = 0;x < s.width;x++) {
				//int col = s.pixels[x + y * s.width];
				//if (col != 0xffff00ff) pixels[px + py * WIDTH] = col;
				pixel(px + x - c.x,py + y - c.y, s.sp.pixels[(s.x + x) + (s.y + y) * s.sp.WIDTH]);
			}
	}
	
	public void renderSprite(int px,int py,int sx,int sy, int size, Spritesheet s,Camera c) {
		if(c==null)c = Camera.zero;
		for(int y = 0;y < size; y++)
			for(int x = 0;x < size;x++) {
				pixel(px + x - c.x,py + y - c.y, s.pixels[sx + x + (sy + y) * s.WIDTH]);
			}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite) {

		for (int y = 0; y < sprite.height; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.width; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= WIDTH || ya < 0 || ya >= HEIGHT) continue;
				//int col = sprite.pixels[x + y * sprite.width];
				//if (col != 0xffff00ff) pixels[xa + ya * WIDTH] = col;
				pixel(xa + x ,ya + y , sprite.sp.pixels[(sprite.x + x) + (sprite.y + y) * sprite.sp.WIDTH]);
				}
		}
	}
	
	public void renderSheet(int xp, int yp, Spritesheet sheet) {


		for (int y = 0; y < sheet.HEIGHT; y++) {

			int ya = y + yp;

			for (int x = 0; x < sheet.WIDTH; x++) {

				int xa = x + xp;

				if (xa < 0 || xa >= WIDTH || ya < 0 || ya >= HEIGHT) continue;

				pixels[xa + ya * WIDTH] = sheet.pixels[x + y * sheet.WIDTH];

			}

		}

	}
	
	public void fcirc(int px,int py,int r,int color) {
		
		for(int y = 0;y < r; y++)
			for(int x = 0;x < r;x++) {
				pixel(x + px,y + py,color);
			}
	}
	
	public void frect(int px,int py,int w,int h,int color) {
		for(int y = 0;y < h; y++)
			for(int x = 0;x < w;x++) {
				pixel(x + px,y + py,color);
			}
	}
	
	public void frect(int px,int py,int w,int h,int color,double angle) {
		for(int y = 0;y < h; y++)
			for(int x = 0;x < w;x++) {
				rotate(pixels,w,h,angle);
				pixel(x + px,y + py,color);
			}
	}
	
	private void pixel(int x,int y,int color) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT || color == 0xFFFF00FF)return;
		pixels[x + y * WIDTH] = color;
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

}
