package ac.ucas.ir.store;

import java.io.*;

import ac.ucas.ir.index.PostList;
import ac.ucas.ir.index.PostListNode;
import ac.ucas.ir.index.PostListRecord;

public class IndexInput {

	public PostList loadIndexFromFIle(String filePath){
		File file = new File(filePath);
		if(!file.exists()){
			System.out.println("Can not open index file " + filePath);
			System.exit(0);
		}
		PostList postList = new PostList();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e1) {
			e1.printStackTrace();
		}

		String line = null;
		try {
			line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int begin = 0;
		int end = 0;
		while(line != null && line.length() != 0){
			end = line.indexOf(',');
			String term = line.substring(0, end);
			PostListRecord plr = new PostListRecord(term);
			String last = line.substring(end + 1);
			end = last.indexOf('-');
			last = last.substring(end);
			end = last.indexOf('-');
			while(end != - 1){
				begin = end + 3;
				end = last.indexOf(',');
				String docID = last.substring(begin, end);
				PostListNode pln = new PostListNode(Integer.parseInt(docID));
				last = last.substring(end + 1);
				end = last.indexOf(')');
				String tf = last.substring(0, end);
				pln.setTf(Integer.parseInt(tf));
				plr.addPostListNode(pln);
				last = last.substring(end + 1);
				end = last.indexOf('-');
			}
			postList.addPostListRecord(plr);
			try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return postList;
	}
}