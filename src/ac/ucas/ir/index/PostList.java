package ac.ucas.ir.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PostList {

	private HashMap<String,PostListRecord> index;
	
	public PostList(){
		index = new HashMap<String,PostListRecord>();
	}
	
	public  HashMap<String,PostListRecord> getIndex(){
		return index;
	}
	
	public boolean containTerm(String term){
		return index.containsKey(term);
	}
	
	public int getTermSize(){
		return index.size();
	}
	
	public void addPostListRecord(PostListRecord plr){
		if(index.containsKey(plr.getTerm())){
			PostListRecord src = index.get(plr.getTerm());
			src.merge(plr);
			index.put(plr.getTerm(),src);
		}
		else{
			index.put(plr.getTerm(), plr);
		}
	}
	
	public void delPostListRecord(String term){
		if(index.containsKey(term)){
			index.remove(term);
		}
	}
	
	public PostListRecord getTermRecord(String term){
		if(index.containsKey(term)){
			return index.get(term);
		}
		else{
			return null;
		}
	}
	
	public Set<String> getTerms(){
		return index.keySet();
	}

	@SuppressWarnings("rawtypes")
	public List<String> getTermsByDocID(int docID){
		List<String> result = new ArrayList<String>();
		Iterator<Entry<String, PostListRecord>> iter = index.entrySet().iterator();
		while (iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			PostListRecord val = (PostListRecord) entry.getValue();
			if(val.containDoc(docID)){
				result.add(val.getTerm());
			}
		}
		return result;
	}
	
	public int getDfByTerm(String term){
		if(index.containsKey(term)){
			PostListRecord plr = index.get(term);
			return plr.getDf();
		}
		else{
			return 0;
		}
	}
	
	public int getTermTfByDocID(String term,int docID){
		if(index.containsKey(term)){
			return index.get(term).getTfByDocID(docID);
		}
		else{
			return 0;
		}
	}
	
	public List<Integer> getDocIDListByTerm(String term){
		if(index.containsKey(term)){
			return index.get(term).getDocIDs();
		}
		else{
			return null;
		}
	}

	public void increTermFrequencyByDocID(String term,int docID){
		if(index.containsKey(term)){
			PostListRecord plr = index.get(term);
			plr.IncreTFByDocID(docID);
			index.put(term, plr);
		}
		else{
			PostListNode pln = new PostListNode(docID);
			pln.increTf();
			PostListRecord plr1 = new PostListRecord(term);
			plr1.addPostListNode(pln);
			index.put(term, plr1);
		}
	}
	
	public void decreTermFrequencyByDocID(String term,int docID){
		if(index.containsKey(term)){
			PostListRecord plr = index.get(term);
			plr.delcreTFByDocID(docID);
			if(plr.getDf() == 0){
				index.remove(term);
			}
			else{
				index.put(term, plr);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void delDocument(int docID){
		HashMap<String,PostListRecord> result = new HashMap<String,PostListRecord>();
		Iterator<Entry<String, PostListRecord>> iter = index.entrySet().iterator();
		while (iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			PostListRecord val = (PostListRecord) entry.getValue();
			val.delDocument(docID);
			result.put(key, val);
		}
		index.clear();
		index = result;
	}
	
	public void clear(){
		index.clear();
	}
	
	@SuppressWarnings("rawtypes")
	public void print(){
		Iterator<Entry<String, PostListRecord>> iter = index.entrySet().iterator();
		while (iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			PostListRecord val = (PostListRecord) entry.getValue();
			val.print();
		}
	}
}