package com.rayuniverse.framework.comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.base64.Base64;
import com.rayuniverse.framework.des.DESCode;
import com.rayuniverse.wchat.gw.util.MyX509TrustManager;

public class Util {
	
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			throw new RuntimeException(ce);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return jsonObject;
	}
	
	static KeyFactory rsaKeyFactory ;
	static {
		try{
			rsaKeyFactory = KeyFactory.getInstance("RSA");
		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}
	public static String getFileMD5(File file)
	{
		return MD5.getMD5(readAsSByte(file));
	}
	
	public static String replaceAll(String s1 ,String s2 ,String s3)
	{
		if(s1==null)
		{
			return s1;
		}
		return s1.replaceAll(s2, s3);
	}
	public static  long  calcCharSum(String s)
	{
	   long l=0;
	   for(int i=0;i<s.length();i++)
	   {
		   l+=s.charAt(i);
	   }
	   return l;
	}
	public static boolean isTomcatContaner()
	{
		if(PlatformContext.getServletContext().getClass().toString().indexOf("catalina")!=-1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static String getUUID()
	{
	    return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}
 
	
	 public static String toMD5(String str)
	 {
    	 try{
    		 MessageDigest md = MessageDigest.getInstance("MD5");
    		 md.update(str.getBytes());
    		 byte[] inputData = md.digest();;   
        	 return DESCode.encryptBASE64(inputData);
    	 }catch(Exception e)
    	 {
    		 throw new RuntimeException("toMD5 Exception",e);
    	 }
	 }
	 public static String toMD5(byte[] data)
	 {
    	 try{
    		 MessageDigest md = MessageDigest.getInstance("MD5");
    		 md.update(data);
    		 byte[] inputData = md.digest();;   
        	 return DESCode.encryptBASE64(inputData);
    	 }catch(Exception e)
    	 {
    		 throw new RuntimeException("toMD5 Exception",e);
    	 }
	 }
	 
	

	public static Object fromBytes(byte[] data) {
		 
		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		ObjectInputStream oin;
		try {
			oin = new ObjectInputStream(bin);
			return oin.readObject();
		} catch (Exception e) {
			throw new RuntimeException("Serialize exception", e);
		}
	}
	public static  byte[] toBytes(Object object) {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(object);
			return bout.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("Serialize exception", e);
		}
	}
	public static String print(Throwable a) {
		if (a == null)
			return null;

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		a.printStackTrace(new PrintStream(bout));
		return new String(bout.toByteArray());
	}
	public static byte[] readAsSByte(File file)
	{ 
		FileInputStream fin=null;
		try {
			ByteArrayOutputStream bout=new ByteArrayOutputStream();
		    fin=new FileInputStream(file);
			copyStream(fin,bout);
			bout.close();
			return bout.toByteArray();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}finally
		{
			if(fin!=null)
			{
				try {
					fin.close();
				} catch (Throwable  e) {
					 
					e.printStackTrace();
				}
			}
		}
	}
	public static String readAsString(File file,String encode)
	{
		if(encode==null)
		{
			encode="utf-8";
		}
		try {
			ByteArrayOutputStream bout=new ByteArrayOutputStream();
			FileInputStream fin=new FileInputStream(file);
			copyStream(fin,bout);
			fin.close();
			bout.close();
			return new String(bout.toByteArray(),encode);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	public static String readAsString(InputStream fin,String encode)
	{
		if(encode==null)
		{
			encode="utf-8";
		}
		try {
			ByteArrayOutputStream bout=new ByteArrayOutputStream();
			copyStream(fin,bout);
			bout.close();
			return new String(bout.toByteArray(),encode);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static Object serCopy(Object object)
	{
		if(object==null)
			return null;
		 byte[] data=Util.toBytes(object);
		 return Util.fromBytes(data);
		
	}
	public static void copyFile(File source,File destination)
	{
		FileInputStream fin=null;
		FileOutputStream fout=null;
		try{
			  fin=new FileInputStream(source);
			  fout=new FileOutputStream(destination);
			  copyStream(fin,fout) ;
		}catch(Throwable e)
		{
			throw new RuntimeException(e);
		}finally
		{
			if(fin!=null)
			{
				try {
					fin.close();
				} catch (IOException e) {
					 
				}
			}
			if(fout!=null)
			{
				try {
					fout.close();
				} catch (IOException e) {
					 
				}
			}
		}
		
	}
	public static void gzipCopyFile(File source,File destination)
	{
		byte[] sourceByte= readAsSByte(source);
		byte[] gzipByte=gzip(sourceByte);
		writeFile(gzipByte,destination);
	}
	
	public static void gzipWriteFile(byte[] sourceByte,File destination)
	{
		byte[] gzipByte=gzip(sourceByte);
		writeFile(gzipByte,destination);
	}
	
	public static void zipDir2File(File dir,File file)
	{
		BufferedOutputStream bo = null;  
		ZipOutputStream   zout=null;
		try{
			 zout=new ZipOutputStream(new FileOutputStream(file)); 
			 bo = new BufferedOutputStream(zout);  
			 zip(zout, dir, dir.getName(), bo); 
		}catch(Throwable e)
		{
			throw new RuntimeException(e);
		}finally
		{
			if(bo!=null)
			{
				try {
					bo.close();
				} catch (IOException e) {
					 
				}
			}
		}
		
		
	}
	
	private static  void zip(ZipOutputStream out, File f, String base,  
            BufferedOutputStream bo) throws Exception { // 鏂规硶閲嶈浇  
        if (f.isDirectory()) {  
            File[] fl = f.listFiles();  
            if (fl.length == 0) {  
                out.putNextEntry(new ZipEntry(base + "/")); // 鍒涘缓zip鍘嬬缉杩涘叆鐐筨ase  
            }  
            for (int i = 0; i < fl.length; i++) {  
                zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 閫掑綊閬嶅巻瀛愭枃浠跺す  
            }  
           
        } else {  
            out.putNextEntry(new ZipEntry(base)); // 鍒涘缓zip鍘嬬缉杩涘叆鐐筨ase  
            BufferedInputStream bi = new BufferedInputStream(new FileInputStream(f));  
            try
            {
            	int b;  
                while ((b = bi.read()) != -1) {  
                bo.write(b); // 灏嗗瓧鑺傛祦鍐欏叆褰撳墠zip鐩綍  
                }  
            	
                bo.flush();
            }finally
            {
            	  bi.close();  
            }
        }  
    }  

	public static void writeFile(byte[] data,File f)
	{
		FileOutputStream fout=null;
		try{
			 
			  fout=new FileOutputStream(f);
			  fout.write(data);
		}catch(Throwable e)
		{
			throw new RuntimeException(e);
		}finally
		{
			if(fout!=null)
			{
				try {
					fout.close();
				} catch (IOException e) {
					 
				}
			}
		}
		
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
	
	public static byte[] gzip(byte[] data) {
		try {
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			GZIPOutputStream gzout = new GZIPOutputStream(o);
			gzout.write(data);
			gzout.close();
			return o.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] unGzip(byte[] data) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			GZIPInputStream gzin = new GZIPInputStream(in);
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			int b = gzin.read();
			while (b != -1) {
				o.write(b);
				b = gzin.read();
			}
			gzin.close();
			o.close();
			return o.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64.decode(key.getBytes());
	}

	public static String encryptBASE64(byte[] key) throws Exception {

		return new String(Base64.encode(key));
	}

	

	public static String sign(byte[] data, PrivateKey priKey) {
		try {
			// 鐢ㄧ閽ュ淇℃伅鐢熸垚鏁板瓧绛惧悕
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(priKey);
			signature.update(data);
			return byte2hex(signature.sign());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}


	
	public static List<String>  getLocalIp()
	{
		List<String> localIps=new ArrayList();
		Enumeration<NetworkInterface> netInterfaces = null;    
		try {    
		    netInterfaces = NetworkInterface.getNetworkInterfaces();    
		    while (netInterfaces.hasMoreElements()) {    
		        NetworkInterface ni = netInterfaces.nextElement();    
		       
		        Enumeration<InetAddress> ips = ni.getInetAddresses();    
		        while (ips.hasMoreElements()) { 
		        	String ip=ips.nextElement().getHostAddress();
		        	if(ip.equalsIgnoreCase("localhost"))
		        	{
		        		continue ;
		        	}
		        	if(ip.equals("127.0.0.1"))
		        	{
		        		continue ;
		        	}
		        	if(ip.startsWith("0"))
		        	{
		        		continue ;
		        	}
		        	if(ip.startsWith("f"))
		        	{
		        		continue ;
		        	}
		        	if(ip.indexOf(":")!=-1)
		        	{
		        		continue ;
		        	}
		        	
		        	localIps.add(ip);
		        }    
		    }    
		} catch (Exception e) {    
		     throw new RuntimeException(e);
		}
		return localIps;
	}

	public static Key getDESKey(String key) {
		try {
			DESKeySpec dks = new DESKeySpec(decryptBASE64(key));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);
			return secretKey;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	public static byte[] decryptDES_ECB_PKCS5Padding(Key DesKey, byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, DesKey);
		return cipher.doFinal(data);
	}
	
	public static byte[] encryptDES_ECB_PKCS5Padding(Key DesKey, byte[] data) {
		try {
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, DesKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] encryptDES(Key DesKey, byte[] data) {
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, DesKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] decryptDES(Key DesKey, byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, DesKey);
		return cipher.doFinal(data);
	}
	
	public static boolean verify(byte[] data, PublicKey pubKey, String sign) {
		try {
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(pubKey);
			signature.update(data);
			// 楠岃瘉绛惧悕鏄惁姝ｅ父
			return signature.verify(hex2byte(sign));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	 }
	 public static String getRequestFullURL(HttpServletRequest request)
	 {
		    StringBuffer reqPath=new StringBuffer();
			reqPath.append("");
			
			String servletPath=request.getServletPath();
			if(servletPath!=null&&servletPath.trim().length()>0)
			{
				reqPath.append(servletPath);
			}
			String pathInfo=request.getPathInfo();
			if(pathInfo!=null&&pathInfo.trim().length()>0)
			{
				reqPath.append(pathInfo);
			}
			
			
			
			String queryString=request.getQueryString();
			if(queryString!=null&&queryString.trim().length()>0)
			{
				reqPath.append("?"+queryString);
			}
			return reqPath.toString();	
	  }
	 
	 public static String getRequestURLWithoutQueryStr(HttpServletRequest request)
	 {
		    StringBuffer reqPath=new StringBuffer();
			reqPath.append("");
			
			String servletPath=request.getServletPath();
			if(servletPath!=null&&servletPath.trim().length()>0)
			{
				reqPath.append(servletPath);
			}
			String pathInfo=request.getPathInfo();
			if(pathInfo!=null&&pathInfo.trim().length()>0)
			{
				reqPath.append(pathInfo);
			}
			return reqPath.toString();	
	  }
	 
	 public static String getClientIp(HttpServletRequest request)
	 {
		 String clientIp=request.getHeader("X-Forwarded-For");
		 if(clientIp==null)
		 {
			 clientIp=request.getRemoteAddr();
		 }
		 return clientIp;
	 }
	 public static String getServerIp(HttpServletRequest request)
	 {
	     String serverIp=request.getLocalAddr()+":"+request.getServerPort();
	     return serverIp;
	    
	 }
	 
	 
	 public static String getWebServerDomainWithContextPath(HttpServletRequest request)
	 {
		   
			if (request.getServerPort() == 80) {
				return "http://" + request.getServerName()+ request.getContextPath();
			} else {
				return "http://" + request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath();
			}
		
		 
	 }
 
	 
	 public static String getWebServerDomainWithContextPath2(HttpServletRequest request)
	 {
		    String webServerDomainWithContextPath=PlatformContext.getConfigItem("WebServerDomainWithContextPath");

		    if(webServerDomainWithContextPath!=null)
		    {
		    	return webServerDomainWithContextPath;
		    }
		    
			if (request.getServerPort() == 80) {
				return "http://" + request.getServerName()+ request.getContextPath();
			} else {
				return "http://" + request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath();
			}
		
		 
	 }
	 
	 public static HashMap<String,String> decodeMapFromString(String paramters)
		{
			HashMap<String,String> map=new HashMap<String,String>();
			if(paramters!=null)
			{
				String[] segs=paramters.split("@");
				for(String seg:segs)
				{
					if(seg!=null)
					{
						if(seg.indexOf(":")!=-1)
						{
							String[]fv=seg.split(":");
							if(fv.length==2)
							{
								map.put(new String(hex2byte(fv[0])), new String(hex2byte(fv[1])));
							}
							else
							{
								map.put(new String(hex2byte(fv[0])), null);
							}
							
						}
					}
					
				}
			}
			
			return map;
		}
		
		public static String encodeMapToString(HashMap<String,String> paramters )
		{
			StringBuffer buf=new StringBuffer();
			for(String k:paramters.keySet())
			{
				if(k==null)
					continue;
				if(paramters.get(k)==null)
					continue;
				
				if(buf.length()>0)
					buf.append("@");
				
				buf.append(byte2hex(k.getBytes())+":"+byte2hex(paramters.get(k).getBytes()));
			}
			return buf.toString();
		}
		
		public static String byte2hex(byte[] data) {
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < data.length; i++) {
	            String temp = Integer.toHexString(((int) data[i]) & 0xFF);
	            for(int t = temp.length();t<2;t++)
	            {
	                sb.append("0");
	            }
	            sb.append(temp);
	        }
	        return sb.toString();
	    }
		 
		
		  public static byte[] hex2byte(String hexStr){
	          byte[] bts = new byte[hexStr.length() / 2];
	          for (int i = 0,j=0; j < bts.length;j++ ) {
	             bts[j] = (byte) Integer.parseInt(hexStr.substring(i, i+2), 16);
	             i+=2;
	          }
	          return bts;
	      }
		  public static void deleteDirSubFiles(File file)
		  {
			  if(file.exists()==false)
			  {
				  File[] files=file.listFiles();
				  for(File f:files)
				  {
					  deleteFile(f);
				  }
			  }
		  }
		  
			public static void deleteFile(File file)
			{
				if(file.exists()==false)
				{
					return ;
				}
				if(file.isDirectory())
				{
					File[] list=file.listFiles();
					if(list!=null)
					{
						for(File f:list)
						{
							deleteFile(f);
						}
					}
				}
				file.delete();
			}

}
