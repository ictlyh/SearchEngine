package ac.ucas.ir.search.similarity;
import java.util.*;
import ac.ucas.ir.index.*;

public class Querytfidf {
	  private  List<String> querywords;
	  private  List<Float> tfidfweight;
	  
      Querytfidf(List<String> querywords)
      {
    	  this.querywords=querywords;
      }
      
      public  List<Float> getTfidfweight()
      {
    	  return tfidfweight;
      }
      
      public  void Calutfidfweight(List<Float>itf,List<Float>idf)
      {
    	  Iterator<Float>itet=itf.iterator();
    	  Iterator<Float>ited=idf.iterator();
    	  tfidfweight=new ArrayList<Float>();
    	  while(itet.hasNext()&&ited.hasNext())
    	  {
    		  tfidfweight.add(new Float(itet.next().floatValue()*ited.next().floatValue()));
    	  }
      }
      
      public  List<Float> Caluitf(PostList list)
      {
    	  List<Float>tf=new ArrayList<Float>();
    	  Iterator<PostListRecord>iter=list.getPostList().iterator();
    	  int itf=0;
    	  String term;
    	  while(iter.hasNext())
    	  {
    		  term=iter.next().getTerm();
    		  Iterator<String> ites=querywords.iterator();
    		  while(ites.hasNext())
    		  {
    			  if(ites.next()==term)
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
      
      public List<Float> Caluidf(PostList list,int documentsize)
      {
    	  List<Float>idf=new ArrayList<Float>();
    	  Iterator<PostListRecord>iter=list.getPostList().iterator();
    	  while(iter.hasNext())
    	  {
    		  idf.add(new Float(Math.log10(documentsize/iter.next().getDf())));
    	  }
    	  return idf;
      }
	
      
     public void Calutfidfweight(int documentsize, PostList list)
     {
    	 Calutfidfweight(Caluitf(list),Caluidf(list,documentsize));
     }
}
