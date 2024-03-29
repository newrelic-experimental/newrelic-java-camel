package org.apache.camel.impl.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.Util;

@Weave
public abstract class DefaultProducerTemplate {

	@Trace(dispatcher=true)
	protected CompletableFuture<Exchange> asyncSendExchange(Endpoint endpoint, ExchangePattern pattern, Processor processor, Processor resultProcessor,Exchange inExchange) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, inExchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		if(endpoint != null) {
			String uri = endpoint.getEndpointUri();
			if(uri != null && !uri.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",uri);
				NewRelic.getAgent().getTracedMethod().addCustomAttribute("EndpointURI", uri);
			}
		}
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public Exchange send(Endpoint endpoint, Exchange exchange, Processor resultProcessor) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		
		if(endpoint != null) {
			String uri = endpoint.getEndpointUri();
			if(uri != null && !uri.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",uri);
				NewRelic.getAgent().getTracedMethod().addCustomAttribute("EndpointURI", uri);
			}
		}
		return Weaver.callOriginal();
	}

}
