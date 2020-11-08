package com.jnngl.jde.system.applications.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Map;

import com.jnngl.jde.Screen;
import com.jnngl.jde.graphics.FontUtils;

public class jLabel extends jComponent {
	
	private Color fontColor=null;
	private Font font=Screen.SYSTEM_FONT;
	private String text="";
	private int x=0,y=(int) jWindow.layout;
	
	public jLabel(jWindow window, Font font)
	{
		super(window);
		this.font=font;
	}
	
	public jLabel(jWindow window)
	{
		super(window);
	}
	
	public void setText(String text)
	{
		this.text=text;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setFont(Font font)
	{
		this.font=font;
	}
	
	public Font getFont()
	{
		return font;
	}
	
	public void setWindow(jWindow window)
	{
		this.window=window;
	}
	
	public jWindow getWindow()
	{
		return window;
	}
	
	public void setPosition(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public Point getPosition()
	{
		return new Point(x,y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@SuppressWarnings("rawtypes")
	public void render(Graphics2D g, int yoffset, Map hints) {
		g.setFont(font);
		g.addRenderingHints(hints);
		
		if(fontColor!=null)
			g.setColor(fontColor);
		else if(Screen.dark)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.BLACK);
		
		String[]lines=text.split("\n");
		int co = FontUtils.getFontHeight(g);
		int cy = co/2;
		for(String line:lines)
		{
			
			g.drawString(line,window.getX()+x,yoffset+window.getY()+y+cy);
			cy+=co;
		}
	}
	
	@Override
	public void render(Graphics2D g, int yoffset)
	{
		render(g, yoffset, Map.of(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
	}

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}
	
}
