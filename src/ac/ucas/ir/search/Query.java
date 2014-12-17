package ac.ucas.ir.search;

import java.util.ArrayList;
import java.util.List;

import ac.ucas.ir.analysis.StopWords;
import ac.ucas.ir.analysis.Tokenize;

public class Query {
	private String query;
	private List<String> querywords;  

	public Query(String query) {
		super();
		this.query = query;
		querywords = getQueryTerms(new StopWords());
	}
	
	public void setQuerywords(List<String> words) {
		querywords=words;
	}
	public List<String> getQuerywords() {
		return querywords;
	}
	public String getQuery() {
		return query;
	}
	private List<String>  getQueryTerms(StopWords stopwords){
		List<String> result = new ArrayList<String>();
		Tokenize tokenize = new Tokenize(query, "UTF-8");
		while(tokenize.hasMoreTokens()){
			String term = tokenize.nextToken();
			if(!stopwords.isStopword(term))
				result.add(term);//(term + " ");
		}
		return result;
	}
}
