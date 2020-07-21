package com.example.IR.Project.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity

public class Word {

	@Id                                                  
	@GeneratedValue(strategy = GenerationType.AUTO)      
	int id ;                                             
	                                                     
	String word ;

	public Word(String word) {
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
