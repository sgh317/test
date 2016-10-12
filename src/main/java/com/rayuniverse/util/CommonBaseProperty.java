package com.rayuniverse.util;

import java.util.concurrent.ConcurrentHashMap;

public class CommonBaseProperty {
	private static ConcurrentHashMap<String, Integer> productSequenceMap = new ConcurrentHashMap<String, Integer>();
	
	public static void addProductCalcSeq(String productCode, Integer seq)
	{
		productSequenceMap.put(productCode, seq);
	}
	public static Integer getProductSequence(String productCode)
	{
		return productSequenceMap.get(productCode);
	}
	
	
}
