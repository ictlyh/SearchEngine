package ac.ucas.ir.search;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import ac.ucas.ir.index.*;
import ac.ucas.ir.search.similarity.*;

public class IndexSearcher {
	public List<Integer> getDocIDListByTerm(String term,PostList list){
	      
	      //  Iterator<PostListNode>iter;
	      List<Integer> docIDlist=new ArrayList<Integer>(); 
	     
	        docIDlist=list.getDocIDListByTerm(term);
	        return docIDlist;          
		}
		
		public List<Integer> unionDocIDList(List<Integer> ls1,List<Integer> ls2){
			 List<Integer> unionlist=new ArrayList<Integer>();
			 //unionlist=null;
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
	 		 while(true)
			 {  
				if(node1.intValue()==node2.intValue())
				{
					if(node1.intValue()>-1)
					{
						unionlist.add(node1);
					}
					if(!ite1.hasNext())
						break;
					 node1=ite1.next();
				   if(!ite2.hasNext())
					  break;
				     node2=ite2.next();
				}
				if(node1.intValue()>node2.intValue())
				{
					 if(!ite2.hasNext())
						  break;
					 node2=ite2.next();
				}
				if(node1.intValue()<node2.intValue())
				{
					 if(!ite1.hasNext())
						  break;
					 node1=ite1.next();
				}
			 }
			return unionlist;
		}
		
		//寰楀埌鎵€鏈夊€欓€塈D閾?
		
		public  List<Integer> getdocIDlistbyquery(Query query,PostList list)
		{
			Iterator<String>ites=query.getQuerywords().iterator();
		//	System.out.print("words siez");
		//	System.out.println(query.getQuerywords().size());
			List<Integer> Ls1=new ArrayList<Integer>();
			List<Integer> Ls2=new ArrayList<Integer>();
			List<Integer> temp=new ArrayList<Integer>();
			Ls1=null;
			Ls2=null;
			int flag=0;
			
			while(ites.hasNext())
			{
				if(flag==0)
				{
					flag=1;
					Ls1=getDocIDListByTerm(ites.next(),list);
					if( query.getQuerywords().size()==1)
						return Ls1;
					
					  /* Iterator<Integer>ite1=Ls1.iterator();
					   System.out.println("Ls1");
					    while(ite1.hasNext())
					    {
					    	System.out.println(ite1.next().intValue());
					    }*/
				}
				if(ites.hasNext())
				{
				  Ls2=getDocIDListByTerm(ites.next(),list);
				  
				 /* 
				  Iterator<Integer>ite2=Ls2.iterator();
				  System.out.println("Ls2");
				    while(ite2.hasNext())
				    {
				    	System.out.println(ite2.next().intValue());
				    }*/
				}
				  
				  temp=unionDocIDList(Ls1,Ls2);
				  Ls1=temp;
			
			}
			return Ls1;
		}
		
		
		public float getTermEvaluateOfDocument(String term,int docID){
			return 0;
		}
		
		//璁＄畻鐗瑰畾鏂囩珷涓庢煡璇㈢殑鐩稿叧搴︺€?
		
		public float getQueryEvaluateOfDocument(Query query,int docID,PostList list,int documentsize){
			TfidfSimilarity tfidfsimilar=new TfidfSimilarity(docID);
			tfidfsimilar.Calutfidfsimilarity(documentsize, list, query.getQuerywords());
			return tfidfsimilar.getTfidfsimilar();
		}
		
		//璁＄畻鎵€鏈夊€欓€塪ocs 鐨刬d鍜岀浉鍏冲害銆?
		public List<DocidTfidfsimilar> CaluAlldocIDlist(List<Integer>docIDlist,int documentsize,PostList list,Query query )
		{ 
			Iterator<Integer> ited=docIDlist.iterator();
			List<DocidTfidfsimilar> docID_weightlist=new ArrayList<DocidTfidfsimilar>();
			int docID=0;
			while(ited.hasNext())
			{  
				docID=ited.next().intValue();
				docID_weightlist.add(new DocidTfidfsimilar(docID,getQueryEvaluateOfDocument(query,docID,list,documentsize)));
			}
			return docID_weightlist;
		}
		
		public List<Integer> getTopKDocuments(List<Integer> docList,int K){
			if(K>docList.size())
				return docList;
			else
		      return docList.subList(0, K);
		}
		
		public List<Integer> search(Query query,List<Integer>docIDlist,int documentsize,PostList list){
			Sort sort=new Sort();
			return sort.sortBytfidfsimilar(CaluAlldocIDlist(getdocIDlistbyquery(query,list),documentsize,list,query));

		}

}
