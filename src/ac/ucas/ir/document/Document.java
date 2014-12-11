package ac.ucas.ir.document;

import java.util.Iterator;
import java.util.Vector;

public class Document {
	private int docID;
	private int docLength;
	Vector<Field> docContent;
	
	public Document(int docID){
		this.docID = docID;
		this.docLength = 0;
		this.docContent = new Vector<Field>();
	}
	
	public Document(){
		this.docLength = 0;
		this.docContent = new Vector<Field>();
	}
	
	public int getDocID() {
		return docID;
	}
	
	public void setDocID(int docID) {
		this.docID = docID;
	}
	
	public int getDocLength() {
		return docLength;
	}
	
	public void setDocLength(int docLength) {
		this.docLength = docLength;
	}
	
	public Vector<Field> getDocContent() {
		return docContent;
	}
	
	public void setDocContent(Vector<Field> docContent) {
		this.docContent = docContent;
	}
	
	public void calcDocLength(){
		docLength = 0;
		Iterator<Field> ite = docContent.iterator();
		while(ite.hasNext()){
			docLength += ite.next().getValue().length();
		}
	}
	
	public void addField(Field field){
		docContent.add(field);
		docLength += field.getValue().length();
	}
	
	public String getFiledContent(String fieldName){
		for(int i = 0; i < docContent.size(); i++){
			Field field = docContent.get(i);
			if(field.getName().equals(fieldName))
				return field.getValue();
		}
		return null;
	}
	
	public void deleteField(String fieldName){
		for(int i = 0; i < docContent.size(); i++){
			Field field = docContent.get(i);
			if(field.getName().equals(fieldName)){
				docContent.remove(i);
				docLength -= field.getValue().length();
				break;
			}
				
		}
	}
	
	public void print(){
		System.out.println("Document ID:" + docID);
		System.out.println("Document Length:" + docLength);
		Iterator<Field> ite = docContent.iterator();
		while(ite.hasNext()){
			ite.next().print();
		}
	}
}