package com.nr.instrumentation.apache.camel.wrappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.camel.Message;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class MessageHeaders implements Headers {
	
	private Message message;
	
	public MessageHeaders(Message msg) {
		message = msg;
	}


	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public String getHeader(String name) {
		return (String) message.getHeader(name);
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
		message.setHeader(name, value);
	}

	@Override
	public void addHeader(String name, String value) {
		message.setHeader(name, value);
	}

	@Override
	public Collection<String> getHeaderNames() {
		Map<String, Object> headers = message.getHeaders();
		return headers != null ? headers.keySet() : new ArrayList<String>();
	}

	@Override
	public boolean containsHeader(String name) {
		return getHeaderNames().contains(name);
	}

}
