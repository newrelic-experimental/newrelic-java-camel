package com.nr.instrumentation.apache.camel.wrappers;

import org.apache.camel.Exchange;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;

public class OutboundExchangeWrapper implements OutboundHeaders {
	
	private Exchange exchange = null;
	
	public OutboundExchangeWrapper(Exchange e) {
		exchange = e;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public void setHeader(String name, String value) {
		exchange.setProperty(name, value);
	}

}
