package com.nr.instrumentation.apache.camel;

import java.util.HashMap;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass,originalName="org.apache.camel.impl.engine.CamelInternalProcessor")
public abstract class CamelInternalProcessor_instrumentation {

	@Trace(dispatcher = true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		HashMap<String, Object> attributes = new HashMap<>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		String routeId = exchange != null ?  exchange.getFromRouteId() : null;
		if(exchange != null) {
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(exchange));
		}
		if(routeId != null && !routeId.isEmpty()) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "CamelInternalProcessor", "Camel",getClass().getSimpleName(),routeId);
		}
		return Weaver.callOriginal();
	}
}
