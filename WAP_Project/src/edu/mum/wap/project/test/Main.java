package edu.mum.wap.project.test;

import edu.mum.wap.project.domain.Definition;
import edu.mum.wap.project.domain.DictionaryService;
import edu.mum.wap.project.domain.Word;

public class Main {

	public static void main(String[] args) {
		DictionaryService service = new DictionaryService();
		Word word = service.getWordDefinitionsByTerm("Aback");
		System.out.println(word.getTerm());
		for (Definition def : word.getDefinitions()) {
			System.out.println(def.getTermType() + " "  + def.getDefinition());
		}
	}
}
