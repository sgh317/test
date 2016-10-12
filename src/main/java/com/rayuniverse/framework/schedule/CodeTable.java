package com.rayuniverse.framework.schedule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CodeTable implements Map{

   HashMap targetMap=new HashMap();
	 
	public int size() {
	 
		return targetMap.size();
	}

	 
	public boolean isEmpty() {
	 
		return targetMap.isEmpty();
	}

 
	public boolean containsKey(Object key) {
	 
		return targetMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		
		return targetMap.containsValue(value);
	}

	 
	public Object get(Object key) {
		ReloadableResource reloadResource=(ReloadableResource) targetMap.get(key);
		if(reloadResource!=null)
		{
			return reloadResource.getResource();
		}
		return null;
	}

	 
	public Object put(Object key, Object value) {
	 
		return targetMap.put(key, value);
	}

	 
	public Object remove(Object key) {
		targetMap.remove(key);
		return null;
	}

	 
	public void putAll(Map m) {
		targetMap.putAll(m);
		
	}

	 
	public void clear() {
		targetMap.clear();
		
	}

	 
	public Set keySet() {
	 
		return targetMap.keySet();
	}

	 
	public Collection values() {
	 
		return targetMap.values();
	}

 
	public Set entrySet() {
	 
		return targetMap.entrySet();
	}

}
