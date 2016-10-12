package com.rayuniverse.domain;

import com.rayuniverse.domain.BaseDomain;

public class SkuDomain extends BaseDomain{
	private static final long serialVersionUID = 5200439034141038317L;
	
	public String skuId;
	public String skuCode;
	public String skuName;
	public String skuInventory;
	public String skuSellPrice;
	public String isDiscount;
	public String discountPrice;
	public String skuStates;
	public String skuSaleVolume;
	public String productId;
	public String productName;
	
	
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getSkuInventory() {
		return skuInventory;
	}
	public void setSkuInventory(String skuInventory) {
		this.skuInventory = skuInventory;
	}
	public String getSkuSellPrice() {
		return skuSellPrice;
	}
	public void setSkuSellPrice(String skuSellPrice) {
		this.skuSellPrice = skuSellPrice;
	}
	public String getIsDiscount() {
		return isDiscount;
	}
	public void setIsDiscount(String isDiscount) {
		this.isDiscount = isDiscount;
	}
	public String getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}
	public String getSkuStates() {
		return skuStates;
	}
	public void setSkuStates(String skuStates) {
		this.skuStates = skuStates;
	}
	public String getSkuSaleVolume() {
		return skuSaleVolume;
	}
	public void setSkuSaleVolume(String skuSaleVolume) {
		this.skuSaleVolume = skuSaleVolume;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

}
