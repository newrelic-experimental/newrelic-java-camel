package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass,originalName="org.apache.camel.processor.CamelInternalProcessor")
public abstract class CamelInternalProcessor_instrumentation {

	@Trace(async=true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);
		if(token != null && token.isActive()) {
			token.link();
			Map<String, Object> attributes = new HashMap<String, Object>();
			Util.recordExchange(attributes, exchange);
			
			TracedMethod traced = NewRelic.getAgent().getTracedMethod();
			if(exchange != null && exchange.getFromRouteId() != null) {
				traced.setMetricName("Custom","CamelInternalProcessor","process",exchange.getFromRouteId());
			}
			if(!attributes.isEmpty()) {
				traced.addCustomAttributes(attributes);
			}
		}
		
		return Weaver.callOriginal();
	}
}
