package Main;

import java.awt.Rectangle;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;




public class UDPServer extends Thread{
	

private DatagramSocket socket;
private DatagramSocket socket2;
private DatagramSocket socket3;
private DatagramSocket socket4;
private Main game;
private float x = 128;
private float y = 264; 
private int yp = (int) y;
private int xp = 0;
int a[][];
String shoot = "np";
public static ArrayList<Rock> r = new ArrayList<Rock>();
public Rectangle  rectship = new Rectangle((int)x, (int)y, 16, 16);
public boolean RUN = false;

public UDPServer() {
	try {
		this.socket = new DatagramSocket(1331); //tworzenie gniazda na podanym porcie
		this.socket2 = new DatagramSocket(1332);
		this.socket3 = new DatagramSocket(1333);
		this.socket4 = new DatagramSocket(8000);
		
	}catch(SocketException e){
		e.printStackTrace();
	}
	createRock();
}
public void run() {
	while(true) {
		byte[] data = new byte[1024]; //tworznie tablicy bajtow na pakiet
		byte[] data2 = new byte[1024];
		byte[] data3 = new byte[1024];
		byte[] data4 = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length); // pozycja statku
		DatagramPacket packet2 = new DatagramPacket(data2, data2.length);// pozycja asteroid
		DatagramPacket packet3 = new DatagramPacket(data3, data3.length);// pozycja strzalu
		DatagramPacket packet4 = new DatagramPacket(data4, data4.length);
		try {
			socket.receive(packet); //oczekiwanie na odpowiedz klienta
			socket2.receive(packet2);
			socket3.receive(packet3);
			socket4.receive(packet4);
		}catch(IOException e) {
			e.printStackTrace();
		}
		//xp = (int) x;
		yp = (int) y;
		
		String message = new String(packet.getData());// rzutowanie otrzymanych danych na string
		//String message2 = new String(packet2.getData());
		//String message3 = new String(packet3.getData());
		String message4 = new String(packet4.getData());
		//System.out.println(message4);
		
		
		String[] splitedArray = null;
		splitedArray = message.split(";");
		rectship = new Rectangle((int)x, (int)y, 16, 16);
		String s = "";
		String dx = String.valueOf(x);
		String dy = String.valueOf(y);
		shoot = "np";
		for(Rock rock : r){
			//s = "";
            rock.move(this);
            s += String.valueOf(rock.GetX())+";"+String.valueOf(rock.GetY())+";";
		}
		sendData(s.getBytes(), packet.getAddress(),packet2.getPort());
		
		if(splitedArray[0].trim().equals("68"))
		{
			dx = String.valueOf(x);
			dy = String.valueOf(y);
			x++;
		}
		if(splitedArray[0].trim().equals("83"))
		{
			dx = String.valueOf(x);
			dy = String.valueOf(y);
			y++;
		}
		if(splitedArray[0].trim().equals("65"))
		{
			dx = String.valueOf(x);
			dy = String.valueOf(y);
			x--;
		}
		if(splitedArray[0].trim().equals("87"))
		{
			dx = String.valueOf(x);
			dy = String.valueOf(y);
			y--;
		}
		if(splitedArray[0].trim().equals("32"))
		{
			shoot = ""+yp;
			sendData(shoot.getBytes(), packet3.getAddress(),packet3.getPort());
		}
		
		//System.out.println(shoot+";"+yp);
		sendData(shoot.getBytes(), packet3.getAddress(),packet3.getPort());
		String dat = dx + ";" + dy;
		String dat2 = message4;
		sendData(dat.getBytes(), packet.getAddress(),packet.getPort());
		sendData(dat2.getBytes(), packet4.getAddress(),packet4.getPort());
		
	}
}public void sendData(byte[] data, InetAddress ipAddress,int port) { // funkcja wysylajaca dane
	DatagramPacket packet = new DatagramPacket(data, data.length,ipAddress,port);
	try {
		this.socket.send(packet);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}}
public void sendData(byte[] data, byte[] data2,InetAddress ipAddress,int port,int port2) { // funkcja wysylajaca dane
	DatagramPacket packet = new DatagramPacket(data, data.length,ipAddress,port);
	DatagramPacket packet2 = new DatagramPacket(data2, data2.length,ipAddress,port2);
	try {
		this.socket.send(packet);
		this.socket2.send(packet2);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}}
public void createRock(){
    for(int i = 0; i < 10; i++){
        int randomStartXPos = (int) (Math.random() * (1024 - 60) + 1);
        int randomStartYPos = (int) (Math.random() * (576- 60) + 1);
        
        r.add(new Rock(Rock.getXarray(randomStartXPos), Rock.getYarray(randomStartYPos), 13, randomStartXPos, randomStartYPos));
        Rock.r=r;
    }
}


    
}