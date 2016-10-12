package com.rayuniverse.util;

import java.util.UUID;

public class UUIDUtil {
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		String uStr = uuid.toString();
		String[] uArray = uStr.split("[-]");
		uStr = "";
        for(int i=0;i<uArray.length;i++){
        	uStr += uArray[i];
        }
		return uStr;
	}

}
