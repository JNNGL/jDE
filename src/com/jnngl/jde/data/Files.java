package com.jnngl.jde.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Files {
	
	public static BufferedImage readImage(String path)
	{
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public static void writeImage(String path, BufferedImage image, String format)
	{
		try {
			ImageIO.write(image, format, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String read(String path)
	{
		try {
			StringBuilder sb = new StringBuilder();
			File file = new File(path);
			Scanner reader = new Scanner(file);
			while(reader.hasNextLine())
			{
				String data = reader.nextLine();
				sb.append(data);
			}
			reader.close();
			return sb.toString();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static void write(String path, String contents)
	{
		try {
			create(path);
			FileWriter writer = new FileWriter(path);
			writer.write(contents);
			writer.close();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void create(String path)
	{
		try {
			new File(path).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void mkdir(String path)
	{
		new File(path).mkdirs();
	}
	
}
