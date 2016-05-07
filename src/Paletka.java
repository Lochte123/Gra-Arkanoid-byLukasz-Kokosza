import javax.swing.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class Paletka 
{
	public static int s = 80;
	public static int  w = 20;
	Rectangle kladka;
	Gra pole;
	public Paletka(Gra pole,int x, int y, int wid, int hei)//konstruktor
	{
		this.pole = pole;
		kladka = new Rectangle(x,y,wid,hei);
	}
	
	/*public void ruchX(int v)//ruch kladki w plaszczyznie x
	{
		int maxDlugosc_ruchu = pole.getpoleGry().width - pole.getPaletka().kladka.width;
		kladka.x = kladka.x + v;
		if(kladka.x < 0) kladka.x = 1;
		else if(kladka.x > maxDlugosc_ruchu) kladka.x = maxDlugosc_ruchu; 
	}*/
	
	public boolean kolizjaP(Rectangle k)
	{
		return kladka.intersects(k);
	}
	
	public void RysujP(Graphics rysuj)
	{
		rysuj.setColor(new Color(30,0,5,120));
		rysuj.fillRect(kladka.x,kladka.y,kladka.width,kladka.height);
		rysuj.setColor(new Color(255,255,255));
		rysuj.drawRect(kladka.x,kladka.y,kladka.width,kladka.height);
	}
}