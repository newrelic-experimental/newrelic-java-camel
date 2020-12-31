package com.nr.instrumentation.apache.camel.wrappers;

import org.apache.camel.Exchange;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.InboundHeaders;

public class InboundExchangeWrapper implements InboundHeaders {
	
	private Exchange exchange;
	
	public InboundExchangeWrapper(Exchange ex) {
		exchange = ex;
	}

	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	public String getHeader(String name) {
		return (String) exchange.getProperty(name);
	}

}