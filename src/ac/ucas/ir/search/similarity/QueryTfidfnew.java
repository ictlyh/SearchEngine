package ac.ucas.ir.search.similarity;

import java.util.Iterator;
import java.util.List;
import ac.ucas.ir.index.PostList;


public class QueryTfidfnew {

	  private  List<String> querywords;
	  private  float tfidfweight;
	  private  int 	docID;
	  
   public QueryTfidfnew(List<String> querywords,int docID)
   {
 	  this.querywords=querywords;
 	  this.docID=docID;
   }
   
   public float getTfidfweight()
   {
 	  return tfidfweight;
   }
       
   public int getDocID()
   {
	  return docID;
   }
   
	public  void Caluitfdf(PostList list,int documentssize)
   {
	      tfidfweight=0;
 		  Iterator<String> ites=querywords.iterator();
 		  float df=0;
 		  float tf=0;
 		  String term=new String();
 	  try{  
 		  while(ites.hasNext())
 		  {   
 			  term=ites.next();
 			  df=list.getDfByTerm(term);
 			  if(df==0)
 			  {
 				  df=0;
 			  }
 			  else
 			  {
 				  df=(float) (1+Math.log10((float)documentssize/df));
 			  }
 			  tf=list.getTermTfByDocID(term, documentssize);
 			  if(tf==0)
 			  {
 				  tf=0;
 			  }
 			  else{
 				  tf=(float) (1+Math.log10(tf));
 			  }
 			  tfidfweight+=tf*df;
 		  }
 		
 	  }catch(Exception e)
 	  {
 		  System.out.println("error in QueryTfidfnew_Caluitfdf"+e);
 	  }
   }
   
  
  }
