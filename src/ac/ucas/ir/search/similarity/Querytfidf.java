package ac.ucas.ir.search.similarity;
//import java.util.*;

import ac.ucas.ir.index.*;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
//import java.util.Set;

public class QueryTfidf {
	
	  private  List<String> querywords;
	  private  List<Float> tfidfweight;
	  
    public QueryTfidf(List<String> querywords)
    {
  	  this.querywords=querywords;
    }
    
    public  List<Float> getTfidfweight()
    {
  	  return tfidfweight;
    }
    
    public  List<Float> Calutfidfweight(List<Float>itf,List<Float>idf)
    {
  	  Iterator<Float>itet=itf.iterator();
  	  Iterator<Float>ited=idf.iterator();
  	  List<Float>tfidf=new ArrayList<Float>();
  	  while(itet.hasNext()&&ited.hasNext())
  	  {
  		  tfidf.add(new Float(itet.next().floatValue()*ited.next().floatValue()));
  	  }
  	  return tfidf;
    }
    
    
    @SuppressWarnings("rawtypes")
	public  List<Float> Caluitf(PostList list)
    {
  	  List<Float>tf=new ArrayList<Float>();
  	  Iterator<Entry<String, PostListRecord>> itel = list.getIndex().entrySet().iterator();
  	  int itf=0;
  	  String term;
  	  while(itel.hasNext())
  	  {
  		  Map.Entry entry = (Map.Entry) itel.next();
  		  term=(String) entry.getKey();
  		  Iterator<String> ites=querywords.iterator();
  		  while(ites.hasNext())
  		  {
  			  if(ites.next().equals(term))
  				  itf++;
  		  }
  		  if(itf==0)
  		  {
  			  tf.add(new Float(0));
  		  }
  		  else
  		  {
  			  tf.add(new Float(1+Math.log10(itf)));
  			  itf=0;
  		  }
  	  }
  	  return tf;
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
  	  return idf;
    }
	
    
   public void Calutfidfweight(int documentsize, PostList list)
   {  
  	 tfidfweight=new ArrayList<Float>(); 
  	 tfidfweight=Calutfidfweight(Caluitf(list),Caluidf(list,documentsize));
   }


}
