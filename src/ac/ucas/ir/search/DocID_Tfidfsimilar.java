package ac.ucas.ir.search;

public class DocID_Tfidfsimilar implements Comparable<DocID_Tfidfsimilar> {
	private int docID;
	private float tfidfsimilar;
	
	public DocID_Tfidfsimilar(int docID,float tfidfsimilar)
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
	public int compareTo(DocID_Tfidfsimilar arg)
	{
          return (new Float(arg.getTfidfsimilar())).compareTo(new Float(this.getTfidfsimilar()));
	}
}
