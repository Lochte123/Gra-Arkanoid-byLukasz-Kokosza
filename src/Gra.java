import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.lang.Object;
import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Gra extends JPanel
{
	Dimension poleGry = new Dimension(500,690);
	Paletka paletka;
	Kulka kulka, kulka2,kulka3;
	BufferedImage tlo;	
	Main main;
	Klient socketKlient;
	Serwer socketSerwer;
	
	public static int ruch_w_prawo = 10;
	public static int ruch_w_lewo = -10;
	public static int ruch_kulki_x = ruch_w_prawo;
	public static int ruch_kulki_y = ruch_kulki_x;
	
	boolean widoczny;
	boolean r;
	boolean l;
	boolean Run = false;
	boolean Run2 = false;
	boolean Pauza = false;
	boolean mute = false;
	boolean mute2 = false;
	boolean ustawiono = false;
	
	public static int tabX = 10;
	public static int tabY = 6;
	public static int tabX2 = 10;
	public static int tabY2 = 6;
	public int szerK;
	public int wysK;
	public static int szerK2;
	public static int wysK2;
	
	int stareD;
	int vK = 30;
	int nvK = vK;
	
	int rozmiarT = 60;
	int rozmiarT2 = 80;
	Klocki[] klocki = new Klocki[rozmiarT];
	Klocki[] klocki2 = new Klocki[rozmiarT2];
	int liczbaK = 0;
	int liczbaK2 = 0;
	
	public Gra(Frame c)
	{
		c.addMouseMotionListener(new MouseMotionAdapter()
				{
					public void mouseMoved(MouseEvent e)
					{
						int max = getpoleGry().width - getPaletka().kladka.width;
						paletka.kladka.x = e.getX() - paletka.s/2;
						if(kulka.SPRAWDZACZ == true && (stareD <= paletka.kladka.x || stareD > paletka.kladka.x))
						{
							vK += paletka.kladka.x/10000;
						}
						else if(paletka.kladka.x < 0)
						{
							paletka.kladka.x = 0;
						}
						else if(paletka.kladka.x > max)
						{
							paletka.kladka.x = max;
						}
						stareD = paletka.kladka.x;
						repaint();
					}
				});
		
		paletka = new Paletka(this, (int)(poleGry.getWidth() - Paletka.s)/2, poleGry.height - paletka.w, paletka.s, paletka.w);
		kulka = new Kulka(this,poleGry.width/2, poleGry.height/2, kulka.rKulki);
		kulka2 = new Kulka(this,poleGry.width/2 + 20,poleGry.height/2, kulka.rKulki);
		MuzykaB();
		
		szerK = poleGry.width/tabX;
		wysK = ((poleGry.height/4)/tabY)+10;
		liczbaK = 0;
		for(int i = 0; i<tabY; i++)
		{
			for(int j=0; j<tabX; j++)
			{
				klocki[liczbaK] = new Klocki(i+1,j,szerK,wysK);
				liczbaK++;
			}
		}
		
		try{ tlo = ImageIO.read(new File("tlo.jpg")); } catch(IOException ex){}
		socketSerwer = new Serwer(this);
		socketKlient = new Klient(this,"localhost");
	}
	
	public void UstawPoziom()
	{
			szerK2 = poleGry.width/tabX2;
			wysK2 = ((poleGry.height/4)/tabY2) + 10;
			liczbaK2 = 0;
			for(int i = 0; i<tabY2; i++)
			{
				for(int j=0; j<tabX2; j++)
				{
					klocki2[liczbaK2] = new Klocki(i,j,szerK2,wysK2);
					liczbaK2++;
				}
			}
	}
	
	public void Mute()
	{
		if(mute == false) {
			mute = true;
			main.button3.setText("W£¥CZ DWIÊKI");
		}
		else 
		{ 
			mute = false; 
			main.button3.setText("WY£¥CZ DWIÊKI");
		}
	}
	
	public void MuzykaB()
	{
		try
		{
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("muzyka.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-15);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}
		catch(Exception e)
		{
		}
	}
	
	public void Przegrana()
	{
		pauza();
		kulka.setP(poleGry.width/2,poleGry.height/2);
		repaint();
	}
	
	public void PoziomLatwy()
	{
		vK = 38;
	}
	
	public void PoziomTrudny()
	{
		vK = 22;
	}
	
	public void setPaletka(Paletka paletka)
	{
		this.paletka = paletka;
	}
	
	public Paletka getPaletka()
	{
		return this.paletka;
	}
	
	public int getS()
	{
		return szerK;
	}
	 
	public int getW()
	{
		return wysK;
	}
	
	public Dimension getpoleGry()
	{
		return poleGry;
	}
	
	public void setSize(Dimension rozmiar)
	{
		super.setSize(rozmiar);
		if(Run==false)
		{
			poleGry = new Dimension(rozmiar.width-200,rozmiar.height-200);
			kulka.setP(poleGry.width/2, poleGry.height/2);
		}
	}
	
	public synchronized void start()
	{
		Pauza = false;

		if(JOptionPane.showConfirmDialog(this, "Czy chcesz graæ sieciowo?") == 0)
		{
			socketSerwer.start();
			socketKlient.start();
			System.out.println("SUKCES! PO£¥CZONO SERWER Z KLIENTEM, INFORMACJE: ");
			socketKlient.wyslijDane("ping".getBytes());
		}
		
		if(Run == false) 
		{
			gThread.start();//rozpoczecie gry
		}
	}
	
	public void pauza()
	{
		Pauza = true;
	}
	
	public synchronized void stop()
	{
		Run = false;
	}
	
	Thread gThread = new Thread(new Runnable()
			{
				public void run()
				{
					Run = true;
					kulka.setW(ruch_kulki_x,ruch_kulki_y);
					kulka2.setW(ruch_kulki_x, ruch_kulki_y);
					while(Run == true)
					{
						if(Pauza == false)
						{
							kulka.ruchK();
							if(kulka.l > 0 && kulka.l < 2 && kulka2.kulkaDuchLicz > 0) { kulka2.ruchKDuch(); }
							if(kulka.poziom2 && ustawiono == false) 
							{
								UstawPoziom();
								ustawiono = true;
							}
							repaint();
						try
						{
							Thread.sleep(vK);
						}
						catch(Exception e){}
						if(Run == false)
						{
							repaint();
						}
					}
					}
				}
			});
	
	
	public void paint(Graphics rysuj)
	{
		super.paint(rysuj);//inicjalizacja rysowania na planszy
		rysuj.drawImage(tlo,0,0,null);
		paletka.RysujP(rysuj);
		kulka.Rysuj(rysuj);
		if(kulka.l > 0 && kulka.l < 2) kulka2.Rysuj2(rysuj);
		for(int i = 0; i<liczbaK; i++)
		{
			if(klocki[i].getV() == true)
			{
				rysuj.setColor(new Color(i + 25,i + 25,i + 25));
				rysuj.fillRect(klocki[i].getR().x,klocki[i].getR().y,klocki[i].getR().width,klocki[i].getR().height);
				rysuj.setColor(Color.BLACK);
				rysuj.drawRect(klocki[i].getR().x,klocki[i].getR().y,klocki[i].getR().width,klocki[i].getR().height);
				
				if(i == 20)//zwiekszenie zycia o 1
				{
					rysuj.setColor(Color.GREEN);
					rysuj.fillRect(klocki[i].getR().x,klocki[i].getR().y,klocki[i].getR().width,klocki[i].getR().height);
					rysuj.setColor(Color.BLACK);
					rysuj.drawRect(klocki[i].getR().x,klocki[i].getR().y,klocki[i].getR().width,klocki[i].getR().height);
				}
				else if(i == 29)//zmniejszenie zycia o 1 jesli zycie>1
				{
					rysuj.setColor(Color.RED);
					rysuj.fillRect(klocki[i].getR().x,klocki[i].getR().y,klocki[i].getR().width,klocki[i].getR().height);
					rysuj.setColor(Color.BLACK);
					rysuj.drawRect(klocki[i].getR().x,klocki[i].getR().y,klocki[i].getR().width,klocki[i].getR().height);
				}
				else if(i == 58)//nowa kulka
				{
					rysuj.setColor(Color.ORANGE);
					rysuj.fillRect(klocki[i].getR().x,klocki[i].getR().y,klocki[i].getR().width,klocki[i].getR().height);
					rysuj.setColor(Color.BLACK);
					rysuj.drawRect(klocki[i].getR().x,klocki[i].getR().y,klocki[i].getR().width,klocki[i].getR().height);
				}
				else if(i == 54)//zmniejszenie predkosci kulki
				{
					rysuj.setColor(Color.YELLOW);
					rysuj.fillRect(klocki[i].getR().x,klocki[i].getR().y,klocki[i].getR().width,klocki[i].getR().height);
					rysuj.setColor(Color.BLACK);
					rysuj.drawRect(klocki[i].getR().x,klocki[i].getR().y,klocki[i].getR().width,klocki[i].getR().height);
				}
				else if(i == 40)//zwiekszenie predkosci kulki
				{
					rysuj.setColor(Color.GRAY);
					rysuj.fillRect(klocki[i].getR().x,klocki[i].getR().y,klocki[i].getR().width,klocki[i].getR().height);
					rysuj.setColor(Color.BLACK);
					rysuj.drawRect(klocki[i].getR().x,klocki[i].getR().y,klocki[i].getR().width,klocki[i].getR().height);
				}
			}
		}
		
		if(kulka.poziom2 == true)
		{
			for(int i = 0; i<liczbaK2; i++)
			{
				if(klocki2[i].getV() == true)
				{
					rysuj.setColor(new Color(i + 190,i + 170,i + 160));
					rysuj.fillRect(klocki2[i].getR().x,klocki2[i].getR().y,klocki2[i].getR().width,klocki2[i].getR().height);
					rysuj.setColor(Color.BLACK);
					rysuj.drawRect(klocki2[i].getR().x,klocki2[i].getR().y,klocki2[i].getR().width,klocki2[i].getR().height);
				}
			}
		}
	}
}