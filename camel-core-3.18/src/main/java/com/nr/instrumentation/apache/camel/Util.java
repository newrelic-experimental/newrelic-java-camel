package com.nr.instrumentation.apache.camel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.camel.AsyncProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExtendedExchange;
import org.apache.camel.Processor;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;

public class Util {

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
			NewRelic.getAgent().getTransaction().convertToWebTransaction();
		}
	}

	private static void recordExtendedExchange(Map<String, Object> attributes, ExtendedExchange exchange) {
		if(exchange != null) {
			recordValue(attributes, "HistoryNodeId", exchange.getHistoryNodeId());
			recordValue(attributes, "HistoryNodeLabel", exchange.getHistoryNodeLabel());
			recordValue(attributes, "HistoryNodeSource", exchange.getHistoryNodeSource());
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

	public static NRAsyncProcessorWrapper getWrapper(AsyncProcessor processor) {
		if(processor == null) return null;
		// no need for wrapper
		if((processor instanceof NRAsyncProcessorWrapper)) return null;

		Token t = NewRelic.getAgent().getTransaction().getToken();
		if(t != null && t.isActive()) {
			return new NRAsyncProcessorWrapper(processor, t);
		} else if(t != null) {
			t.expire();
			t = null;
		}
		return null;
	}

	public static NRProcessorWrapper getWrapper(Processor processor) {
		if(processor == null) return null;
		// no need for wrapper
		if((processor instanceof NRAsyncProcessorWrapper)) return null;

		if((processor instanceof NRProcessorWrapper)) return null;

		Token t = NewRelic.getAgent().getTransaction().getToken();
		if(t != null && t.isActive()) {
			return new NRProcessorWrapper(processor, t);
		} else if(t != null) {
			t.expire();
			t = null;
		}

		return null;
	}

	public static NRAsyncProcessorStart getStartingWrapper(AsyncProcessor asyncProcessor) {
		if(asyncProcessor == null) return null;
		if(asyncProcessor instanceof NRAsyncProcessorStart) return null;

		return new NRAsyncProcessorStart(asyncProcessor);
	}

	public static NRProcessorStart getStartingWrapper(Processor processor) {
		if(processor == null) return null;
		if(processor instanceof NRProcessorStart) return null;

		return new NRProcessorStart(processor);
	}
}