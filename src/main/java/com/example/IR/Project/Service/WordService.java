package com.example.IR.Project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IR.Project.Model.WordModel;
import com.example.IR.Project.Repository.WordRepository;

@Service
public class WordService {

	
	@Autowired 
	WordRepository WordRepo ; 
	
	public void addWord(WordModel w )
	{
		WordRepo.save(w);
	}
	
	public WordModel findWordByName (String name)
	{
		List<WordModel> allWord =WordRepo.findAll(); 
		
		for(WordModel w : allWord)
		{
			if(w.getWord().equalsIgnoreCase(name))
				return w;
		}
		return null;
	}
	
}
