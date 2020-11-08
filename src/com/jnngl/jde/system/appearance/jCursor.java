package com.jnngl.jde.system.appearance;

import java.awt.Cursor;

import javax.swing.JFrame;

import com.jnngl.jde.Desktop;

public class jCursor {

	public static Cursor loadCursor(String name) {
//		BufferedImage cursor = Files.readImage(Theme.get("CURSOR::"+name));
		//BufferedImage cursor = Antialiasing.antialiaze(ICO.getSmallest(Theme.get("CURSOR::"+name)));
		//Screen.def.getBestCursorSize(32, 32);
		Cursor cur = //Screen.def.createCustomCursor(cursor, new Point(0,0), "cursor");
		Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		
		return cur;
	}
	
	public static void changeCursor(String c) {
		setCursor(c, Desktop.session);
	}

	public static void setCursor(String c, JFrame jf) {
		jf.setCursor(loadCursor(c));
	}
	
	public static void changeCursor(int cur)
	{
		Desktop.session.setCursor(Cursor.getPredefinedCursor(cur));
	}
	
}
