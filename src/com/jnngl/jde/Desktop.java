package com.jnngl.jde;

import static com.jnngl.jde.Screen.h;
import static com.jnngl.jde.Screen.initOther;
import static com.jnngl.jde.Screen.w;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jnngl.jde.graphics.Blur;
import com.jnngl.jde.process.Bash;
import com.jnngl.jde.system.Dock;
import com.jnngl.jde.system.Wallpaper;
import com.jnngl.jde.system.appearance.Theme;
import com.jnngl.jde.system.appearance.jCursor;
import com.jnngl.jde.system.applications.components.jSample;
import com.jnngl.jde.system.applications.components.jWindow;

public class Desktop extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	
	public static Desktop native_desktop;
	
	protected static jWindow parent;
	private static final long serialVersionUID = 1L;
	public static JFrame session = null;
	public static Graphics graphical_session; 
	
	public static jWindow getjParent() {
		return parent;
	}
	
	public static void start()
	{
		new Desktop().init();
	}
	
	private static Point initialOffset;
	
	@SuppressWarnings("static-access")
	public void init()
	{
		if(session!=null)
		{
			System.err.println("jDE Session is already started! What do you want to do?");
			return;
		}
		
		native_desktop=this;
		
		Theme.loadTheme("papirus");
		
		if(Wallpaper.isSet())
			Wallpaper.loadWallpaper();
		
		JFrame session = new JFrame("Session");
		session.setFocusable(true);
		//session.setFocusableWindowState(false);
		//session.addFocusListener(this);
		session.setUndecorated(true);
//		session.setSize(w/=1.5f, h/=1.5f);
		session.setSize(w,h);
		initOther();
		
		// Initialize Applications
		new jSample();
		new jSample();
		
		session.setAlwaysOnTop(false);
		session.setResizable(false);
		session.setLocationRelativeTo(null);
		session.setDefaultCloseOperation(3);
		session.add(this);
		this.session=session;
		session.setVisible(true);
//		session.setOpacity(1f);
		session.setBackground(Color.BLACK);
		session.repaint();
		         
		addMouseMotionListener(native_desktop);
		addMouseListener(native_desktop);
		addKeyListener(native_desktop);

				
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
				parent = sortApplications(e.getPoint());
				if(parent==null)
					return;
				
				initialOffset = new Point(e.getX()-parent.getX(), e.getY()-parent.getY());
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter () {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(applications.isEmpty())
					return;
				
				if(applications.get(0)==null)
					return;
				
				if(Dock.isInDock(e.getPoint()))
					parent=null;
				
				if(parent==null)
					return;
				
				parent.setX(e.getX()-initialOffset.x);
				parent.setY(e.getY()-initialOffset.y);
				native_desktop.repaint();
			}
		});
		
	}
	
	public static void shutdown()
	{
		System.out.println("Shutting down... Goodbye!");
		System.out.println(new Bash().execute("shutdown now"));
	}
	
	public static void reboot()
	{
		System.out.println("Restarting... Goodbye!");
		System.out.println(new Bash().execute("shutdown -r now"));
	}
	
	public static void sleep()
	{
		System.out.println("Switching to sleeping mode...");
		System.out.println(new Bash().execute("systemctl suspend || pm-suspend"));
	}
	
	public static void hibernate()
	{
		System.out.println("Switching to hibernate mode...");
		System.out.println(new Bash().execute("systemctl hibernate || pm-hibernate"));
	}
	
	
	public static List<jWindow> applications = new ArrayList<jWindow>();
	
	static BufferedImage desktop = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
	
	public static boolean dock()
	{
		for(jWindow app:applications)
		{
			if(app.getY()+app.getWh()+jWindow.length>h-Dock.layout&&Screen.glass)
				return true;
		}
		return false;
	}
	
	public static BufferedImage dock = new BufferedImage(w,Dock.layout,BufferedImage.TYPE_INT_ARGB);
	
	public static void render(Graphics g)
	{
		g.drawOval(0, 0, 200, 200);
		
		// Draw Desktop
		g.setColor(Color.BLACK);
		g.fillRect(0,0,w,h);
		if(Wallpaper.isSet())
			Wallpaper.renderWallpaper(g);
		else
			System.out.println("Meh... Wallpaper link (/system/wallpaper) not found...");
		
		// Handle Applications
		for(int i = 0; i < applications.size(); i++)
		{
			applications.get(applications.size()-i-1).renderUI(g);
			
		}
		
		if(dock())
		{
			Graphics2D g2d = dock.createGraphics();
			
			for(int i = 0; i < applications.size(); i++)
			{
				applications.get(applications.size()-i-1).renderUI(g2d, Dock.layout-h);
			}
			
			dock=Blur.blur(dock,2);
			
			
		}
		
		// Draw Dock
		Dock.render(graphical_session);
		if(dock())
			dock =new BufferedImage(w,Dock.layout,BufferedImage.TYPE_INT_ARGB);

	
	}
	
	public static void render()
	{
		render(graphical_session);
	}
	
	public void registerApplication(jWindow app)
	{
		applications.add(app);
		native_desktop.repaint();
	}
	
	public void unregisterApplication(jWindow app)
	{
		applications.remove(app);
		native_desktop.repaint();
	}
	
	public void paintComponent(Graphics og)
	{
		
		// Initialize Graphics2D
		Graphics2D g = (Graphics2D) og;
		
		// Add RenderingHints
		//g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		// Initialize Graphical Session
		graphical_session = g;
		
		// Call Desktop Draw
		render();
		
	}
	
	public static jWindow sortApplications(Point c) {
		int mx = c.x;
		int my = c.y;
		for(jWindow app:applications) {
			if(mx>app.getX()&&mx<app.getX()+app.getWw()&&my>app.getY()&&my<app.getY()+app.getWh()) {
				rebuildAppList(app);
				return app;
			}
		}
		return null;
	}
	
	public static jWindow getApplicationAt(Point p)
	{
		if(Dock.isInDock(p))
			return null; 
		int mx = p.x;
		int my = p.y;
		for(jWindow app:applications) {
			if(mx>app.getX()&&mx<app.getX()+app.getWw()&&my>app.getY()&&my<app.getY()+app.getWh()) {
				return app;
			}
		}
		return null;
	}
	
	public static void rebuildAppList(jWindow app)
	{
		List<jWindow> temp = new ArrayList<jWindow>();
		temp.add(app);
		app.setFocused(true);
		for(jWindow capp:applications)
		{
			if(capp!=app)
				temp.add(capp);
			capp.setFocused(false);
		}
		applications=temp;
		native_desktop.repaint();
	}
	
	public jWindow getFocusedApp()
	{
		return applications.get(0);
	}
	
	public static void minimize(jWindow app)
	{
		app.minimize();
		native_desktop.repaint();
	}
	
	public static void unminimize(jWindow app) 
	{
		app.unminimize();
		native_desktop.repaint();
	}
	
	public static void toggleMinimize(jWindow app)
	{
		if(app.isMinimized())
			unminimize(app);
		else
			minimize(app);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		jWindow i = getFocusedApp();
		if(i==null)
			return;i.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		jWindow i = getFocusedApp();
		if(i==null)
			return;
		i.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Not used
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(e.getPoint()==null) {
			jCursor.changeCursor(Cursor.DEFAULT_CURSOR);
			return;
		}
		jWindow i = getApplicationAt(e.getPoint());
		if(i==null) {
			jCursor.changeCursor(Cursor.DEFAULT_CURSOR);
			return;
		}
		i.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(e.getPoint()==null) {
			jCursor.changeCursor(Cursor.DEFAULT_CURSOR);
			return;
		}
		jWindow i = getApplicationAt(e.getPoint());
		if(i==null) {
			jCursor.changeCursor(Cursor.DEFAULT_CURSOR);
			return;
		}
		i.mouseMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Dock.mouseClicked(e);
		if(e.getPoint()==null) {
			jCursor.changeCursor(Cursor.DEFAULT_CURSOR);
			return;
		}
		jWindow i = getApplicationAt(e.getPoint());
		if(i==null) {
			jCursor.changeCursor(Cursor.DEFAULT_CURSOR);
			return;
		}
		i.mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Not used
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Not used
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getPoint()==null) {
			jCursor.changeCursor(Cursor.DEFAULT_CURSOR);
			return;
		}
		jWindow i = getApplicationAt(e.getPoint());
		if(i==null) {
			jCursor.changeCursor(Cursor.DEFAULT_CURSOR);
			return;
		}
		i.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getPoint()==null)
			return;
		jWindow i = getApplicationAt(e.getPoint());
		if(i==null)
			return;
		
i.mouseReleased(e);
	}
	
}
