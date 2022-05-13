package com.hcl.stargate.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Response {
	@JacksonXmlProperty(isAttribute = true)
	private String status;
	 @JacksonXmlProperty(localName = "result")
	Result result;
	String msg ;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
