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
	
	//΢��֧����ѯ����
	@RequestMapping("/order/queryOrdersWX")
	public String queryOrdersWX(ModelMap map) {
		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		String orderType="01";//�������	
		String riskType="01";//�������
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		orderInfo.setOrderType(orderType);
		orderInfo.setRiskType(riskType);
		
		 orderList = orderService.getOrderList(orderInfo);
		
		map.put("orderList", orderList);
		return "orders/queryOrdersWX";
	}
	
	//΢��֧�����ݲ�����ѯ����
	@RequestMapping("/order/queryParamOrdersWX")
	public String getOrderList(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		String orderId = request.getParameter("orderId");
		String orderStatus = request.getParameter("orderStatus");
		String startDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
		
		
		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		String orderType="01";//�������()
		String riskType="01";//�������
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
	
	//΢��֧����ѯ���㶩��
	@RequestMapping("/order/queryOrdersWD")
	public String queryOrdersWD(ModelMap map) {

		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		String orderType="02";//�������
		String riskType="01";//�������
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		orderInfo.setOrderType(orderType);
		orderInfo.setRiskType(riskType);
		
		orderList = orderService.getOrderList(orderInfo);
		
		map.put("orderList", orderList);

		return "orders/queryOrdersWD";
	}
	
	//΢��֧�����ݲ�����ѯ���㶩��
	@RequestMapping("/order/queryParamOrdersWD")
	public String getParamOrdersWD(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		String orderId = request.getParameter("orderId");
		String orderStatus = request.getParameter("orderStatus");
		String startDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
		
		
		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		String orderType="02";//�������
		String riskType="01";//�������
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
	
	//΢��֧����ѯ���Ŷ���
	@RequestMapping("/order/queryOrdersSM")
	public String queryOrdersSM(ModelMap map) {

		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		String orderType="03";//�������
		String riskType="01";//�������
		
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
		String orderType="03";//�������
		String riskType="02";//�������
		
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		orderInfo.setOrderType(orderType);
		orderInfo.setRiskType(riskType);
		
		orderList = orderService.getOrderList(orderInfo);
		
		map.put("orderList", orderList);
		return "orders/queryOrdersHYX";
	}
	
	//΢��֧�����ݲ�����ѯ���㶩��
	@RequestMapping("/order/queryParamOrdersSM")
	public String getParamOrdersSM(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		String orderId = request.getParameter("orderId");
		String orderStatus = request.getParameter("orderStatus");
		String startDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
				
		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		String orderType="03";//�������
		String riskType="01";//�������
		
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
	
	//΢��֧�����ݲ�����ѯ���㶩��
		@RequestMapping("/order/queryParamOrdersHYX")
		public String getParamOrdersHYX(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

			String orderId = request.getParameter("orderId");
			String orderStatus = request.getParameter("orderStatus");
			String startDate = request.getParameter("startdate");
			String endDate = request.getParameter("enddate");
					
			List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
			String orderType="03";//�������
			String riskType="02";//�������
			
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
		
	
	
	//��ʷ����
	@RequestMapping("/order/queryHistoryOrders")
	public String getAllOrderList(ModelMap map) {
		List<OrderDetailDomain> orderList = new ArrayList<OrderDetailDomain>();
		
	
		orderList = orderService.getAllOrderList();
		
		map.put("orderList", orderList);
		return "orders/queryHistoryOrders";
	}
	
	
	//��ѯ��������
	@RequestMapping("/order/queryOrderDetail")
	public String getOrderDetail(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
		String orderId = request.getParameter("OrderId");//��������
		
		OrderDetailDomain orderInfo = orderService.queryOrderByOrderId(orderId);//��ѯ������Ϣ
		String openId= orderInfo.getOpenId();
		CustomerDetailDomain customerDomain =  orderService.queryClientInfoByOpenId(openId);//��ѯ�ͻ���Ϣ
		
		Map<String, Object> carSeqMap = customerService.queryCarDetailInfoByCarSeq(orderInfo.getCarSeq());
		if(carSeqMap!=null&&carSeqMap.get("carDetailInfo")!=null){
			CarTypeListDomain carDetailInfo = (CarTypeListDomain) carSeqMap.get("carDetailInfo");
			
			map.put("carDetailInfo", carDetailInfo);
		}
			
		
		List<OrderRiskInfo> riskInfoList = orderService.queryProductListByOrderId(orderId);
		List<OrderRiskInfo> riskInfoListMap = new ArrayList<OrderRiskInfo>();//��ѯ������Ϣ
		
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
						orderParameter.setParameterKeyDesc("�Ƿ񲻼�����");
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
				orderParameterList.get(k).setParameterKeyDesc("ԤԼ�ͻ���ַ");
				
			}else if(StringUtils.equals("NET_CODE", orderParameterList.get(k).getParameterKey())){
				String netCode = orderParameterList.get(k).getKeyValue();
				StringBuffer netInfo = new StringBuffer();
				Map<String,Object> netMap = reSellDao.queryNetFullInfo(netCode);
				netInfo.append(netMap.get("net_name"));
				netInfo.append("(");
				netInfo.append(netMap.get("net_address"));
				netInfo.append(")");
				
				orderParameterList.get(k).setParameterValue(netInfo.toString());
				orderParameterList.get(k).setParameterKeyDesc("ԤԼ����");
			}else if(StringUtils.equals("VISIT_DATE", orderParameterList.get(k).getParameterKey())){
				String dateStr = orderParameterList.get(k).getKeyValue();
				StringBuffer dateInfo = new StringBuffer();
				dateInfo.append(dateStr.substring(0, 4));
				dateInfo.append("��");
				dateInfo.append(dateStr.substring(4,6));
				dateInfo.append("��");
				dateInfo.append(dateStr.substring(6, 8));
				dateInfo.append("��");
				
				orderParameterList.get(k).setParameterValue(dateInfo.toString());
				orderParameterList.get(k).setParameterKeyDesc("ԤԼ����");
			}else if(StringUtils.equals("VISIT_HOUR", orderParameterList.get(k).getParameterKey())){
				int hour = Integer.parseInt(orderParameterList.get(k).getKeyValue());
				StringBuffer hourInfo = new StringBuffer();
				hourInfo.append(hour);
				hourInfo.append("ʱ��");
				hourInfo.append(hour+2);
				hourInfo.append("ʱ");
				
				orderParameterList.get(k).setParameterValue(hourInfo.toString());
				orderParameterList.get(k).setParameterKeyDesc("ԤԼʱ��");
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
        System.out.println("~~~~~~~~~~~~~~~~����ɹ�~~~~~~~~~");
        outSteam.close();
        inStream.close();
        String result  = new String(outSteam.toByteArray(),"utf-8");//��ȡ΢�ŵ�������notify_url�ķ�����Ϣ
        Map<Object, Object> map = XMLUtil.doXMLParse(result);
        for(Object keyValue : map.keySet()){
            System.out.println(keyValue+"="+map.get(keyValue));
        }
        if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
            //TODO �����ݿ�Ĳ���
            response.getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));   //����΢�ŷ����������յ���Ϣ�ˣ���Ҫ�ڵ��ûص�action��
            System.out.println("-------------"+PayCommonUtil.setXML("SUCCESS", ""));
        } 
        
        // �ж��Ƿ��׳ɹ�
        Map orderInfo = WeixinUtil.getOrderInfo((String)map.get("out_trade_no"));
    
        if("SUCCESS".equals((String)orderInfo.get("trade_state"))){
        	//���¶�����Ϣ
        	customerService.paySuccess((String)map.get("out_trade_no"));
        	
        }else{
        	//֧��ʧ�ܣ��˹��鿴��΢�Žӿڳ�����
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~΢��֧��ʧ��:������["+String.valueOf(orderInfo.get("out_trade_no"))+"]~~~~~~~~~~~~~~~~~~~~~");
        }
	}
	
}
*/