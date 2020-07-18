package com.example.IR.Project.processing;

public class IrregularVerbs {

	
	String Past_Participle ; 
	String Past_simple;
	String Base ;
	
	public IrregularVerbs(String past_Participle, String past_simple, String base) {
		super();
		Past_Participle = past_Participle;
		Past_simple = past_simple;
		Base = base;
	}
	public IrregularVerbs() {
		super();
	}
	public String getPast_Participle() {
		return Past_Participle;
	}
	public void setPast_Participle(String past_Participle) {
		Past_Participle = past_Participle;
	}
	public String getPast_simple() {
		return Past_simple;
	}
	public void setPast_simple(String past_simple) {
		Past_simple = past_simple;
	}
	public String getBase() {
		return Base;
	}
	public void setBase(String base) {
		Base = base;
	} 
	
	
}
