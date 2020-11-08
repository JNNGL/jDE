package com.jnngl.jde.graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import com.jnngl.jde.Screen;

public class FontUtils {
	
	public static Font fromFile(String path, float size)
	{
		try {
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);
			return customFont;
		} catch (IOException|FontFormatException e) {
			e.printStackTrace();
			return Font.getFont("Arial");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static int getStringWidth(String label, Font e)
	{
		return Screen.def.getFontMetrics(e).stringWidth(label);
	}
	
	@SuppressWarnings("deprecation")
	public static int getFontHeight(Font e)
	{
		return Screen.def.getFontMetrics(e).getHeight();
	}
	
	public static int getStringWidth(String label, Graphics e)
	{
		return getFontMetrics(e).stringWidth(label);
	}
	
	public static int getFontHeight(Graphics e)
	{
		return getFontMetrics(e).getHeight();
	}
	
	public static FontMetrics getFontMetrics(Graphics g)
	{
		return g.getFontMetrics();
	}
	
}
