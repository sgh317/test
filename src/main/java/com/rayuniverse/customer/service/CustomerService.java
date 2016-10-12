package com.rayuniverse.customer.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.formula.eval.EvaluationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rayuniverse.customer.CustomerUtils;
import com.rayuniverse.customer.dao.CustomerDao;
import com.rayuniverse.domain.AreaDomain;
import com.rayuniverse.domain.CarTypeListDomain;
import com.rayuniverse.domain.ClientInfoParameterDomain;
import com.rayuniverse.domain.CustomerDetailDomain;
import com.rayuniverse.domain.NetDomain;
import com.rayuniverse.domain.OrderDetailDomain;
import com.rayuniverse.domain.OrderParameterDomain;
import com.rayuniverse.domain.OrderRiskInfo;
import com.rayuniverse.domain.OrderRiskParameter;
import com.rayuniverse.domain.PolicyCalDomain;
import com.rayuniverse.domain.PolicyDomain;
import com.rayuniverse.domain.ProductDomain;
import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.product.service.ProductService;
import com.rayuniverse.resell.dao.ReSellDao;
import com.rayuniverse.util.JSDDConfig;
import com.rayuniverse.util.JSDDUtils;
import com.rayuniverse.wchat.gw.asyn.WXTemplateMessageService;


@Service
public class CustomerService {
	
	String[] toArrary = JSDDConfig.TO_MAIL;
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	ReSellDao reSellDao;
	
	@Autowired
	WXTemplateMessageService wXTemplateMessageService;
	
	@Autowired
	ProductService productCalculateService;
	
	private Logger log = Logger.getLogger(CustomerService.class);

	@Transactional
	public Map<String, Object> importClientList(MultipartFile content) throws IllegalStateException, IOException {
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		File tempFile = new File("/mwbase/apptemp/tempFile");
		if(tempFile.exists()){
			tempFile.delete();
			tempFile.createNewFile();
		}	
		content.transferTo(tempFile);
		
		String fileName = content.getName();		
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
		
		String flag = "N";
		String message = "";
		FileInputStream fi=null;
		ByteArrayInputStream byteArrayInputStream=null;
		List<CustomerDetailDomain> customerList = new ArrayList<CustomerDetailDomain>();
		try {
			
			//一开始，就把流对象用字节数字保存起来  
			fi = new FileInputStream(tempFile);
			byte[] buf = org.apache.commons.io.IOUtils.toByteArray(fi);//execelIS为InputStream流  
			 /////////////////////////  
			 //在需要用到InputStream的地方再封装成InputStream  
			byteArrayInputStream = new ByteArrayInputStream(buf); 
			
			XSSFWorkbook workbook = new XSSFWorkbook(byteArrayInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int max = sheet.getLastRowNum();
			for(int i=1;i<=max;i++){
				//System.out.println(i);
				CustomerDetailDomain customerDetail = new CustomerDetailDomain();
				
				XSSFRow row = sheet.getRow(i);
				
				if(row.getCell(0)==null)
					break;
				
				customerDetail.setCarNo(row.getCell(0).getStringCellValue().toUpperCase());//车牌号
				customerDetail.setCarType(row.getCell(1).getStringCellValue().toUpperCase());//车型
				if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(2).getCellType()){
					double cellValue = row.getCell(2).getNumericCellValue();
					customerDetail.setFrameNo((new DecimalFormat("#").format(cellValue)).toString());
				}else{
					customerDetail.setFrameNo(row.getCell(2).getStringCellValue().toUpperCase());//车架号
				}
				
				if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(3).getCellType()){
					double cellValue = row.getCell(3).getNumericCellValue();
					customerDetail.setEngineNo((new DecimalFormat("#").format(cellValue)).toString());
				}else{
					customerDetail.setEngineNo(row.getCell(3).getStringCellValue().toUpperCase());//发动机号
				}
				customerDetail.setClientName(row.getCell(4).getStringCellValue());				
				if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(5).getCellType()){
					double cellValue = row.getCell(5).getNumericCellValue();
					customerDetail.setMobileNo((new DecimalFormat("#").format(cellValue)).toString());
				}else{
					customerDetail.setMobileNo(row.getCell(5).getStringCellValue());
				}
				
				customerDetail.setClientAddress(row.getCell(6).getStringCellValue());
				
				if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(7).getCellType()){
					double cellValue = row.getCell(7).getNumericCellValue();
					customerDetail.setClientIdNo((new DecimalFormat("#").format(cellValue)).toString());
				}else{
					customerDetail.setClientIdNo(row.getCell(7).getStringCellValue().toUpperCase());
				}	
				
				if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(8).getCellType()){
					
					customerDetail.setLoadDate(parseExcel(row.getCell(8)));
					
				}
				
				if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(9).getCellType()){//保险到期日
					
					customerDetail.setPolicyEnd(parseExcel(row.getCell(9)));//保险到期日
				}
				
				if(row.getCell(10)==null){
					customerDetail.setTransTimesBack3(0);
				}else if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(10).getCellType()){
					customerDetail.setTransTimesBack3((int) row.getCell(10).getNumericCellValue());//前年交强险赔付金额
				}else{
					if(StringUtils.isNotBlank(row.getCell(10).getStringCellValue()))
						customerDetail.setTransTimesBack3(Integer.parseInt(row.getCell(10).getStringCellValue()));//前年交强险出险次数
					else
						customerDetail.setTransTimesBack3(0);
				}
				
				if(row.getCell(11)==null){
					customerDetail.setTransAmountBack3("0.0");
				}else if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(11).getCellType()){
					double cellValue = row.getCell(11).getNumericCellValue();
					customerDetail.setTransAmountBack3((new DecimalFormat("#").format(cellValue)).toString());//前年交强险赔付金额
				}else{
					if(StringUtils.isNotBlank(row.getCell(11).getStringCellValue()))
						customerDetail.setTransAmountBack3(row.getCell(11).getStringCellValue());//前年交强险赔付金额
					else
						customerDetail.setTransAmountBack3("0");
				}
					
				
				if(row.getCell(12)==null){
					customerDetail.setTransTimesBack2(0);
				}else if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(12).getCellType()){
					customerDetail.setTransTimesBack2((int) row.getCell(12).getNumericCellValue());//去年交强险出险次数
				}else{
					if(StringUtils.isNotBlank(row.getCell(12).getStringCellValue()))
						customerDetail.setTransTimesBack2(Integer.parseInt(row.getCell(12).getStringCellValue()));//去年交强险出险次数
					else
						customerDetail.setTransTimesBack2(0);
				}
				
				if(row.getCell(13)==null){
					customerDetail.setTransAmountBack2("0.0");
				}else if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(13).getCellType()){
					double cellValue = row.getCell(13).getNumericCellValue();
					customerDetail.setTransAmountBack2((new DecimalFormat("#").format(cellValue)).toString());//去年交强险赔付金额
				}else{
					if(StringUtils.isNotBlank(row.getCell(13).getStringCellValue()))
						customerDetail.setTransAmountBack2(row.getCell(13).getStringCellValue());//去年交强险赔付金额
					else
						customerDetail.setTransAmountBack2("0");
					
				}
				
				if(row.getCell(14)==null){
					customerDetail.setTransTimes(0);
				}else if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(14).getCellType()){
					customerDetail.setTransTimes((int) row.getCell(14).getNumericCellValue());//上年年交强险出险次数
				}else{
					if(StringUtils.isNotBlank(row.getCell(14).getStringCellValue()))
						customerDetail.setTransTimes(Integer.parseInt(row.getCell(14).getStringCellValue()));//上年交强险出险次数
					else
						customerDetail.setTransTimes(0);
				}
				
				if(row.getCell(15)==null){
					customerDetail.setTransAmount("0.0");
				}else if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(15).getCellType()){
					double cellValue = row.getCell(15).getNumericCellValue();
					customerDetail.setTransAmount((new DecimalFormat("#").format(cellValue)).toString());//上年交强险赔付金额
				}else{
					if(StringUtils.isNotBlank(row.getCell(15).getStringCellValue()))
						customerDetail.setTransAmount(row.getCell(15).getStringCellValue());//上年交强险赔付金额
					else
						customerDetail.setTransAmount("0");
					
				}
					
				
				if(row.getCell(16)==null){
					customerDetail.setBusinessTimesBack3(0);
				}else if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(16).getCellType()){
					customerDetail.setBusinessTimesBack3((int) row.getCell(16).getNumericCellValue());//上年交强险出险次数
				}else{
					if(StringUtils.isNotBlank(row.getCell(16).getStringCellValue()))
						customerDetail.setBusinessTimesBack3(Integer.parseInt(row.getCell(16).getStringCellValue()));//上年交强险出险次数
					else
						customerDetail.setBusinessTimesBack3(0);
				}
				
				if(row.getCell(17)==null){
					customerDetail.setBusinessAmountBack3("0.0");
				}else if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(17).getCellType()){
					double cellValue = row.getCell(17).getNumericCellValue();
					customerDetail.setBusinessAmountBack3((new DecimalFormat("#").format(cellValue)).toString());//上年交强险赔付金额
				}else{
					if(StringUtils.isNotBlank(row.getCell(17).getStringCellValue()))
						customerDetail.setBusinessAmountBack3(row.getCell(17).getStringCellValue());//上年交强险赔付金额
					else
						customerDetail.setBusinessAmountBack3("0");
					
				}
				
				if(row.getCell(18)==null){
					customerDetail.setBusinessTimesBack2(0);
				}else if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(18).getCellType()){
					customerDetail.setBusinessTimesBack2((int) row.getCell(18).getNumericCellValue());//上年交强险出险次数
				}else{
					if(StringUtils.isNotBlank(row.getCell(18).getStringCellValue()))
						customerDetail.setBusinessTimesBack2(Integer.parseInt(row.getCell(18).getStringCellValue()));//上年交强险出险次数
					else
						customerDetail.setBusinessTimesBack2(0);
				}
				
				if(row.getCell(19)==null){
					customerDetail.setBusinessAmountBack2("0.0");
				}else if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(19).getCellType()){
					double cellValue = row.getCell(19).getNumericCellValue();
					customerDetail.setBusinessAmountBack2((new DecimalFormat("#").format(cellValue)).toString());//上年交强险赔付金额
				}else{
					if(StringUtils.isNotBlank(row.getCell(19).getStringCellValue()))
						customerDetail.setBusinessAmountBack2(row.getCell(19).getStringCellValue());//上年交强险赔付金额
					else
						customerDetail.setBusinessAmountBack2("0");					
				}
					
				
				if(row.getCell(20)==null){
					customerDetail.setBusinessTimes(0);
				}else if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(20).getCellType()){
					customerDetail.setBusinessTimes((int) row.getCell(20).getNumericCellValue());//上年交强险出险次数
				}else{
					if(StringUtils.isNotBlank(row.getCell(20).getStringCellValue()))
						customerDetail.setBusinessTimes(Integer.parseInt(row.getCell(20).getStringCellValue()));//上年交强险出险次数
					else
						customerDetail.setBusinessTimes(0);
				}
				
				if(row.getCell(21)==null){
					customerDetail.setBusinessAmount("0.0");
				}else if(XSSFCell.CELL_TYPE_NUMERIC == row.getCell(21).getCellType()){
					double cellValue = row.getCell(21).getNumericCellValue();
					customerDetail.setBusinessAmount((new DecimalFormat("#").format(cellValue)).toString());//上年交强险赔付金额
				}else{
					if(StringUtils.isNotBlank(row.getCell(21).getStringCellValue()))
						customerDetail.setBusinessAmount(row.getCell(21).getStringCellValue());//上年交强险赔付金额
					else
						customerDetail.setBusinessAmount("0");						
				}
				
				customerList.add(customerDetail);
			}
			
			resultMap = importClientByList(customerList);
			
			flag="Y";
			message = "导入成功";
		}catch (Exception e) {
			//System.out.println(e);
			flag = "N";
			message = "出现异常"+e.getMessage();
		}finally{
			try{
			fi.close();
			byteArrayInputStream.close();
			}catch(IOException e){
				;
			}
		}
		
		resultMap.put("flag", flag);
		resultMap.put("message", message);
		
		return resultMap;
	}
	
	private String parseExcel(Cell cell) {  
        String result = new String();  
        switch (cell.getCellType()) {  
        case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型  
            if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
                SimpleDateFormat sdf = null;  
                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
                        .getBuiltinFormat("h:mm")) {  
                    sdf = new SimpleDateFormat("HH:mm");  
                } else {// 日期  
                    sdf = new SimpleDateFormat("yyyy-MM-dd");  
                }  
                Date date = cell.getDateCellValue();  
                result = sdf.format(date);  
            } else if (cell.getCellStyle().getDataFormat() == 58) {  
                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                double value = cell.getNumericCellValue();  
                Date date = org.apache.poi.ss.usermodel.DateUtil  
                        .getJavaDate(value);  
                result = sdf.format(date);  
            } else {  
                double value = cell.getNumericCellValue();  
                CellStyle style = cell.getCellStyle();  
                DecimalFormat format = new DecimalFormat();  
                String temp = style.getDataFormatString();  
                // 单元格设置成常规  
                if (temp.equals("General")) {  
                    format.applyPattern("#");  
                }  
                result = format.format(value);  
            }  
            break;  
        case HSSFCell.CELL_TYPE_STRING:// String类型  
            result = cell.getRichStringCellValue().toString();  
            break;  
        case HSSFCell.CELL_TYPE_BLANK:  
            result = "";  
        default:  
            result = "";  
            break;  
        }  
        return result;  
    }  

	/**
	 * 导入列表
	 * @param customerList
	 * @return
	 */
	private Map<String, Object> importClientByList(
			List<CustomerDetailDomain> customerList) {
		
		int countSusscess = 0;
		int countUpdate = 0;
		int countFailed = 0;
		List<CustomerDetailDomain> failedCustomerList = new ArrayList<CustomerDetailDomain>();
		
		if(customerList!=null&&customerList.size()>0){
			for(int i=0;i<customerList.size();i++){
				CustomerDetailDomain clientInfo = customerList.get(i);
				
				try{
				int countClientInfo = customerDao.countClientInfo(clientInfo);
				
				if(countClientInfo==0){
					clientInfo.setClientIntegral(CustomerUtils.BASE_INTEGRAL);
					customerDao.insertClientInfo(clientInfo);
					countSusscess++;
				}else{
					countUpdate++;
					continue;
				}
				
				}catch(Throwable e){
					log.error("本单导入异常", e);
					failedCustomerList.add(customerList.get(i));
					countFailed ++;
				}
			}
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap.put("countSuccess",countSusscess);
		resultMap.put("countUpdate",countUpdate);
		resultMap.put("countFailed", countFailed);
		resultMap.put("failedCustomerList", failedCustomerList);
		return resultMap;
	}

	
	/**
	 * 绑定用户
	 * @param clientName
	 * @param carNo
	 * @param mobileNo
	 * @return
	 */
	public Map<String, Object> bindCustomerInfo(String clientName,
			String carNo, String mobileNo) {
		
		String openId = PlatformContext.getUmUserId();
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		CustomerDetailDomain clientInfo = new CustomerDetailDomain();
		//clientInfo.setClientName(clientName);
		clientInfo.setCarNo(carNo);
		clientInfo.setMobileNo(mobileNo);
		
		String flag = "N";
		String message = "验证失败";
		
		//查询是否有对应的已导入客户,并且未绑定
		clientInfo = customerDao.queryClientInfoByParameters(clientInfo);
		
		if(clientInfo!=null&&clientInfo.getFollowTime() == null&&"0".equals(clientInfo.getClientIntegral())){
			//还未领取，赠送积分，修改
			clientInfo.setClientIntegral(CustomerUtils.EFFECT_INTEGRAL);
			clientInfo.setOpenId(openId);
			
			//根据openId查询个数
			int countByOpenId = customerDao.countByOpenId(openId);
			if(countByOpenId>1){
				
				clientInfo = customerDao.queryClientInfoByOpenId(PlatformContext.getUmUserId());
				
				flag = "N";
				message = "本微信号已关联过账户";
				
			}else{
				customerDao.effectClient(clientInfo);
				
				String messageNotice = "\n恭喜您通过验证，15000积分已实时到账。积分最高抵扣30%商业车险保费，点击详情查看兑换规则哦！\n";
				
				
				try {
				//点对点推送消息
				HashMap msg = new HashMap();
				// 消息模板
				String templateId = JSDDConfig.JF_SUCCESS_MODE;
				// 点击后跳到哪里

				String toURL = JSDDConfig.JFGZ_URL;

				msg.put("touser", PlatformContext.getUmUserId());
				msg.put("template_id", templateId);
				msg.put("url", toURL);

				HashMap data = new HashMap();
				
				HashMap first = new HashMap();
				first.put("value", messageNotice);
				first.put("color", "#173177");
				data.put("first", first);

				HashMap keyword1 = new HashMap();
				keyword1.put("value", "+15,000");
				data.put("keyword1", keyword1);

				HashMap keyword2 = new HashMap();
				keyword2.put("value", "15,000");
				data.put("keyword2", keyword2);

				HashMap keyword3 = new HashMap();
				keyword3.put("value", "关注大地至尊宝并成功通过验证身份");
				data.put("keyword3", keyword3);

				HashMap remark = new HashMap();
				remark.put("value", "\n10积分可抵扣商业车险保费1元，最高可抵扣30%。积分明细规则，请点击查看下方菜单“积分中心-积分规则”。");
				data.put("remark", remark);

				msg.put("data", data);
		        
				wXTemplateMessageService.pushTemplateMessage(msg);
				} catch (Throwable e) {
					//異常信息記錄
					e.printStackTrace();
				}
				
				flag = "Y";
				message = "验证成功";
			}
			
		}else if(clientInfo!=null&&clientInfo.getFollowTime()!=null){
			//提示  该客户已认证，请使用认证的微信号登录
			
			if(StringUtils.equals(openId, clientInfo.getOpenId())){
				flag = "N";
				message = "您已认证，无需再次认证";
			}else{
				flag = "N";
				message = "该客户已认证，请使用认证的微信号登录";
			}
			
		}else{
			//无提示，绑定失败
			flag = "N";
			message = "客户绑定失败";
		}
		
		resultMap.put("flag", flag);
		resultMap.put("message", message);
		
		return resultMap;
	}

	/**
	 * 查询导入客户信息
	 * @param parameterMap
	 * @return
	 */
	public List<CustomerDetailDomain> queryImportClient(
			Map<String, Object> parameterMap) {
		
		List<CustomerDetailDomain> importClientList = new ArrayList<CustomerDetailDomain>();
		importClientList = customerDao.queryImportClient(parameterMap);
		return importClientList;
	}

	/**
	 * 根据openId查询客户详细信息
	 * @param openId
	 * @return
	 * @throws ParseException 
	 * @throws EvaluationException 
	 */
	public Map<String, Object> queryCustomerDetailInfo(String openId) throws ParseException {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
		CustomerDetailDomain customerDetailInfo =  customerDao.queryClientInfoByOpenId(openId);
		String endDateStr = customerDetailInfo.getPolicyEnd();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date endDate = new Date(Integer.parseInt(endDateStr.substring(0, 4))-1900,Integer.parseInt(endDateStr.substring(5, 7))-1,Integer.parseInt(endDateStr.substring(8, 10)));
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.DATE, 1);//取结束时间一天后
		Date beginDate = c.getTime();
		c.set(1900+new Date().getYear(), beginDate.getMonth(), beginDate.getDate());
		beginDate = c.getTime();
		
		resultMap.put("basicStartDate", formatDate(beginDate));
		
		String loadDate = customerDetailInfo.getLoadDate();
		if(StringUtils.isNotBlank(loadDate)){
			StringBuffer sb= new StringBuffer();
			sb.append(loadDate.substring(0, 4));
			sb.append(loadDate.substring(5, 7));
			sb.append(loadDate.substring(8, 10));
			
			customerDetailInfo.setLoadDate(sb.toString());
		}
			
		
		String flag = "Y";
		String message = "交单成功";
		
		if(customerDetailInfo==null){
			flag = "N";
			message = "您的微信号还没有关联";
		}else{
			
			resultMap.put("customerDetailInfo", customerDetailInfo);
			
			//查询有无未完成的订单
			//if 有 查询具体车型信息
			OrderDetailDomain orderInfo = customerDao.queryOrderByTypeAndNotFinished(openId,"01");//查询车险未完成的订单
			if(orderInfo!=null){				
				resultMap.put("orderInfo", orderInfo);
				
				Date effectDate = orderInfo.getEffectDate();
				resultMap.put("basicStartDate", formatDate(effectDate));
			}
				
			List<CarTypeListDomain> carTypeList = new ArrayList<CarTypeListDomain>();
			carTypeList = customerDao.queryCarTypeList(customerDetailInfo.getCarType());
			
			resultMap.put("carDetailList",carTypeList);
			
			flag = "Y";
			message = "查询成功";
		}
		
		resultMap.put("flag", flag);
		resultMap.put("message", message);
		
		return resultMap;
	}

	/**
	 * 
	 * @param effectDate
	 * @return
	 */
	private String formatDate(Date effectDate) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(effectDate.getYear()+1900);
		
		if(effectDate.getMonth()>8)
			sb.append(effectDate.getMonth()+1);
		else
			sb.append("0").append(effectDate.getMonth()+1);
		
		if(effectDate.getDate()>9)
			sb.append(effectDate.getDate());
		else
			sb.append("0").append(effectDate.getDate());
		
		return sb.toString();
	}

	/**
	 * 根据车型序号查询汽车详细信息
	 * @param carSeq
	 * @return
	 */
	public Map<String, Object> queryCarDetailInfoByCarSeq(String carSeq) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		CarTypeListDomain carDetailInfo = customerDao.queryCarDetailInfoByCarSeq(carSeq);
		
		resultMap.put("carDetailInfo", carDetailInfo);
		resultMap.put("flag", "Y");
		resultMap.put("message", "查询成功");
		
		return resultMap;
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
	public Map<String, Object> saveOrderInfo(String orderId, String carNo,String loadDate,
			 String carClassifyCode,String customerId,String carSeat,String buyPrice,String carSeq,String packageType,String effectDate) throws ParseException {
		
		String flag = "";
		String message = "";
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String openId = PlatformContext.getUmUserId();
		
		if(StringUtils.isNotBlank(loadDate)){
			StringBuffer sb = new StringBuffer();
			
			sb.append(loadDate.substring(0, 4));
			sb.append("-");
			sb.append(loadDate.substring(4, 6));
			sb.append("-");
			sb.append(loadDate.substring(6, 8));
			
			loadDate = sb.toString();
		}
		
		//TODO for test
//		if(StringUtils.isBlank(openId)){
//			openId = "oKhU-wY4SkCtCfy-yf-vrXhtMErg";
//		}
		//oKhU-wSC1vjhsZGG3nsSWcFLZ3FI
		
		if(StringUtils.isBlank(openId)){
			resultMap.put("flag", "N");
			resultMap.put("message", "保存失败：未获取到客户openId");
			
			return resultMap;
		}
		
		if(StringUtils.isNotBlank(orderId)){//有订单号，更新订单信息
			
			OrderDetailDomain orderInfo = customerDao.queryOrderByOrderId(orderId);
			
			if(StringUtils.equals("00", orderInfo.getOrderStatus())||(StringUtils.equals("01", orderInfo.getOrderStatus())&&StringUtils.equals("01", orderInfo.getOrderType()))){//未提交订单状态
				orderInfo.setClientId(customerId);
				orderInfo.setOrderStatus("00");
				orderInfo.setRiskType("01");
				orderInfo.setRiskName("车险");
				orderInfo.setCarNo(carNo);
				orderInfo.setCarClassifyType(carClassifyCode);
				orderInfo.setCarSeat(carSeat);
				orderInfo.setBuyPrice(buyPrice);
				orderInfo.setCarSeq(carSeq);
				
				Date eDate = new Date();
				if(StringUtils.isNotBlank(effectDate)){
					eDate.setYear(Integer.parseInt(effectDate.substring(0, 4))-1900);
					eDate.setMonth(Integer.parseInt(effectDate.substring(4, 6))-1);
					eDate.setDate(Integer.parseInt(effectDate.substring(6, 8)));
				}
				
				orderInfo.setEffectDate(eDate);				
				customerDao.updateOrderDetailInfo(orderInfo);
				
				//保存产品信息
				initAndSaveProduct(orderInfo,packageType);
				
				flag = "Y";
				message = "更新成功";
			}else{
				flag = "N";
				message = "您已提交出单信息，不能重复提交，谢谢";
			}
			
		}else{//无订单号，新建或查询客户原订单
			
			int countOrder = customerDao.countOrderByOpenId(openId,"01");
			
			if(countOrder>0){
				flag = "N";
				message = "您已提交出单信息，不能重复提交，谢谢";
				
				resultMap.put("flag", flag);
				resultMap.put("message", message);
				
				return resultMap;
			}
			
			OrderDetailDomain orderInfo = customerDao.queryOrderByTypeAndNotFinished(openId,"01");//查询车险未完成的订单
			if(orderInfo == null){
				orderInfo = new OrderDetailDomain();
				orderInfo.setOpenId(openId);
				orderInfo.setClientId(customerId);
				orderInfo.setOrderStatus("00");
				orderInfo.setRiskType("01");
				orderInfo.setRiskName("车险");
				orderInfo.setCarNo(carNo);
				orderInfo.setLoadDate(loadDate);
				orderInfo.setCarClassifyType(carClassifyCode);
				orderInfo.setCarSeat(carSeat);
				orderInfo.setBuyPrice(buyPrice);
				orderInfo.setCarSeq(carSeq);
				
				Date eDate = new Date();
				if(StringUtils.isNotBlank(effectDate)){
					eDate.setYear(Integer.parseInt(effectDate.substring(0, 4))-1900);
					eDate.setMonth(Integer.parseInt(effectDate.substring(4, 6))-1);
					eDate.setDate(Integer.parseInt(effectDate.substring(6, 8)));
				}				
				orderInfo.setEffectDate(eDate);		
				
				customerDao.insertOrderDetailInfo(orderInfo);
				
				orderInfo = customerDao.queryOrderByTypeAndNotFinished(openId,"01");//查询车险未完成的订单
				
				//保存产品信息
				initAndSaveProduct(orderInfo,packageType);
				
				flag = "Y";
				message = "保存成功";
			}else{
				if(StringUtils.equals("00", orderInfo.getOrderStatus())||(StringUtils.equals("01", orderInfo.getOrderStatus())&&StringUtils.equals("01", orderInfo.getOrderType()))){//未提交订单状态
					orderInfo.setClientId(customerId);
					orderInfo.setOrderStatus("00");
					orderInfo.setRiskType("01");
					orderInfo.setRiskName("车险");
					orderInfo.setCarNo(carNo);
					orderInfo.setLoadDate(loadDate);
					orderInfo.setCarClassifyType(carClassifyCode);
					orderInfo.setCarSeat(carSeat);
					orderInfo.setBuyPrice(buyPrice);
					orderInfo.setCarSeq(carSeq);
					
					Date eDate = new Date();
					if(StringUtils.isNotBlank(effectDate)){
						eDate.setYear(Integer.parseInt(effectDate.substring(0, 4))-1900);
						eDate.setMonth(Integer.parseInt(effectDate.substring(4, 6))-1);
						eDate.setDate(Integer.parseInt(effectDate.substring(6, 8)));
					}
					orderInfo.setEffectDate(eDate);		
					
					customerDao.updateOrderDetailInfo(orderInfo);
					
					//保存套餐信息
					initAndSaveProduct(orderInfo,packageType);
					
					flag = "Y";
					message = "更新成功";
				}else{
					flag = "N";
					message = "您已提交出单信息，不能重复提交，谢谢";
				}
			}
			
			
		}
		
		resultMap.put("flag", flag);
		resultMap.put("message", message);
		
		return resultMap;
	}

	/**
	 * 初始化保存数据，并保存信息
	 * @param orderInfo
	 * @param packageType
	 * @throws ParseException 
	 */
	private void initAndSaveProduct(OrderDetailDomain orderInfo,
			String packageType) throws ParseException {
		
		String orderId = orderInfo.getOrderId();
		String choose100001 = "N";
		String amount100001 = null;
		String choose100002 = "N";
		String amount100002 = null;
		String choose100003 = "N";
		String amount100003 = null;
		String buyforDriver = "Y";
		String buyforPassenger = "Y";
		String choose100004 = "N";
		String choose100005 = "N";
		String amount100005 = null;
		String isLocalGlass = "Y";
		String choose100006 = "N";
		String amount100006 = null;
		String choose100007 = "N";
		String amount100007 = null;
		String choose100008 = "N";
		String choose100009 = "N";
		String amount100009 = null;
		String choose100010 = "N";
		String amount100010 = null;
		String choose100011 = "N";
		String designatedDriver = "N";
		String designatedArea = "N";
		List<OrderRiskParameter> orderrp100008 = null;
		
		if(StringUtils.equals("top", packageType)){//豪华版本
			choose100011 = "Y";
			choose100002 = "Y";
			buyforDriver = "Y";
			buyforPassenger = "Y";
			choose100001 = "Y";
			amount100001 = "1000000";
			choose100003 = "Y";
			amount100003 = String.valueOf(50000*Integer.parseInt(orderInfo.getCarSeat()));
			choose100004 = "Y";
			choose100005 = "Y";
			isLocalGlass = "Y";
			amount100006 = "2000";
			choose100006 = "Y";
			choose100007 = "Y";
			choose100008 = "Y";
			
			orderrp100008 = new ArrayList<OrderRiskParameter>();
			OrderRiskParameter contain100001 = new OrderRiskParameter();
			contain100001.setParameterKey("contain100001");
			contain100001.setKeyValue("Y");
			orderrp100008.add(contain100001);
			
			OrderRiskParameter contain100002 = new OrderRiskParameter();
			contain100002.setParameterKey("contain100002");
			contain100002.setKeyValue("Y");
			orderrp100008.add(contain100002);
			
			OrderRiskParameter contain100003 = new OrderRiskParameter();
			contain100003.setParameterKey("contain100003");
			contain100003.setKeyValue("Y");
			orderrp100008.add(contain100003);
			
			OrderRiskParameter contain100004 = new OrderRiskParameter();
			contain100004.setParameterKey("contain100004");
			contain100004.setKeyValue("Y");
			orderrp100008.add(contain100004);
			
			OrderRiskParameter contain100006 = new OrderRiskParameter();
			contain100006.setParameterKey("contain100006");
			contain100006.setKeyValue("Y");
			orderrp100008.add(contain100006);
			
		}else if(StringUtils.equals("basic", packageType)){
			choose100011 = "Y";
			choose100002 = "Y";
			buyforDriver = "Y";
			buyforPassenger = "Y";
			choose100001 = "Y";
			amount100001 = "300000";
			choose100003 = "Y";
			amount100003 = String.valueOf(10000*Integer.parseInt(orderInfo.getCarSeat()));
			choose100008 = "Y";
			
			orderrp100008 = new ArrayList<OrderRiskParameter>();
			OrderRiskParameter contain100001 = new OrderRiskParameter();
			contain100001.setParameterKey("contain100001");
			contain100001.setKeyValue("Y");
			orderrp100008.add(contain100001);
			
			OrderRiskParameter contain100002 = new OrderRiskParameter();
			contain100002.setParameterKey("contain100002");
			contain100002.setKeyValue("Y");
			orderrp100008.add(contain100002);
			
			OrderRiskParameter contain100003 = new OrderRiskParameter();
			contain100003.setParameterKey("contain100003");
			contain100003.setKeyValue("Y");
			orderrp100008.add(contain100003);
			
		}else{
			
			choose100011 = "Y";
			choose100002 = "Y";
			buyforDriver = "Y";
			buyforPassenger = "Y";
			choose100001 = "Y";
			amount100001 = "500000";
			choose100003 = "Y";
			amount100003 = String.valueOf(20000*Integer.parseInt(orderInfo.getCarSeat()));
			choose100004 = "Y";
			choose100005 = "Y";
			isLocalGlass = "Y";
			choose100008 = "Y";
			
			orderrp100008 = new ArrayList<OrderRiskParameter>();
			OrderRiskParameter contain100001 = new OrderRiskParameter();
			contain100001.setParameterKey("contain100001");
			contain100001.setKeyValue("Y");
			orderrp100008.add(contain100001);
			
			OrderRiskParameter contain100002 = new OrderRiskParameter();
			contain100002.setParameterKey("contain100002");
			contain100002.setKeyValue("Y");
			orderrp100008.add(contain100002);
			
			OrderRiskParameter contain100003 = new OrderRiskParameter();
			contain100003.setParameterKey("contain100003");
			contain100003.setKeyValue("Y");
			orderrp100008.add(contain100003);
			
			OrderRiskParameter contain100004 = new OrderRiskParameter();
			contain100004.setParameterKey("contain100004");
			contain100004.setKeyValue("Y");
			orderrp100008.add(contain100004);
			
		}
		
		saveProductWithParameters(orderId, choose100001,
				amount100001, choose100002, amount100002,
				choose100003, amount100003, buyforDriver,
				buyforPassenger, choose100004, choose100005,
				amount100005, isLocalGlass, choose100006,
				amount100006, choose100007, amount100007,
				choose100008, choose100009, amount100009,
				choose100010, amount100010, choose100011,
				designatedDriver, designatedArea,orderrp100008,orderInfo.getCarSeq());
		
	}

	/**
	 * 根据保额 计算保费
	 * @param productCode
	 * @param amount
	 * @throws ParseException 
	 */
	public Map<String,Object> calProductWithAmount(String productCode, String amount) throws ParseException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String openId = PlatformContext.getUmUserId();
		OrderDetailDomain orderInfo = customerDao.queryOrderByTypeAndNotFinished(openId,"01");//查询车险未完成的订单
		
		PolicyDomain pd = new PolicyDomain();
		pd.setBuyPrice(orderInfo.getBuyPrice());
		pd.setLoadDate(orderInfo.getLoadDate());	
		pd.setCarType(orderInfo.getCarClassifyType());
		pd.setSeatNum(orderInfo.getCarSeat());
		pd.setLoadDate(orderInfo.getLoadDate());
		pd.setLoadYear(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"Y"));
		pd.setLoadMonth(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"M"));
		
		List<ProductDomain> productList = new ArrayList<ProductDomain>();
		//resultMap = productCalculateService.calculatePremium(pd , productCode);
		
		return resultMap;
	}

	/**
	 * 计算保费
	 * @param productCode
	 * @throws ParseException 
	 */
	public Map<String,Object> calProduct(String productCode) throws ParseException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String openId = PlatformContext.getUmUserId();
		OrderDetailDomain orderInfo = customerDao.queryOrderByTypeAndNotFinished(openId,"01");//查询车险未完成的订单
		
		PolicyDomain pd = new PolicyDomain();
		pd.setBuyPrice(orderInfo.getBuyPrice());
		pd.setLoadDate(orderInfo.getLoadDate());	
		pd.setCarType(orderInfo.getCarClassifyType());
		pd.setSeatNum(orderInfo.getCarSeat());
		pd.setLoadDate(orderInfo.getLoadDate());
		pd.setLoadYear(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"Y"));
		pd.setLoadMonth(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"M"));
		
		List<ProductDomain> productList = new ArrayList<ProductDomain>();
		
		pd.setProductList(productList);
		
		//resultMap = productCalculateService.calculatePremium(pd , productCode);
		
		return resultMap;
	}

	/**
	 * 
	 * @param productCode
	 * @param forDriver
	 * @param forPassenger
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, Object> calProductWithPassenger(String productCode,
			String forDriver, String forPassenger,String amount) throws ParseException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String openId = PlatformContext.getUmUserId();
		OrderDetailDomain orderInfo = customerDao.queryOrderByTypeAndNotFinished(openId,"01");//查询车险未完成的订单
		
		PolicyDomain pd = new PolicyDomain();
		pd.setBuyPrice(orderInfo.getBuyPrice());
		pd.setLoadDate(orderInfo.getLoadDate());	
		pd.setCarType(orderInfo.getCarClassifyType());
		pd.setSeatNum(orderInfo.getCarSeat());
		pd.setLoadDate(orderInfo.getLoadDate());
		pd.setLoadYear(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"Y"));
		pd.setLoadMonth(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"M"));
		
		List<ProductDomain> productList = new ArrayList<ProductDomain>();
		
		ProductDomain productInfo = new ProductDomain();
		
		productList.add(productInfo );
		pd.setProductList(productList);
		//resultMap = productCalculateService.calculatePremium(pd , productCode);
		
		return resultMap;
	}

	/**
	 * 
	 * @param productCode
	 * @param isLocalGlass
	 * @param amount
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, Object> calProductGlass(String productCode,
			String isLocalGlass, String amount) throws ParseException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String openId = PlatformContext.getUmUserId();
		OrderDetailDomain orderInfo = customerDao.queryOrderByTypeAndNotFinished(openId,"01");//查询车险未完成的订单
		
		PolicyDomain pd = new PolicyDomain();
		pd.setBuyPrice(orderInfo.getBuyPrice());
		pd.setLoadDate(orderInfo.getLoadDate());	
		pd.setCarType(orderInfo.getCarClassifyType());
		pd.setSeatNum(orderInfo.getCarSeat());
		pd.setLoadDate(orderInfo.getLoadDate());
		pd.setLoadYear(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"Y"));
		pd.setLoadMonth(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"M"));
		
		List<ProductDomain> productList = new ArrayList<ProductDomain>();
		
		ProductDomain productInfo = new ProductDomain();
		
		productList.add(productInfo );
		pd.setProductList(productList);
		//resultMap = productCalculateService.calculatePremium(pd , productCode);
		return resultMap;
	}
	
	/**
	 * 计算产品列表保费
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, Object> calProductAll(String choose100001,String amount100001,
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
            							 String contain100004,String contain100006
            							) throws ParseException{
		
		String openId = PlatformContext.getUmUserId();
		OrderDetailDomain orderInfo = customerDao.queryOrderByTypeAndNotFinished(openId,"01");//查询车险未完成的订单
		
		PolicyDomain pd = new PolicyDomain();
		pd.setBuyPrice(orderInfo.getBuyPrice());
		pd.setLoadDate(orderInfo.getLoadDate());	
		pd.setCarType(orderInfo.getCarClassifyType());
		pd.setSeatNum(orderInfo.getCarSeat());
		pd.setLoadDate(orderInfo.getLoadDate());
		pd.setLoadYear(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"Y"));
		pd.setLoadMonth(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"M"));
		int year = Integer.parseInt(pd.getLoadYear());
		
		if(StringUtils.equals(designatedDriver,"Y"))
			pd.setDesignatedDriver(true);
		else
			pd.setDesignatedDriver(false);
		
		if(StringUtils.equals(designatedArea,"Y"))
			pd.setDesignatedArea(true);
		else
			pd.setDesignatedArea(false);
		
		
		CustomerDetailDomain customerDomain =  customerDao.queryClientInfoByOpenId(openId);
		if(year>0){
			pd.setTimesBack1(customerDomain.getTransTimes());
			pd.setBusinessAmount(customerDomain.getBusinessAmount());
		}else{
			pd.setTimesBack1(1);
			pd.setBusinessBack(1);
		}
		
		if(year>1){
			pd.setTimesBack2(customerDomain.getTransTimesBack2());
			pd.setBusinessBack2(customerDomain.getBusinessTimes());
		}else{
			pd.setTimesBack2(1);
			pd.setBusinessBack2(1);
		}
		
		if(year>2){
			pd.setTimesBack3(customerDomain.getTransTimesBack3());
			pd.setBusinessBack3(customerDomain.getBusinessTimes());
		}else{
			pd.setTimesBack3(1);
			pd.setBusinessBack3(1);
		}
		pd.setAmountBack1(new BigDecimal(customerDomain.getTransAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		pd.setAmountBack2(new BigDecimal(customerDomain.getTransAmountBack2()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		pd.setAmountBack3(new BigDecimal(customerDomain.getTransAmountBack3()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		
		pd.setBusinessAmount(new BigDecimal(customerDomain.getBusinessAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		pd.setBusinessAmount2(new BigDecimal(customerDomain.getBusinessAmountBack2()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		pd.setBusinessAmount3(new BigDecimal(customerDomain.getBusinessAmountBack3()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		
		CarTypeListDomain carDetailInfo = customerDao.queryCarDetailInfoByCarSeq(orderInfo.getCarSeq());
		pd.setSweptVolumn(carDetailInfo.getSweptVolume());

		// 折旧价
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date load = sd.parse(orderInfo.getLoadDate());

		BigDecimal month = new BigDecimal(CustomerUtils.getMonth(load,
				orderInfo.getEffectDate()));

		BigDecimal buyValue = new BigDecimal(orderInfo.getBuyPrice());
		BigDecimal nowPrice = buyValue.subtract(buyValue.multiply(month).multiply(
				CustomerUtils.ZSXS_MONTH));

		double resultPrice = 0.0d;
		if (nowPrice.compareTo(buyValue.multiply(CustomerUtils.ZSJS_TOTAL)) == 1) {
			resultPrice = nowPrice.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
		} else {
			resultPrice = buyValue.multiply(CustomerUtils.ZSJS_TOTAL)
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		pd.setCurrentPrice(String.valueOf(resultPrice));
		pd.setFrameNo(customerDomain.getFrameNo());
		
		List<ProductDomain> pdProduct = new ArrayList<ProductDomain>();
		if(StringUtils.equals(choose100001,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100002,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100003,"Y")){
			ProductDomain product = new ProductDomain();
		}
		
		if(StringUtils.equals(choose100004,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100005,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100006,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100007,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100008,"Y")){
			ProductDomain product = new ProductDomain();
			
			List<OrderRiskParameter> productParameterList = new ArrayList<OrderRiskParameter>();
			OrderRiskParameter orderRiskPara = new OrderRiskParameter();
			
			if(StringUtils.equals(contain100001, "Y")){
				orderRiskPara = new OrderRiskParameter();
				orderRiskPara.setParameterKey("contain100001");
				orderRiskPara.setKeyValue("Y");
				productParameterList.add(orderRiskPara);
			}
			
			if(StringUtils.equals(contain100002, "Y")){
				orderRiskPara = new OrderRiskParameter();
				orderRiskPara.setParameterKey("contain100002");
				orderRiskPara.setKeyValue("Y");
				productParameterList.add(orderRiskPara);
			}
			
			if(StringUtils.equals(contain100003, "Y")){
				orderRiskPara = new OrderRiskParameter();
				orderRiskPara.setParameterKey("contain100003");
				orderRiskPara.setKeyValue("Y");
				productParameterList.add(orderRiskPara);
			}
			
			if(StringUtils.equals(contain100004, "Y")){
				orderRiskPara = new OrderRiskParameter();
				orderRiskPara.setParameterKey("contain100004");
				orderRiskPara.setKeyValue("Y");
				productParameterList.add(orderRiskPara);
			}
			
			if(StringUtils.equals(contain100006, "Y")){
				orderRiskPara = new OrderRiskParameter();
				orderRiskPara.setParameterKey("contain100006");
				orderRiskPara.setKeyValue("Y");
				productParameterList.add(orderRiskPara);
			}
			
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100009,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100010,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100011,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		ProductDomain pd99 = new ProductDomain();
		pdProduct.add(pd99);
		
		pd.setProductList(pdProduct);
		
		Map<String, Object> result =  new HashMap<String, Object>();
		
		calProductPremAndJF(new BigDecimal(result.get("syAmount").toString()), new BigDecimal(result.get("payAmount").toString()), orderInfo, customerDomain);
		
		result.put("payAmount", orderInfo.getPayAmount());
		result.put("dkAmount", orderInfo.getDiscountAmount());
		
		return result;
	}

	/**
	 * 查询当前用户未完成的订单及产品详情
	 * @return
	 * @throws Throwable 
	 */
	public Map<String, Object> loadProductInfoList() throws Throwable {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String flag = "";
		String message = "";
		
		String openId = PlatformContext.getUmUserId();
		
		ProductDomain productJQ = new ProductDomain();
		ProductDomain taxInfo = new ProductDomain();
		
		if(StringUtils.isBlank(openId)){
			flag = "N";
			message = "未获取永华id";
			
			resultMap.put("payAmount", "0.0");
			resultMap.put("syAmount", "0.0");
			resultMap.put("jqAmount", "0.0");
			resultMap.put("taxAmount", "0.0");
			resultMap.put("dkAmount", "0.0");
		}else{
			
			OrderDetailDomain orderInfo = customerDao.queryOrderByTypeAndNotFinished(openId,"01");//查询车险未完成的订单
			
			List<ProductDomain> productList = new ArrayList<ProductDomain>();
			productList = customerDao.queryProductListByOrderId(orderInfo.getOrderId());
			
			PolicyCalDomain policyCalInfo = new PolicyCalDomain();
			policyCalInfo.setCarType(orderInfo.getCarClassifyType());
			policyCalInfo.setSeatNum(orderInfo.getCarSeat());
			policyCalInfo.setLoadDate(orderInfo.getLoadDate());
			policyCalInfo.setBuyPrice(orderInfo.getBuyPrice());
			
			BigDecimal syAmount = new BigDecimal("0");
			
			if(productList!=null&&productList.size()>0)
			for(int i=0;i<productList.size();i++){
			}
			
			resultMap.put("product", policyCalInfo);
			resultMap.put("orderId", orderInfo.getOrderId());
			
			if(orderInfo!=null&&orderInfo.getPayAmount()!=null){
				resultMap.put("payAmount", orderInfo.getPayAmount());
				resultMap.put("syAmount", syAmount.toString());
				resultMap.put("dkAmount", new BigDecimal(orderInfo.getScore()).divide(new BigDecimal(10)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}else{
				resultMap.put("payAmount", "0.0");
				resultMap.put("syAmount", "0.0");
				resultMap.put("taxAmount", "0.0");
				resultMap.put("dkAmount", "0.0");
			}
			
			
			flag = "Y";
			message = "查询成功";
		}
		
		//检查是否已推送消息，如果未推送消息，推送之
				ClientInfoParameterDomain parameterHasPush = customerDao.queryClientParameterInfo(openId,"PUSH_HYX");
				
				if(parameterHasPush!=null&&"Y".equals(parameterHasPush.getParameterValue())){
					;
				}else{//未推送过消息，推送消息
					//点对点推送消息
					HashMap msg = new HashMap();
					// 消息模板
					String templateId = JSDDConfig.SERVICE_END_MODE;
					// 点击后跳到哪里

					String toURL = JSDDConfig.HYX_URL;
					
					//推送消息
					msg.put("touser", openId);//TODO
					msg.put("template_id", templateId);
					msg.put("url", toURL);

					HashMap data = new HashMap();

					HashMap first = new HashMap();
					first.put("value", "尊敬的客户您好，您已完成一次报价，可免费领取一份航意险");
					first.put("color", "#173177");
					data.put("first", first);

					HashMap keyword1 = new HashMap();
					keyword1.put("value", "100万元保额全年航空意外险");
					data.put("keyword1", keyword1);

					Date date = new Date();
					StringBuffer sb = new StringBuffer();
					sb.append(1900+date.getYear());
					sb.append("年");
					sb.append(date.getMonth()+1);
					sb.append("月");
					sb.append(date.getDate());
					sb.append("日");
					HashMap keyword2 = new HashMap();
					keyword2.put("value", sb.toString());
					data.put("keyword2", keyword2);

					HashMap remark = new HashMap();
					remark.put("value", "直接点击\"详情\"至领奖页面领取，或点击右下方\"精彩互动\",\"礼包领取\"进入领奖页面，点击下方航意险\"立即领取\"按钮。");
					data.put("remark", remark);

					msg.put("data", data);
			        
					wXTemplateMessageService.pushTemplateMessage(msg);
					
					customerDao.updateClientParameterInfo(openId, "PUSH_HYX", "Y");
				}
		
		resultMap.put("flag", flag);
		resultMap.put("message",message);
		return resultMap;
	}
	
	public void saveProductWithParameters(String orderId, String choose100001,
			String amount100001, String choose100002, String amount100002,
			String choose100003, String amount100003, String buyforDriver,
			String buyforPassenger, String choose100004, String choose100005,
			String amount100005, String isLocalGlass, String choose100006,
			String amount100006, String choose100007, String amount100007,
			String choose100008, String choose100009, String amount100009,
			String choose100010, String amount100010, String choose100011,
			String designatedDriver, String designatedArea,List<OrderRiskParameter> riskParameter100008,String carSeq) throws ParseException{
		String openId = PlatformContext.getUmUserId();
		
		//TODO for test
		/*if(StringUtils.isBlank(openId)){
			openId = "oKhU-wY4SkCtCfy-yf-vrXhtMErg";
		}*/
		//oKhU-wSC1vjhsZGG3nsSWcFLZ3FI
		
		OrderDetailDomain orderInfo = customerDao.queryOrderByOrderId(orderId);
		
		PolicyDomain pd = new PolicyDomain();
		pd.setBuyPrice(orderInfo.getBuyPrice());
		pd.setLoadDate(orderInfo.getLoadDate());	
		pd.setCarType(orderInfo.getCarClassifyType());
		pd.setSeatNum(orderInfo.getCarSeat());
		pd.setLoadDate(orderInfo.getLoadDate());
		pd.setLoadYear(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"Y"));
		pd.setLoadMonth(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"M"));
//		pd.setEffectDate(orderInfo.getEffectDate());
		int year = Integer.parseInt(pd.getLoadYear());
		
		if(StringUtils.equals(designatedDriver,"Y"))
			pd.setDesignatedDriver(true);
		else
			pd.setDesignatedDriver(false);
		
		if(StringUtils.equals(designatedArea,"Y"))
			pd.setDesignatedArea(true);
		else
			pd.setDesignatedArea(false);
		
		
		CustomerDetailDomain customerDomain =  customerDao.queryClientInfoByOpenId(openId);
		
		if(year>0){
			pd.setTimesBack1(customerDomain.getTransTimes());
			pd.setBusinessAmount(customerDomain.getBusinessAmount());
		}else{
			pd.setTimesBack1(1);
			pd.setBusinessBack(1);
		}
		
		if(year>1){
			pd.setTimesBack2(customerDomain.getTransTimesBack2());
			pd.setBusinessBack2(customerDomain.getBusinessTimes());
		}else{
			pd.setTimesBack2(1);
			pd.setBusinessBack2(1);
		}
		
		if(year>2){
			pd.setTimesBack3(customerDomain.getTransTimesBack3());
			pd.setBusinessBack3(customerDomain.getBusinessTimes());
		}else{
			pd.setTimesBack3(1);
			pd.setBusinessBack3(1);
		}
		pd.setAmountBack1(new BigDecimal(customerDomain.getTransAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		pd.setAmountBack2(new BigDecimal(customerDomain.getTransAmountBack2()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		pd.setAmountBack3(new BigDecimal(customerDomain.getTransAmountBack3()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		
		pd.setBusinessAmount(new BigDecimal(customerDomain.getBusinessAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		pd.setBusinessAmount2(new BigDecimal(customerDomain.getBusinessAmountBack2()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		pd.setBusinessAmount3(new BigDecimal(customerDomain.getBusinessAmountBack3()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		
		customerDomain.setCarSeq(carSeq);
		CarTypeListDomain carDetailInfo = customerDao.queryCarDetailInfoByCarSeq(customerDomain.getCarSeq());
		pd.setSweptVolumn(carDetailInfo.getSweptVolume());

		// 折旧价
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date load = sd.parse(orderInfo.getLoadDate());
		
		BigDecimal month = new BigDecimal(CustomerUtils.getMonth(load,
				orderInfo.getEffectDate()));

		BigDecimal buyValue = new BigDecimal(orderInfo.getBuyPrice());
		BigDecimal nowPrice = buyValue.subtract(buyValue.multiply(month).multiply(
				CustomerUtils.ZSXS_MONTH)).setScale(0, BigDecimal.ROUND_HALF_UP);

		double resultPrice = 0.0d;
		if (nowPrice.compareTo(buyValue.multiply(CustomerUtils.ZSJS_TOTAL)) == 1) {
			resultPrice = nowPrice.setScale(0, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
		} else {
			resultPrice = buyValue.multiply(CustomerUtils.ZSJS_TOTAL)
					.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		pd.setCurrentPrice(String.valueOf(resultPrice));
		pd.setFrameNo(customerDomain.getFrameNo());
		
		List<ProductDomain> pdProduct = new ArrayList<ProductDomain>();
		if(StringUtils.equals(choose100001,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100002,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100003,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100004,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100005,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100006,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100007,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100008,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100009,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100010,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100011,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		ProductDomain product99 = new ProductDomain();
		pdProduct.add(product99);
		
		pd.setProductList(pdProduct);
		
		Map<String, Object> result =  new HashMap<String, Object>();
		PolicyDomain resultPolicy = (PolicyDomain) result.get("policyInfo");
		
		//删除之前产品
		customerDao.deleteOrderRiskInfo(orderId);
		customerDao.deleteOrderRiskParameter(orderId);
		
		BigDecimal totalPrem = new BigDecimal(0);
		BigDecimal syPrem = new BigDecimal(0);
		BigDecimal jqPrem = new BigDecimal(0);
		BigDecimal taxPrem = new BigDecimal(0);
		//保存产品
		for(int i=0;i<resultPolicy.getProductList().size();i++){
			
			ProductDomain product = resultPolicy.getProductList().get(i);
			OrderRiskInfo orderRisk = new OrderRiskInfo();
			orderRisk.setOrderId(orderId);
			/*if(StringUtils.equals("100005", product.getProductCode())){//
				//车损险的基本保费
				orderRisk.setProductAmount("车损险基本保费");
			}*/
			
		}
		
		
		//计算可用积分抵扣
		calProductPremAndJF(syPrem,totalPrem,orderInfo,customerDomain);
		
		//更新订单
		customerDao.updateOrderPremInfo(orderInfo);
		//customerDao.updateClientIntegral(customerDomain);此处不更新积分，以免修改后不好计算原积分，提交后再更新
	}

	public Map<String, Object> saveProduct(String orderId, String choose100001,
			String amount100001, String choose100002, String amount100002,
			String choose100003, String amount100003, String buyforDriver,
			String buyforPassenger, String choose100004, String choose100005,
			String amount100005, String isLocalGlass, String choose100006,
			String amount100006, String choose100007, String amount100007,
			String choose100008, String choose100009, String amount100009,
			String choose100010, String amount100010, String choose100011,
			String designatedDriver, String designatedArea,String contain100001,String contain100002,
            String contain100003,String contain100004,String contain100006) throws Throwable {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String openId = PlatformContext.getUmUserId();
		OrderDetailDomain orderInfo = customerDao.queryOrderByOrderId(orderId);
		
		PolicyDomain pd = new PolicyDomain();
		pd.setBuyPrice(orderInfo.getBuyPrice());
		pd.setLoadDate(orderInfo.getLoadDate());	
		pd.setCarType(orderInfo.getCarClassifyType());
		pd.setSeatNum(orderInfo.getCarSeat());
		pd.setLoadDate(orderInfo.getLoadDate());
		pd.setLoadYear(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"Y"));
		pd.setLoadMonth(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"M"));
		int year = Integer.parseInt(pd.getLoadYear());
		
		if(StringUtils.equals(designatedDriver,"Y"))
			pd.setDesignatedDriver(true);
		else
			pd.setDesignatedDriver(false);
		
		if(StringUtils.equals(designatedArea,"Y"))
			pd.setDesignatedArea(true);
		else
			pd.setDesignatedArea(false);
		
		
		CustomerDetailDomain customerDomain =  customerDao.queryClientInfoByOpenId(openId);
		
		if(year>0){
			pd.setTimesBack1(customerDomain.getTransTimes());
			pd.setBusinessAmount(customerDomain.getBusinessAmount());
		}else{
			pd.setTimesBack1(1);
			pd.setBusinessBack(1);
		}
		
		if(year>1){
			pd.setTimesBack2(customerDomain.getTransTimesBack2());
			pd.setBusinessBack2(customerDomain.getBusinessTimes());
		}else{
			pd.setTimesBack2(1);
			pd.setBusinessBack2(1);
		}
		
		if(year>2){
			pd.setTimesBack3(customerDomain.getTransTimesBack3());
			pd.setBusinessBack3(customerDomain.getBusinessTimes());
		}else{
			pd.setTimesBack3(1);
			pd.setBusinessBack3(1);
		}
		pd.setAmountBack1(new BigDecimal(customerDomain.getTransAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		pd.setAmountBack2(new BigDecimal(customerDomain.getTransAmountBack2()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		pd.setAmountBack3(new BigDecimal(customerDomain.getTransAmountBack3()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		
		pd.setBusinessAmount(new BigDecimal(customerDomain.getBusinessAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		pd.setBusinessAmount2(new BigDecimal(customerDomain.getBusinessAmountBack2()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		pd.setBusinessAmount3(new BigDecimal(customerDomain.getBusinessAmountBack3()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		
		CarTypeListDomain carDetailInfo = customerDao.queryCarDetailInfoByCarSeq(orderInfo.getCarSeq());
		pd.setSweptVolumn(carDetailInfo.getSweptVolume());

		// 折旧价
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date load = sd.parse(orderInfo.getLoadDate());

		BigDecimal month = new BigDecimal(CustomerUtils.getMonth(load,
				orderInfo.getEffectDate()));

		BigDecimal buyValue = new BigDecimal(orderInfo.getBuyPrice());
		BigDecimal nowPrice = buyValue.subtract(buyValue.multiply(month).multiply(
				CustomerUtils.ZSXS_MONTH));

		double resultPrice = 0.0d;
		if (nowPrice.compareTo(buyValue.multiply(CustomerUtils.ZSJS_TOTAL)) == 1) {
			resultPrice = nowPrice.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
		} else {
			resultPrice = buyValue.multiply(CustomerUtils.ZSJS_TOTAL)
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		pd.setCurrentPrice(String.valueOf(resultPrice));
		pd.setFrameNo(customerDomain.getFrameNo());
		
		List<ProductDomain> pdProduct = new ArrayList<ProductDomain>();
		if(StringUtils.equals(choose100001,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100002,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100003,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100004,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100005,"Y")){
			ProductDomain product = new ProductDomain();
			
			
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100006,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100007,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100008,"Y")){
			ProductDomain product = new ProductDomain();
			
			List<OrderRiskParameter> productParameterList = new ArrayList<OrderRiskParameter>();
			OrderRiskParameter orderRiskPara = new OrderRiskParameter();
			
			if(StringUtils.equals(contain100001, "Y")){
				orderRiskPara = new OrderRiskParameter();
				orderRiskPara.setParameterKey("contain100001");
				orderRiskPara.setKeyValue("Y");
				productParameterList.add(orderRiskPara);
			}
			
			if(StringUtils.equals(contain100002, "Y")){
				orderRiskPara = new OrderRiskParameter();
				orderRiskPara.setParameterKey("contain100002");
				orderRiskPara.setKeyValue("Y");
				productParameterList.add(orderRiskPara);
			}
			
			if(StringUtils.equals(contain100003, "Y")){
				orderRiskPara = new OrderRiskParameter();
				orderRiskPara.setParameterKey("contain100003");
				orderRiskPara.setKeyValue("Y");
				productParameterList.add(orderRiskPara);
			}
			
			if(StringUtils.equals(contain100004, "Y")){
				orderRiskPara = new OrderRiskParameter();
				orderRiskPara.setParameterKey("contain100004");
				orderRiskPara.setKeyValue("Y");
				productParameterList.add(orderRiskPara);
			}
			
			if(StringUtils.equals(contain100006, "Y")){
				orderRiskPara = new OrderRiskParameter();
				orderRiskPara.setParameterKey("contain100006");
				orderRiskPara.setKeyValue("Y");
				productParameterList.add(orderRiskPara);
			}
			
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100009,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100010,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		if(StringUtils.equals(choose100011,"Y")){
			ProductDomain product = new ProductDomain();
			pdProduct.add(product);
		}
		
		ProductDomain pd99 = new ProductDomain();
		pdProduct.add(pd99);
		
		pd.setProductList(pdProduct);
		
		Map<String, Object> result =  new HashMap<String, Object>();
		PolicyDomain resultPolicy = (PolicyDomain) result.get("policyInfo");
		
		//删除之前产品
		customerDao.deleteOrderRiskInfo(orderId);
		customerDao.deleteOrderRiskParameter(orderId);
		
		BigDecimal totalPrem = new BigDecimal(0);
		BigDecimal syPrem = new BigDecimal(0);
		BigDecimal jqPrem = new BigDecimal(0);
		BigDecimal taxPrem = new BigDecimal(0);
		
		//保存产品
		for(int i=0;i<resultPolicy.getProductList().size();i++){
			
			ProductDomain product = resultPolicy.getProductList().get(i);
			OrderRiskInfo orderRisk = new OrderRiskInfo();
			orderRisk.setOrderId(orderId);
			customerDao.insertOrderRiskInfo(orderRisk);
			
				
		}
		
		
		//计算可用积分抵扣
		calProductPremAndJF(syPrem,totalPrem ,orderInfo,customerDomain);
		
		//更新订单
		customerDao.updateOrderPremInfo(orderInfo);
		//customerDao.updateClientIntegral(customerDomain);此处不更新积分，以免修改后不好计算原积分，提交后再更新
		
		//删除影像信息
		customerDao.deleteImageInfo(orderId);
		
		String flag = "Y";
		String message = "保存成功";
		
		resultMap.put("flag", flag);
		resultMap.put("message", message);
		return resultMap;
	}

	/**
	 * 根据交强险，计算汽车需交保费，抵扣积分
	 * @param productJQ
	 * @param totalPrem
	 * @param orderInfo
	 * @param customerDomain
	 * 
	 *  规则如下：
	 *  1.	10积分可抵扣保费1元；
		2.	用户必须同时投保交强险和商业险，不能单独投保其中之一。同时，积分仅可用于抵扣商业险保费；
		3.	当用户投保的商业险金额不小于2000元时，才可使用积分抵扣保费；
		4.	积分最多可抵扣商业险金额的35%
	 */
	private void calProductPremAndJF(BigDecimal syPrem,BigDecimal totalPrem, OrderDetailDomain orderInfo, CustomerDetailDomain customerDomain) {
		
		String jfTotal = customerDomain.getClientIntegral();
		
		BigDecimal businessPremTotal = syPrem;
		
		BigDecimal businessPremMaxJF = businessPremTotal.multiply(new BigDecimal("0.35")).setScale(1,BigDecimal.ROUND_DOWN);//积分最多可抵扣商业险金额的35%,取1位小数，向下取整
		BigDecimal jfMaxPrem = new BigDecimal(jfTotal).divide(new BigDecimal(10));//10积分可抵扣保费1元；
		
		if(businessPremTotal.compareTo(new BigDecimal("2000"))==-1){//当用户投保的商业险金额不小于2000元时，才可使用积分抵扣保费；
			orderInfo.setDiscountAmount("0");
			orderInfo.setPayAmount(totalPrem.toString());
			orderInfo.setSumAmount(totalPrem.toString());
			orderInfo.setScore("0");
		}else{
			
			if(businessPremMaxJF.compareTo(jfMaxPrem)==-1){//保费计算积分可抵扣值小于，积分余额可抵扣商业值
				orderInfo.setDiscountAmount(businessPremMaxJF.toString());
				orderInfo.setPayAmount(totalPrem.subtract(businessPremMaxJF).toString());
				orderInfo.setSumAmount(totalPrem.toString());
				orderInfo.setScore(businessPremMaxJF.multiply(new BigDecimal(10)).toString());
				customerDomain.setClientIntegral(new BigDecimal(jfTotal).subtract(businessPremMaxJF.multiply(new BigDecimal(10))).toString());
			}else{
				orderInfo.setDiscountAmount(jfMaxPrem.toString());
				orderInfo.setPayAmount(totalPrem.subtract(jfMaxPrem).toString());
				orderInfo.setSumAmount(totalPrem.toString());
				orderInfo.setScore(jfMaxPrem.multiply(new BigDecimal(10)).toString());
				customerDomain.setClientIntegral("0");
			}
			
		}
		
		
	}

	/**
	 * 获取信息
	 * @return
	 */
	public Map<String, Object> loadOrderReviewInfo() {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String flag = "";
		String message = "";
		String openId = PlatformContext.getUmUserId();
		
		try{
			OrderDetailDomain orderInfo = customerDao.queryOrderByTypeAndNotFinished(openId,"01");//查询车险未完成的订单
			
			List<ProductDomain> productList = new ArrayList<ProductDomain>();
			productList = customerDao.queryProductListByOrderId(orderInfo.getOrderId());
			
			ProductDomain productJQ = new ProductDomain();
			ProductDomain taxInfo = new ProductDomain();
			if(StringUtils.isBlank(orderInfo.getScore()))
				orderInfo.setScore("0");
			
			if(orderInfo!=null&&orderInfo.getPayAmount()!=null){
				resultMap.put("payAmount", orderInfo.getPayAmount());
				resultMap.put("dkAmount", new BigDecimal(orderInfo.getScore()).divide(new BigDecimal(10)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}else{
				resultMap.put("payAmount", "0.0");
				resultMap.put("syAmount", "0.0");
				resultMap.put("jqAmount", "0.0");
				resultMap.put("taxAmount", "0.0");
				resultMap.put("dkAmount", "0.0");
			}
		
			CustomerDetailDomain customerDetailInfo =  customerDao.queryClientInfoByOpenId(openId);
			
			ClientInfoParameterDomain clientInfoParameter = customerDao.queryClientParameterInfo(openId, "FOLLOW_HYX");
			String isFollowHYX = clientInfoParameter==null?"N":clientInfoParameter.getParameterValue();
		
			resultMap.put("customerInfo", customerDetailInfo);
			resultMap.put("orderInfo", orderInfo);
			resultMap.put("isFollowHYX", isFollowHYX);
			
			flag = "Y";
			message = "查询成功";
		}catch(Throwable e){
			log.error("加载费率数据出现异常",e);
			flag = "N";
			message = "查询失败";
		}finally{
			resultMap.put("flag", flag);
			resultMap.put("message", message);
		}
		
		return resultMap;
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, Object> refreshAreaInfo(String cityCode) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
		String flag = "N";
		String message = "查询失败";
		
		try{
			
			List<AreaDomain> areaList = customerDao.queryAreaByCity(cityCode);
			
			resultMap.put("areaList", areaList);
			
			flag = "Y";
			message = "查询成功";
		}catch(Throwable t){
			flag = "N";
			message = "查询失败";
		}finally{
			resultMap.put("flag", flag);
			resultMap.put("message", message);
		}
		
		
		return resultMap;
	}

	/**
	 * 
	 * @param cityCode
	 * @return
	 */
	public Map<String, Object> refreshNetInfo(String areaCode) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		String flag = "N";
		String message = "查询失败";
		
		try{
			List<NetDomain> netList = customerDao.queryNetByArea(areaCode);
			
			resultMap.put("netList", netList);
			
			flag = "Y";
			message = "查询成功";
		}catch(Throwable t){
			flag = "N";
			message = "查询失败";
		}finally{
			resultMap.put("flag", flag);
			resultMap.put("message", message);
		}
		
		return resultMap;
	}

	/**
	 * 更新订单信息
	 * @param orderId
	 * @param city
	 * @param area
	 * @param net
	 * @return
	 * @throws Throwable 
	 */
	@Transactional
	public Map<String, Object> submitOrderInfo(String orderId,String city,
            								   String area,String net,
            								   String orderType,String visitDate02,String visitHour02,
            								   String clientAddress01,
            								   String visitDate03,
            								   String visitHour03,
            								   String clientAddress03,
            								   String applicantName,
            								   String applicantNo) throws Throwable {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String flag = "";
		String message = "";
		
		String openId = PlatformContext.getUmUserId();
		OrderDetailDomain orderInfo = customerDao.queryOrderByTypeAndNotFinished(openId,"01");//查询车险未完成的订单
		String scroe = orderInfo.getScore();
		String payAmount = orderInfo.getPayAmount();//需交保费合计
		
		//微信支付
		CustomerDetailDomain customerDetailInfo = customerDao.queryClientInfoByOpenId(openId); 
		if(StringUtils.equals(orderType, "01")){//微信支付
			
			//保费再减50元
			payAmount = new BigDecimal(payAmount).subtract(new BigDecimal(50)).toString();
			
			OrderParameterDomain orderParameter = new OrderParameterDomain();
			orderParameter.setOrderId(orderId);
			orderParameter.setParameterKey("CLIENT_ADDRESS");
			orderParameter.setParameterValue(clientAddress01);
			customerDao.insertOrderParameter(orderParameter);
			
			Map<String,Object> updateMap = new HashMap<String,Object>();
			updateMap.put("openId", openId);
			updateMap.put("clientName", applicantName);
			updateMap.put("clientIdNo", applicantNo);
			updateMap.put("clientIntegral", new BigDecimal(customerDetailInfo.getClientIntegral()).toString());
			
			flag = "Y";
			message = "保存成功";
			
		}else {
			Map<String,Object> updateMap = new HashMap<String,Object>();
			updateMap.put("openId", openId);
			updateMap.put("clientName", applicantName);
			updateMap.put("clientIdNo", applicantNo);
			updateMap.put("clientIntegral", new BigDecimal(customerDetailInfo.getClientIntegral()).subtract(new BigDecimal(scroe)).toString());
			
			customerDao.updateIntegral(updateMap);
			
			orderInfo.setPayAmount(payAmount);
			orderInfo.setOrderStatus("01");//到柜面支付已提交
			orderInfo.setOrderType(orderType);
			customerDao.submitOrder(orderInfo);
			
			if(StringUtils.equals(orderType, "02")){//柜台支付
				//转微信支付
				OrderParameterDomain orderParameter = new OrderParameterDomain();
				orderParameter.setOrderId(orderId);
				orderParameter.setParameterKey("NET_CODE");
				orderParameter.setParameterValue(net);
				customerDao.insertOrderParameter(orderParameter);
				
				orderParameter = new OrderParameterDomain();
				orderParameter.setOrderId(orderId);
				orderParameter.setParameterKey("VISIT_DATE");
				orderParameter.setParameterValue(visitDate02);
				customerDao.insertOrderParameter(orderParameter);
				
				orderParameter = new OrderParameterDomain();
				orderParameter.setOrderId(orderId);
				orderParameter.setParameterKey("VISIT_HOUR");
				orderParameter.setParameterValue(visitHour02);
				customerDao.insertOrderParameter(orderParameter);
				
				try{
				//推送消息
				//点对点推送消息
				HashMap msg = new HashMap();
				// 消息模板
				String templateId = JSDDConfig.YY_ORDER_MODE;
				// 点击后跳到哪里

				String toURL = JSDDConfig.FOLLOW_URL;

				msg.put("touser", JSDDConfig.SERVICE_OPEN_ID);//TODO
				msg.put("template_id", templateId);
				msg.put("url", toURL);

				HashMap data = new HashMap();

				HashMap first = new HashMap();
				first.put("value", "客户订单提交，选择支付方式为：到网点支付。");
				first.put("color", "#173177");
				data.put("first", first);

				HashMap keyword1 = new HashMap();
				keyword1.put("value", orderId);
				data.put("keyword1", keyword1);

				StringBuffer sb = new StringBuffer();
				sb.append(visitDate02.substring(0, 4));
				sb.append("年");
				sb.append(visitDate02.substring(4, 6));
				sb.append("月");
				sb.append(visitDate02.substring(6, 8));
				sb.append("日");
				sb.append(visitHour02).append("时至");
				sb.append(Integer.parseInt(visitHour02)+2).append("时");
				
				HashMap keyword2 = new HashMap();
				keyword2.put("value", sb.toString());
				data.put("keyword2", keyword2);

				Map<String,Object> netMap = reSellDao.queryNetFullInfo(net);
				
				HashMap keyword3 = new HashMap();
				keyword3.put("value", netMap.get("net_name"));
				data.put("keyword3", keyword3);

				HashMap keyword4 = new HashMap();
				keyword4.put("value", "4009-500-888");
				data.put("keyword4", keyword4);
				
				HashMap keyword5 = new HashMap();
				keyword5.put("value", netMap.get("net_address"));
				data.put("keyword5", keyword5);

				HashMap remark = new HashMap();
				remark.put("value", "请安排出单及接待");
				data.put("remark", remark);

				msg.put("data", data);
		        
				wXTemplateMessageService.pushTemplateMessage(msg);
				
				//发送邮件
				JSDDUtils.sendMail("订单"+orderId+"选择到柜台交费，请跟进", "订单"+orderId+"已下单，客户选择选择到柜台交费，请跟进", toArrary);
				}catch(Throwable t){
					log.error("推送预约消息异常", t);
				}
				
				//航医险订单
				//givenHYXOrder(orderInfo,customerDetailInfo);
				
				flag = "Y";
				message = "报价成功";
				
			}else{//上门收取已提交
				OrderParameterDomain orderParameter = new OrderParameterDomain();
				orderParameter.setOrderId(orderId);
				orderParameter.setParameterKey("CLIENT_ADDRESS");
				orderParameter.setParameterValue(clientAddress03);
				customerDao.insertOrderParameter(orderParameter);
				
				orderParameter = new OrderParameterDomain();
				orderParameter.setOrderId(orderId);
				orderParameter.setParameterKey("VISIT_DATE");
				orderParameter.setParameterValue(visitDate03);
				customerDao.insertOrderParameter(orderParameter);
				
				orderParameter = new OrderParameterDomain();
				orderParameter.setOrderId(orderId);
				orderParameter.setParameterKey("VISIT_HOUR");
				orderParameter.setParameterValue(visitHour03);
				customerDao.insertOrderParameter(orderParameter);
				
				//推送消息
				//点对点推送消息
				HashMap msg = new HashMap();
				// 消息模板
				String templateId = JSDDConfig.YY_ORDER_MODE;
				// 点击后跳到哪里

				String toURL = JSDDConfig.FOLLOW_URL;
				
				//推送消息
				msg.put("touser", JSDDConfig.SERVICE_OPEN_ID);//TODO
				msg.put("template_id", templateId);
				msg.put("url", toURL);

				HashMap data = new HashMap();

				HashMap first = new HashMap();
				first.put("value", "客户订单提交，选择支付方式为：上门收费。");
				first.put("color", "#173177");
				data.put("first", first);

				HashMap keyword1 = new HashMap();
				keyword1.put("value", orderId);
				data.put("keyword1", keyword1);

				HashMap keyword2 = new HashMap();
				keyword2.put("value", visitDate03+" "+visitHour03);
				data.put("keyword2", keyword2);

				HashMap keyword3 = new HashMap();
				keyword3.put("value", "先生/女士");
				data.put("keyword3", keyword3);

				HashMap keyword4 = new HashMap();
				keyword4.put("value", customerDetailInfo.getMobileNo());
				data.put("keyword4", keyword4);
				
				HashMap keyword5 = new HashMap();
				keyword4.put("value", "请安排上门收费，地址为:"+clientAddress03);
				data.put("keyword5", keyword5);

				HashMap remark = new HashMap();
				remark.put("value", "备注：客户姓名为"+customerDetailInfo.getClientName()+",总计费用:"+orderInfo.getSumAmount()+",抵扣积分:"+orderInfo.getScore()+",实际支付："+orderInfo.getPayAmount());
				data.put("remark", remark);
				
				msg.put("data", data);
		        
				wXTemplateMessageService.pushTemplateMessage(msg);
				
				//发送邮件
				JSDDUtils.sendMail("订单"+orderId+"选择上门收费，请跟进", "订单"+orderId+"已下单，客户选择选择上门收费，请跟进", toArrary);
				
				//航医险订单
				//givenHYXOrder(orderInfo,customerDetailInfo);
				
				flag = "Y";
				message = "报价成功";
			}
			
		}
		
		
		resultMap.put("flag", flag);
		resultMap.put("message", message);
		
		return resultMap;
	}
	
	
	/**
	 * 下发航意险订单
	 * @param orderInfo
	 * @param customerDetailInfo
	 * @throws Throwable 
	 */
	private void givenHYXOrder(OrderDetailDomain orderInfo,
			CustomerDetailDomain customerDetailInfo) throws Throwable {
		
		OrderDetailDomain hyxOrder = new OrderDetailDomain();
		hyxOrder.setOpenId(PlatformContext.getUmUserId());
		hyxOrder.setClientIdNo(orderInfo.getClientIdNo());
		hyxOrder.setClientName(orderInfo.getClientName());
		hyxOrder.setOrderStatus("02");
		hyxOrder.setOrderType("01");
		hyxOrder.setPayAmount("0.0");
		hyxOrder.setSumAmount("0.0");
		hyxOrder.setProductType("02");
		hyxOrder.setRiskName("航意险");
		
		customerDao.insertHYXOrder(hyxOrder);
		
		//发送邮件
		JSDDUtils.sendMail("客户领取航意险订单:"+hyxOrder.getOrderId()+"请安排后续流程", "关联的车险报价订单为"+orderInfo.getOrderId(), toArrary);
		
	}

	/**
	 * 根据套餐计算费率
	 * @param orderId
	 * @param loadDate
	 * @param carClassifyCode
	 * @param carSeat
	 * @param buyPrice
	 * @param packageType
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, Object> callPackagePrem(String orderId, String loadDate,
			String carClassifyCode, String carSeat, String buyPrice,
			String packageType,String carSeq,String effectDate) throws ParseException {
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String openId = PlatformContext.getUmUserId();
		/*if(StringUtils.isBlank(openId)){
			openId = "oKhU-wY4SkCtCfy-yf-vrXhtMErg";//TODO fortest 
		}*/
		//oKhU-wSC1vjhsZGG3nsSWcFLZ3FI
		
		OrderDetailDomain orderInfo = null;
		if(StringUtils.isNotBlank(orderId)){
			orderInfo = customerDao.queryOrderByOrderId(orderId);
		}else{
			orderInfo = customerDao.queryOrderByTypeAndNotFinished(openId, "01");
			
			if(orderInfo == null ) orderInfo = new OrderDetailDomain();
		}
		
		CustomerDetailDomain cdi = customerDao.queryClientInfoByOpenId(openId);
		cdi.setCarSeq(carSeq);
		CustomerDetailDomain cdNormal = cdi.clone();
		CustomerDetailDomain cdTop = cdi.clone();
		
		
		if(StringUtils.isNotBlank(loadDate)){
			StringBuffer sb = new StringBuffer();
			
			sb.append(loadDate.substring(0, 4));
			sb.append("-");
			sb.append(loadDate.substring(4, 6));
			sb.append("-");
			sb.append(loadDate.substring(6, 8));
			
			loadDate = sb.toString();
		}
		orderInfo.setLoadDate(loadDate);
		orderInfo.setCarClassifyType(carClassifyCode);
		orderInfo.setCarSeat(carSeat);
		orderInfo.setBuyPrice(buyPrice);
		Date effect = new Date();
		
		if(StringUtils.isBlank(effectDate)){
			
			String endDateStr = cdi.getPolicyEnd();
			Date endDate = new Date(Integer.parseInt(endDateStr.substring(0, 4))-1900,Integer.parseInt(endDateStr.substring(5, 7))-1,Integer.parseInt(endDateStr.substring(8, 10)));
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, 1);//取结束时间一天后
			
			Calendar currentDate = Calendar.getInstance();;
			while(currentDate.compareTo(c)>1)
				c.add(Calendar.YEAR, 1);
			
			Date beginDate = c.getTime();
			effectDate = formatDate(beginDate);
					
		}
			 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		effect = sdf.parse(effectDate);
		orderInfo.setEffectDate(effect);
		
		PolicyDomain pdBasic = initPolicyWithPackageType(orderInfo,cdi,"basic");
		PolicyDomain pdNormal = initPolicyWithPackageType(orderInfo,cdNormal,"normal");
		PolicyDomain pdTop = initPolicyWithPackageType(orderInfo,cdTop,"top");
		Map<String, Object> resultBasic =  new HashMap<String, Object>();
		Map<String, Object> resultNormal =  new HashMap<String, Object>();
		Map<String, Object> resultTop =  new HashMap<String, Object>();
		
		String basicPayAmount = (String) resultBasic.get("payAmount");
		String basicJQAmount = (String) resultBasic.get("jqAmount");
		String basicSYAmount = (String) resultBasic.get("syAmount");
		pdBasic = (PolicyDomain) resultBasic.get("policyInfo");
		
		String normalPayAmount = (String) resultNormal.get("payAmount");
		String normalJQAmount = (String) resultNormal.get("jqAmount");
		String normalSYAmount = (String) resultNormal.get("syAmount");
		pdNormal = (PolicyDomain) resultBasic.get("policyInfo");
		
		String topPayAmount = (String) resultTop.get("payAmount");
		String topJQAmount = (String) resultTop.get("jqAmount");
		String topSYAmount = (String) resultTop.get("syAmount");
		pdTop = (PolicyDomain) resultBasic.get("policyInfo");
		
		String clientJf = cdi.getClientIntegral();
		
		calProductPremAndJF(new BigDecimal(basicSYAmount), new BigDecimal(basicPayAmount), orderInfo, cdi);
		resultMap.put("basicPremAll", new BigDecimal(orderInfo.getSumAmount()).setScale(0, BigDecimal.ROUND_HALF_UP));
		resultMap.put("basicPremDiscount", new BigDecimal(orderInfo.getDiscountAmount()).setScale(0, BigDecimal.ROUND_HALF_UP));
		resultMap.put("basicPremPay",new BigDecimal(orderInfo.getPayAmount()).setScale(0, BigDecimal.ROUND_HALF_UP));
		
		
		OrderDetailDomain orderNew = orderInfo.clone();
		calProductPremAndJF(new BigDecimal(normalSYAmount), new BigDecimal(normalPayAmount), orderNew, cdNormal);
		resultMap.put("normalPremAll", new BigDecimal(orderNew.getSumAmount()).setScale(0, BigDecimal.ROUND_HALF_UP));
		resultMap.put("normalPremDiscount",new BigDecimal(orderNew.getDiscountAmount()).setScale(0, BigDecimal.ROUND_HALF_UP));
		resultMap.put("normalPremPay",new BigDecimal(orderNew.getPayAmount()).setScale(0, BigDecimal.ROUND_HALF_UP));
		
		OrderDetailDomain orderTop = orderInfo.clone();
		calProductPremAndJF(new BigDecimal(topSYAmount), new BigDecimal(topPayAmount), orderTop, cdTop);
		resultMap.put("topPremAll",new BigDecimal(orderTop.getSumAmount()).setScale(0, BigDecimal.ROUND_HALF_UP));
		resultMap.put("topPremDiscount",new BigDecimal(orderTop.getDiscountAmount()).setScale(0, BigDecimal.ROUND_HALF_UP));
		resultMap.put("topPremPay",new BigDecimal(orderTop.getPayAmount()).setScale(0, BigDecimal.ROUND_HALF_UP));
		
		resultMap.put("resultBasic", resultBasic);
		resultMap.put("resultNormal", resultNormal);
		resultMap.put("resultTop", resultTop);
		
		resultMap.put("flag", "Y");
		resultMap.put("message", "查询成功");
		
		return resultMap;
	}

	private PolicyDomain initPolicyWithPackageType(OrderDetailDomain orderInfo,
			CustomerDetailDomain cdi, String packageType) throws ParseException {
		
		PolicyDomain pd = new PolicyDomain();
		PolicyDomain pdNormal = new PolicyDomain();
		PolicyDomain pdTop = new PolicyDomain();
		
		pd.setBuyPrice(orderInfo.getBuyPrice());
		pd.setLoadDate(orderInfo.getLoadDate());	
		pd.setCarType(orderInfo.getCarClassifyType());
		pd.setSeatNum(orderInfo.getCarSeat());
		pd.setLoadDate(orderInfo.getLoadDate());
		pd.setEffectDate(orderInfo.getEffectDate());
		
		if(StringUtils.isNotBlank(orderInfo.getLoadDate())){
			pd.setLoadYear(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"Y"));
			pd.setLoadMonth(CustomerUtils.calYearAndMonth(orderInfo.getLoadDate(),orderInfo.getEffectDate(),"M"));
		}else{
			pd.setLoadYear(CustomerUtils.calYearAndMonth(cdi.getLoadDate(),orderInfo.getEffectDate(),"Y"));
			pd.setLoadMonth(CustomerUtils.calYearAndMonth(cdi.getLoadDate(),orderInfo.getEffectDate(),"M"));
		}
		
		pd.setDesignatedDriver(false);
		pd.setDesignatedArea(false);
		
		int year = Integer.parseInt(pd.getLoadYear());
		if(year>0){
			pd.setTimesBack1(cdi.getTransTimes());
			pd.setBusinessAmount(cdi.getBusinessAmount());
		}else{
			pd.setTimesBack1(1);
			pd.setBusinessBack(1);
		}
		
		if(year>1){
			pd.setTimesBack2(cdi.getTransTimesBack2());
			pd.setBusinessBack2(cdi.getBusinessTimesBack2());
		}else{
			pd.setTimesBack2(1);
			pd.setBusinessBack2(1);
		}
		
		if(year>2){
			pd.setTimesBack3(cdi.getTransTimesBack3());
			pd.setBusinessBack3(cdi.getBusinessTimesBack3());
		}else{
			pd.setTimesBack3(1);
			pd.setBusinessBack3(1);
		}
		if(StringUtils.isNotBlank(cdi.getTransAmount()))
			pd.setAmountBack1(String.valueOf((new BigDecimal(cdi.getTransAmount())).setScale(2, BigDecimal.ROUND_HALF_UP)));
		else
			pd.setAmountBack1("0");
		
		if(StringUtils.isNotBlank(cdi.getTransAmountBack2()))
			pd.setAmountBack2(String.valueOf((new BigDecimal(cdi.getTransAmountBack2())).setScale(2, BigDecimal.ROUND_HALF_UP)));
		else
			pd.setAmountBack2("0");
		
		if(StringUtils.isNotBlank(cdi.getTransAmountBack3()))
			pd.setAmountBack3(String.valueOf((new BigDecimal(cdi.getTransAmountBack3())).setScale(2, BigDecimal.ROUND_HALF_UP)));
		else
			pd.setAmountBack3("0");
		
		if(StringUtils.isNotBlank(cdi.getBusinessAmount()))
			pd.setBusinessAmount((new BigDecimal(cdi.getBusinessAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
		else
			pd.setBusinessAmount("0");
		
		if(StringUtils.isNotBlank(cdi.getBusinessAmountBack2()))
			pd.setBusinessAmount2((new BigDecimal(cdi.getBusinessAmountBack2()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
		else
			pd.setBusinessAmount2("0");
		
		if(StringUtils.isNotBlank(cdi.getBusinessAmountBack3()))
			pd.setBusinessAmount3((new BigDecimal(cdi.getBusinessAmountBack3()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
		else
			pd.setBusinessAmount3("0");
		
		
		
		CarTypeListDomain carDetailInfo = customerDao.queryCarDetailInfoByCarSeq(cdi.getCarSeq());
		pd.setSweptVolumn(carDetailInfo.getSweptVolume());

		// 折旧价
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date load = sd.parse(orderInfo.getLoadDate());
		BigDecimal month = new BigDecimal(CustomerUtils.getMonth(load,orderInfo.getEffectDate()));
		BigDecimal buyValue = new BigDecimal(orderInfo.getBuyPrice());
		BigDecimal nowPrice = buyValue.subtract(buyValue.multiply(month).multiply(CustomerUtils.ZSXS_MONTH));

		double resultPrice = 0.0d;
		if (nowPrice.compareTo(buyValue.multiply(CustomerUtils.ZSJS_TOTAL)) == 1) {
			resultPrice = nowPrice.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
		} else {
			resultPrice = buyValue.multiply(CustomerUtils.ZSJS_TOTAL)
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		pd.setCurrentPrice(String.valueOf(resultPrice));
		pd.setFrameNo(cdi.getFrameNo());
		
		List<ProductDomain> pdProduct = new ArrayList<ProductDomain>();
		
		ProductDomain pd11 = new ProductDomain();
		pdProduct.add(pd11);
		
		ProductDomain pd02 = new ProductDomain();
		pdProduct.add(pd02);
		
		ProductDomain pd99 = new ProductDomain();
		pdProduct.add(pd99);
		
		if(StringUtils.equals("basic", packageType)){
			ProductDomain pd01 = new ProductDomain();
			pdProduct.add(pd01);		
			
			ProductDomain pd03 = new ProductDomain();
			pdProduct.add(pd03);
			
			ProductDomain pd08 = new ProductDomain();
			List<OrderRiskParameter> productParameterList08 = new ArrayList<OrderRiskParameter>();
			
			OrderRiskParameter contain100001 = new OrderRiskParameter();
			contain100001.setParameterKey("contain100001");
			contain100001.setKeyValue("Y");
			productParameterList08.add(contain100001);
			
			OrderRiskParameter contain100002 = new OrderRiskParameter();
			contain100002.setParameterKey("contain100002");
			contain100002.setKeyValue("Y");
			productParameterList08.add(contain100002);
			
			OrderRiskParameter contain100003 = new OrderRiskParameter();
			contain100003.setParameterKey("contain100003");
			contain100003.setKeyValue("Y");
			productParameterList08.add(contain100003);
			
			pdProduct.add(pd08);
			
		}else if(StringUtils.equals("normal", packageType)){
			ProductDomain pd01 = new ProductDomain();
			pdProduct.add(pd01);		
			
			ProductDomain pd03 = new ProductDomain();
			pdProduct.add(pd03);
			
			ProductDomain pd04 = new ProductDomain();
			pdProduct.add(pd04);
			
			ProductDomain pd05 = new ProductDomain();
			pdProduct.add(pd05);
			
			ProductDomain pd08 = new ProductDomain();
			List<OrderRiskParameter> productParameterList08 = new ArrayList<OrderRiskParameter>();
			
			OrderRiskParameter contain100001 = new OrderRiskParameter();
			contain100001.setParameterKey("contain100001");
			contain100001.setKeyValue("Y");
			productParameterList08.add(contain100001);
			
			OrderRiskParameter contain100002 = new OrderRiskParameter();
			contain100002.setParameterKey("contain100002");
			contain100002.setKeyValue("Y");
			productParameterList08.add(contain100002);
			
			OrderRiskParameter contain100003 = new OrderRiskParameter();
			contain100003.setParameterKey("contain100003");
			contain100003.setKeyValue("Y");
			productParameterList08.add(contain100003);
			
			OrderRiskParameter contain100004 = new OrderRiskParameter();
			contain100004.setParameterKey("contain100004");
			contain100004.setKeyValue("Y");
			productParameterList08.add(contain100004);
			
			pdProduct.add(pd08);
		}else{
			ProductDomain pd01 = new ProductDomain();
			pdProduct.add(pd01);		
			
			ProductDomain pd03 = new ProductDomain();
			pdProduct.add(pd03);
			
			ProductDomain pd04 = new ProductDomain();
			pdProduct.add(pd04);
			
			ProductDomain pd05 = new ProductDomain();
			
			pdProduct.add(pd05);
			
			ProductDomain pd06 = new ProductDomain();
			pdProduct.add(pd06);
			
			ProductDomain pd07 = new ProductDomain();
			pdProduct.add(pd07);
			
			ProductDomain pd08 = new ProductDomain();
			List<OrderRiskParameter> productParameterList08 = new ArrayList<OrderRiskParameter>();
			
			OrderRiskParameter contain100001 = new OrderRiskParameter();
			contain100001.setParameterKey("contain100001");
			contain100001.setKeyValue("Y");
			productParameterList08.add(contain100001);
			
			OrderRiskParameter contain100002 = new OrderRiskParameter();
			contain100002.setParameterKey("contain100002");
			contain100002.setKeyValue("Y");
			productParameterList08.add(contain100002);
			
			OrderRiskParameter contain100003 = new OrderRiskParameter();
			contain100003.setParameterKey("contain100003");
			contain100003.setKeyValue("Y");
			productParameterList08.add(contain100003);
			
			OrderRiskParameter contain100004 = new OrderRiskParameter();
			contain100004.setParameterKey("contain100004");
			contain100004.setKeyValue("Y");
			productParameterList08.add(contain100004);
			
			OrderRiskParameter contain100006 = new OrderRiskParameter();
			contain100006.setParameterKey("contain100006");
			contain100006.setKeyValue("Y");
			productParameterList08.add(contain100006);
			
			pdProduct.add(pd08);
		}
		
		pd.setProductList(pdProduct);
		return pd;
	}

	/**
	 * 查询汽车详细信息
	 * @param carSeq
	 * @return
	 */
	public Map<String, Object> getCarDetail(String carSeq) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		CarTypeListDomain carDetail = customerDao.queryCarDetailInfoByCarSeq(carSeq);
		
		resultMap.put("flag", "Y");
		resultMap.put("message", "查询成功");
		resultMap.put("carDetail", carDetail);
		return resultMap;
	}

	/**
	 * 提交航意险
	 * @param applicantName
	 * @param applicantIdNo
	 * @return
	 * @throws Throwable 
	 */
	public Map<String, Object> submitHYX(String applicantName,
			String applicantIdNo) throws Throwable {
		String openId = PlatformContext.getUmUserId();
		
		int countOrder = customerDao.countOrderByOpenId(openId,"02");
		OrderDetailDomain hyOrder = customerDao.queryOrderByTypeAndNotFinished(openId, "02");
		
		if(hyOrder!=null||countOrder>0){
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("flag", "N");
			resultMap.put("message", "您已领取过航意险，不能重复领取");
			return resultMap;
		}
		
		CustomerDetailDomain customer = customerDao.queryClientInfoByOpenId(openId);
		
		OrderDetailDomain hyxOrder = new OrderDetailDomain();
		hyxOrder.setOpenId(PlatformContext.getUmUserId());
		hyxOrder.setClientIdNo(customer.getClientId());
		hyxOrder.setClientName(applicantName);
		hyxOrder.setOrderStatus("02");
		hyxOrder.setOrderType("03");
		hyxOrder.setPayAmount("0.0");
		hyxOrder.setSumAmount("0.0");
		hyxOrder.setProductType("02");
		hyxOrder.setRiskName("航意险");
		hyxOrder.setScore("0");
		hyxOrder.setRiskType("02");
		
		customerDao.insertHYXOrder(hyxOrder);
		
		OrderDetailDomain resultOrder = customerDao.queryOrderByTypeAndNotFinished(openId, "02");
		
		//点对点推送消息
		HashMap msg = new HashMap();
		// 消息模板
		String templateId = JSDDConfig.ORDER_SUBMIT_MODE;
		// 点击后跳到哪里

		String toURL = JSDDConfig.FOLLOW_URL;
				
		//推送消息
		msg.put("touser", JSDDConfig.SERVICE_OPEN_ID);//TODO
		msg.put("template_id", templateId);
		msg.put("url", toURL);

		HashMap data = new HashMap();

		HashMap first = new HashMap();
		first.put("value", "客户"+hyxOrder.getClientName()+"已领取航意险");
		first.put("color", "#173177");
		data.put("first", first);

		HashMap keyword1 = new HashMap();
		keyword1.put("value", "免费航意险");
		data.put("keyword1", keyword1);

		HashMap keyword2 = new HashMap();
		keyword2.put("value", resultOrder.getOrderId());
		data.put("keyword2", keyword2);

		HashMap keyword3 = new HashMap();
		keyword3.put("value", "");
		data.put("keyword3", keyword3);

		HashMap keyword4 = new HashMap();
		keyword4.put("value", "0.0");
		data.put("keyword4", keyword4);
				
		HashMap keyword5 = new HashMap();
		keyword4.put("value", "已申请领取");
		data.put("keyword5", keyword5);

		HashMap remark = new HashMap();
		remark.put("value", "请安排后续流程，客户的报价订单号为:"+resultOrder.getOrderId());
		data.put("remark", remark);

		msg.put("data", data);
		        
		wXTemplateMessageService.pushTemplateMessage(msg);
				
		customerDao.updateClientParameterInfo(openId, "FOLLOW_HYX", "Y");
		
		customerDao.updateClientInfo(applicantName,applicantIdNo,openId);
		
		//发送邮件
		JSDDUtils.sendMail("客户领取航意险订单:"+resultOrder.getOrderId()+"客户领取航意险订单:"+resultOrder.getOrderId(), "", toArrary);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("flag", "Y");
		resultMap.put("message", "领取成功");
		
		return resultMap;
	}

	public Map<String, Object> loadZSInfo() {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String openId = PlatformContext.getUmUserId();
		int hyxcount = customerDao.queryHYXConunt(openId);
		
		ClientInfoParameterDomain clientParameter = customerDao.queryClientParameterInfo(openId, "PUSH_HYX");
		
		String hasBJ = clientParameter==null?"N":clientParameter.getParameterValue();
		
		resultMap.put("hyxcount", hyxcount);
		resultMap.put("hasBJ", hasBJ);
		resultMap.put("flag","Y");
		return resultMap;
	}

	public Map<String,Object> paySuccess(String orderId) {
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		OrderDetailDomain orderInfo = customerDao.queryOrderByOrderId(orderId);//查询车险未完成的订单
		
		if(orderInfo!=null&&StringUtils.equals("00", orderInfo.getOrderStatus())){
			String openId = orderInfo.getOpenId();
			String scroe = orderInfo.getScore();
			String payAmount = orderInfo.getPayAmount();//需交保费合计
			
			//微信支付
			CustomerDetailDomain customerDetailInfo = customerDao.queryClientInfoByOpenId(openId); 
			
			Map<String,Object> updateMap = new HashMap<String,Object>();
			updateMap.put("openId", openId);
			updateMap.put("clientName", customerDetailInfo.getClientName());
			updateMap.put("clientIdNo", customerDetailInfo.getClientIdNo());
			updateMap.put("clientIntegral", new BigDecimal(customerDetailInfo.getClientIntegral()).subtract(new BigDecimal(scroe)).toString());
			
			customerDao.updateIntegral(updateMap);
			
			orderInfo.setOrderStatus("02");
			//reSellDao.updateOrderStatus(orderInfo);
			
			//发送邮件
			JSDDUtils.sendMail("订单"+orderId+"选择微信支付收费完成，请跟进", "订单"+orderId+"已收费，请跟进", toArrary);
		}
		resultMap.put("flag", "Y");
		resultMap.put("message", "更新成功");
		return resultMap;
	}

	/**
	 * 查询总条数
	 * @param parameterMap
	 * @return
	 */
	public Integer queryTotalPageSize(Map<String, Object> parameterMap) {
		return customerDao.queryTotalPageSize(parameterMap);
	}

	public Map<String,Object> delBind(){
		String openId = PlatformContext.getUmUserId();
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String msg = "保存失败";
		String flag = "N";
		
		if(StringUtils.isBlank(openId)){
			msg = "您的openId未获取";
		}else{
			customerDao.deleteClientBind(openId);
			
			String orderId = customerDao.queryOrderIdByClientId(openId);
			
			if(StringUtils.isNotBlank(orderId)){
				customerDao.deleteClientOrderBind(openId);
			}
			
			flag = "Y";
			msg ="取消绑定成功";
		}
		
		returnMap.put("msg", msg);
		returnMap.put("flag", flag);
		return returnMap;
	}
	
}
