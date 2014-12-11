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
		querywords=new ArrayList<String>();
		querywords=null;
	}
	
	void  getQueryTerms(StopWords stopwords){
		//List<String> result = null;
		Tokenize tokenize = new Tokenize(query, "UTF-8");
		while(tokenize.hasMoreTokens()){
			String term = tokenize.nextToken();
			if(!stopwords.isStopword(term))
				querywords.add(term);//(term + " ");
		}
		//return querywords;
	}
	
	String getQuery()
	{
		return query;
	}
	
	List<String> getQuerywords()
	{
		return querywords;
	}
}
