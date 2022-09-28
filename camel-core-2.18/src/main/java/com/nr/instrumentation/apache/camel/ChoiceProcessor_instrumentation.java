package com.nr.instrumentation.apache.camel;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

import com.newrelic.api.agent.HttpParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.wrappers.InboundMessageWrapper;

@Weave(originalName="org.apache.camel.processor.ChoiceProcessor")
public abstract class ChoiceProcessor_instrumentation {

	@Trace(async=true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		String classname = getClass().getSimpleName();
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);
		if(token != null) {
			token.link();
		}
		Message inMessage = exchange.getIn();
		InboundMessageWrapper msgWrapper = new InboundMessageWrapper(inMessage);
		Endpoint endpoint = exchange.getFromEndpoint();
		if(endpoint != null) {
			String endpointURI = endpoint.getEndpointUri();
			if(endpointURI != null && !endpointURI.isEmpty()) {
				URI uri = URI.create(endpointURI);
				if(uri != null) {
					HttpParameters params = HttpParameters.library("Camel").uri(uri).procedure("process").inboundHeaders(msgWrapper).build();
					NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
				}

			}
		}
		String routeID = exchange != null ? exchange.getFromRouteId() : null;
		if(routeID != null && !routeID.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",classname,"process",routeID});
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "ChoiceProcessor", new String[] {"ChoiceProcessor",routeID});
		}
		return Weaver.callOriginal();
	}
	
}
