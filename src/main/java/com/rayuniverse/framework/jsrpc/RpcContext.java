package com.rayuniverse.framework.jsrpc;
import java.lang.reflect.Method;


public class RpcContext{
 

   public void exportJsonFunction(String functionName,Method method,Object serviceObject)
   {
	   
	   JsonRpcSkeleton.publishJsonFunction(functionName, method, serviceObject);
   }
   
   
   public void exportJsonBean(String beanName,Object serviceObject)
   {
	   JsonRpcSkeleton.publishJsonBean(beanName, serviceObject);
   }
   
   
 

	


}
