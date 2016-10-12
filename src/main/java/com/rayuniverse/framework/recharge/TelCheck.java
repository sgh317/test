package com.rayuniverse.framework.recharge;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
/**
 * ��ѯ�绰������Ӫ��
 * @author Administrator
 *
 */
public class TelCheck {
	
private static CloseableHttpClient httpClient;
	
	private static void initDefaultHttpClient()
	{

        final PoolingHttpClientConnectionManager cm  = new PoolingHttpClientConnectionManager();
	    // ��������������ӵ�200
	    cm.setMaxTotal(600);
	    // ��ÿ��·�ɻ������������ӵ�250
	    cm.setDefaultMaxPerRoute(250);
	   
	    httpClient = HttpClients.custom()
	            .setConnectionManager(cm)
	            .build();
	    
	    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

		 
			public void run() {
				 
				try {
					httpClient.close();
				} catch (IOException e) {
				   e.printStackTrace();
				}
				try {
					cm.close();
				} catch (Throwable e) {
				   e.printStackTrace();
				}
			}
	    	
	    }));


	}
	
	public static  String postTransationPacket(String  xml,String defaultLianlifeURL)throws Throwable 
	{
	    ByteArrayEntity reqEntity= toByteArrayEntity(xml);
		
		HttpPost post = new HttpPost(defaultLianlifeURL);
		
		post.setEntity(reqEntity);
		
		return post2(post);
	}
	
	private static ByteArrayEntity toByteArrayEntity(String requestXml) throws UnsupportedEncodingException
	{
		byte[] requestPlainData=requestXml.getBytes("UTF-8");
		ByteArrayEntity reqEntity = new ByteArrayEntity(requestPlainData);
		return reqEntity;
	 
	}
	private  static String post2(HttpPost post) throws Throwable
	{
		CloseableHttpResponse  response = httpClient.execute(post);
		InputStream returnInputStream=null;
		
		try{
	    	int statusCode = response.getStatusLine().getStatusCode();
	    	
		    if(statusCode!=200)
		    {
		    	throw new RuntimeException("RESPONSE HTTP STATUS ERROR. HTTP STATUS : "+statusCode);
		    }
		    
		    HttpEntity entity = response.getEntity();
		    
		    returnInputStream=entity.getContent();
		    
		    ByteArrayOutputStream bout=new ByteArrayOutputStream();
		    
		    copyStream(returnInputStream, bout);
		    
		    String responseXml=new String(bout.toByteArray(),"UTF-8");
		    
		    return responseXml;
		   
	  }finally
	  {
		  try{
			   if(returnInputStream!=null)
		    	{
		    		returnInputStream.close();
		    	}
			  
		  }catch(Throwable e)
		  {
			  e.printStackTrace();
		  }
		  
		  try{
			    if(response!=null)
		    	{
		    		response.close();
		    	}
			  
		  }catch(Throwable e)
		  {
			  e.printStackTrace();
		  }

	  }
	}
	
	public static final String TEL_QUERY_ADDR="http://v.juhe.cn/flow/telcheck";
	public static final String PHONE_NUM="18501634456";
	
	public static final String LL_KEY="071b3e8952362e4205e88db8d1591606";
	
	
	public static void main(String[] args) throws Throwable {
 
		initDefaultHttpClient();
		
		String url = telQueryUrlStr(PHONE_NUM);
		HttpPost post = new HttpPost(url);
		
		
		String s= post2(post);
		
		JSONObject object = JSONObject.fromObject(s);//ת��ΪJSON��
		String code = object.getString("error_code");//�õ�������
		//�������ж�
		if(code.equals("0")){
			//������Ҫȡ������
			if("null".equals(object.getString("result"))) {
				System.out.println("������Ӫ��");
			} else {
				JSONObject jsonObject =  (JSONObject)object.getJSONArray("result").get(0);
				String companytype = jsonObject.getString("companytype");
				if("1".equals(companytype)){
					System.out.println("��ͨ");
				} else if("2".equals(companytype)) {
					System.out.println("�ƶ�");
				} else {
					System.out.println("����");
				}
			}
		}else{
			System.out.println("error_code:"+code+",reason:"+object.getString("reason"));
		}
		
		System.out.println("\r\n");
		System.out.println("\r\n");
		System.out.println("\r\n");
		System.out.println("\r\n");
		System.out.println("begin--------------------------------------------------------\r\n");
		System.out.println("\r\n");
		System.out.println(s);
		System.out.println("\r\n");
		System.out.println("\r\n");
		System.out.println("end--------------------------------------------------------\r\n");
		System.out.println("\r\n");
		System.out.println("\r\n");
		System.out.println("\r\n");
		System.out.println("\r\n");
	}
	
	public static String telQueryUrlStr(String cardnum) throws IOException
	{
		
		String orderid = getUUID();
		String url= TEL_QUERY_ADDR+"?key="+LL_KEY+"&phone="+PHONE_NUM;
		return url;
	}
	
	
	
	public static void copyStream(InputStream in,OutputStream out) throws IOException
	{
		copyStream(  in,  out , 2048);
	}
	public static void copyStream(InputStream in,OutputStream out,int bufSize) throws IOException
	{
		byte[] buf=new byte[bufSize];
		int c=0;
		do
		{
			c=in.read(buf);
			if(c>0)
			{
				out.write(buf, 0, c);
			}
			
		}while(c!=-1);
		out.flush();
	}
	
    public static String getMD5(byte[] source) { 
        String s = null; 
        char hexDigits[] = { // �������ֽ�ת���� 16 ���Ʊ�ʾ���ַ� 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 
                'e', 'f' }; 
        try { 
            java.security.MessageDigest md = java.security.MessageDigest 
                    .getInstance("MD5"); 
            md.update(source); 
            byte tmp[] = md.digest(); // MD5 �ļ�������һ�� 128 λ�ĳ������� 
            // ���ֽڱ�ʾ���� 16 ���ֽ� 
            char str[] = new char[16 * 2]; // ÿ���ֽ��� 16 ���Ʊ�ʾ�Ļ���ʹ�������ַ��� 
            // ���Ա�ʾ�� 16 ������Ҫ 32 ���ַ� 
            int k = 0; // ��ʾת������ж�Ӧ���ַ�λ�� 
            for (int i = 0; i < 16; i++) { // �ӵ�һ���ֽڿ�ʼ���� MD5 ��ÿһ���ֽ� 
                // ת���� 16 �����ַ���ת�� 
                byte byte0 = tmp[i]; // ȡ�� i ���ֽ� 
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // ȡ�ֽ��и� 4 λ������ת��, 
                // >>> Ϊ�߼����ƣ�������λһ������ 
                str[k++] = hexDigits[byte0 & 0xf]; // ȡ�ֽ��е� 4 λ������ת�� 
            } 
            s = new String(str); // ����Ľ��ת��Ϊ�ַ��� 
  
        } catch (Throwable e) { 
             throw new RuntimeException(e);
        } 
        return s; 
    } 
	    
    public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		String uStr = uuid.toString();
		String[] uArray = uStr.split("[-]");
		uStr = "";
        for(int i=0;i<uArray.length;i++){
        	uStr += uArray[i];
        }
		return uStr;
	}

}
