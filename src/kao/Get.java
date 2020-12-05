package kao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Get {
	static void copy(BufferedReader br, StringWriter sw) throws IOException {
        char[] buf = new char[8192];
        int length;
        while ((length = br.read(buf)) > 0) {
            sw.write(buf, 0, length);
        }
    }
	/**
	 * HTTP호출
	 * @param url
	 * @param parameters
	 * @return String
	 * @throws IOException 
	 */
	public synchronized static String get(String url, Object parameters, String encoding) throws IOException{
		
	      URLConnection con = null;
	      OutputStreamWriter osw = null;
	      InputStreamReader isr = null;
	      System.out.println("GET "+url);
	      StringWriter sw = new StringWriter();
	      int readTimeout = 60000;
	      int connectionTimeout = 60000;


	        con =  new URL(url).openConnection();
	        /**
	         * 소켓자원이 회수가 안되고 계속 생성되어서
	         * Hang 상태에 빠지는 증세가 발생하여 아래 timeout 설정들을 추가
	         */

	        con.setConnectTimeout(readTimeout);
	        con.setReadTimeout(connectionTimeout);

	        if(encoding!=null){
	          con.setRequestProperty("Accept-Charset", encoding);
	        }

	        if(parameters!=null){
	          con.setDoOutput(true);
	          System.out.println("파라메터 : "+parameters.toString());
	          osw = new OutputStreamWriter(con.getOutputStream());
	          osw.write(parameters.toString());
	          osw.flush();
	        }
	        isr = new InputStreamReader(con.getInputStream(),"utf-8");
	        BufferedReader br = new BufferedReader(isr);
	        copy(br,sw);
	        System.out.println("body :"+sw.toString());



	        if(osw != null){
	          try{
	            osw.close();
	          }catch(Exception e){
	            e.printStackTrace();
	          }
	        }
	        if(isr != null){
	          try{
	            isr.close();
	          }catch(Exception e){
	            e.printStackTrace();
	          }
	        }
	      
	      return sw.toString();
	    }
	public static void main(String[] args) throws IOException {
		String body = get("http://124.46.166.33:1337/kkaousers", null, "utf-8");
		System.out.println(body);
		try {
			JSONParser jsonParser = new JSONParser();
			JSONArray jsonArr = (JSONArray) jsonParser.parse(body);
			for(int i=0; i<jsonArr.size();i++) {
				JSONObject user = (JSONObject) jsonArr.get(i);
				System.out.println("userID: "+user.get("userID"));
				System.out.println("created_at: "+user.get("created_at"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
}
