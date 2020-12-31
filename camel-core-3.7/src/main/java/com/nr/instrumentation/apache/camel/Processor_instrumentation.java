package com.nr.instrumentation.apache.camel;

import java.net.URI;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

import com.newrelic.api.agent.HttpParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.wrappers.InboundMessageWrapper;

@Weave(type=MatchType.Interface,originalName="org.apache.camel.Processor")
public abstract class Processor_instrumentation {

	@Trace(dispatcher=true)
	public void process(Exchange exchange) {
		String classname = getClass().getSimpleName();
		
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
		String exchangeID = exchange != null ? exchange.getExchangeId() : null;
		if(exchangeID != null)
			NewRelic.addCustomParameter("Exchange ID", exchangeID);
		String routeID = exchange != null ? exchange.getFromRouteId() : null;
		if(routeID != null && !routeID.isEmpty()) {
			NewRelic.addCustomParameter("From Route ID", routeID);
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Processor",classname,"process",routeID});
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "CamelProcessor", new String[] {"CamelProcessor",routeID});
		}
		CamelContext context = exchange.getContext();
		String ctxName = context != null ? context.getName() : null;
		if(ctxName != null && !ctxName.isEmpty()) {
			NewRelic.addCustomParameter("Context", ctxName);
		}
		Weaver.callOriginal();
	}
}
