package com.rayuniverse.framework.ds;

public class DataSourceContext {
	static ThreadLocal<Boolean> asyncDataSource=new ThreadLocal();
	
	public static boolean isUseAsyncDataSource()
	{
		Boolean b=asyncDataSource.get();
		if(b==null)
		{
			return false;
		}
		return b;
	}
	
	public static void clear()
	{
		asyncDataSource.remove();
	}
	
	public static void setUseAsyncDataSource()
	{
		asyncDataSource.set(true);
	}

}
