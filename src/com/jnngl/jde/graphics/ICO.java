package com.jnngl.jde.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.image4j.codec.ico.ICODecoder;
import net.sf.image4j.codec.ico.ICOEncoder;

public class ICO {

	public static void write(BufferedImage i, String dest)
	{
		try {
			ICOEncoder.write(i, new File(dest));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<BufferedImage> read(String path)
	{
		try {
			return ICODecoder.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static BufferedImage getLargest(String path)
	{
		return read(path).get(0);
	}
	public static BufferedImage getSmallest(String path)
	{
		List<BufferedImage> li = read(path);
		return li.get(li.size()-1);
	}
	
}
