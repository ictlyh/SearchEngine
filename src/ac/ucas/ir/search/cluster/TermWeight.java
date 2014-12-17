package ac.ucas.ir.search.cluster;

//import ac.ucas.ir.search.DoctermsTfidf;

public class TermWeight implements Comparable<TermWeight>{
             private String term;
             private float weight;
             public  TermWeight(String ter,float wei)
             {
            	 term=ter;
            	 weight=wei;
             }
             
             public float getWeight()
             {
            	 return weight;
             }
             public String getTerm()
             {
            	 return term;
             }
             
             @Override
         	public int compareTo(TermWeight arg)
         	{
                 return (new Float(arg.getWeight())).compareTo(new Float(this.getWeight()));
         		//return (new Float(this.getTfidfsimilar())).compareTo(new Float(arg.getTfidfsimilar()));
         	}
}
