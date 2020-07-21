package com.example.IR.Project.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FileWord {

	@Id                                                  
	@GeneratedValue(strategy = GenerationType.AUTO)      
	int id ;   
	
	@ManyToOne                                                  
	FileModel file ; 
	@ManyToOne
	Word word;
	
	int Ranking;
	
	
	public FileWord(FileModel file, Word word, int ranking) {
		super();
		this.file = file;
		this.word = word;
		Ranking = ranking;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public FileModel getFile() {
		return file;
	}
	public void setFile(FileModel file) {
		this.file = file;
	}
	public Word getWord() {
		return word;
	}
	public void setWord(Word word) {
		this.word = word;
	}
	public int getRanking() {
		return Ranking;
	}
	public void setRanking(int ranking) {
		Ranking = ranking;
	}
	
	
	
	
}
