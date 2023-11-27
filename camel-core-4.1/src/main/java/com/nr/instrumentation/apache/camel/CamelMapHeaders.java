package com.nr.instrumentation.apache.camel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class CamelMapHeaders implements Headers {
	
	private Map<String, Object> map = null;
	
	public CamelMapHeaders(Map<String,Object> m) {
		map = m;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public String getHeader(String name) {
		Object obj = map.get(name);
		if(obj != null) return obj.toString();
		return null;
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
		map.put(name, value);
	}

	@Override
	public void addHeader(String name, String value) {
		map.put(name, value);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return map.keySet();
	}

	@Override
	public boolean containsHeader(String name) {
		return getHeaderNames().contains(name);
	}

}
