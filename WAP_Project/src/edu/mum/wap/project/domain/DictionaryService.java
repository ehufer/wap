package edu.mum.wap.project.domain;

import java.util.List;

import edu.mum.wap.project.database.DBconnection;

public class DictionaryService {

	private DBconnection db;
	
	public DictionaryService(){
		this.db = new DBconnection();
	}
	
	public Word getWordDefinitionsByTerm(String term){
		return new Word(term, this.db.getDefinitionsByTerm(term));
	}
	
	public List<Word> getAllWords(){
		return this.db.getAllWords();
	}
	
	public List<Word> getAllWordsBeginWith(String begins){
		return this.db.getAllWordsBeginsWith(begins);
	}
}
