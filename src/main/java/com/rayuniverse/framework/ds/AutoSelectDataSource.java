package com.rayuniverse.framework.ds;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class AutoSelectDataSource implements DataSource{
	
	private DataSource syncDataSource;
	private DataSource asyncDataSource;
	
	public DataSource getSyncDataSource() {
		return syncDataSource;
	}

	public void setSyncDataSource(DataSource syncDataSource) {
		this.syncDataSource =  syncDataSource;
	}

	public DataSource getAsyncDataSource() {
		return asyncDataSource;
	}

	public void setAsyncDataSource(DataSource asyncDataSource) {
		this.asyncDataSource = asyncDataSource;
	}

	public PrintWriter getLogWriter() throws SQLException {
	   throw new RuntimeException("Not Support");
	}

	public int getLoginTimeout() throws SQLException {
		 throw new RuntimeException("Not Support");
	}

	public void setLogWriter(PrintWriter arg0) throws SQLException {
		 throw new RuntimeException("Not Support");
		
	}

	public void setLoginTimeout(int arg0) throws SQLException {
		 throw new RuntimeException("Not Support");
		
	}

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		 throw new RuntimeException("Not Support");
	}

	public <T> T unwrap(Class<T> arg0) throws SQLException {
		 throw new RuntimeException("Not Support");
	}

	public Connection getConnection() throws SQLException {
		
		 
		if(DataSourceContext.isUseAsyncDataSource())
		{
			return asyncDataSource.getConnection();
		}
		else
		{
			return syncDataSource.getConnection();
		}
		
	}
	

	public Connection getConnection(String arg0, String arg1)
			throws SQLException {
		
		 
		if(DataSourceContext.isUseAsyncDataSource())
		{
			return asyncDataSource.getConnection(arg0,arg1);
		}
		else
		{
			return syncDataSource.getConnection(arg0,arg1);
		}
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
