package com.jnngl.jde.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Bash {
	
	public String execute(String command) {
	    String output="";
	    System.out.println("Executing BASH command:\n   " + command);
	    Runtime r = Runtime.getRuntime();
	    // Use bash -c so we can handle things like multi commands separated by ; and
	    // things like quotes, $, |, and \. My tests show that command comes as
	    // one argument to bash, so we do not need to quote it to make it one thing.
	    // Also, exec may object if it does not have an executable file as the first thing,
	    // so having bash here makes it happy provided bash is installed and in path.
	    String[] commands = {"bash", "-c", command};
	    try {
	        Process p = r.exec(commands);

	        p.waitFor();
	        BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line = "";

	        while ((line = b.readLine()) != null) {
	            output+=line+"\n";
	        }

	        b.close();
	    } catch (Exception e) {
	        System.err.println("Failed to execute bash with command: " + command);
	        e.printStackTrace();
	    }
	    return output;
	}
	
}
