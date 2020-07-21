package com.example.IR.Project.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IR.Project.Model.FileWord;
import com.example.IR.Project.Model.WordModel;
import com.example.IR.Project.Repository.FileWordRepository;

@Service
public class FileWordService {

	@Autowired 
	FileWordRepository FileWordRepo ;
	
	public void add (FileWord fw)
	{
		FileWordRepo.save(fw);
	}
	
	
	public FileWord findWord (WordModel word)
	{
		List<FileWord> allFileWord = FileWordRepo.findAll();
		
		for(FileWord f : allFileWord)
		{
			if(f.getWord().getWord().equalsIgnoreCase( word.getWord()))
					return f;
		}
		return null;
		
	}
	
	
	
	
	public void incrementRank (FileWord fileword)
	{
		fileword.setRanking(fileword.getRanking()+1);	
	}
	
}
