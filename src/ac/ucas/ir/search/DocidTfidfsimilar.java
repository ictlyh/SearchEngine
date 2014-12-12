package ac.ucas.ir.search;

public class DocidTfidfsimilar implements Comparable<DocidTfidfsimilar>  {
	private int docID;
	private float tfidfsimilar;
	
	public DocidTfidfsimilar(int docID,float tfidfsimilar)
	{
		this.docID=docID;
		this.tfidfsimilar=tfidfsimilar;
	}
   
	public int getDocID()
	{
		return docID;
	}
	
	public float getTfidfsimilar()
	{
		return tfidfsimilar;
	}
	
	@Override
	public int compareTo(DocidTfidfsimilar arg)
	{
        return (new Float(arg.getTfidfsimilar())).compareTo(new Float(this.getTfidfsimilar()));
		//return (new Float(this.getTfidfsimilar())).compareTo(new Float(arg.getTfidfsimilar()));
	}

}