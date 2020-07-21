package com.example.IR.Project.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity

public class WordModel {

	@Id                                                  
	@GeneratedValue(strategy = GenerationType.AUTO)      
	int id ;                                             
	                                                     
	String word ;

	public WordModel(String word) {
		super();
		this.word = word;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	

}
