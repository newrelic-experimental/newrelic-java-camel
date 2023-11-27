package org.apache.camel;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.CamelMessageHeaders;

@Weave(type = MatchType.Interface)
public abstract class ConsumerTemplate {

	@Trace(dispatcher = true)
	public Exchange receive(String endpointUri) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("EndpointURI", endpointUri);
		Exchange exchange = Weaver.callOriginal();
		Message message = exchange.getMessage();
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelMessageHeaders(message));
		return exchange;
	}
	
	@Trace(dispatcher = true)
	public Exchange receive(String endpointUri, long timeout) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("EndpointURI", endpointUri);
		Exchange exchange = Weaver.callOriginal();
		if (exchange != null) {
			Message message = exchange.getMessage();
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelMessageHeaders(message));
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return exchange;
	}
	
	@Trace(dispatcher = true)
	public Exchange receive(Endpoint endpoint) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("EndpointURI", endpoint.getEndpointUri());
		Exchange exchange = Weaver.callOriginal();
		Message message = exchange.getMessage();
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelMessageHeaders(message));
		return exchange;
	}
	
	@Trace(dispatcher = true)
	public Exchange receive(Endpoint endpoint, long timeout) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("EndpointURI", endpoint.getEndpointUri());
		Exchange exchange = Weaver.callOriginal();
		if (exchange != null) {
			Message message = exchange.getMessage();
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelMessageHeaders(message));
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return exchange;
	}
	
	@Trace(dispatcher = true)
	public Exchange receiveNoWait(String endpointUri) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("EndpointURI", endpointUri);
		Exchange exchange = Weaver.callOriginal();
		if (exchange != null) {
			Message message = exchange.getMessage();
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelMessageHeaders(message));
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return exchange;
	}
	
	@Trace(dispatcher = true)
	public Exchange receiveNoWait(Endpoint endpoint) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("EndpointURI", endpoint.getEndpointUri());
		Exchange exchange = Weaver.callOriginal();
		if (exchange != null) {
			Message message = exchange.getMessage();
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelMessageHeaders(message));
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return exchange;
	}
}
