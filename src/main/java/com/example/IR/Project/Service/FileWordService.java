package com.example.IR.Project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IR.Project.Model.FileWord;
import com.example.IR.Project.Repository.FileWordRepository;

@Service
public class FileWordService {

	@Autowired 
	FileWordRepository FileWordRepo ;
	
	public void add (FileWord fw)
	{
		FileWordRepo.save(fw);
	}
	
	
}
