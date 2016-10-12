package com.rayuniverse.framework.jsrpc;



import com.rayuniverse.framework.json.mapper.MapperException;
import com.rayuniverse.framework.json.model.JSONObject;


public interface MethodJsonParamterResolver {
	public Object[] resolve(JSONObject jsonObject) throws MapperException, ClassNotFoundException;
}
