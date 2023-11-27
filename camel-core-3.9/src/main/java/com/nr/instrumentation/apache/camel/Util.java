package com.nr.instrumentation.apache.camel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExtendedExchange;
import org.apache.camel.spi.Synchronization;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;

public class Util {

	public static final String NRTOKENPROPERTY = "newrelic.asynctoken";
	private static final List<String> ignores;
	
	static {
		ignores = new ArrayList<>();
		ignores.add("org.apache.camel.impl.engine.CamelInternalProcessor$AsyncAfterTask");
	}

	@SuppressWarnings("deprecation")
	public static boolean isTranscationActive() {
		return AgentBridge.getAgent().getTransaction().isStarted();
	}
	
	public static NRRunnable getRunnable(Runnable runnable) {
		String classname = runnable.getClass().getName();
		if(ignores.contains(classname)) return null;
		
		if(runnable instanceof NRRunnable) return (NRRunnable)runnable;
		
		Token token = NewRelic.getAgent().getTransaction().getToken();
		if(token != null && token.isActive()) {
			return new NRRunnable(runnable, token);
		} else if(token != null) {
			token.expire();
			token = null;
		}
		
		return null;
	}
	
	public static String getMethodName(Method m) {
		if(m == null) return "UnknownClass.UnknownMethod";

		String classname = m.getDeclaringClass().getSimpleName();
		String methodName = m.getName();

		return classname + "." + methodName;
	}

	public static void addCompletionIfNeeded(ExtendedExchange exchange) {
		if(exchange == null) return;

		List<Synchronization> completions = exchange.handoverCompletions();
		if(completions == null || completions.isEmpty()) {
			NRSynchronization nrSync = new NRSynchronization();
			exchange.addOnCompletion(nrSync);
		} else {
			boolean hasNR = false;
			for(int i=0; i<completions.size() && !hasNR; i++) {
				Synchronization sync = completions.get(i);
				hasNR = sync instanceof NRSynchronization;
			}
			if(!hasNR) {
				NRSynchronization nrSync = new NRSynchronization();
				exchange.addOnCompletion(nrSync);
			}
		}
	}

	public static void recordExchange(Map<String, Object> attributes, Exchange exchange) {
		if(exchange != null) {
			recordValue(attributes, "ExchangeId", exchange.getExchangeId());
			CamelContext context = exchange.getContext();
			String ctxName = context != null ? context.getName() : null;
			recordValue(attributes, "CamelContextName", ctxName);
			recordValue(attributes, "CamelContextManagementName", context.getManagementName());
			Endpoint endpoint = exchange.getFromEndpoint();
			if(endpoint != null) {
				recordValue(attributes, "From_EndPointURI", endpoint.getEndpointBaseUri());
			}
			recordValue(attributes, "FromRouteId", exchange.getFromRouteId());
			if(exchange instanceof ExtendedExchange) {
				ExtendedExchange extended = (ExtendedExchange)exchange;
				recordExtendedExchange(attributes, extended);
			}
		}
	}
	
	private static void recordExtendedExchange(Map<String, Object> attributes, ExtendedExchange exchange) {
		if(exchange != null) {
			recordValue(attributes, "HistoryNodeId", exchange.getHistoryNodeId());
			recordValue(attributes, "HistoryNodeLabel", exchange.getHistoryNodeLabel());
			Map<String, Object> internalProps = exchange.getInternalProperties();
			if (internalProps != null) {
				for (String key : internalProps.keySet()) {
					Object value = internalProps.get(key);
					recordValue(attributes, "InternalProperty-"+key, value);
				} 
			}
			recordValue(attributes, "HistoryNodeId", exchange.getInternalProperties());

		}
	}
	
	public static void recordValue(Map<String,Object> attributes, String key, Object value) {
		if(key != null && !key.isEmpty() && value != null) {
			attributes.put(key, value);
		}
	}

	public static void reportExchange(Exchange exchange) {
		HashMap<String, Object> attributes = new HashMap<>();
		recordValue(attributes, "ExchangeId", exchange.getExchangeId());
		Map<String, Object> properties = exchange.getAllProperties();
		Set<String> keys = properties.keySet();
		for(String key : keys) {
			recordValue(attributes, key, properties.get(key));
		}
		NewRelic.getAgent().getInsights().recordCustomEvent("Pipeline_Exchange", attributes);
	}
}