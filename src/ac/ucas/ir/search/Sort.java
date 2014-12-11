package ac.ucas.ir.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Sort {
	public List<Integer> sortByTime(List<Integer> docList){
		return null;
	}
	public List<Integer> sortByComment(List<Integer> docList){
		return null;
	}
	public List<Integer> sortBytfidfsimilar(List<DocidTfidfsimilar> doc_weightlist)
	{
		  Collections.sort(doc_weightlist);
		  List<Integer> docIDList=new ArrayList<Integer>();
		  Iterator<DocidTfidfsimilar>ited=doc_weightlist.iterator();
		  while(ited.hasNext())
		  {
			  docIDList.add(new Integer(ited.next().getDocID()));
		  }
		  
		  return docIDList;
	}
}
