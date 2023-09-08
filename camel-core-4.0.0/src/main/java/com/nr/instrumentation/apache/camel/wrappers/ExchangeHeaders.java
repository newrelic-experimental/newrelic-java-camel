package com.nr.instrumentation.apache.camel.wrappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class ExchangeHeaders implements Headers {
	
	private Exchange exchange;

	public ExchangeHeaders(Exchange ex) {
		exchange = ex;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public String getHeader(String name) {
		return (String) exchange.getProperty(name);
	}

	@Override
	public Collection<String> getHeaders(String name) {
		String value = getHeader(name);
		List<String> list = new ArrayList<>();
		if(value != null && !value.isEmpty()) {
			list.add(value);
		}
		return list;
	}

	@Override
	public void setHeader(String name, String value) {
		exchange.setProperty(name, value);
	}

	@Override
	public void addHeader(String name, String value) {
		exchange.setProperty(name, value);
	}

	@Override
	public Collection<String> getHeaderNames() {
		Map<String, Object> allProps = exchange.getProperties();
		
		return allProps == null ? new ArrayList<String>() : allProps.keySet();
	}

	@Override
	public boolean containsHeader(String name) {
		return getHeaderNames().contains(name);
	}

}
