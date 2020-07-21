package com.example.IR.Project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IR.Project.Model.Word;
import com.example.IR.Project.Repository.WordRepository;

@Service
public class WordService {

	
	@Autowired 
	WordRepository WordRepo ; 
	
	public void addWord(Word w )
	{
		WordRepo.save(w);
	}
	
	
}
