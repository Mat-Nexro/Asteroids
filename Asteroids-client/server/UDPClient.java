package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import game.Main;
import java.net.UnknownHostException;
public class UDPClient extends Thread{
	
private static InetAddress ipAddress;
private static DatagramSocket socket;
private static DatagramSocket socket2;
private static DatagramSocket socket3;
private static DatagramSocket socket4;
public static String message = "64.0;64.0";
public static String message2 = "10;5;20;60;100;54;87;23;86;32;10;5;20;60;100;54;87;23;86;32";
public static String message3 = "np";
public static String message4 = "true;true;true;true;true";
public static String ipClient;


public UDPClient(String ipAddress) { //tworzymy gniazdo z podanym ip
	
	InetAddress addr = null;
	try {
		addr = InetAddress.getLocalHost();
	} catch (UnknownHostException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	ipClient = addr.getHostAddress();
	
	try {
		this.socket = new DatagramSocket(); //tworzenie gniazda (reprezentuje polaczenie UDP)
		this.socket2 = new DatagramSocket();//tworzenie gniazda (reprezentuje polaczenie UDP)
		this.socket3 = new DatagramSocket();
		this.socket4 = new DatagramSocket();
	} catch (SocketException e) {
		e.printStackTrace();
	}
	try {
		this.ipAddress = InetAddress.getByName(ipAddress); // ustawiamy ip na podane w konstruktorze
	} catch (UnknownHostException e) {
		e.printStackTrace();
	}
}
public void run() {
	while(true){
		byte[] data = new byte[1024]; // tworzenie tablicy na dane
		byte[] data2 = new byte[1024];
		byte[] data3 = new byte[1024];
		byte[] data4 = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length); // tworzenie pakietu z tablicy na dane
		DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
		DatagramPacket packet3 = new DatagramPacket(data3, data3.length);
		DatagramPacket packet4 = new DatagramPacket(data4, data4.length);
		try {
			socket.receive(packet); // oczekiwanie na dane z serwera
			socket2.receive(packet2);
			socket3.receive(packet3);
			socket4.receive(packet4);
			//System.out.println("k");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	//	this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		message = new String(packet.getData()); // rzutowanie pakietu na string
		message2 = new String(packet2.getData());
		message3 = new String(packet3.getData());
		message4 = new String(packet4.getData());
		//System.out.println("SERVER > " + message);
	}
}public static void sendData(byte[] data){ //funkcja wysylajaca dane
	DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331); // wcisniety klawisz
	DatagramPacket packet2 = new DatagramPacket(data, data.length, ipAddress, 1332);
	DatagramPacket packet3 = new DatagramPacket(data, data.length, ipAddress, 1333);
	try {
		socket.send(packet);
		socket2.send(packet2);
		socket3.send(packet3);
	} catch (IOException e) {
		e.printStackTrace();
	}
}
public static void sendData4(byte[] data){ //funkcja wysylajaca dane
	DatagramPacket packet4 = new DatagramPacket(data, data.length, ipAddress, 8000);
	try {
		socket4.send(packet4);
	} catch (IOException e) {
		e.printStackTrace();
	}
}
    
}
