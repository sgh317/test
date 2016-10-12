package com.rayuniverse.framework.recharge;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;



public class Recharge {

	private static CloseableHttpClient httpClient;
	
	private static void initDefaultHttpClient()
	{

        final PoolingHttpClientConnectionManager cm  = new PoolingHttpClientConnectionManager();
	    // 将最大连接数增加到200
	    cm.setMaxTotal(600);
	    // 将每个路由基础的连接增加到250
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
	static {
		initDefaultHttpClient();
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
	
	/**
	 * 本地环境地址
	 */
	public static final String HF_ADDR="http://op.juhe.cn/ofpay/mobile/onlineorder";
	
	public static final String LL_ADDR="http://v.juhe.cn/flow/recharge";
	
	 
	
	public static final String CHARSET="UTF-8";
//	public static final String OPENID="JHb04f378f9d36dd82594287a163c7f9c4";
//	public static final String LL_KEY="b8ece895b896a5eb1df33ddcbc4d4dd5";
//	public static final String HF_KEY="812eb9c22d83b05a942090d4395dde51";
//	
	public static final String OPENID="JHd92370f70a44b468cd6f96328809b700";
	public static final String LL_KEY="071b3e8952362e4205e88db8d1591606";
	public static final String HF_KEY="528f42bb09ced3608e0f7917bf5c6fd8";

	
	public static void shf(String cardnum,String PHONE_NUM){
		try {
			String url=huafeiUrlStr(  cardnum,  PHONE_NUM);
			HttpPost post = new HttpPost(url);
			
			
			String s= post2(post);
			System.out.println("============");
			System.out.println(s);
			System.out.println("============");
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
//	public static void main(String[] args) throws Throwable {
// 
//		initDefaultHttpClient();
//		
//		String url = liuliangUrlStr();
//		HttpPost post = new HttpPost(url);
//		
//		
//		String s= post2(post);
//		
//		System.out.println("\r\n");
//		System.out.println("\r\n");
//		System.out.println("\r\n");
//		System.out.println("\r\n");
//		System.out.println("begin--------------------------------------------------------\r\n");
//		System.out.println("\r\n");
//		System.out.println(s);
//		System.out.println("\r\n");
//		System.out.println("\r\n");
//		System.out.println("end--------------------------------------------------------\r\n");
//		System.out.println("\r\n");
//		System.out.println("\r\n");
//		System.out.println("\r\n");
//		System.out.println("\r\n");
//		
//		
//	}
//	
	
	
	public static String liuliangUrlStr(String PHONE_NUM) throws IOException
	{
		
		String orderid = getUUID();
		String url= LL_ADDR+"?key="+LL_KEY+"&phone="+PHONE_NUM+"&pid=1&orderid="+orderid+"&sign=";
		return url=url+getMD5((OPENID+LL_KEY+PHONE_NUM+"1"+orderid).getBytes(CHARSET));
	}
	public static String huafeiUrlStr(String cardnum,String PHONE_NUM) throws IOException
	{
		
		String orderid = getUUID();
		String url= HF_ADDR+"?key="+HF_KEY+"&phoneno="+PHONE_NUM+"&cardnum="+cardnum+"&orderid="+orderid+"&sign=";
		return url=url+getMD5((OPENID+HF_KEY+PHONE_NUM+cardnum+orderid).getBytes(CHARSET));
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
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 
                'e', 'f' }; 
        try { 
            java.security.MessageDigest md = java.security.MessageDigest 
                    .getInstance("MD5"); 
            md.update(source); 
            byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数， 
            // 用字节表示就是 16 个字节 
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符， 
            // 所以表示成 16 进制需要 32 个字符 
            int k = 0; // 表示转换结果中对应的字符位置 
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节 
                // 转换成 16 进制字符的转换 
                byte byte0 = tmp[i]; // 取第 i 个字节 
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, 
                // >>> 为逻辑右移，将符号位一起右移 
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换 
            } 
            s = new String(str); // 换后的结果转换为字符串 
  
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
