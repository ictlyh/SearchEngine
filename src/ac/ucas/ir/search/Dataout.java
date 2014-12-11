package ac.ucas.ir.search;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import ac.ucas.ir.document.Document;
import ac.ucas.ir.document.Field;
import ac.ucas.ir.util.Skip;

public class Dataout {
	//public  
	
	public Document parseLine(String line){
		Document doc = new Document();
		int begin=line.indexOf("url");
		begin=begin+7;
		int end=line.indexOf('"', begin);
		String url=line.substring(begin, end);
		
		begin = line.indexOf("content");
		begin = begin + 11;
		end = line.indexOf('"', begin);
		String content = line.substring(begin, end);
		
		begin = line.indexOf("title", end);
		begin = begin + 9;
		end = line.indexOf('"', begin);
		String title = line.substring(begin, end);
		doc.addField(new Field("url",url));
		doc.addField(new Field("title",title));
		doc.addField(new Field("content",content));
		return doc;
	}
	
	
	public List<Document> getDocumentlist(List<Integer> docidlist,String filePath)
    {
		List<Document> documentslist=new ArrayList<Document>();
		List<Document> documentslist_copy=new ArrayList<Document>();
		List<Integer> incrlist=new ArrayList<Integer>();
		incrlist.addAll(docidlist);
		Collections.sort(incrlist);
		/***
		 read content form file 
    	****/
		//IndexWriter index = new IndexWriter();
	
		Skip skip = new Skip();
		int numOfDoc = 0;
		Iterator<Integer> itel=incrlist.iterator();
		int seq=itel.next();
		
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(filePath);
			sc = new Scanner(inputStream, "UTF-8");
			while (sc.hasNextLine()) {
				String line = skip.skip(sc.nextLine());
				if(line == null || line.length() == 0)
					continue ;
			
				numOfDoc++;
				if(numOfDoc==seq)
				{
				 Document doc = parseLine(line);
				 doc.setDocID(numOfDoc);
				 documentslist.add(doc);
				 if(itel.hasNext())
				 {
					 seq=itel.next().intValue();
				 }
				 else
				 {
					 break;
				 }
				 
				}
				
			}
			
			/*
			indexOutput.writeIndexToFile(index.getIndex(), "index\\part" + part + ".txt");
			part++;
			index.clear(); */
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if (inputStream != null) {
		        try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
		documentslist_copy.addAll(documentslist);
		documentslist.clear();
		itel=docidlist.iterator();
		int  seql=-1;
		Document doc2=new Document();
		while(itel.hasNext())
		{ 
			seql=itel.next().intValue();
			Iterator<Document> iter=documentslist_copy.iterator();
			while(iter.hasNext())
			{
				doc2=iter.next(); 
				if(doc2.getDocID()==seql)
				{
					documentslist.add(doc2);
					break;
				}
			}
         // documentslist.add(documentslist_copy.)			
		}
		
    	return documentslist;
    }
    
	public void PrintResult(List<Document> documents,Query query){
		
		Iterator<Document> iteDoc = documents.iterator();
		System.out.println(documents.size());
		System.out.println(query.getQuerywords().size());
		Iterator<String> itestr=query.getQuerywords().iterator();
		while(itestr.hasNext())
		{
			System.out.println(itestr.next());
		}
		
		while(iteDoc.hasNext()){
			Document doc = iteDoc.next();
			String line = new String();
			line = line + "{";
		//	line = line + doc.getDocID();
			Iterator<Field> iteField = doc.getDocContent().iterator();
			while(iteField.hasNext()){
				Field field = iteField.next();
				line = line + "\"";
				line = line + field.getName();
				line = line + "\":\"";
				line = line + field.getValue();
				line = line + "\"";
				line = line + ",";
			}
			line=line.substring(0,line.length()-2);
			line = line + "}";
			System.out.println(line);
		
	}
 }


}
