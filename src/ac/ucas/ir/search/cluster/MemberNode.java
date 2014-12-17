package ac.ucas.ir.search.cluster;

public class MemberNode {
            private int docID;
            private int priority;
            private String title; 
            private MemberVector mvector;
             
            public MemberNode(int docID,int priority)
            {
            	this.docID=docID;
            	this.priority=priority;
            }
            
            public String getTitle()
            {
            	return title;
            }
            
            public  void setTitle(String titl)
            {
            	title=titl;
            }
            
            public int getDocid()
            {
            	return docID;
            }
            
            public int getpriority()
            {
            	return  priority;
            }
            
            public  MemberVector getMembervector()
            {
            	return mvector;
            }
            
            public void setDocid(int doc)
            {
            	docID=doc;
            }
            
            public void setpriority(int prior) 
            {
            	 priority=prior;
            }
            
            public void setMembervector(MemberVector vector )
            {
            	mvector=vector;
            }
            
            
}
