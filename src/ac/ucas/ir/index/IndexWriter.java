package ac.ucas.ir.index;

import java.util.Iterator;
import java.util.List;
import ac.ucas.ir.analysis.StopWords;
import ac.ucas.ir.analysis.Tokenize;
import ac.ucas.ir.document.Document;
import ac.ucas.ir.document.Field;

public class IndexWriter {

	private PostList postList;
	private StopWords stopwords;
	
	public IndexWriter(){
		postList = new PostList();
	}
	
	public void loadStopwords(){
		stopwords = new StopWords();
	}
	
	public PostList getIndex(){
		return postList;
	}
	
	public void addDocument(Document doc){
		Iterator<Field> ite = doc.getDocContent().iterator();
		while(ite.hasNext()){
			String content = ite.next().getValue();
			Tokenize tokenize = new Tokenize(content, "UTF-8");
			while(tokenize.hasMoreTokens()){
				String term = tokenize.nextToken();
				if(term == null)
					break;
				if(!stopwords.isStopword(term))
					postList.increTermFrequencyByDocID(term, doc.getDocID());
			}
		}
		
	}
	
	public void deleteDocument(int docID){
		postList.delDocument(docID);
	}
	
	public void deleteDocuments(String term){
		List<Integer> li = postList.getDocIDListByTerm(term);
		Iterator<Integer> ite = li.iterator();
		while(ite.hasNext()){
			postList.delDocument(ite.next().intValue());
		}
	}
	
	public void updateDocument(Document doc){
		postList.delDocument(doc.getDocID());
		addDocument(doc);
	}
	
	public void clear(){
		postList = new PostList();
	}
	
	public void print(){
		postList.print();
	}
}