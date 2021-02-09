package com.nr.instrumentation.camel.jms;

import java.util.Collection;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class MapHeaders implements Headers {

	@Override
	public HeaderType getHeaderType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHeader(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHeader(String name, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addHeader(String name, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<String> getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsHeader(String name) {
		// TODO Auto-generated method stub
		return false;
	}

}
