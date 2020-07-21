package com.example.IR.Project.Service;

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
	
	
}
