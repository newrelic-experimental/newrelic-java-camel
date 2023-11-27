package com.newrelic.instrumentation.labs.camel.netty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class ExchangeHeaders implements Headers {
	
	private Exchange exchange = null;
	
	public ExchangeHeaders(Exchange ex) {
		exchange = ex;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public String getHeader(String name) {
		Object objValue = exchange.getProperty(name);
		if(objValue != null) return objValue.toString();
		
		Message message = exchange.getMessage();
		if(message != null) {
			objValue = message.getHeader(name);
			if(objValue != null) return objValue.toString();
		}
		return null;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		List<String>  list = new ArrayList<>();
		String value = getHeader(name);
		if(value != null && !value.isEmpty()) {
			list.add(value);
		}
		return list;
	}

	@Override
	public void setHeader(String name, String value) {
		exchange.setProperty(name, value);
		Message msg = exchange.getMessage();
		if(msg != null) {
			msg.setHeader(name, value);
		}
	}

	@Override
	public void addHeader(String name, String value) {
		exchange.setProperty(name, value);
		Message msg = exchange.getMessage();
		if(msg != null) {
			msg.setHeader(name, value);
		}
	}

	@Override
	public Collection<String> getHeaderNames() {
		Map<String, Object> props = exchange.getProperties();
		return props != null ? props.keySet() : Collections.emptyList();
	}

	@Override
	public boolean containsHeader(String name) {
		return getHeaderNames().contains(name);
	}

}
