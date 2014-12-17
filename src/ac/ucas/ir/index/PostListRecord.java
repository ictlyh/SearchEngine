package ac.ucas.ir.index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PostListRecord {

	private String term;
	private int df;
	private List<PostListNode> postListRecord;
	
	public PostListRecord(String term){
		this.term = term;
		this.df = 0;
		this.postListRecord = new ArrayList<PostListNode>();
	}
	
	public String getTerm() {
		return term;
	}
	
	public void setTerm(String term) {
		this.term = term;
	}
	
	public int getDf() {
		return df;
	}
	
	public void setDf(int df) {
		this.df = df;
	}
	
	public List<PostListNode> getPostListRecord() {
		return postListRecord;
	}
	
	public void setPostListRecord(List<PostListNode> postListRecord) {
		this.postListRecord = postListRecord;
	}
	
	public void addPostListNode(PostListNode postListNode){
		for(int i = 0; i < postListRecord.size(); i++){
			if(postListRecord.get(i).getDocID() == postListNode.getDocID()){
				PostListNode pln = postListRecord.get(i);
				pln.setTf(pln.getTf() + postListNode.getTf());
				postListRecord.set(i, pln);
				return ;
			}
			else if(postListRecord.get(i).getDocID() > postListNode.getDocID()){
				postListRecord.add(i,postListNode);
				df++;
				return ;
			}
		}
		postListRecord.add(postListNode);
		df++;
	}
	
	public boolean containDoc(int docID){
		for(int i = 0; i < postListRecord.size(); i++){
			if(postListRecord.get(i).getDocID() == docID){
				return true;
			}		
		}
		return false;
	}

	public List<Integer> getDocIDs(){
		List<Integer> result = new ArrayList<Integer>();
		for(int i = 0; i < postListRecord.size(); i++)
			result.add(new Integer(postListRecord.get(i).getDocID()));
		return result;
	}
	
	public int getTfByDocID(int docID){
		for(int i = 0; i < postListRecord.size(); i++){
			PostListNode postListNode = postListRecord.get(i);
			if(postListNode.getDocID() == docID){
				return postListNode.getTf();
			}		
		}
		return 0;
	}
	
	public void IncreTFByDocID(int docID){
		for(int i = 0; i < postListRecord.size(); i++){
			PostListNode pln = postListRecord.get(i);
			if(pln.getDocID() == docID){
				pln.increTf();
				postListRecord.set(i, pln);
				return ;
			}
			else if(pln.getDocID() > docID){
				PostListNode npln = new PostListNode(docID);
				npln.increTf();
				postListRecord.add(i, npln);
				df++;
				return ;
			}
		}
		PostListNode npln = new PostListNode(docID);
		npln.increTf();
		postListRecord.add(npln);
		df++;
	}
	
	public void delcreTFByDocID(int docID){
		for(int i = 0; i < postListRecord.size(); i++){
			PostListNode pln = postListRecord.get(i);
			if(pln.getDocID() == docID){
				pln.decreTf();
				if(pln.getTf() == 0){
					postListRecord.remove(i);
					df--;
					return ;
				}
				postListRecord.set(i, pln);
				return ;
			}		
		}
	}
	
	public void delDocument(int docID){
		for(int i = 0; i < postListRecord.size(); i++){
			if(postListRecord.get(i).getDocID() == docID){
				postListRecord.remove(i);
				df--;
				return ;
			}		
		}
	}
	
	public void merge(PostListRecord src){
		List<PostListNode> lpln = src.getPostListRecord();
		Iterator<PostListNode> ite = lpln.iterator();
		while(ite.hasNext()){
			addPostListNode(ite.next());
		}
	}
	
	public void print(){
		System.out.print(term + "," + df);
		Iterator<PostListNode> ite = postListRecord.iterator();
		while(ite.hasNext()){
			System.out.print("->");
			ite.next().print();
		}
		System.out.println();
	}
}