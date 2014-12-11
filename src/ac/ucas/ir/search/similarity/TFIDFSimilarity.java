package ac.ucas.ir.search.similarity;
import java.util.Iterator;
import java.util.List;
import ac.ucas.ir.index.*;

public class TfidfSimilarity {
	private float ifidfsimilar;
	private int docID;
	
	public TfidfSimilarity(int docID)
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
   	  
	
    public void Calutfidfsimilarity(int documentsize,PostList list,List<String> querywords)//鏂囦欢鏁扮洰锛屽叏灞€绱㈠紩锛屾煡璇㈠瓧娈?
    {
    	 DocumentsTfidf docitf=new DocumentsTfidf(docID);
    	 docitf.Calutfidfweight(documentsize, list);
    	 QueryTfidf  termtfidf=new QueryTfidf(querywords);
    	 termtfidf.Calutfidfweight(documentsize, list);
    	 float result=0;
    	 result=Calutwofvector(docitf.getIfweight(),termtfidf.getTfidfweight());
    	 float temp1=(float) Math.sqrt(Calutwofvector(docitf.getIfweight(),docitf.getIfweight()));
    	 float temp2=(float) Math.sqrt(Calutwofvector(termtfidf.getTfidfweight(),termtfidf.getTfidfweight()));
    	 result=result/(temp1*temp2); 
    	 ifidfsimilar=result;
    }

}
