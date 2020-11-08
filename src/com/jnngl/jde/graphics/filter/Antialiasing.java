package com.jnngl.jde.graphics.filter;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Antialiasing extends BufferedHint{

	public static BufferedImage antialiaze(BufferedImage s)
	{
		return applyHints(s, Map.of(
				RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY,
				RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
	}
	
}
