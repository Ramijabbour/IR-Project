package com.example.IR.Project.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.commons.lang3.StringUtils;



public class DocumentsParse {

	
	public static List<String> TermsTokens;
	public static List<String> StemmingWords = new ArrayList<String>();; 
	public static List<String> StopWords = new ArrayList<String>();
	public static List<JSONObject> IrregularVerbs = new ArrayList<JSONObject>();
	

	
	  public void readFolder(String filePath) throws IOException{
			File[] allfiles = new File(filePath).listFiles();
			readFiles(allfiles);
		}
	
	  
		public void  readFiles(File[] allfiles) throws IOException
		{
			TermsTokens = new ArrayList<String>();
			BufferedReader in = null;
			StringBuilder sb = new StringBuilder();
	        ReadStopWords("C:\\Users\\Khalil\\Downloads\\Compressed\\IR Homework\\stop words.txt");
        	GetIrregularVerbs();

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
			        	String fileContent = sb.toString();	
			        	fileContent=fileContent.trim();
			        	//fileContent = fileContent.replaceAll("( )+", " ");
			        	String[] tokenes = fileContent.toString().toLowerCase().split(" ");
			        	for(int i = 0 ; i < tokenes.length;i++)
			        		removeUniqueCharactar(tokenes[i]);
			        		
			        	TermsTokens.removeAll(StopWords);
			        	for(int i = 0 ; i<TermsTokens.size()-2;i++)
			        	{
			        		System.out.println(TermsTokens.get(i));
			        		
			        		if(StringUtils.isNumeric(TermsTokens.get(i)))
			        		{
			        				System.out.println("step 1 -----------------------");
			        				String testDate1 = TermsTokens.get(i) +TermsTokens.get(i+1)+TermsTokens.get(i+2) ;
			        				String testDate2 = TermsTokens.get(i) +TermsTokens.get(i+1);
			        				
				        			if(!check_Date(testDate1))
				        			{
				        				check_Date(testDate2);
				        			}
			        			
			        		}
			        		else 
			        		{
			        			if(!check_Date(TermsTokens.get(i)))
			        			{
			        				String testDate2 = TermsTokens.get(i)+" "+TermsTokens.get(i+1);
			        				check_Date(testDate2);

			        			}
			        		}
			        		
			        		//getStemmingWords(word); 
			        		//System.out.println(word);
		        	   }
			        }
		        }    
		}

		public void removeUniqueCharactar(String token) 
		{
			//|[a-zA-Z0-9]([a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@([a-zA-Z0-9-]+[a-zA-Z0-9]+)([.][a-zA-Z0-9-]+[a-zA-Z0-9]+)*)|[a-z]+[.][a-z]+|([a-z]+[-]*)+|[0-9]+
		    Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9.!@#$%&'*+/=?^_`{|}~-]+"); 
	        Matcher m1 = p.matcher(token); 			           
	        while (m1.find())
	        	//TermsTokens.add(m1.group());
	        	{System.out.println(m1.group());
	        	removeSemicolonCharactar(m1.group());
	        	checkEmailCharactar(m1.group());
	        	//check_Date(m1.group());
	        	
	        	}
		}
		
		

		public void checkEmailCharactar(String token) 
		{
		    Pattern p = Pattern.compile("[a-zA-Z0-9]([a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@([a-zA-Z0-9-]+[a-zA-Z0-9]+)([.][a-zA-Z0-9-]+[a-zA-Z0-9]+)*)"); 
	        Matcher m1 = p.matcher(token);
	        if(m1.matches())	 
	        	{
	        		System.out.println("true");
	        	 }
	     }
		
		
		
		
		public void removeSemicolonCharactar(String token) 
		{
		    Pattern p = Pattern.compile("[a-zA-Z0-9]([a-zA-Z0-9!@#$%&'*+/=?^_`{|}~-]+)*[a-zA-Z0-9]+|([a-zA-Z0-9][a-zA-Z0-9.!@#$%&'*+/=?^_`{|}~-]+[.][a-zA-Z0-9][a-zA-Z0-9.!@#$%&'*+/=?^_`{|}~-]+)+"); 
	        Matcher m1 = p.matcher(token);
	        if(!m1.matches())
	        {
	        		System.out.println("true ----------- ");
	        	 }
	     }
		
		
		
		public void ReadStopWords(String path) throws IOException
		{
			File f = new File(path);
			BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(f)));   
			String s = null;   
    		while ((s = b.readLine()) != null) 
    		{   
    			if(!s.equalsIgnoreCase(""))
    			StopWords.add(s.toLowerCase());
    		}     
    		b.close();
		}
		
		public void GetIrregularVerbs()
		{
			 try {
			        JSONParser parser = new JSONParser();
			        //Use JSONObject for simple JSON and JSONArray for array of JSON.
			        JSONObject data = (JSONObject) parser.parse(new FileReader("C:\\Users\\Khalil\\Downloads\\Compressed\\IR Homework\\Verbs.json"));//path to the JSON file.
			        JSONArray array = (JSONArray) data.get("verbs");
			        for (int i = 0 ; i<array.size() ; i++) {
			        	IrregularVerbs.add((JSONObject) array.get(i));
			        	
			        }
			     }
			 catch (IOException | ParseException e)
			 {
			        e.printStackTrace();
			 }
			
		}
		
		public void getStemmingWords(String token)
		{
			
			boolean ifIrregular = false ; 
			for(JSONObject object : IrregularVerbs)
			{
				if(token.equalsIgnoreCase(object.get("Past-simple").toString()) || token.equalsIgnoreCase(object.get("Past-Participle").toString()))
				{

					StemmingWords.add(object.get("Base").toString());
					ifIrregular=true ; 
				}
				
			}
			
			if(!ifIrregular)
			{
				Porter p = new Porter ();
				String Stemm_word = p.stripAffixes(token);
				StemmingWords.add(Stemm_word);
			}
				
		}
		
		public boolean check_Date(String word)
		{
	         String regex1 = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
	         
	         String regex2 = "^[0-3]?[0-9]\\.[0-3]?[0-9]\\.(?:[0-9]{2})?[0-9]{2}$";
	         
	         String regex3 = "^[0-3]?[0-9]-[0-3]?[0-9]-(?:[0-9]{2})?[0-9]{2}$";
	         //String regex4 = "^[0-3]?[0-9]-[0-3]?[0-9]-(?:[0-9]{2})?[0-9]{2}$";
	       //  String regex40 = "^[0-3]?[0-9]-\b(?:Jan(?:uary)?|Feb(?:ruary)?|Dec(?:ember)?)(?:[0-9]{2})?[0-9]{2}$";
	        // String regex4 = "^[0-3]?[0-9]/[a-zA-Z]+/(?:[0-9]{2})?[0-9]{2}$";
	         String regex5 = "^[0-3]?[0-9]?\\ ?(?:jan(?:uary)?|feb(?:ruary)?|mar(?:ch)?|apr(?:il)?|may|jun(?:e)?|jul(?:y)?|aug(?:ust)?|sep(?:temper)?|oct(?:ober)?|nov(?:ember)?|dec(?:ember)?)\\ ?(?:[0-9]{2})[0-9]{2}$";
	         String regex4 = "^[0-3]?[0-9]-(?:jan(?:uary)?|feb(?:ruary)?|mar(?:ch)?|apr(?:il)?|may|jun(?:e)?|jul(?:y)?|aug(?:ust)?|sep(?:temper)?|oct(?:ober)?|nov(?:ember)?|dec(?:ember)?)-?((?:[0-9]{2})[0-9]{2}$)?";

	         
	         List<String> regexs = new ArrayList<>();
	         regexs.add(regex1);
	         regexs.add(regex2);
	         regexs.add(regex3);
	         regexs.add(regex4);
	         regexs.add(regex5);
	         //regexs.add(regex40);
	          
	         for(String s: regexs){
	        	 Pattern pattern = Pattern.compile(s);
		            
	              Matcher matcher = pattern.matcher(word);
	              if(matcher.matches()){
	                System.out.println(word +" : "+ matcher.matches());
	              return true ; 
	           }
	         }
	         return false ; 
		}


		
}
