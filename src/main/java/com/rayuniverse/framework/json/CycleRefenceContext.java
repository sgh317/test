package com.rayuniverse.framework.json;

import java.util.HashMap;

public class CycleRefenceContext {
   static ThreadLocal<HashMap> objectRefence=new ThreadLocal();
   
   public static boolean  can2Json(Object obj)
   {
	   HashMap map=objectRefence.get();
	   if(map==null)
	   {
		   map=new HashMap();
		   objectRefence.set(map);
	   }
	   if(map.get(obj)!=null)
	   {
		   return false;
	   }
	   map.put(obj, obj);
	   return true;
   }
   public static void destory()
   {
	   objectRefence.remove();
   }

}
