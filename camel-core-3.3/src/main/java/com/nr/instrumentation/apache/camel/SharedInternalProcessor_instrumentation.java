package com.nr.instrumentation.apache.camel;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName="org.apache.camel.processor.SharedCamelInternalProcessor")
public class SharedInternalProcessor_instrumentation {

	@Trace
	public boolean process(Exchange exchange, AsyncCallback callback, AsyncProcessor processor, Processor resultProcessor) {
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);

		if(token != null) {
			token.link();
		}

		return Weaver.callOriginal();
	}
}
