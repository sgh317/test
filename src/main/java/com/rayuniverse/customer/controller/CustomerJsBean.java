package com.rayuniverse.customer.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.eval.EvaluationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.rayuniverse.customer.service.CustomerService;
import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.json.JsonUtil;
import com.rayuniverse.framework.jsrpc.JsBean;
import com.rayuniverse.framework.recharge.ZSService;


@Controller
@JsBean("customerJsBean")
public class CustomerJsBean {
	
	private Logger log = Logger.getLogger(CustomerJsBean.class);
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ZSService zSService;
	
	
	/**
	 * 
	 * @param clientName 代理人姓名
	 * @param carNo      车牌号
	 * @param mobileNo   电话号码
	 * @return
	 */
	@Transactional
	public String bindCustomerInfo(String clientName,String mobileNo,String carNo){
		
		//String openId = "";
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap = customerService.bindCustomerInfo(clientName,carNo,mobileNo);
		
		return JsonUtil.toJson(resultMap, true);
	}
	
	/**
	 * 赠送流量或话费
	 * @param zsType 赠送类型
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Transactional
	public String zsProductInfo(String zsType) throws UnsupportedEncodingException{
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		if("tel".equals(zsType)){//赠送流量
			resultMap = zSService.zsHFService();
		}else if("ll".equals(zsType)){//赠送流量
			resultMap = zSService.zsLLService();
		}else{
			resultMap.put("flag", "N");
			resultMap.put("message", "赠送类别有误");
		}
		
		return JsonUtil.toJson(resultMap, true);
	}
	
	/**
	 * 查询详细信息
	 * @return
	 * @throws EvaluationException 
	 * @throws ParseException 
	 */
	@Transactional
	public String queryCustomerDetailInfo() throws ParseException{
		String openId = PlatformContext.getUmUserId();
		
//		if(StringUtils.isBlank(openId))
//			openId = "oKhU-wSC1vjhsZGG3nsSWcFLZ3FI";//TODO fortest 
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		if(StringUtils.isNotBlank(openId)){
			
			resultMap = customerService.queryCustomerDetailInfo(openId);
			
		}else{
			resultMap.put("flag", "N");
			resultMap.put("message", "未能获取到对应的openId");
		}
		
		return JsonUtil.toJson(resultMap, true);
	}
	
	/**
	 * 查询车型详细信息
	 * @param carSeq
	 * @return
	 */
	@Transactional
	public String queryCarDetailInfoByCarSeq(String carSeq){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		if(StringUtils.isNotBlank(carSeq)){
			
			resultMap = customerService.queryCarDetailInfoByCarSeq(carSeq);
		}else{
			resultMap.put("flag", "N");
			resultMap.put("message", "未能获取车型序号");
		}
		
		return JsonUtil.toJson(resultMap, true);
	}
	
	/**
	 * 保存订单信息
	 * @param orderId
	 * @param carNo
	 * @param loadDate
	 * @param frameNo
	 * @param engineNo
	 * @param clientName
	 * @param clientIdNo
	 * @param carClassifyCode
	 * @param customerId
	 * @return
	 * @throws ParseException 
	 */
	@Transactional
	public String saveOrderInfo(String orderId,
			                    String carNo,
			                    String loadDate,
			                    String carClassifyCode,
			                    String customerId,
			                    String carSeat,
			                    String buyPrice,
			                    String carSeq,
			                    String packageType,
			                    String effectDate) throws ParseException{
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap = customerService.saveOrderInfo(orderId,
                							      carNo,
                							      loadDate,
                							      carClassifyCode,
                							      customerId,
                							      carSeat,
                							      buyPrice,
                							      carSeq,
                							      packageType,
                							      effectDate);
		
		return JsonUtil.toJson(resultMap, true);
		
	}
	
	@Transactional
	public String calProduct(String productCode) throws ParseException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
				
		resultMap = customerService.calProduct(productCode);
		
		return JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String calProductWithAmount(String productCode,String amount) throws ParseException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap = customerService.calProductWithAmount(productCode,amount);
				
		return JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String calProductWithPassenger(String productCode,String forDriver,String forPassenger,String amount) throws ParseException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap = customerService.calProductWithPassenger(productCode,forDriver,forPassenger,amount);
				
		return JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String calProductGlass(String productCode,String isLocalGlass,String amount) throws ParseException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap = customerService.calProductGlass(productCode,isLocalGlass,amount);
		
		return JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String calProductAll(String choose100001,String amount100001,
			                    String choose100002,String amount100002,
			                    String choose100003,String amount100003,String buyforDriver,String buyforPassenger,
			                    String choose100004,
			                    String choose100005,String amount100005,String isLocalGlass,
			                    String choose100006,String amount100006,
			                    String choose100007,String amount100007,
			                    String choose100008,
			                    String choose100009,String amount100009,
			                    String choose100010,String amount100010,
			                    String choose100011,
			                    String designatedDriver,
   							    String designatedArea,String contain100001,
   							    String contain100002,String contain100003,
   							    String contain100004,String contain100006) throws ParseException{
		Map<String, Object> result = customerService.calProductAll(choose100001,amount100001,choose100002,amount100002,choose100003,amount100003,buyforDriver,buyforPassenger,
															   choose100004,choose100005,amount100005,isLocalGlass,choose100006,amount100006,choose100007,amount100007,
															   choose100008,choose100009,amount100009,choose100010,amount100010,choose100011,designatedDriver,designatedArea,
															   contain100001,contain100002,contain100003,contain100004,contain100006);
		
		return  JsonUtil.toJson(result, true);
	}
	
	@Transactional
	public String loadProductInfoList() throws Throwable{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap = customerService.loadProductInfoList();
		
		return JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String saveProduct(
			String orderId,
			String choose100001,String amount100001,
            String choose100002,String amount100002,
            String choose100003,String amount100003,String buyforDriver,String buyforPassenger,
            String choose100004,
            String choose100005,String amount100005,String isLocalGlass,
            String choose100006,String amount100006,
            String choose100007,String amount100007,
            String choose100008,
            String choose100009,String amount100009,
            String choose100010,String amount100010,
            String choose100011,
            String designatedDriver,String designatedArea,
            String contain100001,String contain100002,
            String contain100003,String contain100004,String contain100006) throws Throwable{
		Map<String, Object> resultMap = customerService.saveProduct(orderId,choose100001,amount100001,choose100002,amount100002,choose100003,amount100003,buyforDriver,buyforPassenger,
				   choose100004,choose100005,amount100005,isLocalGlass,choose100006,amount100006,choose100007,amount100007,
				   choose100008,choose100009,amount100009,choose100010,amount100010,choose100011,designatedDriver,designatedArea,contain100001,contain100002,contain100003,contain100004,contain100006);
		
		
		
		return  JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String loadOrderReviewInfo(){
		Map<String, Object> resultMap = customerService.loadOrderReviewInfo();
		
		return JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String refreshAreaInfo(String cityCode){
		Map<String, Object> resultMap = customerService.refreshAreaInfo(cityCode);
		return JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String refreshNetInfo(String areaCode){
		Map<String, Object> resultMap = customerService.refreshNetInfo(areaCode);
		
		return JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String submitOrderInfo(String orderId,String city,
			                      String area,String net,
			                      String orderType,String visitDate02,String visitHour02,
			                      String clientAddress01,
			                      String visitDate03,
			                      String visitHour03,
			                      String clientAddress03,
			                      String applicantName,
			                      String applicantNo) throws Throwable{
		Map<String, Object> resultMap = customerService.submitOrderInfo(orderId,city,area,net,orderType,visitDate02,visitHour02,clientAddress01,visitDate03,visitHour03,clientAddress03,applicantName,applicantNo);
		return JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String callPackagePrem(String orderId,String loadDate,String carClassifyCode,String carSeat,String buyPrice,String packageType,String carSeq,String effectDate) throws ParseException{
		
		Map<String,Object> resultMap = customerService.callPackagePrem(orderId,loadDate,carClassifyCode,carSeat,buyPrice,packageType,carSeq,effectDate);
		return JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String getCarDetail(String carSeq){
		Map<String,Object> resultMap = customerService.getCarDetail(carSeq);
		return JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String submitHYX(String applicantName,String applicantIdNo) throws Throwable{
		Map<String,Object> resultMap = customerService.submitHYX(applicantName,applicantIdNo);
		return JsonUtil.toJson(resultMap, true);
	}
	
	public String loadZSInfo(){
		Map<String,Object> resultMap = customerService.loadZSInfo();
		return JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String paySuccess(String orderId){
		Map<String,Object> resultMap = customerService.paySuccess(orderId);
		return JsonUtil.toJson(resultMap, true);
	}
	
	@Transactional
	public String delBind(){
		Map<String,Object> resultMap = customerService.delBind();
		return JsonUtil.toJson(resultMap, true);
	}
	
}
