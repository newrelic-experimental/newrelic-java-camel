package org.apache.camel.support;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.camel.NRBiConsumer;
import com.nr.instrumentation.apache.camel.Util;

@Weave(type = MatchType.BaseClass)
public abstract class DefaultAsyncProducer {
	
	@Trace(dispatcher = true)
	public void process(Exchange exchange) {
		HashMap<String, Object> attributes = new HashMap<>();
		Util.recordExchange(attributes, exchange);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","Camel","AsyncProducer",getClass().getSimpleName(),"process");
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public CompletableFuture<Exchange> processAsync(Exchange exchange) {
		HashMap<String, Object> attributes = new HashMap<>();
		Util.recordExchange(attributes, exchange);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","Camel","AsyncProducer",getClass().getSimpleName(),"processAsync");
		CompletableFuture<Exchange> result = Weaver.callOriginal();
		String segmentName = "AsyncProcessor/Processing";
		String fromRoute = exchange.getFromRouteId();
		if(fromRoute != null && !fromRoute.isEmpty()) {
			segmentName = "ProcessAsync/RouteId/" + fromRoute;
		} else {
			Endpoint endPt = exchange.getFromEndpoint();
			if(endPt != null) {
				String base = endPt.getEndpointBaseUri();
				if(base != null && !base.isEmpty()) {
					segmentName = "ProcessAsync/EndPoint/" + base;
				}
			}
		}
		
		NRBiConsumer<Exchange> consumer = new NRBiConsumer<Exchange>(segmentName);
		return result.whenComplete(consumer);
	}

}