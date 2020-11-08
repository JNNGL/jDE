package com.jnngl.jde.system;

import static com.jnngl.jde.Screen.h;
import static com.jnngl.jde.Screen.w;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

import com.jnngl.jde.data.Files;
import com.jnngl.jde.graphics.Blur;

public class Wallpaper {
	
	public static BufferedImage wallpaper;
	
	public static final String SMART_RESIZE  ="344645645u234",
							   JUST_RESIZE   ="537845974r654",
							   CENTER_IT     ="457348953x853",
							   SMART_RESIZEP ="458734832j345",
							   SMART_RESIZE2 ="534759832h254";
	
	private static String type = SMART_RESIZE2;
	public static BufferedImage blurpaper;
	
	public static void setWallpaperResizeMethod(String ntype)
	{
		type=ntype;
	}
	
	public static String getCurrentResizeMethod()
	{
		return type;
	}
	
	public static void changeWallpaper(BufferedImage wp)
	{
		wallpaper=wp;
	}
	
	public static void loadWallpaper()
	{
		changeWallpaper( Files.readImage(Files.read("/etc/jde/system/wallpaper")));
	}
	
	public static void setWallpaper(String path)
	{
		Files.write("/etc/jde/system/wallpaper", path);
		loadWallpaper();
	}
	
	public static void renderWallpaper(Graphics og)
	{
		renderWallpaper(og,0);
	}
	
	public static boolean isSet()
	{
		return new File("/etc/jde/system/wallpaper").exists();
	}

	public static void renderWallpaper(Graphics og, int yoffset) {
		Graphics2D g = (Graphics2D) og;
		g.addRenderingHints(Map.of(
				RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY,
				RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		
		if(type.equals(SMART_RESIZE2)) {
			double ww = wallpaper.getWidth();
			double wh = wallpaper.getHeight();
			
			double wwr = w/ww;
			double mul = wwr;
			
			if(wh*wwr<h)
				mul=h/wh;
			
			g.drawImage(wallpaper,(w-(int)(ww*mul))/2,yoffset+(h-(int)(wh*mul))/2,(int)(ww*mul),(int)(wh*mul),null);
		
		}
		else if(type.equals(SMART_RESIZE)||type.equals(SMART_RESIZEP))
		{
			

			double ww = wallpaper.getWidth();
			double wh = wallpaper.getHeight();
			
			double wwr = w/ww;
			double mul = wwr;
			
			if(wh*wwr>h)
				mul=h/wh;
			
			if(ww*mul>=w&&wh*mul>=h)
				type=SMART_RESIZE;
				
			
			g.drawImage(wallpaper,(w-(int)(ww*mul))/2,yoffset+(h-(int)(wh*mul))/2,(int)(ww*mul),(int)(wh*mul),null);
			
		
			if(type.equals(SMART_RESIZEP))
			{
				if(blurpaper==null)
				{
					if(new File("/etc/jde/system/blurpaper.png").exists())
					{
						blurpaper = Files.readImage("/etc/jde/system/blurpaper.png");
						if(blurpaper.getWidth()>=w&&blurpaper.getHeight()>=h)
						{
							g.drawImage(blurpaper, 0,0,null);
							return;
						}
					}
					
					int www = (int)(ww/w+1);
					int wwh = (int)(wh/h+1);
					
					blurpaper = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
					Graphics2D vg = blurpaper.createGraphics();
					
					BufferedImage blurred = Blur.blur(wallpaper, 50);
					vg.drawImage(blurred, (int)(w/2-ww*www/2),yoffset+(int)(h/2-wh*wwh/2),(int)(ww*www),(int)(wh*wwh), null);
					vg.drawImage(wallpaper,(w-(int)(ww*mul))/2,yoffset+(h-(int)(wh*mul))/2,(int)(ww*mul),(int)(wh*mul),null);
				
					Files.writeImage("/etc/jde/system/blurpaper.png", blurpaper, "png");
					
				}
				g.drawImage(blurpaper, 0,0,null);
				
			}
		
		}
		else if(type.equals(JUST_RESIZE))
			g.drawImage(wallpaper,0,0,w,h,null);
		else if(type.equals(CENTER_IT))
			g.drawImage(wallpaper,w/2-wallpaper.getWidth()/2,yoffset+h/2-wallpaper.getHeight()/2,null);
	}
	
}
