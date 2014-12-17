package ac.ucas.ir.search.cluster;
import  java.util.*;

public class MemberVector {
              private List<Float> mvector; 
              
              public MemberVector(List<Float> vector)
              {
            	  mvector=vector;
              }
              
              public void setMvector(List<Float> vector)
              {
            	  mvector=vector;
              }
              
              public List<Float> getMvector()
              {
            	  return mvector;
              }
}
