package com.rayuniverse.wchat.gw.schema.domain;

import java.util.concurrent.ConcurrentHashMap;

public class SchemaSet {
	ConcurrentHashMap<String,Schema> set=new ConcurrentHashMap();
	public Schema getSchema(String key)
	{
		return set.get(key.toLowerCase());
	}
	public void addSchema(Schema schema)
	{
		String[] keys=schema.getKey().split(",");
		for(String k : keys)
		{
			set.put(k.toLowerCase(), schema);
		}
		
	}

}
