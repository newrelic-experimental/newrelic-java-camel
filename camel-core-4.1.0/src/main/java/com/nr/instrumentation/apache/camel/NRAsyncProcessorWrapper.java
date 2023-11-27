package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Route;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.TransportType;

public class NRAsyncProcessorWrapper extends NRProcessorWrapper implements AsyncProcessor {
	
	public NRAsyncProcessorWrapper(AsyncProcessor p,Route r) {
		super(p,r);
	}

	@Override
	@Trace(dispatcher=true)
	public boolean process(Exchange exchange, AsyncCallback callback) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(exchange));
		String[] names;
		if(route != null) {
			String routeId = route.getId();
			if(routeId != null && !routeId.isEmpty()) {
				names = new String[] {"Custom","AsyncProcessor",delegate.getClass().getSimpleName(),"process",routeId};
				NewRelic.getAgent().getTracedMethod().addCustomAttribute("RouteId", routeId);
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Processor", "Process",routeId);
			} else {
				names = new String[] {"Custom","AsyncProcessor",delegate.getClass().getSimpleName(),"process"};
			}
		} else {
			names = new String[] {"Custom","AsyncProcessor",delegate.getClass().getSimpleName(),"process"};
			
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(names);
		return ((AsyncProcessor)delegate).process(exchange, callback);
	}

	@Override
	@Trace(dispatcher = true)
	public CompletableFuture<Exchange> processAsync(Exchange exchange) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, new CamelHeaders(exchange));
		String[] names;
		if(route != null) {
			String routeId = route.getId();
			if(routeId != null && !routeId.isEmpty()) {
				names = new String[] {"Custom","AsyncProcessor",delegate.getClass().getSimpleName(),"process",routeId};
				NewRelic.getAgent().getTracedMethod().addCustomAttribute("RouteId", routeId);
			} else {
				names = new String[] {"Custom","AsyncProcessor",delegate.getClass().getSimpleName(),"process"};
			}
		} else {
			names = new String[] {"Custom","AsyncProcessor",delegate.getClass().getSimpleName(),"processAsync"};
			
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(names);
		return ((AsyncProcessor)delegate).processAsync(exchange);
	}

}
