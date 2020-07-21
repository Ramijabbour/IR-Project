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

import javax.annotation.PostConstruct;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IR.Project.Model.FileModel;
import com.example.IR.Project.Model.FileWord;
import com.example.IR.Project.Model.WordModel;
import com.example.IR.Project.Repository.FileWordRepository;
import com.example.IR.Project.Service.FileService;
import com.example.IR.Project.Service.FileWordService;
import com.example.IR.Project.Service.WordService;



@RestController
public class DocumentsParse {

	@Autowired
	FileService fileService; 
	@Autowired
	FileWordService fileWordService; 
	@Autowired
	WordService wordService; 
	
	
	
	public static List<String> StopWords = new ArrayList<String>();
	public static List<String> TermsTokens =new ArrayList<String>();
	//public static ArrayList<String> indexTerms = new ArrayList<String>();
	public static List<Word> index = new ArrayList<Word>();
	 ArrayList<String> temp ;
	public static List<String> withdate = new ArrayList<String>();
	public static List<JSONObject> IrregularVerbs = new ArrayList<JSONObject>();
	public static List<List<String>> documents = new ArrayList<List<String>>();
	
	
	@RequestMapping("/file")
	  public void readFolder() throws IOException{
		String filePath="C:\\Users\\ramij\\Desktop\\IR\\IR Homework\\corpus 2";
			File[] allfiles = new File(filePath).listFiles();
			readFiles(allfiles);
		}
    

		public void  readFiles(File[] allfiles) throws IOException
		{
			BufferedReader in = null;
			StringBuilder sb = new StringBuilder();
	        ReadStopWords("C:\\Users\\ramij\\Desktop\\IR\\IR Homework\\stop words.txt");
        	GetIrregularVerbs();
		        for (File f : allfiles) 
		        {	
		        	FileModel fileModel = new FileModel(f.getName());
		        	fileService.addFile(fileModel);
		        	
		        	sb.setLength(0);
		        	System.out.println(" -------------- " + f.getName()+" ------------------");
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
			        	//System.out.println(fileContent);
			        	fileContent=fileContent.trim();
			        	//fileContent = fileContent.replaceAll("( )+", " ");
			        	String[] tokenes = fileContent.toString().toLowerCase().split(" ");

			        	for(int i = 0 ; i < tokenes.length;i++)
			        		removeUniqueCharactar(tokenes[i]);	
			        	System.out.println("step 1 ");
			        		TermsTokens.removeAll(StopWords);
			        		System.out.println("step 2 ");
			            	//buildIndexTermsList(fileModel);
			            	TermsTokens.removeAll(withdate);

			            	for (String word : TermsTokens)
			            		getStemmingWords(word,fileModel);
			            		
			            	
			            	/*for(String ss : indexTerms)
			            		System.out.println(ss);*/
			            	
		        	   } 
		       // temp = (ArrayList<String>)indexTerms.clone();	
		      //  documents.add(temp);	
		        //indexTerms.clear();
		        TermsTokens.clear();
		        withdate.clear();
		        in.close();
		        }     
		      
		        
		}
		

		public void removeUniqueCharactar(String token) 
		{
			String word ; 
			//|[a-zA-Z0-9]([a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@([a-zA-Z0-9-]+[a-zA-Z0-9]+)([.][a-zA-Z0-9-]+[a-zA-Z0-9]+)*)|[a-z]+[.][a-z]+|([a-z]+[-]*)+|[0-9]+
		    Pattern p = Pattern.compile("([a-zA-Z0-9][a-zA-Z0-9.!@#$%&'*+/=?^_`{|}~-]+)"); 
	        Matcher m1 = p.matcher(token); 			           
	        while (m1.find())
	        	//TermsTokens.add(m1.group());
	        	{	
	        	word = removeSemicolonCharactar(m1.group());
	        	TermsTokens.add(word);
	        	//checkEmailCharactar(m1.group());
	        	//check_Date(m1.group());
	        	
	        	}
		}
		
		
/*
		public void checkEmailCharactar(String token) 
		{
		    Pattern p = Pattern.compile("[a-zA-Z0-9]([a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@([a-zA-Z0-9-]+[a-zA-Z0-9]+)([.][a-zA-Z0-9-]+[a-zA-Z0-9]+)*)"); 
	        Matcher m1 = p.matcher(token);
	        if(m1.matches())	 
	        	{
	        		System.out.println("true");
	        	}
	     }
		
	*/	
		
		
		public String removeSemicolonCharactar(String token) 
		{
		    Pattern p = Pattern.compile("[a-zA-Z0-9]([a-zA-Z0-9.!@#$%&'*+/=?^_`{|}~-]+)*[a-zA-Z0-9]+|([a-zA-Z0-9][a-zA-Z0-9.!@#$%&'*+/=?^_`{|}~-]+[.][a-zA-Z0-9][a-zA-Z0-9.!@#$%&'*+/=?^_`{|}~-]+)+"); 
	        Matcher m1 = p.matcher(token);
	        while(m1.find())
	        	return m1.group();
	        return  "w";
	        			
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
			        JSONObject data = (JSONObject) parser.parse(new FileReader("C:\\Users\\ramij\\Desktop\\IR\\IR Homework\\Verbs.json"));//path to the JSON file.
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
		
		public void getStemmingWords(String token ,FileModel filemodel)
		{
			
			boolean ifIrregular = false ; 	
			for(JSONObject object : IrregularVerbs)
			{
				
				if(token.equalsIgnoreCase(object.get("Past-simple").toString()) || token.equalsIgnoreCase(object.get("Past-Participle").toString()))
				{
					//indexTerms.add(object.get("Base").toString());
					WordModel  wordtoDataBase = new WordModel(object.get("Base").toString());
					FileWord fileWord = fileWordService.findWord(wordtoDataBase);
					if(fileWord==null) {
    				wordService.addWord(wordtoDataBase);
    				FileWord newFileWord = new FileWord(filemodel,wordtoDataBase , 1);
    				fileWordService.add(newFileWord);
					}
					else 
					{
						fileWordService.incrementRank(fileWord);
						
					}
					
					ifIrregular=true ; 
				}
				
			}
			
			if(!ifIrregular)
			{
				Porter p = new Porter ();
				String Stemm_word = p.stripAffixes(token);
				//indexTerms.add(Stemm_word);
				WordModel  wordtoDataBase = new WordModel(Stemm_word);
				FileWord fileWord = fileWordService.findWord(wordtoDataBase);
				if(fileWord==null) {
				wordService.addWord(wordtoDataBase);
				FileWord newFileWord = new FileWord(filemodel,wordtoDataBase , 1);
				fileWordService.add(newFileWord);
				wordService.addWord(wordtoDataBase);
				}
				else 
				{
					fileWordService.incrementRank(fileWord);	
				}
				
				
			
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

		public void buildIndexTermsList()
		{
        	
        	for(int i = 0 ; i<TermsTokens.size();i++)
        	{			        		
        		if(i==TermsTokens.size()-1)
        		{
        			if(check_Date(TermsTokens.get(i)))
    				{
        				String date = TermsTokens.get(i).replaceAll("[-]|[.]", "/");
    					//indexTerms.add(date);
        				WordModel  wordtoDataBase = new WordModel(date);
        				wordService.addWord(wordtoDataBase);
        				
    					withdate.add(TermsTokens.get(i));
        				//TermsTokens.remove(i);
    				}
        		}
        		else if(i==TermsTokens.size()-2)
        		{ 
        			String testDate2 = TermsTokens.get(i) +TermsTokens.get(i+1);
        			if(check_Date(testDate2))
    				{ 
    					System.out.println(testDate2 + " ------------------------" );
    					String date = "1/"+TermsTokens.get(i)+"/"+TermsTokens.get(i+1);
    					//indexTerms.add(date);
    			    	WordModel  wordtoDataBase = new WordModel(date);
        				wordService.addWord(wordtoDataBase);
    					withdate.add(TermsTokens.get(i));
    					withdate.add(TermsTokens.get(i+1));

    					//TermsTokens.remove(i);TermsTokens.remove(i+1);	
    				}
        		}
        		else 
        		{
		        		String testDate1 = TermsTokens.get(i) +" "+TermsTokens.get(i+1)+" "+TermsTokens.get(i+2) ;
	    				String testDate2 = TermsTokens.get(i)+" "+TermsTokens.get(i+1);
	    				if(check_Date(testDate1))
	    				{   
	    					System.out.println(testDate1+"*************************************");
	    					String date = TermsTokens.get(i)+"/"+TermsTokens.get(i+1)+"/"+TermsTokens.get(i+2);
	    					//indexTerms.add(date);
	    					WordModel  wordtoDataBase = new WordModel(date);
	        				wordService.addWord(wordtoDataBase);
	    					//TermsTokens.remove(i);TermsTokens.remove(i+1);TermsTokens.remove(i+2);
	    					withdate.add(TermsTokens.get(i));
	    					withdate.add(TermsTokens.get(i+1));
	    					withdate.add(TermsTokens.get(i+2));

	    					i=i+1;
	    				}
	    				else if(check_Date(testDate2))
	    				{
	    					System.out.println(testDate2+"++++++++++++++++++++++++++++++++++++++++");
	    					String date = "1/"+TermsTokens.get(i)+"/"+TermsTokens.get(i+1);
	    					//indexTerms.add(date);
	    					WordModel  wordtoDataBase = new WordModel(date);
	    					
	        				wordService.addWord(wordtoDataBase);
	    					withdate.add(TermsTokens.get(i));
	    					withdate.add(TermsTokens.get(i+1));

	    					//TermsTokens.remove(i);TermsTokens.remove(i+1);
	    					//i=i+1; 
	    				}
	    				else if (check_Date(TermsTokens.get(i)))
	    				{
	    					String date = TermsTokens.get(i).replaceAll("[-]|[.]", "/");
	    					//indexTerms.add(date);
	    					WordModel  wordtoDataBase = new WordModel(date);
	        				wordService.addWord(wordtoDataBase);
	    					//TermsTokens.remove(i);
	    					withdate.add(TermsTokens.get(i));

	    					
	    				}
	        		}
        		}
		}
		
		public Word existWord(List<Word> word , String name)
		{
			for(Word w : word)
			{
				if(w.getWord().equalsIgnoreCase(name))
					return w;
			}
			
			return null ; 
		}
		
		public boolean existDoc(List<Occurence> occ , int name)
		{
			for(Occurence w : occ)
			{
				if(w.getDoc()==name)
					return true;
			}
			
			return false ; 
		}
		
		
		public void CreateIndex()
		{
			TFIDFCalculator tfidf  =new TFIDFCalculator();
			System.out.println("size List : "+documents.size());
			for(int i = 0 ; i< documents.size() ; i++) {
				
				for(String s : documents.get(i))
				{
					double weghit = tfidf.tfIdf(documents.get(i), documents, s);
					Word w = new Word(s,i,weghit);
					
					if(existWord(index, w.getWord())==null)
					{	//wordService.addword(w);
						index.add(w);
					}
					else 
					{	
						Word temp = existWord(index, w.getWord());
										
						if(!existDoc(temp.getOccurences(), i)) {
							temp.insert(i, weghit);
							index.remove(temp);
							index.add(temp);
						}

					}
				}
			}
			
			for(Word w: index)
			{	System.out.println(w.getWord() );
				for(Occurence c : w.getOccurences())
				System.out.println (" , " + c.getDoc() +" , "+ c.getFre() );
			}
			System.out.println("---------------------------------------------");
		}
		
		

		
}
