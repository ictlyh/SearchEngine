package ac.ucas.ir.search.cluster;

import java.util.*;

import ac.ucas.ir.document.Document;
import ac.ucas.ir.document.Field;
import ac.ucas.ir.index.PostList;
import ac.ucas.ir.search.similarity.DocumentsTfidf;
import ac.ucas.ir.search.similarity.TfidfSimilarity;


/****
 * 
 *  the Cluster's label is select from the title of two documents which have the biggest similar with the center . 
 *  
 * **/

public class Cluster { 
       private List<Document> docList;
       private List<MembersOfclass> Listclass=new ArrayList<MembersOfclass>(); 
       private int size; 
       private  float [][] similar;
       private  float [][] temp; 
       
       public Cluster(List<Document> docList)
       {
    	   
    	   this.docList=docList;
    	   try{ this.size=docList.size();
    	   
    		   similar=new float[size][size];
    		   temp=new float[size][size];
    	   }catch( Exception e)
    	   {
    		   System.out.println("Error in Cluster_Cluster"+e);
    	   }
       }
       
       public void maritxPrint(float mar[][],int num)
       {
    	   int i=0;
    	   int j=0;
    	   for(i=0;i<num;i++)
    	   {
    		   for(j=0;j<num;j++)
    		   {
    			   System.out.print(mar[i][j]);
    			   System.out.print("            ");
    		   }
    		   System.out.print("\n");
    	   }
       }
       
       public void initClasses(int documentsize,PostList list)
       {
    	     if(docList==null||docList.size()==0)
    	     {
    	    	System.out.println(" doclist is Empty!"); 
    	     }
    	     else
    	      {
    	    	 
    	    	// System.out.print(size);
    	    	try{ 
    	    	 int i=0;
    	    	 for(i=0;i<size;i++)
    	    	  {
    	    		 DocumentsTfidf docsvector=new DocumentsTfidf(docList.get(i).getDocID());
    	    		 docsvector.Calutfidfweight(documentsize, list);
    	    		 
    	    		 MemberNode node=new MemberNode(docList.get(i).getDocID(),i);
    	    		 node.setMembervector(new MemberVector(docsvector.getIfweight()));
    	    		 node.setTitle(docList.get(i).getFiledContent("title"));
    	    		 System.out.println(docList.get(i).getFiledContent("title"));
    	    		 MembersOfclass nodelist= new MembersOfclass (node,i);
    	    		 Listclass.add(nodelist);
    	    		 
    	    		 int j=0;   	    		
    	    		 for(j=0;j<i;j++)
    	    		 {
    	    			 TfidfSimilarity tfidf=new TfidfSimilarity(docList.get(i).getDocID(),docList.get(j).getDocID());
    	    			 tfidf.Calutfidfsimilarity(documentsize, list);
    	    			 similar[i][j]=tfidf.getTfidfsimilar();
    	    			 //System.out.print(similar[i][j]);
    	    		 }
    	    		
    	    	  }
    	    	}catch(Exception e)
    	    	{
    	    		 System.out.println("Error in Cluster_initClasses"+e);
    	    	}
    	    	 maritxPrint(similar,size);
    	     }
       }
       
       
       public float max(float a, float b)
       {
    	   if(a>b)
    		   return b;
    	   return a;
       }
       
       
       public void Merage()
       {
    	    int index1=-1;
    	    int index2=-1;
    	    int i=0;
    	    int j=0;
    	    float t_emp=0;
    	    for(i=1;i<size;i++)
    	    {
    	    	for(j=0;j<i;j++)
    	    	{
    	    		if(similar[i][j]>t_emp)
    	    		{
    	    			index1=i;
    	    			index2=j;
    	    			t_emp=similar[i][j];
    	    		}
    	    	}
    	    }
    	    
    	    //find out the index1 and index2;
    	    
    	    for(i=index1+1;i<size;i++)
    	       Listclass.get(i).setSeq(i-1); 
    	    Listclass.get(index2).addMemberList(Listclass.get(index1).getMemberlist(), index1);
    	    Listclass.remove(index1);
    	       
    	    for(i=1;i<size-1;i++)
    	    { 
    	        for(j=0;j<i;j++)
    	        {
    	           if(i<index1)
    	           {   
    	        	   if(i==index2)
    	        	   {
    	        		  temp[i][j]=max(similar[index1][j],similar[i][j]); 
    	        	   }else if(i>index2&&j==index2)
    	        	   {
    	        		  temp[i][j]=max(similar[i][j],similar[index1][i]);
    	        	   }
    	        	   else
    	        	   {
    	        	    temp[i][j]=similar[i][j];
    	        	   }
    	           }
    	           else 
    	           {
    	        	    if(j==index2)
    	        	    {
    	        	    	temp[i][j]=max(similar[i+1][j],similar[i+1][index1]);
    	        	    }
    	        	    else if(j>=index1)
    	        	    {
    	        	    	temp[i][j]=similar[i+1][j+1]; 
    	        	    }
    	        	    else
    	        	    {
    	        	    	temp[i][j]=similar[i+1][j];
    	        	    }
    	        	   
    	           }
    	        }
    	    }
    	    
    	    size=size-1;   	    
    	    similar=temp.clone();    	    
    	   // maritxPrint(similar,size);
    	    temp=new float[size][size];
    	   
    	  
       }
       
       
       public  void  clusterTosize(int num)
       {
    	   if(size<=num)
    		   return;
    	   while(size>num)
    	   {
    		   Merage();
    	   }
    	   
       }
       
       public void  labelselect(PostList index,int documentsize )
       {
    	   int i=0;
    	   System.out.println("in labselect!");
    	   
    	   for(i=0;i<Listclass.size();i++)
    	   {  
    		  // Listclass.get(i).getCenter();
    		   Listclass.get(i).Calucenter();
    		   Listclass.get(i).selectTwodoc();
    		   LableSelect labsec=new LableSelect(Listclass.get(i));
    		   labsec.selectlabel(index, documentsize); 
    		   System.out.println(labsec.getLabel());
    		   Listclass.get(i).setLabel(labsec.getLabel());
    	   }
       }
       
       public  void showcluster()
       {
    	    System.out.print("cluster 结果：");
    	    System.out.println(size);
    	    Iterator<MembersOfclass> ite1=Listclass.iterator();
    	    MembersOfclass one;

    		while(ite1.hasNext())
    	    {
    	    	one=ite1.next();
    	    	System.out.println(one.getMemberlist().size());
    	    	List<MemberNode> node=one.getMemberlist();
    	    	Iterator<MemberNode>it=node.iterator();
    	    	System.out.println(one.getLabel());
    	    	
    	    	while(it.hasNext())
    	    	{
    	    		out(it.next().getDocid());
    	    		
    	    		//System.out.print("  ");
    	    	}
    	    	System.out.println();
    	    }
       }
   	  
      public void out(int docid)
      {
    	    Document doc=null;
    	    int i=0;
    	    for(i=0;i<docList.size();i++)
    	    {
    	    	if(docList.get(i).getDocID()==docid)
    	    	{
    	    		doc=docList.get(i);
    	    		break;
    	    	}
    	    }
    	    //doc=docList.g
    		String line = new String();
			line = line + "{";
		//	line = line + doc.getDocID();
			Iterator<Field> iteField = doc.getDocContent().iterator();
			while(iteField.hasNext()){
				Field field = iteField.next();
				line = line + "\"";
				line = line + field.getName();
				line = line + "\":\"";
				line = line + field.getValue();
				line = line + "\"";
				line = line + ",";
			}
			//line=line.substring(0,line.length()-2);
			line = line.substring(0, line.length() - 1);
			line = line + "}";
			System.out.println(line);
      }
}
