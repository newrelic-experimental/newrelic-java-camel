package com.newrelic.instrumentation.labs.camel.netty;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;

public class Util {

	public static final String NRTOKENPROPERTY = "newrelic.asynctoken";

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
				recordValue(attributes, "From_EndPointURI", endpoint.getEndpointUri());
			}
			recordValue(attributes, "FromRouteId", exchange.getFromRouteId());
		}
	}
	
	private static void recordValue(Map<String,Object> attributes, String key, Object value) {
		if(key != null && !key.isEmpty() && value != null) {
			attributes.put(key, value);
		}
	}
	
}