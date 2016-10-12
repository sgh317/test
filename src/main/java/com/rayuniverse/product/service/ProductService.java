package com.rayuniverse.product.service;

import java.io.File;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rayuniverse.domain.ProductDomain;
import com.rayuniverse.domain.SkuDomain;
import com.rayuniverse.framework.comm.Util;
import com.rayuniverse.framework.json.JsonUtil;
import com.rayuniverse.product.dao.ProductDao;

/**
 * 
 * @author DiGua
 *
 */
@Service
@Transactional
public class ProductService {
	
	@Autowired
	ProductDao productDao;
	
	public void uploads(MultipartFile[] files, String destDir,
            HttpServletRequest request,HttpServletResponse response) throws Exception {
//        String path = request.getContextPath();
//        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 
//            request.getServerPort() + path;
        // 获取文件上传的真实路径
        String imageAddr = request.getParameter("imageAddr");
        //String uploadPath = request.getSession().getServletContext().getRealPath("/");
        String[] fileNames = new String[files.length];
        Map map = new HashMap();
        int index = 0;
        //上传文件过程
        for (MultipartFile file : files) {              
            String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            int length = getAllowSuffix().indexOf(suffix);
            if (length == -1) {
                throw new Exception("请上传允许格式的文件");
            }
            destDir = "/mwbase/apptemp/images/"+imageAddr+"/";
            File destFile = new File(destDir);
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            String fileNameNew = getFileNameNew() + "." + suffix;//
            File f = new File(destFile.getAbsoluteFile() + File.separator + fileNameNew);
            //如果当前文件已经存在了，就跳过。
            if(f.exists()){
                continue;
            }
            file.transferTo(f);
            f.createNewFile();
            fileNames[index++] = destDir + fileNameNew;
        }
        map.put("id", fileNames);
        Writer out;
        response.setContentType("text/heml;charset=UTF-8");
        out = response.getWriter();
        out.write(JsonUtil.toJson(map, true));
        out.close();
    }
	
	public void uploads(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String imageAddr = request.getParameter("imageAddr");
        //String uploadPath = request.getSession().getServletContext().getRealPath("/"); 
        
        MultipartHttpServletRequest mureq = (MultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile>  multFileMap = mureq.getMultiFileMap();
        for (List<MultipartFile> files : multFileMap.values()) {
        	String[] fileNames = new String[files.size()];
        	Map map = new HashMap();
        	int index = 0;
        	for (MultipartFile file : files) {              
//                String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//                int length = getAllowSuffix().indexOf(suffix);
//                if (length == -1) {
//                    throw new Exception("请上传允许格式的文件");
//                }
                String destDir = "/mwbase/apptemp/images/"+imageAddr+"/";
                File destFile = new File(destDir);
                if (!destFile.exists()) {
                    destFile.mkdirs();
                }
                String fileNameNew = getFileNameNew() + ".jpg";//
                File f = new File(destFile.getAbsoluteFile() + File.separator + fileNameNew);
                //如果当前文件已经存在了，就跳过。
                if(f.exists()){
                    continue;
                }
                file.transferTo(f);
                f.createNewFile();
                fileNames[index++] = fileNameNew;
            }
        	map.put("id", fileNames);
        	Writer out;
        	response.setContentType("text/heml;charset=UTF-8");
        	out = response.getWriter();
        	out.write(JsonUtil.toJson(map, true));
        	out.close();
        }
	}
	
	public List<Map> queryCatalogList() {
		List<Map> catalogList = new ArrayList<Map>();
		List<Map> catalogAllList = productDao.queryCatalogList();
		for(Map catalogMap : catalogAllList) {
			if(null == catalogMap.get("parentCid")) {
				catalogList.add(catalogMap);
			}
		}
		return catalogList;
	}
	
	/**
	 * 查询小分类列表
	 * @param parameterMap
	 * @return
	 */
	public List<Map> queryClassifyList(Map parameterMap) {
		return productDao.queryClassifyList(parameterMap);
	}
	
	/**
	 * 查询小分类列表总数
	 * @param queryMap
	 * @return
	 */
	public Integer queryClassifyListTotalPageSize(Map parameterMap) {
		return productDao.queryClassifyListTotalPageSize(parameterMap);
	}
	/**
	 * 查询商品列表
	 * @param queryMap
	 * @return
	 */
	public Map queryProductInfoList(Map parameterMap) {
		List<Map> productMapList = productDao.queryProductInfoList(parameterMap);
		Map<Object,List> productNewMap = new HashMap<Object,List>();
		
		if(null != productMapList && productMapList.size() != 0) {
			for(Map productMap : productMapList) {
				if(productNewMap.containsKey(productMap.get("proid"))) {
					((List) productNewMap.get(productMap.get("proid"))).add(productMap);
				} else {
					List<Map> a = new ArrayList<Map>();
					a.add(productMap);
					productNewMap.put(productMap.get("proid"), a);
				}
			}
		}
		return productNewMap;
	}
	
	/**
	 * 查询商品列表总数
	 * @param queryMap
	 * @return
	 */
	public Integer queryProductInfoListTotalPageSize(Map parameterMap) {
		return productDao.queryProductInfoListTotalPageSize(parameterMap);
	}
	
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public void saveProductInfo(ProductDomain productInfo) throws Throwable {
		String productId = Util.getUUID();
		productInfo.setProductId(productId);
		productDao.saveProductInfo(productInfo);
		List<SkuDomain> skuList = productInfo.getSkuList();
		for(SkuDomain sku:skuList) {
			sku.setSkuId(Util.getUUID());
			sku.setProductId(productId);
			sku.setProductName(productInfo.getProductName());
			saveSkuInfo(sku);
		}
	}
	
	public void saveSkuInfo(SkuDomain skuInfo) {
		productDao.saveSkuInfo(skuInfo);
	}
	
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public void updateProductStatus(Map parameterMap) {
		productDao.updateProductStatus(parameterMap);
	}
	
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public void updateProductInfo(ProductDomain productInfo) {
		productDao.updateProductInfo(productInfo);
		for(SkuDomain skuInfo : productInfo.getSkuList()) {
			if(null == skuInfo.getSkuId() || "".equals(skuInfo.getSkuId())) {
				skuInfo.setSkuId(Util.getUUID());
				skuInfo.setProductId(productInfo.getProductId());
				skuInfo.setProductName(productInfo.getProductName());
				productDao.saveSkuInfo(skuInfo);
			}
			productDao.updateSkuInfo(skuInfo);
		}
	}

    /**
     * 为文件重新命名，命名规则为当前系统时间毫秒数
     * @return string
     */
    private String getFileNameNew() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmt.format(new Date());
    }
    
    private List getAllowSuffix() {
    	List a = new ArrayList();
    	a.add("gif");
    	a.add("jpg");
    	a.add("jpeg");
    	a.add("bmp");
    	a.add("png");
    	return a;
    }
    
    /*查询分类名称*/
	public void queryProductClassifyList(String classify) {
		productDao.queryProductClassifyList(classify);
	}
	
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public void deleteSkuInfo(String skuId) {
		productDao.deleteSkuInfo(skuId);
	}
    	
    /*	查询商品详情
    	   
    	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
    	public void queryGooddetail(String proid) {
    		productDao.queryGooddetail(proid);
    	}*/

}
