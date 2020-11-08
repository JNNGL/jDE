package com.jnngl.jde.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class HalfRoundRect {

	public static void draw(Graphics g, Color c, int x, int y, int w, int h, int r) {
		BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		Graphics2D bg = bi.createGraphics();
		bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		bg.setColor(c);
		if(r!=0)
			bg.fillRoundRect(0, 0, w, h*r, r, r);
		else
			bg.fillRect(0, 0, w, h);
		bg.dispose();
		g.drawImage(bi, x, y, null);
	}

}
