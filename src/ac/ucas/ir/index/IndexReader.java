package ac.ucas.ir.index;

import java.io.File;
import ac.ucas.ir.store.IndexInput;

public class IndexReader {

	public PostList loadIndexFromDirectory(String dirPath){
		File root = new File(dirPath);
		PostList index = new PostList();
		IndexMerge im = new IndexMerge();
		IndexInput ii = new IndexInput();
		File[] files = root.listFiles();
		for(File file : files){
			PostList src = ii.loadIndexFromFIle(file.getPath());
			index = im.indexMerge(index,src);
		}
		return index;
	}
}