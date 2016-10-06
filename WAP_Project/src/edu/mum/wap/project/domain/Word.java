package edu.mum.wap.project.domain;

import java.util.List;

public class Word {

	private String term;
	
	private List<Definition> definitions;
	
	public Word(String word){
		this.term = word;
	}
	
	public Word(String term, List<Definition> defs){
		this.term = term;
		this.definitions = defs;
	}
	
	public void addDefinition(String def, String type){
		this.addDefinition(new Definition(def, type));
	}
	
	public void addDefinition(Definition def){
		this.definitions.add(def);
	}
	
	public List<Definition> getDefinitions(){
		return this.definitions;
	}
	
	@Override
	public String toString() {
		return this.term;
	}

	public String getTerm(){
		return term;
	}
}
