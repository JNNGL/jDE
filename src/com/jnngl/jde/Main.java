package com.jnngl.jde;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Initializing jDE system...");
		
		Screen.initScreen();
		
		if(Screen.isInit())
			Desktop.start();
		else
			stop("Error 10?. Screen init failed!");
	}
	
	public static void stop(String error_code)
	{
		new Exception(error_code).printStackTrace();
		System.exit(1);
	}
	
}
