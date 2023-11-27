package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName="org.apache.camel.impl.engine.SharedCamelInternalProcessor")
public class SharedInternalProcessor_instrumentation {

	@Trace
	public boolean process(Exchange exchange, AsyncCallback callback, AsyncProcessor processor, Processor resultProcessor) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(exchange));

		return Weaver.callOriginal();
	}
}
