package ac.ucas.ir.search;

public class DoctermsTfidf implements Comparable<DoctermsTfidf> {
	private int docID;
	private float tfidf;
	
	public  DoctermsTfidf(int docID,float tfidf)
	{
		this.docID=docID;
		this.tfidf=tfidf;
	}
   
	public int getDocID()
	{
		return docID;
	}
	
	public float getTfidf()
	{
		return tfidf;
	}
	
	@Override
	public int compareTo(DoctermsTfidf arg)
	{
        return (new Float(arg.getTfidf())).compareTo(new Float(this.getTfidf()));
		//return (new Float(this.getTfidfsimilar())).compareTo(new Float(arg.getTfidfsimilar()));
	}
}
