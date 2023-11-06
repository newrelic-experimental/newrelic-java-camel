package com.nr.instrumentation.apache.camel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class CamelHeaders implements Headers {
	
	private Exchange exchange = null;
	
	public CamelHeaders(Exchange ex) {
		exchange = ex;
	}

	@Override
	public void addHeader(String name, String value) {
		exchange.setProperty(name, value);
	}

	@Override
	public boolean containsHeader(String name) {
		return getHeaderNames().contains(name);
	}

	@Override
	public String getHeader(String name) {
		return (String) exchange.getProperty(name);
	}

	@Override
	public Collection<String> getHeaderNames() {
		Map<String, Object> props = exchange.getProperties();
		
		return props != null ? props.keySet() : Collections.emptyList();
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		List<String> list = new ArrayList<>();
		String value = getHeader(name);
		if(value != null && !value.isEmpty()) {
			list.add(value);
		}
		return list;
	}

	@Override
	public void setHeader(String name, String value) {
		exchange.setProperty(name, value);
	}

}
