package com.example.IR.Project.Query;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IR.Project.Model.FileModel;
import com.example.IR.Project.Model.FileWord;
import com.example.IR.Project.Model.WordModel;
import com.example.IR.Project.Service.FileWordService;
import com.example.IR.Project.Service.WordService;
import com.example.IR.Project.processing.DocumentsParse;

import javassist.expr.NewArray;

@RestController
public class QueryController {
	
	@Autowired
	WordService wordService ;
	
	@Autowired 
	FileWordService filewordService ; 
	
	DocumentsParse d= new DocumentsParse();
	public  List<String> Tokens =new ArrayList<String>();
	public  List<String> QueryStemm =new ArrayList<String>();
	public static List<String> releatedDoc = new ArrayList<String>();
	public static List<Double> QueryVector = new ArrayList<Double>();
	public static List<List<Double>> resultWithVSM = new ArrayList<List<Double>>();

	
	@RequestMapping("/Search/{name}")
	  public void Search(@PathVariable String name) throws IOException {
		
		String[] StringQuery = name.toLowerCase().split(" ");
		for(int i = 0 ; i < StringQuery.length;i++)
    		 {
				String s = d.removeUniqueCharactar(StringQuery[i]);
				if(s.length()>1)
					Tokens.add(s);
    		 }
		d.ReadStopWords("C:\\Users\\ramij\\Desktop\\IR\\IR Homework\\stop words.txt");
		List <FileWord> filesForThisWord=new ArrayList<FileWord>();
		Tokens.removeAll(DocumentsParse.StopWords);
		for (String word : Tokens)
    		{	
			QueryStemm.add(d.getStemmingWords(word));
    		System.out.println(d.getStemmingWords(word));
			if(wordService.findWordByName(d.getStemmingWords(word))!=null)
	    		{
	    		    filesForThisWord = filewordService.getFilesforWord(d.getStemmingWords(word));
	    			for(FileWord fw : filesForThisWord)
	    			{
	    				if(!exitfile(fw.getFile().getName()))
	    					releatedDoc.add(fw.getFile().getName());
	    			}
    			}
			
			
    		}
	
			List<FileWord> allfileWord = filewordService.getFilesforWord(QueryStemm);
			List<List<Double>>TFIDF = vectorSpaceModel(allfileWord,QueryStemm);
			
			System.out.println(TFIDF.size());
			System.out.println(TFIDF.get(0).size());
			System.out.println(TFIDF.get(1).size());
			for(int i = 0 ; i<TFIDF.size();i++ )
			{
				List<Double>DocVector=new ArrayList<Double>(); 

				for(int j = 0 ; j<TFIDF.get(i).size();j++)
				{
					DocVector.add (TFIDF.get(j).get(i));
				}
				resultWithVSM.add(DocVector);
			}
			//uniqueFiles(L)
			//TFIDFallWord.add(TFIDF);
		
		}


	

	private boolean exitfile(String name) {
		
		for(String s : releatedDoc)
		{
			if(s.equalsIgnoreCase(name))
				return true;
		}
		return false;
	}




	private List<List<Double>> vectorSpaceModel(List<FileWord> filesForThisWord, List<String> words) {
		
		
		List<List<Double>> AllTFiDF = new ArrayList<List<Double>>();
		for (String w : words) {
			List<FileWord> filesWord = new ArrayList<FileWord>();
			List<Double> TFIDF =new ArrayList<Double>();
			filesWord = filewordService.getFilesforWord(d.getStemmingWords(w));
		    
			for(FileWord f : filesForThisWord)
			{
				
				double tfidf = f.getRanking() * Math.log(423/filesWord.size());
				
				TFIDF.add(tfidf);
			}
			TFIDF = normalize (TFIDF);
			Collections.sort(TFIDF);
			Collections.reverse(TFIDF);
			AllTFiDF.add(TFIDF);
		}
		return AllTFiDF;
		
	}
	
	
	private List<Double> normalize(List<Double> TFIDF) {
		double total = 0 ; 
		
		for(double tfidf : TFIDF)
		{
			total += tfidf*tfidf;
		}
		
		total=Math.sqrt(total);
		
		for(double tfidf : TFIDF)
		{
			tfidf = tfidf/total;
		}
		
		
		return TFIDF;
	}

}
