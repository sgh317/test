package com.rayuniverse.resell.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayuniverse.domain.OrderDetailDomain;
import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.resell.dao.ReSellDao;
import com.rayuniverse.util.JSDDConfig;
import com.rayuniverse.wchat.gw.asyn.WXTemplateMessageService;

@Service
public class ReSellService {

	@Autowired
	ReSellDao reSellDao;
	@Autowired
	WXTemplateMessageService wXTemplateMessageService;
	
	
	/**
	 * 保单回销
	 * @param orderInfo	
	 * @return
	 */
	public Map<String, Object> reSellPolicyNo(OrderDetailDomain orderInfo) {
		
		String flag = "N";
		String message = "系统异常！";
		
		Map<String, Object> resultMap =new HashMap<String, Object>();
		
		OrderDetailDomain orderStatusInfo = reSellDao.getOrderStatus(orderInfo);
				
		if(null != orderStatusInfo && !"".equals(orderStatusInfo.getOrderStatus())){
			
			String orderStatus = orderStatusInfo.getOrderStatus();
			String orderType = orderStatusInfo.getOrderType();//订单类别
			String clientName = orderStatusInfo.getClientName();//客户姓名
			String policyNo = orderInfo.getPolicyNo();//保单号
			String carNo = orderStatusInfo.getCarNo();//车牌号
			String openId = orderStatusInfo.getOpenId();//微信openID
		
				if(orderStatus.equals("02")){
					 reSellDao.reSellPolicyNo(orderInfo);
					 
					  flag = "Y";
					  message = "回销成功";
				  
					  try {
						  
						this.noticeMessageByWx(orderInfo.getOrderId(),orderType,clientName,policyNo,carNo,openId);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					  
				}else if(orderStatus.equals("03")){
					flag = "N";
					message = "该订单已回销,谢谢。";
								
				}else{
					
					flag = "N";
					message = "该订单未进行收费确认！";
					
				}
			}else{		
				flag = "N";
				message = "没有该订单！";
			}	
				resultMap.put("flag", flag);
				resultMap.put("message", message);
				
				
				return resultMap;
			}
	
	/**
	 * 收费确认
	 * @param orderInfo	
	 * @return
	 */
	public Map<String, Object> confirmAmount(OrderDetailDomain orderInfo) {
		
		String flag = "N";
		String message = "系统异常！";
		
		Map<String, Object> resultMap =new HashMap<String, Object>();
		
		OrderDetailDomain orderStatusInfo = reSellDao.getOrderStatus(orderInfo);
		
		
		
		if( null != orderStatusInfo && !"".equals(orderStatusInfo.getOrderStatus())){
			
			String orderStatus = orderStatusInfo.getOrderStatus();
			String payAmount = orderStatusInfo.getPayAmount();//应收金额
			String discountAmount = orderInfo.getDiscountAmount();//实际付款金额
		if(orderStatus.equals("01")){
			if(discountAmount.equals(payAmount)){
				 reSellDao.confirmAmount(orderInfo);				 				 
				  flag = "Y";
				  message = "收费确认成功。";
			}else{
				 reSellDao.confirmAmount(orderInfo);
				  flag = "Y";
				  message = "收费确认成功,与应收金额有差异。";
			}
			
			
			
		}else if(orderStatus.equals("02")){
			flag = "N";
			message = "该订单已进行收费确认,谢谢。";
						
		}else if(orderStatus.equals("03")){
			flag = "N";
			message = "该订单已回销,谢谢。";
			
		}else{
			
			flag = "N";
			message = "该订单未进行收费确认！";	
		}
		
	}else{
		flag = "N";
		message = "没有该订单!";
			
	}
		resultMap.put("flag", flag);
		resultMap.put("message", message);
		
		
		return resultMap;
	}
	
	
	/**
	 * 航意险保单回销
	 * @param orderInfo	
	 * @return
	 */
	public Map<String, Object> reSellHangYiPolicyNo(OrderDetailDomain orderInfo) {
		
		String flag = "N";
		String message = "系统异常！";
		
		Map<String, Object> resultMap =new HashMap<String, Object>();
		
		OrderDetailDomain orderStatusInfo = reSellDao.getOrderStatus(orderInfo);
				
		if(null != orderStatusInfo && !"".equals(orderStatusInfo.getOrderStatus())){
			
			String orderStatus = orderStatusInfo.getOrderStatus();
			orderInfo.setOpenId(orderStatusInfo.getOpenId());
			
		 if(orderStatus.equals("03")){
			flag = "N";
			message = "该订单已回销,谢谢。";
						
		}else{
			reSellDao.reSellHangYiPolicyNo(orderInfo);
			flag = "Y";
			message = "回销成功！";
			
			try {
				  
				this.noticeHYMessageByWx(orderInfo);
				
			} catch (Throwable e) {
				e.printStackTrace();
			}
			
		}
	}else{		
		flag = "N";
		message = "没有该订单！";
	}	
		resultMap.put("flag", flag);
		resultMap.put("message", message);
		
		
		return resultMap;
	}
	
	private void noticeHYMessageByWx(OrderDetailDomain orderInfo) {
		String messageNotice = "您的航意险保单已成功出单。";
		
		Date currentTime = new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		String time = df.format(currentTime);
		
		String noticeMessage ="纸质保单已成功出具，您的保单号是"+orderInfo.getPolicyNo()+"，保单将尽快为您快递至预留地址。如有订单方面的问题或需修改订单配送地址，请致电服务专线 4009-500-888";
		
		try {
			//点对点推送消息
			HashMap msg = new HashMap();
			// 消息模板
			String templateId = JSDDConfig.POLICY_ISSUE_MODE;
			// 点击后跳到哪里
		
			msg.put("touser", orderInfo.getOpenId());
			msg.put("template_id", templateId);
			msg.put("url", "#");
			msg.put("topcolor", "#FF0000");
			
			HashMap data = new HashMap();

			HashMap first = new HashMap();
			first.put("value", messageNotice);
			first.put("color", "#173177");
			data.put("first", first);

			HashMap keyword1 = new HashMap();
			keyword1.put("value", time);
			data.put("keyword1", keyword1);

			HashMap keyword2 = new HashMap();
			keyword2.put("value", orderInfo.getPolicyNo());
			data.put("keyword2", keyword2);

			HashMap keyword3 = new HashMap();
			keyword3.put("value", "航意险保单");
			data.put("keyword3", keyword3);

			HashMap keyword4 = new HashMap();		
			keyword4.put("value", orderInfo.getClientName());
			data.put("keyword4", keyword4);
			
			HashMap keyword5 = new HashMap();		
			keyword5.put("value", orderInfo.getCarNo());
			data.put("keyword5", keyword5);

			HashMap remark = new HashMap();
			remark.put("value", noticeMessage);
			data.put("remark", remark);

			msg.put("data", data);
	        
			wXTemplateMessageService.pushTemplateMessage(msg);
		} catch (Throwable e) {
				
		//異常信息記錄
		e.printStackTrace();
		}
	}

	//核心出单回销微信推送消息
	public void noticeMessageByWx(String orderId,String orderType,String clientName,String policyNo,String carNo,String openId)throws IOException{
		
		String messageNotice = "您的保单已成功出单。";
		
		Date currentTime = new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		String time = df.format(currentTime);
		
		String noticeMessage = "";//通知信息
		if(orderType.equals("01")||orderType.equals("03")){			
			
			 noticeMessage ="纸质保单已成功出具，您的保单号是"+policyNo+"，" +
					"保单将尽快为您快递至预留地址。如有订单方面的问题或需修改订单配送地址，请致电服务专线 4009-500-888";	
			
		}else{
			List<Map<String,Object>> orderParameterList =  reSellDao.queryOrderParameter(orderId);
			
			StringBuffer netInfo = new StringBuffer();
			StringBuffer dateInfo = new StringBuffer();
			StringBuffer hourInfo = new StringBuffer();
			for(int i=0;i<orderParameterList.size();i++){
				if("NET_CODE".equals(orderParameterList.get(i).get("parameter_key"))){
					String netCode = (String) orderParameterList.get(i).get("key_value");
					Map<String,Object> netMap = reSellDao.queryNetFullInfo(netCode);
					netInfo.append(netMap.get("net_name"));
					netInfo.append("(");
					netInfo.append(netMap.get("net_address"));
					netInfo.append(")");
				}
				
				if("VISIT_DATE".equals(orderParameterList.get(i).get("parameter_key"))){
					String dateStr = (String) orderParameterList.get(i).get("key_value");
					dateInfo.append(dateStr.substring(0, 4));
					dateInfo.append("年");
					dateInfo.append(dateStr.substring(4,6));
					dateInfo.append("月");
					dateInfo.append(dateStr.substring(6, 8));
					dateInfo.append("日");
				}
				
				if("VISIT_HOUR".equals(orderParameterList.get(i).get("parameter_key"))){
					int hour = Integer.parseInt((String)orderParameterList.get(i).get("key_value"));
					hourInfo.append(hour);
					hourInfo.append("时至");
					hourInfo.append(hour+2);
					hourInfo.append("时");
				}
			}
			
			 noticeMessage ="保单已出具，保单号为"+policyNo+"，请您于"+dateInfo.toString()+hourInfo.toString()+"至"+netInfo.toString()+"网点上门取单。取单时请携带好个人身份证以便验证身份，" +
			 		"同时携带银行卡或现金现场支付保费。如有订单方面的问题或需修改上门取保单地址，请致电服务专线4009-500-888";			
		}
		
		
		try {
		//点对点推送消息
		HashMap msg = new HashMap();
		// 消息模板
		String templateId = JSDDConfig.POLICY_ISSUE_MODE;
		// 点击后跳到哪里
	
		msg.put("touser", openId);
		msg.put("template_id", templateId);
		msg.put("url", JSDDConfig.QUERY_URL);
		msg.put("topcolor", "#FF0000");
		
		HashMap data = new HashMap();

		HashMap first = new HashMap();
		first.put("value", messageNotice);
		first.put("color", "#173177");
		data.put("first", first);

		HashMap keyword1 = new HashMap();
		keyword1.put("value", time);
		data.put("keyword1", keyword1);

		HashMap keyword2 = new HashMap();
		keyword2.put("value", policyNo);
		data.put("keyword2", keyword2);

		HashMap keyword3 = new HashMap();
		keyword3.put("value", "车险保单");
		data.put("keyword3", keyword3);

		HashMap keyword4 = new HashMap();		
		keyword4.put("value", clientName);
		data.put("keyword4", keyword4);
		
		HashMap keyword5 = new HashMap();		
		keyword5.put("value", carNo);
		data.put("keyword5", keyword5);

		HashMap remark = new HashMap();
		remark.put("value", noticeMessage);
		data.put("remark", remark);

		msg.put("data", data);
        
			wXTemplateMessageService.pushTemplateMessage(msg);
		} catch (Throwable e) {
			
			//異常信息記錄
			e.printStackTrace();
		}
		
	
		
		
		
	}
	
	
}
