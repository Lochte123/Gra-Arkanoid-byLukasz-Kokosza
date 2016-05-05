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
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.lang.*;
import org.sqlite.*;
import java.sql.*;
import java.net.*;

public class Main extends JFrame//klasa z samym Mainem
{
	public static Baza_danych bd;
	public static Gra gra;
	public static JFrame frame, poziomy;
	public static final int szerokoscP = 700;
	public static final int wysokoscP = 780;
	public static JButton button1, button2, button3, button4;
	public static JRadioButton latwy, trudny;
	public static JPanel panel, panel2;
	public static Kulka kulka;
	public static JLabel wynik = new JLabel("POZOSTALO SZANS: 3");
	public static JLabel bonusy = new JLabel("BONUSY: ");
	public static JLabel bonusZielony = new JLabel("Zielony +1 szansa");
	public static JLabel bonusCzerwony = new JLabel("Czerwony -1 szansa");
	public static JLabel nowaKulka = new JLabel("Pomaranczowy kulka Duch");
	public static JLabel predkoscKPlus = new JLabel("ØÛ≥ty predkoscUP");
	public static JLabel predkoscKMinus = new JLabel("Szary predkoscDOWN");
	public static JLabel liczPunkty = new JLabel("LICZBA PUNKT”W: ");
	public static JLabel poziom = new JLabel("WYBIERZ POZIOM: ");
	public static JLabel info = new JLabel("");
	static ImageIcon tlo;
	static JLabel label;
	static boolean startuj = false;
	public static String baza;
	public static ServerSocket serwer;
	public static Socket socket;
	public static DataOutputStream streamOut;
	
	public static void main(String[] args) throws Exception// wyjatek dla socketow
	{
		//
		//GUI
		//
		button1 = new JButton("START");
		button2 = new JButton("ZAMKNIJ");
		button3 = new JButton("WY£•CZ DèWI KI");
		button4 = new JButton("OK");
		latwy = new JRadioButton("£ATWY");
		trudny = new JRadioButton("TRUDNY");
		tlo = new ImageIcon("tlo.jpg");
		label = new JLabel();
		label.setIcon(tlo);
			
		button1.setPreferredSize(new Dimension(160, 60));
		button2.setPreferredSize(new Dimension(160, 60));
		button3.setPreferredSize(new Dimension(160, 60));
		button4.setPreferredSize(new Dimension(160, 60));
		
		button1.setBackground(Color.WHITE);
		button1.setForeground(Color.BLACK);
		button2.setBackground(Color.BLACK);
		button2.setForeground(Color.WHITE);
		button3.setBackground(Color.GRAY);
		button3.setForeground(Color.BLACK);
		latwy.setBackground(new Color(50,200,50));
		trudny.setBackground(new Color(200,50,50));
		
		
		button1.setFont(new Font("Arial",Font.TYPE1_FONT, 17));
		button2.setFont(new Font("Arial",Font.TYPE1_FONT, 17));
		button3.setFont(new Font("Arial",Font.TYPE1_FONT, 15));
		button4.setFont(new Font("Arial",Font.TYPE1_FONT, 15));
		latwy.setFont(new Font("Arial",Font.TYPE1_FONT, 12));
		trudny.setFont(new Font("Arial",Font.TYPE1_FONT, 12));
		wynik.setFont(new Font("Arial",Font.PLAIN, 30));
		
		bonusZielony.setFont(new Font("Arial",Font.TYPE1_FONT,12));
		bonusZielony.setForeground(Color.GREEN);
		bonusCzerwony.setFont(new Font("Arial",Font.TYPE1_FONT,12));
		bonusCzerwony.setForeground(Color.RED);
		nowaKulka.setFont(new Font("Arial",Font.TYPE1_FONT,12));
		nowaKulka.setForeground(Color.ORANGE);
		predkoscKPlus.setFont(new Font("Arial", Font.TYPE1_FONT, 12));
		predkoscKPlus.setForeground(Color.YELLOW);
		predkoscKMinus.setFont(new Font("Arial", Font.TYPE1_FONT, 12));
		predkoscKMinus.setForeground(Color.GRAY);
		bonusy.setFont(new Font("Arial", Font.TYPE1_FONT, 18));
		bonusy.setForeground(Color.BLACK);
		liczPunkty.setFont(new Font("Arial", Font.TYPE1_FONT, 14));
		liczPunkty.setForeground(Color.MAGENTA);
		poziom.setFont(new Font("Arial", Font.TYPE1_FONT, 16));
		poziom.setForeground(Color.WHITE);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(193,100));
		panel.setBackground(new Color(0,0,0,215));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(wynik);
		panel.add(Box.createRigidArea(new Dimension(55,25)));
		panel.add(button1);
		panel.add(Box.createRigidArea(new Dimension(55,25)));
		panel.add(button3);
		panel.add(Box.createRigidArea(new Dimension(55,25)));
		panel.add(button2);
		panel.add(Box.createRigidArea(new Dimension(55,60)));
		panel.add(bonusy);
		panel.add(Box.createRigidArea(new Dimension(55,25)));
		panel.add(bonusZielony);
		panel.add(Box.createRigidArea(new Dimension(55,15)));
		panel.add(bonusCzerwony);
		panel.add(Box.createRigidArea(new Dimension(55,15)));
		panel.add(nowaKulka);
		panel.add(Box.createRigidArea(new Dimension(55,15)));
		panel.add(predkoscKPlus);
		panel.add(Box.createRigidArea(new Dimension(55,15)));
		panel.add(predkoscKMinus);
		panel.add(Box.createRigidArea(new Dimension(55,30)));
		panel.add(liczPunkty);
		panel.add(Box.createRigidArea(new Dimension(55,55)));
		panel.add(poziom);
		panel.add(Box.createRigidArea(new Dimension(55,25)));
		panel.add(latwy);
		panel.add(Box.createRigidArea(new Dimension(55,25)));
		panel.add(trudny);
		
		
		frame = new JFrame("Arkanoid_by_Lukasz_Kokosza");
		frame.setSize(szerokoscP,wysokoscP);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//zamkniecie okienka z gra
		frame.setResizable(false);
		
		poziomy = new JFrame("Informacja");
		poziomy.setSize(250, 150);
		poziomy.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		poziomy.setLocationRelativeTo(null);
		poziomy.setResizable(false);
		ButtonGroup radioB = new ButtonGroup();
		radioB.add(latwy);
		radioB.add(trudny);
		panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(200,100));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		poziomy.getContentPane().add(panel2, BorderLayout.NORTH);
		
		gra = new Gra(frame);
		button1.addActionListener(new Action());
		button2.addActionListener(new Action());
		button3.addActionListener(new Action());
		button4.addActionListener(new Action());
		latwy.addActionListener(new Action());
		trudny.addActionListener(new Action());
		
		frame.add(gra);
		frame.getContentPane().add(wynik, BorderLayout.PAGE_END);
		frame.getContentPane().add(panel, BorderLayout.LINE_END);
		frame.setVisible(true);	
		
		//
		//BAZA DANYCH
		//
		baza = "WYNIKI";
		Connection polaczenie = bd.polacz(baza);//
		bd.stworzTab(polaczenie, baza);
		
		//
		//GRA SIECIOWA
		//
		System.out.println("Trwa ≥πczenie z serwerem ...");
		int port = 7777;
		serwer = new ServerSocket(port);
		System.out.println("Serwer rozpoczyna dzia≥anie ...");
		socket = serwer.accept();
		System.out.println("Po≥πczono z: " + socket.getInetAddress());
		streamOut = new DataOutputStream(socket.getOutputStream());
		streamOut.writeUTF("Witamy klienta na pok≥adzie");
		System.out.println("Wiadomosc wyslana.");

	}
	
	static class Action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == button1 && (gra.Pauza == true || gra.Run == false) && startuj == true) { gra.start(); }
			if(e.getSource() == button2) { System.exit(0); }
			if(e.getSource() == button3) { gra.Mute(); }
			if(e.getSource() == latwy && startuj == false && !trudny.isSelected())
			{
				startuj = true;
				gra.PoziomLatwy();
				info.setFont(new Font("Arial",Font.TYPE1_FONT, 15));
				info.setText("POZIOM TRUDNOåCI: £ATWY");
				panel2.add(Box.createRigidArea(new Dimension(25,40)));
				panel2.add(info);
				poziomy.add(button4);
				poziomy.setVisible(true);
				trudny.setEnabled(false);
			}
			if(e.getSource() == trudny && startuj == false && !latwy.isSelected())
			{
				startuj = true;
				gra.PoziomTrudny(); 
				info.setFont(new Font("Arial",Font.TYPE1_FONT, 15));
				info.setText("POZIOM TRUDNOåCI: TRUDNY");
				panel2.add(Box.createRigidArea(new Dimension(25,40)));
				panel2.add(info);
				poziomy.add(button4);
				poziomy.setVisible(true);
				latwy.setEnabled(false);
			}
			if(e.getSource() == button4)
			{
				poziomy.dispose();
			}
		}
	}
}
