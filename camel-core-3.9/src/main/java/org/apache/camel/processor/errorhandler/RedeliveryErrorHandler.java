package org.apache.camel.processor.errorhandler;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.ExtendedExchange;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class RedeliveryErrorHandler {
	
	@Weave
	protected static class SimpleTask {
		
		private ExtendedExchange exchange = Weaver.callOriginal();		
		
		public void run() {
			List<String> names = new ArrayList<>();
			names.add("Custom");
			names.add("Camel");
			names.add("RedeliveryErrorHandler");
			names.add("SimpleTask");
			if(exchange != null) {
				String routeId = exchange.getFromRouteId();
				if(routeId != null) {
					names.add(routeId);
				}
				
			}
			String[] metricNames = new String[names.size()];
			names.toArray(metricNames);
			NewRelic.getAgent().getTracedMethod().setMetricName(metricNames);
			Weaver.callOriginal();
		}
		
	}

	@Weave
	protected static class RedeliveryTask {
		
        private ExtendedExchange exchange = Weaver.callOriginal();
        
        
		public void run() {
			List<String> names = new ArrayList<>();
			names.add("Custom");
			names.add("Camel");
			names.add("RedeliveryErrorHandler");
			names.add("RedeliveryTask");
			if(exchange != null) {
				String routeId = exchange.getFromRouteId();
				if(routeId != null) {
					names.add(routeId);
				}
				
			}
			String[] metricNames = new String[names.size()];
			names.toArray(metricNames);
			NewRelic.getAgent().getTracedMethod().setMetricName(metricNames);
			Weaver.callOriginal();
		}

	}
}
