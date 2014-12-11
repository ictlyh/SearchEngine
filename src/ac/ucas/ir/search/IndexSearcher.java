package ac.ucas.ir.search;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import ac.ucas.ir.index.*;
import ac.ucas.ir.search.similarity.*;

public class IndexSearcher {
	IndexSearcher()
	{
		
	}	
	
	public List<Integer> getDocIDListByTerm(String term,PostList list){
        Iterator<PostListRecord>ite=list.getPostList().iterator();
      //  Iterator<PostListNode>iter;
        List<Integer> docIDlist=new ArrayList<Integer>(); 
        PostListRecord Record = null;
        while(ite.hasNext())
        {
          Record=ite.next();
          if(Record.getTerm()==term)
          {
        	 break;
          }
        }
        if(Record==null)
        {
           return null; 	
        }
        else{
              docIDlist=Record.getDocIDs();      
        }
       return docIDlist;          
	}
	
	public List<Integer> unionDocIDList(List<Integer> ls1,List<Integer> ls2){
		 List<Integer> unionlist=new ArrayList<Integer>();
		 Iterator<Integer> ite1;
		 Iterator<Integer> ite2;
		 if(ls1.size()<=ls2.size())
		 {
		     ite1 = ls1.iterator();
		     ite2 = ls2.iterator();
		 }else
		 {
			 ite1 = ls2.iterator();
			 ite2 = ls1.iterator();
		 }
		 
		 Integer node1=new Integer(-1);
		 Integer node2=new Integer(-1);
 		 while(ite1.hasNext())
		 {  
			 node1=ite1.next();
			while(ite2.hasNext())
			{   
				if(node1.intValue()>node2.intValue())
				{
				    node2=ite2.next();
				}
				if(node1.intValue()==node2.intValue())
				{
					unionlist.add(node1);
					break;
				}
				else if(node1.intValue()<node2.intValue())
					break;
			}
			if(!ite2.hasNext())
				break;
		 }
		return unionlist;
	}
	
	//得到所有候选ID链
	public  List<Integer> getdocIDlistbyquery(Query query,PostList list)
	{
		Iterator<String>ites=query.getQuerywords().iterator();
		List<Integer> Ls1=new ArrayList<Integer>();
		List<Integer> Ls2=new ArrayList<Integer>();
		Ls1=null;
		Ls2=null;
		while(ites.hasNext())
		{
			if(Ls1==null)
			{
				Ls1=getDocIDListByTerm(ites.next(), list);
				if(ites.hasNext())
				{
					Ls2=getDocIDListByTerm(ites.next(), list);
					Ls1=unionDocIDList(Ls1,Ls2);
				}
			}
		}
		return Ls1;
	}
	
	
	public float getTermEvaluateOfDocument(String term,int docID){
		return 0;
	}
	//计算特定文章与查询的相关度。
	public float getQueryEvaluateOfDocument(Query query,int docID,PostList list,int documentsize){
		TFIDFSimilarity tfidfsimilar=new TFIDFSimilarity(docID);
		tfidfsimilar.Calutfidfsimilarity(documentsize, list, query.getQuerywords());
		return tfidfsimilar.getTfidfsimilar();
	}
	
	//计算所有候选docs 的id和相关度。
	public List<DocID_Tfidfsimilar> CaluAlldocIDlist(List<Integer>docIDlist,int documentsize,PostList list,Query query )
	{ 
		Iterator<Integer> ited=docIDlist.iterator();
		List<DocID_Tfidfsimilar> docID_weightlist=new ArrayList<DocID_Tfidfsimilar>();
		int docID=0;
		while(ited.hasNext())
		{  
			docID=ited.next().intValue();
			docID_weightlist.add(new DocID_Tfidfsimilar(docID,getQueryEvaluateOfDocument(query,docID,list,documentsize)));
		}
		return docID_weightlist;
	}
	
	
	public List<Integer> getTopKDocuments(List<Integer> docList,int K){
		if(K>docList.size())
			return docList;
		else
	      return docList.subList(0, K-1);
	}
	public List<Integer> search(Query query,List<Integer>docIDlist,int documentsize,PostList list){
		Sort sort=new Sort();
		return sort.sortBytfidfsimilar(CaluAlldocIDlist(getdocIDlistbyquery(query,list),documentsize,list,query));

	}
}
