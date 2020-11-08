package com.jnngl.jde.system.applications.components;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import com.jnngl.jde.Screen;
import com.jnngl.jde.system.appearance.Theme;

public class jIconButton extends jButton {

	private BufferedImage icon=null;
	private boolean undec = false;
	
	public jIconButton(jWindow w) {
		super(w);
	}
	
	public void setIcon(BufferedImage icon) {
		this.icon=icon;
	}
	
	public BufferedImage getIcon() {
		return icon;
	}
	
	public void setUndecorated(boolean value) {
		undec=value;
	}
	
	public boolean getUndecorated() {
		return undec;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public void render(Graphics2D g, int yoffset, Map hints) {
		g.setRenderingHints(hints);
		render(g,yoffset);
	}
	
	@Override
	public Dimension getAutoScale() {
		int w = icon.getWidth()+6;
		int h = icon.getHeight()+6;
		return new Dimension(w,h);
	}
	
	@Override
	public void proccedAutoScale() {
		Dimension size = getAutoScale();
		setW(size.width);
		setH(size.height);
	}

	@Override
	public void render(Graphics2D g, int yoffset) {
		setRound(getAutoRound());
		if(!undec) {
			if(getBgColor()!=null)
				g.setColor(getBgColor());
			else
				g.setColor(Theme.getDark());
			g.fillRoundRect(getWindow().getX()+getX()+2, getWindow().getY()+getY()+yoffset+2, getW()-3, getH()-3, getRound()-2, getRound()-2);
			g.drawRoundRect(getWindow().getX()+getX()  , getWindow().getY()+getY()+yoffset  , getW()  , getH(),   getRound()  , getRound()  );
		}
		if(icon!=null)
			g.drawImage(icon, getX()+getW()/2-icon.getWidth()/2, getY()+yoffset+getH()/2-icon.getHeight()/2, null);
	}
	
	public int getAutoRound() {
		int round = Screen.round;
		if(round>10)
			round=10;
		return round;
	}
	
	@Override
	public void proccedAutoRound() {
		setRound(getAutoRound());
	}

}
