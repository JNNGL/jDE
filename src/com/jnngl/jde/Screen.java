package com.jnngl.jde;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import com.jnngl.jde.graphics.FontUtils;

public class Screen {
	
	public static int w=-1,h=-1;
	public static Toolkit def;
	public static Dimension screen;
	
	public static void initScreen()
	{
		def=Toolkit.getDefaultToolkit();
		screen = def.getScreenSize();
		w=(int) screen.getWidth();
		h=(int) screen.getHeight();
		
	}
	
	public static void initOther()
	{
		FONT_SIZE=h/70f;
		SYSTEM_FONT = FontUtils.fromFile("/etc/jde/system/system.ttf", FONT_SIZE);
	}
	
	public static boolean isInit()
	{
		return (w>1&&h>1);
	}
	
	public static boolean dark  = false;
	public static boolean glass = true;
	//public static boolean glass_back = true;
	
	public static final String SYSTEM_PATH="/etc/jde";
	public static float FONT_SIZE;
	public static Font SYSTEM_FONT;
	
	public static int round=20;
	public static int dockRound=(int)(round*2f);
	
}
