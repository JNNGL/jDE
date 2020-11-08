package com.jnngl.jde.graphics.svg;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;

public class SVG {
	
	@SuppressWarnings("deprecation")
	public static BufferedImage toImage(String path)
	{
		try {
			File f = new File(path);
			SVGUniverse svgu = new SVGUniverse();
			SVGDiagram svgd = svgu.getDiagram(svgu.loadSVG(f.toURL()));
			BufferedImage bi = new BufferedImage((int)svgd.getWidth(),(int)svgd.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = bi.createGraphics();
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			svgd.render(g);
			return bi;
		} catch (MalformedURLException | SVGException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
