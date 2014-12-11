package ac.ucas.ir.store;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import ac.ucas.ir.document.Document;
import ac.ucas.ir.document.Field;
import ac.ucas.ir.index.IndexWriter;
import ac.ucas.ir.util.Skip;

public class DataInput {

	public void loadDocumentFromFile(String filePath){
		IndexWriter index = new IndexWriter();
		IndexOutput indexOutput = new IndexOutput();
		//Skip skip = new Skip();
		int numOfDoc = 0;
		int part = 0;
		index.loadStopwords("stopwords.txt");
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(filePath);
			sc = new Scanner(inputStream, "UTF-8");
			while (sc.hasNextLine()) {
				//String line = skip.skip(sc.nextLine());
				String line = sc.nextLine();
				++numOfDoc;
				if(line == null || line.length() == 0){
					System.out.println("null line " + numOfDoc);
					continue ;
				}
				Document doc = parseLine(line);
				if(doc != null){
					doc.setDocID(numOfDoc);
					index.addDocument(doc);
				}
				else{
					System.out.println("error occur while parsing line " + numOfDoc + line);
				}
				//每处理n篇文档后将索引写入文件
				if(numOfDoc % 1000 == 0){
					indexOutput.writeIndexToFile(index.getIndex(), "index\\part" + part + ".txt");
					part++;
					index.clear();
				}
			}
			indexOutput.writeIndexToFile(index.getIndex(), "index\\part" + part + ".txt");
			part++;
			index.clear();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if (inputStream != null) {
		        try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
		
	}
	
	private Document parseLine(String line){
		Document doc = new Document();
		String title = "";
		String content = "";
		try{
			int begin = line.indexOf("content");
			begin = begin + 11;
			int end = line.indexOf("comment",begin);
			content = line.substring(begin, end - 4);
			begin = line.indexOf("title", end);
			begin = begin + 9;
			end = line.indexOf('}', begin);
			title = line.substring(begin, end - 1);
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}
		doc.addField(new Field("title",title));
		doc.addField(new Field("content",content));
		return doc;
	}
}