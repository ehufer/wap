package edu.mum.wap.project.api;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.mum.wap.project.domain.DictionaryService;
import edu.mum.wap.project.domain.Word;
import edu.mum.wap.project.domain.Definition;

/**
 * Servlet implementation class DictionaryServlet
 */
@WebServlet("/dictionary")
public class DictionaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DictionaryService service;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DictionaryServlet() {
        super();
    }
    

	@Override
	public void init() throws ServletException {
		super.init();
		service = new DictionaryService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String term = request.getParameter("term");
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");
		
		if(term == null){
			String begins = request.getParameter("begins");
			List<Word> words;
			if(begins == null){
				words = service.getAllWords();
			} else {
				words = service.getAllWordsBeginWith(begins);
			}
			JSONArray jsonWords = new JSONArray();
			words.forEach(word->jsonWords.add(word.toString()));
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("terms", jsonWords);
			response.getWriter().println(jsonObj.toString());
		}else{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			response.getWriter().println(mapWordToJson(service.getWordDefinitionsByTerm(term))); 
		}
	}

	private JSONObject mapWordToJson(Word word) {
		JSONArray jsonDefs = new JSONArray();
		jsonDefs.addAll(word.getDefinitions().stream().map(def->mapDefitionToJson(def)).collect(Collectors.toList()));
		JSONObject jsonWord = new JSONObject();
		jsonWord.put("definitions", jsonDefs);
		jsonWord.put("word", word.getTerm());
		return jsonWord;
	}

	private JSONObject mapDefitionToJson(Definition def) {
		JSONObject jsonDef = new JSONObject(); 
		jsonDef.put("type", def.getTermType());
		jsonDef.put("definition", def.getDefinition());
		return jsonDef;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}