package org.apache.camel.impl.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.NRProcessorWrapper;
import com.nr.instrumentation.apache.camel.Util;

@Weave
public abstract class DefaultProducerTemplate {

	@Trace(dispatcher=true)
	protected CompletableFuture<Exchange> asyncSendExchange(Endpoint endpoint, ExchangePattern pattern, Processor processor, Processor resultProcessor,Exchange inExchange) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, inExchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Token token = (Token) inExchange.getProperty(Util.NRTOKENPROPERTY);
		if(token == null) {
			token = NewRelic.getAgent().getTransaction().getToken();
			if(token != null && token.isActive()) {
				inExchange.setProperty(Util.NRTOKENPROPERTY, token);
			} else if(token != null) {
				token.expire();
				token = null;
			}
		}
		if(endpoint != null) {
			String uri = endpoint.getEndpointUri();
			if(uri != null && !uri.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",uri);
			}
		}
		if(resultProcessor != null && !(resultProcessor instanceof NRProcessorWrapper)) {
			resultProcessor = new NRProcessorWrapper(resultProcessor, null);
		}
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public Exchange send(Endpoint endpoint, Exchange exchange, Processor resultProcessor) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Token token = (Token) exchange.getProperty(Util.NRTOKENPROPERTY);
		if(token == null) {
			token = NewRelic.getAgent().getTransaction().getToken();
			if(token != null && token.isActive()) {
				exchange.setProperty(Util.NRTOKENPROPERTY, token);
			} else if(token != null) {
				token.expire();
				token = null;
			}
		}
		if(endpoint != null) {
			String uri = endpoint.getEndpointUri();
			if(uri != null && !uri.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",uri);
				NewRelic.getAgent().getTracedMethod().addCustomAttribute("EndpointURI", uri);
			}
		}
		if(resultProcessor != null && !(resultProcessor instanceof NRProcessorWrapper)) {
			resultProcessor = new NRProcessorWrapper(resultProcessor, null);
		}
		return Weaver.callOriginal();
	}


}
