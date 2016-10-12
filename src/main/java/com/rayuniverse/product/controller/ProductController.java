package com.rayuniverse.product.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rayuniverse.domain.ProductDomain;
import com.rayuniverse.domain.SkuDomain;
import com.rayuniverse.framework.base64.Base64;
import com.rayuniverse.product.service.ProductService;

import net.sf.json.JSONObject;



@Controller
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@RequestMapping("product/uploadImage")
	public void uploadImage(@RequestParam("file")MultipartFile[] files, String destDir,
	        HttpServletRequest request,HttpServletResponse response){
		try {
			productService.uploads(files, destDir, request, response);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("product/uploadImageForThumbnail")
	public void uploadImage(HttpServletRequest request,HttpServletResponse response){
		try {
			productService.uploads(request, response);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询小分类列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws Throwable 
	 */
	@RequestMapping("product/queryCatalogList")
	public void queryCatalogList(HttpServletResponse response) throws Throwable{
		List<Map> classifyList = productService.queryCatalogList();
		Map resultMap = new HashMap();
		resultMap.put("CatalogList", classifyList);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject obj = JSONObject.fromObject(resultMap);
		writer.print(obj.toString());
		writer.flush();
		writer.close();
	}
	
	/**
	 * 查询小分类列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws Throwable 
	 */
	@RequestMapping("product/queryClassifyList")
	public void queryClassifyList(HttpServletRequest request,HttpServletResponse response) throws Throwable{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String parentcid = request.getParameter("parentcid");
		String classifyNname = request.getParameter("classifyNname");
		String currentPage = request.getParameter("currentPage");
		int pageSize = 10;
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		if(null != currentPage) {
			parameterMap.put("startIndex", pageSize*(Integer.parseInt(currentPage)-1));
			parameterMap.put("pageSize", pageSize);
		}
		parameterMap.put("parentcid", parentcid);
		parameterMap.put("classifyNname", classifyNname);
		List<Map> classifyList = productService.queryClassifyList(parameterMap);
		Integer classifyListCount = productService.queryClassifyListTotalPageSize(parameterMap);
		resultMap.put("ClassifyList", classifyList);
		int maxPage=classifyListCount/pageSize;
		if(classifyListCount%pageSize>0)
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
	
	@RequestMapping("product/saveProductInfo")
	public String savePorductInfo(ProductDomain productDomain,String[] skuName,String[] skuInventory,
			String[] skuSellPrice,String[] discountPrice){
		try {
			productDomain.setProductDetail(Base64.decode(productDomain.getProductDetail()));
			List<SkuDomain> skuList = new ArrayList<SkuDomain>();
			for (int i = 0; i < skuName.length; i++ ) {
				SkuDomain skuDomain = new SkuDomain();
				skuDomain.setSkuName(skuName[i]);
				skuDomain.setSkuInventory(skuInventory[i]);
				skuDomain.setSkuSellPrice(skuSellPrice[i]);
				skuDomain.setIsDiscount(productDomain.getIsDiscount());
				if(discountPrice.length > 0) {
					skuDomain.setDiscountPrice(discountPrice[i]);
				}
				skuList.add(skuDomain);
			}
			productDomain.setSkuList(skuList);
		
			productService.saveProductInfo(productDomain);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "product/product_list";
	}
	
	@RequestMapping("product/saveCardInfo")
	public String saveCardInfo(ProductDomain productDomain,String skuSellPrice,String discountPrice){
		try {
			productDomain.setCatalogId("003");
			productDomain.setIsDiscount("Y");
			List<SkuDomain> skuList = new ArrayList<SkuDomain>();
			SkuDomain skuDomain = new SkuDomain();
			skuDomain.setSkuName(productDomain.getProductName());
			skuDomain.setSkuInventory("100000000");
			skuDomain.setSkuSellPrice(skuSellPrice);
			skuDomain.setIsDiscount(productDomain.getIsDiscount());
			skuDomain.setDiscountPrice(discountPrice);
			skuList.add(skuDomain);
			productDomain.setSkuList(skuList);
			productService.saveProductInfo(productDomain);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "card/card_list";
	}
	
	@RequestMapping("product/deleteSkuInfo")
	public void deleteSkuInfo(HttpServletRequest request){
		String skuId = request.getParameter("skuId");
		productService.deleteSkuInfo(skuId);
	}
	
	@RequestMapping("product/PorductInfoList")
	public void queryProductInfoList(HttpServletRequest request,HttpServletResponse response) throws Throwable{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String produectName = request.getParameter("produectName");
		String productStatus = request.getParameter("productStatus");
		String currentPage = request.getParameter("currentPage");
		String catalogId = request.getParameter("catalogId");
		int pageSize = 10;
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		
		parameterMap.put("startIndex", pageSize*(Integer.parseInt(currentPage)-1));
		parameterMap.put("pageSize", pageSize);
		parameterMap.put("produectName", produectName);
		parameterMap.put("productStatus", productStatus);
		parameterMap.put("catalogId", catalogId);
		
		Map productInfoMap = productService.queryProductInfoList(parameterMap);
		Integer productInfoCount = productService.queryProductInfoListTotalPageSize(parameterMap);
		
		resultMap.put("productInfoMap", productInfoMap);
		int maxPage=productInfoCount/pageSize;
		if(productInfoCount%pageSize>0)
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
	
	@RequestMapping("product/updateProductStatus")
	public void updateProductStatus(HttpServletRequest request,
			HttpServletResponse response) throws Throwable{
		String productStatus = request.getParameter("productStatus");
		String productId = request.getParameter("productId");
		if("Y".equals(productStatus)) {
			productStatus = "N";
		} else {
			productStatus = "Y";
		}
		Map parameterMap = new HashMap();
		parameterMap.put("productStatus", productStatus);
		parameterMap.put("productId", productId);
		productService.updateProductStatus(parameterMap);
	}
	
	@RequestMapping("product/modifyProductInfo")
	public String modifyProductInfo(ProductDomain productDomain,String[] skuId,String[] skuName,String[] skuInventory,
			String[] skuSellPrice,String[] discountPrice) throws Throwable{
		productDomain.setProductDetail(Base64.decode(productDomain.getProductDetail()));
		List<SkuDomain> skuList = new ArrayList<SkuDomain>();
		for (int i = 0; i < skuName.length; i++ ) {
			SkuDomain skuDomain = new SkuDomain();
			skuDomain.setSkuId(skuId[i]);
			skuDomain.setSkuName(skuName[i]);
			skuDomain.setSkuSellPrice(skuSellPrice[i]);
			skuDomain.setSkuInventory(skuInventory[i]);
			skuDomain.setIsDiscount(productDomain.getIsDiscount());
			if(discountPrice.length > 0) {
				skuDomain.setDiscountPrice(discountPrice[i]);
			}
			skuList.add(skuDomain);
		}
		productDomain.setSkuList(skuList);
		productService.updateProductInfo(productDomain);
		return "product/product_list";
	}
	
	@RequestMapping("product/modifyCardInfo")
	public String modifyCardInfo(ProductDomain productInfo,String skuId,String skuSellPrice,String discountPrice) throws Throwable{
		List<SkuDomain> skuList = new ArrayList<SkuDomain>();
		SkuDomain skuDomain = new SkuDomain();
		skuDomain.setSkuId(skuId);
		skuDomain.setSkuName(productInfo.getProductName());
		skuDomain.setSkuSellPrice(skuSellPrice);
		skuDomain.setIsDiscount(productInfo.getIsDiscount());
		skuDomain.setDiscountPrice(discountPrice);
		skuList.add(skuDomain);
		productInfo.setSkuList(skuList);
		productService.updateProductInfo(productInfo);
		return "card/card_list";
	}
	
	@RequestMapping("product/redirectProductInfo")
	public String redirectProductInfo(HttpServletRequest request,ModelMap model) throws Throwable{
		String productId = request.getParameter("productId");
		Map parameterMap = new HashMap();
		parameterMap.put("productId", productId);
		Map productInfoMap = productService.queryProductInfoList(parameterMap);
		model.put("productInfoMap", productInfoMap);
		Map result = new HashMap();
		result.put("productInfoMap", productInfoMap);
		JSONObject obj = JSONObject.fromObject(result);
		model.put("result",obj);
		return "product/product_edit";
	}
	
	@RequestMapping("product/redirectCardInfo")
	public String redirectCardInfo(HttpServletRequest request,ModelMap model) throws Throwable{
		String productId = request.getParameter("productId");
		Map parameterMap = new HashMap();
		parameterMap.put("productId", productId);
		parameterMap.put("catalogId", "003");
		Map productInfoMap = productService.queryProductInfoList(parameterMap);
		Map result = new HashMap();
		result.put("productInfoMap", productInfoMap);
		JSONObject obj = JSONObject.fromObject(result);
		model.put("result",obj);
		return "card/card_edit";
	}
	@RequestMapping("product/showImage")
	public void showImage(HttpServletRequest request,HttpServletResponse response) throws Throwable{
		String imageAddress = request.getParameter("imageAddress"); 
		OutputStream os = null; 
		FileInputStream fips = null;
		try {
			os = response.getOutputStream();
			File file = new File(imageAddress);  
			fips = new FileInputStream(file);  
			byte[] btImg = readStream(fips);  
			os.write(btImg);  
			os.flush();
			os.close();
		}finally{
            try{
                if(os!=null){
                	os.flush();
                	os.close();
                }
                if(fips!=null){
                	fips.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
	}
	
	/** 
     * 读取管道中的流数据 
     */  
    private byte[] readStream(InputStream inStream) {  
        ByteArrayOutputStream bops = new ByteArrayOutputStream();  
        int data = -1;  
        try {  
            while((data = inStream.read()) != -1){  
                bops.write(data);  
            }  
            return bops.toByteArray();  
        }catch(Exception e){  
            return null;  
        }  
    }  
}