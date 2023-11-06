package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.TransportType;

public class NRProcessorWrapper implements Processor {
	
	private static boolean isTransformed = false;
	protected Route route = null;
	
	protected Processor delegate = null;
	
	public NRProcessorWrapper(Processor p,Route r) {
		delegate = p;
		route = r;
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}

	@Override
	@Trace(dispatcher=true)
	public void process(Exchange exchange) throws Exception {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		CamelHeaders headers = new CamelHeaders(exchange);
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, headers);
		if(route != null) {
			String routeId = route.getId();
			if(routeId != null && !routeId.isEmpty()) {
				NewRelic.getAgent().getTracedMethod().addCustomAttribute("RouteId", routeId);
				NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Consumer","Processor",delegate.getClass().getSimpleName(),"process",routeId);
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Camel-Consumer", "Consumer","Route",routeId);
			} else {
				NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Consumer","Processor",delegate.getClass().getSimpleName(),"process");
			}
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Producer","Processor",delegate.getClass().getSimpleName(),"process");
		}
		if(delegate != null) {
			delegate.process(exchange);
		}

	}

}
