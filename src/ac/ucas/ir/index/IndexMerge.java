package ac.ucas.ir.index;

import java.util.Iterator;
import java.util.Set;
import ac.ucas.ir.util.SetMerge;

public class IndexMerge {

	public PostList indexMerge(PostList pl1, PostList pl2){
		if(pl1 == null) {
			return pl2;
		}
		if(pl2 == null) {
			return pl1;
		}
		PostList index = new PostList();
		Set<String> key1 = pl1.getIndex().keySet();
		Set<String> key2 = pl2.getIndex().keySet();

		new SetMerge();
		Iterator<String> ite = SetMerge.merge(key1, key2).iterator();
		while(ite.hasNext()){
			String term = ite.next();
			PostListRecord plr1 = pl1.getIndex().get(term);
			PostListRecord plr2 = pl2.getIndex().get(term);
			if(plr1 != null && plr2 != null){
				//plr2.print();
				plr1.merge(plr2);
				index.addPostListRecord(plr1);
			}
			else if(plr1 != null){
				index.addPostListRecord(plr1);
			}
			else if(plr2 != null){
				index.addPostListRecord(plr2);
			}
		}
		pl1.clear();
		pl2.clear();
		return index;
	}
}