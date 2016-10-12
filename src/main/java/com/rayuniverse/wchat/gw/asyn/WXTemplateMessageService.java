package com.rayuniverse.wchat.gw.asyn;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayuniverse.domain.TaskData;
import com.rayuniverse.framework.comm.XmlMapUtil;
import com.rayuniverse.wchat.gw.cfg.ConfigItem;


@Service
public class WXTemplateMessageService {
	
	private static final Logger logger = LoggerFactory.getLogger(WXTemplateMessageService.class);
	
	@Autowired
	AsyncTaskDao taskRepository;
	
	@Autowired
	protected WXConfigService cfg;
	
	public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	
	public void pushTemplateMessage(HashMap msg) throws Throwable
	{
		
		String touser=(String)msg.get("touser");
		msg.put("touser", touser);

	    TaskData taskData=new TaskData();
		taskData.setContext(XmlMapUtil.toBytes(msg));
		taskData.setInvokeId(UUID.randomUUID().toString());
		
		taskData.setSync((String)msg.get("touser"));
		
		if(taskData.getSync()!=null)
		{
			int grp=Math.abs(taskData.getSync().hashCode())%100;
			taskData.setGrp(grp);
		}
		else
		{
			Random r=new Random();
			int grp=Math.abs(r.nextInt())%100;
			taskData.setGrp(grp);
		}
		
		taskData.setMethod("pushTemplateMessage");
		taskData.setProcessorId("WXTemplateMessage");
	    
		
		pushTemplateMessage(taskData);
	    //taskRepository.addNewTask(taskData);
	}
	
	public void pushTemplateMessage(TaskData taskData) throws Throwable
	{		
		
		HashMap map=cfg.getAccessToken();
		ConfigItem item=new ConfigItem();
		item.init(map);
		if(item.isValidate()==false)
		{
			 map=cfg.getAccessToken();
		}
		String token=item.getValue();
		System.out.println("token info +++++++++++++++++++++++++++++++++++++++++"+token);
		
		String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;
		
		HashMap msgMap=(HashMap)XmlMapUtil.fromBytes(taskData.getContext());
		String json = JSONObject.fromObject(msgMap).toString();
		System.out.println("token info +++++++++++++++++++++++++++++++++++++++++"+token);
		
		DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json; charset=utf-8");
        try
        {
            StringEntity s = new StringEntity(json,"UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
 
            HttpResponse res = client.execute(post);
            
            System.out.println(res);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                HttpEntity entity = res.getEntity();
                System.out.println(EntityUtils.toString(entity, "utf-8"));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
		
		
		
//		String rs = null;
//        BufferedReader reader = null;
//	   
//		
//		
//		
//		//ByteArrayInputStream in=new ByteArrayInputStream(json.getBytes("utf-8"));
//		//String s=PlatformContext.getRpcContext().getHttpService().upload(url, in);
//		
//		logger.info("json info +++++++++++++++++++++++++++++++++++++++++"+json);
//		
//		HttpURLConnection urlConn = null;
//		
//		URL connUrl = new URL(url);
//		urlConn = (HttpURLConnection) connUrl.openConnection();
//		
//		
//        	urlConn.setRequestMethod("POST");
//        	urlConn.setDoOutput(true);
//        	
//		urlConn.setRequestProperty("User-agent", userAgent);
//		urlConn.setUseCaches(false);
//		urlConn.setConnectTimeout(DEF_CONN_TIMEOUT);
//		urlConn.setReadTimeout(DEF_READ_TIMEOUT);
//		urlConn.setInstanceFollowRedirects(false);
//		urlConn.connect();
//		
//		DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
//        out.writeBytes(json);
//        
//        StringBuffer sb = new StringBuffer();
//        
//        InputStream is = urlConn.getInputStream();
//        reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
//        String strRead = null;
//        while ((strRead = reader.readLine()) != null) {
//            sb.append(strRead);
//        }
//        rs = sb.toString();
//        
//        logger.info("result info +++++++++++++++++++++++++++++++++++++++++"+rs);
//        
//        is.close();
//        reader.close();
//        out.close();
//        //in.close();
//        urlConn.disconnect();
		
//		String s=PlatformContext.getRpcContext().getHttpService().upload(url, in);
//		
//		JSONObject	jsonObject = JSONObject.fromObject(s);
//		
//		if((jsonObject.get("errcode")+"").equals("0")){
//			
//			 taskRepository.deleteTask(invokeId);
//			
//		}else{
//			throw new RuntimeException("error   "+jsonObject.toString());
//		}

	    
	}
	
	//将map型转为请求参数型
    public static String urlencode(Map<String, ?> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ?> i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
