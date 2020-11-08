package com.jnngl.jde.graphics.filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

public class BufferedHint {

	@SuppressWarnings("rawtypes")
	public static BufferedImage applyHints(BufferedImage s, Map hints)
	{
		// Create destImage;
		BufferedImage r = new BufferedImage(s.getWidth(), s.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		// Apply hints
		Graphics2D g = r.createGraphics();
		g.addRenderingHints(hints);
		g.drawImage(s,0,0,null);
		
		// Done!
		return r;
		
	}
	
}
