/*package com.rayuniverse.backshow.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rayuniverse.customer.service.CustomerService;
import com.rayuniverse.domain.CarTypeListDomain;
import com.rayuniverse.domain.CustomerDetailDomain;
import com.rayuniverse.domain.OrderDetailDomain;
import com.rayuniverse.domain.OrderParameterDomain;
import com.rayuniverse.domain.OrderRiskInfo;
import com.rayuniverse.domain.OrderRiskParameter;
import com.rayuniverse.order.service.OrderService;
import com.rayuniverse.resell.dao.ReSellDao;
import com.rayuniverse.wchat.gw.pay.jsb.PayCommonUtil;
import com.rayuniverse.wchat.gw.pay.jsb.XMLUtil;
import com.rayuniverse.wchat.gw.util.WeixinUtil;



@Controller
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ReSellDao reSellDao;
	
	//微信支付查询订单
	@RequestMapping("/order/queryOrdersWX")
	public String queryOrdersWX(ModelMap map) {
		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		String orderType="01";//订单类别	
		String riskType="01";//保险类别
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		orderInfo.setOrderType(orderType);
		orderInfo.setRiskType(riskType);
		
		 orderList = orderService.getOrderList(orderInfo);
		
		map.put("orderList", orderList);
		return "orders/queryOrdersWX";
	}
	
	//微信支付根据参数查询订单
	@RequestMapping("/order/queryParamOrdersWX")
	public String getOrderList(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		String orderId = request.getParameter("orderId");
		String orderStatus = request.getParameter("orderStatus");
		String startDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
		
		
		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		String orderType="01";//订单类别()
		String riskType="01";//保险类别
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		orderInfo.setOrderType(orderType);
		orderInfo.setRiskType(riskType);
		if(null != orderId  && !"".equals(orderId)){
			try
			{
				orderInfo.setOrderId(orderId);
			}catch(Exception e){}
		}		
		
			
		orderInfo.setOrderStatus(orderStatus);
		orderInfo.setStartDate(startDate);
		orderInfo.setEndDate(endDate);
		
		orderList = orderService.getParamOrderList(orderInfo);
		
		map.put("orderList", orderList);
		map.put("orderInfo", orderInfo);
		return "orders/queryParamOrdersWX";
	}
	
	//微信支付查询网点订单
	@RequestMapping("/order/queryOrdersWD")
	public String queryOrdersWD(ModelMap map) {

		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		String orderType="02";//订单类别
		String riskType="01";//保险类别
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		orderInfo.setOrderType(orderType);
		orderInfo.setRiskType(riskType);
		
		orderList = orderService.getOrderList(orderInfo);
		
		map.put("orderList", orderList);

		return "orders/queryOrdersWD";
	}
	
	//微信支付根据参数查询网点订单
	@RequestMapping("/order/queryParamOrdersWD")
	public String getParamOrdersWD(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		String orderId = request.getParameter("orderId");
		String orderStatus = request.getParameter("orderStatus");
		String startDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
		
		
		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		String orderType="02";//订单类别
		String riskType="01";//保险类别
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		orderInfo.setOrderType(orderType);
		orderInfo.setRiskType(riskType);
		if(null != orderId  && !"".equals(orderId)){
			try
			{
				orderInfo.setOrderId(orderId);
			}catch(Exception e){}
		}		
		orderInfo.setOrderStatus(orderStatus);
		orderInfo.setStartDate(startDate);
		orderInfo.setEndDate(endDate);
		
		orderList = orderService.getParamOrderList(orderInfo);
		
		map.put("orderList", orderList);
		map.put("orderInfo", orderInfo);

		return "orders/queryParamOrdersWD";
	}
	
	//微信支付查询上门订单
	@RequestMapping("/order/queryOrdersSM")
	public String queryOrdersSM(ModelMap map) {

		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		String orderType="03";//订单类别
		String riskType="01";//保险类别
		
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		orderInfo.setOrderType(orderType);
		orderInfo.setRiskType(riskType);
		
		orderList = orderService.getOrderList(orderInfo);
		
		map.put("orderList", orderList);
		return "orders/queryOrdersSM";
	}
	
	@RequestMapping("/order/queryOrdersHYX")
	public String queryOrderHYX(ModelMap map){
		
		
		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		String orderType="03";//订单类别
		String riskType="02";//保险类别
		
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		orderInfo.setOrderType(orderType);
		orderInfo.setRiskType(riskType);
		
		orderList = orderService.getOrderList(orderInfo);
		
		map.put("orderList", orderList);
		return "orders/queryOrdersHYX";
	}
	
	//微信支付根据参数查询网点订单
	@RequestMapping("/order/queryParamOrdersSM")
	public String getParamOrdersSM(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		String orderId = request.getParameter("orderId");
		String orderStatus = request.getParameter("orderStatus");
		String startDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
				
		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		String orderType="03";//订单类别
		String riskType="01";//保险类别
		
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		orderInfo.setOrderType(orderType);
		orderInfo.setRiskType(riskType);
		if(null != orderId  && !"".equals(orderId)){
			try
			{
				orderInfo.setOrderId(orderId);
			}catch(Exception e){}
		}	
		orderInfo.setOrderStatus(orderStatus);
		orderInfo.setStartDate(startDate);
		orderInfo.setEndDate(endDate);
		
		orderList = orderService.getParamOrderList(orderInfo);
		
		map.put("orderList", orderList);
		map.put("orderInfo", orderInfo);

		return "orders/queryParamOrdersSM";
	}
	
	//微信支付根据参数查询网点订单
		@RequestMapping("/order/queryParamOrdersHYX")
		public String getParamOrdersHYX(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

			String orderId = request.getParameter("orderId");
			String orderStatus = request.getParameter("orderStatus");
			String startDate = request.getParameter("startdate");
			String endDate = request.getParameter("enddate");
					
			List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
			String orderType="03";//订单类别
			String riskType="02";//保险类别
			
			OrderDetailDomain orderInfo = new OrderDetailDomain();
			orderInfo.setOrderType(orderType);
			orderInfo.setRiskType(riskType);
			if(null != orderId  && !"".equals(orderId)){
				try
				{
					orderInfo.setOrderId(orderId);
				}catch(Exception e){}
			}	
			orderInfo.setOrderStatus(orderStatus);
			orderInfo.setStartDate(startDate);
			orderInfo.setEndDate(endDate);
			
			orderList = orderService.getParamOrderList(orderInfo);
			
			map.put("orderList", orderList);
			map.put("orderInfo", orderInfo);

			return "orders/queryParamOrdersHYX";
		}
		
	
	
	//历史订单
	@RequestMapping("/order/queryHistoryOrders")
	public String getAllOrderList(ModelMap map) {
		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		
	
		orderList = orderService.getAllOrderList();
		
		map.put("orderList", orderList);
		return "orders/queryHistoryOrders";
	}
	
	
	//查询订单详情
	@RequestMapping("/order/queryOrderDetail")
	public String getOrderDetail(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
		String orderId = request.getParameter("OrderId");//订单号码
		
		OrderDetailDomain orderInfo = orderService.queryOrderByOrderId(orderId);//查询订单信息
		String openId= orderInfo.getOpenId();
		CustomerDetailDomain customerDomain =  orderService.queryClientInfoByOpenId(openId);//查询客户信息
		
		Map<String, Object> carSeqMap = customerService.queryCarDetailInfoByCarSeq(orderInfo.getCarSeq());
		if(carSeqMap!=null&&carSeqMap.get("carDetailInfo")!=null){
			CarTypeListDomain carDetailInfo = (CarTypeListDomain) carSeqMap.get("carDetailInfo");
			
			map.put("carDetailInfo", carDetailInfo);
		}
			
		
		List<OrderRiskInfo> riskInfoList = orderService.queryProductListByOrderId(orderId);
		List<OrderRiskInfo> riskInfoListMap = new ArrayList<OrderRiskInfo>();//查询险种信息
		
		List<OrderRiskParameter> riskParameter100008 = new ArrayList<OrderRiskParameter>();
		
		for(OrderRiskInfo param : riskInfoList){
			if(StringUtils.equals("100008", param.getProductCode())){
				riskParameter100008 = orderService.queryRiskParameterByProductAndOrder(orderId,param.getProductSeq());
			}
		}
		
		 for(OrderRiskInfo param : riskInfoList){			
			 String productSeq = param.getProductSeq();
			 if(!StringUtils.equals("100008", param.getProductCode())){
				 List<OrderRiskParameter> riskParameterList = orderService.queryRiskParameterByProductAndOrder(orderId,productSeq);
				 
				 for(OrderRiskParameter riskParameter:riskParameter100008){
					 if(StringUtils.equals("contain"+param.getProductCode(), riskParameter.getParameterKey())){
						 
						OrderRiskParameter orderParameter = new OrderRiskParameter();
						orderParameter.setParameterKeyDesc("是否不计免赔");
						orderParameter.setKeyValue(riskParameter.getKeyValue());
						riskParameterList.add(orderParameter);
						break;
					 }
				 }
				 
				 param.setOrderRiskParameterList(riskParameterList);
				 
				 riskInfoListMap.add(param); 
			 }else{
				 param.setOrderRiskParameterList(null);
				 riskInfoListMap.add(param);
			 }
		 }
		 
		orderInfo.setOrderRiskInfoList(riskInfoListMap);
		
		List<OrderParameterDomain> orderParameterList = orderService.queryOrderParameterList(orderId);
		for(int k=0;k<orderParameterList.size();k++){
			if(StringUtils.equals("CLIENT_ADDRESS", orderParameterList.get(k).getParameterKey())){
				orderParameterList.get(k).setParameterValue(orderParameterList.get(k).getKeyValue());
				orderParameterList.get(k).setParameterKeyDesc("预约客户地址");
				
			}else if(StringUtils.equals("NET_CODE", orderParameterList.get(k).getParameterKey())){
				String netCode = orderParameterList.get(k).getKeyValue();
				StringBuffer netInfo = new StringBuffer();
				Map<String,Object> netMap = reSellDao.queryNetFullInfo(netCode);
				netInfo.append(netMap.get("net_name"));
				netInfo.append("(");
				netInfo.append(netMap.get("net_address"));
				netInfo.append(")");
				
				orderParameterList.get(k).setParameterValue(netInfo.toString());
				orderParameterList.get(k).setParameterKeyDesc("预约网点");
			}else if(StringUtils.equals("VISIT_DATE", orderParameterList.get(k).getParameterKey())){
				String dateStr = orderParameterList.get(k).getKeyValue();
				StringBuffer dateInfo = new StringBuffer();
				dateInfo.append(dateStr.substring(0, 4));
				dateInfo.append("年");
				dateInfo.append(dateStr.substring(4,6));
				dateInfo.append("月");
				dateInfo.append(dateStr.substring(6, 8));
				dateInfo.append("日");
				
				orderParameterList.get(k).setParameterValue(dateInfo.toString());
				orderParameterList.get(k).setParameterKeyDesc("预约日期");
			}else if(StringUtils.equals("VISIT_HOUR", orderParameterList.get(k).getParameterKey())){
				int hour = Integer.parseInt(orderParameterList.get(k).getKeyValue());
				StringBuffer hourInfo = new StringBuffer();
				hourInfo.append(hour);
				hourInfo.append("时至");
				hourInfo.append(hour+2);
				hourInfo.append("时");
				
				orderParameterList.get(k).setParameterValue(hourInfo.toString());
				orderParameterList.get(k).setParameterKeyDesc("预约时间");
			}
				
		}
		 
		map.put("orderInfo", orderInfo);
		map.put("customerInfo", customerDomain);
		map.put("orderParameterList", orderParameterList);
		return "orders/orderDetail";
	}
	
	
	
	@RequestMapping("/order/queryAppoiOrders")
	public String queryAppoiOrders(ModelMap map) {
		return "orders/queryAppoiOrders";
	}
	
	@RequestMapping("/order/queryNotSubmitOrders")
	public String queryNotSubmitOrders(ModelMap map) {
		return "orders/queryNotSubmittedOrders";
	}
	
	@RequestMapping("order/queryOrderImage")
	public String queryImageOrder(HttpServletRequest request,ModelMap map){
		String orderId = request.getParameter("orderId");
		
		Map<String,Object> imageList = new HashMap<String,Object>();
		
		imageList = orderService.queryOrderImageList(orderId);
		
		map.put("imageList",imageList);
		
		return "orders/orderImageList";
	}
	
	@RequestMapping("/payResult/getResult")
	public void getPaymentResult(HttpServletRequest request, HttpServletResponse response, ModelMap mMap) throws JDOMException, IOException{
		InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
        outSteam.close();
        inStream.close();
        String result  = new String(outSteam.toByteArray(),"utf-8");//获取微信调用我们notify_url的返回信息
        Map<Object, Object> map = XMLUtil.doXMLParse(result);
        for(Object keyValue : map.keySet()){
            System.out.println(keyValue+"="+map.get(keyValue));
        }
        if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
            //TODO 对数据库的操作
            response.getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));   //告诉微信服务器，我收到信息了，不要在调用回调action了
            System.out.println("-------------"+PayCommonUtil.setXML("SUCCESS", ""));
        } 
        
        // 判断是否交易成功
        Map orderInfo = WeixinUtil.getOrderInfo((String)map.get("out_trade_no"));
    
        if("SUCCESS".equals((String)orderInfo.get("trade_state"))){
        	//更新订单信息
        	customerService.paySuccess((String)map.get("out_trade_no"));
        	
        }else{
        	//支付失败，人工查看，微信接口出问题
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~微信支付失败:订单号["+String.valueOf(orderInfo.get("out_trade_no"))+"]~~~~~~~~~~~~~~~~~~~~~");
        }
	}
	
}
*/