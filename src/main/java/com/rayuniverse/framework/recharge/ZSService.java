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
	
	//���ͻ���
	public Map<String,Object> zsHFService() throws UnsupportedEncodingException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String openId = PlatformContext.getUmUserId();
		
		CustomerDetailDomain customerInfo = customerDao.queryClientInfoByOpenId(openId);
		
		if(customerInfo==null){
			resultMap.put("flag", "N");
			resultMap.put("message", "����δ��ɰ�");
			return resultMap;
		}
		
		if(customerInfo.getGivenTime()!=null||customerInfo.getGivenType()!=null){
			resultMap.put("flag", "N");
			resultMap.put("message", "������ȡ������");
			return resultMap;
		}
		
		String mobileNo = customerInfo.getMobileNo();
		
		String result =null;
        String url ="http://v.juhe.cn/huafei/telcheck";//����ӿڵ�ַ
        Map<String, Object> params = new HashMap<String, Object>();//�������
            params.put("phone",mobileNo);//�ֻ�����
            params.put("pervalue",JSDDConfig.TEL_JH_AMOUNT);//��ֵ����ѡ��10,20,30,50,100,200,300,500
            params.put("key",JSDDConfig.TEL_JH_APPID);//Ӧ��APPKEY(Ӧ����ϸҳ��ѯ)
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
            	System.out.println("��������Գ�ֵ"+JSDDConfig.TEL_JH_AMOUNT+"Ԫ");
            	
            	String orderid = getUUID();
        		String sign = getMD5((JSDDConfig.JH_OPEN_ID+JSDDConfig.TEL_JH_APPID+mobileNo+JSDDConfig.TEL_JH_AMOUNT+orderid).getBytes("UTF-8"));
        		
        		result =null;
        		params = new HashMap<String, Object>();//�������
        		params.put("phone",mobileNo);//�ֻ�����
        		params.put("pervalue",JSDDConfig.TEL_JH_AMOUNT);//��ֵ����ѡ:10,20,30,50,100,200,300,500
        		params.put("orderid",orderid);//�Զ��嶩���ţ�8-32λ��ĸ�������
        		params.put("key",JSDDConfig.TEL_JH_APPID);//Ӧ��APPKEY(Ӧ����ϸҳ��ѯ)
        		params.put("sign",sign);//У��ֵ��md5(&lt;b&gt;OpenID&lt;/b&gt;+key+phone+pervalue+orderid)��OpenID�ڸ������Ĳ�ѯ�����תΪ32Сд
            	url = "http://v.juhe.cn/huafei/recharge";
        		try {
        			result =net(url, params, "GET");
        			object = JSONObject.fromObject(result);
        			if(object.getInt("error_code")==0){
        				
        				//�޸���ȡ����ʱ��
                    	customerDao.updateGivenDate(openId,"tel");
                    	
                    	//������Ϣ
                    	givenPushToUser(customerInfo,"tel",JSDDConfig.TEL_JH_AMOUNT);
        				
        				resultMap.put("flag", "Y");
        				resultMap.put("message", "Ϊ���ĺ���"+mobileNo+"��ֵ"+JSDDConfig.TEL_JH_AMOUNT+"�ɹ�");
        			}else{
        					resultMap.put("flag", "N");
        					resultMap.put("message", object.get("reason"));
        			}
        			}catch (Exception e){
        				logger.error("���ó�ֵ�ӿ�ʧ��", e);
        				resultMap.put("flag", "N");
    					resultMap.put("message", object.get("reason"));
        			}
            }else{
            	resultMap.put("flag", "N");
				resultMap.put("message", object.get("reason"));
            }
        } catch (Exception e) {
        	logger.error("���ó�ֵ�ӿ�ʧ��", e);
			resultMap.put("flag", "N");
			resultMap.put("message", "���ýӿڲ�ѯ�쳣");
        }
		
		return resultMap;
	}
	
	//��������
	public Map<String,Object> zsLLService() throws UnsupportedEncodingException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String openId = PlatformContext.getUmUserId();
		
		CustomerDetailDomain customerInfo = customerDao.queryClientInfoByOpenId(openId);
		
		if(customerInfo==null){
			resultMap.put("flag", "N");
			resultMap.put("message", "����δ��ɰ�");
			return resultMap;
		}
		
		if(customerInfo.getGivenTime()!=null||customerInfo.getGivenType()!=null){
			resultMap.put("flag", "N");
			resultMap.put("message", "������ȡ������");
			return resultMap;
		}
		
		String mobileNo = customerInfo.getMobileNo();
		
		if(StringUtils.isBlank(mobileNo)){
			
			resultMap.put("flag", "N");
			resultMap.put("message", "δȡ�õ绰����");
			return resultMap;
		}
		
		int beginMobile = Integer.parseInt(mobileNo.substring(0,3));
		//�ж���Ӫ��
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
			resultMap.put("message", "�ݲ�֧��Ϊ�����ֻ���������");
			return resultMap;
			}
		}
		
			String result =null;
	        String url ="http://v.juhe.cn/flow/list";//����ӿڵ�ַ
	        Map<String, Object> params = new HashMap<String, Object>();//�������
	        params.put("key",JSDDConfig.LL_JH_APPID);//Ӧ��APPKEY(Ӧ����ϸҳ��ѯ)
	 
	        try {
	            result =net(url, params, "GET");
	            JSONObject object = JSONObject.fromObject(result);
	            if(object.getInt("error_code")==0){
	            	
	            	String orderid = getUUID();
	        		String sign = getMD5((JSDDConfig.JH_OPEN_ID+JSDDConfig.LL_JH_APPID+mobileNo+mobileLL+orderid).getBytes("UTF-8"));
	        		
	        		result =null;
	                url ="http://v.juhe.cn/flow/recharge";//����ӿڵ�ַ
	                params = new HashMap<String, Object>();//�������
	                params.put("phone",mobileNo);//��Ҫ��ֵ�������ֻ�����
	                params.put("pid",mobileLL);//�����ײ�ID
	                params.put("orderid",orderid);//�Զ��嶩���ţ�8-32��ĸ�������
	                params.put("key",JSDDConfig.LL_JH_APPID);//Ӧ��APPKEY(Ӧ����ϸҳ��ѯ)
	                params.put("sign",sign);//У��ֵ��md5(&lt;b&gt;OpenID&lt;/b&gt;+key+phone+pid+orderid)�����תΪСд
	         
	                try {
	                    result =net(url, params, "GET");
	                    object = JSONObject.fromObject(result);
	                    if(object.getInt("error_code")==0){
	                    	
	                    	//�޸���ȡ����ʱ��
	                    	customerDao.updateGivenDate(openId,"tel");
	                    	
	                    	//������Ϣ
	                    	givenPushToUser(customerInfo,"ll",mobileLL);
	                    	
	                    	resultMap.put("flag", "Y");
	        				resultMap.put("message", "Ϊ���ĺ���"+mobileNo+"��ֵ����"+mobileLL+"���ײͳɹ�");
	                    }else{
	                    	resultMap.put("flag", "N");
	        				resultMap.put("message", object.get("reason"));
	                    }
	                } catch (Exception e) {
	                	logger.error("����������ֵ�ӿ�ʧ��", e);
	        			resultMap.put("flag", "N");
	        			resultMap.put("message", "����������ֵ�ӿ�ʧ��");
	                }
	            	
	            }else{
	            	resultMap.put("flag", "N");
        			resultMap.put("message", "��ѯ�����ײͽӿ�ʧ��");
	            }
	        } catch (Exception e) {
	        	logger.error("����������ֵ�ӿ�ʧ��", e);
	        	resultMap.put("flag", "N");
    			resultMap.put("message", "��ѯ�����ײͽӿ�ʧ��");
	        }
		
		
		
		return resultMap;
	}
	
	/**
	 * ������Ϣ���ͻ������鱨��
	 * @param openId
	 * @param string
	 * @param telJhAmount
	 */
	private void givenPushToUser(CustomerDetailDomain customerInfo, String zsType,
			String telJhAmount) {
		
		
		String messageNotice = "";
		String keymessage1 = "";
		
		try {
		//��Ե�������Ϣ
		HashMap msg = new HashMap();
		// ��Ϣģ��
		String templateId = JSDDConfig.HFLL_SUCCESS_MODE;
		// �������������

		// https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxee929a1a1cf2bbcc&redirect_uri=http%3a%2f%2fes.lianlife.com%2fP%2fYC%2fJSDD%2fPUBLIC%2fWXRedirectServlet&response_type=code&scope=snsapi_base&state=zs#wechat_redirect
		String toURL = JSDDConfig.BUY_URL;

		msg.put("touser", PlatformContext.getUmUserId());
		msg.put("template_id", templateId);
		msg.put("url", toURL);

		HashMap data = new HashMap();
		
		if("tel".equals(zsType)){
			messageNotice = "��ϲ����ȡ�������ͳɹ�";
			keymessage1 = JSDDConfig.TEL_JH_AMOUNT+"Ԫ����ֱ��";
		}else{
			messageNotice = "��ϲ����ȡ���������ɹ�";
			keymessage1 = JSDDConfig.TEL_JH_AMOUNT+"Ԫ��ֵ����ֱ��";
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
		remark.put("value", "�������۳ɹ�����������ȡһ����ѵĺ����գ��Ͻ��ж���");
		data.put("remark", remark);

		msg.put("data", data);
        
		wXTemplateMessageService.pushTemplateMessage(msg);
		} catch (Throwable e) {
			
			//������Ϣӛ�
			e.printStackTrace();
		}
		
	}

	public String getMD5(byte[] source) { 
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
    
    //��map��תΪ���������
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
