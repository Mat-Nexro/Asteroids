package Main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Main.UDPServer;

public class Main {

	private static String adres;
	private static UDPServer udpServer;
	public static JFrame frame;
	public static JLabel info;
	public static String ip;
	
	public static void main(String[] args){
		
	    try {
	       Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
	        while (interfaces.hasMoreElements()) {
	            NetworkInterface iface = interfaces.nextElement();
	            // filters out 127.0.0.1 and inactive interfaces
	            if (iface.isLoopback() || !iface.isUp())
	                continue;

	            Enumeration<InetAddress> addresses = iface.getInetAddresses();
	            while(addresses.hasMoreElements()) {
	                InetAddress addr = addresses.nextElement();
	                ip = addr.getHostAddress();
	                //System.out.println(iface.getDisplayName() + " " + ip);
	            }
	        }
	    } catch (SocketException e) {
	        throw new RuntimeException(e);
	    }
		
		info = new JLabel("Server jest włączony:\r\n"+ip);
		frame = new JFrame();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(400,200));
		frame.setMinimumSize(new Dimension(400,200));
		frame.setMaximumSize(new Dimension(400,200));
		frame.add(info);

		udpServer = new UDPServer();
		udpServer.start();

	}

}
