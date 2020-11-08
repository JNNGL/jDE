package com.jnngl.jde.graphics;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Map;

public class GradientUtils {
	
	public static void linearGradient(Graphics2D g, Point pfrom, Point pto, Color c1, Color c2, Rectangle r) {
//		g.addRenderingHints(Map.of(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE, RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
//				RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC, RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		GradientPaint gp = new GradientPaint(pfrom.x, pfrom.y, c1, pto.x, pto.y, c2);
		g.setPaint(gp);
		g.fill(r);
	}
	
	public static void gradient(Graphics2D g, float radius, float[]dists,Color[]colors,Point2D center, Rectangle r)
	{
		RadialGradientPaint rgp = new RadialGradientPaint(center, radius, dists, colors);
//		g.addRenderingHints(Map.of(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE, RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
//				RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC, RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g.setPaint(rgp);
		g.fill(new Ellipse2D.Float(r.x,r.y,r.width,r.height));
	}
	
	public static BufferedImage createGradient(int wh)
	{
		BufferedImage bi1 = new BufferedImage(wh,wh,BufferedImage.TYPE_INT_ARGB);
		Graphics2D tg = bi1.createGraphics();
		gradient(tg, wh/2, new float[] {0f,1f}, new Color[] {new Color(0,0,0,75), new Color(0,0,0,0)}, new Point(wh/2,wh/2), new Rectangle(0,0,wh,wh));
		return bi1;
	}
	
	public static BufferedImage createRoundGradient(int length, Color from, Color to)
	{
		BufferedImage bi1 = new BufferedImage(length*2, length*2,BufferedImage.TYPE_INT_ARGB);
		Graphics2D tg = bi1.createGraphics();
		tg.addRenderingHints(Map.of(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE, RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
				RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC, RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		gradient(tg, length, new float[] {0f,1f}, new Color[] {from, to}, new Point(length,length), new Rectangle(0,0,length*2,length*2));
		return bi1;
	}
	
}
