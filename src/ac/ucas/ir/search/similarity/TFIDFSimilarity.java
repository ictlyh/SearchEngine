package ac.ucas.ir.search.similarity;
import java.util.Iterator;
import java.util.List;
import ac.ucas.ir.index.*;

public class TFIDFSimilarity {
	private float ifidfsimilar;
	private int docID;
	public TFIDFSimilarity(int docID)
    {
    	this.docID=docID;
    }
	
	public float getTfidfsimilar()
	{
		return ifidfsimilar;
	}
	
	public float Calutwofvector(List<Float>v1,List<Float>v2)
	{
		Iterator<Float> itev1=v1.iterator();
		Iterator<Float> itev2=v2.iterator();
		float result=0;
		while(itev1.hasNext()&&itev2.hasNext())
		{
			result+=itev1.next().floatValue()*itev2.next().floatValue();
		}
		return result;
	}
   	  
	
    public void Calutfidfsimilarity(int documentsize,PostList list,List<String> querywords)//文件数目，全局索引，查询字段
    {
    	 Documentstf docitf=new Documentstf(docID);
    	 docitf.getTfwightofDocuments(list);
    	 Querytfidf  termtfidf=new Querytfidf(querywords);
    	 termtfidf.Calutfidfweight(documentsize, list);
    	 float result=0;
    	 result=Calutwofvector(docitf.getIfweight(),termtfidf.getTfidfweight());
    	 float temp1=(float) Math.sqrt(Calutwofvector(docitf.getIfweight(),docitf.getIfweight()));
    	 float temp2=(float) Math.sqrt(Calutwofvector(termtfidf.getTfidfweight(),termtfidf.getTfidfweight()));
    	 result=result/(temp1*temp2); 	 
    }

}
