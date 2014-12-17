package ac.ucas.ir.search;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

import ac.ucas.ir.index.*;
import ac.ucas.ir.search.similarity.*;

public class IndexSearcher {
	public List<Integer> getDocIDListByTerm(String term,PostList list) {
		return list.getDocIDListByTerm(term);    
		}
		
		public List<Integer> unionDocIDList(List<Integer> ls1,List<Integer> ls2){
			 List<Integer> unionlist=new ArrayList<Integer>();
			 if(ls1 == null){
				 return ls2;
			 }
			 if(ls2 == null){
				 return ls1;
			 }
			 
			 ListIterator<Integer> ite1 = ls1.listIterator();
			 ListIterator<Integer> ite2 = ls2.listIterator();
			 int tmp1, tmp2;
			 while(ite1.hasNext() && ite2.hasNext()){
				 tmp1 = ite1.next().intValue();
				 tmp2 = ite2.next().intValue();
				 if(tmp1 == tmp2){
					 unionlist.add(tmp1);
				 }
				 else if(tmp1 < tmp2){
					 unionlist.add(tmp1);
					 ite2.previous();
				 }
				 else{
					 unionlist.add(tmp2);
					 ite1.previous();
				 }
			 }
			 while(ite1.hasNext()) {
				 unionlist.add(ite1.next().intValue());
			 }
			 while(ite2.hasNext()) {
				 unionlist.add(ite2.next().intValue());
			 }
			return unionlist;
		}
		
		//寰楀埌鎵€鏈夊€欓€塈D閾?
		
		public  List<Integer> getdocIDlistbyquery(Query query,PostList list)
		{			
			List<Integer> result = null;
			Iterator<String> ite = query.getQuerywords().iterator();
			while(ite.hasNext()){
				result = unionDocIDList(result, getDocIDListByTerm(ite.next(), list));
			}
			return result;
		}
		
		
		/*public float getTermEvaluateOfDocument(String term,int docID){
			return 0;
		}*/
		
		//璁＄畻鐗瑰畾鏂囩珷涓庢煡璇㈢殑鐩稿叧搴︺€?
		
		public float getQueryEvaluateOfDocument(Query query,int docID,PostList list,int documentsize){
			QueryTfidfnew querytfidf=new QueryTfidfnew(query.getQuerywords(),docID);
			querytfidf.Caluitfdf(list, documentsize);
			return querytfidf.getTfidfweight(); 

		}
		
		//璁＄畻鎵€鏈夊€欓€塪ocs 鐨刬d鍜岀浉鍏冲害銆?
		public List<DoctermsTfidf> CaluAlldocIDlist(List<Integer> docIDlist,int documentsize,PostList list,Query query )
		{
			if(docIDlist == null) {
				return null;
			}
			Iterator<Integer> ited=docIDlist.iterator();
			List<DoctermsTfidf> docID_weightlist=new ArrayList<DoctermsTfidf>();
			int docID=0;
			while(ited.hasNext())
			{  
				docID=ited.next().intValue();
				docID_weightlist.add(new DoctermsTfidf(docID,getQueryEvaluateOfDocument(query,docID,list,documentsize)));
			}
			return docID_weightlist;
		}
		
		public List<Integer> getTopKDocuments(List<Integer> docList,int K){
			if(docList == null || K > docList.size())
				return docList;
			else
		      return docList.subList(0, K);
		}
		
		public List<Integer> search(Query query,List<Integer>docIDlist,int documentsize,PostList list){
			Sort sort=new Sort();
			return sort.sortBytfidfsimilar(CaluAlldocIDlist(getdocIDlistbyquery(query,list),documentsize,list,query));

		}

}
