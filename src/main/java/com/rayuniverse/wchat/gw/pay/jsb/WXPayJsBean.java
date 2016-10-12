package com.rayuniverse.wchat.gw.pay.jsb;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.json.JsonUtil;
import com.rayuniverse.framework.jsrpc.JsBean;
import com.rayuniverse.order.service.OrderService;
import com.rayuniverse.util.JSDDConfig;

@Controller
@JsBean(value="WXPaySrv")
public class WXPayJsBean {
	
		@Autowired
		OrderService orderSrv;
		
		/**
		 * H5页面点击支付按钮
		 * @return
		 * @throws JDOMException
		 * @throws IOException
		 */
		public String getParams(String omOrderId,String omAmount,String productName) throws JDOMException, IOException{
			String prepayId = submitXmlGetPrepayId(omOrderId,omAmount,productName);
			SortedMap<Object,Object> params = new TreeMap<Object,Object>();
	        params.put("appId", PlatformContext.getConfigItem("WEIXIN_APPID"));
	        params.put("timeStamp", Long.toString(new Date().getTime()));
	        params.put("nonceStr", PayCommonUtil.CreateNoncestr());
	        params.put("package", "prepay_id="+prepayId);
	        params.put("signType", "MD5");
	        String paySign =  PayCommonUtil.createSign("UTF-8", params);
	        params.put("packageValue", "prepay_id="+prepayId);    //这里用packageValue是预防package是关键字在js获取值出错
	        params.put("paySign", paySign);                       //paySign的生成规则和Sign的生成规则一致
	        params.put("sendUrl", "");        //付款成功后跳转的页面
//	        String userAgent = request.getHeader("user-agent");
//	        char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger")+15);
//	        params.put("agent", new String(new char[]{agent}));//微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
//	        String json = JSONObject.fromObject(params).toString();
			
	        return JsonUtil.toJson(params, true);
		}
		
		
		/**
		 * 获取prepay_id
		 * @return
		 * @throws JDOMException
		 * @throws IOException
		 */
		public String submitXmlGetPrepayId(String orderId,String amount,String productName) throws JDOMException, IOException {
			
			SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
			parameters.put("appid", PlatformContext.getConfigItem("WEIXIN_APPID")); //公众号
			parameters.put("mch_id", JSDDConfig.WX_SH_ID); //商户号
			//parameters.put("attach", "车险支付");
			parameters.put("nonce_str", PayCommonUtil.CreateNoncestr()); //随机字符串
			System.out.println("1-1");
			parameters.put("body", productName); //商品描述
			parameters.put("notify_url", JSDDConfig.NOTIFY_URL);
			parameters.put("openid", PlatformContext.getUmUserId());//openid
			parameters.put("out_trade_no", orderId); //商户订单号
//			parameters.put("spbill_create_ip",request.getRemoteAddr());   //待处理
			String totalFee = "0";
			
			//oELUVuPSg9JMjPSuA2pP9d5M1ht0
			if(StringUtils.equals("oELUVuPSg9JMjPSuA2pP9d5M1ht0", PlatformContext.getUmUserId())){
				totalFee = "1";
			}else if(StringUtils.isNotBlank(amount))
				totalFee = new BigDecimal(amount).multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			else{
				throw new RuntimeException("未获取到金额");
			}
			parameters.put("total_fee", totalFee); //订单总金额
			parameters.put("trade_type", "JSAPI");
			String sign = PayCommonUtil.createSign("UTF-8", parameters);
			parameters.put("sign", sign);
			
			String requestXML = PayCommonUtil.getRequestXml(parameters);
			//解析xml
			String result =CommonUtil.httpsRequest(JSDDConfig.UNIFIED_ORDER_URL, "POST", requestXML);
			Map<String, String> map = XMLUtil.doXMLParse(result);
			//待定配置返回参数
			System.out.println("prepay_id="+map.get("prepay_id"));
			return map.get("prepay_id");
		}
}
