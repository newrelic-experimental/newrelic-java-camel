package org.apache.camel;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;

public class Util {

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
		NewRelic.getAgent().getInsights().recordCustomEvent("Pipeline_Exchange", attributes);
	}
}