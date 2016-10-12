package com.rayuniverse.framework.jsrpc;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.rayuniverse.framework.Consts;
import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.json.mapper.JSONMapper;
import com.rayuniverse.framework.json.mapper.MapperException;
import com.rayuniverse.framework.json.model.JSONObject;
import com.rayuniverse.framework.json.model.JSONValue;
import com.rayuniverse.framework.json.parser.JSONParser;




public class JsonRpcSkeleton {
	    private static final Logger  logger = org.slf4j.LoggerFactory.getLogger(JsonRpcSkeleton.class.getName());
	    static HashMap<String,String> skipMethods=new HashMap();
	    static boolean supportSpring=false;
		static {
			skipMethods.put("addAdvice","");
			skipMethods.put("getAdvisors","");
			skipMethods.put("getCallback","");
			skipMethods.put("getCallbacks","");
			skipMethods.put("getProxiedInterfaces","");
			skipMethods.put("getTargetClass","");
			skipMethods.put("getTargetSource","");
			skipMethods.put("hashCode","");
			skipMethods.put("indexOf","");
			skipMethods.put("isExposeProxy","");
			skipMethods.put("isFrozen","");
			skipMethods.put("isInterfaceProxied","");
			skipMethods.put("isPreFiltered","");
			skipMethods.put("isProxyTargetClass","");
			skipMethods.put("newInstance","");
			skipMethods.put("removeAdvice","");
			skipMethods.put("removeAdvisor","");
			skipMethods.put("replaceAdvisor","");
			skipMethods.put("setCallback","");
			skipMethods.put("setCallbacks","");
			skipMethods.put("setExposeProxy","");
			skipMethods.put("setPreFiltered","");
			skipMethods.put("setTargetSource","");
			skipMethods.put("toProxyConfigString","");
			skipMethods.put("toString","");
			skipMethods.put("equals", "");
			skipMethods.put("addAdvisor", "");
			 try{
				 Class.forName("org.springframework.aop.framework.Advised");
				 supportSpring=true;
			 }catch(Throwable e)
			 {
				 
			 }
		}
		
	   protected static ConcurrentHashMap jsonRpcPublishMethodScriptCache=new ConcurrentHashMap();
	   protected static ConcurrentHashMap jsonRpcPublishMethodCache=new ConcurrentHashMap();
	   protected static ConcurrentHashMap jsonRpcPublishBeanCache=new ConcurrentHashMap();
	 
	 
	   private  static String generateFunction(String functionName,Method method)
	   {
		   StringBuffer buf=new StringBuffer();
			buf.append("function(");
			Object[] paramters=method.getParameterTypes();
			if(paramters!=null)
			{
				for(int i=0;i<paramters.length;i++)
				{
					if(i>0)
					{
						buf.append(",");
					}
					buf.append("arg"+i);
				}
			}
			if(paramters!=null&&paramters.length>0)
			{
				buf.append(",");
			}
			buf.append("callbackObj");
			buf.append("){\r\n");
			buf.append(generateFunctionBody(  functionName,  method));
			buf.append("\r\n   }");
			return buf.toString();
		 
	   }
	   public static String generateFunctionBody(String functionName,Method method)
	    {
	    	StringBuffer buf=new StringBuffer();
	    	buf.append("      var request={\r\n");
	    	buf.append("          functionName:'").append(functionName).append("',\r\n");
	    	buf.append("          paramters:JSON.stringify({\r\n");
	    	if(method.getParameterTypes()!=null)
	    	{
	    		for(int i=0;i<method.getParameterTypes().length;i++)
	    		{
	    			if(i>0)
	    				buf.append(",\r\n");
	    			buf.append("          arg").append(i).append(":arg").append(i).append("");
	    		}
	    	}
	    	buf.append("\r\n          })\r\n");
	    	buf.append("      };\r\n");
	    	
	    	buf.append("      if(callbackObj!=null){\r\n");
	    	buf.append("            RpcContext.invoke(request,callbackObj);\r\n");
	    	buf.append("      }else{\r\n");
	    	buf.append("            return RpcContext.invoke(request,callbackObj);\r\n");
	    	buf.append("      }");
	    	
	    	return buf.toString();
	    }
	   
	
	 
	   static public void publishJsonFunction(String functionName,Method method,Object serviceObject)
	   {
		   ServiceMethodSkeleton sm=new ServiceMethodSkeleton();
		   sm.setMethod(method);
		   sm.setTarget(serviceObject);
		   
		   JsonParamterResolver jpr=null;
		   if(method.getDeclaringClass().getName().indexOf("$EnhancerByCGLIB$")!=-1)
		   {
			  Class cls= method.getDeclaringClass().getSuperclass();
			  try {
				jpr= cls.getMethod(method.getName(), method.getParameterTypes()).getAnnotation(JsonParamterResolver.class);
			 } catch (Throwable e) {
				throw new RuntimeException(e);
			 }
		   }
		   else
		   {
			    jpr=method.getAnnotation(JsonParamterResolver.class);
		   }
		   if(jpr!=null)
		   {
			   try {
				sm.setJsonResolver((MethodJsonParamterResolver) jpr.value().newInstance());
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		   }
		   jsonRpcPublishMethodCache.put(functionName, sm);
		   String ms=generateFunction(functionName,method);
		   jsonRpcPublishMethodScriptCache.put(functionName, ms);
	   }
	   
	   static public void publishJsonBean(String beanName,Object serviceObject)
	   {
		   
		   if(supportSpring)
		   {
			   if(serviceObject instanceof org.springframework.aop.framework.Advised)
			   {
				   StringBuffer buf=new StringBuffer();
				   buf.append("{\r\n");
				   int i=0;
				   
				   org.springframework.aop.framework.Advised adv=(org.springframework.aop.framework.Advised)serviceObject;
				   try {
					   Method[] ims=adv.getTargetSource().getTarget().getClass().getMethods();
				       if(ims!=null)
				       {
				    	   for(Method im:ims)
				    	   {

							    if(im.getName().startsWith("CGLIB"))
								{
									continue ;
								}
								if(skipMethods.get(im.getName())!=null)
								{
									continue ;
								}
								if(im.getName().indexOf("$")!=-1)
								{
									continue ;
								}
								if(im.getDeclaringClass().getName().startsWith("java."))
								{
									continue ;
								}
								if(im.getDeclaringClass().getName().startsWith("javax."))
								{
									continue ;
								}
								if(im.getDeclaringClass().getName().startsWith("org.spring"))
								{
									continue ;
								}
								if(Modifier.isPublic(im.getModifiers()))
								{
									publishJsonFunction(beanName+"."+im.getName(),serviceObject.getClass().getMethod(im.getName(), im.getParameterTypes()),serviceObject);
									i++;
									if(i>1)
									{
										buf.append(",\r\n");
									}
								    String mx=generateFunction(beanName+"."+im.getName(),serviceObject.getClass().getMethod(im.getName(), im.getParameterTypes()));;
									buf.append("   "+im.getName()+":"+mx);
								}
				    	   }
				       }
				   } catch (Throwable  e) {
					 throw new RuntimeException(e);
				   }
				   
				   buf.append("\r\n}\r\n");
				   
				   jsonRpcPublishBeanCache.put(beanName, buf.toString());
				  
				   
				   
				   
				   return ;
			   }
		   }
		   
		   {
			   StringBuffer buf=new StringBuffer();
			   buf.append("{\r\n");
			   int i=0;
			   
			   Method[] ms=serviceObject.getClass().getMethods();
			   for(Method m:ms)
			   {
				   if(m.getName().startsWith("CGLIB"))
					{
						continue ;
					}
					if(skipMethods.get(m.getName())!=null)
					{
						continue ;
					}
					if(m.getName().indexOf("$")!=-1)
					{
						continue ;
					}
					if(m.getDeclaringClass().getName().startsWith("java."))
					{
						continue ;
					}
					if(m.getDeclaringClass().getName().startsWith("javax."))
					{
						continue ;
					}
					if(m.getDeclaringClass().getName().startsWith("org.spring"))
					{
						continue ;
					}
					if(Modifier.isPublic(m.getModifiers()))
					{
						publishJsonFunction(beanName+"."+m.getName(),m,serviceObject);
						i++;
						if(i>1)
						{
							buf.append(",\r\n");
						}
					    String mx=generateFunction(beanName+"."+m.getName(),m);;
						buf.append("   "+m.getName()+":"+mx);
					}
			   }
			   
			   buf.append("\r\n}\r\n");
			   jsonRpcPublishBeanCache.put(beanName, buf.toString());

		   }
		 
		  
	   }
	   
	 
	   public static void getBeanJsonStub(String beanName,HttpServletResponse response,HttpServletRequest request) throws IOException
	   {
		   beanName=beanName.substring(0,beanName.length()-3);
		   String bean=(String) jsonRpcPublishBeanCache.get(beanName);
		   if(bean==null)
		   {
			   response.sendError(404);
		   }
		   else
		   {
			    response.setCharacterEncoding("utf-8");
				response.setContentType("text/javascript");
				
				response.getWriter().println("RpcContext.beans['"+beanName+"']="+bean+";");
				response.getWriter().flush();
				
		   }
	   
	   }
	   public static void getFunctionJsonStub(String functionName,HttpServletResponse response,HttpServletRequest request) throws IOException
	   {
		   functionName=functionName.substring(0,functionName.length()-3);
		   String function=(String) jsonRpcPublishMethodScriptCache.get(functionName);
		   if(function==null)
		   {
			   response.sendError(404);
		   }
		   else
		   {
			    response.setCharacterEncoding("utf-8");
				response.setContentType("text/javascript");
				String fullFunctionName=request.getParameter("fullFunctionName");
				response.getWriter().println("RpcContext.functions['"+fullFunctionName+"']="+function+";");
				response.getWriter().println("RpcContext.functions['"+fullFunctionName+"'].baseURLKeyName='"+fullFunctionName+"';");
				response.getWriter().flush();
				
		   }
	   }
	   
	   public static void invoke(HttpServletRequest request,HttpServletResponse response) throws IOException
	   {
		    response.setCharacterEncoding("utf-8");
		    response.setHeader("Cache-Control", "no-cache");
			response.setContentType("text/javascript");
			String function=request.getParameter("functionName");
			String paramtersStr=request.getParameter("paramters");
			ServiceMethodSkeleton sm=(ServiceMethodSkeleton) jsonRpcPublishMethodCache.get(function);
			if(sm==null)
			{
				logger.warn("No Accessable  function "+function);
				String e= throwException(1,"No Accessable  Method ");
				response.getWriter().println(e);
				response.getWriter().flush();
				return ;
			}
			
			Object[] paramters=null;
			try{
				paramters=decodeParamters(paramtersStr, sm.getMethod(),sm.getJsonResolver());
			}catch(Throwable e)
			{
				logger.error("Decode Paramters Error ", e);
				String es= throwException(2,"Decode Paramters Error ");
				response.getWriter().println(es);
				response.getWriter().flush();
				return ;
			}
			
			Object rt=null;
			try{
				rt=sm.getMethod().invoke(sm.getTarget(), paramters);
				if(null!=PlatformContext.getHttpRequestContext(Consts.JSON_RPC_RETURN_OBJ_ENABLE))
				{
					rt=PlatformContext.getHttpRequestContext(Consts.JSON_RPC_RETURN_OBJ);
				}
			}catch(Throwable e)
			{
				logger.error("json rpc invoke error", e);
				String es=  throwException(3,getMessage(e));
				response.getWriter().println(es);
				response.getWriter().flush();
				return ;
			}
			
			String rts=null;
			try{
				rts=  returnObject(rt);
				response.getWriter().println(rts);
				response.getWriter().flush();
				return ;
				
			}catch(Throwable e)
			{
				logger.error("json rpc encode return object error", e);
				String es=  throwException(4,"Encord Return Object Error");
				response.getWriter().println(es);
				response.getWriter().flush();
				return ;
			}
	   }
	   
	   static String getMessage(Throwable a)
	   {
		   if(a.getMessage()==null)
		   {
			   if(a.getCause()==null)
			   {
				   return "";
			   }
			   else
			   {
				   return getMessage(a.getCause());
			   }
		   }
		   else {
			   return a.getMessage();
		   }
	   }
	   
	   static String throwException(int code,String msg) 
	   {
			
			try {
				HashMap map=new HashMap();
				map.put("code", code);
				map.put("msg", msg);
				JSONValue jsonValue;
				jsonValue = JSONMapper.toJSON(map);
				return  jsonValue.render(true);
			} catch (MapperException e) {
				throw new RuntimeException(e);
			}
			
	  }
	   static Object[] decodeParamters(String paramters,Method method,MethodJsonParamterResolver resolver)  
	   {
		    try
		    {
		    	Class[]  paramterTypes=method.getParameterTypes();
		    	
				if(paramterTypes==null||paramterTypes.length==0)
				{
					return new Object[0];
				}
				 JSONParser parser = new JSONParser(new StringReader(paramters));  
				 JSONObject jsonObject= (JSONObject) parser.nextValue();
				 if(resolver!=null)
				 {
					 return resolver.resolve(jsonObject);
				 }
				 Object[] paramtersObjectArray=new Object[paramterTypes.length];
				 for(int i=0;i<paramterTypes.length;i++)
				 {
					 JSONValue jv=(JSONValue) jsonObject.getValue().get("arg"+i);
					 paramtersObjectArray[i]=JSONMapper.toJava(jv,paramterTypes[i]);
				 }
			 	 return paramtersObjectArray;
		    }catch(Throwable e)
		    {
		    	throw new RuntimeException(e);
		    }
	   }
	   
	   public static String returnObject(Object object) 
		{
		   try{
			   HashMap map=new HashMap();
				map.put("code", 0);
				map.put("rt", object);
				JSONValue jsonValue = JSONMapper.toJSON(map);
				return  jsonValue.render(true);
		   } catch(Throwable e)
		    {
		    	throw new RuntimeException(e);
		    }
			
		}
	   

}
