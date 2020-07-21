package com.example.IR.Project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IR.Project.Model.FileModel;
import com.example.IR.Project.Repository.FileRepository;

@Service
public class FileService {

	
	@Autowired 
	FileRepository fileRepo ; 
	
	public void addFile(FileModel f )
	{
		fileRepo.save(f);
	}
	
	
}
