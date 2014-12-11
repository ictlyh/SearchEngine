package ac.ucas.ir.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetMerge {

	public static Set<String> merge(Set<String> s1, Set<String> s2){
		Set<String> result = new HashSet<String>();
		Iterator<String> ite = s1.iterator();
		while(ite.hasNext()){
			result.add(ite.next());
		}
		ite = s2.iterator();
		while(ite.hasNext()){
			result.add(ite.next());
		}
		return result;
	}
}