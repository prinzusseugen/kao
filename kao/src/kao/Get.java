package kao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

public class Get {
	/**
	 * HTTP호출
	 * @param url
	 * @param parameters
	 * @return String
	 */
	public synchronized static String get(String url, Object parameters, String encoding){
	  StringBuffer sb = new StringBuffer();

	  URLConnection con = null;
	  OutputStreamWriter osw = null;
	  InputStreamReader isr = null;
	  System.out.println("API 호출 : "+url);

	  int readTimeout = 60000;
	  int connectionTimeout = 60000;

	  try{

	    con =  new URL(url).openConnection();
	    /**
	     * 소켓자원이 회수가 안되고 계속 생성되어서
	     * Hang 상태에 빠지는 증세가 발생하여 아래 timeout 설정들을 추가
	     */

	    con.setConnectTimeout(readTimeout);
	    con.setReadTimeout(connectionTimeout);

	    if(encoding!=null){
	      System.out.println("인코딩 설정 : "+encoding);
	      con.setRequestProperty("Accept-Charset", encoding);
	    }

	    if(parameters!=null){
	      con.setDoOutput(true);
	      System.out.println("파라메터 : "+parameters.toString());
	      osw = new OutputStreamWriter(con.getOutputStream());
	      osw.write(parameters.toString());
	      osw.flush();
	    }
	    isr = new InputStreamReader(con.getInputStream());
	    BufferedReader br = new BufferedReader(isr);

	    String tmp = "";
	    while((tmp=br.readLine())!=null){
	      sb.append(tmp);
	    }
	    System.out.println("결과값 : "+sb.toString());
	  }catch(Exception e){
	    e.printStackTrace();
	  }finally{

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
	  }
	  return sb.toString();
	}
	public static void main(String[] args) {
		String body = get("http://localhost:1337/kkaousers", null, "utf-8");
		System.out.println(body);
	}
}
