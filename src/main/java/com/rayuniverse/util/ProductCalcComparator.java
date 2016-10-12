package com.rayuniverse.util;

import java.util.Comparator;

import com.rayuniverse.domain.ProductDomain;

public class ProductCalcComparator implements Comparator<ProductDomain> {

	@Override
	public int compare(ProductDomain o1, ProductDomain o2) {
		int flag = 0;
		try {
			int s1 = 0;
			int s2 = 0;
			if(s1 > s2)
			{
				flag = 1;
			}
			else if(s1 < s2)
			{
				flag = -1;
			}
		} catch (Exception e) { }
		return flag;
	}

}
