package Main;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Rock extends Polygon{
int xpos,ypos;
int xdir,ydir=1;
int rwidth=26;
int rheight=31;
public static ArrayList<Rock> r = new ArrayList<Rock>();
int WIDTH = 1024 , HEIGHT = 576;
static int[][] polyXArray;
static int[][] polyYArray;
public boolean onScreen = true;
public Rock(int[] polyX, int[] polyY, int pointsInPoly, int startXpos, int startYpos){
	 super(polyX, polyY, pointsInPoly);
	 
	 this.xdir = (int) (Math.random() * 4 + 1); //ustawianie losowej predkosci
	 this.ydir= (int) (Math.random() * 4 + 1);
	 this.xpos = startXpos;
	 this.ypos = startYpos;
}

public Rectangle getPolyField() { //zwraca kwadrat otaczaący asteroide , dla sprawdzenie kolizji
			return new Rectangle(super.xpoints[0], super.ypoints[0], rwidth, rheight);
		}

public void move(UDPServer ship){
	Rectangle checkPoly = this.getPolyField();
	for(Rock rock : r){
		if(rock.onScreen){
			Rectangle tempcheckPoly = this.getPolyField();
			
			if(rock != this && tempcheckPoly.intersects(checkPoly)){
				//zmiana kierunku gdy asteroidy sie zderza
			/*int tempXDirection = this.xdir;
			  int tempYDirection = this.ydir;
				this.xdir = rock.xdir;
				this.ydir = rock.ydir;
				rock.xdir = tempXDirection;
			  rock.ydir = tempYDirection;*/
				
				

		}
			/*Rectangle shipBox = ship.rectship;
			 if(tempcheckPoly.intersects(shipBox)){
				 System.out.println("Koniec gry !!");

			 }*/

	}
	}
       int XPos = super.xpoints[0];

       int YPos = super.ypoints[0];
//jesli asteroidy uderzą ściene z okienka to zmieniaja kierunek na przeciwny
       if (XPos < 0 || (XPos+26) > WIDTH) xdir = -xdir;

       if (YPos < 0 || (YPos+31) > HEIGHT) ydir = -ydir;
       
       //if((YPos + 20) > HEIGHT)  YPos = 0;

  for (int i = 0; i < super.xpoints.length; i++){

           super.xpoints[i] += xdir;

           super.ypoints[i] += ydir;
       }
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

public int[][] GetXarr(int j) {
		for (int i = 0; i < super.xpoints.length; i++){
			polyXArray[j][i] = super.xpoints[i];
	}
	return polyXArray;
}

public int[][] GetYarr(int j) {
		for (int i = 0; i < super.ypoints.length; i++){
			polyYArray[j][i] = super.ypoints[i];
	}
	return polyYArray;
}

public int GetX() {
	return super.xpoints[0];
}
public int GetY() {
	return super.ypoints[0];
}

}
