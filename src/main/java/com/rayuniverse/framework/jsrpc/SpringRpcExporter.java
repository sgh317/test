package com.rayuniverse.framework.jsrpc;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rayuniverse.framework.PlatformContext;

public class SpringRpcExporter {
	private static Logger logger = LoggerFactory.getLogger(SpringRpcExporter.class);
	 public void   export(Object bean, String beanName)
	 {
			if (bean instanceof org.springframework.aop.framework.Advised) {
				try {
					org.springframework.aop.framework.Advised adv = (org.springframework.aop.framework.Advised) bean;
					
					//JS Bean
					{
						JsBean js = adv.getTargetSource().getTarget().getClass()
								.getAnnotation(JsBean.class);
						if (js != null) {
							String jsBeanName=beanName;
							if(js.value()!=null&&js.value().trim().length()>0)
							{
								jsBeanName=js.value();
							}
							logger.info("JsonRpcContext publishJsonBean["+jsBeanName+"]");
							PlatformContext.getRpcContext().exportJsonBean(jsBeanName, bean);
						}

						for (Method method : adv.getTargetSource().getTarget()
								.getClass().getDeclaredMethods()) {
							JsFunction jm = method.getAnnotation(JsFunction.class);
							if (jm != null) {
								logger.info("JsonRpcContext publishJsonFunction["+jm.value()+"]");
								PlatformContext.getRpcContext().exportJsonFunction(
										jm.value(),
										bean.getClass().getDeclaredMethod(
												method.getName(),
												method.getParameterTypes()), bean);
							}
						}
					}
					
					
					
				} catch (Throwable e) {
					throw new RuntimeException(e);
				}

			} else {
			 
				
				//JS Bean
				{
					JsBean js = bean.getClass().getAnnotation(JsBean.class);
					if (js != null) {
						String jsBeanName=beanName;
						if(js.value()!=null&&js.value().trim().length()>0)
						{
							jsBeanName=js.value();
						}
						PlatformContext.getRpcContext().exportJsonBean(jsBeanName, bean);
						logger.info("JsonRpcContext publishJsonBean["+jsBeanName+"]");
					}

					for (Method method : bean.getClass().getMethods()) {
						JsFunction jm = method.getAnnotation(JsFunction.class);
						if (jm != null) {
							logger.info("JsonRpcContext publishJsonFunction["+jm.value()+"]");
							PlatformContext.getRpcContext()
									.exportJsonFunction(jm.value(), method, bean);
						}
					}
				}
				
				
			}
			
			
		
		 
	 }
	 
	
}
