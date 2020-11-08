package com.jnngl.jde.system.applications.components;

import java.awt.Graphics2D;

public abstract class jComponent {
	
	protected jWindow window;
	
	public jComponent(jWindow w) {
		window=w;
	}
	
	public abstract void render(Graphics2D g, int yoffset);
	
}
