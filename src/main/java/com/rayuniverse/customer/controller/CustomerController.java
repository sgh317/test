package com.rayuniverse.customer.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rayuniverse.customer.service.CustomerService;
import com.rayuniverse.domain.CustomerDetailDomain;
import com.rayuniverse.framework.json.JsonUtil;

@Controller
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping("/customer/toImportData")
	public String toImportData(ModelMap map) {

		return "customer/importData";
	}
	
	
	@RequestMapping("/customer/importCustomerList")
	public String importClientList(@RequestParam MultipartFile content,
								   HttpServletRequest request,
								   HttpServletResponse response,
								   ModelMap map) {	
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String flag = "N";
		String message = "";
		
		try{
		resultMap = customerService.importClientList(content);
		
		flag = (String)resultMap.get("flag");
		message = (String)resultMap.get("message");
		
		}catch(Exception e){
			flag = "N";
			message = "只支持xsl及xslx,请重新选择";
		}
		
		map.putAll(resultMap);
		
		return "customer/importClientResult";
	}
	
	@RequestMapping("/customer/importCustomerQuery")
	public String importCustomerQuery(ModelMap map){
		return "customer/importCustomerQuery";
	}
	
	/**
	 * 查询详细内容
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/customer/queryImportClient")
	public void queryImportClient(HttpServletRequest request,
			   						HttpServletResponse response) throws IOException{
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String clientName = request.getParameter("clientName");
		String carNo = request.getParameter("carNo");
		String mobileNo = request.getParameter("mobileMp");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String bindStartDate = request.getParameter("bindStartDate");
		String bindEndDate = request.getParameter("bindEndDate");
		String currentPage = request.getParameter("currentPage");
		
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("clientName", clientName);
		parameterMap.put("carNo", carNo);
		parameterMap.put("mobileNo", mobileNo);
		parameterMap.put("startDate", startDate);
		parameterMap.put("endDate", endDate);
		parameterMap.put("bindStartDate", bindStartDate);
		parameterMap.put("bindEndDate", bindEndDate);
		parameterMap.put("currentPage", Integer.parseInt(currentPage));
		
		List<CustomerDetailDomain> customerDetailList = customerService.queryImportClient(parameterMap);
		
		Integer totalPage = customerService.queryTotalPageSize(parameterMap);
		
		resultMap.put("customerDetailList", customerDetailList);
		resultMap.put("totalPage", 1+totalPage/50);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject obj = JSONObject.fromObject(resultMap);
		writer.print(obj.toString());
		writer.flush();
		writer.close();
		
	}
	
}
