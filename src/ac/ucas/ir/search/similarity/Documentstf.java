package ac.ucas.ir.search.similarity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ac.ucas.ir.index.*;
//import  java.util.ArrayList;
public class Documentstf {
             private int docID;
             private List<Float> itfweight;
             Documentstf(int docID)
             {
            	 this.docID=docID;
             }
             
             public void getTfwightofDocuments(PostList list)
             {
            	 Iterator<PostListRecord> itel = list.getPostList().iterator();
            	// Iterator<PostListNode>iten;
            	 int tf;
            	 itfweight=new ArrayList<Float>();
            	 while(itel.hasNext())
            	 {
            		 tf=itel.next().getTfByDocID(docID);
            		 if(tf==0)
            		 {
            			 itfweight.add(new Float(0));
            		 }
            		 else
            		 {
            			 itfweight.add(new Float(1+Math.log10(tf)));
            		 }
            		 
            	 }
             }
             
             public List<Float> getIfweight()
             {
            	return itfweight; 
             }            
             
}
