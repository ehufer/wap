package edu.mum.wap.project.domain;

public class Definition {

	private String definition;
	
	private String type;
	
	public Definition(String definition, String type){
		this.definition = definition;
		this.type = type;
	}

	public String getDefinition() {
		return definition;
	}

	public String getTermType() {
		return type;
	}
}
