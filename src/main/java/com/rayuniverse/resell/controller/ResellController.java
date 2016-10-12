package com.rayuniverse.resell.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


import com.rayuniverse.domain.OrderDetailDomain;
import com.rayuniverse.order.service.OrderService;
import com.rayuniverse.resell.service.ReSellService;

@Controller
public class ResellController {
	
	@Autowired
	ReSellService reSellService;
	
	
	//核心出单回销页面
	@RequestMapping("/resell/toLksOrderResell")
	public String toLksOrderResell(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		String orderId = request.getParameter("OrderId");
		String orderType = request.getParameter("OrderType");		
		
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		
	
		orderInfo.setOrderType(orderType);
		if(orderId != null && !"".equals(orderId)){
			try
			{
				orderInfo.setOrderId(orderId);
			}catch(Exception e){}
		}		
		
		map.put("orderInfo", orderInfo);
		
		return "resell/lksOrderResell";
	}
	
	//收费确认页面
	@RequestMapping("/resell/toOutletsOrderResell")
	public String toOutletsOrderResell(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		String orderId = request.getParameter("OrderId");
		String orderType = request.getParameter("OrderType");
					
		
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		
		orderInfo.setOrderType(orderType);
		
		if(orderId != null && !"".equals(orderId)){
			try
			{
				orderInfo.setOrderId(orderId);
			}catch(Exception e){}
		}		
					
		
		map.put("orderInfo", orderInfo);
		
		return "resell/confirmAmtOrderResell";
	}
		
	
	//航意险保单回销
	@RequestMapping("/resell/HangYiPolicyResell")
	public String HangYiPolicyResell(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		String orderId = request.getParameter("OrderId");
	
					
		
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		
	
		
		if(orderId != null && !"".equals(orderId)){
			try
			{
				orderInfo.setOrderId(orderId);
			}catch(Exception e){}
		}		
					
		
		map.put("orderInfo", orderInfo);
		
		return "resell/hangYiOrderResell";
	}
	
	
	
	//核心出单回销
	@RequestMapping("/resell/resellPolicy")
	public void resellPolicy(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String orderId = request.getParameter("orderId");	
		String policyNo = request.getParameter("policyNo");	
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		orderInfo.setPolicyNo(policyNo);
	
		orderInfo.setOrderId(orderId);
	
	
		Map<String, Object> resultMap =new HashMap<String, Object>();
		resultMap = reSellService.reSellPolicyNo(orderInfo);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject obj = JSONObject.fromObject(resultMap);
		writer.print(obj.toString());
		writer.flush();
		writer.close();
	}
	
	//收费确认插入数据
	@RequestMapping("/resell/confirmAmount")
	public void confirmAmount(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String orderId = request.getParameter("orderId");	
		String amount = request.getParameter("amount");
		String agent = request.getParameter("agent");	
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		orderInfo.setAgent(agent);
		orderInfo.setDiscountAmount(amount);
		
	    orderInfo.setOrderId(orderId);
	
	
		Map<String, Object> resultMap =new HashMap<String, Object>();
		resultMap = reSellService.confirmAmount(orderInfo);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject obj = JSONObject.fromObject(resultMap);
		writer.print(obj.toString());
		writer.flush();
		writer.close();
	}
	
	//航意险保单回销
	@RequestMapping("/resell/resellHangYiPolicy")
	public void resellHangYiPolicy(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String orderId = request.getParameter("orderId");	
		String policyNo = request.getParameter("policyNo");	
		OrderDetailDomain orderInfo = new OrderDetailDomain();
		orderInfo.setPolicyNo(policyNo);
	
		orderInfo.setOrderId(orderId);
	
	
		Map<String, Object> resultMap =new HashMap<String, Object>();
		resultMap = reSellService.reSellHangYiPolicyNo(orderInfo);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject obj = JSONObject.fromObject(resultMap);
		writer.print(obj.toString());
		writer.flush();
		writer.close();
	}
	
	
	
}
