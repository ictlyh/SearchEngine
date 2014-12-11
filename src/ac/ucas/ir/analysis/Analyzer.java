package ac.ucas.ir.analysis;

import java.io.UnsupportedEncodingException;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class Analyzer {

	// 定义接口CLibrary，继承自com.sun.jna.Library
	private interface CLibrary extends Library {
	// 定义并初始化接口的静态变量
		CLibrary Instance = (CLibrary) Native.loadLibrary(
				"E:\\StudyFiles\\Workspace\\Eclipse\\SearchEngine\\lib\\win64\\NLPIR", CLibrary.class);
			
		public int NLPIR_Init(String sDataPath, int encoding,String sLicenceCode);
		public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);
		public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit,boolean bWeightOut);
		public String NLPIR_GetFileKeyWords(String sLine, int nMaxKeyLimit,boolean bWeightOut);
		public int NLPIR_AddUserWord(String sWord);
		public int NLPIR_DelUsrWord(String sWord);
		public String NLPIR_GetLastErrorMsg();
		public void NLPIR_Exit();
	}
	
	private static String transString(String aidString, String ori_encoding,String new_encoding) {
		try{
			return new String(aidString.getBytes(ori_encoding), new_encoding);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return null;
	}

	public String tokenizeByNLPIR(String content,String encode){
		if( !encode.equals("UTF-8") )
			content = transString(content,encode,"UTF-8");
		String argu = "";
		int charset_type = 1;
		int init_flag = CLibrary.Instance.NLPIR_Init(argu, charset_type, "0");
		String nativeBytes = null;

		if (0 == init_flag) {
			nativeBytes = CLibrary.Instance.NLPIR_GetLastErrorMsg();
			System.err.println("初始化失败！fail reason is "+nativeBytes);
			System.exit(0);
		}
		try {
			nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(content, 1);
			CLibrary.Instance.NLPIR_Exit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return nativeBytes;
	}
}