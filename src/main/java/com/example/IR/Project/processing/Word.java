package com.example.IR.Project.processing;


import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Word{
	

		public int ID ;
		private String word;
		private ArrayList<Occurence> occurences;
		Word(){
			occurences = new ArrayList<Occurence>();
		}
		Word(String w, int d,double tfidf){
			word = w;
			occurences = new ArrayList<Occurence>();
			occurences.add(new Occurence(d,tfidf));
		}
		public String toString() {
			String tmp = word;
			for (int i = 0; i < occurences.size(); i++)
				tmp += " " + occurences.get(i).toString();
			return tmp;
		}
		
		public void insert(int d,double weghit){
			this.occurences.add(new Occurence(d,weghit));
		}
		
	/*	public void incr(int index){
			int curfr = this.occurences.get(index).getFre()+1;
			this.occurences.get(index).setFre(curfr);
		}
		*/
		public int indexOf(int d){
			for (int i = 0; i < this.occurences.size(); i++)
				if (this.occurences.get(i).getDoc() == d)
					return i;
			return -1;
		}
		public String getWord() {
			return word;
		}
		public void setWord(String word) {
			this.word = word;
		}
		public ArrayList<Occurence> getOccurences() {
			return occurences;
		}
		public void setOccurences(ArrayList<Occurence> occurences) {
			this.occurences = occurences;
		}
	}

class Occurence{
	private int doc;
	private double fre;
	public int getDoc() {
		return doc;
	}
	public void setDoc(int doc) {
		this.doc = doc;
	}
	public double getFre() {
		return fre;
	}
	public void setFre(double fre) {
		this.fre = fre;
	}
	public Occurence(int doc, double fre) {
		super();
		this.doc = doc;
		this.fre = fre;
	}
	public Occurence() {
		super();
	}
	public String toString() {
		return this.doc + "_" + this.fre;
	}
	
	
	
}