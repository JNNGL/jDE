package com.jnngl.jde.system.appearance;

import static com.jnngl.jde.Screen.dark;
import static com.jnngl.jde.Screen.glass;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.jnngl.jde.graphics.svg.SVG;

public class Theme {

	public static Map<String, String> theme = new HashMap<String, String>();
	
	public static void loadTheme(String themefile)
	{
		// Read Theme Scheme
		try {
			File file = new File("/etc/jde/system/themes/"+themefile+".thi");
			Scanner reader = new Scanner(file);
			String defpath="";
			while(reader.hasNextLine())
			{
				String data = reader.nextLine();
				String[]link=data.split(" ");
				if(link.length==1)
				{
					defpath=link[0];
				}
				else if(link.length==2)
				{
					theme.put(link[0],defpath+link[1]);
				}
			}
			reader.close();
			System.out.println("Theme '"+themefile+"' loaded successfully: "+theme.toString());
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void put(String name, String abspath) {
		theme.put(name, abspath);
	}
	
	public static String get(String name)
	{
		if (theme.containsKey(name)) {
			return theme.get(name);
		}
		return "";
	}
	
	public static BufferedImage getSVG(String name)
	{
		return SVG.toImage(get(name));
	}
	
	public static Color getLight() {
		int opacity = 100;
		if(!glass)
			opacity = 255;
		
		if(dark)
			return new Color(50,50,50,opacity);
		else if(glass)
			return new Color(255,255,255,opacity);
		else 
			return new Color(205,205,205,opacity);
	}
	
	public static Color getDark() {
		if(!glass)
			if(dark)
				return new Color(40,40,40);
			else
				return new Color(235,235,235);
		else
			if(dark)
				return new Color(30,30,30,150);
			else
				return new Color(205,205,205,150);
	}
	
	public static Color getFontColor() {
		if(dark)
			return new Color(255,255,255,255);
		else
			return new Color(50,50,50,255);
	}
	
}
