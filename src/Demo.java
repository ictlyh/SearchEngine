import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Calendar;

import ac.ucas.ir.document.Document;
import ac.ucas.ir.index.IndexReader;
import ac.ucas.ir.index.IndexWriter;
import ac.ucas.ir.index.PostList;
import ac.ucas.ir.search.*;
import ac.ucas.ir.search.IndexSearcher;
import ac.ucas.ir.search.Query;
import ac.ucas.ir.store.DataInput;
import ac.ucas.ir.store.DataOutput;
import ac.ucas.ir.store.IndexInput;
import ac.ucas.ir.store.IndexOutput;

public class Demo {
	public static void main(String[] args){
		/*Analyzer analyzer = new Analyzer();
		String result = analyzer.tokenizeByNLPIR("这是一个测试，中国共产党","UTF-8");
		System.out.println(result);*/
		
		/*StopWords stopwords = new StopWords();
		stopwords.setStopwordFilePath("stopwords.txt");
		System.out.println(stopwords.getStopwordFilePath());
		stopwords.readFileIntoString();
		System.out.println(stopwords.getStopwords());
		System.out.println(stopwords.isStopword("的"));*/
		
		/*Tokenize tokenize = new Tokenize("中国人民共和国成立于1998年，是我的祖国","UTF-8");
		System.out.println(tokenize.getTokenString());
		while(tokenize.hasMoreTokens()){
			System.out.println(tokenize.nextToken());
		}*/
		
		/*Document document = new Document(1);
		Field field1 = new Field("title","这是标题");
		document.addField(field1);
		Field field2 = new Field("content","这是正文内容");
		document.addField(field2);
		document.print();
		System.out.println(document.getFiledContent("content"));*/
		
		/*PostListRecord plr1 = new PostListRecord("term1");
		PostListNode pln = new PostListNode(1);
		pln.increTf();
		plr1.addPostListNode(pln);
		PostListNode pln2 = new PostListNode(2);
		pln2.setTf(2);
		plr1.addPostListNode(pln2);
		PostListRecord plr2 = new PostListRecord("term2");
		plr2.IncreTFByDocID(3);	
		PostList pl = new PostList();
		pl.addPostListRecord(plr1);
		pl.addPostListRecord(plr2);
		pl.addPostListRecord(new PostListRecord("term"));
		pl.clear();
		pl.print();*/
		
		Calendar start = Calendar.getInstance();
		/*DataInput dataInput = new DataInput();
		dataInput.loadDocumentFromFile("testinput.txt");*/
		Calendar end = Calendar.getInstance();
		//System.out.println("Building index using " + (end.getTimeInMillis() - start.getTimeInMillis()) + " millseconds");

		start = Calendar.getInstance();
		IndexReader ir = new IndexReader();
		PostList index = ir.loadIndexFromDirectory("index");
		end = Calendar.getInstance();
		//System.out.println("loading index from file using " + (end.getTimeInMillis() - start.getTimeInMillis()) + " millseconds");
	//	index.print();
		
		IndexSearcher searcher=new IndexSearcher(); // init a searcher
		int documentsize=10;//the total number of documents;
		 
		Query query =new Query(new String("主帅拜伦"));
	    //query.getQueryTerms(stopwords);  I need stopwords ,so I just choose the setquerywords without it later;
		//List<String>words=new ArrayList<String>();
		//words.add(new String("主教练"));
		//words.add(new String("拜伦"));
		//words.add(new String("林书豪"));
		 //query.setquerywords(words);
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
