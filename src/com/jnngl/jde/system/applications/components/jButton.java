package com.jnngl.jde.system.applications.components;

import static com.jnngl.jde.Screen.dark;
import static com.jnngl.jde.Screen.glass;
import static com.jnngl.jde.graphics.FontUtils.getFontHeight;
import static com.jnngl.jde.graphics.FontUtils.getStringWidth;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.Map;

import com.jnngl.jde.Screen;
import com.jnngl.jde.graphics.FontUtils;
import com.jnngl.jde.system.appearance.jCursor;

public class jButton extends jLabel {

	private int round = Screen.round;
	private Color bgColor=null;
	private int ww=-1, wh=-1;
	private Runnable action;
	
	public jButton(jWindow window) {
		super(window);
	}
	
	public jButton(jWindow window, Font font) {
		super(window, font);
	}
	
	public Dimension getAutoScale()
	{
		String longestring = "";
		
		String[]lines = getText().split("\n");
		
		for(String line:lines)
		{
			if(line.length()>longestring.length())
				longestring=line;
		}
		
		int i = 20;
		if(round*2>0)
			i=0;
		
		return new Dimension(getStringWidth(longestring, getFont())+round*2+i, getFontHeight(getFont())*getText().split("\n").length+4);
	}
	
	public void proccedAutoScale()
	{
		Dimension as = getAutoScale();
		wh=(int)Math.round(as.getHeight());
		proccedAutoRound();
		as = getAutoScale();
		ww=(int)Math.round(as.getWidth ());
		wh=(int)Math.round(as.getHeight());
	}
	
	public void proccedAutoRound() {
		if(round>wh)
			round=wh;
	}
	
	public int getRound() {
		return round;
	}
	
	public void setRound(int value) {
		round=value;
	}
	
	public void setW(int value)
	{
		ww=value;
	}
	
	public void setH(int value)
	{
		wh=value;
	}
	
	public void setSize(int w, int h) {
		ww=w;
		wh=h;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void render(Graphics2D og, int yoffset, Map hints)
	{
		if(ww<=0||wh<=0)
			proccedAutoScale();
		
		Graphics2D g = (Graphics2D) og;
		
		if(bgColor!=null)
			if(glass)
				g.setColor(new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 150));
			else
				g.setColor(bgColor);
		if(!glass)
			if(dark)
				g.setColor(new Color(40,40,40));
			else
				g.setColor(new Color(235,235,235));
		else
			if(dark)
				g.setColor(new Color(30,30,30,150));
			else
				g.setColor(new Color(205,205,205,150));
		
		g.fillRoundRect(getPosition().x+getWindow().getX(),yoffset+getPosition().y+getWindow().getY(),ww,wh,round,round);
		
		if(getFontColor()!=null)
			g.setColor(getFontColor());
		else if(Screen.dark)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.BLACK);
		
		String[]lines=getText().split("\n");
		int co = FontUtils.getFontHeight(g);
		int cy = (int)(co/1.25f);
		
		String longestring = "";
		
		for(String line:lines)
		{
			if(line.length()>longestring.length())
				longestring=line;
		}
		
		int dx = ww/2-getStringWidth(longestring,getFont())/2;
		int dy = wh/2-(getFontHeight(getFont())*lines.length)/2;
		
		for(String line:lines)
		{
			
			g.drawString(line,dx+getWindow().getX()+getPosition().x,yoffset+dy+getWindow().getY()+getPosition().y+cy);
			cy+=co;
		}
		
		
	}
	
	@Override
	public void render(Graphics2D g, int yoffset)
	{
		render(g,yoffset,Map.of(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE, RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
				RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC, RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}
	
	public int getW() {
		return ww;
	}
	
	public int getH() {
		return wh;
	}
	
	public boolean isOnButton(Point p)
	{
		int mx = p.x;
		int my = p.y;
		return (mx>getWindow().getX()+getPosition().x&&mx<getWindow().getX()+getPosition().x+ww&&my>getWindow().getY()+getPosition().y&&my<getWindow().getY()+getPosition().y+wh);
	}
	
	public void setAction(Runnable action) {
		this.action=action;
	}
	
	public Runnable getAction() {
		return action;
	}
	
	public void executeAction()
	{
		action.run();
	}
	
	public boolean mouseMoved(MouseEvent e)
	{
		if(isOnButton(e.getPoint())) {
			jCursor.changeCursor(Cursor.HAND_CURSOR);
			return true;
		}
		return false;	
	}
	
	public void mouseClicked(MouseEvent e)
	{
		if(isOnButton(e.getPoint()))
		{
			jCursor.changeCursor(Cursor.HAND_CURSOR);
			executeAction();
		}
	}
}
