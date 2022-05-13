package com.hcl.stargate.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "response")
public class Result {
	

	private String key;
	 @JacksonXmlProperty(localName = "entry")
	    @JacksonXmlElementWrapper(useWrapping = false)
	    private List<Entry> entries = new ArrayList<>();
	private String phash;

	public String getPhash() {
		return phash;
	}
	public void setPhash(String phash) {
		this.phash = phash;
	}

	public List<Entry> getEntries() {
		return entries;
	}
	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}


}
