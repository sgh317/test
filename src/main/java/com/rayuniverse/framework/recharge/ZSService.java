package com.rayuniverse.framework.recharge;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayuniverse.customer.dao.CustomerDao;
import com.rayuniverse.domain.CustomerDetailDomain;
import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.util.JSDDConfig;
import com.rayuniverse.wchat.gw.asyn.WXTemplateMessageService;

@Service
public class ZSService {
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	WXTemplateMessageService wXTemplateMessageService;
	
	private static final Logger logger = LoggerFactory.getLogger(ZSService.class);
	
	public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	
	//赠送话费
	public Map<String,Object> zsHFService() throws UnsupportedEncodingException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String openId = PlatformContext.getUmUserId();
		
		CustomerDetailDomain customerInfo = customerDao.queryClientInfoByOpenId(openId);
		
		if(customerInfo==null){
			resultMap.put("flag", "N");
			resultMap.put("message", "您还未完成绑定");
			return resultMap;
		}
		
		if(customerInfo.getGivenTime()!=null||customerInfo.getGivenType()!=null){
			resultMap.put("flag", "N");
			resultMap.put("message", "您已领取过奖励");
			return resultMap;
		}
		
		String mobileNo = customerInfo.getMobileNo();
		
		String result =null;
        String url ="http://v.juhe.cn/huafei/telcheck";//请求接口地址
        Map<String, Object> params = new HashMap<String, Object>();//请求参数
            params.put("phone",mobileNo);//手机号码
            params.put("pervalue",JSDDConfig.TEL_JH_AMOUNT);//充值面额，可选：10,20,30,50,100,200,300,500
            params.put("key",JSDDConfig.TEL_JH_APPID);//应用APPKEY(应用详细页查询)
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
            	System.out.println("本号码可以充值"+JSDDConfig.TEL_JH_AMOUNT+"元");
            	
            	String orderid = getUUID();
        		String sign = getMD5((JSDDConfig.JH_OPEN_ID+JSDDConfig.TEL_JH_APPID+mobileNo+JSDDConfig.TEL_JH_AMOUNT+orderid).getBytes("UTF-8"));
        		
        		result =null;
        		params = new HashMap<String, Object>();//请求参数
        		params.put("phone",mobileNo);//手机号码
        		params.put("pervalue",JSDDConfig.TEL_JH_AMOUNT);//充值面额，可选:10,20,30,50,100,200,300,500
        		params.put("orderid",orderid);//自定义订单号，8-32位字母数字组合
        		params.put("key",JSDDConfig.TEL_JH_APPID);//应用APPKEY(应用详细页查询)
        		params.put("sign",sign);//校验值，md5(&lt;b&gt;OpenID&lt;/b&gt;+key+phone+pervalue+orderid)，OpenID在个人中心查询，结果转为32小写
            	url = "http://v.juhe.cn/huafei/recharge";
        		try {
        			result =net(url, params, "GET");
        			object = JSONObject.fromObject(result);
        			if(object.getInt("error_code")==0){
        				
        				//修改领取赠送时间
                    	customerDao.updateGivenDate(openId,"tel");
                    	
                    	//推送消息
                    	givenPushToUser(customerInfo,"tel",JSDDConfig.TEL_JH_AMOUNT);
        				
        				resultMap.put("flag", "Y");
        				resultMap.put("message", "为您的号码"+mobileNo+"充值"+JSDDConfig.TEL_JH_AMOUNT+"成功");
        			}else{
        					resultMap.put("flag", "N");
        					resultMap.put("message", object.get("reason"));
        			}
        			}catch (Exception e){
        				logger.error("调用充值接口失败", e);
        				resultMap.put("flag", "N");
    					resultMap.put("message", object.get("reason"));
        			}
            }else{
            	resultMap.put("flag", "N");
				resultMap.put("message", object.get("reason"));
            }
        } catch (Exception e) {
        	logger.error("调用充值接口失败", e);
			resultMap.put("flag", "N");
			resultMap.put("message", "调用接口查询异常");
        }
		
		return resultMap;
	}
	
	//赠送流量
	public Map<String,Object> zsLLService() throws UnsupportedEncodingException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String openId = PlatformContext.getUmUserId();
		
		CustomerDetailDomain customerInfo = customerDao.queryClientInfoByOpenId(openId);
		
		if(customerInfo==null){
			resultMap.put("flag", "N");
			resultMap.put("message", "您还未完成绑定");
			return resultMap;
		}
		
		if(customerInfo.getGivenTime()!=null||customerInfo.getGivenType()!=null){
			resultMap.put("flag", "N");
			resultMap.put("message", "您已领取过奖励");
			return resultMap;
		}
		
		String mobileNo = customerInfo.getMobileNo();
		
		if(StringUtils.isBlank(mobileNo)){
			
			resultMap.put("flag", "N");
			resultMap.put("message", "未取得电话号码");
			return resultMap;
		}
		
		int beginMobile = Integer.parseInt(mobileNo.substring(0,3));
		//判断运营商
		String mobileLL = null;
		
		switch(beginMobile){
		case 130:
		case 131:
		case 132:
		case 155:
		case 156:
		case 185:
		case 186:{
				mobileLL = JSDDConfig.LL_JH_AMONUT_LT;
				break;
				}
		        
		case 133:
		case 153:
		case 180:
		case 189:{
				mobileLL = JSDDConfig.LL_JH_AMONUT_DX;
				break;
				}
		case 134:
		case 135:
		case 136:
		case 137:
		case 138:
		case 139:
		case 150:
		case 151:
		case 157:
		case 158:
		case 159:
		case 147:
		case 170:
		case 187:
		case 188:{
				mobileLL = JSDDConfig.LL_JH_AMONUT_YD;
				break;
				}
		default:{
			resultMap.put("flag", "N");
			resultMap.put("message", "暂不支持为您的手机冲入流量");
			return resultMap;
			}
		}
		
			String result =null;
	        String url ="http://v.juhe.cn/flow/list";//请求接口地址
	        Map<String, Object> params = new HashMap<String, Object>();//请求参数
	        params.put("key",JSDDConfig.LL_JH_APPID);//应用APPKEY(应用详细页查询)
	 
	        try {
	            result =net(url, params, "GET");
	            JSONObject object = JSONObject.fromObject(result);
	            if(object.getInt("error_code")==0){
	            	
	            	String orderid = getUUID();
	        		String sign = getMD5((JSDDConfig.JH_OPEN_ID+JSDDConfig.LL_JH_APPID+mobileNo+mobileLL+orderid).getBytes("UTF-8"));
	        		
	        		result =null;
	                url ="http://v.juhe.cn/flow/recharge";//请求接口地址
	                params = new HashMap<String, Object>();//请求参数
	                params.put("phone",mobileNo);//需要充值流量的手机号码
	                params.put("pid",mobileLL);//流量套餐ID
	                params.put("orderid",orderid);//自定义订单号，8-32字母数字组合
	                params.put("key",JSDDConfig.LL_JH_APPID);//应用APPKEY(应用详细页查询)
	                params.put("sign",sign);//校验值，md5(&lt;b&gt;OpenID&lt;/b&gt;+key+phone+pid+orderid)，结果转为小写
	         
	                try {
	                    result =net(url, params, "GET");
	                    object = JSONObject.fromObject(result);
	                    if(object.getInt("error_code")==0){
	                    	
	                    	//修改领取赠送时间
	                    	customerDao.updateGivenDate(openId,"tel");
	                    	
	                    	//推送消息
	                    	givenPushToUser(customerInfo,"ll",mobileLL);
	                    	
	                    	resultMap.put("flag", "Y");
	        				resultMap.put("message", "为您的号码"+mobileNo+"充值流量"+mobileLL+"号套餐成功");
	                    }else{
	                    	resultMap.put("flag", "N");
	        				resultMap.put("message", object.get("reason"));
	                    }
	                } catch (Exception e) {
	                	logger.error("调用流量充值接口失败", e);
	        			resultMap.put("flag", "N");
	        			resultMap.put("message", "调用流量充值接口失败");
	                }
	            	
	            }else{
	            	resultMap.put("flag", "N");
        			resultMap.put("message", "查询流量套餐接口失败");
	            }
	        } catch (Exception e) {
	        	logger.error("调用流量充值接口失败", e);
	        	resultMap.put("flag", "N");
    			resultMap.put("message", "查询流量套餐接口失败");
	        }
		
		
		
		return resultMap;
	}
	
	/**
	 * 推送消息给客户，建议报价
	 * @param openId
	 * @param string
	 * @param telJhAmount
	 */
	private void givenPushToUser(CustomerDetailDomain customerInfo, String zsType,
			String telJhAmount) {
		
		
		String messageNotice = "";
		String keymessage1 = "";
		
		try {
		//点对点推送消息
		HashMap msg = new HashMap();
		// 消息模板
		String templateId = JSDDConfig.HFLL_SUCCESS_MODE;
		// 点击后跳到哪里

		// https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxee929a1a1cf2bbcc&redirect_uri=http%3a%2f%2fes.lianlife.com%2fP%2fYC%2fJSDD%2fPUBLIC%2fWXRedirectServlet&response_type=code&scope=snsapi_base&state=zs#wechat_redirect
		String toURL = JSDDConfig.BUY_URL;

		msg.put("touser", PlatformContext.getUmUserId());
		msg.put("template_id", templateId);
		msg.put("url", toURL);

		HashMap data = new HashMap();
		
		if("tel".equals(zsType)){
			messageNotice = "恭喜您领取话费赠送成功";
			keymessage1 = JSDDConfig.TEL_JH_AMOUNT+"元话费直充";
		}else{
			messageNotice = "恭喜您领取话费流量成功";
			keymessage1 = JSDDConfig.TEL_JH_AMOUNT+"元等值流量直充";
		}

		HashMap first = new HashMap();
		first.put("value", messageNotice);
		first.put("color", "#173177");
		data.put("first", first);

		HashMap keyword1 = new HashMap();
		keyword1.put("value", keymessage1);
		data.put("keyword1", keyword1);

		HashMap keyword2 = new HashMap();
		keyword2.put("value", customerInfo.getClientName());
		data.put("keyword2", keyword2);

		HashMap remark = new HashMap();
		remark.put("value", "立即报价成功，还可以领取一份免费的航意险，赶紧行动吧");
		data.put("remark", remark);

		msg.put("data", data);
        
		wXTemplateMessageService.pushTemplateMessage(msg);
		} catch (Throwable e) {
			
			//異常信息記錄
			e.printStackTrace();
		}
		
	}

	public String getMD5(byte[] source) { 
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
	    
    public String getUUID(){
		UUID uuid = UUID.randomUUID();
		String uStr = uuid.toString();
		String[] uArray = uStr.split("[-]");
		uStr = "";
        for(int i=0;i<uArray.length;i++){
        	uStr += uArray[i];
        }
		return uStr;
	}
    
    public String net(String strUrl, Map<String, Object> params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(urlencode(params));
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
    
    //将map型转为请求参数型
    public String urlencode(Map<String, ?> data) {
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
