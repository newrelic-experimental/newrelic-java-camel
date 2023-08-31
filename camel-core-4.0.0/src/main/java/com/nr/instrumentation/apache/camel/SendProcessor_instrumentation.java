package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.wrappers.OutboundExchangeWrapper;

@Weave(originalName="org.apache.camel.processor.SendProcessor")
public abstract class SendProcessor_instrumentation {
	
	protected final Endpoint destination = Weaver.callOriginal();

	@Trace(async=true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);

		if(token != null) {
			token.link();
		}

		String dest = destination != null ? destination.getEndpointUri() : null;
		if(dest != null && !dest.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","SendProcessor","process",dest);
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("DestinationURI", dest);
		}
		OutboundExchangeWrapper wrapper = new OutboundExchangeWrapper(exchange);
		NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders(wrapper);
		return Weaver.callOriginal();
	}
	
}
