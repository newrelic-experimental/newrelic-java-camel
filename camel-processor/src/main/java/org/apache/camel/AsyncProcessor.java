package org.apache.camel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class AsyncProcessor {

	@Trace
    public boolean process(Exchange exchange, AsyncCallback callback) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		String routeID = exchange != null ? exchange.getFromRouteId() : null;
		if(routeID != null && !routeID.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","AsyncProcessor",getClass().getSimpleName(),"process",routeID);
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","AsyncProcessor",getClass().getSimpleName(),"process");
		}
    	return Weaver.callOriginal();
    }

	@Trace
    public CompletableFuture<Exchange> processAsync(Exchange exchange) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		String routeID = exchange != null ? exchange.getFromRouteId() : null;
		if(routeID != null && !routeID.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","AsyncProcessor",getClass().getSimpleName(),"processAsync",routeID);
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Camel","AsyncProcessor",getClass().getSimpleName(),"processAsync");
		}
    	return Weaver.callOriginal();
    }

}
