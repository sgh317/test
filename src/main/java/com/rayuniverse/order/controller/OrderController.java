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
	
	/**
	 * ��ѯ��ֵ�б�
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
	 * ��ѯ�����б�
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
	* ��ӡ������ϸ�б�
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
		  
	    // ����Excel�Ĺ���sheet,��Ӧ��һ��excel�ĵ���tab  
	    HSSFSheet sheet = wb.createSheet("sheet1");  
	    {
	    	// ����Excel��sheet��һ��  
		    HSSFRow row = sheet.createRow(0);  
		   
		    HSSFCell cell1 = row.createCell(0);  
		    // ��Excel�ĵ�Ԫ��������ʽ�͸�ֵ  
		   
		    cell1.setCellValue("�������");  
		    
		    HSSFCell cell2 = row.createCell(1);  
		    // ��Excel�ĵ�Ԫ��������ʽ�͸�ֵ  
		   
		    cell2.setCellValue("��Ʒ����");  
		    
		    HSSFCell cell3 = row.createCell(2);  
		    // ��Excel�ĵ�Ԫ��������ʽ�͸�ֵ  
		    
		    cell3.setCellValue("�˷�");  
		    
		    HSSFCell cell4 = row.createCell(3);  
		    // ��Excel�ĵ�Ԫ��������ʽ�͸�ֵ  
		    
		    cell4.setCellValue("�ܽ��");  
		    
		    
		    HSSFCell cell5 = row.createCell(4);  
		    // ��Excel�ĵ�Ԫ��������ʽ�͸�ֵ  
		    
		    cell5.setCellValue("��������ʱ��"); 
		    
		    
		    HSSFCell cell6 = row.createCell(5);  
		    // ��Excel�ĵ�Ԫ��������ʽ�͸�ֵ  
		    
		    cell6.setCellValue("�ջ���ַ"); 
		    
		    
		    HSSFCell cell7 = row.createCell(6);  
		    // ��Excel�ĵ�Ԫ��������ʽ�͸�ֵ  
		    
		    cell7.setCellValue("����ʱ��");  
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
					// ����Excel��sheet��һ��  
				    HSSFRow row = sheet.createRow(rowIndex);  
				    rowIndex++;
				    
				    HSSFCell cell1 = row.createCell(0);  
				    cell1.setCellValue(""+m.get("orderNum"));  
				    
				    HSSFCell cell2 = row.createCell(1);  
				    cell2.setCellValue(m.get("proname")+"���"+m.get("skuname")+"������"+m.get("numsku")+"��"+m.get("subtotal"));  
				    
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
	 * ��ѯ��ֵ�б�
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
	 * ��ѯ�����б�
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
	 * ��ѯ����б�
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
