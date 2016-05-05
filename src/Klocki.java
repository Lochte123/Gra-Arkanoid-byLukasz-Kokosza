import javax.swing.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.lang.*;
import java.util.ArrayList;

public class Klocki 
{
	Rectangle k;
	boolean widoczny;
	
	public Klocki(int rzad, int kolumna, int szerokosc, int wysokosc)
	{
		widoczny = true;
		k = new Rectangle(kolumna * szerokosc ,rzad * wysokosc + 1,kolumna * szerokosc + szerokosc - 1,rzad * 2 + wysokosc - 1);
	}
	
	public Rectangle getR()
	{
		return this.k;
	}
	
	public void setV()
	{
		widoczny = false;
	}
	
	public boolean getV()
	{
		return widoczny;
	}
	
	public boolean kolizjaK(Rectangle k1)
	{
		return k.intersects(k1);
	}
}