package ac.ucas.ir.search.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ac.ucas.ir.index.PostList;
import ac.ucas.ir.search.Query;

public class LableSelect {
          private  MembersOfclass memsclass;
          private String label;
          public LableSelect(MembersOfclass msclass)
          {
        	  memsclass=msclass;
        	  label=new String();
          }
          
          public String getLabel()
          {
        	  return label;
          }
          
          public void selectlabel(PostList index,int documentsize)
          {
        	  if(memsclass== null)
        	  {
        		  System.out.println("membersofclass is empty");
        		  return;
        	  }
        	  if(!memsclass.getOnlyone())
        	  {
        		 // 
        		 // System.out.println("in selectlab one titel");
        		String title=memsclass.gettitleBydocid(memsclass.getdocid1());//memsclass.getdocid1();  
        		LabelGet1(title, index, documentsize);
        	  }
        	  else
        	  {
        		//  System.out.println("in selectlab two titel");
        		  String title1=memsclass.gettitleBydocid(memsclass.getdocid1());
        		  String title2=memsclass.gettitleBydocid(memsclass.getdocid2());
        		  LabelGet2(title1,title2,index,documentsize);
        	  }
          }
          
          public void LabelGet1(String title,PostList index,int documentsize)
          {
        	  Query query =new Query(title);
        	  String Label="";
        	  if(query.getQuerywords().size()<4)
        	  {
        		  int i=0;
        		  for(i=0;i<query.getQuerywords().size();i++)
        			   Label+=query.getQuerywords().get(i)+" ";
        		  label=Label; 
        		  return ;
        	  }
        	  label=selectwords( memsclass.getdocid1(),query.getQuerywords(),index,documentsize);
        	  
        	        	  
          }
          
          public String selectwords(int docid,List<String> words,PostList index,int documentsize)
          {
        	  if(words==null||words.size()==0)
        	  {
        		  System.out.println("words is empry in TermWeight");
        		  return null;
        	  }
        	  List<TermWeight> Termwes=new ArrayList<TermWeight>();
        	  int i=0;
        	  float tf=0;
        	  float df=0;
        	  for(i=0;i<words.size();i++)
        	  {
        		df=index.getDfByTerm(words.get(i));
        		if(df!=0)
        		{
        			df=(float) Math.log10((float)documentsize/df);
        		}
        		tf=index.getTermTfByDocID(words.get(i), docid);
        		if(tf!=0)
        		{
        			tf=(float) (1+Math.log10(tf));
        		}
        		TermWeight one=new TermWeight(words.get(i),df*tf);
        		Termwes.add(one);
        	  }
        	  Collections.sort(Termwes);
        	  String labelwords="";
        	  int count=1;
        	  Iterator <TermWeight> ite=Termwes.iterator();
        	  while(ite.hasNext()&&count<4)
        	  {
        		  labelwords+=ite.next().getTerm()+" ";
        		  count++;
        	  }
        	  return labelwords;
          }
          
          public void LabelGet2(String title1,String title2,PostList index,int documentsize)
          {
        	  List<String> unionlist=new ArrayList<String>();
        	  unionlist=unionTwostring(title1,title2);
        	  if(unionlist==null||unionlist.size()==0)
        	  {
        		  //System.out.println("")
        		  if(title1==null)
        		  {
        			  System.out.println("title is nill in LabelGet2");
        			  return;
        		  }
        		  LabelGet1( title1,index, documentsize);
        		  return ;
        	  }
        	  String Label="";
        	  if(unionlist.size()<4)
        	  {
        		  int i=0;
        		  for(i=0;i<unionlist.size();i++)
        			   Label+=unionlist.get(i)+" ";
        		  label=Label; 
        		  return ;
        	  }
        	  label=selectwords( memsclass.getdocid1(),unionlist,index,documentsize);
        	  
        	   
        		  
        	
          }
          
          public List<String> unionTwostring(String t1,String t2)
          {
        	  Query query1 =new Query(t1);
        	  Query query2 =new Query(t2);
        	  if(query1.getQuerywords()==null||query2.getQuerywords()==null||query1.getQuerywords().size()==0||query2.getQuerywords().size()==0)
        	  {
        		  System.out.println("string t1 ||string t2 is null");
        		  return null;
        	  }
        	  List<String>union=new ArrayList<String>();
        	  int i=0;
        	  int j=0;
        	  for(i=0;i<query1.getQuerywords().size();i++)
        	  {
        		  for(j=0;j<query2.getQuerywords().size();j++)
        		  {
        			  if(query1.getQuerywords().get(i).equals(query2.getQuerywords().get(j)))
        				 {
        				   union.add(query1.getQuerywords().get(i));
        				   break;
        			    }
        		  }
        	  }
        	  return union;
          }
          
}
