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
import com.nr.instrumentation.apache.camel.CamelHeaders;
import com.nr.instrumentation.apache.camel.CamelMapHeaders;
import com.nr.instrumentation.apache.camel.NRProcessorWrapper;
import com.nr.instrumentation.apache.camel.Util;

@Weave
public abstract class DefaultProducerTemplate {

	@Trace(dispatcher=true)
	protected CompletableFuture<Exchange> asyncSendExchange(Endpoint endpoint, ExchangePattern pattern, Processor processor, Processor resultProcessor,Exchange inExchange) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, inExchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		CamelHeaders headers = new CamelHeaders(inExchange);
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
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

	@Trace(dispatcher=true)
	public Exchange send(Endpoint endpoint, Exchange exchange, Processor resultProcessor) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		CamelHeaders headers = new CamelHeaders(exchange);
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
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


	protected Processor createBodyAndHeaders(Object body, Map<String, Object> headers) {
		CamelMapHeaders nrHeaders = new CamelMapHeaders(headers);
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(nrHeaders);
		return Weaver.callOriginal();
	}
}
