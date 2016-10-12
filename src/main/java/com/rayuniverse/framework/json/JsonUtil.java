package com.rayuniverse.framework.json;

import java.io.StringReader;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.rayuniverse.framework.json.mapper.JSONMapper;
import com.rayuniverse.framework.json.mapper.MapperException;
import com.rayuniverse.framework.json.model.JSONValue;
import com.rayuniverse.framework.json.parser.JSONParser;

public class JsonUtil {
	
	public static  String toJson(Object vo, boolean b) {
		
		 try
		 {
			 JSONValue jsonValue = JSONMapper.toJSON(vo);
			 String s= jsonValue.render(b);
		     return s;
		 }catch(Throwable e)
		 {
			 throw new RuntimeException(e);
		 }finally
		 {
			 CycleRefenceContext.destory();
		 }
	}
	
	public static JSONValue parser(String json) throws TokenStreamException, RecognitionException
	{
		 JSONParser parser = new JSONParser(new StringReader(json));  
		 return  parser.nextValue();
	}
	public static Object toJava(JSONValue v,Class cls) throws MapperException
	{
		return JSONMapper.toJava(v,cls);
	}
	
}
