package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sf.json.JSONArray;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.Logger;

public class HttpUtil {
	public static String getFromHttp(String Url,NameValuePair[] data){
		HttpClient client = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true)); 
		PostMethod method = new PostMethod(Url);  //post过去	
		client.getHttpConnectionManager().getParams().setConnectionTimeout(600*1000);
		client.getHttpConnectionManager().getParams().setSoTimeout(600*1000);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		 
	    /**
		NameValuePair[] data = {//提交短信
			    new NameValuePair("account", account), 
			    //new NameValuePair("password", "密码"), //密码可以使用明文密码或使用32位MD5加密
			    new NameValuePair("password", StringUtil.MD5Encode(password)),
			    new NameValuePair("mobile", mobile), 
			    new NameValuePair("content", content),
		};
		**/
		String SubmitResult;
		method.setRequestBody(data);		 
		
		try {
			client.executeMethod(method);	
			
			//SubmitResult =method.getResponseBodyAsString();  
			BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));  
			StringBuffer stringBuffer = new StringBuffer();  
			String str = "";  
			while((str = reader.readLine())!=null){  
			    stringBuffer.append(str);  
			}  
			SubmitResult = stringBuffer.toString();
			Logger log = Logger.getLogger("http");
			log.info("http");
			log.info(SubmitResult);
			
			//System.out.println("111"+SubmitResult);
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "0";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "0";
		}finally{
			method.releaseConnection();  
		} 
		 return SubmitResult;
		
	}
	   public static void  showObj(Object o) {
		   JSONArray jsonArray = JSONArray.fromObject( o );  
	       System.out.println( jsonArray );  
	}
}
