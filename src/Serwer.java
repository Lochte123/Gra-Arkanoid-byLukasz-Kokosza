import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.lang.*;
import org.sqlite.*;
import java.sql.*;
import java.net.*;

public class Serwer extends Thread
{
	DatagramSocket socket;
	Gra gra;
	int port = 1331;
	
	public Serwer(Gra gra)
	{
		this.gra = gra;
		
		try
		{
			this.socket = new DatagramSocket(port);
		}
		catch(SocketException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(true)
		{
			byte[] dane = new byte[1024];//tablica odczytywanych danych z klienta
			DatagramPacket packet = new DatagramPacket(dane,dane.length);
			try
			{
				socket.receive(packet);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			String powiadomienie = new String(packet.getData());
			System.out.println("Klient: " + " adres IP = " + packet.getAddress().getHostAddress() + " port = " + packet.getPort());
			
			if(powiadomienie.trim().equalsIgnoreCase("ping")) 
			{
				wyslijDane("Wyslane ".getBytes(), packet.getAddress(), packet.getPort());
			}
		}
	}
	
	public void wyslijDane(byte[] dane, InetAddress adresIP, int port)
	{
		DatagramPacket packet = new DatagramPacket(dane,dane.length, adresIP, port);
		try
		{
			socket.send(packet);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
