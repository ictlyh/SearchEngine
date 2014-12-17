package ac.ucas.ir.store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import ac.ucas.ir.document.Document;
import ac.ucas.ir.document.Field;

public class DataOutput {

	public void writeDocumentsToFile(List<Document> documents,String filePath){
		File file = new File(filePath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Iterator<Document> iteDoc = documents.iterator();
		while(iteDoc.hasNext()){
			Document doc = iteDoc.next();
			String line = new String();
			line = line + "{";
			line = line + doc.getDocID();
			Iterator<Field> iteField = doc.getDocContent().iterator();
			while(iteField.hasNext()){
				line = line + ",";
				Field field = iteField.next();
				line = line + "\"";
				line = line + field.getName();
				line = line + "\":\"";
				line = line + field.getValue();
				line = line + "\"";
			}
			line = line + "}";
			try {
				bw.write(line, 0, line.length());
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bw.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}