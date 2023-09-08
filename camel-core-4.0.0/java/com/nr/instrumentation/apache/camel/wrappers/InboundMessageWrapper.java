package com.nr.instrumentation.apache.camel.wrappers;

import org.apache.camel.Message;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.InboundHeaders;

public class InboundMessageWrapper implements InboundHeaders {
	
	private Message message;

	public InboundMessageWrapper(Message msg) {
		message = msg;
	}

	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	public String getHeader(String name) {
		return (String) message.getHeader(name);
	}

}