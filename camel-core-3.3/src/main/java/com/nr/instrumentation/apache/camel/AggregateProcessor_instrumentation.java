package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName="org.apache.camel.processor.aggregate.AggregateProcessor")
public abstract class AggregateProcessor_instrumentation {

	@Trace(async=true)
	 protected boolean doProcess(Exchange exchange, AsyncCallback callback) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		String routeID = exchange != null ? exchange.getFromRouteId() : null;
		if(routeID != null && !routeID.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","AggregateProcessor","doProcess",routeID});
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "CamelProcessor", new String[] {"AggregateProcessor",routeID});
		}
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);
		if(token != null) {
			token.link();
		}
	
		return Weaver.callOriginal();
	}
	
	@Trace(async=true)
	protected boolean doProcess(Exchange exchange, String key, AsyncCallback callback, boolean sync) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		String routeID = exchange != null ? exchange.getFromRouteId() : null;
		if(routeID != null && !routeID.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","AggregateProcessor","doProcess",routeID});
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "CamelProcessor", new String[] {"AggregateProcessor",routeID});
		}
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);
		if(token != null) {
			token.link();
		}
	
		return Weaver.callOriginal();
	}
}
