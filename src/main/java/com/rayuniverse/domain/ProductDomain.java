package com.rayuniverse.domain;

import java.util.List;

public class ProductDomain extends BaseDomain{
private static final long serialVersionUID = 5200439034141038307L;
	
	public String productId;
	public String productCode;//��Ʒ����
	public String productName;//��Ʒ����
	public String productIntroductio;//��Ʒ���
	public String productThumbnail;  //����ͼ
	public String productPhotoAlbumImage; //��Ʒ���
	public String productDetail;//��Ʒ����
	public String producterialumber;//��Ʒ���к�
	public String isDiscount;//�Ƿ��ؼ�
	public String isShow;//��Ʒ״̬
	public String classifyId;//��ƷС����
	public String catalogId;//��Ʒ�����
	public List<SkuDomain> skuList;
	public String getIsDiscount() {
		return isDiscount;
	}
	public void setIsDiscount(String isDiscount) {
		this.isDiscount = isDiscount;
	}
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductIntroductio() {
		return productIntroductio;
	}
	public void setProductIntroductio(String productIntroductio) {
		this.productIntroductio = productIntroductio;
	}
	public String getProductThumbnail() {
		return productThumbnail;
	}
	public void setProductThumbnail(String productThumbnail) {
		this.productThumbnail = productThumbnail;
	}
	public String getProductPhotoAlbumImage() {
		return productPhotoAlbumImage;
	}
	public void setProductPhotoAlbumImage(String productPhotoAlbumImage) {
		this.productPhotoAlbumImage = productPhotoAlbumImage;
	}
	public String getProductDetail() {
		return productDetail;
	}
	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public String getProducterialumber() {
		return producterialumber;
	}
	public void setProducterialumber(String producterialumber) {
		this.producterialumber = producterialumber;
	}
	public List<SkuDomain> getSkuList() {
		return skuList;
	}
	public void setSkuList(List<SkuDomain> skuList) {
		this.skuList = skuList;
	}

}
