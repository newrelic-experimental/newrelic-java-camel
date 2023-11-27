package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName="org.apache.camel.processor.ChoiceProcessor")
public abstract class ChoiceProcessor_instrumentation {

	@Trace(dispatcher=true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		String classname = getClass().getSimpleName();
		CamelHeaders headers = new CamelHeaders(exchange);
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, headers);
		Endpoint endpoint = exchange.getFromEndpoint();
		if(endpoint != null) {
			String endpointURI = endpoint.getEndpointBaseUri();
			if(endpointURI != null && !endpointURI.isEmpty()) {
				Util.recordValue(attributes, "EndpointURI", endpointURI);
			}
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		String routeID = exchange != null ? exchange.getFromRouteId() : null;
		if(routeID != null && !routeID.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",classname,"process",routeID});
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "ChoiceProcessor", new String[] {"ChoiceProcessor",routeID});
		}
		return Weaver.callOriginal();
	}
	
}
