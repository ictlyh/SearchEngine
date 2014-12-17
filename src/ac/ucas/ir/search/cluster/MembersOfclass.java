package ac.ucas.ir.search.cluster;

import java.util.ArrayList;
//import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MembersOfclass {
           private List<MemberNode> Memberslist=new ArrayList<MemberNode>();
           private int seq;
           private List<Float> center;
           String  label;
           int docid1;
           int docid2;
           boolean onlyone;
           //private List<MemberVector> vectors;
           public boolean getOnlyone()
           {
        	   return onlyone;
           }
           
           public String gettitleBydocid(int docid)
           {
        	   int i=0;
        	   for(i=0;i<Memberslist.size();i++)
        	   {
        		   if(Memberslist.get(i).getDocid()==docid)
        		   {
        			   return Memberslist.get(i).getTitle();
        		   }
        	   }
        	   return null;
           }
           
           
           public int getdocid1()
           {
             return docid1;
           }
           
           public int getdocid2()
           {
        	   return docid2;
           }
           
           public void setSeq( int seq1)
           { 
        	 seq=seq1;  
           }
           
           public String getLabel()
           {
        	   return label;
           }
           public void setLabel(String lab)
           {
        	   label=lab;
           }
           
           public MembersOfclass( MemberNode initone,int seq)
           {
        	       this.seq=seq;
        	       Memberslist.add( initone);
        	       center=new ArrayList<Float>();
        	       onlyone=false;
           }
           
           public int  getSeq()
           {
        	   return seq;
           }
           
           public List<Float> getCenter() 
           {
        	 return center;   
           }
        
           public List<MemberNode> getMemberlist()
           {
        	   return Memberslist;
           }
          
           public void addMemberList(List<MemberNode> members,int seq)
           {  
        	   if(seq<this.seq)  // Merger the big_seq class into the small_eq class
        		   this.seq=seq;
        	    try{
        	    	Iterator<MemberNode> ite1=members.iterator();
        	    	while(ite1.hasNext())
        	    	{
        	    		addonemember(ite1.next());
        	    	}
        	    }catch(java.lang.NullPointerException e)
        	    {
        	    	System.out.println("Error in the  addMemberList"+e);
        	    }
        	   
           }
           
           public boolean addonemember(MemberNode node)
           {
        	   if(node==null)
        		   return false;
        	   int i=0;
        	   for(i=0;i<Memberslist.size();i++)
        	   {
        		  if(Memberslist.get(i).getpriority()>node.getpriority())
        		  {
        			  Memberslist.add(i, node);
        			  return true;
        		  }
        	   }
        	   Memberslist.add(node); 
        	   return true;
           }
           
          // @SuppressWarnings("unchecked")
		public void Calucenter()
           {
        	   if(Memberslist==null||Memberslist.size()==0)
        	   {
        		   System.out.println("class is Empty");
        		   return ;
        	   }
        	   center=Memberslist.get(0).getMembervector().getMvector();
        	   if(Memberslist.size()==1)
        	   {   
        		   return ;
        	   }
        	   int i=0;
        	   for(i=1;i<Memberslist.size();i++)
        	   {
        		   center=Listplus(center,Memberslist.get(i).getMembervector().getMvector());
        	   }
        	 
           }
		
		public  List<Float> Listplus(List<Float> m1,List<Float> m2)
		{ 
			if(m1==null||m1.size()==0)
			{
				System.out.println("MemberNode is Empty!");
				return null;
			}
			List<Float>temp=new ArrayList<Float>();
			int i=0;
			for(i=0;i<m1.size();i++)
			{
			   temp.add(new Float(m1.get(i).floatValue()+m2.get(i).floatValue()));
			}
			return temp;
		}
         
		public  float Listmul(List<Float> m1,List<Float> m2)
		{ 
			if(m1==null||m1.size()==0)
			{
				System.out.println("MemberNode is Empty!");
				return -1;
			}
			float temp=0;
			int i=0;
			for(i=0;i<m1.size();i++)
			{
			   temp+=m1.get(i).floatValue()*m2.get(i).floatValue();
			}
			return temp;
		}
		
		public void selectTwodoc()
		{
		   if(Memberslist==null||Memberslist.size()==0)
		   {
			   System.out.println("Memberslist is Empty!"); 
			   return;
		   }
		    if(Memberslist.size()==1)
		    {
		    	docid1=Memberslist.get(0).getDocid();
		    	return ;
		    }
		    docid1=docid2=-1;
		    float temp1=0;
		    float temp2=0;
		    float  s=0;
		    int i=0;
	try{
		    for(i=0;i<Memberslist.size();i++)
		    { 
		    	onlyone=true;
		    	s=Listmul(center,Memberslist.get(i).getMembervector().getMvector());
		    	if(s>temp2)
		    	{
		    		if(s>temp1)
		    		{
		    			docid2=docid1;
		    			docid1=Memberslist.get(i).getDocid();
		    			temp2=temp1;
		    			temp1=s;
		    		}
		    		else
		    		{
		    			docid2=Memberslist.get(i).getDocid();
		    			temp2=s;
		    		}
		    	}
		    }
	   }catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	}
          
}
