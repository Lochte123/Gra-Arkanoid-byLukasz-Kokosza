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
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.lang.*;
import org.sqlite.*;
import java.sql.*;

public class Baza_danych 
{
	public static Connection polacz(String baza)
	{
		Connection polaczenie = null;
		try 
		{
            Class.forName("org.sqlite.JDBC");           
            polaczenie = DriverManager.getConnection("jdbc:sqlite:"+baza+".db");
            System.out.println("Po³¹czono z baz¹ "+baza);
        } 
		catch (Exception e) 
		{
            System.err.println("B³¹d po³¹czenia z baz¹: \n" + e.getMessage());
            return null;
        }
        return polaczenie;
	}
	
	public static void stworzTab(Connection polaczenie, String tab)
	{
		Statement stat = null;
		try
		{
			stat = polaczenie.createStatement();
			String tabSQL = "CREATE TABLE " + tab
	                    + " (LICZBA_GRACZY INT                 NOT NULL,"
	                    + " POZIOM_TRUDNOŒCI 	CHAR(10)	 NOT NULL,"
	                    + " WYGRANA		  CHAR(5)   NOT NULL,"	
	                    + " WYNIK         INT)";
			stat.executeUpdate(tabSQL);
			stat.close();
	        polaczenie.close();
		}
		catch(SQLException e)
		{
			System.out.println("Nie mo¿na utworzyæ tablicy" + e.getMessage());
		}
	}
	
	public static void dodajDane(int ID, int w, String t,String p, String baza)
	{
        Connection polaczenie = null;
        Statement stat = null;
        
        try
        {
        	 Class.forName("org.sqlite.JDBC");
             polaczenie = DriverManager.getConnection("jdbc:sqlite:" + baza + ".db");
             stat = polaczenie.createStatement();
             
             String dodajSQL = "INSERT INTO " + baza + " (LICZBA_GRACZY, POZIOM_TRUDNOŒCI, WYGRANA, WYNIK) "
            		 + " VALUES ("
            		 + ID + ","
            		 + "'" + p + "',"
            		 + "'" + t + "',"
            		 + "'" + w + "/120" + "'"
            		 + " );";
             
             stat.executeUpdate(dodajSQL);
             stat.close();
             polaczenie.close();
             
             System.out.println("Polecenie: \n" + dodajSQL + "\n wykonane.");
        }
        catch(Exception e)
        {
        	System.out.println("Nie mo¿na dodaæ nowych danych " + e.getMessage());
        }
	}
}
