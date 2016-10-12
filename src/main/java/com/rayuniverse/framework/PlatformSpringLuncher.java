package com.rayuniverse.framework;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.rayuniverse.framework.comm.CodeTableExporter;
import com.rayuniverse.framework.comm.ProtectDecisionSpi;
import com.rayuniverse.framework.jsrpc.SpringRpcExporter;
import com.rayuniverse.framework.schedule.SchduleTask;

public class PlatformSpringLuncher implements ApplicationContextAware,
BeanPostProcessor ,ApplicationListener,BeanFactoryPostProcessor,BeanDefinitionRegistryPostProcessor{
	private static Logger logger = LoggerFactory.getLogger(PlatformSpringLuncher.class);
	private static List<SchduleTask> tasks=new ArrayList();
	private SpringRpcExporter rpcExporter=new SpringRpcExporter();
	private CodeTableExporter codeTableExport=new CodeTableExporter();
	public  PlatformSpringLuncher()
	{
		
	}
	
	public static Logger getLogger() {
		return logger;
	}
	public static void setLogger(Logger logger) {
		PlatformSpringLuncher.logger = logger;
	}
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		PlatformContext.putGoalbalContext(ApplicationContext.class, ctx);
	}
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		
		if(bean instanceof SchduleTask)
		{
			tasks.add((SchduleTask) bean);
		}
		
		if(bean instanceof ProtectDecisionSpi)
		{
			PlatformContext.setProtectDecision((ProtectDecisionSpi) bean);
		}
		
		rpcExporter.export(  bean,   beanName);
		

		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {

		return bean;
	}
	
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof ContextRefreshedEvent)
		{
			
			codeTableExport.export();
			for( SchduleTask t:tasks)
			{
				PlatformContext.getTaskScheduler().addSchduleTask(t);
			}

		}
	}
	
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
			throws BeansException {
		
		 
	}
	
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry register)
			throws BeansException {
		 
	}

}
