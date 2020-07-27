package com.example.IR.Project.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IR.Project.Model.FileModel;
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
	
	
	public List<FileWord> getFilesforWord (String word)
	{
		List<FileWord> index = FileWordRepo.findAll();
		List<FileWord> files = new ArrayList<FileWord>();
		for(FileWord f : index)
		{
			if(f.getWord().getWord().equalsIgnoreCase( word))
					files.add(f);
		}
		return files;
		
	}
	
	
	public List<FileWord> getFilesforWord (List<String> words)
	{
		List<FileWord> index = FileWordRepo.findAll();
		List<FileWord> files = new ArrayList<FileWord>();
		for(String w:words) {
			for(FileWord f : index)
			{
				if(f.getWord().getWord().equalsIgnoreCase(w))
				{
						files.add(f);
				}
			}
		}
		return files;
		
	}
	
	
	
	private boolean existfile(List<FileWord> f ,FileWord fileword) {

		for(FileWord fw : f)
		{
			if(fw.getFile().getName().equalsIgnoreCase(fileword.getFile().getName()))
				return true;
		}
		return false;
	}


	public void incrementRank (FileWord fileword)
	{
		fileword.setRanking(fileword.getRanking()+1);	
	}
	
}
