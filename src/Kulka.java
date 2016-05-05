import javax.swing.JFrame;
import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.File;
import java.io.*;
import sun.audio.*;
import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.lang.*;
import java.applet.*;
import java.net.*;

public class Kulka 
{
	AtomicInteger c = new AtomicInteger(0);
	Baza_danych bd;
	Point p = new Point(0,0);
	Gra gra;
	Dimension wektor = new Dimension();
	Paletka paletka;
	Main main;
	Klocki klocki;
	String poziomT;
	int r;
	int flaga = 0;
	int vK;
	boolean SPRAWDZACZ = false;
	boolean wycisz = false;
	boolean koniec = false;
	boolean poziom2 = false;
	boolean tekst = false;
	int l = 0;
	int kulkaDuchLicz = 3;
	int strataD = 0;
	int IDg;
	public static int licznikSzans = 3;
	public static int licznikPunktow = 0;
	public static int rKulki = 8;
	
	
	public Kulka(Gra gra, int x, int y, int r)
	{
		this.gra = gra;
		p = new Point(x,y);
		this.r = r;
	}
	
	public void setW(int ruchX, int ruchY)
	{
		wektor = new Dimension(ruchX,ruchY);
	}
	
	public Point getP()
	{
		return p;
	}
	
	public void setP(int x,int y)
	{
		p = new Point(x,y);
	}
	
	public void StraconaSzansa()
	{
		licznikSzans--;
		main.wynik.setText("POZOSTALO SZANS: "+ licznikSzans);
		if(licznikSzans == 1)
		{
			main.wynik.setForeground(Color.RED);
			main.wynik.setText("POZOSTALO SZANS: "+ licznikSzans);
		}
		if(licznikSzans > 1)
		{
			main.wynik.setForeground(Color.DARK_GRAY);
			main.wynik.setText("POZOSTALO SZANS: "+ licznikSzans);
		}
		if(licznikSzans == 0)
		{
			Dzwiek_Przegrana();
			main.wynik.setForeground(Color.RED);
			main.wynik.setBackground(Color.BLACK);
			main.wynik.setText("PRZEGRALES!");
			if(main.trudny.isSelected()) poziomT = "TRUDNY";
			else poziomT = "£ATWY";
			IDg = c.incrementAndGet();
			bd.dodajDane(IDg, licznikPunktow, "NIE", poziomT, main.baza);
			gra.Run = false;
		}
	}
	
	public void Punkt()
	{
		licznikPunktow++;
		main.liczPunkty.setText("LICZBA PUNKTÓW: " + licznikPunktow);
		
		if(licznikPunktow == 60)
		{
			if(gra.mute == false){ Dzwiek_Wygrana(); }
			main.wynik.setForeground(Color.GREEN);
			main.wynik.setBackground(Color.BLACK);
			main.wynik.setText("POZIOM 2");
			poziom2 = true;
			if(poziom2 == true && tekst == false) setP(gra.poleGry.width/2, gra.poleGry.height/2);
		}
		if(poziom2 == true && licznikPunktow >= 120)
		{
			if(gra.mute == false){ Dzwiek_Wygrana(); }
			main.wynik.setForeground(Color.GREEN);
			main.wynik.setBackground(Color.BLACK);
			main.wynik.setText("WYGRA£EŒ!");
			if(main.trudny.isSelected()) poziomT = "TRUDNY";
			else poziomT = "£ATWY";
			IDg = c.incrementAndGet();
			bd.dodajDane(IDg, licznikPunktow, "TAK", poziomT, main.baza);
			gra.Run = false;
		}
	}
	
	public void Dzwiek_Paletka()
	{
		try
		{
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("dzwiek.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
		}
		catch(Exception e)
		{
			System.out.println("blad");
		}
	}
	
	public void Dzwiek_Klocek()
	{
		try
		{
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("klocek.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
		}
		catch(Exception e)
		{
			System.out.println("blad");
		}
	}
	
	public void Dzwiek_Szansa()
	{
		try
		{
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("smierc.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
		}
		catch(Exception e)
		{
			System.out.println("blad");
		}
	}
	
	public void Dzwiek_Wygrana()
	{
		try
		{
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("wygrana.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
		}
		catch(Exception e)
		{
			System.out.println("blad");
		}
	}
	
	public void Dzwiek_Przegrana()
	{
		try
		{
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("przegrana.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
		}
		catch(Exception e)
		{
			System.out.println("blad");
		}
	}

	
	public void ruchK()
	{
		if(p.x - r <= 0 && wektor.width < 0) wektor.width = - wektor.width;
		if(p.x + r >= gra.getpoleGry().width && wektor.width > 0) wektor.width = - wektor.width;
		if(p.y - r <= 0 && wektor.height < 0) wektor.height = - wektor.height;
		if(p.y + r >= gra.getpoleGry().height && wektor.height > 0) 
		{
			if(gra.mute == false) { Dzwiek_Szansa(); }
			StraconaSzansa();
			setP(gra.poleGry.width/2, gra.poleGry.height/2);
			if(licznikSzans == 0)
			{
				gra.Przegrana();
			}
		}
		if(gra.getPaletka()!=null)
		{
			if(gra.getPaletka().kolizjaP(new Rectangle(p.x - r + wektor.width,p.y - r + wektor.height, 2*r, 2*r))) //gdy kolizja z paletka
			{
				if(poziom2 == true && tekst == false)
				{
					main.wynik.setForeground(Color.DARK_GRAY);
					main.wynik.setText("POZOSTALO SZANS: "+ licznikSzans);
					tekst = true;
				}
				SPRAWDZACZ = true;
				wektor.height = - wektor.height;
				if(gra.mute == false) Dzwiek_Paletka();
			}
			else 
			{
				SPRAWDZACZ = false;
			}
		}
		
		if(poziom2 == false)
		{
		for(int i=0; i<gra.liczbaK; i++)
		{
			flaga = 0;
			if(gra.klocki[i].getV() == true)
			{
				if(gra.klocki[i].kolizjaK(new Rectangle(p.x - gra.getS()/2 - 10,p.y - gra.getW()/2 - 10, 2*rKulki + 10, 2*rKulki + 10)) && flaga == 0)
				{
					
					if(gra.mute == false) { Dzwiek_Klocek(); }
					Punkt();
					gra.klocki[i].setV();
					wektor.height = - wektor.height;
					flaga = 1;
					
					if(i == 20)
					{
						licznikSzans = licznikSzans + 1;
						main.wynik.setText("POZOSTALO SZANS: "+ licznikSzans);
						if(licznikSzans > 1)
						{
							main.wynik.setForeground(Color.DARK_GRAY);
							main.wynik.setText("POZOSTALO SZANS: "+ licznikSzans);
							if(licznikPunktow>=60)
							{
								if(gra.mute == false){ Dzwiek_Wygrana(); }
								main.wynik.setForeground(Color.GREEN);
								main.wynik.setBackground(Color.BLACK);
								main.wynik.setText("POZIOM 2");
								poziom2 = true;
								setP(gra.poleGry.width/2, gra.poleGry.height/2);
							}
						}
					}
					else if(i == 29 && licznikSzans > 1)
					{
						licznikSzans = licznikSzans - 1;
						main.wynik.setText("POZOSTALO SZANS: "+ licznikSzans);
						if(licznikSzans == 1)
						{
							main.wynik.setForeground(Color.RED);
							main.wynik.setText("POZOSTALO SZANS: "+ licznikSzans);
							if(licznikPunktow>=60)
							{
								if(gra.mute == false){ Dzwiek_Wygrana(); }
								main.wynik.setForeground(Color.GREEN);
								main.wynik.setBackground(Color.BLACK);
								main.wynik.setText("POZIOM 2");
								poziom2 = true;
								setP(gra.poleGry.width/2, gra.poleGry.height/2);
							}
						}
					}
					else if(i == 58)
					{
						l++;
					}
					else if(i == 40)
					{
						gra.vK = gra.vK + 5;
					}
					else if(i == 54)
					{
						gra.vK = gra.vK - 6;
					}
				}
			}
		}
	}
		
		if(poziom2 == true)
		{
		for(int i=0; i<gra.liczbaK2; i++)
		{
			flaga = 0;
			if(gra.klocki2[i].getV() == true)
			{
				if(gra.klocki2[i].kolizjaK(new Rectangle(p.x - gra.getS()/2,p.y - gra.getW()/2, 2*r + 10, 2*r + 10)) && flaga == 0)
				{
					if(gra.mute == false) { Dzwiek_Klocek(); }
					Punkt();
					gra.klocki2[i].setV();
					wektor.height = - wektor.height;
					flaga = 1;
				}
			}
		}
	}
		
	p.move(p.x + wektor.width, p.y + wektor.height);
	}
	
	public void ruchKDuch()
	{
		if(p.x - r <= 0 && wektor.width < 0) wektor.width = - wektor.width;
		if(p.x + r >= gra.getpoleGry().width && wektor.width > 0) wektor.width = - wektor.width;
		if(p.y - r <= 0 && wektor.height < 0) wektor.height = - wektor.height;
		if(p.y + r >= gra.getpoleGry().height && wektor.height > 0) 
		{
			if(gra.mute == false) { Dzwiek_Szansa(); }
			strataD++;
			if(strataD == 3)
			{
				main.wynik.setText("NIE MOZESZ STRACIC DUSZKA!");
				if(main.trudny.isSelected()) poziomT = "TRUDNY";
				else poziomT = "£ATWY";
				IDg = c.incrementAndGet();
				bd.dodajDane(IDg, licznikPunktow, "NIE", poziomT, main.baza);
				gra.Run = false;
			}
			kulkaDuchLicz = kulkaDuchLicz - 1;
			if(kulkaDuchLicz > 0) setP(gra.poleGry.width/2, gra.poleGry.height/2);
			else setP(main.frame.WIDTH + 690, main.frame.HEIGHT + 700);
		}
		if(gra.getPaletka()!=null)
		{
			if(gra.getPaletka().kolizjaP(new Rectangle(p.x - r + wektor.width,p.y - r + wektor.height, 2*r, 2*r))) //gdy kolizja z paletka
			{
				SPRAWDZACZ = true;
				wektor.height = - wektor.height;
				if(gra.mute == false) Dzwiek_Paletka();
			}
			else 
			{
				SPRAWDZACZ = false;
			}
		}

		p.move(p.x + wektor.width, p.y + wektor.height);	
	}
	
	public int getWynik()
	{
		if(licznikSzans <= 0)
		{
			return licznikPunktow;
		}
		else
		{
			return 0;
		}
	}
	
	public void Rysuj(Graphics rysuj)
	{
		rysuj.setColor(new Color(255,255,255));
		rysuj.fillOval(p.x - r, p.y - r, 2*r, 2*r);
		rysuj.setColor(new Color(0,0,0));
		rysuj.drawOval(p.x - r, p.y - r, 2*r, 2*r);
	}
	
	public void Rysuj2(Graphics rysuj)
	{
		rysuj.setColor(new Color(255,255,255,70));
		rysuj.fillOval(p.x - r, p.y - r, 2*r, 2*r);
		rysuj.setColor(new Color(0,0,0));
		rysuj.drawOval(p.x - r, p.y - r, 2*r, 2*r);
	}
}