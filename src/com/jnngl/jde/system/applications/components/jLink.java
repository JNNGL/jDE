package com.jnngl.jde.system.applications.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class jLink extends jWindow {

	public jLink(String codename, BufferedImage icon) {
		super(0,0,"",0,0,codename, true);
		setIcon(icon);
	}

	@Override
	public void renderUI(Graphics g, int yoffset) {};
	
	@Override
	public void render(Graphics2D g, int yoffset) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}
	
}
