package com.newrelic.instrumentation.camel.kafka;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class HeaderListWrapper implements Headers {
	
	private List<Header> headerList = null;
	
	public HeaderListWrapper(List<Header> list) {
		headerList = list;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public String getHeader(String name) {
		for(Header header : headerList) {
			if(header.key().equals(name)) {
				return new String(header.value());
			}
		}
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
		Header header = new RecordHeader(name, value.getBytes());
		headerList.add(header);
	}

	@Override
	public void addHeader(String name, String value) {
		Header header = new RecordHeader(name, value.getBytes());
		headerList.add(header);
	}

	@Override
	public Collection<String> getHeaderNames() {
		List<String> names = new ArrayList<>();
		for(Header header : headerList) {
			names.add(header.key());
		}
		return names;
	}

	@Override
	public boolean containsHeader(String name) {
		return getHeaderNames().contains(name);
	}

}
