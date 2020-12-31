package com.nr.instrumentation.apache.camel;

import org.apache.camel.AsyncCallback;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass,originalName="org.apache.camel.processor.CamelInternalProcessor")
public abstract class CamelInternalProcessor_instrumentation {

	@Trace(async=true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
	
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);
		if(token != null) {
			token.link();
		}
		
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","CamelInternalProcessor","process",exchange.getFromRouteId());

		String exchangeID = exchange != null ? exchange.getExchangeId() : null;
		if(exchangeID != null) {
			NewRelic.addCustomParameter("Exchange ID", exchangeID);
		}
		CamelContext context = exchange.getContext();
		String ctxName = context != null ? context.getName() : null;
		if(ctxName != null && !ctxName.isEmpty()) {
			NewRelic.addCustomParameter("Context", ctxName);
		}
		return Weaver.callOriginal();
	}
}
