package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProducer;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName="org.apache.camel.processor.SendProcessor")
public abstract class SendProcessor_instrumentation {
	
	protected final Endpoint destination = Weaver.callOriginal();
	protected String routeId = Weaver.callOriginal();
	protected AsyncProducer producer = Weaver.callOriginal();
	

	@Trace(dispatcher=true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(exchange));
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		Util.recordValue(attributes, "RouteID", routeId);

		String dest = destination != null ? destination.getEndpointBaseUri() : null;
		Util.recordValue(attributes, "Destination", dest);
		Util.recordValue(attributes, "SendProcessor-Processor", producer != null ? producer.getClass().getName() : null);

		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		if(dest != null && !dest.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","SendProcessor","process",dest);
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("DestinationURI", dest);
		}
		return Weaver.callOriginal();
	}
	
}
