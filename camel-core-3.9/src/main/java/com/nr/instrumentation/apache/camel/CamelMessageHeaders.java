package com.nr.instrumentation.apache.camel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.apache.camel.Message;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;
import com.newrelic.api.agent.NewRelic;

public class CamelMessageHeaders implements Headers {
	
	private Message message = null;
	
	public CamelMessageHeaders(Message ex) {
		message = ex;
	}

	@Override
	public void addHeader(String name, String value) {
		message.setHeader(name, value);
	}

	@Override
	public boolean containsHeader(String name) {
		return getHeaderNames().contains(name);
	}

	@Override
	public String getHeader(String name) {
		Object value = message.getHeader(name);
		if(value == null) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "Message Header is null");
			return null;
		}
		if(value instanceof String) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "Message Header is string: {0}", value.toString());
			return (String)value;
		}
		if(value instanceof byte[]) {
			String v = new String((byte[])value);
			NewRelic.getAgent().getLogger().log(Level.FINE, "Message Header is byte array: {0}", v);
			return new String((byte[])value);
		}
		NewRelic.getAgent().getLogger().log(Level.FINE, "Message header is not a string, it is {0}", value.getClass());
		return value.toString();
	}

	@Override
	public Collection<String> getHeaderNames() {
		Map<String, Object> props = message.getHeaders();
		
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
		message.setHeader(name, value);
	}

}
