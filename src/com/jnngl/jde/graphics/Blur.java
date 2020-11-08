package com.jnngl.jde.graphics;

import static com.jnngl.jde.math.Clamp.clamp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public class Blur {
	
	public static BufferedImage motion(BufferedImage source, int range, int angle)
	{
		BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = b.createGraphics();
        
        for(int x = 0; x < source.getWidth(); x++)
        {
            for(int y = 0; y < source.getHeight(); y++) {
                
                int red[] = new int[range * 2], green[] = new int[range * 2], blue[] = new int[range * 2];
                int pixels[] = new int[range * 2];
                
                for(int i = 0; i < pixels.length; i++)
                {
                    pixels[i] = source.getRGB(clamp(x - clamp(range / 2, 0, range) + i, 0, source.getWidth() - 1), clamp(y - clamp(range / 2, 0, range) + (int)(i * Math.toRadians(angle)), 0, source.getHeight() - 1));
                    
                    red[i] = (pixels[i] >> 16) & 0xff;
                    green[i] = (pixels[i] >> 8) & 0xff;
                    blue[i] = (pixels[i]) & 0xff;
                }
                
                int red_t = 0, green_t = 0, blue_t = 0;
                
                for(int i = 0; i < pixels.length; i++)
                {
                    red_t += red[i];
                    green_t += green[i];
                    blue_t += blue[i];
                }
                
                int r = red_t / (range * 2);
                int gr = green_t / (range * 2);
                int bl = blue_t / (range * 2);
                
                //System.out.println(r + ", " + gr + ", " + bl);
                
                g.setColor(new Color(r, gr, bl));
                g.fillRect(x, y, 1, 1);
                
            }
        }
        g.dispose();
        
        return b;
	}
	
	public static BufferedImage blur(BufferedImage image, int factor)
	{
		float[] matrix = new float[400];
		for (int i = 0; i < 400; i++)
			matrix[i] = 0.1f;

	    BufferedImageOp op = new GaussianFilter(factor);
		return op.filter(image, null);

	}
	
}
