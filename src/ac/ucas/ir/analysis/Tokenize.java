package ac.ucas.ir.analysis;

public class Tokenize {
	private String tokenString;
	public Tokenize(String content,String encode){
		tokenString = new Analyzer().tokenizeByNLPIR(content, encode);
	}
	
	public String getTokenString() {
		return tokenString;
	}
	
	public void setTokenString(String tokenString) {
		this.tokenString = tokenString;
	}
	
	public boolean hasMoreTokens(){
		return (!tokenString.equals(""));
	}
	
	public String nextToken(){
		String token;
		try{
			token = tokenString.substring(0, tokenString.indexOf('/'));
		}catch(Exception e){
			tokenString = "";
			return null;
		}
		String lastString;
		try{
			lastString = tokenString.substring(tokenString.indexOf(' ') + 1);
		}catch(IndexOutOfBoundsException ex){
			lastString = "";
		}
		tokenString = lastString;
		return token;
	}
}