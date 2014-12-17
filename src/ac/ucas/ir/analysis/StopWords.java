package ac.ucas.ir.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class StopWords {
	private String stopwords;
	
	public StopWords(){
		readFileIntoString();
	}
	
	public String getStopwords() {
		return stopwords;
	}

	public void setStopwords(String stopwords) {
		this.stopwords = stopwords;
	}
	
	public boolean isStopword(String word){
		return stopwords.contains(word);
	}
	
	private void readFileIntoString(){
		InputStream fis = null;
		try {
			fis = new FileInputStream(new File("stopwords.txt"));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		//fis = this.getClass().getResourceAsStream("/res/stopwords.txt");
		stopwords = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(fis,"utf-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String line = "";
		try {
			while((line = br.readLine()) != null)
				stopwords = stopwords + line + " ";
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}