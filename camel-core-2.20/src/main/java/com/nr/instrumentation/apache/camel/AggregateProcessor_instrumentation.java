package com.nr.instrumentation.apache.camel;

import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName="org.apache.camel.processor.aggregate.AggregateProcessor")
public abstract class AggregateProcessor_instrumentation {

	@Trace(async=true)
	protected void doProcess(Exchange exchange) {
		String routeID = exchange != null ? exchange.getFromRouteId() : null;
		if(routeID != null && !routeID.isEmpty()) {
			NewRelic.addCustomParameter("From Route ID", routeID);
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","AggregateProcessor","doProcess",routeID});
		}
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);
		if(token != null) {
			token.link();
		}
		Weaver.callOriginal();
	}
}
