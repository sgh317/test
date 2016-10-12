package com.rayuniverse.framework.jsrpc;

import java.lang.reflect.Method;

public class ServiceMethodSkeleton {
	Method method;
	Object target;
	MethodJsonParamterResolver jsonResolver;
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	public MethodJsonParamterResolver getJsonResolver() {
		return jsonResolver;
	}
	public void setJsonResolver(MethodJsonParamterResolver jsonResolver) {
		this.jsonResolver = jsonResolver;
	}
	
}
