package com.nr.instrumentation.apache.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

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
	@Trace(async=true)
	public void process(Exchange exchange) throws Exception {
		Map<String, Object> attributes = new HashMap<String, Object>();
		Util.recordExchange(attributes, exchange);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Token token = exchange.getProperty(Util.NRTOKENPROPERTY,Token.class);

		if(token != null) {
			token.link();
		}
		if(route != null) {
			String routeId = route.getId();
			if(routeId != null && !routeId.isEmpty()) {
				NewRelic.getAgent().getTracedMethod().addCustomAttribute("RouteId", routeId);
				NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Processor",delegate.getClass().getSimpleName(),"process",routeId);
			} else {
				NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Processor",delegate.getClass().getSimpleName(),"process");
			}
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Processor",delegate.getClass().getSimpleName(),"process");
		}
		if(delegate != null) {
			delegate.process(exchange);
		}

	}

}
