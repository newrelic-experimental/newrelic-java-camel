package com.nr.instrumentation.camel.jms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.camel.Message;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class CamelHeaders implements Headers {

	private Message msg = null;

	public CamelHeaders(Message message) {
		msg = message;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public String getHeader(String name) {
		Object header = msg.getHeader(name);

		return header != null ? header.toString() : null;
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
		msg.setHeader(name, value);
	}

	@Override
	public void addHeader(String name, String value) {
		msg.setHeader(name, value);
	}

	@Override
	public Collection<String> getHeaderNames() {
		Map<String, Object> headerMap = msg.getHeaders();
		return headerMap != null ? headerMap.keySet() : new ArrayList<String>();
	}

	@Override
	public boolean containsHeader(String name) {
		return getHeaderNames().contains(name);
	}

}
