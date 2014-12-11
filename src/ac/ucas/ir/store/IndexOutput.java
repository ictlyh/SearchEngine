package ac.ucas.ir.store;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import ac.ucas.ir.index.PostList;
import ac.ucas.ir.index.PostListNode;
import ac.ucas.ir.index.PostListRecord;

public class IndexOutput {
	@SuppressWarnings("rawtypes")
	public void writeIndexToFile(PostList postList,String filePath){
		File file = new File(filePath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Iterator<Entry<String, PostListRecord>> iter = postList.getIndex().entrySet().iterator();
		while (iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			PostListRecord plr = (PostListRecord) entry.getValue();
			String line = new String();
			line = line + plr.getTerm();
			line = line + ",";
			line = line + plr.getDf();
			Iterator<PostListNode> itePLN = plr.getPostListRecord().iterator();
			while(itePLN.hasNext()){
				line = line + "->";
				PostListNode pln = itePLN.next();
				line = line + "(";
				line = line + pln.getDocID();
				line = line + ",";
				line = line + pln.getTf();
				line = line + ")";
			}
			try {
				bw.write(line, 0, line.length());
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bw.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}