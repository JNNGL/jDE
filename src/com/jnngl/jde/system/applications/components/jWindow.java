package com.jnngl.jde.system.applications.components;

import static com.jnngl.jde.Screen.*;
import static com.jnngl.jde.graphics.FontUtils.getFontHeight;
import static com.jnngl.jde.graphics.FontUtils.getStringWidth;
import static com.jnngl.jde.graphics.GradientUtils.createRoundGradient;
import static com.jnngl.jde.graphics.GradientUtils.linearGradient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Map;

import com.jnngl.jde.Desktop;
import com.jnngl.jde.graphics.HalfRoundRect;
import com.jnngl.jde.graphics.svg.SVG;
import com.jnngl.jde.system.appearance.Theme;


//public abstract class jWindow extends MotionPanel {
	
//	private static final long serialVersionUID = 1L;
//	
//	protected static JFrame window;
//	protected static float layout = w/50f;
//	
//	public jWindow(JFrame parent) {
//		super(parent);
//		window=parent;
//		window.add(this);
//	}
//
//	protected static JFrame createWindow(int width, int height, String name, int x, int y)
//	{
//		if(window!=null)
//			window.dispose();
//		JFrame jf = new JFrame(name);
//		jf.setUndecorated(true);
//		
//		
//		jf.setSize(width, height+(int)layout);
//		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		jf.setLocation(x, y);
//		if(glass_back)
//			if(dark)
//				jf.setBackground(new Color(50,50,50, 75));
//			else
//				jf.setBackground(new Color(200,200,200, 75));
//		else if(glass)
//			jf.setOpacity(.7f);
//		jf.setVisible(true);
//		jf.repaint();
//		
//		jCursor.setCursor("Default", jf);
//		
//		return jf;
//	}
//	
//	public void paintComponent(Graphics og)
//	{
//		Graphics2D g = (Graphics2D) og;
//		
//		// Draw Window GUI;
//		
//		// Draw window
//		if(glass) {
//			if(dark)
//				g.setColor(new Color(50,50,50));
//			else
//				g.setColor(new Color(200,200,200));
//			g.fillRect(0,(int)layout,window.getWidth(),window.getHeight());
//		}
//		
//		// Draw bar
//		
//		int opacity = 255;
//		
//		if(glass_back)
//			opacity = 100;
//		
//		if(dark)
//			g.setColor(new Color(0,0,0,opacity));
//		else
//			g.setColor(new Color(235,235,235,opacity));
//		g.fillRect(0,0,window.getWidth(),(int)layout);
//		
//		// Draw label;
//		// Calculate label
//		g.setFont(SYSTEM_FONT);
//		String label = window.getTitle();
//		int tlw = getStringWidth(label,g);
//		int lh = getFontHeight(g);
//		
//		int lw = tlw;
//		
//		if(tlw>window.getWidth()/2)
//		{
//			int cls = tlw/label.length();
//			int avs = window.getWidth()/2/cls;
//			String tlabel = label.substring(0,avs);
//			label = tlabel.substring(0,tlabel.length()-3)+"...";
//			lw = getStringWidth(label,g);
//		}
//		
//		// Draw label
//		if(dark)
//			g.setColor(Color.WHITE);
//		else
//			g.setColor(Color.GRAY);
//		
//		g.setRenderingHint(
//		        RenderingHints.KEY_TEXT_ANTIALIASING,
//		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//		
//		g.drawString(label, window.getWidth()/2-lw/2, (int)(layout-lh/2));
//	}

public abstract class jWindow {
	
	public int owh=0;
	
	private int x=0;
	private int y=0;
	private int ww=0;
	private int wh=0;
	protected String label;
	protected boolean focused=false;
	private String codename="System::jWindow::6738434";
	
	private int tx=0,ty=0,tw=0,th=0;
	private String tl="";
	private boolean minimized=false;
	
	public static int layout = round/2+round/4;
	public static int deflayout = h/23;
	
	private BufferedImage icon;
	
	public boolean isMinimized() {
		return minimized;
	}
	
	public void minimize() {
		if(minimized)
			return;
		tx=x;x=-10;
		ty=y;y=-10;
		tw=ww;ww=0;
		th=wh;wh=0;
		tl=label;label="";
		minimized=true;
	}
	
	public void unminimize() {
		if(!minimized)
			return;
		x=tx;tx=0;
		y=ty;ty=0;
		ww=tw;tw=0;
		wh=th;th=0;
		label=tl;tl="";
		minimized=false;
	}
	
	public void setFocused(boolean b)
	{
		focused=b;
	}
	
	public boolean getFocused()
	{
		return focused;
	}
	
	public jWindow(int ww, int wh, String label, int x, int y, final String codename, boolean dry)
	{
		restoreDefaultIcon();
		this.codename=codename;
		if(dry)
			return;
		
		this.setX(x);
		this.setY(y);
		this.label=label;
		owh=wh;
		this.setWw(ww);
		this.setWh(wh);
		Desktop.native_desktop.registerApplication(this);
	}
	
	public static int length = w/99;
	
	public abstract void render(Graphics2D g, int yoffset);
	
	public void render(Graphics2D g)
	{
		render(g, 0);
	}
	
	public void renderUI(Graphics og)
	{
		renderUI(og,0);
	}
	
	public void renderUI(Graphics og, int yoffset) {
		layout=(int)(round/2+round/4);
		if(layout<deflayout)
			layout=deflayout;
		setWh(owh);
		if(minimized)
			return;
		Graphics2D g = (Graphics2D) og;
		
		g.addRenderingHints(Map.of(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE, RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
				RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC, RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		
		
		Color from = new Color(length/6,length/6,length/6,length*4);
		Color to   = new Color(0 ,0 ,0 ,0 );
		
		
		g.setColor(from);
		g.fillRect(x, y+yoffset, ww, wh);
		BufferedImage grad = createRoundGradient(length, from, to);
		
		linearGradient(g, new Point(x,yoffset+y),new Point(x-length,yoffset+y),from,to,new Rectangle(x-length,yoffset+y,length,wh));
		g.drawImage(grad.getSubimage(0,length,length,length),x-length,yoffset+y+wh,null);
		
		linearGradient(g, new Point(x,yoffset+y+wh), new Point(x,yoffset+y+wh+length),from,to,new Rectangle(x,yoffset+y+wh,ww,length));
		g.drawImage(grad.getSubimage(length,length,length,length),x+ww,yoffset+y+wh,null);
		
		linearGradient(g, new Point(x+ww,yoffset+y),new Point(x+ww+length,yoffset+y),from,to,new Rectangle(x+ww,yoffset+y,length,wh));
		g.drawImage(grad.getSubimage(length,0,length,length),x+ww,yoffset+y-length,null);
		
		linearGradient(g, new Point(x,yoffset+y),new Point(x,yoffset+y-length),from,to,new Rectangle(x,yoffset+y-length,ww,length));
		g.drawImage(grad.getSubimage(0,0,length,length),x-length,yoffset+y-length,null);
		
		
		
		int opacity = 100;
		if(!glass)
			opacity = 255;
		
		if(dark)
			g.setColor(new Color(50,50,50,opacity));
		else if(glass)
			g.setColor(new Color(255,255,255,opacity));
		else 
			g.setColor(new Color(205,205,205,opacity));

		g.fillRoundRect(x,yoffset+y,getWw(),getWh(),round,round);
		
		if(!glass)
			if(dark)
				g.setColor(new Color(40,40,40));
			else
				g.setColor(new Color(235,235,235));
		
//		g.fillRoundRect(x,yoffset+y,getWw(),layout,round,round);
		
		HalfRoundRect.draw(g,g.getColor(),x,yoffset+y,getWw(),layout,round);
		
		if(dark)
			g.setColor(new Color(255,255,255,255));
		else
			g.setColor(new Color(50,50,50,255));
		
		g.setFont(SYSTEM_FONT);
		String label = this.label;
		int tlw = getStringWidth(label,g);
		int lh = getFontHeight(g);
		
		int lw = tlw;
		
		if(tlw>getWw()/2)
		{
			int cls = tlw/label.length();
			int avs = getWw()/2/cls;
			String tlabel = label.substring(0,avs);
			label = tlabel.substring(0,tlabel.length()-3)+"...";
			lw = getStringWidth(label,g);
		}
		
		g.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		
		g.drawString(label, getX()+(getWw()/2-lw/2), yoffset+getY()+layout/2+lh/2);
		
		/// SHADOWS
		
		render(g, yoffset);
	}
	
	public void close()
	{
		Desktop.native_desktop.unregisterApplication(this);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWw() {
		return ww;
	}

	public void setWw(int ww) {
		this.ww = ww;
	}

	public int getWh() {
		return wh;
	}

	public void setWh(int wh) {
		this.wh = wh+layout;
	}

	public boolean isIn(Point p)
	{
		int mx = p.x;
		int my = p.y;
		return (mx>x&&mx<x+ww&&my>y&&my<y+wh);
	}
	
	public void loadIcon(String iconName) {
		String iconPath = Theme.get(iconName);
		BufferedImage icon = SVG.toImage(iconPath);
		this.icon=icon;
	}
	
	public void setIcon(BufferedImage i)
	{
		icon=i;
	}
	
	public void restoreDefaultIcon()
	{
		loadIcon("ICON::Unknown");
	}
	
	public BufferedImage getIcon()
	{
		return icon;
	}
	
	public String getCodeName()
	{
		return codename;
	}
	
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);
	public abstract void mouseDragged(MouseEvent e);
	public abstract void mouseMoved(MouseEvent e);
	public abstract void mousePressed(MouseEvent e);
	public abstract void mouseReleased(MouseEvent e);
	public abstract void mouseClicked(MouseEvent e);
}
