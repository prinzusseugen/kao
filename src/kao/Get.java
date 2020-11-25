package kao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Get {
	/**
	 * HTTPȣ��
	 * @param url
	 * @param parameters
	 * @return String
	 */
	public synchronized static String get(String url, Object parameters, String encoding){
	  StringBuffer sb = new StringBuffer();

	  URLConnection con = null;
	  OutputStreamWriter osw = null;
	  InputStreamReader isr = null;
	  System.out.println("API ȣ�� : "+url);

	  int readTimeout = 60000;
	  int connectionTimeout = 60000;

	  try{

	    con =  new URL(url).openConnection();
	    /**
	     * �����ڿ��� ȸ���� �ȵǰ� ��� �����Ǿ
	     * Hang ���¿� ������ ������ �߻��Ͽ� �Ʒ� timeout �������� �߰�
	     */

	    con.setConnectTimeout(readTimeout);
	    con.setReadTimeout(connectionTimeout);

	    if(encoding!=null){
	      System.out.println("���ڵ� ���� : "+encoding);
	      con.setRequestProperty("Accept-Charset", encoding);
	    }

	    if(parameters!=null){
	      con.setDoOutput(true);
	      System.out.println("�Ķ���� : "+parameters.toString());
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
	    System.out.println("����� : "+sb.toString());
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
