package com.rayuniverse.order.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rayuniverse.customer.service.CustomerService;
import com.rayuniverse.order.service.OrderService;
import com.rayuniverse.resell.dao.ReSellDao;
import com.rayuniverse.wchat.gw.pay.jsb.PayCommonUtil;
import com.rayuniverse.wchat.gw.pay.jsb.XMLUtil;
import com.rayuniverse.wchat.gw.util.WeixinUtil;

import net.sf.json.JSONObject;



@Controller
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ReSellDao reSellDao;
	
	
	@RequestMapping("order/queryOrderImage")
	public String queryImageOrder(HttpServletRequest request,ModelMap map){
		String orderId = request.getParameter("orderId");
		
		Map<String,Object> imageList = new HashMap<String,Object>();
		
		//imageList = orderService.queryOrderImageList(orderId);
		
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
	
	/**
	 * 查询充值列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/order/queryRechargeList")
	public void queryRechargeList(HttpServletRequest request,
			   						HttpServletResponse response) throws IOException{
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String clientName = request.getParameter("clientName");
		String currentPage = request.getParameter("currentPage");
		int pageSize = 10;
		
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("clientName", clientName);
		parameterMap.put("startIndex", pageSize*(Integer.parseInt(currentPage)-1));
		parameterMap.put("pageSize", pageSize);
		
		List<Map> recordsList = orderService.queryRechargeList(parameterMap);
		
		Integer recordsCount = orderService.queryRechargeTotalPageSize(parameterMap);
		
		resultMap.put("recordsList", recordsList);
		int maxPage=recordsCount/pageSize;
	   	 if(recordsCount%pageSize>0)
	   	 {
	   		 maxPage=maxPage+1;
	   	 }
		resultMap.put("totalPage", maxPage);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject obj = JSONObject.fromObject(resultMap);
		writer.print(obj.toString());
		writer.flush();
		writer.close();
		
	}
	
	/**
	 * 查询消费列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/order/queryConsumeList")
	public void queryConsumeList(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String clientName = request.getParameter("clientName");
		String currentPage = request.getParameter("currentPage");
		int pageSize = 10;
		
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("clientName", clientName);
		parameterMap.put("startIndex", pageSize*(Integer.parseInt(currentPage)-1));
		parameterMap.put("pageSize", pageSize);
		
		List<Map> recordsList = orderService.queryConsumeList(parameterMap);
		
		Integer recordsCount = orderService.queryConsumeTotalPageSize(parameterMap);
		
		resultMap.put("recordsList", recordsList);
		int maxPage=recordsCount/pageSize;
		if(recordsCount%pageSize>0)
		{
			maxPage=maxPage+1;
		}
		resultMap.put("totalPage", maxPage);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject obj = JSONObject.fromObject(resultMap);
		writer.print(obj.toString());
		writer.flush();
		writer.close();
	}
	
	/**
	* 打印订单明细列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/order/printOrderDetailList")
	public void printOrderDetailList(HttpServletRequest request,
			HttpServletResponse response) throws Throwable{
		String orderNo = request.getParameter("orderNo");
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		String[] orderArray = orderNo.split(",");
		parameterMap.put("orderNo", orderArray);
		Map<Object,List> recordsMap = orderService.queryOrderDetailList(parameterMap);
		HSSFWorkbook wb = new HSSFWorkbook();  
		  
	    // 创建Excel的工作sheet,对应到一个excel文档的tab  
	    HSSFSheet sheet = wb.createSheet("sheet1");  
	    {
	    	// 创建Excel的sheet的一行  
		    HSSFRow row = sheet.createRow(0);  
		   
		    HSSFCell cell1 = row.createCell(0);  
		    // 给Excel的单元格设置样式和赋值  
		   
		    cell1.setCellValue("订单编号");  
		    
		    HSSFCell cell2 = row.createCell(1);  
		    // 给Excel的单元格设置样式和赋值  
		   
		    cell2.setCellValue("商品名称");  
		    
		    HSSFCell cell3 = row.createCell(2);  
		    // 给Excel的单元格设置样式和赋值  
		    
		    cell3.setCellValue("运费");  
		    
		    HSSFCell cell4 = row.createCell(3);  
		    // 给Excel的单元格设置样式和赋值  
		    
		    cell4.setCellValue("总金额");  
		    
		    
		    HSSFCell cell5 = row.createCell(4);  
		    // 给Excel的单元格设置样式和赋值  
		    
		    cell5.setCellValue("订单生成时间"); 
		    
		    
		    HSSFCell cell6 = row.createCell(5);  
		    // 给Excel的单元格设置样式和赋值  
		    
		    cell6.setCellValue("收货地址"); 
		    
		    
		    HSSFCell cell7 = row.createCell(6);  
		    // 给Excel的单元格设置样式和赋值  
		    
		    cell7.setCellValue("配送时间");  
	    }
		if(null != recordsMap) {
		    int rowIndex=1;
			for (Object key : recordsMap.keySet()) {
				List<Map> recordList = recordsMap.get(key);
				for(Map m : recordList) {
					
					Map parameterOrderMap = new HashMap();
					parameterOrderMap.put("orderNo", orderNo);
					parameterOrderMap.put("orderStatus", "002");
					orderService.processOrder(parameterOrderMap);
					// 创建Excel的sheet的一行  
				    HSSFRow row = sheet.createRow(rowIndex);  
				    rowIndex++;
				    
				    HSSFCell cell1 = row.createCell(0);  
				    cell1.setCellValue(""+m.get("orderNum"));  
				    
				    HSSFCell cell2 = row.createCell(1);  
				    cell2.setCellValue(m.get("proname")+"规格："+m.get("skuname")+"数量："+m.get("numsku")+"金额："+m.get("subtotal"));  
				    
				    HSSFCell cell3 = row.createCell(2);  
				    cell3.setCellValue(""+m.get("extraPay"));  
				    
				    HSSFCell cell4 = row.createCell(3);  
				    cell4.setCellValue(""+m.get("payAmount"));  
				    
				    HSSFCell cell5 = row.createCell(4);  
				    cell5.setCellValue(""+m.get("createTime")); 
				    
				    HSSFCell cell6 = row.createCell(5);  
				    cell6.setCellValue(""+m.get("userAddress")); 
				    
				    HSSFCell cell7 = row.createCell(6);  
				    cell7.setCellValue(""+m.get("deliveryTime"));  
				}
			}
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();  
	    wb.write(os);  
	    os.close();  
	    
	    response.setHeader("Content-disposition", "attachment; filename=delivery.xls"); 
	    response.getOutputStream().write(os.toByteArray());
	    response.getOutputStream().flush();
	}
	/**
	 * 查询充值列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws Throwable 
	 */
	@RequestMapping("/order/queryOrderDetailList")
	public void queryOrderDetailList(HttpServletRequest request,
			HttpServletResponse response) throws Throwable{
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String orderNo = request.getParameter("orderNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String orderStatus = request.getParameter("orderStatus");
		String currentPage = request.getParameter("currentPage");
		int pageSize = 10;
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		if(null != startTime && null != endTime && !"".equals(startTime) && !"".equals(endTime)) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd") ;
			startTime = sdf.format(sdf1.parse(startTime));
			endTime = sdf.format(sdf1.parse(endTime));
			parameterMap.put("startTime", startTime);
			parameterMap.put("endTime", endTime);
		}
		if(null != orderNo && !"".equals(orderNo)) {
			parameterMap.put("orderNo", orderNo.split(","));
		}
		parameterMap.put("orderStatus", orderStatus);
		parameterMap.put("startIndex", pageSize*(Integer.parseInt(currentPage)-1));
		parameterMap.put("pageSize", pageSize);
		
		Map recordsMap = orderService.queryOrderDetailList(parameterMap);
		
		Integer recordsCount = orderService.queryConsumeTotalPageSize(parameterMap);
		
		resultMap.put("recordsMap", recordsMap);
		int maxPage=recordsCount/pageSize;
		if(recordsCount%pageSize>0)
		{
			maxPage=maxPage+1;
		}
		resultMap.put("totalPage", maxPage);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject obj = JSONObject.fromObject(resultMap);
		writer.print(obj.toString());
		writer.flush();
		writer.close();
		
	}
	@RequestMapping("/order/processOrder")
	public void processOrder(HttpServletRequest request,
			HttpServletResponse response) throws Throwable{
		String orderNo = request.getParameter("orderNo");
		Map parameterMap = new HashMap();
		parameterMap.put("orderNo", orderNo);
		parameterMap.put("orderStatus", "003");
		orderService.processOrder(parameterMap);
	}
	
	/**
	 * 查询评论列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/order/queryEvaluateList")
	public void queryEvaluateList(HttpServletRequest request,
			   						HttpServletResponse response) throws IOException{
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String clientName = request.getParameter("clientName");
		String auditStatus = request.getParameter("auditStatus");
		String currentPage = request.getParameter("currentPage");
		int pageSize = 10;
		
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("clientName", clientName);
		parameterMap.put("auditStatus", auditStatus);
		parameterMap.put("startIndex", pageSize*(Integer.parseInt(currentPage)-1));
		parameterMap.put("pageSize", pageSize);
		
		List<Map> recordsList = orderService.queryEvaluateList(parameterMap);
		
		Integer recordsCount = orderService.queryEvaluateTotalPageSize(parameterMap);
		
		resultMap.put("recordsList", recordsList);
		int maxPage=recordsCount/pageSize;
	   	 if(recordsCount%pageSize>0)
	   	 {
	   		 maxPage=maxPage+1;
	   	 }
		resultMap.put("totalPage", maxPage);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject obj = JSONObject.fromObject(resultMap);
		writer.print(obj.toString());
		writer.flush();
		writer.close();
		
	}
	
	
	@RequestMapping("/evaluate/processEvaluate")
	public void processEvaluate(HttpServletRequest request,
			HttpServletResponse response) throws Throwable{
		String praiseId = request.getParameter("praiseId");
		orderService.processEvaluate(praiseId);
	}
	
	
	/**
	 * 查询广告列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/order/queryAdList")
	public void queryAdList(HttpServletRequest request,
			   						HttpServletResponse response) throws IOException{
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		//String clientName = request.getParameter("clientName");
		String currentPage = request.getParameter("currentPage");
		int pageSize = 10;
		
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		//parameterMap.put("clientName", clientName);
		parameterMap.put("startIndex", pageSize*(Integer.parseInt(currentPage)-1));
		parameterMap.put("pageSize", pageSize);
		
		List<Map> recordsList = orderService.queryAdList(parameterMap);
		
		Integer recordsCount = orderService.queryAdTotalPageSize(parameterMap);
		
		resultMap.put("recordsList", recordsList);
		int maxPage=recordsCount/pageSize;
	   	 if(recordsCount%pageSize>0)
	   	 {
	   		 maxPage=maxPage+1;
	   	 }
		resultMap.put("totalPage", maxPage);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject obj = JSONObject.fromObject(resultMap);
		writer.print(obj.toString());
		writer.flush();
		writer.close();
		
	}
	
	@RequestMapping("/order/processAd")
	public void processAd(HttpServletRequest request,
			HttpServletResponse response) throws Throwable{
		String AdId = request.getParameter("AdId");
		orderService.processAd(AdId);
	}
	

	
	@RequestMapping("/order/processAdN")
	public void processAdN(HttpServletRequest request,
			HttpServletResponse response) throws Throwable{
		String AdId = request.getParameter("AdId");
		orderService.processAdN(AdId);
	}

	
	@RequestMapping("/order/processAdY")
	public void processAdY(HttpServletRequest request,
			HttpServletResponse response) throws Throwable{
		String AdId = request.getParameter("AdId");
		String adStatus = request.getParameter("adstatus");
		String  AdStatus = "";
		if("Y".equals(adStatus)) {
			 AdStatus = "N";
		} else {
			 AdStatus = "Y";
		}
		Map parameterMap = new HashMap();
		parameterMap.put("AdId", AdId);
		parameterMap.put("AdStatus", AdStatus);
		orderService.processAdY(parameterMap);
	}
	
	@RequestMapping("/order/updateFreepost")
	public void updateFreepost(HttpServletRequest request,
			HttpServletResponse response) throws Throwable{
		String setId = request.getParameter("setId");
		String freepost = request.getParameter("freepost");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map map = new HashMap();
		map.put("setId", setId);
		map.put("freepost", freepost);
		orderService.updateFreepost(map);
		resultMap.put("freepost", freepost);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject obj = JSONObject.fromObject(resultMap);
		writer.print(obj.toString());
		writer.flush();
		writer.close();
	}
}
