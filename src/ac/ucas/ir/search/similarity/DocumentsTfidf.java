package ac.ucas.ir.search.similarity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ac.ucas.ir.index.*;

public class DocumentsTfidf {

    private int docID;
    private List<Float> itfweight;
    public  DocumentsTfidf(int docID)
    {
   	 this.docID=docID;
    }
    
  // @SuppressWarnings("rawtypes")
    @SuppressWarnings("rawtypes")
	public List<Float> getTfwightofDocuments(PostList list)
    {
    Iterator<Entry<String, PostListRecord>> itel = list.getIndex().entrySet().iterator();
   	 int tf;
   	 List<Float>itf=new ArrayList<Float>();
   	 while(itel.hasNext())
   	 {
   		
   		 Map.Entry entry = (Map.Entry) itel.next();
   		 String term=(String) entry.getKey();
   		 tf=list.getTermTfByDocID(term, docID);
   		 
   		 if(tf==0)
   		 {
   			 itf.add(new Float(0));
   		 }
   		 else
   		 {
   			 itf.add(new Float(1+Math.log10(tf)));
   		 }
   		 
   	 }
   	 return itf;
    }
    
    @SuppressWarnings("rawtypes")
	public List<Float> Caluidf(PostList list,int documentsize)
    {
  	  List<Float>idf=new ArrayList<Float>();
  	  Iterator<Entry<String, PostListRecord>> itel = list.getIndex().entrySet().iterator();
  	  int df=0;
  	  while(itel.hasNext())
  	  {
  		 Map.Entry entry = (Map.Entry) itel.next();
   		 String term=(String) entry.getKey();
   		 df=list.getDfByTerm(term);
   		 if(df==0)
   		 {
   			 idf.add(new Float(df));
   		 }
   		 else
   		 {
   			 idf.add(new Float(Math.log10((float)documentsize/df)));
   		 }
  	  }
  	 /* Iterator<PostListRecord>iter=list.getPostList().iterator();
  	  while(iter.hasNext())
  	  {
  		  idf.add(new Float(Math.log10(documentsize/iter.next().getDf())));
  	  }*/
  	  /*	int i=0;
  	  	for(i=0;i<list.getPostList().size();i++)
  		  idf.add(new Float(Math.log10((float)documentsize/list.getPostList().get(i).getDf())));*/
  	  	return idf;
    }
    
    public List<Float> Calutfidfweight(List<Float>itf,List<Float>idf)
    {
  	  	Iterator<Float>itet=itf.iterator();
  	  	Iterator<Float>ited=idf.iterator();
  	  	List<Float>itfdf=new ArrayList<Float>();
  		while(itet.hasNext()&&ited.hasNext())
  	  	{
  		  itfdf.add(new Float(itet.next().floatValue()*ited.next().floatValue()));
  	  	}
  	  
  	  	return itfdf;
    }
    
    public void Calutfidfweight(int documentsize, PostList list)
    { 
   	  	itfweight=new ArrayList<Float>();
   	   	itfweight=Calutfidfweight(getTfwightofDocuments(list),Caluidf(list,documentsize));
    }
    
    public List<Float> getIfweight()
    { 
   	  
    	return itfweight; 
    }            


}
