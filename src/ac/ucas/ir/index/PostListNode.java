package ac.ucas.ir.index;

public class PostListNode {

	private int docID;
	private int tf;
	
	public PostListNode(int docID){
		this.docID = docID;
		this.tf = 0;
	}
	
	public int getDocID() {
		return docID;
	}
	
	public void setDocID(int docID) {
		this.docID = docID;
	}
	
	public int getTf() {
		return tf;
	}
	
	public void setTf(int tf) {
		this.tf = tf;
	}
	
	public void increTf(){
		tf++;
	}
	
	public void decreTf(){
		tf--;
	}
	
	public void print(){
		System.out.print("(" + docID + "," + tf + ")");
	}
}