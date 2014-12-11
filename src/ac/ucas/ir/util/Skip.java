package ac.ucas.ir.util;

public class Skip {

	private String skips;
	
	public Skip(){
		this.skips = " \t\n";
	}
	
	public Skip(String skips){
		this.skips = skips;
	}
	
	public String skip(String src){
		String result = new String();
		for(int i = 0; i < src.length(); i++){
			int j = 0;
			for(; j < skips.length(); j++){
				if(src.charAt(i) == skips.charAt(j))
					break;
			}
			if(j == skips.length()){
				result = result + src.charAt(i);
			}
		}
		return result;
	}
}