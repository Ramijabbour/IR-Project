package com.example.IR.Project.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DocumentsParse {

	
	  public void readFolder(String filePath) throws IOException{
			File[] allfiles = new File(filePath).listFiles();
			readFiles(allfiles);
		}
	
	  
		public void  readFiles(File[] allfiles) throws IOException
		{

			 BufferedReader in = null;
			 StringBuilder sb = new StringBuilder(); 
		        for (File f : allfiles) 
		        {
		        	if (f.getName().endsWith(".txt"))
		        	{
		        		System.out .println("Parsing document ");
		        		in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));   
		        		String s = null;   
		        		while ((s = in.readLine()) != null) 
		        		{   
		        			sb.append(s + "\n");   
		        		}      		        	
		        		
		        	String [] tokenes = sb.toString().toLowerCase().split(" "); 
		        	Stemmer stem = new Stemmer();
		        	 stem.stem();
		        	}
		        }
		        
		}
}
