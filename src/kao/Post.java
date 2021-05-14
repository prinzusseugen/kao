package kao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.simple.JSONObject;

public class Post {
    /**
     * Post 방식
     * 요청 방식 : Key-set , json 선택
     * 응답방식 : JSON
     * @param  sendUrl
     * @param  data
     * @param  sendType ( "json" or "key" )
     * @return HashMap resData
     * @throws Exception
     */
    public static String post( String sendUrl ,  String data , String sendType ) throws Exception {
      JSONObject jobj = null;
      URL url = new URL(sendUrl);
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("POST");
      byte[] postDataBytes = null ;

      if(sendType.equals("json")) {
        conn.setRequestProperty("Content-Type", "application/json");
      }else {
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      }
      postDataBytes = data.getBytes("UTF-8");
      if(postDataBytes !=null && postDataBytes.length != 0){
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuffer sf = new StringBuffer();

        for (int c; (c = in.read()) >= 0;)    sf.append((char)c);
        conn.disconnect();

        String resStr = sf.toString();
        return resStr;
      }else {
        conn.disconnect();
        return null;
      }
    }
    public static void main(String[] args) {
    	String user = "{\"userID\":\"prinzeugen\",\"userPW\":\"4321\",\"nickname\":\"aai\"}";	// 테스트 용
    	System.out.println(user);
    	try {
			String body = post("http://203.234.62.94:1337/kkaousers", user, "json");
			System.out.println(body);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
