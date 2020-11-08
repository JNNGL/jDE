package com.jnngl.jde.system;

import static com.jnngl.jde.Screen.dark;
import static com.jnngl.jde.Screen.dockRound;
import static com.jnngl.jde.Screen.glass;
import static com.jnngl.jde.Screen.h;
import static com.jnngl.jde.Screen.w;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.jnngl.jde.Desktop;
import com.jnngl.jde.data.Files;
import com.jnngl.jde.graphics.Blur;
import com.jnngl.jde.math.Range;
import com.jnngl.jde.system.appearance.Theme;
import com.jnngl.jde.system.applications.components.jLink;
import com.jnngl.jde.system.applications.components.jWindow;

public class Dock {
	
	public Dock() {
		init();
	}
	
	public static int layout = h/14;
	
	public static BufferedImage blurground;
	
	public static int size = layout-20;
	public static int space = layout/11;
	public static int freeX = layout+space;
	
	public static int round=dockRound;
	
	public static Range<jWindow> range  = new Range<jWindow>();
	public static List<jWindow>  active = new ArrayList<jWindow>();
//	public static HashMap<jWindow, Integer> pos = new HashMap<jWindow, Integer>();
	
	public static int getXOf(jWindow app) {
		return layout+space+(size+space)*active.indexOf(app);
	}
	
	protected static void buildRange() {
		range.clear();
		for(jWindow app : active) {
			int x = getXOf(app);
			range.put(x, x+size, app);
		}
	}
	
	protected static void init() {
		active.clear();
		File config = new File("/etc/jde/system/config/dock/pined.cfg");
		if (config.exists()) {
			String contents = Files.read(config.getAbsolutePath());
			String[]lines=contents.split("\n");
			for(String line:lines) {
				String[]data = line.split(" ");
				if(data.length>=2) {
					String exec    =data[0];
					String icon    =line.substring(exec.length()+1);
					
					BufferedImage bicon = Theme.getSVG(icon);
					jLink link = new jLink(exec, bicon);
					active.add(link);
				}
			}
		}
	}
	
	public static void rebuild() {
		init();
		for(jWindow app:Desktop.applications) {
			// Test if non-contain in dock application
			if(!contains(app.getCodeName()))
				active.add(app);
		}
		buildRange();
	}
	
	public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
	    int w = image.getWidth();
	    int h = image.getHeight();
	    BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2 = output.createGraphics();
	    
	    // This is what we want, but it only does hard-clipping, i.e. aliasing
	    // g2.setClip(new RoundRectangle2D ...)

	    // so instead fake soft-clipping by first drawing the desired clip shape
	    // in fully opaque white with antialiasing enabled...
	    g2.setComposite(AlphaComposite.Src);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setColor(Color.WHITE);
	    g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
	    
	    // ... then compositing the image on top,
	    // using the white shape from above as alpha source
	    g2.setComposite(AlphaComposite.SrcAtop);
	    g2.drawImage(image, 0, 0, null);
	    
	    g2.dispose();
	    
	    return output;
	}
	
	public static void proccedAutoRound() {
		if(round>layout)
			round=layout;
	}
	
	public static void render(Graphics og)
	{
		round=dockRound;
		proccedAutoRound();
		Graphics2D g = (Graphics2D) og;
		if(glass&&blurground==null)
		{
//			blurground = new BufferedImage(w,layout,BufferedImage.TYPE_INT_ARGB);
			BufferedImage background = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
			Graphics2D tg = background.createGraphics();
			tg.setColor(Color.BLACK);
			tg.fillRect(0,0,w,layout);
			if(Wallpaper.isSet())
				Wallpaper.renderWallpaper(tg);
			
			blurground = makeRoundedCorner(Blur.blur(background.getSubimage(0,h-layout,w,layout),55),round);
		}
		
		if(glass)
			g.drawImage(blurground,0,h-layout,null);
		
//		if(glass&&Desktop.dock())
			g.drawImage(Desktop.dock, 0, h-layout, null);
		
		int opacity = 100;
		
		if(!glass)
			opacity=255;
		
		if(dark)
			g.setColor(new Color(50,50,50,opacity));
		else
			g.setColor(new Color(200,200,200,opacity));
		g.fillRoundRect(0,h-layout,w,layout,round,round);
		if(dark)
			g.setColor(new Color(0,0,0,opacity));
		else
			g.setColor(new Color(235,235,235,opacity-50));
		g.fillRoundRect(0,h-layout,layout,layout,round,round);
		
		//GradientUtils.linearGradient((Graphics2D) g, new Point(0,h-layout), new Point(0,h), new Color(0,0,0,layout/2<255? layout/2:255), new Color(0,0,0,0), new Rectangle(0,h-layout,w,layout));
		
		rebuild();
		
		for(jWindow app : active) {
			g.drawImage(app.getIcon(), freeX, h-layout+size/5, size, size, null);
			
			if(Desktop.applications.contains(app)) {
				if(dark)
					g.setColor(new Color(205,205,205,55));
				else 
					g.setColor(new Color(50,50,50,55));
				g.fillRoundRect(freeX,h-layout+size+size/4, size, size, 5,5);
			}
			
			freeX+=size+space;
		}
		freeX = layout+space;
	}
	
	public static boolean contains(String name) {
		for(jWindow app:active) {
			if(app.getCodeName().equals(name))
				return true;
		}
		return false;
	}
	
	public static boolean isInDock(Point p) {
//		int mx = p.x;
		int my = p.y;
		if(my>=h-layout)
			return true;
		return false;
	}
	
	public static void mouseClicked(MouseEvent e) {
		Point m = e.getPoint();
		if(isInDock(m)) {
			jWindow app;
			if((app=range.getValue(m.x))!=null)
				Desktop.toggleMinimize(app);
		}
	}
	
}
