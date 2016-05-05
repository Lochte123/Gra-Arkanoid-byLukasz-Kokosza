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

public class Klient 
{
	static Socket socket;
	static DataInputStream streamIn;
	
	public static void main(String[] args) throws Exception
	{
		String test;
		int port = 7777;
		System.out.println("Trwa ³¹czenie z serwerem ...");
		socket = new Socket("localhost",port);
		System.out.println("Po³¹czono!");
		streamIn = new DataInputStream(socket.getInputStream());
		System.out.println("Odbieranie wiadomoœci ...");
		test = streamIn.readUTF();
		System.out.println("Wiadomosc od serwera: " + test);
	}
}
