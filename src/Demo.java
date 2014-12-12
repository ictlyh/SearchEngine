import java.util.List;
import java.util.Calendar;

import ac.ucas.ir.index.IndexReader;
import ac.ucas.ir.index.PostList;
import ac.ucas.ir.search.*;
import ac.ucas.ir.store.DataInput;

public class Demo {
	public static void main(String[] args){
		
		Calendar start = Calendar.getInstance();
		DataInput dataInput = new DataInput();
		dataInput.loadDocumentFromFile("testinput.txt");
		Calendar end = Calendar.getInstance();
		System.out.println("Building index using " + (end.getTimeInMillis() - start.getTimeInMillis()) + " millseconds");

		start = Calendar.getInstance();
		IndexReader ir = new IndexReader();
		PostList index = ir.loadIndexFromDirectory("index");
		end = Calendar.getInstance();
		System.out.println("loading index from file using " + (end.getTimeInMillis() - start.getTimeInMillis()) + " millseconds");
		
		IndexSearcher searcher=new IndexSearcher(); // init a searcher
		int documentsize=10;//the total number of documents;
		 
		Query query =new Query(new String("主帅拜伦"));
		 List<Integer> docidl = searcher.getdocIDlistbyquery(query, index);
		 List<Integer> docids = searcher.search(query, docidl, documentsize, index); //the all  responds;
		 Dataout out=new Dataout();
	     out.PrintResult(out.getDocumentlist(docids, "testinput.txt"),query);
	     /*int topK=1;
	     List<Integer> topKdocids = searcher.getTopKDocuments(docids, topK); 
	     System.out.println("select the topK");
	     out.PrintResult(out.getDocumentlist(topKdocids, "testinput.txt"),query);*/
	    
		
	}
}
