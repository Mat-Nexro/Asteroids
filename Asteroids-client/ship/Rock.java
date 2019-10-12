package ship;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Rock extends Polygon{
static int xpos=1;
static int ypos=1;
int  xdir,ydir=1;
static int rwidth=26;
static int rheight=31;
public static ArrayList<Rock> r = new ArrayList<Rock>();
int WIDTH = 1024 , HEIGHT = 576;
static int[] polyXArray;
static int[] polyYArray;
public boolean onScreen = true;
public Rock(int[] polyX, int[] polyY, int pointsInPoly, int startXpos, int startYpos){
	 super(polyX, polyY, pointsInPoly);
	 
	 this.xdir = (int) (Math.random() * 4 + 1); //ustawianie losowej predkosci
	 this.ydir= (int) (Math.random() * 4 + 1);
	 this.xpos = startXpos;
	 this.ypos = startYpos;
}

public static Rectangle getRectangle(int xpos,int ypos) {
	return new Rectangle(xpos,ypos,rwidth,rheight);
}

public static int[] getXarray(int startXPos){
	int[] tempX = {0,5,1,8,13,20,31,28,31,22,16,7,0}; // ustawianie punktów wielokata
      for (int i = 0; i < tempX.length; i++) {
          tempX[i] += startXPos;
      }
      return tempX;

  }
public static int[] getYarray(int startYPos){
	int[] tempY =  {10,17,26,34,27,36,26,14,8,1,5,1,10};
      for (int i = 0; i < tempY.length; i++) {
          tempY[i] += startYPos;
      }
      return tempY;

  }

    







}
