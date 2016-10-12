package com.rayuniverse.wchat.gw.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.util.JSDDConfig;
import com.rayuniverse.wchat.gw.pay.jsb.CommonUtil;
import com.rayuniverse.wchat.gw.pay.jsb.PayCommonUtil;
import com.rayuniverse.wchat.gw.pay.jsb.XMLUtil;


public class WeixinUtil {

private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
    
    //获取access_token的接口地�?��GET�?�?00（次/天）
	public final static String jsapi_ticket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	// 获取access_token的接口地�?��GET�?�?00（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 菜单创建（POST�?�?00（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	// 菜单查询（POST�?�?000（次/天）
    public static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    
    // 菜单删除（POST�?�?00（次/天）
 	public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
 	
 	
 	public static JsApiTicket getJsApiTicket2(String token)
 	{
 		JsApiTicket accessToken = null;

//		Properties prop = new Properties();
//		InputStream in = WeixinUtil.class.getClassLoader()
//				.getResourceAsStream("cfg.properties");
//		prop.load(in);
//		
//		String downloadPath = prop.getProperty("dat.dir");
//		in.close();
		
		String requestUrl = jsapi_ticket.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new JsApiTicket();
				accessToken.setTicket(jsonObject.getString("ticket"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}
 	/**
	 * 获取access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getAccessToken2(String appid, String appsecret) {
		AccessToken accessToken = null;

//		Properties prop = new Properties();
//		InputStream in = WeixinUtil.class.getClassLoader()
//				.getResourceAsStream("cfg.properties");
//		prop.load(in);
//		
//		String downloadPath = prop.getProperty("dat.dir");
//		in.close();
		
		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}
	
	/**
	 * 发起https请求并获取结�?
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST�?
	 * @param outputStr 提交的数�?
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性�?)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始�?
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
			// 设置请求方式（GET/POST�?
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据�?��提交�?
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱�?
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符�?
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
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}
	
	/**
	 * 删除菜单
	 * 
	 * @param menu 菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他�?表示失败
	 */
	public static int deleteMenu(String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
		
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", null);

		if (null != jsonObject) {
			if (jsonObject.containsKey("errcode") && !"0".equals( jsonObject.getString("errcode"))) {
				result = -1;
				log.error("删除菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}else{
				System.out.println("删除菜单成功");
			}
		}

		return result;
	}
	
	/**
	 * 创建菜单
	 * 
	 * @param menu 菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他�?表示失败
	 */
	public static int createMenu(Map<String,Object> menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符�?
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (!"0".equals( jsonObject.getString("errcode"))) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}

		return result;
	}
	
	/**
	 * 获取订单信息
	 * @param tradeNo
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map getOrderInfo(String tradeNo) throws JDOMException, IOException{
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		System.out.println("1-getOrderInfo");
		parameters.put("appid", PlatformContext.getConfigItem("WEIXIN_APPID"));
		parameters.put("mch_id", JSDDConfig.WX_SH_ID);
//		parameters.put("transaction_id", "");
		parameters.put("out_trade_no", tradeNo);
		parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
		String sign = PayCommonUtil.createSign("UTF-8", parameters);
		parameters.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);
		String result =CommonUtil.httpsRequest(JSDDConfig.CHECK_ORDER_URL, "POST", requestXML);
		Map<String, String> map = XMLUtil.doXMLParse(result);
		return map;
	}
}
