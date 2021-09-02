package org.apache.camel.impl;

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

@Weave
public abstract class DefaultProducerTemplate {

	@Trace(dispatcher=true)
	protected CompletableFuture<Exchange> asyncSendExchange(Endpoint endpoint, ExchangePattern pattern, Processor processor, Processor resultProcessor,Exchange inExchange) {
		if(endpoint != null) {
			String uri = endpoint.getEndpointUri();
			if(uri != null && !uri.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",uri);
			}
		}
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public <T> T requestBody(Object body, Class<T> type) {
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public <T> T requestBody(String endpointUri, Object body, Class<T> type) {
		if(endpointUri != null && !endpointUri.isEmpty()) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",endpointUri);
		}
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public <T> T requestBody(Endpoint endpoint, Object body, Class<T> type) {
		if(endpoint != null) {
			String endpointUri = endpoint.getEndpointUri();
			if(endpointUri != null && !endpointUri.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",endpointUri);
			}
		}
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public <T> T requestBodyAndHeader(String endpointUri, Object body, String header, Object headerValue, Class<T> type) {
		if(endpointUri != null && !endpointUri.isEmpty()) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",endpointUri);
		}
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public <T> T requestBodyAndHeader(Endpoint endpoint, Object body, String header, Object headerValue, Class<T> type) {
		if(endpoint != null) {
			String endpointUri = endpoint.getEndpointUri();
			if(endpointUri != null && !endpointUri.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",endpointUri);
			}
		}
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public <T> T requestBodyAndHeaders(String endpointUri, Object body, Map<String, Object> headers, Class<T> type) {
		if(endpointUri != null && !endpointUri.isEmpty()) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",endpointUri);
		}
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public <T> T requestBodyAndHeaders(Endpoint endpoint, Object body, Map<String, Object> headers, Class<T> type) {
		if(endpoint != null) {
			String endpointUri = endpoint.getEndpointUri();
			if(endpointUri != null && !endpointUri.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",endpointUri);
			}
		}
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public Exchange send(Endpoint endpoint, Exchange exchange) {
		if(endpoint != null) {
			String endpointUri = endpoint.getEndpointUri();
			if(endpointUri != null && !endpointUri.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",endpointUri);
			}
		}
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public Exchange send(Endpoint endpoint, ExchangePattern pattern, Processor processor) {
		if(endpoint != null) {
			String endpointUri = endpoint.getEndpointUri();
			if(endpointUri != null && !endpointUri.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",endpointUri);
			}
		}
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public Exchange send(Endpoint endpoint, ExchangePattern pattern, Processor processor, Processor resultProcessor) {
		if(endpoint != null) {
			String endpointUri = endpoint.getEndpointUri();
			if(endpointUri != null && !endpointUri.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",endpointUri);
			}
		}
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public Exchange send(Endpoint endpoint, Processor processor) {
		if(endpoint != null) {
			String endpointUri = endpoint.getEndpointUri();
			if(endpointUri != null && !endpointUri.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Producer", "Custom","Producer",endpointUri);
			}
		}
		return Weaver.callOriginal();
	}

	
}
