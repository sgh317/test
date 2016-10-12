package com.rayuniverse.domain;

import java.util.Date;

public class TaskData extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3128835191187591893L;
	
	String invokeId;
	String processorId;
	int grp;
	String sync;
	String method;
	String executeFlag;
	String requestExecuteState;
	String returnExecuteState;
	String fallMessage;
	String paramterID;
	
	Date lockEndTime;
	
	byte[] context;
	String transCode;
	String channelNum;
	public String getInvokeId() {
		return invokeId;
	}
	public void setInvokeId(String invokeId) {
		this.invokeId = invokeId;
	}
	public String getProcessorId() {
		return processorId;
	}
	public void setProcessorId(String processorId) {
		this.processorId = processorId;
	}
	public int getGrp() {
		return grp;
	}
	public void setGrp(int grp) {
		this.grp = grp;
	}
	public String getSync() {
		return sync;
	}
	public void setSync(String sync) {
		this.sync = sync;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getExecuteFlag() {
		return executeFlag;
	}
	public void setExecuteFlag(String executeFlag) {
		this.executeFlag = executeFlag;
	}
	public String getRequestExecuteState() {
		return requestExecuteState;
	}
	public void setRequestExecuteState(String requestExecuteState) {
		this.requestExecuteState = requestExecuteState;
	}
	public String getReturnExecuteState() {
		return returnExecuteState;
	}
	public void setReturnExecuteState(String returnExecuteState) {
		this.returnExecuteState = returnExecuteState;
	}
	public String getFallMessage() {
		return fallMessage;
	}
	public void setFallMessage(String fallMessage) {
		this.fallMessage = fallMessage;
	}
	public String getParamterID() {
		return paramterID;
	}
	public void setParamterID(String paramterID) {
		this.paramterID = paramterID;
	}
	public Date getLockEndTime() {
		return lockEndTime;
	}
	public void setLockEndTime(Date lockEndTime) {
		this.lockEndTime = lockEndTime;
	}
	public byte[] getContext() {
		return context;
	}
	public void setContext(byte[] context) {
		this.context = context;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getChannelNum() {
		return channelNum;
	}
	public void setChannelNum(String channelNum) {
		this.channelNum = channelNum;
	}
}
